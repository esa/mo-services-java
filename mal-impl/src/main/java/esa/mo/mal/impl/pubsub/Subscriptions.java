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
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilterList;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Holds a list of subscriptions keyed on a subscription Id
 */
public class Subscriptions {

    private final ArrayList<SingleSubscription> subscriptions = new ArrayList<>();
    private final String subscriptionId;

    public Subscriptions(final String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public final ArrayList<SingleSubscription> getSubscriptions() {
        return subscriptions;
    }

    public final Identifier getSubscriptionId() {
        return new Identifier(subscriptionId);
    }

    public void report() {
        StringBuilder str = new StringBuilder();
        str.append("    START Subscription: ").append(subscriptionId);

        for (SingleSubscription key : subscriptions) {
            str.append("            : Rqd : ").append(key);
        }
        MALBrokerImpl.LOGGER.log(Level.FINE, str.toString());
    }

    public void setIds(final IdentifierList domain,
            final MALMessageHeader srcHdr, final SubscriptionFilterList filters) {
        subscriptions.clear();
        subscriptions.add(new SingleSubscription(domain, srcHdr, filters));
    }

    public boolean matchesAnySubscription(UpdateKeyValues providerUpdates) {
        return BrokerMatcher.keyValuesMatchSubs(providerUpdates, subscriptions);
    }
}
