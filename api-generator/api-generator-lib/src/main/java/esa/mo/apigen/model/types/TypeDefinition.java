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
package esa.mo.apigen.model.types;

import esa.mo.apigen.model.Area;
import esa.mo.apigen.model.Service;
import esa.mo.apigen.model.SourceLocation;

/**
 * Base of every type a specification defines.
 * <p>
 * The owning area and service are held as back-references rather than as names, so that
 * the defining area's version is directly available and there is no second copy of the
 * name to keep consistent.
 */
public abstract class TypeDefinition {

    private String name;
    private String comment;
    private Area area;
    private Service service;
    private SourceLocation location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    /**
     * @return the service that defines this type, or null for an area-level type.
     */
    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public SourceLocation getLocation() {
        return location;
    }

    public void setLocation(SourceLocation location) {
        this.location = location;
    }

    /**
     * Returns true if the type cannot be instantiated, and so has no short form part.
     *
     * @return true if abstract.
     */
    public abstract boolean isAbstract();

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + name;
    }
}
