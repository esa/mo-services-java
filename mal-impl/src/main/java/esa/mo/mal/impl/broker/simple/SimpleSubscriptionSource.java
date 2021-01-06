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
package esa.mo.mal.impl.broker.simple;

import esa.mo.mal.impl.broker.MALBrokerImpl;
import esa.mo.mal.impl.broker.NotifyMessageSet;
import esa.mo.mal.impl.broker.NotifyMessageSet.NotifyMessage;
import esa.mo.mal.impl.broker.SubscriptionSource;
import esa.mo.mal.impl.broker.key.SubscriptionKey;
import esa.mo.mal.impl.util.StructureHelper;
import java.util.*;
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

    private final String signature;
    private final Set<SubscriptionKey> required = new TreeSet<SubscriptionKey>();
    private final Map<String, SimpleSubscriptionDetails> details = new HashMap<String, SimpleSubscriptionDetails>();

    /**
     * Constructor.
     *
     * @param hdr The message header of the subscription message.
     * @param binding The broker binding that received the subscription.
     */
    public SimpleSubscriptionSource(final MALMessageHeader hdr) {
        super(hdr, hdr.getURIFrom());
        this.signature = hdr.getURIFrom().getValue();
    }

    @Override
    public boolean active() {
        return !required.isEmpty();
    }

    @Override
    public void report() {
        MALBrokerImpl.LOGGER.log(Level.FINE, "  START Consumer ( {0} )", signature);
        MALBrokerImpl.LOGGER.log(Level.FINE, "   Required: {0}", required.size());
        for (Map.Entry<String, SimpleSubscriptionDetails> entry : details.entrySet()) {
            entry.getValue().report();
        }
        MALBrokerImpl.LOGGER.log(Level.FINE, "  END Consumer ( {0} )", signature);
    }

    @Override
    public String getSignature() {
        return signature;
    }

    @Override
    public void addSubscription(final MALMessageHeader srcHdr, final Subscription subscription) {
        final String subId = subscription.getSubscriptionId().getValue();
        SimpleSubscriptionDetails sub = details.get(subId);
        if (null == sub) {
            sub = new SimpleSubscriptionDetails(subId);
            details.put(subId, sub);
        }
        sub.setIds(srcHdr, subscription.getEntities());

        updateIds();
    }

    @Override
    public void populateNotifyList(final MALMessageHeader srcHdr,
            final List<NotifyMessageSet> lst,
            final UpdateHeaderList updateHeaderList,
            final MALPublishBody publishBody) throws MALException {
        MALBrokerImpl.LOGGER.log(Level.FINE, "Checking SimComSource : {0}", signature);

        final String srcDomainId = StructureHelper.domainToString(srcHdr.getDomain());
        final List<NotifyMessage> msgs = new LinkedList<NotifyMessage>();

        for (Map.Entry<String, SimpleSubscriptionDetails> ent : details.entrySet()) {
            final NotifyMessage subUpdate = ent.getValue().populateNotifyList(
                    srcHdr, srcDomainId, updateHeaderList, publishBody);
            if (null != subUpdate) {
                msgs.add(subUpdate);
            }
        }

        if (!msgs.isEmpty()) {
            NotifyMessageSet msgSet = new NotifyMessageSet();
            msgSet.details = getMsgHeaderDetails();
            msgSet.messages = msgs;
            for (NotifyMessage msg : msgs) {
                // update the details in the header
                msg.domain = srcHdr.getDomain();
                msg.networkZone = srcHdr.getNetworkZone();
                msg.area = srcHdr.getServiceArea();
                msg.service = srcHdr.getService();
                msg.operation = srcHdr.getOperation();
                msg.version = srcHdr.getAreaVersion();
            }

            lst.add(msgSet);
        }
    }

    @Override
    public void removeSubscriptions(final IdentifierList subscriptions) {
        if (null != subscriptions) {
            for (Identifier sub : subscriptions) {
                details.remove(sub.getValue());
            }

            updateIds();
        } else {
            // remove all
            details.clear();
            required.clear();
        }
    }

    private void updateIds() {
        required.clear();

        for (Map.Entry<String, SimpleSubscriptionDetails> entry : details.entrySet()) {
            entry.getValue().appendIds(required);
        }
    }
}
