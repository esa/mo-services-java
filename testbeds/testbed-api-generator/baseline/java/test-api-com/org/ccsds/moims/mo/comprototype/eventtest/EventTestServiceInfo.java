package org.ccsds.moims.mo.comprototype.eventtest;

import org.ccsds.moims.mo.com.COMObject;
import org.ccsds.moims.mo.com.COMService;
import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.comprototype.COMPrototypeHelper;
import org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnum;
import org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectCreation;
import org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectDeletion;
import org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectUpdate;
import org.ccsds.moims.mo.comprototype.eventtest.structures.TestObjectA;
import org.ccsds.moims.mo.comprototype.eventtest.structures.TestObjectB;
import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.ServiceKey;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.ShortList;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * Helper class for EventTest service.
 */
public class EventTestServiceInfo extends COMService {

    /**
     * Service number literal.
     */
    public static final int _EVENTTEST_SERVICE_NUMBER = 2;

    /**
     * Service number instance.
     */
    public static final UShort EVENTTEST_SERVICE_NUMBER = new UShort(_EVENTTEST_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier EVENTTEST_SERVICE_NAME = new Identifier("EventTest");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            200, 1, EVENTTEST_SERVICE_NUMBER);

    /**
     * Operation number literal for operation RESETTEST.
     */
    public static final int _RESETTEST_OP_NUMBER = 100;

    /**
     * Operation number instance for operation RESETTEST.
     */
    private static final UShort RESETTEST_OP_NUMBER = new UShort(_RESETTEST_OP_NUMBER);

    /**
     * Operation instance for operation RESETTEST.
     */
    public static final MALSubmitOperation RESETTEST_OP = new MALSubmitOperation(SERVICE_KEY, 
            RESETTEST_OP_NUMBER, 
            new Identifier("resetTest"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("in1", true, Attribute.STRING_SHORT_FORM, "")}, 
            "Resets the EventTest service provider.");

    /**
     * Operation number literal for operation CREATEINSTANCE.
     */
    public static final int _CREATEINSTANCE_OP_NUMBER = 101;

    /**
     * Operation number instance for operation CREATEINSTANCE.
     */
    private static final UShort CREATEINSTANCE_OP_NUMBER = new UShort(_CREATEINSTANCE_OP_NUMBER);

    /**
     * Operation instance for operation CREATEINSTANCE.
     */
    public static final MALRequestOperation CREATEINSTANCE_OP = new MALRequestOperation(SERVICE_KEY, 
            CREATEINSTANCE_OP_NUMBER, 
            new Identifier("createinstance"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("in1", true, Attribute.SHORT_SHORT_FORM, ""),
                new OperationField("in2", true, Attribute.STRING_SHORT_FORM, ""),
                new OperationField("in3", true, Attribute.STRING_SHORT_FORM, ""),
                new OperationField("in4", true, Attribute.LONG_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output", true, Attribute.LONG_SHORT_FORM, "")}, 
            "Creates an instance of one of the test objects: TestObject A or Test Object B Arg 1 - ObjectNumber (identifies object to be created) Arg 2 Domain Arg 3 Description Arg 4 parent instanceIdentifier returns object instance identifier. The provider will publish a TestObjectCreation event reporting the deletion");

    /**
     * Operation number literal for operation DELETEINSTANCE.
     */
    public static final int _DELETEINSTANCE_OP_NUMBER = 102;

    /**
     * Operation number instance for operation DELETEINSTANCE.
     */
    private static final UShort DELETEINSTANCE_OP_NUMBER = new UShort(_DELETEINSTANCE_OP_NUMBER);

    /**
     * Operation instance for operation DELETEINSTANCE.
     */
    public static final MALSubmitOperation DELETEINSTANCE_OP = new MALSubmitOperation(SERVICE_KEY, 
            DELETEINSTANCE_OP_NUMBER, 
            new Identifier("deleteInstance"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("in1", true, Attribute.SHORT_SHORT_FORM, ""),
                new OperationField("in2", true, Attribute.STRING_SHORT_FORM, ""),
                new OperationField("in3", true, Attribute.LONG_SHORT_FORM, "")}, 
            "deletes a test object instance.");

    /**
     * Operation number literal for operation UPDATEINSTANCE.
     */
    public static final int _UPDATEINSTANCE_OP_NUMBER = 103;

    /**
     * Operation number instance for operation UPDATEINSTANCE.
     */
    private static final UShort UPDATEINSTANCE_OP_NUMBER = new UShort(_UPDATEINSTANCE_OP_NUMBER);

    /**
     * Operation instance for operation UPDATEINSTANCE.
     */
    public static final MALSubmitOperation UPDATEINSTANCE_OP = new MALSubmitOperation(SERVICE_KEY, 
            UPDATEINSTANCE_OP_NUMBER, 
            new Identifier("updateInstance"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("in1", true, Attribute.LONG_SHORT_FORM, ""),
                new OperationField("in2", true, BasicEnum.SHORT_FORM, ""),
                new OperationField("in3", true, Attribute.DURATION_SHORT_FORM, ""),
                new OperationField("in4", true, ShortList.SHORT_FORM, "")}, 
            "Updates a number of fields on an instance of a test object. The provider will publish a TestObjectUpdate event reporting the updated attributes ");

    /**
     * Operation number literal for operation UPDATEINSTANCECOMPOSITE.
     */
    public static final int _UPDATEINSTANCECOMPOSITE_OP_NUMBER = 104;

    /**
     * Operation number instance for operation UPDATEINSTANCECOMPOSITE.
     */
    private static final UShort UPDATEINSTANCECOMPOSITE_OP_NUMBER = new UShort(_UPDATEINSTANCECOMPOSITE_OP_NUMBER);

    /**
     * Operation instance for operation UPDATEINSTANCECOMPOSITE.
     */
    public static final MALSubmitOperation UPDATEINSTANCECOMPOSITE_OP = new MALSubmitOperation(SERVICE_KEY, 
            UPDATEINSTANCECOMPOSITE_OP_NUMBER, 
            new Identifier("updateInstanceComposite"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("in1", true, Attribute.LONG_SHORT_FORM, ""),
                new OperationField("in2", true, Attribute.UOCTET_SHORT_FORM, ""),
                new OperationField("in3", true, Attribute.OCTET_SHORT_FORM, ""),
                new OperationField("in4", true, Attribute.DOUBLE_SHORT_FORM, "")}, 
            "Updates the composite field on a instance of a test object. The provider will publish a TestObjectUpdate event reporting the updated attributes ");

    /**
     * Area elements.
     */
    public static final Element[] EVENTTEST_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{RESETTEST_OP,
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
    public static final UShort TESTOBJECTA_OBJECT_NUMBER = new UShort(_TESTOBJECTA_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier TESTOBJECTA_OBJECT_NAME = new Identifier("TestObjectA");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType TESTOBJECTA_OBJECT_TYPE = new ObjectType(new UShort(200), EVENTTEST_SERVICE_NUMBER, new UOctet(1), TESTOBJECTA_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject TESTOBJECTA_OBJECT = new COMObject(TESTOBJECTA_OBJECT_TYPE, TESTOBJECTA_OBJECT_NAME, TestObjectA.SHORT_FORM, false, null, false, null, false);

    /**
     * Literal for object TESTOBJECTB.
     */
    @Deprecated
    public static final int _TESTOBJECTB_OBJECT_NUMBER = 2002;

    /**
     * Instance for object TESTOBJECTB.
     */
    @Deprecated
    public static final UShort TESTOBJECTB_OBJECT_NUMBER = new UShort(_TESTOBJECTB_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier TESTOBJECTB_OBJECT_NAME = new Identifier("TestObjectB");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType TESTOBJECTB_OBJECT_TYPE = new ObjectType(new UShort(200), EVENTTEST_SERVICE_NUMBER, new UOctet(1), TESTOBJECTB_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject TESTOBJECTB_OBJECT = new COMObject(TESTOBJECTB_OBJECT_TYPE, TESTOBJECTB_OBJECT_NAME, TestObjectB.SHORT_FORM, false, null, false, null, false);

    /**
     * Literal for object TESTOBJECTCREATION.
     */
    @Deprecated
    public static final int _TESTOBJECTCREATION_OBJECT_NUMBER = 3001;

    /**
     * Instance for object TESTOBJECTCREATION.
     */
    @Deprecated
    public static final UShort TESTOBJECTCREATION_OBJECT_NUMBER = new UShort(_TESTOBJECTCREATION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier TESTOBJECTCREATION_OBJECT_NAME = new Identifier("TestObjectCreation");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType TESTOBJECTCREATION_OBJECT_TYPE = new ObjectType(new UShort(200), EVENTTEST_SERVICE_NUMBER, new UOctet(1), TESTOBJECTCREATION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject TESTOBJECTCREATION_OBJECT = new COMObject(TESTOBJECTCREATION_OBJECT_TYPE, TESTOBJECTCREATION_OBJECT_NAME, ObjectCreation.SHORT_FORM, false, null, true, null, true);

    /**
     * Literal for object TESTOBJECTDELETION.
     */
    @Deprecated
    public static final int _TESTOBJECTDELETION_OBJECT_NUMBER = 3002;

    /**
     * Instance for object TESTOBJECTDELETION.
     */
    @Deprecated
    public static final UShort TESTOBJECTDELETION_OBJECT_NUMBER = new UShort(_TESTOBJECTDELETION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier TESTOBJECTDELETION_OBJECT_NAME = new Identifier("TestObjectDeletion");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType TESTOBJECTDELETION_OBJECT_TYPE = new ObjectType(new UShort(200), EVENTTEST_SERVICE_NUMBER, new UOctet(1), TESTOBJECTDELETION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject TESTOBJECTDELETION_OBJECT = new COMObject(TESTOBJECTDELETION_OBJECT_TYPE, TESTOBJECTDELETION_OBJECT_NAME, ObjectDeletion.SHORT_FORM, false, null, true, null, true);

    /**
     * Literal for object TESTOBJECTUPDATE.
     */
    @Deprecated
    public static final int _TESTOBJECTUPDATE_OBJECT_NUMBER = 3003;

    /**
     * Instance for object TESTOBJECTUPDATE.
     */
    @Deprecated
    public static final UShort TESTOBJECTUPDATE_OBJECT_NUMBER = new UShort(_TESTOBJECTUPDATE_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier TESTOBJECTUPDATE_OBJECT_NAME = new Identifier("TestObjectUpdate");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType TESTOBJECTUPDATE_OBJECT_TYPE = new ObjectType(new UShort(200), EVENTTEST_SERVICE_NUMBER, new UOctet(1), TESTOBJECTUPDATE_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject TESTOBJECTUPDATE_OBJECT = new COMObject(TESTOBJECTUPDATE_OBJECT_TYPE, TESTOBJECTUPDATE_OBJECT_NAME, ObjectUpdate.SHORT_FORM, false, null, true, null, true);

    /**
     * Object instance.
     */
    public static final COMObject[] COM_OBJECTS = {
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
    public MALArea getArea() {
        return COMPrototypeHelper.COMPROTOTYPE_AREA;
    }

    @Override
    public MOErrorException generateMOError(int operationNumber,
            int errorNumber,
            Object extraInfo) {
        MOErrorException areaError = COMPrototypeHelper.generateMOError(errorNumber, extraInfo);
        return (areaError != null) ? areaError : MALHelper.generateMOError(errorNumber, extraInfo);
    }

}
