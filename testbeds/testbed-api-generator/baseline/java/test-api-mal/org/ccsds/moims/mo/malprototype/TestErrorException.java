package org.ccsds.moims.mo.malprototype;

import org.ccsds.moims.mo.mal.MOErrorException;

/**
 * The TestErrorException exception. Fake error for testing.
 */
public final class TestErrorException extends MOErrorException {

    private static final String MO_ERROR_NAME = "TEST_ERROR";

    /**
     * Constructs a new TestErrorException exception.
     * 
     */
    public TestErrorException() {
        super(MO_ERROR_NAME, MALPrototypeHelper.TEST_ERROR_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new TestErrorException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public TestErrorException(Object extraInformation) {
        super(MO_ERROR_NAME, MALPrototypeHelper.TEST_ERROR_ERROR_NUMBER, extraInformation);
    }

}
