package org.ccsds.moims.mo.comprototype;

/**
 * Helper class for COMPrototype area.
 */
public class COMPrototypeHelper {

    /**
     * Area number literal.
     */
    public static final int _COMPROTOTYPE_AREA_NUMBER = 200;

    /**
     * Area number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort COMPROTOTYPE_AREA_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_COMPROTOTYPE_AREA_NUMBER);

    /**
     * Area name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier COMPROTOTYPE_AREA_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("COMPrototype");

    /**
     * Area version literal.
     */
    public static final short _COMPROTOTYPE_AREA_VERSION = 1;

    /**
     * Area version instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UOctet COMPROTOTYPE_AREA_VERSION = new org.ccsds.moims.mo.mal.structures.UOctet(_COMPROTOTYPE_AREA_VERSION);

    /**
     * Area Elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] COMPROTOTYPE_AREA_ELEMENTS = {};

    /**
     * Services in this Area.
     */
    public static final org.ccsds.moims.mo.mal.ServiceInfo[] COMPROTOTYPE_AREA_SERVICES = {
        org.ccsds.moims.mo.comprototype.eventtest.EventTestHelper.EVENTTEST_SERVICE,
        org.ccsds.moims.mo.comprototype.activitytest.ActivityTestHelper.ACTIVITYTEST_SERVICE,
        org.ccsds.moims.mo.comprototype.activityrelaymanagement.ActivityRelayManagementHelper.ACTIVITYRELAYMANAGEMENT_SERVICE,
        org.ccsds.moims.mo.comprototype.archivetest.ArchiveTestHelper.ARCHIVETEST_SERVICE,};

    /**
     * Area singleton instance.
     */
    public static final org.ccsds.moims.mo.mal.MALArea COMPROTOTYPE_AREA = new org.ccsds.moims.mo.mal.MALArea(COMPROTOTYPE_AREA_NUMBER, COMPROTOTYPE_AREA_NAME, COMPROTOTYPE_AREA_VERSION, COMPROTOTYPE_AREA_ELEMENTS, COMPROTOTYPE_AREA_SERVICES, new COMPrototypeElementFactory());

    private COMPrototypeHelper() {
        // Utility class; not meant to be instantiated.
    }

}
