/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java API
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
package org.ccsds.moims.mo.mal.transport.chain;

import java.nio.ByteBuffer;

/**
 * The callback a {@link LinkDriver} uses to hand inbound bytes to the framework.
 * The framework supplies a sink to {@link LinkDriver#listen(InboundSink)}; the
 * driver calls it for each frame it receives, naming the {@link Link} the frame
 * arrived on so the framework can route the reply and locate the channel's
 * inbound chain.
 */
public interface InboundSink {

    /**
     * Called by the driver when a frame arrives from a peer.
     *
     * @param link The link the frame arrived on. Must not be null.
     * @param frame The received bytes. Must not be null.
     */
    void onFrame(Link link, ByteBuffer frame);
}
