package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;

/**
 * List class for PlanDetailUpdate.
 */
public final class PlanDetailUpdateList extends HeterogeneousList {

    /**
     * Default constructor for PlanDetailUpdateList.
     * 
     */
    public PlanDetailUpdateList() {
    }

    @Override
    public boolean add(Element element) {
        if (element != null && !(element instanceof PlanDetailUpdate)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: PlanDetailUpdate");
        }
        return super.add(element);
    }

}
