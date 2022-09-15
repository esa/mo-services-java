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
import esa.mo.mal.impl.broker.key.UpdateKeyValues;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilterList;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;
import org.ccsds.moims.mo.mal.transport.MALEncodedElementList;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALPublishBody;

/**
 * A SimpleSubscriptionDetails is keyed on subscription Id
 */
class SimpleSubscriptionDetails {

    private final String subscriptionId;
    private final ArrayList<SubscriptionConsumer> subs = new ArrayList<>();

    SimpleSubscriptionDetails(final String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public void report() {
        MALBrokerImpl.LOGGER.log(Level.FINE, "    START Subscription ( {0} )", subscriptionId);
        MALBrokerImpl.LOGGER.log(Level.FINE, "     Subs: {0}", subs.size());
        for (SubscriptionConsumer key : subs) {
            MALBrokerImpl.LOGGER.log(Level.FINE, "            : Rqd : {0}", key);
        }
        MALBrokerImpl.LOGGER.log(Level.FINE, "    END Subscription ( {0} )", subscriptionId);
    }

    public void setIds(final MALMessageHeader srcHdr, final SubscriptionFilterList filters) {
        subs.clear();
        subs.add(new SubscriptionConsumer(srcHdr, filters));
    }

    /**
    * The populateNotifyList method returns a NotifyMessage object if there are
    * matches with any of the subscriptions, or a null if there are no matches.
    */
    public NotifyMessage populateNotifyList(final MALMessageHeader srcHdr,
            final String srcDomainId,
            final UpdateHeaderList updateHeaderList,
            final MALPublishBody publishBody) throws MALException {
        MALBrokerImpl.LOGGER.fine("Checking SimpleSubscriptionDetails");

        final UpdateHeaderList notifyHeaders = new UpdateHeaderList();

        final List[] updateLists = publishBody.getUpdateLists((List[]) null);
        List[] notifyLists = null;

        // have to check for the case where the pubsub message does not contain a body
        if (null != updateLists) {
            notifyLists = new List[updateLists.length];

            for (int i = 0; i < notifyLists.length; i++) {
                if (updateLists[i] != null) {
                    if (updateLists[i] instanceof MALEncodedElementList) {
                        MALEncodedElementList encodedElementList = (MALEncodedElementList) updateLists[i];
                        notifyLists[i] = new MALEncodedElementList(
                                encodedElementList.getShortForm(), encodedElementList.size());
                    } else {
                        notifyLists[i] = (List) ((Element) updateLists[i]).createElement();
                    }
                } else {
                    // publishing an empty list
                    notifyLists[i] = null;
                }
            }
        }

        for (int i = 0; i < updateHeaderList.size(); ++i) {
            populateNotifyList(srcHdr, srcDomainId, updateHeaderList.get(i),
                    updateLists, i, notifyHeaders, notifyLists);
        }

        if (!notifyHeaders.isEmpty()) {
            NotifyMessage retVal = new NotifyMessage();
            retVal.subscriptionId = new Identifier(subscriptionId);
            retVal.updateHeaderList = notifyHeaders;
            retVal.updateList = notifyLists;
            return retVal;
        }

        return null;
    }

    private void populateNotifyList(final MALMessageHeader srcHdr,
            final String srcDomainId,
            final UpdateHeader updateHeader,
            final List[] updateLists,
            final int index,
            final UpdateHeaderList notifyHeaders,
            final List[] notifyLists) throws MALException {
        final UpdateKeyValues key = new UpdateKeyValues(srcHdr, srcDomainId, updateHeader.getKeyValues());
        MALBrokerImpl.LOGGER.log(Level.FINE, "Checking {0}", key);

        if (BrokerMatcher.keyValuesMatchSubs(key, subs)) {
            // add update for this consumer/subscription
            notifyHeaders.add(updateHeader);

            if (null != notifyLists) {
                for (int i = 0; i < notifyLists.length; i++) {
                    if ((notifyLists[i] != null) && (updateLists[i] != null)) {
                        notifyLists[i].add(updateLists[i].get(index));
                    }
                }
            }
        }
    }

    public final ArrayList<SubscriptionConsumer> getRequired(){
        return subs;
    }
}
