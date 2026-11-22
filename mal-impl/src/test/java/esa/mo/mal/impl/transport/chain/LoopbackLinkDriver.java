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
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.chain.Capability;
import org.ccsds.moims.mo.mal.transport.chain.CapabilitySet;
import org.ccsds.moims.mo.mal.transport.chain.InboundSink;
import org.ccsds.moims.mo.mal.transport.chain.Link;
import org.ccsds.moims.mo.mal.transport.chain.LinkDriver;

/**
 * An in-process {@link LinkDriver} test double: whatever a {@link Link} sends is
 * echoed straight back to the registered {@link InboundSink} as a received
 * frame. It exercises both directions of a chain without any real I/O.
 *
 * <p>
 * The advertised capabilities are configurable so capability resolution can be
 * tested; the default set is {@code ORDERED, RELIABLE, FRAMED, DUPLEX} (notably
 * not {@code STREAMING}).
 */
final class LoopbackLinkDriver implements LinkDriver {

    private final CapabilitySet capabilities;
    private volatile InboundSink sink;

    LoopbackLinkDriver(final Capability... capabilities) {
        this.capabilities = (capabilities.length == 0)
                ? CapabilitySet.of(Capability.ORDERED, Capability.RELIABLE,
                        Capability.FRAMED, Capability.DUPLEX)
                : CapabilitySet.of(capabilities);
    }

    @Override
    public String protocol() {
        return "loopback";
    }

    @Override
    public CapabilitySet capabilities() {
        return capabilities;
    }

    @Override
    public Link connect(final URI remote, final Map<String, ?> properties) throws MALException {
        final URI peer = (remote != null) ? remote : new URI("loopback://local");
        return new LoopbackLink(peer);
    }

    @Override
    public void listen(final InboundSink sink) throws MALException {
        this.sink = sink;
    }

    @Override
    public void close() throws MALException {
        this.sink = null;
    }

    private void echo(final Link source, final ByteBuffer frame) {
        final InboundSink current = sink;
        if (current != null) {
            current.onFrame(source, frame);
        }
    }

    /**
     * A link that echoes everything it sends back to the driver's sink.
     */
    private final class LoopbackLink implements Link {

        private final URI remote;

        LoopbackLink(final URI remote) {
            this.remote = remote;
        }

        @Override
        public void send(final ByteBuffer frame) throws MALException {
            // Duplicate so the receiver's position/limit is independent of the
            // sender's buffer.
            echo(this, frame.duplicate());
        }

        @Override
        public URI remote() {
            return remote;
        }

        @Override
        public void close() {
        }
    }
}
