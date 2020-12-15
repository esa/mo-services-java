/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
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
package esa.mo.mal.impl.patterns;

import esa.mo.mal.impl.Address;
import esa.mo.mal.impl.MessageSend;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * PubSub interaction class.
 */
public class PubSubInteractionImpl extends BaseInteractionImpl
{
  /**
   * Constructor.
   *
   * @param sender Used to return the messages.
   * @param address Details of this endpoint.
   * @param msg The source message.
   * @throws MALInteractionException if the received message operation is unknown.
   */
  public PubSubInteractionImpl(final MessageSend sender,
          final Address address,
          final MALMessage msg) throws MALInteractionException
  {
    super(sender, address, msg);
  }
}
