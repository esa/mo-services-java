package org.ccsds.moims.mo.mps.planningrequest.consumer;

/**
 * Consumer adapter for PlanningRequest service.
 */
public abstract class PlanningRequestAdapter extends org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter {

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation submitRequest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param requestResponse The requestResponse field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void submitRequestResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mps.structures.PlanningRequestResponse requestResponse,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation submitRequest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void submitRequestErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getRequestSummaries.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param requestSummaries The requestSummaries field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestSummariesResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mps.structures.RequestSummaryStatusList requestSummaries,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getRequestSummaries.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestSummariesErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement is received from a provider
     * for the operation getRequestStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestStatusAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update is received from a provider for
     * the operation getRequestStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param requestStatuses The requestStatuses field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestStatusUpdateReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mps.structures.RequestStatusUpdateList requestStatuses,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response is received from a provider
     * for the operation getRequestStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestStatusResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement error is received from
     * a provider for the operation getRequestStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestStatusAckErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update error is received from a provider
     * for the operation getRequestStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestStatusUpdateErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response error is received from a provider
     * for the operation getRequestStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestStatusResponseErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation cancelRequest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void cancelRequestAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation cancelRequest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void cancelRequestErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation updateRequest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param requestResponse The requestResponse field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateRequestResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mps.structures.PlanningRequestResponse requestResponse,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation updateRequest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateRequestErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation monitorRequestStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorRequestStatusRegisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is received
     * from a broker for the operation monitorRequestStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorRequestStatusRegisterErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation monitorRequestStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorRequestStatusDeregisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
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
    public void monitorRequestStatusNotifyReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Identifier subscriptionId,
            org.ccsds.moims.mo.mal.structures.UpdateHeader updateHeader,
            MonitorRequestStatusSubscriptionKeys keys,
            org.ccsds.moims.mo.mps.structures.RequestStatusUpdate requestStatusUpdate,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation monitorRequestStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorRequestStatusNotifyErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement is received from a provider
     * for the operation getRequest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update is received from a provider for
     * the operation getRequest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param requestInstances The requestInstances field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestUpdateReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mps.structures.RequestInstanceList requestInstances,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response is received from a provider
     * for the operation getRequest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement error is received from
     * a provider for the operation getRequest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestAckErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update error is received from a provider
     * for the operation getRequest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestUpdateErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response error is received from a provider
     * for the operation getRequest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestResponseErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo._CANCELREQUEST_OP_NUMBER:
            cancelRequestAckReceived(msgHeader, qosProperties);
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
          case org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo._CANCELREQUEST_OP_NUMBER:
            cancelRequestErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo._SUBMITREQUEST_OP_NUMBER:
            submitRequestResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mps.structures.PlanningRequestResponse) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.PlanningRequestResponse()), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo._GETREQUESTSUMMARIES_OP_NUMBER:
            getRequestSummariesResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mps.structures.RequestSummaryStatusList) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.RequestSummaryStatusList()), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo._UPDATEREQUEST_OP_NUMBER:
            updateRequestResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mps.structures.PlanningRequestResponse) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.PlanningRequestResponse()), qosProperties);
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
          case org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo._SUBMITREQUEST_OP_NUMBER:
            submitRequestErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo._GETREQUESTSUMMARIES_OP_NUMBER:
            getRequestSummariesErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo._UPDATEREQUEST_OP_NUMBER:
            updateRequestErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo._GETREQUESTSTATUS_OP_NUMBER:
            getRequestStatusAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo._GETREQUEST_OP_NUMBER:
            getRequestAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressAckErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo._GETREQUESTSTATUS_OP_NUMBER:
            getRequestStatusAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo._GETREQUEST_OP_NUMBER:
            getRequestAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressUpdateReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo._GETREQUESTSTATUS_OP_NUMBER:
            getRequestStatusUpdateReceived(msgHeader,
                (org.ccsds.moims.mo.mps.structures.RequestStatusUpdateList) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.RequestStatusUpdateList()), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo._GETREQUEST_OP_NUMBER:
            getRequestUpdateReceived(msgHeader,
                (org.ccsds.moims.mo.mps.structures.RequestInstanceList) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.RequestInstanceList()), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressUpdateErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo._GETREQUESTSTATUS_OP_NUMBER:
            getRequestStatusUpdateErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo._GETREQUEST_OP_NUMBER:
            getRequestUpdateErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo._GETREQUESTSTATUS_OP_NUMBER:
            getRequestStatusResponseReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo._GETREQUEST_OP_NUMBER:
            getRequestResponseReceived(msgHeader, qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressResponseErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo._GETREQUESTSTATUS_OP_NUMBER:
            getRequestStatusResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo._GETREQUEST_OP_NUMBER:
            getRequestResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void registerAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo._MONITORREQUESTSTATUS_OP_NUMBER:
            monitorRequestStatusRegisterAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void registerErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo._MONITORREQUESTSTATUS_OP_NUMBER:
            monitorRequestStatusRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void notifyReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALNotifyBody body,
            org.ccsds.moims.mo.mal.structures.IdentifierList selectedKeys,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        if ((org.ccsds.moims.mo.mps.MPSHelper.MPS_AREA_NUMBER.equals(msgHeader.getServiceArea())) && (org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo.PLANNINGREQUEST_SERVICE_NUMBER.equals(msgHeader.getService()))) {
          switch (msgHeader.getOperation().getValue()) {
            case org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo._MONITORREQUESTSTATUS_OP_NUMBER:
              monitorRequestStatusNotifyReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Identifier) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Identifier()),
                (org.ccsds.moims.mo.mal.structures.UpdateHeader) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UpdateHeader()),
                new MonitorRequestStatusSubscriptionKeys((org.ccsds.moims.mo.mal.structures.UpdateHeader) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UpdateHeader()), selectedKeys),
                (org.ccsds.moims.mo.mps.structures.RequestStatusUpdate) body.getBodyElement(2, new org.ccsds.moims.mo.mps.structures.RequestStatusUpdate()), qosProperties);
              break;
            default:
              throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
          }
        }
        else {
          notifyReceivedFromOtherService(msgHeader, body, qosProperties);
        }
    }

    @Override
    public final void notifyErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo._MONITORREQUESTSTATUS_OP_NUMBER:
            monitorRequestStatusNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void deregisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo._MONITORREQUESTSTATUS_OP_NUMBER:
            monitorRequestStatusDeregisterAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    /**
     * Called by the MAL when a PubSub update from another service is received
     * from a broker.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param body body The body of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     * @throws org.ccsds.moims.mo.mal.MALException if an error is detected processing the message.
     */
    public void notifyReceivedFromOtherService(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALNotifyBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
    }

}
