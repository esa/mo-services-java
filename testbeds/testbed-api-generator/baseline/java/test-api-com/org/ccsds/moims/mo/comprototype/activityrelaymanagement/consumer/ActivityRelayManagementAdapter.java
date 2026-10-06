package org.ccsds.moims.mo.comprototype.activityrelaymanagement.consumer;

import java.util.Map;
import org.ccsds.moims.mo.comprototype.activityrelaymanagement.ActivityRelayManagementServiceInfo;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Consumer adapter for ActivityRelayManagement service.
 */
public abstract class ActivityRelayManagementAdapter extends MALInteractionAdapter {

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
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation createRelay.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void createRelayAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation createRelay.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void createRelayErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ActivityRelayManagementServiceInfo._RESETTEST_OP_NUMBER:
            resetTestAckReceived(msgHeader, qosProperties);
            break;
          case ActivityRelayManagementServiceInfo._CREATERELAY_OP_NUMBER:
            createRelayAckReceived(msgHeader, qosProperties);
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
          case ActivityRelayManagementServiceInfo._RESETTEST_OP_NUMBER:
            resetTestErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ActivityRelayManagementServiceInfo._CREATERELAY_OP_NUMBER:
            createRelayErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

}
