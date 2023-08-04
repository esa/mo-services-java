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

import org.ccsds.moims.mo.mal.encoding.GENElementInputStream;
import esa.mo.mal.transport.gen.GENTransport;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALElementsRegistry;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.encoding.MALEncodingContext;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ElementList;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.transport.MALEncodedBody;
import org.ccsds.moims.mo.mal.transport.MALEncodedElement;
import org.ccsds.moims.mo.mal.transport.MALEncodedElementList;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;

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
    public MALEncodedBody getEncodedBody() throws MALException {
        if (!decodedBody && (encBodyElements instanceof GENElementInputStream)) {
            byte[] rd = ((GENElementInputStream) encBodyElements).getRemainingEncodedData();

            if ((encBodyBytes != null) && (encBodyBytes.available() > 0)) {
                byte[] c = new byte[rd.length + encBodyBytes.available()];
                System.arraycopy(rd, 0, c, 0, rd.length);
                encBodyBytes.mark(0);
                encBodyBytes.read(c, rd.length, encBodyBytes.available());
                encBodyBytes.reset();

                rd = c;
            }

            return new MALEncodedBody(new Blob(rd));
        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Override
    public Object getBodyElement(final int index, final Object element)
            throws IllegalArgumentException, MALException {
        decodeMessageBody();

        Object bodyPart = messageParts[index];

        // Up-cast the List if it is a heterogeneous list!
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
     * @param stage The operation stage being encoded.
     * @param ctx The encoding context.
     * @throws MALException On encoding error.
     */
    public void encodeMessageBody(final MALElementStreamFactory streamFactory,
            final MALElementOutputStream enc,
            final OutputStream lowLevelOutputStream,
            final UOctet stage,
            final MALEncodingContext ctx) throws MALException {
        // first check to see if we have an already encoded body
        if ((messageParts != null)
                && (1 == messageParts.length)
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

            try {
                lowLevelOutputStream.write(getEncodedBody().getEncodedBody().getValue());
                lowLevelOutputStream.flush();
            } catch (IOException ex) {
                throw new MALException("MAL encoded body encoding error", ex);
            }
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

            for (int i = 0; i < count; i++) {
                if (ctx != null) {
                    ctx.setBodyElementIndex(i);
                }
                encodeBodyPart(streamFactory, benc, wrappedBodyParts,
                        getBodyElement(i, null), ctx);
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
            final Object o, final MALEncodingContext ctx) throws MALException {
        // if it is already an encoded element then just write it directly
        if (o instanceof MALEncodedElement) {
            enc.writeElement(((MALEncodedElement) o).getEncodedElement(), ctx);
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
            lenc.writeElement((Element) o, ctx);

            if (wrapBodyParts) {
                lenc.flush();
                lenc.close();

                // write the encoded blob to the stream
                enc.writeElement(new Blob(lbaos.toByteArray()), null);
            }
        } else {
            throw new MALException(
                    "ERROR: Unable to encode body object of type: "
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
            UOctet interactionStage = ctx.getHeader().getInteractionStage();
            OperationField[] fields = ctx.getOperation().getOperationStage(interactionStage).getFields();

            bodyPartCount = (ctx.getHeader().getIsErrorMessage()) ? 2 : fields.length;
            messageParts = new Object[bodyPartCount];

            MALElementInputStream benc = encBodyElements;
            if (wrappedBodyParts) {
                GENTransport.LOGGER.fine("GEN Message decoding body wrapper");
                final Blob body = (Blob) encBodyElements.readElement(new Blob(), null);
                final ByteArrayInputStream bais = new ByteArrayInputStream(body.getValue());
                benc = encFactory.createInputStream(bais);
            }

            // Iterate through each message part and decode it
            for (int i = 0; i < bodyPartCount; i++) {
                ctx.setBodyElementIndex(i);
                Object sf = null;

                if (!ctx.getHeader().getIsErrorMessage()) {
                    sf = fields[i].getTypeId();
                }

                try {
                    messageParts[i] = decodeBodyPart(benc, ctx, sf);
                } catch (Exception ex) {
                    TypeId typeId = new TypeId((Long) sf);
                    Logger.getLogger(MessageBody.class.getName()).log(Level.SEVERE,
                            "Error decoding Body part (with typeId: "
                            + typeId.toString()
                            + ") with index: " + i, ex);
                    throw ex;
                }
            }
        } catch (MALException ex) {
            GENTransport.LOGGER.log(Level.WARNING,
                    "Unable to decode the Message Body!", ex);
            throw ex;
        }
    }

    /**
     * Decodes a single part of the message body.
     *
     * @param meel The encoded element list.
     * @return The decoded chunk.
     * @throws MALException if any error detected.
     */
    protected Object decodeEncodedElementListBodyPart(
            final MALEncodedElementList meel) throws MALException {
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
                elementList.add(lenc.readElement(fr.createElement(sf), ctx));
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
     * @param ctx The encoding context to use.
     * @param sf The type short form.
     * @return The decoded chunk.
     * @throws MALException if any error detected.
     */
    protected Object decodeBodyPart(final MALElementInputStream decoder,
            MALEncodingContext ctx, Object sf) throws MALException {
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
            GENTransport.LOGGER.log(Level.FINER,
                    "GEN Message decoding body part : Type = {0}", shortForm);

            try {
                element = MALContextFactory.getElementsRegistry().createElement(shortForm);
            } catch (Exception ex) {
                throw new MALException("The Element could not be created!", ex);
            }
        }

        return lenc.readElement(element, ctx);
    }
}
