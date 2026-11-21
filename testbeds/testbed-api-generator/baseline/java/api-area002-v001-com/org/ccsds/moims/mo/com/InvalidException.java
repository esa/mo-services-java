package org.ccsds.moims.mo.com;

import org.ccsds.moims.mo.mal.MOErrorException;

/**
 * The InvalidException exception. Operation specific.
 */
public final class InvalidException extends MOErrorException {

    private static final String MO_ERROR_NAME = "INVALID";

    /**
     * Constructs a new InvalidException exception.
     * 
     */
    public InvalidException() {
        super(MO_ERROR_NAME, COMHelper.INVALID_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new InvalidException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public InvalidException(Object extraInformation) {
        super(MO_ERROR_NAME, COMHelper.INVALID_ERROR_NUMBER, extraInformation);
    }

}
