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
 * The MALElementOutputStream interface is used to encode Elements.
 */
public interface MALElementOutputStream {

    /**
     * The method encodes an Element.
     *
     * @param element Element to encode, may be null.
     * @param ctx MALEncodingContext to be used in order to encode an Element
     * @throws java.lang.IllegalArgumentException If the parameter ‘ctx’ is NULL
     * @throws MALException If the MALElementOutputStream is closed
     */
    void writeElement(Object element, MALEncodingContext ctx) throws IllegalArgumentException, MALException;

    /**
     * Flushes the stream.
     *
     * @throws MALException If the MALElementOutputStream is closed
     */
    void flush() throws MALException;

    /**
     * Closes the stream.
     *
     * @throws MALException If an internal error occurs
     */
    void close() throws MALException;
}
