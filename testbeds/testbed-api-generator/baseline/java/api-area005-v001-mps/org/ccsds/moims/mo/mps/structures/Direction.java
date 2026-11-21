package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E6: Abstract type that represents a unique direction in three-dimensional
 * space.  The actual manner in which this direction is evaluated depends
 * on the concrete subtype used.
 */
public abstract class Direction implements Composite {

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
    public void encode(MALEncoder encoder) throws MALException {
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        return this;
    }

}
