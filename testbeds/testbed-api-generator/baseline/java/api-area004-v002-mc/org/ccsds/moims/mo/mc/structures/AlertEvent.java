package org.ccsds.moims.mo.mc.structures;

/**
 * The AlertEvent structure shall be used to hold the details of an instance
 * of an alert.
 */
public final class AlertEvent implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125899940397087L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397087L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The alertRef field.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mc.structures.AlertDefinition> alertRef;

    /**
     * The timestamp field.
     */
    private org.ccsds.moims.mo.mal.structures.Time timestamp;

    /**
     * The argumentValues field.
     */
    private org.ccsds.moims.mo.mal.structures.NullableAttributeList argumentValues;

    /**
     * Default constructor for AlertEvent.
     * 
     */
    public AlertEvent() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param alertRef The alertRef field.
     * @param timestamp The timestamp field.
     * @param argumentValues The argumentValues field.
     */
    public AlertEvent(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mc.structures.AlertDefinition> alertRef,
            org.ccsds.moims.mo.mal.structures.Time timestamp,
            org.ccsds.moims.mo.mal.structures.NullableAttributeList argumentValues) {
        this.alertRef = alertRef;
        this.timestamp = timestamp;
        this.argumentValues = argumentValues;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param alertRef The alertRef field.
     * @param timestamp The timestamp field.
     */
    public AlertEvent(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mc.structures.AlertDefinition> alertRef,
            org.ccsds.moims.mo.mal.structures.Time timestamp) {
        this.alertRef = alertRef;
        this.timestamp = timestamp;
        this.argumentValues = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.structures.AlertEvent();
    }

    /**
     * Returns the field alertRef.
     * 
     * @return The field alertRef
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mc.structures.AlertDefinition> getAlertRef() {
        return alertRef;
    }

    /**
     * Returns the field timestamp.
     * 
     * @return The field timestamp
     */
    public org.ccsds.moims.mo.mal.structures.Time getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the field argumentValues.
     * 
     * @return The field argumentValues
     */
    public org.ccsds.moims.mo.mal.structures.NullableAttributeList getArgumentValues() {
        return argumentValues;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AlertEvent) {
            AlertEvent other = (AlertEvent) obj;
            if (alertRef == null) {
                if (other.alertRef != null) {
                    return false;
                }
            } else {
                if (! alertRef.equals(other.alertRef)) {
                    return false;
                }
            }
            if (timestamp == null) {
                if (other.timestamp != null) {
                    return false;
                }
            } else {
                if (! timestamp.equals(other.timestamp)) {
                    return false;
                }
            }
            if (argumentValues == null) {
                if (other.argumentValues != null) {
                    return false;
                }
            } else {
                if (! argumentValues.equals(other.argumentValues)) {
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
        hash = 83 * hash + (alertRef != null ? alertRef.hashCode() : 0);
        hash = 83 * hash + (timestamp != null ? timestamp.hashCode() : 0);
        hash = 83 * hash + (argumentValues != null ? argumentValues.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(AlertEvent: ");
        buf.append("alertRef=").append(alertRef);
        buf.append(", timestamp=").append(timestamp);
        buf.append(", argumentValues=").append(argumentValues);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (alertRef == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'alertRef' cannot be null!");
        }
        if (timestamp == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'timestamp' cannot be null!");
        }
        encoder.encodeElement(alertRef);
        encoder.encodeTime(timestamp);
        encoder.encodeNullableElement(argumentValues);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        alertRef = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mc.structures.AlertDefinition>) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mc.structures.AlertDefinition>());
        timestamp = decoder.decodeTime();
        argumentValues = (org.ccsds.moims.mo.mal.structures.NullableAttributeList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.NullableAttributeList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
