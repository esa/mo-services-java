package org.ccsds.moims.mo.mps.planningrequest.consumer;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALNotifyBody;
import org.ccsds.moims.mo.mps.MPSHelper;
import org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo;
import org.ccsds.moims.mo.mps.structures.PlanningRequestResponse;
import org.ccsds.moims.mo.mps.structures.RequestInstanceList;
import org.ccsds.moims.mo.mps.structures.RequestStatusUpdate;
import org.ccsds.moims.mo.mps.structures.RequestStatusUpdateList;
import org.ccsds.moims.mo.mps.structures.RequestSummaryStatusList;

/**
 * Consumer adapter for PlanningRequest service.
 */
public abstract class PlanningRequestAdapter extends MALInteractionAdapter {

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation submitRequest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param requestResponse The requestResponse field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void submitRequestResponseReceived(MALMessageHeader msgHeader,
            PlanningRequestResponse requestResponse,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation submitRequest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void submitRequestErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getRequestSummaries.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param requestSummaries The requestSummaries field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestSummariesResponseReceived(MALMessageHeader msgHeader,
            RequestSummaryStatusList requestSummaries,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getRequestSummaries.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestSummariesErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement is received from a provider
     * for the operation getRequestStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestStatusAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update is received from a provider for
     * the operation getRequestStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param requestStatuses The requestStatuses field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestStatusUpdateReceived(MALMessageHeader msgHeader,
            RequestStatusUpdateList requestStatuses,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response is received from a provider
     * for the operation getRequestStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestStatusResponseReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement error is received from
     * a provider for the operation getRequestStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestStatusAckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update error is received from a provider
     * for the operation getRequestStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestStatusUpdateErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response error is received from a provider
     * for the operation getRequestStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestStatusResponseErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation cancelRequest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void cancelRequestAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation cancelRequest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void cancelRequestErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation updateRequest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param requestResponse The requestResponse field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateRequestResponseReceived(MALMessageHeader msgHeader,
            PlanningRequestResponse requestResponse,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation updateRequest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateRequestErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation monitorRequestStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorRequestStatusRegisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is received
     * from a broker for the operation monitorRequestStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorRequestStatusRegisterErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation monitorRequestStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorRequestStatusDeregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update is received from a broker for the
     * operation monitorRequestStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param subscriptionId The subscriptionId of the subscription.
     * @param updateHeader The Update header.
     * @param keys The typed Subscription Key accessors for this update
     * @param requestStatusUpdate The requestStatusUpdate field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorRequestStatusNotifyReceived(MALMessageHeader msgHeader,
            Identifier subscriptionId,
            UpdateHeader updateHeader,
            MonitorRequestStatusSubscriptionKeys keys,
            RequestStatusUpdate requestStatusUpdate,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation monitorRequestStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorRequestStatusNotifyErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement is received from a provider
     * for the operation getRequest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update is received from a provider for
     * the operation getRequest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param requestInstances The requestInstances field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestUpdateReceived(MALMessageHeader msgHeader,
            RequestInstanceList requestInstances,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response is received from a provider
     * for the operation getRequest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestResponseReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement error is received from
     * a provider for the operation getRequest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestAckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update error is received from a provider
     * for the operation getRequest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestUpdateErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response error is received from a provider
     * for the operation getRequest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestResponseErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanningRequestServiceInfo._CANCELREQUEST_OP_NUMBER:
            cancelRequestAckReceived(msgHeader, qosProperties);
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
          case PlanningRequestServiceInfo._CANCELREQUEST_OP_NUMBER:
            cancelRequestErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case PlanningRequestServiceInfo._SUBMITREQUEST_OP_NUMBER:
            submitRequestResponseReceived(msgHeader,
                (PlanningRequestResponse) body.getBodyElement(0, new PlanningRequestResponse()), qosProperties);
            break;
          case PlanningRequestServiceInfo._GETREQUESTSUMMARIES_OP_NUMBER:
            getRequestSummariesResponseReceived(msgHeader,
                (RequestSummaryStatusList) body.getBodyElement(0, new RequestSummaryStatusList()), qosProperties);
            break;
          case PlanningRequestServiceInfo._UPDATEREQUEST_OP_NUMBER:
            updateRequestResponseReceived(msgHeader,
                (PlanningRequestResponse) body.getBodyElement(0, new PlanningRequestResponse()), qosProperties);
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
          case PlanningRequestServiceInfo._SUBMITREQUEST_OP_NUMBER:
            submitRequestErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanningRequestServiceInfo._GETREQUESTSUMMARIES_OP_NUMBER:
            getRequestSummariesErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanningRequestServiceInfo._UPDATEREQUEST_OP_NUMBER:
            updateRequestErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressAckReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanningRequestServiceInfo._GETREQUESTSTATUS_OP_NUMBER:
            getRequestStatusAckReceived(msgHeader, qosProperties);
            break;
          case PlanningRequestServiceInfo._GETREQUEST_OP_NUMBER:
            getRequestAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressAckErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanningRequestServiceInfo._GETREQUESTSTATUS_OP_NUMBER:
            getRequestStatusAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanningRequestServiceInfo._GETREQUEST_OP_NUMBER:
            getRequestAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressUpdateReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanningRequestServiceInfo._GETREQUESTSTATUS_OP_NUMBER:
            getRequestStatusUpdateReceived(msgHeader,
                (RequestStatusUpdateList) body.getBodyElement(0, new RequestStatusUpdateList()), qosProperties);
            break;
          case PlanningRequestServiceInfo._GETREQUEST_OP_NUMBER:
            getRequestUpdateReceived(msgHeader,
                (RequestInstanceList) body.getBodyElement(0, new RequestInstanceList()), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressUpdateErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanningRequestServiceInfo._GETREQUESTSTATUS_OP_NUMBER:
            getRequestStatusUpdateErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanningRequestServiceInfo._GETREQUEST_OP_NUMBER:
            getRequestUpdateErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressResponseReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanningRequestServiceInfo._GETREQUESTSTATUS_OP_NUMBER:
            getRequestStatusResponseReceived(msgHeader, qosProperties);
            break;
          case PlanningRequestServiceInfo._GETREQUEST_OP_NUMBER:
            getRequestResponseReceived(msgHeader, qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressResponseErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanningRequestServiceInfo._GETREQUESTSTATUS_OP_NUMBER:
            getRequestStatusResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanningRequestServiceInfo._GETREQUEST_OP_NUMBER:
            getRequestResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void registerAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanningRequestServiceInfo._MONITORREQUESTSTATUS_OP_NUMBER:
            monitorRequestStatusRegisterAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void registerErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanningRequestServiceInfo._MONITORREQUESTSTATUS_OP_NUMBER:
            monitorRequestStatusRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void notifyReceived(MALMessageHeader msgHeader,
            MALNotifyBody body,
            IdentifierList selectedKeys,
            Map qosProperties) throws MALException {
        if ((MPSHelper.MPS_AREA_NUMBER.equals(msgHeader.getServiceArea())) && (PlanningRequestServiceInfo.PLANNINGREQUEST_SERVICE_NUMBER.equals(msgHeader.getService()))) {
          switch (msgHeader.getOperation().getValue()) {
            case PlanningRequestServiceInfo._MONITORREQUESTSTATUS_OP_NUMBER:
              monitorRequestStatusNotifyReceived(msgHeader,
                (Identifier) body.getBodyElement(0, new Identifier()),
                (UpdateHeader) body.getBodyElement(1, new UpdateHeader()),
                new MonitorRequestStatusSubscriptionKeys((UpdateHeader) body.getBodyElement(1, new UpdateHeader()), selectedKeys),
                (RequestStatusUpdate) body.getBodyElement(2, new RequestStatusUpdate()), qosProperties);
              break;
            default:
              throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
          }
        }
        else {
          notifyReceivedFromOtherService(msgHeader, body, qosProperties);
        }
    }

    @Override
    public final void notifyErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanningRequestServiceInfo._MONITORREQUESTSTATUS_OP_NUMBER:
            monitorRequestStatusNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void deregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanningRequestServiceInfo._MONITORREQUESTSTATUS_OP_NUMBER:
            monitorRequestStatusDeregisterAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    /**
     * Called by the MAL when a PubSub update from another service is received
     * from a broker.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param body body The body of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     * @throws MALException if an error is detected processing the message.
     */
    public void notifyReceivedFromOtherService(MALMessageHeader msgHeader,
            MALNotifyBody body,
            Map qosProperties) throws MALException {
    }

}
