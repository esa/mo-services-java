package org.ccsds.moims.mo.comprototype.activitytest;

/**
 * Helper class for ActivityTest service.
 */
public class ActivityTestServiceInfo extends org.ccsds.moims.mo.mal.ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _ACTIVITYTEST_SERVICE_NUMBER = 4;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort ACTIVITYTEST_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ACTIVITYTEST_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier ACTIVITYTEST_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ActivityTest");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            200, 1, ACTIVITYTEST_SERVICE_NUMBER);

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
     * Operation number literal for operation CLOSE.
     */
    public static final int _CLOSE_OP_NUMBER = 104;

    /**
     * Operation number instance for operation CLOSE.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort CLOSE_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_CLOSE_OP_NUMBER);

    /**
     * Operation instance for operation CLOSE.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation CLOSE_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            CLOSE_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("close"), 
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            "Closes the service provider");

    /**
     * Operation number literal for operation SEND.
     */
    public static final int _SEND_OP_NUMBER = 200;

    /**
     * Operation number instance for operation SEND.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort SEND_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_SEND_OP_NUMBER);

    /**
     * Operation instance for operation SEND.
     */
    public static final org.ccsds.moims.mo.mal.MALSendOperation SEND_OP = new org.ccsds.moims.mo.mal.MALSendOperation(SERVICE_KEY, 
            SEND_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("send"), 
            new org.ccsds.moims.mo.mal.structures.UShort(101), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("in1", true, org.ccsds.moims.mo.mal.structures.StringList.SHORT_FORM, "")}, 
            "");

    /**
     * Operation number literal for operation TESTSUBMIT.
     */
    public static final int _TESTSUBMIT_OP_NUMBER = 201;

    /**
     * Operation number instance for operation TESTSUBMIT.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort TESTSUBMIT_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTSUBMIT_OP_NUMBER);

    /**
     * Operation instance for operation TESTSUBMIT.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation TESTSUBMIT_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            TESTSUBMIT_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("testSubmit"), 
            new org.ccsds.moims.mo.mal.structures.UShort(101), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("in1", true, org.ccsds.moims.mo.mal.structures.StringList.SHORT_FORM, "")}, 
            "");

    /**
     * Operation number literal for operation REQUEST.
     */
    public static final int _REQUEST_OP_NUMBER = 202;

    /**
     * Operation number instance for operation REQUEST.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort REQUEST_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_REQUEST_OP_NUMBER);

    /**
     * Operation instance for operation REQUEST.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation REQUEST_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            REQUEST_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("request"), 
            new org.ccsds.moims.mo.mal.structures.UShort(101), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("in1", true, org.ccsds.moims.mo.mal.structures.StringList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("out1", true, org.ccsds.moims.mo.mal.structures.StringList.SHORT_FORM, "")}, 
            "");

    /**
     * Operation number literal for operation INVOKE.
     */
    public static final int _INVOKE_OP_NUMBER = 203;

    /**
     * Operation number instance for operation INVOKE.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort INVOKE_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_INVOKE_OP_NUMBER);

    /**
     * Operation instance for operation INVOKE.
     */
    public static final org.ccsds.moims.mo.mal.MALInvokeOperation INVOKE_OP = new org.ccsds.moims.mo.mal.MALInvokeOperation(SERVICE_KEY, 
            INVOKE_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("invoke"), 
            new org.ccsds.moims.mo.mal.structures.UShort(101), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("in1", true, org.ccsds.moims.mo.mal.structures.StringList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("ack1", true, org.ccsds.moims.mo.mal.structures.StringList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("out1", true, org.ccsds.moims.mo.mal.structures.StringList.SHORT_FORM, "")}, 
            "");

    /**
     * Operation number literal for operation PROGRESS.
     */
    public static final int _PROGRESS_OP_NUMBER = 204;

    /**
     * Operation number instance for operation PROGRESS.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort PROGRESS_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_PROGRESS_OP_NUMBER);

    /**
     * Operation instance for operation PROGRESS.
     */
    public static final org.ccsds.moims.mo.mal.MALProgressOperation PROGRESS_OP = new org.ccsds.moims.mo.mal.MALProgressOperation(SERVICE_KEY, 
            PROGRESS_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("progress"), 
            new org.ccsds.moims.mo.mal.structures.UShort(101), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("in1", true, org.ccsds.moims.mo.mal.structures.StringList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("ack1", true, org.ccsds.moims.mo.mal.structures.StringList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("out1", true, org.ccsds.moims.mo.mal.structures.StringList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("out2", true, org.ccsds.moims.mo.mal.structures.StringList.SHORT_FORM, "")}, 
            "");

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] ACTIVITYTEST_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{RESETTEST_OP,
        CLOSE_OP,
        SEND_OP,
        TESTSUBMIT_OP,
        REQUEST_OP,
        INVOKE_OP,
        PROGRESS_OP};

    /**
     * Creates an instance of the ActivityTest ServiceInfo.
     * 
     */
    public ActivityTestServiceInfo() {
        super(SERVICE_KEY, ACTIVITYTEST_SERVICE_NAME, ACTIVITYTEST_SERVICE_ELEMENTS, OPERATIONS);
    }

    @Override
    public org.ccsds.moims.mo.mal.MALArea getArea() {
        return org.ccsds.moims.mo.comprototype.COMPrototypeHelper.COMPROTOTYPE_AREA;
    }

    @Override
    public org.ccsds.moims.mo.mal.MOErrorException generateMOError(int errorNumber,
            Object extraInfo) {
        switch (errorNumber) {
        }
        return null;
    }

}
