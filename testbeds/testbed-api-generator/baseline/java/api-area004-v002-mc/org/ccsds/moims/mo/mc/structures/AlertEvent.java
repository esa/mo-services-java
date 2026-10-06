package org.ccsds.moims.mo.mc.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Time;

/**
 * The AlertEvent structure shall be used to hold the details of an instance
 * of an alert.
 */
public final class AlertEvent implements Composite {

    private static final long serialVersionUID = 1125899940397087L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397087L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The alertRef field.
     */
    private ObjectRef<AlertDefinition> alertRef;

    /**
     * The timestamp field.
     */
    private Time timestamp;

    /**
     * The argumentValues field.
     */
    private NullableAttributeList argumentValues;

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
    public AlertEvent(ObjectRef<AlertDefinition> alertRef,
            Time timestamp,
            NullableAttributeList argumentValues) {
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
    public AlertEvent(ObjectRef<AlertDefinition> alertRef,
            Time timestamp) {
        this.alertRef = alertRef;
        this.timestamp = timestamp;
        this.argumentValues = null;
    }

    @Override
    public Element createElement() {
        return new AlertEvent();
    }

    /**
     * Returns the field alertRef.
     * 
     * @return The field alertRef
     */
    public ObjectRef<AlertDefinition> getAlertRef() {
        return alertRef;
    }

    /**
     * Returns the field timestamp.
     * 
     * @return The field timestamp
     */
    public Time getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the field argumentValues.
     * 
     * @return The field argumentValues
     */
    public NullableAttributeList getArgumentValues() {
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
    public void encode(MALEncoder encoder) throws MALException {
        if (alertRef == null) {
            throw new MALException("The field 'alertRef' cannot be null!");
        }
        if (timestamp == null) {
            throw new MALException("The field 'timestamp' cannot be null!");
        }
        encoder.encodeElement(alertRef);
        encoder.encodeTime(timestamp);
        encoder.encodeNullableElement(argumentValues);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        alertRef = (ObjectRef<AlertDefinition>) decoder.decodeElement(new ObjectRef<AlertDefinition>());
        timestamp = decoder.decodeTime();
        argumentValues = (NullableAttributeList) decoder.decodeNullableElement(new NullableAttributeList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
