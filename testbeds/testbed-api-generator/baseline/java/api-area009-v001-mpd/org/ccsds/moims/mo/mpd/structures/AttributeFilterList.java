package org.ccsds.moims.mo.mpd.structures;

/**
 * List class for AttributeFilter.
 */
public final class AttributeFilterList extends org.ccsds.moims.mo.mal.structures.HeterogeneousList {

    /**
     * Default constructor for AttributeFilterList.
     * 
     */
    public AttributeFilterList() {
    }

    @Override
    public boolean add(org.ccsds.moims.mo.mal.structures.Element element) {
        if (element != null && !(element instanceof AttributeFilter)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: AttributeFilter");
        }
        return super.add(element);
    }

}
