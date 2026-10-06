package org.ccsds.moims.mo.mc.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * The AttributeValue structure holds an Attribute value. It allows a list
 * of different Attribute types to be created whereas List of Attribute would
 * require the values to be all of the same type.
 */
public final class AttributeValue implements Composite {

    private static final long serialVersionUID = 1125899923619842L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899923619842L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The argument value. Must not be NULL. NULL may be represented by having
     * a NULL in place of the complete AttributeValue composite.
     */
    private Attribute value;

    /**
     * Default constructor for AttributeValue.
     * 
     */
    public AttributeValue() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param value The argument value. Must not be NULL. NULL may be represented by having a NULL in place of the complete AttributeValue composite.
     */
    public AttributeValue(Attribute value) {
        this.value = value;
    }

    @Override
    public Element createElement() {
        return new AttributeValue();
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
        if (obj instanceof AttributeValue) {
            AttributeValue other = (AttributeValue) obj;
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
        buf.append("(AttributeValue: ");
        buf.append("value=").append(value);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (value == null) {
            throw new MALException("The field 'value' cannot be null!");
        }
        encoder.encodeAttribute(value);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        value = (Attribute) decoder.decodeAttribute();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
