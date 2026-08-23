package org.ccsds.moims.mo.mps;

/**
 * The DeleteFailedException exception. The deleteActivity or deleteEvent
 * operation failed to delete the requested object.
 */
public final class DeleteFailedException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "DELETE_FAILED";

    /**
     * Constructs a new DeleteFailedException exception.
     * 
     */
    public DeleteFailedException() {
        super(MO_ERROR_NAME, MPSHelper.DELETE_FAILED_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new DeleteFailedException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public DeleteFailedException(Object extraInformation) {
        super(MO_ERROR_NAME, MPSHelper.DELETE_FAILED_ERROR_NUMBER, extraInformation);
    }

}
