/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Generic Transport Framework
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
package esa.mo.mal.transport.gen;

import static esa.mo.mal.transport.gen.Transport.LOGGER;
import esa.mo.mal.transport.gen.body.LazyMessageBody;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.BadEncodingException;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.encoding.MALEncodingContext;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALTransmitErrorException;

/**
 * A generic implementation of the message interface.
 */
public class GENMessage implements MALMessage, java.io.Serializable {

    private static final long serialVersionUID = 222222222222222L;
    protected MALMessageHeader header;
    protected LazyMessageBody body;
    protected Map qosProperties;
    protected MALElementStreamFactory encFactory;

    public GENMessage(MALMessageHeader header, LazyMessageBody body,
            MALElementStreamFactory encFactory, Map qosProperties) {
        this.header = header;
        this.body = body;
        this.encFactory = encFactory;
        this.qosProperties = qosProperties;
    }

    /**
     * Constructor.
     *
     * @param header The message header to use.
     * @param qosProperties The QoS properties for this message.
     * @param encFactory The stream factory to use for decoding.
     * @param body the body of the message.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException If the operation
     * is unknown.
     */
    @Deprecated
    public GENMessage(
            final MALMessageHeader header,
            final Map qosProperties,
            final MALElementStreamFactory encFactory,
            final Object... body) throws MALInteractionException {
        this.header = header;
        this.encFactory = encFactory;
        this.body = LazyMessageBody.createMessageBody(header, encFactory, body);
        this.qosProperties = qosProperties;
    }

    @Override
    public MALMessageHeader getHeader() {
        return header;
    }

    @Override
    public MALMessageBody getBody() {
        return body;
    }

    @Override
    public Map getQoSProperties() {
        return qosProperties;
    }

    /**
     * Encodes the contents of the message into the provided stream
     *
     * @param streamFactory The stream factory to use for encoder creation.
     * @param enc The output stream to use for encoding.
     * @param outStream the stream to write to.
     * @param writeHeader True if the header should be written to the output
     * stream.
     * @throws MALException On encoding error.
     */
    public void encodeMessage(final MALElementStreamFactory streamFactory,
            final MALElementOutputStream enc,
            final OutputStream outStream,
            final boolean writeHeader) throws MALException {
        try {
            MALEncodingContext ctx = new MALEncodingContext(header);

            // If we have a header encode it
            if (writeHeader && (header != null)) {
                enc.writeHeader(header);
            }

            // Now encode the body:
            body.encodeMessageBody(streamFactory, enc, outStream, ctx);
        } catch (IllegalArgumentException ex1) {
            throw new MALException("Internal error encoding header of message", ex1);
        } catch (MALException ex2) {
            throw new MALException("Internal error encoding message for"
                    + " message with header:\n" + header.toString(), ex2);
        }
    }

    /**
     * Encodes the message.
     *
     * @return The message holder for the outgoing message.
     * @throws MALTransmitErrorException if an error.
     */
    public byte[] internalEncodeByteMessage() throws MALTransmitErrorException {
        // encode the message
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final MALElementOutputStream enc = encFactory.createOutputStream(baos);
            this.encodeMessage(encFactory, enc, baos, true);
            byte[] data = baos.toByteArray();

            // Message is encoded:
            LOGGER.log(Level.FINE, "Encoded message: {0}", new PacketToString(data));
            return data;
        } catch (MALException ex) {
            LOGGER.log(Level.SEVERE, "Could not encode message!", ex);
            throw new MALTransmitErrorException(this.getHeader(),
                    new BadEncodingException(null), null);
        }
    }

}
