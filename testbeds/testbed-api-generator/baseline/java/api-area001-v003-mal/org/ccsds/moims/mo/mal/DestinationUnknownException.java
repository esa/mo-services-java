package org.ccsds.moims.mo.mal;

/**
 * The DestinationUnknownException exception. Destination cannot be contacted.
 */
public final class DestinationUnknownException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Destination Unknown";

    /**
     * Constructs a new DestinationUnknownException exception.
     * 
     */
    public DestinationUnknownException() {
        super(MO_ERROR_NAME, MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new DestinationUnknownException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public DestinationUnknownException(Object extraInformation) {
        super(MO_ERROR_NAME, MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER, extraInformation);
    }

}
