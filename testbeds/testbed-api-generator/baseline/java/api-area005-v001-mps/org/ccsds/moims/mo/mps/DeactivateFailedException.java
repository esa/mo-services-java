package org.ccsds.moims.mo.mps;

/**
 * The DeactivateFailedException exception. The deactivatePlan operation failed.
 */
public final class DeactivateFailedException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "DEACTIVATE_FAILED";

    /**
     * Constructs a new DeactivateFailedException exception.
     * 
     */
    public DeactivateFailedException() {
        super(MO_ERROR_NAME, MPSHelper.DEACTIVATE_FAILED_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new DeactivateFailedException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public DeactivateFailedException(Object extraInformation) {
        super(MO_ERROR_NAME, MPSHelper.DEACTIVATE_FAILED_ERROR_NUMBER, extraInformation);
    }

}
