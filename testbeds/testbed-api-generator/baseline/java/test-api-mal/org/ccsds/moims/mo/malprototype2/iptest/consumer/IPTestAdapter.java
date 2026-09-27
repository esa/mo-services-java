package org.ccsds.moims.mo.malprototype2.iptest.consumer;

/**
 * Consumer adapter for IPTest service.
 */
public abstract class IPTestAdapter extends org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter {

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation monitor.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorRegisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is received
     * from a broker for the operation monitor.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorRegisterErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation monitor.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorDeregisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
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
    public void monitorNotifyReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Identifier subscriptionId,
            org.ccsds.moims.mo.mal.structures.UpdateHeader updateHeader,
            MonitorSubscriptionKeys keys,
            org.ccsds.moims.mo.malprototype.structures.TestUpdate pub,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation monitor.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorNotifyErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation monitor2.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitor2RegisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is received
     * from a broker for the operation monitor2.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitor2RegisterErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation monitor2.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitor2DeregisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update is received from a broker for the
     * operation monitor2.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param subscriptionId The subscriptionId of the subscription.
     * @param updateHeader The Update header.
     * @param keys The typed Subscription Key accessors for this update
     * @param pub The pub field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitor2NotifyReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Identifier subscriptionId,
            org.ccsds.moims.mo.mal.structures.UpdateHeader updateHeader,
            Monitor2SubscriptionKeys keys,
            org.ccsds.moims.mo.malprototype.structures.TestUpdate pub,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation monitor2.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitor2NotifyErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation publishUpdates.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void publishUpdatesAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation publishUpdates.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void publishUpdatesErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation publishRegister.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void publishRegisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation publishRegister.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void publishRegisterErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation publishDeregister.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void publishDeregisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation publishDeregister.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void publishDeregisterErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation testMultipleNotify.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testMultipleNotifyAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation testMultipleNotify.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testMultipleNotifyErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation testObjectRefSubmit.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testObjectRefSubmitAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation testObjectRefSubmit.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testObjectRefSubmitErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._PUBLISHUPDATES_OP_NUMBER:
            publishUpdatesAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._PUBLISHREGISTER_OP_NUMBER:
            publishRegisterAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._PUBLISHDEREGISTER_OP_NUMBER:
            publishDeregisterAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._TESTMULTIPLENOTIFY_OP_NUMBER:
            testMultipleNotifyAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._TESTOBJECTREFSUBMIT_OP_NUMBER:
            testObjectRefSubmitAckReceived(msgHeader, qosProperties);
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
          case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._PUBLISHUPDATES_OP_NUMBER:
            publishUpdatesErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._PUBLISHREGISTER_OP_NUMBER:
            publishRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._PUBLISHDEREGISTER_OP_NUMBER:
            publishDeregisterErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._TESTMULTIPLENOTIFY_OP_NUMBER:
            testMultipleNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._TESTOBJECTREFSUBMIT_OP_NUMBER:
            testObjectRefSubmitErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void registerAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._MONITOR_OP_NUMBER:
            monitorRegisterAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._MONITOR2_OP_NUMBER:
            monitor2RegisterAckReceived(msgHeader, qosProperties);
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
          case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._MONITOR_OP_NUMBER:
            monitorRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._MONITOR2_OP_NUMBER:
            monitor2RegisterErrorReceived(msgHeader, body.getError(), qosProperties);
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
        if ((org.ccsds.moims.mo.malprototype2.MALPrototype2Helper.MALPROTOTYPE2_AREA_NUMBER.equals(msgHeader.getServiceArea())) && (org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.IPTEST_SERVICE_NUMBER.equals(msgHeader.getService()))) {
          switch (msgHeader.getOperation().getValue()) {
            case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._MONITOR_OP_NUMBER:
              monitorNotifyReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Identifier) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Identifier()),
                (org.ccsds.moims.mo.mal.structures.UpdateHeader) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UpdateHeader()),
                new MonitorSubscriptionKeys((org.ccsds.moims.mo.mal.structures.UpdateHeader) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UpdateHeader()), selectedKeys),
                (org.ccsds.moims.mo.malprototype.structures.TestUpdate) body.getBodyElement(2, new org.ccsds.moims.mo.malprototype.structures.TestUpdate()), qosProperties);
              break;
            case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._MONITOR2_OP_NUMBER:
              monitor2NotifyReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Identifier) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Identifier()),
                (org.ccsds.moims.mo.mal.structures.UpdateHeader) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UpdateHeader()),
                new Monitor2SubscriptionKeys((org.ccsds.moims.mo.mal.structures.UpdateHeader) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UpdateHeader()), selectedKeys),
                (org.ccsds.moims.mo.malprototype.structures.TestUpdate) body.getBodyElement(2, new org.ccsds.moims.mo.malprototype.structures.TestUpdate()), qosProperties);
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
          case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._MONITOR_OP_NUMBER:
            monitorNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._MONITOR2_OP_NUMBER:
            monitor2NotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void deregisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._MONITOR_OP_NUMBER:
            monitorDeregisterAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._MONITOR2_OP_NUMBER:
            monitor2DeregisterAckReceived(msgHeader, qosProperties);
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
