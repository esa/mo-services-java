package org.ccsds.moims.mo.mal;

/**
 * The InternalException exception. An internal error has occurred.
 */
public final class InternalException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Internal";

    /**
     * Constructs a new InternalException exception.
     * 
     */
    public InternalException() {
        super(MO_ERROR_NAME, MALHelper.INTERNAL_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new InternalException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public InternalException(Object extraInformation) {
        super(MO_ERROR_NAME, MALHelper.INTERNAL_ERROR_NUMBER, extraInformation);
    }

}
