package org.ccsds.moims.mo.mal;

/**
 * The UnsupportedAreaVersionException exception. The destination does not
 * support the selected area version.
 */
public final class UnsupportedAreaVersionException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Unsupported Area Version";

    /**
     * Constructs a new UnsupportedAreaVersionException exception.
     * 
     */
    public UnsupportedAreaVersionException() {
        super(MO_ERROR_NAME, MALHelper.UNSUPPORTED_AREA_VERSION_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new UnsupportedAreaVersionException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public UnsupportedAreaVersionException(Object extraInformation) {
        super(MO_ERROR_NAME, MALHelper.UNSUPPORTED_AREA_VERSION_ERROR_NUMBER, extraInformation);
    }

}
