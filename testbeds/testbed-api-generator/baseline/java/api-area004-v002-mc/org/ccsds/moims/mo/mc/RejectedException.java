package org.ccsds.moims.mo.mc;

import org.ccsds.moims.mo.mal.MOErrorException;

/**
 * The RejectedException exception. The operation has been rejected due to
 * policy or validation rules.
 */
public final class RejectedException extends MOErrorException {

    private static final String MO_ERROR_NAME = "Rejected";

    /**
     * Constructs a new RejectedException exception.
     * 
     */
    public RejectedException() {
        super(MO_ERROR_NAME, MCHelper.REJECTED_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new RejectedException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public RejectedException(Object extraInformation) {
        super(MO_ERROR_NAME, MCHelper.REJECTED_ERROR_NUMBER, extraInformation);
    }

}
