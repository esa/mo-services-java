package org.ccsds.moims.mo.mps.structures;

/**
 * List class for ResourceConstraint.
 */
public final class ResourceConstraintList extends org.ccsds.moims.mo.mal.structures.HeterogeneousList {

    /**
     * Default constructor for ResourceConstraintList.
     * 
     */
    public ResourceConstraintList() {
    }

    @Override
    public boolean add(org.ccsds.moims.mo.mal.structures.Element element) {
        if (element != null && !(element instanceof ResourceConstraint)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: ResourceConstraint");
        }
        return super.add(element);
    }

}
