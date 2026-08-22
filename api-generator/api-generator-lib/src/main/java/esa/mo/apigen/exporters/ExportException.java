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

/**
 * Thrown when a model cannot be written in the requested format.
 * <p>
 * The commonest cause is a construct the target schema version does not have. An exporter
 * refuses rather than dropping it: writing a v003 model into a v001 file would silently
 * lose its subscription keys.
 */
public class ExportException extends Exception {

    private static final long serialVersionUID = 1L;

    public ExportException(String message) {
        super(message);
    }
}
