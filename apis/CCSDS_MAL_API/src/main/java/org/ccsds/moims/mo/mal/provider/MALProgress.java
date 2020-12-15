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
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.transport.MALEncodedBody;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * The MALProgress interface represents a PROGRESS interaction handling context.
 */
public interface MALProgress extends MALInvoke
{
  /**
   * The method sends an UPDATE message.
   *
   * @param body Message body to be transmitted to the consumer
   * @return the MALMessage that has been sent
   * @throws MALInteractionException if the interaction is in the incorrect state.
   * @throws MALException If an error occurs
   */
  MALMessage sendUpdate(Object... body)
          throws MALInteractionException, MALException;

  /**
   * The method sends an UPDATE message.
   *
   * @param body The already encoded message body to be transmitted to the consumer
   * @return the MALMessage that has been sent
   * @throws MALInteractionException if the interaction is in the incorrect state.
   * @throws MALException If an error occurs
   */
  MALMessage sendUpdate(MALEncodedBody body)
          throws MALInteractionException, MALException;

  /**
   * The method sends an UPDATE ERROR message.
   *
   * @param error Error to be transmitted to the consumer
   * @return the MALMessage that has been sent
   * @throws java.lang.IllegalArgumentException If the argument is NULL
   * @throws MALInteractionException if the interaction is in the incorrect state.
   * @throws MALException If an error occurs
   */
  MALMessage sendUpdateError(MALStandardError error)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException;
}
