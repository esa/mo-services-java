package org.ccsds.moims.mo.mps.structures;

/**
 * E1: Concrete sub-type of ValidationDetails that provides additional fields
 * to support data validation for numeric data types.
 */
public final class NumericRange extends org.ccsds.moims.mo.mps.structures.ValidationDetails {

    private static final long serialVersionUID = 1407374900330522L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330522L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Minimum value of the argument; if omitted, no minimum value is considered.
     */
    private Double min;

    /**
     * Maximum value of the argument; if omitted, no maximum value is considered.
     */
    private Double max;

    /**
     * Default constructor for NumericRange.
     * 
     */
    public NumericRange() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param min Minimum value of the argument; if omitted, no minimum value is considered.
     * @param max Maximum value of the argument; if omitted, no maximum value is considered.
     */
    public NumericRange(Double min,
            Double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.NumericRange();
    }

    /**
     * Returns the field min.
     * 
     * @return The field min
     */
    public Double getMin() {
        return min;
    }

    /**
     * Returns the field max.
     * 
     * @return The field max
     */
    public Double getMax() {
        return max;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NumericRange) {
            if (! super.equals(obj)) {
                return false;
            }
            NumericRange other = (NumericRange) obj;
            if (min == null) {
                if (other.min != null) {
                    return false;
                }
            } else {
                if (! min.equals(other.min)) {
                    return false;
                }
            }
            if (max == null) {
                if (other.max != null) {
                    return false;
                }
            } else {
                if (! max.equals(other.max)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 83 * hash + (min != null ? min.hashCode() : 0);
        hash = 83 * hash + (max != null ? max.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(NumericRange: ");
        buf.append(super.toString());
        buf.append(", min=").append(min);
        buf.append(", max=").append(max);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        encoder.encodeNullableDouble(min);
        encoder.encodeNullableDouble(max);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        min = decoder.decodeNullableDouble();
        max = decoder.decodeNullableDouble();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
