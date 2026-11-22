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
 * The handle a {@link Stage} uses to pass an exchange to the next stage on the
 * way <em>up</em> (towards the MAL). As with {@link Downlink}, the explicit
 * handle lets a stage reassemble several inbound exchanges into one, drop one,
 * or deliver several.
 */
public interface Uplink {

    /**
     * Delivers the exchange to the next stage up the chain.
     *
     * @param exchange The exchange to deliver. Must not be null.
     * @throws MALException If delivery fails.
     */
    void deliver(Exchange exchange) throws MALException;
}
