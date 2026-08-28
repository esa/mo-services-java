package org.ccsds.moims.mo.mps.structures;

/**
 * List class for ActivityDetails.
 */
public final class ActivityDetailsList extends org.ccsds.moims.mo.mal.structures.HeterogeneousList {

    /**
     * Default constructor for ActivityDetailsList.
     * 
     */
    public ActivityDetailsList() {
    }

    @Override
    public boolean add(org.ccsds.moims.mo.mal.structures.Element element) {
        if (element != null && !(element instanceof ActivityDetails)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: ActivityDetails");
        }
        return super.add(element);
    }

}
