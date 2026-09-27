package org.ccsds.moims.mo.mpd.productorderdelivery.consumer;

/**
 * Consumer stub for ProductOrderDelivery service.
 */
public class ProductOrderDeliveryStub {

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
    public ProductOrderDeliveryStub(org.ccsds.moims.mo.mal.consumer.MALConsumer consumer) {
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
     * Register method for the notifyProductDelivery PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void notifyProductDeliveryRegister(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.mpd.productorderdelivery.consumer.ProductOrderDeliveryAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.register(org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryServiceInfo.NOTIFYPRODUCTDELIVERY_OP, subscription, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method notifyProductDeliveryRegister.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncNotifyProductDeliveryRegister(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.mpd.productorderdelivery.consumer.ProductOrderDeliveryAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRegister(org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryServiceInfo.NOTIFYPRODUCTDELIVERY_OP, subscription, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Deregister method for the notifyProductDelivery PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void notifyProductDeliveryDeregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.deregister(org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryServiceInfo.NOTIFYPRODUCTDELIVERY_OP, identifierList);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method notifyProductDeliveryDeregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncNotifyProductDeliveryDeregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList,
            org.ccsds.moims.mo.mpd.productorderdelivery.consumer.ProductOrderDeliveryAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncDeregister(org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryServiceInfo.NOTIFYPRODUCTDELIVERY_OP, identifierList, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Register method for the deliverProducts PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void deliverProductsRegister(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.mpd.productorderdelivery.consumer.ProductOrderDeliveryAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.register(org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryServiceInfo.DELIVERPRODUCTS_OP, subscription, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method deliverProductsRegister.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncDeliverProductsRegister(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.mpd.productorderdelivery.consumer.ProductOrderDeliveryAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRegister(org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryServiceInfo.DELIVERPRODUCTS_OP, subscription, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Deregister method for the deliverProducts PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void deliverProductsDeregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.deregister(org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryServiceInfo.DELIVERPRODUCTS_OP, identifierList);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method deliverProductsDeregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncDeliverProductsDeregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList,
            org.ccsds.moims.mo.mpd.productorderdelivery.consumer.ProductOrderDeliveryAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncDeregister(org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryServiceInfo.DELIVERPRODUCTS_OP, identifierList, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

}
