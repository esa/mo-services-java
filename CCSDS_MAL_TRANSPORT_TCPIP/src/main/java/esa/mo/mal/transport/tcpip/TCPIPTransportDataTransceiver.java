/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO TCP/IP Transport Framework
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
package esa.mo.mal.transport.tcpip;

import esa.mo.mal.transport.gen.receiving.GENDataReceiver;
import esa.mo.mal.transport.gen.sending.GENDataTransmitter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * This class implements the low level data (MAL Message) transport protocol. In order to differentiate messages with
 * each other, the protocol has a very simple format: |size|message|
 *
 */
public class TCPIPTransportDataTransceiver implements GENDataReceiver, GENDataTransmitter
{
  private final Socket socket;

  private final DataOutputStream socketWriteIf;
  private final DataInputStream socketReadIf;

  public TCPIPTransportDataTransceiver(Socket socket) throws IOException
  {
    this.socket = socket;
    socketWriteIf = new DataOutputStream(socket.getOutputStream());
    socketReadIf = new DataInputStream(socket.getInputStream());
  }

  /**
   * Sends data to the client (MAL Message encoded as a byte array)
   *
   * @param packetData the MALMessage encoded as a byte array
   * @throws IOException in case the data cannot be send to the client
   */
  @Override
  public void sendPacket(byte[] packetData) throws IOException
  {
    // write packet length and then the packet
    socketWriteIf.writeInt(packetData.length);

    socketWriteIf.write(packetData);
    socketWriteIf.flush();
  }

  /**
   * Reads a MALMessage encoded as a byte array.
   *
   * @return the byte array containing the MAL Message
   * @throws IOException in case the data cannot be read
   */
  public byte[] readPacket() throws IOException
  {
    try
    {
      // read packet length and then the packet
      int packetSize = socketReadIf.readInt();
      byte[] data = new byte[packetSize];
      socketReadIf.readFully(data);
      return data;
    }
    catch (java.net.SocketException ex)
    {
      if (socket.isClosed())
      {
        // socket has been closed to throw EOF exception higher
        throw new java.io.EOFException();
      }

      throw ex;
    }
  }

  public void close()
  {
    try
    {
      socket.close();
    }
    catch (IOException e)
    {
      // ignore
    }
  }
}
