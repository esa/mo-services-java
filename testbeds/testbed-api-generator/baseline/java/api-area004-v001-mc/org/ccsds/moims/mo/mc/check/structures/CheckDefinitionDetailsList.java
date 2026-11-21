package org.ccsds.moims.mo.mc.check.structures;

import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;

/**
 * List class for CheckDefinitionDetails.
 */
public final class CheckDefinitionDetailsList extends HeterogeneousList {

    /**
     * Default constructor for CheckDefinitionDetailsList.
     * 
     */
    public CheckDefinitionDetailsList() {
    }

    @Override
    public boolean add(Element element) {
        if (element != null && !(element instanceof CheckDefinitionDetails)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: CheckDefinitionDetails");
        }
        return super.add(element);
    }

}
