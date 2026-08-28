package org.ccsds.moims.mo.mps;

/**
 * The ActivateSubplanFailedException exception. The activateSubPlan operation
 * failed.
 */
public final class ActivateSubplanFailedException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "ACTIVATE_SUBPLAN_FAILED";

    /**
     * Constructs a new ActivateSubplanFailedException exception.
     * 
     */
    public ActivateSubplanFailedException() {
        super(MO_ERROR_NAME, MPSHelper.ACTIVATE_SUBPLAN_FAILED_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new ActivateSubplanFailedException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public ActivateSubplanFailedException(Object extraInformation) {
        super(MO_ERROR_NAME, MPSHelper.ACTIVATE_SUBPLAN_FAILED_ERROR_NUMBER, extraInformation);
    }

}
