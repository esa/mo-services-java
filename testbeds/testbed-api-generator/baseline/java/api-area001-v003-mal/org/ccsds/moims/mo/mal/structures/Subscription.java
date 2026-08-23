package org.ccsds.moims.mo.mal.structures;

/**
 * The Subscription structure shall be used when subscribing for updates using
 * the PUBSUB Interaction Pattern. It shall contain a single identifier that
 * identifies the subscription being defined and a set of entities being requested.
 */
public final class Subscription implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 281475027043305L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475027043305L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The identifier of this subscription.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier subscriptionId;

    /**
     * Optional domain identifier. If NULL, the subscription shall match with
     * any domain.
     */
    private org.ccsds.moims.mo.mal.structures.IdentifierList domain;

    /**
     * The list of names of the selected Subscription Keys to be transmitted to
     * the consumer. The Subscription Keys that are not in this list will be removed.
     * If NULL, then all Subscription Keys will be transmitted.
     */
    private org.ccsds.moims.mo.mal.structures.IdentifierList selectedKeys;

    /**
     * The list of filters for this subscription. The list of filters must be
     * ANDed together. If NULL, the subscription will not filter specific keys.
     */
    private org.ccsds.moims.mo.mal.structures.SubscriptionFilterList filters;

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
    public Subscription(org.ccsds.moims.mo.mal.structures.Identifier subscriptionId,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.IdentifierList selectedKeys,
            org.ccsds.moims.mo.mal.structures.SubscriptionFilterList filters) {
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
    public Subscription(org.ccsds.moims.mo.mal.structures.Identifier subscriptionId) {
        this.subscriptionId = subscriptionId;
        this.domain = null;
        this.selectedKeys = null;
        this.filters = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mal.structures.Subscription();
    }

    /**
     * Returns the field subscriptionId.
     * 
     * @return The field subscriptionId
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getSubscriptionId() {
        return subscriptionId;
    }

    /**
     * Returns the field domain.
     * 
     * @return The field domain
     */
    public org.ccsds.moims.mo.mal.structures.IdentifierList getDomain() {
        return domain;
    }

    /**
     * Returns the field selectedKeys.
     * 
     * @return The field selectedKeys
     */
    public org.ccsds.moims.mo.mal.structures.IdentifierList getSelectedKeys() {
        return selectedKeys;
    }

    /**
     * Returns the field filters.
     * 
     * @return The field filters
     */
    public org.ccsds.moims.mo.mal.structures.SubscriptionFilterList getFilters() {
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
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (subscriptionId == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'subscriptionId' cannot be null!");
        }
        encoder.encodeIdentifier(subscriptionId);
        encoder.encodeNullableElement(domain);
        encoder.encodeNullableElement(selectedKeys);
        encoder.encodeNullableElement(filters);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        subscriptionId = decoder.decodeIdentifier();
        domain = (org.ccsds.moims.mo.mal.structures.IdentifierList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.IdentifierList());
        selectedKeys = (org.ccsds.moims.mo.mal.structures.IdentifierList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.IdentifierList());
        filters = (org.ccsds.moims.mo.mal.structures.SubscriptionFilterList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.SubscriptionFilterList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
