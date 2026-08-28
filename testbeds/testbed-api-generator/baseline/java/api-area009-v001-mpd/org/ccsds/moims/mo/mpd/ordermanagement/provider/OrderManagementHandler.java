package org.ccsds.moims.mo.mpd.ordermanagement.provider;

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
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mpd.structures.StandingOrderList listStandingOrders(org.ccsds.moims.mo.mal.structures.Identifier user,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation submitStandingOrder.
     * 
     * @param orderDetails The details of the order to be submitted for processing.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mpd.InvalidException When a field in the message contains an invalid value.  When the delivery method is selected as FILETRANFER and the delivery URI is set to NULL.  When the delivery method is not selected as FILETRANFER and the delivery URI is not set to NULL.
     * @throws org.ccsds.moims.mo.mpd.OrderFailedException When the selected URI contains an unsupported scheme/protocol.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    Long submitStandingOrder(org.ccsds.moims.mo.mpd.structures.StandingOrder orderDetails,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mpd.InvalidException, org.ccsds.moims.mo.mpd.OrderFailedException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation cancelStandingOrder.
     * 
     * @param orderID The unique id of the standing order to be cancelled.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mpd.UnknownException When the referenced orderID does not exist.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void cancelStandingOrder(Long orderID,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mpd.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.mpd.ordermanagement.provider.OrderManagementSkeleton skeleton);
}
