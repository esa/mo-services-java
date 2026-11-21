package org.ccsds.moims.mo.mc.check.structures;

import org.ccsds.moims.mo.com.structures.ObjectKey;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * The ReferenceValue structure defines a value to compare against. A validCount
 * of &quot;1&quot; and deltaTime of &quot;0&quot; would compare against the
 * previous sample value.
 */
public final class ReferenceValue implements Composite {

    private static final long serialVersionUID = 1125917103489031L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125917103489031L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Number of valid samples that should be collected to update the reference
     * value.
     */
    private UShort validCount;

    /**
     * Delta time from now into the past from which the reference value should
     * be sampled.
     */
    private Duration deltaTime;

    /**
     * The ParameterIdentity object to compare against. If NULL, then checked
     * parameter should be compared against itself.
     */
    private ObjectKey parameterId;

    /**
     * Default constructor for ReferenceValue.
     * 
     */
    public ReferenceValue() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param validCount Number of valid samples that should be collected to update the reference value.
     * @param deltaTime Delta time from now into the past from which the reference value should be sampled.
     * @param parameterId The ParameterIdentity object to compare against. If NULL, then checked parameter should be compared against itself.
     */
    public ReferenceValue(UShort validCount,
            Duration deltaTime,
            ObjectKey parameterId) {
        this.validCount = validCount;
        this.deltaTime = deltaTime;
        this.parameterId = parameterId;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param validCount Number of valid samples that should be collected to update the reference value.
     * @param deltaTime Delta time from now into the past from which the reference value should be sampled.
     */
    public ReferenceValue(UShort validCount,
            Duration deltaTime) {
        this.validCount = validCount;
        this.deltaTime = deltaTime;
        this.parameterId = null;
    }

    @Override
    public Element createElement() {
        return new ReferenceValue();
    }

    /**
     * Returns the field validCount.
     * 
     * @return The field validCount
     */
    public UShort getValidCount() {
        return validCount;
    }

    /**
     * Returns the field deltaTime.
     * 
     * @return The field deltaTime
     */
    public Duration getDeltaTime() {
        return deltaTime;
    }

    /**
     * Returns the field parameterId.
     * 
     * @return The field parameterId
     */
    public ObjectKey getParameterId() {
        return parameterId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ReferenceValue) {
            ReferenceValue other = (ReferenceValue) obj;
            if (validCount == null) {
                if (other.validCount != null) {
                    return false;
                }
            } else {
                if (! validCount.equals(other.validCount)) {
                    return false;
                }
            }
            if (deltaTime == null) {
                if (other.deltaTime != null) {
                    return false;
                }
            } else {
                if (! deltaTime.equals(other.deltaTime)) {
                    return false;
                }
            }
            if (parameterId == null) {
                if (other.parameterId != null) {
                    return false;
                }
            } else {
                if (! parameterId.equals(other.parameterId)) {
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
        hash = 83 * hash + (validCount != null ? validCount.hashCode() : 0);
        hash = 83 * hash + (deltaTime != null ? deltaTime.hashCode() : 0);
        hash = 83 * hash + (parameterId != null ? parameterId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ReferenceValue: ");
        buf.append("validCount=").append(validCount);
        buf.append(", deltaTime=").append(deltaTime);
        buf.append(", parameterId=").append(parameterId);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (validCount == null) {
            throw new MALException("The field 'validCount' cannot be null!");
        }
        if (deltaTime == null) {
            throw new MALException("The field 'deltaTime' cannot be null!");
        }
        encoder.encodeUShort(validCount);
        encoder.encodeDuration(deltaTime);
        encoder.encodeNullableElement(parameterId);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        validCount = decoder.decodeUShort();
        deltaTime = decoder.decodeDuration();
        parameterId = (ObjectKey) decoder.decodeNullableElement(new ObjectKey());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
