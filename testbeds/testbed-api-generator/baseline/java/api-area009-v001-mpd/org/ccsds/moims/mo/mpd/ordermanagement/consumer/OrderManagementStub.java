package org.ccsds.moims.mo.mpd.ordermanagement.consumer;

/**
 * Consumer stub for OrderManagement service.
 */
public class OrderManagementStub {

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
    public OrderManagementStub(org.ccsds.moims.mo.mal.consumer.MALConsumer consumer) {
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
     * The listStandingOrders operation lists the existing standing orders on
     * the service provider for a given user and domain.
     * 
     * @param user The user of the standing order(s) to be listed.
     * @param domain The domain of the standing order(s) to be listed.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mpd.structures.StandingOrderList listStandingOrders(org.ccsds.moims.mo.mal.structures.Identifier user,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementServiceInfo.LISTSTANDINGORDERS_OP, user, domain);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mpd.structures.StandingOrderList());
        return (org.ccsds.moims.mo.mpd.structures.StandingOrderList) body0;
    }

    /**
     * Asynchronous version of method listStandingOrders.
     * 
     * @param user The user of the standing order(s) to be listed.
     * @param domain The domain of the standing order(s) to be listed.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncListStandingOrders(org.ccsds.moims.mo.mal.structures.Identifier user,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mpd.ordermanagement.consumer.OrderManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementServiceInfo.LISTSTANDINGORDERS_OP, adapter, user, domain);
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
    public void continueListStandingOrders(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mpd.ordermanagement.consumer.OrderManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementServiceInfo.LISTSTANDINGORDERS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The submitStandingOrder operation creates a new standing order in the provider
     * for delivery of mission data products.
     * 
     * @param orderDetails The details of the order to be submitted for processing.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public Long submitStandingOrder(org.ccsds.moims.mo.mpd.structures.StandingOrder orderDetails) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementServiceInfo.SUBMITSTANDINGORDER_OP, orderDetails);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE));
        return (body0 == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body0).getLongValue();
    }

    /**
     * Asynchronous version of method submitStandingOrder.
     * 
     * @param orderDetails The details of the order to be submitted for processing.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncSubmitStandingOrder(org.ccsds.moims.mo.mpd.structures.StandingOrder orderDetails,
            org.ccsds.moims.mo.mpd.ordermanagement.consumer.OrderManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementServiceInfo.SUBMITSTANDINGORDER_OP, adapter, orderDetails);
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
    public void continueSubmitStandingOrder(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mpd.ordermanagement.consumer.OrderManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementServiceInfo.SUBMITSTANDINGORDER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The cancelStandingOrder operation cancels an existing standing order.
     * 
     * @param orderID The unique id of the standing order to be cancelled.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void cancelStandingOrder(Long orderID) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementServiceInfo.CANCELSTANDINGORDER_OP, (orderID == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(orderID));
    }

    /**
     * Asynchronous version of method cancelStandingOrder.
     * 
     * @param orderID The unique id of the standing order to be cancelled.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncCancelStandingOrder(Long orderID,
            org.ccsds.moims.mo.mpd.ordermanagement.consumer.OrderManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementServiceInfo.CANCELSTANDINGORDER_OP, adapter, (orderID == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(orderID));
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
    public void continueCancelStandingOrder(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mpd.ordermanagement.consumer.OrderManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementServiceInfo.CANCELSTANDINGORDER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

}
