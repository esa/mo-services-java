package org.ccsds.moims.mo.mc.alert.consumer;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mc.alert.AlertServiceInfo;
import org.ccsds.moims.mo.mc.structures.ObjectInstancePairList;

/**
 * Consumer adapter for Alert service.
 */
public abstract class AlertAdapter extends MALInteractionAdapter {

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation enableGeneration.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param newObjInstIds The response shall contain the list of object instance identifiers for the new AlertDefinition objects.
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
     * @param alertObjInstIds The response shall contain a list of matching AlertIdentity and AlertDefinition object instance identifiers.
The returned list shall maintain the same order as the submitted list unless the wildcard value was included in the request.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listDefinitionResponseReceived(MALMessageHeader msgHeader,
            ObjectInstancePairList alertObjInstIds,
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
     * the operation addAlert.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param newObjInstIds The response shall contain the list of object instance identifiers for the AlertIdentity and new AlertDefinition objects.
The returned list shall maintain the same order as the submitted definitions.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void addAlertResponseReceived(MALMessageHeader msgHeader,
            ObjectInstancePairList newObjInstIds,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation addAlert.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void addAlertErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation updateDefinition.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param newObjInstIds The response shall contain the list of object instance identifiers for the new AlertDefinition objects.
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
     * for the operation removeAlert.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void removeAlertAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation removeAlert.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void removeAlertErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case AlertServiceInfo._REMOVEALERT_OP_NUMBER:
            removeAlertAckReceived(msgHeader, qosProperties);
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
          case AlertServiceInfo._REMOVEALERT_OP_NUMBER:
            removeAlertErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case AlertServiceInfo._ENABLEGENERATION_OP_NUMBER:
            enableGenerationResponseReceived(msgHeader,
                (LongList) body.getBodyElement(0, new LongList()), qosProperties);
            break;
          case AlertServiceInfo._LISTDEFINITION_OP_NUMBER:
            listDefinitionResponseReceived(msgHeader,
                (ObjectInstancePairList) body.getBodyElement(0, new ObjectInstancePairList()), qosProperties);
            break;
          case AlertServiceInfo._ADDALERT_OP_NUMBER:
            addAlertResponseReceived(msgHeader,
                (ObjectInstancePairList) body.getBodyElement(0, new ObjectInstancePairList()), qosProperties);
            break;
          case AlertServiceInfo._UPDATEDEFINITION_OP_NUMBER:
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
          case AlertServiceInfo._ENABLEGENERATION_OP_NUMBER:
            enableGenerationErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case AlertServiceInfo._LISTDEFINITION_OP_NUMBER:
            listDefinitionErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case AlertServiceInfo._ADDALERT_OP_NUMBER:
            addAlertErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case AlertServiceInfo._UPDATEDEFINITION_OP_NUMBER:
            updateDefinitionErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

}
