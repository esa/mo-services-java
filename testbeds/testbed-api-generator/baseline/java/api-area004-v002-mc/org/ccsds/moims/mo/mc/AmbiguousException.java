package org.ccsds.moims.mo.mc;

/**
 * The AmbiguousException exception. The data or operation is ambiguous, requiring
 * clarification to proceed.
 */
public final class AmbiguousException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Ambiguous";

    /**
     * Constructs a new AmbiguousException exception.
     * 
     */
    public AmbiguousException() {
        super(MO_ERROR_NAME, MCHelper.AMBIGUOUS_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new AmbiguousException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public AmbiguousException(Object extraInformation) {
        super(MO_ERROR_NAME, MCHelper.AMBIGUOUS_ERROR_NUMBER, extraInformation);
    }

}
