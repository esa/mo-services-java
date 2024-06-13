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
package org.ccsds.moims.mo.mal.accesscontrol;

import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * The interface used by a MAL implementation to implement access control
 * policy.
 */
public interface MALAccessControl {

    /**
     * The method is invoked by a MAL implementation to intercept and check:
     * outgoing messages before they are transmitted to the transport layer;
     * incoming messages before they are delivered to the MAL client.
     *
     * It may modify the message and therefore returns the message to use.
     *
     * @param msg The MAL message to check
     * @return The MAL message to use from this point onwards.
     * @throws IllegalArgumentException if the argument is null.
     * @throws MALCheckErrorException If there is an access control violation.
     */
    MALMessage check(MALMessage msg) throws IllegalArgumentException, MALCheckErrorException;
}
