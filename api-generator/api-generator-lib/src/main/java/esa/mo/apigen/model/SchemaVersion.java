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
 * The version of the service schema a specification conforms to, taken from the root
 * element's namespace.
 * <p>
 * It governs which constructs are legal in the file, not which version of a referenced
 * area a type reference resolves against - see the design document, section 4.1.
 */
public enum SchemaVersion {
    /**
     * http://www.ccsds.org/schema/ServiceSchema
     */
    V001("http://www.ccsds.org/schema/ServiceSchema"),
    /**
     * http://www.ccsds.org/schema/ServiceSchema-v003
     */
    V003("http://www.ccsds.org/schema/ServiceSchema-v003");

    private final String namespace;

    private SchemaVersion(String namespace) {
        this.namespace = namespace;
    }

    /**
     * Returns the XML namespace that identifies this schema version.
     *
     * @return the namespace URI.
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * Returns the version of the MAL area this generation of the schema goes with.
     * <p>
     * A specification says which generation it belongs to by the namespace it declares,
     * and that is the only thing in the file which does: a type or error reference names
     * an area and never a version. So a document in the {@code ServiceSchema} namespace
     * is written against MAL 1 and one in {@code ServiceSchema-v003} against MAL 3, and
     * their error names follow accordingly - v001 calls it UNKNOWN, v003 calls it Unknown.
     *
     * @return the MAL area version of this generation.
     */
    public int getMalVersion() {
        return this == V001 ? 1 : 3;
    }

    /**
     * Returns the schema version for an XML namespace.
     *
     * @param namespace The namespace URI.
     * @return the matching version, or null if the namespace is not a service schema.
     */
    public static SchemaVersion fromNamespace(String namespace) {
        for (SchemaVersion v : values()) {
            if (v.namespace.equals(namespace)) {
                return v;
            }
        }
        return null;
    }
}
