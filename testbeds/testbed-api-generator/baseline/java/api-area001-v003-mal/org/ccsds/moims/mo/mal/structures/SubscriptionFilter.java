package org.ccsds.moims.mo.mal.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;

/**
 * The SubscriptionFilter structure shall be used when subscribing for updates
 * using the PUBSUB Interaction Pattern. It shall contain a single identifier
 * that identifies the Subscription Key name and the set of values to be registered
 * for the defined key name.
 */
public final class SubscriptionFilter implements Composite {

    private static final long serialVersionUID = 281475027043306L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475027043306L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The identifier name of the key.
     */
    private Identifier name;

    /**
     * The list of values that are being subscribed for this key. These shall
     * be ORed together.
     */
    private AttributeList values;

    /**
     * Default constructor for SubscriptionFilter.
     * 
     */
    public SubscriptionFilter() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param name The identifier name of the key.
     * @param values The list of values that are being subscribed for this key. These shall be ORed together.
     */
    public SubscriptionFilter(Identifier name,
            AttributeList values) {
        this.name = name;
        this.values = values;
    }

    @Override
    public Element createElement() {
        return new SubscriptionFilter();
    }

    /**
     * Returns the field name.
     * 
     * @return The field name
     */
    public Identifier getName() {
        return name;
    }

    /**
     * Returns the field values.
     * 
     * @return The field values
     */
    public AttributeList getValues() {
        return values;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SubscriptionFilter) {
            SubscriptionFilter other = (SubscriptionFilter) obj;
            if (name == null) {
                if (other.name != null) {
                    return false;
                }
            } else {
                if (! name.equals(other.name)) {
                    return false;
                }
            }
            if (values == null) {
                if (other.values != null) {
                    return false;
                }
            } else {
                if (! values.equals(other.values)) {
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
        hash = 83 * hash + (name != null ? name.hashCode() : 0);
        hash = 83 * hash + (values != null ? values.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(SubscriptionFilter: ");
        buf.append("name=").append(name);
        buf.append(", values=").append(values);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (name == null) {
            throw new MALException("The field 'name' cannot be null!");
        }
        if (values == null) {
            throw new MALException("The field 'values' cannot be null!");
        }
        encoder.encodeIdentifier(name);
        encoder.encodeElement(values);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        name = decoder.decodeIdentifier();
        values = (AttributeList) decoder.decodeElement(new AttributeList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
