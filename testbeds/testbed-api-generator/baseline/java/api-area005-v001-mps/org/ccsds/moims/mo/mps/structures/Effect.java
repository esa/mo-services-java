package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ObjectRef;

/**
 * E5: An Effect is an abstract type that may be used to represent the impact
 * that executing a planning activity will have on a planning resource.
 */
public abstract class Effect implements Composite {

    /**
     * Identifies the planning resource that is constrained for the duration of
     * the planning activity.
     */
    private ObjectRef<Resource> resourceRef;

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
    public Effect(ObjectRef<Resource> resourceRef) {
        this.resourceRef = resourceRef;
    }

    /**
     * Returns the field resourceRef.
     * 
     * @return The field resourceRef
     */
    public ObjectRef<Resource> getResourceRef() {
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
    public void encode(MALEncoder encoder) throws MALException {
        if (resourceRef == null) {
            throw new MALException("The field 'resourceRef' cannot be null!");
        }
        encoder.encodeElement(resourceRef);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        resourceRef = (ObjectRef<Resource>) decoder.decodeElement(new ObjectRef<Resource>());
        return this;
    }

}
