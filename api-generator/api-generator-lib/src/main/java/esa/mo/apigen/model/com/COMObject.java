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
package esa.mo.apigen.model.com;

import esa.mo.apigen.model.SourceLocation;
import esa.mo.apigen.model.types.TypeRef;

/**
 * A COM object or event.
 * <p>
 * Events use the same shape - the schema's event list holds elements of the same type - so
 * an event is a COM object that happens to live in the events list. Object and event
 * numbers share one namespace within a service.
 */
public final class COMObject {

    private String name;
    private int number;
    private String comment;
    private TypeRef bodyType;
    private ObjectLink related;
    private ObjectLink source;
    private SourceLocation location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the type of the object's body, or null if it has no body.
     */
    public TypeRef getBodyType() {
        return bodyType;
    }

    public void setBodyType(TypeRef bodyType) {
        this.bodyType = bodyType;
    }

    public ObjectLink getRelated() {
        return related;
    }

    public void setRelated(ObjectLink related) {
        this.related = related;
    }

    public ObjectLink getSource() {
        return source;
    }

    public void setSource(ObjectLink source) {
        this.source = source;
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
