package org.ccsds.moims.mo.mc.structures;

/**
 * The AttributeValue structure holds an Attribute value. It allows a list
 * of different Attribute types to be created whereas List of Attribute would
 * require the values to be all of the same type.
 */
public final class AttributeValue implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125899923619842L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899923619842L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The argument value. Must not be NULL. NULL may be represented by having
     * a NULL in place of the complete AttributeValue composite.
     */
    private org.ccsds.moims.mo.mal.structures.Attribute value;

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
    public AttributeValue(org.ccsds.moims.mo.mal.structures.Attribute value) {
        this.value = value;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.structures.AttributeValue();
    }

    /**
     * Returns the field value.
     * 
     * @return The field value
     */
    public org.ccsds.moims.mo.mal.structures.Attribute getValue() {
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
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (value == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'value' cannot be null!");
        }
        encoder.encodeAttribute(value);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        value = (org.ccsds.moims.mo.mal.structures.Attribute) decoder.decodeAttribute();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
