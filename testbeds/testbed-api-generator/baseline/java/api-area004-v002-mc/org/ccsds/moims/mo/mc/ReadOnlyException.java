package org.ccsds.moims.mo.mc;

/**
 * The ReadOnlyException exception. The operation attempted to modify read-only
 * data, which cannot be changed.
 */
public final class ReadOnlyException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Read Only";

    /**
     * Constructs a new ReadOnlyException exception.
     * 
     */
    public ReadOnlyException() {
        super(MO_ERROR_NAME, MCHelper.READ_ONLY_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new ReadOnlyException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public ReadOnlyException(Object extraInformation) {
        super(MO_ERROR_NAME, MCHelper.READ_ONLY_ERROR_NUMBER, extraInformation);
    }

}
