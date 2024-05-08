/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO HTTP Transport Framework
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
package esa.mo.mal.transport.http.receiving;

import esa.mo.mal.transport.gen.GENMessage;
import esa.mo.mal.transport.gen.PacketToString;
import esa.mo.mal.transport.gen.receivers.IncomingMessageHolder;
import esa.mo.mal.transport.http.HTTPTransport;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * The HTTP message receiver. Holds a reference to the transport instance that
 * created it and defines a single method for the reception of an encoded
 * message.
 */
public class HTTPMessageReceiver {

    private final HTTPTransport transport;
    private final MALMessageHeader header;
    private final int statusCode;

    /**
     * Creates a new instance of HTTPMessageReceiver
     *
     * @param transport The transport instance to pass received messages to.
     * @param header The message header.
     * @param statusCode The status code.
     */
    public HTTPMessageReceiver(HTTPTransport transport, MALMessageHeader header, int statusCode) {
        this.transport = transport;
        this.header = header;
        this.statusCode = statusCode;
    }

    /**
     * Used to pass an encoded message to a HTTP Transport instance.
     *
     * @param packet The encoded message.
     */
    public void receive(final byte[] packet) {
        try {
            PacketToString smsg = new PacketToString(packet);
            GENMessage malMsg = transport.decodeMessage(new HTTPHeaderAndBody(header, packet, statusCode));
            transport.receive(null, new IncomingMessageHolder(malMsg, smsg));
        } catch (MALException ex) {
            Logger.getLogger(HTTPMessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
