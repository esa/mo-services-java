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

import esa.mo.apigen.model.docs.Documentation;
import esa.mo.apigen.model.types.TypeDefinition;
import java.util.ArrayList;
import java.util.List;

/**
 * An area: the top-level unit of an MO specification.
 */
public final class Area {

    private int number;
    private int version;
    private String name;
    private String comment;
    private Specification specification;
    private final List<Service> services = new ArrayList<Service>();
    private final List<TypeDefinition> dataTypes = new ArrayList<TypeDefinition>();
    private final List<ErrorDefinition> errors = new ArrayList<ErrorDefinition>();
    private Documentation documentation = new Documentation();
    private SourceLocation location;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

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

    /**
     * @return the identity of this area: name, number and version together.
     */
    public AreaKey getKey() {
        return new AreaKey(name, number, version);
    }

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public List<Service> getServices() {
        return services;
    }

    /**
     * Adds a service and sets its back-reference.
     *
     * @param service The service to add.
     */
    public void addService(Service service) {
        service.setArea(this);
        services.add(service);
    }

    /**
     * Returns a service by name.
     *
     * @param serviceName The name to look for.
     * @return the service, or null if this area has no such service.
     */
    public Service getService(String serviceName) {
        for (Service s : services) {
            if (s.getName() != null && s.getName().equals(serviceName)) {
                return s;
            }
        }
        return null;
    }

    public List<TypeDefinition> getDataTypes() {
        return dataTypes;
    }

    public List<ErrorDefinition> getErrors() {
        return errors;
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
        return name + " [" + number + "." + version + "]";
    }
}
