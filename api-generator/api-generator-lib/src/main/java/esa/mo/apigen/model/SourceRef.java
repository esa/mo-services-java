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

/**
 * Where a specification came from. Provenance only: never written to any output format.
 */
public final class SourceRef {

    private final String name;
    private final String location;

    /**
     * Constructor.
     *
     * @param name A short name for the source, typically the file name.
     * @param location The full location, or null when the source has no file behind it -
     * text held in an editor, for instance.
     */
    public SourceRef(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns the full location of the source, or null if it has none. Sidecar files -
     * a MOSpec diagram, for example - are resolved relative to this.
     *
     * @return the location, may be null.
     */
    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return name;
    }
}
