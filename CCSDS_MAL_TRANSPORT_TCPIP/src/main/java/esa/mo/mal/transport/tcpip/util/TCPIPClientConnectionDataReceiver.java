/**
 * 
 */
package esa.mo.mal.transport.tcpip.util;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALMessage;

import esa.mo.mal.transport.tcpip.TCPIPMessage;
import esa.mo.mal.transport.tcpip.TCPIPTransport;

/**
 * This thread is responsible for receiving data from a connection
 * that was initiated by this MAL (Client connection). 
 * Its main difference than its parent, which is normaly used
 * in reading data from server connections, is that at construction time we
 * know the remote URI associated with this resource. 
 * Thus the transport will not re-associate this resource with a URI on the first reception message.
 * 
 * @author Petros Pissias
 *
 */
public class TCPIPClientConnectionDataReceiver extends TCPIPConnectionDataReceiver {

    public TCPIPClientConnectionDataReceiver(TCPIPTransport transport, Socket socket, String URI) throws IOException {
	super(transport,socket);
	super.setRemoteURI(URI);
	setName(getClass().getName()+" URI:"+URI);
    }

}
