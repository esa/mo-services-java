package org.ccsds.moims.mo.mps.structures;

/**
 * List class for PlanDetailUpdate.
 */
public final class PlanDetailUpdateList extends org.ccsds.moims.mo.mal.structures.HeterogeneousList {

    /**
     * Default constructor for PlanDetailUpdateList.
     * 
     */
    public PlanDetailUpdateList() {
    }

    @Override
    public boolean add(org.ccsds.moims.mo.mal.structures.Element element) {
        if (element != null && !(element instanceof PlanDetailUpdate)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: PlanDetailUpdate");
        }
        return super.add(element);
    }

}
