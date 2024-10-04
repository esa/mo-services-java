/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
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

package esa.mo.tools.stubgen;

import esa.mo.tools.stubgen.specification.TypeUtils;
import esa.mo.xsd.TypeReference;

/**
 * Simple comparable class that allows indexing on an MO type.
 */
public final class TypeKey implements Comparable<TypeKey> {

    private final String key;

    /**
     * Constructor.
     *
     * @param type The type reference to create the key from.
     */
    public TypeKey(TypeReference type) {
        this.key = createTypeKey(type);
    }

    /**
     * Constructor.
     *
     * @param area The area of the type.
     * @param service The service of the type.
     * @param name The name of the type.
     */
    public TypeKey(String area, String service, String name) {
        this.key = createTypeKey(TypeUtils.createTypeReference(area, service, name, false));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.key != null ? this.key.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TypeKey other = (TypeKey) obj;

        return !((this.key == null) ? (other.key != null) : !this.key.equals(other.key));
    }

    @Override
    public int compareTo(TypeKey o) {
        return this.key.compareTo(o.key);
    }

    @Override
    public String toString() {
        return "TypeKey{" + key + '}';
    }

    private static String createTypeKey(TypeReference type) {
        StringBuilder buf = new StringBuilder();
        buf.append(type.getArea());

        if (null != type.getService()) {
            buf.append(':').append(type.getService()).append(':');
        } else {
            buf.append(":_:");
        }

        buf.append(type.getName());
        return buf.toString();
    }
}
