package org.ccsds.moims.mo.mal.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;

/**
 * The Subscription structure shall be used when subscribing for updates using
 * the PUBSUB Interaction Pattern. It shall contain a single identifier that
 * identifies the subscription being defined and a set of entities being requested.
 */
public final class Subscription implements Composite {

    private static final long serialVersionUID = 281475027043305L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475027043305L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The identifier of this subscription.
     */
    private Identifier subscriptionId;

    /**
     * Optional domain identifier. If NULL, the subscription shall match with
     * any domain.
     */
    private IdentifierList domain;

    /**
     * The list of names of the selected Subscription Keys to be transmitted to
     * the consumer. The Subscription Keys that are not in this list will be removed.
     * If NULL, then all Subscription Keys will be transmitted.
     */
    private IdentifierList selectedKeys;

    /**
     * The list of filters for this subscription. The list of filters must be
     * ANDed together. If NULL, the subscription will not filter specific keys.
     */
    private SubscriptionFilterList filters;

    /**
     * Default constructor for Subscription.
     * 
     */
    public Subscription() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param subscriptionId The identifier of this subscription.
     * @param domain Optional domain identifier. If NULL, the subscription shall match with any domain.
     * @param selectedKeys The list of names of the selected Subscription Keys to be transmitted to the consumer. The Subscription Keys that are not in this list will be removed. If NULL, then all Subscription Keys will be transmitted.
     * @param filters The list of filters for this subscription. The list of filters must be ANDed together. If NULL, the subscription will not filter specific keys.
     */
    public Subscription(Identifier subscriptionId,
            IdentifierList domain,
            IdentifierList selectedKeys,
            SubscriptionFilterList filters) {
        this.subscriptionId = subscriptionId;
        this.domain = domain;
        this.selectedKeys = selectedKeys;
        this.filters = filters;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param subscriptionId The identifier of this subscription.
     */
    public Subscription(Identifier subscriptionId) {
        this.subscriptionId = subscriptionId;
        this.domain = null;
        this.selectedKeys = null;
        this.filters = null;
    }

    @Override
    public Element createElement() {
        return new Subscription();
    }

    /**
     * Returns the field subscriptionId.
     * 
     * @return The field subscriptionId
     */
    public Identifier getSubscriptionId() {
        return subscriptionId;
    }

    /**
     * Returns the field domain.
     * 
     * @return The field domain
     */
    public IdentifierList getDomain() {
        return domain;
    }

    /**
     * Returns the field selectedKeys.
     * 
     * @return The field selectedKeys
     */
    public IdentifierList getSelectedKeys() {
        return selectedKeys;
    }

    /**
     * Returns the field filters.
     * 
     * @return The field filters
     */
    public SubscriptionFilterList getFilters() {
        return filters;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Subscription) {
            Subscription other = (Subscription) obj;
            if (subscriptionId == null) {
                if (other.subscriptionId != null) {
                    return false;
                }
            } else {
                if (! subscriptionId.equals(other.subscriptionId)) {
                    return false;
                }
            }
            if (domain == null) {
                if (other.domain != null) {
                    return false;
                }
            } else {
                if (! domain.equals(other.domain)) {
                    return false;
                }
            }
            if (selectedKeys == null) {
                if (other.selectedKeys != null) {
                    return false;
                }
            } else {
                if (! selectedKeys.equals(other.selectedKeys)) {
                    return false;
                }
            }
            if (filters == null) {
                if (other.filters != null) {
                    return false;
                }
            } else {
                if (! filters.equals(other.filters)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (subscriptionId != null ? subscriptionId.hashCode() : 0);
        hash = 83 * hash + (domain != null ? domain.hashCode() : 0);
        hash = 83 * hash + (selectedKeys != null ? selectedKeys.hashCode() : 0);
        hash = 83 * hash + (filters != null ? filters.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(Subscription: ");
        buf.append("subscriptionId=").append(subscriptionId);
        buf.append(", domain=").append(domain);
        buf.append(", selectedKeys=").append(selectedKeys);
        buf.append(", filters=").append(filters);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (subscriptionId == null) {
            throw new MALException("The field 'subscriptionId' cannot be null!");
        }
        encoder.encodeIdentifier(subscriptionId);
        encoder.encodeNullableElement(domain);
        encoder.encodeNullableElement(selectedKeys);
        encoder.encodeNullableElement(filters);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        subscriptionId = decoder.decodeIdentifier();
        domain = (IdentifierList) decoder.decodeNullableElement(new IdentifierList());
        selectedKeys = (IdentifierList) decoder.decodeNullableElement(new IdentifierList());
        filters = (SubscriptionFilterList) decoder.decodeNullableElement(new SubscriptionFilterList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
