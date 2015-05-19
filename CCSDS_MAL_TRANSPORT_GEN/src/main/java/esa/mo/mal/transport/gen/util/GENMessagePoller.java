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
import java.io.InputStream;

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
  private final MessageAdapter messageReceiver;
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
  public GENMessagePoller(GENTransport transport, GENMessageSender messageSender, GENByteMessageReceiver messageReceiver)
  {
    this.transport = transport;
    this.messageSender = messageSender;
    this.messageReceiver = new ByteAdapter(transport, this, messageReceiver);
    setName(getClass().getName());
  }

  /**
   * Constructor.
   *
   * @param transport Message transport being used.
   * @param messageSender The message sending interface associated to this connection.
   * @param messageReceiver The message reception interface, used for pulling messaging into this transport.
   */
  public GENMessagePoller(GENTransport transport, GENMessageSender messageSender, GENStreamMessageReceiver messageReceiver)
  {
    this.transport = transport;
    this.messageSender = messageSender;
    this.messageReceiver = new StreamAdapter(transport, this, messageReceiver);
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
        messageReceiver.receiveMessage();
      }
      catch (InterruptedException ex)
      {
        LOGGER.log(Level.INFO, "Client closing connection: {0}", remoteURI);

        transport.closeConnection(remoteURI, this);
        close();

        //and terminate
        bContinue = false;
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
  public static interface GENByteMessageReceiver
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

  /**
   * Simple interface for reading stream encoded messages from a low level transport. Used by the message poller class.
   */
  public static interface GENStreamMessageReceiver
  {
    /**
     * Reads a MALMessage encoded as a IO stream.
     *
     * @return the input stream containing the encoded MAL Message
     * @throws IOException in case the encoded message cannot be read
     * @throws InterruptedException in case IO read is interrupted
     */
    java.io.InputStream readEncodedMessage() throws IOException, InterruptedException;

    /**
     * Closes any used resources.
     */
    void close();
  }

  private interface MessageAdapter
  {
    void receiveMessage() throws IOException, InterruptedException;
    
    void close();
  }

  private static class ByteAdapter implements MessageAdapter
  {
    private final GENTransport transport;
    private final GENReceptionHandler handler;
    private final GENByteMessageReceiver receiver;

    public ByteAdapter(GENTransport transport, GENReceptionHandler handler, GENByteMessageReceiver receiver)
    {
      this.transport = transport;
      this.handler = handler;
      this.receiver = receiver;
    }

    public void receiveMessage() throws IOException, InterruptedException
    {
      byte[] msg = receiver.readEncodedMessage();
      
      if(null != msg)
      {
        transport.receive(msg, handler);
      }
    }
    
    public void close()
    {
      receiver.close();
    }
  }

  private static class StreamAdapter implements MessageAdapter
  {
    private final GENTransport transport;
    private final GENReceptionHandler handler;
    private final GENStreamMessageReceiver receiver;

    public StreamAdapter(GENTransport transport, GENReceptionHandler handler, GENStreamMessageReceiver receiver)
    {
      this.transport = transport;
      this.handler = handler;
      this.receiver = receiver;
    }

    public void receiveMessage() throws IOException, InterruptedException
    {
      InputStream msg = receiver.readEncodedMessage();
      
      if(null != msg)
      {
        transport.receive(msg, handler);
      }
    }
    
    public void close()
    {
      receiver.close();
    }
  }
}
