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
package org.ccsds.moims.mo.mal.structures;

import java.io.Serializable;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;

/**
 * The Element interface represents the MAL Element type.
 */
public interface Element extends Serializable {

    /**
     * Return the Type Id of the element.
     *
     * @return the TypeId.
     */
    TypeId getTypeId();

    /**
     * The method allows the creation of an element in a generic way, i.e.,
     * using the MAL Element polymorphism.
     *
     * @return A new instance.
     */
    Element createElement();

    /**
     * Encodes this element using the supplied encoder.
     *
     * @param encoder The encoder to use, must not be null.
     * @throws MALException If an error occurs
     */
    void encode(MALEncoder encoder) throws MALException;

    /**
     * Decodes an instance of this element type using the supplied decoder.
     *
     * @param decoder The decoder to use, must not be null.
     * @return the decoded instance, may be not the same instance as this
     * Element.
     * @throws MALException If an error occurs
     */
    Element decode(MALDecoder decoder) throws MALException;
}
