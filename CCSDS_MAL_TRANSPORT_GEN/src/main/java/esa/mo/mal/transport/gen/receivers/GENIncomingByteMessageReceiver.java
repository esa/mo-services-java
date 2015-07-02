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
package esa.mo.mal.transport.gen.receivers;

import esa.mo.mal.transport.gen.GENMessage;
import esa.mo.mal.transport.gen.GENReceptionHandler;
import esa.mo.mal.transport.gen.GENTransport;
import esa.mo.mal.transport.gen.GENTransport.GENIncomingMessageReceiverBase;
import esa.mo.mal.transport.gen.GENTransport.PacketToString;
import org.ccsds.moims.mo.mal.MALException;

/**
 * Extension of the GENIncomingMessageReceiverBase class for newly arrived MAL Messages in byte array format.
 */
public final class GENIncomingByteMessageReceiver extends GENIncomingMessageReceiverBase
{
  private final byte[] rawMessage;

  /**
   * Constructor
   *
   * @param transport Containing transport.
   * @param rawMessage The raw message
   * @param receptionHandler The reception handler to pass them to.
   */
  public GENIncomingByteMessageReceiver(final GENTransport transport, byte[] rawMessage, GENReceptionHandler receptionHandler)
  {
    super(transport, receptionHandler);
    this.rawMessage = rawMessage;
  }

  @Override
  protected GENIncomingMessageHolder decodeAndCreateMessage() throws MALException
  {
    GENTransport.PacketToString smsg = transport.new PacketToString(rawMessage);
    GENMessage malMsg = transport.createMessage(rawMessage);
    return new GENIncomingMessageHolder(malMsg.getHeader().getTransactionId(), malMsg, smsg);
  }
}
