package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E1: Abstract type that is used to represent an allowed range of values
 * for a given Argument or Resource.
 */
public abstract class ValidationDetails implements Composite {

    /**
     * Default constructor for ValidationDetails.
     * 
     */
    public ValidationDetails() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ValidationDetails) {
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
        buf.append("(ValidationDetails: ");
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
