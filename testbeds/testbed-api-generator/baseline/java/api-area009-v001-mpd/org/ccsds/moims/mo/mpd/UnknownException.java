package org.ccsds.moims.mo.mpd;

/**
 * The UnknownException exception. The referenced item does not exist.
 */
public final class UnknownException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Unknown";

    /**
     * Constructs a new UnknownException exception.
     * 
     */
    public UnknownException() {
        super(MO_ERROR_NAME, MPDHelper.UNKNOWN_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new UnknownException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public UnknownException(Object extraInformation) {
        super(MO_ERROR_NAME, MPDHelper.UNKNOWN_ERROR_NUMBER, extraInformation);
    }

}
