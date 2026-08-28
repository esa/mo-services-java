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
 * One {@code <mal:documentation>} element, mirrored exactly so that XML round-trips
 * unchanged - including the convention where a single section holds several requirements
 * as prose.
 * <p>
 * The content is a small markup language of its own: lines that are exactly {@code <ol>}
 * or {@code </ol>} open and close numbering levels, a line containing {@code <li>} becomes
 * a list paragraph, and {@code <ul>}/{@code </ul>} are dropped. That behaviour is
 * normative, reverse-engineered from the existing document writer.
 */
public final class DocSection {

    private String name;
    private int order;
    private String content;
    private SourceLocation location;

    /**
     * @return the section name, verbatim: "Requirement", "High Level Requirements", and so on.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * @return the section body, or null when the element is empty.
     */
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SourceLocation getLocation() {
        return location;
    }

    public void setLocation(SourceLocation location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return name + " [" + order + "]";
    }
}
