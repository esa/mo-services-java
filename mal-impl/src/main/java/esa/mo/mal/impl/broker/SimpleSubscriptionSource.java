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

import esa.mo.mal.impl.broker.key.SubscriptionConsumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALPublishBody;

/**
 * A SimpleSubscriptionSource represents a single consumer indexed by URI.
 */
class SimpleSubscriptionSource extends SubscriptionSource {

    private final HashMap<String, SimpleSubscriptionDetails> subs = new HashMap<>();
    private final ArrayList<SubscriptionConsumer> required = new ArrayList<>();
    private final String signatureURI;

    /**
     * Constructor.
     *
     * @param hdr The message header of the subscription message.
     */
    public SimpleSubscriptionSource(final MALMessageHeader hdr) {
        super(hdr, hdr.getURIFrom());
        this.signatureURI = hdr.getURIFrom().getValue();
    }

    @Override
    public boolean active() {
        return !required.isEmpty();
    }

    @Override
    public void report() {
        MALBrokerImpl.LOGGER.log(Level.FINE, "  START Consumer ( {0} )", signatureURI);
        MALBrokerImpl.LOGGER.log(Level.FINE, "   Required: {0}", required.size());
        for (Map.Entry<String, SimpleSubscriptionDetails> entry : subs.entrySet()) {
            entry.getValue().report();
        }
        MALBrokerImpl.LOGGER.log(Level.FINE, "  END Consumer ( {0} )", signatureURI);
    }

    @Override
    public String getSignature() {
        return signatureURI;
    }

    @Override
    public void addSubscription(final MALMessageHeader srcHdr, final Subscription subscription) {
        final String subId = subscription.getSubscriptionId().getValue();
        SimpleSubscriptionDetails sub = subs.get(subId);
        if (sub == null) {
            sub = new SimpleSubscriptionDetails(subId);
            subs.put(subId, sub);
        }
        sub.setIds(subscription.getDomain(), srcHdr, subscription.getFilters());
        updateIds();
    }

    @Override
    public NotifyMessageSet populateNotifyList(final MALMessageHeader srcHdr,
            final UpdateHeaderList updateHeaderList,
            final MALPublishBody publishBody,
            IdentifierList keyNames) throws MALException {
        MALBrokerImpl.LOGGER.log(Level.FINE, "Checking SimComSource : {0}", signatureURI);

        final IdentifierList srcDomainId = srcHdr.getDomain();
        final List<NotifyMessage> msgs = new LinkedList<>();

        for (SimpleSubscriptionDetails sub : subs.values()) {
            NotifyMessage subUpdate = sub.generateNotifyMessage(
                    srcHdr, srcDomainId, updateHeaderList, publishBody, keyNames);
            
            if (subUpdate != null) {
                msgs.add(subUpdate);
            }
        }

        NotifyMessageSet.MessageHeaderDetails header = getMsgHeaderDetails();
        return (msgs.isEmpty()) ? null : new NotifyMessageSet(header, msgs);
    }

    @Override
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

        for (SimpleSubscriptionDetails subDetails : subs.values()) {
            required.addAll(subDetails.getSubscriptions());
        }
    }
}
