package org.ccsds.moims.mo.mps;

/**
 * The CancelFailedException exception. The cancelRequest operation failed
 * to cancel the referenced RequestInstance.
 */
public final class CancelFailedException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "CANCEL_FAILED";

    /**
     * Constructs a new CancelFailedException exception.
     * 
     */
    public CancelFailedException() {
        super(MO_ERROR_NAME, MPSHelper.CANCEL_FAILED_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new CancelFailedException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public CancelFailedException(Object extraInformation) {
        super(MO_ERROR_NAME, MPSHelper.CANCEL_FAILED_ERROR_NUMBER, extraInformation);
    }

}
