package org.ccsds.moims.mo.malprototype.structures;

import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;

/**
 * List class for Auto.
 */
public final class AutoList extends HeterogeneousList {

    /**
     * Default constructor for AutoList.
     * 
     */
    public AutoList() {
    }

    @Override
    public boolean add(Element element) {
        if (element != null && !(element instanceof Auto)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: Auto");
        }
        return super.add(element);
    }

}
