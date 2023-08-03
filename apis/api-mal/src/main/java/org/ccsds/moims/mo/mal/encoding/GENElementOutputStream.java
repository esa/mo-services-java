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
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

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
    public void writeHeader(final MALMessageHeader header) throws MALException {
        if (enc == null) {
            this.enc = createEncoder(dos);
        }

        header.encode(enc);
    }

    @Override
    public void writeElement(final Element element, final MALEncodingContext ctx) throws MALException {
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
                encodeAbstractSubElement((Element) element, true);
            }
            return;
        }

        if (element == null) {
            enc.encodeNullableElement(null);
            return;
        }

        OperationField field = ctx.getOperationField();

        if (field.isAbstractType()) {
            encodeAbstractSubElement(element, true);
        } else {
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

    protected void encodeAbstractSubElement(final Element element, boolean isNullable) throws MALException {
        if (element != null) {
            enc.encodeAbstractElementSFP(element.getShortForm(), isNullable);
            enc.encodeElement(element);
        } else {
            enc.encodeAbstractElementSFP(null, isNullable);
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
