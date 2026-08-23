package org.ccsds.moims.mo.mc.check.structures;

/**
 * The ReferenceValue structure defines a value to compare against. A validCount
 * of &quot;1&quot; and deltaTime of &quot;0&quot; would compare against the
 * previous sample value.
 */
public final class ReferenceValue implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125917103489031L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125917103489031L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Number of valid samples that should be collected to update the reference
     * value.
     */
    private org.ccsds.moims.mo.mal.structures.UShort validCount;

    /**
     * Delta time from now into the past from which the reference value should
     * be sampled.
     */
    private org.ccsds.moims.mo.mal.structures.Duration deltaTime;

    /**
     * The ParameterIdentity object to compare against. If NULL, then checked
     * parameter should be compared against itself.
     */
    private org.ccsds.moims.mo.com.structures.ObjectKey parameterId;

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
    public ReferenceValue(org.ccsds.moims.mo.mal.structures.UShort validCount,
            org.ccsds.moims.mo.mal.structures.Duration deltaTime,
            org.ccsds.moims.mo.com.structures.ObjectKey parameterId) {
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
    public ReferenceValue(org.ccsds.moims.mo.mal.structures.UShort validCount,
            org.ccsds.moims.mo.mal.structures.Duration deltaTime) {
        this.validCount = validCount;
        this.deltaTime = deltaTime;
        this.parameterId = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.check.structures.ReferenceValue();
    }

    /**
     * Returns the field validCount.
     * 
     * @return The field validCount
     */
    public org.ccsds.moims.mo.mal.structures.UShort getValidCount() {
        return validCount;
    }

    /**
     * Returns the field deltaTime.
     * 
     * @return The field deltaTime
     */
    public org.ccsds.moims.mo.mal.structures.Duration getDeltaTime() {
        return deltaTime;
    }

    /**
     * Returns the field parameterId.
     * 
     * @return The field parameterId
     */
    public org.ccsds.moims.mo.com.structures.ObjectKey getParameterId() {
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
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (validCount == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'validCount' cannot be null!");
        }
        if (deltaTime == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'deltaTime' cannot be null!");
        }
        encoder.encodeUShort(validCount);
        encoder.encodeDuration(deltaTime);
        encoder.encodeNullableElement(parameterId);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        validCount = decoder.decodeUShort();
        deltaTime = decoder.decodeDuration();
        parameterId = (org.ccsds.moims.mo.com.structures.ObjectKey) decoder.decodeNullableElement(new org.ccsds.moims.mo.com.structures.ObjectKey());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
