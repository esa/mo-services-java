/* ----------------------------------------------------------------------------
 * Copyright (C) 2023      European Space Agency
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

/**
 * Class that represents a field in a MO Operation.
 */
public final class OperationField {

    private final String fieldName;
    private final boolean nullable;

    // Should be Long for normal MAL types or String for XML types:
    private final Object typeId;

    public OperationField(String fieldName, boolean nullable, Object typeId) {
        this.fieldName = fieldName;
        this.nullable = nullable;
        this.typeId = typeId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public boolean isNullable() {
        return nullable;
    }

    public Object getTypeId() {
        return typeId;
    }

    public boolean isAbstractType() {
        return (typeId == null);
    }
}
