package org.ccsds.moims.mo.com;

/**
 * The DuplicateException exception. Operation specific.
 */
public final class DuplicateException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "DUPLICATE";

    /**
     * Constructs a new DuplicateException exception.
     * 
     */
    public DuplicateException() {
        super(MO_ERROR_NAME, COMHelper.DUPLICATE_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new DuplicateException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public DuplicateException(Object extraInformation) {
        super(MO_ERROR_NAME, COMHelper.DUPLICATE_ERROR_NUMBER, extraInformation);
    }

}
