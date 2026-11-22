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

import java.util.ArrayList;
import java.util.List;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.transport.chain.Capability;
import org.ccsds.moims.mo.mal.transport.chain.CapabilitySet;
import org.ccsds.moims.mo.mal.transport.chain.Exchange;
import org.ccsds.moims.mo.mal.transport.chain.Uplink;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 * End-to-end tests for {@link ChainBuilder} / {@link Chain} over the in-process
 * {@link LoopbackLinkDriver}.
 */
public class ChainRoundTripTest {

    /**
     * A message sent down the chain is encoded, echoed by the loopback link, and
     * decoded back on the way up to the MAL delivery point, unchanged.
     */
    @Test
    public void messageRoundTripsThroughTheChain() throws MALException {
        final List<Object> delivered = new ArrayList<Object>();
        final Uplink mal = new Uplink() {
            @Override
            public void deliver(final Exchange exchange) throws MALException {
                delivered.add(exchange.body());
            }
        };

        final Chain chain = ChainBuilder.over(new LoopbackLinkDriver())
                .stage(new StringCodecStage())
                .deliveringTo(mal)
                .build();

        chain.send(chain.outbound(null, "hello transport chain"));

        assertEquals(1, delivered.size());
        assertEquals("hello transport chain", delivered.get(0));
    }

    /**
     * The build fails when a stage requires a capability the link does not
     * provide.
     */
    @Test
    public void buildRejectsMissingCapability() {
        final LoopbackLinkDriver framedOnly = new LoopbackLinkDriver(Capability.FRAMED);
        final Uplink mal = new Uplink() {
            @Override
            public void deliver(final Exchange exchange) {
            }
        };
        try {
            ChainBuilder.over(framedOnly)
                    .stage(new StringCodecStage(CapabilitySet.of(Capability.STREAMING)))
                    .deliveringTo(mal)
                    .build();
            fail("Expected a MALException for the missing STREAMING capability");
        } catch (MALException expected) {
            assertTrue(expected.getMessage().contains("STREAMING"));
        }
    }

    /**
     * An empty chain (no stages) still delivers inbound frames to the MAL, so
     * the builder and link wiring work without any stage in between.
     */
    @Test
    public void emptyChainDeliversRawFrame() throws MALException {
        final List<Exchange> delivered = new ArrayList<Exchange>();
        final Uplink mal = new Uplink() {
            @Override
            public void deliver(final Exchange exchange) throws MALException {
                delivered.add(exchange);
            }
        };

        final Chain chain = ChainBuilder.over(new LoopbackLinkDriver())
                .deliveringTo(mal)
                .build();

        final Exchange out = chain.outbound(null, null);
        out.setBytes(java.nio.ByteBuffer.wrap(new byte[]{1, 2, 3}));
        chain.send(out);

        assertEquals(1, delivered.size());
        assertEquals(3, delivered.get(0).bytes().remaining());
    }
}
