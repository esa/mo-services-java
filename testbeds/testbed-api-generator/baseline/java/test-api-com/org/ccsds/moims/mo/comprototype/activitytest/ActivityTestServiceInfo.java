package org.ccsds.moims.mo.comprototype.activitytest;

import org.ccsds.moims.mo.comprototype.COMPrototypeHelper;
import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInvokeOperation;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MALSendOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.ServiceKey;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * Helper class for ActivityTest service.
 */
public class ActivityTestServiceInfo extends ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _ACTIVITYTEST_SERVICE_NUMBER = 4;

    /**
     * Service number instance.
     */
    public static final UShort ACTIVITYTEST_SERVICE_NUMBER = new UShort(_ACTIVITYTEST_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier ACTIVITYTEST_SERVICE_NAME = new Identifier("ActivityTest");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            200, 1, ACTIVITYTEST_SERVICE_NUMBER);

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
     * Operation number literal for operation CLOSE.
     */
    public static final int _CLOSE_OP_NUMBER = 104;

    /**
     * Operation number instance for operation CLOSE.
     */
    private static final UShort CLOSE_OP_NUMBER = new UShort(_CLOSE_OP_NUMBER);

    /**
     * Operation instance for operation CLOSE.
     */
    public static final MALSubmitOperation CLOSE_OP = new MALSubmitOperation(SERVICE_KEY, 
            CLOSE_OP_NUMBER, 
            new Identifier("close"), 
            new UShort(100), 
            new OperationField[] {}, 
            "Closes the service provider");

    /**
     * Operation number literal for operation SEND.
     */
    public static final int _SEND_OP_NUMBER = 200;

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
            new UShort(101), 
            new OperationField[] {
                new OperationField("in1", true, StringList.SHORT_FORM, "")}, 
            "");

    /**
     * Operation number literal for operation TESTSUBMIT.
     */
    public static final int _TESTSUBMIT_OP_NUMBER = 201;

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
            new UShort(101), 
            new OperationField[] {
                new OperationField("in1", true, StringList.SHORT_FORM, "")}, 
            "");

    /**
     * Operation number literal for operation REQUEST.
     */
    public static final int _REQUEST_OP_NUMBER = 202;

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
            new UShort(101), 
            new OperationField[] {
                new OperationField("in1", true, StringList.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("out1", true, StringList.SHORT_FORM, "")}, 
            "");

    /**
     * Operation number literal for operation INVOKE.
     */
    public static final int _INVOKE_OP_NUMBER = 203;

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
            new UShort(101), 
            new OperationField[] {
                new OperationField("in1", true, StringList.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("ack1", true, StringList.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("out1", true, StringList.SHORT_FORM, "")}, 
            "");

    /**
     * Operation number literal for operation PROGRESS.
     */
    public static final int _PROGRESS_OP_NUMBER = 204;

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
            new UShort(101), 
            new OperationField[] {
                new OperationField("in1", true, StringList.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("ack1", true, StringList.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("out1", true, StringList.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("out2", true, StringList.SHORT_FORM, "")}, 
            "");

    /**
     * Area elements.
     */
    public static final Element[] ACTIVITYTEST_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{RESETTEST_OP,
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
    public MALArea getArea() {
        return COMPrototypeHelper.COMPROTOTYPE_AREA;
    }

    @Override
    public MOErrorException generateMOError(int operationNumber,
            int errorNumber,
            Object extraInfo) {
        switch (operationNumber) {
            case 201:
                switch (errorNumber) {
                }
                break;
            case 202:
                switch (errorNumber) {
                }
                break;
            case 203:
                switch (errorNumber) {
                }
                break;
            case 204:
                switch (errorNumber) {
                }
                break;
        }
        MOErrorException areaError = COMPrototypeHelper.generateMOError(errorNumber, extraInfo);
        return (areaError != null) ? areaError : MALHelper.generateMOError(errorNumber, extraInfo);
    }

}
