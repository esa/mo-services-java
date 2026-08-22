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
package esa.mo.apigen.model.docs;

import esa.mo.apigen.model.SourceLocation;

/**
 * A single normative statement, derived from a {@link DocSection} rather than stored.
 * Because it is derived it never reaches an exporter and cannot affect a round trip.
 * <p>
 * {@link #getId()} is set only where the specification authored the number - the
 * convention of one requirement per element, with {@code order} naming it. Where a section
 * holds several statements as prose, their position is the only thing distinguishing them,
 * so no identifier is claimed: inserting a sentence would renumber everything after it.
 */
public final class Requirement {

    private final String id;
    private final String text;
    private final DocSection origin;
    private final SourceLocation location;

    public Requirement(String id, String text, DocSection origin, SourceLocation location) {
        this.id = id;
        this.text = text;
        this.origin = origin;
        this.location = location;
    }

    /**
     * @return the authored identifier, or null when the source did not provide one.
     */
    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    /**
     * @return the documentation section this requirement was taken from.
     */
    public DocSection getOrigin() {
        return origin;
    }

    public SourceLocation getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return (id == null ? "(unnumbered)" : id) + ": " + text;
    }
}
