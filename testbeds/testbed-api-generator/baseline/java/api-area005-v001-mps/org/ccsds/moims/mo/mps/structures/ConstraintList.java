package org.ccsds.moims.mo.mps.structures;

/**
 * List class for Constraint.
 */
public final class ConstraintList extends org.ccsds.moims.mo.mal.structures.HeterogeneousList {

    /**
     * Default constructor for ConstraintList.
     * 
     */
    public ConstraintList() {
    }

    @Override
    public boolean add(org.ccsds.moims.mo.mal.structures.Element element) {
        if (element != null && !(element instanceof Constraint)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: Constraint");
        }
        return super.add(element);
    }

}
