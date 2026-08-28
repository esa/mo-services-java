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

import esa.mo.apigen.model.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * A structured type with named fields.
 * <p>
 * A composite with no short form part is abstract. That is the only thing which makes it
 * abstract: the schema states it outright, and a short form part of 0 was never legal
 * (the type is restricted to 1 and above in every schema version).
 * <p>
 * {@link #getSuperType()} is never null once the specification has been imported. The XML
 * may omit {@code <extends>}, which the schema defines as implying the base Composite; the
 * importer resolves that, so no reader has to know the rule.
 */
public final class CompositeType extends TypeDefinition {

    private Integer shortFormPart;
    private TypeRef superType;
    private final List<Field> fields = new ArrayList<Field>();

    /**
     * @return the short form part, or null if the composite is abstract.
     */
    public Integer getShortFormPart() {
        return shortFormPart;
    }

    public void setShortFormPart(Integer shortFormPart) {
        this.shortFormPart = shortFormPart;
    }

    /**
     * @return the type this composite extends. Never null after import.
     */
    public TypeRef getSuperType() {
        return superType;
    }

    public void setSuperType(TypeRef superType) {
        this.superType = superType;
    }

    /**
     * @return the fields this composite declares, not including inherited ones.
     */
    public List<Field> getFields() {
        return fields;
    }

    @Override
    public boolean isAbstract() {
        return shortFormPart == null;
    }
}
