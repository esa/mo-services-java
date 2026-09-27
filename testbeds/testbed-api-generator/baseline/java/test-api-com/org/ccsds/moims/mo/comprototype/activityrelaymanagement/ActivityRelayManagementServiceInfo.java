package org.ccsds.moims.mo.comprototype.activityrelaymanagement;

/**
 * Helper class for ActivityRelayManagement service.
 */
public class ActivityRelayManagementServiceInfo extends org.ccsds.moims.mo.mal.ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _ACTIVITYRELAYMANAGEMENT_SERVICE_NUMBER = 5;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort ACTIVITYRELAYMANAGEMENT_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ACTIVITYRELAYMANAGEMENT_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier ACTIVITYRELAYMANAGEMENT_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ActivityRelayManagement");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            200, 1, ACTIVITYRELAYMANAGEMENT_SERVICE_NUMBER);

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
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            "Resets all values back to their default value.");

    /**
     * Operation number literal for operation CREATERELAY.
     */
    public static final int _CREATERELAY_OP_NUMBER = 101;

    /**
     * Operation number instance for operation CREATERELAY.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort CREATERELAY_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_CREATERELAY_OP_NUMBER);

    /**
     * Operation instance for operation CREATERELAY.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation CREATERELAY_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            CREATERELAY_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("createRelay"), 
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("in1", true, org.ccsds.moims.mo.mal.structures.Attribute.STRING_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("in2", true, org.ccsds.moims.mo.mal.structures.Attribute.STRING_SHORT_FORM, "")}, 
            "Create a new relay node that supports the Activity and ActivityTest services.");

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] ACTIVITYRELAYMANAGEMENT_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{RESETTEST_OP,
        CREATERELAY_OP};

    /**
     * Creates an instance of the ActivityRelayManagement ServiceInfo.
     * 
     */
    public ActivityRelayManagementServiceInfo() {
        super(SERVICE_KEY, ACTIVITYRELAYMANAGEMENT_SERVICE_NAME, ACTIVITYRELAYMANAGEMENT_SERVICE_ELEMENTS, OPERATIONS);
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
