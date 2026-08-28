/* ----------------------------------------------------------------------------
 * Copyright (C) 2026      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO API Generator
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
package esa.mo.apigen.model;

import esa.mo.apigen.model.types.TypeRef;

/**
 * An operation's reference to an error defined elsewhere.
 */
public final class ErrorReference {

    private TypeRef error;
    private String comment;
    private Field extraInformation;
    private SourceLocation location;

    public TypeRef getError() {
        return error;
    }

    public void setError(TypeRef error) {
        this.error = error;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the extra information carried when this operation raises the error, or null.
     */
    public Field getExtraInformation() {
        return extraInformation;
    }

    public void setExtraInformation(Field extraInformation) {
        this.extraInformation = extraInformation;
    }

    public SourceLocation getLocation() {
        return location;
    }

    public void setLocation(SourceLocation location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return String.valueOf(error);
    }
}
