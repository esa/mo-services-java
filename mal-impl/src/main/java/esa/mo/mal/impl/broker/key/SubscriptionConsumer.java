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
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NamedValue;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilter;
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
    
    private final IdentifierList domain = new IdentifierList();
    // private final boolean andSubDomains;
    private final UShort area;
    private final UShort service;
    private final UShort operation;
    private final SubscriptionFilterList filters;

    /**
     *
     * @param domain subscription domain
     * @param area subscription area
     * @param service subscription service
     * @param operation subscription operation
     * @param filters subscription filters
     */
    public SubscriptionConsumer(final IdentifierList domain, final UShort area, final UShort service, final UShort operation, final SubscriptionFilterList filters) {
        this.domain.equals(domain);
        this.area = area;
        this.service = service;
        this.operation = operation;
        this.filters = filters;
    }
        
    /**
     * Constructor.
     *
     * @param hdr Subscription message header.
     * @param filters The subscription filters.
     */
    public SubscriptionConsumer(final MALMessageHeader hdr, final SubscriptionFilterList filters) {
        this(null, hdr.getServiceArea(), hdr.getService(),hdr.getOperation(), filters);
        // Converts the domain from list form to string form.
        //String tmpDomain = "";
        //boolean tmpAndSubDomains = false;

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
        // this.andSubDomains = tmpAndSubDomains;
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
        if(this.filters != null){
            if(!this.filters.equals(other.filters)) {
                return false;
            }         
        } 
        return true;
    }
    
    /**
     * check if the provided list of key value by provider match the consumer
     * subscription filters
     *
     * @param rhs list of key value with some metadata provided by the provider
     * @return true if they match otherwise false
     */
    public boolean matchesWithFilters(final UpdateKeyValues rhs){
        
        if(rhs == null) return false;
        
        boolean matched;        
        matched = BrokerMatcher.matchedSubkeyWithWildcard(area, rhs.getArea());
        matched = matched && BrokerMatcher.matchedSubkeyWithWildcard(service, rhs.getService());
        matched = matched && BrokerMatcher.matchedSubkeyWithWildcard(operation, rhs.getOperation());
        
        if(!matched) return matched; //if not matched, return without checking domain & filters to avoid cpu load
        
        //matched = matched && BrokerMatcher.matchedDomainWithWildcard(domain, rhs.getDomain());
        
        if(!matched) return matched; //if not matched, return without checking filters to avoid cpu load
        
        if(filters == null || rhs.getKeyValues() == null){  
            return matched;
        }
        boolean matchedANDed =      true;  //ANDed
        for(SubscriptionFilter filter : filters){   // iterate all filters
            for(NamedValue keyValue: rhs.getKeyValues()){ // iterate over all provided key value pairs
                if(filter.getName().equals(keyValue.getName())){ //matching key
                    matchedANDed = true;
                    boolean matchedORed = false; //ORed
                    
                    for(Object value: filter.getValues()){
                        if(value== null || value.equals(keyValue.getValue())){ //matching value
                            matchedORed = true;
                            break;
                        }
                    }
                    if(!matchedORed) {
                        matchedANDed = false;
                    }
                    break;
                }
                else matchedANDed = false;
            }
            if(!matchedANDed) break;
        }   
        matched = matched && matchedANDed;
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
