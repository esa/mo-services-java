package org.ccsds.moims.mo.mps.plandistribution.consumer;

import java.util.ArrayList;
import java.util.Arrays;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttribute;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;

/**
 * Typed accessors for the Subscription Keys of the monitorPlan PubSub operation.
 */
public final class MonitorPlanSubscriptionKeys {

    /**
     * The key values as received in the UpdateHeader.
     */
    private NullableAttributeList keyValues;

    /**
     * The effective key names for the received key values.
     */
    private IdentifierList keyNames;

    /**
     * The Subscription Key names defined by the operation, in order.
     */
    private static final IdentifierList CANONICAL_KEY_NAMES = new IdentifierList(new ArrayList<>(Arrays.asList(new Identifier("planID"), new Identifier("precursor"), new Identifier("status"), new Identifier("originator"))));

    /**
     * Creates an instance from the received UpdateHeader and the subscription
     * selectedKeys.
     * 
     * @param updateHeader The UpdateHeader received in the NOTIFY message
     * @param selectedKeys The selectedKeys of the subscription, or null if trimming was not enabled
     */
    public MonitorPlanSubscriptionKeys(UpdateHeader updateHeader,
            IdentifierList selectedKeys) {
        this.keyValues = (updateHeader == null) ? null : updateHeader.getKeyValues();
        this.keyNames = (selectedKeys != null) ? selectedKeys : CANONICAL_KEY_NAMES;
    }

    /**
     * Returns the value of the "planID" Subscription Key, or null if not present.
     * 
     * @return The key value, or null if not present
     */
    public Identifier getPlanID() {
        return (Identifier) valueByName("planID");
    }

    /**
     * Returns the value of the "precursor" Subscription Key, or null if not present.
     * 
     * @return The key value, or null if not present
     */
    public Identifier getPrecursor() {
        return (Identifier) valueByName("precursor");
    }

    /**
     * Returns the value of the "status" Subscription Key, or null if not present.
     * 
     * @return The key value, or null if not present
     */
    public UShort getStatus() {
        return (UShort) valueByName("status");
    }

    /**
     * Returns the value of the "originator" Subscription Key, or null if not
     * present.
     * 
     * @return The key value, or null if not present
     */
    public Identifier getOriginator() {
        return (Identifier) valueByName("originator");
    }

    /**
     * Returns the Subscription Key value with the given name, or null if it is
     * not present (for example when it was trimmed away or is a custom key that
     * is not part of this subscription).
     * 
     * @param name The Subscription Key name
     * @return The key value, or null if not present
     */
    public Attribute getByName(String name) {
        return valueByName(name);
    }

    /**
     * 
     * @param name The Subscription Key name
     */
    private Attribute valueByName(String name) {
        if (keyNames == null || keyValues == null) {
            return null;
        }
        for (int i = 0; i < keyNames.size(); i++) {
            if (name.equals(keyNames.get(i).getValue())) {
                if (i >= keyValues.size()) {
                    return null;
                }
                NullableAttribute na = keyValues.get(i);
                return (na == null) ? null : na.getValue();
            }
        }
        return null;
    }

}
