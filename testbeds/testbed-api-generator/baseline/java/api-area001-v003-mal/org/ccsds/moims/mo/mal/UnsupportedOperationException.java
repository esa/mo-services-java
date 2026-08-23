package org.ccsds.moims.mo.mal;

/**
 * The UnsupportedOperationException exception. The destination does not support
 * the selected operation.
 */
public final class UnsupportedOperationException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Unsupported Operation";

    /**
     * Constructs a new UnsupportedOperationException exception.
     * 
     */
    public UnsupportedOperationException() {
        super(MO_ERROR_NAME, MALHelper.UNSUPPORTED_OPERATION_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new UnsupportedOperationException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public UnsupportedOperationException(Object extraInformation) {
        super(MO_ERROR_NAME, MALHelper.UNSUPPORTED_OPERATION_ERROR_NUMBER, extraInformation);
    }

}
