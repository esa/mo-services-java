package org.ccsds.moims.mo.mps.structures;

/**
 * List class for Position.
 */
public final class PositionList extends org.ccsds.moims.mo.mal.structures.HeterogeneousList {

    /**
     * Default constructor for PositionList.
     * 
     */
    public PositionList() {
    }

    @Override
    public boolean add(org.ccsds.moims.mo.mal.structures.Element element) {
        if (element != null && !(element instanceof Position)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: Position");
        }
        return super.add(element);
    }

}
