package org.ccsds.moims.mo.malprototype.iptest2;

import java.util.ArrayList;
import java.util.Arrays;
import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.ServiceKey;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.malprototype.MALPrototypeHelper;
import org.ccsds.moims.mo.malprototype.structures.TestPublishDeregister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishRegister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate;
import org.ccsds.moims.mo.malprototype.structures.TestUpdate;

/**
 * Helper class for IPTest2 service.
 */
public class IPTest2ServiceInfo extends ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _IPTEST2_SERVICE_NUMBER = 4;

    /**
     * Service number instance.
     */
    public static final UShort IPTEST2_SERVICE_NUMBER = new UShort(_IPTEST2_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier IPTEST2_SERVICE_NAME = new Identifier("IPTest2");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            100, 1, IPTEST2_SERVICE_NUMBER);

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
     * Area elements.
     */
    public static final Element[] IPTEST2_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{MONITOR_OP,
        PUBLISHUPDATES_OP,
        PUBLISHREGISTER_OP,
        PUBLISHDEREGISTER_OP,
        TESTMULTIPLENOTIFY_OP};

    /**
     * Creates an instance of the IPTest2 ServiceInfo.
     * 
     */
    public IPTest2ServiceInfo() {
        super(SERVICE_KEY, IPTEST2_SERVICE_NAME, IPTEST2_SERVICE_ELEMENTS, OPERATIONS);
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
            case 105:
                switch (errorNumber) {
                }
                break;
        }
        MOErrorException areaError = MALPrototypeHelper.generateMOError(errorNumber, extraInfo);
        return (areaError != null) ? areaError : MALHelper.generateMOError(errorNumber, extraInfo);
    }

}
