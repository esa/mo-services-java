package org.ccsds.moims.mo.comprototype.archivetest;

/**
 * Helper class for ArchiveTest service.
 */
public class ArchiveTestServiceInfo extends org.ccsds.moims.mo.com.COMService {

    /**
     * Service number literal.
     */
    public static final int _ARCHIVETEST_SERVICE_NUMBER = 6;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort ARCHIVETEST_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ARCHIVETEST_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier ARCHIVETEST_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ArchiveTest");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            200, 1, ARCHIVETEST_SERVICE_NUMBER);

    /**
     * Operation number literal for operation RESET.
     */
    public static final int _RESET_OP_NUMBER = 100;

    /**
     * Operation number instance for operation RESET.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort RESET_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_RESET_OP_NUMBER);

    /**
     * Operation instance for operation RESET.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation RESET_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            RESET_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("reset"), 
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            "Resets all values back to their default value.");

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] ARCHIVETEST_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{RESET_OP};

    /**
     * Literal for object TESTOBJECT.
     */
    @Deprecated
    public static final int _TESTOBJECT_OBJECT_NUMBER = 1;

    /**
     * Instance for object TESTOBJECT.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort TESTOBJECT_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTOBJECT_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier TESTOBJECT_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("TestObject");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType TESTOBJECT_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(200), ARCHIVETEST_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), TESTOBJECT_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject TESTOBJECT_OBJECT = new org.ccsds.moims.mo.com.COMObject(TESTOBJECT_OBJECT_TYPE, TESTOBJECT_OBJECT_NAME, org.ccsds.moims.mo.comprototype.archivetest.structures.TestObjectPayload.SHORT_FORM, false, null, false, null, false);

    /**
     * Literal for object TESTOBJECT2.
     */
    @Deprecated
    public static final int _TESTOBJECT2_OBJECT_NUMBER = 2;

    /**
     * Instance for object TESTOBJECT2.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort TESTOBJECT2_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTOBJECT2_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier TESTOBJECT2_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("TestObject2");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType TESTOBJECT2_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(200), ARCHIVETEST_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), TESTOBJECT2_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject TESTOBJECT2_OBJECT = new org.ccsds.moims.mo.com.COMObject(TESTOBJECT2_OBJECT_TYPE, TESTOBJECT2_OBJECT_NAME, org.ccsds.moims.mo.mal.structures.Attribute.INTEGER_SHORT_FORM, false, null, false, null, false);

    /**
     * Literal for object TESTOBJECT3.
     */
    @Deprecated
    public static final int _TESTOBJECT3_OBJECT_NUMBER = 3;

    /**
     * Instance for object TESTOBJECT3.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort TESTOBJECT3_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTOBJECT3_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier TESTOBJECT3_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("TestObject3");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType TESTOBJECT3_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(200), ARCHIVETEST_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), TESTOBJECT3_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject TESTOBJECT3_OBJECT = new org.ccsds.moims.mo.com.COMObject(TESTOBJECT3_OBJECT_TYPE, TESTOBJECT3_OBJECT_NAME, org.ccsds.moims.mo.comprototype.archivetest.structures.EnumeratedObject.SHORT_FORM, false, null, false, null, false);

    /**
     * Literal for object TESTOBJECT4.
     */
    @Deprecated
    public static final int _TESTOBJECT4_OBJECT_NUMBER = 4;

    /**
     * Instance for object TESTOBJECT4.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort TESTOBJECT4_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTOBJECT4_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier TESTOBJECT4_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("TestObject4");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType TESTOBJECT4_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(200), ARCHIVETEST_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), TESTOBJECT4_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject TESTOBJECT4_OBJECT = new org.ccsds.moims.mo.com.COMObject(TESTOBJECT4_OBJECT_TYPE, TESTOBJECT4_OBJECT_NAME, org.ccsds.moims.mo.mal.structures.Attribute.BLOB_SHORT_FORM, false, null, false, null, false);

    /**
     * Literal for object TESTOBJECT5.
     */
    @Deprecated
    public static final int _TESTOBJECT5_OBJECT_NUMBER = 5;

    /**
     * Instance for object TESTOBJECT5.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort TESTOBJECT5_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTOBJECT5_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier TESTOBJECT5_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("TestObject5");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType TESTOBJECT5_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(200), ARCHIVETEST_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), TESTOBJECT5_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject TESTOBJECT5_OBJECT = new org.ccsds.moims.mo.com.COMObject(TESTOBJECT5_OBJECT_TYPE, TESTOBJECT5_OBJECT_NAME, null, false, null, false, null, false);

    /**
     * Literal for object TESTOBJECT6.
     */
    @Deprecated
    public static final int _TESTOBJECT6_OBJECT_NUMBER = 6;

    /**
     * Instance for object TESTOBJECT6.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort TESTOBJECT6_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTOBJECT6_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier TESTOBJECT6_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("TestObject6");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType TESTOBJECT6_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(200), ARCHIVETEST_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), TESTOBJECT6_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject TESTOBJECT6_OBJECT = new org.ccsds.moims.mo.com.COMObject(TESTOBJECT6_OBJECT_TYPE, TESTOBJECT6_OBJECT_NAME, org.ccsds.moims.mo.mal.structures.Attribute.IDENTIFIER_SHORT_FORM, false, null, false, null, false);

    /**
     * Literal for object TESTOBJECT7.
     */
    @Deprecated
    public static final int _TESTOBJECT7_OBJECT_NUMBER = 7;

    /**
     * Instance for object TESTOBJECT7.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort TESTOBJECT7_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTOBJECT7_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier TESTOBJECT7_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("TestObject7");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType TESTOBJECT7_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(200), ARCHIVETEST_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), TESTOBJECT7_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject TESTOBJECT7_OBJECT = new org.ccsds.moims.mo.com.COMObject(TESTOBJECT7_OBJECT_TYPE, TESTOBJECT7_OBJECT_NAME, org.ccsds.moims.mo.mal.structures.Attribute.BLOB_SHORT_FORM, false, null, false, null, false);

    /**
     * Object instance.
     */
    public static final org.ccsds.moims.mo.com.COMObject[] COM_OBJECTS = {
        TESTOBJECT_OBJECT,
        TESTOBJECT2_OBJECT,
        TESTOBJECT3_OBJECT,
        TESTOBJECT4_OBJECT,
        TESTOBJECT5_OBJECT,
        TESTOBJECT6_OBJECT,
        TESTOBJECT7_OBJECT,};

    /**
     * Creates an instance of the ArchiveTest ServiceInfo.
     * 
     */
    public ArchiveTestServiceInfo() {
        super(SERVICE_KEY, ARCHIVETEST_SERVICE_NAME, ARCHIVETEST_SERVICE_ELEMENTS, OPERATIONS, COM_OBJECTS);
    }

    @Override
    public org.ccsds.moims.mo.mal.MALArea getArea() {
        return org.ccsds.moims.mo.comprototype.COMPrototypeHelper.COMPROTOTYPE_AREA;
    }

    @Override
    public org.ccsds.moims.mo.mal.MOErrorException generateMOError(int operationNumber,
            int errorNumber,
            Object extraInfo) {
        org.ccsds.moims.mo.mal.MOErrorException areaError = org.ccsds.moims.mo.comprototype.COMPrototypeHelper.generateMOError(errorNumber, extraInfo);
        return (areaError != null) ? areaError : org.ccsds.moims.mo.mal.MALHelper.generateMOError(errorNumber, extraInfo);
    }

}
