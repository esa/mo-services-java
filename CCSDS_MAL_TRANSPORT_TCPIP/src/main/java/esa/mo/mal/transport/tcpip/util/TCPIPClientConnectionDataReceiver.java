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
