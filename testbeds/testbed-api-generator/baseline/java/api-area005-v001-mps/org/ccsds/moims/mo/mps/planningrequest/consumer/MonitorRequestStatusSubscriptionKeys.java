package org.ccsds.moims.mo.mps.planningrequest.consumer;

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
 * Typed accessors for the Subscription Keys of the monitorRequestStatus PubSub
 * operation.
 */
public final class MonitorRequestStatusSubscriptionKeys {

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
    private static final IdentifierList CANONICAL_KEY_NAMES = new IdentifierList(new ArrayList<>(Arrays.asList(new Identifier("instanceID"), new Identifier("definitionID"), new Identifier("userID"), new Identifier("userReference"), new Identifier("status"), new Identifier("outputPlanID"))));

    /**
     * Creates an instance from the received UpdateHeader and the subscription
     * selectedKeys.
     * 
     * @param updateHeader The UpdateHeader received in the NOTIFY message
     * @param selectedKeys The selectedKeys of the subscription, or null if trimming was not enabled
     */
    public MonitorRequestStatusSubscriptionKeys(UpdateHeader updateHeader,
            IdentifierList selectedKeys) {
        this.keyValues = (updateHeader == null) ? null : updateHeader.getKeyValues();
        this.keyNames = (selectedKeys != null) ? selectedKeys : CANONICAL_KEY_NAMES;
    }

    /**
     * Returns the value of the "instanceID" Subscription Key, or null if not
     * present.
     * 
     * @return The key value, or null if not present
     */
    public Identifier getInstanceID() {
        return (Identifier) valueByName("instanceID");
    }

    /**
     * Returns the value of the "definitionID" Subscription Key, or null if not
     * present.
     * 
     * @return The key value, or null if not present
     */
    public Identifier getDefinitionID() {
        return (Identifier) valueByName("definitionID");
    }

    /**
     * Returns the value of the "userID" Subscription Key, or null if not present.
     * 
     * @return The key value, or null if not present
     */
    public Identifier getUserID() {
        return (Identifier) valueByName("userID");
    }

    /**
     * Returns the value of the "userReference" Subscription Key, or null if not
     * present.
     * 
     * @return The key value, or null if not present
     */
    public Identifier getUserReference() {
        return (Identifier) valueByName("userReference");
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
     * Returns the value of the "outputPlanID" Subscription Key, or null if not
     * present.
     * 
     * @return The key value, or null if not present
     */
    public Identifier getOutputPlanID() {
        return (Identifier) valueByName("outputPlanID");
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
