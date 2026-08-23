package org.ccsds.moims.mo.mps;

/**
 * The UnsupportedException exception. An optional data structure used in
 * the message is not supported by the service provider.
 */
public final class UnsupportedException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "UNSUPPORTED";

    /**
     * Constructs a new UnsupportedException exception.
     * 
     */
    public UnsupportedException() {
        super(MO_ERROR_NAME, MPSHelper.UNSUPPORTED_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new UnsupportedException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public UnsupportedException(Object extraInformation) {
        super(MO_ERROR_NAME, MPSHelper.UNSUPPORTED_ERROR_NUMBER, extraInformation);
    }

}
