package org.ccsds.moims.mo.mps.structures;

/**
 * E5: An Effect is an abstract type that may be used to represent the impact
 * that executing a planning activity will have on a planning resource.
 */
public abstract class Effect implements org.ccsds.moims.mo.mal.structures.Composite {

    /**
     * Identifies the planning resource that is constrained for the duration of
     * the planning activity.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Resource> resourceRef;

    /**
     * Default constructor for Effect.
     * 
     */
    public Effect() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param resourceRef Identifies the planning resource that is constrained for the duration of the planning activity.
     */
    public Effect(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Resource> resourceRef) {
        this.resourceRef = resourceRef;
    }

    /**
     * Returns the field resourceRef.
     * 
     * @return The field resourceRef
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Resource> getResourceRef() {
        return resourceRef;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Effect) {
            Effect other = (Effect) obj;
            if (resourceRef == null) {
                if (other.resourceRef != null) {
                    return false;
                }
            } else {
                if (! resourceRef.equals(other.resourceRef)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (resourceRef != null ? resourceRef.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(Effect: ");
        buf.append("resourceRef=").append(resourceRef);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (resourceRef == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'resourceRef' cannot be null!");
        }
        encoder.encodeElement(resourceRef);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        resourceRef = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Resource>) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Resource>());
        return this;
    }

}
