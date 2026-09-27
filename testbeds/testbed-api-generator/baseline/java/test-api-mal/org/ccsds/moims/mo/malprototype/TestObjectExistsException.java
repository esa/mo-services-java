package org.ccsds.moims.mo.malprototype;

/**
 * The TestObjectExistsException exception. MO Object already exists.
 */
public final class TestObjectExistsException extends org.ccsds.moims.mo.mal.MOErrorException {

    private static final String MO_ERROR_NAME = "TEST_OBJECT_EXISTS";

    /**
     * Constructs a new TestObjectExistsException exception.
     * 
     */
    public TestObjectExistsException() {
        super(MO_ERROR_NAME, MALPrototypeHelper.TEST_OBJECT_EXISTS_ERROR_NUMBER, "");
    }

    /**
     * Constructs a new TestObjectExistsException exception.
     * 
     * @param extraInformation The extraInformation of the exception.
     */
    public TestObjectExistsException(Object extraInformation) {
        super(MO_ERROR_NAME, MALPrototypeHelper.TEST_OBJECT_EXISTS_ERROR_NUMBER, extraInformation);
    }

}
