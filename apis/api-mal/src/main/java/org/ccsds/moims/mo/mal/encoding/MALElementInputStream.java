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

import org.ccsds.moims.mo.mal.MALException;

/**
 * The MALElementInputStream interface is used to decode Elements.
 */
public interface MALElementInputStream {

    /**
     * The method reads the header from the input stream.
     *
     * @param header The header to decode.
     * @return The decoded header.
     * @throws java.lang.IllegalArgumentException If there is something wrong
     * with the provided header.
     * @throws MALException If the MALElementInputStream is closed
     */
    Object readHeader(Object header) throws java.lang.IllegalArgumentException, MALException;

    /**
     * The method reads an Element from the stream.
     *
     * @param element Element to decode, may be null.
     * @param ctx MALEncodingContext to be used in order to decode an Element
     * @return The decoded element.
     * @throws java.lang.IllegalArgumentException If the parameter ‘ctx’ is NULL
     * @throws MALException If the MALElementInputStream is closed
     */
    Object readElement(Object element, MALEncodingContext ctx)
            throws java.lang.IllegalArgumentException, MALException;

    /**
     * Closes the stream.
     *
     * @throws MALException If an internal error occurs
     */
    void close() throws MALException;
}
