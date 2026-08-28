package org.ccsds.moims.mo.mps.structures;

/**
 * E1: The NamedElement composite represents a pair of a MAL::Identifier and
 * an abstract MAL::Element.  It is an extension of the MAL::NamedValue composite
 * that adds support for non-MAL::Attribute values.
 */
public final class NamedElement implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1407374900330498L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330498L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Name identifying the element.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier name;

    /**
     * Expression evaluating to the corresponding MAL::Element value. When used
     * in a pointing constraint, then the MAL Element subtype must match the argument
     * type of the corresponding pointing template argument (see 4.6.6.4.4 and
     * table 4-6).
     */
    private org.ccsds.moims.mo.mal.structures.Element value;

    /**
     * Default constructor for NamedElement.
     * 
     */
    public NamedElement() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param name Name identifying the element.
     * @param value Expression evaluating to the corresponding MAL::Element value. When used in a pointing constraint, then the MAL Element subtype must match the argument type of the corresponding pointing template argument (see 4.6.6.4.4 and table 4-6).
     */
    public NamedElement(org.ccsds.moims.mo.mal.structures.Identifier name,
            org.ccsds.moims.mo.mal.structures.Element value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param name Name identifying the element.
     */
    public NamedElement(org.ccsds.moims.mo.mal.structures.Identifier name) {
        this.name = name;
        this.value = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.NamedElement();
    }

    /**
     * Returns the field name.
     * 
     * @return The field name
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getName() {
        return name;
    }

    /**
     * Returns the field value.
     * 
     * @return The field value
     */
    public org.ccsds.moims.mo.mal.structures.Element getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NamedElement) {
            NamedElement other = (NamedElement) obj;
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
        buf.append("(NamedElement: ");
        buf.append("name=").append(name);
        buf.append(", value=").append(value);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (name == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'name' cannot be null!");
        }
        encoder.encodeIdentifier(name);
        encoder.encodeNullableAbstractElement(value);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        name = decoder.decodeIdentifier();
        value = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeNullableAbstractElement();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
