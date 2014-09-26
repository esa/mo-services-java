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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;

import static esa.mo.mal.transport.gen.GENTransport.LOGGER;

/**
 * This class manages a set of threads that are able to send messages via transceivers. It uses a blocking queue from
 * where all threads consume messages to be sent. The worker threads are created via the addProcessor method which is
 * called when a new connection is associated to a given URI by the transport.
 *
 * Each object of this class is associated with a URI at the transport level.
 *
 * There is normally a numConnections (transport configuration item) number of threads.
 *
 * It accepts requests to send the data, which is done via the worker threads. A reply is provided indicating if the
 * data was sent successfully or not.
 *
 */
public class GENConcurrentMessageSender
{
  //input message queue
  private final BlockingQueue<GENOutgoingDataHolder> inputQueue;

  //the list of processing threads that send the messages
  private final List<GENSenderThread> processingThreads;

  //reference to the transport
  private final GENTransport transport;

  //reference to target URI
  private final String targetURI;

  /**
   * Creates a new instance. Typically each instance is associated with a given URI.
   *
   * @param transport reference to the transport
   * @param targetURI
   */
  public GENConcurrentMessageSender(GENTransport transport, String targetURI)
  {
    inputQueue = new LinkedBlockingQueue<GENOutgoingDataHolder>();
    processingThreads = Collections.synchronizedList(new ArrayList<GENSenderThread>());
    this.transport = transport;
    this.targetURI = targetURI;
  }

  /**
   * This method will try to send the data via one of the available sockets and provide a reply through the
   * TCPIPOutgoingDataHolder object if the data was successful or not. users of this method should call getResult to
   * block waiting for an indication if the data was sent successfully or not.
   *
   * @param data the data to be sent.
   */
  public void sendMessage(GENOutgoingDataHolder data)
  {
    if (processingThreads.isEmpty())
    {
      //this should never happen. Only possibly in boundary cases where this object is asked
      //to terminate and there is another thread trying to send data in parallel.
      LOGGER.log(Level.SEVERE, "No active processors in this processing queue!", new Throwable());
      data.setResult(Boolean.FALSE);

      return;
    }
    boolean inserted = inputQueue.add(data);
    if (!inserted)
    {
      // log error. According to the specification (see *add* call
      // documentation) this will always return true, or throw an
      // exception
      LOGGER.log(Level.SEVERE, "Could not insert message to processing queue", new Throwable());
      data.setResult(Boolean.FALSE);
    }
  }

  /**
   * Adds a processor which is able to send messages to a specific URI
   *
   * @param dataTransmitter the socket that this processor will use
   * @param uriTo the target URI
   * @return number of active processors
   */
  public synchronized int addProcessor(GENDataTransmitter dataTransmitter, String uriTo)
  {
    // create new thread
    GENSenderThread procThread = new GENSenderThread(inputQueue, dataTransmitter, uriTo, transport);
    // keep reference to thread
    processingThreads.add(procThread);
    // start thread
    procThread.start();

    LOGGER.log(Level.INFO, "Adding processor for URI:{0} total processors:{1}", new Object[]{uriTo, processingThreads.size()});

    // return number of processors
    return processingThreads.size();
  }

  public String getTargetURI()
  {
    return targetURI;
  }

  /**
   * This method will shutdown all processing threads (by calling their interrupt method) which will result in all of
   * them closing their sockets and terminating their processing.
   *
   * Typically Called by the transport in order to shutdown all processing threads and close all remote connections.
   */
  public synchronized void terminate()
  {
    LOGGER.log(Level.INFO, "Terminating all processing threads for sender for URI:{0}", targetURI);

    for (GENSenderThread t : processingThreads)
    {
      // this will cause all threads to terminate
      LOGGER.log(Level.INFO, "Terminating sender processing thread for URI:{0}", t.getUriTo());
      t.interrupt();
    }
    //clear the references to active threads
    processingThreads.clear();
  }
}
