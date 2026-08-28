package org.ccsds.moims.mo.mps.planedit.consumer;

/**
 * Consumer adapter for PlanEdit service.
 */
public abstract class PlanEditAdapter extends org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter {

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation updatePlanStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updatePlanStatusAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation updatePlanStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updatePlanStatusErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation insertActivity.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param activityRef The activityRef field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void insertActivityResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityInstance> activityRef,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation insertActivity.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void insertActivityErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation insertEvent.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param eventRef The eventRef field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void insertEventResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventInstance> eventRef,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation insertEvent.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void insertEventErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation deleteActivity.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deleteActivityAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation deleteActivity.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deleteActivityErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation deleteEvent.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deleteEventAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation deleteEvent.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deleteEventErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation updateActivity.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateActivityAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation updateActivity.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateActivityErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation updateEvent.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateEventAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation updateEvent.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateEventErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation updateResourceValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateResourceValueAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation updateResourceValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateResourceValueErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation updateResourceProfile.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateResourceProfileAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation updateResourceProfile.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateResourceProfileErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation applyTimeShift.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void applyTimeShiftAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation applyTimeShift.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void applyTimeShiftErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._UPDATEPLANSTATUS_OP_NUMBER:
            updatePlanStatusAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._DELETEACTIVITY_OP_NUMBER:
            deleteActivityAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._DELETEEVENT_OP_NUMBER:
            deleteEventAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._UPDATEACTIVITY_OP_NUMBER:
            updateActivityAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._UPDATEEVENT_OP_NUMBER:
            updateEventAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._UPDATERESOURCEVALUE_OP_NUMBER:
            updateResourceValueAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._UPDATERESOURCEPROFILE_OP_NUMBER:
            updateResourceProfileAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._APPLYTIMESHIFT_OP_NUMBER:
            applyTimeShiftAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void submitErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._UPDATEPLANSTATUS_OP_NUMBER:
            updatePlanStatusErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._DELETEACTIVITY_OP_NUMBER:
            deleteActivityErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._DELETEEVENT_OP_NUMBER:
            deleteEventErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._UPDATEACTIVITY_OP_NUMBER:
            updateActivityErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._UPDATEEVENT_OP_NUMBER:
            updateEventErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._UPDATERESOURCEVALUE_OP_NUMBER:
            updateResourceValueErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._UPDATERESOURCEPROFILE_OP_NUMBER:
            updateResourceProfileErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._APPLYTIMESHIFT_OP_NUMBER:
            applyTimeShiftErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._INSERTACTIVITY_OP_NUMBER:
            insertActivityResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityInstance>) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityInstance>()), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._INSERTEVENT_OP_NUMBER:
            insertEventResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventInstance>) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventInstance>()), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._INSERTACTIVITY_OP_NUMBER:
            insertActivityErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._INSERTEVENT_OP_NUMBER:
            insertEventErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

}
