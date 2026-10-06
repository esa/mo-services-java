package org.ccsds.moims.mo.comprototype.eventtest.consumer;

import java.util.Map;
import org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Consumer adapter for EventTest service.
 */
public abstract class EventTestAdapter extends MALInteractionAdapter {

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation resetTest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void resetTestAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation resetTest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void resetTestErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation createinstance.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void createinstanceResponseReceived(MALMessageHeader msgHeader,
            Long output,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation createinstance.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void createinstanceErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation deleteInstance.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deleteInstanceAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation deleteInstance.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deleteInstanceErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation updateInstance.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateInstanceAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation updateInstance.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateInstanceErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation updateInstanceComposite.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateInstanceCompositeAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation updateInstanceComposite.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateInstanceCompositeErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case EventTestServiceInfo._RESETTEST_OP_NUMBER:
            resetTestAckReceived(msgHeader, qosProperties);
            break;
          case EventTestServiceInfo._DELETEINSTANCE_OP_NUMBER:
            deleteInstanceAckReceived(msgHeader, qosProperties);
            break;
          case EventTestServiceInfo._UPDATEINSTANCE_OP_NUMBER:
            updateInstanceAckReceived(msgHeader, qosProperties);
            break;
          case EventTestServiceInfo._UPDATEINSTANCECOMPOSITE_OP_NUMBER:
            updateInstanceCompositeAckReceived(msgHeader, qosProperties);
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
          case EventTestServiceInfo._RESETTEST_OP_NUMBER:
            resetTestErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case EventTestServiceInfo._DELETEINSTANCE_OP_NUMBER:
            deleteInstanceErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case EventTestServiceInfo._UPDATEINSTANCE_OP_NUMBER:
            updateInstanceErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case EventTestServiceInfo._UPDATEINSTANCECOMPOSITE_OP_NUMBER:
            updateInstanceCompositeErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case EventTestServiceInfo._CREATEINSTANCE_OP_NUMBER:
            createinstanceResponseReceived(msgHeader,
                (body.getBodyElement(0, new Union(Long.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Long.MAX_VALUE))).getLongValue(), qosProperties);
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
          case EventTestServiceInfo._CREATEINSTANCE_OP_NUMBER:
            createinstanceErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

}
