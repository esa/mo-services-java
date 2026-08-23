package org.ccsds.moims.mo.mal;

/**
 * The UnsupportedAreaException exception. The destination does not support
 * the selected area.
 */
public final class UnsupportedAreaException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Unsupported Area";

    /**
     * Constructs a new UnsupportedAreaException exception.
     * 
     */
    public UnsupportedAreaException() {
        super(MO_ERROR_NAME, MALHelper.UNSUPPORTED_AREA_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new UnsupportedAreaException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public UnsupportedAreaException(Object extraInformation) {
        super(MO_ERROR_NAME, MALHelper.UNSUPPORTED_AREA_ERROR_NUMBER, extraInformation);
    }

}
