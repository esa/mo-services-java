package org.ccsds.moims.mo.mpd;

/**
 * Helper class for MPD area.
 */
public class MPDHelper {

    /**
     * Area number literal.
     */
    public static final int _MPD_AREA_NUMBER = 9;

    /**
     * Area number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort MPD_AREA_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_MPD_AREA_NUMBER);

    /**
     * Area name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier MPD_AREA_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("MPD");

    /**
     * Area version literal.
     */
    public static final short _MPD_AREA_VERSION = 1;

    /**
     * Area version instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UOctet MPD_AREA_VERSION = new org.ccsds.moims.mo.mal.structures.UOctet(_MPD_AREA_VERSION);

    /**
     * Area Elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] MPD_AREA_ELEMENTS = {};

    /**
     * Services in this Area.
     */
    public static final org.ccsds.moims.mo.mal.ServiceInfo[] MPD_AREA_SERVICES = {
        org.ccsds.moims.mo.mpd.productretrieval.ProductRetrievalHelper.PRODUCTRETRIEVAL_SERVICE,
        org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementHelper.ORDERMANAGEMENT_SERVICE,
        org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryHelper.PRODUCTORDERDELIVERY_SERVICE,};

    /**
     * Area singleton instance.
     */
    public static final org.ccsds.moims.mo.mal.MALArea MPD_AREA = new org.ccsds.moims.mo.mal.MALArea(MPD_AREA_NUMBER, MPD_AREA_NAME, MPD_AREA_VERSION, MPD_AREA_ELEMENTS, MPD_AREA_SERVICES, new MPDElementFactory());

    /**
     * Error literal for error INVALID.
     */
    public static final long _INVALID_ERROR_NUMBER = 1;

    /**
     * Error instance for error INVALID.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger INVALID_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_INVALID_ERROR_NUMBER);

    /**
     * Error literal for error DELIVERY_FAILED.
     */
    public static final long _DELIVERY_FAILED_ERROR_NUMBER = 2;

    /**
     * Error instance for error DELIVERY_FAILED.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger DELIVERY_FAILED_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_DELIVERY_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error ORDER_FAILED.
     */
    public static final long _ORDER_FAILED_ERROR_NUMBER = 3;

    /**
     * Error instance for error ORDER_FAILED.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger ORDER_FAILED_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_ORDER_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error UNKNOWN.
     */
    public static final long _UNKNOWN_ERROR_NUMBER = 4;

    /**
     * Error instance for error UNKNOWN.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger UNKNOWN_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_UNKNOWN_ERROR_NUMBER);

    /**
     * Error literal for error TOO_MANY.
     */
    public static final long _TOO_MANY_ERROR_NUMBER = 5;

    /**
     * Error instance for error TOO_MANY.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger TOO_MANY_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_TOO_MANY_ERROR_NUMBER);

    private MPDHelper() {
        // Utility class; not meant to be instantiated.
    }

}
