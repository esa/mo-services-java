package org.ccsds.moims.mo.mps.planedit.consumer;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mps.DeleteFailedException;
import org.ccsds.moims.mo.mps.InsertFailedException;
import org.ccsds.moims.mo.mps.InvalidException;
import org.ccsds.moims.mo.mps.UnsupportedException;
import org.ccsds.moims.mo.mps.UpdateFailedException;
import org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo;
import org.ccsds.moims.mo.mps.structures.ActivityInstance;
import org.ccsds.moims.mo.mps.structures.ActivityUpdate;
import org.ccsds.moims.mo.mps.structures.EventInstance;
import org.ccsds.moims.mo.mps.structures.EventUpdate;
import org.ccsds.moims.mo.mps.structures.InsertedActivityDetails;
import org.ccsds.moims.mo.mps.structures.InsertedEventDetails;
import org.ccsds.moims.mo.mps.structures.Plan;
import org.ccsds.moims.mo.mps.structures.PlanStatusEnum;
import org.ccsds.moims.mo.mps.structures.ResourceProfile;
import org.ccsds.moims.mo.mps.structures.ResourceUpdate;
import org.ccsds.moims.mo.mps.structures.TimeWindow;

/**
 * Consumer stub for PlanEdit service.
 */
public class PlanEditStub {

    /**
     * The consumer field.
     */
    private final MALConsumer consumer;

    /**
     * Wraps a MALconsumer connection with service specific methods that map from
     * the high level service API to the generic MAL API.
     * 
     * @param consumer consumer The MALConsumer to use in this stub.
     */
    public PlanEditStub(MALConsumer consumer) {
        this.consumer = consumer;
    }

    /**
     * Returns the internal MAL consumer object used for sending of messages from
     * this interface.
     * 
     * @return The MAL consumer object.
     */
    public MALConsumer getConsumer() {
        return consumer;
    }

    /**
     * The updatePlanStatus operation may be used to modify the status of a previously
     * submitted Plan.  Directly modifying the status field of a Plan may be used
     * by a third party function to autonomously terminate (or activate) a Plan,
     * but the operation also allows the isAlternate flag to be set or cleared.
     * It is implementation dependent what action the service provider takes in
     * response to a change of Plan status.  The service provider may not permit
     * certain state changes (for example to modify the status of a TERMINATED
     * plan, which is inconsistent with the plan status model), in which case
     * an UPDATE_FAILED error shall be returned. A set of Plans with a common
     * precursor may be submitted to a plan execution function to cater for alternative
     * or contingency scenarios.  All but one of these Plans should have the isAlternate
     * flag set, to inform the plan execution function (and the mission operations
     * team) which is the nominal Plan.  It is implementation dependent whether
     * a plan execution control service provider will allow a Plan to be activated
     * with the isAlternate flag set, but for operational safety reasons this
     * may be blocked.  In a contingency scenario, the updatePlanStatus operation
     * can be used to set the flag on the nominal Plan, and reset the flag on
     * the required contingency Plan, making it operational.
     * 
     * @param planRef The planRef field.
     * @param status The status field.
     * @param isAlternate The isAlternate field.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws UpdateFailedException The update operation (to Request, PlanStatus, Activity, Event or Resource) failed to update the referenced object.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void updatePlanStatus(ObjectRef<Plan> planRef,
            PlanStatusEnum status,
            Boolean isAlternate) throws InvalidException, UpdateFailedException, MALStandardError, MALException {
        try {
            consumer.submit(PlanEditServiceInfo.UPDATEPLANSTATUS_OP, planRef, status, (isAlternate == null) ? null : new Union(isAlternate));
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof UpdateFailedException) {
                throw (UpdateFailedException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method updatePlanStatus.
     * 
     * @param planRef The planRef field.
     * @param status The status field.
     * @param isAlternate The isAlternate field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncUpdatePlanStatus(ObjectRef<Plan> planRef,
            PlanStatusEnum status,
            Boolean isAlternate,
            PlanEditAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(PlanEditServiceInfo.UPDATEPLANSTATUS_OP, adapter, planRef, status, (isAlternate == null) ? null : new Union(isAlternate));
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueUpdatePlanStatus(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanEditAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanEditServiceInfo.UPDATEPLANSTATUS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The insertActivity operation sends an InsertedActivityDetails structure
     * (an ActivityDetails structure with Plan reference and start/end triggers)
     * to the provider, which then creates a corresponding ActivityInstance object
     * in the referenced Plan and returns its identity to the consumer.  It is
     * up to the planning system, how to manage concurrent access to the plan.
     * Insertion may fail if the Plan is already in the TERMINATED state, in which
     * case an INSERT_FAILED error shall be returned.
     * 
     * @param activityDetails The activityDetails field.
     * @return The return value of the interaction
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws UnsupportedException An optional data structure used in the message is not supported by the service provider.
     * @throws InsertFailedException The insertActivity or insertEvent operation failed to insert the requested object.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public ObjectRef<ActivityInstance> insertActivity(InsertedActivityDetails activityDetails) throws InvalidException, UnsupportedException, InsertFailedException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(PlanEditServiceInfo.INSERTACTIVITY_OP, activityDetails);
            Object body0 = (Object) body.getBodyElement(0, new ObjectRef<ActivityInstance>());
            return (ObjectRef<ActivityInstance>) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof UnsupportedException) {
                throw (UnsupportedException) error;
            }
            if (error instanceof InsertFailedException) {
                throw (InsertFailedException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method insertActivity.
     * 
     * @param activityDetails The activityDetails field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncInsertActivity(InsertedActivityDetails activityDetails,
            PlanEditAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(PlanEditServiceInfo.INSERTACTIVITY_OP, adapter, activityDetails);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueInsertActivity(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanEditAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanEditServiceInfo.INSERTACTIVITY_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The insertEvent operation sends an InsertedEventDetails structure, which
     * includes a Plan reference, to the provider, which then creates a corresponding
     * EventInstance object in the referenced Plan and returns its identity to
     * the consumer.  It is up to the planning system, how to manage concurrent
     * access to the plan. Insertion may fail if the Plan is already in the TERMINATED
     * state, in which case an INSERT_FAILED error shall be returned.
     * 
     * @param eventDetails The eventDetails field.
     * @return The return value of the interaction
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws InsertFailedException The insertActivity or insertEvent operation failed to insert the requested object.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public ObjectRef<EventInstance> insertEvent(InsertedEventDetails eventDetails) throws InvalidException, InsertFailedException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(PlanEditServiceInfo.INSERTEVENT_OP, eventDetails);
            Object body0 = (Object) body.getBodyElement(0, new ObjectRef<EventInstance>());
            return (ObjectRef<EventInstance>) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof InsertFailedException) {
                throw (InsertFailedException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method insertEvent.
     * 
     * @param eventDetails The eventDetails field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncInsertEvent(InsertedEventDetails eventDetails,
            PlanEditAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(PlanEditServiceInfo.INSERTEVENT_OP, adapter, eventDetails);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueInsertEvent(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanEditAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanEditServiceInfo.INSERTEVENT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The deleteActivity operation requests that a specified ActivityInstance
     * within a Plan is deleted by the service provider.  In practice, the activity
     * is not removed, but transitioned to the TERMINATED state with deletion
     * indicated in the statusInfo field.  The ActivityInstance is not subsequently
     * executed by the service provider, but it is implementation dependent what
     * action is taken by the service provider if the ActivityInstance is in the
     * EXECUTING state.  It is up to the planning system, how to manage concurrent
     * access to the plan. Deletion may fail if the referenced Plan or ActivityInstance
     * is already in the TERMINATED state, in which case the DELETE_FAILED error
     * shall be returned.
     * 
     * @param planRef The planRef field.
     * @param activityRef The activityRef field.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws DeleteFailedException The deleteActivity or deleteEvent operation failed to delete the requested object.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void deleteActivity(ObjectRef<Plan> planRef,
            ObjectRef<ActivityInstance> activityRef) throws InvalidException, DeleteFailedException, MALStandardError, MALException {
        try {
            consumer.submit(PlanEditServiceInfo.DELETEACTIVITY_OP, planRef, activityRef);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof DeleteFailedException) {
                throw (DeleteFailedException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method deleteActivity.
     * 
     * @param planRef The planRef field.
     * @param activityRef The activityRef field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncDeleteActivity(ObjectRef<Plan> planRef,
            ObjectRef<ActivityInstance> activityRef,
            PlanEditAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(PlanEditServiceInfo.DELETEACTIVITY_OP, adapter, planRef, activityRef);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueDeleteActivity(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanEditAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanEditServiceInfo.DELETEACTIVITY_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The deleteEvent operation requests that a specified EventInstance within
     * a Plan is deleted by the service provider.  In practice, the event is not
     * removed, but transitioned to the TERMINATED state with deletion indicated
     * in the statusInfo field.  The EventInstance is not subsequently triggered
     * by the service provider.  It is up to the planning system, how to manage
     * concurrent access to the plan. Deletion may fail if the referenced Plan
     * or EventInstance is already in the TERMINATED state, in which case the
     * DELETE_FAILED error shall be returned.
     * 
     * @param planRef The planRef field.
     * @param eventRef The eventRef field.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws DeleteFailedException The deleteActivity or deleteEvent operation failed to delete the requested object.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void deleteEvent(ObjectRef<Plan> planRef,
            ObjectRef<EventInstance> eventRef) throws InvalidException, DeleteFailedException, MALStandardError, MALException {
        try {
            consumer.submit(PlanEditServiceInfo.DELETEEVENT_OP, planRef, eventRef);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof DeleteFailedException) {
                throw (DeleteFailedException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method deleteEvent.
     * 
     * @param planRef The planRef field.
     * @param eventRef The eventRef field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncDeleteEvent(ObjectRef<Plan> planRef,
            ObjectRef<EventInstance> eventRef,
            PlanEditAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(PlanEditServiceInfo.DELETEEVENT_OP, adapter, planRef, eventRef);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueDeleteEvent(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanEditAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanEditServiceInfo.DELETEEVENT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The updateActivity operation may be used to modify an ActivityInstance
     * in a Plan that has already been submitted to the service provider.  The
     * consumer submits an ActivityUpdate structure which is applied by the service
     * provider to the referenced ActivityInstance.  It is up to the planning
     * system, how to manage concurrent access to the plan. Update may fail if
     * the referenced Plan or ActivityInstance is already in the TERMINATED state,
     * in which case the UPDATE_FAILED error shall be returned.
     * 
     * @param planRef The planRef field.
     * @param activityUpdate The activityUpdate field.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws UpdateFailedException The update operation (to Request, PlanStatus, Activity, Event or Resource) failed to update the referenced object.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void updateActivity(ObjectRef<Plan> planRef,
            ActivityUpdate activityUpdate) throws InvalidException, UpdateFailedException, MALStandardError, MALException {
        try {
            consumer.submit(PlanEditServiceInfo.UPDATEACTIVITY_OP, planRef, activityUpdate);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof UpdateFailedException) {
                throw (UpdateFailedException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method updateActivity.
     * 
     * @param planRef The planRef field.
     * @param activityUpdate The activityUpdate field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncUpdateActivity(ObjectRef<Plan> planRef,
            ActivityUpdate activityUpdate,
            PlanEditAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(PlanEditServiceInfo.UPDATEACTIVITY_OP, adapter, planRef, activityUpdate);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueUpdateActivity(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanEditAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanEditServiceInfo.UPDATEACTIVITY_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The updateEvent operation may be used to modify an EventInstance in a Plan
     * that has already been submitted to the service provider.  The consumer
     * submits an EventUpdate structure which is applied by the service provider
     * to the referenced EventInstance.  It is up to the planning system, how
     * to manage concurrent access to the plan. Update may fail if the referenced
     * Plan or EventInstance is already in the TERMINATED state, in which case
     * the UPDATE_FAILED error shall be returned.
     * 
     * @param planRef The planRef field.
     * @param eventUpdate The eventUpdate field.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws UpdateFailedException The update operation (to Request, PlanStatus, Activity, Event or Resource) failed to update the referenced object.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void updateEvent(ObjectRef<Plan> planRef,
            EventUpdate eventUpdate) throws InvalidException, UpdateFailedException, MALStandardError, MALException {
        try {
            consumer.submit(PlanEditServiceInfo.UPDATEEVENT_OP, planRef, eventUpdate);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof UpdateFailedException) {
                throw (UpdateFailedException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method updateEvent.
     * 
     * @param planRef The planRef field.
     * @param eventUpdate The eventUpdate field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncUpdateEvent(ObjectRef<Plan> planRef,
            EventUpdate eventUpdate,
            PlanEditAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(PlanEditServiceInfo.UPDATEEVENT_OP, adapter, planRef, eventUpdate);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueUpdateEvent(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanEditAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanEditServiceInfo.UPDATEEVENT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The updateResourceValue operation may be used to modify the value of a
     * Resource at the specified point in time, in a Plan that has already been
     * submitted to the service provider.  The consumer submits a ResourceUpdate
     * structure which is applied by the service provider to the referenced Resource.
     * It is up to the planning system, how to manage concurrent access to the
     * plan. Update may fail if the referenced Plan is already in the TERMINATED
     * state, in which case the UPDATE_FAILED error shall be returned.
     * 
     * @param planRef The planRef field.
     * @param resourceUpdate The resourceUpdate field.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws UnsupportedException An optional data structure used in the message is not supported by the service provider.
     * @throws UpdateFailedException The update operation (to Request, PlanStatus, Activity, Event or Resource) failed to update the referenced object.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void updateResourceValue(ObjectRef<Plan> planRef,
            ResourceUpdate resourceUpdate) throws InvalidException, UnsupportedException, UpdateFailedException, MALStandardError, MALException {
        try {
            consumer.submit(PlanEditServiceInfo.UPDATERESOURCEVALUE_OP, planRef, resourceUpdate);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof UnsupportedException) {
                throw (UnsupportedException) error;
            }
            if (error instanceof UpdateFailedException) {
                throw (UpdateFailedException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method updateResourceValue.
     * 
     * @param planRef The planRef field.
     * @param resourceUpdate The resourceUpdate field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncUpdateResourceValue(ObjectRef<Plan> planRef,
            ResourceUpdate resourceUpdate,
            PlanEditAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(PlanEditServiceInfo.UPDATERESOURCEVALUE_OP, adapter, planRef, resourceUpdate);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueUpdateResourceValue(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanEditAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanEditServiceInfo.UPDATERESOURCEVALUE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The updateResourceProfile operation may be used to modify the value of
     * a Resource over a period of time, in a Plan that has already been submitted
     * to the service provider.  The consumer submits a ResourceProfile structure
     * which is applied by the service provider to the referenced Resource.  It
     * is up to the planning system, how to manage concurrent access to the plan.
     * Update may fail if the referenced Plan is already in the TERMINATED state,
     * in which case the UPDATE_FAILED error shall be returned.
     * 
     * @param planRef The planRef field.
     * @param resourceProfile The resourceProfile field.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws UnsupportedException An optional data structure used in the message is not supported by the service provider.
     * @throws UpdateFailedException The update operation (to Request, PlanStatus, Activity, Event or Resource) failed to update the referenced object.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void updateResourceProfile(ObjectRef<Plan> planRef,
            ResourceProfile resourceProfile) throws InvalidException, UnsupportedException, UpdateFailedException, MALStandardError, MALException {
        try {
            consumer.submit(PlanEditServiceInfo.UPDATERESOURCEPROFILE_OP, planRef, resourceProfile);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof UnsupportedException) {
                throw (UnsupportedException) error;
            }
            if (error instanceof UpdateFailedException) {
                throw (UpdateFailedException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method updateResourceProfile.
     * 
     * @param planRef The planRef field.
     * @param resourceProfile The resourceProfile field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncUpdateResourceProfile(ObjectRef<Plan> planRef,
            ResourceProfile resourceProfile,
            PlanEditAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(PlanEditServiceInfo.UPDATERESOURCEPROFILE_OP, adapter, planRef, resourceProfile);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueUpdateResourceProfile(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanEditAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanEditServiceInfo.UPDATERESOURCEPROFILE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The applyTimeShift operation may be used to request a shift in the timing
     * by a fixed offset of the ActivityInstances, EventInstances, and ResourceProfiles
     * contained within a Plan that has previously been submitted to a plan execution
     * function.  The operation may also be restricted to one or more SubPlans
     * within the referenced Plan and/or to a specified time period within the
     * Plan.  The service provider applies the time shift to the timing of ActivityInstances,
     * EventInstances, and ResourceProfiles contained within the Plan or SubPlan(s).
     * The time shift may fail if the referenced Plan is already in the TERMINATED
     * state, in which case the UPDATE_FAILED error shall be returned. The operation
     * is designed to support backward compatibility with simple time-based on-board
     * schedules, and may not be appropriate for use with plans that include event
     * or position-based triggers and resource profiles.  What is shifted within
     * the Plan is implementation dependent, but shall include time-based start
     * and end triggers on ActivityInstances.  EventInstances may also be shifted,
     * but it is noted that some EventInstances correspond to predicted orbital
     * events that cannot meaningfully be shifted.  Similarly, where supported,
     * resource profiles may reflect the ActivityInstances contained within the
     * Plan and if those are shifted, the corresponding changes in Resource value
     * should also be shifted. NOTE – ActivityInstances have duration which means
     * they may overlap the start or end of the specified TimeWindow for the applicability
     * of the time shift.  It is implementation dependent how this is managed,
     * but a reasonable assumption is that the start time of the ActivityInstances
     * must be within the specified TimeWindow.  Given the potential to introduce
     * inconsistencies into a Plan, it must be assumed that users of this service
     * operation understand both its operational implications and its specific
     * implementation.
     * 
     * @param planRef The planRef field.
     * @param subPlans The subPlans field.
     * @param timePeriod The timePeriod field.
     * @param offset The offset field.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws UpdateFailedException The update operation (to Request, PlanStatus, Activity, Event or Resource) failed to update the referenced object.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void applyTimeShift(ObjectRef<Plan> planRef,
            IdentifierList subPlans,
            TimeWindow timePeriod,
            Duration offset) throws InvalidException, UpdateFailedException, MALStandardError, MALException {
        try {
            consumer.submit(PlanEditServiceInfo.APPLYTIMESHIFT_OP, planRef, subPlans, timePeriod, offset);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof UpdateFailedException) {
                throw (UpdateFailedException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method applyTimeShift.
     * 
     * @param planRef The planRef field.
     * @param subPlans The subPlans field.
     * @param timePeriod The timePeriod field.
     * @param offset The offset field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncApplyTimeShift(ObjectRef<Plan> planRef,
            IdentifierList subPlans,
            TimeWindow timePeriod,
            Duration offset,
            PlanEditAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(PlanEditServiceInfo.APPLYTIMESHIFT_OP, adapter, planRef, subPlans, timePeriod, offset);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueApplyTimeShift(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanEditAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanEditServiceInfo.APPLYTIMESHIFT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

}
