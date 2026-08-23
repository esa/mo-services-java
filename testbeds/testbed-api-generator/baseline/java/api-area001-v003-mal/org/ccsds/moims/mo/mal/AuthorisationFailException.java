package org.ccsds.moims.mo.mal;

/**
 * The AuthorisationFailException exception. A failure in the MAL to authorise
 * the message.
 */
public final class AuthorisationFailException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Authorisation Fail";

    /**
     * Constructs a new AuthorisationFailException exception.
     * 
     */
    public AuthorisationFailException() {
        super(MO_ERROR_NAME, MALHelper.AUTHORISATION_FAIL_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new AuthorisationFailException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public AuthorisationFailException(Object extraInformation) {
        super(MO_ERROR_NAME, MALHelper.AUTHORISATION_FAIL_ERROR_NUMBER, extraInformation);
    }

}
