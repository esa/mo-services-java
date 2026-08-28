package org.ccsds.moims.mo.mps;

/**
 * The ActivateFailedException exception. The activatePlan operation failed
 * as the activation was outside the validity period of the Plan, or the start
 * of the planPeriod had already passed.  .
 */
public final class ActivateFailedException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "ACTIVATE_FAILED";

    /**
     * Constructs a new ActivateFailedException exception.
     * 
     */
    public ActivateFailedException() {
        super(MO_ERROR_NAME, MPSHelper.ACTIVATE_FAILED_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new ActivateFailedException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public ActivateFailedException(Object extraInformation) {
        super(MO_ERROR_NAME, MPSHelper.ACTIVATE_FAILED_ERROR_NUMBER, extraInformation);
    }

}
