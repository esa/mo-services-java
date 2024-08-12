/* ----------------------------------------------------------------------------
 * Copyright (C) 2023      European Space Agency
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

import esa.mo.mal.impl.pubsub.SingleSubscription;
import esa.mo.mal.impl.pubsub.UpdateKeyValues;
import java.util.ArrayList;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.helpertools.helpers.HelperAttributes;
import org.ccsds.moims.mo.mal.helpertools.helpers.HelperMisc;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.Union;

/**
 * The BrokerMatcher includes the methods to match keys
 */
public class BrokerMatcher {

    /**
     * Match all string constant.
     */
    public static final String ALL_ID = "*";
    /**
     * Match all numeric constant.
     */
    public static final Long ALL_NUMBER = 0L;
    /**
     * Match all ushort constant.
     */
    public static final UShort ALL_SHORT = new UShort(0);

    /**
     * Compare provider's UpdateKeyValues with consumer's subscription filters
     *
     * @param values which is provided by provider
     * @param searchSet list of subscriptions from customers
     * @return boolean match found or not
     */
    public static boolean keyValuesMatchSubs(final UpdateKeyValues values, final ArrayList<SingleSubscription> searchSet) {
        if (values == null || searchSet == null) {
            return false;
        }

        boolean matched = false;
        for (SingleSubscription subscriptionKey : searchSet) {
            MALBrokerImpl.LOGGER.log(Level.FINE, "Checking: {0}\nAgainst: {1}",
                    new Object[]{values, subscriptionKey});

            if (subscriptionKey.matchesWithFilters(values)) {
                MALBrokerImpl.LOGGER.fine("    : Matched");
                matched = true;
                break;
            }
            MALBrokerImpl.LOGGER.fine("    : No match");
        }
        return matched;
    }

    /**
     * Compares two Attribute Key Values taking into account wildcard values.
     *
     * @param consumer The consumer key value.
     * @param provider The provider key value.
     * @return True if they match or one is the wildcard.
     */
    public static boolean matchKeyValues(final Attribute consumer, final Attribute provider) {
        boolean consumerIsNull = (consumer == null);
        boolean providerIsNull = (provider == null);

        // Easiest case: both are null
        if (consumerIsNull && providerIsNull) {
            return true;
        }

        // Check if we have a Wildcard on the consumer subscription...
        if (!consumerIsNull && Attribute.isStringAttribute(consumer)) {
            String str = HelperAttributes.attribute2string(consumer);
            // Check the asterisk case
            if (ALL_ID.equals(str)) {
                return true;
            }
        }
        if (!consumerIsNull && !Attribute.isStringAttribute(consumer)) {
            // Check the zero case for numbers
            if ((consumer instanceof Union) && ((Union) consumer).isZero()) {
                return true;
            }
        }

        // If one of them is null, then it is false.. the wildcard cases are covered above
        if (consumerIsNull || providerIsNull) {
            return false;
        }

        // Are we handling strings?
        if (HelperMisc.isStringAttribute(consumer) && HelperMisc.isStringAttribute(provider)) {
            String first = HelperAttributes.attribute2string(consumer);
            String second = HelperAttributes.attribute2string(provider);
            return first.equals(second);
        }

        // Are we not handling strings?
        if (!HelperMisc.isStringAttribute(consumer) && !HelperMisc.isStringAttribute(provider)) {
            if ((consumer instanceof Union) || (provider instanceof Union)) {
                // Sometimes the nulls are wrapped in a Union type!
                // We need to check that...
                if (((Union) consumer).isNull() || ((Union) provider).isNull()) {
                    return true;
                }
            }

            Object first = HelperAttributes.attribute2JavaType(consumer);
            Object second = HelperAttributes.attribute2JavaType(provider);
            return first.equals(second);
        }

        // Weird case with different data types:
        return consumer.equals(provider);
    }

    /**
     * Checks if the provided domain matches the subscribed domain without
     * wildcard in prefix or suffix
     *
     * @param consumerDomainList The list of domains subscribed by consumers, it
     * can contain wildcard
     * @param providerDomainList The list of domains provided by providers
     * @param consumerStartIndex The start index of consumerDomainList
     * @param providerStartIndex The start index of providerDomainList
     * @param size The length to be matched
     * @return True if the domain matches the domain with the wildcard
     */
    public static boolean innerDomainMatchesWildcardDomain(IdentifierList consumerDomainList, IdentifierList providerDomainList,
            int consumerStartIndex, int providerStartIndex, int size) {
        for (int i = 0; i < size; i++) {
            String consumerDomain = consumerDomainList.get(consumerStartIndex + i).getValue();
            String providerDomain = providerDomainList.get(providerStartIndex + i).getValue();
            if (!"*".equals(consumerDomain) && !consumerDomain.equals(providerDomain)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the provided domain matches the subscribed domain
     *
     * @param consumerDomainList The list of domains subscribed by consumers, it
     * can contain wildcard
     * @param providerDomainList The list of domains provided by providers
     * @return True if the domain matches the domain with the wildcard
     */
    public static boolean domainMatchesWildcardDomain(IdentifierList consumerDomainList, IdentifierList providerDomainList) {
        if (consumerDomainList == null || providerDomainList == null) {
            return consumerDomainList == null && providerDomainList == null;
        }

        // Match it if the consumer registered with a empty list!
        if (consumerDomainList.isEmpty()) {
            return true;
        }

        boolean matched = false;
        String firstDomain = consumerDomainList.get(0).getValue();
        String lastDomain = consumerDomainList.get(consumerDomainList.size() - 1).getValue();

        if ("*".equals(firstDomain) && "*".equals(lastDomain)) {  // *.B.*
            if (consumerDomainList.size() > providerDomainList.size() + 2) {
                return false;
            }

            for (int i = 0; i <= providerDomainList.size() - consumerDomainList.size() + 2; i++) {
                matched = innerDomainMatchesWildcardDomain(consumerDomainList, providerDomainList, 1, i, consumerDomainList.size() - 2);
                if (matched) {
                    return true;
                }
            }
        } else if (!"*".equals(firstDomain) && !"*".equals(lastDomain)) { // A.B.C or A.B.*.C
            if (consumerDomainList.size() != providerDomainList.size()) {
                return false;
            }
            matched = innerDomainMatchesWildcardDomain(consumerDomainList, providerDomainList, 0, 0, consumerDomainList.size());
        } else {
            if (consumerDomainList.size() > providerDomainList.size() + 1) {
                return false;
            }

            if ("*".equals(lastDomain)) { // A.B.*
                matched = innerDomainMatchesWildcardDomain(consumerDomainList, providerDomainList, 0, 0, consumerDomainList.size() - 1);
            } else if ("*".equals(firstDomain)) { //*.B.C
                int i = consumerDomainList.size() - 1;
                int j = providerDomainList.size() - 1;
                for (; i >= 1; i--, j--) {
                    String consumerDomain = consumerDomainList.get(i).getValue();
                    String providerDomain = providerDomainList.get(j).getValue();
                    if (!"*".equals(consumerDomain) && !consumerDomain.equals(providerDomain)) {
                        return false;
                    }
                }
                matched = true;
            }
        }

        return matched;
    }
}
