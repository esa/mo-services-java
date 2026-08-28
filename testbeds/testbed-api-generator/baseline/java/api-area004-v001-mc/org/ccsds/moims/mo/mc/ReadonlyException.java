package org.ccsds.moims.mo.mc;

/**
 * The ReadonlyException exception. Operation specific.
 */
public final class ReadonlyException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "READONLY";

    /**
     * Constructs a new ReadonlyException exception.
     * 
     */
    public ReadonlyException() {
        super(MO_ERROR_NAME, MCHelper.READONLY_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new ReadonlyException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public ReadonlyException(Object extraInformation) {
        super(MO_ERROR_NAME, MCHelper.READONLY_ERROR_NUMBER, extraInformation);
    }

}
