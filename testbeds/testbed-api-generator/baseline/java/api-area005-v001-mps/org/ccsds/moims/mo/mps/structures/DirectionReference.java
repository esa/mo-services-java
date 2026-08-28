package org.ccsds.moims.mo.mps.structures;

/**
 * E6: A DirectionReference is a Direction that may be computed following
 * some mission specific definition.
 */
public final class DirectionReference extends org.ccsds.moims.mo.mps.structures.Direction {

    private static final long serialVersionUID = 1407374900330514L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330514L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Name of a mission specific direction definition.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier reference;

    /**
     * Default constructor for DirectionReference.
     * 
     */
    public DirectionReference() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param reference Name of a mission specific direction definition.
     */
    public DirectionReference(org.ccsds.moims.mo.mal.structures.Identifier reference) {
        this.reference = reference;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.DirectionReference();
    }

    /**
     * Returns the field reference.
     * 
     * @return The field reference
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getReference() {
        return reference;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DirectionReference) {
            if (! super.equals(obj)) {
                return false;
            }
            DirectionReference other = (DirectionReference) obj;
            if (reference == null) {
                if (other.reference != null) {
                    return false;
                }
            } else {
                if (! reference.equals(other.reference)) {
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
        hash = 83 * hash + (reference != null ? reference.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(DirectionReference: ");
        buf.append(super.toString());
        buf.append(", reference=").append(reference);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (reference == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'reference' cannot be null!");
        }
        encoder.encodeIdentifier(reference);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        reference = decoder.decodeIdentifier();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
