package org.ccsds.moims.mo.mps.structures;

/**
 * List class for Trigger.
 */
public final class TriggerList extends org.ccsds.moims.mo.mal.structures.HeterogeneousList {

    /**
     * Default constructor for TriggerList.
     * 
     */
    public TriggerList() {
    }

    @Override
    public boolean add(org.ccsds.moims.mo.mal.structures.Element element) {
        if (element != null && !(element instanceof Trigger)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: Trigger");
        }
        return super.add(element);
    }

}
