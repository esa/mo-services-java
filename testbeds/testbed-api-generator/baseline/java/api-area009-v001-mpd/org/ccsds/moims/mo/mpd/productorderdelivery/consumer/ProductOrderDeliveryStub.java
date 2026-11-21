package org.ccsds.moims.mo.mpd.productorderdelivery.consumer;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryServiceInfo;

/**
 * Consumer stub for ProductOrderDelivery service.
 */
public class ProductOrderDeliveryStub {

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
    public ProductOrderDeliveryStub(MALConsumer consumer) {
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
     * Register method for the notifyProductDelivery PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void notifyProductDeliveryRegister(Subscription subscription,
            ProductOrderDeliveryAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.register(ProductOrderDeliveryServiceInfo.NOTIFYPRODUCTDELIVERY_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method notifyProductDeliveryRegister.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncNotifyProductDeliveryRegister(Subscription subscription,
            ProductOrderDeliveryAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRegister(ProductOrderDeliveryServiceInfo.NOTIFYPRODUCTDELIVERY_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Deregister method for the notifyProductDelivery PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void notifyProductDeliveryDeregister(IdentifierList identifierList) throws MALStandardError, MALException {
        try {
            consumer.deregister(ProductOrderDeliveryServiceInfo.NOTIFYPRODUCTDELIVERY_OP, identifierList);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method notifyProductDeliveryDeregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncNotifyProductDeliveryDeregister(IdentifierList identifierList,
            ProductOrderDeliveryAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncDeregister(ProductOrderDeliveryServiceInfo.NOTIFYPRODUCTDELIVERY_OP, identifierList, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Register method for the deliverProducts PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void deliverProductsRegister(Subscription subscription,
            ProductOrderDeliveryAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.register(ProductOrderDeliveryServiceInfo.DELIVERPRODUCTS_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method deliverProductsRegister.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncDeliverProductsRegister(Subscription subscription,
            ProductOrderDeliveryAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRegister(ProductOrderDeliveryServiceInfo.DELIVERPRODUCTS_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Deregister method for the deliverProducts PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void deliverProductsDeregister(IdentifierList identifierList) throws MALStandardError, MALException {
        try {
            consumer.deregister(ProductOrderDeliveryServiceInfo.DELIVERPRODUCTS_OP, identifierList);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method deliverProductsDeregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncDeliverProductsDeregister(IdentifierList identifierList,
            ProductOrderDeliveryAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncDeregister(ProductOrderDeliveryServiceInfo.DELIVERPRODUCTS_OP, identifierList, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

}
