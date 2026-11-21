package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;

/**
 * List class for PhysicalValue.
 */
public final class PhysicalValueList extends HeterogeneousList {

    /**
     * Default constructor for PhysicalValueList.
     * 
     */
    public PhysicalValueList() {
    }

    @Override
    public boolean add(Element element) {
        if (element != null && !(element instanceof PhysicalValue)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: PhysicalValue");
        }
        return super.add(element);
    }

}
