package org.ccsds.moims.mo.mc.structures;

/**
 * List class for ActionEvent.
 */
public final class ActionEventList extends org.ccsds.moims.mo.mal.structures.HeterogeneousList {

    /**
     * Default constructor for ActionEventList.
     * 
     */
    public ActionEventList() {
    }

    @Override
    public boolean add(org.ccsds.moims.mo.mal.structures.Element element) {
        if (element != null && !(element instanceof ActionEvent)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: ActionEvent");
        }
        return super.add(element);
    }

}
