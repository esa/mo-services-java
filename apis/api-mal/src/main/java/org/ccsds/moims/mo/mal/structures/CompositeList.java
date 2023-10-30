/* ----------------------------------------------------------------------------
 * Copyright (C) 2023      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java API
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
package org.ccsds.moims.mo.mal.structures;

/**
 * List class for Composites.
 */
public class CompositeList extends org.ccsds.moims.mo.mal.structures.HeterogeneousList {

    /**
     * Default constructor for CompositeList.
     *
     */
    public CompositeList() {
    }

    /**
     * Adds an element to the list and checks if the type is correct.
     *
     * @param element The element to be added.
     * @return The success status.
     */
    @Override
    public boolean add(org.ccsds.moims.mo.mal.structures.Element element) {
        if (element != null && !(element instanceof Composite)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: Composite");
        }
        return super.add(element);
    }

}
