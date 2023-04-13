/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java API
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
package org.ccsds.moims.mo.mal.encoding;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALOperationStage;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;

/**
 * Extends the MALElementInputStream interface to enable aware transport access
 * to the encoded data stream.
 */
public abstract class GENElementInputStream implements MALElementInputStream {

    protected final Decoder dec;

    /**
     * Sub class constructor.
     *
     * @param pdec Decoder to use.
     */
    protected GENElementInputStream(Decoder pdec) {
        this.dec = pdec;
    }

    @Override
    public Object readElement(final Object element, final MALEncodingContext ctx)
            throws IllegalArgumentException, MALException {
        if (element == ctx.getHeader()) {
            return dec.decodeElement((Element) element);
        }

        if (ctx.getHeader().getIsErrorMessage()) {
            // error messages have a standard format
            if (ctx.getBodyElementIndex() == 0) {
                return dec.decodeUInteger();
            }

            return decodeAbstractSubElement();
        }

        if (InteractionType._PUBSUB_INDEX == ctx.getHeader().getInteractionType().getOrdinal()) {
            /*
            // In theory, we should not have to hardcode the decoding part
            // because it is alread properly defined in the MALPubSubOperation
            // This code need to be tested and upgraded in the future to something like:
            MALPubSubOperation operation = (MALPubSubOperation) ctx.getOperation();
            MALOperationStage stage = operation.getOperationStage(ctx.getHeader().getInteractionStage());
            int idx = ctx.getBodyElementIndex();
            return dec.decodeElement((Element) stage.getElementShortForms()[idx]);
             */
            int index = ctx.getBodyElementIndex();

            switch (ctx.getHeader().getInteractionStage().getValue()) {
                case MALPubSubOperation._REGISTER_STAGE:
                    return dec.decodeElement(new Subscription());
                case MALPubSubOperation._PUBLISH_REGISTER_STAGE:
                    return dec.decodeElement(new IdentifierList());
                case MALPubSubOperation._DEREGISTER_STAGE:
                    return dec.decodeElement(new IdentifierList());
                case MALPubSubOperation._PUBLISH_STAGE: {
                    if (index == 0) {
                        return dec.decodeElement(new UpdateHeader());
                    } else {
                        return decodePublishNotifyMessages(ctx);
                    }
                }
                case MALPubSubOperation._NOTIFY_STAGE: {
                    if (index == 0) {
                        return dec.decodeIdentifier();
                    } else if (index == 1) {
                        return dec.decodeElement(new UpdateHeader());
                    } else {
                        return decodePublishNotifyMessages(ctx);
                    }
                }
                default:
                    return decodeAbstractSubElement();
            }
        }

        if (element == null) {
            return decodeAbstractSubElement();
        } else {
            return dec.decodeNullableElement((Element) element);
        }
    }

    private Object decodePublishNotifyMessages(final MALEncodingContext ctx) throws MALException {
        UOctet stage = ctx.getHeader().getInteractionStage();
        MALOperationStage op = ctx.getOperation().getOperationStage(stage);
        Object sf = op.getElementShortForms()[ctx.getBodyElementIndex()];

        // element is defined as an abstract type
        if (sf == null) {
            sf = dec.decodeAbstractElementSFP(true);

            if (sf == null) {
                return null;
            }
        }

        try {
            Element e = MALContextFactory.getElementsRegistry().createElement((Long) sf);
            return dec.decodeNullableElement(e);
        } catch (Exception ex) {
            Logger.getLogger(GENElementInputStream.class.getName()).log(Level.SEVERE,
                    "The Element could not be created or decoded!", ex);
        }
        return null;
    }

    /**
     * Returns a new byte array containing the remaining encoded data for this
     * stream. Expected to be used for creating an MAL encoded body object.
     *
     * @return a byte array containing the remaining encoded data for this
     * stream.
     * @throws MALException On error.
     */
    public byte[] getRemainingEncodedData() throws MALException {
        return dec.getRemainingEncodedData();
    }

    @Override
    public void close() throws MALException {
        // Nothing to do for this decoder
    }

    protected Object decodeAbstractSubElement() throws MALException {
        Long shortForm = dec.decodeAbstractElementSFP(true);

        if (shortForm == null) {
            return null;
        }

        try {
            Element e = MALContextFactory.getElementsRegistry().createElement(shortForm);
            try {
                return dec.decodeElement(e);
            } catch (Exception ex) {
                throw new MALException("Unable to decode element: " + e.toString(), ex);
            }
        } catch (Exception ex) {
            throw new MALException("Unable to create element for short form part: " + shortForm, ex);
        }
    }
}
