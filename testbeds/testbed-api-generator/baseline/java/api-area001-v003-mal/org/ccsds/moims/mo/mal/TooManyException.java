package org.ccsds.moims.mo.mal;

/**
 * The TooManyException exception. Maximum number of subscriptions or providers
 * of a broker has been exceeded.
 */
public final class TooManyException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Too Many";

    /**
     * Constructs a new TooManyException exception.
     * 
     */
    public TooManyException() {
        super(MO_ERROR_NAME, MALHelper.TOO_MANY_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new TooManyException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public TooManyException(Object extraInformation) {
        super(MO_ERROR_NAME, MALHelper.TOO_MANY_ERROR_NUMBER, extraInformation);
    }

}
