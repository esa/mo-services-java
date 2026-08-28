package org.ccsds.moims.mo.common;

/**
 * Helper class for Common area.
 */
public class CommonHelper {

    /**
     * Area number literal.
     */
    public static final int _COMMON_AREA_NUMBER = 3;

    /**
     * Area number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort COMMON_AREA_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_COMMON_AREA_NUMBER);

    /**
     * Area name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier COMMON_AREA_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("Common");

    /**
     * Area version literal.
     */
    public static final short _COMMON_AREA_VERSION = 1;

    /**
     * Area version instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UOctet COMMON_AREA_VERSION = new org.ccsds.moims.mo.mal.structures.UOctet(_COMMON_AREA_VERSION);

    /**
     * Area Elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] COMMON_AREA_ELEMENTS = {};

    /**
     * Services in this Area.
     */
    public static final org.ccsds.moims.mo.mal.ServiceInfo[] COMMON_AREA_SERVICES = {
        org.ccsds.moims.mo.common.directory.DirectoryHelper.DIRECTORY_SERVICE,
        org.ccsds.moims.mo.common.login.LoginHelper.LOGIN_SERVICE,
        org.ccsds.moims.mo.common.configuration.ConfigurationHelper.CONFIGURATION_SERVICE,};

    /**
     * Area singleton instance.
     */
    public static final org.ccsds.moims.mo.mal.MALArea COMMON_AREA = new org.ccsds.moims.mo.mal.MALArea(COMMON_AREA_NUMBER, COMMON_AREA_NAME, COMMON_AREA_VERSION, COMMON_AREA_ELEMENTS, COMMON_AREA_SERVICES, new CommonElementFactory());

    private CommonHelper() {
        // Utility class; not meant to be instantiated.
    }

}
