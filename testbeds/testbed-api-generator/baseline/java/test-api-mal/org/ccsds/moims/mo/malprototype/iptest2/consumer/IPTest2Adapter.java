package org.ccsds.moims.mo.malprototype.iptest2.consumer;

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
import org.ccsds.moims.mo.malprototype.MALPrototypeHelper;
import org.ccsds.moims.mo.malprototype.iptest2.IPTest2ServiceInfo;
import org.ccsds.moims.mo.malprototype.structures.TestUpdate;

/**
 * Consumer adapter for IPTest2 service.
 */
public abstract class IPTest2Adapter extends MALInteractionAdapter {

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation monitor.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorRegisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is received
     * from a broker for the operation monitor.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorRegisterErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation monitor.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorDeregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update is received from a broker for the
     * operation monitor.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param subscriptionId The subscriptionId of the subscription.
     * @param updateHeader The Update header.
     * @param keys The typed Subscription Key accessors for this update
     * @param pub The pub field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorNotifyReceived(MALMessageHeader msgHeader,
            Identifier subscriptionId,
            UpdateHeader updateHeader,
            MonitorSubscriptionKeys keys,
            TestUpdate pub,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation monitor.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorNotifyErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation publishUpdates.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void publishUpdatesAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation publishUpdates.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void publishUpdatesErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation publishRegister.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void publishRegisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation publishRegister.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void publishRegisterErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation publishDeregister.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void publishDeregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation publishDeregister.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void publishDeregisterErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation testMultipleNotify.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testMultipleNotifyAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation testMultipleNotify.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testMultipleNotifyErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case IPTest2ServiceInfo._PUBLISHUPDATES_OP_NUMBER:
            publishUpdatesAckReceived(msgHeader, qosProperties);
            break;
          case IPTest2ServiceInfo._PUBLISHREGISTER_OP_NUMBER:
            publishRegisterAckReceived(msgHeader, qosProperties);
            break;
          case IPTest2ServiceInfo._PUBLISHDEREGISTER_OP_NUMBER:
            publishDeregisterAckReceived(msgHeader, qosProperties);
            break;
          case IPTest2ServiceInfo._TESTMULTIPLENOTIFY_OP_NUMBER:
            testMultipleNotifyAckReceived(msgHeader, qosProperties);
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
          case IPTest2ServiceInfo._PUBLISHUPDATES_OP_NUMBER:
            publishUpdatesErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case IPTest2ServiceInfo._PUBLISHREGISTER_OP_NUMBER:
            publishRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case IPTest2ServiceInfo._PUBLISHDEREGISTER_OP_NUMBER:
            publishDeregisterErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case IPTest2ServiceInfo._TESTMULTIPLENOTIFY_OP_NUMBER:
            testMultipleNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void registerAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case IPTest2ServiceInfo._MONITOR_OP_NUMBER:
            monitorRegisterAckReceived(msgHeader, qosProperties);
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
          case IPTest2ServiceInfo._MONITOR_OP_NUMBER:
            monitorRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
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
        if ((MALPrototypeHelper.MALPROTOTYPE_AREA_NUMBER.equals(msgHeader.getServiceArea())) && (IPTest2ServiceInfo.IPTEST2_SERVICE_NUMBER.equals(msgHeader.getService()))) {
          switch (msgHeader.getOperation().getValue()) {
            case IPTest2ServiceInfo._MONITOR_OP_NUMBER:
              monitorNotifyReceived(msgHeader,
                (Identifier) body.getBodyElement(0, new Identifier()),
                (UpdateHeader) body.getBodyElement(1, new UpdateHeader()),
                new MonitorSubscriptionKeys((UpdateHeader) body.getBodyElement(1, new UpdateHeader()), selectedKeys),
                (TestUpdate) body.getBodyElement(2, new TestUpdate()), qosProperties);
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
          case IPTest2ServiceInfo._MONITOR_OP_NUMBER:
            monitorNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void deregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case IPTest2ServiceInfo._MONITOR_OP_NUMBER:
            monitorDeregisterAckReceived(msgHeader, qosProperties);
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
