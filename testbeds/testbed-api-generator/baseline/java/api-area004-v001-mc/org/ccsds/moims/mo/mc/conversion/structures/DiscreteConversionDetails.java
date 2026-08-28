package org.ccsds.moims.mo.mc.conversion.structures;

/**
 * The DiscreteConversionDetails structure holds a bidirectional conversion
 * between raw and converted values. The first element of the pair is the
 * raw value and the second is the converted value. Both sets of values must
 * be unique.
 */
public final class DiscreteConversionDetails implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125929988390913L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125929988390913L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Defines a mapping between raw and converted values as a discrete set of
     * points. The first entry in the pair is the raw value, and the second entry
     * is the converted value.
     */
    private org.ccsds.moims.mo.mal.structures.PairList mapping;

    /**
     * Default constructor for DiscreteConversionDetails.
     * 
     */
    public DiscreteConversionDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param mapping Defines a mapping between raw and converted values as a discrete set of points. The first entry in the pair is the raw value, and the second entry is the converted value.
     */
    public DiscreteConversionDetails(org.ccsds.moims.mo.mal.structures.PairList mapping) {
        this.mapping = mapping;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.conversion.structures.DiscreteConversionDetails();
    }

    /**
     * Returns the field mapping.
     * 
     * @return The field mapping
     */
    public org.ccsds.moims.mo.mal.structures.PairList getMapping() {
        return mapping;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DiscreteConversionDetails) {
            DiscreteConversionDetails other = (DiscreteConversionDetails) obj;
            if (mapping == null) {
                if (other.mapping != null) {
                    return false;
                }
            } else {
                if (! mapping.equals(other.mapping)) {
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
        hash = 83 * hash + (mapping != null ? mapping.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(DiscreteConversionDetails: ");
        buf.append("mapping=").append(mapping);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (mapping == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'mapping' cannot be null!");
        }
        encoder.encodeElement(mapping);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        mapping = (org.ccsds.moims.mo.mal.structures.PairList) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.PairList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
