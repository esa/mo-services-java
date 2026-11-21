package org.ccsds.moims.mo.malprototype.structures;

import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;

/**
 * List class for AbstractComposite.
 */
public final class AbstractCompositeList extends HeterogeneousList {

    /**
     * Default constructor for AbstractCompositeList.
     * 
     */
    public AbstractCompositeList() {
    }

    @Override
    public boolean add(Element element) {
        if (element != null && !(element instanceof AbstractComposite)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: AbstractComposite");
        }
        return super.add(element);
    }

}
