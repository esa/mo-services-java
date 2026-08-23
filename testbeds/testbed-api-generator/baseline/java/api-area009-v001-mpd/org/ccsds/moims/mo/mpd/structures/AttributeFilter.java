package org.ccsds.moims.mo.mpd.structures;

/**
 * An AttributeFilter is an abstract data structure that enables three types
 * of filters: ValueRange, ValueSet, and StringPattern. It is used in the
 * context of selecting a subset of mission data products.
 */
public abstract class AttributeFilter implements org.ccsds.moims.mo.mal.structures.Composite {

    /**
     * The name of the metadata attribute to filter. If the product metadata being
     * evaluated does not contain an attribute with this name, then the evaluation
     * of the filter shall be false.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier name;

    /**
     * Indicates whether the filter is to include [TRUE] or exclude [FALSE] attribute
     * values that match the filter.
     */
    private Boolean include;

    /**
     * Default constructor for AttributeFilter.
     * 
     */
    public AttributeFilter() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param name The name of the metadata attribute to filter. If the product metadata being evaluated does not contain an attribute with this name, then the evaluation of the filter shall be false.
     * @param include Indicates whether the filter is to include [TRUE] or exclude [FALSE] attribute values that match the filter.
     */
    public AttributeFilter(org.ccsds.moims.mo.mal.structures.Identifier name,
            Boolean include) {
        this.name = name;
        this.include = include;
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
     * Returns the field include.
     * 
     * @return The field include
     */
    public Boolean getInclude() {
        return include;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AttributeFilter) {
            AttributeFilter other = (AttributeFilter) obj;
            if (name == null) {
                if (other.name != null) {
                    return false;
                }
            } else {
                if (! name.equals(other.name)) {
                    return false;
                }
            }
            if (include == null) {
                if (other.include != null) {
                    return false;
                }
            } else {
                if (! include.equals(other.include)) {
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
        hash = 83 * hash + (include != null ? include.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(AttributeFilter: ");
        buf.append("name=").append(name);
        buf.append(", include=").append(include);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (name == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'name' cannot be null!");
        }
        if (include == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'include' cannot be null!");
        }
        encoder.encodeIdentifier(name);
        encoder.encodeBoolean(include);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        name = decoder.decodeIdentifier();
        include = decoder.decodeBoolean();
        return this;
    }

}
