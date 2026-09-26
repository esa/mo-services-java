package org.ccsds.moims.mo.comprototype.activityrelaymanagement.consumer;

/**
 * Consumer stub for ActivityRelayManagement service.
 */
public class ActivityRelayManagementStub {

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
    public ActivityRelayManagementStub(org.ccsds.moims.mo.mal.consumer.MALConsumer consumer) {
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
     * Resets all values back to their default value.
     * 
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void resetTest() throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.comprototype.activityrelaymanagement.ActivityRelayManagementServiceInfo.RESETTEST_OP, (Object[]) null);
    }

    /**
     * Asynchronous version of method resetTest.
     * 
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncResetTest(org.ccsds.moims.mo.comprototype.activityrelaymanagement.consumer.ActivityRelayManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.comprototype.activityrelaymanagement.ActivityRelayManagementServiceInfo.RESETTEST_OP, adapter, (Object[]) null);
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
            org.ccsds.moims.mo.comprototype.activityrelaymanagement.consumer.ActivityRelayManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.comprototype.activityrelaymanagement.ActivityRelayManagementServiceInfo.RESETTEST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * Create a new relay node that supports the Activity and ActivityTest services.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void createRelay(String in1,
            String in2) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.comprototype.activityrelaymanagement.ActivityRelayManagementServiceInfo.CREATERELAY_OP, (in1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in1), (in2 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in2));
    }

    /**
     * Asynchronous version of method createRelay.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncCreateRelay(String in1,
            String in2,
            org.ccsds.moims.mo.comprototype.activityrelaymanagement.consumer.ActivityRelayManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.comprototype.activityrelaymanagement.ActivityRelayManagementServiceInfo.CREATERELAY_OP, adapter, (in1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in1), (in2 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(in2));
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
    public void continueCreateRelay(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.comprototype.activityrelaymanagement.consumer.ActivityRelayManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.comprototype.activityrelaymanagement.ActivityRelayManagementServiceInfo.CREATERELAY_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

}
