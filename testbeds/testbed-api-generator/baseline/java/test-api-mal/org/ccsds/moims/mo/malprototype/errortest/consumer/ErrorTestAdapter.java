package org.ccsds.moims.mo.malprototype.errortest.consumer;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo;

/**
 * Consumer adapter for ErrorTest service.
 */
public abstract class ErrorTestAdapter extends MALInteractionAdapter {

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDeliveryFailed.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDeliveryFailedResponseReceived(MALMessageHeader msgHeader,
            Element output,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDeliveryFailed.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDeliveryFailedErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDeliveryTimedout.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDeliveryTimedoutResponseReceived(MALMessageHeader msgHeader,
            Element output,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDeliveryTimedout.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDeliveryTimedoutErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDeliveryDelayed.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDeliveryDelayedResponseReceived(MALMessageHeader msgHeader,
            Element output,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDeliveryDelayed.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDeliveryDelayedErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDestinationUnknown.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDestinationUnknownResponseReceived(MALMessageHeader msgHeader,
            Element output,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDestinationUnknown.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDestinationUnknownErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDestinationTransient.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDestinationTransientResponseReceived(MALMessageHeader msgHeader,
            Element output,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDestinationTransient.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDestinationTransientErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDestinationLost.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDestinationLostResponseReceived(MALMessageHeader msgHeader,
            Element output,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDestinationLost.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDestinationLostErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testEncryptionFail.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testEncryptionFailResponseReceived(MALMessageHeader msgHeader,
            Element output,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testEncryptionFail.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testEncryptionFailErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testUnsupportedArea.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testUnsupportedAreaResponseReceived(MALMessageHeader msgHeader,
            Element output,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testUnsupportedArea.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testUnsupportedAreaErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testUnsupportedOperation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testUnsupportedOperationResponseReceived(MALMessageHeader msgHeader,
            Element output,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testUnsupportedOperation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testUnsupportedOperationErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testUnsupportedAreaVersion.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testUnsupportedAreaVersionResponseReceived(MALMessageHeader msgHeader,
            Element output,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testUnsupportedAreaVersion.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testUnsupportedAreaVersionErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testBadEncoding.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testBadEncodingResponseReceived(MALMessageHeader msgHeader,
            Element output,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testBadEncoding.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testBadEncodingErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testUnknown.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testUnknownResponseReceived(MALMessageHeader msgHeader,
            Element output,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testUnknown.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testUnknownErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testAuthenticationFailure.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testAuthenticationFailureResponseReceived(MALMessageHeader msgHeader,
            Element output,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testAuthenticationFailure.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testAuthenticationFailureErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testAuthorizationFailure.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testAuthorizationFailureResponseReceived(MALMessageHeader msgHeader,
            Element output,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testAuthorizationFailure.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testAuthorizationFailureErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testUnsupportedService.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testUnsupportedServiceResponseReceived(MALMessageHeader msgHeader,
            Element output,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testUnsupportedService.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testUnsupportedServiceErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void requestResponseReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ErrorTestServiceInfo._TESTDELIVERYFAILED_OP_NUMBER:
            testDeliveryFailedResponseReceived(msgHeader,
                (Element) body.getBodyElement(0, null), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTDELIVERYTIMEDOUT_OP_NUMBER:
            testDeliveryTimedoutResponseReceived(msgHeader,
                (Element) body.getBodyElement(0, null), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTDELIVERYDELAYED_OP_NUMBER:
            testDeliveryDelayedResponseReceived(msgHeader,
                (Element) body.getBodyElement(0, null), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTDESTINATIONUNKNOWN_OP_NUMBER:
            testDestinationUnknownResponseReceived(msgHeader,
                (Element) body.getBodyElement(0, null), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTDESTINATIONTRANSIENT_OP_NUMBER:
            testDestinationTransientResponseReceived(msgHeader,
                (Element) body.getBodyElement(0, null), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTDESTINATIONLOST_OP_NUMBER:
            testDestinationLostResponseReceived(msgHeader,
                (Element) body.getBodyElement(0, null), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTENCRYPTIONFAIL_OP_NUMBER:
            testEncryptionFailResponseReceived(msgHeader,
                (Element) body.getBodyElement(0, null), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTUNSUPPORTEDAREA_OP_NUMBER:
            testUnsupportedAreaResponseReceived(msgHeader,
                (Element) body.getBodyElement(0, null), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTUNSUPPORTEDOPERATION_OP_NUMBER:
            testUnsupportedOperationResponseReceived(msgHeader,
                (Element) body.getBodyElement(0, null), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTUNSUPPORTEDAREAVERSION_OP_NUMBER:
            testUnsupportedAreaVersionResponseReceived(msgHeader,
                (Element) body.getBodyElement(0, null), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTBADENCODING_OP_NUMBER:
            testBadEncodingResponseReceived(msgHeader,
                (Element) body.getBodyElement(0, null), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTUNKNOWN_OP_NUMBER:
            testUnknownResponseReceived(msgHeader,
                (Element) body.getBodyElement(0, null), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTAUTHENTICATIONFAILURE_OP_NUMBER:
            testAuthenticationFailureResponseReceived(msgHeader,
                (Element) body.getBodyElement(0, null), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTAUTHORIZATIONFAILURE_OP_NUMBER:
            testAuthorizationFailureResponseReceived(msgHeader,
                (Element) body.getBodyElement(0, null), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTUNSUPPORTEDSERVICE_OP_NUMBER:
            testUnsupportedServiceResponseReceived(msgHeader,
                (Element) body.getBodyElement(0, null), qosProperties);
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
          case ErrorTestServiceInfo._TESTDELIVERYFAILED_OP_NUMBER:
            testDeliveryFailedErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTDELIVERYTIMEDOUT_OP_NUMBER:
            testDeliveryTimedoutErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTDELIVERYDELAYED_OP_NUMBER:
            testDeliveryDelayedErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTDESTINATIONUNKNOWN_OP_NUMBER:
            testDestinationUnknownErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTDESTINATIONTRANSIENT_OP_NUMBER:
            testDestinationTransientErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTDESTINATIONLOST_OP_NUMBER:
            testDestinationLostErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTENCRYPTIONFAIL_OP_NUMBER:
            testEncryptionFailErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTUNSUPPORTEDAREA_OP_NUMBER:
            testUnsupportedAreaErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTUNSUPPORTEDOPERATION_OP_NUMBER:
            testUnsupportedOperationErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTUNSUPPORTEDAREAVERSION_OP_NUMBER:
            testUnsupportedAreaVersionErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTBADENCODING_OP_NUMBER:
            testBadEncodingErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTUNKNOWN_OP_NUMBER:
            testUnknownErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTAUTHENTICATIONFAILURE_OP_NUMBER:
            testAuthenticationFailureErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTAUTHORIZATIONFAILURE_OP_NUMBER:
            testAuthorizationFailureErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ErrorTestServiceInfo._TESTUNSUPPORTEDSERVICE_OP_NUMBER:
            testUnsupportedServiceErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

}
