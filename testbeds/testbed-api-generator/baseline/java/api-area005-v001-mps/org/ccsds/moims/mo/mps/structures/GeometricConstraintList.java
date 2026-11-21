package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;

/**
 * List class for GeometricConstraint.
 */
public final class GeometricConstraintList extends HeterogeneousList {

    /**
     * Default constructor for GeometricConstraintList.
     * 
     */
    public GeometricConstraintList() {
    }

    @Override
    public boolean add(Element element) {
        if (element != null && !(element instanceof GeometricConstraint)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: GeometricConstraint");
        }
        return super.add(element);
    }

}
