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

import esa.mo.apigen.model.com.COMFeatures;
import esa.mo.apigen.model.docs.Documentation;
import esa.mo.apigen.model.types.TypeDefinition;
import java.util.ArrayList;
import java.util.List;

/**
 * A service.
 * <p>
 * {@code dataTypes} and {@code errors} at service level exist only in v001
 * specifications; v003 moved both to the area.
 */
public final class Service {

    private int number;
    private String name;
    private String comment;

    private boolean extended = false;
    private Area area;
    private final List<CapabilitySet> capabilitySets = new ArrayList<CapabilitySet>();
    private final List<TypeDefinition> dataTypes = new ArrayList<TypeDefinition>();
    private final List<ErrorDefinition> errors = new ArrayList<ErrorDefinition>();
    private COMFeatures com;
    private Documentation documentation = new Documentation();
    private SourceLocation location;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns true if the service was declared as a COM extended service.
     * <p>
     * Kept apart from the COM features it may declare: a service can be extended and
     * declare no objects or events at all, and the generated code still has to say so.
     *
     * @return true if the service is a COM extended service.
     */
    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
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

    public List<CapabilitySet> getCapabilitySets() {
        return capabilitySets;
    }

    /**
     * Adds a capability set and sets its back-reference.
     *
     * @param set The capability set to add.
     */
    public void addCapabilitySet(CapabilitySet set) {
        set.setService(this);
        capabilitySets.add(set);
    }

    /**
     * Returns every operation of the service, across all capability sets, in declaration
     * order.
     *
     * @return the operations.
     */
    public List<Operation> getOperations() {
        List<Operation> all = new ArrayList<Operation>();
        for (CapabilitySet set : capabilitySets) {
            all.addAll(set.getOperations());
        }
        return all;
    }

    /**
     * @return the types this service defines. v001 only.
     */
    public List<TypeDefinition> getDataTypes() {
        return dataTypes;
    }

    /**
     * @return the errors this service defines. v001 only.
     */
    public List<ErrorDefinition> getErrors() {
        return errors;
    }

    /**
     * @return the COM features of the service, or null if it declares none.
     */
    public COMFeatures getCom() {
        return com;
    }

    public void setCom(COMFeatures com) {
        this.com = com;
    }

    public Documentation getDocumentation() {
        return documentation;
    }

    public void setDocumentation(Documentation documentation) {
        this.documentation = documentation;
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
