/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Service XML loaders
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
package esa.mo.xsd.util;

import java.io.File;
import w3c.xsd.Schema;

/**
 * The XSD Specification
 */
public final class XsdSpecification {

    /**
     * Holds the source file object.
     */
    private final File file;

    /**
     * Holds the SpecificationType.
     */
    private final Schema schema;

    /**
     * Constructor.
     *
     * @param file The file.
     * @param schema The schema.
     */
    public XsdSpecification(File file, Schema schema) {
        this.file = file;
        this.schema = schema;
    }

    /**
     * Returns the XSD file.
     *
     * @return the XSD file.
     */
    public File getFile() {
        return file;
    }

    /**
     * Returns the XSD Schema.
     *
     * @return the XSD Schema.
     */
    public Schema getSchema() {
        return schema;
    }
}
