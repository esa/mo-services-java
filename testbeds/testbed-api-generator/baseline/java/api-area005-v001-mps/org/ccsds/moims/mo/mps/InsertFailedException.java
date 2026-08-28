package org.ccsds.moims.mo.mps;

/**
 * The InsertFailedException exception. The insertActivity or insertEvent
 * operation failed to insert the requested object.
 */
public final class InsertFailedException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "INSERT_FAILED";

    /**
     * Constructs a new InsertFailedException exception.
     * 
     */
    public InsertFailedException() {
        super(MO_ERROR_NAME, MPSHelper.INSERT_FAILED_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new InsertFailedException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public InsertFailedException(Object extraInformation) {
        super(MO_ERROR_NAME, MPSHelper.INSERT_FAILED_ERROR_NUMBER, extraInformation);
    }

}
