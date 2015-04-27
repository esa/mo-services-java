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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;

import static esa.mo.mal.transport.gen.GENTransport.LOGGER;

/**
 * This class holds the message to be sent in encoded format and a reply queue that the internal sender of the message
 * can listen to in order to be informed if the message was successfully sent or not.
 *
 */
public class GENOutgoingMessageHolder
{
  //reply queue
  private final BlockingQueue<Boolean> replyQueue;

  private final String destinationRootURI;
  private final String destinationURI;
  private final Object handle;
  private final boolean lastForHandle;
  //the encoded message
  private final byte[] encodedMessage;

  /**
   * Will construct a new object and create a new internal reply queue
   *
   * @param encodedMessage the encoded message to be sent
   */
  public GENOutgoingMessageHolder(final String destinationRootURI,
          final String destinationURI, final Object handle,
          final boolean lastForHandle,
          byte[] encodedMessage)
  {
    replyQueue = new LinkedBlockingQueue<Boolean>();
    this.destinationRootURI = destinationRootURI;
    this.destinationURI = destinationURI;
    this.handle = handle;
    this.lastForHandle = lastForHandle;
    this.encodedMessage = encodedMessage;
  }

  /**
   * This method blocks until there is an attempt to send the message.
   *
   * @return TRUE if the message was successfully sent and FALSE if there was a communication or internal problem.
   * @throws InterruptedException in case of shutting down or internal error
   */
  public Boolean getResult() throws InterruptedException
  {
    return replyQueue.take();
  }

  /**
   * Sets the result indicating if the message was sent successfully.
   *
   * @param result TRUE if the message was successfully sent and FALSE if there was a communication or internal problem.
   */
  public void setResult(Boolean result)
  {
    boolean inserted = replyQueue.add(result);
    if (!inserted)
    {
      // log error. According to the specification (see *add* call
      // documentation) this will always return true, or throw an
      // exception
      LOGGER.log(Level.SEVERE, "Could not insert result to processing queue", new Throwable());
    }
  }

  public String getDestinationURI()
  {
    return destinationURI;
  }

  public String getDestinationRootURI()
  {
    return destinationRootURI;
  }

  public Object getHandle()
  {
    return handle;
  }

  public boolean isLastForHandle()
  {
    return lastForHandle;
  }

  /**
   * Getter for the encoded message to be sent
   *
   * @return the encoded message
   */
  public byte[] getEncodedMessage()
  {
    return encodedMessage;
  }
}
