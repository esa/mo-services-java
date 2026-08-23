package org.ccsds.moims.mo.com.event.provider;

/**
 * Publisher class for the monitorEvent operation.
 */
public final class MonitorEventPublisher {

    /**
     * The publisherSet field.
     */
    private org.ccsds.moims.mo.mal.provider.MALPublisherSet publisherSet;

    /**
     * Creates an instance of this class using the supplied publisher set.
     * 
     * @param publisherSet The set of broker connections to use when registering and publishing.
     */
    public MonitorEventPublisher(org.ccsds.moims.mo.mal.provider.MALPublisherSet publisherSet) {
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
     * @param eventLinks The MAL::EntityKey.firstSubKey shall contain the event object number as a base 10 string. For example, for an object number of '14' the key value would be '14' with no padding.
The MAL::EntityKey.secondSubKey shall contain the area, service, and version ObjectType fields as a MAL::Long populated as (in hex) 0xAAAASSSSVVXXXXXX where AAAA is the area (16 bits), SSSS is the service (16 bits), VV is the version (8 bits), and XXXXXX is unused and set to zero (24 bits). For example, for an area of '1', and a service of '2', and a version of '3' the field would contain (in hex) 0x0001000203000000.
The MAL::EntityKey.thirdSubKey shall contain the event object instance identifier.
The MAL::EntityKey.fourthSubKey shall contain the area, service, version and number fields of the event source ObjectType using same methodology as given for the second sub key but replacing the XXXXXX part with the number field.
The related and source links of the event shall populate the ObjectDetails part of the publish/notify message.
The body of the event shall populate the final part of the publish/notify message.
The timestamp of the Event shall be taken from the publish message.
An event in one domain may be generated by something in another domain therefore the domain of the event shall not be required to be the same domain as the source of the event.
     * @param eventBody eventBody Argument number 1 as defined by the service operation
     * @throws java.lang.IllegalArgumentException If any supplied argument is invalid
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void publish(org.ccsds.moims.mo.mal.structures.UpdateHeader updateHeader,
            org.ccsds.moims.mo.com.structures.ObjectDetails eventLinks,
            org.ccsds.moims.mo.mal.structures.Element eventBody) throws java.lang.IllegalArgumentException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        publisherSet.publish(updateHeader, eventLinks, eventBody);
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
