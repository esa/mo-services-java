package org.ccsds.moims.mo.comprototype.eventtest;

/**
 * Helper class for EventTest service.
 */
public class EventTestServiceInfo extends org.ccsds.moims.mo.com.COMService {

    /**
     * Service number literal.
     */
    public static final int _EVENTTEST_SERVICE_NUMBER = 2;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort EVENTTEST_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_EVENTTEST_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier EVENTTEST_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("EventTest");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            200, 1, EVENTTEST_SERVICE_NUMBER);

    /**
     * Operation number literal for operation RESETTEST.
     */
    public static final int _RESETTEST_OP_NUMBER = 100;

    /**
     * Operation number instance for operation RESETTEST.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort RESETTEST_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_RESETTEST_OP_NUMBER);

    /**
     * Operation instance for operation RESETTEST.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation RESETTEST_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            RESETTEST_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("resetTest"), 
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("in1", true, org.ccsds.moims.mo.mal.structures.Attribute.STRING_SHORT_FORM, "")}, 
            "Resets the EventTest service provider.");

    /**
     * Operation number literal for operation CREATEINSTANCE.
     */
    public static final int _CREATEINSTANCE_OP_NUMBER = 101;

    /**
     * Operation number instance for operation CREATEINSTANCE.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort CREATEINSTANCE_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_CREATEINSTANCE_OP_NUMBER);

    /**
     * Operation instance for operation CREATEINSTANCE.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation CREATEINSTANCE_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            CREATEINSTANCE_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("createinstance"), 
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("in1", true, org.ccsds.moims.mo.mal.structures.Attribute.SHORT_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("in2", true, org.ccsds.moims.mo.mal.structures.Attribute.STRING_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("in3", true, org.ccsds.moims.mo.mal.structures.Attribute.STRING_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("in4", true, org.ccsds.moims.mo.mal.structures.Attribute.LONG_SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("output", true, org.ccsds.moims.mo.mal.structures.Attribute.LONG_SHORT_FORM, "")}, 
            "Creates an instance of one of the test objects: TestObject A or Test Object B Arg 1 - ObjectNumber (identifies object to be created) Arg 2 Domain Arg 3 Description Arg 4 parent instanceIdentifier returns object instance identifier. The provider will publish a TestObjectCreation event reporting the deletion");

    /**
     * Operation number literal for operation DELETEINSTANCE.
     */
    public static final int _DELETEINSTANCE_OP_NUMBER = 102;

    /**
     * Operation number instance for operation DELETEINSTANCE.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort DELETEINSTANCE_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_DELETEINSTANCE_OP_NUMBER);

    /**
     * Operation instance for operation DELETEINSTANCE.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation DELETEINSTANCE_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            DELETEINSTANCE_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("deleteInstance"), 
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("in1", true, org.ccsds.moims.mo.mal.structures.Attribute.SHORT_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("in2", true, org.ccsds.moims.mo.mal.structures.Attribute.STRING_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("in3", true, org.ccsds.moims.mo.mal.structures.Attribute.LONG_SHORT_FORM, "")}, 
            "deletes a test object instance.");

    /**
     * Operation number literal for operation UPDATEINSTANCE.
     */
    public static final int _UPDATEINSTANCE_OP_NUMBER = 103;

    /**
     * Operation number instance for operation UPDATEINSTANCE.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort UPDATEINSTANCE_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_UPDATEINSTANCE_OP_NUMBER);

    /**
     * Operation instance for operation UPDATEINSTANCE.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation UPDATEINSTANCE_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            UPDATEINSTANCE_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("updateInstance"), 
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("in1", true, org.ccsds.moims.mo.mal.structures.Attribute.LONG_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("in2", true, org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnum.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("in3", true, org.ccsds.moims.mo.mal.structures.Attribute.DURATION_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("in4", true, org.ccsds.moims.mo.mal.structures.ShortList.SHORT_FORM, "")}, 
            "Updates a number of fields on an instance of a test object. The provider will publish a TestObjectUpdate event reporting the updated attributes ");

    /**
     * Operation number literal for operation UPDATEINSTANCECOMPOSITE.
     */
    public static final int _UPDATEINSTANCECOMPOSITE_OP_NUMBER = 104;

    /**
     * Operation number instance for operation UPDATEINSTANCECOMPOSITE.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort UPDATEINSTANCECOMPOSITE_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_UPDATEINSTANCECOMPOSITE_OP_NUMBER);

    /**
     * Operation instance for operation UPDATEINSTANCECOMPOSITE.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation UPDATEINSTANCECOMPOSITE_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            UPDATEINSTANCECOMPOSITE_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("updateInstanceComposite"), 
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("in1", true, org.ccsds.moims.mo.mal.structures.Attribute.LONG_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("in2", true, org.ccsds.moims.mo.mal.structures.Attribute.UOCTET_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("in3", true, org.ccsds.moims.mo.mal.structures.Attribute.OCTET_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("in4", true, org.ccsds.moims.mo.mal.structures.Attribute.DOUBLE_SHORT_FORM, "")}, 
            "Updates the composite field on a instance of a test object. The provider will publish a TestObjectUpdate event reporting the updated attributes ");

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] EVENTTEST_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{RESETTEST_OP,
        CREATEINSTANCE_OP,
        DELETEINSTANCE_OP,
        UPDATEINSTANCE_OP,
        UPDATEINSTANCECOMPOSITE_OP};

    /**
     * Literal for object TESTOBJECTA.
     */
    @Deprecated
    public static final int _TESTOBJECTA_OBJECT_NUMBER = 2001;

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
    public static final org.ccsds.moims.mo.com.structures.ObjectType TESTOBJECTA_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(200), EVENTTEST_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), TESTOBJECTA_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject TESTOBJECTA_OBJECT = new org.ccsds.moims.mo.com.COMObject(TESTOBJECTA_OBJECT_TYPE, TESTOBJECTA_OBJECT_NAME, org.ccsds.moims.mo.comprototype.eventtest.structures.TestObjectA.SHORT_FORM, false, null, false, null, false);

    /**
     * Literal for object TESTOBJECTB.
     */
    @Deprecated
    public static final int _TESTOBJECTB_OBJECT_NUMBER = 2002;

    /**
     * Instance for object TESTOBJECTB.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort TESTOBJECTB_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTOBJECTB_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier TESTOBJECTB_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("TestObjectB");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType TESTOBJECTB_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(200), EVENTTEST_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), TESTOBJECTB_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject TESTOBJECTB_OBJECT = new org.ccsds.moims.mo.com.COMObject(TESTOBJECTB_OBJECT_TYPE, TESTOBJECTB_OBJECT_NAME, org.ccsds.moims.mo.comprototype.eventtest.structures.TestObjectB.SHORT_FORM, false, null, false, null, false);

    /**
     * Literal for object TESTOBJECTCREATION.
     */
    @Deprecated
    public static final int _TESTOBJECTCREATION_OBJECT_NUMBER = 3001;

    /**
     * Instance for object TESTOBJECTCREATION.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort TESTOBJECTCREATION_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTOBJECTCREATION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier TESTOBJECTCREATION_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("TestObjectCreation");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType TESTOBJECTCREATION_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(200), EVENTTEST_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), TESTOBJECTCREATION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject TESTOBJECTCREATION_OBJECT = new org.ccsds.moims.mo.com.COMObject(TESTOBJECTCREATION_OBJECT_TYPE, TESTOBJECTCREATION_OBJECT_NAME, org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectCreation.SHORT_FORM, false, null, true, null, true);

    /**
     * Literal for object TESTOBJECTDELETION.
     */
    @Deprecated
    public static final int _TESTOBJECTDELETION_OBJECT_NUMBER = 3002;

    /**
     * Instance for object TESTOBJECTDELETION.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort TESTOBJECTDELETION_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTOBJECTDELETION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier TESTOBJECTDELETION_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("TestObjectDeletion");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType TESTOBJECTDELETION_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(200), EVENTTEST_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), TESTOBJECTDELETION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject TESTOBJECTDELETION_OBJECT = new org.ccsds.moims.mo.com.COMObject(TESTOBJECTDELETION_OBJECT_TYPE, TESTOBJECTDELETION_OBJECT_NAME, org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectDeletion.SHORT_FORM, false, null, true, null, true);

    /**
     * Literal for object TESTOBJECTUPDATE.
     */
    @Deprecated
    public static final int _TESTOBJECTUPDATE_OBJECT_NUMBER = 3003;

    /**
     * Instance for object TESTOBJECTUPDATE.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort TESTOBJECTUPDATE_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTOBJECTUPDATE_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier TESTOBJECTUPDATE_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("TestObjectUpdate");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType TESTOBJECTUPDATE_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(200), EVENTTEST_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), TESTOBJECTUPDATE_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject TESTOBJECTUPDATE_OBJECT = new org.ccsds.moims.mo.com.COMObject(TESTOBJECTUPDATE_OBJECT_TYPE, TESTOBJECTUPDATE_OBJECT_NAME, org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectUpdate.SHORT_FORM, false, null, true, null, true);

    /**
     * Object instance.
     */
    public static final org.ccsds.moims.mo.com.COMObject[] COM_OBJECTS = {
        TESTOBJECTA_OBJECT,
        TESTOBJECTB_OBJECT,
        TESTOBJECTCREATION_OBJECT,
        TESTOBJECTDELETION_OBJECT,
        TESTOBJECTUPDATE_OBJECT,};

    /**
     * Creates an instance of the EventTest ServiceInfo.
     * 
     */
    public EventTestServiceInfo() {
        super(SERVICE_KEY, EVENTTEST_SERVICE_NAME, EVENTTEST_SERVICE_ELEMENTS, OPERATIONS, COM_OBJECTS);
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
