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

/**
 * A fundamental type. Only Element, Attribute and Composite are fundamental, and only the
 * MAL area may define them. Always abstract.
 */
public final class FundamentalType extends TypeDefinition {

    private TypeRef superType;

    /**
     * @return the type this one extends, or null if it extends nothing.
     */
    public TypeRef getSuperType() {
        return superType;
    }

    public void setSuperType(TypeRef superType) {
        this.superType = superType;
    }

    @Override
    public boolean isAbstract() {
        return true;
    }
}
