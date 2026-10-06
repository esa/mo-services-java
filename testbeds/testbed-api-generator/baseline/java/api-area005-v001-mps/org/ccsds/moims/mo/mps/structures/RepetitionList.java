package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;

/**
 * List class for Repetition.
 */
public final class RepetitionList extends HeterogeneousList {

    /**
     * Default constructor for RepetitionList.
     * 
     */
    public RepetitionList() {
    }

    @Override
    public boolean add(Element element) {
        if (element != null && !(element instanceof Repetition)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: Repetition");
        }
        return super.add(element);
    }

}
