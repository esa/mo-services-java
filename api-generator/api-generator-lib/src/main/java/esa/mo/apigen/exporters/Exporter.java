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
package esa.mo.apigen.exporters;

import esa.mo.apigen.model.Specification;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Writes a specification out.
 * <p>
 * Exporters write to a directory rather than to a stream because no export is necessarily
 * one document: a MOSpec specification is a text file plus a sidecar for each diagram.
 */
public interface Exporter {

    /**
     * Writes a specification.
     *
     * @param spec The specification to write.
     * @param outputDir The directory to write into. Created if it does not exist.
     * @throws IOException if writing fails.
     * @throws ExportException if the specification cannot be represented in this format.
     */
    void write(Specification spec, Path outputDir) throws IOException, ExportException;
}
