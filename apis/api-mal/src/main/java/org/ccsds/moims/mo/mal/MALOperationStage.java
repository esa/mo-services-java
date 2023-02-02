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
package org.ccsds.moims.mo.mal;

import org.ccsds.moims.mo.mal.structures.UOctet;

/**
 * The MALOperationStage class represents the element types used by an operation
 * during a stage.
 */
public class MALOperationStage {

    private final UOctet number;
    private final Object[] elementShortForms;
    private final Object[] lastElementShortForms;

    /**
     * Constructs an operation stage using the supplied arguments.
     *
     * @param number Number of the interaction stage.
     * @param elementShortForms Short forms of the body element types.
     * @param lastElementShortForms Short forms of the types that can be used
     * for the last element in case of polymorphism.
     * @throws java.lang.IllegalArgumentException If any of the arguments are
     * null.
     */
    public MALOperationStage(final UOctet number,
            final Object[] elementShortForms,
            final Object[] lastElementShortForms) throws java.lang.IllegalArgumentException {
        if (number == null) {
            throw new IllegalArgumentException("Number argument must not be NULL");
        }
        if (elementShortForms == null) {
            throw new IllegalArgumentException("Element short forms argument must not be NULL");
        }
        if (lastElementShortForms == null) {
            throw new IllegalArgumentException("Last element short forms argument must not be NULL");
        }
        this.number = number;
        this.elementShortForms = elementShortForms;
        this.lastElementShortForms = lastElementShortForms;
    }

    /**
     * Returns the stage number.
     *
     * @return the stage number.
     */
    public UOctet getNumber() {
        return number;
    }

    /**
     * Returns the contained element short forms.
     *
     * @return The element short forms.
     */
    public Object[] getElementShortForms() {
        // returns the internal reference for performance reasons
        return elementShortForms;
    }

    /**
     * Returns the last element short forms.
     *
     * @return The last element short forms.
     */
    public Object[] getLastElementShortForms() {
        // returns the internal reference for performance reasons
        return lastElementShortForms;
    }
}
