package org.ccsds.moims.mo.mps.structures;

/**
 * E4: An additional concrete sub-type of ValidationDetails applicable only
 * to Resources of any numeric type, including Duration, that provides additional
 * fields for the specification of numeric data validation.
 */
public final class NumericResource extends org.ccsds.moims.mo.mps.structures.ValidationDetails {

    private static final long serialVersionUID = 1407374900330798L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330798L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Defines the permitted minimum value over time.
     */
    private org.ccsds.moims.mo.mps.structures.ResourceProfile minimum;

    /**
     * Defines the permitted maximum value over time.
     */
    private org.ccsds.moims.mo.mps.structures.ResourceProfile maximum;

    /**
     * Default constructor for NumericResource.
     * 
     */
    public NumericResource() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param minimum Defines the permitted minimum value over time.
     * @param maximum Defines the permitted maximum value over time.
     */
    public NumericResource(org.ccsds.moims.mo.mps.structures.ResourceProfile minimum,
            org.ccsds.moims.mo.mps.structures.ResourceProfile maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.NumericResource();
    }

    /**
     * Returns the field minimum.
     * 
     * @return The field minimum
     */
    public org.ccsds.moims.mo.mps.structures.ResourceProfile getMinimum() {
        return minimum;
    }

    /**
     * Returns the field maximum.
     * 
     * @return The field maximum
     */
    public org.ccsds.moims.mo.mps.structures.ResourceProfile getMaximum() {
        return maximum;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NumericResource) {
            if (! super.equals(obj)) {
                return false;
            }
            NumericResource other = (NumericResource) obj;
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
        buf.append("(NumericResource: ");
        buf.append(super.toString());
        buf.append(", minimum=").append(minimum);
        buf.append(", maximum=").append(maximum);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (minimum == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'minimum' cannot be null!");
        }
        if (maximum == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'maximum' cannot be null!");
        }
        encoder.encodeElement(minimum);
        encoder.encodeElement(maximum);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        minimum = (org.ccsds.moims.mo.mps.structures.ResourceProfile) decoder.decodeElement(new org.ccsds.moims.mo.mps.structures.ResourceProfile());
        maximum = (org.ccsds.moims.mo.mps.structures.ResourceProfile) decoder.decodeElement(new org.ccsds.moims.mo.mps.structures.ResourceProfile());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
