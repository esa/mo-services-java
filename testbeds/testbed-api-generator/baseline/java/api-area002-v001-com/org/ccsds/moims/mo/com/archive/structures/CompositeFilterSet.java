package org.ccsds.moims.mo.com.archive.structures;

/**
 * Contains a list of CompositeFilters that are AND&quot;d together to form
 * a more complex filter.
 */
public final class CompositeFilterSet extends org.ccsds.moims.mo.com.archive.structures.QueryFilter {

    private static final long serialVersionUID = 562958560133124L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 562958560133124L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The list of filters to apply.
     */
    private org.ccsds.moims.mo.com.archive.structures.CompositeFilterList filters;

    /**
     * Default constructor for CompositeFilterSet.
     * 
     */
    public CompositeFilterSet() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param filters The list of filters to apply.
     */
    public CompositeFilterSet(org.ccsds.moims.mo.com.archive.structures.CompositeFilterList filters) {
        this.filters = filters;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.com.archive.structures.CompositeFilterSet();
    }

    /**
     * Returns the field filters.
     * 
     * @return The field filters
     */
    public org.ccsds.moims.mo.com.archive.structures.CompositeFilterList getFilters() {
        return filters;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CompositeFilterSet) {
            if (! super.equals(obj)) {
                return false;
            }
            CompositeFilterSet other = (CompositeFilterSet) obj;
            if (filters == null) {
                if (other.filters != null) {
                    return false;
                }
            } else {
                if (! filters.equals(other.filters)) {
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
        hash = 83 * hash + (filters != null ? filters.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(CompositeFilterSet: ");
        buf.append(super.toString());
        buf.append(", filters=").append(filters);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (filters == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'filters' cannot be null!");
        }
        encoder.encodeElement(filters);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        filters = (org.ccsds.moims.mo.com.archive.structures.CompositeFilterList) decoder.decodeElement(new org.ccsds.moims.mo.com.archive.structures.CompositeFilterList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
