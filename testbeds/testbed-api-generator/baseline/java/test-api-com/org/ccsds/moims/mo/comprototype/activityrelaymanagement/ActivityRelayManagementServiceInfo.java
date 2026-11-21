package org.ccsds.moims.mo.comprototype.activityrelaymanagement;

import org.ccsds.moims.mo.comprototype.COMPrototypeHelper;
import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.ServiceKey;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * Helper class for ActivityRelayManagement service.
 */
public class ActivityRelayManagementServiceInfo extends ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _ACTIVITYRELAYMANAGEMENT_SERVICE_NUMBER = 5;

    /**
     * Service number instance.
     */
    public static final UShort ACTIVITYRELAYMANAGEMENT_SERVICE_NUMBER = new UShort(_ACTIVITYRELAYMANAGEMENT_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier ACTIVITYRELAYMANAGEMENT_SERVICE_NAME = new Identifier("ActivityRelayManagement");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            200, 1, ACTIVITYRELAYMANAGEMENT_SERVICE_NUMBER);

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
            new OperationField[] {}, 
            "Resets all values back to their default value.");

    /**
     * Operation number literal for operation CREATERELAY.
     */
    public static final int _CREATERELAY_OP_NUMBER = 101;

    /**
     * Operation number instance for operation CREATERELAY.
     */
    private static final UShort CREATERELAY_OP_NUMBER = new UShort(_CREATERELAY_OP_NUMBER);

    /**
     * Operation instance for operation CREATERELAY.
     */
    public static final MALSubmitOperation CREATERELAY_OP = new MALSubmitOperation(SERVICE_KEY, 
            CREATERELAY_OP_NUMBER, 
            new Identifier("createRelay"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("in1", true, Attribute.STRING_SHORT_FORM, ""),
                new OperationField("in2", true, Attribute.STRING_SHORT_FORM, "")}, 
            "Create a new relay node that supports the Activity and ActivityTest services.");

    /**
     * Area elements.
     */
    public static final Element[] ACTIVITYRELAYMANAGEMENT_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{RESETTEST_OP,
        CREATERELAY_OP};

    /**
     * Creates an instance of the ActivityRelayManagement ServiceInfo.
     * 
     */
    public ActivityRelayManagementServiceInfo() {
        super(SERVICE_KEY, ACTIVITYRELAYMANAGEMENT_SERVICE_NAME, ACTIVITYRELAYMANAGEMENT_SERVICE_ELEMENTS, OPERATIONS);
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
