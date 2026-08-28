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

import esa.mo.apigen.model.types.CompositeType;
import esa.mo.apigen.model.types.TypeRef;

/**
 * Small builders, so the tests read as what they are checking rather than as setup.
 */
public final class ModelFixtures {

    private ModelFixtures() {
    }

    public static Specification spec(String name, SchemaVersion version) {
        Specification spec = new Specification();
        spec.setSchemaVersion(version);
        spec.setSource(new SourceRef(name, null));
        return spec;
    }

    public static Area area(Specification spec, String name, int number, int version) {
        Area area = new Area();
        area.setName(name);
        area.setNumber(number);
        area.setVersion(version);
        spec.addArea(area);
        return area;
    }

    public static Service service(Area area, String name, int number) {
        Service service = new Service();
        service.setName(name);
        service.setNumber(number);
        area.addService(service);
        return service;
    }

    public static CapabilitySet capability(Service service, int number) {
        CapabilitySet set = new CapabilitySet();
        set.setNumber(number);
        service.addCapabilitySet(set);
        return set;
    }

    public static Operation operation(CapabilitySet set, String name, int number,
            InteractionPattern pattern) {
        Operation op = new Operation();
        op.setName(name);
        op.setNumber(number);
        op.setPattern(pattern);
        set.addOperation(op);
        return op;
    }

    public static CompositeType composite(Area area, String name, Integer shortFormPart) {
        CompositeType type = new CompositeType();
        type.setName(name);
        type.setShortFormPart(shortFormPart);
        type.setArea(area);
        type.setSuperType(new TypeRef("MAL", 3, null, "Composite", false, false));
        area.getDataTypes().add(type);
        return type;
    }

    public static TypeRef ref(String area, int version, String name) {
        return new TypeRef(area, version, null, name, false, false);
    }
}
