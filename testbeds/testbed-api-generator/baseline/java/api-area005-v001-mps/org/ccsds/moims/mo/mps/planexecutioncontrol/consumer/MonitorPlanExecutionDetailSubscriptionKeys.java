package org.ccsds.moims.mo.mps.planexecutioncontrol.consumer;

/**
 * Typed accessors for the Subscription Keys of the monitorPlanExecutionDetail
 * PubSub operation.
 */
public final class MonitorPlanExecutionDetailSubscriptionKeys {

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
    private static final org.ccsds.moims.mo.mal.structures.IdentifierList CANONICAL_KEY_NAMES = new org.ccsds.moims.mo.mal.structures.IdentifierList(new java.util.ArrayList<>(java.util.Arrays.asList(new org.ccsds.moims.mo.mal.structures.Identifier("planID"), new org.ccsds.moims.mo.mal.structures.Identifier("subPlan"), new org.ccsds.moims.mo.mal.structures.Identifier("tag"), new org.ccsds.moims.mo.mal.structures.Identifier("type"))));

    /**
     * Creates an instance from the received UpdateHeader and the subscription
     * selectedKeys.
     * 
     * @param updateHeader The UpdateHeader received in the NOTIFY message
     * @param selectedKeys The selectedKeys of the subscription, or null if trimming was not enabled
     */
    public MonitorPlanExecutionDetailSubscriptionKeys(org.ccsds.moims.mo.mal.structures.UpdateHeader updateHeader,
            org.ccsds.moims.mo.mal.structures.IdentifierList selectedKeys) {
        this.keyValues = (updateHeader == null) ? null : updateHeader.getKeyValues();
        this.keyNames = (selectedKeys != null) ? selectedKeys : CANONICAL_KEY_NAMES;
    }

    /**
     * Returns the value of the "planID" Subscription Key, or null if not present.
     * 
     * @return The key value, or null if not present
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getPlanID() {
        return (org.ccsds.moims.mo.mal.structures.Identifier) valueByName("planID");
    }

    /**
     * Returns the value of the "subPlan" Subscription Key, or null if not present.
     * 
     * @return The key value, or null if not present
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getSubPlan() {
        return (org.ccsds.moims.mo.mal.structures.Identifier) valueByName("subPlan");
    }

    /**
     * Returns the value of the "tag" Subscription Key, or null if not present.
     * 
     * @return The key value, or null if not present
     */
    public String getTag() {
        org.ccsds.moims.mo.mal.structures.Attribute v = valueByName("tag");
        return (v == null) ? null : (String) org.ccsds.moims.mo.mal.structures.Attribute.attribute2JavaType(v);
    }

    /**
     * Returns the value of the "type" Subscription Key, or null if not present.
     * 
     * @return The key value, or null if not present
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getType() {
        return (org.ccsds.moims.mo.mal.structures.Identifier) valueByName("type");
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
