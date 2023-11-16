/* ----------------------------------------------------------------------------
 * Copyright (C) 2023      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Service Stub Generator
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
package esa.mo.tools.stubgen.specification;

import esa.mo.xsd.NamedElementReferenceWithCommentType;
import esa.mo.xsd.TypeReference;

/**
 * Simple holder class that contains either a XML Type reference or a named
 * field.
 */
public class TypeRef {

    private final Object ref;
    private final boolean field;

    /**
     * Constructor
     *
     * @param ref The field to encapsulate
     */
    public TypeRef(NamedElementReferenceWithCommentType ref) {
        this.ref = ref;
        this.field = true;
    }

    /**
     * Constructor
     *
     * @param ref The type to encapsulate
     */
    public TypeRef(TypeReference ref) {
        this.ref = ref;
        this.field = false;
    }

    /**
     * Is the type reference encapsulating a field or type.
     *
     * @return true if encapsulating a field.
     */
    public boolean isField() {
        return field;
    }

    /**
     * Get contained object as a field.
     *
     * @return the encapsulated field.
     */
    public NamedElementReferenceWithCommentType getFieldRef() {
        return (NamedElementReferenceWithCommentType) ref;
    }

    /**
     * Get contained object as a type reference.
     *
     * @return the encapsulated type reference.
     */
    public TypeReference getTypeRef() {
        if (ref == null) {
            return null;
        }

        if (field) {
            return ((NamedElementReferenceWithCommentType) ref).getType();
        } else {
            return (TypeReference) ref;
        }
    }
}
