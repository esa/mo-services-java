package org.ccsds.moims.mo.comprototype1.test1;

/**
 * Helper class for Test1 service.
 */
public class Test1ServiceInfo extends org.ccsds.moims.mo.com.COMService {

    /**
     * Service number literal.
     */
    public static final int _TEST1_SERVICE_NUMBER = 1;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort TEST1_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TEST1_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier TEST1_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("Test1");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            201, 1, TEST1_SERVICE_NUMBER);

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] TEST1_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{};

    /**
     * Literal for object TESTOBJECTA.
     */
    @Deprecated
    public static final int _TESTOBJECTA_OBJECT_NUMBER = 1;

    /**
     * Instance for object TESTOBJECTA.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort TESTOBJECTA_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTOBJECTA_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier TESTOBJECTA_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("TestObjectA");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType TESTOBJECTA_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(201), TEST1_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), TESTOBJECTA_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject TESTOBJECTA_OBJECT = new org.ccsds.moims.mo.com.COMObject(TESTOBJECTA_OBJECT_TYPE, TESTOBJECTA_OBJECT_NAME, org.ccsds.moims.mo.comprototype.archivetest.structures.TestObjectPayload.SHORT_FORM, false, null, false, null, false);

    /**
     * Object instance.
     */
    public static final org.ccsds.moims.mo.com.COMObject[] COM_OBJECTS = {
        TESTOBJECTA_OBJECT,};

    /**
     * Creates an instance of the Test1 ServiceInfo.
     * 
     */
    public Test1ServiceInfo() {
        super(SERVICE_KEY, TEST1_SERVICE_NAME, TEST1_SERVICE_ELEMENTS, OPERATIONS, COM_OBJECTS);
    }

    @Override
    public org.ccsds.moims.mo.mal.MALArea getArea() {
        return org.ccsds.moims.mo.comprototype1.COMPrototype1Helper.COMPROTOTYPE1_AREA;
    }

    @Override
    public org.ccsds.moims.mo.mal.MOErrorException generateMOError(int operationNumber,
            int errorNumber,
            Object extraInfo) {
        org.ccsds.moims.mo.mal.MOErrorException areaError = org.ccsds.moims.mo.comprototype1.COMPrototype1Helper.generateMOError(errorNumber, extraInfo);
        return (areaError != null) ? areaError : org.ccsds.moims.mo.mal.MALHelper.generateMOError(errorNumber, extraInfo);
    }

}
