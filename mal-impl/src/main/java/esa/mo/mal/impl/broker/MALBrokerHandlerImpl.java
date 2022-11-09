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

import esa.mo.mal.impl.StringPair;
import esa.mo.mal.impl.broker.key.BrokerKey;
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

    private final List<MALBrokerBindingImpl> bindings = new LinkedList<>();
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
        bindings.add((MALBrokerBindingImpl) brokerBinding);
    }

    @Override
    public void malFinalize(MALBrokerBinding brokerBinding) {
        bindings.remove((MALBrokerBindingImpl) brokerBinding);
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
        if ((null != hdr)) {
            PublisherSource s = this.getProviderEntry(key, hdr, true);
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
        PublisherSource s = this.getProviderEntry(brokerKey, hdr, false);
        IdentifierList subKeys = s.getSubscriptionKeyNames();
        final List<NotifyMessageSet> notifyList = generateNotifyMessages(brokerKey, hdr, body, subKeys);

        // Dispatch the Notify messages
        if (!notifyList.isEmpty()) {
            for (NotifyMessageSet notifyMessageSet : notifyList) {
                String uriTo = notifyMessageSet.getDetails().uriTo.getValue();
                MALBrokerBinding binding = this.getBinding(uriTo);

                if (binding != null) {
                    for (NotifyMessage notifyMessage : notifyMessageSet.getMessages()) {
                        try {
                            binding.sendNotify(notifyMessage.area,
                                    notifyMessage.service,
                                    notifyMessage.operation,
                                    notifyMessage.version,
                                    notifyMessageSet.getDetails().uriTo,
                                    notifyMessageSet.getDetails().transactionId,
                                    notifyMessage.domain,
                                    notifyMessage.networkZone,
                                    notifyMessageSet.getDetails().sessionType,
                                    notifyMessageSet.getDetails().sessionName,
                                    notifyMessageSet.getDetails().qosLevel,
                                    notifyMessageSet.getDetails().qosProps,
                                    notifyMessageSet.getDetails().priority,
                                    notifyMessage.subscriptionId,
                                    notifyMessage.updateHeaderList,
                                    notifyMessage.updateList);
                        } catch (MALTransmitErrorException ex) {
                            MALBrokerImpl.LOGGER.log(Level.WARNING,
                                    "MALTransmitErrorException during transmission of NOTIFY to Consumer URI: {0}",
                                    uriTo);

                            handleConsumerCommunicationError(brokerKey, uriTo);
                        }
                    }
                } else {
                    MALBrokerImpl.LOGGER.log(Level.WARNING,
                            "Unable to find binding for NOTIFY message to: {0}", uriTo);
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
        final PublisherSource details = this.getProviderEntry(key, hdr, false);

        if (null != details) {
            MALBrokerImpl.LOGGER.log(Level.FINE, "Getting publisher QoS details: {0}", hdr);
            return details.getQosLevel();
        }

        return QoSLevel.BESTEFFORT;
    }

    private synchronized MALBrokerBinding getBinding(String uriTo) {
        for (MALBrokerBindingImpl binding : bindings) {
            if (binding.hasSubscriber(uriTo)) {
                return binding;
            }
        }

        return null;
    }

    private synchronized List<NotifyMessageSet> generateNotifyMessages(
            final BrokerKey brokerKey, final MALMessageHeader hdr,
            final MALPublishBody MALPublishBodypublishBody,
            IdentifierList keyNames) throws MALInteractionException, MALException {
        MALBrokerImpl.LOGGER.fine("Checking if Provider is registered...");
        final PublisherSource details = this.getProviderEntry(brokerKey, hdr, false);

        if (details == null) {
            String msg = "Provider not registered! Please register the provider"
                    + " (with PUBLISH_REGISTER) before publishing!";
            MALBrokerImpl.LOGGER.warning(msg);
            throw new MALInteractionException(new MALStandardError(
                    MALHelper.INCORRECT_STATE_ERROR_NUMBER, msg));
        }

        final UpdateHeaderList hl = MALPublishBodypublishBody.getUpdateHeaderList();
        details.checkPublish(hdr, hl);

        List<NotifyMessageSet> lst = new LinkedList<>();

        if (hl != null) {
            final Map<String, SubscriptionSource> rv = this.getConsumerSubscriptions(brokerKey);

            // Iterate through all the consumer subscriptions and populate
            // the notify list if it matches the published updates
            for (SubscriptionSource subSource : rv.values()) {
                NotifyMessageSet nms = subSource.populateNotifyList(hdr, hl, MALPublishBodypublishBody, keyNames);
                if(null != nms){
                    lst.add(nms);
                }
            }

            /*
            for (Map.Entry<String, SubscriptionSource> entry : rv.entrySet()) {
                entry.getValue().populateNotifyList(hdr, lst, hl, publishBody);
            }
             */
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

        if ((null == subSource) && (create)) {
            subSource = createEntry(hdr);
            subs.put(signature, subSource);
        }

        return subSource;
    }

    private SubscriptionSource getConsumerEntry(final BrokerKey key, final String consumerUri) {
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

    private PublisherSource getProviderEntry(final BrokerKey key,
            final MALMessageHeader hdr, final boolean create) {
        final Map<StringPair, PublisherSource> subs = this.getProviderSubscriptions(key);
        String providerKey = createProviderKey(hdr);
        String uriFrom = hdr.getURIFrom().getValue();
        PublisherSource details = subs.get(new StringPair(uriFrom, providerKey));

        if ((details == null) && create) {
            details = new PublisherSource(uriFrom, hdr.getQoSlevel());
            subs.put(new StringPair(uriFrom, providerKey), details);
            MALBrokerImpl.LOGGER.log(Level.FINE, "New publisher registering: {0}", hdr);
        }

        return details;
    }

    private void handleConsumerCommunicationError(final BrokerKey key,
            final String uriTo) {
        final SubscriptionSource entry = getConsumerEntry(key, uriTo);

        if (entry != null) {
            entry.incCommsErrorCount();

            if (entry.getCommsErrorCount() > 2) {
                MALBrokerImpl.LOGGER.log(Level.WARNING,
                        "Removing the Consumer due to too many errors: {0}",
                        uriTo);

                // three strikes and you're out!
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
