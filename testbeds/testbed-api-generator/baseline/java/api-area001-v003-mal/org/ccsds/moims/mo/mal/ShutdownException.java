package org.ccsds.moims.mo.mal;

/**
 * The ShutdownException exception. The component is being shutdown.
 */
public final class ShutdownException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Shutdown";

    /**
     * Constructs a new ShutdownException exception.
     * 
     */
    public ShutdownException() {
        super(MO_ERROR_NAME, MALHelper.SHUTDOWN_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new ShutdownException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public ShutdownException(Object extraInformation) {
        super(MO_ERROR_NAME, MALHelper.SHUTDOWN_ERROR_NUMBER, extraInformation);
    }

}
