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
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 * One byte pipe to one peer: the bottom of the chain. A {@link LinkDriver}
 * creates a link for an outgoing peer, or hands one to its {@link InboundSink}
 * for an incoming peer. Stages above the link exchange {@link Exchange}s; the
 * link itself only moves the finished bytes.
 *
 * <p>
 * The ordering and reliability a link offers are described by the driver's
 * {@link LinkDriver#capabilities()}, not implied by this interface, so the same
 * interface serves an ordered reliable stream and a one-way lossy datagram link.
 */
public interface Link {

    /**
     * Sends one encoded / framed message to the peer.
     *
     * @param frame The bytes to send. Must not be null.
     * @throws MALException If the send fails.
     */
    void send(ByteBuffer frame) throws MALException;

    /**
     * @return The URI of the peer this link is connected to. Never null.
     */
    URI remote();

    /**
     * Closes this link, releasing its resources. Idempotent.
     */
    void close();
}
