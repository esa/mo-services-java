package org.ccsds.moims.mo.mps.structures;

/**
 * E4: ResourceUpdate is a data structure that is used to report the value
 * of a Resource at a given point in time in the context of the MPS Plan Execution
 * Control service monitorPlanExecutionDetail operation, or to supply an updated
 * value for a Resource in the context of the MPS Plan Edit service. Resource
 * updates may be distributed to subscribing applications, including status
 * displays, to inform them of the latest value of the Resource.  This may
 * be particularly relevant in conjunction with a plan execution function.
 * Resource updates may be stored in resource history to provide a complete
 * record of evolving value over time. Resource updates are also effectively
 * contained within a Plan to describe the predicted evolution of Resources
 * over the duration of that Plan.  However, in this context the ResourceProfile
 * construct is used (see 4.5.4.4 above).
 */
public final class ResourceUpdate extends org.ccsds.moims.mo.mps.structures.PlanDetailUpdate {

    private static final long serialVersionUID = 1407374900330806L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330806L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Reference to the Resource to which the value update relates.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Resource> resource;

    /**
     * Time of Resource value update.
     */
    private org.ccsds.moims.mo.mal.structures.Time timestamp;

    /**
     * Value of the resource.  MAL Attribute type must match the dataType of the
     * resource definition.
     */
    private org.ccsds.moims.mo.mal.structures.Attribute value;

    /**
     * Default constructor for ResourceUpdate.
     * 
     */
    public ResourceUpdate() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param resource Reference to the Resource to which the value update relates.
     * @param timestamp Time of Resource value update.
     * @param value Value of the resource.  MAL Attribute type must match the dataType of the resource definition.
     */
    public ResourceUpdate(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Resource> resource,
            org.ccsds.moims.mo.mal.structures.Time timestamp,
            org.ccsds.moims.mo.mal.structures.Attribute value) {
        this.resource = resource;
        this.timestamp = timestamp;
        this.value = value;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.ResourceUpdate();
    }

    /**
     * Returns the field resource.
     * 
     * @return The field resource
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Resource> getResource() {
        return resource;
    }

    /**
     * Returns the field timestamp.
     * 
     * @return The field timestamp
     */
    public org.ccsds.moims.mo.mal.structures.Time getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the field value.
     * 
     * @return The field value
     */
    public org.ccsds.moims.mo.mal.structures.Attribute getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ResourceUpdate) {
            if (! super.equals(obj)) {
                return false;
            }
            ResourceUpdate other = (ResourceUpdate) obj;
            if (resource == null) {
                if (other.resource != null) {
                    return false;
                }
            } else {
                if (! resource.equals(other.resource)) {
                    return false;
                }
            }
            if (timestamp == null) {
                if (other.timestamp != null) {
                    return false;
                }
            } else {
                if (! timestamp.equals(other.timestamp)) {
                    return false;
                }
            }
            if (value == null) {
                if (other.value != null) {
                    return false;
                }
            } else {
                if (! value.equals(other.value)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 83 * hash + (resource != null ? resource.hashCode() : 0);
        hash = 83 * hash + (timestamp != null ? timestamp.hashCode() : 0);
        hash = 83 * hash + (value != null ? value.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ResourceUpdate: ");
        buf.append(super.toString());
        buf.append(", resource=").append(resource);
        buf.append(", timestamp=").append(timestamp);
        buf.append(", value=").append(value);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (resource == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'resource' cannot be null!");
        }
        if (timestamp == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'timestamp' cannot be null!");
        }
        if (value == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'value' cannot be null!");
        }
        encoder.encodeElement(resource);
        encoder.encodeTime(timestamp);
        encoder.encodeAttribute(value);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        resource = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Resource>) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Resource>());
        timestamp = decoder.decodeTime();
        value = (org.ccsds.moims.mo.mal.structures.Attribute) decoder.decodeAttribute();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
