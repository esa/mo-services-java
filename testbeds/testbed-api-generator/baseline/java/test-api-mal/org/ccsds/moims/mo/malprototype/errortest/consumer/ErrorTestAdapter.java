package org.ccsds.moims.mo.malprototype.errortest.consumer;

/**
 * Consumer adapter for ErrorTest service.
 */
public abstract class ErrorTestAdapter extends org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter {

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDeliveryFailed.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDeliveryFailedResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Element output,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDeliveryFailed.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDeliveryFailedErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDeliveryTimedout.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDeliveryTimedoutResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Element output,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDeliveryTimedout.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDeliveryTimedoutErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDeliveryDelayed.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDeliveryDelayedResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Element output,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDeliveryDelayed.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDeliveryDelayedErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDestinationUnknown.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDestinationUnknownResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Element output,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDestinationUnknown.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDestinationUnknownErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDestinationTransient.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDestinationTransientResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Element output,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDestinationTransient.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDestinationTransientErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDestinationLost.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDestinationLostResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Element output,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDestinationLost.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDestinationLostErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testEncryptionFail.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testEncryptionFailResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Element output,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testEncryptionFail.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testEncryptionFailErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testUnsupportedArea.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testUnsupportedAreaResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Element output,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testUnsupportedArea.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testUnsupportedAreaErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testUnsupportedOperation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testUnsupportedOperationResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Element output,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testUnsupportedOperation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testUnsupportedOperationErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testUnsupportedAreaVersion.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testUnsupportedAreaVersionResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Element output,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testUnsupportedAreaVersion.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testUnsupportedAreaVersionErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testBadEncoding.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testBadEncodingResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Element output,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testBadEncoding.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testBadEncodingErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testUnknown.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testUnknownResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Element output,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testUnknown.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testUnknownErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testAuthenticationFailure.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testAuthenticationFailureResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Element output,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testAuthenticationFailure.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testAuthenticationFailureErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testAuthorizationFailure.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testAuthorizationFailureResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Element output,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testAuthorizationFailure.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testAuthorizationFailureErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testUnsupportedService.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testUnsupportedServiceResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Element output,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testUnsupportedService.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testUnsupportedServiceErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    @Override
    public final void requestResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTDELIVERYFAILED_OP_NUMBER:
            testDeliveryFailedResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTDELIVERYTIMEDOUT_OP_NUMBER:
            testDeliveryTimedoutResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTDELIVERYDELAYED_OP_NUMBER:
            testDeliveryDelayedResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTDESTINATIONUNKNOWN_OP_NUMBER:
            testDestinationUnknownResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTDESTINATIONTRANSIENT_OP_NUMBER:
            testDestinationTransientResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTDESTINATIONLOST_OP_NUMBER:
            testDestinationLostResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTENCRYPTIONFAIL_OP_NUMBER:
            testEncryptionFailResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTUNSUPPORTEDAREA_OP_NUMBER:
            testUnsupportedAreaResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTUNSUPPORTEDOPERATION_OP_NUMBER:
            testUnsupportedOperationResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTUNSUPPORTEDAREAVERSION_OP_NUMBER:
            testUnsupportedAreaVersionResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTBADENCODING_OP_NUMBER:
            testBadEncodingResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTUNKNOWN_OP_NUMBER:
            testUnknownResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTAUTHENTICATIONFAILURE_OP_NUMBER:
            testAuthenticationFailureResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTAUTHORIZATIONFAILURE_OP_NUMBER:
            testAuthorizationFailureResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTUNSUPPORTEDSERVICE_OP_NUMBER:
            testUnsupportedServiceResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTDELIVERYFAILED_OP_NUMBER:
            testDeliveryFailedErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTDELIVERYTIMEDOUT_OP_NUMBER:
            testDeliveryTimedoutErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTDELIVERYDELAYED_OP_NUMBER:
            testDeliveryDelayedErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTDESTINATIONUNKNOWN_OP_NUMBER:
            testDestinationUnknownErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTDESTINATIONTRANSIENT_OP_NUMBER:
            testDestinationTransientErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTDESTINATIONLOST_OP_NUMBER:
            testDestinationLostErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTENCRYPTIONFAIL_OP_NUMBER:
            testEncryptionFailErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTUNSUPPORTEDAREA_OP_NUMBER:
            testUnsupportedAreaErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTUNSUPPORTEDOPERATION_OP_NUMBER:
            testUnsupportedOperationErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTUNSUPPORTEDAREAVERSION_OP_NUMBER:
            testUnsupportedAreaVersionErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTBADENCODING_OP_NUMBER:
            testBadEncodingErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTUNKNOWN_OP_NUMBER:
            testUnknownErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTAUTHENTICATIONFAILURE_OP_NUMBER:
            testAuthenticationFailureErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTAUTHORIZATIONFAILURE_OP_NUMBER:
            testAuthorizationFailureErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTUNSUPPORTEDSERVICE_OP_NUMBER:
            testUnsupportedServiceErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

}
