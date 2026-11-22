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

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 * The per-protocol provider at the bottom of the chain: it opens {@link Link}s
 * to peers and declares what the protocol can do. This is the interface a
 * transport author implements for a new protocol (TCP/IP, RMI, SPP, ...).
 *
 * <p>
 * The capabilities are <em>static per transport type</em>: a driver reports a
 * fixed set, resolved once at chain-build time, which the framework uses to
 * decide which MAL stages to insert (for example a reliability stage over an
 * unreliable link) or omit (not re-ordering over an already ordered link).
 * Per-connection negotiation is a possible future extension and is not part of
 * this interface.
 */
public interface LinkDriver {

    /**
     * @return The protocol identifier this driver handles, e.g. {@code "tcpip"},
     * {@code "rmi"} or {@code "malspp"}. Never null.
     */
    String protocol();

    /**
     * @return The capabilities this protocol provides. Static for the transport
     * type. Never null.
     */
    CapabilitySet capabilities();

    /**
     * Opens a link to a remote peer for outgoing traffic.
     *
     * @param remote The peer URI. Must not be null.
     * @param properties Transport-specific configuration. Must not be null; may
     * be empty.
     * @return The opened link. Never null.
     * @throws MALException If the link cannot be opened.
     */
    Link connect(URI remote, Map<String, ?> properties) throws MALException;

    /**
     * Begins accepting incoming peers, delivering each received frame to the
     * given sink.
     *
     * @param sink The callback for inbound frames. Must not be null.
     * @throws MALException If the driver cannot start listening.
     */
    void listen(InboundSink sink) throws MALException;

    /**
     * Closes the driver and all links it opened. Idempotent.
     *
     * @throws MALException If an error occurs while closing.
     */
    void close() throws MALException;
}
