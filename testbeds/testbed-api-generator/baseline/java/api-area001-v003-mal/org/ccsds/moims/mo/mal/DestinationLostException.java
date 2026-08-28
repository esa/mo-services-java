package org.ccsds.moims.mo.mal;

/**
 * The DestinationLostException exception. Destination lost halfway through
 * conversation.
 */
public final class DestinationLostException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Destination Lost";

    /**
     * Constructs a new DestinationLostException exception.
     * 
     */
    public DestinationLostException() {
        super(MO_ERROR_NAME, MALHelper.DESTINATION_LOST_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new DestinationLostException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public DestinationLostException(Object extraInformation) {
        super(MO_ERROR_NAME, MALHelper.DESTINATION_LOST_ERROR_NUMBER, extraInformation);
    }

}
