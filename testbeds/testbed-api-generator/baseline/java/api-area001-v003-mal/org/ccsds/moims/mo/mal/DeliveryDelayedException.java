package org.ccsds.moims.mo.mal;

/**
 * The DeliveryDelayedException exception. Message queued somewhere awaiting
 * contact.
 */
public final class DeliveryDelayedException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Delivery Delayed";

    /**
     * Constructs a new DeliveryDelayedException exception.
     * 
     */
    public DeliveryDelayedException() {
        super(MO_ERROR_NAME, MALHelper.DELIVERY_DELAYED_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new DeliveryDelayedException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public DeliveryDelayedException(Object extraInformation) {
        super(MO_ERROR_NAME, MALHelper.DELIVERY_DELAYED_ERROR_NUMBER, extraInformation);
    }

}
