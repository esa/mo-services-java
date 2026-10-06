package org.ccsds.moims.mo.mps.planningrequest;

import java.util.ArrayList;
import java.util.Arrays;
import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.ServiceKey;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mps.CancelFailedException;
import org.ccsds.moims.mo.mps.InvalidException;
import org.ccsds.moims.mo.mps.MPSHelper;
import org.ccsds.moims.mo.mps.UnsupportedException;
import org.ccsds.moims.mo.mps.UpdateFailedException;
import org.ccsds.moims.mo.mps.structures.PlanningRequestDetails;
import org.ccsds.moims.mo.mps.structures.PlanningRequestResponse;
import org.ccsds.moims.mo.mps.structures.RequestFilter;
import org.ccsds.moims.mo.mps.structures.RequestInstanceList;
import org.ccsds.moims.mo.mps.structures.RequestStatusUpdate;
import org.ccsds.moims.mo.mps.structures.RequestStatusUpdateList;
import org.ccsds.moims.mo.mps.structures.RequestSummaryStatusList;

/**
 * Helper class for PlanningRequest service.
 */
public class PlanningRequestServiceInfo extends ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _PLANNINGREQUEST_SERVICE_NUMBER = 1;

    /**
     * Service number instance.
     */
    public static final UShort PLANNINGREQUEST_SERVICE_NUMBER = new UShort(_PLANNINGREQUEST_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier PLANNINGREQUEST_SERVICE_NAME = new Identifier("PlanningRequest");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            5, 1, PLANNINGREQUEST_SERVICE_NUMBER);

    /**
     * Operation number literal for operation SUBMITREQUEST.
     */
    public static final int _SUBMITREQUEST_OP_NUMBER = 1;

    /**
     * Operation number instance for operation SUBMITREQUEST.
     */
    private static final UShort SUBMITREQUEST_OP_NUMBER = new UShort(_SUBMITREQUEST_OP_NUMBER);

    /**
     * Operation instance for operation SUBMITREQUEST.
     */
    public static final MALRequestOperation SUBMITREQUEST_OP = new MALRequestOperation(SERVICE_KEY, 
            SUBMITREQUEST_OP_NUMBER, 
            new Identifier("submitRequest"), 
            new UShort(1), 
            new OperationField[] {
                new OperationField("requestDetails", false, PlanningRequestDetails.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("requestResponse", false, PlanningRequestResponse.SHORT_FORM, "")}, 
            "The submitRequest operation sends a planning request to the provider, which then creates a corresponding RequestInstance object and returns its identity to the consumer.");

    /**
     * Operation number literal for operation GETREQUESTSUMMARIES.
     */
    public static final int _GETREQUESTSUMMARIES_OP_NUMBER = 2;

    /**
     * Operation number instance for operation GETREQUESTSUMMARIES.
     */
    private static final UShort GETREQUESTSUMMARIES_OP_NUMBER = new UShort(_GETREQUESTSUMMARIES_OP_NUMBER);

    /**
     * Operation instance for operation GETREQUESTSUMMARIES.
     */
    public static final MALRequestOperation GETREQUESTSUMMARIES_OP = new MALRequestOperation(SERVICE_KEY, 
            GETREQUESTSUMMARIES_OP_NUMBER, 
            new Identifier("getRequestSummaries"), 
            new UShort(1), 
            new OperationField[] {
                new OperationField("requestFilter", false, RequestFilter.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("requestSummaries", false, RequestSummaryStatusList.SHORT_FORM, "")}, 
            "The getRequestSummaries operation allows consumers to obtain a filtered list of currently available RequestInstances.  The request uses the RequestFilter structure to select the set of planning requests of interest, using the following keys: Domain of the RequestInstance; Reference to the RequestInstance; Creation date and time of the RequestInstance (as a time range); Reference to the RequestDefinition from which the RequestInstance was created; User ID of the PlanningUser who initiated the RequestInstance; User Reference supplied by the User when submitting the RequestInstance; Current status of the RequestInstance; Reference to the output Plan(s) generated in response to the RequestInstance. The response returns a list of RequestSummaryStatus structures containing references to the identities, descriptive header fields, and status of the RequestInstances that match the filter.");

    /**
     * Operation number literal for operation GETREQUESTSTATUS.
     */
    public static final int _GETREQUESTSTATUS_OP_NUMBER = 3;

    /**
     * Operation number instance for operation GETREQUESTSTATUS.
     */
    private static final UShort GETREQUESTSTATUS_OP_NUMBER = new UShort(_GETREQUESTSTATUS_OP_NUMBER);

    /**
     * Operation instance for operation GETREQUESTSTATUS.
     */
    public static final MALProgressOperation GETREQUESTSTATUS_OP = new MALProgressOperation(SERVICE_KEY, 
            GETREQUESTSTATUS_OP_NUMBER, 
            new Identifier("getRequestStatus"), 
            new UShort(1), 
            new OperationField[] {
                new OperationField("requestRefs", false, ObjectRefList.SHORT_FORM, "")}, 
            new OperationField[] {}, 
            new OperationField[] {
                new OperationField("requestStatuses", false, RequestStatusUpdateList.SHORT_FORM, "")}, 
            new OperationField[] {}, 
            "The getRequestStatus operation is used to obtain the current status of one or more known RequestInstances.  The operation uses the Progress interaction pattern, to allow the response to be spread across multiple messages.");

    /**
     * Operation number literal for operation CANCELREQUEST.
     */
    public static final int _CANCELREQUEST_OP_NUMBER = 4;

    /**
     * Operation number instance for operation CANCELREQUEST.
     */
    private static final UShort CANCELREQUEST_OP_NUMBER = new UShort(_CANCELREQUEST_OP_NUMBER);

    /**
     * Operation instance for operation CANCELREQUEST.
     */
    public static final MALSubmitOperation CANCELREQUEST_OP = new MALSubmitOperation(SERVICE_KEY, 
            CANCELREQUEST_OP_NUMBER, 
            new Identifier("cancelRequest"), 
            new UShort(2), 
            new OperationField[] {
                new OperationField("requestRef", false, ObjectRef.OBJECTREF_SHORT_FORM, "")}, 
            "The cancelRequest operation is used by a consumer to cancel a previously submitted planning request.  The service provider acknowledges the cancellation of the RequestInstance or returns an error.");

    /**
     * Operation number literal for operation UPDATEREQUEST.
     */
    public static final int _UPDATEREQUEST_OP_NUMBER = 5;

    /**
     * Operation number instance for operation UPDATEREQUEST.
     */
    private static final UShort UPDATEREQUEST_OP_NUMBER = new UShort(_UPDATEREQUEST_OP_NUMBER);

    /**
     * Operation instance for operation UPDATEREQUEST.
     */
    public static final MALRequestOperation UPDATEREQUEST_OP = new MALRequestOperation(SERVICE_KEY, 
            UPDATEREQUEST_OP_NUMBER, 
            new Identifier("updateRequest"), 
            new UShort(3), 
            new OperationField[] {
                new OperationField("requestRef", false, ObjectRef.OBJECTREF_SHORT_FORM, ""),
                new OperationField("requestDetails", false, PlanningRequestDetails.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("requestResponse", false, PlanningRequestResponse.SHORT_FORM, "")}, 
            "The updateRequest operation may be used to modify the PlanningRequestDetails associated with a previously submitted planning request.  This results in the creation of a new version of the RequestInstance (with the same key) by the service provider, which returns a reference to the new version to the consumer.");

    /**
     * Operation number literal for operation MONITORREQUESTSTATUS.
     */
    public static final int _MONITORREQUESTSTATUS_OP_NUMBER = 6;

    /**
     * Operation number instance for operation MONITORREQUESTSTATUS.
     */
    private static final UShort MONITORREQUESTSTATUS_OP_NUMBER = new UShort(_MONITORREQUESTSTATUS_OP_NUMBER);

    /**
     * Operation instance for operation MONITORREQUESTSTATUS.
     */
    public static final MALPubSubOperation MONITORREQUESTSTATUS_OP = new MALPubSubOperation(SERVICE_KEY, 
            MONITORREQUESTSTATUS_OP_NUMBER, 
            new Identifier("monitorRequestStatus"), 
            new UShort(4), 
            new OperationField[] {
                new OperationField("requestStatusUpdate", false, RequestStatusUpdate.SHORT_FORM, "")}, 
            "The monitorRequestStatus operation is used to subscribe to status updates for a filtered set of planning RequestInstances.  The operation uses the Publish-Subscribe interaction pattern, with the body of the notification message comprising a RequestStatusUpdate for a subscribed RequestInstance.");

    /**
     * Key names instance for MONITORREQUESTSTATUS operation of pubsub interaction
     * pattern.
     */
    private static final Identifier [] _MONITORREQUESTSTATUS_OP_KEY_NAMES = {new Identifier("instanceID"),
            new Identifier("definitionID"),
            new Identifier("userID"),
            new Identifier("userReference"),
            new Identifier("status"),
            new Identifier("outputPlanID")};

    /**
     * Key names instance for MONITORREQUESTSTATUS operation of pubsub interaction
     * pattern.
     */
    private static final IdentifierList MONITORREQUESTSTATUS_OP_KEY_NAMES = new IdentifierList(new ArrayList<>(Arrays.asList(_MONITORREQUESTSTATUS_OP_KEY_NAMES)));

    /**
     * Operation number literal for operation GETREQUEST.
     */
    public static final int _GETREQUEST_OP_NUMBER = 7;

    /**
     * Operation number instance for operation GETREQUEST.
     */
    private static final UShort GETREQUEST_OP_NUMBER = new UShort(_GETREQUEST_OP_NUMBER);

    /**
     * Operation instance for operation GETREQUEST.
     */
    public static final MALProgressOperation GETREQUEST_OP = new MALProgressOperation(SERVICE_KEY, 
            GETREQUEST_OP_NUMBER, 
            new Identifier("getRequest"), 
            new UShort(5), 
            new OperationField[] {
                new OperationField("requestRefs", false, ObjectRefList.SHORT_FORM, "")}, 
            new OperationField[] {}, 
            new OperationField[] {
                new OperationField("requestInstances", false, RequestInstanceList.SHORT_FORM, "")}, 
            new OperationField[] {}, 
            "The getRequest operation is used to obtain the full content of one or more known RequestInstances.  The operation uses the Progress interaction pattern, to allow the response to be spread across multiple messages.");

    /**
     * Area elements.
     */
    public static final Element[] PLANNINGREQUEST_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{SUBMITREQUEST_OP,
        GETREQUESTSUMMARIES_OP,
        GETREQUESTSTATUS_OP,
        CANCELREQUEST_OP,
        UPDATEREQUEST_OP,
        MONITORREQUESTSTATUS_OP,
        GETREQUEST_OP};

    /**
     * Creates an instance of the PlanningRequest ServiceInfo.
     * 
     */
    public PlanningRequestServiceInfo() {
        super(SERVICE_KEY, PLANNINGREQUEST_SERVICE_NAME, PLANNINGREQUEST_SERVICE_ELEMENTS, OPERATIONS);
    }

    @Override
    public MALArea getArea() {
        return MPSHelper.MPS_AREA;
    }

    @Override
    public MOErrorException generateMOError(int operationNumber,
            int errorNumber,
            Object extraInfo) {
        switch (operationNumber) {
            case 1:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
                    case 10:
                        return new UnsupportedException(extraInfo);
                }
                break;
            case 2:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
                }
                break;
            case 3:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
                }
                break;
            case 4:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
                    case 2:
                        return new CancelFailedException(extraInfo);
                }
                break;
            case 5:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
                    case 10:
                        return new UnsupportedException(extraInfo);
                    case 3:
                        return new UpdateFailedException(extraInfo);
                }
                break;
            case 7:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
                }
                break;
        }
        MOErrorException areaError = MPSHelper.generateMOError(errorNumber, extraInfo);
        return (areaError != null) ? areaError : MALHelper.generateMOError(errorNumber, extraInfo);
    }

}
