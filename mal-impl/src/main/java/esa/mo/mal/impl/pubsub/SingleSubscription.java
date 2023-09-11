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
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NamedValue;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilter;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilterList;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Simple class that represents a MAL subscription.
 */
public final class SingleSubscription {

    /**
     * Hash function magic number.
     */
    protected static final int HASH_MAGIC_NUMBER = 47;

    private final IdentifierList domain;
    private final UShort area;
    private final UShort service;
    private final UShort operation;
    private final SubscriptionFilterList filters;
    private final IdentifierList selectedKeys;

    /**
     * Constructor.
     *
     * @param domain subscription domain
     * @param area subscription area
     * @param service subscription service
     * @param operation subscription operation
     * @param filters subscription filters
     * @param selectedKeys The selectedKeys of the subscription.
     */
    public SingleSubscription(final IdentifierList domain, final UShort area,
            final UShort service, final UShort operation,
            final SubscriptionFilterList filters, final IdentifierList selectedKeys) {
        this.domain = domain;
        this.area = area;
        this.service = service;
        this.operation = operation;
        this.filters = filters;
        this.selectedKeys = selectedKeys;
    }

    /**
     * Constructor.
     *
     * @param domain The domain of the subscription.
     * @param hdr Subscription message header.
     * @param filters The filters of the subscription.
     * @param selectedKeys The selectedKeys of the subscription.
     */
    public SingleSubscription(final IdentifierList domain, final MALMessageHeader hdr,
            final SubscriptionFilterList filters, final IdentifierList selectedKeys) {
        this(domain, hdr.getServiceArea(), hdr.getService(), hdr.getOperation(), filters, selectedKeys);
    }

    public IdentifierList getSelectedKeys() {
        return selectedKeys;
    }

    /**
     * check if the provided list of key value by provider match the consumer
     * subscription filters
     *
     * @param updateMsg list of key value with some metadata provided by the
     * provider
     * @return true if they match otherwise false
     */
    public boolean matchesWithFilters(final UpdateKeyValues updateMsg) {
        if (updateMsg == null) {
            return false;
        }

        if (!(area.equals(updateMsg.getArea())
                && service.equals(updateMsg.getService())
                && operation.equals(updateMsg.getOperation()))) {
            // If not matched, return without checking domain & filters to avoid cpu load
            return false;
        }

        // Don't check the domain if the subscription is done with a null on it
        if (domain != null) {
            if (!BrokerMatcher.domainMatchesWildcardDomain(domain, updateMsg.getDomain())) {
                return false;
            }
        }

        if (filters == null || filters.isEmpty()) {
            return true; // No filters selected! All match!
        }

        if (updateMsg.getKeyValues() == null || updateMsg.getKeyValues().isEmpty()) {
            // The update message has no Subscription Key-values, so nothing to be checked
            return true;
        }

        // Iterate over all filters. Filter out the updates that don't matter
        for (SubscriptionFilter filter : filters) {
            // Iterate over all provided key value pairs
            for (NamedValue keyValue : updateMsg.getKeyValues()) {
                if (!filter.getName().equals(keyValue.getName())) {
                    continue; // This is not the key name that we want...
                }

                boolean matchedORed = false; //ORed

                // We need to match at least one of the values!
                for (Attribute value : filter.getValues().getAsAttributes()) {
                    MALBrokerImpl.LOGGER.log(Level.FINE,
                            "Matching the subscription value against the provider value! "
                            + "For key name: {0}\nConsumer Value: {1}  -  Provider Value:  {2}",
                            new Object[]{keyValue.getName(), value, keyValue.getValue()});

                    // Keep looking until we find a match!
                    if (BrokerMatcher.matchKeyValues(value, keyValue.getValue())) {
                        MALBrokerImpl.LOGGER.log(Level.FINER, "Matched: true");
                        matchedORed = true;
                        break;
                    }
                    MALBrokerImpl.LOGGER.log(Level.FINER, "Matched: false");
                }

                if (!matchedORed) { // No values matched?
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append('[').append(this.domain);
        buf.append(':').append(this.area);
        buf.append(':').append(this.service);
        buf.append(':').append(this.operation);
        buf.append(':').append(super.toString());
        buf.append(']');
        return buf.toString();
    }
}
