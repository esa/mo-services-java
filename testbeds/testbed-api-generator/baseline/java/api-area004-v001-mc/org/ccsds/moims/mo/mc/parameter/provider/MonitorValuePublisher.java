package org.ccsds.moims.mo.mc.parameter.provider;

/**
 * Publisher class for the monitorValue operation.
 */
public final class MonitorValuePublisher {

    /**
     * The publisherSet field.
     */
    private org.ccsds.moims.mo.mal.provider.MALPublisherSet publisherSet;

    /**
     * Creates an instance of this class using the supplied publisher set.
     * 
     * @param publisherSet The set of broker connections to use when registering and publishing.
     */
    public MonitorValuePublisher(org.ccsds.moims.mo.mal.provider.MALPublisherSet publisherSet) {
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
     * @param objId The MAL EntityKey.firstSubKey shall contain the parameter name.
The MAL EntityKey.secondSubKey shall contain the ParameterIdentity object instance identifier.
The MAL EntityKey.thirdSubKey shall contain the ParameterDefinition object instance identifier.
The MAL EntityKey.fourthSubKey shall contain the new ParameterValueInstance object instance identifier.
The timestamp of the ParameterValueInstance report shall be taken from the publish message and shall be the time of the parameter value update.
The publish message shall include the ObjectId of the source link of the report.
If no source link is needed then the ObjectId shall be replaced with a NULL.
     * @param newValue The second part of the publish message shall be the ParameterValueInstance object value.
     * @throws java.lang.IllegalArgumentException If any supplied argument is invalid
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void publish(org.ccsds.moims.mo.mal.structures.UpdateHeader updateHeader,
            org.ccsds.moims.mo.com.structures.ObjectId objId,
            org.ccsds.moims.mo.mc.parameter.structures.ParameterValue newValue) throws java.lang.IllegalArgumentException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        publisherSet.publish(updateHeader, objId, newValue);
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
