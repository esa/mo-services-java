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
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mpd.structures.StandingOrderList listStandingOrders(org.ccsds.moims.mo.mal.structures.Identifier user,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementServiceInfo.LISTSTANDINGORDERS_OP, user, domain);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mpd.structures.StandingOrderList());
            return (org.ccsds.moims.mo.mpd.structures.StandingOrderList) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method listStandingOrders.
     * 
     * @param user The user of the standing order(s) to be listed.
     * @param domain The domain of the standing order(s) to be listed.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncListStandingOrders(org.ccsds.moims.mo.mal.structures.Identifier user,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mpd.ordermanagement.consumer.OrderManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementServiceInfo.LISTSTANDINGORDERS_OP, adapter, user, domain);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueListStandingOrders(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mpd.ordermanagement.consumer.OrderManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementServiceInfo.LISTSTANDINGORDERS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The submitStandingOrder operation creates a new standing order in the provider
     * for delivery of mission data products.
     * 
     * @param orderDetails The details of the order to be submitted for processing.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mpd.InvalidException When a field in the message contains an invalid value.  When the delivery method is selected as FILETRANFER and the delivery URI is set to NULL.  When the delivery method is not selected as FILETRANFER and the delivery URI is not set to NULL.
     * @throws org.ccsds.moims.mo.mpd.OrderFailedException When the selected URI contains an unsupported scheme/protocol.
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Long submitStandingOrder(org.ccsds.moims.mo.mpd.structures.StandingOrder orderDetails) throws org.ccsds.moims.mo.mpd.InvalidException, org.ccsds.moims.mo.mpd.OrderFailedException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementServiceInfo.SUBMITSTANDINGORDER_OP, orderDetails);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE));
            return (body0 == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body0).getLongValue();
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.mpd.InvalidException) {
                throw (org.ccsds.moims.mo.mpd.InvalidException) error;
            }
            if (error instanceof org.ccsds.moims.mo.mpd.OrderFailedException) {
                throw (org.ccsds.moims.mo.mpd.OrderFailedException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method submitStandingOrder.
     * 
     * @param orderDetails The details of the order to be submitted for processing.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncSubmitStandingOrder(org.ccsds.moims.mo.mpd.structures.StandingOrder orderDetails,
            org.ccsds.moims.mo.mpd.ordermanagement.consumer.OrderManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementServiceInfo.SUBMITSTANDINGORDER_OP, adapter, orderDetails);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueSubmitStandingOrder(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mpd.ordermanagement.consumer.OrderManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementServiceInfo.SUBMITSTANDINGORDER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The cancelStandingOrder operation cancels an existing standing order.
     * 
     * @param orderID The unique id of the standing order to be cancelled.
     * @throws org.ccsds.moims.mo.mpd.UnknownException When the referenced orderID does not exist.
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void cancelStandingOrder(Long orderID) throws org.ccsds.moims.mo.mpd.UnknownException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.submit(org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementServiceInfo.CANCELSTANDINGORDER_OP, (orderID == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(orderID));
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.mpd.UnknownException) {
                throw (org.ccsds.moims.mo.mpd.UnknownException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method cancelStandingOrder.
     * 
     * @param orderID The unique id of the standing order to be cancelled.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncCancelStandingOrder(Long orderID,
            org.ccsds.moims.mo.mpd.ordermanagement.consumer.OrderManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncSubmit(org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementServiceInfo.CANCELSTANDINGORDER_OP, adapter, (orderID == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(orderID));
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueCancelStandingOrder(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mpd.ordermanagement.consumer.OrderManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementServiceInfo.CANCELSTANDINGORDER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

}
