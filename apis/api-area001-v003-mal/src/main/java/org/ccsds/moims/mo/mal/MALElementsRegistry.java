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
     * The registered factories, with the Area that each one answers for held
     * beside it as a plain number.
     *
     * Finding the right factory is then a walk over an array of ints rather
     * than a pair of calls into every factory in turn. Those calls go through
     * an interface that a handful of classes implement, so they cannot be
     * bound to one of them, and asking each factory which Area it belongs to
     * cost more than the switch it was being asked to reach.
     */
    private static final class Registered {

        /**
         * The Area of each factory, as an area number and an area version
         * packed into one int so that a candidate is one comparison.
         */
        private final int[] areas;

        private final AreaElementFactory[] factories;

        Registered(int[] areas, AreaElementFactory[] factories) {
            this.areas = areas;
            this.factories = factories;
        }
    }

    /**
     * Replaced as a whole when a factory is registered, so that a scan reads it
     * once and then walks something that cannot change underneath it. Areas are
     * registered while the messages of the ones already registered are being
     * decoded, so this has to hold while both are happening.
     */
    private volatile Registered registered
            = new Registered(new int[0], new AreaElementFactory[0]);

    /**
     * Packs an area number and an area version into the one int that the scan
     * compares. An area number is 16 bits wide and a version 8, so the two sit
     * side by side without either reaching into the other.
     *
     * @param areaNumber The area number.
     * @param areaVersion The area version.
     * @return The packed value.
     */
    private static int areaKeyOf(final int areaNumber, final int areaVersion) {
        return (areaNumber << 8) | (areaVersion & 0xFF);
    }

    /**
     * Registers a factory, so that the Elements it creates can be reached
     * without every one of their classes having to be loaded first.
     *
     * More than one factory can be registered for the same Area. Types whose
     * numbers the XML schema cannot express are written by hand, and their
     * factory is registered alongside the generated one of that Area. Should
     * two of them answer for the same type, the one registered first is the one
     * that is asked, so a factory added later cannot take a type away from the
     * one that already had it.
     *
     * @param factory The factory that creates the Elements.
     */
    public synchronized void registerAreaFactory(final AreaElementFactory factory) {
        if (factory == null) {
            return;
        }

        final Registered current = this.registered;

        for (AreaElementFactory alreadyThere : current.factories) {
            if (alreadyThere.getClass() == factory.getClass()) {
                return; // Already registered
            }
        }

        final int count = current.factories.length;
        final int[] areas = java.util.Arrays.copyOf(current.areas, count + 1);
        final AreaElementFactory[] factories
                = java.util.Arrays.copyOf(current.factories, count + 1);

        // Asked once, here, rather than on every Element that is created
        areas[count] = areaKeyOf(factory.getAreaNumber(), factory.getAreaVersion());
        factories[count] = factory;

        this.registered = new Registered(areas, factories);
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
        final int areaKey = areaKeyOf(TypeId.areaNumberOf(typeId), TypeId.areaVersionOf(typeId));
        final int serviceNumber = TypeId.serviceNumberOf(typeId);
        final int typeNumber = TypeId.typeNumberOf(typeId);

        // Read once, so that a factory registered halfway through is not seen
        // for some of this scan and not for the rest of it.
        final Registered current = this.registered;
        final int[] areas = current.areas;

        for (int i = 0; i < areas.length; i++) {
            if (areas[i] == areaKey) {
                Element element = current.factories[i].createElement(serviceNumber, typeNumber);

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
