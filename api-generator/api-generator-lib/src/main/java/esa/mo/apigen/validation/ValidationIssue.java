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

import esa.mo.apigen.model.SourceLocation;

/**
 * One problem found in a model.
 */
public final class ValidationIssue {

    private final Severity severity;
    private final String rule;
    private final String message;
    private final SourceLocation location;

    public ValidationIssue(Severity severity, String rule, String message, SourceLocation location) {
        this.severity = severity;
        this.rule = rule;
        this.message = message;
        this.location = location;
    }

    public Severity getSeverity() {
        return severity;
    }

    /**
     * @return a short identifier for the rule that fired, so issues can be grouped or
     * suppressed by rule rather than by message text.
     */
    public String getRule() {
        return rule;
    }

    public String getMessage() {
        return message;
    }

    /**
     * @return where the problem is, or null if it cannot be pinned to a location.
     */
    public SourceLocation getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return severity + " [" + rule + "] "
                + (location == null ? "" : location + ": ") + message;
    }
}
