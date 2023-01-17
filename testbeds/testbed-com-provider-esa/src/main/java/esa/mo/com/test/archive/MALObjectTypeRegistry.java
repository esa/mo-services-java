/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO COM Testbed ESA provider
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
package esa.mo.com.test.archive;

import java.util.Map;
import java.util.HashMap;
import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ElementList;

/**
 *
 * Holds a map of MALElements for the element and the associated
 * elementList indexed on ObjectType.
 */
public class MALObjectTypeRegistry {

    private final Map<String, ElementsReg> elements = new HashMap();

    private static MALObjectTypeRegistry instance = null;

    public static MALObjectTypeRegistry inst() {
        if (instance == null) {
            instance = new MALObjectTypeRegistry();
        }

        return instance;
    }

    /**
     * Registers an element in the map using the supplied short form
     * object as the key.
     *
     * @param objectType The object type used for lookup.
     * @param element
     * @param elementList
     * @throws IllegalArgumentException If either supplied argument is null.
     */
    public void registerElements(final ObjectType objectType, final Element element,
            final ElementList elementList) throws IllegalArgumentException {
        if ((null == elementList)) {
            throw new IllegalArgumentException("NULL argument");
        }

        elements.put(objectType.toString(), new ElementsReg(element, elementList));
    }

    /**
     * Returns a Element for the supplied object Type, or null if not
     * found.
     *
     * @param objectType The short form to search for.
     * @return The Element or null if not found.
     * @throws IllegalArgumentException If supplied argument is null.
     */
    public Element lookupElement(final ObjectType objectType) throws IllegalArgumentException {
        if (null == objectType) {
            throw new IllegalArgumentException("NULL argument");
        }

        return elements.get(objectType.toString()).getElement();
    }

    /**
     * Returns a ElementList, for the list, for the supplied object Type,
     * or null if not found.
     *
     * @param objectType The short form to search for.
     * @return The ElementList or null if not found.
     * @throws IllegalArgumentException If supplied argument is null.
     */
    public ElementList lookupElementlist(final ObjectType objectType)
            throws IllegalArgumentException {
        if (null == objectType) {
            throw new IllegalArgumentException("NULL argument");
        }
        if (elements.get(objectType.toString()) != null) {
            return elements.get(objectType.toString()).getElementList();
        } else {
            return null;
        }
    }

    private class ElementsReg {

        private final Element element;
        private final ElementList elementList;

        public ElementsReg(Element element, ElementList elementList) {
            this.element = element;
            this.elementList = elementList;
        }

        public Element getElement() {
            return element;
        }

        public ElementList getElementList() {
            return elementList;
        }

    }

}
