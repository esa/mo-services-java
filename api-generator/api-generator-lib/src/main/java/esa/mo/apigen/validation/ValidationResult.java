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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Everything one validation run found.
 * <p>
 * All issues are collected before anything stops, which matters for the editor as much as
 * for the build: a user wants every problem in a file highlighted at once, not a
 * fix-one-recompile loop.
 */
public final class ValidationResult {

    private final List<ValidationIssue> issues = new ArrayList<ValidationIssue>();

    public void add(ValidationIssue issue) {
        issues.add(issue);
    }

    public List<ValidationIssue> getIssues() {
        return Collections.unmodifiableList(issues);
    }

    /**
     * Returns the issues of one severity.
     *
     * @param severity The severity to filter on.
     * @return the matching issues.
     */
    public List<ValidationIssue> getIssues(Severity severity) {
        List<ValidationIssue> found = new ArrayList<ValidationIssue>();
        for (ValidationIssue issue : issues) {
            if (issue.getSeverity() == severity) {
                found.add(issue);
            }
        }
        return found;
    }

    /**
     * @return true if anything at all was reported.
     */
    public boolean hasIssues() {
        return !issues.isEmpty();
    }

    /**
     * @return true if generation must not start.
     */
    public boolean hasErrors() {
        for (ValidationIssue issue : issues) {
            if (issue.getSeverity() == Severity.ERROR) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        for (ValidationIssue issue : issues) {
            buf.append(issue).append(System.getProperty("line.separator"));
        }
        return buf.toString();
    }
}
