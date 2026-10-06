package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;

/**
 * List class for Effect.
 */
public final class EffectList extends HeterogeneousList {

    /**
     * Default constructor for EffectList.
     * 
     */
    public EffectList() {
    }

    @Override
    public boolean add(Element element) {
        if (element != null && !(element instanceof Effect)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: Effect");
        }
        return super.add(element);
    }

}
