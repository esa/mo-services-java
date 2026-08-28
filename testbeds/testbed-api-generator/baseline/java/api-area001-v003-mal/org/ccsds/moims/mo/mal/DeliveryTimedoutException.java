package org.ccsds.moims.mo.mal;

/**
 * The DeliveryTimedoutException exception. Unconfirmed communication error.
 */
public final class DeliveryTimedoutException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Delivery Timedout";

    /**
     * Constructs a new DeliveryTimedoutException exception.
     * 
     */
    public DeliveryTimedoutException() {
        super(MO_ERROR_NAME, MALHelper.DELIVERY_TIMEDOUT_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new DeliveryTimedoutException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public DeliveryTimedoutException(Object extraInformation) {
        super(MO_ERROR_NAME, MALHelper.DELIVERY_TIMEDOUT_ERROR_NUMBER, extraInformation);
    }

}
