package org.ccsds.moims.mo.mal.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;

/**
 * Pair shall be a simple Composite structure for holding pairs.
 */
public final class Pair implements Composite {

    private static final long serialVersionUID = 281475027043309L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475027043309L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The attribute value for the first Element of this pair.
     */
    private Attribute first;

    /**
     * The attribute value for the second Element of this pair.
     */
    private Attribute second;

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
    public Pair(Attribute first,
            Attribute second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public Element createElement() {
        return new Pair();
    }

    /**
     * Returns the field first.
     * 
     * @return The field first
     */
    public Attribute getFirst() {
        return first;
    }

    /**
     * Returns the field second.
     * 
     * @return The field second
     */
    public Attribute getSecond() {
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
    public void encode(MALEncoder encoder) throws MALException {
        encoder.encodeNullableAttribute(first);
        encoder.encodeNullableAttribute(second);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        first = (Attribute) decoder.decodeNullableAttribute();
        second = (Attribute) decoder.decodeNullableAttribute();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
