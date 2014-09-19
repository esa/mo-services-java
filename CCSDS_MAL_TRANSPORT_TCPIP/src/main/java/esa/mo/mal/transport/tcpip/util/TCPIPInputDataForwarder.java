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
package esa.mo.mal.transport.tcpip.util;

import esa.mo.mal.transport.tcpip.TCPIPTransport;

/**
 * This Runnable task is responsible of forwarding newly arrived MAL Messages (in raw format) to the transport.
 *
 */
public class TCPIPInputDataForwarder implements Runnable
{

  private final TCPIPTransport transport;
  private final byte[] rawMessage;
  private final TCPIPConnectionDataReceiver receptionHandler;

  public TCPIPInputDataForwarder(TCPIPTransport transport, byte[] rawMessage, TCPIPConnectionDataReceiver receptionHandler)
  {
    this.transport = transport;
    this.rawMessage = rawMessage;
    this.receptionHandler = receptionHandler;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Runnable#run()
   */
  @Override
  public void run()
  {
    transport.receive(rawMessage, receptionHandler);
  }
}
