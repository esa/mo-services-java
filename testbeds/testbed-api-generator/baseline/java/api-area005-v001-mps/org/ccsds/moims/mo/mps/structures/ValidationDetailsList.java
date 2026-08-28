package org.ccsds.moims.mo.mps.structures;

/**
 * List class for ValidationDetails.
 */
public final class ValidationDetailsList extends org.ccsds.moims.mo.mal.structures.HeterogeneousList {

    /**
     * Default constructor for ValidationDetailsList.
     * 
     */
    public ValidationDetailsList() {
    }

    @Override
    public boolean add(org.ccsds.moims.mo.mal.structures.Element element) {
        if (element != null && !(element instanceof ValidationDetails)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: ValidationDetails");
        }
        return super.add(element);
    }

}
