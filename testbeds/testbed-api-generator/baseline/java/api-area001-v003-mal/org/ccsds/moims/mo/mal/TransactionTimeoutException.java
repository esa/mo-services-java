package org.ccsds.moims.mo.mal;

/**
 * The TransactionTimeoutException exception. The interaction exceeded a certain
 * timeout duration.
 */
public final class TransactionTimeoutException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "Transaction Timeout";

    /**
     * Constructs a new TransactionTimeoutException exception.
     * 
     */
    public TransactionTimeoutException() {
        super(MO_ERROR_NAME, MALHelper.TRANSACTION_TIMEOUT_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new TransactionTimeoutException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public TransactionTimeoutException(Object extraInformation) {
        super(MO_ERROR_NAME, MALHelper.TRANSACTION_TIMEOUT_ERROR_NUMBER, extraInformation);
    }

}
