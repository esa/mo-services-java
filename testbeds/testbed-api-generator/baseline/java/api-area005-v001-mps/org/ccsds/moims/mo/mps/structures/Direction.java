package org.ccsds.moims.mo.mps.structures;

/**
 * E6: Abstract type that represents a unique direction in three-dimensional
 * space.  The actual manner in which this direction is evaluated depends
 * on the concrete subtype used.
 */
public abstract class Direction implements org.ccsds.moims.mo.mal.structures.Composite {

    /**
     * Default constructor for Direction.
     * 
     */
    public Direction() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Direction) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(Direction: ");
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        return this;
    }

}
