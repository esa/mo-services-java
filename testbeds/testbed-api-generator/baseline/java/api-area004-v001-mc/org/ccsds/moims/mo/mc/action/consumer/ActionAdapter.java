package org.ccsds.moims.mo.mc.action.consumer;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mc.action.ActionServiceInfo;
import org.ccsds.moims.mo.mc.structures.ObjectInstancePairList;

/**
 * Consumer adapter for Action service.
 */
public abstract class ActionAdapter extends MALInteractionAdapter {

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation submitAction.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void submitActionAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation submitAction.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void submitActionErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation preCheckAction.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param accepted The returned Boolean shall be set to TRUE if the action would be accepted successfully; otherwise the operation shall return FALSE.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void preCheckActionResponseReceived(MALMessageHeader msgHeader,
            Boolean accepted,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation preCheckAction.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void preCheckActionErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation listDefinition.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param actionInstIds The response shall contain a list of matching ActionIdentity and ActionDefinition object instance identifiers.
The returned list shall maintain the same order as the submitted list unless the wildcard value was included in the request.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listDefinitionResponseReceived(MALMessageHeader msgHeader,
            ObjectInstancePairList actionInstIds,
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
     * the operation addAction.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param newObjInstIds The response shall contain the list of object instance identifiers for the ActionIdentity and new ActionDefinition objects.
The returned list shall maintain the same order as the submitted definitions.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void addActionResponseReceived(MALMessageHeader msgHeader,
            ObjectInstancePairList newObjInstIds,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation addAction.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void addActionErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation updateDefinition.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param newDefInstIds The response shall contain the list of object instance identifiers for the new ActionDefinition objects.
The returned list shall maintain the same order as the submitted definitions.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateDefinitionResponseReceived(MALMessageHeader msgHeader,
            LongList newDefInstIds,
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
     * for the operation removeAction.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void removeActionAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation removeAction.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void removeActionErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ActionServiceInfo._SUBMITACTION_OP_NUMBER:
            submitActionAckReceived(msgHeader, qosProperties);
            break;
          case ActionServiceInfo._REMOVEACTION_OP_NUMBER:
            removeActionAckReceived(msgHeader, qosProperties);
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
          case ActionServiceInfo._SUBMITACTION_OP_NUMBER:
            submitActionErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ActionServiceInfo._REMOVEACTION_OP_NUMBER:
            removeActionErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case ActionServiceInfo._PRECHECKACTION_OP_NUMBER:
            preCheckActionResponseReceived(msgHeader,
                (body.getBodyElement(0, new Union(Boolean.FALSE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Boolean.FALSE))).getBooleanValue(), qosProperties);
            break;
          case ActionServiceInfo._LISTDEFINITION_OP_NUMBER:
            listDefinitionResponseReceived(msgHeader,
                (ObjectInstancePairList) body.getBodyElement(0, new ObjectInstancePairList()), qosProperties);
            break;
          case ActionServiceInfo._ADDACTION_OP_NUMBER:
            addActionResponseReceived(msgHeader,
                (ObjectInstancePairList) body.getBodyElement(0, new ObjectInstancePairList()), qosProperties);
            break;
          case ActionServiceInfo._UPDATEDEFINITION_OP_NUMBER:
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
          case ActionServiceInfo._PRECHECKACTION_OP_NUMBER:
            preCheckActionErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ActionServiceInfo._LISTDEFINITION_OP_NUMBER:
            listDefinitionErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ActionServiceInfo._ADDACTION_OP_NUMBER:
            addActionErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ActionServiceInfo._UPDATEDEFINITION_OP_NUMBER:
            updateDefinitionErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

}
