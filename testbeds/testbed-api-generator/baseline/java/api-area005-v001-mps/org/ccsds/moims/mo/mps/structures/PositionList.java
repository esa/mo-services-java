package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;

/**
 * List class for Position.
 */
public final class PositionList extends HeterogeneousList {

    /**
     * Default constructor for PositionList.
     * 
     */
    public PositionList() {
    }

    @Override
    public boolean add(Element element) {
        if (element != null && !(element instanceof Position)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: Position");
        }
        return super.add(element);
    }

}
