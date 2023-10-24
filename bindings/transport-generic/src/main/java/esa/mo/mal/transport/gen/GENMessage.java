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

import esa.mo.mal.transport.gen.body.DeregisterBody;
import esa.mo.mal.transport.gen.body.ErrorBody;
import esa.mo.mal.transport.gen.body.MessageBody;
import esa.mo.mal.transport.gen.body.NotifyBody;
import esa.mo.mal.transport.gen.body.PublishBody;
import esa.mo.mal.transport.gen.body.PublishRegisterBody;
import esa.mo.mal.transport.gen.body.RegisterBody;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.encoding.MALEncodingContext;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * A generic implementation of the message interface.
 */
public class GENMessage implements MALMessage, java.io.Serializable {

    private static final long serialVersionUID = 222222222222222L;
    protected final MALMessageHeader header;
    protected final MessageBody body;
    protected final Map qosProperties;
    protected final boolean wrapBodyParts;

    /**
     * Constructor.
     *
     * @param wrapBodyParts True if the encoded body parts should be wrapped in
     * BLOBs.
     * @param header The message header to use.
     * @param qosProperties The QoS properties for this message.
     * @param encFactory The stream factory to use for decoding.
     * @param body the body of the message.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException If the operation
     * is unknown.
     */
    public GENMessage(final boolean wrapBodyParts,
            final MALMessageHeader header,
            final Map qosProperties,
            final MALElementStreamFactory encFactory,
            final Object... body) throws MALInteractionException {
        this.header = header;
        this.body = createMessageBody(encFactory, body);
        this.qosProperties = qosProperties;
        this.wrapBodyParts = wrapBodyParts;
    }

    /**
     * Constructor.
     *
     * @param wrapBodyParts True if the encoded body parts should be wrapped in
     * BLOBs.
     * @param readHeader True if the header should be read from the packet.
     * @param header An instance of the header class to use.
     * @param qosProperties The QoS properties for this message.
     * @param packet The message in encoded form.
     * @param encFactory The stream factory to use for decoding.
     * @throws MALException On decoding error.
     */
    public GENMessage(final boolean wrapBodyParts,
            final boolean readHeader,
            final MALMessageHeader header,
            final Map qosProperties,
            final byte[] packet,
            final MALElementStreamFactory encFactory) throws MALException {
        this.qosProperties = qosProperties;
        this.wrapBodyParts = wrapBodyParts;

        final ByteArrayInputStream bais = new ByteArrayInputStream(packet);
        final MALElementInputStream enc = encFactory.createInputStream(bais);

        this.header = readHeader ? (MALMessageHeader) enc.readHeader(header) : header;
        this.body = createMessageBody(encFactory, bais, enc);
    }

    /**
     * Constructor.
     *
     * @param wrapBodyParts True if the encoded body parts should be wrapped in
     * BLOBs.
     * @param readHeader True if the header should be read from the stream.
     * @param header An instance of the header class to use.
     * @param qosProperties The QoS properties for this message.
     * @param ios The message in encoded form.
     * @param encFactory The stream factory to use for decoding.
     * @throws MALException On decoding error.
     */
    public GENMessage(final boolean wrapBodyParts,
            final boolean readHeader,
            final MALMessageHeader header,
            final Map qosProperties,
            final java.io.InputStream ios,
            final MALElementStreamFactory encFactory) throws MALException {
        this.qosProperties = qosProperties;
        this.wrapBodyParts = wrapBodyParts;

        final MALElementInputStream enc = encFactory.createInputStream(ios);

        this.header = readHeader ? (MALMessageHeader) enc.readHeader(header) : header;
        this.body = createMessageBody(encFactory, null, enc);
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

    @Override
    public void free() throws MALException {
        // do nothing in the GEN case.
    }

    /**
     * Encodes the contents of the message into the provided stream
     *
     * @param streamFactory The stream factory to use for encoder creation.
     * @param enc The output stream to use for encoding.
     * @param lowLevelOutputStream the stream to write to.
     * @param writeHeader True if the header should be written to the output
     * stream.
     * @throws MALException On encoding error.
     */
    public void encodeMessage(final MALElementStreamFactory streamFactory,
            final MALElementOutputStream enc,
            final OutputStream lowLevelOutputStream,
            final boolean writeHeader) throws MALException {
        try {
            MALEncodingContext ctx = new MALEncodingContext(header);

            // If we have a header encode it
            if (writeHeader && (header != null)) {
                enc.writeHeader(header);
            }

            // Now encode the body:
            body.encodeMessageBody(streamFactory, enc, lowLevelOutputStream, ctx);
        } catch (IllegalArgumentException ex1) {
            throw new MALException("Internal error encoding header of message", ex1);
        } catch (MALException ex2) {
            throw new MALException("Internal error encoding message for"
                    + " message with header:\n" + header.toString(), ex2);
        }
    }

    private MessageBody createMessageBody(final MALElementStreamFactory encFactory,
            final ByteArrayInputStream encBodyBytes, final MALElementInputStream encBodyElements) {
        MALEncodingContext ctx = new MALEncodingContext(header);

        if (header.getIsErrorMessage()) {
            return new ErrorBody(ctx, wrapBodyParts,
                    encFactory, encBodyBytes, encBodyElements);
        }

        if (InteractionType._PUBSUB_INDEX == header.getInteractionType().getOrdinal()) {
            final short stage = header.getInteractionStage().getValue();
            switch (stage) {
                case MALPubSubOperation._REGISTER_STAGE:
                    return new RegisterBody(ctx, wrapBodyParts,
                            encFactory, encBodyBytes, encBodyElements);
                case MALPubSubOperation._PUBLISH_REGISTER_STAGE:
                    return new PublishRegisterBody(ctx, wrapBodyParts,
                            encFactory, encBodyBytes,
                            encBodyElements);
                case MALPubSubOperation._PUBLISH_STAGE:
                    return new PublishBody(ctx, wrapBodyParts,
                            encFactory, encBodyBytes, encBodyElements);
                case MALPubSubOperation._NOTIFY_STAGE:
                    return new NotifyBody(ctx, wrapBodyParts,
                            encFactory, encBodyBytes, encBodyElements);
                case MALPubSubOperation._DEREGISTER_STAGE:
                    return new DeregisterBody(ctx, wrapBodyParts,
                            encFactory, encBodyBytes, encBodyElements);
                default:
                    return new MessageBody(ctx, wrapBodyParts,
                            encFactory, encBodyBytes, encBodyElements);
            }
        }

        return new MessageBody(ctx, wrapBodyParts,
                encFactory, encBodyBytes, encBodyElements);
    }

    private MessageBody createMessageBody(final MALElementStreamFactory encFactory, final Object[] bodyElements) {
        MALEncodingContext ctx = new MALEncodingContext(header);

        if (header.getIsErrorMessage()) {
            return new ErrorBody(ctx, encFactory, bodyElements);
        }

        if (InteractionType._PUBSUB_INDEX == header.getInteractionType().getOrdinal()) {
            final short stage = header.getInteractionStage().getValue();
            switch (stage) {
                case MALPubSubOperation._REGISTER_STAGE:
                    return new RegisterBody(ctx, encFactory, bodyElements);
                case MALPubSubOperation._PUBLISH_REGISTER_STAGE:
                    return new PublishRegisterBody(ctx, encFactory, bodyElements);
                case MALPubSubOperation._PUBLISH_STAGE:
                    return new PublishBody(ctx, encFactory, bodyElements);
                case MALPubSubOperation._NOTIFY_STAGE:
                    return new NotifyBody(ctx, encFactory, bodyElements);
                case MALPubSubOperation._DEREGISTER_STAGE:
                    return new DeregisterBody(ctx, encFactory, bodyElements);
                default:
                    return new MessageBody(ctx, encFactory, bodyElements);
            }
        }

        return new MessageBody(ctx, encFactory, bodyElements);
    }
}
