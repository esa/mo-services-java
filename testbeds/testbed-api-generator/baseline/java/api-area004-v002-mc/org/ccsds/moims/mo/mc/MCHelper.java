package org.ccsds.moims.mo.mc;

/**
 * Helper class for MC area.
 */
public class MCHelper {

    /**
     * Area number literal.
     */
    public static final int _MC_AREA_NUMBER = 4;

    /**
     * Area number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort MC_AREA_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_MC_AREA_NUMBER);

    /**
     * Area name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier MC_AREA_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("MC");

    /**
     * Area version literal.
     */
    public static final short _MC_AREA_VERSION = 2;

    /**
     * Area version instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UOctet MC_AREA_VERSION = new org.ccsds.moims.mo.mal.structures.UOctet(_MC_AREA_VERSION);

    /**
     * Area Elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] MC_AREA_ELEMENTS = {};

    /**
     * Services in this Area.
     */
    public static final org.ccsds.moims.mo.mal.ServiceInfo[] MC_AREA_SERVICES = {
        org.ccsds.moims.mo.mc.action.ActionHelper.ACTION_SERVICE,
        org.ccsds.moims.mo.mc.parameter.ParameterHelper.PARAMETER_SERVICE,
        org.ccsds.moims.mo.mc.alert.AlertHelper.ALERT_SERVICE,
        org.ccsds.moims.mo.mc.aggregation.AggregationHelper.AGGREGATION_SERVICE,
        org.ccsds.moims.mo.mc.packet.PacketHelper.PACKET_SERVICE,};

    /**
     * Area singleton instance.
     */
    public static final org.ccsds.moims.mo.mal.MALArea MC_AREA = new org.ccsds.moims.mo.mal.MALArea(MC_AREA_NUMBER, MC_AREA_NAME, MC_AREA_VERSION, MC_AREA_ELEMENTS, MC_AREA_SERVICES, new MCElementFactory());

    /**
     * Error literal for error READ_ONLY.
     */
    public static final long _READ_ONLY_ERROR_NUMBER = 1;

    /**
     * Error instance for error READ_ONLY.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger READ_ONLY_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_READ_ONLY_ERROR_NUMBER);

    /**
     * Error literal for error DUPLICATE.
     */
    public static final long _DUPLICATE_ERROR_NUMBER = 2;

    /**
     * Error instance for error DUPLICATE.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger DUPLICATE_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_DUPLICATE_ERROR_NUMBER);

    /**
     * Error literal for error INVALID.
     */
    public static final long _INVALID_ERROR_NUMBER = 3;

    /**
     * Error instance for error INVALID.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger INVALID_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_INVALID_ERROR_NUMBER);

    /**
     * Error literal for error REJECTED.
     */
    public static final long _REJECTED_ERROR_NUMBER = 4;

    /**
     * Error instance for error REJECTED.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger REJECTED_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_REJECTED_ERROR_NUMBER);

    /**
     * Error literal for error AMBIGUOUS.
     */
    public static final long _AMBIGUOUS_ERROR_NUMBER = 5;

    /**
     * Error instance for error AMBIGUOUS.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger AMBIGUOUS_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_AMBIGUOUS_ERROR_NUMBER);

    private MCHelper() {
        // Utility class; not meant to be instantiated.
    }

}
