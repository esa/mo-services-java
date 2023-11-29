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

import esa.mo.mal.encoder.tcpip.TCPIPFixedBinaryEncoder;
import static esa.mo.mal.transport.tcpip.TCPIPTransport.RLOGGER;
import esa.mo.mal.transport.gen.GENMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;

/**
 * This TCPIP implementation of MAL Message provides encoding methods for
 * encoding the MAL Message according to the TCPIP Transport Binding red book.
 *
 * @author Rian van Gijlswijk
 *
 */
public class TCPIPMessage extends GENMessage {

    public TCPIPMessage(boolean wrapBodyParts,
            TCPIPMessageHeader header, Map qosProperties, byte[] packet,
            MALElementStreamFactory encFactory) throws MALException {
        super(wrapBodyParts, false, header, qosProperties, packet, encFactory);
    }

    public TCPIPMessage(boolean wrapBodyParts, TCPIPMessageHeader header, Map qosProperties,
            MALElementStreamFactory encFactory, Object... body) throws MALInteractionException {
        super(wrapBodyParts, header, qosProperties, encFactory, body);
    }

    /**
     * Encode a MAL Message.
     *
     * Header and body are encoded separately, each with their own separate
     * stream Factory. This is done because the header needs to be encoded
     * according to the specifications in the TCPIP Transport Binding red book,
     * but the body can be whatever.
     *
     * @param bodyStreamFactory the stream factory to use for body encoding
     * @param lowLevelOutputStream the stream onto which both the encoded head
     * and body will be written
     * @throws MALException if encoding failed
     */
    public void encodeMessage(final MALElementStreamFactory bodyStreamFactory,
            final OutputStream lowLevelOutputStream) throws MALException {
        // Header must always be TCPIP Fixed Binary
        ByteArrayOutputStream hdrBaos = new ByteArrayOutputStream();
        TCPIPFixedBinaryEncoder encoder = new TCPIPFixedBinaryEncoder(hdrBaos);
        ((TCPIPMessageHeader) this.getHeader()).encode(encoder);

        // Encode Body using the selected Encoding
        ByteArrayOutputStream bodyBaos = new ByteArrayOutputStream();
        MALElementOutputStream bodyEncoder = bodyStreamFactory.createOutputStream(bodyBaos);
        super.encodeMessage(bodyStreamFactory, bodyEncoder, bodyBaos, false);

        // Overwrite bodysize parameter in the Header
        byte[] hdrBuf = hdrBaos.toByteArray();
        ByteBuffer b = ByteBuffer.allocate(4);
        b.order(ByteOrder.BIG_ENDIAN);
        b.putInt(hdrBaos.size() + bodyBaos.size() - 23);
        System.arraycopy(b.array(), 0, hdrBuf, 19, 4);

        try {
            lowLevelOutputStream.write(hdrBuf);
            if (this.getBody() != null) {
                lowLevelOutputStream.write(bodyBaos.toByteArray());
            }
        } catch (IOException e) {
            RLOGGER.log(Level.WARNING,
                    "An IOException was thrown during message encoding!", e);
            throw new MALException(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "TCPIPMessage {URIFrom:" + this.getHeader().getFrom()
                + "URITo:" + this.getHeader().getTo() + "}";
    }

    /**
     * Ok for debugging but don't put it nowhere near the final code.
     * Performance!
     *
     * @return the string representation of the body of the message.
     */
    public String bodytoString() {
        if (this.getBody() != null) {
            StringBuilder output = new StringBuilder();
            output.append(this.getBody().getClass().getCanonicalName());
            for (int i = 0; i < this.getBody().getElementCount(); i++) {
                try {
                    if (this.getBody().getBodyElement(i, null) != null) {
                        output.append(" | ");
                        output.append(this.getBody().getBodyElement(i, null).toString());
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (MALException e) {
                    e.printStackTrace();
                }
            }
            return output.toString();
        }
        return " --no body--";
    }
}
