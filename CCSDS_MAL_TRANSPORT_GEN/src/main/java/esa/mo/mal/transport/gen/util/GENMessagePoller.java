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
import esa.mo.mal.transport.gen.sending.GENMessageSender;
import esa.mo.mal.transport.gen.GENTransport;
import java.io.IOException;
import java.io.EOFException;
import java.util.logging.Level;
import static esa.mo.mal.transport.gen.GENTransport.LOGGER;

/**
 * This utility class creates a thread to pull encoded messages from a transceiver. It receives messages from it and
 * then forwards the incoming message to an asynchronous processor in order to return immediately and not hold the
 * calling thread while the message is processed.
 *
 * In case of a communication problem it informs the transport and/or closes the resource
 *
 * Only transport adapter that pull messages from their transport layer will need to use this class.
 */
public class GENMessagePoller extends Thread implements GENReceptionHandler
{
  /**
   * Reference to the transport
   */
  private final GENTransport transport;
  /**
   * the low level message sender
   */
  private final GENMessageSender messageSender;
  /**
   * the low level message receiver
   */
  private final GENMessageReceiver messageReceiver;
  /**
   * the remote URI (client) this connection is associated to. This is volatile as it is potentially set by a different
   * thread after its creation
   */
  private volatile String remoteURI = null;

  /**
   * Constructor.
   *
   * @param transport Message transport being used.
   * @param messageSender The message sending interface associated to this connection.
   * @param messageReceiver The message reception interface, used for pulling messaging into this transport.
   */
  public GENMessagePoller(GENTransport transport, GENMessageSender messageSender, GENMessageReceiver messageReceiver)
  {
    this.transport = transport;
    this.messageSender = messageSender;
    this.messageReceiver = messageReceiver;
    setName(getClass().getName());
  }

  @Override
  public void run()
  {
    boolean bContinue = true;

    // handles message reads from this client
    while (bContinue && !interrupted())
    {
      try
      {
        byte[] encodedMalMessage = messageReceiver.readEncodedMessage();

        transport.receive(encodedMalMessage, this);
      }
      catch (EOFException ex)
      {
        LOGGER.log(Level.INFO, "Client closing connection: {0}", remoteURI);

        transport.closeConnection(remoteURI, this);
        close();

        //and terminate
        bContinue = false;
      }
      catch (IOException e)
      {
        LOGGER.log(Level.WARNING, "Cannot read message from client", e);

        transport.communicationError(remoteURI, this);
        close();

        //and terminate
        bContinue = false;
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
  public GENMessageSender getMessageSender()
  {
    return messageSender;
  }

  @Override
  public void close()
  {
    messageSender.close();
    messageReceiver.close();
  }

  /**
   * Simple interface for reading byte encoded messages from a low level transport. Used by the message poller class.
   */
  public static interface GENMessageReceiver
  {
    /**
     * Reads a MALMessage encoded as a byte array.
     *
     * @return the byte array containing the encoded MAL Message
     * @throws IOException in case the encoded message cannot be read
     */
    byte[] readEncodedMessage() throws IOException;

    /**
     * Closes any used resources.
     */
    void close();
  }
}
