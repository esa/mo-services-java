package org.ccsds.moims.mo.mps;

/**
 * The UpdateFailedException exception. The update operation (to Request,
 * PlanStatus, Activity, Event or Resource) failed to update the referenced
 * object.
 */
public final class UpdateFailedException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "UPDATE_FAILED";

    /**
     * Constructs a new UpdateFailedException exception.
     * 
     */
    public UpdateFailedException() {
        super(MO_ERROR_NAME, MPSHelper.UPDATE_FAILED_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new UpdateFailedException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public UpdateFailedException(Object extraInformation) {
        super(MO_ERROR_NAME, MPSHelper.UPDATE_FAILED_ERROR_NUMBER, extraInformation);
    }

}
