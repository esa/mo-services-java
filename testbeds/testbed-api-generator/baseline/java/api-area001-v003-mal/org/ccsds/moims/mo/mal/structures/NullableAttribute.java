package org.ccsds.moims.mo.mal.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;

/**
 * NullableAttribute structure shall represent an Attribute that can be nullable.
 */
public final class NullableAttribute implements Composite {

    private static final long serialVersionUID = 281475027043314L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475027043314L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The value of the nullable attribute.
     */
    private Attribute value;

    /**
     * Default constructor for NullableAttribute.
     * 
     */
    public NullableAttribute() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param value The value of the nullable attribute.
     */
    public NullableAttribute(Attribute value) {
        this.value = value;
    }

    @Override
    public Element createElement() {
        return new NullableAttribute();
    }

    /**
     * Returns the field value.
     * 
     * @return The field value
     */
    public Attribute getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NullableAttribute) {
            NullableAttribute other = (NullableAttribute) obj;
            if (value == null) {
                if (other.value != null) {
                    return false;
                }
            } else {
                if (! value.equals(other.value)) {
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
        hash = 83 * hash + (value != null ? value.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(NullableAttribute: ");
        buf.append("value=").append(value);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        encoder.encodeNullableAttribute(value);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        value = (Attribute) decoder.decodeNullableAttribute();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
