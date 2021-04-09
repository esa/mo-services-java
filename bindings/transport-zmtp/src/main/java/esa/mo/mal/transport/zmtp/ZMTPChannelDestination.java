/* ----------------------------------------------------------------------------
 * Copyright (C) 2017      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO ZMTP Transport Framework
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
package esa.mo.mal.transport.zmtp;

import java.io.IOException;
import java.nio.channels.ClosedByInterruptException;
import java.util.logging.Level;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZFrame;
import org.zeromq.ZMQException;

/**
 * This class implements the low level data (MAL Message) transport protocol.
 *
 * If the protocol uses a different message encoding this class can be replaced
 * in the ZMTPTransport.
 *
 */
public class ZMTPChannelDestination {

    /**
     * Reference to a ZMQ socket produced by the OPEN primitive
     */
    protected ZMQ.Socket socket;

    /**
     * Reference to parent transport
     */
    protected final ZMTPTransport transport;

    /**
     * Holds ZMQ Context shadowing the binding context
     */
    protected ZContext zmqContext;

    /**
     * Communication pattern of the socket
     */
    protected int communicationPattern;

    /**
     * URI the socket binds to
     */
    protected String zmtpURI;

    /**
     * Receiver thread
     */
    protected Thread rxThread;

    public static long POLL_TIMEOUT_MS = 500;

    /**
     * Constructor.
     *
     * @param transport the parent transport
     * @param communicationPattern Communication pattern of the socket
     * @param zmtpURI URI the socket binds to
     * @throws IOException if there is an error.
     */
    public ZMTPChannelDestination(ZMTPTransport transport, 
            int communicationPattern, String zmtpURI) throws IOException {
        this.transport = transport;
        this.zmtpURI = zmtpURI;
        this.communicationPattern = communicationPattern;
    }

    public ZMQ.Socket getSocket() {
        return socket;
    }

    public void runRxThread() {
        rxThread = new Thread(() -> {
            zmqContext = ZContext.shadow(transport.getZmqContext());
            socket = ZMTPTransport.openSocket(zmqContext, communicationPattern, zmtpURI, true);

            while (true) {
                if (Thread.interrupted()) {
                    ZMTPTransport.RLOGGER.log(Level.INFO, "Thread interrupted");
                    break;
                }
                try {
                    org.zeromq.ZMsg recvMsg = org.zeromq.ZMsg.recvMsg(socket);
                    ZFrame first = recvMsg.pop(); // The frame with sender id
                    byte[] remoteIdentity = first.getData();
                    byte[] rxData = getMessageBuffer(recvMsg);
                    transport.channelDataReceived(remoteIdentity, rxData);
                    recvMsg.destroy();
                } catch (ZMQException e) {
                    if (e.getErrorCode() == ZMQ.Error.ETERM.getCode()) {
                        ZMTPTransport.RLOGGER.log(Level.INFO,
                                "ZMQ context terminated - shutting down");
                    } else {
                        ZMTPTransport.RLOGGER.log(Level.WARNING,
                                "ZMQ Error in ZMTPChannelDestination RX Thread", e);
                    }
                    break;
                } catch (zmq.ZError.IOException e) {
                    if (e.getCause() instanceof ClosedByInterruptException) {
                        ZMTPTransport.RLOGGER.log(Level.INFO, "Thread interrupted");
                    } else {
                        ZMTPTransport.RLOGGER.log(Level.WARNING,
                                "ZMQ IO Error in ZMTPChannelDestination RX Thread", e);
                    }
                    break;
                }
            }
            // Don't destroy the context properly as it locks the application
            //zmqContext.destroy();
        });
        rxThread.setName("ZMTPChannelDestination RX Thread");
        rxThread.start();
    }

    private byte[] getMessageBuffer(org.zeromq.ZMsg recvMsg) {
        int totalSize = 0;
        // Calculate total data size
        for (ZFrame frame : recvMsg) {
            totalSize += frame.size();
        }
        // Allocate buffer
        byte[] ret = new byte[totalSize];
        // Reassemble frames
        int i = 0;
        for (ZFrame frame : recvMsg) {
            System.arraycopy(frame.getData(), 0, ret, i, frame.size());
            i += frame.size();
        }
        return ret;
    }

    public void interrupt() {
        rxThread.interrupt();
    }
}
