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
package esa.mo.mal.transport.gen.sending;

import esa.mo.mal.transport.gen.GENTransport;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;

import static esa.mo.mal.transport.gen.GENTransport.LOGGER;

/**
 * This thread will listen for incoming messages through a blocking queue and send them through a transceiver. In case of
 * communication problems it will inform the transport and terminate.
 *
 * In any case, a reply is send back to the originator of the request using a blocking queue from the input data.
 *
 */
public class GENSenderThread extends Thread
{
  // input queue to receive data to send
  private final BlockingQueue<GENOutgoingMessageHolder> inputQueue;

  // the destination URI
  private final String uriTo;

  // the data tranceiver
  private final GENMessageSender dataTransmitter;

  // the transport object. Needed in order to send information on connection / data problems
  private final GENTransport transport;

  /**
   * Constructor
   *
   * @param inputQueue
   * @param dataTransmitter
   * @param uriTo
   * @param transport
   */
  public GENSenderThread(BlockingQueue<GENOutgoingMessageHolder> inputQueue, GENMessageSender dataTransmitter, String uriTo, GENTransport transport)
  {
    this.inputQueue = inputQueue;
    this.uriTo = uriTo;
    this.transport = transport;
    this.dataTransmitter = dataTransmitter;
    setName(getClass().getName() + " URI:" + uriTo);
  }

  @Override
  public void run()
  {
    // read forever while not interrupted
    while (!interrupted())
    {
      try
      {
        GENOutgoingMessageHolder packet = inputQueue.take();

        try
        {
          dataTransmitter.sendEncodedMessage(packet.getData());

          //send back reply that the data was sent succesfully
          packet.setResult(Boolean.TRUE);
        }
        catch (IOException e)
        {
          LOGGER.log(Level.WARNING, "Cannot send packet to destination:" + uriTo + " informing transport");
          //send back reply that the data was not sent succesfully
          packet.setResult(Boolean.FALSE);
          //inform transport about communication error 
          transport.communicationError(uriTo, null);
          break;
        }
      }
      catch (InterruptedException e)
      {
        // finish processing
        break;
      }
    }

    // finished processing, close socket if not already closed
      dataTransmitter.close();
  }

  public String getUriTo()
  {
    return uriTo;
  }
}
