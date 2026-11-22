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

import org.ccsds.moims.mo.mal.MALException;

/**
 * A composable transform in the chain. Each stage performs one concern on the
 * way <em>down</em> (send) and reverses it on the way <em>up</em> (receive); the
 * two directions are explicit methods so the code reads in both. A
 * one-directional stage leaves the other direction as a pass-through.
 *
 * <p>
 * The chain is an ordered list of stages above a {@link Link}. A stage receives
 * an {@link Exchange}, transforms it, and forwards it to the next stage via the
 * supplied {@link Downlink} / {@link Uplink} handle. Because forwarding is
 * explicit, a stage may short-circuit, drop, split or merge exchanges rather
 * than being limited to a one-in, one-out transform.
 *
 * <p>
 * A stage declares its {@link #concurrency() concurrency class} so the framework
 * can schedule the chain multi-threaded, and the capabilities it
 * {@link #requires() requires} from below and {@link #provides() provides}
 * above, which the chain builder resolves at build time.
 */
public interface Stage {

    /**
     * @return How this stage may be scheduled relative to other messages. Never
     * null.
     */
    ConcurrencyClass concurrency();

    /**
     * @return The capabilities this stage needs from the chain below it. The
     * builder rejects a chain whose link does not provide them. Never null; use
     * {@link CapabilitySet#EMPTY} if none.
     */
    CapabilitySet requires();

    /**
     * @return The capabilities this stage adds, visible to stages above it (for
     * example a reliability stage provides {@link Capability#RELIABLE}). Never
     * null; use {@link CapabilitySet#EMPTY} if none.
     */
    CapabilitySet provides();

    /**
     * Transforms an outbound exchange and forwards it towards the link.
     *
     * @param exchange The exchange moving down. Must not be null.
     * @param next The handle to the next stage down. Must not be null.
     * @throws MALException If the stage fails to process the exchange.
     */
    void down(Exchange exchange, Downlink next) throws MALException;

    /**
     * Transforms an inbound exchange and delivers it towards the MAL.
     *
     * @param exchange The exchange moving up. Must not be null.
     * @param next The handle to the next stage up. Must not be null.
     * @throws MALException If the stage fails to process the exchange.
     */
    void up(Exchange exchange, Uplink next) throws MALException;
}
