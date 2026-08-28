package org.ccsds.moims.mo.mps;

/**
 * The SubmitFailedException exception. The submitPlan operation failed as
 * the submitted plan was already terminated.
 */
public final class SubmitFailedException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "SUBMIT_FAILED";

    /**
     * Constructs a new SubmitFailedException exception.
     * 
     */
    public SubmitFailedException() {
        super(MO_ERROR_NAME, MPSHelper.SUBMIT_FAILED_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new SubmitFailedException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public SubmitFailedException(Object extraInformation) {
        super(MO_ERROR_NAME, MPSHelper.SUBMIT_FAILED_ERROR_NUMBER, extraInformation);
    }

}
