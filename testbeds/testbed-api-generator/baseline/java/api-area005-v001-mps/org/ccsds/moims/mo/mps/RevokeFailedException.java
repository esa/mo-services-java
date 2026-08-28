package org.ccsds.moims.mo.mps;

/**
 * The RevokeFailedException exception. The revokePlan operation failed to
 * revoke the referenced Plan, for example because it has already started
 * executing.
 */
public final class RevokeFailedException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "REVOKE_FAILED";

    /**
     * Constructs a new RevokeFailedException exception.
     * 
     */
    public RevokeFailedException() {
        super(MO_ERROR_NAME, MPSHelper.REVOKE_FAILED_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new RevokeFailedException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public RevokeFailedException(Object extraInformation) {
        super(MO_ERROR_NAME, MPSHelper.REVOKE_FAILED_ERROR_NUMBER, extraInformation);
    }

}
