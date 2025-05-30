/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
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
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * A factory of MAL context objects.
 */
public abstract class MALContextFactory {

    /**
     * The Java property that is used to determine the correct implementation of
     * the factory to use.
     */
    public static final String MAL_FACTORY_PROPERTY = "org.ccsds.moims.mo.mal.factory.class";
    /**
     * The default implementation to use.
     */
    public static final String MAL_DEFAULT_FACTORY = "esa.mo.mal.impl.MALContextFactoryImpl";
    private static final HashMap<String, Class> MAL_MAP = new HashMap<>();
    private static final HashMap<VersionizedAreaNumber, MALArea> VERSIONIZED_AREA_NUMBER_MAP = new HashMap<>();
    private static final HashMap<String, Integer> AREA_NAME_MAP = new HashMap<>();
    private static final HashMap<Long, Identifier> ERRORS = new HashMap<>();
    private static final MALElementsRegistry ELEMENTS_REGISTRY = new MALElementsRegistry();

    /**
     * Registers a MALArea in the list of areas held by this context factory.
     *
     * @param area The new MALArea to register.
     * @throws IllegalArgumentException If area is null.
     * @throws MALException If area number already registered to a different
     * MALArea instance.
     */
    public static void registerArea(final MALArea area) throws MALException {
        if (area == null) {
            throw new IllegalArgumentException("NULL area argument");
        }

        final int num = area.getNumber().getValue();
        final short ver = area.getVersion().getValue();
        final VersionizedAreaNumber verArea = new VersionizedAreaNumber(num, ver);
        final MALArea currentMapping = VERSIONIZED_AREA_NUMBER_MAP.get(verArea);

        if ((currentMapping != null) && (currentMapping != area)) {
            throw new MALException("MALArea already registered with a different instance");
        }

        Integer currentNum = AREA_NAME_MAP.get(area.getName().getValue());

        if (currentNum != null && currentNum.shortValue() != num) {
            throw new MALException("Trying to register the same 'Area Name' with a different 'Area Number'");
        }

        AREA_NAME_MAP.put(area.getName().getValue(), num);
        VERSIONIZED_AREA_NUMBER_MAP.put(verArea, area);
    }

    /**
     * Registers an Error number to an Error name. This method will be removed
     * in the future as it is not used anywhere other than in the init methods.
     * This slows down start-up and does not add value.
     *
     * @param areaNumber The Area number.
     * @param areaVersion The Area version.
     * @param errorNumber The number to use.
     * @param errorName The matching name to the number.
     * @throws java.lang.IllegalArgumentException if either is null.
     * @throws MALException If already registered the number.
     */
    @Deprecated
    private static void registerError(UShort areaNumber, UOctet areaVersion, UInteger errorNumber,
            Identifier errorName) throws java.lang.IllegalArgumentException, MALException {
        if (areaNumber == null || areaVersion == null || errorNumber == null || errorName == null) {
            throw new IllegalArgumentException("NULL argument");
        }

        synchronized (ERRORS) {
            final Long key = generateKey(areaNumber, areaVersion, errorNumber);
            final Identifier name = ERRORS.get(key);

            if ((name != null) && !(name.equals(errorName))) {
                throw new MALException("Error already registered with a different name!");
            }

            ERRORS.put(key, errorName);
        }
    }

    private static long generateKey(UShort areaNumber, UOctet areaVersion, UInteger errorNumber) {
        return ((long) areaNumber.getValue()) << 48
                | ((long) areaVersion.getValue()) << 32
                | ((long) errorNumber.getValue());
    }

    /**
     * Look up a MALArea from an area name.
     *
     * @param areaName The area name to search for.
     * @param version The version of the area to find.
     * @return The matched MALArea or null if not found.
     * @throws IllegalArgumentException If an argument is null.
     */
    public static MALArea lookupArea(final Identifier areaName, final UOctet version)
            throws IllegalArgumentException {
        if (areaName == null) {
            throw new IllegalArgumentException("NULL area argument");
        }
        if (version == null) {
            throw new IllegalArgumentException("NULL version argument");
        }

        final Integer num = AREA_NAME_MAP.get(areaName.getValue());

        if (num == null) {
            return null;
        }

        return (MALArea) VERSIONIZED_AREA_NUMBER_MAP.get(
                new VersionizedAreaNumber(num, version.getValue()));
    }

    /**
     * Look up a MALArea from an area number.
     *
     * @param areaNumber The area number to search for.
     * @param version The version of the area to find.
     * @return The matched MALArea or null if not found.
     * @throws IllegalArgumentException If an argument is null.
     */
    public static MALArea lookupArea(final UShort areaNumber, final UOctet version)
            throws IllegalArgumentException {
        if (areaNumber == null) {
            throw new IllegalArgumentException("NULL area argument");
        }
        if (version == null) {
            throw new IllegalArgumentException("NULL version argument");
        }

        return (MALArea) VERSIONIZED_AREA_NUMBER_MAP.get(
                new VersionizedAreaNumber(areaNumber.getValue(), version.getValue()));
    }

    /**
     * Look up an error name from its number. This method will be removed in the
     * future because it is not being used anywhere and having a HashMap costs
     * memory and time during start-up.
     *
     * @param areaNumber The Area number.
     * @param areaVersion The Area version.
     * @param errorNumber The error name.
     * @return The error number or null if not found.
     */
    @Deprecated
    public static Identifier lookupError(UShort areaNumber, UOctet areaVersion, UInteger errorNumber) {
        Long key = generateKey(areaNumber, areaVersion, errorNumber);
        return ERRORS.get(key);
    }

    /**
     * Returns the elements registry.
     *
     * @return The Elements Registry.
     */
    public static MALElementsRegistry getElementsRegistry() {
        return ELEMENTS_REGISTRY;
    }

    /**
     * Allows a specific implementation of this class to be registered where
     * multiple class loaders can cause instantiation problems.
     *
     * @param factoryClass The factory class to register.
     * @throws IllegalArgumentException if the parameter does not extend
     * MALContextFactory or is null.
     */
    public static void registerFactoryClass(final Class factoryClass) throws IllegalArgumentException {
        if (factoryClass == null) {
            throw new IllegalArgumentException("NULL argument");
        }

        if (!MALContextFactory.class.isAssignableFrom(factoryClass)) {
            throw new IllegalArgumentException(
                    "Supplied factory class does not extend MALContextFactory: "
                    + factoryClass.getName());
        }

        MAL_MAP.put(factoryClass.getName(), factoryClass);
    }

    /**
     * Deregisters a previously registered factory class implementation. Does
     * nothing if passed null.
     *
     * @param factoryClass The class to deregister
     * @throws IllegalArgumentException if the parameter does not extend
     * MALContextFactory.
     */
    public static void deregisterFactoryClass(final java.lang.Class factoryClass) throws IllegalArgumentException {
        if (null != factoryClass) {
            if (!MALContextFactory.class.isAssignableFrom(factoryClass)) {
                throw new IllegalArgumentException(
                        "Supplied factory class does not extend MALContextFactory: "
                        + factoryClass.getName());
            }

            MAL_MAP.remove(factoryClass.getName());
        } else {
            throw new IllegalArgumentException("NULL argument");
        }
    }

    /**
     * Creates and returns a new instance of the factory class identified by the
     * property.
     *
     * @return The new instance.
     * @throws MALException If there is a problem instantiating the new
     * instance.
     */
    public static MALContextFactory newFactory() throws MALException {
        try {
            final String classname = System.getProperty(MAL_FACTORY_PROPERTY, MAL_DEFAULT_FACTORY);
            Class malFactoryClass;

            if (MAL_MAP.containsKey(classname)) {
                malFactoryClass = (Class) MAL_MAP.get(classname);
            } else {
                malFactoryClass = Class.forName(classname);
                registerFactoryClass(malFactoryClass);
            }

            return (MALContextFactory) malFactoryClass.newInstance();
        } catch (ClassNotFoundException e) {
            throw new MALException(e.getLocalizedMessage(), e);
        } catch (InstantiationException e) {
            throw new MALException(e.getLocalizedMessage(), e);
        } catch (IllegalAccessException e) {
            throw new MALException(e.getLocalizedMessage(), e);
        }
    }

    /**
     * Creates a MAL Context instance.
     *
     * @param props Properties required by the specific MALContext
     * implementation
     * @return The new MALContext instance.
     * @throws MALException If there is a problem instantiating the new
     * instance.
     */
    public abstract MALContext createMALContext(java.util.Map props) throws MALException;

    protected static class VersionizedAreaNumber {

        private final int areaNumber;
        private final int version;

        public VersionizedAreaNumber(int areaNumber, short version) {
            this.areaNumber = areaNumber;
            this.version = (int) version;
        }

        @Override
        public int hashCode() {
            int hash = this.areaNumber << 16;
            return hash + this.version;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final VersionizedAreaNumber other = (VersionizedAreaNumber) obj;
            if (this.areaNumber != other.areaNumber) {
                return false;
            }
            return this.version == other.version;
        }
    }
}
