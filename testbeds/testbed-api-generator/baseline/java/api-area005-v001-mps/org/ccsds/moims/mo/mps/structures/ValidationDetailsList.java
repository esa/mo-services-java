package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;

/**
 * List class for ValidationDetails.
 */
public final class ValidationDetailsList extends HeterogeneousList {

    /**
     * Default constructor for ValidationDetailsList.
     * 
     */
    public ValidationDetailsList() {
    }

    @Override
    public boolean add(Element element) {
        if (element != null && !(element instanceof ValidationDetails)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: ValidationDetails");
        }
        return super.add(element);
    }

}
