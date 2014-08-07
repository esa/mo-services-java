/**
 * 
 */
package esa.mo.mal.transport.tcpip.util;

import esa.mo.mal.transport.tcpip.TCPIPTransport;

/**
 * This Runnable task is responsible of forwarding newly arrived MAL Messages (in raw format)
 * to the transport. 
 * 
 * @author Petros Pissias
 *
 */
public class TCPIPInputDataForwarder implements Runnable {

    private final TCPIPTransport transport;
    private final byte[] rawMessage;
    private final TCPIPConnectionDataReceiver receptionHandler;
    
    public TCPIPInputDataForwarder(TCPIPTransport transport, byte[] rawMessage, TCPIPConnectionDataReceiver receptionHandler) {
	this.transport = transport;
	this.rawMessage = rawMessage;
	this.receptionHandler = receptionHandler;
    }
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
	transport.receive(rawMessage, receptionHandler);
    }

}
