package org.ccsds.moims.mo.mps.structures;

/**
 * E6: Physical value with units of type AngularVelocity.
 */
public final class AngularVelocity extends org.ccsds.moims.mo.mps.structures.PhysicalValue {

    private static final long serialVersionUID = 1407374900330516L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330516L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Default constructor for AngularVelocity.
     * 
     */
    public AngularVelocity() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param value Physical value.
     * @param units Optional units.  The units for a single quantity.  The unit type depends on the specific value type.
     */
    public AngularVelocity(Double value,
            String units) {
        super(value,
            units);
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param value Physical value.
     */
    public AngularVelocity(Double value) {
        super(value);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.AngularVelocity();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AngularVelocity) {
            if (! super.equals(obj)) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(AngularVelocity: ");
        buf.append(super.toString());
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
