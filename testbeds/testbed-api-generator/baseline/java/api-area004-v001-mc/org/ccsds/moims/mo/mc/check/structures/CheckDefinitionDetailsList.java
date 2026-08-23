package org.ccsds.moims.mo.mc.check.structures;

/**
 * List class for CheckDefinitionDetails.
 */
public final class CheckDefinitionDetailsList extends org.ccsds.moims.mo.mal.structures.HeterogeneousList {

    /**
     * Default constructor for CheckDefinitionDetailsList.
     * 
     */
    public CheckDefinitionDetailsList() {
    }

    @Override
    public boolean add(org.ccsds.moims.mo.mal.structures.Element element) {
        if (element != null && !(element instanceof CheckDefinitionDetails)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: CheckDefinitionDetails");
        }
        return super.add(element);
    }

}
