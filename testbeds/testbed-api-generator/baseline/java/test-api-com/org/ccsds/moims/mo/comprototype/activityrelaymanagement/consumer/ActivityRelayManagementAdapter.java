package org.ccsds.moims.mo.comprototype.activityrelaymanagement.consumer;

/**
 * Consumer adapter for ActivityRelayManagement service.
 */
public abstract class ActivityRelayManagementAdapter extends org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter {

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation resetTest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void resetTestAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation resetTest.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void resetTestErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation createRelay.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void createRelayAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation createRelay.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void createRelayErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.comprototype.activityrelaymanagement.ActivityRelayManagementServiceInfo._RESETTEST_OP_NUMBER:
            resetTestAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.comprototype.activityrelaymanagement.ActivityRelayManagementServiceInfo._CREATERELAY_OP_NUMBER:
            createRelayAckReceived(msgHeader, qosProperties);
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
          case org.ccsds.moims.mo.comprototype.activityrelaymanagement.ActivityRelayManagementServiceInfo._RESETTEST_OP_NUMBER:
            resetTestErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.comprototype.activityrelaymanagement.ActivityRelayManagementServiceInfo._CREATERELAY_OP_NUMBER:
            createRelayErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

}
