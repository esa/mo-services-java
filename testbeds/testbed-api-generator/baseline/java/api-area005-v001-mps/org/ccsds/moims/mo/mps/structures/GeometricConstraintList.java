package org.ccsds.moims.mo.mps.structures;

/**
 * List class for GeometricConstraint.
 */
public final class GeometricConstraintList extends org.ccsds.moims.mo.mal.structures.HeterogeneousList {

    /**
     * Default constructor for GeometricConstraintList.
     * 
     */
    public GeometricConstraintList() {
    }

    @Override
    public boolean add(org.ccsds.moims.mo.mal.structures.Element element) {
        if (element != null && !(element instanceof GeometricConstraint)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: GeometricConstraint");
        }
        return super.add(element);
    }

}
