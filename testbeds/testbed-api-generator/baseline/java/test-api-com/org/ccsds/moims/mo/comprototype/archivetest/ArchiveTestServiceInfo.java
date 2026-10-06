package org.ccsds.moims.mo.comprototype.archivetest;

import org.ccsds.moims.mo.com.COMObject;
import org.ccsds.moims.mo.com.COMService;
import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.comprototype.COMPrototypeHelper;
import org.ccsds.moims.mo.comprototype.archivetest.structures.EnumeratedObject;
import org.ccsds.moims.mo.comprototype.archivetest.structures.TestObjectPayload;
import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.ServiceKey;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * Helper class for ArchiveTest service.
 */
public class ArchiveTestServiceInfo extends COMService {

    /**
     * Service number literal.
     */
    public static final int _ARCHIVETEST_SERVICE_NUMBER = 6;

    /**
     * Service number instance.
     */
    public static final UShort ARCHIVETEST_SERVICE_NUMBER = new UShort(_ARCHIVETEST_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier ARCHIVETEST_SERVICE_NAME = new Identifier("ArchiveTest");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            200, 1, ARCHIVETEST_SERVICE_NUMBER);

    /**
     * Operation number literal for operation RESET.
     */
    public static final int _RESET_OP_NUMBER = 100;

    /**
     * Operation number instance for operation RESET.
     */
    private static final UShort RESET_OP_NUMBER = new UShort(_RESET_OP_NUMBER);

    /**
     * Operation instance for operation RESET.
     */
    public static final MALSubmitOperation RESET_OP = new MALSubmitOperation(SERVICE_KEY, 
            RESET_OP_NUMBER, 
            new Identifier("reset"), 
            new UShort(100), 
            new OperationField[] {}, 
            "Resets all values back to their default value.");

    /**
     * Area elements.
     */
    public static final Element[] ARCHIVETEST_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{RESET_OP};

    /**
     * Literal for object TESTOBJECT.
     */
    @Deprecated
    public static final int _TESTOBJECT_OBJECT_NUMBER = 1;

    /**
     * Instance for object TESTOBJECT.
     */
    @Deprecated
    public static final UShort TESTOBJECT_OBJECT_NUMBER = new UShort(_TESTOBJECT_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier TESTOBJECT_OBJECT_NAME = new Identifier("TestObject");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType TESTOBJECT_OBJECT_TYPE = new ObjectType(new UShort(200), ARCHIVETEST_SERVICE_NUMBER, new UOctet(1), TESTOBJECT_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject TESTOBJECT_OBJECT = new COMObject(TESTOBJECT_OBJECT_TYPE, TESTOBJECT_OBJECT_NAME, TestObjectPayload.SHORT_FORM, false, null, false, null, false);

    /**
     * Literal for object TESTOBJECT2.
     */
    @Deprecated
    public static final int _TESTOBJECT2_OBJECT_NUMBER = 2;

    /**
     * Instance for object TESTOBJECT2.
     */
    @Deprecated
    public static final UShort TESTOBJECT2_OBJECT_NUMBER = new UShort(_TESTOBJECT2_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier TESTOBJECT2_OBJECT_NAME = new Identifier("TestObject2");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType TESTOBJECT2_OBJECT_TYPE = new ObjectType(new UShort(200), ARCHIVETEST_SERVICE_NUMBER, new UOctet(1), TESTOBJECT2_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject TESTOBJECT2_OBJECT = new COMObject(TESTOBJECT2_OBJECT_TYPE, TESTOBJECT2_OBJECT_NAME, Attribute.INTEGER_SHORT_FORM, false, null, false, null, false);

    /**
     * Literal for object TESTOBJECT3.
     */
    @Deprecated
    public static final int _TESTOBJECT3_OBJECT_NUMBER = 3;

    /**
     * Instance for object TESTOBJECT3.
     */
    @Deprecated
    public static final UShort TESTOBJECT3_OBJECT_NUMBER = new UShort(_TESTOBJECT3_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier TESTOBJECT3_OBJECT_NAME = new Identifier("TestObject3");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType TESTOBJECT3_OBJECT_TYPE = new ObjectType(new UShort(200), ARCHIVETEST_SERVICE_NUMBER, new UOctet(1), TESTOBJECT3_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject TESTOBJECT3_OBJECT = new COMObject(TESTOBJECT3_OBJECT_TYPE, TESTOBJECT3_OBJECT_NAME, EnumeratedObject.SHORT_FORM, false, null, false, null, false);

    /**
     * Literal for object TESTOBJECT4.
     */
    @Deprecated
    public static final int _TESTOBJECT4_OBJECT_NUMBER = 4;

    /**
     * Instance for object TESTOBJECT4.
     */
    @Deprecated
    public static final UShort TESTOBJECT4_OBJECT_NUMBER = new UShort(_TESTOBJECT4_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier TESTOBJECT4_OBJECT_NAME = new Identifier("TestObject4");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType TESTOBJECT4_OBJECT_TYPE = new ObjectType(new UShort(200), ARCHIVETEST_SERVICE_NUMBER, new UOctet(1), TESTOBJECT4_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject TESTOBJECT4_OBJECT = new COMObject(TESTOBJECT4_OBJECT_TYPE, TESTOBJECT4_OBJECT_NAME, Attribute.BLOB_SHORT_FORM, false, null, false, null, false);

    /**
     * Literal for object TESTOBJECT5.
     */
    @Deprecated
    public static final int _TESTOBJECT5_OBJECT_NUMBER = 5;

    /**
     * Instance for object TESTOBJECT5.
     */
    @Deprecated
    public static final UShort TESTOBJECT5_OBJECT_NUMBER = new UShort(_TESTOBJECT5_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier TESTOBJECT5_OBJECT_NAME = new Identifier("TestObject5");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType TESTOBJECT5_OBJECT_TYPE = new ObjectType(new UShort(200), ARCHIVETEST_SERVICE_NUMBER, new UOctet(1), TESTOBJECT5_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject TESTOBJECT5_OBJECT = new COMObject(TESTOBJECT5_OBJECT_TYPE, TESTOBJECT5_OBJECT_NAME, null, false, null, false, null, false);

    /**
     * Literal for object TESTOBJECT6.
     */
    @Deprecated
    public static final int _TESTOBJECT6_OBJECT_NUMBER = 6;

    /**
     * Instance for object TESTOBJECT6.
     */
    @Deprecated
    public static final UShort TESTOBJECT6_OBJECT_NUMBER = new UShort(_TESTOBJECT6_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier TESTOBJECT6_OBJECT_NAME = new Identifier("TestObject6");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType TESTOBJECT6_OBJECT_TYPE = new ObjectType(new UShort(200), ARCHIVETEST_SERVICE_NUMBER, new UOctet(1), TESTOBJECT6_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject TESTOBJECT6_OBJECT = new COMObject(TESTOBJECT6_OBJECT_TYPE, TESTOBJECT6_OBJECT_NAME, Attribute.IDENTIFIER_SHORT_FORM, false, null, false, null, false);

    /**
     * Literal for object TESTOBJECT7.
     */
    @Deprecated
    public static final int _TESTOBJECT7_OBJECT_NUMBER = 7;

    /**
     * Instance for object TESTOBJECT7.
     */
    @Deprecated
    public static final UShort TESTOBJECT7_OBJECT_NUMBER = new UShort(_TESTOBJECT7_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier TESTOBJECT7_OBJECT_NAME = new Identifier("TestObject7");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType TESTOBJECT7_OBJECT_TYPE = new ObjectType(new UShort(200), ARCHIVETEST_SERVICE_NUMBER, new UOctet(1), TESTOBJECT7_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject TESTOBJECT7_OBJECT = new COMObject(TESTOBJECT7_OBJECT_TYPE, TESTOBJECT7_OBJECT_NAME, Attribute.BLOB_SHORT_FORM, false, null, false, null, false);

    /**
     * Object instance.
     */
    public static final COMObject[] COM_OBJECTS = {
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
