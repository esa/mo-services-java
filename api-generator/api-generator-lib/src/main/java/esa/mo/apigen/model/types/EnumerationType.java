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
package esa.mo.apigen.model.types;

import java.util.ArrayList;
import java.util.List;

/**
 * An enumeration.
 */
public final class EnumerationType extends TypeDefinition {

    private int shortFormPart;
    private final List<EnumerationItem> items = new ArrayList<EnumerationItem>();

    public int getShortFormPart() {
        return shortFormPart;
    }

    public void setShortFormPart(int shortFormPart) {
        this.shortFormPart = shortFormPart;
    }

    public List<EnumerationItem> getItems() {
        return items;
    }

    @Override
    public boolean isAbstract() {
        return false;
    }
}
