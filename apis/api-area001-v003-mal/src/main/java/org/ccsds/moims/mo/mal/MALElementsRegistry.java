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

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ElementList;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;

/**
 * Holds the factory of each registered Area, and asks them for the Element of a
 * Type Id. A factory creates the Element of a type without an instance of every
 * other type of its Area having to be held, so the class of a type is only
 * loaded once a message actually carries that type.
 */
public class MALElementsRegistry {

    /**
     * The registered factories. There are a handful in total, one per Area plus
     * the occasional one for types that are written by hand rather than
     * generated, which is why a scan is enough and no map is needed. Each one
     * says which Area it belongs to, so nothing here can register a factory
     * against the wrong number or version.
     */
    private final List<AreaElementFactory> AREA_FACTORIES = new java.util.ArrayList<>();

    /**
     * Registers a factory, so that the Elements it creates can be reached
     * without every one of their classes having to be loaded first.
     *
     * More than one factory can be registered for the same Area. Types whose
     * numbers the XML schema cannot express are written by hand, and their
     * factory is registered alongside the generated one of that Area.
     *
     * @param factory The factory that creates the Elements.
     */
    public synchronized void registerAreaFactory(final AreaElementFactory factory) {
        if (factory == null) {
            return;
        }

        for (AreaElementFactory registered : AREA_FACTORIES) {
            if (registered.getClass() == factory.getClass()) {
                return; // Already registered
            }
        }

        AREA_FACTORIES.add(factory);
    }

    /**
     * Asks the factories of the Area addressed by a Type Id for the Element,
     * without loading the classes of the types that are not asked for.
     *
     * @param typeId The Type Id.
     * @return The created Element, or null if no factory claims that Type Id.
     */
    private Element createFromAreaFactory(final long typeId) {
        // Read once, up front: a Type Id that no Area claims is the rare case,
        // so there is nothing to be saved by reading the last two only after a
        // factory has been found.
        final int areaNumber = TypeId.areaNumberOf(typeId);
        final int areaVersion = TypeId.areaVersionOf(typeId);
        final int serviceNumber = TypeId.serviceNumberOf(typeId);
        final int typeNumber = TypeId.typeNumberOf(typeId);

        for (int i = 0; i < AREA_FACTORIES.size(); i++) {
            AreaElementFactory factory = AREA_FACTORIES.get(i);

            if (factory.getAreaNumber() == areaNumber && factory.getAreaVersion() == areaVersion) {
                Element element = factory.createElement(serviceNumber, typeNumber);

                // An Area can hold more than one factory, so a factory that
                // does not know the type is asked past, not taken as an answer
                if (element != null) {
                    return element;
                }
            }
        }

        return null;
    }

    /**
     * Creates an element from the absolute short form part.
     *
     * @param typeIdLong The Type Id (aka: absolute short form part).
     * @return The created Element.
     * @throws NotFoundException if the element was not found.
     */
    public Element createElement(Long typeIdLong) throws Exception {
        if (typeIdLong == 0) {
            return new HeterogeneousList();
        }

        Element element = createFromAreaFactory(typeIdLong);

        if (element == null) {
            TypeId typeId = new TypeId(typeIdLong);

            if (typeId.isOldMAL()) {
                Logger.getLogger(MALElementsRegistry.class.getName()).log(Level.SEVERE,
                        "The typeId is using the old MAL version 1: {0}", typeId.toString());
            }

            throw new NotFoundException("The element was not found: "
                    + typeIdLong + " - " + typeId.toString());
        }

        return element;
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

        TypeId typeId = obj.getTypeId();
        int sfp = typeId.getSFP();
        int newSPF = (sfp > 0) ? -sfp : sfp;
        long newTypeId = (new TypeId(typeId.getAreaNumber(), typeId.getAreaVersion(),
                typeId.getServiceNumber(), newSPF)).getTypeId();

        try {
            Element createdElement = MALContextFactory.getElementsRegistry().createElement(newTypeId);
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

        long newTypeId = obj.getTypeId().generateTypeIdPositive().getTypeId();

        try {
            return MALContextFactory.getElementsRegistry().createElement(newTypeId);
        } catch (Exception ex) {
            throw new NotFoundException("The element could not be found in the MAL ElementFactory!"
                    + " The object type is: " + obj.getClass().getSimpleName()
                    + ". Maybe the service Helper for this object was not initialized."
                    + " Try initializing the Service Helper of this object.", ex);
        }
    }

    /**
     * Registers the factory of a certain Area.
     *
     * @param malArea The Area whose factory is to be registered.
     */
    private synchronized void registerElementsForArea(MALArea malArea) {
        // Any area that brings a factory has it registered here, so that every
        // route into this class registers it, including the MAL area itself.
        if (malArea.getElementFactory() != null) {
            this.registerAreaFactory(malArea.getElementFactory());
        }
    }

    /**
     * Loads the Elements for a certain service and its respective Area.
     *
     * @param service The Service to be loaded.
     */
    public void loadServiceAndAreaElements(ServiceInfo service) {
        MALArea parent = service.getArea();

        if (parent != null && parent.getElementFactory() != null) {
            this.registerAreaFactory(parent.getElementFactory());
        }

        // Load the elements here:
        this.registerElementsForArea(MALHelper.MAL_AREA);

        // The Top-level Area loading also needs to be loaded
        this.registerElementsForArea(service.getArea());
        try {
            org.ccsds.moims.mo.mal.MALContextFactory.registerArea(service.getArea());
        } catch (MALException ex) {
            Logger.getLogger(MALElementsRegistry.class.getName()).log(
                    Level.SEVERE, "Something went wrong!", ex);
        }
    }

    /**
     * Loads the Area Elements and all the Service Elements in that Area.
     *
     * @param area The Area to be loaded.
     */
    public void loadFullArea(MALArea area) {
        // The factory creates the Elements of this Area on demand, so that the
        // class of a type is only loaded once a message carries that type.
        if (area.getElementFactory() != null) {
            this.registerAreaFactory(area.getElementFactory());
        }

        this.registerElementsForArea(MALHelper.MAL_AREA);
        // The Top-level Area loading also needs to be loaded
        this.registerElementsForArea(area);

        for (ServiceInfo service : area.getServices()) {
            loadServiceAndAreaElements(service);
        }
    }
}
