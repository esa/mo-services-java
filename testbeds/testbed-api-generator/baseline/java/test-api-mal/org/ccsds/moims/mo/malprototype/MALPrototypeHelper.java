package org.ccsds.moims.mo.malprototype;

/**
 * Helper class for MALPrototype area.
 */
public class MALPrototypeHelper {

    /**
     * Area number literal.
     */
    public static final int _MALPROTOTYPE_AREA_NUMBER = 100;

    /**
     * Area number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort MALPROTOTYPE_AREA_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_MALPROTOTYPE_AREA_NUMBER);

    /**
     * Area name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier MALPROTOTYPE_AREA_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("MALPrototype");

    /**
     * Area version literal.
     */
    public static final short _MALPROTOTYPE_AREA_VERSION = 1;

    /**
     * Area version instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UOctet MALPROTOTYPE_AREA_VERSION = new org.ccsds.moims.mo.mal.structures.UOctet(_MALPROTOTYPE_AREA_VERSION);

    /**
     * Area Elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] MALPROTOTYPE_AREA_ELEMENTS = {};

    /**
     * Services in this Area.
     */
    public static final org.ccsds.moims.mo.mal.ServiceInfo[] MALPROTOTYPE_AREA_SERVICES = {
        org.ccsds.moims.mo.malprototype.iptest.IPTestHelper.IPTEST_SERVICE,
        org.ccsds.moims.mo.malprototype.datatest.DataTestHelper.DATATEST_SERVICE,
        org.ccsds.moims.mo.malprototype.errortest.ErrorTestHelper.ERRORTEST_SERVICE,
        org.ccsds.moims.mo.malprototype.iptest2.IPTest2Helper.IPTEST2_SERVICE,};

    /**
     * Area singleton instance.
     */
    public static final org.ccsds.moims.mo.mal.MALArea MALPROTOTYPE_AREA = new org.ccsds.moims.mo.mal.MALArea(MALPROTOTYPE_AREA_NUMBER, MALPROTOTYPE_AREA_NAME, MALPROTOTYPE_AREA_VERSION, MALPROTOTYPE_AREA_ELEMENTS, MALPROTOTYPE_AREA_SERVICES, new MALPrototypeElementFactory());

    /**
     * Error literal for error DATA_ERROR.
     */
    public static final long _DATA_ERROR_ERROR_NUMBER = 1;

    /**
     * Error instance for error DATA_ERROR.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger DATA_ERROR_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_DATA_ERROR_ERROR_NUMBER);

    /**
     * Error literal for error TEST_OBJECT_EXISTS.
     */
    public static final long _TEST_OBJECT_EXISTS_ERROR_NUMBER = 2;

    /**
     * Error instance for error TEST_OBJECT_EXISTS.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger TEST_OBJECT_EXISTS_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_TEST_OBJECT_EXISTS_ERROR_NUMBER);

    /**
     * Error literal for error TEST_ERROR.
     */
    public static final long _TEST_ERROR_ERROR_NUMBER = 3;

    /**
     * Error instance for error TEST_ERROR.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger TEST_ERROR_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_TEST_ERROR_ERROR_NUMBER);

    /**
     * Returns the exception of the error of this area with the given number.
     * 
     * @param errorNumber The number of the error.
     * @param extraInfo The extra information of the error.
     * @return the exception, or null if the area declares no error with that number
     */
    public static org.ccsds.moims.mo.mal.MOErrorException generateMOError(int errorNumber,
            Object extraInfo) {
        switch (errorNumber) {
            case 1:
                return new org.ccsds.moims.mo.malprototype.DataErrorException(extraInfo);
            case 2:
                return new org.ccsds.moims.mo.malprototype.TestObjectExistsException(extraInfo);
            case 3:
                return new org.ccsds.moims.mo.malprototype.TestErrorException(extraInfo);
        }
        return null;
    }

    private MALPrototypeHelper() {
        // Utility class; not meant to be instantiated.
    }

}
