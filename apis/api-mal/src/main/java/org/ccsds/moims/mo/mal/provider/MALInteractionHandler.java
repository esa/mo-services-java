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
import org.ccsds.moims.mo.mal.transport.MALMessageBody;

/**
 * The MALInteractionHandler interface handles the interactions on the provider side. The interface does not handle the
 * PUBLISH-SUBSCRIBE REGISTER, PUBLISH REGISTER, PUBLISH, DEREGISTER and PUBLISH DEREGISTER messages as they are handled
 * by a broker. A MALInteractionHandler shall be passed as a parameter of the method ‘createProvider’ when creating a
 * MALProvider. Several MALProviders may use the same instance of MALInteractionHandler
 *
 */
public interface MALInteractionHandler
{
  /**
   * The method enables a MALInteractionHandler to be initialised when the provider is activated.
   *
   * @param provider Created MALProvider
   * @throws MALException if an error occurs
   */
  void malInitialize(MALProvider provider) throws MALException;

  /**
   * The method handles a SEND interaction.
   *
   * @param interaction The interaction context.
   * @param body The message body.
   * @throws MALInteractionException If an interaction exception needs to be raised.
   * @throws MALException If an error occurs.
   */
  void handleSend(MALInteraction interaction,
          MALMessageBody body) throws MALInteractionException, MALException;

  /**
   * The method handles a SUBMIT interaction.
   *
   * @param interaction The interaction context.
   * @param body The message body.
   * @throws MALInteractionException If an interaction exception needs to be raised.
   * @throws MALException If an error occurs.
   */
  void handleSubmit(MALSubmit interaction,
          MALMessageBody body) throws MALInteractionException, MALException;

  /**
   * The method handles a REQUEST interaction.
   *
   * @param interaction The interaction context.
   * @param body The message body.
   * @throws MALInteractionException If an interaction exception needs to be raised.
   * @throws MALException If an error occurs.
   */
  void handleRequest(MALRequest interaction,
          MALMessageBody body) throws MALInteractionException, MALException;

  /**
   * The method handles a INVOKE interaction.
   *
   * @param interaction The interaction context.
   * @param body The message body.
   * @throws MALInteractionException If an interaction exception needs to be raised.
   * @throws MALException If an error occurs.
   */
  void handleInvoke(MALInvoke interaction,
          MALMessageBody body) throws MALInteractionException, MALException;

  /**
   * The method handles a PROGRESS interaction.
   *
   * @param interaction The interaction context.
   * @param body The message body.
   * @throws MALInteractionException If an interaction exception needs to be raised.
   * @throws MALException If an error occurs.
   */
  void handleProgress(MALProgress interaction,
          MALMessageBody body) throws MALInteractionException, MALException;

  /**
   * The method enables a MALInteractionHandler to be notified when a MALProvider is closed.
   *
   * @param provider The provider being closed.
   * @throws MALException if an error occurs.
   */
  void malFinalize(MALProvider provider) throws MALException;
}
