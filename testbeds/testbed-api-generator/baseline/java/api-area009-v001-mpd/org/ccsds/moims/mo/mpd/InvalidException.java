package org.ccsds.moims.mo.mpd;

/**
 * The InvalidException exception. A field in the message contains an invalid
 * value. If there are multiple errors, the first invalid field is reported.
 */
public final class InvalidException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Invalid";

    /**
     * Constructs a new InvalidException exception.
     * 
     */
    public InvalidException() {
        super(MO_ERROR_NAME, MPDHelper.INVALID_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new InvalidException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public InvalidException(Object extraInformation) {
        super(MO_ERROR_NAME, MPDHelper.INVALID_ERROR_NUMBER, extraInformation);
    }

}
