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

/**
 * Class representing a Type Id of a MAL Element.
 */
public class TypeId {

    /**
     * The bit shift value for the area part of a Type Id.
     */
    private final static int AREA_BIT_SHIFT = 48;
    /**
     * The bit shift value for the service part of a Type Id.
     */
    private final static int SERVICE_BIT_SHIFT = 32;
    /**
     * The bit shift value for the version part of a Type Id.
     */
    private final static int VERSION_BIT_SHIFT = 24;

    private final static long MASK_08 = 0xFF;
    private final static long MASK_16 = 0xFFFF;
    private final static long MASK_24 = 0xFFFFFF;

    /**
     * Area number (defined as UShort by the MAL).
     */
    private final short areaNumber;

    /**
     * Area version (defined as UShort by the MAL).
     */
    private final short areaVersion;

    /**
     * Service number (defined as UShort by the MAL).
     */
    private final short serviceNumber;

    /**
     * Short Form Part number (defined as Short by the MAL).
     */
    private final short sfp;

    private Long typeId = null;

    /**
     * Constructor.
     *
     * @param typeId The TypeId of the type.
     */
    public TypeId(final Long typeId) {
        this.typeId = typeId;
        this.areaNumber = (short) ((typeId >> AREA_BIT_SHIFT) & MASK_16);
        this.areaVersion = (short) ((typeId >> VERSION_BIT_SHIFT) & MASK_08);
        this.serviceNumber = (short) ((typeId >> SERVICE_BIT_SHIFT) & MASK_16);
        this.sfp = (short) (typeId & MASK_24);
    }

    /**
     * Initializes the TypeId class.
     *
     * @param areaNumber The area number of the service.
     * @param areaVersion The area version of the service.
     * @param serviceNumber The service number of the service.
     * @param sfp The Short Form Part number.
     * @throws java.lang.IllegalArgumentException If any argument is null.
     */
    public TypeId(final short areaNumber, final short areaVersion,
            final short serviceNumber, final short sfp) {
        this.areaNumber = areaNumber;
        this.areaVersion = areaVersion;
        this.serviceNumber = serviceNumber;
        this.sfp = sfp;
    }

    /**
     * Initializes the TypeId class.
     *
     * @param areaNumber The area number of the service.
     * @param areaVersion The area version of the service.
     * @param serviceNumber The service number of the service.
     * @param sfp The Short Form Part number.
     * @throws java.lang.IllegalArgumentException If any argument is null.
     */
    public TypeId(final int areaNumber, final int areaVersion,
            final int serviceNumber, final int sfp) {
        this((short) areaNumber, (short) areaVersion, (short) serviceNumber, (short) sfp);
    }

    /**
     * Returns the area number of the service.
     *
     * @return The area number of the service.
     */
    public int getAreaNumber() {
        return areaNumber;
    }

    /**
     * Returns the area version of the service.
     *
     * @return The area version of the service.
     */
    public int getAreaVersion() {
        return areaVersion;
    }

    /**
     * Returns the service number of the service.
     *
     * @return The service number of the service.
     */
    public int getServiceNumber() {
        return serviceNumber;
    }

    public boolean isOldMAL() {
        return (areaNumber == 1 && areaVersion < 3);
    }

    /**
     * Returns the short form part number.
     *
     * @return The short form part number.
     */
    public int getSFP() {
        return sfp;
    }

    /**
     * Returns true if it represents a list type.
     *
     * @return True if it represents a list type.
     */
    public boolean isList() {
        return (sfp <= 0);
    }

    /**
     * Returns the long value of this TypeId.
     *
     * @return The long value of this TypeId.
     */
    public long getTypeId() {
        if (typeId != null) {
            return typeId;
        }

        long asf = ((long) areaNumber) << AREA_BIT_SHIFT;
        asf += ((long) areaVersion) << VERSION_BIT_SHIFT;

        if (serviceNumber != 0) {
            asf += ((long) serviceNumber) << SERVICE_BIT_SHIFT;
        }

        if (sfp >= 0) {
            asf += sfp;
        } else {
            asf += Long.parseLong(Integer.toHexString(sfp).toUpperCase().substring(2), 16);
        }

        typeId = asf;
        return typeId;
    }

    public TypeId generateTypeIdPositive() {
        int newSPF = (sfp > 0) ? sfp : (-1) * sfp;
        return new TypeId(areaNumber, areaVersion, serviceNumber, newSPF);
    }

    @Override
    public String toString() {
        return "(TypeId:"
                + " areaNumber=" + areaNumber
                + " areaVersion=" + areaVersion
                + " serviceNumber=" + serviceNumber
                + " sfp=" + sfp
                + ")";
    }
}
