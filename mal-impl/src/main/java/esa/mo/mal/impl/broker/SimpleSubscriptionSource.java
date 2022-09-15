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
import esa.mo.mal.impl.util.StructureHelper;
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

    private final String signature;
    private final ArrayList<SubscriptionConsumer> required = new ArrayList<>();
    private final HashMap<String, SimpleSubscriptionDetails> subs = new HashMap<>();

    /**
     * Constructor.
     *
     * @param hdr The message header of the subscription message.
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
        for (Map.Entry<String, SimpleSubscriptionDetails> entry : subs.entrySet()) {
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
        SimpleSubscriptionDetails sub = subs.get(subId);
        if (sub == null) {
            sub = new SimpleSubscriptionDetails(subId);
            subs.put(subId, sub);
        }
        sub.setIds(srcHdr, subscription.getFilters());
        updateIds();
    }

    @Override
    public void populateNotifyList(final MALMessageHeader srcHdr,
            final List<NotifyMessageSet> lst,
            final UpdateHeaderList updateHeaderList,
            final MALPublishBody publishBody) throws MALException {
        MALBrokerImpl.LOGGER.log(Level.FINE, "Checking SimComSource : {0}", signature);

        final String srcDomainId = StructureHelper.domainToString(srcHdr.getDomain());
        final List<NotifyMessage> msgs = new LinkedList<>();

        for (Map.Entry<String, SimpleSubscriptionDetails> ent : subs.entrySet()) {
            final NotifyMessage subUpdate = ent.getValue().populateNotifyList(
                    srcHdr, srcDomainId, updateHeaderList, publishBody);
            if (subUpdate != null) {
                msgs.add(subUpdate);
            }
        }

        if (!msgs.isEmpty()) {
            for (NotifyMessage msg : msgs) {
                // update the subs in the header
                msg.domain = srcHdr.getDomain();
                msg.networkZone = srcHdr.getNetworkZone();
                msg.area = srcHdr.getServiceArea();
                msg.service = srcHdr.getService();
                msg.operation = srcHdr.getOperation();
                msg.version = srcHdr.getAreaVersion();
            }
            
            lst.add(new NotifyMessageSet(getMsgHeaderDetails(), msgs));
        }
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

        /*
        for (Map.Entry<String, SimpleSubscriptionDetails> entry : subs.entrySet()) {
            // entry.getValue().appendIds(required);
            required.addAll(entry.getValue().getRequired());
        }
        */
        for (SimpleSubscriptionDetails subDetails : subs.values()) {
            required.addAll(subDetails.getRequired());
        }
    }
}
