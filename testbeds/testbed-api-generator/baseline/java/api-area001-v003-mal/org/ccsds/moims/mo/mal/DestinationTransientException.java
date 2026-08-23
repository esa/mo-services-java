package org.ccsds.moims.mo.mal;

/**
 * The DestinationTransientException exception. Destination middleware reports
 * destination application does not exist.
 */
public final class DestinationTransientException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Destination Transient";

    /**
     * Constructs a new DestinationTransientException exception.
     * 
     */
    public DestinationTransientException() {
        super(MO_ERROR_NAME, MALHelper.DESTINATION_TRANSIENT_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new DestinationTransientException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public DestinationTransientException(Object extraInformation) {
        super(MO_ERROR_NAME, MALHelper.DESTINATION_TRANSIENT_ERROR_NUMBER, extraInformation);
    }

}
