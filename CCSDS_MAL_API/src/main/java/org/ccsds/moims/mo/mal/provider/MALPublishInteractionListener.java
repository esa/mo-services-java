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

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * The MALPublishInteractionListener interface allows the reception of the publish interaction results.
 */
public interface MALPublishInteractionListener
{
  /**
   * The method receives the PUBLISH REGISTER ACK message.
   *
   * @param header Header of the message
   * @param qosProperties QoS properties of the message
   * @throws MALException If an error occurs then a MALException may be raised.
   */
  void publishRegisterAckReceived(MALMessageHeader header, Map qosProperties)
          throws MALException;

  /**
   * The method receives the PUBLISH REGISTER ERROR message.
   *
   * @param header Header of the message
   * @param body Body of the message
   * @param qosProperties QoS properties of the message
   * @throws MALException If an error occurs then a MALException may be raised.
   */
  void publishRegisterErrorReceived(MALMessageHeader header, MALErrorBody body, Map qosProperties)
          throws MALException;

  /**
   * The method receives the PUBLISH ERROR message.
   *
   * @param header Header of the message
   * @param body Body of the message
   * @param qosProperties QoS properties of the message
   * @throws MALException If an error occurs then a MALException may be raised.
   */
  void publishErrorReceived(MALMessageHeader header, MALErrorBody body, Map qosProperties)
          throws MALException;

  /**
   * The method receives the PUBLISH DEREGISTER ACK message.
   *
   * @param header Header of the message
   * @param qosProperties QoS properties of the message
   * @throws MALException If an error occurs then a MALException may be raised.
   */
  void publishDeregisterAckReceived(MALMessageHeader header, Map qosProperties)
          throws MALException;
}
