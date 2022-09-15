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
package esa.mo.mal.impl.broker.key;

import esa.mo.mal.impl.broker.BrokerMatcher;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilterList;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Simple class that represents a MAL subscription.
 */
public final class SubscriptionConsumer {

    /**
     * Hash function magic number.
     */
    protected static final int HASH_MAGIC_NUMBER = 47;
    
    private final String domain;
    // private final boolean andSubDomains;
    private final UShort area;
    private final UShort service;
    private final UShort operation;
    private final SubscriptionFilterList filters;

    /**
     * Constructor.
     *
     * @param hdr Subscription message header.
     * @param rqst The subscription request.
     * @param key The subscription entity key.
     */
    public SubscriptionConsumer(final MALMessageHeader hdr, final SubscriptionFilterList filters) {
        // Converts the domain from list form to string form.
        String tmpDomain = "";
        boolean tmpAndSubDomains = false;

        /*
        final IdentifierList mdomain = hdr.getDomain();
        final IdentifierList sdomain = rqst.getSubDomain();
        if ((null != mdomain) || (null != sdomain)) {
            final StringBuilder buf = new StringBuilder();
            if ((null != mdomain) && !mdomain.isEmpty()) {
                buf.append(StructureHelper.domainToString(mdomain));
            }

            if ((null != sdomain) && !sdomain.isEmpty()) {
                for (Identifier identifier : sdomain) {
                    final String id = identifier.getValue();
                    if (ALL_ID.equals(id)) {
                        tmpAndSubDomains = true;
                    } else {
                        if (0 < buf.length()) {
                            buf.append('.');
                        }

                        buf.append(id);
                    }
                }
            }

            tmpDomain = buf.toString();
        }
        */

        this.domain = tmpDomain;
        // this.andSubDomains = tmpAndSubDomains;
        this.area = hdr.getServiceArea();
        this.service = hdr.getService();
        this.operation = hdr.getOperation();
        this.filters = filters;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = HASH_MAGIC_NUMBER * hash + (this.domain != null ? this.domain.hashCode() : 0);
        //hash = HASH_MAGIC_NUMBER * hash + (this.andSubDomains ? 1 : 0);
        hash = HASH_MAGIC_NUMBER * hash + (this.area != null ? this.area.hashCode() : 0);
        hash = HASH_MAGIC_NUMBER * hash + (this.service != null ? this.service.hashCode() : 0);
        hash = HASH_MAGIC_NUMBER * hash + (this.operation != null ? this.operation.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SubscriptionConsumer other = (SubscriptionConsumer) obj;
        if (!super.equals(obj)) {
            return false;
        }
        if (this.domain == null ? other.domain != null : !this.domain.equals(other.domain)) {
            return false;
        }
        /*
        if (this.andSubDomains != other.andSubDomains) {
            return false;
        }
        */
        if (this.area != other.area && (this.area == null || !this.area.equals(other.area))) {
            return false;
        }
        if (this.service != other.service && (this.service == null || !this.service.equals(other.service))) {
            return false;
        }
        if (this.operation != other.operation && (this.operation == null || !this.operation.equals(other.operation))) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if this key matches supplied argument taking into account
     * wildcards.
     *
     * @param rhs Key to match against.
     * @return True if matches.
     */
    public boolean matchesWithWildcard(final UpdateKeyValues rhs) {
        //boolean matched = super.matchesWithWildcard(rhs);
        boolean matched = true;
        if (matched) {
            matched = rhs.getDomain().startsWith(this.domain);

            if (matched) {
                /*
                if (this.domain.length() < rhs.getDomain().length()) {
                    matched = this.andSubDomains;
                }
                */

                if (matched) {
                    matched = BrokerMatcher.matchedSubkeyWithWildcard(area, rhs.getArea());
                    if (matched) {
                        matched = BrokerMatcher.matchedSubkeyWithWildcard(service, rhs.getService());
                        if (matched) {
                            matched = BrokerMatcher.matchedSubkeyWithWildcard(operation, rhs.getOperation());
                        }
                    }
                }
            }
        }

        return matched;
    }

    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append('[');
        buf.append(this.domain);
        /*
        if (this.andSubDomains) {
            buf.append(".*");
        }
        */
        buf.append(':');
        buf.append(this.area);
        buf.append(':');
        buf.append(this.service);
        buf.append(':');
        buf.append(this.operation);
        buf.append(':');
        buf.append(super.toString());
        buf.append(']');
        return buf.toString();
    }
}
