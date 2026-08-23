package org.ccsds.moims.mo.mps.plandistribution;

/**
 * Helper class for PlanDistribution service.
 */
public class PlanDistributionServiceInfo extends org.ccsds.moims.mo.mal.ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _PLANDISTRIBUTION_SERVICE_NUMBER = 2;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort PLANDISTRIBUTION_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_PLANDISTRIBUTION_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier PLANDISTRIBUTION_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("PlanDistribution");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            5, 1, PLANDISTRIBUTION_SERVICE_NUMBER);

    /**
     * Operation number literal for operation GETPLANSUMMARIES.
     */
    public static final int _GETPLANSUMMARIES_OP_NUMBER = 1;

    /**
     * Operation number instance for operation GETPLANSUMMARIES.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort GETPLANSUMMARIES_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GETPLANSUMMARIES_OP_NUMBER);

    /**
     * Operation instance for operation GETPLANSUMMARIES.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation GETPLANSUMMARIES_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            GETPLANSUMMARIES_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("getPlanSummaries"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("planFilter", false, org.ccsds.moims.mo.mps.structures.PlanFilter.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("planSummaries", false, org.ccsds.moims.mo.mps.structures.PlanSummaryStatusList.SHORT_FORM, "")}, 
            "The getPlanSummaries operation allows consumers to obtain a filtered list of currently available Plans.  The request uses the PlanFilter structure to select the set of plans of interest, using the following keys: Domain of the Plan; Reference to the Plan; Reference to the precursor Plan of the Plan; Current status of the Plan; Originator of the Plan; Validity period of the Plan (as a time window). The response returns a list of PlanSummaryStatus structures containing references to the identities, descriptive header fields, and status of the Plans that match the filter.");

    /**
     * Operation number literal for operation GETPLAN.
     */
    public static final int _GETPLAN_OP_NUMBER = 2;

    /**
     * Operation number instance for operation GETPLAN.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort GETPLAN_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GETPLAN_OP_NUMBER);

    /**
     * Operation instance for operation GETPLAN.
     */
    public static final org.ccsds.moims.mo.mal.MALProgressOperation GETPLAN_OP = new org.ccsds.moims.mo.mal.MALProgressOperation(SERVICE_KEY, 
            GETPLAN_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("getPlan"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("planRefs", false, org.ccsds.moims.mo.mal.structures.ObjectRefList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("retrievedPlan", false, org.ccsds.moims.mo.mps.structures.Plan.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            "The getPlan operation is used to obtain the full content of one or more known Plans.  The operation uses the Progress interaction pattern, to allow the response to be spread across multiple messages.");

    /**
     * Operation number literal for operation GETPLANSTATUS.
     */
    public static final int _GETPLANSTATUS_OP_NUMBER = 3;

    /**
     * Operation number instance for operation GETPLANSTATUS.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort GETPLANSTATUS_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GETPLANSTATUS_OP_NUMBER);

    /**
     * Operation instance for operation GETPLANSTATUS.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation GETPLANSTATUS_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            GETPLANSTATUS_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("getPlanStatus"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("planRefs", false, org.ccsds.moims.mo.mal.structures.ObjectRefList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("responsePlans", false, org.ccsds.moims.mo.mps.structures.PlanUpdateList.SHORT_FORM, "")}, 
            "The getPlanStatus operation is used to obtain the current status of one or more known Plans.  The operation uses the Request interaction pattern.");

    /**
     * Operation number literal for operation MONITORPLANSTATUS.
     */
    public static final int _MONITORPLANSTATUS_OP_NUMBER = 4;

    /**
     * Operation number instance for operation MONITORPLANSTATUS.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort MONITORPLANSTATUS_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_MONITORPLANSTATUS_OP_NUMBER);

    /**
     * Operation instance for operation MONITORPLANSTATUS.
     */
    public static final org.ccsds.moims.mo.mal.MALPubSubOperation MONITORPLANSTATUS_OP = new org.ccsds.moims.mo.mal.MALPubSubOperation(SERVICE_KEY, 
            MONITORPLANSTATUS_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("monitorPlanStatus"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("planUpdate", false, org.ccsds.moims.mo.mps.structures.PlanUpdate.SHORT_FORM, "")}, 
            "The monitorPlanStatus operation is used to subscribe to status updates for a filtered set of Plans.  The operation uses the Publish-Subscribe interaction pattern, with the body of the notification message comprising a PlanUpdate for a subscribed Plan.");

    /**
     * Key names instance for MONITORPLANSTATUS operation of pubsub interaction
     * pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.Identifier [] _MONITORPLANSTATUS_OP_KEY_NAMES = {new org.ccsds.moims.mo.mal.structures.Identifier("planID"),
            new org.ccsds.moims.mo.mal.structures.Identifier("precursor"),
            new org.ccsds.moims.mo.mal.structures.Identifier("status"),
            new org.ccsds.moims.mo.mal.structures.Identifier("originator")};

    /**
     * Key names instance for MONITORPLANSTATUS operation of pubsub interaction
     * pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.IdentifierList MONITORPLANSTATUS_OP_KEY_NAMES = new org.ccsds.moims.mo.mal.structures.IdentifierList(new java.util.ArrayList<>(java.util.Arrays.asList(_MONITORPLANSTATUS_OP_KEY_NAMES)));

    /**
     * Operation number literal for operation MONITORPLAN.
     */
    public static final int _MONITORPLAN_OP_NUMBER = 5;

    /**
     * Operation number instance for operation MONITORPLAN.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort MONITORPLAN_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_MONITORPLAN_OP_NUMBER);

    /**
     * Operation instance for operation MONITORPLAN.
     */
    public static final org.ccsds.moims.mo.mal.MALPubSubOperation MONITORPLAN_OP = new org.ccsds.moims.mo.mal.MALPubSubOperation(SERVICE_KEY, 
            MONITORPLAN_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("monitorPlan"), 
            new org.ccsds.moims.mo.mal.structures.UShort(3), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("plan", false, org.ccsds.moims.mo.mps.structures.Plan.SHORT_FORM, "")}, 
            "The monitorPlan operation is used by a consumer to subscribe to receive new Plans, or new versions of Plans, as they published.  The operation uses the Publish-Subscribe interaction pattern, with the body of the notification message comprising a Plan.");

    /**
     * Key names instance for MONITORPLAN operation of pubsub interaction pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.Identifier [] _MONITORPLAN_OP_KEY_NAMES = {new org.ccsds.moims.mo.mal.structures.Identifier("planID"),
            new org.ccsds.moims.mo.mal.structures.Identifier("precursor"),
            new org.ccsds.moims.mo.mal.structures.Identifier("status"),
            new org.ccsds.moims.mo.mal.structures.Identifier("originator")};

    /**
     * Key names instance for MONITORPLAN operation of pubsub interaction pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.IdentifierList MONITORPLAN_OP_KEY_NAMES = new org.ccsds.moims.mo.mal.structures.IdentifierList(new java.util.ArrayList<>(java.util.Arrays.asList(_MONITORPLAN_OP_KEY_NAMES)));

    /**
     * Operation number literal for operation QUERYPLAN.
     */
    public static final int _QUERYPLAN_OP_NUMBER = 6;

    /**
     * Operation number instance for operation QUERYPLAN.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort QUERYPLAN_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_QUERYPLAN_OP_NUMBER);

    /**
     * Operation instance for operation QUERYPLAN.
     */
    public static final org.ccsds.moims.mo.mal.MALProgressOperation QUERYPLAN_OP = new org.ccsds.moims.mo.mal.MALProgressOperation(SERVICE_KEY, 
            QUERYPLAN_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("queryPlan"), 
            new org.ccsds.moims.mo.mal.structures.UShort(4), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("query", false, org.ccsds.moims.mo.mps.structures.PlanQuery.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("queriedPlan", false, org.ccsds.moims.mo.mps.structures.Plan.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            "The queryPlan operation enables a consumer to retrieve a filtered set of plans, based on an extended set of filter criteria, including relevant fields of the plan information sections of the plan, as well as the type of planning activities and planning events contained within the plan.");

    /**
     * Operation number literal for operation GETPARTIALPLAN.
     */
    public static final int _GETPARTIALPLAN_OP_NUMBER = 7;

    /**
     * Operation number instance for operation GETPARTIALPLAN.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort GETPARTIALPLAN_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GETPARTIALPLAN_OP_NUMBER);

    /**
     * Operation instance for operation GETPARTIALPLAN.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation GETPARTIALPLAN_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            GETPARTIALPLAN_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("getPartialPlan"), 
            new org.ccsds.moims.mo.mal.structures.UShort(5), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("partialPlanFilter", false, org.ccsds.moims.mo.mps.structures.PartialPlanFilter.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("partialPlan", false, org.ccsds.moims.mo.mps.structures.PartialPlan.SHORT_FORM, "")}, 
            "The getPartialPlan operation enables a consumer to extract a subset of a Plan that meets the supplied partialPlanFilter.  The filter can select the partial plan content based on: a shorter period than that covered by the plan, specified by time, position, or events; a subset of contained ActivityInstances, based on their domain, associated SubPlan or tags. The PartialPlan returned includes the filter criteria and a version of the plan containing only the ActivityInstances that match those criteria.  It is implementation dependent what is returned in terms of events and resources, but it may be assumed that any related events and resources would be included in the returned partial plan.");

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] PLANDISTRIBUTION_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{GETPLANSUMMARIES_OP,
        GETPLAN_OP,
        GETPLANSTATUS_OP,
        MONITORPLANSTATUS_OP,
        MONITORPLAN_OP,
        QUERYPLAN_OP,
        GETPARTIALPLAN_OP};

    /**
     * Creates an instance of the PlanDistribution ServiceInfo.
     * 
     */
    public PlanDistributionServiceInfo() {
        super(SERVICE_KEY, PLANDISTRIBUTION_SERVICE_NAME, PLANDISTRIBUTION_SERVICE_ELEMENTS, OPERATIONS);
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
