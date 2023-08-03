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

import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

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
    public MALMessageHeader readHeader(final MALMessageHeader header) throws MALException {
        return header.decode(dec);
    }

    @Override
    public Element readElement(final Element element, final MALEncodingContext ctx)
            throws IllegalArgumentException, MALException {
        if (ctx == null) {
            return dec.decodeNullableElement(element);
        }

        if (element == ctx.getHeader()) {
            throw new MALException("The header is no longer read here! Use: readHeader()");
        }

        if (ctx.getHeader().getIsErrorMessage()) {
            // error messages have a standard format
            if (ctx.getBodyElementIndex() == 0) {
                return dec.decodeUInteger();
            }

            return decodeAbstractSubElement(true);
        }

        if (element == null) {
            return decodeAbstractSubElement(true);
        } else {
            return dec.decodeNullableElement(element);
        }
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
