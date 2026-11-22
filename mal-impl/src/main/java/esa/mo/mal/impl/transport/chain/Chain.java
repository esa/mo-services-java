/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ----------------------------------------------------------------------------
 */
package esa.mo.mal.impl.transport.chain;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.chain.CapabilitySet;
import org.ccsds.moims.mo.mal.transport.chain.Downlink;
import org.ccsds.moims.mo.mal.transport.chain.Exchange;
import org.ccsds.moims.mo.mal.transport.chain.Link;
import org.ccsds.moims.mo.mal.transport.chain.Stage;
import org.ccsds.moims.mo.mal.transport.chain.Uplink;

/**
 * An assembled processing chain: an ordered list of {@link Stage}s above a
 * {@link Link}, built by {@link ChainBuilder}. A message sent flows
 * <em>down</em> stage 0 (the top, MAL-facing) to the last stage and out through
 * the link; a frame received flows <em>up</em> the stages to the MAL delivery
 * point.
 *
 * <p>
 * This implementation runs the chain synchronously on the calling thread. The
 * multi-threaded scheduler and sequence-gated ordered egress are a later step
 * and will slot in here without changing the {@link Stage} contract.
 */
public final class Chain {

    private final Link link;
    private final CapabilitySet linkCapabilities;
    private final Uplink malDelivery;

    // Precomputed forward handles. down[0] is the send entry; down[n] writes to
    // the link. upEntry runs the bottom stage first and ends at malDelivery.
    private final Downlink[] down;
    private final Uplink upEntry;

    Chain(final List<Stage> stages, final Link link, final CapabilitySet linkCapabilities,
            final Uplink malDelivery) {
        this.link = link;
        this.linkCapabilities = linkCapabilities;
        this.malDelivery = malDelivery;

        final List<Stage> ordered = new ArrayList<Stage>(stages);
        final int n = ordered.size();

        this.down = new Downlink[n + 1];
        this.down[n] = new Downlink() {
            @Override
            public void forward(final Exchange exchange) throws MALException {
                link.send(exchange.bytes());
            }
        };
        for (int i = n - 1; i >= 0; i--) {
            final Stage stage = ordered.get(i);
            final Downlink next = this.down[i + 1];
            this.down[i] = new Downlink() {
                @Override
                public void forward(final Exchange exchange) throws MALException {
                    stage.down(exchange, next);
                }
            };
        }

        Uplink above = malDelivery;
        for (int i = 0; i < n; i++) {
            final Stage stage = ordered.get(i);
            final Uplink continuation = above;
            above = new Uplink() {
                @Override
                public void deliver(final Exchange exchange) throws MALException {
                    stage.up(exchange, continuation);
                }
            };
        }
        this.upEntry = above;
    }

    /**
     * Mints an outbound exchange stamped with this chain's link capabilities.
     *
     * @param header The MAL message header. May be null in test contexts.
     * @param body The decoded body to send.
     * @return A new exchange ready to be passed to {@link #send(Exchange)}.
     */
    public Exchange outbound(final MALMessageHeader header, final Object body) {
        return new ExchangeImpl(header, linkCapabilities, null, null, null, body, null);
    }

    /**
     * Sends an exchange down the chain and out through the link.
     *
     * @param exchange The exchange to send. Must not be null.
     * @throws MALException If a stage or the link fails.
     */
    public void send(final Exchange exchange) throws MALException {
        down[0].forward(exchange);
    }

    /**
     * Entry point for a frame received on the link: mints an inbound exchange
     * and runs it up the chain to the MAL delivery point.
     *
     * @param frame The received bytes. Must not be null.
     * @throws MALException If a stage or the delivery fails.
     */
    public void inbound(final ByteBuffer frame) throws MALException {
        final Exchange exchange = new ExchangeImpl(null, linkCapabilities, null, null, null, null, frame);
        upEntry.deliver(exchange);
    }
}
