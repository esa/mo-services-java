/* ----------------------------------------------------------------------------
 * Copyright (C) 2026      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Generic Transport Framework
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
package esa.mo.mal.transport.gen;

import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;

/**
 * The encodings a message body can be in, with the identifier that a transport
 * header carries for each of them.
 * <p>
 * The binary identifiers are those of the SANA registry "MAL Encoding Ids",
 * defined by CCSDS 524.1-B-1. XML has no entry there; it takes the next
 * value, 3. A factory is matched by its exact class: a subclass, such as an
 * SPP variant, may encode differently and has no identifier.
 */
public enum BodyEncoding {

    FIXED_BINARY(0, "esa.mo.mal.encoder.binary.fixed.FixedBinaryStreamFactory"),
    VARIABLE_BINARY(1, "esa.mo.mal.encoder.binary.variable.VariableBinaryStreamFactory"),
    SPLIT_BINARY(2, "esa.mo.mal.encoder.binary.split.SplitBinaryStreamFactory"),
    XML(3, "esa.mo.mal.encoder.xml.XMLStreamFactory");

    private final int id;
    private final String factoryClassName;

    BodyEncoding(int id, String factoryClassName) {
        this.id = id;
        this.factoryClassName = factoryClassName;
    }

    /**
     * @return the identifier carried in a transport header.
     */
    public int getId() {
        return id;
    }

    /**
     * @return the fully qualified name of the factory class.
     */
    public String getFactoryClassName() {
        return factoryClassName;
    }

    /**
     * @param id An identifier read from a transport header.
     * @return the encoding with this identifier, or null if there is none.
     */
    public static BodyEncoding ofId(int id) {
        for (BodyEncoding encoding : values()) {
            if (encoding.id == id) {
                return encoding;
            }
        }
        return null;
    }

    /**
     * @param className The fully qualified name of a factory class.
     * @return the encoding produced by this class, or null if it has no
     * identifier.
     */
    public static BodyEncoding ofFactoryClass(String className) {
        for (BodyEncoding encoding : values()) {
            if (encoding.factoryClassName.equals(className)) {
                return encoding;
            }
        }
        return null;
    }

    /**
     * @param factory A stream factory.
     * @return the encoding produced by this factory, or null if it has no
     * identifier.
     */
    public static BodyEncoding of(MALElementStreamFactory factory) {
        return ofFactoryClass(factory.getClass().getName());
    }
}
