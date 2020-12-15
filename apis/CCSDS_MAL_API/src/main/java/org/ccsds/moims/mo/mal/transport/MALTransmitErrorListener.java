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
package org.ccsds.moims.mo.mal.transport;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALStandardError;

/**
 * The MALTransmitErrorListener interface enables the MAL layer to be notified by the transport module when an
 * asynchronous internal error has been raised by the transport layer when it has failed to transmit a message.
 */
public interface MALTransmitErrorListener
{
  /**
   * The method receives a transmission error.
   *
   * @param callingEndpoint MALEndpoint calling the MALMessageListener
   * @param srcMessageHeader The message header of the message being sent.
   * @param err Error to be received by the listener
   * @param qosMap The QoS details of the message being sent.
   */
  void onTransmitError(MALEndpoint callingEndpoint, MALMessageHeader srcMessageHeader, MALStandardError err, Map qosMap);
}
