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
package esa.mo.apigen.importers;

import esa.mo.apigen.model.SourceRef;
import esa.mo.apigen.model.Specification;
import java.io.Reader;

/**
 * Reads a specification from a source.
 * <p>
 * Importers take a {@link Reader} rather than a path so that text held in an editor can be
 * parsed directly. Where the source does have a file behind it, {@link SourceRef} carries
 * the location, which is also what locates sidecar files.
 */
public interface Importer {

    /**
     * Reads one specification.
     *
     * @param in The source text.
     * @param source Where it came from, for diagnostics and sidecar resolution.
     * @return the specification.
     * @throws ImportException if the source cannot be read at all.
     */
    Specification read(Reader in, SourceRef source) throws ImportException;
}
