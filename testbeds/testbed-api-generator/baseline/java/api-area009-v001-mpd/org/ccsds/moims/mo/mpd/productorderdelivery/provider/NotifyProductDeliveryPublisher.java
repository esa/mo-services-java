package org.ccsds.moims.mo.mpd.productorderdelivery.provider;

/**
 * Publisher class for the notifyProductDelivery operation.
 */
public final class NotifyProductDeliveryPublisher {

    /**
     * The publisherSet field.
     */
    private org.ccsds.moims.mo.mal.provider.MALPublisherSet publisherSet;

    /**
     * Creates an instance of this class using the supplied publisher set.
     * 
     * @param publisherSet The set of broker connections to use when registering and publishing.
     */
    public NotifyProductDeliveryPublisher(org.ccsds.moims.mo.mal.provider.MALPublisherSet publisherSet) {
        this.publisherSet = publisherSet;
    }

    /**
     * Registers this provider implementation to the set of broker connections.
     * 
     * @param keyNames The key names to use in the method
     * @param keyTypes The key types to use in the method
     * @param listener The listener object to use for callback from the publisher
     * @throws java.lang.IllegalArgumentException If any supplied argument is invalid
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void register(org.ccsds.moims.mo.mal.structures.IdentifierList keyNames,
            org.ccsds.moims.mo.mal.structures.AttributeTypeList keyTypes,
            org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener listener) throws java.lang.IllegalArgumentException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        publisherSet.register(keyNames, keyTypes, listener);
    }

    /**
     * Registers this provider implementation to the set of broker connections
     * with the default subscription keys.
     * 
     * @param listener The listener object to use for callback from the publisher
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void registerWithDefaultKeys(org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener listener) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.structures.IdentifierList keyNames = new org.ccsds.moims.mo.mal.structures.IdentifierList();
        org.ccsds.moims.mo.mal.structures.AttributeTypeList keyTypes = new org.ccsds.moims.mo.mal.structures.AttributeTypeList();
        keyNames.add(new org.ccsds.moims.mo.mal.structures.Identifier("user"));
        keyTypes.add(org.ccsds.moims.mo.mal.structures.AttributeType.IDENTIFIER);
        keyNames.add(new org.ccsds.moims.mo.mal.structures.Identifier("orderID"));
        keyTypes.add(org.ccsds.moims.mo.mal.structures.AttributeType.LONG);
        publisherSet.register(keyNames, keyTypes, listener);
    }

    /**
     * Asynchronously registers this provider implementation to the set of broker
     * connections.
     * 
     * @param keyNames The key names to use in the method
     * @param keyTypes The key types to use in the method
     * @param listener The listener object to use for callback from the publisher
     * @throws java.lang.IllegalArgumentException If any supplied argument is invalid
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void asyncRegister(org.ccsds.moims.mo.mal.structures.IdentifierList keyNames,
            org.ccsds.moims.mo.mal.structures.AttributeTypeList keyTypes,
            org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener listener) throws java.lang.IllegalArgumentException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        publisherSet.asyncRegister(keyNames, keyTypes, listener);
    }

    /**
     * Publishes updates to the set of registered broker connections.
     * 
     * @param updateHeader The headers of the updates being added
     * @param metadata The metadata of the mission data product.
     * @param filename The filename of the mission data product.
     * @param deliveredTo The location's URI where the mission data product was delivered.
     * @param success The status indicating the successful delivery of the mission data product.
     * @throws java.lang.IllegalArgumentException If any supplied argument is invalid
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void publish(org.ccsds.moims.mo.mal.structures.UpdateHeader updateHeader,
            org.ccsds.moims.mo.mpd.structures.ProductMetadata metadata,
            String filename,
            org.ccsds.moims.mo.mal.structures.URI deliveredTo,
            Boolean success) throws java.lang.IllegalArgumentException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        publisherSet.publish(updateHeader, metadata, filename, deliveredTo, success);
    }

    /**
     * Deregisters this provider implementation from the set of broker connections.
     * 
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void deregister() throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        publisherSet.deregister();
    }

    /**
     * Asynchronously deregisters this provider implementation from the set of
     * broker connections.
     * 
     * @param listener The listener object to use for callback from the publisher
     * @throws java.lang.IllegalArgumentException If any supplied argument is invalid
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void asyncDeregister(org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener listener) throws java.lang.IllegalArgumentException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        publisherSet.asyncDeregister(listener);
    }

    /**
     * Closes this publisher.
     * 
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void close() throws org.ccsds.moims.mo.mal.MALException {
        publisherSet.close();
    }

}
