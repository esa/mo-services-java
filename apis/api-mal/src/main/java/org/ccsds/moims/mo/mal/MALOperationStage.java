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

    private final UOctet stageNumber;

    // Should be Long for normal MAL types or String for XML types:
    private final Object[] elementShortForms;

    /**
     * Constructs an operation stage using the supplied arguments.
     *
     * @param stageNumber Number of the interaction stage.
     * @param elementShortForms Short forms of the body element types.
     * @throws java.lang.IllegalArgumentException If any of the arguments are
     * null.
     */
    public MALOperationStage(final UOctet stageNumber,
            final Object[] elementShortForms) throws java.lang.IllegalArgumentException {
        if (stageNumber == null) {
            throw new IllegalArgumentException("Number argument must not be NULL");
        }
        if (elementShortForms == null) {
            throw new IllegalArgumentException("Element short forms argument must not be NULL");
        }
        this.stageNumber = stageNumber;
        this.elementShortForms = elementShortForms;
    }

    /**
     * Returns the stage number.
     *
     * @return the stage number.
     */
    public UOctet getNumber() {
        return stageNumber;
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
}
