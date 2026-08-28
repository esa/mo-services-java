package org.ccsds.moims.mo.mps.structures;

/**
 * E1: Abstract type that is used to represent an allowed range of values
 * for a given Argument or Resource.
 */
public abstract class ValidationDetails implements org.ccsds.moims.mo.mal.structures.Composite {

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
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        return this;
    }

}
