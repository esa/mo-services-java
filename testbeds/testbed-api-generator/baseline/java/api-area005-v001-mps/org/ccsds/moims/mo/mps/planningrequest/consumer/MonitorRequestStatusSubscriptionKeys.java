package org.ccsds.moims.mo.mps.planningrequest.consumer;

/**
 * Typed accessors for the Subscription Keys of the monitorRequestStatus PubSub
 * operation.
 */
public final class MonitorRequestStatusSubscriptionKeys {

    /**
     * The key values as received in the UpdateHeader.
     */
    private org.ccsds.moims.mo.mal.structures.NullableAttributeList keyValues;

    /**
     * The effective key names for the received key values.
     */
    private org.ccsds.moims.mo.mal.structures.IdentifierList keyNames;

    /**
     * The Subscription Key names defined by the operation, in order.
     */
    private static final org.ccsds.moims.mo.mal.structures.IdentifierList CANONICAL_KEY_NAMES = new org.ccsds.moims.mo.mal.structures.IdentifierList(new java.util.ArrayList<>(java.util.Arrays.asList(new org.ccsds.moims.mo.mal.structures.Identifier("instanceID"), new org.ccsds.moims.mo.mal.structures.Identifier("definitionID"), new org.ccsds.moims.mo.mal.structures.Identifier("userID"), new org.ccsds.moims.mo.mal.structures.Identifier("userReference"), new org.ccsds.moims.mo.mal.structures.Identifier("status"), new org.ccsds.moims.mo.mal.structures.Identifier("outputPlanID"))));

    /**
     * Creates an instance from the received UpdateHeader and the subscription
     * selectedKeys.
     * 
     * @param updateHeader The UpdateHeader received in the NOTIFY message
     * @param selectedKeys The selectedKeys of the subscription, or null if trimming was not enabled
     */
    public MonitorRequestStatusSubscriptionKeys(org.ccsds.moims.mo.mal.structures.UpdateHeader updateHeader,
            org.ccsds.moims.mo.mal.structures.IdentifierList selectedKeys) {
        this.keyValues = (updateHeader == null) ? null : updateHeader.getKeyValues();
        this.keyNames = (selectedKeys != null) ? selectedKeys : CANONICAL_KEY_NAMES;
    }

    /**
     * Returns the value of the "instanceID" Subscription Key, or null if not
     * present.
     * 
     * @return The key value, or null if not present
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getInstanceID() {
        return (org.ccsds.moims.mo.mal.structures.Identifier) valueByName("instanceID");
    }

    /**
     * Returns the value of the "definitionID" Subscription Key, or null if not
     * present.
     * 
     * @return The key value, or null if not present
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getDefinitionID() {
        return (org.ccsds.moims.mo.mal.structures.Identifier) valueByName("definitionID");
    }

    /**
     * Returns the value of the "userID" Subscription Key, or null if not present.
     * 
     * @return The key value, or null if not present
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getUserID() {
        return (org.ccsds.moims.mo.mal.structures.Identifier) valueByName("userID");
    }

    /**
     * Returns the value of the "userReference" Subscription Key, or null if not
     * present.
     * 
     * @return The key value, or null if not present
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getUserReference() {
        return (org.ccsds.moims.mo.mal.structures.Identifier) valueByName("userReference");
    }

    /**
     * Returns the value of the "status" Subscription Key, or null if not present.
     * 
     * @return The key value, or null if not present
     */
    public org.ccsds.moims.mo.mal.structures.UShort getStatus() {
        return (org.ccsds.moims.mo.mal.structures.UShort) valueByName("status");
    }

    /**
     * Returns the value of the "outputPlanID" Subscription Key, or null if not
     * present.
     * 
     * @return The key value, or null if not present
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getOutputPlanID() {
        return (org.ccsds.moims.mo.mal.structures.Identifier) valueByName("outputPlanID");
    }

    /**
     * Returns the Subscription Key value with the given name, or null if it is
     * not present (for example when it was trimmed away or is a custom key that
     * is not part of this subscription).
     * 
     * @param name The Subscription Key name
     * @return The key value, or null if not present
     */
    public org.ccsds.moims.mo.mal.structures.Attribute getByName(String name) {
        return valueByName(name);
    }

    /**
     * 
     * @param name The Subscription Key name
     */
    private org.ccsds.moims.mo.mal.structures.Attribute valueByName(String name) {
        if (keyNames == null || keyValues == null) {
            return null;
        }
        for (int i = 0; i < keyNames.size(); i++) {
            if (name.equals(keyNames.get(i).getValue())) {
                if (i >= keyValues.size()) {
                    return null;
                }
                org.ccsds.moims.mo.mal.structures.NullableAttribute na = keyValues.get(i);
                return (na == null) ? null : na.getValue();
            }
        }
        return null;
    }

}
