package org.ccsds.moims.mo.mps;

/**
 * Helper class for MPS area.
 */
public class MPSHelper {

    /**
     * Area number literal.
     */
    public static final int _MPS_AREA_NUMBER = 5;

    /**
     * Area number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort MPS_AREA_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_MPS_AREA_NUMBER);

    /**
     * Area name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier MPS_AREA_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("MPS");

    /**
     * Area version literal.
     */
    public static final short _MPS_AREA_VERSION = 1;

    /**
     * Area version instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UOctet MPS_AREA_VERSION = new org.ccsds.moims.mo.mal.structures.UOctet(_MPS_AREA_VERSION);

    /**
     * Area Elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] MPS_AREA_ELEMENTS = {};

    /**
     * Services in this Area.
     */
    public static final org.ccsds.moims.mo.mal.ServiceInfo[] MPS_AREA_SERVICES = {
        org.ccsds.moims.mo.mps.planningrequest.PlanningRequestHelper.PLANNINGREQUEST_SERVICE,
        org.ccsds.moims.mo.mps.plandistribution.PlanDistributionHelper.PLANDISTRIBUTION_SERVICE,
        org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlHelper.PLANEXECUTIONCONTROL_SERVICE,
        org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementHelper.PLANINFORMATIONMANAGEMENT_SERVICE,
        org.ccsds.moims.mo.mps.planedit.PlanEditHelper.PLANEDIT_SERVICE,};

    /**
     * Area singleton instance.
     */
    public static final org.ccsds.moims.mo.mal.MALArea MPS_AREA = new org.ccsds.moims.mo.mal.MALArea(MPS_AREA_NUMBER, MPS_AREA_NAME, MPS_AREA_VERSION, MPS_AREA_ELEMENTS, MPS_AREA_SERVICES, new MPSElementFactory());

    /**
     * Error literal for error INVALID.
     */
    public static final long _INVALID_ERROR_NUMBER = 1;

    /**
     * Error instance for error INVALID.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger INVALID_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_INVALID_ERROR_NUMBER);

    /**
     * Error literal for error CANCEL_FAILED.
     */
    public static final long _CANCEL_FAILED_ERROR_NUMBER = 2;

    /**
     * Error instance for error CANCEL_FAILED.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger CANCEL_FAILED_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_CANCEL_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error UPDATE_FAILED.
     */
    public static final long _UPDATE_FAILED_ERROR_NUMBER = 3;

    /**
     * Error instance for error UPDATE_FAILED.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger UPDATE_FAILED_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_UPDATE_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error REVOKE_FAILED.
     */
    public static final long _REVOKE_FAILED_ERROR_NUMBER = 4;

    /**
     * Error instance for error REVOKE_FAILED.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger REVOKE_FAILED_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_REVOKE_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error INSERT_FAILED.
     */
    public static final long _INSERT_FAILED_ERROR_NUMBER = 5;

    /**
     * Error instance for error INSERT_FAILED.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger INSERT_FAILED_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_INSERT_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error DELETE_FAILED.
     */
    public static final long _DELETE_FAILED_ERROR_NUMBER = 6;

    /**
     * Error instance for error DELETE_FAILED.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger DELETE_FAILED_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_DELETE_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error ACTIVATE_FAILED.
     */
    public static final long _ACTIVATE_FAILED_ERROR_NUMBER = 7;

    /**
     * Error instance for error ACTIVATE_FAILED.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger ACTIVATE_FAILED_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_ACTIVATE_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error DEACTIVATE_FAILED.
     */
    public static final long _DEACTIVATE_FAILED_ERROR_NUMBER = 8;

    /**
     * Error instance for error DEACTIVATE_FAILED.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger DEACTIVATE_FAILED_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_DEACTIVATE_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error SUBMIT_FAILED.
     */
    public static final long _SUBMIT_FAILED_ERROR_NUMBER = 9;

    /**
     * Error instance for error SUBMIT_FAILED.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger SUBMIT_FAILED_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_SUBMIT_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error UNSUPPORTED.
     */
    public static final long _UNSUPPORTED_ERROR_NUMBER = 10;

    /**
     * Error instance for error UNSUPPORTED.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger UNSUPPORTED_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_UNSUPPORTED_ERROR_NUMBER);

    /**
     * Error literal for error ACTIVATE_SUBPLAN_FAILED.
     */
    public static final long _ACTIVATE_SUBPLAN_FAILED_ERROR_NUMBER = 11;

    /**
     * Error instance for error ACTIVATE_SUBPLAN_FAILED.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger ACTIVATE_SUBPLAN_FAILED_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_ACTIVATE_SUBPLAN_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error DEACTIVATE_SUBPLAN_FAILED.
     */
    public static final long _DEACTIVATE_SUBPLAN_FAILED_ERROR_NUMBER = 12;

    /**
     * Error instance for error DEACTIVATE_SUBPLAN_FAILED.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger DEACTIVATE_SUBPLAN_FAILED_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_DEACTIVATE_SUBPLAN_FAILED_ERROR_NUMBER);

    private MPSHelper() {
        // Utility class; not meant to be instantiated.
    }

}
