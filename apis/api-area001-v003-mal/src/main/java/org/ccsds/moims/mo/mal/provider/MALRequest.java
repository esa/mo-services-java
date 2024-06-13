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
package org.ccsds.moims.mo.mal.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.transport.MALEncodedBody;

/**
 * The MALRequest interface represents a REQUEST interaction handling context.
 */
public interface MALRequest extends MALInteraction {

    /**
     * The method sends a RESPONSE message.
     *
     * @param body Message body to be transmitted to the consumer
     * @return the MALMessage that has been sent
     * @throws MALInteractionException if the interaction is in the incorrect
     * state.
     * @throws MALException If an error occurs
     */
    org.ccsds.moims.mo.mal.transport.MALMessage sendResponse(Object... body)
            throws MALInteractionException, MALException;

    /**
     * The method sends a RESPONSE message.
     *
     * @param body The already encoded message body to be transmitted to the
     * consumer
     * @return the MALMessage that has been sent
     * @throws MALInteractionException if the interaction is in the incorrect
     * state.
     * @throws MALException If an error occurs
     */
    org.ccsds.moims.mo.mal.transport.MALMessage sendResponse(MALEncodedBody body)
            throws MALInteractionException, MALException;

    /**
     * The method sends an RESPONSE ERROR message.
     *
     * @param error Error to be transmitted to the consumer
     * @return the MALMessage that has been sent
     * @throws java.lang.IllegalArgumentException If the argument is NULL
     * @throws MALInteractionException if the interaction is in the incorrect
     * state.
     * @throws MALException If an error occurs
     */
    org.ccsds.moims.mo.mal.transport.MALMessage sendError(MOErrorException error)
            throws IllegalArgumentException, MALInteractionException, MALException;
}
