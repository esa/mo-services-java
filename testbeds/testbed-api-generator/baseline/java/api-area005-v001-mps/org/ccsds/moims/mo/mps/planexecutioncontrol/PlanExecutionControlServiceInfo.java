package org.ccsds.moims.mo.mps.planexecutioncontrol;

/**
 * Helper class for PlanExecutionControl service.
 */
public class PlanExecutionControlServiceInfo extends org.ccsds.moims.mo.mal.ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _PLANEXECUTIONCONTROL_SERVICE_NUMBER = 3;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort PLANEXECUTIONCONTROL_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_PLANEXECUTIONCONTROL_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier PLANEXECUTIONCONTROL_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("PlanExecutionControl");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            5, 1, PLANEXECUTIONCONTROL_SERVICE_NUMBER);

    /**
     * Operation number literal for operation SUBMITPLAN.
     */
    public static final int _SUBMITPLAN_OP_NUMBER = 1;

    /**
     * Operation number instance for operation SUBMITPLAN.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort SUBMITPLAN_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_SUBMITPLAN_OP_NUMBER);

    /**
     * Operation instance for operation SUBMITPLAN.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation SUBMITPLAN_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            SUBMITPLAN_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("submitPlan"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("plan", false, org.ccsds.moims.mo.mps.structures.Plan.SHORT_FORM, "")}, 
            "The submitPlan operation is used to send a plan to a plan execution function (the service provider), making it available for execution.  The service provider acknowledges the reception of the plan or returns an error. NOTE – The submitted plan may be a full plan or a patch plan.");

    /**
     * Operation number literal for operation REVOKEPLAN.
     */
    public static final int _REVOKEPLAN_OP_NUMBER = 2;

    /**
     * Operation number instance for operation REVOKEPLAN.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort REVOKEPLAN_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_REVOKEPLAN_OP_NUMBER);

    /**
     * Operation instance for operation REVOKEPLAN.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation REVOKEPLAN_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            REVOKEPLAN_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("revokePlan"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("planRef", false, org.ccsds.moims.mo.mal.structures.ObjectRef.OBJECTREF_SHORT_FORM, "")}, 
            "The revokePlan operation is used to request a plan execution function to revoke a previously submitted Plan, making it unavailable for execution.  The service provider acknowledges the revocation of the Plan or returns an error.");

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
                new org.ccsds.moims.mo.mal.OperationField("planStatus", false, org.ccsds.moims.mo.mps.structures.PlanUpdateList.SHORT_FORM, "")}, 
            "The getPlanStatus operation is used to obtain the current status of one or more known Plans that have been previously submitted to a plan execution function.");

    /**
     * Operation number literal for operation ACTIVATEPLAN.
     */
    public static final int _ACTIVATEPLAN_OP_NUMBER = 4;

    /**
     * Operation number instance for operation ACTIVATEPLAN.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort ACTIVATEPLAN_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ACTIVATEPLAN_OP_NUMBER);

    /**
     * Operation instance for operation ACTIVATEPLAN.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation ACTIVATEPLAN_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            ACTIVATEPLAN_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("activatePlan"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("planRefs", false, org.ccsds.moims.mo.mal.structures.ObjectRefList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("activationStatus", false, org.ccsds.moims.mo.mps.structures.PlanActivationStatusList.SHORT_FORM, "")}, 
            "The activatePlan operation is used to request the execution of specified Plans that have previously been submitted to a plan execution function.  The service provider enables the execution of the referenced Plans and the ActivityInstances contained within them, subject to the triggering constraints specified within the Plans.  It is not possible to activate a Plan outside its validity period, or after the start of the Plan period.  In this case, the operation will return an ACTIVATE_FAILED error. NOTES Multiple plans with a common precursor may have been submitted to a plan execution function.  Usually only one of these is considered the nominal plan, the other alternative or contingency plans having the isAlternate flag set.  It is implementation dependent whether the service provider will allow activation of Plans that have the isAlternate flag set, but this may be blocked for operational safety.  Where this is the case, the plan edit service can be used to change the state of the isAlternate flag prior to activation (see 3.9.5). In order to activate a patch Plan, the precursor Plan on which it is based must also be activated.  It is recommended that the activatePlan operation references the target Plan (the result of merging the patch Plan with its precursor), rather than the patch Plan itself (although this is allowed).  It is implementation dependent how it is achieved (merge patch with precursor prior to activation, or activate precursor and then merge patch), but if the precursor Plan is not already activated, then activating a target or patch Plan implies that the precursor is also activated.  If the precursor plan has not previously been submitted to the service provider (or has been revoked), then it is not possible to activate the target or patch Plan.");

    /**
     * Operation number literal for operation DEACTIVATEPLAN.
     */
    public static final int _DEACTIVATEPLAN_OP_NUMBER = 5;

    /**
     * Operation number instance for operation DEACTIVATEPLAN.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort DEACTIVATEPLAN_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_DEACTIVATEPLAN_OP_NUMBER);

    /**
     * Operation instance for operation DEACTIVATEPLAN.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation DEACTIVATEPLAN_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            DEACTIVATEPLAN_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("deactivatePlan"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("planRefs", false, org.ccsds.moims.mo.mal.structures.ObjectRefList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("deactivationMode", false, org.ccsds.moims.mo.mal.structures.Attribute.IDENTIFIER_SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("activationStatus", false, org.ccsds.moims.mo.mps.structures.PlanActivationStatusList.SHORT_FORM, "")}, 
            "The deactivatePlan operation is used to request deactivation of specified Plans that have previously been activated.  The service provider disables the execution of the referenced Plans and the ActivityInstances contained within them, where it is possible to do so. The deactivationMode argument allows selection of the deactivation behavior.  For example: Orderly (ceases execution of any new activities, but allows those already initiated to complete); Rapid (ceases execution of the Plan, but allows activities already initiated to continue until their next defined breakpoint); Immediate (ceases execution of the Plan and all activities currently in progress). It should be noted that it is dependent on the service provider implementation which deactivationModes are supported, and that the above list is not exhaustive. The service provider returns a list of PlanActivationStatus data structures comprising Plan status and activationInfo as a String for each Plan in the deactivation list.  The activationInfo allows the return of deployment specific details on the deactivation, such as the deactivation mode applied or reasons for a failure to deactivate. If a Plan is deactivated prior to any of its constituent ActivityInstances being executed (or before the specified planPeriodStart), then all new ActivityInstances and EventInstances contained in the Plan are unloaded or removed, and the status of the Plan reverts to SUBMITTED. If a Plan is deactivated after any of its constituent ActivityInstances have been executed (or after the specified planPeriodStart), then the status of the Plan and the status of all contained ActivityInstances and EventInstances that will not be executed are set to TERMINATED with the additional statusInfo ‘CANCELLED’.");

    /**
     * Operation number literal for operation MONITORPLANEXECUTION.
     */
    public static final int _MONITORPLANEXECUTION_OP_NUMBER = 6;

    /**
     * Operation number instance for operation MONITORPLANEXECUTION.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort MONITORPLANEXECUTION_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_MONITORPLANEXECUTION_OP_NUMBER);

    /**
     * Operation instance for operation MONITORPLANEXECUTION.
     */
    public static final org.ccsds.moims.mo.mal.MALPubSubOperation MONITORPLANEXECUTION_OP = new org.ccsds.moims.mo.mal.MALPubSubOperation(SERVICE_KEY, 
            MONITORPLANEXECUTION_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("monitorPlanExecution"), 
            new org.ccsds.moims.mo.mal.structures.UShort(3), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("planUpdate", false, org.ccsds.moims.mo.mps.structures.PlanUpdate.SHORT_FORM, "")}, 
            "The monitorPlanExecution operation is used to subscribe to status updates for a filtered set of Plans that have been submitted to a plan execution function.  The operation uses the Publish-Subscribe interaction pattern, with the body of the notification message comprising a PlanUpdate for a subscribed Plan. The operation is equivalent to the monitorPlanStatus operation of the Plan Distribution Service, but only reports the status of plans currently being managed by a plan execution function.");

    /**
     * Key names instance for MONITORPLANEXECUTION operation of pubsub interaction
     * pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.Identifier [] _MONITORPLANEXECUTION_OP_KEY_NAMES = {new org.ccsds.moims.mo.mal.structures.Identifier("planID"),
            new org.ccsds.moims.mo.mal.structures.Identifier("precursor"),
            new org.ccsds.moims.mo.mal.structures.Identifier("status"),
            new org.ccsds.moims.mo.mal.structures.Identifier("originator")};

    /**
     * Key names instance for MONITORPLANEXECUTION operation of pubsub interaction
     * pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.IdentifierList MONITORPLANEXECUTION_OP_KEY_NAMES = new org.ccsds.moims.mo.mal.structures.IdentifierList(new java.util.ArrayList<>(java.util.Arrays.asList(_MONITORPLANEXECUTION_OP_KEY_NAMES)));

    /**
     * Operation number literal for operation MONITORPLANEXECUTIONDETAIL.
     */
    public static final int _MONITORPLANEXECUTIONDETAIL_OP_NUMBER = 7;

    /**
     * Operation number instance for operation MONITORPLANEXECUTIONDETAIL.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort MONITORPLANEXECUTIONDETAIL_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_MONITORPLANEXECUTIONDETAIL_OP_NUMBER);

    /**
     * Operation instance for operation MONITORPLANEXECUTIONDETAIL.
     */
    public static final org.ccsds.moims.mo.mal.MALPubSubOperation MONITORPLANEXECUTIONDETAIL_OP = new org.ccsds.moims.mo.mal.MALPubSubOperation(SERVICE_KEY, 
            MONITORPLANEXECUTIONDETAIL_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("monitorPlanExecutionDetail"), 
            new org.ccsds.moims.mo.mal.structures.UShort(4), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("detailUpdate", false, null, "")}, 
            "The monitorPlanExecutionDetail operation is used to subscribe to updates that report changes in the detailed execution status for a filtered set of Plan contents at the level of planning activities, events and resources.  A planning function requires feedback at the level of planning activities and events to be able to reconstitute the status of planning requests, as well to support re-planning.  The operation uses the Publish-Subscribe interaction pattern. It is implementation dependent which details are reported on, but this may be any combination of planning activities, events, and resources.  The notification message body comprises a single structure of the abstract class PlanDetailUpdate, which corresponds to one of the concrete classes ActivityUpdate, EventUpdate, or ResourceUpdate.");

    /**
     * Key names instance for MONITORPLANEXECUTIONDETAIL operation of pubsub interaction
     * pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.Identifier [] _MONITORPLANEXECUTIONDETAIL_OP_KEY_NAMES = {new org.ccsds.moims.mo.mal.structures.Identifier("planID"),
            new org.ccsds.moims.mo.mal.structures.Identifier("subPlan"),
            new org.ccsds.moims.mo.mal.structures.Identifier("tag"),
            new org.ccsds.moims.mo.mal.structures.Identifier("type")};

    /**
     * Key names instance for MONITORPLANEXECUTIONDETAIL operation of pubsub interaction
     * pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.IdentifierList MONITORPLANEXECUTIONDETAIL_OP_KEY_NAMES = new org.ccsds.moims.mo.mal.structures.IdentifierList(new java.util.ArrayList<>(java.util.Arrays.asList(_MONITORPLANEXECUTIONDETAIL_OP_KEY_NAMES)));

    /**
     * Operation number literal for operation ACTIVATESUBPLAN.
     */
    public static final int _ACTIVATESUBPLAN_OP_NUMBER = 8;

    /**
     * Operation number instance for operation ACTIVATESUBPLAN.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort ACTIVATESUBPLAN_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ACTIVATESUBPLAN_OP_NUMBER);

    /**
     * Operation instance for operation ACTIVATESUBPLAN.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation ACTIVATESUBPLAN_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            ACTIVATESUBPLAN_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("activateSubPlan"), 
            new org.ccsds.moims.mo.mal.structures.UShort(5), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("subPlanIDs", false, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("activationStatus", false, org.ccsds.moims.mo.mps.structures.SubPlanActivationStatusList.SHORT_FORM, "")}, 
            "The activateSubPlan operation is used to request that the service provider activates the referenced SubPlans and enables the execution of ActivityInstances that are contained in activated Plans and allocated to activated SubPlans. NOTES It is implementation dependent whether SubPlans are initially ACTIVATED and therefore do not require activation unless previously deactivated. Where the operation is directly supported by the service provider there is little reason for the activation to fail, but if the operation is delegated, for example to an on-board planning function, there is the potential for the operation to fail.");

    /**
     * Operation number literal for operation DEACTIVATESUBPLAN.
     */
    public static final int _DEACTIVATESUBPLAN_OP_NUMBER = 9;

    /**
     * Operation number instance for operation DEACTIVATESUBPLAN.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort DEACTIVATESUBPLAN_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_DEACTIVATESUBPLAN_OP_NUMBER);

    /**
     * Operation instance for operation DEACTIVATESUBPLAN.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation DEACTIVATESUBPLAN_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            DEACTIVATESUBPLAN_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("deactivateSubPlan"), 
            new org.ccsds.moims.mo.mal.structures.UShort(5), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("subPlanIDs", false, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("deactivationMode", false, org.ccsds.moims.mo.mal.structures.Attribute.STRING_SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("activationStatus", false, org.ccsds.moims.mo.mps.structures.SubPlanActivationStatusList.SHORT_FORM, "")}, 
            "The deactivateSubPlan operation is used to request that the service provider deactivates the referenced SubPlans and disables the execution of ActivityInstances that are contained in activated Plans and allocated to the deactivated SubPlans, where it is possible to do so. The deactivationMode argument allows selection of the deactivation behavior.  For example: Orderly (ceases execution of any new activities, but allows those already initiated to complete); Rapid (ceases execution of the Sub-plan, but allows activities already initiated to continue until their next defined breakpoint); Immediate (ceases execution of the Sub-plan and all activities currently in progress). It should be noted that it is dependent on the service provider implementation which deactivationModes are supported, and that the above list is not exhaustive. The service provider returns a list of SubPlanActivationStatus data structures comprising sub-plan status and activationInfo as a String for each sub-plan in the deactivation list.  The activationInfo allows the return of deployment specific details on the deactivation, such as the deactivation mode applied or reasons for a failure to deactivate. NOTE – Where the operation is directly supported by the service provider there is little reason for the deactivation to fail, but if the operation is delegated, for example to an on-board planning function, there is the potential for the operation to fail.");

    /**
     * Operation number literal for operation GETSUBPLANSTATUS.
     */
    public static final int _GETSUBPLANSTATUS_OP_NUMBER = 10;

    /**
     * Operation number instance for operation GETSUBPLANSTATUS.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort GETSUBPLANSTATUS_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GETSUBPLANSTATUS_OP_NUMBER);

    /**
     * Operation instance for operation GETSUBPLANSTATUS.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation GETSUBPLANSTATUS_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            GETSUBPLANSTATUS_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("getSubPlanStatus"), 
            new org.ccsds.moims.mo.mal.structures.UShort(5), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("subPlanIDs", false, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("subPlanStatus", false, org.ccsds.moims.mo.mps.structures.SubPlanUpdateList.SHORT_FORM, "")}, 
            "The getSubPlanStatus operation is used to obtain the current status of one or more SubPlans.");

    /**
     * Operation number literal for operation MONITORSUBPLANEXECUTION.
     */
    public static final int _MONITORSUBPLANEXECUTION_OP_NUMBER = 11;

    /**
     * Operation number instance for operation MONITORSUBPLANEXECUTION.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort MONITORSUBPLANEXECUTION_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_MONITORSUBPLANEXECUTION_OP_NUMBER);

    /**
     * Operation instance for operation MONITORSUBPLANEXECUTION.
     */
    public static final org.ccsds.moims.mo.mal.MALPubSubOperation MONITORSUBPLANEXECUTION_OP = new org.ccsds.moims.mo.mal.MALPubSubOperation(SERVICE_KEY, 
            MONITORSUBPLANEXECUTION_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("monitorSubPlanExecution"), 
            new org.ccsds.moims.mo.mal.structures.UShort(6), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("subPlanUpdate", false, org.ccsds.moims.mo.mps.structures.SubPlanUpdate.SHORT_FORM, "")}, 
            "The monitorSubPlanExecution operation is used to subscribe to status updates for a filtered set of SubPlans.  The operation uses the Publish-Subscribe interaction pattern, with the body of the notification message comprising a SubPlanUpdate for a subscribed sub-plan.");

    /**
     * Key names instance for MONITORSUBPLANEXECUTION operation of pubsub interaction
     * pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.Identifier [] _MONITORSUBPLANEXECUTION_OP_KEY_NAMES = {new org.ccsds.moims.mo.mal.structures.Identifier("subPlan")};

    /**
     * Key names instance for MONITORSUBPLANEXECUTION operation of pubsub interaction
     * pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.IdentifierList MONITORSUBPLANEXECUTION_OP_KEY_NAMES = new org.ccsds.moims.mo.mal.structures.IdentifierList(new java.util.ArrayList<>(java.util.Arrays.asList(_MONITORSUBPLANEXECUTION_OP_KEY_NAMES)));

    /**
     * Operation number literal for operation SUSPENDACTIVITY.
     */
    public static final int _SUSPENDACTIVITY_OP_NUMBER = 12;

    /**
     * Operation number instance for operation SUSPENDACTIVITY.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort SUSPENDACTIVITY_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_SUSPENDACTIVITY_OP_NUMBER);

    /**
     * Operation instance for operation SUSPENDACTIVITY.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation SUSPENDACTIVITY_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            SUSPENDACTIVITY_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("suspendActivity"), 
            new org.ccsds.moims.mo.mal.structures.UShort(7), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("planRefs", true, org.ccsds.moims.mo.mal.structures.ObjectRefList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("activityRefs", true, org.ccsds.moims.mo.mal.structures.ObjectRefList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("tags", true, org.ccsds.moims.mo.mal.structures.StringList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("suspensionMode", false, org.ccsds.moims.mo.mal.structures.Attribute.STRING_SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("suspensionStatus", false, org.ccsds.moims.mo.mps.structures.ActivitySuspensionStatusList.SHORT_FORM, "")}, 
            "The suspendActivity operation is used to request suspension of the execution of selected activities in one or more plans, without changing the state of the plan(s). The suspensionMode argument allows selection of the suspension behavior.  For example: Orderly (suspends execution of any new activities, but allows those already initiated to complete); Rapid (suspends execution of any new activities, but allows any activities and their sub-activities already initiated to continue until their next defined breakpoint); Immediate (suspends execution of all activities, including those currently in progress). It should be noted that it is dependent on the service provider implementation which deactivationModes are supported, and that the above list is not exhaustive. The service provider responds with a list of ActivitySuspensionStatus data structures comprising activity status and suspensionInfo (as a String) for each activity subject to the suspension request. The suspensionInfo allows the return of deployment specific details on the suspension, such as the suspension mode applied or reasons for a failure to suspend.");

    /**
     * Operation number literal for operation RESUMEACTIVITY.
     */
    public static final int _RESUMEACTIVITY_OP_NUMBER = 13;

    /**
     * Operation number instance for operation RESUMEACTIVITY.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort RESUMEACTIVITY_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_RESUMEACTIVITY_OP_NUMBER);

    /**
     * Operation instance for operation RESUMEACTIVITY.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation RESUMEACTIVITY_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            RESUMEACTIVITY_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("resumeActivity"), 
            new org.ccsds.moims.mo.mal.structures.UShort(7), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("planRefs", true, org.ccsds.moims.mo.mal.structures.ObjectRefList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("activityRefs", true, org.ccsds.moims.mo.mal.structures.ObjectRefList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("tags", true, org.ccsds.moims.mo.mal.structures.StringList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("suspensionStatus", false, org.ccsds.moims.mo.mps.structures.ActivitySuspensionStatusList.SHORT_FORM, "")}, 
            "The resumeActivity operation is used to request resumption of the execution of selected activities in one or more plans, without changing the state of the plan(s). The service provider responds with a list of ActivitySuspensionStatus data structures comprising activity status and suspensionInfo (as a String) for each activity subject to the resumption request. The suspensionInfo allows the return of deployment specific details on the resumption, such as the reasons for a failure to resume.");

    /**
     * Operation number literal for operation GETACTIVITYSTATUS.
     */
    public static final int _GETACTIVITYSTATUS_OP_NUMBER = 14;

    /**
     * Operation number instance for operation GETACTIVITYSTATUS.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort GETACTIVITYSTATUS_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GETACTIVITYSTATUS_OP_NUMBER);

    /**
     * Operation instance for operation GETACTIVITYSTATUS.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation GETACTIVITYSTATUS_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            GETACTIVITYSTATUS_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("getActivityStatus"), 
            new org.ccsds.moims.mo.mal.structures.UShort(8), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("planRefs", true, org.ccsds.moims.mo.mal.structures.ObjectRefList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("activityRefs", true, org.ccsds.moims.mo.mal.structures.ObjectRefList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("subPlans", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("tags", true, org.ccsds.moims.mo.mal.structures.StringList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("activityStatus", false, org.ccsds.moims.mo.mps.structures.ActivityUpdateList.SHORT_FORM, "")}, 
            "The getActivityStatus operation is used to request a detailed report from the service provider on the current status of ActivityInstances, selected at activity, sub-plan, or tag levels.");

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] PLANEXECUTIONCONTROL_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{SUBMITPLAN_OP,
        REVOKEPLAN_OP,
        GETPLANSTATUS_OP,
        ACTIVATEPLAN_OP,
        DEACTIVATEPLAN_OP,
        MONITORPLANEXECUTION_OP,
        MONITORPLANEXECUTIONDETAIL_OP,
        ACTIVATESUBPLAN_OP,
        DEACTIVATESUBPLAN_OP,
        GETSUBPLANSTATUS_OP,
        MONITORSUBPLANEXECUTION_OP,
        SUSPENDACTIVITY_OP,
        RESUMEACTIVITY_OP,
        GETACTIVITYSTATUS_OP};

    /**
     * Creates an instance of the PlanExecutionControl ServiceInfo.
     * 
     */
    public PlanExecutionControlServiceInfo() {
        super(SERVICE_KEY, PLANEXECUTIONCONTROL_SERVICE_NAME, PLANEXECUTIONCONTROL_SERVICE_ELEMENTS, OPERATIONS);
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
