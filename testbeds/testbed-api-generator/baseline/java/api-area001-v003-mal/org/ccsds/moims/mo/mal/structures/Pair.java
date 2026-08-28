package org.ccsds.moims.mo.mal.structures;

/**
 * Pair shall be a simple Composite structure for holding pairs.
 */
public final class Pair implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 281475027043309L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475027043309L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The attribute value for the first Element of this pair.
     */
    private org.ccsds.moims.mo.mal.structures.Attribute first;

    /**
     * The attribute value for the second Element of this pair.
     */
    private org.ccsds.moims.mo.mal.structures.Attribute second;

    /**
     * Default constructor for Pair.
     * 
     */
    public Pair() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param first The attribute value for the first Element of this pair.
     * @param second The attribute value for the second Element of this pair.
     */
    public Pair(org.ccsds.moims.mo.mal.structures.Attribute first,
            org.ccsds.moims.mo.mal.structures.Attribute second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mal.structures.Pair();
    }

    /**
     * Returns the field first.
     * 
     * @return The field first
     */
    public org.ccsds.moims.mo.mal.structures.Attribute getFirst() {
        return first;
    }

    /**
     * Returns the field second.
     * 
     * @return The field second
     */
    public org.ccsds.moims.mo.mal.structures.Attribute getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pair) {
            Pair other = (Pair) obj;
            if (first == null) {
                if (other.first != null) {
                    return false;
                }
            } else {
                if (! first.equals(other.first)) {
                    return false;
                }
            }
            if (second == null) {
                if (other.second != null) {
                    return false;
                }
            } else {
                if (! second.equals(other.second)) {
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
        hash = 83 * hash + (first != null ? first.hashCode() : 0);
        hash = 83 * hash + (second != null ? second.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(Pair: ");
        buf.append("first=").append(first);
        buf.append(", second=").append(second);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        encoder.encodeNullableAttribute(first);
        encoder.encodeNullableAttribute(second);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        first = (org.ccsds.moims.mo.mal.structures.Attribute) decoder.decodeNullableAttribute();
        second = (org.ccsds.moims.mo.mal.structures.Attribute) decoder.decodeNullableAttribute();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
