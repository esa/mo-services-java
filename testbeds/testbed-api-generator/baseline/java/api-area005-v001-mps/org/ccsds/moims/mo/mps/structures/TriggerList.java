package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;

/**
 * List class for Trigger.
 */
public final class TriggerList extends HeterogeneousList {

    /**
     * Default constructor for TriggerList.
     * 
     */
    public TriggerList() {
    }

    @Override
    public boolean add(Element element) {
        if (element != null && !(element instanceof Trigger)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: Trigger");
        }
        return super.add(element);
    }

}
