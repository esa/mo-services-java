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

import esa.mo.mal.impl.pubsub.NotifyMessage;
import esa.mo.mal.impl.pubsub.NotifyMessageBody;
import esa.mo.mal.impl.pubsub.SubscriptionSource;
import esa.mo.mal.impl.pubsub.PublisherSource;
import esa.mo.mal.impl.pubsub.UpdateKeyValues;
import esa.mo.mal.impl.util.MALClose;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.broker.MALBrokerHandler;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NamedValue;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
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
    private final Map<String, Map<String, PublisherSource>> providers = new HashMap();
    private final Map<String, Map<String, SubscriptionSource>> consumers = new HashMap();

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
        final String key = hdr.getTo().getValue();

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
        final String key = hdr.getTo().getValue();

        report(key);
        if (hdr != null) {
            PublisherSource s = this.getPublisherSource(key, hdr, true);
            s.setSubscriptionKeyNames(body.getSubscriptionKeyNames());
        }
        report(key);
    }

    @Override
    public void handlePublish(final MALInteraction interaction,
            final MALPublishBody body) throws MALInteractionException, MALException {
        final MALMessageHeader hdr = interaction.getMessageHeader();
        // Generate the Notify Messages (the matching is done inside it!)
        final List<NotifyMessage> notifyList = this.generateNotifyMessages(hdr, body);
        final String brokerKey = hdr.getTo().getValue();

        // Dispatch the Notify messages
        for (NotifyMessage msg : notifyList) {
            String uriTo = msg.getHeader().getUriTo().getValue();
            MALBrokerBinding binding = this.getBroker(uriTo);

            if (binding == null) {
                MALBrokerImpl.LOGGER.log(Level.WARNING,
                        "The Broker was unable to find a binding to URI: {0}",
                        uriTo);
                handleConsumerCommunicationError(brokerKey, uriTo);
                continue;
            }

            NotifyMessageBody msgBody = msg.getBody();

            try {
                binding.sendNotify(msgBody.getArea(),
                        msgBody.getService(),
                        msgBody.getOperation(),
                        msgBody.getVersion(),
                        new URI(uriTo),
                        msg.getHeader().getTransactionId(),
                        msgBody.getDomain(),
                        msg.getHeader().getQosProps(),
                        msgBody.getSubscriptionId(),
                        hdr.getSupplements(),
                        msgBody.getUpdateHeader(),
                        msgBody.getUpdateObjects());
            } catch (MALTransmitErrorException ex) {
                MALBrokerImpl.LOGGER.log(Level.WARNING,
                        "The Broker was unable to send a NOTIFY message to URI: {0}",
                        msgBody.toString());
                handleConsumerCommunicationError(brokerKey, uriTo);
            }
        }
    }

    @Override
    public synchronized void handleDeregister(final MALInteraction interaction,
            final MALDeregisterBody body) throws MALInteractionException, MALException {
        final MALMessageHeader hdr = interaction.getMessageHeader();
        final IdentifierList subIds = body.getIdentifierList();
        final String key = hdr.getTo().getValue();

        report(key);

        if ((hdr != null) && (subIds != null) && !subIds.isEmpty()) {
            SubscriptionSource sub = this.getConsumerEntry(key, hdr, false);
            internalDeregisterSubscriptions(key, sub, subIds);
        }

        report(key);
    }

    @Override
    public synchronized void handlePublishDeregister(final MALInteraction interaction)
            throws MALInteractionException, MALException {
        final MALMessageHeader hdr = interaction.getMessageHeader();
        final String key = hdr.getTo().getValue();

        report(key);
        final Map<String, PublisherSource> subs = this.getProviderSubscriptions(key);

        if (subs.remove(hdr.getFrom().getValue()) != null) {
            MALBrokerImpl.LOGGER.log(Level.FINE, "Removing publisher! Details: {0}", hdr);
        }

        if (subs.isEmpty()) {
            providers.remove(key);
        }
        report(key);
    }

    private synchronized MALBrokerBinding getBroker(String uriTo) {
        for (MALBrokerBindingImpl binding : brokers) {
            if (binding.hasSubscriber(uriTo)) {
                return binding;
            }
        }

        return null;
    }

    private synchronized List<NotifyMessage> generateNotifyMessages(final MALMessageHeader srcHdr,
            final MALPublishBody publishBody) throws MALInteractionException, MALException {
        MALBrokerImpl.LOGGER.fine("Checking if Provider is registered...");
        final String brokerKey = srcHdr.getTo().getValue();
        PublisherSource details = this.getPublisherSource(brokerKey, srcHdr, false);
        IdentifierList keyNames = details.getSubscriptionKeyNames();

        if (details == null) {
            String msg = "Provider not registered! Please register the provider"
                    + " (with PUBLISH_REGISTER) before publishing!";
            MALBrokerImpl.LOGGER.warning(msg);
            throw new MALInteractionException(new MOErrorException(
                    MALHelper.INCORRECT_STATE_ERROR_NUMBER, msg));
        }

        final UpdateHeader updateHeader = publishBody.getUpdateHeader();
        List<NotifyMessage> notifyMessages = new LinkedList<>();

        if (updateHeader != null) {
            Map<String, SubscriptionSource> consumers = this.getConsumerSubscriptions(brokerKey);

            NullableAttributeList keyValues = updateHeader.getKeyValues();
            IdentifierList srcDomainId = updateHeader.getDomain();

            if (keyValues.size() != keyNames.size()) {
                String txt = "The keyValues size don't match the providerNames "
                        + "size: " + keyValues.size() + "!=" + keyNames.size()
                        + "\nkeyNames: " + keyNames.toString()
                        + "\nkeyValues: " + keyValues.toString();

                MALBrokerImpl.LOGGER.warning(txt);
                throw new MALInteractionException(new MOErrorException(
                        MALHelper.UNKNOWN_ERROR_NUMBER, null));
            }

            // Prepare the Key-Value list
            List<NamedValue> providerKeyValues = new ArrayList<>();

            for (int j = 0; j < keyNames.size(); j++) {
                Identifier name = keyNames.get(j);
                Object value = keyValues.get(j).getValue();
                value = (Attribute) Attribute.javaType2Attribute(value);
                providerKeyValues.add(new NamedValue(name, (Attribute) value));
            }

            UpdateKeyValues providerUpdates = new UpdateKeyValues(srcHdr, srcDomainId, providerKeyValues);

            // Iterate through all the consumers and generate
            // the notify list if it matches with any of the subscriptions
            for (SubscriptionSource subSource : consumers.values()) {
                try {
                    List<NotifyMessage> list = subSource.generateNotifyMessagesIfMatch(srcHdr, publishBody, providerUpdates);
                    notifyMessages.addAll(list);
                } catch (MALException ex) {
                    MALBrokerImpl.LOGGER.warning(ex.getMessage());
                    throw new MALInteractionException(new MOErrorException(
                            MALHelper.UNKNOWN_ERROR_NUMBER, null));
                }
            }
        }

        return notifyMessages;
    }

    private synchronized void report(final String key) {
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

    private Map<String, SubscriptionSource> getConsumerSubscriptions(final String key) {
        Map<String, SubscriptionSource> subs = consumers.get(key);

        if (subs == null) {
            subs = new HashMap();
            consumers.put(key, subs);
        }

        return subs;
    }

    private SubscriptionSource getConsumerEntry(final String key,
            final MALMessageHeader hdr, final boolean create) {
        final Map<String, SubscriptionSource> subs = this.getConsumerSubscriptions(key);
        final String signature = hdr.getFrom().getValue();
        SubscriptionSource subSource = subs.get(signature);

        if ((subSource == null) && (create)) {
            subSource = createEntry(hdr);
            subs.put(signature, subSource);
        }

        return subSource;
    }

    private SubscriptionSource getSubscriptionSource(final String key, final String consumerUri) {
        return this.getConsumerSubscriptions(key).get(consumerUri);
    }

    private Map<String, PublisherSource> getProviderSubscriptions(final String key) {
        Map<String, PublisherSource> provider = providers.get(key);

        if (provider == null) {
            provider = new HashMap();
            providers.put(key, provider);
        }

        return provider;
    }

    private PublisherSource getPublisherSource(final String key, final MALMessageHeader hdr, final boolean create) {
        final Map<String, PublisherSource> subs = this.getProviderSubscriptions(key);
        String uriFrom = hdr.getFrom().getValue();
        PublisherSource publisher = subs.get(uriFrom);

        if ((publisher == null) && create) {
            publisher = new PublisherSource(uriFrom);
            subs.put(uriFrom, publisher);
            MALBrokerImpl.LOGGER.log(Level.FINE, "New publisher registering: {0}", hdr);
        }

        return publisher;
    }

    private void handleConsumerCommunicationError(final String key, final String uriTo) {
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

    private void internalDeregisterSubscriptions(final String key,
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
