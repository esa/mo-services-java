package org.ccsds.moims.mo.malprototype.iptest.consumer;

/**
 * Consumer stub for IPTest service.
 */
public class IPTestStub {

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
    public IPTestStub(org.ccsds.moims.mo.mal.consumer.MALConsumer consumer) {
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
     * This operation cleans the assertions table and check that the header of
     * the received message is the same as the one expected (see 4.1.1).
     * 
     * @param input The input field.
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage send(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.send(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.SEND_OP, input);
    }

    /**
     * This operation cleans the assertions table and checks that the header of
     * the received message is the same as the one expected (see 4.1.1). Moreover
     * it triggers the transitions specified by the IPTestDefinition (see 3.1.1.1).
     * 
     * @param input The input field.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void testSubmit(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.TESTSUBMIT_OP, input);
    }

    /**
     * Asynchronous version of method testSubmit.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestSubmit(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.TESTSUBMIT_OP, adapter, input);
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
    public void continueTestSubmit(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.TESTSUBMIT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation cleans the assertions table and checks that the header of
     * the received message is the same as the one expected (see 4.1.1). Moreover
     * it triggers the transitions specified by the IPTestDefinition (see 3.1.1.1).
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public String request(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.REQUEST_OP, input);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(""));
        return (body0 == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body0).getStringValue();
    }

    /**
     * Asynchronous version of method request.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncRequest(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.REQUEST_OP, adapter, input);
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
    public void continueRequest(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.REQUEST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation cleans the assertions table and checks that the header of
     * the received message is the same as the one expected (see 4.1.1). Moreover
     * it triggers the transitions specified by the IPTestDefinition (see 3.1.1.1).
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return The acknowledge value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public String invoke(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.invoke(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.INVOKE_OP, adapter, input);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(""));
        return (body0 == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body0).getStringValue();
    }

    /**
     * Asynchronous version of method invoke.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncInvoke(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncInvoke(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.INVOKE_OP, adapter, input);
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
    public void continueInvoke(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.INVOKE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation cleans the assertions table and checks that the header of
     * the received message is the same as the one expected (see 4.1.1). Moreover
     * it triggers the transitions specified by the IPTestDefinition (see 3.1.1.1).
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return The acknowledge value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public String progress(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.progress(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.PROGRESS_OP, adapter, input);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(""));
        return (body0 == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body0).getStringValue();
    }

    /**
     * Asynchronous version of method progress.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncProgress(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncProgress(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.PROGRESS_OP, adapter, input);
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
    public void continueProgress(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.PROGRESS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * Register method for the monitor PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void monitorRegister(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.register(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.MONITOR_OP, subscription, adapter);
    }

    /**
     * Asynchronous version of method monitorRegister.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncMonitorRegister(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRegister(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.MONITOR_OP, subscription, adapter);
    }

    /**
     * Deregister method for the monitor PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void monitorDeregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.deregister(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.MONITOR_OP, identifierList);
    }

    /**
     * Asynchronous version of method monitorDeregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncMonitorDeregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncDeregister(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.MONITOR_OP, identifierList, adapter);
    }

    /**
     * This operation returns an IPTestResult.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.malprototype.structures.IPTestResult getResult(org.ccsds.moims.mo.mal.structures.Element input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.GETRESULT_OP, input);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.IPTestResult());
        return (org.ccsds.moims.mo.malprototype.structures.IPTestResult) body0;
    }

    /**
     * Asynchronous version of method getResult.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncGetResult(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.GETRESULT_OP, adapter, input);
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
    public void continueGetResult(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.GETRESULT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation cleans the assertions table, publishes an update as specified
     * by the parameter TestPublishUpdate and checks the header of the Publish
     * message (see 4.1.3). Moreover if an error is expected by the TestPublishUpdate
     * then the operation hangs until a Publish error is raised or a timer ends
     * (see 4.1.10). The header of the Publish error message is checked (see 4.1.4).
     * 
     * @param input The input field.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void publishUpdates(org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.PUBLISHUPDATES_OP, input);
    }

    /**
     * Asynchronous version of method publishUpdates.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncPublishUpdates(org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate input,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.PUBLISHUPDATES_OP, adapter, input);
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
    public void continuePublishUpdates(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.PUBLISHUPDATES_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation cleans the assertions table, registers a publisher as specified
     * by the parameter TestPublishRegister and checks the header of the Publish
     * Register message (see 4.1.5). Moreover if no error is expected by the TestPublishRegister,
     * it checks the header of the Publish Register acknowledgement message (see
     * 4.1.6). Otherwise it checks the header of the Publish Register error message
     * (see 4.1.7).
     * 
     * @param input The input field.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void publishRegister(org.ccsds.moims.mo.malprototype.structures.TestPublishRegister input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.PUBLISHREGISTER_OP, input);
    }

    /**
     * Asynchronous version of method publishRegister.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncPublishRegister(org.ccsds.moims.mo.malprototype.structures.TestPublishRegister input,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.PUBLISHREGISTER_OP, adapter, input);
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
    public void continuePublishRegister(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.PUBLISHREGISTER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation cleans the assertions table, registers a publisher as specified
     * by the parameter TestPublishDeregister and checks the header of the Publish
     * Deregister message (see 4.1.5). Moreover if no error is expected by the
     * TestPublishDeregister, it checks the header of the Publish Deregister acknowledgement
     * message (see 4.1.8). Otherwise it checks the header of the Publish Deregister
     * error message (see 4.1.9).
     * 
     * @param input The input field.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void publishDeregister(org.ccsds.moims.mo.malprototype.structures.TestPublishDeregister input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.PUBLISHDEREGISTER_OP, input);
    }

    /**
     * Asynchronous version of method publishDeregister.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncPublishDeregister(org.ccsds.moims.mo.malprototype.structures.TestPublishDeregister input,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.PUBLISHDEREGISTER_OP, adapter, input);
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
    public void continuePublishDeregister(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.PUBLISHDEREGISTER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * 
     * @param input The input field.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void testMultipleNotify(org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.TESTMULTIPLENOTIFY_OP, input);
    }

    /**
     * Asynchronous version of method testMultipleNotify.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestMultipleNotify(org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate input,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.TESTMULTIPLENOTIFY_OP, adapter, input);
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
    public void continueTestMultipleNotify(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.TESTMULTIPLENOTIFY_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation duplicates the send operation with an additional IN parameter.
     * 
     * @param input1 The input1 field.
     * @param input2 The input2 field.
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage sendMulti(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input1,
            org.ccsds.moims.mo.mal.structures.Element input2) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.send(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.SENDMULTI_OP, input1, input2);
    }

    /**
     * This operation duplicates the testSubmit operation with an additional IN
     * parameter.
     * 
     * @param input1 The input1 field.
     * @param input2 The input2 field.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void submitMulti(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input1,
            org.ccsds.moims.mo.mal.structures.Element input2) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.SUBMITMULTI_OP, input1, input2);
    }

    /**
     * Asynchronous version of method submitMulti.
     * 
     * @param input1 The input1 field.
     * @param input2 The input2 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncSubmitMulti(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input1,
            org.ccsds.moims.mo.mal.structures.Element input2,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.SUBMITMULTI_OP, adapter, input1, input2);
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
    public void continueSubmitMulti(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.SUBMITMULTI_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation duplicates the request operation with an additional IN parameter.
     * 
     * @param input1 The input1 field.
     * @param input2 The input2 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.malprototype.iptest.body.RequestMultiResponse requestMulti(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input1,
            org.ccsds.moims.mo.mal.structures.Element input2) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.REQUESTMULTI_OP, input1, input2);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(""));
        Object body1 = (Object) body.getBodyElement(1, null);
        return new org.ccsds.moims.mo.malprototype.iptest.body.RequestMultiResponse((body0 == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body0).getStringValue(), (org.ccsds.moims.mo.mal.structures.Element) body1);
    }

    /**
     * Asynchronous version of method requestMulti.
     * 
     * @param input1 The input1 field.
     * @param input2 The input2 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncRequestMulti(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input1,
            org.ccsds.moims.mo.mal.structures.Element input2,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.REQUESTMULTI_OP, adapter, input1, input2);
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
    public void continueRequestMulti(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.REQUESTMULTI_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation cleans the assertions table and checks that the header of
     * the received message is the same as the one expected (see 4.1.1). Moreover
     * it triggers the transitions specified by the IPTestDefinition (see 4.1.2).
     * 
     * @param input1 The input1 field.
     * @param input2 The input2 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return The acknowledge value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.malprototype.iptest.body.InvokeMultiAck invokeMulti(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input1,
            org.ccsds.moims.mo.mal.structures.Element input2,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.invoke(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.INVOKEMULTI_OP, adapter, input1, input2);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(""));
        Object body1 = (Object) body.getBodyElement(1, null);
        return new org.ccsds.moims.mo.malprototype.iptest.body.InvokeMultiAck((body0 == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body0).getStringValue(), (org.ccsds.moims.mo.mal.structures.Element) body1);
    }

    /**
     * Asynchronous version of method invokeMulti.
     * 
     * @param input1 The input1 field.
     * @param input2 The input2 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncInvokeMulti(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input1,
            org.ccsds.moims.mo.mal.structures.Element input2,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncInvoke(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.INVOKEMULTI_OP, adapter, input1, input2);
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
    public void continueInvokeMulti(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.INVOKEMULTI_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation duplicates the progress operation with an additional IN
     * parameter.
     * 
     * @param input1 The input1 field.
     * @param input2 The input2 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return The acknowledge value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.malprototype.iptest.body.ProgressMultiAck progressMulti(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input1,
            org.ccsds.moims.mo.mal.structures.Element input2,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.progress(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.PROGRESSMULTI_OP, adapter, input1, input2);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(""));
        Object body1 = (Object) body.getBodyElement(1, null);
        return new org.ccsds.moims.mo.malprototype.iptest.body.ProgressMultiAck((body0 == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body0).getStringValue(), (org.ccsds.moims.mo.mal.structures.Element) body1);
    }

    /**
     * Asynchronous version of method progressMulti.
     * 
     * @param input1 The input1 field.
     * @param input2 The input2 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncProgressMulti(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input1,
            org.ccsds.moims.mo.mal.structures.Element input2,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncProgress(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.PROGRESSMULTI_OP, adapter, input1, input2);
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
    public void continueProgressMulti(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.PROGRESSMULTI_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * Register method for the monitorMulti PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void monitorMultiRegister(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.register(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.MONITORMULTI_OP, subscription, adapter);
    }

    /**
     * Asynchronous version of method monitorMultiRegister.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncMonitorMultiRegister(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRegister(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.MONITORMULTI_OP, subscription, adapter);
    }

    /**
     * Deregister method for the monitorMulti PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void monitorMultiDeregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.deregister(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.MONITORMULTI_OP, identifierList);
    }

    /**
     * Asynchronous version of method monitorMultiDeregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncMonitorMultiDeregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncDeregister(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.MONITORMULTI_OP, identifierList, adapter);
    }

    /**
     * This operation checks that an empty body can be sent and received explicitly
     * for a request pattern.
     * 
     * @param input1 The input1 field.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void testRequestEmptyBody(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input1) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.request(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.TESTREQUESTEMPTYBODY_OP, input1);
    }

    /**
     * Asynchronous version of method testRequestEmptyBody.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestRequestEmptyBody(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input1,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.TESTREQUESTEMPTYBODY_OP, adapter, input1);
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
    public void continueTestRequestEmptyBody(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.TESTREQUESTEMPTYBODY_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation checks that an empty body can be sent and received explicitly
     * for an Invoke pattern.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void testInvokeEmptyBody(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input1,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.invoke(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.TESTINVOKEEMPTYBODY_OP, adapter, input1);
    }

    /**
     * Asynchronous version of method testInvokeEmptyBody.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestInvokeEmptyBody(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input1,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncInvoke(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.TESTINVOKEEMPTYBODY_OP, adapter, input1);
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
    public void continueTestInvokeEmptyBody(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.TESTINVOKEEMPTYBODY_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * This operation checks that an empty body can be sent and received explicitly
     * for a Progress pattern.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void testProgressEmptyBody(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input1,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.progress(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.TESTPROGRESSEMPTYBODY_OP, adapter, input1);
    }

    /**
     * Asynchronous version of method testProgressEmptyBody.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestProgressEmptyBody(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input1,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncProgress(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.TESTPROGRESSEMPTYBODY_OP, adapter, input1);
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
    public void continueTestProgressEmptyBody(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.TESTPROGRESSEMPTYBODY_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

}
