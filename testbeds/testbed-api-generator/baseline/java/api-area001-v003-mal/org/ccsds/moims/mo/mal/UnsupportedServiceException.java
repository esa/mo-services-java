package org.ccsds.moims.mo.mal;

/**
 * The UnsupportedServiceException exception. The destination does not support
 * the selected service.
 */
public final class UnsupportedServiceException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Unsupported Service";

    /**
     * Constructs a new UnsupportedServiceException exception.
     * 
     */
    public UnsupportedServiceException() {
        super(MO_ERROR_NAME, MALHelper.UNSUPPORTED_SERVICE_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new UnsupportedServiceException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public UnsupportedServiceException(Object extraInformation) {
        super(MO_ERROR_NAME, MALHelper.UNSUPPORTED_SERVICE_ERROR_NUMBER, extraInformation);
    }

}
