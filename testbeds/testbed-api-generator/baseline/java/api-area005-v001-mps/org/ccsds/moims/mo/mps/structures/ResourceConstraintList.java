package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;

/**
 * List class for ResourceConstraint.
 */
public final class ResourceConstraintList extends HeterogeneousList {

    /**
     * Default constructor for ResourceConstraintList.
     * 
     */
    public ResourceConstraintList() {
    }

    @Override
    public boolean add(Element element) {
        if (element != null && !(element instanceof ResourceConstraint)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: ResourceConstraint");
        }
        return super.add(element);
    }

}
