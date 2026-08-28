package org.ccsds.moims.mo.mps.planedit.consumer;

/**
 * Consumer stub for PlanEdit service.
 */
public class PlanEditStub {

    /**
     * The consumer field.
     */
    private final org.ccsds.moims.mo.mal.consumer.MALConsumer consumer;

    /**
     * Wraps a MALconsumer connection with service specific methods that map from
     * the high level service API to the generic MAL API.
     * 
     * @param consumer consumer The MALConsumer to use in this stub.
     */
    public PlanEditStub(org.ccsds.moims.mo.mal.consumer.MALConsumer consumer) {
        this.consumer = consumer;
    }

    /**
     * Returns the internal MAL consumer object used for sending of messages from
     * this interface.
     * 
     * @return The MAL consumer object.
     */
    public org.ccsds.moims.mo.mal.consumer.MALConsumer getConsumer() {
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
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void updatePlanStatus(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mps.structures.PlanStatusEnum status,
            Boolean isAlternate) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.UPDATEPLANSTATUS_OP, planRef, status, (isAlternate == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(isAlternate));
    }

    /**
     * Asynchronous version of method updatePlanStatus.
     * 
     * @param planRef The planRef field.
     * @param status The status field.
     * @param isAlternate The isAlternate field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncUpdatePlanStatus(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mps.structures.PlanStatusEnum status,
            Boolean isAlternate,
            org.ccsds.moims.mo.mps.planedit.consumer.PlanEditAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.UPDATEPLANSTATUS_OP, adapter, planRef, status, (isAlternate == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(isAlternate));
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueUpdatePlanStatus(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planedit.consumer.PlanEditAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.UPDATEPLANSTATUS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
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
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityInstance> insertActivity(org.ccsds.moims.mo.mps.structures.InsertedActivityDetails activityDetails) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.INSERTACTIVITY_OP, activityDetails);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityInstance>());
        return (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityInstance>) body0;
    }

    /**
     * Asynchronous version of method insertActivity.
     * 
     * @param activityDetails The activityDetails field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncInsertActivity(org.ccsds.moims.mo.mps.structures.InsertedActivityDetails activityDetails,
            org.ccsds.moims.mo.mps.planedit.consumer.PlanEditAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.INSERTACTIVITY_OP, adapter, activityDetails);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueInsertActivity(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planedit.consumer.PlanEditAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.INSERTACTIVITY_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
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
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventInstance> insertEvent(org.ccsds.moims.mo.mps.structures.InsertedEventDetails eventDetails) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.INSERTEVENT_OP, eventDetails);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventInstance>());
        return (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventInstance>) body0;
    }

    /**
     * Asynchronous version of method insertEvent.
     * 
     * @param eventDetails The eventDetails field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncInsertEvent(org.ccsds.moims.mo.mps.structures.InsertedEventDetails eventDetails,
            org.ccsds.moims.mo.mps.planedit.consumer.PlanEditAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.INSERTEVENT_OP, adapter, eventDetails);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueInsertEvent(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planedit.consumer.PlanEditAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.INSERTEVENT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
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
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void deleteActivity(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityInstance> activityRef) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.DELETEACTIVITY_OP, planRef, activityRef);
    }

    /**
     * Asynchronous version of method deleteActivity.
     * 
     * @param planRef The planRef field.
     * @param activityRef The activityRef field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncDeleteActivity(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityInstance> activityRef,
            org.ccsds.moims.mo.mps.planedit.consumer.PlanEditAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.DELETEACTIVITY_OP, adapter, planRef, activityRef);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueDeleteActivity(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planedit.consumer.PlanEditAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.DELETEACTIVITY_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
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
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void deleteEvent(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventInstance> eventRef) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.DELETEEVENT_OP, planRef, eventRef);
    }

    /**
     * Asynchronous version of method deleteEvent.
     * 
     * @param planRef The planRef field.
     * @param eventRef The eventRef field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncDeleteEvent(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventInstance> eventRef,
            org.ccsds.moims.mo.mps.planedit.consumer.PlanEditAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.DELETEEVENT_OP, adapter, planRef, eventRef);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueDeleteEvent(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planedit.consumer.PlanEditAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.DELETEEVENT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
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
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void updateActivity(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mps.structures.ActivityUpdate activityUpdate) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.UPDATEACTIVITY_OP, planRef, activityUpdate);
    }

    /**
     * Asynchronous version of method updateActivity.
     * 
     * @param planRef The planRef field.
     * @param activityUpdate The activityUpdate field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncUpdateActivity(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mps.structures.ActivityUpdate activityUpdate,
            org.ccsds.moims.mo.mps.planedit.consumer.PlanEditAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.UPDATEACTIVITY_OP, adapter, planRef, activityUpdate);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueUpdateActivity(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planedit.consumer.PlanEditAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.UPDATEACTIVITY_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
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
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void updateEvent(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mps.structures.EventUpdate eventUpdate) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.UPDATEEVENT_OP, planRef, eventUpdate);
    }

    /**
     * Asynchronous version of method updateEvent.
     * 
     * @param planRef The planRef field.
     * @param eventUpdate The eventUpdate field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncUpdateEvent(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mps.structures.EventUpdate eventUpdate,
            org.ccsds.moims.mo.mps.planedit.consumer.PlanEditAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.UPDATEEVENT_OP, adapter, planRef, eventUpdate);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueUpdateEvent(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planedit.consumer.PlanEditAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.UPDATEEVENT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
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
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void updateResourceValue(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mps.structures.ResourceUpdate resourceUpdate) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.UPDATERESOURCEVALUE_OP, planRef, resourceUpdate);
    }

    /**
     * Asynchronous version of method updateResourceValue.
     * 
     * @param planRef The planRef field.
     * @param resourceUpdate The resourceUpdate field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncUpdateResourceValue(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mps.structures.ResourceUpdate resourceUpdate,
            org.ccsds.moims.mo.mps.planedit.consumer.PlanEditAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.UPDATERESOURCEVALUE_OP, adapter, planRef, resourceUpdate);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueUpdateResourceValue(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planedit.consumer.PlanEditAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.UPDATERESOURCEVALUE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
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
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void updateResourceProfile(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mps.structures.ResourceProfile resourceProfile) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.UPDATERESOURCEPROFILE_OP, planRef, resourceProfile);
    }

    /**
     * Asynchronous version of method updateResourceProfile.
     * 
     * @param planRef The planRef field.
     * @param resourceProfile The resourceProfile field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncUpdateResourceProfile(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mps.structures.ResourceProfile resourceProfile,
            org.ccsds.moims.mo.mps.planedit.consumer.PlanEditAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.UPDATERESOURCEPROFILE_OP, adapter, planRef, resourceProfile);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueUpdateResourceProfile(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planedit.consumer.PlanEditAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.UPDATERESOURCEPROFILE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
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
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void applyTimeShift(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mal.structures.IdentifierList subPlans,
            org.ccsds.moims.mo.mps.structures.TimeWindow timePeriod,
            org.ccsds.moims.mo.mal.structures.Duration offset) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.APPLYTIMESHIFT_OP, planRef, subPlans, timePeriod, offset);
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
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncApplyTimeShift(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mal.structures.IdentifierList subPlans,
            org.ccsds.moims.mo.mps.structures.TimeWindow timePeriod,
            org.ccsds.moims.mo.mal.structures.Duration offset,
            org.ccsds.moims.mo.mps.planedit.consumer.PlanEditAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.APPLYTIMESHIFT_OP, adapter, planRef, subPlans, timePeriod, offset);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueApplyTimeShift(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planedit.consumer.PlanEditAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo.APPLYTIMESHIFT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

}
