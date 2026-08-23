package org.ccsds.moims.mo.mps.planningrequest.provider;

/**
 * Publisher class for the monitorRequestStatus operation.
 */
public final class MonitorRequestStatusPublisher {

    /**
     * The publisherSet field.
     */
    private org.ccsds.moims.mo.mal.provider.MALPublisherSet publisherSet;

    /**
     * Creates an instance of this class using the supplied publisher set.
     * 
     * @param publisherSet The set of broker connections to use when registering and publishing.
     */
    public MonitorRequestStatusPublisher(org.ccsds.moims.mo.mal.provider.MALPublisherSet publisherSet) {
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
        keyNames.add(new org.ccsds.moims.mo.mal.structures.Identifier("instanceID"));
        keyTypes.add(org.ccsds.moims.mo.mal.structures.AttributeType.IDENTIFIER);
        keyNames.add(new org.ccsds.moims.mo.mal.structures.Identifier("definitionID"));
        keyTypes.add(org.ccsds.moims.mo.mal.structures.AttributeType.IDENTIFIER);
        keyNames.add(new org.ccsds.moims.mo.mal.structures.Identifier("userID"));
        keyTypes.add(org.ccsds.moims.mo.mal.structures.AttributeType.IDENTIFIER);
        keyNames.add(new org.ccsds.moims.mo.mal.structures.Identifier("userReference"));
        keyTypes.add(org.ccsds.moims.mo.mal.structures.AttributeType.IDENTIFIER);
        keyNames.add(new org.ccsds.moims.mo.mal.structures.Identifier("status"));
        keyTypes.add(org.ccsds.moims.mo.mal.structures.AttributeType.USHORT);
        keyNames.add(new org.ccsds.moims.mo.mal.structures.Identifier("outputPlanID"));
        keyTypes.add(org.ccsds.moims.mo.mal.structures.AttributeType.IDENTIFIER);
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
     * @param requestStatusUpdate The requestStatusUpdate field.
     * @throws java.lang.IllegalArgumentException If any supplied argument is invalid
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void publish(org.ccsds.moims.mo.mal.structures.UpdateHeader updateHeader,
            org.ccsds.moims.mo.mps.structures.RequestStatusUpdate requestStatusUpdate) throws java.lang.IllegalArgumentException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        publisherSet.publish(updateHeader, requestStatusUpdate);
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
