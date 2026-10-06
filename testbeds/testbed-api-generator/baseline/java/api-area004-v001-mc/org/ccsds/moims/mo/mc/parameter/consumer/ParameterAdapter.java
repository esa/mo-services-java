package org.ccsds.moims.mo.mc.parameter.consumer;

import java.util.Map;
import org.ccsds.moims.mo.com.structures.ObjectId;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALNotifyBody;
import org.ccsds.moims.mo.mc.MCHelper;
import org.ccsds.moims.mo.mc.parameter.ParameterServiceInfo;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterValue;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterValueDetailsList;
import org.ccsds.moims.mo.mc.structures.ObjectInstancePairList;

/**
 * Consumer adapter for Parameter service.
 */
public abstract class ParameterAdapter extends MALInteractionAdapter {

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation monitorValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorValueRegisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is received
     * from a broker for the operation monitorValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorValueRegisterErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation monitorValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorValueDeregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update is received from a broker for the
     * operation monitorValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param subscriptionId The subscriptionId of the subscription.
     * @param updateHeader The Update header.
     * @param keys The typed Subscription Key accessors for this update
     * @param objId The MAL EntityKey.firstSubKey shall contain the parameter name.
The MAL EntityKey.secondSubKey shall contain the ParameterIdentity object instance identifier.
The MAL EntityKey.thirdSubKey shall contain the ParameterDefinition object instance identifier.
The MAL EntityKey.fourthSubKey shall contain the new ParameterValueInstance object instance identifier.
The timestamp of the ParameterValueInstance report shall be taken from the publish message and shall be the time of the parameter value update.
The publish message shall include the ObjectId of the source link of the report.
If no source link is needed then the ObjectId shall be replaced with a NULL.
     * @param newValue The second part of the publish message shall be the ParameterValueInstance object value.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorValueNotifyReceived(MALMessageHeader msgHeader,
            Identifier subscriptionId,
            UpdateHeader updateHeader,
            MonitorValueSubscriptionKeys keys,
            ObjectId objId,
            ParameterValue newValue,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation monitorValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorValueNotifyErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param paramValDetails The response shall contain a list of returned ParameterIdentity and ParameterDefinition object instance identifier pairs and a matching list of parameter values.
The new value shall not be published via the monitorValue operation.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getValueResponseReceived(MALMessageHeader msgHeader,
            ParameterValueDetailsList paramValDetails,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getValueErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation setValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void setValueAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation setValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void setValueErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation enableGeneration.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param newObjInstIds The response shall contain the list of object instance identifiers for the new ParameterDefinition objects.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void enableGenerationResponseReceived(MALMessageHeader msgHeader,
            LongList newObjInstIds,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation enableGeneration.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void enableGenerationErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation listDefinition.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param objInstIds The response shall contain a list of matching ParameterIdentity and ParameterDefinition object instance identifier pairs.
The returned list shall maintain the same order as the submitted list unless the wildcard value was included in the request.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listDefinitionResponseReceived(MALMessageHeader msgHeader,
            ObjectInstancePairList objInstIds,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation listDefinition.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listDefinitionErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation addParameter.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param newObjInstIds The response shall contain the list of object instance identifiers for the ParameterIdentity and new ParameterDefinition objects.
The returned list shall maintain the same order as the submitted definitions.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void addParameterResponseReceived(MALMessageHeader msgHeader,
            ObjectInstancePairList newObjInstIds,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation addParameter.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void addParameterErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation updateDefinition.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param newObjInstIds The response shall contain the list of object instance identifiers for the new ParameterDefinition objects.
The returned list shall maintain the same order as the submitted definitions.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateDefinitionResponseReceived(MALMessageHeader msgHeader,
            LongList newObjInstIds,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation updateDefinition.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateDefinitionErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation removeParameter.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void removeParameterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation removeParameter.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void removeParameterErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ParameterServiceInfo._SETVALUE_OP_NUMBER:
            setValueAckReceived(msgHeader, qosProperties);
            break;
          case ParameterServiceInfo._REMOVEPARAMETER_OP_NUMBER:
            removeParameterAckReceived(msgHeader, qosProperties);
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
          case ParameterServiceInfo._SETVALUE_OP_NUMBER:
            setValueErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ParameterServiceInfo._REMOVEPARAMETER_OP_NUMBER:
            removeParameterErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case ParameterServiceInfo._GETVALUE_OP_NUMBER:
            getValueResponseReceived(msgHeader,
                (ParameterValueDetailsList) body.getBodyElement(0, new ParameterValueDetailsList()), qosProperties);
            break;
          case ParameterServiceInfo._ENABLEGENERATION_OP_NUMBER:
            enableGenerationResponseReceived(msgHeader,
                (LongList) body.getBodyElement(0, new LongList()), qosProperties);
            break;
          case ParameterServiceInfo._LISTDEFINITION_OP_NUMBER:
            listDefinitionResponseReceived(msgHeader,
                (ObjectInstancePairList) body.getBodyElement(0, new ObjectInstancePairList()), qosProperties);
            break;
          case ParameterServiceInfo._ADDPARAMETER_OP_NUMBER:
            addParameterResponseReceived(msgHeader,
                (ObjectInstancePairList) body.getBodyElement(0, new ObjectInstancePairList()), qosProperties);
            break;
          case ParameterServiceInfo._UPDATEDEFINITION_OP_NUMBER:
            updateDefinitionResponseReceived(msgHeader,
                (LongList) body.getBodyElement(0, new LongList()), qosProperties);
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
          case ParameterServiceInfo._GETVALUE_OP_NUMBER:
            getValueErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ParameterServiceInfo._ENABLEGENERATION_OP_NUMBER:
            enableGenerationErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ParameterServiceInfo._LISTDEFINITION_OP_NUMBER:
            listDefinitionErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ParameterServiceInfo._ADDPARAMETER_OP_NUMBER:
            addParameterErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ParameterServiceInfo._UPDATEDEFINITION_OP_NUMBER:
            updateDefinitionErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void registerAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ParameterServiceInfo._MONITORVALUE_OP_NUMBER:
            monitorValueRegisterAckReceived(msgHeader, qosProperties);
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
          case ParameterServiceInfo._MONITORVALUE_OP_NUMBER:
            monitorValueRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
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
        if ((MCHelper.MC_AREA_NUMBER.equals(msgHeader.getServiceArea())) && (ParameterServiceInfo.PARAMETER_SERVICE_NUMBER.equals(msgHeader.getService()))) {
          switch (msgHeader.getOperation().getValue()) {
            case ParameterServiceInfo._MONITORVALUE_OP_NUMBER:
              monitorValueNotifyReceived(msgHeader,
                (Identifier) body.getBodyElement(0, new Identifier()),
                (UpdateHeader) body.getBodyElement(1, new UpdateHeader()),
                new MonitorValueSubscriptionKeys((UpdateHeader) body.getBodyElement(1, new UpdateHeader()), selectedKeys),
                (ObjectId) body.getBodyElement(2, new ObjectId()),
                (ParameterValue) body.getBodyElement(3, new ParameterValue()), qosProperties);
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
          case ParameterServiceInfo._MONITORVALUE_OP_NUMBER:
            monitorValueNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void deregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ParameterServiceInfo._MONITORVALUE_OP_NUMBER:
            monitorValueDeregisterAckReceived(msgHeader, qosProperties);
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
