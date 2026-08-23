package org.ccsds.moims.mo.com;

/**
 * Helper class for COM area.
 */
public class COMHelper {

    /**
     * Area number literal.
     */
    public static final int _COM_AREA_NUMBER = 2;

    /**
     * Area number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort COM_AREA_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_COM_AREA_NUMBER);

    /**
     * Area name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier COM_AREA_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("COM");

    /**
     * Area version literal.
     */
    public static final short _COM_AREA_VERSION = 1;

    /**
     * Area version instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UOctet COM_AREA_VERSION = new org.ccsds.moims.mo.mal.structures.UOctet(_COM_AREA_VERSION);

    /**
     * Area Elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] COM_AREA_ELEMENTS = {};

    /**
     * Services in this Area.
     */
    public static final org.ccsds.moims.mo.mal.ServiceInfo[] COM_AREA_SERVICES = {
        org.ccsds.moims.mo.com.event.EventHelper.EVENT_SERVICE,
        org.ccsds.moims.mo.com.archive.ArchiveHelper.ARCHIVE_SERVICE,
        org.ccsds.moims.mo.com.activitytracking.ActivityTrackingHelper.ACTIVITYTRACKING_SERVICE,};

    /**
     * Area singleton instance.
     */
    public static final org.ccsds.moims.mo.mal.MALArea COM_AREA = new org.ccsds.moims.mo.mal.MALArea(COM_AREA_NUMBER, COM_AREA_NAME, COM_AREA_VERSION, COM_AREA_ELEMENTS, COM_AREA_SERVICES, new COMElementFactory());

    /**
     * Error literal for error INVALID.
     */
    public static final long _INVALID_ERROR_NUMBER = 70000;

    /**
     * Error instance for error INVALID.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger INVALID_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_INVALID_ERROR_NUMBER);

    /**
     * Error literal for error DUPLICATE.
     */
    public static final long _DUPLICATE_ERROR_NUMBER = 70001;

    /**
     * Error instance for error DUPLICATE.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger DUPLICATE_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_DUPLICATE_ERROR_NUMBER);

    private COMHelper() {
        // Utility class; not meant to be instantiated.
    }

}
