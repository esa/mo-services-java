package org.ccsds.moims.mo.mc;

/**
 * The DuplicateException exception. The entry or operation is a duplicate
 * of an existing record, violating uniqueness.
 */
public final class DuplicateException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Duplicate";

    /**
     * Constructs a new DuplicateException exception.
     * 
     */
    public DuplicateException() {
        super(MO_ERROR_NAME, MCHelper.DUPLICATE_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new DuplicateException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public DuplicateException(Object extraInformation) {
        super(MO_ERROR_NAME, MCHelper.DUPLICATE_ERROR_NUMBER, extraInformation);
    }

}
