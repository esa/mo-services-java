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
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Extends the MALElementInputStream interface to enable aware transport access
 * to the encoded data stream.
 */
public abstract class ElementInputStream implements MALElementInputStream {

    protected final MALDecoder dec;

    /**
     * Sub class constructor.
     *
     * @param pdec Decoder to use.
     */
    protected ElementInputStream(MALDecoder pdec) {
        this.dec = pdec;
    }

    @Override
    public MALMessageHeader readHeader(final MALMessageHeader header) throws MALException {
        return header.decode(dec);
    }

    @Override
    public Element readElement(final Element element, final OperationField field)
            throws IllegalArgumentException, MALException {
        if (field == null) {
            return dec.decodeNullableElement(element);
        }

        try {
            if (field.isAbstractType()) {
                return decodeAbstractSubElement(field.isNullable());
            } else {
                if (field.isNullable()) {
                    return dec.decodeNullableElement(element);
                } else {
                    return dec.decodeElement(element);
                }
            }
        } catch (MALException ex) {
            Logger.getLogger(ElementOutputStream.class.getName()).log(Level.SEVERE,
                    "The following field could not be decoded: " + field.getFieldName(), ex);
            throw ex;
        }
    }

    @Override
    public void close() throws MALException {
        // Nothing to do for this decoder
    }

    protected Element decodeAbstractSubElement(boolean isNullable) throws MALException {
        Long shortForm = dec.decodeAbstractElementSFP(isNullable);

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
