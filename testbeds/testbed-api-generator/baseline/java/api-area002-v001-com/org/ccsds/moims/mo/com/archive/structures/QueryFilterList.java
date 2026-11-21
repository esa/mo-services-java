package org.ccsds.moims.mo.com.archive.structures;

import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;

/**
 * List class for QueryFilter.
 */
public final class QueryFilterList extends HeterogeneousList {

    /**
     * Default constructor for QueryFilterList.
     * 
     */
    public QueryFilterList() {
    }

    @Override
    public boolean add(Element element) {
        if (element != null && !(element instanceof QueryFilter)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: QueryFilter");
        }
        return super.add(element);
    }

}
