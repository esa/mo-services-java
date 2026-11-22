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

/**
 * A property a {@link LinkDriver} may provide and a {@link Stage} may require or
 * add. The chain builder resolves these at build time: a stage that
 * {@link Stage#requires() requires} a capability the link does not provide is a
 * configuration error, and a MAL stage that would duplicate a capability the
 * link already provides is omitted.
 */
public enum Capability {

    /**
     * Messages are delivered in the order they were sent.
     */
    ORDERED,

    /**
     * Delivery is guaranteed (retransmission on loss); the peer either receives
     * the message or an error is raised to the sender.
     */
    RELIABLE,

    /**
     * The link carries a continuous byte stream rather than discrete datagrams,
     * so stream-oriented stages (e.g. streaming compression) are possible.
     */
    STREAMING,

    /**
     * The link preserves message boundaries (framing); a receiver gets whole
     * messages, not a byte stream it must re-delimit.
     */
    FRAMED,

    /**
     * The link is bidirectional, so the peer can send back (acknowledgements,
     * flow control) on the same link.
     */
    DUPLEX
}
