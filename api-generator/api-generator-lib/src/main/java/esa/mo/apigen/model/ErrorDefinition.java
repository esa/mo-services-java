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

/**
 * An error defined by an area or a service.
 */
public final class ErrorDefinition {

    private String name;
    private long number;
    private String comment;
    private Field extraInformation;
    private SourceLocation location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the extra information the error carries, or null if it carries none.
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
        return name + " [" + number + "]";
    }
}
