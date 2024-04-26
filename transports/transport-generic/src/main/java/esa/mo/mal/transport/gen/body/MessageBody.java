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
package esa.mo.mal.transport.gen.body;

import esa.mo.mal.transport.gen.Transport;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.encoding.ElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.encoding.MALEncodingContext;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALElementsRegistry;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.NotFoundException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ElementList;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;
import org.ccsds.moims.mo.mal.transport.MALEncodedBody;
import org.ccsds.moims.mo.mal.transport.MALEncodedElement;
import org.ccsds.moims.mo.mal.transport.MALEncodedElementList;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.TypeId;

/**
 * Implementation of the MALMessageBody interface.
 */
public class MessageBody implements MALMessageBody, java.io.Serializable {

    /**
     * Factory used to create encoders/decoders.
     */
    protected MALElementStreamFactory encFactory;
    /**
     * Input ByteArray that holds the encoded message body parts.
     */
    protected ByteArrayInputStream encBodyBytes;
    /**
     * Input stream that holds the encoded message body parts.
     */
    protected MALElementInputStream encBodyElements;
    /**
     * True if we have already decoded the body.
     */
    protected boolean decodedBody = false;
    protected final MALEncodingContext ctx;
    /**
     * Number of body parts.
     */
    protected int bodyPartCount;
    /**
     * The decoded body parts.
     */
    protected Object[] messageParts;
    private final boolean wrappedBodyParts;
    private static final long serialVersionUID = 222222222222223L;

    /**
     * Constructor.
     *
     * @param ctx The encoding context to use.
     * @param encFactory The encoder stream factory to use.
     * @param messageParts The message body parts.
     */
    public MessageBody(final MALEncodingContext ctx,
            final MALElementStreamFactory encFactory,
            final Object[] messageParts) {
        this.ctx = ctx;
        wrappedBodyParts = false;
        this.bodyPartCount = (messageParts != null) ? messageParts.length : 0;
        this.messageParts = messageParts;
        this.encFactory = encFactory;
        decodedBody = true;
    }

    /**
     * Constructor.
     *
     * @param ctx The encoding context to use.
     * @param wrappedBodyParts True if the encoded body parts are wrapped in
     * BLOBs.
     * @param encFactory The encoder stream factory to use.
     * @param encBodyBytes The encoder body bytes.
     * @param encBodyElements The input stream that holds the encoded body
     * parts.
     */
    public MessageBody(final MALEncodingContext ctx,
            final boolean wrappedBodyParts,
            final MALElementStreamFactory encFactory,
            final ByteArrayInputStream encBodyBytes,
            final MALElementInputStream encBodyElements) {
        this.ctx = ctx;
        this.wrappedBodyParts = wrappedBodyParts;
        this.encFactory = encFactory;
        this.encBodyBytes = encBodyBytes;
        this.encBodyElements = encBodyElements;
    }

    @Override
    public int getElementCount() {
        try {
            decodeMessageBody();
        } catch (MALException ex) {
            Logger.getLogger(MessageBody.class.getName()).log(Level.SEVERE,
                    "MAL encoded body encoding error", ex);
        }

        return bodyPartCount;
    }

    @Override
    public Object getBodyElement(final int index, final Object element)
            throws IllegalArgumentException, MALException {
        decodeMessageBody();

        Object bodyPart = messageParts[index];

        // Downcast the List to the provided (specialized) type if
        // it is a Heterogeneous list! Downcasting...
        if (element != null && bodyPart instanceof HeterogeneousList) {
            for (Element entry : (HeterogeneousList) bodyPart) {
                ((HeterogeneousList) element).add(entry);
            }
            return element;
        }

        if (bodyPart instanceof MALEncodedElementList) {
            System.out.println("getBodyElement requesting encoded body list: "
                    + this.decodedBody + " : " + ((MALEncodedElementList) bodyPart).getShortForm());
            bodyPart = decodeEncodedElementListBodyPart(((MALEncodedElementList) bodyPart));
            messageParts[index] = bodyPart;
        }
        if (bodyPart instanceof MALEncodedElement) {
            System.out.println("getBodyElement requesting encoded body: " + this.decodedBody);
        }

        return bodyPart;
    }

    @Override
    public MALEncodedElement getEncodedBodyElement(final int index) throws MALException {
        if (index != -1) {
            return null;
        }

        // want the complete message body
        return new MALEncodedElement((Blob) encBodyElements.readElement(new Blob(), null));
    }

    /**
     * Encodes the contents of the message body into the provided stream
     *
     * @param streamFactory The stream factory to use for encoder creation.
     * @param enc The output stream to use for encoding.
     * @param lowLevelOutputStream Low level output stream to use when have an
     * already encoded body.
     * @param ctx The encoding context.
     * @throws MALException On encoding error.
     */
    public void encodeMessageBody(final MALElementStreamFactory streamFactory,
            final MALElementOutputStream enc,
            final OutputStream lowLevelOutputStream,
            final MALEncodingContext ctx) throws MALException {
        // first check to see if we have an already encoded body
        if ((messageParts != null) && (messageParts.length == 1)
                && (messageParts[0] instanceof MALEncodedBody)) {
            enc.flush();

            try {
                lowLevelOutputStream.write(((MALEncodedBody) messageParts[0]).getEncodedBody().getValue());
                lowLevelOutputStream.flush();
            } catch (IOException ex) {
                throw new MALException("MAL encoded body encoding error", ex);
            }
        } else if (!decodedBody) {
            enc.flush();
        } else {
            final int count = getElementCount();

            MALElementOutputStream benc = enc;
            ByteArrayOutputStream bbaos = null;

            if (wrappedBodyParts) {
                // we have more than one body part, therefore encode each part 
                // into a separate byte buffer, and then encode that byte buffer 
                // as a whole. This allows use to be able to return the complete 
                // body of the message as a single unit if required.
                bbaos = new ByteArrayOutputStream();
                benc = streamFactory.createOutputStream(bbaos);
            }

            try {
                OperationField[] fields = ctx.getOperationFields();
                for (int i = 0; i < count; i++) {
                    encodeBodyPart(streamFactory, benc, wrappedBodyParts,
                            getBodyElement(i, null), fields[i]);
                }
            } catch (NotFoundException ex) {
                Logger.getLogger(MessageBody.class.getName()).log(Level.SEVERE,
                        "The Operation fields could not be found!", ex);
            }

            if (wrappedBodyParts) {
                benc.flush();
                benc.close();
                enc.writeElement(new Blob(bbaos.toByteArray()), null);
            }
        }

        enc.flush();
        enc.close();
    }

    protected void encodeBodyPart(final MALElementStreamFactory streamFactory,
            final MALElementOutputStream enc, final boolean wrapBodyParts,
            final Object obj, final OperationField field) throws MALException {
        // Attempt to convert if not Element
        Object o = (obj instanceof Element) ? obj : Attribute.javaType2Attribute(obj);

        // if it is already an encoded element then just write it directly
        if (o instanceof MALEncodedElement) {
            enc.writeElement(((MALEncodedElement) o).getEncodedElement(), field);
        } // else if it is a MAL data type object
        else if ((o == null) || (o instanceof Element)) {
            MALElementOutputStream lenc = enc;
            ByteArrayOutputStream lbaos = null;

            if (wrapBodyParts) {
                // we encode it into a byte buffer so that it can be extracted as a MALEncodedElement if required
                lbaos = new ByteArrayOutputStream();
                lenc = streamFactory.createOutputStream(lbaos);
            }

            // now encode the element
            lenc.writeElement((Element) o, field);

            if (wrapBodyParts) {
                lenc.flush();
                lenc.close();

                // write the encoded blob to the stream
                enc.writeElement(new Blob(lbaos.toByteArray()), null);
            }
        } else {
            throw new MALException("Unable to encode body object of type: "
                    + o.getClass().getSimpleName());
        }
    }

    /**
     * Decodes the message body.
     *
     * @throws MALException if any error detected.
     */
    protected void decodeMessageBody() throws MALException {
        if (decodedBody) {
            return;
        }

        decodedBody = true;

        try {
            OperationField[] fields = ctx.getOperationFields();
            bodyPartCount = fields.length;
            messageParts = new Object[bodyPartCount];

            MALElementInputStream benc = encBodyElements;
            if (wrappedBodyParts) {
                Transport.LOGGER.fine("GEN Message decoding body wrapper");
                final Blob body = (Blob) encBodyElements.readElement(new Blob(), null);
                final ByteArrayInputStream bais = new ByteArrayInputStream(body.getValue());
                benc = encFactory.createInputStream(bais);
            }

            // Iterate through each message part and decode it
            for (int i = 0; i < bodyPartCount; i++) {
                Object sf = fields[i].getTypeId();

                try {
                    messageParts[i] = decodeBodyPart(benc, ctx.getOperationFields()[i], sf);
                } catch (Exception ex) {
                    String typeStr = "";

                    if (sf != null) {
                        TypeId typeId = new TypeId((Long) sf);
                        typeStr = "' and typeId:'" + typeId.toString();
                    }

                    Logger.getLogger(MessageBody.class.getName()).log(Level.SEVERE,
                            "Error decoding Body part with fieldName:'"
                            + fields[i].getFieldName() + typeStr
                            + "' and with index: " + i, ex);
                    throw ex;
                }
            }
        } catch (MALException ex) {
            Transport.LOGGER.log(Level.WARNING,
                    "(1) Unable to decode the Message Body!", ex);
            throw ex;
        } catch (NotFoundException ex) {
            Transport.LOGGER.log(Level.WARNING,
                    "(2) Unable to decode the Message Body!", ex);
        }
    }

    /**
     * Decodes a single part of the message body.
     *
     * @param meel The encoded element list.
     * @return The decoded chunk.
     * @throws MALException if any error detected.
     */
    protected Object decodeEncodedElementListBodyPart(MALEncodedElementList meel) throws MALException {
        long sf = (Long) meel.getShortForm();

        // create list of correct type
        long lsf = (-((sf) & 0xFFFFFFL)) & 0xFFFFFFL + (sf & 0xFFFFFFFFFF000000L);
        MALElementsRegistry fr = MALContextFactory.getElementsRegistry();
        ElementList elementList;
        try {
            elementList = (ElementList) fr.createElement(lsf);
        } catch (Exception ex) {
            throw new MALException("The ElementList could not be created!", ex);
        }

        for (MALEncodedElement ele : meel) {
            final ByteArrayInputStream lbais
                    = new ByteArrayInputStream(ele.getEncodedElement().getValue());
            MALElementInputStream lenc = encFactory.createInputStream(lbais);

            try {
                elementList.add(lenc.readElement(fr.createElement(sf), ctx.getOperationFields()[0]));
            } catch (Exception ex) {
                throw new MALException("The Element could not be created!", ex);
            }
        }

        return elementList;
    }

    /**
     * Decodes a single part of the message body.
     *
     * @param decoder The decoder to use.
     * @param field The field information to encode.
     * @param sf The type short form.
     * @return The decoded chunk.
     * @throws MALException if any error detected.
     */
    protected Object decodeBodyPart(final MALElementInputStream decoder,
            OperationField field, Object sf) throws MALException {
        MALElementInputStream lenc = decoder;

        if (wrappedBodyParts) {
            final Blob ele = (Blob) decoder.readElement(new Blob(), null);
            final ByteArrayInputStream lbais = new ByteArrayInputStream(ele.getValue());
            lenc = encFactory.createInputStream(lbais);
        }

        // work out whether it is a MAL element or JAXB element we have received
        if (sf instanceof String) {
            throw new MALException("Marshalling and unmarshalling of "
                    + "JAXB elements is no longer supported!");
        }

        Element element = null;

        // It is not an abstract element:
        if (sf != null) {
            Long shortForm = (Long) sf;
            Transport.LOGGER.log(Level.FINER,
                    "GEN Message decoding body part : Type = {0}", shortForm);

            try {
                element = MALContextFactory.getElementsRegistry().createElement(shortForm);
            } catch (Exception ex) {
                throw new MALException("The Element could not be created!", ex);
            }
        }

        return lenc.readElement(element, field);
    }
}
