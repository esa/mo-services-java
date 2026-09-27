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
    public static final short _MC_AREA_VERSION = 1;

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
        org.ccsds.moims.mo.mc.check.CheckHelper.CHECK_SERVICE,
        org.ccsds.moims.mo.mc.statistic.StatisticHelper.STATISTIC_SERVICE,
        org.ccsds.moims.mo.mc.aggregation.AggregationHelper.AGGREGATION_SERVICE,
        org.ccsds.moims.mo.mc.conversion.ConversionHelper.CONVERSION_SERVICE,
        org.ccsds.moims.mo.mc.group.GroupHelper.GROUP_SERVICE,};

    /**
     * Area singleton instance.
     */
    public static final org.ccsds.moims.mo.mal.MALArea MC_AREA = new org.ccsds.moims.mo.mal.MALArea(MC_AREA_NUMBER, MC_AREA_NAME, MC_AREA_VERSION, MC_AREA_ELEMENTS, MC_AREA_SERVICES, new MCElementFactory());

    /**
     * Error literal for error READONLY.
     */
    public static final long _READONLY_ERROR_NUMBER = 70020;

    /**
     * Error instance for error READONLY.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger READONLY_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_READONLY_ERROR_NUMBER);

    /**
     * Error literal for error REFERENCED.
     */
    public static final long _REFERENCED_ERROR_NUMBER = 70021;

    /**
     * Error instance for error REFERENCED.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger REFERENCED_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_REFERENCED_ERROR_NUMBER);

    /**
     * Returns the exception of the error of this area with the given number.
     * 
     * @param errorNumber The number of the error.
     * @param extraInfo The extra information of the error.
     * @return the exception, or null if the area declares no error with that number
     */
    public static org.ccsds.moims.mo.mal.MOErrorException generateMOError(int errorNumber,
            Object extraInfo) {
        switch (errorNumber) {
            case 70020:
                return new org.ccsds.moims.mo.mc.ReadonlyException(extraInfo);
            case 70021:
                return new org.ccsds.moims.mo.mc.ReferencedException(extraInfo);
        }
        return null;
    }

    private MCHelper() {
        // Utility class; not meant to be instantiated.
    }

}
