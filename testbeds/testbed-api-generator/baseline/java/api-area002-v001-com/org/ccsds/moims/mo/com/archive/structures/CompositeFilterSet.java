package org.ccsds.moims.mo.com.archive.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * Contains a list of CompositeFilters that are AND&quot;d together to form
 * a more complex filter.
 */
public final class CompositeFilterSet extends QueryFilter {

    private static final long serialVersionUID = 562958560133124L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 562958560133124L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The list of filters to apply.
     */
    private CompositeFilterList filters;

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
    public CompositeFilterSet(CompositeFilterList filters) {
        this.filters = filters;
    }

    @Override
    public Element createElement() {
        return new CompositeFilterSet();
    }

    /**
     * Returns the field filters.
     * 
     * @return The field filters
     */
    public CompositeFilterList getFilters() {
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
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (filters == null) {
            throw new MALException("The field 'filters' cannot be null!");
        }
        encoder.encodeElement(filters);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        filters = (CompositeFilterList) decoder.decodeElement(new CompositeFilterList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
