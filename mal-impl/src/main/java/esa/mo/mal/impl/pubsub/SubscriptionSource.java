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
package esa.mo.mal.impl.pubsub;

import esa.mo.mal.impl.broker.BrokerMatcher;
import esa.mo.mal.impl.broker.MALBrokerImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALPublishBody;

/**
 * The SubscriptionSource represents a single consumer source indexed by URI.
 */
public class SubscriptionSource {

    // Subscriptions from the this consumer by subscriptionId
    private final HashMap<String, Subscriptions> subs = new HashMap<>();
    private final ArrayList<SingleSubscription> required = new ArrayList<>();
    private final NotifyMessageHeader msgHeaderDetails;
    private final String signatureURI;
    private int commsErrorCount = 0;

    /**
     * Constructor.
     *
     * @param hdr The message header of the subscription message.
     */
    public SubscriptionSource(final MALMessageHeader hdr) {
        this.signatureURI = hdr.getFrom().getValue();
        msgHeaderDetails = new NotifyMessageHeader(hdr.getFrom(), hdr.getTransactionId(), null);
    }

    /**
     * Increments the count of communication errors.
     */
    public void incrementCommsErrorCount() {
        ++commsErrorCount;
    }

    /**
     * Returns the current communications error count.
     *
     * @return the error count.
     */
    public int getCommsErrorCount() {
        return commsErrorCount;
    }

    /**
     * Resets the count of communication errors.
     */
    public void resetCommsErrorCount() {
        commsErrorCount = 0;
    }

    public boolean active() {
        return !required.isEmpty();
    }

    public void report() {
        MALBrokerImpl.LOGGER.log(Level.FINE, "  START Consumer ( {0} )", signatureURI);
        MALBrokerImpl.LOGGER.log(Level.FINE, "   Required: {0}", required.size());
        for (Map.Entry<String, Subscriptions> entry : subs.entrySet()) {
            entry.getValue().report();
        }
        MALBrokerImpl.LOGGER.log(Level.FINE, "  END Consumer ( {0} )", signatureURI);
    }

    public String getSignature() {
        return signatureURI;
    }

    public void addSubscription(final MALMessageHeader srcHdr, final Subscription subscription) {
        final String subId = subscription.getSubscriptionId().getValue();
        Subscriptions sub = subs.get(subId);
        if (sub == null) {
            sub = new Subscriptions(subId);
            subs.put(subId, sub);
        }
        sub.setIds(subscription.getDomain(), srcHdr, subscription.getFilters());
        updateIds();
    }

    /**
     * Creates the list of Notify messages to be sent.
     *
     * @param srcHdr The source Header.
     * @param publishBody The publish body.
     * @param updateKeyValues The provider Update key values.
     * @return the list of Notify messages.
     * @throws MALException if one of the Notify messages could not be
     * generated.
     */
    public List<NotifyMessage> generateNotifyMessagesIfMatch(final MALMessageHeader srcHdr,
            final MALPublishBody publishBody, UpdateKeyValues updateKeyValues) throws MALException {
        MALBrokerImpl.LOGGER.log(Level.FINE, "Checking SubscriptionSource: {0}", signatureURI);

        final UpdateHeader updateHeader = publishBody.getUpdateHeader();
        IdentifierList srcDomainId = updateHeader.getDomain();
        Object[] updateObjects = publishBody.getUpdateObjects();
        final List<NotifyMessage> notifyMsgs = new LinkedList<>();

        // Iterate through all existing subscriptions from this consumer
        for (Subscriptions sub : subs.values()) {
            if (sub.matchesAnySubscription(updateKeyValues)) {
                // Create a Notify message for this consumer because at least one
                // of the subscriptions matched the published Update Key-values
                NullableAttributeList notifyValues = updateKeyValues.selectKeys(null);
                UpdateHeader strippedUpdateHeader = new UpdateHeader(updateHeader.getSource(),
                        updateHeader.getDomain(), notifyValues);

                NotifyMessageBody body = new NotifyMessageBody(sub.getSubscriptionId(),
                        strippedUpdateHeader, updateObjects, srcHdr, srcDomainId);
                notifyMsgs.add(new NotifyMessage(msgHeaderDetails, body));
            }
        }

        return notifyMsgs;
    }

    public void removeSubscriptions(final IdentifierList subscriptionIds) {
        if (null != subscriptionIds) {
            for (Identifier id : subscriptionIds) {
                subs.remove(id.getValue());
            }

            updateIds();
        } else {
            // remove all
            subs.clear();
            required.clear();
        }
    }

    private void updateIds() {
        required.clear();

        for (Subscriptions subDetails : subs.values()) {
            required.addAll(subDetails.getSubscriptions());
        }
    }
}
