package org.ccsds.moims.mo.mc;

/**
 * The ReferencedException exception. Operation specific.
 */
public final class ReferencedException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "REFERENCED";

    /**
     * Constructs a new ReferencedException exception.
     * 
     */
    public ReferencedException() {
        super(MO_ERROR_NAME, MCHelper.REFERENCED_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new ReferencedException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public ReferencedException(Object extraInformation) {
        super(MO_ERROR_NAME, MCHelper.REFERENCED_ERROR_NUMBER, extraInformation);
    }

}
