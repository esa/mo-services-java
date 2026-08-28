package org.ccsds.moims.mo.mc.structures;

/**
 * The PacketValue structure is used to represent each space packet published
 * by the provider.
 */
public final class PacketValue implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125899940397146L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397146L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The payload field.
     */
    private org.ccsds.moims.mo.mal.structures.Blob payload;

    /**
     * The timestamp field.
     */
    private org.ccsds.moims.mo.mal.structures.Time timestamp;

    /**
     * The apid field.
     */
    private org.ccsds.moims.mo.mal.structures.UShort apid;

    /**
     * The keyValues field.
     */
    private org.ccsds.moims.mo.mal.structures.NullableAttributeList keyValues;

    /**
     * Default constructor for PacketValue.
     * 
     */
    public PacketValue() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param payload The payload field.
     * @param timestamp The timestamp field.
     * @param apid The apid field.
     * @param keyValues The keyValues field.
     */
    public PacketValue(org.ccsds.moims.mo.mal.structures.Blob payload,
            org.ccsds.moims.mo.mal.structures.Time timestamp,
            org.ccsds.moims.mo.mal.structures.UShort apid,
            org.ccsds.moims.mo.mal.structures.NullableAttributeList keyValues) {
        this.payload = payload;
        this.timestamp = timestamp;
        this.apid = apid;
        this.keyValues = keyValues;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param payload The payload field.
     * @param timestamp The timestamp field.
     * @param apid The apid field.
     */
    public PacketValue(org.ccsds.moims.mo.mal.structures.Blob payload,
            org.ccsds.moims.mo.mal.structures.Time timestamp,
            org.ccsds.moims.mo.mal.structures.UShort apid) {
        this.payload = payload;
        this.timestamp = timestamp;
        this.apid = apid;
        this.keyValues = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.structures.PacketValue();
    }

    /**
     * Returns the field payload.
     * 
     * @return The field payload
     */
    public org.ccsds.moims.mo.mal.structures.Blob getPayload() {
        return payload;
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
     * Returns the field apid.
     * 
     * @return The field apid
     */
    public org.ccsds.moims.mo.mal.structures.UShort getApid() {
        return apid;
    }

    /**
     * Returns the field keyValues.
     * 
     * @return The field keyValues
     */
    public org.ccsds.moims.mo.mal.structures.NullableAttributeList getKeyValues() {
        return keyValues;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PacketValue) {
            PacketValue other = (PacketValue) obj;
            if (payload == null) {
                if (other.payload != null) {
                    return false;
                }
            } else {
                if (! payload.equals(other.payload)) {
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
            if (apid == null) {
                if (other.apid != null) {
                    return false;
                }
            } else {
                if (! apid.equals(other.apid)) {
                    return false;
                }
            }
            if (keyValues == null) {
                if (other.keyValues != null) {
                    return false;
                }
            } else {
                if (! keyValues.equals(other.keyValues)) {
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
        hash = 83 * hash + (payload != null ? payload.hashCode() : 0);
        hash = 83 * hash + (timestamp != null ? timestamp.hashCode() : 0);
        hash = 83 * hash + (apid != null ? apid.hashCode() : 0);
        hash = 83 * hash + (keyValues != null ? keyValues.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(PacketValue: ");
        buf.append("payload=").append(payload);
        buf.append(", timestamp=").append(timestamp);
        buf.append(", apid=").append(apid);
        buf.append(", keyValues=").append(keyValues);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (payload == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'payload' cannot be null!");
        }
        if (timestamp == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'timestamp' cannot be null!");
        }
        if (apid == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'apid' cannot be null!");
        }
        encoder.encodeBlob(payload);
        encoder.encodeTime(timestamp);
        encoder.encodeUShort(apid);
        encoder.encodeNullableElement(keyValues);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        payload = decoder.decodeBlob();
        timestamp = decoder.decodeTime();
        apid = decoder.decodeUShort();
        keyValues = (org.ccsds.moims.mo.mal.structures.NullableAttributeList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.NullableAttributeList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
