package org.ccsds.moims.mo.malprototype2.iptest.consumer;

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
     * Register method for the monitor PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void monitorRegister(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.malprototype2.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.register(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.MONITOR_OP, subscription, adapter);
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
            org.ccsds.moims.mo.malprototype2.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRegister(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.MONITOR_OP, subscription, adapter);
    }

    /**
     * Deregister method for the monitor PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void monitorDeregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.deregister(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.MONITOR_OP, identifierList);
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
            org.ccsds.moims.mo.malprototype2.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncDeregister(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.MONITOR_OP, identifierList, adapter);
    }

    /**
     * Register method for the monitor2 PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void monitor2Register(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.malprototype2.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.register(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.MONITOR2_OP, subscription, adapter);
    }

    /**
     * Asynchronous version of method monitor2Register.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncMonitor2Register(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.malprototype2.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRegister(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.MONITOR2_OP, subscription, adapter);
    }

    /**
     * Deregister method for the monitor2 PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void monitor2Deregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.deregister(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.MONITOR2_OP, identifierList);
    }

    /**
     * Asynchronous version of method monitor2Deregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncMonitor2Deregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList,
            org.ccsds.moims.mo.malprototype2.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncDeregister(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.MONITOR2_OP, identifierList, adapter);
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
        consumer.submit(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.PUBLISHUPDATES_OP, input);
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
            org.ccsds.moims.mo.malprototype2.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.PUBLISHUPDATES_OP, adapter, input);
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
            org.ccsds.moims.mo.malprototype2.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.PUBLISHUPDATES_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
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
        consumer.submit(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.PUBLISHREGISTER_OP, input);
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
            org.ccsds.moims.mo.malprototype2.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.PUBLISHREGISTER_OP, adapter, input);
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
            org.ccsds.moims.mo.malprototype2.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.PUBLISHREGISTER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
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
        consumer.submit(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.PUBLISHDEREGISTER_OP, input);
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
            org.ccsds.moims.mo.malprototype2.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.PUBLISHDEREGISTER_OP, adapter, input);
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
            org.ccsds.moims.mo.malprototype2.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.PUBLISHDEREGISTER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * 
     * @param input The input field.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void testMultipleNotify(org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.TESTMULTIPLENOTIFY_OP, input);
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
            org.ccsds.moims.mo.malprototype2.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.TESTMULTIPLENOTIFY_OP, adapter, input);
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
            org.ccsds.moims.mo.malprototype2.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.TESTMULTIPLENOTIFY_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * 
     * @param input The input field.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void testObjectRefSubmit(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto> input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.TESTOBJECTREFSUBMIT_OP, input);
    }

    /**
     * Asynchronous version of method testObjectRefSubmit.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestObjectRefSubmit(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto> input,
            org.ccsds.moims.mo.malprototype2.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.TESTOBJECTREFSUBMIT_OP, adapter, input);
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
    public void continueTestObjectRefSubmit(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype2.iptest.consumer.IPTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.TESTOBJECTREFSUBMIT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * 
     * @param input The input field.
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage testObjectRefSend(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto> input) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.send(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.TESTOBJECTREFSEND_OP, input);
    }

}
