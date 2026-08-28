package org.ccsds.moims.mo.mpd.structures;

/**
 * A ValueRange is a concrete subtype of AttributeFilter that allows the specification
 * of an allowed (or disallowed) value range for a metadata attribute.
 */
public final class ValueRange extends org.ccsds.moims.mo.mpd.structures.AttributeFilter {

    private static final long serialVersionUID = 2533274807173129L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 2533274807173129L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The minimum value of the value range (greater than or equal to).
     */
    private org.ccsds.moims.mo.mal.structures.Attribute minimum;

    /**
     * The maximum value of the value range (less than or equal to).
     */
    private org.ccsds.moims.mo.mal.structures.Attribute maximum;

    /**
     * Default constructor for ValueRange.
     * 
     */
    public ValueRange() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param name The name of the metadata attribute to filter. If the product metadata being evaluated does not contain an attribute with this name, then the evaluation of the filter shall be false.
     * @param include Indicates whether the filter is to include [TRUE] or exclude [FALSE] attribute values that match the filter.
     * @param minimum The minimum value of the value range (greater than or equal to).
     * @param maximum The maximum value of the value range (less than or equal to).
     */
    public ValueRange(org.ccsds.moims.mo.mal.structures.Identifier name,
            Boolean include,
            org.ccsds.moims.mo.mal.structures.Attribute minimum,
            org.ccsds.moims.mo.mal.structures.Attribute maximum) {
        super(name,
            include);
        this.minimum = minimum;
        this.maximum = maximum;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param name The name of the metadata attribute to filter. If the product metadata being evaluated does not contain an attribute with this name, then the evaluation of the filter shall be false.
     * @param include Indicates whether the filter is to include [TRUE] or exclude [FALSE] attribute values that match the filter.
     */
    public ValueRange(org.ccsds.moims.mo.mal.structures.Identifier name,
            Boolean include) {
        super(name,
            include);
        this.minimum = null;
        this.maximum = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mpd.structures.ValueRange();
    }

    /**
     * Returns the field minimum.
     * 
     * @return The field minimum
     */
    public org.ccsds.moims.mo.mal.structures.Attribute getMinimum() {
        return minimum;
    }

    /**
     * Returns the field maximum.
     * 
     * @return The field maximum
     */
    public org.ccsds.moims.mo.mal.structures.Attribute getMaximum() {
        return maximum;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ValueRange) {
            if (! super.equals(obj)) {
                return false;
            }
            ValueRange other = (ValueRange) obj;
            if (minimum == null) {
                if (other.minimum != null) {
                    return false;
                }
            } else {
                if (! minimum.equals(other.minimum)) {
                    return false;
                }
            }
            if (maximum == null) {
                if (other.maximum != null) {
                    return false;
                }
            } else {
                if (! maximum.equals(other.maximum)) {
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
        hash = 83 * hash + (minimum != null ? minimum.hashCode() : 0);
        hash = 83 * hash + (maximum != null ? maximum.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ValueRange: ");
        buf.append(super.toString());
        buf.append(", minimum=").append(minimum);
        buf.append(", maximum=").append(maximum);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        encoder.encodeNullableAttribute(minimum);
        encoder.encodeNullableAttribute(maximum);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        minimum = (org.ccsds.moims.mo.mal.structures.Attribute) decoder.decodeNullableAttribute();
        maximum = (org.ccsds.moims.mo.mal.structures.Attribute) decoder.decodeNullableAttribute();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
