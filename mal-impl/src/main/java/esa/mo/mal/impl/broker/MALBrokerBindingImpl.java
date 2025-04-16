
/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package esa.mo.mal.impl.broker;

import esa.mo.mal.impl.MALContextImpl;
import esa.mo.mal.impl.ServiceComponentImpl;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.*;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALTransmitErrorListener;

/**
 * Implementation of MALBrokerBinding for MAL level brokers.
 */
public class MALBrokerBindingImpl extends ServiceComponentImpl implements MALBrokerBinding {

    private final Set<String> subscriberSet = new TreeSet<>();
    private final MALBrokerImpl brokerImpl;
    private MALTransmitErrorListener listener;

    /**
     * Constructor.
     *
     * @param parent MAL Broker implementation.
     * @param impl MAL Context Implementation.
     * @param localName Local name.
     * @param protocol Used protocol.
     * @param authenticationId The authentication ID.
     * @param expectedQos The expected QoS level.
     * @param priorityLevelNumber The priority level.
     * @param supplements MAL Supplements.
     * @param qosProperties QoS Properties.
     * @throws MALException If the MAL endpoint is closed.
     */
    MALBrokerBindingImpl(final MALBrokerImpl parent,
            final MALContextImpl impl,
            final String localName,
            final String protocol,
            final Blob authenticationId,
            final QoSLevel[] expectedQos,
            final UInteger priorityLevelNumber,
            final NamedValueList supplements,
            final Map qosProperties) throws MALException {
        super(
                impl,
                localName,
                protocol,
                null,
                authenticationId,
                expectedQos,
                priorityLevelNumber,
                supplements,
                qosProperties,
                null);

        this.brokerImpl = parent;
        this.endpoint.startMessageDelivery();

        MALBrokerImpl.LOGGER.log(Level.FINE,
                "Creating internal MAL Broker for localName: {0} on protocol: {1} with URI: {2}",
                new Object[]{localName, protocol, this.localUri});
    }

    /**
     * Constructor.
     *
     * @param parent Broker implementation.
     * @param impl Context implementation.
     * @param endPoint MAL Endpoint.
     * @param authenticationId Authentication ID.
     * @param expectedQos Expected QoS level.
     * @param priorityLevelNumber Priority level.
     * @param supplements MAL Supplements.
     * @param qosProperties QoS priorities.
     * @throws MALException on error.
     */
    MALBrokerBindingImpl(final MALBrokerImpl parent,
            final MALContextImpl impl,
            final MALEndpoint endPoint,
            final Blob authenticationId,
            final QoSLevel[] expectedQos,
            final UInteger priorityLevelNumber,
            final NamedValueList supplements,
            final Map qosProperties) throws MALException {
        super(
                impl,
                endPoint,
                null,
                authenticationId,
                expectedQos,
                priorityLevelNumber,
                supplements,
                qosProperties,
                null);

        this.brokerImpl = parent;

        MALBrokerImpl.LOGGER.log(Level.INFO,
                "Creating internal MAL Broker for localName: {0} with URI: {1}",
                new Object[]{localName, this.localUri});
    }

    /**
     * Adds the binding implementation to this broker.
     */
    void init() {
        this.brokerImpl.addBinding(this);
    }

    @Override
    public MALMessage sendNotify(final UShort area,
            final UShort service,
            final UShort operation,
            final UOctet version,
            final URI subscriber,
            final Long transactionId,
            final IdentifierList domainId,
            final Map notifyQosProps,
            final Identifier subscriptionId,
            final NamedValueList supplements,
            final UpdateHeader updateHeader,
            final Object... updateObjects)
            throws IllegalArgumentException, MALInteractionException, MALException {
        final Object[] body = new Object[2 + updateObjects.length];
        body[0] = subscriptionId;
        body[1] = updateHeader;
        int i = 2;
        for (Object object : updateObjects) {
            body[i] = object;
            i++;
        }

        final MALMessage msg = endpoint.createMessage(authenticationId,
                subscriber,
                Time.now(),
                InteractionType.PUBSUB,
                MALPubSubOperation.NOTIFY_STAGE,
                transactionId,
                area,
                service,
                operation,
                version,
                Boolean.FALSE,
                supplements,
                notifyQosProps,
                body);

        super.sendHandler.getSecurityManager().check(msg);
        endpoint.sendMessage(msg);
        return msg;
    }

    @Override
    public MALMessage sendNotifyError(final UShort area,
            final UShort service,
            final UShort operation,
            final UOctet version,
            final URI subscriber,
            final Long transactionId,
            final IdentifierList domainId,
            final Identifier networkZone,
            final SessionType sessionType,
            final Identifier sessionName,
            final QoSLevel notifyQos,
            final Map notifyQosProps,
            final UInteger notifyPriority,
            final MOErrorException error,
            final NamedValueList supplements)
            throws IllegalArgumentException, MALInteractionException, MALException {
        final MALMessage msg = endpoint.createMessage(authenticationId,
                subscriber,
                Time.now(),
                InteractionType.PUBSUB,
                MALPubSubOperation.NOTIFY_STAGE,
                transactionId,
                area,
                service,
                operation,
                version,
                Boolean.TRUE,
                supplements,
                notifyQosProps,
                error.getErrorNumber(), error.getExtraInformation());

        sendHandler.getSecurityManager().check(msg);
        endpoint.sendMessage(msg);
        return msg;
    }

    @Override
    public MALMessage sendPublishError(final UShort area,
            final UShort service,
            final UShort operation,
            final UOctet version,
            final URI publisher,
            final Long transactionId,
            final IdentifierList domainId,
            final Identifier networkZone,
            final SessionType sessionType,
            final Identifier sessionName,
            final QoSLevel qos,
            final Map qosProps,
            final UInteger priority,
            final MOErrorException error,
            final NamedValueList supplements)
            throws IllegalArgumentException, MALInteractionException, MALException {
        final MALMessage msg = endpoint.createMessage(authenticationId,
                publisher,
                Time.now(),
                InteractionType.PUBSUB,
                MALPubSubOperation.PUBLISH_STAGE,
                transactionId,
                area,
                service,
                operation,
                version,
                Boolean.TRUE,
                supplements,
                qosProps,
                error.getErrorNumber(),
                error.getExtraInformation());

        sendHandler.getSecurityManager().check(msg);
        endpoint.sendMessage(msg);
        return msg;
    }

    @Override
    public void setTransmitErrorListener(final MALTransmitErrorListener plistener) throws MALException {
        listener = plistener;
    }

    @Override
    public MALTransmitErrorListener getTransmitErrorListener() throws MALException {
        return listener;
    }

    /**
     * Returns the reference to the top level broker object.
     *
     * @return The parent broker.
     */
    public MALBrokerImpl getBrokerImpl() {
        return brokerImpl;
    }

    /**
     * Adds a subscriber from this binding.
     *
     * @param uriTo The URI of the subscriber.
     */
    public void addSubscriber(String uriTo) {
        subscriberSet.add(uriTo);
    }

    /**
     * Removes a subscriber from this binding.
     *
     * @param uriTo The URI of the subscriber.
     */
    public void removeSubscriber(String uriTo) {
        subscriberSet.remove(uriTo);
    }

    /**
     * Returns true if the uri supplied is known by this binding.
     *
     * @param uri URI of the subscriber to search for.
     * @return True if a subscriber known to this binding.
     */
    public boolean hasSubscriber(String uri) {
        return subscriberSet.contains(uri);
    }

}
