/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
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
package esa.mo.mal.transport.gen.util;

import esa.mo.mal.transport.gen.GENReceptionHandler;
import esa.mo.mal.transport.gen.receiving.GENDataReceiver;
import esa.mo.mal.transport.gen.sending.GENDataTransmitter;
import esa.mo.mal.transport.gen.GENTransport;
import java.io.IOException;
import java.util.logging.Level;

import static esa.mo.mal.transport.gen.GENTransport.LOGGER;

/**
 * This utility class creates a thread to pull packets from a transceiver. It receives data from it (MAL packets) and
 * then forwards the incoming packet to an asynchronous processor in order to return immediately and not hold the
 * calling thread while the packet is processed.
 *
 * In case of a communication problem it informs the transport and/or closes the resource (socket)
 *
 * Only transport adapter that pull messages from their transport layer will need to use this class.
 */
public class GENDataPoller extends Thread implements GENReceptionHandler
{
  //reference to the transport
  private final GENTransport transport;
  //the low level data transmitter
  private final GENDataTransmitter dataTransmitter;
  //the low level data receiver
  private final GENDataReceiver dataReceiver;
  //the remote URI (client) this socket is associated to. This is volatile as it is potentially set by a different thread after its creation
  private volatile String remoteURI = null;

  public GENDataPoller(GENTransport transport, GENDataTransmitter dataTransmitter, GENDataReceiver dataReceiver)
  {
    this.transport = transport;
    this.dataTransmitter = dataTransmitter;
    this.dataReceiver = dataReceiver;
    setName(getClass().getName());
  }

  @Override
  public void run()
  {
    // handles data reads from this client
    while (!interrupted())
    {
      try
      {
        byte[] malMsgData = dataReceiver.readPacket();

        transport.receive(malMsgData, this);
      }
      catch (java.io.EOFException ex)
      {
        LOGGER.log(Level.INFO, "Client closing connection: {0}", remoteURI);

        transport.closeConnection(remoteURI, this);
        close();

        //and terminate
        break;
      }
      catch (IOException e)
      {
        LOGGER.log(Level.WARNING, "Cannot read data from client", e);

        transport.communicationError(remoteURI, this);
        close();

        //and terminate
        break;
      }
    }
  }

  @Override
  public String getRemoteURI()
  {
    return remoteURI;
  }

  @Override
  public void setRemoteURI(String remoteURI)
  {
    this.remoteURI = remoteURI;
    setName(getClass().getName() + " URI:" + remoteURI);
  }

  @Override
  public GENDataTransmitter getTransportTransmitter()
  {
    return dataTransmitter;
  }

  @Override
  public void close()
  {
    dataTransmitter.close();
    dataReceiver.close();
  }
}
