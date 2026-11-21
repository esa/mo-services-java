package org.ccsds.moims.mo.mps.planedit;

import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.ServiceKey;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mps.DeleteFailedException;
import org.ccsds.moims.mo.mps.InsertFailedException;
import org.ccsds.moims.mo.mps.InvalidException;
import org.ccsds.moims.mo.mps.MPSHelper;
import org.ccsds.moims.mo.mps.UnsupportedException;
import org.ccsds.moims.mo.mps.UpdateFailedException;
import org.ccsds.moims.mo.mps.structures.ActivityUpdate;
import org.ccsds.moims.mo.mps.structures.EventUpdate;
import org.ccsds.moims.mo.mps.structures.InsertedActivityDetails;
import org.ccsds.moims.mo.mps.structures.InsertedEventDetails;
import org.ccsds.moims.mo.mps.structures.PlanStatusEnum;
import org.ccsds.moims.mo.mps.structures.ResourceProfile;
import org.ccsds.moims.mo.mps.structures.ResourceUpdate;
import org.ccsds.moims.mo.mps.structures.TimeWindow;

/**
 * Helper class for PlanEdit service.
 */
public class PlanEditServiceInfo extends ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _PLANEDIT_SERVICE_NUMBER = 5;

    /**
     * Service number instance.
     */
    public static final UShort PLANEDIT_SERVICE_NUMBER = new UShort(_PLANEDIT_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier PLANEDIT_SERVICE_NAME = new Identifier("PlanEdit");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            5, 1, PLANEDIT_SERVICE_NUMBER);

    /**
     * Operation number literal for operation UPDATEPLANSTATUS.
     */
    public static final int _UPDATEPLANSTATUS_OP_NUMBER = 1;

    /**
     * Operation number instance for operation UPDATEPLANSTATUS.
     */
    private static final UShort UPDATEPLANSTATUS_OP_NUMBER = new UShort(_UPDATEPLANSTATUS_OP_NUMBER);

    /**
     * Operation instance for operation UPDATEPLANSTATUS.
     */
    public static final MALSubmitOperation UPDATEPLANSTATUS_OP = new MALSubmitOperation(SERVICE_KEY, 
            UPDATEPLANSTATUS_OP_NUMBER, 
            new Identifier("updatePlanStatus"), 
            new UShort(1), 
            new OperationField[] {
                new OperationField("planRef", false, ObjectRef.OBJECTREF_SHORT_FORM, ""),
                new OperationField("status", false, PlanStatusEnum.SHORT_FORM, ""),
                new OperationField("isAlternate", false, Attribute.BOOLEAN_SHORT_FORM, "")}, 
            "The updatePlanStatus operation may be used to modify the status of a previously submitted Plan.  Directly modifying the status field of a Plan may be used by a third party function to autonomously terminate (or activate) a Plan, but the operation also allows the isAlternate flag to be set or cleared. It is implementation dependent what action the service provider takes in response to a change of Plan status.  The service provider may not permit certain state changes (for example to modify the status of a TERMINATED plan, which is inconsistent with the plan status model), in which case an UPDATE_FAILED error shall be returned. A set of Plans with a common precursor may be submitted to a plan execution function to cater for alternative or contingency scenarios.  All but one of these Plans should have the isAlternate flag set, to inform the plan execution function (and the mission operations team) which is the nominal Plan.  It is implementation dependent whether a plan execution control service provider will allow a Plan to be activated with the isAlternate flag set, but for operational safety reasons this may be blocked.  In a contingency scenario, the updatePlanStatus operation can be used to set the flag on the nominal Plan, and reset the flag on the required contingency Plan, making it operational.");

    /**
     * Operation number literal for operation INSERTACTIVITY.
     */
    public static final int _INSERTACTIVITY_OP_NUMBER = 2;

    /**
     * Operation number instance for operation INSERTACTIVITY.
     */
    private static final UShort INSERTACTIVITY_OP_NUMBER = new UShort(_INSERTACTIVITY_OP_NUMBER);

    /**
     * Operation instance for operation INSERTACTIVITY.
     */
    public static final MALRequestOperation INSERTACTIVITY_OP = new MALRequestOperation(SERVICE_KEY, 
            INSERTACTIVITY_OP_NUMBER, 
            new Identifier("insertActivity"), 
            new UShort(2), 
            new OperationField[] {
                new OperationField("activityDetails", false, InsertedActivityDetails.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("activityRef", false, ObjectRef.OBJECTREF_SHORT_FORM, "")}, 
            "The insertActivity operation sends an InsertedActivityDetails structure (an ActivityDetails structure with Plan reference and start/end triggers) to the provider, which then creates a corresponding ActivityInstance object in the referenced Plan and returns its identity to the consumer.  It is up to the planning system, how to manage concurrent access to the plan. Insertion may fail if the Plan is already in the TERMINATED state, in which case an INSERT_FAILED error shall be returned.");

    /**
     * Operation number literal for operation INSERTEVENT.
     */
    public static final int _INSERTEVENT_OP_NUMBER = 3;

    /**
     * Operation number instance for operation INSERTEVENT.
     */
    private static final UShort INSERTEVENT_OP_NUMBER = new UShort(_INSERTEVENT_OP_NUMBER);

    /**
     * Operation instance for operation INSERTEVENT.
     */
    public static final MALRequestOperation INSERTEVENT_OP = new MALRequestOperation(SERVICE_KEY, 
            INSERTEVENT_OP_NUMBER, 
            new Identifier("insertEvent"), 
            new UShort(2), 
            new OperationField[] {
                new OperationField("eventDetails", false, InsertedEventDetails.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("eventRef", false, ObjectRef.OBJECTREF_SHORT_FORM, "")}, 
            "The insertEvent operation sends an InsertedEventDetails structure, which includes a Plan reference, to the provider, which then creates a corresponding EventInstance object in the referenced Plan and returns its identity to the consumer.  It is up to the planning system, how to manage concurrent access to the plan. Insertion may fail if the Plan is already in the TERMINATED state, in which case an INSERT_FAILED error shall be returned.");

    /**
     * Operation number literal for operation DELETEACTIVITY.
     */
    public static final int _DELETEACTIVITY_OP_NUMBER = 4;

    /**
     * Operation number instance for operation DELETEACTIVITY.
     */
    private static final UShort DELETEACTIVITY_OP_NUMBER = new UShort(_DELETEACTIVITY_OP_NUMBER);

    /**
     * Operation instance for operation DELETEACTIVITY.
     */
    public static final MALSubmitOperation DELETEACTIVITY_OP = new MALSubmitOperation(SERVICE_KEY, 
            DELETEACTIVITY_OP_NUMBER, 
            new Identifier("deleteActivity"), 
            new UShort(2), 
            new OperationField[] {
                new OperationField("planRef", false, ObjectRef.OBJECTREF_SHORT_FORM, ""),
                new OperationField("activityRef", false, ObjectRef.OBJECTREF_SHORT_FORM, "")}, 
            "The deleteActivity operation requests that a specified ActivityInstance within a Plan is deleted by the service provider.  In practice, the activity is not removed, but transitioned to the TERMINATED state with deletion indicated in the statusInfo field.  The ActivityInstance is not subsequently executed by the service provider, but it is implementation dependent what action is taken by the service provider if the ActivityInstance is in the EXECUTING state.  It is up to the planning system, how to manage concurrent access to the plan. Deletion may fail if the referenced Plan or ActivityInstance is already in the TERMINATED state, in which case the DELETE_FAILED error shall be returned.");

    /**
     * Operation number literal for operation DELETEEVENT.
     */
    public static final int _DELETEEVENT_OP_NUMBER = 5;

    /**
     * Operation number instance for operation DELETEEVENT.
     */
    private static final UShort DELETEEVENT_OP_NUMBER = new UShort(_DELETEEVENT_OP_NUMBER);

    /**
     * Operation instance for operation DELETEEVENT.
     */
    public static final MALSubmitOperation DELETEEVENT_OP = new MALSubmitOperation(SERVICE_KEY, 
            DELETEEVENT_OP_NUMBER, 
            new Identifier("deleteEvent"), 
            new UShort(2), 
            new OperationField[] {
                new OperationField("planRef", false, ObjectRef.OBJECTREF_SHORT_FORM, ""),
                new OperationField("eventRef", false, ObjectRef.OBJECTREF_SHORT_FORM, "")}, 
            "The deleteEvent operation requests that a specified EventInstance within a Plan is deleted by the service provider.  In practice, the event is not removed, but transitioned to the TERMINATED state with deletion indicated in the statusInfo field.  The EventInstance is not subsequently triggered by the service provider.  It is up to the planning system, how to manage concurrent access to the plan. Deletion may fail if the referenced Plan or EventInstance is already in the TERMINATED state, in which case the DELETE_FAILED error shall be returned.");

    /**
     * Operation number literal for operation UPDATEACTIVITY.
     */
    public static final int _UPDATEACTIVITY_OP_NUMBER = 6;

    /**
     * Operation number instance for operation UPDATEACTIVITY.
     */
    private static final UShort UPDATEACTIVITY_OP_NUMBER = new UShort(_UPDATEACTIVITY_OP_NUMBER);

    /**
     * Operation instance for operation UPDATEACTIVITY.
     */
    public static final MALSubmitOperation UPDATEACTIVITY_OP = new MALSubmitOperation(SERVICE_KEY, 
            UPDATEACTIVITY_OP_NUMBER, 
            new Identifier("updateActivity"), 
            new UShort(3), 
            new OperationField[] {
                new OperationField("planRef", false, ObjectRef.OBJECTREF_SHORT_FORM, ""),
                new OperationField("activityUpdate", false, ActivityUpdate.SHORT_FORM, "")}, 
            "The updateActivity operation may be used to modify an ActivityInstance in a Plan that has already been submitted to the service provider.  The consumer submits an ActivityUpdate structure which is applied by the service provider to the referenced ActivityInstance.  It is up to the planning system, how to manage concurrent access to the plan. Update may fail if the referenced Plan or ActivityInstance is already in the TERMINATED state, in which case the UPDATE_FAILED error shall be returned.");

    /**
     * Operation number literal for operation UPDATEEVENT.
     */
    public static final int _UPDATEEVENT_OP_NUMBER = 7;

    /**
     * Operation number instance for operation UPDATEEVENT.
     */
    private static final UShort UPDATEEVENT_OP_NUMBER = new UShort(_UPDATEEVENT_OP_NUMBER);

    /**
     * Operation instance for operation UPDATEEVENT.
     */
    public static final MALSubmitOperation UPDATEEVENT_OP = new MALSubmitOperation(SERVICE_KEY, 
            UPDATEEVENT_OP_NUMBER, 
            new Identifier("updateEvent"), 
            new UShort(3), 
            new OperationField[] {
                new OperationField("planRef", false, ObjectRef.OBJECTREF_SHORT_FORM, ""),
                new OperationField("eventUpdate", false, EventUpdate.SHORT_FORM, "")}, 
            "The updateEvent operation may be used to modify an EventInstance in a Plan that has already been submitted to the service provider.  The consumer submits an EventUpdate structure which is applied by the service provider to the referenced EventInstance.  It is up to the planning system, how to manage concurrent access to the plan. Update may fail if the referenced Plan or EventInstance is already in the TERMINATED state, in which case the UPDATE_FAILED error shall be returned.");

    /**
     * Operation number literal for operation UPDATERESOURCEVALUE.
     */
    public static final int _UPDATERESOURCEVALUE_OP_NUMBER = 8;

    /**
     * Operation number instance for operation UPDATERESOURCEVALUE.
     */
    private static final UShort UPDATERESOURCEVALUE_OP_NUMBER = new UShort(_UPDATERESOURCEVALUE_OP_NUMBER);

    /**
     * Operation instance for operation UPDATERESOURCEVALUE.
     */
    public static final MALSubmitOperation UPDATERESOURCEVALUE_OP = new MALSubmitOperation(SERVICE_KEY, 
            UPDATERESOURCEVALUE_OP_NUMBER, 
            new Identifier("updateResourceValue"), 
            new UShort(4), 
            new OperationField[] {
                new OperationField("planRef", false, ObjectRef.OBJECTREF_SHORT_FORM, ""),
                new OperationField("resourceUpdate", false, ResourceUpdate.SHORT_FORM, "")}, 
            "The updateResourceValue operation may be used to modify the value of a Resource at the specified point in time, in a Plan that has already been submitted to the service provider.  The consumer submits a ResourceUpdate structure which is applied by the service provider to the referenced Resource.  It is up to the planning system, how to manage concurrent access to the plan. Update may fail if the referenced Plan is already in the TERMINATED state, in which case the UPDATE_FAILED error shall be returned.");

    /**
     * Operation number literal for operation UPDATERESOURCEPROFILE.
     */
    public static final int _UPDATERESOURCEPROFILE_OP_NUMBER = 9;

    /**
     * Operation number instance for operation UPDATERESOURCEPROFILE.
     */
    private static final UShort UPDATERESOURCEPROFILE_OP_NUMBER = new UShort(_UPDATERESOURCEPROFILE_OP_NUMBER);

    /**
     * Operation instance for operation UPDATERESOURCEPROFILE.
     */
    public static final MALSubmitOperation UPDATERESOURCEPROFILE_OP = new MALSubmitOperation(SERVICE_KEY, 
            UPDATERESOURCEPROFILE_OP_NUMBER, 
            new Identifier("updateResourceProfile"), 
            new UShort(5), 
            new OperationField[] {
                new OperationField("planRef", false, ObjectRef.OBJECTREF_SHORT_FORM, ""),
                new OperationField("resourceProfile", false, ResourceProfile.SHORT_FORM, "")}, 
            "The updateResourceProfile operation may be used to modify the value of a Resource over a period of time, in a Plan that has already been submitted to the service provider.  The consumer submits a ResourceProfile structure which is applied by the service provider to the referenced Resource.  It is up to the planning system, how to manage concurrent access to the plan. Update may fail if the referenced Plan is already in the TERMINATED state, in which case the UPDATE_FAILED error shall be returned.");

    /**
     * Operation number literal for operation APPLYTIMESHIFT.
     */
    public static final int _APPLYTIMESHIFT_OP_NUMBER = 10;

    /**
     * Operation number instance for operation APPLYTIMESHIFT.
     */
    private static final UShort APPLYTIMESHIFT_OP_NUMBER = new UShort(_APPLYTIMESHIFT_OP_NUMBER);

    /**
     * Operation instance for operation APPLYTIMESHIFT.
     */
    public static final MALSubmitOperation APPLYTIMESHIFT_OP = new MALSubmitOperation(SERVICE_KEY, 
            APPLYTIMESHIFT_OP_NUMBER, 
            new Identifier("applyTimeShift"), 
            new UShort(6), 
            new OperationField[] {
                new OperationField("planRef", false, ObjectRef.OBJECTREF_SHORT_FORM, ""),
                new OperationField("subPlans", true, IdentifierList.SHORT_FORM, ""),
                new OperationField("timePeriod", false, TimeWindow.SHORT_FORM, ""),
                new OperationField("offset", false, Attribute.DURATION_SHORT_FORM, "")}, 
            "The applyTimeShift operation may be used to request a shift in the timing by a fixed offset of the ActivityInstances, EventInstances, and ResourceProfiles contained within a Plan that has previously been submitted to a plan execution function.  The operation may also be restricted to one or more SubPlans within the referenced Plan and/or to a specified time period within the Plan.  The service provider applies the time shift to the timing of ActivityInstances, EventInstances, and ResourceProfiles contained within the Plan or SubPlan(s). The time shift may fail if the referenced Plan is already in the TERMINATED state, in which case the UPDATE_FAILED error shall be returned. The operation is designed to support backward compatibility with simple time-based on-board schedules, and may not be appropriate for use with plans that include event or position-based triggers and resource profiles.  What is shifted within the Plan is implementation dependent, but shall include time-based start and end triggers on ActivityInstances.  EventInstances may also be shifted, but it is noted that some EventInstances correspond to predicted orbital events that cannot meaningfully be shifted.  Similarly, where supported, resource profiles may reflect the ActivityInstances contained within the Plan and if those are shifted, the corresponding changes in Resource value should also be shifted. NOTE – ActivityInstances have duration which means they may overlap the start or end of the specified TimeWindow for the applicability of the time shift.  It is implementation dependent how this is managed, but a reasonable assumption is that the start time of the ActivityInstances must be within the specified TimeWindow.  Given the potential to introduce inconsistencies into a Plan, it must be assumed that users of this service operation understand both its operational implications and its specific implementation.");

    /**
     * Area elements.
     */
    public static final Element[] PLANEDIT_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{UPDATEPLANSTATUS_OP,
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
                    case 3:
                        return new UpdateFailedException(extraInfo);
                }
                break;
            case 2:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
                    case 10:
                        return new UnsupportedException(extraInfo);
                    case 5:
                        return new InsertFailedException(extraInfo);
                }
                break;
            case 3:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
                    case 5:
                        return new InsertFailedException(extraInfo);
                }
                break;
            case 4:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
                    case 6:
                        return new DeleteFailedException(extraInfo);
                }
                break;
            case 5:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
                    case 6:
                        return new DeleteFailedException(extraInfo);
                }
                break;
            case 6:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
                    case 3:
                        return new UpdateFailedException(extraInfo);
                }
                break;
            case 7:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
                    case 3:
                        return new UpdateFailedException(extraInfo);
                }
                break;
            case 8:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
                    case 10:
                        return new UnsupportedException(extraInfo);
                    case 3:
                        return new UpdateFailedException(extraInfo);
                }
                break;
            case 9:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
                    case 10:
                        return new UnsupportedException(extraInfo);
                    case 3:
                        return new UpdateFailedException(extraInfo);
                }
                break;
            case 10:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
                    case 3:
                        return new UpdateFailedException(extraInfo);
                }
                break;
        }
        MOErrorException areaError = MPSHelper.generateMOError(errorNumber, extraInfo);
        return (areaError != null) ? areaError : MALHelper.generateMOError(errorNumber, extraInfo);
    }

}
