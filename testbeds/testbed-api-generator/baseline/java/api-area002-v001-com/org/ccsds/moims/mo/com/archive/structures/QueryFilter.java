package org.ccsds.moims.mo.com.archive.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * The base structure for archive filters.
 */
public abstract class QueryFilter implements Composite {

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
    public void encode(MALEncoder encoder) throws MALException {
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        return this;
    }

}
