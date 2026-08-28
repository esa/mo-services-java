package org.ccsds.moims.mo.mal;

/**
 * The AuthenticationFailedException exception. A failure to authenticate
 * the message correctly.
 */
public final class AuthenticationFailedException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Authentication Failed";

    /**
     * Constructs a new AuthenticationFailedException exception.
     * 
     */
    public AuthenticationFailedException() {
        super(MO_ERROR_NAME, MALHelper.AUTHENTICATION_FAILED_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new AuthenticationFailedException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public AuthenticationFailedException(Object extraInformation) {
        super(MO_ERROR_NAME, MALHelper.AUTHENTICATION_FAILED_ERROR_NUMBER, extraInformation);
    }

}
