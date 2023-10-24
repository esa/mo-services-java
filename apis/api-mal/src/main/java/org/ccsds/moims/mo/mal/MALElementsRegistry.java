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
package org.ccsds.moims.mo.mal;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ElementList;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;

/**
 * Holds a map of MAL Elements indexed on the absolute short form part. Used to
 * lookup the correct elements for a supplied absolute short form part.
 */
public class MALElementsRegistry {

    private final HashMap<Long, Callable<Element>> ELEMENTS = new java.util.HashMap<>(128);

    /**
     * Adds an Element to the map of Elements.
     *
     * @param absoluteSFP The absolute short form part.
     * @param callable The method with the generation of the Element.
     */
    public void addCallableElement(Long absoluteSFP, Callable<Element> callable) {
        ELEMENTS.put(absoluteSFP, callable);
    }

    /**
     * Removes an Element from the map of Elements.
     *
     * @param absoluteSFP The absolute short form part.
     */
    public synchronized void removeCallableElement(Long absoluteSFP) {
        ELEMENTS.remove(absoluteSFP);
    }

    /**
     * Returns the number of elements available on the registry.
     *
     * @return The number of elements.
     */
    public int howMany() {
        return ELEMENTS.size();
    }

    /**
     * Creates an element from the absolute short form part.
     *
     * @param absoluteSFP The absolute short form part.
     * @return The created Element.
     * @throws NotFoundException if the element was not found.
     */
    public Element createElement(Long absoluteSFP) throws Exception {
        if (absoluteSFP == 0) {
            return new HeterogeneousList();
        }

        Callable<Element> callable = ELEMENTS.get(absoluteSFP);

        if (callable == null) {
            TypeId typeId = new TypeId(absoluteSFP);

            if (typeId.isOldMAL()) {
                Logger.getLogger(MALElementsRegistry.class.getName()).log(Level.SEVERE,
                        "The typeId is incorrect! It is: {0}", typeId.toString());
            }

            throw new NotFoundException("The element was not found: "
                    + absoluteSFP + "\n" + typeId.toString());
        }

        return callable.call();
    }

    /**
     * Returns the list type of the supplied MAL element type.
     *
     * @param obj The MAL element type to return the list type of.
     * @return The list type or null if not found or null passed in.
     * @throws NotFoundException if the element could not be found.
     */
    public static ElementList elementToElementList(Element obj) throws NotFoundException {
        if (obj == null) {
            return null;
        }

        // Is it already a List?
        if (obj instanceof ElementList) {
            return (ElementList) obj;
        }

        long l = obj.getShortForm();
        long ll = (-((l) & 0xFFFFFFL)) & 0xFFFFFFL + (l & 0xFFFFFFFFFF000000L);

        try {
            Element createdElement = MALContextFactory.getElementsRegistry().createElement(ll);
            return (ElementList) createdElement;
        } catch (Exception ex) {
            throw new NotFoundException("The element could not be found in the MAL ElementFactory!"
                    + " The object type is: " + obj.getClass().getSimpleName()
                    + ". Maybe the service Helper for this object was not initialized."
                    + " Try initializing the Service Helper of this object.", ex);
        }
    }

    /**
     * Returns the MAL element type of the supplied list type.
     *
     * @param obj The list type to return the MAL element type of.
     * @return The MAL element type or null if not found or null passed in.
     * @throws NotFoundException if the element could not be found.
     */
    public static Element elementListToElement(ElementList obj) throws NotFoundException {
        if (obj == null) {
            return null;
        }

        long l = obj.getShortForm();
        long ll = (-((l) & 0xFFFFFFL)) & 0xFFFFFFL + (l & 0xFFFFFFFFFF000000L);

        try {
            return MALContextFactory.getElementsRegistry().createElement(ll);
        } catch (Exception ex) {
            throw new NotFoundException("The element could not be found in the MAL ElementFactory!"
                    + " The object type is: " + obj.getClass().getSimpleName()
                    + ". Maybe the service Helper for this object was not initialized."
                    + " Try initializing the Service Helper of this object.", ex);
        }
    }

    public synchronized void registerElementsForArea(MALArea malArea) {
        Element[] elements = malArea.getElements();

        for (Element element : elements) {
            this.addCallableElement(element.getShortForm(), () -> element.createElement());
        }
    }

    public synchronized void registerElementsForService(MALService malService) {
        Element[] elements = malService.getElements();

        for (Element element : elements) {
            this.addCallableElement(element.getShortForm(), () -> element.createElement());
        }
    }
}
