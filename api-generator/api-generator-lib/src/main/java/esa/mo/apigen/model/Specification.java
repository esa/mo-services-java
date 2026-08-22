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
package esa.mo.apigen.model;

import java.util.ArrayList;
import java.util.List;

/**
 * One specification - what a single file holds.
 * <p>
 * The list of areas stays a list even though every specification in the corpus contains
 * exactly one: the schema allows more, and nothing may assume otherwise.
 * <p>
 * There is deliberately no flag saying whether this specification is to be generated. That
 * is a property of a particular build, not of the file - the same specification is a
 * generation target in one module and a reference in another - so the caller passes the
 * areas it wants to {@code Generator.generate} instead.
 */
public final class Specification {

    private final List<Area> areas = new ArrayList<Area>();
    private SchemaVersion schemaVersion;
    private String comment;
    private SourceRef source;

    public List<Area> getAreas() {
        return areas;
    }

    /**
     * Adds an area and sets its back-reference.
     *
     * @param area The area to add.
     */
    public void addArea(Area area) {
        area.setSpecification(this);
        areas.add(area);
    }

    /**
     * @return which schema this specification conforms to.
     */
    public SchemaVersion getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(SchemaVersion schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    /**
     * @return the comment carried on the specification element itself, or null.
     */
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public SourceRef getSource() {
        return source;
    }

    public void setSource(SourceRef source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return source == null ? "<specification>" : source.toString();
    }
}
