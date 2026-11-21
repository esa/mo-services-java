package org.ccsds.moims.mo.malprototype;

import org.ccsds.moims.mo.mal.MOErrorException;

/**
 * The DataErrorException exception. Data interoperability error.
 */
public final class DataErrorException extends MOErrorException {

    private static final String MO_ERROR_NAME = "DATA_ERROR";

    /**
     * Constructs a new DataErrorException exception.
     * 
     */
    public DataErrorException() {
        super(MO_ERROR_NAME, MALPrototypeHelper.DATA_ERROR_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new DataErrorException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public DataErrorException(Object extraInformation) {
        super(MO_ERROR_NAME, MALPrototypeHelper.DATA_ERROR_ERROR_NUMBER, extraInformation);
    }

}
