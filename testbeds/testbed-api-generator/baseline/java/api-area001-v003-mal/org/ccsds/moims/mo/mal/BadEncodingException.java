package org.ccsds.moims.mo.mal;

/**
 * The BadEncodingException exception. The destination was unable to decode
 * the message.
 */
public final class BadEncodingException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Bad Encoding";

    /**
     * Constructs a new BadEncodingException exception.
     * 
     */
    public BadEncodingException() {
        super(MO_ERROR_NAME, MALHelper.BAD_ENCODING_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new BadEncodingException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public BadEncodingException(Object extraInformation) {
        super(MO_ERROR_NAME, MALHelper.BAD_ENCODING_ERROR_NUMBER, extraInformation);
    }

}
