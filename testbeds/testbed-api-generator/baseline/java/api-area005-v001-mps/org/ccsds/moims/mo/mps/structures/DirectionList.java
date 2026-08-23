package org.ccsds.moims.mo.mps.structures;

/**
 * List class for Direction.
 */
public final class DirectionList extends org.ccsds.moims.mo.mal.structures.HeterogeneousList {

    /**
     * Default constructor for DirectionList.
     * 
     */
    public DirectionList() {
    }

    @Override
    public boolean add(org.ccsds.moims.mo.mal.structures.Element element) {
        if (element != null && !(element instanceof Direction)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: Direction");
        }
        return super.add(element);
    }

}
