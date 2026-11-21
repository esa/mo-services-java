package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;

/**
 * List class for Direction.
 */
public final class DirectionList extends HeterogeneousList {

    /**
     * Default constructor for DirectionList.
     * 
     */
    public DirectionList() {
    }

    @Override
    public boolean add(Element element) {
        if (element != null && !(element instanceof Direction)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: Direction");
        }
        return super.add(element);
    }

}
