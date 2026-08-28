package org.ccsds.moims.mo.mpd.structures;

/**
 * A ValueSet is a concrete subtype of AttributeFilter that allows the specification
 * of a set of allowed (or disallowed) values for a metadata attribute.
 */
public final class ValueSet extends org.ccsds.moims.mo.mpd.structures.AttributeFilter {

    private static final long serialVersionUID = 2533274807173130L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 2533274807173130L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The set of allowed (or disallowed) values for the metadata attribute.
     */
    private org.ccsds.moims.mo.mal.structures.AttributeList values;

    /**
     * Default constructor for ValueSet.
     * 
     */
    public ValueSet() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param name The name of the metadata attribute to filter. If the product metadata being evaluated does not contain an attribute with this name, then the evaluation of the filter shall be false.
     * @param include Indicates whether the filter is to include [TRUE] or exclude [FALSE] attribute values that match the filter.
     * @param values The set of allowed (or disallowed) values for the metadata attribute.
     */
    public ValueSet(org.ccsds.moims.mo.mal.structures.Identifier name,
            Boolean include,
            org.ccsds.moims.mo.mal.structures.AttributeList values) {
        super(name,
            include);
        this.values = values;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mpd.structures.ValueSet();
    }

    /**
     * Returns the field values.
     * 
     * @return The field values
     */
    public org.ccsds.moims.mo.mal.structures.AttributeList getValues() {
        return values;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ValueSet) {
            if (! super.equals(obj)) {
                return false;
            }
            ValueSet other = (ValueSet) obj;
            if (values == null) {
                if (other.values != null) {
                    return false;
                }
            } else {
                if (! values.equals(other.values)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 83 * hash + (values != null ? values.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ValueSet: ");
        buf.append(super.toString());
        buf.append(", values=").append(values);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (values == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'values' cannot be null!");
        }
        encoder.encodeElement(values);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        values = (org.ccsds.moims.mo.mal.structures.AttributeList) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.AttributeList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
