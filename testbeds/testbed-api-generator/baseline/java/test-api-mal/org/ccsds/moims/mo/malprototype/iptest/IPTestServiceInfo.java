package org.ccsds.moims.mo.malprototype.iptest;

import java.util.ArrayList;
import java.util.Arrays;
import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInvokeOperation;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MALSendOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.ServiceKey;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.malprototype.MALPrototypeHelper;
import org.ccsds.moims.mo.malprototype.TestErrorException;
import org.ccsds.moims.mo.malprototype.structures.IPTestDefinition;
import org.ccsds.moims.mo.malprototype.structures.IPTestResult;
import org.ccsds.moims.mo.malprototype.structures.TestPublishDeregister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishRegister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate;
import org.ccsds.moims.mo.malprototype.structures.TestUpdate;

/**
 * Helper class for IPTest service.
 */
public class IPTestServiceInfo extends ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _IPTEST_SERVICE_NUMBER = 1;

    /**
     * Service number instance.
     */
    public static final UShort IPTEST_SERVICE_NUMBER = new UShort(_IPTEST_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier IPTEST_SERVICE_NAME = new Identifier("IPTest");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            100, 1, IPTEST_SERVICE_NUMBER);

    /**
     * Operation number literal for operation SEND.
     */
    public static final int _SEND_OP_NUMBER = 100;

    /**
     * Operation number instance for operation SEND.
     */
    private static final UShort SEND_OP_NUMBER = new UShort(_SEND_OP_NUMBER);

    /**
     * Operation instance for operation SEND.
     */
    public static final MALSendOperation SEND_OP = new MALSendOperation(SERVICE_KEY, 
            SEND_OP_NUMBER, 
            new Identifier("send"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input", true, IPTestDefinition.SHORT_FORM, "")}, 
            "This operation cleans the assertions table and check that the header of the received message is the same as the one expected (see 4.1.1).");

    /**
     * Operation number literal for operation TESTSUBMIT.
     */
    public static final int _TESTSUBMIT_OP_NUMBER = 101;

    /**
     * Operation number instance for operation TESTSUBMIT.
     */
    private static final UShort TESTSUBMIT_OP_NUMBER = new UShort(_TESTSUBMIT_OP_NUMBER);

    /**
     * Operation instance for operation TESTSUBMIT.
     */
    public static final MALSubmitOperation TESTSUBMIT_OP = new MALSubmitOperation(SERVICE_KEY, 
            TESTSUBMIT_OP_NUMBER, 
            new Identifier("testSubmit"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input", true, IPTestDefinition.SHORT_FORM, "")}, 
            "This operation cleans the assertions table and checks that the header of the received message is the same as the one expected (see 4.1.1). Moreover it triggers the transitions specified by the IPTestDefinition (see 3.1.1.1).");

    /**
     * Operation number literal for operation REQUEST.
     */
    public static final int _REQUEST_OP_NUMBER = 102;

    /**
     * Operation number instance for operation REQUEST.
     */
    private static final UShort REQUEST_OP_NUMBER = new UShort(_REQUEST_OP_NUMBER);

    /**
     * Operation instance for operation REQUEST.
     */
    public static final MALRequestOperation REQUEST_OP = new MALRequestOperation(SERVICE_KEY, 
            REQUEST_OP_NUMBER, 
            new Identifier("request"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input", true, IPTestDefinition.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output", true, Attribute.STRING_SHORT_FORM, "")}, 
            "This operation cleans the assertions table and checks that the header of the received message is the same as the one expected (see 4.1.1). Moreover it triggers the transitions specified by the IPTestDefinition (see 3.1.1.1).");

    /**
     * Operation number literal for operation INVOKE.
     */
    public static final int _INVOKE_OP_NUMBER = 103;

    /**
     * Operation number instance for operation INVOKE.
     */
    private static final UShort INVOKE_OP_NUMBER = new UShort(_INVOKE_OP_NUMBER);

    /**
     * Operation instance for operation INVOKE.
     */
    public static final MALInvokeOperation INVOKE_OP = new MALInvokeOperation(SERVICE_KEY, 
            INVOKE_OP_NUMBER, 
            new Identifier("invoke"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input", true, IPTestDefinition.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("ack", true, Attribute.STRING_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output", true, Attribute.STRING_SHORT_FORM, "")}, 
            "This operation cleans the assertions table and checks that the header of the received message is the same as the one expected (see 4.1.1). Moreover it triggers the transitions specified by the IPTestDefinition (see 3.1.1.1).");

    /**
     * Operation number literal for operation PROGRESS.
     */
    public static final int _PROGRESS_OP_NUMBER = 104;

    /**
     * Operation number instance for operation PROGRESS.
     */
    private static final UShort PROGRESS_OP_NUMBER = new UShort(_PROGRESS_OP_NUMBER);

    /**
     * Operation instance for operation PROGRESS.
     */
    public static final MALProgressOperation PROGRESS_OP = new MALProgressOperation(SERVICE_KEY, 
            PROGRESS_OP_NUMBER, 
            new Identifier("progress"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input", true, IPTestDefinition.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("ack", true, Attribute.STRING_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("update", true, Attribute.INTEGER_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("response", true, Attribute.STRING_SHORT_FORM, "")}, 
            "This operation cleans the assertions table and checks that the header of the received message is the same as the one expected (see 4.1.1). Moreover it triggers the transitions specified by the IPTestDefinition (see 3.1.1.1).");

    /**
     * Operation number literal for operation MONITOR.
     */
    public static final int _MONITOR_OP_NUMBER = 105;

    /**
     * Operation number instance for operation MONITOR.
     */
    private static final UShort MONITOR_OP_NUMBER = new UShort(_MONITOR_OP_NUMBER);

    /**
     * Operation instance for operation MONITOR.
     */
    public static final MALPubSubOperation MONITOR_OP = new MALPubSubOperation(SERVICE_KEY, 
            MONITOR_OP_NUMBER, 
            new Identifier("monitor"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("pubField", true, TestUpdate.SHORT_FORM, "")}, 
            "This operation initiates a Pub/Sub interaction. It is not implemented by the service provider but by a broker.");

    /**
     * Key names instance for MONITOR operation of pubsub interaction pattern.
     */
    private static final Identifier [] _MONITOR_OP_KEY_NAMES = {};

    /**
     * Key names instance for MONITOR operation of pubsub interaction pattern.
     */
    private static final IdentifierList MONITOR_OP_KEY_NAMES = new IdentifierList(new ArrayList<>(Arrays.asList(_MONITOR_OP_KEY_NAMES)));

    /**
     * Operation number literal for operation GETRESULT.
     */
    public static final int _GETRESULT_OP_NUMBER = 106;

    /**
     * Operation number instance for operation GETRESULT.
     */
    private static final UShort GETRESULT_OP_NUMBER = new UShort(_GETRESULT_OP_NUMBER);

    /**
     * Operation instance for operation GETRESULT.
     */
    public static final MALRequestOperation GETRESULT_OP = new MALRequestOperation(SERVICE_KEY, 
            GETRESULT_OP_NUMBER, 
            new Identifier("getResult"), 
            new UShort(101), 
            new OperationField[] {
                new OperationField("input", true, null, "")}, 
            new OperationField[] {
                new OperationField("output", true, IPTestResult.SHORT_FORM, "")}, 
            "This operation returns an IPTestResult.");

    /**
     * Operation number literal for operation PUBLISHUPDATES.
     */
    public static final int _PUBLISHUPDATES_OP_NUMBER = 108;

    /**
     * Operation number instance for operation PUBLISHUPDATES.
     */
    private static final UShort PUBLISHUPDATES_OP_NUMBER = new UShort(_PUBLISHUPDATES_OP_NUMBER);

    /**
     * Operation instance for operation PUBLISHUPDATES.
     */
    public static final MALSubmitOperation PUBLISHUPDATES_OP = new MALSubmitOperation(SERVICE_KEY, 
            PUBLISHUPDATES_OP_NUMBER, 
            new Identifier("publishUpdates"), 
            new UShort(102), 
            new OperationField[] {
                new OperationField("input", true, TestPublishUpdate.SHORT_FORM, "")}, 
            "This operation cleans the assertions table, publishes an update as specified by the parameter TestPublishUpdate and checks the header of the Publish message (see 4.1.3). Moreover if an error is expected by the TestPublishUpdate then the operation hangs until a Publish error is raised or a timer ends (see 4.1.10). The header of the Publish error message is checked (see 4.1.4).");

    /**
     * Operation number literal for operation PUBLISHREGISTER.
     */
    public static final int _PUBLISHREGISTER_OP_NUMBER = 110;

    /**
     * Operation number instance for operation PUBLISHREGISTER.
     */
    private static final UShort PUBLISHREGISTER_OP_NUMBER = new UShort(_PUBLISHREGISTER_OP_NUMBER);

    /**
     * Operation instance for operation PUBLISHREGISTER.
     */
    public static final MALSubmitOperation PUBLISHREGISTER_OP = new MALSubmitOperation(SERVICE_KEY, 
            PUBLISHREGISTER_OP_NUMBER, 
            new Identifier("publishRegister"), 
            new UShort(102), 
            new OperationField[] {
                new OperationField("input", true, TestPublishRegister.SHORT_FORM, "")}, 
            "This operation cleans the assertions table, registers a publisher as specified by the parameter TestPublishRegister and checks the header of the Publish Register message (see 4.1.5). Moreover if no error is expected by the TestPublishRegister, it checks the header of the Publish Register acknowledgement message (see 4.1.6). Otherwise it checks the header of the Publish Register error message (see 4.1.7).");

    /**
     * Operation number literal for operation PUBLISHDEREGISTER.
     */
    public static final int _PUBLISHDEREGISTER_OP_NUMBER = 111;

    /**
     * Operation number instance for operation PUBLISHDEREGISTER.
     */
    private static final UShort PUBLISHDEREGISTER_OP_NUMBER = new UShort(_PUBLISHDEREGISTER_OP_NUMBER);

    /**
     * Operation instance for operation PUBLISHDEREGISTER.
     */
    public static final MALSubmitOperation PUBLISHDEREGISTER_OP = new MALSubmitOperation(SERVICE_KEY, 
            PUBLISHDEREGISTER_OP_NUMBER, 
            new Identifier("publishDeregister"), 
            new UShort(102), 
            new OperationField[] {
                new OperationField("input", true, TestPublishDeregister.SHORT_FORM, "")}, 
            "This operation cleans the assertions table, registers a publisher as specified by the parameter TestPublishDeregister and checks the header of the Publish Deregister message (see 4.1.5). Moreover if no error is expected by the TestPublishDeregister, it checks the header of the Publish Deregister acknowledgement message (see 4.1.8). Otherwise it checks the header of the Publish Deregister error message (see 4.1.9).");

    /**
     * Operation number literal for operation TESTMULTIPLENOTIFY.
     */
    public static final int _TESTMULTIPLENOTIFY_OP_NUMBER = 112;

    /**
     * Operation number instance for operation TESTMULTIPLENOTIFY.
     */
    private static final UShort TESTMULTIPLENOTIFY_OP_NUMBER = new UShort(_TESTMULTIPLENOTIFY_OP_NUMBER);

    /**
     * Operation instance for operation TESTMULTIPLENOTIFY.
     */
    public static final MALSubmitOperation TESTMULTIPLENOTIFY_OP = new MALSubmitOperation(SERVICE_KEY, 
            TESTMULTIPLENOTIFY_OP_NUMBER, 
            new Identifier("testMultipleNotify"), 
            new UShort(102), 
            new OperationField[] {
                new OperationField("input", true, TestPublishUpdate.SHORT_FORM, "")}, 
            "");

    /**
     * Operation number literal for operation SENDMULTI.
     */
    public static final int _SENDMULTI_OP_NUMBER = 113;

    /**
     * Operation number instance for operation SENDMULTI.
     */
    private static final UShort SENDMULTI_OP_NUMBER = new UShort(_SENDMULTI_OP_NUMBER);

    /**
     * Operation instance for operation SENDMULTI.
     */
    public static final MALSendOperation SENDMULTI_OP = new MALSendOperation(SERVICE_KEY, 
            SENDMULTI_OP_NUMBER, 
            new Identifier("sendMulti"), 
            new UShort(103), 
            new OperationField[] {
                new OperationField("input1", true, IPTestDefinition.SHORT_FORM, ""),
                new OperationField("input2", true, null, "")}, 
            "This operation duplicates the send operation with an additional IN parameter.");

    /**
     * Operation number literal for operation SUBMITMULTI.
     */
    public static final int _SUBMITMULTI_OP_NUMBER = 114;

    /**
     * Operation number instance for operation SUBMITMULTI.
     */
    private static final UShort SUBMITMULTI_OP_NUMBER = new UShort(_SUBMITMULTI_OP_NUMBER);

    /**
     * Operation instance for operation SUBMITMULTI.
     */
    public static final MALSubmitOperation SUBMITMULTI_OP = new MALSubmitOperation(SERVICE_KEY, 
            SUBMITMULTI_OP_NUMBER, 
            new Identifier("submitMulti"), 
            new UShort(103), 
            new OperationField[] {
                new OperationField("input1", true, IPTestDefinition.SHORT_FORM, ""),
                new OperationField("input2", true, null, "")}, 
            "This operation duplicates the testSubmit operation with an additional IN parameter.");

    /**
     * Operation number literal for operation REQUESTMULTI.
     */
    public static final int _REQUESTMULTI_OP_NUMBER = 115;

    /**
     * Operation number instance for operation REQUESTMULTI.
     */
    private static final UShort REQUESTMULTI_OP_NUMBER = new UShort(_REQUESTMULTI_OP_NUMBER);

    /**
     * Operation instance for operation REQUESTMULTI.
     */
    public static final MALRequestOperation REQUESTMULTI_OP = new MALRequestOperation(SERVICE_KEY, 
            REQUESTMULTI_OP_NUMBER, 
            new Identifier("requestMulti"), 
            new UShort(103), 
            new OperationField[] {
                new OperationField("input1", true, IPTestDefinition.SHORT_FORM, ""),
                new OperationField("input2", true, null, "")}, 
            new OperationField[] {
                new OperationField("output1", true, Attribute.STRING_SHORT_FORM, ""),
                new OperationField("output2", true, null, "")}, 
            "This operation duplicates the request operation with an additional IN parameter.");

    /**
     * Operation number literal for operation INVOKEMULTI.
     */
    public static final int _INVOKEMULTI_OP_NUMBER = 116;

    /**
     * Operation number instance for operation INVOKEMULTI.
     */
    private static final UShort INVOKEMULTI_OP_NUMBER = new UShort(_INVOKEMULTI_OP_NUMBER);

    /**
     * Operation instance for operation INVOKEMULTI.
     */
    public static final MALInvokeOperation INVOKEMULTI_OP = new MALInvokeOperation(SERVICE_KEY, 
            INVOKEMULTI_OP_NUMBER, 
            new Identifier("invokeMulti"), 
            new UShort(103), 
            new OperationField[] {
                new OperationField("input1", true, IPTestDefinition.SHORT_FORM, ""),
                new OperationField("input2", true, null, "")}, 
            new OperationField[] {
                new OperationField("ack1", true, Attribute.STRING_SHORT_FORM, ""),
                new OperationField("ack2", true, null, "")}, 
            new OperationField[] {
                new OperationField("output1", true, Attribute.STRING_SHORT_FORM, ""),
                new OperationField("output2", true, null, "")}, 
            "This operation cleans the assertions table and checks that the header of the received message is the same as the one expected (see 4.1.1). Moreover it triggers the transitions specified by the IPTestDefinition (see 4.1.2).");

    /**
     * Operation number literal for operation PROGRESSMULTI.
     */
    public static final int _PROGRESSMULTI_OP_NUMBER = 117;

    /**
     * Operation number instance for operation PROGRESSMULTI.
     */
    private static final UShort PROGRESSMULTI_OP_NUMBER = new UShort(_PROGRESSMULTI_OP_NUMBER);

    /**
     * Operation instance for operation PROGRESSMULTI.
     */
    public static final MALProgressOperation PROGRESSMULTI_OP = new MALProgressOperation(SERVICE_KEY, 
            PROGRESSMULTI_OP_NUMBER, 
            new Identifier("progressMulti"), 
            new UShort(103), 
            new OperationField[] {
                new OperationField("input1", true, IPTestDefinition.SHORT_FORM, ""),
                new OperationField("input2", true, null, "")}, 
            new OperationField[] {
                new OperationField("ack1", true, Attribute.STRING_SHORT_FORM, ""),
                new OperationField("ack2", true, null, "")}, 
            new OperationField[] {
                new OperationField("output1", true, Attribute.INTEGER_SHORT_FORM, ""),
                new OperationField("output2", true, null, "")}, 
            new OperationField[] {
                new OperationField("output3", true, Attribute.STRING_SHORT_FORM, ""),
                new OperationField("output4", true, null, "")}, 
            "This operation duplicates the progress operation with an additional IN parameter.");

    /**
     * Operation number literal for operation MONITORMULTI.
     */
    public static final int _MONITORMULTI_OP_NUMBER = 118;

    /**
     * Operation number instance for operation MONITORMULTI.
     */
    private static final UShort MONITORMULTI_OP_NUMBER = new UShort(_MONITORMULTI_OP_NUMBER);

    /**
     * Operation instance for operation MONITORMULTI.
     */
    public static final MALPubSubOperation MONITORMULTI_OP = new MALPubSubOperation(SERVICE_KEY, 
            MONITORMULTI_OP_NUMBER, 
            new Identifier("monitorMulti"), 
            new UShort(103), 
            new OperationField[] {
                new OperationField("output1", true, TestUpdate.SHORT_FORM, ""),
                new OperationField("output2", true, null, "")}, 
            "This operation duplicates the monitor operation with an additional IN parameter.");

    /**
     * Key names instance for MONITORMULTI operation of pubsub interaction pattern.
     */
    private static final Identifier [] _MONITORMULTI_OP_KEY_NAMES = {};

    /**
     * Key names instance for MONITORMULTI operation of pubsub interaction pattern.
     */
    private static final IdentifierList MONITORMULTI_OP_KEY_NAMES = new IdentifierList(new ArrayList<>(Arrays.asList(_MONITORMULTI_OP_KEY_NAMES)));

    /**
     * Operation number literal for operation TESTREQUESTEMPTYBODY.
     */
    public static final int _TESTREQUESTEMPTYBODY_OP_NUMBER = 120;

    /**
     * Operation number instance for operation TESTREQUESTEMPTYBODY.
     */
    private static final UShort TESTREQUESTEMPTYBODY_OP_NUMBER = new UShort(_TESTREQUESTEMPTYBODY_OP_NUMBER);

    /**
     * Operation instance for operation TESTREQUESTEMPTYBODY.
     */
    public static final MALRequestOperation TESTREQUESTEMPTYBODY_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTREQUESTEMPTYBODY_OP_NUMBER, 
            new Identifier("testRequestEmptyBody"), 
            new UShort(104), 
            new OperationField[] {
                new OperationField("input1", true, IPTestDefinition.SHORT_FORM, "")}, 
            new OperationField[] {}, 
            "This operation checks that an empty body can be sent and received explicitly for a request pattern");

    /**
     * Operation number literal for operation TESTINVOKEEMPTYBODY.
     */
    public static final int _TESTINVOKEEMPTYBODY_OP_NUMBER = 121;

    /**
     * Operation number instance for operation TESTINVOKEEMPTYBODY.
     */
    private static final UShort TESTINVOKEEMPTYBODY_OP_NUMBER = new UShort(_TESTINVOKEEMPTYBODY_OP_NUMBER);

    /**
     * Operation instance for operation TESTINVOKEEMPTYBODY.
     */
    public static final MALInvokeOperation TESTINVOKEEMPTYBODY_OP = new MALInvokeOperation(SERVICE_KEY, 
            TESTINVOKEEMPTYBODY_OP_NUMBER, 
            new Identifier("testInvokeEmptyBody"), 
            new UShort(104), 
            new OperationField[] {
                new OperationField("input1", true, IPTestDefinition.SHORT_FORM, "")}, 
            new OperationField[] {}, 
            new OperationField[] {}, 
            "This operation checks that an empty body can be sent and received explicitly for an Invoke pattern");

    /**
     * Operation number literal for operation TESTPROGRESSEMPTYBODY.
     */
    public static final int _TESTPROGRESSEMPTYBODY_OP_NUMBER = 122;

    /**
     * Operation number instance for operation TESTPROGRESSEMPTYBODY.
     */
    private static final UShort TESTPROGRESSEMPTYBODY_OP_NUMBER = new UShort(_TESTPROGRESSEMPTYBODY_OP_NUMBER);

    /**
     * Operation instance for operation TESTPROGRESSEMPTYBODY.
     */
    public static final MALProgressOperation TESTPROGRESSEMPTYBODY_OP = new MALProgressOperation(SERVICE_KEY, 
            TESTPROGRESSEMPTYBODY_OP_NUMBER, 
            new Identifier("testProgressEmptyBody"), 
            new UShort(104), 
            new OperationField[] {
                new OperationField("input1", true, IPTestDefinition.SHORT_FORM, "")}, 
            new OperationField[] {}, 
            new OperationField[] {}, 
            new OperationField[] {}, 
            "This operation checks that an empty body can be sent and received explicitly for a Progress pattern");

    /**
     * Area elements.
     */
    public static final Element[] IPTEST_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{SEND_OP,
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
    public MALArea getArea() {
        return MALPrototypeHelper.MALPROTOTYPE_AREA;
    }

    @Override
    public MOErrorException generateMOError(int operationNumber,
            int errorNumber,
            Object extraInfo) {
        switch (operationNumber) {
            case 101:
                switch (errorNumber) {
                    case 3:
                        return new TestErrorException(extraInfo);
                }
                break;
            case 102:
                switch (errorNumber) {
                    case 3:
                        return new TestErrorException(extraInfo);
                }
                break;
            case 103:
                switch (errorNumber) {
                    case 3:
                        return new TestErrorException(extraInfo);
                }
                break;
            case 104:
                switch (errorNumber) {
                    case 3:
                        return new TestErrorException(extraInfo);
                }
                break;
            case 105:
                switch (errorNumber) {
                }
                break;
            case 114:
                switch (errorNumber) {
                    case 3:
                        return new TestErrorException(extraInfo);
                }
                break;
            case 115:
                switch (errorNumber) {
                    case 3:
                        return new TestErrorException(extraInfo);
                }
                break;
            case 116:
                switch (errorNumber) {
                    case 3:
                        return new TestErrorException(extraInfo);
                }
                break;
            case 117:
                switch (errorNumber) {
                    case 3:
                        return new TestErrorException(extraInfo);
                }
                break;
            case 118:
                switch (errorNumber) {
                }
                break;
        }
        MOErrorException areaError = MALPrototypeHelper.generateMOError(errorNumber, extraInfo);
        return (areaError != null) ? areaError : MALHelper.generateMOError(errorNumber, extraInfo);
    }

}
