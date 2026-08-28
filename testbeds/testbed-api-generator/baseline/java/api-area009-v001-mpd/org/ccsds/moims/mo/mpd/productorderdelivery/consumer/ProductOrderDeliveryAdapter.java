package org.ccsds.moims.mo.mpd.productorderdelivery.consumer;

/**
 * Consumer adapter for ProductOrderDelivery service.
 */
public abstract class ProductOrderDeliveryAdapter extends org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter {

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation notifyProductDelivery.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void notifyProductDeliveryRegisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is received
     * from a broker for the operation notifyProductDelivery.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void notifyProductDeliveryRegisterErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation notifyProductDelivery.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void notifyProductDeliveryDeregisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update is received from a broker for the
     * operation notifyProductDelivery.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param subscriptionId The subscriptionId of the subscription.
     * @param updateHeader The Update header.
     * @param keys The typed Subscription Key accessors for this update
     * @param metadata The metadata of the mission data product.
     * @param filename The filename of the mission data product.
     * @param deliveredTo The location's URI where the mission data product was delivered.
     * @param success The status indicating the successful delivery of the mission data product.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void notifyProductDeliveryNotifyReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Identifier subscriptionId,
            org.ccsds.moims.mo.mal.structures.UpdateHeader updateHeader,
            NotifyProductDeliverySubscriptionKeys keys,
            org.ccsds.moims.mo.mpd.structures.ProductMetadata metadata,
            String filename,
            org.ccsds.moims.mo.mal.structures.URI deliveredTo,
            Boolean success,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation notifyProductDelivery.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void notifyProductDeliveryNotifyErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation deliverProducts.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deliverProductsRegisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is received
     * from a broker for the operation deliverProducts.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deliverProductsRegisterErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation deliverProducts.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deliverProductsDeregisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update is received from a broker for the
     * operation deliverProducts.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param subscriptionId The subscriptionId of the subscription.
     * @param updateHeader The Update header.
     * @param keys The typed Subscription Key accessors for this update
     * @param product The mission data product.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deliverProductsNotifyReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Identifier subscriptionId,
            org.ccsds.moims.mo.mal.structures.UpdateHeader updateHeader,
            DeliverProductsSubscriptionKeys keys,
            org.ccsds.moims.mo.mpd.structures.Product product,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation deliverProducts.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deliverProductsNotifyErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    @Override
    public final void registerAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryServiceInfo._NOTIFYPRODUCTDELIVERY_OP_NUMBER:
            notifyProductDeliveryRegisterAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryServiceInfo._DELIVERPRODUCTS_OP_NUMBER:
            deliverProductsRegisterAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void registerErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryServiceInfo._NOTIFYPRODUCTDELIVERY_OP_NUMBER:
            notifyProductDeliveryRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryServiceInfo._DELIVERPRODUCTS_OP_NUMBER:
            deliverProductsRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void notifyReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALNotifyBody body,
            org.ccsds.moims.mo.mal.structures.IdentifierList selectedKeys,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        if ((org.ccsds.moims.mo.mpd.MPDHelper.MPD_AREA_NUMBER.equals(msgHeader.getServiceArea())) && (org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryServiceInfo.PRODUCTORDERDELIVERY_SERVICE_NUMBER.equals(msgHeader.getService()))) {
          switch (msgHeader.getOperation().getValue()) {
            case org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryServiceInfo._NOTIFYPRODUCTDELIVERY_OP_NUMBER:
              notifyProductDeliveryNotifyReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Identifier) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Identifier()),
                (org.ccsds.moims.mo.mal.structures.UpdateHeader) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UpdateHeader()),
                new NotifyProductDeliverySubscriptionKeys((org.ccsds.moims.mo.mal.structures.UpdateHeader) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UpdateHeader()), selectedKeys),
                (org.ccsds.moims.mo.mpd.structures.ProductMetadata) body.getBodyElement(2, new org.ccsds.moims.mo.mpd.structures.ProductMetadata()),
                (body.getBodyElement(3, new org.ccsds.moims.mo.mal.structures.Union("")) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(3, new org.ccsds.moims.mo.mal.structures.Union(""))).getStringValue(),
                (org.ccsds.moims.mo.mal.structures.URI) body.getBodyElement(4, new org.ccsds.moims.mo.mal.structures.URI()),
                (body.getBodyElement(5, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(5, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE))).getBooleanValue(), qosProperties);
              break;
            case org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryServiceInfo._DELIVERPRODUCTS_OP_NUMBER:
              deliverProductsNotifyReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Identifier) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Identifier()),
                (org.ccsds.moims.mo.mal.structures.UpdateHeader) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UpdateHeader()),
                new DeliverProductsSubscriptionKeys((org.ccsds.moims.mo.mal.structures.UpdateHeader) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UpdateHeader()), selectedKeys),
                (org.ccsds.moims.mo.mpd.structures.Product) body.getBodyElement(2, new org.ccsds.moims.mo.mpd.structures.Product()), qosProperties);
              break;
            default:
              throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
          }
        }
        else {
          notifyReceivedFromOtherService(msgHeader, body, qosProperties);
        }
    }

    @Override
    public final void notifyErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryServiceInfo._NOTIFYPRODUCTDELIVERY_OP_NUMBER:
            notifyProductDeliveryNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryServiceInfo._DELIVERPRODUCTS_OP_NUMBER:
            deliverProductsNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void deregisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryServiceInfo._NOTIFYPRODUCTDELIVERY_OP_NUMBER:
            notifyProductDeliveryDeregisterAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryServiceInfo._DELIVERPRODUCTS_OP_NUMBER:
            deliverProductsDeregisterAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    /**
     * Called by the MAL when a PubSub update from another service is received
     * from a broker.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param body body The body of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     * @throws org.ccsds.moims.mo.mal.MALException if an error is detected processing the message.
     */
    public void notifyReceivedFromOtherService(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALNotifyBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
    }

}
