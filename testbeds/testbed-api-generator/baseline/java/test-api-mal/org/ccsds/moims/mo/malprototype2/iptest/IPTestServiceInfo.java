package org.ccsds.moims.mo.malprototype2.iptest;

/**
 * Helper class for IPTest service.
 */
public class IPTestServiceInfo extends org.ccsds.moims.mo.mal.ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _IPTEST_SERVICE_NUMBER = 1;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort IPTEST_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_IPTEST_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier IPTEST_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("IPTest");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            101, 1, IPTEST_SERVICE_NUMBER);

    /**
     * Operation number literal for operation MONITOR.
     */
    public static final int _MONITOR_OP_NUMBER = 105;

    /**
     * Operation number instance for operation MONITOR.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort MONITOR_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_MONITOR_OP_NUMBER);

    /**
     * Operation instance for operation MONITOR.
     */
    public static final org.ccsds.moims.mo.mal.MALPubSubOperation MONITOR_OP = new org.ccsds.moims.mo.mal.MALPubSubOperation(SERVICE_KEY, 
            MONITOR_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("monitor"), 
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("pub", true, org.ccsds.moims.mo.malprototype.structures.TestUpdate.SHORT_FORM, "")}, 
            "This operation initiates a Pub/Sub interaction. It is not implemented by the service provider but by a broker.");

    /**
     * Key names instance for MONITOR operation of pubsub interaction pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.Identifier [] _MONITOR_OP_KEY_NAMES = {};

    /**
     * Key names instance for MONITOR operation of pubsub interaction pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.IdentifierList MONITOR_OP_KEY_NAMES = new org.ccsds.moims.mo.mal.structures.IdentifierList(new java.util.ArrayList<>(java.util.Arrays.asList(_MONITOR_OP_KEY_NAMES)));

    /**
     * Operation number literal for operation MONITOR2.
     */
    public static final int _MONITOR2_OP_NUMBER = 106;

    /**
     * Operation number instance for operation MONITOR2.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort MONITOR2_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_MONITOR2_OP_NUMBER);

    /**
     * Operation instance for operation MONITOR2.
     */
    public static final org.ccsds.moims.mo.mal.MALPubSubOperation MONITOR2_OP = new org.ccsds.moims.mo.mal.MALPubSubOperation(SERVICE_KEY, 
            MONITOR2_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("monitor2"), 
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("pub", true, org.ccsds.moims.mo.malprototype.structures.TestUpdate.SHORT_FORM, "")}, 
            "This operation initiates a Pub/Sub interaction. It is not implemented by the service provider but by a broker.");

    /**
     * Key names instance for MONITOR2 operation of pubsub interaction pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.Identifier [] _MONITOR2_OP_KEY_NAMES = {};

    /**
     * Key names instance for MONITOR2 operation of pubsub interaction pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.IdentifierList MONITOR2_OP_KEY_NAMES = new org.ccsds.moims.mo.mal.structures.IdentifierList(new java.util.ArrayList<>(java.util.Arrays.asList(_MONITOR2_OP_KEY_NAMES)));

    /**
     * Operation number literal for operation PUBLISHUPDATES.
     */
    public static final int _PUBLISHUPDATES_OP_NUMBER = 108;

    /**
     * Operation number instance for operation PUBLISHUPDATES.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort PUBLISHUPDATES_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_PUBLISHUPDATES_OP_NUMBER);

    /**
     * Operation instance for operation PUBLISHUPDATES.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation PUBLISHUPDATES_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            PUBLISHUPDATES_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("publishUpdates"), 
            new org.ccsds.moims.mo.mal.structures.UShort(102), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate.SHORT_FORM, "")}, 
            "This operation cleans the assertions table, publishes an update as specified by the parameter TestPublishUpdate and checks the header of the Publish message (see 4.1.3). Moreover if an error is expected by the TestPublishUpdate then the operation hangs until a Publish error is raised or a timer ends (see 4.1.10). The header of the Publish error message is checked (see 4.1.4).");

    /**
     * Operation number literal for operation PUBLISHREGISTER.
     */
    public static final int _PUBLISHREGISTER_OP_NUMBER = 110;

    /**
     * Operation number instance for operation PUBLISHREGISTER.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort PUBLISHREGISTER_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_PUBLISHREGISTER_OP_NUMBER);

    /**
     * Operation instance for operation PUBLISHREGISTER.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation PUBLISHREGISTER_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            PUBLISHREGISTER_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("publishRegister"), 
            new org.ccsds.moims.mo.mal.structures.UShort(102), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, org.ccsds.moims.mo.malprototype.structures.TestPublishRegister.SHORT_FORM, "")}, 
            "This operation cleans the assertions table, registers a publisher as specified by the parameter TestPublishRegister and checks the header of the Publish Register message (see 4.1.5). Moreover if no error is expected by the TestPublishRegister, it checks the header of the Publish Register acknowledgement message (see 4.1.6). Otherwise it checks the header of the Publish Register error message (see 4.1.7).");

    /**
     * Operation number literal for operation PUBLISHDEREGISTER.
     */
    public static final int _PUBLISHDEREGISTER_OP_NUMBER = 111;

    /**
     * Operation number instance for operation PUBLISHDEREGISTER.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort PUBLISHDEREGISTER_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_PUBLISHDEREGISTER_OP_NUMBER);

    /**
     * Operation instance for operation PUBLISHDEREGISTER.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation PUBLISHDEREGISTER_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            PUBLISHDEREGISTER_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("publishDeregister"), 
            new org.ccsds.moims.mo.mal.structures.UShort(102), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, org.ccsds.moims.mo.malprototype.structures.TestPublishDeregister.SHORT_FORM, "")}, 
            "This operation cleans the assertions table, registers a publisher as specified by the parameter TestPublishDeregister and checks the header of the Publish Deregister message (see 4.1.5). Moreover if no error is expected by the TestPublishDeregister, it checks the header of the Publish Deregister acknowledgement message (see 4.1.8). Otherwise it checks the header of the Publish Deregister error message (see 4.1.9).");

    /**
     * Operation number literal for operation TESTMULTIPLENOTIFY.
     */
    public static final int _TESTMULTIPLENOTIFY_OP_NUMBER = 112;

    /**
     * Operation number instance for operation TESTMULTIPLENOTIFY.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort TESTMULTIPLENOTIFY_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTMULTIPLENOTIFY_OP_NUMBER);

    /**
     * Operation instance for operation TESTMULTIPLENOTIFY.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation TESTMULTIPLENOTIFY_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            TESTMULTIPLENOTIFY_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("testMultipleNotify"), 
            new org.ccsds.moims.mo.mal.structures.UShort(102), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate.SHORT_FORM, "")}, 
            "");

    /**
     * Operation number literal for operation TESTOBJECTREFSUBMIT.
     */
    public static final int _TESTOBJECTREFSUBMIT_OP_NUMBER = 113;

    /**
     * Operation number instance for operation TESTOBJECTREFSUBMIT.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort TESTOBJECTREFSUBMIT_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTOBJECTREFSUBMIT_OP_NUMBER);

    /**
     * Operation instance for operation TESTOBJECTREFSUBMIT.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation TESTOBJECTREFSUBMIT_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            TESTOBJECTREFSUBMIT_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("testObjectRefSubmit"), 
            new org.ccsds.moims.mo.mal.structures.UShort(102), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, org.ccsds.moims.mo.mal.structures.ObjectRef.OBJECTREF_SHORT_FORM, "")}, 
            "");

    /**
     * Operation number literal for operation TESTOBJECTREFSEND.
     */
    public static final int _TESTOBJECTREFSEND_OP_NUMBER = 114;

    /**
     * Operation number instance for operation TESTOBJECTREFSEND.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort TESTOBJECTREFSEND_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTOBJECTREFSEND_OP_NUMBER);

    /**
     * Operation instance for operation TESTOBJECTREFSEND.
     */
    public static final org.ccsds.moims.mo.mal.MALSendOperation TESTOBJECTREFSEND_OP = new org.ccsds.moims.mo.mal.MALSendOperation(SERVICE_KEY, 
            TESTOBJECTREFSEND_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("testObjectRefSend"), 
            new org.ccsds.moims.mo.mal.structures.UShort(102), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, org.ccsds.moims.mo.mal.structures.ObjectRef.OBJECTREF_SHORT_FORM, "")}, 
            "");

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] IPTEST_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{MONITOR_OP,
        MONITOR2_OP,
        PUBLISHUPDATES_OP,
        PUBLISHREGISTER_OP,
        PUBLISHDEREGISTER_OP,
        TESTMULTIPLENOTIFY_OP,
        TESTOBJECTREFSUBMIT_OP,
        TESTOBJECTREFSEND_OP};

    /**
     * Creates an instance of the IPTest ServiceInfo.
     * 
     */
    public IPTestServiceInfo() {
        super(SERVICE_KEY, IPTEST_SERVICE_NAME, IPTEST_SERVICE_ELEMENTS, OPERATIONS);
    }

    @Override
    public org.ccsds.moims.mo.mal.MALArea getArea() {
        return org.ccsds.moims.mo.malprototype2.MALPrototype2Helper.MALPROTOTYPE2_AREA;
    }

    @Override
    public org.ccsds.moims.mo.mal.MOErrorException generateMOError(int errorNumber,
            Object extraInfo) {
        switch (errorNumber) {
        }
        return null;
    }

}
