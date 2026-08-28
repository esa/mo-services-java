package org.ccsds.moims.mo.mps.structures;

/**
 * List class for Repetition.
 */
public final class RepetitionList extends org.ccsds.moims.mo.mal.structures.HeterogeneousList {

    /**
     * Default constructor for RepetitionList.
     * 
     */
    public RepetitionList() {
    }

    @Override
    public boolean add(org.ccsds.moims.mo.mal.structures.Element element) {
        if (element != null && !(element instanceof Repetition)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: Repetition");
        }
        return super.add(element);
    }

}
