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
package esa.mo.mal.transport.tcpip;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import static esa.mo.mal.transport.tcpip.TCPIPTransport.RLOGGER;

/**
 * The TCPIP Connection pool manager keeps a list of client sockets, uniquely
 * identified by port.
 *
 * This class handles the creation, retrieval and destruction of client sockets.
 * Each time a client requests a new socket to some provider, it requests it
 * from here. The connection pool manager either returns the socket if it
 * already exists, or creates a new socket, adds it to the list of sockets and
 * returns the socket to the client.
 *
 * The connection pool manager supports the creation of both sockets without a
 * predefined port, and sockets with a predefined port. In the former case, a
 * random ephemeral port number is assigned. In the latter case, a socket is
 * created with a predefined port. If this doesn't work because the port already
 * exists, the manager creates a socket with an ephemeral port number.
 *
 * @author Rian van Gijlswijk
 */
public class TCPIPClientSocketsManager {

    /**
     * The internal list of sockets. Each socket is identified by its port
     * number.
     */
    private final Map<Integer, Socket> connections = new HashMap<>();

    /**
     * Returns a socket bound to a specific port. If this socket doesn't exist,
     * this method will try once to create a socket on the same port. If that is
     * not possible, a socket with an ephemeral port number will be created and
     * returned.
     *
     * @param localPort The port number of the socket to return. Returns a
     * socket with an ephemeral port number if the localPort is occupied or not
     * existant.
     * @return Socket instance
     */
    public synchronized Socket get(int localPort) {
        Socket s = connections.get(localPort);

        if (s == null) {
            s = createSocket(localPort);
        }

        return s;
    }

    /**
     * Create a socket at a predefined port. If this is not possible, because
     * the port is occupied or otherwise unavailable, this method tries to
     * create a socket with an ephemeral port number exactly once.
     *
     * @param localPort The port number to create the socket at
     * @return Socket instance
     */
    private synchronized Socket createSocket(int localPort) {
        Socket s = new Socket();
        try {
            s.bind(new InetSocketAddress(localPort));
            RLOGGER.log(Level.INFO, "New socket created on port: {0}", s.getLocalPort());
        } catch (IOException e) {
            try {
                s.bind(null);
            } catch (IOException e1) {
                RLOGGER.log(Level.WARNING, "Failed to create a socket!", e1);
            }

            RLOGGER.log(Level.WARNING, "Failed to create a socket at port {0}! {1}",
                    new Object[]{localPort, e.getMessage()});
        }

        connections.put(s.getLocalPort(), s);
        return s;
    }

    /**
     * Close all sockets and remove them from the connections pool
     */
    public synchronized void close() {
        RLOGGER.info("Closing client sockets...");

        for (int port : connections.keySet()) {
            try {
                connections.get(port).close();
                connections.remove(port);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * Return a string representation of the current connection pool
     *
     * @return
     */
    @Override
    public synchronized String toString() {
        StringBuilder result = new StringBuilder();
        result.append("LocalSockets:\n");

        for (int port : connections.keySet()) {
            result.append(" -> ").append(port).append(" - ");
            result.append(connections.get(port)).append("\n");
        }

        return result.toString();
    }
}
