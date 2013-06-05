/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Generic Transport Framework
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
package esa.mo.mal.transport.gen;

import org.ccsds.moims.mo.mal.transport.MALTransmitErrorException;

/**
 *
 */
public interface GENSender
{
  /**
   * The main exit point for messages from this transport.
   *
   * @param ep The endpoint sending the message.
   * @param handle A context handle for multi send
   * @param lastForHandle True if that is the last message in a multi send for the handle
   * @param msg The message to send.
   * @throws MALTransmitErrorException On transmit error.
   */
  void sendMessage(final GENEndpoint ep, final Object handle, final boolean lastForHandle, final GENMessage msg)
          throws MALTransmitErrorException;
}
