package org.ccsds.moims.mo.mal.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;

/**
 * The NamedValue structure shall represent a simple pair type of an identifier
 * and abstract Attribute value.
 */
public final class NamedValue implements Composite {

    private static final long serialVersionUID = 281475027043310L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475027043310L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The Identifier value.
     */
    private Identifier name;

    /**
     * The Attribute value.
     */
    private Attribute value;

    /**
     * Default constructor for NamedValue.
     * 
     */
    public NamedValue() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param name The Identifier value.
     * @param value The Attribute value.
     */
    public NamedValue(Identifier name,
            Attribute value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param name The Identifier value.
     */
    public NamedValue(Identifier name) {
        this.name = name;
        this.value = null;
    }

    @Override
    public Element createElement() {
        return new NamedValue();
    }

    /**
     * Returns the field name.
     * 
     * @return The field name
     */
    public Identifier getName() {
        return name;
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
        if (obj instanceof NamedValue) {
            NamedValue other = (NamedValue) obj;
            if (name == null) {
                if (other.name != null) {
                    return false;
                }
            } else {
                if (! name.equals(other.name)) {
                    return false;
                }
            }
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
        hash = 83 * hash + (name != null ? name.hashCode() : 0);
        hash = 83 * hash + (value != null ? value.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(NamedValue: ");
        buf.append("name=").append(name);
        buf.append(", value=").append(value);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (name == null) {
            throw new MALException("The field 'name' cannot be null!");
        }
        encoder.encodeIdentifier(name);
        encoder.encodeNullableAttribute(value);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        name = decoder.decodeIdentifier();
        value = (Attribute) decoder.decodeNullableAttribute();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
