package org.ccsds.moims.mo.mpd;

/**
 * The OrderFailedException exception. Creation of a new product order failed.
 */
public final class OrderFailedException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Order Failed";

    /**
     * Constructs a new OrderFailedException exception.
     * 
     */
    public OrderFailedException() {
        super(MO_ERROR_NAME, MPDHelper.ORDER_FAILED_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new OrderFailedException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public OrderFailedException(Object extraInformation) {
        super(MO_ERROR_NAME, MPDHelper.ORDER_FAILED_ERROR_NUMBER, extraInformation);
    }

}
