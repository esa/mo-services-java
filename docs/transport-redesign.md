Transport Layer Redesign
========================

Status: draft / working design. Target: v16.0 (a deliberately breaking major
change at the Java level). This document is the agreed design reference and is
expected to evolve; open questions are tracked at the end.

Goals
-----

* Replace the inheritance-based `transport-generic` framework with a clear,
  composable, multi-threaded processing chain.
* Make the two flows explicit and independently readable: the **downward**
  (send) flow and the **upward** (receive) flow.
* Turn the MAL quality-of-service hooks, which are carried on the wire but never
  enforced today, into real, optional, composable behaviour.
* Let a transport declare what it already provides so the MAL never does the
  same work twice.
* Allow a feature to be selected per service.
* Add first-class message priority.
* Build in observation points (sniffing now, auditing later) between every step.
* Leave room for smart encodings, live audio/video, large-file transfer and a
  future DTN transport, without implementing them now.

Non-goals (this redesign)
-------------------------

* No DTN implementation. The design must be DTN-aware (a DTN transport can be
  added later); the Bundle Protocol itself is out of scope.
* No audio/video codecs or smart-encoding algorithms. The design must *enable*
  them; it does not deliver them.
* No change to the MAL wire format.

Hard constraints
----------------

The MAL standard freezes three things, which this redesign must not change:

* the **MAL Types**,
* the **Interaction Patterns**, and
* the **MAL Message Header**.

Everything on the wire stays MO/MAL-compliant, so interoperability with other
MAL implementations is preserved. The Java interfaces are **not** standardised
(the MAL Java binding standard was deprecated precisely to free the code), so
the Java SPI, module layout and class structure may be redesigned freely. New
per-message information (e.g. priority) must ride in existing header
extensibility (`Supplements`), never in a new header field.

The problem with what exists today
----------------------------------

* **Inheritance, not composition.** `esa.mo.mal.transport.gen.Transport<I, O>`
  is an abstract base that every transport (`transport-rmi`, `-tcpip`, `-http`,
  `-spp`, `-zmtp`) extends. Behaviour is bolted on by overriding; there is no way
  to compose independent concerns (encode, encrypt, compress, audit) cleanly.
* **No chain.** The send and receive paths are inline procedural code with a
  single encode/decode step and no defined insertion points. Adding a concern
  means editing the flow. This is the "spaghetti".
* **QoS is inert.** The only `QoSLevel` logic in any transport is
  `isSupportedQoSLevel()` returning "BESTEFFORT only". No code acts on ASSURED /
  QUEUED / TIMELY. The header carries the level across the wire and nothing
  honours it.
* **`MALTransportFactory` and the base `Transport` class disappear** in the new
  design; they force inheritance and are not worth preserving.

Target architecture
--------------------

### The chain

A message flows through an ordered list of **stages**. Each stage does one
concern and is independently testable. The chain is built once per channel from
the active feature policy and the transport's declared capabilities.

```
DOWNWARD (send): MAL message ───────────────────────────────► wire
  [ priority ] → [ encode ] → [ compress? ] → [ encrypt? ] →
  [ reliability/ordering? ] → [ segment? ] → [ transport.write ]

UPWARD (receive): wire ───────────────────────────────► MAL message
  [ transport.read ] → [ reassemble? ] → [ decrypt? ] →
  [ decompress? ] → [ decode ] → [ deliver (ordered?) ]
```

Every arrow is a potential **tap point** (see Observation). Stages marked `?`
are optional and present only when the feature is enabled *and* not already
provided by the transport.

### Stage SPI (shape, not final signatures)

* A transport is no longer a subclass; it is a small set of composed
  interfaces. At minimum: a **bearer** (`read`/`write` bytes, plus connection
  management) and a **capability descriptor**.
* A **stage** transforms a message on the way down and reverses it on the way
  up. Downward and upward are explicit (two methods, or two paired interfaces)
  so the code reads in both directions.
* Stages are **stateless by default** — which is what lets different messages
  run on different threads. A stage that needs cross-message context declares a
  **concurrency class** the scheduler respects:
  * **STATELESS** — parallel across messages (the default: encode, per-message
    encryption, message encoding).
  * **SHARED-READ-ONLY** — uses immutable shared context (e.g. a compression
    dictionary); still fully parallel.
  * **CHANNEL-SERIAL** — holds mutable per-channel state (e.g. a streaming
    compressor, or the sequence gate); serialized in order within a channel,
    parallel across channels.

### Capability model

Each transport advertises a capability set, e.g. `RELIABLE`, `ORDERED`,
`FLOW_CONTROLLED`, `SEGMENTS`. When the chain is built:

* if the transport already provides a capability, the corresponding MAL stage is
  **omitted** — TCP is reliable and ordered, so the MAL adds neither;
* if a user enables a feature the transport already provides, the MAL **ignores
  it** rather than doing the work twice;
* if the transport lacks it (e.g. SPP is not reliable, regardless of which SPP
  implementation flies), the MAL **inserts** the stage.

Capabilities are **static per transport type**: each transport declares a fixed
capability set, resolved at chain-build time. Per-connection negotiation is a
future idea (see Future ideas), not built now; because resolution happens at
chain-build time, it can be added later without reworking the stage SPI.

### Per-service feature policy

Features are selected **per service** (finer granularity, e.g. per operation,
is a later extension — see Open questions). The MAL binds a service to a feature
profile and builds the channel's chain from it. Because `QoSLevel` is a
per-message header field, per-service (and later per-operation) selection needs
no wire change: it is a local policy that decides which stages exist and how
they are configured.

### Multi-threading and ordered egress

The chain is multi-threaded: two messages can occupy different stages on
different CPU cores at the same time. Ordering is enforced **only where
required**, not by serialising the pipeline:

* On a channel with ordering enabled, each message gets a monotonic sequence
  number at **ingress**.
* The **egress** (the final `transport.write`) is sequence-gated through a
  reorder buffer: message *N* is written to the wire before *N+1*, even if *N+1*
  finished its stages first.
* The upward path is symmetric: ordered delivery to the MAL is gated at the
  delivery boundary, parallel everywhere else.

This keeps parallelism everywhere except the single boundary that the ordering
guarantee actually constrains.

Feature mapping
---------------

| Feature            | Where it lives            | Notes |
|--------------------|---------------------------|-------|
| Encode / decode    | encode/decode stage       | See Encoding. |
| Message encoding (opt) | encode-side transform | Intra-message, end-to-end, parallel. See Compression. |
| Encryption (opt)   | per-message, below encode | Transform/encode before encrypt. See Compression for the channel interaction. |
| Ordering (opt)     | ingress seq + egress gate | Omitted if transport is ORDERED. |
| Guaranteed delivery (opt) | reliability stage  | Omitted if transport is RELIABLE (e.g. inserted for SPP). |
| Priority           | `Supplements`, send-queue | See Priority. |
| Segmentation       | segment/reassemble stage  | SPP already segments; reuse the pattern. |
| Channel compression (opt) | transport stream   | Inter-message, hop-by-hop, serial. Omitted if transport compresses; off when encryption is on. See Compression. |
| Sniffing / auditing| tap between any steps      | See Observation. |

### Priority

Priority is the **only** item carried in `Supplements` for now. `Supplements` is
reserved for end-to-end, transport-agnostic metadata that must survive through
brokers; per-link stage metadata (encoding id, compression, encryption,
sequence, segmentation) stays in each transport's own framing, not here.

* Carried in the header `Supplements` as a **`UOctet`** (0–255). A wide field is
  used deliberately for headroom; the MAL Message Header is not touched.
* A small set of **named levels** over that range, spaced DiffServ-style so
  intermediate values (0–255) remain usable: `LOW` = 64, `NORMAL` = 128
  (default), `HIGH` = 192, and `CONTROL` = 255 reserved for control traffic such
  as the Heartbeat beat.
* **Convention: higher value = higher priority** (matches network QoS; avoids
  the Unix-`nice` "lower is higher" confusion).

**`NORMAL` is an implicit default that is never written to the wire.** Only
non-NORMAL priorities add a `Supplements` entry, so the common case carries zero
overhead — the same approach proto3 takes with default values. The rules that
keep the wire unambiguous:

* **Canonical write:** on encode, if the priority is `NORMAL` — whether set
  explicitly or never set — the priority supplement is **omitted**. "Unset" and
  "explicitly NORMAL" therefore produce the identical wire form.
* **Tolerant read:** on decode, **no priority supplement means `NORMAL`**. An
  explicit NORMAL value sent by another MAL implementation is accepted and read
  as NORMAL, but this implementation never produces one.
* **Accessor hides the default:** a `getPriority()` helper returns `NORMAL` when
  the supplement is absent, so callers never distinguish "missing" from
  "NORMAL".
* The priority entry is added or removed **without disturbing any other
  `Supplements`**.

**Scope: priority is end-to-end.** A broker must copy a non-NORMAL priority
through on forward, so "high priority through a broker" holds; an absent
(NORMAL) one naturally stays absent. Priority orders the local send queue first;
later it can feed DTN bundle priority and link scheduling.

### Encoding

The byte-level encoding SPI (`MALElementStreamFactory` →
`MALElementInputStream` / `MALElementOutputStream`, with `Encoder` / `Decoder` /
`BufferHolder`) is already clean and is **kept as-is**. Smart encodings are
added **above** it as a selection strategy, not by changing those interfaces:

* **Static encoding** (today): one factory per protocol, chosen by the
  `org.ccsds.moims.mo.mal.encoding.protocol.*` property; on receive, chosen by
  the encoding identifier the header now carries (added in 15.1).
* **Smart encoding** (future): a strategy that chooses or adapts the encoding at
  runtime — by content, link state, or negotiation — and still delegates the
  bytes to the existing stream factories. The wire mechanism already exists: the
  header encoding identifier lets the receiver decode whatever the sender chose.
* **Per-field** encoding, if ever wanted, is feasible without new interfaces
  because `MALEncodingContext` already exposes the operation fields; the design
  reserves the hook but does not build it.

The split the user suggested (static vs smart) therefore lives at the **strategy
layer**, leaving the simplified stream interfaces untouched.

### Compression

Compression is modelled as **two layers that exploit different redundancy**, so
they compose instead of cancelling out. The governing rule: **only the channel
layer does the final entropy coding — stacking two generic compressors is
pointless, because the first one's output is already high-entropy.**

* **Message encoding (intra-message, end-to-end, parallel).** A structural
  transform applied per message using what MO knows about it — a shared
  compression dictionary, or a schema-aware delta against a reference (the
  near-identical periodic PUB-SUB parameter publishes are the motivating case).
  It *exposes/reduces* redundancy rather than entropy-coding, stays
  self-contained per message, and runs STATELESS or SHARED-READ-ONLY (parallel).
  Being self-contained, it survives brokers (which decode/re-encode) and message
  reordering.
* **Channel compression (inter-message, hop-by-hop, serial).** A generic stream
  compressor (deflate/zstd) over the transport's ordered byte stream, with a
  per-message sync-flush so the sliding window carries across messages — this is
  where the bulk gain from repeated messages comes from. It is CHANNEL-SERIAL
  and requires an **ordered + reliable** stream, provided either by the transport
  (TCP) or by the MAL ordering + guaranteed-delivery stages layered on an
  otherwise unreliable bearer (SPP). A transport that compresses its own stream
  advertises the capability, and the MAL adds nothing.

This mirrors established practice: **intra-frame vs inter-frame** video coding
(I-frames vs P/B-frames, with keyframes as resync points), Parquet/ORC
**encodings vs compression codec**, and HTTP **Content-Encoding (end-to-end) vs
Transfer-Encoding (hop-by-hop)**.

Interactions and constraints:

* **One entropy coder, last.** Message encoding must stay a transform; if it
  entropy-codes (produces high-entropy bytes), channel compression gains nothing.
* **Channel compression is off when encryption is on.** Stream compression mixed
  with encryption is the CRIME/BREACH attack class (why TLS dropped
  compression). With encryption enabled, rely on message encoding plus, if
  needed, per-message compression under the cipher.
* **Lossy / one-way links.** Streaming (channel) compression tolerates no gaps;
  on one-way or very lossy SPP links prefer message encoding (self-contained), or
  periodic full-flush resets that bound loss propagation.

### Observation (sniffing and auditing)

A single **tap/observer** interface can be attached between any two steps of the
chain. It sees the decoded MAL message and the encoded bytes at that point:

* **Downward:** print the MAL message, then the encoded MAL message.
* **Upward:** the reverse.

Sniffing and auditing share this one insertion point and interface; they differ
only in retention and guarantees. **Logging/sniffing** ships first as the
initial implementation (useful for debugging, including by Claude Code).
**Auditing** — persistent, structured, possibly tamper-evident — becomes a
second implementation of the same interface later, with no interface churn.

Taps are controlled by clean properties (no legacy `.gen.` prefix, since the
"generic" project is deleted), e.g.
`org.ccsds.moims.mo.mal.transport.sniff=true`. Exact property names are defined
during implementation.

Forward-looking hooks (enabled, not built)
------------------------------------------

* **Smart encodings** — the strategy layer above the encoding SPI (above).
* **Live audio/video** — treated as **continuous media**, distinct from
  discrete request/response messages and from large-file transfer. The adaptive
  behaviour (reduce quality on a poor link) may be driven only by delivery acks;
  whether a media stage needs link statistics from the bearer is an open
  question. The design reserves a streaming/continuous channel concept so this
  can be added.
* **Large-file transfer** — distinct from live streaming; segmented and given
  **lower priority**. Builds on the existing segmentation pattern.
* **DTN** — a future DTN transport/convergence layer must be able to slot in as
  a bearer with store-and-forward and custody semantics. Priority and
  guaranteed-delivery stages are designed so DTN can reuse or defer to them.

Module layout
-------------

* **api-mal** — the new transport and stage **interfaces** (bearer, stage,
  capability descriptor, encoding-selection strategy, tap/observer).
* **mal-impl** — the **framework implementation** (chain builder, channel
  management, addressing, the default stages, the thread pool and ordered
  egress).
* **transport-\*** — the concrete transports stay as **separate modules**, now
  implementing the small interfaces and composed into the chain, depending on
  api-mal/mal-impl instead of the deleted `transport-generic`.
* **`transport-generic`** — deleted.

Open questions
--------------

All remaining items are **[detail]** — later tuning or per-mission choices that
do not change the architecture. (The previously listed blocking item, capability
negotiation, and the priority levels are now decided — see the Capability model,
Priority and Future ideas sections.)

1. **[detail] Feature granularity.** Decided: per service for now. Per operation
   is a later extension that does not change the core SPI.
2. **[detail] Connection sharing.** When two services with different feature
   profiles share one transport/connection: one multiplexed connection or
   separate channels per profile? Start with separate channels; add multiplexing
   later.
3. **[detail] Audio/video mechanics.** Whether a media stage needs link
   statistics from the bearer, or delivery acks suffice. Forward-looking; not in
   the core refactor.
4. **[detail] Compression defaults per bearer.** Channel (streaming) compression
   on ordered reliable bearers (TCP, or SPP + reliability) vs message encoding
   (self-contained) on one-way/lossy bearers — and whether both layers run
   together on a given link.
5. **[detail] Dictionary management.** How a message-encoding dictionary is
   trained, versioned and distributed to peers, and how the per-message
   dictionary id rides in the transport framing.
6. **[detail] Schema-aware delta.** Whether to build MO-field-level delta
   encoding for periodic publishes, beyond a generic dictionary.
7. **[detail] SPP link assumptions.** Whether target SPP links are bidirectional
   (acks possible → reliability + channel compression) or one-way/lossy (→
   self-contained message encoding). Needs input.

Future ideas
------------

Parked deliberately; not part of this redesign.

* **Capability negotiation.** Instead of static per-transport capability sets, a
  connect-time handshake in which peers negotiate the capability set per
  connection (e.g. plain TCP vs TLS-over-TCP, or a link that has degraded and
  dropped a guarantee). Decided for now: capabilities are **static**. Because
  capability resolution already happens at chain-build time, negotiation can be
  added later as an additive step, without reworking the stage SPI.

Appendix: survey findings that ground this design
-------------------------------------------------

* `QoSLevel` is carried but never enforced — only `isSupportedQoSLevel()` exists.
* `Supplements` is an existing per-message `NamedValueList` on the header, used
  already in mal-impl — a valid home for priority with no wire change.
* SPP already segments (`SPPSegmentsAssembler`, sequence counters) but never
  retransmits; guaranteed delivery is a genuine gap for any SPP bearer.
* NMF wires transports by global properties (TCP + RMI; SPP kept out precisely
  because of the reliability gap). There is no per-service transport hook today.
* A `transport.gen.debug` property already exists — a precedent for formal taps,
  to be renamed without `.gen.`.
