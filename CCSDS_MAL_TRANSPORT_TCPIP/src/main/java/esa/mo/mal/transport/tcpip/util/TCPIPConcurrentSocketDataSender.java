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

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;

import esa.mo.mal.transport.tcpip.TCPIPTransport;

/**
 * This class manages a set of threads that are able
 * to send messages via Sockets. It uses a blocking queue from
 * where all threads consume messages to be sent. 
 * The worker threads are created via the addProcessor method
 * which is called when a new Socket is associated to a given URI
 * by the transport.
 * 
 * Each object of this class is associated with a URI at the transport level.
 *  
 * There is normally a numConnections (transport configuration item) number
 * of threads. 
 * 
 * It accepts requests to send the data, which is done via the worker threads.
 * A reply is provided indicating if the data was sent successfully or not.
 * 
 * @author Petros Pissias
 *
 */
public class TCPIPConcurrentSocketDataSender {

    //logger
    private final java.util.logging.Logger LOGGER = TCPIPTransport.LOGGER;

    //input message queue
    private final BlockingQueue<TCPIPOutgoingDataHolder> inputQueue;

    //the list of processing threads that send the messages
    private final List<TCPIPSocketSenderThread> processingThreads;

    //reference to the transport
    private final TCPIPTransport transport;

    //reference to target URI
    private final String taretURI;
    /**
     * Creates a new instance. Typically each instance is associated with a given URI.
     * @param transport reference to the transport
     */
    public TCPIPConcurrentSocketDataSender(TCPIPTransport transport, String taretURI) {
	inputQueue = new LinkedBlockingQueue<TCPIPOutgoingDataHolder>();
	processingThreads = Collections.synchronizedList(new ArrayList<TCPIPSocketSenderThread>());
	this.transport = transport;
	this.taretURI = taretURI;
    }

    /**
     * This method will try to send the data via one of the available
     * sockets and provide a reply through the TCPIPOutgoingDataHolder object
     * if the data was succesful or not. users of this method should call 
     * getResult to block waiting for an indication if the data was sent succesfully or not.
     * @param data the data to be sent. 
     */
    public void sendData(TCPIPOutgoingDataHolder data) {
	if (processingThreads.isEmpty()) {
	    //this should never happen. Only possibly in boundary cases where this object is asked
	    //to terminate and there is another thread trying to send data in parallel.
	    LOGGER.log(Level.SEVERE, "No active processors in this processing queue!", new Throwable());
	    data.setResult(Boolean.FALSE);

	    return;
	}
	boolean inserted = inputQueue.add(data);
	if (!inserted) {
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
     * @param socket the socket that this processor will use
     * @param uriTo the target URI
     * @return number of active processors
     * @throws IOException in case of a problem with the provided socket
     */
    public synchronized int addProcessor(Socket socket, String uriTo) throws IOException {
	// create new thread
	TCPIPSocketSenderThread procThread = new TCPIPSocketSenderThread(inputQueue, socket, uriTo, transport);
	// keep reference to thread
	processingThreads.add(procThread);
	// start thread
	procThread.start();
	
	LOGGER.log(Level.INFO, "Adding processor for URI:" + uriTo+" total processors:" +processingThreads.size());
	
	// return number of processors
	return processingThreads.size();
    }

 

    /**
     * This method will shutdown all processing threads (by calling their interrupt method)
     * which will result in all of them closing their sockets and terminating their processing.
     * 
     * Typically Called by the transport in order to shutdown all processing threads and
     * close all remote connections.
     */
    public synchronized void terminate() {
	LOGGER.log(Level.INFO, "Terminating all processing threads for sender for URI:"+taretURI);

	for (TCPIPSocketSenderThread t : processingThreads) {
	    // this will cause all threads to terminate
	    LOGGER.log(Level.INFO, "Terminating sender processing thread for URI:"+ t.getUriTo());
	    t.interrupt();
	}
	//clear the references to active threads
	processingThreads.clear();
    }

}
