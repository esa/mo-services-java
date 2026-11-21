package org.ccsds.moims.mo.malprototype2.iptest;

import java.util.ArrayList;
import java.util.Arrays;
import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALSendOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.ServiceKey;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.malprototype.structures.TestPublishDeregister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishRegister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate;
import org.ccsds.moims.mo.malprototype.structures.TestUpdate;
import org.ccsds.moims.mo.malprototype2.MALPrototype2Helper;

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
            101, 1, IPTEST_SERVICE_NUMBER);

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
                new OperationField("pub", true, TestUpdate.SHORT_FORM, "")}, 
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
     * Operation number literal for operation MONITOR2.
     */
    public static final int _MONITOR2_OP_NUMBER = 106;

    /**
     * Operation number instance for operation MONITOR2.
     */
    private static final UShort MONITOR2_OP_NUMBER = new UShort(_MONITOR2_OP_NUMBER);

    /**
     * Operation instance for operation MONITOR2.
     */
    public static final MALPubSubOperation MONITOR2_OP = new MALPubSubOperation(SERVICE_KEY, 
            MONITOR2_OP_NUMBER, 
            new Identifier("monitor2"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("pub", true, TestUpdate.SHORT_FORM, "")}, 
            "This operation initiates a Pub/Sub interaction. It is not implemented by the service provider but by a broker.");

    /**
     * Key names instance for MONITOR2 operation of pubsub interaction pattern.
     */
    private static final Identifier [] _MONITOR2_OP_KEY_NAMES = {};

    /**
     * Key names instance for MONITOR2 operation of pubsub interaction pattern.
     */
    private static final IdentifierList MONITOR2_OP_KEY_NAMES = new IdentifierList(new ArrayList<>(Arrays.asList(_MONITOR2_OP_KEY_NAMES)));

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
     * Operation number literal for operation TESTOBJECTREFSUBMIT.
     */
    public static final int _TESTOBJECTREFSUBMIT_OP_NUMBER = 113;

    /**
     * Operation number instance for operation TESTOBJECTREFSUBMIT.
     */
    private static final UShort TESTOBJECTREFSUBMIT_OP_NUMBER = new UShort(_TESTOBJECTREFSUBMIT_OP_NUMBER);

    /**
     * Operation instance for operation TESTOBJECTREFSUBMIT.
     */
    public static final MALSubmitOperation TESTOBJECTREFSUBMIT_OP = new MALSubmitOperation(SERVICE_KEY, 
            TESTOBJECTREFSUBMIT_OP_NUMBER, 
            new Identifier("testObjectRefSubmit"), 
            new UShort(102), 
            new OperationField[] {
                new OperationField("input", true, ObjectRef.OBJECTREF_SHORT_FORM, "")}, 
            "");

    /**
     * Operation number literal for operation TESTOBJECTREFSEND.
     */
    public static final int _TESTOBJECTREFSEND_OP_NUMBER = 114;

    /**
     * Operation number instance for operation TESTOBJECTREFSEND.
     */
    private static final UShort TESTOBJECTREFSEND_OP_NUMBER = new UShort(_TESTOBJECTREFSEND_OP_NUMBER);

    /**
     * Operation instance for operation TESTOBJECTREFSEND.
     */
    public static final MALSendOperation TESTOBJECTREFSEND_OP = new MALSendOperation(SERVICE_KEY, 
            TESTOBJECTREFSEND_OP_NUMBER, 
            new Identifier("testObjectRefSend"), 
            new UShort(102), 
            new OperationField[] {
                new OperationField("input", true, ObjectRef.OBJECTREF_SHORT_FORM, "")}, 
            "");

    /**
     * Area elements.
     */
    public static final Element[] IPTEST_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{MONITOR_OP,
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
    public MALArea getArea() {
        return MALPrototype2Helper.MALPROTOTYPE2_AREA;
    }

    @Override
    public MOErrorException generateMOError(int operationNumber,
            int errorNumber,
            Object extraInfo) {
        switch (operationNumber) {
            case 105:
                switch (errorNumber) {
                }
                break;
            case 106:
                switch (errorNumber) {
                }
                break;
        }
        MOErrorException areaError = MALPrototype2Helper.generateMOError(errorNumber, extraInfo);
        return (areaError != null) ? areaError : MALHelper.generateMOError(errorNumber, extraInfo);
    }

}
