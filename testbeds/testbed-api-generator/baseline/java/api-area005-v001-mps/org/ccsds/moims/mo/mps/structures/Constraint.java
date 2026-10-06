package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E1: Abstract type representing a planning constraint, a Boolean condition
 * which restricts the planning of planning activities.
 */
public abstract class Constraint implements Composite {

    /**
     * Specifies whether the result of combining the Constraints is to be inverted
     * (NOT function). Default = False.
     */
    private Boolean negate;

    /**
     * Default constructor for Constraint.
     * 
     */
    public Constraint() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param negate Specifies whether the result of combining the Constraints is to be inverted (NOT function). Default = False.
     */
    public Constraint(Boolean negate) {
        this.negate = negate;
    }

    /**
     * Returns the field negate.
     * 
     * @return The field negate
     */
    public Boolean getNegate() {
        return negate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Constraint) {
            Constraint other = (Constraint) obj;
            if (negate == null) {
                if (other.negate != null) {
                    return false;
                }
            } else {
                if (! negate.equals(other.negate)) {
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
        hash = 83 * hash + (negate != null ? negate.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(Constraint: ");
        buf.append("negate=").append(negate);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        encoder.encodeNullableBoolean(negate);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        negate = decoder.decodeNullableBoolean();
        return this;
    }

}
