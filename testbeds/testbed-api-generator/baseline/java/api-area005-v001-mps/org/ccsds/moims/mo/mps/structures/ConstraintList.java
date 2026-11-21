package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;

/**
 * List class for Constraint.
 */
public final class ConstraintList extends HeterogeneousList {

    /**
     * Default constructor for ConstraintList.
     * 
     */
    public ConstraintList() {
    }

    @Override
    public boolean add(Element element) {
        if (element != null && !(element instanceof Constraint)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: Constraint");
        }
        return super.add(element);
    }

}
