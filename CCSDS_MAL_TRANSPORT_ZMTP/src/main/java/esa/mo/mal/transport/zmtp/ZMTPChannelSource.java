/* ----------------------------------------------------------------------------
 * Copyright (C) 2017      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO ZMTP Transport Framework
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
package esa.mo.mal.transport.zmtp;

import esa.mo.mal.transport.gen.sending.GENMessageSender;
import esa.mo.mal.transport.gen.sending.GENOutgoingMessageHolder;
import java.io.IOException;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

/**
 * This class implements the low level data (MAL Message) transport protocol. In order to
 * differentiate messages with each other, the protocol has a very simple format: |size|message|
 *
 * If the protocol uses a different message encoding this class can be replaced in the
 * ZMTPTransport.
 *
 */
public class ZMTPChannelSource implements GENMessageSender<byte[]>
{

  /**
   * Reference to a ZMQ socket produced by the OPEN primitive
   */
  protected final ZMQ.Socket socket;

  /**
   * Constructor.
   *
   * @param socket The ZMTP socket.
   */
  public ZMTPChannelSource(ZMQ.Socket socket)
  {
    this.socket = socket;
  }

  @Override
  public void sendEncodedMessage(GENOutgoingMessageHolder<byte[]> packetData) throws IOException
  {
    ZMsg outMsg = new ZMsg();
    outMsg.add(packetData.getEncodedMessage());
    outMsg.send(socket, true);
  }

  @Override
  public void close()
  {
  }
}
