package org.ccsds.moims.mo.comprototype1.test1;

import org.ccsds.moims.mo.com.COMObject;
import org.ccsds.moims.mo.com.COMService;
import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.comprototype.archivetest.structures.TestObjectPayload;
import org.ccsds.moims.mo.comprototype1.COMPrototype1Helper;
import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.ServiceKey;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * Helper class for Test1 service.
 */
public class Test1ServiceInfo extends COMService {

    /**
     * Service number literal.
     */
    public static final int _TEST1_SERVICE_NUMBER = 1;

    /**
     * Service number instance.
     */
    public static final UShort TEST1_SERVICE_NUMBER = new UShort(_TEST1_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier TEST1_SERVICE_NAME = new Identifier("Test1");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            201, 1, TEST1_SERVICE_NUMBER);

    /**
     * Area elements.
     */
    public static final Element[] TEST1_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{};

    /**
     * Literal for object TESTOBJECTA.
     */
    @Deprecated
    public static final int _TESTOBJECTA_OBJECT_NUMBER = 1;

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
    public static final ObjectType TESTOBJECTA_OBJECT_TYPE = new ObjectType(new UShort(201), TEST1_SERVICE_NUMBER, new UOctet(1), TESTOBJECTA_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject TESTOBJECTA_OBJECT = new COMObject(TESTOBJECTA_OBJECT_TYPE, TESTOBJECTA_OBJECT_NAME, TestObjectPayload.SHORT_FORM, false, null, false, null, false);

    /**
     * Object instance.
     */
    public static final COMObject[] COM_OBJECTS = {
        TESTOBJECTA_OBJECT,};

    /**
     * Creates an instance of the Test1 ServiceInfo.
     * 
     */
    public Test1ServiceInfo() {
        super(SERVICE_KEY, TEST1_SERVICE_NAME, TEST1_SERVICE_ELEMENTS, OPERATIONS, COM_OBJECTS);
    }

    @Override
    public MALArea getArea() {
        return COMPrototype1Helper.COMPROTOTYPE1_AREA;
    }

    @Override
    public MOErrorException generateMOError(int operationNumber,
            int errorNumber,
            Object extraInfo) {
        MOErrorException areaError = COMPrototype1Helper.generateMOError(errorNumber, extraInfo);
        return (areaError != null) ? areaError : MALHelper.generateMOError(errorNumber, extraInfo);
    }

}
