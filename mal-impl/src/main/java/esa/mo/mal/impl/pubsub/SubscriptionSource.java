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
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALPublishBody;

/**
 * The SubscriptionSource represents a single consumer source indexed by URI.
 */
public class SubscriptionSource {

    private final HashMap<String, Subscriptions> subs = new HashMap<>();
    private final ArrayList<SubscriptionConsumer> required = new ArrayList<>();
    private final NotifyMessageHeader msgHeaderDetails;
    private final String signatureURI;
    private int commsErrorCount = 0;

    /**
     * Constructor.
     *
     * @param hdr The message header of the subscription message.
     */
    public SubscriptionSource(final MALMessageHeader hdr) {
        this.signatureURI = hdr.getFromURI().getValue();
        msgHeaderDetails = new NotifyMessageHeader(
                hdr.getFromURI(),
                hdr.getTransactionId(),
                null);
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

    public NotifyMessageSet generateNotifyList(final MALMessageHeader srcHdr,
            final UpdateHeaderList updateHeaderList,
            final MALPublishBody publishBody,
            IdentifierList keyNames) throws MALException {
        MALBrokerImpl.LOGGER.log(Level.FINE, "Checking SimComSource : {0}", signatureURI);

        //final IdentifierList srcDomainId = srcHdr.getDomain();
        IdentifierList srcDomainId = null;
        final List<NotifyMessageBody> msgs = new LinkedList<>();

        for (Subscriptions sub : subs.values()) {
            if (!updateHeaderList.isEmpty()){
                srcDomainId = updateHeaderList.get(0).getDomain();
            }
            
            NotifyMessageBody subUpdate = sub.generateNotifyMessage(
                    srcHdr, srcDomainId, updateHeaderList, publishBody, keyNames);

            if (subUpdate != null) {
                msgs.add(subUpdate);
            }
        }

        return (msgs.isEmpty()) ? null : new NotifyMessageSet(msgHeaderDetails, msgs);
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
