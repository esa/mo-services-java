package org.ccsds.moims.mo.malprototype.structures;

/**
 * List class for Auto.
 */
public final class AutoList extends org.ccsds.moims.mo.mal.structures.HeterogeneousList {

    /**
     * Default constructor for AutoList.
     * 
     */
    public AutoList() {
    }

    @Override
    public boolean add(org.ccsds.moims.mo.mal.structures.Element element) {
        if (element != null && !(element instanceof Auto)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: Auto");
        }
        return super.add(element);
    }

}
