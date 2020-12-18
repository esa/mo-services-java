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
package org.ccsds.moims.mo.mal.broker;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.transport.MALDeregisterBody;
import org.ccsds.moims.mo.mal.transport.MALPublishBody;
import org.ccsds.moims.mo.mal.transport.MALPublishRegisterBody;
import org.ccsds.moims.mo.mal.transport.MALRegisterBody;

/**
 * The MALBrokerHandler interface handles the interactions on the broker side. The interface handles the following
 * PUBLISH-SUBSCRIBE interaction stages: REGISTER, PUBLISH REGISTER, PUBLISH, DEREGISTER and PUBLISH DEREGISTER.
 *
 */
public interface MALBrokerHandler
{
  /**
   * The method enables a MALBrokerHandler to be initialized when the broker is activated. It’ enables the handler to
   * store the reference of the MALBrokerBinding in order to send NOTIFY, NOTIFY ERROR and PUBLISH ERROR messages.
   *
   * May be called more than once and for several bindings.
   *
   * @param brokerBinding The broker binding.
   */
  void malInitialize(MALBrokerBinding brokerBinding);

  /**
   * The method is called by an implementation to handle the REGISTER stage of a PUBLISH-SUBSCRIBE interaction.
   *
   * @param interaction Interaction context
   * @param body Body of the REGISTER message to handle
   * @throws MALInteractionException If a MAL standard error occurs
   * @throws MALException If a non-MAL error occurs
   */
  void handleRegister(MALInteraction interaction, MALRegisterBody body)
          throws MALInteractionException, MALException;

  /**
   * The method is called by an implementation to handle the PUBLISH REGISTER stage of a PUBLISH-SUBSCRIBE interaction.
   *
   * @param interaction Interaction context
   * @param body Body of the PUBLISH REGISTER message to handle
   * @throws MALInteractionException If a MAL standard error occurs
   * @throws MALException If a non-MAL error occurs
   */
  void handlePublishRegister(MALInteraction interaction, MALPublishRegisterBody body)
          throws MALInteractionException, MALException;

  /**
   * The method is called by an implementation to handle the PUBLISH stage of a PUBLISH-SUBSCRIBE interaction.
   *
   * @param interaction Interaction context
   * @param body Body of the PUBLISH message to handle
   * @throws MALInteractionException If a MAL standard error occurs
   * @throws MALException If a non-MAL error occurs
   */
  void handlePublish(MALInteraction interaction, MALPublishBody body)
          throws MALInteractionException, MALException;

  /**
   * The method is called by an implementation to handle the DEREGISTER stage of a PUBLISH-SUBSCRIBE interaction.
   *
   * @param interaction Interaction context
   * @param body Body of the DEREGISTER message to handle
   * @throws MALInteractionException If a MAL standard error occurs
   * @throws MALException If a non-MAL error occurs
   */
  void handleDeregister(MALInteraction interaction, MALDeregisterBody body)
          throws MALInteractionException, MALException;

  /**
   * The method is called by an implementation to handle the PUBLISH DEREGISTER stage of a PUBLISH-SUBSCRIBE
   * interaction.
   *
   * @param interaction Interaction context
   * @throws MALInteractionException If a MAL standard error occurs
   * @throws MALException If a non-MAL error occurs
   */
  void handlePublishDeregister(MALInteraction interaction)
          throws MALInteractionException, MALException;

  /**
   * the method enables a MALBrokerHandler to be notified when a MALBrokerBinding is closed.
   *
   * @param brokerBinding The broker binding being closed.
   */
  void malFinalize(MALBrokerBinding brokerBinding);
}
