package org.ccsds.moims.mo.comprototype.eventtest.consumer;

/**
 * Consumer stub for EventTest service.
 */
public class EventTestStub {

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
    public EventTestStub(org.ccsds.moims.mo.mal.consumer.MALConsumer consumer) {
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
     * Resets the EventTest service provider.
     * 
     * @param in1 The in1 field.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void resetTest(String in1) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo.RESETTEST_OP, (in1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in1));
    }

    /**
     * Asynchronous version of method resetTest.
     * 
     * @param in1 The in1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncResetTest(String in1,
            org.ccsds.moims.mo.comprototype.eventtest.consumer.EventTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo.RESETTEST_OP, adapter, (in1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in1));
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
    public void continueResetTest(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.comprototype.eventtest.consumer.EventTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo.RESETTEST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * Creates an instance of one of the test objects: TestObject A or Test Object
     * B Arg 1 - ObjectNumber (identifies object to be created) Arg 2 Domain Arg
     * 3 Description Arg 4 parent instanceIdentifier returns object instance identifier.
     * The provider will publish a TestObjectCreation event reporting the deletion.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public Long createinstance(Short in1,
            String in2,
            String in3,
            Long in4) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo.CREATEINSTANCE_OP, (in1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in1), (in2 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in2), (in3 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in3), (in4 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in4));
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE));
        return (body0 == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body0).getLongValue();
    }

    /**
     * Asynchronous version of method createinstance.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncCreateinstance(Short in1,
            String in2,
            String in3,
            Long in4,
            org.ccsds.moims.mo.comprototype.eventtest.consumer.EventTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo.CREATEINSTANCE_OP, adapter, (in1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in1), (in2 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in2), (in3 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in3), (in4 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in4));
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
    public void continueCreateinstance(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.comprototype.eventtest.consumer.EventTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo.CREATEINSTANCE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * deletes a test object instance.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void deleteInstance(Short in1,
            String in2,
            Long in3) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo.DELETEINSTANCE_OP, (in1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in1), (in2 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in2), (in3 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in3));
    }

    /**
     * Asynchronous version of method deleteInstance.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncDeleteInstance(Short in1,
            String in2,
            Long in3,
            org.ccsds.moims.mo.comprototype.eventtest.consumer.EventTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo.DELETEINSTANCE_OP, adapter, (in1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in1), (in2 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in2), (in3 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in3));
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
    public void continueDeleteInstance(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.comprototype.eventtest.consumer.EventTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo.DELETEINSTANCE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * Updates a number of fields on an instance of a test object. The provider
     * will publish a TestObjectUpdate event reporting the updated attributes
     * .
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void updateInstance(Long in1,
            org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnum in2,
            org.ccsds.moims.mo.mal.structures.Duration in3,
            org.ccsds.moims.mo.mal.structures.ShortList in4) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo.UPDATEINSTANCE_OP, (in1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in1), in2, in3, in4);
    }

    /**
     * Asynchronous version of method updateInstance.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncUpdateInstance(Long in1,
            org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnum in2,
            org.ccsds.moims.mo.mal.structures.Duration in3,
            org.ccsds.moims.mo.mal.structures.ShortList in4,
            org.ccsds.moims.mo.comprototype.eventtest.consumer.EventTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo.UPDATEINSTANCE_OP, adapter, (in1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in1), in2, in3, in4);
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
    public void continueUpdateInstance(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.comprototype.eventtest.consumer.EventTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo.UPDATEINSTANCE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * Updates the composite field on a instance of a test object. The provider
     * will publish a TestObjectUpdate event reporting the updated attributes
     * .
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void updateInstanceComposite(Long in1,
            org.ccsds.moims.mo.mal.structures.UOctet in2,
            Byte in3,
            Double in4) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo.UPDATEINSTANCECOMPOSITE_OP, (in1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in1), in2, (in3 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in3), (in4 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in4));
    }

    /**
     * Asynchronous version of method updateInstanceComposite.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncUpdateInstanceComposite(Long in1,
            org.ccsds.moims.mo.mal.structures.UOctet in2,
            Byte in3,
            Double in4,
            org.ccsds.moims.mo.comprototype.eventtest.consumer.EventTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo.UPDATEINSTANCECOMPOSITE_OP, adapter, (in1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in1), in2, (in3 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in3), (in4 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in4));
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
    public void continueUpdateInstanceComposite(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.comprototype.eventtest.consumer.EventTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo.UPDATEINSTANCECOMPOSITE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

}
