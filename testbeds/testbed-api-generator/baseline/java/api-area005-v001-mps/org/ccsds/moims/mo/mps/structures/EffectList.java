package org.ccsds.moims.mo.mps.structures;

/**
 * List class for Effect.
 */
public final class EffectList extends org.ccsds.moims.mo.mal.structures.HeterogeneousList {

    /**
     * Default constructor for EffectList.
     * 
     */
    public EffectList() {
    }

    @Override
    public boolean add(org.ccsds.moims.mo.mal.structures.Element element) {
        if (element != null && !(element instanceof Effect)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: Effect");
        }
        return super.add(element);
    }

}
