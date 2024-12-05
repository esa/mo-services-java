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
import esa.mo.mal.impl.util.MALCloseable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.IncorrectStateException;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.UnknownException;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.broker.MALBrokerHandler;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NamedValue;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
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
public class MALBrokerHandlerImpl implements MALBrokerHandler, MALCloseable {

    private final List<MALBrokerBindingImpl> brokers = new LinkedList<>();
    private final Map<String, Map<String, PublisherSource>> providers = new HashMap();
    private final Map<String, Map<String, SubscriptionSource>> consumers = new HashMap();

    /**
     * Constructor.
     */
    public MALBrokerHandlerImpl() {
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
        final String brokerKey = hdr.getTo().getValue();

        report(brokerKey);
        if (subscription != null) {
            SubscriptionSource sub = this.getConsumerEntry(brokerKey, hdr, true);
            sub.addSubscription(hdr, subscription);
        }
        report(brokerKey);
    }

    @Override
    public synchronized void handlePublishRegister(final MALInteraction interaction,
            final MALPublishRegisterBody body) throws MALInteractionException, MALException {
        final MALMessageHeader hdr = interaction.getMessageHeader();
        final String providerKey = hdr.getFrom().getValue();

        report(providerKey);
        PublisherSource s = this.getPublisherSource(providerKey, hdr, true);
        s.setSubscriptionKeyNames(body.getSubscriptionKeyNames());
        report(providerKey);
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
            String consumerURI = msg.getHeader().getUriTo().getValue();
            MALBrokerBinding binding = this.getBinding(consumerURI);

            if (binding == null) {
                MALBrokerImpl.LOGGER.log(Level.WARNING,
                        "The Broker was unable to find a binding to URI: {0}",
                        consumerURI);
                handleConsumerCommunicationError(brokerKey, consumerURI);
                continue;
            }

            NotifyMessageBody msgBody = msg.getBody();

            try {
                binding.sendNotify(msgBody.getArea(),
                        msgBody.getService(),
                        msgBody.getOperation(),
                        msgBody.getVersion(),
                        new URI(consumerURI),
                        msg.getHeader().getTransactionId(),
                        msgBody.getDomain(),
                        msg.getHeader().getQosProps(),
                        msgBody.getSubscriptionId(),
                        hdr.getSupplements(),
                        msgBody.getUpdateHeader(),
                        msgBody.getUpdateObjects());
            } catch (MALTransmitErrorException ex) {
                MALBrokerImpl.LOGGER.log(Level.WARNING,
                        "The Broker was unable to send the NOTIFY message:\n{0}",
                        msgBody.toString());
                handleConsumerCommunicationError(brokerKey, consumerURI);
            }
        }
    }

    @Override
    public synchronized void handleDeregister(final MALInteraction interaction,
            final MALDeregisterBody body) throws MALInteractionException, MALException {
        final MALMessageHeader hdr = interaction.getMessageHeader();
        final IdentifierList subIds = body.getSubscriptionIds();
        final String brokerKey = hdr.getTo().getValue();

        report(brokerKey);

        if ((subIds != null) && !subIds.isEmpty()) {
            SubscriptionSource sub = this.getConsumerEntry(brokerKey, hdr, false);
            internalDeregisterSubscriptions(brokerKey, sub, subIds);
        }

        report(brokerKey);
    }

    @Override
    public synchronized void handlePublishDeregister(final MALInteraction interaction)
            throws MALInteractionException, MALException {
        final MALMessageHeader hdr = interaction.getMessageHeader();
        final String providerKey = hdr.getFrom().getValue();

        report(providerKey);
        final Map<String, PublisherSource> subs = this.getProviderRegistrations(providerKey);

        if (subs.remove(hdr.getFrom().getValue()) != null) {
            MALBrokerImpl.LOGGER.log(Level.FINE, "Removing publisher! Details: {0}", hdr);
        }

        if (subs.isEmpty()) {
            providers.remove(providerKey);
        }
        report(providerKey);
    }

    private synchronized MALBrokerBinding getBinding(String uriTo) {
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
        final String providerKey = srcHdr.getFrom().getValue();
        PublisherSource details = this.getPublisherSource(providerKey, srcHdr, false);
        IdentifierList keyNames = details.getSubscriptionKeyNames();

        if (details == null) {
            String msg = "Provider not registered! Please register the provider"
                    + " (with PUBLISH_REGISTER) before publishing!";
            MALBrokerImpl.LOGGER.warning(msg);
            throw new MALInteractionException(new IncorrectStateException(msg));
        }

        final UpdateHeader updateHeader = publishBody.getUpdateHeader();

        if (updateHeader == null) {
            return new LinkedList<>(); // Empty list
        }

        NullableAttributeList keyValues = updateHeader.getKeyValues();
        IdentifierList srcDomainId = updateHeader.getDomain();

        if (keyValues == null) {
            throw new IllegalArgumentException("keyValues cannot be NULL!");
        }

        if (keyNames == null) {
            throw new IllegalArgumentException("keyNames cannot be NULL!");
        }

        if (keyValues.size() != keyNames.size()) {
            String txt = "The keyValues size don't match the providerNames "
                    + "size: " + keyValues.size() + "!=" + keyNames.size()
                    + "\nkeyNames: " + keyNames.toString()
                    + "\nkeyValues: " + keyValues.toString();

            MALBrokerImpl.LOGGER.warning(txt);
            throw new MALInteractionException(new UnknownException(null));
        }

        // Prepare the Key-Value list
        NamedValueList providerKeyValues = new NamedValueList();

        for (int j = 0; j < keyNames.size(); j++) {
            Identifier name = keyNames.get(j);
            Object value = keyValues.get(j).getValue();
            value = (Attribute) Attribute.javaType2Attribute(value);
            providerKeyValues.add(new NamedValue(name, (Attribute) value));
        }

        UpdateKeyValues providerUpdates = new UpdateKeyValues(srcHdr, srcDomainId, providerKeyValues);
        List<NotifyMessage> notifyMessages = new LinkedList<>();

        final String brokerKey = srcHdr.getTo().getValue();
        Collection<SubscriptionSource> subSources = this.getConsumerSubscriptions(brokerKey).values();

        // Iterate through all the consumers and generate
        // the notify list if it matches with any of the subscriptions
        for (SubscriptionSource subSource : subSources) {
            try {
                List<NotifyMessage> list = subSource.generateNotifyMessagesIfMatch(srcHdr, publishBody, providerUpdates);
                notifyMessages.addAll(list);
            } catch (MALException ex) {
                MALBrokerImpl.LOGGER.warning(ex.getMessage());
                throw new MALInteractionException(new UnknownException(null));
            }
        }

        return notifyMessages;
    }

    private synchronized void report(final String key) {
        if (MALBrokerImpl.LOGGER.isLoggable(Level.FINE)) {
            MALBrokerImpl.LOGGER.fine("START REPORT");

            for (PublisherSource publisherSource : this.getProviderRegistrations(key).values()) {
                publisherSource.report();
            }

            for (SubscriptionSource subscriptionSource : this.getConsumerSubscriptions(key).values()) {
                subscriptionSource.report();
            }

            MALBrokerImpl.LOGGER.fine("END REPORT");
        }
    }

    private Map<String, SubscriptionSource> getConsumerSubscriptions(final String brokerKey) {
        Map<String, SubscriptionSource> subs = consumers.get(brokerKey);

        if (subs == null) {
            subs = new HashMap();
            consumers.put(brokerKey, subs);
        }

        return subs;
    }

    private SubscriptionSource getConsumerEntry(final String brokerKey,
            final MALMessageHeader hdr, final boolean create) {
        final Map<String, SubscriptionSource> subs = this.getConsumerSubscriptions(brokerKey);
        final String consumerKey = hdr.getFrom().getValue();
        SubscriptionSource subSource = subs.get(consumerKey);

        if ((subSource == null) && create) {
            subSource = new SubscriptionSource(hdr);
            subs.put(consumerKey, subSource);
        }

        return subSource;
    }

    private Map<String, PublisherSource> getProviderRegistrations(final String providerKey) {
        Map<String, PublisherSource> provider = providers.get(providerKey);

        if (provider == null) {
            provider = new HashMap();
            providers.put(providerKey, provider);
        }

        return provider;
    }

    private PublisherSource getPublisherSource(final String providerKey, final MALMessageHeader hdr, final boolean create) {
        final Map<String, PublisherSource> subs = this.getProviderRegistrations(providerKey);
        String uriFrom = hdr.getFrom().getValue();
        PublisherSource publisher = subs.get(uriFrom);

        if ((publisher == null) && create) {
            publisher = new PublisherSource(uriFrom);
            subs.put(uriFrom, publisher);
            MALBrokerImpl.LOGGER.log(Level.FINE, "New publisher registering: {0}", hdr);
        }

        return publisher;
    }

    private void handleConsumerCommunicationError(final String brokerKey, final String consumerURI) {
        final SubscriptionSource entry = this.getConsumerSubscriptions(brokerKey).get(consumerURI);

        if (entry != null) {
            entry.incrementCommsErrorCount();

            // Deregister the subscription if it is unreachable
            if (entry.getCommsErrorCount() != 0) {
                MALBrokerImpl.LOGGER.log(Level.WARNING,
                        "Removing the Consumer Subscription: {0}", consumerURI);

                internalDeregisterSubscriptions(brokerKey, entry, null);
            }
        }
    }

    private void internalDeregisterSubscriptions(final String brokerKey,
            final SubscriptionSource subSource, final IdentifierList subscriptionIds) {
        if (subSource != null) {
            subSource.removeSubscriptions(subscriptionIds);
            if (!subSource.active()) {
                Map<String, SubscriptionSource> subs = getConsumerSubscriptions(brokerKey);
                subs.remove(subSource.getSignature());

                if (subs.isEmpty()) {
                    consumers.remove(brokerKey);
                }
            }
        }
    }

    @Override
    public void close() throws MALException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
