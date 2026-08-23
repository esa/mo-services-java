package org.ccsds.moims.mo.mps.planningrequest;

/**
 * Helper class for PlanningRequest service.
 */
public class PlanningRequestServiceInfo extends org.ccsds.moims.mo.mal.ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _PLANNINGREQUEST_SERVICE_NUMBER = 1;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort PLANNINGREQUEST_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_PLANNINGREQUEST_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier PLANNINGREQUEST_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("PlanningRequest");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            5, 1, PLANNINGREQUEST_SERVICE_NUMBER);

    /**
     * Operation number literal for operation SUBMITREQUEST.
     */
    public static final int _SUBMITREQUEST_OP_NUMBER = 1;

    /**
     * Operation number instance for operation SUBMITREQUEST.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort SUBMITREQUEST_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_SUBMITREQUEST_OP_NUMBER);

    /**
     * Operation instance for operation SUBMITREQUEST.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation SUBMITREQUEST_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            SUBMITREQUEST_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("submitRequest"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("requestDetails", false, org.ccsds.moims.mo.mps.structures.PlanningRequestDetails.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("requestResponse", false, org.ccsds.moims.mo.mps.structures.PlanningRequestResponse.SHORT_FORM, "")}, 
            "The submitRequest operation sends a planning request to the provider, which then creates a corresponding RequestInstance object and returns its identity to the consumer.");

    /**
     * Operation number literal for operation GETREQUESTSUMMARIES.
     */
    public static final int _GETREQUESTSUMMARIES_OP_NUMBER = 2;

    /**
     * Operation number instance for operation GETREQUESTSUMMARIES.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort GETREQUESTSUMMARIES_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GETREQUESTSUMMARIES_OP_NUMBER);

    /**
     * Operation instance for operation GETREQUESTSUMMARIES.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation GETREQUESTSUMMARIES_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            GETREQUESTSUMMARIES_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("getRequestSummaries"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("requestFilter", false, org.ccsds.moims.mo.mps.structures.RequestFilter.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("requestSummaries", false, org.ccsds.moims.mo.mps.structures.RequestSummaryStatusList.SHORT_FORM, "")}, 
            "The getRequestSummaries operation allows consumers to obtain a filtered list of currently available RequestInstances.  The request uses the RequestFilter structure to select the set of planning requests of interest, using the following keys: Domain of the RequestInstance; Reference to the RequestInstance; Creation date and time of the RequestInstance (as a time range); Reference to the RequestDefinition from which the RequestInstance was created; User ID of the PlanningUser who initiated the RequestInstance; User Reference supplied by the User when submitting the RequestInstance; Current status of the RequestInstance; Reference to the output Plan(s) generated in response to the RequestInstance. The response returns a list of RequestSummaryStatus structures containing references to the identities, descriptive header fields, and status of the RequestInstances that match the filter.");

    /**
     * Operation number literal for operation GETREQUESTSTATUS.
     */
    public static final int _GETREQUESTSTATUS_OP_NUMBER = 3;

    /**
     * Operation number instance for operation GETREQUESTSTATUS.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort GETREQUESTSTATUS_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GETREQUESTSTATUS_OP_NUMBER);

    /**
     * Operation instance for operation GETREQUESTSTATUS.
     */
    public static final org.ccsds.moims.mo.mal.MALProgressOperation GETREQUESTSTATUS_OP = new org.ccsds.moims.mo.mal.MALProgressOperation(SERVICE_KEY, 
            GETREQUESTSTATUS_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("getRequestStatus"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("requestRefs", false, org.ccsds.moims.mo.mal.structures.ObjectRefList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("requestStatuses", false, org.ccsds.moims.mo.mps.structures.RequestStatusUpdateList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            "The getRequestStatus operation is used to obtain the current status of one or more known RequestInstances.  The operation uses the Progress interaction pattern, to allow the response to be spread across multiple messages.");

    /**
     * Operation number literal for operation CANCELREQUEST.
     */
    public static final int _CANCELREQUEST_OP_NUMBER = 4;

    /**
     * Operation number instance for operation CANCELREQUEST.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort CANCELREQUEST_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_CANCELREQUEST_OP_NUMBER);

    /**
     * Operation instance for operation CANCELREQUEST.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation CANCELREQUEST_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            CANCELREQUEST_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("cancelRequest"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("requestRef", false, org.ccsds.moims.mo.mal.structures.ObjectRef.OBJECTREF_SHORT_FORM, "")}, 
            "The cancelRequest operation is used by a consumer to cancel a previously submitted planning request.  The service provider acknowledges the cancellation of the RequestInstance or returns an error.");

    /**
     * Operation number literal for operation UPDATEREQUEST.
     */
    public static final int _UPDATEREQUEST_OP_NUMBER = 5;

    /**
     * Operation number instance for operation UPDATEREQUEST.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort UPDATEREQUEST_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_UPDATEREQUEST_OP_NUMBER);

    /**
     * Operation instance for operation UPDATEREQUEST.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation UPDATEREQUEST_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            UPDATEREQUEST_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("updateRequest"), 
            new org.ccsds.moims.mo.mal.structures.UShort(3), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("requestRef", false, org.ccsds.moims.mo.mal.structures.ObjectRef.OBJECTREF_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("requestDetails", false, org.ccsds.moims.mo.mps.structures.PlanningRequestDetails.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("requestResponse", false, org.ccsds.moims.mo.mps.structures.PlanningRequestResponse.SHORT_FORM, "")}, 
            "The updateRequest operation may be used to modify the PlanningRequestDetails associated with a previously submitted planning request.  This results in the creation of a new version of the RequestInstance (with the same key) by the service provider, which returns a reference to the new version to the consumer.");

    /**
     * Operation number literal for operation MONITORREQUESTSTATUS.
     */
    public static final int _MONITORREQUESTSTATUS_OP_NUMBER = 6;

    /**
     * Operation number instance for operation MONITORREQUESTSTATUS.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort MONITORREQUESTSTATUS_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_MONITORREQUESTSTATUS_OP_NUMBER);

    /**
     * Operation instance for operation MONITORREQUESTSTATUS.
     */
    public static final org.ccsds.moims.mo.mal.MALPubSubOperation MONITORREQUESTSTATUS_OP = new org.ccsds.moims.mo.mal.MALPubSubOperation(SERVICE_KEY, 
            MONITORREQUESTSTATUS_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("monitorRequestStatus"), 
            new org.ccsds.moims.mo.mal.structures.UShort(4), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("requestStatusUpdate", false, org.ccsds.moims.mo.mps.structures.RequestStatusUpdate.SHORT_FORM, "")}, 
            "The monitorRequestStatus operation is used to subscribe to status updates for a filtered set of planning RequestInstances.  The operation uses the Publish-Subscribe interaction pattern, with the body of the notification message comprising a RequestStatusUpdate for a subscribed RequestInstance.");

    /**
     * Key names instance for MONITORREQUESTSTATUS operation of pubsub interaction
     * pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.Identifier [] _MONITORREQUESTSTATUS_OP_KEY_NAMES = {new org.ccsds.moims.mo.mal.structures.Identifier("instanceID"),
            new org.ccsds.moims.mo.mal.structures.Identifier("definitionID"),
            new org.ccsds.moims.mo.mal.structures.Identifier("userID"),
            new org.ccsds.moims.mo.mal.structures.Identifier("userReference"),
            new org.ccsds.moims.mo.mal.structures.Identifier("status"),
            new org.ccsds.moims.mo.mal.structures.Identifier("outputPlanID")};

    /**
     * Key names instance for MONITORREQUESTSTATUS operation of pubsub interaction
     * pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.IdentifierList MONITORREQUESTSTATUS_OP_KEY_NAMES = new org.ccsds.moims.mo.mal.structures.IdentifierList(new java.util.ArrayList<>(java.util.Arrays.asList(_MONITORREQUESTSTATUS_OP_KEY_NAMES)));

    /**
     * Operation number literal for operation GETREQUEST.
     */
    public static final int _GETREQUEST_OP_NUMBER = 7;

    /**
     * Operation number instance for operation GETREQUEST.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort GETREQUEST_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GETREQUEST_OP_NUMBER);

    /**
     * Operation instance for operation GETREQUEST.
     */
    public static final org.ccsds.moims.mo.mal.MALProgressOperation GETREQUEST_OP = new org.ccsds.moims.mo.mal.MALProgressOperation(SERVICE_KEY, 
            GETREQUEST_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("getRequest"), 
            new org.ccsds.moims.mo.mal.structures.UShort(5), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("requestRefs", false, org.ccsds.moims.mo.mal.structures.ObjectRefList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("requestInstances", false, org.ccsds.moims.mo.mps.structures.RequestInstanceList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            "The getRequest operation is used to obtain the full content of one or more known RequestInstances.  The operation uses the Progress interaction pattern, to allow the response to be spread across multiple messages.");

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] PLANNINGREQUEST_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{SUBMITREQUEST_OP,
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
    public org.ccsds.moims.mo.mal.MALArea getArea() {
        return org.ccsds.moims.mo.mps.MPSHelper.MPS_AREA;
    }

    @Override
    public org.ccsds.moims.mo.mal.MOErrorException generateMOError(int errorNumber,
            Object extraInfo) {
        switch (errorNumber) {
            case 1:
                return new org.ccsds.moims.mo.mps.InvalidException(extraInfo);
            case 2:
                return new org.ccsds.moims.mo.mps.CancelFailedException(extraInfo);
            case 3:
                return new org.ccsds.moims.mo.mps.UpdateFailedException(extraInfo);
            case 4:
                return new org.ccsds.moims.mo.mps.RevokeFailedException(extraInfo);
            case 5:
                return new org.ccsds.moims.mo.mps.InsertFailedException(extraInfo);
            case 6:
                return new org.ccsds.moims.mo.mps.DeleteFailedException(extraInfo);
            case 7:
                return new org.ccsds.moims.mo.mps.ActivateFailedException(extraInfo);
            case 8:
                return new org.ccsds.moims.mo.mps.DeactivateFailedException(extraInfo);
            case 9:
                return new org.ccsds.moims.mo.mps.SubmitFailedException(extraInfo);
            case 10:
                return new org.ccsds.moims.mo.mps.UnsupportedException(extraInfo);
            case 11:
                return new org.ccsds.moims.mo.mps.ActivateSubplanFailedException(extraInfo);
            case 12:
                return new org.ccsds.moims.mo.mps.DeactivateSubplanFailedException(extraInfo);
        }
        return null;
    }

}
