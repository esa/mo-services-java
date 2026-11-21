package org.ccsds.moims.mo.mpd.ordermanagement.consumer;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mpd.InvalidException;
import org.ccsds.moims.mo.mpd.OrderFailedException;
import org.ccsds.moims.mo.mpd.UnknownException;
import org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementServiceInfo;
import org.ccsds.moims.mo.mpd.structures.StandingOrder;
import org.ccsds.moims.mo.mpd.structures.StandingOrderList;

/**
 * Consumer stub for OrderManagement service.
 */
public class OrderManagementStub {

    /**
     * The consumer field.
     */
    private final MALConsumer consumer;

    /**
     * Wraps a MALconsumer connection with service specific methods that map from
     * the high level service API to the generic MAL API.
     * 
     * @param consumer consumer The MALConsumer to use in this stub.
     */
    public OrderManagementStub(MALConsumer consumer) {
        this.consumer = consumer;
    }

    /**
     * Returns the internal MAL consumer object used for sending of messages from
     * this interface.
     * 
     * @return The MAL consumer object.
     */
    public MALConsumer getConsumer() {
        return consumer;
    }

    /**
     * The listStandingOrders operation lists the existing standing orders on
     * the service provider for a given user and domain.
     * 
     * @param user The user of the standing order(s) to be listed.
     * @param domain The domain of the standing order(s) to be listed.
     * @return The return value of the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public StandingOrderList listStandingOrders(Identifier user,
            IdentifierList domain) throws MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(OrderManagementServiceInfo.LISTSTANDINGORDERS_OP, user, domain);
            Object body0 = (Object) body.getBodyElement(0, new StandingOrderList());
            return (StandingOrderList) body0;
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method listStandingOrders.
     * 
     * @param user The user of the standing order(s) to be listed.
     * @param domain The domain of the standing order(s) to be listed.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncListStandingOrders(Identifier user,
            IdentifierList domain,
            OrderManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(OrderManagementServiceInfo.LISTSTANDINGORDERS_OP, adapter, user, domain);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueListStandingOrders(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            OrderManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(OrderManagementServiceInfo.LISTSTANDINGORDERS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The submitStandingOrder operation creates a new standing order in the provider
     * for delivery of mission data products.
     * 
     * @param orderDetails The details of the order to be submitted for processing.
     * @return The return value of the interaction
     * @throws InvalidException When a field in the message contains an invalid value.  When the delivery method is selected as FILETRANFER and the delivery URI is set to NULL.  When the delivery method is not selected as FILETRANFER and the delivery URI is not set to NULL.
     * @throws OrderFailedException When the selected URI contains an unsupported scheme/protocol.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Long submitStandingOrder(StandingOrder orderDetails) throws InvalidException, OrderFailedException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(OrderManagementServiceInfo.SUBMITSTANDINGORDER_OP, orderDetails);
            Object body0 = (Object) body.getBodyElement(0, new Union(Long.MAX_VALUE));
            return (body0 == null) ? null : ((Union) body0).getLongValue();
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof OrderFailedException) {
                throw (OrderFailedException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method submitStandingOrder.
     * 
     * @param orderDetails The details of the order to be submitted for processing.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncSubmitStandingOrder(StandingOrder orderDetails,
            OrderManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(OrderManagementServiceInfo.SUBMITSTANDINGORDER_OP, adapter, orderDetails);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueSubmitStandingOrder(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            OrderManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(OrderManagementServiceInfo.SUBMITSTANDINGORDER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The cancelStandingOrder operation cancels an existing standing order.
     * 
     * @param orderID The unique id of the standing order to be cancelled.
     * @throws UnknownException When the referenced orderID does not exist.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void cancelStandingOrder(Long orderID) throws UnknownException, MALStandardError, MALException {
        try {
            consumer.submit(OrderManagementServiceInfo.CANCELSTANDINGORDER_OP, (orderID == null) ? null : new Union(orderID));
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method cancelStandingOrder.
     * 
     * @param orderID The unique id of the standing order to be cancelled.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncCancelStandingOrder(Long orderID,
            OrderManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(OrderManagementServiceInfo.CANCELSTANDINGORDER_OP, adapter, (orderID == null) ? null : new Union(orderID));
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueCancelStandingOrder(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            OrderManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(OrderManagementServiceInfo.CANCELSTANDINGORDER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

}
