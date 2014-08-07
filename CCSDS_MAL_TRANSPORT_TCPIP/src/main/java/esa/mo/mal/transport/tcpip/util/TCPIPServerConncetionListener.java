/**
 * 
 */
package esa.mo.mal.transport.tcpip.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import esa.mo.mal.transport.tcpip.TCPIPTransport;

/**
 * Server Thread for the TCPIP transport. 
 * 
 * This thread listens for new connections to a predefined port and when new connections
 * arrive it forwards the newly created socket to a dedicated manager thread.
 * 
 * @author Petros Pissias
 *
 */
public class TCPIPServerConncetionListener extends Thread {

    private final TCPIPTransport transport;
    private final ServerSocket serverSocket;

    private final java.util.logging.Logger LOGGER = TCPIPTransport.LOGGER;

    public TCPIPServerConncetionListener(TCPIPTransport transport, ServerSocket serverSocket) {
	this.transport = transport;
	this.serverSocket = serverSocket;
	setName(getClass().getName()+" - Main Server Socket Thread");
    }

    @Override
    public void run() {
	// setup socket and then listen for connections forever
	while (true) {
	    try {

		// wait for connection
		Socket socket = serverSocket.accept();

		// handle socket in separate thread
		new TCPIPConnectionDataReceiver(transport, socket).start();

	    } catch (IOException e) {
		LOGGER.log(Level.WARNING, "Error while accepting connection", e);
	    }
	}
    }
}
