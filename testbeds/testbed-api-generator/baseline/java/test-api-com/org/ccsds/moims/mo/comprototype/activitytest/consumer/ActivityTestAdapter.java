package org.ccsds.moims.mo.comprototype.activitytest.consumer;

import java.util.Map;
import org.ccsds.moims.mo.comprototype.activitytest.ActivityTestServiceInfo;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Consumer adapter for ActivityTest service.
 */
public abstract class ActivityTestAdapter extends MALInteractionAdapter {

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
     * for the operation close.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void closeAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation close.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void closeErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation testSubmit.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testSubmitAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation testSubmit.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testSubmitErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation request.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param out1 The out1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void requestResponseReceived(MALMessageHeader msgHeader,
            StringList out1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation request.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void requestErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE acknowledgement is received from a provider
     * for the operation invoke.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param ack1 The ack1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void invokeAckReceived(MALMessageHeader msgHeader,
            StringList ack1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE response is received from a provider for
     * the operation invoke.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param out1 The out1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void invokeResponseReceived(MALMessageHeader msgHeader,
            StringList out1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE acknowledgement error is received from
     * a provider for the operation invoke.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void invokeAckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE response error is received from a provider
     * for the operation invoke.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void invokeResponseErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement is received from a provider
     * for the operation progress.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param ack1 The ack1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void progressAckReceived(MALMessageHeader msgHeader,
            StringList ack1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update is received from a provider for
     * the operation progress.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param out1 The out1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void progressUpdateReceived(MALMessageHeader msgHeader,
            StringList out1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response is received from a provider
     * for the operation progress.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param out2 The out2 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void progressResponseReceived(MALMessageHeader msgHeader,
            StringList out2,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement error is received from
     * a provider for the operation progress.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void progressAckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update error is received from a provider
     * for the operation progress.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void progressUpdateErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response error is received from a provider
     * for the operation progress.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void progressResponseErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ActivityTestServiceInfo._RESETTEST_OP_NUMBER:
            resetTestAckReceived(msgHeader, qosProperties);
            break;
          case ActivityTestServiceInfo._CLOSE_OP_NUMBER:
            closeAckReceived(msgHeader, qosProperties);
            break;
          case ActivityTestServiceInfo._TESTSUBMIT_OP_NUMBER:
            testSubmitAckReceived(msgHeader, qosProperties);
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
          case ActivityTestServiceInfo._RESETTEST_OP_NUMBER:
            resetTestErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ActivityTestServiceInfo._CLOSE_OP_NUMBER:
            closeErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ActivityTestServiceInfo._TESTSUBMIT_OP_NUMBER:
            testSubmitErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case ActivityTestServiceInfo._REQUEST_OP_NUMBER:
            requestResponseReceived(msgHeader,
                (StringList) body.getBodyElement(0, new StringList()), qosProperties);
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
          case ActivityTestServiceInfo._REQUEST_OP_NUMBER:
            requestErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void invokeAckReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ActivityTestServiceInfo._INVOKE_OP_NUMBER:
            invokeAckReceived(msgHeader,
                (StringList) body.getBodyElement(0, new StringList()), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void invokeAckErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ActivityTestServiceInfo._INVOKE_OP_NUMBER:
            invokeAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void invokeResponseReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ActivityTestServiceInfo._INVOKE_OP_NUMBER:
            invokeResponseReceived(msgHeader,
                (StringList) body.getBodyElement(0, new StringList()), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void invokeResponseErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ActivityTestServiceInfo._INVOKE_OP_NUMBER:
            invokeResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressAckReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ActivityTestServiceInfo._PROGRESS_OP_NUMBER:
            progressAckReceived(msgHeader,
                (StringList) body.getBodyElement(0, new StringList()), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressAckErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ActivityTestServiceInfo._PROGRESS_OP_NUMBER:
            progressAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressUpdateReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ActivityTestServiceInfo._PROGRESS_OP_NUMBER:
            progressUpdateReceived(msgHeader,
                (StringList) body.getBodyElement(0, new StringList()), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressUpdateErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ActivityTestServiceInfo._PROGRESS_OP_NUMBER:
            progressUpdateErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressResponseReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ActivityTestServiceInfo._PROGRESS_OP_NUMBER:
            progressResponseReceived(msgHeader,
                (StringList) body.getBodyElement(0, new StringList()), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressResponseErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ActivityTestServiceInfo._PROGRESS_OP_NUMBER:
            progressResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

}
