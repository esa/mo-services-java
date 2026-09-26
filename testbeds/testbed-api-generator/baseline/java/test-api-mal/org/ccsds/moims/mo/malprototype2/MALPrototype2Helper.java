package org.ccsds.moims.mo.malprototype2;

/**
 * Helper class for MALPrototype2 area.
 */
public class MALPrototype2Helper {

    /**
     * Area number literal.
     */
    public static final int _MALPROTOTYPE2_AREA_NUMBER = 101;

    /**
     * Area number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort MALPROTOTYPE2_AREA_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_MALPROTOTYPE2_AREA_NUMBER);

    /**
     * Area name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier MALPROTOTYPE2_AREA_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("MALPrototype2");

    /**
     * Area version literal.
     */
    public static final short _MALPROTOTYPE2_AREA_VERSION = 1;

    /**
     * Area version instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UOctet MALPROTOTYPE2_AREA_VERSION = new org.ccsds.moims.mo.mal.structures.UOctet(_MALPROTOTYPE2_AREA_VERSION);

    /**
     * Area Elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] MALPROTOTYPE2_AREA_ELEMENTS = {};

    /**
     * Services in this Area.
     */
    public static final org.ccsds.moims.mo.mal.ServiceInfo[] MALPROTOTYPE2_AREA_SERVICES = {
        org.ccsds.moims.mo.malprototype2.iptest.IPTestHelper.IPTEST_SERVICE,};

    /**
     * Area singleton instance.
     */
    public static final org.ccsds.moims.mo.mal.MALArea MALPROTOTYPE2_AREA = new org.ccsds.moims.mo.mal.MALArea(MALPROTOTYPE2_AREA_NUMBER, MALPROTOTYPE2_AREA_NAME, MALPROTOTYPE2_AREA_VERSION, MALPROTOTYPE2_AREA_ELEMENTS, MALPROTOTYPE2_AREA_SERVICES, new MALPrototype2ElementFactory());

    private MALPrototype2Helper() {
        // Utility class; not meant to be instantiated.
    }

}
