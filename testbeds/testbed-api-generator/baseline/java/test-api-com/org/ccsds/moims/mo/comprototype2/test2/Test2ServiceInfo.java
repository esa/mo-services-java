package org.ccsds.moims.mo.comprototype2.test2;

/**
 * Helper class for Test2 service.
 */
public class Test2ServiceInfo extends org.ccsds.moims.mo.com.COMService {

    /**
     * Service number literal.
     */
    public static final int _TEST2_SERVICE_NUMBER = 2;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort TEST2_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TEST2_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier TEST2_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("Test2");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            202, 1, TEST2_SERVICE_NUMBER);

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] TEST2_SERVICE_ELEMENTS = {};

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
    public static final org.ccsds.moims.mo.com.structures.ObjectType TESTOBJECTA_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(202), TEST2_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), TESTOBJECTA_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject TESTOBJECTA_OBJECT = new org.ccsds.moims.mo.com.COMObject(TESTOBJECTA_OBJECT_TYPE, TESTOBJECTA_OBJECT_NAME, org.ccsds.moims.mo.mal.structures.Attribute.BOOLEAN_SHORT_FORM, false, null, false, null, false);

    /**
     * Object instance.
     */
    public static final org.ccsds.moims.mo.com.COMObject[] COM_OBJECTS = {
        TESTOBJECTA_OBJECT,};

    /**
     * Creates an instance of the Test2 ServiceInfo.
     * 
     */
    public Test2ServiceInfo() {
        super(SERVICE_KEY, TEST2_SERVICE_NAME, TEST2_SERVICE_ELEMENTS, OPERATIONS, COM_OBJECTS);
    }

    @Override
    public org.ccsds.moims.mo.mal.MALArea getArea() {
        return org.ccsds.moims.mo.comprototype2.COMPrototype2Helper.COMPROTOTYPE2_AREA;
    }

    @Override
    public org.ccsds.moims.mo.mal.MOErrorException generateMOError(int errorNumber,
            Object extraInfo) {
        switch (errorNumber) {
        }
        return null;
    }

}
