package org.ccsds.moims.mo.mps;

/**
 * The DeactivateSubplanFailedException exception. The deactivateSubPlan operation
 * failed.
 */
public final class DeactivateSubplanFailedException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "DEACTIVATE_SUBPLAN_FAILED";

    /**
     * Constructs a new DeactivateSubplanFailedException exception.
     * 
     */
    public DeactivateSubplanFailedException() {
        super(MO_ERROR_NAME, MPSHelper.DEACTIVATE_SUBPLAN_FAILED_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new DeactivateSubplanFailedException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public DeactivateSubplanFailedException(Object extraInformation) {
        super(MO_ERROR_NAME, MPSHelper.DEACTIVATE_SUBPLAN_FAILED_ERROR_NUMBER, extraInformation);
    }

}
