package org.ccsds.moims.mo.malprototype.iptest.consumer;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALNotifyBody;
import org.ccsds.moims.mo.malprototype.MALPrototypeHelper;
import org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo;
import org.ccsds.moims.mo.malprototype.structures.IPTestResult;
import org.ccsds.moims.mo.malprototype.structures.TestUpdate;

/**
 * Consumer adapter for IPTest service.
 */
public abstract class IPTestAdapter extends MALInteractionAdapter {

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
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void requestResponseReceived(MALMessageHeader msgHeader,
            String output,
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
     * @param ack The ack field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void invokeAckReceived(MALMessageHeader msgHeader,
            String ack,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE response is received from a provider for
     * the operation invoke.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void invokeResponseReceived(MALMessageHeader msgHeader,
            String output,
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
     * @param ack The ack field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void progressAckReceived(MALMessageHeader msgHeader,
            String ack,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update is received from a provider for
     * the operation progress.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param update The update field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void progressUpdateReceived(MALMessageHeader msgHeader,
            Integer update,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response is received from a provider
     * for the operation progress.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param response The response field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void progressResponseReceived(MALMessageHeader msgHeader,
            String response,
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
     * @param pubField The pubField field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorNotifyReceived(MALMessageHeader msgHeader,
            Identifier subscriptionId,
            UpdateHeader updateHeader,
            MonitorSubscriptionKeys keys,
            TestUpdate pubField,
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
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getResult.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getResultResponseReceived(MALMessageHeader msgHeader,
            IPTestResult output,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getResult.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getResultErrorReceived(MALMessageHeader msgHeader,
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

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation submitMulti.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void submitMultiAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation submitMulti.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void submitMultiErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation requestMulti.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param output2 The output2 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void requestMultiResponseReceived(MALMessageHeader msgHeader,
            String output1,
            Element output2,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation requestMulti.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void requestMultiErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE acknowledgement is received from a provider
     * for the operation invokeMulti.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param ack1 The ack1 field.
     * @param ack2 The ack2 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void invokeMultiAckReceived(MALMessageHeader msgHeader,
            String ack1,
            Element ack2,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE response is received from a provider for
     * the operation invokeMulti.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param output2 The output2 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void invokeMultiResponseReceived(MALMessageHeader msgHeader,
            String output1,
            Element output2,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE acknowledgement error is received from
     * a provider for the operation invokeMulti.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void invokeMultiAckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE response error is received from a provider
     * for the operation invokeMulti.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void invokeMultiResponseErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement is received from a provider
     * for the operation progressMulti.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param ack1 The ack1 field.
     * @param ack2 The ack2 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void progressMultiAckReceived(MALMessageHeader msgHeader,
            String ack1,
            Element ack2,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update is received from a provider for
     * the operation progressMulti.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param output2 The output2 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void progressMultiUpdateReceived(MALMessageHeader msgHeader,
            Integer output1,
            Element output2,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response is received from a provider
     * for the operation progressMulti.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output3 The output3 field.
     * @param output4 The output4 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void progressMultiResponseReceived(MALMessageHeader msgHeader,
            String output3,
            Element output4,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement error is received from
     * a provider for the operation progressMulti.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void progressMultiAckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update error is received from a provider
     * for the operation progressMulti.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void progressMultiUpdateErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response error is received from a provider
     * for the operation progressMulti.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void progressMultiResponseErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation monitorMulti.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorMultiRegisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is received
     * from a broker for the operation monitorMulti.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorMultiRegisterErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation monitorMulti.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorMultiDeregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update is received from a broker for the
     * operation monitorMulti.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param subscriptionId The subscriptionId of the subscription.
     * @param updateHeader The Update header.
     * @param keys The typed Subscription Key accessors for this update
     * @param output1 The output1 field.
     * @param output2 The output2 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorMultiNotifyReceived(MALMessageHeader msgHeader,
            Identifier subscriptionId,
            UpdateHeader updateHeader,
            MonitorMultiSubscriptionKeys keys,
            TestUpdate output1,
            Element output2,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation monitorMulti.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorMultiNotifyErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testRequestEmptyBody.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testRequestEmptyBodyResponseReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testRequestEmptyBody.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testRequestEmptyBodyErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE acknowledgement is received from a provider
     * for the operation testInvokeEmptyBody.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testInvokeEmptyBodyAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE response is received from a provider for
     * the operation testInvokeEmptyBody.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testInvokeEmptyBodyResponseReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE acknowledgement error is received from
     * a provider for the operation testInvokeEmptyBody.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testInvokeEmptyBodyAckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when an INVOKE response error is received from a provider
     * for the operation testInvokeEmptyBody.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testInvokeEmptyBodyResponseErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement is received from a provider
     * for the operation testProgressEmptyBody.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testProgressEmptyBodyAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update is received from a provider for
     * the operation testProgressEmptyBody.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testProgressEmptyBodyUpdateReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response is received from a provider
     * for the operation testProgressEmptyBody.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testProgressEmptyBodyResponseReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement error is received from
     * a provider for the operation testProgressEmptyBody.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testProgressEmptyBodyAckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update error is received from a provider
     * for the operation testProgressEmptyBody.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testProgressEmptyBodyUpdateErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response error is received from a provider
     * for the operation testProgressEmptyBody.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testProgressEmptyBodyResponseErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case IPTestServiceInfo._TESTSUBMIT_OP_NUMBER:
            testSubmitAckReceived(msgHeader, qosProperties);
            break;
          case IPTestServiceInfo._PUBLISHUPDATES_OP_NUMBER:
            publishUpdatesAckReceived(msgHeader, qosProperties);
            break;
          case IPTestServiceInfo._PUBLISHREGISTER_OP_NUMBER:
            publishRegisterAckReceived(msgHeader, qosProperties);
            break;
          case IPTestServiceInfo._PUBLISHDEREGISTER_OP_NUMBER:
            publishDeregisterAckReceived(msgHeader, qosProperties);
            break;
          case IPTestServiceInfo._TESTMULTIPLENOTIFY_OP_NUMBER:
            testMultipleNotifyAckReceived(msgHeader, qosProperties);
            break;
          case IPTestServiceInfo._SUBMITMULTI_OP_NUMBER:
            submitMultiAckReceived(msgHeader, qosProperties);
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
          case IPTestServiceInfo._TESTSUBMIT_OP_NUMBER:
            testSubmitErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case IPTestServiceInfo._PUBLISHUPDATES_OP_NUMBER:
            publishUpdatesErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case IPTestServiceInfo._PUBLISHREGISTER_OP_NUMBER:
            publishRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case IPTestServiceInfo._PUBLISHDEREGISTER_OP_NUMBER:
            publishDeregisterErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case IPTestServiceInfo._TESTMULTIPLENOTIFY_OP_NUMBER:
            testMultipleNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case IPTestServiceInfo._SUBMITMULTI_OP_NUMBER:
            submitMultiErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case IPTestServiceInfo._REQUEST_OP_NUMBER:
            requestResponseReceived(msgHeader,
                (body.getBodyElement(0, new Union("")) == null) ? null : ((Union) body.getBodyElement(0, new Union(""))).getStringValue(), qosProperties);
            break;
          case IPTestServiceInfo._GETRESULT_OP_NUMBER:
            getResultResponseReceived(msgHeader,
                (IPTestResult) body.getBodyElement(0, new IPTestResult()), qosProperties);
            break;
          case IPTestServiceInfo._REQUESTMULTI_OP_NUMBER:
            requestMultiResponseReceived(msgHeader,
                (body.getBodyElement(0, new Union("")) == null) ? null : ((Union) body.getBodyElement(0, new Union(""))).getStringValue(),
                (Element) body.getBodyElement(1, null), qosProperties);
            break;
          case IPTestServiceInfo._TESTREQUESTEMPTYBODY_OP_NUMBER:
            testRequestEmptyBodyResponseReceived(msgHeader, qosProperties);
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
          case IPTestServiceInfo._REQUEST_OP_NUMBER:
            requestErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case IPTestServiceInfo._GETRESULT_OP_NUMBER:
            getResultErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case IPTestServiceInfo._REQUESTMULTI_OP_NUMBER:
            requestMultiErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case IPTestServiceInfo._TESTREQUESTEMPTYBODY_OP_NUMBER:
            testRequestEmptyBodyErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case IPTestServiceInfo._INVOKE_OP_NUMBER:
            invokeAckReceived(msgHeader,
                (body.getBodyElement(0, new Union("")) == null) ? null : ((Union) body.getBodyElement(0, new Union(""))).getStringValue(), qosProperties);
            break;
          case IPTestServiceInfo._INVOKEMULTI_OP_NUMBER:
            invokeMultiAckReceived(msgHeader,
                (body.getBodyElement(0, new Union("")) == null) ? null : ((Union) body.getBodyElement(0, new Union(""))).getStringValue(),
                (Element) body.getBodyElement(1, null), qosProperties);
            break;
          case IPTestServiceInfo._TESTINVOKEEMPTYBODY_OP_NUMBER:
            testInvokeEmptyBodyAckReceived(msgHeader, qosProperties);
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
          case IPTestServiceInfo._INVOKE_OP_NUMBER:
            invokeAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case IPTestServiceInfo._INVOKEMULTI_OP_NUMBER:
            invokeMultiAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case IPTestServiceInfo._TESTINVOKEEMPTYBODY_OP_NUMBER:
            testInvokeEmptyBodyAckErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case IPTestServiceInfo._INVOKE_OP_NUMBER:
            invokeResponseReceived(msgHeader,
                (body.getBodyElement(0, new Union("")) == null) ? null : ((Union) body.getBodyElement(0, new Union(""))).getStringValue(), qosProperties);
            break;
          case IPTestServiceInfo._INVOKEMULTI_OP_NUMBER:
            invokeMultiResponseReceived(msgHeader,
                (body.getBodyElement(0, new Union("")) == null) ? null : ((Union) body.getBodyElement(0, new Union(""))).getStringValue(),
                (Element) body.getBodyElement(1, null), qosProperties);
            break;
          case IPTestServiceInfo._TESTINVOKEEMPTYBODY_OP_NUMBER:
            testInvokeEmptyBodyResponseReceived(msgHeader, qosProperties);
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
          case IPTestServiceInfo._INVOKE_OP_NUMBER:
            invokeResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case IPTestServiceInfo._INVOKEMULTI_OP_NUMBER:
            invokeMultiResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case IPTestServiceInfo._TESTINVOKEEMPTYBODY_OP_NUMBER:
            testInvokeEmptyBodyResponseErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case IPTestServiceInfo._PROGRESS_OP_NUMBER:
            progressAckReceived(msgHeader,
                (body.getBodyElement(0, new Union("")) == null) ? null : ((Union) body.getBodyElement(0, new Union(""))).getStringValue(), qosProperties);
            break;
          case IPTestServiceInfo._PROGRESSMULTI_OP_NUMBER:
            progressMultiAckReceived(msgHeader,
                (body.getBodyElement(0, new Union("")) == null) ? null : ((Union) body.getBodyElement(0, new Union(""))).getStringValue(),
                (Element) body.getBodyElement(1, null), qosProperties);
            break;
          case IPTestServiceInfo._TESTPROGRESSEMPTYBODY_OP_NUMBER:
            testProgressEmptyBodyAckReceived(msgHeader, qosProperties);
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
          case IPTestServiceInfo._PROGRESS_OP_NUMBER:
            progressAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case IPTestServiceInfo._PROGRESSMULTI_OP_NUMBER:
            progressMultiAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case IPTestServiceInfo._TESTPROGRESSEMPTYBODY_OP_NUMBER:
            testProgressEmptyBodyAckErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case IPTestServiceInfo._PROGRESS_OP_NUMBER:
            progressUpdateReceived(msgHeader,
                (body.getBodyElement(0, new Union(Integer.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Integer.MAX_VALUE))).getIntegerValue(), qosProperties);
            break;
          case IPTestServiceInfo._PROGRESSMULTI_OP_NUMBER:
            progressMultiUpdateReceived(msgHeader,
                (body.getBodyElement(0, new Union(Integer.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Integer.MAX_VALUE))).getIntegerValue(),
                (Element) body.getBodyElement(1, null), qosProperties);
            break;
          case IPTestServiceInfo._TESTPROGRESSEMPTYBODY_OP_NUMBER:
            testProgressEmptyBodyUpdateReceived(msgHeader, qosProperties);
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
          case IPTestServiceInfo._PROGRESS_OP_NUMBER:
            progressUpdateErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case IPTestServiceInfo._PROGRESSMULTI_OP_NUMBER:
            progressMultiUpdateErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case IPTestServiceInfo._TESTPROGRESSEMPTYBODY_OP_NUMBER:
            testProgressEmptyBodyUpdateErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case IPTestServiceInfo._PROGRESS_OP_NUMBER:
            progressResponseReceived(msgHeader,
                (body.getBodyElement(0, new Union("")) == null) ? null : ((Union) body.getBodyElement(0, new Union(""))).getStringValue(), qosProperties);
            break;
          case IPTestServiceInfo._PROGRESSMULTI_OP_NUMBER:
            progressMultiResponseReceived(msgHeader,
                (body.getBodyElement(0, new Union("")) == null) ? null : ((Union) body.getBodyElement(0, new Union(""))).getStringValue(),
                (Element) body.getBodyElement(1, null), qosProperties);
            break;
          case IPTestServiceInfo._TESTPROGRESSEMPTYBODY_OP_NUMBER:
            testProgressEmptyBodyResponseReceived(msgHeader, qosProperties);
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
          case IPTestServiceInfo._PROGRESS_OP_NUMBER:
            progressResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case IPTestServiceInfo._PROGRESSMULTI_OP_NUMBER:
            progressMultiResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case IPTestServiceInfo._TESTPROGRESSEMPTYBODY_OP_NUMBER:
            testProgressEmptyBodyResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void registerAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case IPTestServiceInfo._MONITOR_OP_NUMBER:
            monitorRegisterAckReceived(msgHeader, qosProperties);
            break;
          case IPTestServiceInfo._MONITORMULTI_OP_NUMBER:
            monitorMultiRegisterAckReceived(msgHeader, qosProperties);
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
          case IPTestServiceInfo._MONITOR_OP_NUMBER:
            monitorRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case IPTestServiceInfo._MONITORMULTI_OP_NUMBER:
            monitorMultiRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
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
        if ((MALPrototypeHelper.MALPROTOTYPE_AREA_NUMBER.equals(msgHeader.getServiceArea())) && (IPTestServiceInfo.IPTEST_SERVICE_NUMBER.equals(msgHeader.getService()))) {
          switch (msgHeader.getOperation().getValue()) {
            case IPTestServiceInfo._MONITOR_OP_NUMBER:
              monitorNotifyReceived(msgHeader,
                (Identifier) body.getBodyElement(0, new Identifier()),
                (UpdateHeader) body.getBodyElement(1, new UpdateHeader()),
                new MonitorSubscriptionKeys((UpdateHeader) body.getBodyElement(1, new UpdateHeader()), selectedKeys),
                (TestUpdate) body.getBodyElement(2, new TestUpdate()), qosProperties);
              break;
            case IPTestServiceInfo._MONITORMULTI_OP_NUMBER:
              monitorMultiNotifyReceived(msgHeader,
                (Identifier) body.getBodyElement(0, new Identifier()),
                (UpdateHeader) body.getBodyElement(1, new UpdateHeader()),
                new MonitorMultiSubscriptionKeys((UpdateHeader) body.getBodyElement(1, new UpdateHeader()), selectedKeys),
                (TestUpdate) body.getBodyElement(2, new TestUpdate()),
                (Element) body.getBodyElement(3, null), qosProperties);
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
          case IPTestServiceInfo._MONITOR_OP_NUMBER:
            monitorNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case IPTestServiceInfo._MONITORMULTI_OP_NUMBER:
            monitorMultiNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void deregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case IPTestServiceInfo._MONITOR_OP_NUMBER:
            monitorDeregisterAckReceived(msgHeader, qosProperties);
            break;
          case IPTestServiceInfo._MONITORMULTI_OP_NUMBER:
            monitorMultiDeregisterAckReceived(msgHeader, qosProperties);
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
