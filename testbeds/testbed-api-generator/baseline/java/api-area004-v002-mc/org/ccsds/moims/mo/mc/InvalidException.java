package org.ccsds.moims.mo.mc;

/**
 * The InvalidException exception. The input data or operation format is invalid
 * and does not meet required criteria.
 */
public final class InvalidException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Invalid";

    /**
     * Constructs a new InvalidException exception.
     * 
     */
    public InvalidException() {
        super(MO_ERROR_NAME, MCHelper.INVALID_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new InvalidException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public InvalidException(Object extraInformation) {
        super(MO_ERROR_NAME, MCHelper.INVALID_ERROR_NUMBER, extraInformation);
    }

}
