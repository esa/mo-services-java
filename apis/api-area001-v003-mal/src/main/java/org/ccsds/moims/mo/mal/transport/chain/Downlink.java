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
 * way <em>down</em> (towards the {@link Link}). Giving the stage an explicit
 * forward handle, rather than a plain return value, lets it short-circuit, drop,
 * or emit several exchanges (for example segmentation or media splitting).
 */
public interface Downlink {

    /**
     * Forwards the exchange to the next stage down the chain.
     *
     * @param exchange The exchange to forward. Must not be null.
     * @throws MALException If forwarding fails.
     */
    void forward(Exchange exchange) throws MALException;
}
