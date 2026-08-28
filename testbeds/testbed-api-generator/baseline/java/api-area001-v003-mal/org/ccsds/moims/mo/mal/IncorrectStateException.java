package org.ccsds.moims.mo.mal;

/**
 * The IncorrectStateException exception. The destination was not in the correct
 * state for the received message.
 */
public final class IncorrectStateException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Incorrect State";

    /**
     * Constructs a new IncorrectStateException exception.
     * 
     */
    public IncorrectStateException() {
        super(MO_ERROR_NAME, MALHelper.INCORRECT_STATE_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new IncorrectStateException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public IncorrectStateException(Object extraInformation) {
        super(MO_ERROR_NAME, MALHelper.INCORRECT_STATE_ERROR_NUMBER, extraInformation);
    }

}
