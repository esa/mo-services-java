package org.ccsds.moims.mo.mc.action;

/**
 * Helper class for Action service.
 */
public class ActionServiceInfo extends org.ccsds.moims.mo.mal.ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _ACTION_SERVICE_NUMBER = 1;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort ACTION_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ACTION_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier ACTION_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("Action");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            4, 2, ACTION_SERVICE_NUMBER);

    /**
     * Operation number literal for operation EXECUTE.
     */
    public static final int _EXECUTE_OP_NUMBER = 1;

    /**
     * Operation number instance for operation EXECUTE.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort EXECUTE_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_EXECUTE_OP_NUMBER);

    /**
     * Operation instance for operation EXECUTE.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation EXECUTE_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            EXECUTE_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("execute"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("executionRequest", false, org.ccsds.moims.mo.mc.structures.ActionExecutionRequest.SHORT_FORM, "")}, 
            "The execute operation allows a consumer to request a provider to execute an action.");

    /**
     * Operation number literal for operation MONITOREXECUTION.
     */
    public static final int _MONITOREXECUTION_OP_NUMBER = 2;

    /**
     * Operation number instance for operation MONITOREXECUTION.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort MONITOREXECUTION_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_MONITOREXECUTION_OP_NUMBER);

    /**
     * Operation instance for operation MONITOREXECUTION.
     */
    public static final org.ccsds.moims.mo.mal.MALPubSubOperation MONITOREXECUTION_OP = new org.ccsds.moims.mo.mal.MALPubSubOperation(SERVICE_KEY, 
            MONITOREXECUTION_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("monitorExecution"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("progressEvent", false, null, "")}, 
            "The monitorExecution operation allows a consumer to be informed of the progress in the execution of an action or a set of actions.");

    /**
     * Key names instance for MONITOREXECUTION operation of pubsub interaction
     * pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.Identifier [] _MONITOREXECUTION_OP_KEY_NAMES = {new org.ccsds.moims.mo.mal.structures.Identifier("requestId"),
            new org.ccsds.moims.mo.mal.structures.Identifier("actionKey"),
            new org.ccsds.moims.mo.mal.structures.Identifier("actionCategory")};

    /**
     * Key names instance for MONITOREXECUTION operation of pubsub interaction
     * pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.IdentifierList MONITOREXECUTION_OP_KEY_NAMES = new org.ccsds.moims.mo.mal.structures.IdentifierList(new java.util.ArrayList<>(java.util.Arrays.asList(_MONITOREXECUTION_OP_KEY_NAMES)));

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] ACTION_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{EXECUTE_OP,
        MONITOREXECUTION_OP};

    /**
     * Creates an instance of the Action ServiceInfo.
     * 
     */
    public ActionServiceInfo() {
        super(SERVICE_KEY, ACTION_SERVICE_NAME, ACTION_SERVICE_ELEMENTS, OPERATIONS);
    }

    @Override
    public org.ccsds.moims.mo.mal.MALArea getArea() {
        return org.ccsds.moims.mo.mc.MCHelper.MC_AREA;
    }

    @Override
    public org.ccsds.moims.mo.mal.MOErrorException generateMOError(int errorNumber,
            Object extraInfo) {
        switch (errorNumber) {
            case 1:
                return new org.ccsds.moims.mo.mc.ReadOnlyException(extraInfo);
            case 2:
                return new org.ccsds.moims.mo.mc.DuplicateException(extraInfo);
            case 3:
                return new org.ccsds.moims.mo.mc.InvalidException(extraInfo);
            case 4:
                return new org.ccsds.moims.mo.mc.RejectedException(extraInfo);
            case 5:
                return new org.ccsds.moims.mo.mc.AmbiguousException(extraInfo);
        }
        return null;
    }

}
