package org.ccsds.moims.mo.malprototype;

import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.malprototype.datatest.DataTestHelper;
import org.ccsds.moims.mo.malprototype.errortest.ErrorTestHelper;
import org.ccsds.moims.mo.malprototype.iptest.IPTestHelper;
import org.ccsds.moims.mo.malprototype.iptest2.IPTest2Helper;

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
    public static final UShort MALPROTOTYPE_AREA_NUMBER = new UShort(_MALPROTOTYPE_AREA_NUMBER);

    /**
     * Area name constant.
     */
    public static final Identifier MALPROTOTYPE_AREA_NAME = new Identifier("MALPrototype");

    /**
     * Area version literal.
     */
    public static final short _MALPROTOTYPE_AREA_VERSION = 1;

    /**
     * Area version instance.
     */
    public static final UOctet MALPROTOTYPE_AREA_VERSION = new UOctet(_MALPROTOTYPE_AREA_VERSION);

    /**
     * Area Elements.
     */
    public static final Element[] MALPROTOTYPE_AREA_ELEMENTS = {};

    /**
     * Services in this Area.
     */
    public static final ServiceInfo[] MALPROTOTYPE_AREA_SERVICES = {
        IPTestHelper.IPTEST_SERVICE,
        DataTestHelper.DATATEST_SERVICE,
        ErrorTestHelper.ERRORTEST_SERVICE,
        IPTest2Helper.IPTEST2_SERVICE,};

    /**
     * Area singleton instance.
     */
    public static final MALArea MALPROTOTYPE_AREA = new MALArea(MALPROTOTYPE_AREA_NUMBER, MALPROTOTYPE_AREA_NAME, MALPROTOTYPE_AREA_VERSION, MALPROTOTYPE_AREA_ELEMENTS, MALPROTOTYPE_AREA_SERVICES, new MALPrototypeElementFactory());

    /**
     * Error literal for error DATA_ERROR.
     */
    public static final long _DATA_ERROR_ERROR_NUMBER = 1;

    /**
     * Error instance for error DATA_ERROR.
     */
    public static final UInteger DATA_ERROR_ERROR_NUMBER = new UInteger(_DATA_ERROR_ERROR_NUMBER);

    /**
     * Error literal for error TEST_OBJECT_EXISTS.
     */
    public static final long _TEST_OBJECT_EXISTS_ERROR_NUMBER = 2;

    /**
     * Error instance for error TEST_OBJECT_EXISTS.
     */
    public static final UInteger TEST_OBJECT_EXISTS_ERROR_NUMBER = new UInteger(_TEST_OBJECT_EXISTS_ERROR_NUMBER);

    /**
     * Error literal for error TEST_ERROR.
     */
    public static final long _TEST_ERROR_ERROR_NUMBER = 3;

    /**
     * Error instance for error TEST_ERROR.
     */
    public static final UInteger TEST_ERROR_ERROR_NUMBER = new UInteger(_TEST_ERROR_ERROR_NUMBER);

    /**
     * Returns the exception of the error of this area with the given number.
     * 
     * @param errorNumber The number of the error.
     * @param extraInfo The extra information of the error.
     * @return the exception, or null if the area declares no error with that number
     */
    public static MOErrorException generateMOError(int errorNumber,
            Object extraInfo) {
        switch (errorNumber) {
            case 1:
                return new DataErrorException(extraInfo);
            case 2:
                return new TestObjectExistsException(extraInfo);
            case 3:
                return new TestErrorException(extraInfo);
        }
        return null;
    }

    private MALPrototypeHelper() {
        // Utility class; not meant to be instantiated.
    }

}
