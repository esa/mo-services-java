package org.ccsds.moims.mo.mpd.productorderdelivery.consumer;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALNotifyBody;
import org.ccsds.moims.mo.mpd.MPDHelper;
import org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryServiceInfo;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductMetadata;

/**
 * Consumer adapter for ProductOrderDelivery service.
 */
public abstract class ProductOrderDeliveryAdapter extends MALInteractionAdapter {

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation notifyProductDelivery.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void notifyProductDeliveryRegisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is received
     * from a broker for the operation notifyProductDelivery.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void notifyProductDeliveryRegisterErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation notifyProductDelivery.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void notifyProductDeliveryDeregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
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
    public void notifyProductDeliveryNotifyReceived(MALMessageHeader msgHeader,
            Identifier subscriptionId,
            UpdateHeader updateHeader,
            NotifyProductDeliverySubscriptionKeys keys,
            ProductMetadata metadata,
            String filename,
            URI deliveredTo,
            Boolean success,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation notifyProductDelivery.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void notifyProductDeliveryNotifyErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation deliverProducts.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deliverProductsRegisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is received
     * from a broker for the operation deliverProducts.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deliverProductsRegisterErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation deliverProducts.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deliverProductsDeregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
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
    public void deliverProductsNotifyReceived(MALMessageHeader msgHeader,
            Identifier subscriptionId,
            UpdateHeader updateHeader,
            DeliverProductsSubscriptionKeys keys,
            Product product,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation deliverProducts.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deliverProductsNotifyErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void registerAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ProductOrderDeliveryServiceInfo._NOTIFYPRODUCTDELIVERY_OP_NUMBER:
            notifyProductDeliveryRegisterAckReceived(msgHeader, qosProperties);
            break;
          case ProductOrderDeliveryServiceInfo._DELIVERPRODUCTS_OP_NUMBER:
            deliverProductsRegisterAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void registerErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ProductOrderDeliveryServiceInfo._NOTIFYPRODUCTDELIVERY_OP_NUMBER:
            notifyProductDeliveryRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ProductOrderDeliveryServiceInfo._DELIVERPRODUCTS_OP_NUMBER:
            deliverProductsRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void notifyReceived(MALMessageHeader msgHeader,
            MALNotifyBody body,
            IdentifierList selectedKeys,
            Map qosProperties) throws MALException {
        if ((MPDHelper.MPD_AREA_NUMBER.equals(msgHeader.getServiceArea())) && (ProductOrderDeliveryServiceInfo.PRODUCTORDERDELIVERY_SERVICE_NUMBER.equals(msgHeader.getService()))) {
          switch (msgHeader.getOperation().getValue()) {
            case ProductOrderDeliveryServiceInfo._NOTIFYPRODUCTDELIVERY_OP_NUMBER:
              notifyProductDeliveryNotifyReceived(msgHeader,
                (Identifier) body.getBodyElement(0, new Identifier()),
                (UpdateHeader) body.getBodyElement(1, new UpdateHeader()),
                new NotifyProductDeliverySubscriptionKeys((UpdateHeader) body.getBodyElement(1, new UpdateHeader()), selectedKeys),
                (ProductMetadata) body.getBodyElement(2, new ProductMetadata()),
                (body.getBodyElement(3, new Union("")) == null) ? null : ((Union) body.getBodyElement(3, new Union(""))).getStringValue(),
                (URI) body.getBodyElement(4, new URI()),
                (body.getBodyElement(5, new Union(Boolean.FALSE)) == null) ? null : ((Union) body.getBodyElement(5, new Union(Boolean.FALSE))).getBooleanValue(), qosProperties);
              break;
            case ProductOrderDeliveryServiceInfo._DELIVERPRODUCTS_OP_NUMBER:
              deliverProductsNotifyReceived(msgHeader,
                (Identifier) body.getBodyElement(0, new Identifier()),
                (UpdateHeader) body.getBodyElement(1, new UpdateHeader()),
                new DeliverProductsSubscriptionKeys((UpdateHeader) body.getBodyElement(1, new UpdateHeader()), selectedKeys),
                (Product) body.getBodyElement(2, new Product()), qosProperties);
              break;
            default:
              throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
          }
        }
        else {
          notifyReceivedFromOtherService(msgHeader, body, qosProperties);
        }
    }

    @Override
    public final void notifyErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ProductOrderDeliveryServiceInfo._NOTIFYPRODUCTDELIVERY_OP_NUMBER:
            notifyProductDeliveryNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ProductOrderDeliveryServiceInfo._DELIVERPRODUCTS_OP_NUMBER:
            deliverProductsNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void deregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ProductOrderDeliveryServiceInfo._NOTIFYPRODUCTDELIVERY_OP_NUMBER:
            notifyProductDeliveryDeregisterAckReceived(msgHeader, qosProperties);
            break;
          case ProductOrderDeliveryServiceInfo._DELIVERPRODUCTS_OP_NUMBER:
            deliverProductsDeregisterAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    /**
     * Called by the MAL when a PubSub update from another service is received
     * from a broker.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param body body The body of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     * @throws MALException if an error is detected processing the message.
     */
    public void notifyReceivedFromOtherService(MALMessageHeader msgHeader,
            MALNotifyBody body,
            Map qosProperties) throws MALException {
    }

}
