package org.ccsds.moims.mo.mal;

/**
 * The DeliveryFailedException exception. Confirmed communication error.
 */
public final class DeliveryFailedException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Delivery Failed";

    /**
     * Constructs a new DeliveryFailedException exception.
     * 
     */
    public DeliveryFailedException() {
        super(MO_ERROR_NAME, MALHelper.DELIVERY_FAILED_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new DeliveryFailedException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public DeliveryFailedException(Object extraInformation) {
        super(MO_ERROR_NAME, MALHelper.DELIVERY_FAILED_ERROR_NUMBER, extraInformation);
    }

}
