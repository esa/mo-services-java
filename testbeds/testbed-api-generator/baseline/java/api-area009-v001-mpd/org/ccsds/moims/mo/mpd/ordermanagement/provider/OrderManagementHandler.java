package org.ccsds.moims.mo.mpd.ordermanagement.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mpd.InvalidException;
import org.ccsds.moims.mo.mpd.OrderFailedException;
import org.ccsds.moims.mo.mpd.UnknownException;
import org.ccsds.moims.mo.mpd.structures.StandingOrder;
import org.ccsds.moims.mo.mpd.structures.StandingOrderList;

/**
 * Interface that providers of the OrderManagement service must implement
 * to handle the operations of that service.
 */
public interface OrderManagementHandler {

    /**
     * Implements the operation listStandingOrders.
     * 
     * @param user The user of the standing order(s) to be listed.
     * @param domain The domain of the standing order(s) to be listed.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws MALException if there is an implementation exception
     */
    StandingOrderList listStandingOrders(Identifier user,
            IdentifierList domain,
            MALInteraction interaction) throws MALException;
    /**
     * Implements the operation submitStandingOrder.
     * 
     * @param orderDetails The details of the order to be submitted for processing.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws InvalidException When a field in the message contains an invalid value.  When the delivery method is selected as FILETRANFER and the delivery URI is set to NULL.  When the delivery method is not selected as FILETRANFER and the delivery URI is not set to NULL.
     * @throws OrderFailedException When the selected URI contains an unsupported scheme/protocol.
     * @throws MALException if there is an implementation exception
     */
    Long submitStandingOrder(StandingOrder orderDetails,
            MALInteraction interaction) throws InvalidException, OrderFailedException, MALException;
    /**
     * Implements the operation cancelStandingOrder.
     * 
     * @param orderID The unique id of the standing order to be cancelled.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws UnknownException When the referenced orderID does not exist.
     * @throws MALException if there is an implementation exception
     */
    void cancelStandingOrder(Long orderID,
            MALInteraction interaction) throws UnknownException, MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(OrderManagementSkeleton skeleton);
}
