package org.ccsds.moims.mo.mps.planedit;

/**
 * Helper class for PlanEdit service.
 */
public class PlanEditServiceInfo extends org.ccsds.moims.mo.mal.ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _PLANEDIT_SERVICE_NUMBER = 5;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort PLANEDIT_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_PLANEDIT_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier PLANEDIT_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("PlanEdit");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            5, 1, PLANEDIT_SERVICE_NUMBER);

    /**
     * Operation number literal for operation UPDATEPLANSTATUS.
     */
    public static final int _UPDATEPLANSTATUS_OP_NUMBER = 1;

    /**
     * Operation number instance for operation UPDATEPLANSTATUS.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort UPDATEPLANSTATUS_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_UPDATEPLANSTATUS_OP_NUMBER);

    /**
     * Operation instance for operation UPDATEPLANSTATUS.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation UPDATEPLANSTATUS_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            UPDATEPLANSTATUS_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("updatePlanStatus"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("planRef", false, org.ccsds.moims.mo.mal.structures.ObjectRef.OBJECTREF_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("status", false, org.ccsds.moims.mo.mps.structures.PlanStatusEnum.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("isAlternate", false, org.ccsds.moims.mo.mal.structures.Attribute.BOOLEAN_SHORT_FORM, "")}, 
            "The updatePlanStatus operation may be used to modify the status of a previously submitted Plan.  Directly modifying the status field of a Plan may be used by a third party function to autonomously terminate (or activate) a Plan, but the operation also allows the isAlternate flag to be set or cleared. It is implementation dependent what action the service provider takes in response to a change of Plan status.  The service provider may not permit certain state changes (for example to modify the status of a TERMINATED plan, which is inconsistent with the plan status model), in which case an UPDATE_FAILED error shall be returned. A set of Plans with a common precursor may be submitted to a plan execution function to cater for alternative or contingency scenarios.  All but one of these Plans should have the isAlternate flag set, to inform the plan execution function (and the mission operations team) which is the nominal Plan.  It is implementation dependent whether a plan execution control service provider will allow a Plan to be activated with the isAlternate flag set, but for operational safety reasons this may be blocked.  In a contingency scenario, the updatePlanStatus operation can be used to set the flag on the nominal Plan, and reset the flag on the required contingency Plan, making it operational.");

    /**
     * Operation number literal for operation INSERTACTIVITY.
     */
    public static final int _INSERTACTIVITY_OP_NUMBER = 2;

    /**
     * Operation number instance for operation INSERTACTIVITY.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort INSERTACTIVITY_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_INSERTACTIVITY_OP_NUMBER);

    /**
     * Operation instance for operation INSERTACTIVITY.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation INSERTACTIVITY_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            INSERTACTIVITY_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("insertActivity"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("activityDetails", false, org.ccsds.moims.mo.mps.structures.InsertedActivityDetails.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("activityRef", false, org.ccsds.moims.mo.mal.structures.ObjectRef.OBJECTREF_SHORT_FORM, "")}, 
            "The insertActivity operation sends an InsertedActivityDetails structure (an ActivityDetails structure with Plan reference and start/end triggers) to the provider, which then creates a corresponding ActivityInstance object in the referenced Plan and returns its identity to the consumer.  It is up to the planning system, how to manage concurrent access to the plan. Insertion may fail if the Plan is already in the TERMINATED state, in which case an INSERT_FAILED error shall be returned.");

    /**
     * Operation number literal for operation INSERTEVENT.
     */
    public static final int _INSERTEVENT_OP_NUMBER = 3;

    /**
     * Operation number instance for operation INSERTEVENT.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort INSERTEVENT_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_INSERTEVENT_OP_NUMBER);

    /**
     * Operation instance for operation INSERTEVENT.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation INSERTEVENT_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            INSERTEVENT_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("insertEvent"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("eventDetails", false, org.ccsds.moims.mo.mps.structures.InsertedEventDetails.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("eventRef", false, org.ccsds.moims.mo.mal.structures.ObjectRef.OBJECTREF_SHORT_FORM, "")}, 
            "The insertEvent operation sends an InsertedEventDetails structure, which includes a Plan reference, to the provider, which then creates a corresponding EventInstance object in the referenced Plan and returns its identity to the consumer.  It is up to the planning system, how to manage concurrent access to the plan. Insertion may fail if the Plan is already in the TERMINATED state, in which case an INSERT_FAILED error shall be returned.");

    /**
     * Operation number literal for operation DELETEACTIVITY.
     */
    public static final int _DELETEACTIVITY_OP_NUMBER = 4;

    /**
     * Operation number instance for operation DELETEACTIVITY.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort DELETEACTIVITY_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_DELETEACTIVITY_OP_NUMBER);

    /**
     * Operation instance for operation DELETEACTIVITY.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation DELETEACTIVITY_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            DELETEACTIVITY_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("deleteActivity"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("planRef", false, org.ccsds.moims.mo.mal.structures.ObjectRef.OBJECTREF_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("activityRef", false, org.ccsds.moims.mo.mal.structures.ObjectRef.OBJECTREF_SHORT_FORM, "")}, 
            "The deleteActivity operation requests that a specified ActivityInstance within a Plan is deleted by the service provider.  In practice, the activity is not removed, but transitioned to the TERMINATED state with deletion indicated in the statusInfo field.  The ActivityInstance is not subsequently executed by the service provider, but it is implementation dependent what action is taken by the service provider if the ActivityInstance is in the EXECUTING state.  It is up to the planning system, how to manage concurrent access to the plan. Deletion may fail if the referenced Plan or ActivityInstance is already in the TERMINATED state, in which case the DELETE_FAILED error shall be returned.");

    /**
     * Operation number literal for operation DELETEEVENT.
     */
    public static final int _DELETEEVENT_OP_NUMBER = 5;

    /**
     * Operation number instance for operation DELETEEVENT.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort DELETEEVENT_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_DELETEEVENT_OP_NUMBER);

    /**
     * Operation instance for operation DELETEEVENT.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation DELETEEVENT_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            DELETEEVENT_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("deleteEvent"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("planRef", false, org.ccsds.moims.mo.mal.structures.ObjectRef.OBJECTREF_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("eventRef", false, org.ccsds.moims.mo.mal.structures.ObjectRef.OBJECTREF_SHORT_FORM, "")}, 
            "The deleteEvent operation requests that a specified EventInstance within a Plan is deleted by the service provider.  In practice, the event is not removed, but transitioned to the TERMINATED state with deletion indicated in the statusInfo field.  The EventInstance is not subsequently triggered by the service provider.  It is up to the planning system, how to manage concurrent access to the plan. Deletion may fail if the referenced Plan or EventInstance is already in the TERMINATED state, in which case the DELETE_FAILED error shall be returned.");

    /**
     * Operation number literal for operation UPDATEACTIVITY.
     */
    public static final int _UPDATEACTIVITY_OP_NUMBER = 6;

    /**
     * Operation number instance for operation UPDATEACTIVITY.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort UPDATEACTIVITY_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_UPDATEACTIVITY_OP_NUMBER);

    /**
     * Operation instance for operation UPDATEACTIVITY.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation UPDATEACTIVITY_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            UPDATEACTIVITY_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("updateActivity"), 
            new org.ccsds.moims.mo.mal.structures.UShort(3), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("planRef", false, org.ccsds.moims.mo.mal.structures.ObjectRef.OBJECTREF_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("activityUpdate", false, org.ccsds.moims.mo.mps.structures.ActivityUpdate.SHORT_FORM, "")}, 
            "The updateActivity operation may be used to modify an ActivityInstance in a Plan that has already been submitted to the service provider.  The consumer submits an ActivityUpdate structure which is applied by the service provider to the referenced ActivityInstance.  It is up to the planning system, how to manage concurrent access to the plan. Update may fail if the referenced Plan or ActivityInstance is already in the TERMINATED state, in which case the UPDATE_FAILED error shall be returned.");

    /**
     * Operation number literal for operation UPDATEEVENT.
     */
    public static final int _UPDATEEVENT_OP_NUMBER = 7;

    /**
     * Operation number instance for operation UPDATEEVENT.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort UPDATEEVENT_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_UPDATEEVENT_OP_NUMBER);

    /**
     * Operation instance for operation UPDATEEVENT.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation UPDATEEVENT_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            UPDATEEVENT_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("updateEvent"), 
            new org.ccsds.moims.mo.mal.structures.UShort(3), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("planRef", false, org.ccsds.moims.mo.mal.structures.ObjectRef.OBJECTREF_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("eventUpdate", false, org.ccsds.moims.mo.mps.structures.EventUpdate.SHORT_FORM, "")}, 
            "The updateEvent operation may be used to modify an EventInstance in a Plan that has already been submitted to the service provider.  The consumer submits an EventUpdate structure which is applied by the service provider to the referenced EventInstance.  It is up to the planning system, how to manage concurrent access to the plan. Update may fail if the referenced Plan or EventInstance is already in the TERMINATED state, in which case the UPDATE_FAILED error shall be returned.");

    /**
     * Operation number literal for operation UPDATERESOURCEVALUE.
     */
    public static final int _UPDATERESOURCEVALUE_OP_NUMBER = 8;

    /**
     * Operation number instance for operation UPDATERESOURCEVALUE.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort UPDATERESOURCEVALUE_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_UPDATERESOURCEVALUE_OP_NUMBER);

    /**
     * Operation instance for operation UPDATERESOURCEVALUE.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation UPDATERESOURCEVALUE_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            UPDATERESOURCEVALUE_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("updateResourceValue"), 
            new org.ccsds.moims.mo.mal.structures.UShort(4), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("planRef", false, org.ccsds.moims.mo.mal.structures.ObjectRef.OBJECTREF_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("resourceUpdate", false, org.ccsds.moims.mo.mps.structures.ResourceUpdate.SHORT_FORM, "")}, 
            "The updateResourceValue operation may be used to modify the value of a Resource at the specified point in time, in a Plan that has already been submitted to the service provider.  The consumer submits a ResourceUpdate structure which is applied by the service provider to the referenced Resource.  It is up to the planning system, how to manage concurrent access to the plan. Update may fail if the referenced Plan is already in the TERMINATED state, in which case the UPDATE_FAILED error shall be returned.");

    /**
     * Operation number literal for operation UPDATERESOURCEPROFILE.
     */
    public static final int _UPDATERESOURCEPROFILE_OP_NUMBER = 9;

    /**
     * Operation number instance for operation UPDATERESOURCEPROFILE.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort UPDATERESOURCEPROFILE_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_UPDATERESOURCEPROFILE_OP_NUMBER);

    /**
     * Operation instance for operation UPDATERESOURCEPROFILE.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation UPDATERESOURCEPROFILE_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            UPDATERESOURCEPROFILE_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("updateResourceProfile"), 
            new org.ccsds.moims.mo.mal.structures.UShort(5), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("planRef", false, org.ccsds.moims.mo.mal.structures.ObjectRef.OBJECTREF_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("resourceProfile", false, org.ccsds.moims.mo.mps.structures.ResourceProfile.SHORT_FORM, "")}, 
            "The updateResourceProfile operation may be used to modify the value of a Resource over a period of time, in a Plan that has already been submitted to the service provider.  The consumer submits a ResourceProfile structure which is applied by the service provider to the referenced Resource.  It is up to the planning system, how to manage concurrent access to the plan. Update may fail if the referenced Plan is already in the TERMINATED state, in which case the UPDATE_FAILED error shall be returned.");

    /**
     * Operation number literal for operation APPLYTIMESHIFT.
     */
    public static final int _APPLYTIMESHIFT_OP_NUMBER = 10;

    /**
     * Operation number instance for operation APPLYTIMESHIFT.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort APPLYTIMESHIFT_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_APPLYTIMESHIFT_OP_NUMBER);

    /**
     * Operation instance for operation APPLYTIMESHIFT.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation APPLYTIMESHIFT_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            APPLYTIMESHIFT_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("applyTimeShift"), 
            new org.ccsds.moims.mo.mal.structures.UShort(6), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("planRef", false, org.ccsds.moims.mo.mal.structures.ObjectRef.OBJECTREF_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("subPlans", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("timePeriod", false, org.ccsds.moims.mo.mps.structures.TimeWindow.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("offset", false, org.ccsds.moims.mo.mal.structures.Attribute.DURATION_SHORT_FORM, "")}, 
            "The applyTimeShift operation may be used to request a shift in the timing by a fixed offset of the ActivityInstances, EventInstances, and ResourceProfiles contained within a Plan that has previously been submitted to a plan execution function.  The operation may also be restricted to one or more SubPlans within the referenced Plan and/or to a specified time period within the Plan.  The service provider applies the time shift to the timing of ActivityInstances, EventInstances, and ResourceProfiles contained within the Plan or SubPlan(s). The time shift may fail if the referenced Plan is already in the TERMINATED state, in which case the UPDATE_FAILED error shall be returned. The operation is designed to support backward compatibility with simple time-based on-board schedules, and may not be appropriate for use with plans that include event or position-based triggers and resource profiles.  What is shifted within the Plan is implementation dependent, but shall include time-based start and end triggers on ActivityInstances.  EventInstances may also be shifted, but it is noted that some EventInstances correspond to predicted orbital events that cannot meaningfully be shifted.  Similarly, where supported, resource profiles may reflect the ActivityInstances contained within the Plan and if those are shifted, the corresponding changes in Resource value should also be shifted. NOTE – ActivityInstances have duration which means they may overlap the start or end of the specified TimeWindow for the applicability of the time shift.  It is implementation dependent how this is managed, but a reasonable assumption is that the start time of the ActivityInstances must be within the specified TimeWindow.  Given the potential to introduce inconsistencies into a Plan, it must be assumed that users of this service operation understand both its operational implications and its specific implementation.");

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] PLANEDIT_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{UPDATEPLANSTATUS_OP,
        INSERTACTIVITY_OP,
        INSERTEVENT_OP,
        DELETEACTIVITY_OP,
        DELETEEVENT_OP,
        UPDATEACTIVITY_OP,
        UPDATEEVENT_OP,
        UPDATERESOURCEVALUE_OP,
        UPDATERESOURCEPROFILE_OP,
        APPLYTIMESHIFT_OP};

    /**
     * Creates an instance of the PlanEdit ServiceInfo.
     * 
     */
    public PlanEditServiceInfo() {
        super(SERVICE_KEY, PLANEDIT_SERVICE_NAME, PLANEDIT_SERVICE_ELEMENTS, OPERATIONS);
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
