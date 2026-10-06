package org.ccsds.moims.mo.mpd.productorderdelivery.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.provider.MALPublisherSet;
import org.ccsds.moims.mo.mal.structures.AttributeType;
import org.ccsds.moims.mo.mal.structures.AttributeTypeList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mpd.structures.ProductMetadata;

/**
 * Publisher class for the notifyProductDelivery operation.
 */
public final class NotifyProductDeliveryPublisher {

    /**
     * The publisherSet field.
     */
    private MALPublisherSet publisherSet;

    /**
     * Creates an instance of this class using the supplied publisher set.
     * 
     * @param publisherSet The set of broker connections to use when registering and publishing.
     */
    public NotifyProductDeliveryPublisher(MALPublisherSet publisherSet) {
        this.publisherSet = publisherSet;
    }

    /**
     * Registers this provider implementation to the set of broker connections.
     * 
     * @param keyNames The key names to use in the method
     * @param keyTypes The key types to use in the method
     * @param listener The listener object to use for callback from the publisher
     * @throws java.lang.IllegalArgumentException If any supplied argument is invalid
     * @throws MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws MALException if there is an implementation exception
     */
    public void register(IdentifierList keyNames,
            AttributeTypeList keyTypes,
            MALPublishInteractionListener listener) throws java.lang.IllegalArgumentException, MALInteractionException, MALException {
        publisherSet.register(keyNames, keyTypes, listener);
    }

    /**
     * Registers this provider implementation to the set of broker connections
     * with the default subscription keys.
     * 
     * @param listener The listener object to use for callback from the publisher
     * @throws MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws MALException if there is an implementation exception
     */
    public void registerWithDefaultKeys(MALPublishInteractionListener listener) throws MALInteractionException, MALException {
        IdentifierList keyNames = new IdentifierList();
        AttributeTypeList keyTypes = new AttributeTypeList();
        keyNames.add(new Identifier("user"));
        keyTypes.add(AttributeType.IDENTIFIER);
        keyNames.add(new Identifier("orderID"));
        keyTypes.add(AttributeType.LONG);
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
     * @throws MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws MALException if there is an implementation exception
     */
    public void asyncRegister(IdentifierList keyNames,
            AttributeTypeList keyTypes,
            MALPublishInteractionListener listener) throws java.lang.IllegalArgumentException, MALInteractionException, MALException {
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
     * @throws MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws MALException if there is an implementation exception
     */
    public void publish(UpdateHeader updateHeader,
            ProductMetadata metadata,
            String filename,
            URI deliveredTo,
            Boolean success) throws java.lang.IllegalArgumentException, MALInteractionException, MALException {
        publisherSet.publish(updateHeader, metadata, filename, deliveredTo, success);
    }

    /**
     * Deregisters this provider implementation from the set of broker connections.
     * 
     * @throws MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws MALException if there is an implementation exception
     */
    public void deregister() throws MALInteractionException, MALException {
        publisherSet.deregister();
    }

    /**
     * Asynchronously deregisters this provider implementation from the set of
     * broker connections.
     * 
     * @param listener The listener object to use for callback from the publisher
     * @throws java.lang.IllegalArgumentException If any supplied argument is invalid
     * @throws MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws MALException if there is an implementation exception
     */
    public void asyncDeregister(MALPublishInteractionListener listener) throws java.lang.IllegalArgumentException, MALInteractionException, MALException {
        publisherSet.asyncDeregister(listener);
    }

    /**
     * Closes this publisher.
     * 
     * @throws MALException if there is an implementation exception
     */
    public void close() throws MALException {
        publisherSet.close();
    }

}
