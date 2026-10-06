package org.ccsds.moims.mo.mps.planexecutioncontrol.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.provider.MALPublisherSet;
import org.ccsds.moims.mo.mal.structures.AttributeType;
import org.ccsds.moims.mo.mal.structures.AttributeTypeList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mps.structures.PlanDetailUpdate;

/**
 * Publisher class for the monitorPlanExecutionDetail operation.
 */
public final class MonitorPlanExecutionDetailPublisher {

    /**
     * The publisherSet field.
     */
    private MALPublisherSet publisherSet;

    /**
     * Creates an instance of this class using the supplied publisher set.
     * 
     * @param publisherSet The set of broker connections to use when registering and publishing.
     */
    public MonitorPlanExecutionDetailPublisher(MALPublisherSet publisherSet) {
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
        keyNames.add(new Identifier("planID"));
        keyTypes.add(AttributeType.IDENTIFIER);
        keyNames.add(new Identifier("subPlan"));
        keyTypes.add(AttributeType.IDENTIFIER);
        keyNames.add(new Identifier("tag"));
        keyTypes.add(AttributeType.STRING);
        keyNames.add(new Identifier("type"));
        keyTypes.add(AttributeType.IDENTIFIER);
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
     * @param detailUpdate The detailUpdate field.
     * @throws java.lang.IllegalArgumentException If any supplied argument is invalid
     * @throws MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws MALException if there is an implementation exception
     */
    public void publish(UpdateHeader updateHeader,
            PlanDetailUpdate detailUpdate) throws java.lang.IllegalArgumentException, MALInteractionException, MALException {
        publisherSet.publish(updateHeader, detailUpdate);
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
