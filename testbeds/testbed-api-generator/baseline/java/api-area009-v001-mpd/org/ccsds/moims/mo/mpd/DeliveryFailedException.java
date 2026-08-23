package org.ccsds.moims.mo.mpd;

/**
 * The DeliveryFailedException exception. An attempt to deliver a product
 * file to the nominated address failed.
 */
public final class DeliveryFailedException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Delivery Failed";

    /**
     * Constructs a new DeliveryFailedException exception.
     * 
     */
    public DeliveryFailedException() {
        super(MO_ERROR_NAME, MPDHelper.DELIVERY_FAILED_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new DeliveryFailedException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public DeliveryFailedException(Object extraInformation) {
        super(MO_ERROR_NAME, MPDHelper.DELIVERY_FAILED_ERROR_NUMBER, extraInformation);
    }

}
