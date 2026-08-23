package org.ccsds.moims.mo.mc.parameter.consumer;

/**
 * Typed accessors for the Subscription Keys of the monitorValue PubSub operation.
 */
public final class MonitorValueSubscriptionKeys {

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
    private static final org.ccsds.moims.mo.mal.structures.IdentifierList CANONICAL_KEY_NAMES = new org.ccsds.moims.mo.mal.structures.IdentifierList(new java.util.ArrayList<>(java.util.Arrays.asList(new org.ccsds.moims.mo.mal.structures.Identifier("parameterKey"), new org.ccsds.moims.mo.mal.structures.Identifier("parameterVersion"))));

    /**
     * Creates an instance from the received UpdateHeader and the subscription
     * selectedKeys.
     * 
     * @param updateHeader The UpdateHeader received in the NOTIFY message
     * @param selectedKeys The selectedKeys of the subscription, or null if trimming was not enabled
     */
    public MonitorValueSubscriptionKeys(org.ccsds.moims.mo.mal.structures.UpdateHeader updateHeader,
            org.ccsds.moims.mo.mal.structures.IdentifierList selectedKeys) {
        this.keyValues = (updateHeader == null) ? null : updateHeader.getKeyValues();
        this.keyNames = (selectedKeys != null) ? selectedKeys : CANONICAL_KEY_NAMES;
    }

    /**
     * Returns the value of the "parameterKey" Subscription Key, or null if not
     * present.
     * 
     * @return The key value, or null if not present
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getParameterKey() {
        return (org.ccsds.moims.mo.mal.structures.Identifier) valueByName("parameterKey");
    }

    /**
     * Returns the value of the "parameterVersion" Subscription Key, or null if
     * not present.
     * 
     * @return The key value, or null if not present
     */
    public org.ccsds.moims.mo.mal.structures.UInteger getParameterVersion() {
        return (org.ccsds.moims.mo.mal.structures.UInteger) valueByName("parameterVersion");
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
