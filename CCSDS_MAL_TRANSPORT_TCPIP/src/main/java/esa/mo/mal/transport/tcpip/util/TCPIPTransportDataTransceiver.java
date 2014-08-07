/**
 * 
 */
package esa.mo.mal.transport.tcpip.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Petros Pissias
 *         
 * This class implements the low level data (MAL Message) transport
 * protocol. In order to differentiate messages with each other, the
 * protocol has a very simple format: |size|message|
 * 
 */
public class TCPIPTransportDataTransceiver {

    private final Socket socket;

    private final DataOutputStream socketWriteIf;
    private final DataInputStream socketReadIf;

    public TCPIPTransportDataTransceiver(Socket socket) throws IOException {
	this.socket = socket;
	socketWriteIf = new DataOutputStream(socket.getOutputStream());
	socketReadIf = new DataInputStream(socket.getInputStream());
    }

    /**
     * Sends data to the client (MAL Message encoded as a byte array)
     * 
     * @param packetData the MALMessage encoded as a byte array
     * @throws IOException in case the data cannot be send to the client
     */
    public void sendPacket(byte[] packetData) throws IOException {
	// write packet length and then the packet
	socketWriteIf.writeInt(packetData.length);

	socketWriteIf.write(packetData);
	socketWriteIf.flush();
    }

    /**
     * Reads a MALMessage encoded as a byte array.
     * 
     * @return the byte array containing the MAL Message
     * @throws IOException in case the data cannot be read
     */
    public byte[] readPacket() throws IOException {
	// read packet length and then the packet
	int packetSize = socketReadIf.readInt();
	byte[] data = new byte[packetSize];
	socketReadIf.readFully(data);
	return data;
    }

}
