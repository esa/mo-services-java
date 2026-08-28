package org.ccsds.moims.mo.mpd.productorderdelivery.provider;

/**
 * Interface that providers of the ProductOrderDelivery service must implement
 * to handle the operations of that service.
 */
public interface ProductOrderDeliveryHandler {

    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.mpd.productorderdelivery.provider.ProductOrderDeliverySkeleton skeleton);
}
