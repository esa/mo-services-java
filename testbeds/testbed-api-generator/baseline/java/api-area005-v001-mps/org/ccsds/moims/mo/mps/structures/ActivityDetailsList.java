package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;

/**
 * List class for ActivityDetails.
 */
public final class ActivityDetailsList extends HeterogeneousList {

    /**
     * Default constructor for ActivityDetailsList.
     * 
     */
    public ActivityDetailsList() {
    }

    @Override
    public boolean add(Element element) {
        if (element != null && !(element instanceof ActivityDetails)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: ActivityDetails");
        }
        return super.add(element);
    }

}
