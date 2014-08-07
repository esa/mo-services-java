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
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;

import esa.mo.mal.transport.tcpip.TCPIPTransport;

/**
 * This thread will listen for incoming messages
 * through a blocking queue and send them through a socket. 
 * In case of communication problems it will inform the transport and terminate. 
 * 
 * In any case, a reply is send back to the originator of the request
 * using a blocking queue from the input data.
 * 
 * @author Petros Pissias
 *
 */
public class TCPIPSocketSenderThread extends Thread {

    public final java.util.logging.Logger LOGGER = TCPIPTransport.LOGGER;

    // input queue to receive data to send
    private final BlockingQueue<TCPIPOutgoingDataHolder> inputQueue;

    // the socket to send the data to
    private final Socket socket;

    // the destination URI
    private final String uriTo;

    // the data tranceiver
    private final TCPIPTransportDataTransceiver dataSender;

    // the transport object. Needed in order to send information on connection /
    // data problems
    private TCPIPTransport transport;

    /**
     * Constructor
     * 
     * @param inputQueue
     * @param socket
     * @param uriTo
     * @param transport
     * @throws IOException
     */
    public TCPIPSocketSenderThread(BlockingQueue<TCPIPOutgoingDataHolder> inputQueue, Socket socket, String uriTo, TCPIPTransport transport) throws IOException {
	this.inputQueue = inputQueue;
	this.socket = socket;
	this.uriTo = uriTo;
	this.transport = transport;
	dataSender = new TCPIPTransportDataTransceiver(socket);
	setName(getClass().getName()+" URI:"+uriTo);
    }

    @Override
    public void run() {
	// read forever while not interrupted
	while (!interrupted()) {
	    try {
		TCPIPOutgoingDataHolder packet = inputQueue.take();

		try {
		    dataSender.sendPacket(packet.getData());
		    
		    //send back reply that the data was sent succesfully
		    packet.setResult(Boolean.TRUE);
		} catch (IOException e) {
		    LOGGER.log(Level.WARNING, "Cannot send packet to descination:" + uriTo + " informing transport");
		    //send back reply that the data was not sent succesfully
		    packet.setResult(Boolean.FALSE);
		    //inform transport about communication error 
		    transport.communicationError(uriTo);
		    break;
		}


		
	    } catch (InterruptedException e) {
		// finish processing
		break;
	    }
	}

	// finished processing, close socket if not already closed
	try {
	    socket.close();
	} catch (IOException e) {
	    // ignore
	}
    }

    public Socket getSocket() {
	return socket;
    }

    public String getUriTo() {
	return uriTo;
    }

}
