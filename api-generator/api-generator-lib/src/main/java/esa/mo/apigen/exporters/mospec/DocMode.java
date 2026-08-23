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
package esa.mo.apigen.exporters.mospec;

/**
 * Where the documentation of an operation's fields and errors is placed.
 */
public enum DocMode {

    /**
     * Hoisted into one block above the signature, tagged by what it describes. The default,
     * because it keeps the signature scannable in one glance.
     */
    BULK,

    /**
     * Written beside the field or error it describes, inside the signature.
     */
    INLINE,

    /**
     * Left out. The only mode that does not preserve the model, and so the only one the
     * round-trip test cannot hold to equality.
     */
    SUPPRESS
}
