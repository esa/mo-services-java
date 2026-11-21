package org.ccsds.moims.mo.mc.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * The PacketValue structure is used to represent each space packet published
 * by the provider.
 */
public final class PacketValue implements Composite {

    private static final long serialVersionUID = 1125899940397146L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397146L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The payload field.
     */
    private Blob payload;

    /**
     * The timestamp field.
     */
    private Time timestamp;

    /**
     * The apid field.
     */
    private UShort apid;

    /**
     * The keyValues field.
     */
    private NullableAttributeList keyValues;

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
    public PacketValue(Blob payload,
            Time timestamp,
            UShort apid,
            NullableAttributeList keyValues) {
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
    public PacketValue(Blob payload,
            Time timestamp,
            UShort apid) {
        this.payload = payload;
        this.timestamp = timestamp;
        this.apid = apid;
        this.keyValues = null;
    }

    @Override
    public Element createElement() {
        return new PacketValue();
    }

    /**
     * Returns the field payload.
     * 
     * @return The field payload
     */
    public Blob getPayload() {
        return payload;
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
     * Returns the field apid.
     * 
     * @return The field apid
     */
    public UShort getApid() {
        return apid;
    }

    /**
     * Returns the field keyValues.
     * 
     * @return The field keyValues
     */
    public NullableAttributeList getKeyValues() {
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
    public void encode(MALEncoder encoder) throws MALException {
        if (payload == null) {
            throw new MALException("The field 'payload' cannot be null!");
        }
        if (timestamp == null) {
            throw new MALException("The field 'timestamp' cannot be null!");
        }
        if (apid == null) {
            throw new MALException("The field 'apid' cannot be null!");
        }
        encoder.encodeBlob(payload);
        encoder.encodeTime(timestamp);
        encoder.encodeUShort(apid);
        encoder.encodeNullableElement(keyValues);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        payload = decoder.decodeBlob();
        timestamp = decoder.decodeTime();
        apid = decoder.decodeUShort();
        keyValues = (NullableAttributeList) decoder.decodeNullableElement(new NullableAttributeList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
