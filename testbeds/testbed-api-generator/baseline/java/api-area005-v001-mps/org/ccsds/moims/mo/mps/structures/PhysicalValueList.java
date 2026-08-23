package org.ccsds.moims.mo.mps.structures;

/**
 * List class for PhysicalValue.
 */
public final class PhysicalValueList extends org.ccsds.moims.mo.mal.structures.HeterogeneousList {

    /**
     * Default constructor for PhysicalValueList.
     * 
     */
    public PhysicalValueList() {
    }

    @Override
    public boolean add(org.ccsds.moims.mo.mal.structures.Element element) {
        if (element != null && !(element instanceof PhysicalValue)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: PhysicalValue");
        }
        return super.add(element);
    }

}
