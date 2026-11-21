package org.ccsds.moims.mo.mps;

import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mps.plandistribution.PlanDistributionHelper;
import org.ccsds.moims.mo.mps.planedit.PlanEditHelper;
import org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlHelper;
import org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementHelper;
import org.ccsds.moims.mo.mps.planningrequest.PlanningRequestHelper;

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
    public static final UShort MPS_AREA_NUMBER = new UShort(_MPS_AREA_NUMBER);

    /**
     * Area name constant.
     */
    public static final Identifier MPS_AREA_NAME = new Identifier("MPS");

    /**
     * Area version literal.
     */
    public static final short _MPS_AREA_VERSION = 1;

    /**
     * Area version instance.
     */
    public static final UOctet MPS_AREA_VERSION = new UOctet(_MPS_AREA_VERSION);

    /**
     * Area Elements.
     */
    public static final Element[] MPS_AREA_ELEMENTS = {};

    /**
     * Services in this Area.
     */
    public static final ServiceInfo[] MPS_AREA_SERVICES = {
        PlanningRequestHelper.PLANNINGREQUEST_SERVICE,
        PlanDistributionHelper.PLANDISTRIBUTION_SERVICE,
        PlanExecutionControlHelper.PLANEXECUTIONCONTROL_SERVICE,
        PlanInformationManagementHelper.PLANINFORMATIONMANAGEMENT_SERVICE,
        PlanEditHelper.PLANEDIT_SERVICE,};

    /**
     * Area singleton instance.
     */
    public static final MALArea MPS_AREA = new MALArea(MPS_AREA_NUMBER, MPS_AREA_NAME, MPS_AREA_VERSION, MPS_AREA_ELEMENTS, MPS_AREA_SERVICES, new MPSElementFactory());

    /**
     * Error literal for error INVALID.
     */
    public static final long _INVALID_ERROR_NUMBER = 1;

    /**
     * Error instance for error INVALID.
     */
    public static final UInteger INVALID_ERROR_NUMBER = new UInteger(_INVALID_ERROR_NUMBER);

    /**
     * Error literal for error CANCEL_FAILED.
     */
    public static final long _CANCEL_FAILED_ERROR_NUMBER = 2;

    /**
     * Error instance for error CANCEL_FAILED.
     */
    public static final UInteger CANCEL_FAILED_ERROR_NUMBER = new UInteger(_CANCEL_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error UPDATE_FAILED.
     */
    public static final long _UPDATE_FAILED_ERROR_NUMBER = 3;

    /**
     * Error instance for error UPDATE_FAILED.
     */
    public static final UInteger UPDATE_FAILED_ERROR_NUMBER = new UInteger(_UPDATE_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error REVOKE_FAILED.
     */
    public static final long _REVOKE_FAILED_ERROR_NUMBER = 4;

    /**
     * Error instance for error REVOKE_FAILED.
     */
    public static final UInteger REVOKE_FAILED_ERROR_NUMBER = new UInteger(_REVOKE_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error INSERT_FAILED.
     */
    public static final long _INSERT_FAILED_ERROR_NUMBER = 5;

    /**
     * Error instance for error INSERT_FAILED.
     */
    public static final UInteger INSERT_FAILED_ERROR_NUMBER = new UInteger(_INSERT_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error DELETE_FAILED.
     */
    public static final long _DELETE_FAILED_ERROR_NUMBER = 6;

    /**
     * Error instance for error DELETE_FAILED.
     */
    public static final UInteger DELETE_FAILED_ERROR_NUMBER = new UInteger(_DELETE_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error ACTIVATE_FAILED.
     */
    public static final long _ACTIVATE_FAILED_ERROR_NUMBER = 7;

    /**
     * Error instance for error ACTIVATE_FAILED.
     */
    public static final UInteger ACTIVATE_FAILED_ERROR_NUMBER = new UInteger(_ACTIVATE_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error DEACTIVATE_FAILED.
     */
    public static final long _DEACTIVATE_FAILED_ERROR_NUMBER = 8;

    /**
     * Error instance for error DEACTIVATE_FAILED.
     */
    public static final UInteger DEACTIVATE_FAILED_ERROR_NUMBER = new UInteger(_DEACTIVATE_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error SUBMIT_FAILED.
     */
    public static final long _SUBMIT_FAILED_ERROR_NUMBER = 9;

    /**
     * Error instance for error SUBMIT_FAILED.
     */
    public static final UInteger SUBMIT_FAILED_ERROR_NUMBER = new UInteger(_SUBMIT_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error UNSUPPORTED.
     */
    public static final long _UNSUPPORTED_ERROR_NUMBER = 10;

    /**
     * Error instance for error UNSUPPORTED.
     */
    public static final UInteger UNSUPPORTED_ERROR_NUMBER = new UInteger(_UNSUPPORTED_ERROR_NUMBER);

    /**
     * Error literal for error ACTIVATE_SUBPLAN_FAILED.
     */
    public static final long _ACTIVATE_SUBPLAN_FAILED_ERROR_NUMBER = 11;

    /**
     * Error instance for error ACTIVATE_SUBPLAN_FAILED.
     */
    public static final UInteger ACTIVATE_SUBPLAN_FAILED_ERROR_NUMBER = new UInteger(_ACTIVATE_SUBPLAN_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error DEACTIVATE_SUBPLAN_FAILED.
     */
    public static final long _DEACTIVATE_SUBPLAN_FAILED_ERROR_NUMBER = 12;

    /**
     * Error instance for error DEACTIVATE_SUBPLAN_FAILED.
     */
    public static final UInteger DEACTIVATE_SUBPLAN_FAILED_ERROR_NUMBER = new UInteger(_DEACTIVATE_SUBPLAN_FAILED_ERROR_NUMBER);

    /**
     * Returns the exception of the error of this area with the given number.
     * 
     * @param errorNumber The number of the error.
     * @param extraInfo The extra information of the error.
     * @return the exception, or null if the area declares no error with that number
     */
    public static MOErrorException generateMOError(int errorNumber,
            Object extraInfo) {
        switch (errorNumber) {
            case 1:
                return new InvalidException(extraInfo);
            case 2:
                return new CancelFailedException(extraInfo);
            case 3:
                return new UpdateFailedException(extraInfo);
            case 4:
                return new RevokeFailedException(extraInfo);
            case 5:
                return new InsertFailedException(extraInfo);
            case 6:
                return new DeleteFailedException(extraInfo);
            case 7:
                return new ActivateFailedException(extraInfo);
            case 8:
                return new DeactivateFailedException(extraInfo);
            case 9:
                return new SubmitFailedException(extraInfo);
            case 10:
                return new UnsupportedException(extraInfo);
            case 11:
                return new ActivateSubplanFailedException(extraInfo);
            case 12:
                return new DeactivateSubplanFailedException(extraInfo);
        }
        return null;
    }

    private MPSHelper() {
        // Utility class; not meant to be instantiated.
    }

}
