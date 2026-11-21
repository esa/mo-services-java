package org.ccsds.moims.mo.mps.plandistribution;

import java.util.ArrayList;
import java.util.Arrays;
import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.ServiceKey;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mps.InvalidException;
import org.ccsds.moims.mo.mps.MPSHelper;
import org.ccsds.moims.mo.mps.structures.PartialPlan;
import org.ccsds.moims.mo.mps.structures.PartialPlanFilter;
import org.ccsds.moims.mo.mps.structures.Plan;
import org.ccsds.moims.mo.mps.structures.PlanFilter;
import org.ccsds.moims.mo.mps.structures.PlanQuery;
import org.ccsds.moims.mo.mps.structures.PlanSummaryStatusList;
import org.ccsds.moims.mo.mps.structures.PlanUpdate;
import org.ccsds.moims.mo.mps.structures.PlanUpdateList;

/**
 * Helper class for PlanDistribution service.
 */
public class PlanDistributionServiceInfo extends ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _PLANDISTRIBUTION_SERVICE_NUMBER = 2;

    /**
     * Service number instance.
     */
    public static final UShort PLANDISTRIBUTION_SERVICE_NUMBER = new UShort(_PLANDISTRIBUTION_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier PLANDISTRIBUTION_SERVICE_NAME = new Identifier("PlanDistribution");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            5, 1, PLANDISTRIBUTION_SERVICE_NUMBER);

    /**
     * Operation number literal for operation GETPLANSUMMARIES.
     */
    public static final int _GETPLANSUMMARIES_OP_NUMBER = 1;

    /**
     * Operation number instance for operation GETPLANSUMMARIES.
     */
    private static final UShort GETPLANSUMMARIES_OP_NUMBER = new UShort(_GETPLANSUMMARIES_OP_NUMBER);

    /**
     * Operation instance for operation GETPLANSUMMARIES.
     */
    public static final MALRequestOperation GETPLANSUMMARIES_OP = new MALRequestOperation(SERVICE_KEY, 
            GETPLANSUMMARIES_OP_NUMBER, 
            new Identifier("getPlanSummaries"), 
            new UShort(1), 
            new OperationField[] {
                new OperationField("planFilter", false, PlanFilter.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("planSummaries", false, PlanSummaryStatusList.SHORT_FORM, "")}, 
            "The getPlanSummaries operation allows consumers to obtain a filtered list of currently available Plans.  The request uses the PlanFilter structure to select the set of plans of interest, using the following keys: Domain of the Plan; Reference to the Plan; Reference to the precursor Plan of the Plan; Current status of the Plan; Originator of the Plan; Validity period of the Plan (as a time window). The response returns a list of PlanSummaryStatus structures containing references to the identities, descriptive header fields, and status of the Plans that match the filter.");

    /**
     * Operation number literal for operation GETPLAN.
     */
    public static final int _GETPLAN_OP_NUMBER = 2;

    /**
     * Operation number instance for operation GETPLAN.
     */
    private static final UShort GETPLAN_OP_NUMBER = new UShort(_GETPLAN_OP_NUMBER);

    /**
     * Operation instance for operation GETPLAN.
     */
    public static final MALProgressOperation GETPLAN_OP = new MALProgressOperation(SERVICE_KEY, 
            GETPLAN_OP_NUMBER, 
            new Identifier("getPlan"), 
            new UShort(1), 
            new OperationField[] {
                new OperationField("planRefs", false, ObjectRefList.SHORT_FORM, "")}, 
            new OperationField[] {}, 
            new OperationField[] {
                new OperationField("retrievedPlan", false, Plan.SHORT_FORM, "")}, 
            new OperationField[] {}, 
            "The getPlan operation is used to obtain the full content of one or more known Plans.  The operation uses the Progress interaction pattern, to allow the response to be spread across multiple messages.");

    /**
     * Operation number literal for operation GETPLANSTATUS.
     */
    public static final int _GETPLANSTATUS_OP_NUMBER = 3;

    /**
     * Operation number instance for operation GETPLANSTATUS.
     */
    private static final UShort GETPLANSTATUS_OP_NUMBER = new UShort(_GETPLANSTATUS_OP_NUMBER);

    /**
     * Operation instance for operation GETPLANSTATUS.
     */
    public static final MALRequestOperation GETPLANSTATUS_OP = new MALRequestOperation(SERVICE_KEY, 
            GETPLANSTATUS_OP_NUMBER, 
            new Identifier("getPlanStatus"), 
            new UShort(1), 
            new OperationField[] {
                new OperationField("planRefs", false, ObjectRefList.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("responsePlans", false, PlanUpdateList.SHORT_FORM, "")}, 
            "The getPlanStatus operation is used to obtain the current status of one or more known Plans.  The operation uses the Request interaction pattern.");

    /**
     * Operation number literal for operation MONITORPLANSTATUS.
     */
    public static final int _MONITORPLANSTATUS_OP_NUMBER = 4;

    /**
     * Operation number instance for operation MONITORPLANSTATUS.
     */
    private static final UShort MONITORPLANSTATUS_OP_NUMBER = new UShort(_MONITORPLANSTATUS_OP_NUMBER);

    /**
     * Operation instance for operation MONITORPLANSTATUS.
     */
    public static final MALPubSubOperation MONITORPLANSTATUS_OP = new MALPubSubOperation(SERVICE_KEY, 
            MONITORPLANSTATUS_OP_NUMBER, 
            new Identifier("monitorPlanStatus"), 
            new UShort(2), 
            new OperationField[] {
                new OperationField("planUpdate", false, PlanUpdate.SHORT_FORM, "")}, 
            "The monitorPlanStatus operation is used to subscribe to status updates for a filtered set of Plans.  The operation uses the Publish-Subscribe interaction pattern, with the body of the notification message comprising a PlanUpdate for a subscribed Plan.");

    /**
     * Key names instance for MONITORPLANSTATUS operation of pubsub interaction
     * pattern.
     */
    private static final Identifier [] _MONITORPLANSTATUS_OP_KEY_NAMES = {new Identifier("planID"),
            new Identifier("precursor"),
            new Identifier("status"),
            new Identifier("originator")};

    /**
     * Key names instance for MONITORPLANSTATUS operation of pubsub interaction
     * pattern.
     */
    private static final IdentifierList MONITORPLANSTATUS_OP_KEY_NAMES = new IdentifierList(new ArrayList<>(Arrays.asList(_MONITORPLANSTATUS_OP_KEY_NAMES)));

    /**
     * Operation number literal for operation MONITORPLAN.
     */
    public static final int _MONITORPLAN_OP_NUMBER = 5;

    /**
     * Operation number instance for operation MONITORPLAN.
     */
    private static final UShort MONITORPLAN_OP_NUMBER = new UShort(_MONITORPLAN_OP_NUMBER);

    /**
     * Operation instance for operation MONITORPLAN.
     */
    public static final MALPubSubOperation MONITORPLAN_OP = new MALPubSubOperation(SERVICE_KEY, 
            MONITORPLAN_OP_NUMBER, 
            new Identifier("monitorPlan"), 
            new UShort(3), 
            new OperationField[] {
                new OperationField("plan", false, Plan.SHORT_FORM, "")}, 
            "The monitorPlan operation is used by a consumer to subscribe to receive new Plans, or new versions of Plans, as they published.  The operation uses the Publish-Subscribe interaction pattern, with the body of the notification message comprising a Plan.");

    /**
     * Key names instance for MONITORPLAN operation of pubsub interaction pattern.
     */
    private static final Identifier [] _MONITORPLAN_OP_KEY_NAMES = {new Identifier("planID"),
            new Identifier("precursor"),
            new Identifier("status"),
            new Identifier("originator")};

    /**
     * Key names instance for MONITORPLAN operation of pubsub interaction pattern.
     */
    private static final IdentifierList MONITORPLAN_OP_KEY_NAMES = new IdentifierList(new ArrayList<>(Arrays.asList(_MONITORPLAN_OP_KEY_NAMES)));

    /**
     * Operation number literal for operation QUERYPLAN.
     */
    public static final int _QUERYPLAN_OP_NUMBER = 6;

    /**
     * Operation number instance for operation QUERYPLAN.
     */
    private static final UShort QUERYPLAN_OP_NUMBER = new UShort(_QUERYPLAN_OP_NUMBER);

    /**
     * Operation instance for operation QUERYPLAN.
     */
    public static final MALProgressOperation QUERYPLAN_OP = new MALProgressOperation(SERVICE_KEY, 
            QUERYPLAN_OP_NUMBER, 
            new Identifier("queryPlan"), 
            new UShort(4), 
            new OperationField[] {
                new OperationField("query", false, PlanQuery.SHORT_FORM, "")}, 
            new OperationField[] {}, 
            new OperationField[] {
                new OperationField("queriedPlan", false, Plan.SHORT_FORM, "")}, 
            new OperationField[] {}, 
            "The queryPlan operation enables a consumer to retrieve a filtered set of plans, based on an extended set of filter criteria, including relevant fields of the plan information sections of the plan, as well as the type of planning activities and planning events contained within the plan.");

    /**
     * Operation number literal for operation GETPARTIALPLAN.
     */
    public static final int _GETPARTIALPLAN_OP_NUMBER = 7;

    /**
     * Operation number instance for operation GETPARTIALPLAN.
     */
    private static final UShort GETPARTIALPLAN_OP_NUMBER = new UShort(_GETPARTIALPLAN_OP_NUMBER);

    /**
     * Operation instance for operation GETPARTIALPLAN.
     */
    public static final MALRequestOperation GETPARTIALPLAN_OP = new MALRequestOperation(SERVICE_KEY, 
            GETPARTIALPLAN_OP_NUMBER, 
            new Identifier("getPartialPlan"), 
            new UShort(5), 
            new OperationField[] {
                new OperationField("partialPlanFilter", false, PartialPlanFilter.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("partialPlan", false, PartialPlan.SHORT_FORM, "")}, 
            "The getPartialPlan operation enables a consumer to extract a subset of a Plan that meets the supplied partialPlanFilter.  The filter can select the partial plan content based on: a shorter period than that covered by the plan, specified by time, position, or events; a subset of contained ActivityInstances, based on their domain, associated SubPlan or tags. The PartialPlan returned includes the filter criteria and a version of the plan containing only the ActivityInstances that match those criteria.  It is implementation dependent what is returned in terms of events and resources, but it may be assumed that any related events and resources would be included in the returned partial plan.");

    /**
     * Area elements.
     */
    public static final Element[] PLANDISTRIBUTION_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{GETPLANSUMMARIES_OP,
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
            case 6:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
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
