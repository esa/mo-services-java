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

import java.io.IOException;
import java.io.OutputStream;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALOperationStage;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.UOctet;

/**
 * Extends the MALElementOutputStream interface to enable aware transport access
 * to the encoded data stream.
 */
public abstract class GENElementOutputStream implements MALElementOutputStream {

    protected final OutputStream dos;
    protected Encoder enc = null;

    /**
     * Constructor.
     *
     * @param os Output stream to write to.
     */
    protected GENElementOutputStream(final OutputStream os) {
        this.dos = os;
    }

    @Override
    public void writeHeader(final Object header, final MALEncodingContext ctx) throws MALException {
        if (enc == null) {
            this.enc = createEncoder(dos);
        }

        ((Element) header).encode(enc);
    }

    @Override
    public void writeElement(final Object element, final MALEncodingContext ctx) throws MALException {
        if (enc == null) {
            this.enc = createEncoder(dos);
        }

        if (element == ctx.getHeader()) {
            throw new MALException("The header is no longer read here! Use: writeHeader()");
        }

        if (ctx.getHeader().getIsErrorMessage()) {
            // error messages have a standard format
            if (ctx.getBodyElementIndex() == 0) {
                ((Element) element).encode(enc);
            } else {
                encodeAbstractSubElement((Element) element);
            }
            return;
        }

        if (InteractionType._PUBSUB_INDEX == ctx.getHeader().getInteractionType().getOrdinal()) {
            int index = ctx.getBodyElementIndex();

            switch (ctx.getHeader().getInteractionStage().getValue()) {
                case MALPubSubOperation._REGISTER_STAGE:
                case MALPubSubOperation._PUBLISH_REGISTER_STAGE:
                case MALPubSubOperation._DEREGISTER_STAGE:
                    ((Element) element).encode(enc);
                    return;
                case MALPubSubOperation._PUBLISH_STAGE:
                    if (index == 0) {
                        ((Element) element).encode(enc);
                    } else {
                        encodePublishNotifyMessages((Element) element, ctx);
                    }
                    return;
                case MALPubSubOperation._NOTIFY_STAGE:
                    if (index == 0) {
                        ((Element) element).encode(enc);
                    } else if (index == 1) {
                        ((Element) element).encode(enc);
                    } else {
                        encodePublishNotifyMessages((Element) element, ctx);
                    }
                    return;
                default:
                    // This should never happen...
                    encodeAbstractSubElement((Element) element);
                    return;
            }
        }

        if (element == null) {
            enc.encodeNullableElement(null);
            return;
        }

        if (element instanceof Element) {
            // encode the short form if it is not fixed in the operation
            final Element e = (Element) element;

            UOctet stage = ctx.getHeader().getInteractionStage();
            Object sf = ctx.getOperation()
                    .getOperationStage(stage)
                    .getElementShortForms()[ctx.getBodyElementIndex()];

            if (sf == null) {
                encodeAbstractSubElement(e);
            } else {
                enc.encodeNullableElement(e);
            }
        }
    }

    private void encodePublishNotifyMessages(final Element element,
            final MALEncodingContext ctx) throws MALException {
        UOctet stage = ctx.getHeader().getInteractionStage();
        MALOperationStage op = ctx.getOperation().getOperationStage(stage);
        Object sf = op.getElementShortForms()[ctx.getBodyElementIndex()];

        // Is it encoding an abstract element?
        if (sf == null) {
            if (element != null) {
                enc.encodeAbstractElementSFP(element.getShortForm(), true);
                enc.encodeNullableElement(element);
            } else {
                // If the element is null
                enc.encodeAbstractElementSFP(null, true);
            }
        } else {
            // Not abstract type:
            enc.encodeNullableElement(element);
        }
    }

    @Override
    public void flush() throws MALException {
        try {
            dos.flush();
        } catch (IOException ex) {
            throw new MALException("IO exception flushing Element stream", ex);
        }
    }

    @Override
    public void close() throws MALException {
        try {
            dos.close();
            if (enc != null) {
                enc.close();
            }
        } catch (IOException ex) {
            throw new MALException(ex.getLocalizedMessage(), ex);
        }
    }

    protected void encodeAbstractSubElement(final Element element) throws MALException {
        if (element != null) {
            enc.encodeAbstractElementSFP(element.getShortForm(), true);
            enc.encodeElement(element);
        } else {
            enc.encodeAbstractElementSFP(null, true);
        }
    }

    /**
     * Over-ridable factory function.
     *
     * @param os Output stream to wrap.
     * @return the new encoder.
     */
    protected abstract Encoder createEncoder(OutputStream os);
}
