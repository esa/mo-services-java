package org.ccsds.moims.mo.mc.aggregation.consumer;

/**
 * Consumer adapter for Aggregation service.
 */
public abstract class AggregationAdapter extends org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter {

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation monitorValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorValueRegisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is received
     * from a broker for the operation monitorValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorValueRegisterErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation monitorValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorValueDeregisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update is received from a broker for the
     * operation monitorValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param subscriptionId The subscriptionId of the subscription.
     * @param updateHeader The Update header.
     * @param keys The typed Subscription Key accessors for this update
     * @param objId The MAL EntityKey.firstSubKey shall contain the aggregation name.
The MAL EntityKey.secondSubKey shall contain the AggregationIdentity object instance identifier.
The MAL EntityKey.thirdSubKey shall contain the AggregationDefinition object instance identifier.
The MAL EntityKey.fourthSubKey shall contain the new AggregationValueInstance object instance identifier.
The timestamp of the AggregationValueInstance report shall be taken from the publish message.
The publish message shall include the ObjectId of the source link of the report.
If no source link is needed then the ObjectId shall be replaced with a NULL.
     * @param newValue The second part of the publish message shall be the AggregationValue.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorValueNotifyReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Identifier subscriptionId,
            org.ccsds.moims.mo.mal.structures.UpdateHeader updateHeader,
            MonitorValueSubscriptionKeys keys,
            org.ccsds.moims.mo.com.structures.ObjectId objId,
            org.ccsds.moims.mo.mc.aggregation.structures.AggregationValue newValue,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation monitorValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorValueNotifyErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param aggValDetails The response shall contain a list of returned AggregationIdentity and AggregationDefinition object instance identifier pairs and a matching list of AggregationValues.
The new value shall not be published via the monitorValue operation.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getValueResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mc.aggregation.structures.AggregationValueDetailsList aggValDetails,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getValueErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation enableGeneration.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param newObjInstIds The response shall contain the list of object instance identifiers for the new AggregationDefinition objects.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void enableGenerationResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.LongList newObjInstIds,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation enableGeneration.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void enableGenerationErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation enableFilter.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void enableFilterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation enableFilter.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void enableFilterErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation listDefinition.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param objInstIds The response shall contain a list of matching AggregationIdentity and AggregationDefinition object instance identifiers.
The returned list shall maintain the same order as the submitted list unless the wildcard value was included in the request.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listDefinitionResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mc.structures.ObjectInstancePairList objInstIds,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation listDefinition.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listDefinitionErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation addAggregation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param newObjInstIds The response shall contain the list of object instance identifiers for the AggregationIdentity and new AggregationDefinition objects.
The returned list shall maintain the same order as the submitted definitions.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void addAggregationResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mc.structures.ObjectInstancePairList newObjInstIds,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation addAggregation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void addAggregationErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation updateDefinition.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param newObjInstIds The response shall contain the list of object instance identifiers for the new AggregationDefinition objects.
The returned list shall maintain the same order as the submitted definitions.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateDefinitionResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.LongList newObjInstIds,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation updateDefinition.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateDefinitionErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation removeAggregation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void removeAggregationAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation removeAggregation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void removeAggregationErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo._ENABLEFILTER_OP_NUMBER:
            enableFilterAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo._REMOVEAGGREGATION_OP_NUMBER:
            removeAggregationAckReceived(msgHeader, qosProperties);
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
          case org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo._ENABLEFILTER_OP_NUMBER:
            enableFilterErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo._REMOVEAGGREGATION_OP_NUMBER:
            removeAggregationErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo._GETVALUE_OP_NUMBER:
            getValueResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mc.aggregation.structures.AggregationValueDetailsList) body.getBodyElement(0, new org.ccsds.moims.mo.mc.aggregation.structures.AggregationValueDetailsList()), qosProperties);
            break;
          case org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo._ENABLEGENERATION_OP_NUMBER:
            enableGenerationResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.LongList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.LongList()), qosProperties);
            break;
          case org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo._LISTDEFINITION_OP_NUMBER:
            listDefinitionResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mc.structures.ObjectInstancePairList) body.getBodyElement(0, new org.ccsds.moims.mo.mc.structures.ObjectInstancePairList()), qosProperties);
            break;
          case org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo._ADDAGGREGATION_OP_NUMBER:
            addAggregationResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mc.structures.ObjectInstancePairList) body.getBodyElement(0, new org.ccsds.moims.mo.mc.structures.ObjectInstancePairList()), qosProperties);
            break;
          case org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo._UPDATEDEFINITION_OP_NUMBER:
            updateDefinitionResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.LongList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.LongList()), qosProperties);
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
          case org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo._GETVALUE_OP_NUMBER:
            getValueErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo._ENABLEGENERATION_OP_NUMBER:
            enableGenerationErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo._LISTDEFINITION_OP_NUMBER:
            listDefinitionErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo._ADDAGGREGATION_OP_NUMBER:
            addAggregationErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo._UPDATEDEFINITION_OP_NUMBER:
            updateDefinitionErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void registerAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo._MONITORVALUE_OP_NUMBER:
            monitorValueRegisterAckReceived(msgHeader, qosProperties);
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
          case org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo._MONITORVALUE_OP_NUMBER:
            monitorValueRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
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
        if ((org.ccsds.moims.mo.mc.MCHelper.MC_AREA_NUMBER.equals(msgHeader.getServiceArea())) && (org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.AGGREGATION_SERVICE_NUMBER.equals(msgHeader.getService()))) {
          switch (msgHeader.getOperation().getValue()) {
            case org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo._MONITORVALUE_OP_NUMBER:
              monitorValueNotifyReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Identifier) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Identifier()),
                (org.ccsds.moims.mo.mal.structures.UpdateHeader) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UpdateHeader()),
                new MonitorValueSubscriptionKeys((org.ccsds.moims.mo.mal.structures.UpdateHeader) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UpdateHeader()), selectedKeys),
                (org.ccsds.moims.mo.com.structures.ObjectId) body.getBodyElement(2, new org.ccsds.moims.mo.com.structures.ObjectId()),
                (org.ccsds.moims.mo.mc.aggregation.structures.AggregationValue) body.getBodyElement(3, new org.ccsds.moims.mo.mc.aggregation.structures.AggregationValue()), qosProperties);
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
          case org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo._MONITORVALUE_OP_NUMBER:
            monitorValueNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void deregisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo._MONITORVALUE_OP_NUMBER:
            monitorValueDeregisterAckReceived(msgHeader, qosProperties);
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
