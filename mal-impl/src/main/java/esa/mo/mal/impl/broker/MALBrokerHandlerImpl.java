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

import esa.mo.mal.impl.pubsub.NotifyMessageSet;
import esa.mo.mal.impl.pubsub.NotifyMessageBody;
import esa.mo.mal.impl.pubsub.SubscriptionSource;
import esa.mo.mal.impl.pubsub.PublisherSource;
import esa.mo.mal.impl.StringPair;
import esa.mo.mal.impl.util.MALClose;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.broker.MALBrokerHandler;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;
import org.ccsds.moims.mo.mal.transport.MALDeregisterBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALPublishBody;
import org.ccsds.moims.mo.mal.transport.MALPublishRegisterBody;
import org.ccsds.moims.mo.mal.transport.MALRegisterBody;
import org.ccsds.moims.mo.mal.transport.MALTransmitErrorException;

/**
 * Base implementation of the MALBrokerHandler class that should be extended by
 * real broker implementations.
 */
public abstract class MALBrokerHandlerImpl extends MALClose implements MALBrokerHandler {

    private final List<MALBrokerBindingImpl> brokers = new LinkedList<>();
    private final Map<BrokerKey, Map<StringPair, PublisherSource>> providers = new HashMap();
    private final Map<BrokerKey, Map<String, SubscriptionSource>> consumers = new HashMap();

    /**
     * Constructor.
     *
     * @param parent Parent closing class.
     */
    protected MALBrokerHandlerImpl(MALClose parent) {
        super(parent);
    }

    @Override
    public void malInitialize(MALBrokerBinding brokerBinding) {
        brokers.add((MALBrokerBindingImpl) brokerBinding);
    }

    @Override
    public void malFinalize(MALBrokerBinding brokerBinding) {
        brokers.remove((MALBrokerBindingImpl) brokerBinding);
    }

    @Override
    public synchronized void handleRegister(final MALInteraction interaction,
            final MALRegisterBody body) throws MALInteractionException, MALException {
        final MALMessageHeader hdr = interaction.getMessageHeader();
        final Subscription subscription = body.getSubscription();
        final BrokerKey key = new BrokerKey(hdr);

        report(key);
        if ((hdr != null) && (subscription != null)) {
            SubscriptionSource sub = this.getConsumerEntry(key, hdr, true);
            sub.addSubscription(hdr, subscription);
        }
        report(key);
    }

    @Override
    public synchronized void handlePublishRegister(final MALInteraction interaction,
            final MALPublishRegisterBody body) throws MALInteractionException, MALException {
        final MALMessageHeader hdr = interaction.getMessageHeader();
        final BrokerKey key = new BrokerKey(hdr);

        report(key);
        if ((hdr != null)) {
            PublisherSource s = this.getPublisherSource(key, hdr, true);
            s.setSubscriptionKeyNames(body.getSubscriptionKeyNames());
        }
        report(key);
    }

    @Override
    public void handlePublish(final MALInteraction interaction,
            final MALPublishBody body) throws MALInteractionException, MALException {
        final MALMessageHeader hdr = interaction.getMessageHeader();
        final BrokerKey brokerKey = new BrokerKey(hdr);
        // Generate the Notify Messages (the matching is done inside it!)
        PublisherSource s = this.getPublisherSource(brokerKey, hdr, false);
        IdentifierList subKeys = s.getSubscriptionKeyNames();
        final List<NotifyMessageSet> notifyList = generateNotifyMessages(brokerKey, hdr, body, subKeys);

        // Dispatch the Notify messages
        for (NotifyMessageSet notifyMessageSet : notifyList) {
            String uriTo = notifyMessageSet.getDetails().uriTo.getValue();
            MALBrokerBinding binding = this.getBroker(uriTo);

            if (binding == null) {
                MALBrokerImpl.LOGGER.log(Level.WARNING,
                        "Unable to find binding for NOTIFY message to: {0}", uriTo);
                handleConsumerCommunicationError(brokerKey, uriTo);
                continue;
            }

            for (NotifyMessageBody msgBody : notifyMessageSet.getBodies()) {
                try {
                    binding.sendNotify(msgBody.getArea(),
                            msgBody.getService(),
                            msgBody.getOperation(),
                            msgBody.getVersion(),
                            notifyMessageSet.getDetails().uriTo,
                            notifyMessageSet.getDetails().transactionId,
                            msgBody.getDomain(),
                            msgBody.getNetworkZone(),
                            notifyMessageSet.getDetails().sessionType,
                            notifyMessageSet.getDetails().sessionName,
                            notifyMessageSet.getDetails().qosLevel,
                            notifyMessageSet.getDetails().qosProps,
                            notifyMessageSet.getDetails().priority,
                            msgBody.getSubscriptionId(),
                            msgBody.getUpdateHeaderList(),
                            msgBody.getUpdateList());
                } catch (MALTransmitErrorException ex) {
                    MALBrokerImpl.LOGGER.log(Level.WARNING,
                            "Unable to send NOTIFY message:\n{0}", msgBody.toString());

                    handleConsumerCommunicationError(brokerKey, uriTo);
                }
            }
        }
    }

    @Override
    public synchronized void handleDeregister(final MALInteraction interaction,
            final MALDeregisterBody body) throws MALInteractionException, MALException {
        final MALMessageHeader hdr = interaction.getMessageHeader();
        final IdentifierList subIds = body.getIdentifierList();
        final BrokerKey key = new BrokerKey(hdr);

        report(key);

        if ((hdr != null) && (subIds != null) && !subIds.isEmpty()) {
            internalDeregisterSubscriptions(key, this.getConsumerEntry(key, hdr, false), subIds);
        }

        report(key);
    }

    @Override
    public synchronized void handlePublishDeregister(final MALInteraction interaction)
            throws MALInteractionException, MALException {
        final MALMessageHeader hdr = interaction.getMessageHeader();
        final BrokerKey key = new BrokerKey(hdr);

        report(key);
        final Map<StringPair, PublisherSource> subs = this.getProviderSubscriptions(key);
        String providerKey = createProviderKey(hdr);
        StringPair pair = new StringPair(hdr.getURIFrom().getValue(), providerKey);

        if (subs.remove(pair) != null) {
            MALBrokerImpl.LOGGER.log(Level.FINE, "Removing publisher! Details: {0}", hdr);
        }

        if (subs.isEmpty()) {
            providers.remove(key);
        }
        report(key);
    }

    /**
     * Returns the QoS used when contacting the provider.
     *
     * @param hdr The supplied header message.
     * @return The required QoS level.
     */
    public QoSLevel getProviderQoSLevel(final MALMessageHeader hdr) {
        final BrokerKey key = new BrokerKey(hdr);
        final PublisherSource details = this.getPublisherSource(key, hdr, false);

        if (details != null) {
            return details.getQosLevel();
        }

        return QoSLevel.BESTEFFORT;
    }

    private synchronized MALBrokerBinding getBroker(String uriTo) {
        for (MALBrokerBindingImpl binding : brokers) {
            if (binding.hasSubscriber(uriTo)) {
                return binding;
            }
        }

        return null;
    }

    private synchronized List<NotifyMessageSet> generateNotifyMessages(
            final BrokerKey brokerKey, final MALMessageHeader hdr,
            final MALPublishBody publishBody, IdentifierList keyNames)
            throws MALInteractionException, MALException {
        MALBrokerImpl.LOGGER.fine("Checking if Provider is registered...");
        PublisherSource details = this.getPublisherSource(brokerKey, hdr, false);

        if (details == null) {
            String msg = "Provider not registered! Please register the provider"
                    + " (with PUBLISH_REGISTER) before publishing!";
            MALBrokerImpl.LOGGER.warning(msg);
            throw new MALInteractionException(new MALStandardError(
                    MALHelper.INCORRECT_STATE_ERROR_NUMBER, msg));
        }

        final UpdateHeaderList hl = publishBody.getUpdateHeaderList();
        details.checkPublish(hdr, hl);

        List<NotifyMessageSet> lst = new LinkedList<>();

        if (hl != null) {
            Map<String, SubscriptionSource> rv = this.getConsumerSubscriptions(brokerKey);

            // Iterate through all the consumer subscriptions and generate
            // the notify list if it matches the published updates
            for (SubscriptionSource subSource : rv.values()) {
                try {
                    NotifyMessageSet nms = subSource.generateNotifyList(hdr, hl, publishBody, keyNames);
                    if (nms != null) {
                        lst.add(nms);
                    }
                } catch (MALException ex) {
                    MALBrokerImpl.LOGGER.warning(ex.getMessage());
                    throw new MALInteractionException(new MALStandardError(
                            MALHelper.UNKNOWN_ERROR_NUMBER, null));
                }
            }
        }

        return lst;
    }

    private synchronized void report(final BrokerKey key) {
        if (MALBrokerImpl.LOGGER.isLoggable(Level.FINE)) {
            MALBrokerImpl.LOGGER.fine("START REPORT");

            for (PublisherSource publisherSource : this.getProviderSubscriptions(key).values()) {
                publisherSource.report();
            }

            for (SubscriptionSource subscriptionSource : this.getConsumerSubscriptions(key).values()) {
                subscriptionSource.report();
            }

            MALBrokerImpl.LOGGER.fine("END REPORT");
        }
    }

    private static String createProviderKey(final MALMessageHeader details) {
        final StringBuilder buf = new StringBuilder();
        buf.append(details.getSession());
        buf.append(':').append(details.getSessionName());
        buf.append(':').append(details.getNetworkZone());
        buf.append(':').append(details.getDomain());
        return buf.toString();
    }

    private Map<String, SubscriptionSource> getConsumerSubscriptions(final BrokerKey key) {
        Map<String, SubscriptionSource> subs = consumers.get(key);

        if (subs == null) {
            subs = new HashMap();
            consumers.put(key, subs);
        }

        return subs;
    }

    private SubscriptionSource getConsumerEntry(final BrokerKey key,
            final MALMessageHeader hdr, final boolean create) {
        final Map<String, SubscriptionSource> subs = this.getConsumerSubscriptions(key);
        final String signature = hdr.getURIFrom().getValue();
        SubscriptionSource subSource = subs.get(signature);

        if ((subSource == null) && (create)) {
            subSource = createEntry(hdr);
            subs.put(signature, subSource);
        }

        return subSource;
    }

    private SubscriptionSource getSubscriptionSource(final BrokerKey key, final String consumerUri) {
        return this.getConsumerSubscriptions(key).get(consumerUri);
    }

    private Map<StringPair, PublisherSource> getProviderSubscriptions(final BrokerKey key) {
        Map<StringPair, PublisherSource> provider = providers.get(key);

        if (provider == null) {
            provider = new HashMap();
            providers.put(key, provider);
        }

        return provider;
    }

    private PublisherSource getPublisherSource(final BrokerKey key,
            final MALMessageHeader hdr, final boolean create) {
        final Map<StringPair, PublisherSource> subs = this.getProviderSubscriptions(key);
        String providerKey = createProviderKey(hdr);
        String uriFrom = hdr.getURIFrom().getValue();
        StringPair pair = new StringPair(uriFrom, providerKey);
        PublisherSource publisher = subs.get(pair);

        if ((publisher == null) && create) {
            publisher = new PublisherSource(uriFrom, hdr.getQoSlevel());
            subs.put(pair, publisher);
            MALBrokerImpl.LOGGER.log(Level.FINE, "New publisher registering: {0}", hdr);
        }

        return publisher;
    }

    private void handleConsumerCommunicationError(final BrokerKey key, final String uriTo) {
        final SubscriptionSource entry = getSubscriptionSource(key, uriTo);

        if (entry != null) {
            entry.incrementCommsErrorCount();

            // Deregister the subscription if it is unreachable
            if (entry.getCommsErrorCount() != 0) {
                MALBrokerImpl.LOGGER.log(Level.WARNING,
                        "Removing the Consumer Subscription: {0}", uriTo);

                internalDeregisterSubscriptions(key, entry, null);
            }
        }
    }

    private void internalDeregisterSubscriptions(final BrokerKey key,
            final SubscriptionSource subSource, final IdentifierList subscriptionIds) {
        if (subSource != null) {
            subSource.removeSubscriptions(subscriptionIds);
            if (!subSource.active()) {
                Map<String, SubscriptionSource> subs = getConsumerSubscriptions(key);
                subs.remove(subSource.getSignature());

                if (subs.isEmpty()) {
                    consumers.remove(key);
                }
            }
        }
    }

    /**
     * Creates a broker implementation specific subscription source.
     *
     * @param hdr Source message header.
     * @return The new subscription source object.
     */
    protected abstract SubscriptionSource createEntry(final MALMessageHeader hdr);
}
