package org.ccsds.moims.mo.mps.structures;

/**
 * E1: Used to indicate a relative position with respect to an MPS object,
 * such as a planning activity where 0 represents the start and 1 the end
 * of the activity.  The slider is a real number that can represent any point
 * between these two extremes.
 */
public final class Slider implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1407374900330499L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330499L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Relative point between the start and end of an MPS object, where 0 represents
     * the start and 1 represents the end.
     */
    private Float position;

    /**
     * Default constructor for Slider.
     * 
     */
    public Slider() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param position Relative point between the start and end of an MPS object, where 0 represents the start and 1 represents the end.
     */
    public Slider(Float position) {
        this.position = position;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.Slider();
    }

    /**
     * Returns the field position.
     * 
     * @return The field position
     */
    public Float getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Slider) {
            Slider other = (Slider) obj;
            if (position == null) {
                if (other.position != null) {
                    return false;
                }
            } else {
                if (! position.equals(other.position)) {
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
        hash = 83 * hash + (position != null ? position.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(Slider: ");
        buf.append("position=").append(position);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (position == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'position' cannot be null!");
        }
        encoder.encodeFloat(position);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        position = decoder.decodeFloat();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
