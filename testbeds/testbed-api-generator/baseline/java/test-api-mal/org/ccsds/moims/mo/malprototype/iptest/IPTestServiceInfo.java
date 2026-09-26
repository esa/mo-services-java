package org.ccsds.moims.mo.malprototype.iptest;

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
            100, 1, IPTEST_SERVICE_NUMBER);

    /**
     * Operation number literal for operation SEND.
     */
    public static final int _SEND_OP_NUMBER = 100;

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
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, org.ccsds.moims.mo.malprototype.structures.IPTestDefinition.SHORT_FORM, "")}, 
            "This operation cleans the assertions table and check that the header of the received message is the same as the one expected (see 4.1.1).");

    /**
     * Operation number literal for operation TESTSUBMIT.
     */
    public static final int _TESTSUBMIT_OP_NUMBER = 101;

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
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, org.ccsds.moims.mo.malprototype.structures.IPTestDefinition.SHORT_FORM, "")}, 
            "This operation cleans the assertions table and checks that the header of the received message is the same as the one expected (see 4.1.1). Moreover it triggers the transitions specified by the IPTestDefinition (see 3.1.1.1).");

    /**
     * Operation number literal for operation REQUEST.
     */
    public static final int _REQUEST_OP_NUMBER = 102;

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
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, org.ccsds.moims.mo.malprototype.structures.IPTestDefinition.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("output", true, org.ccsds.moims.mo.mal.structures.Attribute.STRING_SHORT_FORM, "")}, 
            "This operation cleans the assertions table and checks that the header of the received message is the same as the one expected (see 4.1.1). Moreover it triggers the transitions specified by the IPTestDefinition (see 3.1.1.1).");

    /**
     * Operation number literal for operation INVOKE.
     */
    public static final int _INVOKE_OP_NUMBER = 103;

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
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, org.ccsds.moims.mo.malprototype.structures.IPTestDefinition.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("ack", true, org.ccsds.moims.mo.mal.structures.Attribute.STRING_SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("output", true, org.ccsds.moims.mo.mal.structures.Attribute.STRING_SHORT_FORM, "")}, 
            "This operation cleans the assertions table and checks that the header of the received message is the same as the one expected (see 4.1.1). Moreover it triggers the transitions specified by the IPTestDefinition (see 3.1.1.1).");

    /**
     * Operation number literal for operation PROGRESS.
     */
    public static final int _PROGRESS_OP_NUMBER = 104;

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
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, org.ccsds.moims.mo.malprototype.structures.IPTestDefinition.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("ack", true, org.ccsds.moims.mo.mal.structures.Attribute.STRING_SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("update", true, org.ccsds.moims.mo.mal.structures.Attribute.INTEGER_SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("response", true, org.ccsds.moims.mo.mal.structures.Attribute.STRING_SHORT_FORM, "")}, 
            "This operation cleans the assertions table and checks that the header of the received message is the same as the one expected (see 4.1.1). Moreover it triggers the transitions specified by the IPTestDefinition (see 3.1.1.1).");

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
                new org.ccsds.moims.mo.mal.OperationField("pubField", true, org.ccsds.moims.mo.malprototype.structures.TestUpdate.SHORT_FORM, "")}, 
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
     * Operation number literal for operation GETRESULT.
     */
    public static final int _GETRESULT_OP_NUMBER = 106;

    /**
     * Operation number instance for operation GETRESULT.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort GETRESULT_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GETRESULT_OP_NUMBER);

    /**
     * Operation instance for operation GETRESULT.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation GETRESULT_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            GETRESULT_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("getResult"), 
            new org.ccsds.moims.mo.mal.structures.UShort(101), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, null, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("output", true, org.ccsds.moims.mo.malprototype.structures.IPTestResult.SHORT_FORM, "")}, 
            "This operation returns an IPTestResult.");

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
     * Operation number literal for operation SENDMULTI.
     */
    public static final int _SENDMULTI_OP_NUMBER = 113;

    /**
     * Operation number instance for operation SENDMULTI.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort SENDMULTI_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_SENDMULTI_OP_NUMBER);

    /**
     * Operation instance for operation SENDMULTI.
     */
    public static final org.ccsds.moims.mo.mal.MALSendOperation SENDMULTI_OP = new org.ccsds.moims.mo.mal.MALSendOperation(SERVICE_KEY, 
            SENDMULTI_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("sendMulti"), 
            new org.ccsds.moims.mo.mal.structures.UShort(103), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input1", true, org.ccsds.moims.mo.malprototype.structures.IPTestDefinition.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("input2", true, null, "")}, 
            "This operation duplicates the send operation with an additional IN parameter.");

    /**
     * Operation number literal for operation SUBMITMULTI.
     */
    public static final int _SUBMITMULTI_OP_NUMBER = 114;

    /**
     * Operation number instance for operation SUBMITMULTI.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort SUBMITMULTI_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_SUBMITMULTI_OP_NUMBER);

    /**
     * Operation instance for operation SUBMITMULTI.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation SUBMITMULTI_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            SUBMITMULTI_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("submitMulti"), 
            new org.ccsds.moims.mo.mal.structures.UShort(103), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input1", true, org.ccsds.moims.mo.malprototype.structures.IPTestDefinition.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("input2", true, null, "")}, 
            "This operation duplicates the testSubmit operation with an additional IN parameter.");

    /**
     * Operation number literal for operation REQUESTMULTI.
     */
    public static final int _REQUESTMULTI_OP_NUMBER = 115;

    /**
     * Operation number instance for operation REQUESTMULTI.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort REQUESTMULTI_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_REQUESTMULTI_OP_NUMBER);

    /**
     * Operation instance for operation REQUESTMULTI.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation REQUESTMULTI_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            REQUESTMULTI_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("requestMulti"), 
            new org.ccsds.moims.mo.mal.structures.UShort(103), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input1", true, org.ccsds.moims.mo.malprototype.structures.IPTestDefinition.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("input2", true, null, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("output1", true, org.ccsds.moims.mo.mal.structures.Attribute.STRING_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("output2", true, null, "")}, 
            "This operation duplicates the request operation with an additional IN parameter.");

    /**
     * Operation number literal for operation INVOKEMULTI.
     */
    public static final int _INVOKEMULTI_OP_NUMBER = 116;

    /**
     * Operation number instance for operation INVOKEMULTI.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort INVOKEMULTI_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_INVOKEMULTI_OP_NUMBER);

    /**
     * Operation instance for operation INVOKEMULTI.
     */
    public static final org.ccsds.moims.mo.mal.MALInvokeOperation INVOKEMULTI_OP = new org.ccsds.moims.mo.mal.MALInvokeOperation(SERVICE_KEY, 
            INVOKEMULTI_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("invokeMulti"), 
            new org.ccsds.moims.mo.mal.structures.UShort(103), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input1", true, org.ccsds.moims.mo.malprototype.structures.IPTestDefinition.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("input2", true, null, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("ack1", true, org.ccsds.moims.mo.mal.structures.Attribute.STRING_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("ack2", true, null, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("output1", true, org.ccsds.moims.mo.mal.structures.Attribute.STRING_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("output2", true, null, "")}, 
            "This operation cleans the assertions table and checks that the header of the received message is the same as the one expected (see 4.1.1). Moreover it triggers the transitions specified by the IPTestDefinition (see 4.1.2).");

    /**
     * Operation number literal for operation PROGRESSMULTI.
     */
    public static final int _PROGRESSMULTI_OP_NUMBER = 117;

    /**
     * Operation number instance for operation PROGRESSMULTI.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort PROGRESSMULTI_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_PROGRESSMULTI_OP_NUMBER);

    /**
     * Operation instance for operation PROGRESSMULTI.
     */
    public static final org.ccsds.moims.mo.mal.MALProgressOperation PROGRESSMULTI_OP = new org.ccsds.moims.mo.mal.MALProgressOperation(SERVICE_KEY, 
            PROGRESSMULTI_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("progressMulti"), 
            new org.ccsds.moims.mo.mal.structures.UShort(103), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input1", true, org.ccsds.moims.mo.malprototype.structures.IPTestDefinition.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("input2", true, null, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("ack1", true, org.ccsds.moims.mo.mal.structures.Attribute.STRING_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("ack2", true, null, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("output1", true, org.ccsds.moims.mo.mal.structures.Attribute.INTEGER_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("output2", true, null, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("output3", true, org.ccsds.moims.mo.mal.structures.Attribute.STRING_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("output4", true, null, "")}, 
            "This operation duplicates the progress operation with an additional IN parameter.");

    /**
     * Operation number literal for operation MONITORMULTI.
     */
    public static final int _MONITORMULTI_OP_NUMBER = 118;

    /**
     * Operation number instance for operation MONITORMULTI.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort MONITORMULTI_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_MONITORMULTI_OP_NUMBER);

    /**
     * Operation instance for operation MONITORMULTI.
     */
    public static final org.ccsds.moims.mo.mal.MALPubSubOperation MONITORMULTI_OP = new org.ccsds.moims.mo.mal.MALPubSubOperation(SERVICE_KEY, 
            MONITORMULTI_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("monitorMulti"), 
            new org.ccsds.moims.mo.mal.structures.UShort(103), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("output1", true, org.ccsds.moims.mo.malprototype.structures.TestUpdate.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("output2", true, null, "")}, 
            "This operation duplicates the monitor operation with an additional IN parameter.");

    /**
     * Key names instance for MONITORMULTI operation of pubsub interaction pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.Identifier [] _MONITORMULTI_OP_KEY_NAMES = {};

    /**
     * Key names instance for MONITORMULTI operation of pubsub interaction pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.IdentifierList MONITORMULTI_OP_KEY_NAMES = new org.ccsds.moims.mo.mal.structures.IdentifierList(new java.util.ArrayList<>(java.util.Arrays.asList(_MONITORMULTI_OP_KEY_NAMES)));

    /**
     * Operation number literal for operation TESTREQUESTEMPTYBODY.
     */
    public static final int _TESTREQUESTEMPTYBODY_OP_NUMBER = 120;

    /**
     * Operation number instance for operation TESTREQUESTEMPTYBODY.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort TESTREQUESTEMPTYBODY_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTREQUESTEMPTYBODY_OP_NUMBER);

    /**
     * Operation instance for operation TESTREQUESTEMPTYBODY.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation TESTREQUESTEMPTYBODY_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            TESTREQUESTEMPTYBODY_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("testRequestEmptyBody"), 
            new org.ccsds.moims.mo.mal.structures.UShort(104), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input1", true, org.ccsds.moims.mo.malprototype.structures.IPTestDefinition.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            "This operation checks that an empty body can be sent and received explicitly for a request pattern");

    /**
     * Operation number literal for operation TESTINVOKEEMPTYBODY.
     */
    public static final int _TESTINVOKEEMPTYBODY_OP_NUMBER = 121;

    /**
     * Operation number instance for operation TESTINVOKEEMPTYBODY.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort TESTINVOKEEMPTYBODY_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTINVOKEEMPTYBODY_OP_NUMBER);

    /**
     * Operation instance for operation TESTINVOKEEMPTYBODY.
     */
    public static final org.ccsds.moims.mo.mal.MALInvokeOperation TESTINVOKEEMPTYBODY_OP = new org.ccsds.moims.mo.mal.MALInvokeOperation(SERVICE_KEY, 
            TESTINVOKEEMPTYBODY_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("testInvokeEmptyBody"), 
            new org.ccsds.moims.mo.mal.structures.UShort(104), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input1", true, org.ccsds.moims.mo.malprototype.structures.IPTestDefinition.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            "This operation checks that an empty body can be sent and received explicitly for an Invoke pattern");

    /**
     * Operation number literal for operation TESTPROGRESSEMPTYBODY.
     */
    public static final int _TESTPROGRESSEMPTYBODY_OP_NUMBER = 122;

    /**
     * Operation number instance for operation TESTPROGRESSEMPTYBODY.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort TESTPROGRESSEMPTYBODY_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTPROGRESSEMPTYBODY_OP_NUMBER);

    /**
     * Operation instance for operation TESTPROGRESSEMPTYBODY.
     */
    public static final org.ccsds.moims.mo.mal.MALProgressOperation TESTPROGRESSEMPTYBODY_OP = new org.ccsds.moims.mo.mal.MALProgressOperation(SERVICE_KEY, 
            TESTPROGRESSEMPTYBODY_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("testProgressEmptyBody"), 
            new org.ccsds.moims.mo.mal.structures.UShort(104), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input1", true, org.ccsds.moims.mo.malprototype.structures.IPTestDefinition.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            "This operation checks that an empty body can be sent and received explicitly for a Progress pattern");

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] IPTEST_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{SEND_OP,
        TESTSUBMIT_OP,
        REQUEST_OP,
        INVOKE_OP,
        PROGRESS_OP,
        MONITOR_OP,
        GETRESULT_OP,
        PUBLISHUPDATES_OP,
        PUBLISHREGISTER_OP,
        PUBLISHDEREGISTER_OP,
        TESTMULTIPLENOTIFY_OP,
        SENDMULTI_OP,
        SUBMITMULTI_OP,
        REQUESTMULTI_OP,
        INVOKEMULTI_OP,
        PROGRESSMULTI_OP,
        MONITORMULTI_OP,
        TESTREQUESTEMPTYBODY_OP,
        TESTINVOKEEMPTYBODY_OP,
        TESTPROGRESSEMPTYBODY_OP};

    /**
     * Creates an instance of the IPTest ServiceInfo.
     * 
     */
    public IPTestServiceInfo() {
        super(SERVICE_KEY, IPTEST_SERVICE_NAME, IPTEST_SERVICE_ELEMENTS, OPERATIONS);
    }

    @Override
    public org.ccsds.moims.mo.mal.MALArea getArea() {
        return org.ccsds.moims.mo.malprototype.MALPrototypeHelper.MALPROTOTYPE_AREA;
    }

    @Override
    public org.ccsds.moims.mo.mal.MOErrorException generateMOError(int errorNumber,
            Object extraInfo) {
        switch (errorNumber) {
            case 1:
                return new org.ccsds.moims.mo.malprototype.DataErrorException(extraInfo);
            case 2:
                return new org.ccsds.moims.mo.malprototype.TestObjectExistsException(extraInfo);
            case 3:
                return new org.ccsds.moims.mo.malprototype.TestErrorException(extraInfo);
        }
        return null;
    }

}
