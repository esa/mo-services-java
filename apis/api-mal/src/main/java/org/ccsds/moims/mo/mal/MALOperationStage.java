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

    private final OperationField[] fields;

    /**
     * Constructs an operation stage using the supplied arguments.
     *
     * @param stageNumber Number of the interaction stage.
     * @param fields The fields of this operation stage.
     * @throws java.lang.IllegalArgumentException If any of the arguments are
     * null.
     */
    public MALOperationStage(final UOctet stageNumber, final OperationField[] fields) {
        if (stageNumber == null) {
            throw new IllegalArgumentException("stageNumber argument cannot be NULL");
        }
        if (fields == null) {
            throw new IllegalArgumentException("fields argument cannot be NULL");
        }
        this.stageNumber = stageNumber;
        this.fields = fields;
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
     * Returns the fields for this operation stage.
     *
     * @return The fields for this operation stage.
     */
    public OperationField[] getFields() {
        return fields;
    }
}
