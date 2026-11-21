package org.ccsds.moims.mo.mc.action;

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
import org.ccsds.moims.mo.mal.UnknownException;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mc.DuplicateException;
import org.ccsds.moims.mo.mc.InvalidException;
import org.ccsds.moims.mo.mc.MCHelper;
import org.ccsds.moims.mo.mc.RejectedException;
import org.ccsds.moims.mo.mc.structures.ActionExecutionRequest;

/**
 * Helper class for Action service.
 */
public class ActionServiceInfo extends ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _ACTION_SERVICE_NUMBER = 1;

    /**
     * Service number instance.
     */
    public static final UShort ACTION_SERVICE_NUMBER = new UShort(_ACTION_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier ACTION_SERVICE_NAME = new Identifier("Action");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            4, 2, ACTION_SERVICE_NUMBER);

    /**
     * Operation number literal for operation EXECUTE.
     */
    public static final int _EXECUTE_OP_NUMBER = 1;

    /**
     * Operation number instance for operation EXECUTE.
     */
    private static final UShort EXECUTE_OP_NUMBER = new UShort(_EXECUTE_OP_NUMBER);

    /**
     * Operation instance for operation EXECUTE.
     */
    public static final MALSubmitOperation EXECUTE_OP = new MALSubmitOperation(SERVICE_KEY, 
            EXECUTE_OP_NUMBER, 
            new Identifier("execute"), 
            new UShort(1), 
            new OperationField[] {
                new OperationField("executionRequest", false, ActionExecutionRequest.SHORT_FORM, "")}, 
            "The execute operation allows a consumer to request a provider to execute an action.");

    /**
     * Operation number literal for operation MONITOREXECUTION.
     */
    public static final int _MONITOREXECUTION_OP_NUMBER = 2;

    /**
     * Operation number instance for operation MONITOREXECUTION.
     */
    private static final UShort MONITOREXECUTION_OP_NUMBER = new UShort(_MONITOREXECUTION_OP_NUMBER);

    /**
     * Operation instance for operation MONITOREXECUTION.
     */
    public static final MALPubSubOperation MONITOREXECUTION_OP = new MALPubSubOperation(SERVICE_KEY, 
            MONITOREXECUTION_OP_NUMBER, 
            new Identifier("monitorExecution"), 
            new UShort(2), 
            new OperationField[] {
                new OperationField("progressEvent", false, null, "")}, 
            "The monitorExecution operation allows a consumer to be informed of the progress in the execution of an action or a set of actions.");

    /**
     * Key names instance for MONITOREXECUTION operation of pubsub interaction
     * pattern.
     */
    private static final Identifier [] _MONITOREXECUTION_OP_KEY_NAMES = {new Identifier("requestId"),
            new Identifier("actionKey"),
            new Identifier("actionCategory")};

    /**
     * Key names instance for MONITOREXECUTION operation of pubsub interaction
     * pattern.
     */
    private static final IdentifierList MONITOREXECUTION_OP_KEY_NAMES = new IdentifierList(new ArrayList<>(Arrays.asList(_MONITOREXECUTION_OP_KEY_NAMES)));

    /**
     * Area elements.
     */
    public static final Element[] ACTION_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{EXECUTE_OP,
        MONITOREXECUTION_OP};

    /**
     * Creates an instance of the Action ServiceInfo.
     * 
     */
    public ActionServiceInfo() {
        super(SERVICE_KEY, ACTION_SERVICE_NAME, ACTION_SERVICE_ELEMENTS, OPERATIONS);
    }

    @Override
    public MALArea getArea() {
        return MCHelper.MC_AREA;
    }

    @Override
    public MOErrorException generateMOError(int operationNumber,
            int errorNumber,
            Object extraInfo) {
        switch (operationNumber) {
            case 1:
                switch (errorNumber) {
                    case 2:
                        return new DuplicateException(extraInfo);
                    case 3:
                        return new InvalidException(extraInfo);
                    case 4:
                        return new RejectedException(extraInfo);
                    case 65551:
                        return new UnknownException(extraInfo);
                }
                break;
        }
        MOErrorException areaError = MCHelper.generateMOError(errorNumber, extraInfo);
        return (areaError != null) ? areaError : MALHelper.generateMOError(errorNumber, extraInfo);
    }

}
