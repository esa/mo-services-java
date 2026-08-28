package org.ccsds.moims.mo.mps;

/**
 * The InvalidException exception. One or more fields in the message contain
 * invalid values.
 */
public final class InvalidException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "INVALID";

    /**
     * Constructs a new InvalidException exception.
     * 
     */
    public InvalidException() {
        super(MO_ERROR_NAME, MPSHelper.INVALID_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new InvalidException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public InvalidException(Object extraInformation) {
        super(MO_ERROR_NAME, MPSHelper.INVALID_ERROR_NUMBER, extraInformation);
    }

}
