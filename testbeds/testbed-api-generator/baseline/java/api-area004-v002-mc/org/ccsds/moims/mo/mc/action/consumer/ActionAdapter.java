package org.ccsds.moims.mo.mc.action.consumer;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALNotifyBody;
import org.ccsds.moims.mo.mc.MCHelper;
import org.ccsds.moims.mo.mc.action.ActionServiceInfo;
import org.ccsds.moims.mo.mc.structures.ActionEvent;

/**
 * Consumer adapter for Action service.
 */
public abstract class ActionAdapter extends MALInteractionAdapter {

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation execute.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void executeAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation execute.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void executeErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation monitorExecution.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorExecutionRegisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is received
     * from a broker for the operation monitorExecution.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorExecutionRegisterErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation monitorExecution.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorExecutionDeregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update is received from a broker for the
     * operation monitorExecution.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param subscriptionId The subscriptionId of the subscription.
     * @param updateHeader The Update header.
     * @param keys The typed Subscription Key accessors for this update
     * @param progressEvent The progressEvent field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorExecutionNotifyReceived(MALMessageHeader msgHeader,
            Identifier subscriptionId,
            UpdateHeader updateHeader,
            MonitorExecutionSubscriptionKeys keys,
            ActionEvent progressEvent,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation monitorExecution.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorExecutionNotifyErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ActionServiceInfo._EXECUTE_OP_NUMBER:
            executeAckReceived(msgHeader, qosProperties);
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
          case ActionServiceInfo._EXECUTE_OP_NUMBER:
            executeErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void registerAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ActionServiceInfo._MONITOREXECUTION_OP_NUMBER:
            monitorExecutionRegisterAckReceived(msgHeader, qosProperties);
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
          case ActionServiceInfo._MONITOREXECUTION_OP_NUMBER:
            monitorExecutionRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
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
        if ((MCHelper.MC_AREA_NUMBER.equals(msgHeader.getServiceArea())) && (ActionServiceInfo.ACTION_SERVICE_NUMBER.equals(msgHeader.getService()))) {
          switch (msgHeader.getOperation().getValue()) {
            case ActionServiceInfo._MONITOREXECUTION_OP_NUMBER:
              monitorExecutionNotifyReceived(msgHeader,
                (Identifier) body.getBodyElement(0, new Identifier()),
                (UpdateHeader) body.getBodyElement(1, new UpdateHeader()),
                new MonitorExecutionSubscriptionKeys((UpdateHeader) body.getBodyElement(1, new UpdateHeader()), selectedKeys),
                (ActionEvent) body.getBodyElement(2, null), qosProperties);
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
          case ActionServiceInfo._MONITOREXECUTION_OP_NUMBER:
            monitorExecutionNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void deregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ActionServiceInfo._MONITOREXECUTION_OP_NUMBER:
            monitorExecutionDeregisterAckReceived(msgHeader, qosProperties);
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
