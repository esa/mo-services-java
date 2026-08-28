package org.ccsds.moims.mo.mps.structures;

/**
 * E6: A NamedTargetDirection is a Direction that points to an existing object.
 * The manner in which the planning system derives the value of this Direction
 * from the name of the referenced object is implementation-defined.
 */
public final class NamedTargetDirection extends org.ccsds.moims.mo.mps.structures.Direction {

    private static final long serialVersionUID = 1407374900330513L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330513L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Name or identifier of a catalogued celestial object or a mission specific
     * object (see 4.4.3).
     */
    private org.ccsds.moims.mo.mal.structures.Identifier namedTarget;

    /**
     * Default constructor for NamedTargetDirection.
     * 
     */
    public NamedTargetDirection() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param namedTarget Name or identifier of a catalogued celestial object or a mission specific object (see 4.4.3).
     */
    public NamedTargetDirection(org.ccsds.moims.mo.mal.structures.Identifier namedTarget) {
        this.namedTarget = namedTarget;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.NamedTargetDirection();
    }

    /**
     * Returns the field namedTarget.
     * 
     * @return The field namedTarget
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getNamedTarget() {
        return namedTarget;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NamedTargetDirection) {
            if (! super.equals(obj)) {
                return false;
            }
            NamedTargetDirection other = (NamedTargetDirection) obj;
            if (namedTarget == null) {
                if (other.namedTarget != null) {
                    return false;
                }
            } else {
                if (! namedTarget.equals(other.namedTarget)) {
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
        hash = 83 * hash + (namedTarget != null ? namedTarget.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(NamedTargetDirection: ");
        buf.append(super.toString());
        buf.append(", namedTarget=").append(namedTarget);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (namedTarget == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'namedTarget' cannot be null!");
        }
        encoder.encodeIdentifier(namedTarget);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        namedTarget = decoder.decodeIdentifier();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
