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

import java.util.ArrayList;
import java.util.List;

/**
 * The documentation attached to an area, service, operation or COM feature set.
 */
public final class Documentation {

    private final List<DocSection> sections = new ArrayList<DocSection>();
    private final List<Diagram> diagrams = new ArrayList<Diagram>();

    public List<DocSection> getSections() {
        return sections;
    }

    public List<Diagram> getDiagrams() {
        return diagrams;
    }

    /**
     * @return true if there is nothing here at all.
     */
    public boolean isEmpty() {
        return sections.isEmpty() && diagrams.isEmpty();
    }
}
