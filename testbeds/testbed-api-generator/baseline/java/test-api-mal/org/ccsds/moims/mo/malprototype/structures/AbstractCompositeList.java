package org.ccsds.moims.mo.malprototype.structures;

/**
 * List class for AbstractComposite.
 */
public final class AbstractCompositeList extends org.ccsds.moims.mo.mal.structures.HeterogeneousList {

    /**
     * Default constructor for AbstractCompositeList.
     * 
     */
    public AbstractCompositeList() {
    }

    @Override
    public boolean add(org.ccsds.moims.mo.mal.structures.Element element) {
        if (element != null && !(element instanceof AbstractComposite)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: AbstractComposite");
        }
        return super.add(element);
    }

}
