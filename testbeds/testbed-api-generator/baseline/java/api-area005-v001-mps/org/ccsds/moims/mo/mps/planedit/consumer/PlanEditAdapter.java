package org.ccsds.moims.mo.mps.planedit.consumer;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo;
import org.ccsds.moims.mo.mps.structures.ActivityInstance;
import org.ccsds.moims.mo.mps.structures.EventInstance;

/**
 * Consumer adapter for PlanEdit service.
 */
public abstract class PlanEditAdapter extends MALInteractionAdapter {

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation updatePlanStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updatePlanStatusAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation updatePlanStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updatePlanStatusErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation insertActivity.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param activityRef The activityRef field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void insertActivityResponseReceived(MALMessageHeader msgHeader,
            ObjectRef<ActivityInstance> activityRef,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation insertActivity.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void insertActivityErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation insertEvent.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param eventRef The eventRef field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void insertEventResponseReceived(MALMessageHeader msgHeader,
            ObjectRef<EventInstance> eventRef,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation insertEvent.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void insertEventErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation deleteActivity.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deleteActivityAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation deleteActivity.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deleteActivityErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation deleteEvent.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deleteEventAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation deleteEvent.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deleteEventErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation updateActivity.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateActivityAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation updateActivity.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateActivityErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation updateEvent.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateEventAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation updateEvent.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateEventErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation updateResourceValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateResourceValueAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation updateResourceValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateResourceValueErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation updateResourceProfile.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateResourceProfileAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation updateResourceProfile.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateResourceProfileErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation applyTimeShift.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void applyTimeShiftAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation applyTimeShift.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void applyTimeShiftErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanEditServiceInfo._UPDATEPLANSTATUS_OP_NUMBER:
            updatePlanStatusAckReceived(msgHeader, qosProperties);
            break;
          case PlanEditServiceInfo._DELETEACTIVITY_OP_NUMBER:
            deleteActivityAckReceived(msgHeader, qosProperties);
            break;
          case PlanEditServiceInfo._DELETEEVENT_OP_NUMBER:
            deleteEventAckReceived(msgHeader, qosProperties);
            break;
          case PlanEditServiceInfo._UPDATEACTIVITY_OP_NUMBER:
            updateActivityAckReceived(msgHeader, qosProperties);
            break;
          case PlanEditServiceInfo._UPDATEEVENT_OP_NUMBER:
            updateEventAckReceived(msgHeader, qosProperties);
            break;
          case PlanEditServiceInfo._UPDATERESOURCEVALUE_OP_NUMBER:
            updateResourceValueAckReceived(msgHeader, qosProperties);
            break;
          case PlanEditServiceInfo._UPDATERESOURCEPROFILE_OP_NUMBER:
            updateResourceProfileAckReceived(msgHeader, qosProperties);
            break;
          case PlanEditServiceInfo._APPLYTIMESHIFT_OP_NUMBER:
            applyTimeShiftAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void submitErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanEditServiceInfo._UPDATEPLANSTATUS_OP_NUMBER:
            updatePlanStatusErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanEditServiceInfo._DELETEACTIVITY_OP_NUMBER:
            deleteActivityErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanEditServiceInfo._DELETEEVENT_OP_NUMBER:
            deleteEventErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanEditServiceInfo._UPDATEACTIVITY_OP_NUMBER:
            updateActivityErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanEditServiceInfo._UPDATEEVENT_OP_NUMBER:
            updateEventErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanEditServiceInfo._UPDATERESOURCEVALUE_OP_NUMBER:
            updateResourceValueErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanEditServiceInfo._UPDATERESOURCEPROFILE_OP_NUMBER:
            updateResourceProfileErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanEditServiceInfo._APPLYTIMESHIFT_OP_NUMBER:
            applyTimeShiftErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestResponseReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanEditServiceInfo._INSERTACTIVITY_OP_NUMBER:
            insertActivityResponseReceived(msgHeader,
                (ObjectRef<ActivityInstance>) body.getBodyElement(0, new ObjectRef<ActivityInstance>()), qosProperties);
            break;
          case PlanEditServiceInfo._INSERTEVENT_OP_NUMBER:
            insertEventResponseReceived(msgHeader,
                (ObjectRef<EventInstance>) body.getBodyElement(0, new ObjectRef<EventInstance>()), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanEditServiceInfo._INSERTACTIVITY_OP_NUMBER:
            insertActivityErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanEditServiceInfo._INSERTEVENT_OP_NUMBER:
            insertEventErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

}
