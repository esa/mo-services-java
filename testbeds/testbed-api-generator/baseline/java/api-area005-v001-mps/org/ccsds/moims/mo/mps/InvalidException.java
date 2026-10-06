package org.ccsds.moims.mo.mps;

import org.ccsds.moims.mo.mal.MOErrorException;

/**
 * The InvalidException exception. One or more fields in the message contain
 * invalid values.
 */
public final class InvalidException extends MOErrorException {

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
