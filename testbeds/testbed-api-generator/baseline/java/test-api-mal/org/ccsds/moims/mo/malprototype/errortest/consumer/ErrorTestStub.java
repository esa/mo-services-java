package org.ccsds.moims.mo.malprototype.errortest.consumer;

/**
 * Consumer stub for ErrorTest service.
 */
public class ErrorTestStub {

    /**
     * The consumer field.
     */
    private final org.ccsds.moims.mo.mal.consumer.MALConsumer consumer;

    /**
     * Wraps a MALconsumer connection with service specific methods that map from
     * the high level service API to the generic MAL API.
     * 
     * @param consumer consumer The MALConsumer to use in this stub.
     */
    public ErrorTestStub(org.ccsds.moims.mo.mal.consumer.MALConsumer consumer) {
        this.consumer = consumer;
    }

    /**
     * Returns the internal MAL consumer object used for sending of messages from
     * this interface.
     * 
     * @return The MAL consumer object.
     */
    public org.ccsds.moims.mo.mal.consumer.MALConsumer getConsumer() {
        return consumer;
    }

    /**
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.structures.Element testDeliveryFailed(org.ccsds.moims.mo.mal.structures.Element input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTDELIVERYFAILED_OP, input);
        Object body0 = (Object) body.getBodyElement(0, null);
        return (org.ccsds.moims.mo.mal.structures.Element) body0;
    }

    /**
     * Asynchronous version of method testDeliveryFailed.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDeliveryFailed(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTDELIVERYFAILED_OP, adapter, input);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueTestDeliveryFailed(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTDELIVERYFAILED_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.structures.Element testDeliveryTimedout(org.ccsds.moims.mo.mal.structures.Element input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTDELIVERYTIMEDOUT_OP, input);
        Object body0 = (Object) body.getBodyElement(0, null);
        return (org.ccsds.moims.mo.mal.structures.Element) body0;
    }

    /**
     * Asynchronous version of method testDeliveryTimedout.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDeliveryTimedout(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTDELIVERYTIMEDOUT_OP, adapter, input);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueTestDeliveryTimedout(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTDELIVERYTIMEDOUT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.structures.Element testDeliveryDelayed(org.ccsds.moims.mo.mal.structures.Element input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTDELIVERYDELAYED_OP, input);
        Object body0 = (Object) body.getBodyElement(0, null);
        return (org.ccsds.moims.mo.mal.structures.Element) body0;
    }

    /**
     * Asynchronous version of method testDeliveryDelayed.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDeliveryDelayed(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTDELIVERYDELAYED_OP, adapter, input);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueTestDeliveryDelayed(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTDELIVERYDELAYED_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.structures.Element testDestinationUnknown(org.ccsds.moims.mo.mal.structures.Element input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTDESTINATIONUNKNOWN_OP, input);
        Object body0 = (Object) body.getBodyElement(0, null);
        return (org.ccsds.moims.mo.mal.structures.Element) body0;
    }

    /**
     * Asynchronous version of method testDestinationUnknown.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDestinationUnknown(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTDESTINATIONUNKNOWN_OP, adapter, input);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueTestDestinationUnknown(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTDESTINATIONUNKNOWN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.structures.Element testDestinationTransient(org.ccsds.moims.mo.mal.structures.Element input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTDESTINATIONTRANSIENT_OP, input);
        Object body0 = (Object) body.getBodyElement(0, null);
        return (org.ccsds.moims.mo.mal.structures.Element) body0;
    }

    /**
     * Asynchronous version of method testDestinationTransient.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDestinationTransient(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTDESTINATIONTRANSIENT_OP, adapter, input);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueTestDestinationTransient(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTDESTINATIONTRANSIENT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.structures.Element testDestinationLost(org.ccsds.moims.mo.mal.structures.Element input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTDESTINATIONLOST_OP, input);
        Object body0 = (Object) body.getBodyElement(0, null);
        return (org.ccsds.moims.mo.mal.structures.Element) body0;
    }

    /**
     * Asynchronous version of method testDestinationLost.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDestinationLost(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTDESTINATIONLOST_OP, adapter, input);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueTestDestinationLost(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTDESTINATIONLOST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.structures.Element testEncryptionFail(org.ccsds.moims.mo.mal.structures.Element input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTENCRYPTIONFAIL_OP, input);
        Object body0 = (Object) body.getBodyElement(0, null);
        return (org.ccsds.moims.mo.mal.structures.Element) body0;
    }

    /**
     * Asynchronous version of method testEncryptionFail.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestEncryptionFail(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTENCRYPTIONFAIL_OP, adapter, input);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueTestEncryptionFail(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTENCRYPTIONFAIL_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.structures.Element testUnsupportedArea(org.ccsds.moims.mo.mal.structures.Element input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTUNSUPPORTEDAREA_OP, input);
        Object body0 = (Object) body.getBodyElement(0, null);
        return (org.ccsds.moims.mo.mal.structures.Element) body0;
    }

    /**
     * Asynchronous version of method testUnsupportedArea.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestUnsupportedArea(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTUNSUPPORTEDAREA_OP, adapter, input);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueTestUnsupportedArea(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTUNSUPPORTEDAREA_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.structures.Element testUnsupportedOperation(org.ccsds.moims.mo.mal.structures.Element input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTUNSUPPORTEDOPERATION_OP, input);
        Object body0 = (Object) body.getBodyElement(0, null);
        return (org.ccsds.moims.mo.mal.structures.Element) body0;
    }

    /**
     * Asynchronous version of method testUnsupportedOperation.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestUnsupportedOperation(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTUNSUPPORTEDOPERATION_OP, adapter, input);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueTestUnsupportedOperation(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTUNSUPPORTEDOPERATION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.structures.Element testUnsupportedAreaVersion(org.ccsds.moims.mo.mal.structures.Element input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTUNSUPPORTEDAREAVERSION_OP, input);
        Object body0 = (Object) body.getBodyElement(0, null);
        return (org.ccsds.moims.mo.mal.structures.Element) body0;
    }

    /**
     * Asynchronous version of method testUnsupportedAreaVersion.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestUnsupportedAreaVersion(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTUNSUPPORTEDAREAVERSION_OP, adapter, input);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueTestUnsupportedAreaVersion(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTUNSUPPORTEDAREAVERSION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.structures.Element testBadEncoding(org.ccsds.moims.mo.mal.structures.Element input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTBADENCODING_OP, input);
        Object body0 = (Object) body.getBodyElement(0, null);
        return (org.ccsds.moims.mo.mal.structures.Element) body0;
    }

    /**
     * Asynchronous version of method testBadEncoding.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestBadEncoding(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTBADENCODING_OP, adapter, input);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueTestBadEncoding(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTBADENCODING_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.structures.Element testUnknown(org.ccsds.moims.mo.mal.structures.Element input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTUNKNOWN_OP, input);
        Object body0 = (Object) body.getBodyElement(0, null);
        return (org.ccsds.moims.mo.mal.structures.Element) body0;
    }

    /**
     * Asynchronous version of method testUnknown.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestUnknown(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTUNKNOWN_OP, adapter, input);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueTestUnknown(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTUNKNOWN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation does nothing. Actually the error is raised by the MAL layer
     * before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.structures.Element testAuthenticationFailure(org.ccsds.moims.mo.mal.structures.Element input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTAUTHENTICATIONFAILURE_OP, input);
        Object body0 = (Object) body.getBodyElement(0, null);
        return (org.ccsds.moims.mo.mal.structures.Element) body0;
    }

    /**
     * Asynchronous version of method testAuthenticationFailure.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestAuthenticationFailure(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTAUTHENTICATIONFAILURE_OP, adapter, input);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueTestAuthenticationFailure(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTAUTHENTICATIONFAILURE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation does nothing. Actually the error is raised by the MAL layer
     * before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.structures.Element testAuthorizationFailure(org.ccsds.moims.mo.mal.structures.Element input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTAUTHORIZATIONFAILURE_OP, input);
        Object body0 = (Object) body.getBodyElement(0, null);
        return (org.ccsds.moims.mo.mal.structures.Element) body0;
    }

    /**
     * Asynchronous version of method testAuthorizationFailure.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestAuthorizationFailure(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTAUTHORIZATIONFAILURE_OP, adapter, input);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueTestAuthorizationFailure(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTAUTHORIZATIONFAILURE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.structures.Element testUnsupportedService(org.ccsds.moims.mo.mal.structures.Element input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTUNSUPPORTEDSERVICE_OP, input);
        Object body0 = (Object) body.getBodyElement(0, null);
        return (org.ccsds.moims.mo.mal.structures.Element) body0;
    }

    /**
     * Asynchronous version of method testUnsupportedService.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestUnsupportedService(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTUNSUPPORTEDSERVICE_OP, adapter, input);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueTestUnsupportedService(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo.TESTUNSUPPORTEDSERVICE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

}
