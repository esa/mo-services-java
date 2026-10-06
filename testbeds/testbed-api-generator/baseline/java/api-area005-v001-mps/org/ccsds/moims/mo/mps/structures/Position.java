package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E6: Abstract type that represents a unique position in three-dimensional
 * space.  Depending on the concrete subtype used, the actual position may
 * be derived in different manners.
 */
public abstract class Position implements Composite {

    /**
     * Default constructor for Position.
     * 
     */
    public Position() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
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
        buf.append("(Position: ");
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        return this;
    }

}
