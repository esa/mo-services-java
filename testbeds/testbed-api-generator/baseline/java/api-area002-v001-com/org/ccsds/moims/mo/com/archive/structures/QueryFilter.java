package org.ccsds.moims.mo.com.archive.structures;

/**
 * The base structure for archive filters.
 */
public abstract class QueryFilter implements org.ccsds.moims.mo.mal.structures.Composite {

    /**
     * Default constructor for QueryFilter.
     * 
     */
    public QueryFilter() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof QueryFilter) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(QueryFilter: ");
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        return this;
    }

}
