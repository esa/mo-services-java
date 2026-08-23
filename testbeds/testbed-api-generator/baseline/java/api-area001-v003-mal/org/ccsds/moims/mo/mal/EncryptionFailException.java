package org.ccsds.moims.mo.mal;

/**
 * The EncryptionFailException exception. A failure in the MAL to encrypt/decrypt
 * the message.
 */
public final class EncryptionFailException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Encryption Fail";

    /**
     * Constructs a new EncryptionFailException exception.
     * 
     */
    public EncryptionFailException() {
        super(MO_ERROR_NAME, MALHelper.ENCRYPTION_FAIL_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new EncryptionFailException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public EncryptionFailException(Object extraInformation) {
        super(MO_ERROR_NAME, MALHelper.ENCRYPTION_FAIL_ERROR_NUMBER, extraInformation);
    }

}
