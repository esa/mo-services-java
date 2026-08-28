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
package esa.mo.apigen.validation;

/**
 * How much a validation issue matters.
 * <p>
 * Severity is a property of the rule, not of the category, so individual rules can be
 * tightened as the specifications are cleaned up.
 */
public enum Severity {
    /**
     * Generation does not start. Every error found is reported, not just the first.
     */
    ERROR,
    /**
     * Reported; generation proceeds.
     */
    WARNING
}
