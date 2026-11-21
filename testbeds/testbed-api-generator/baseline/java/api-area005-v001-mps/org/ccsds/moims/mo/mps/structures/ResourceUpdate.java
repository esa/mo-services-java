package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Time;

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
public final class ResourceUpdate extends PlanDetailUpdate {

    private static final long serialVersionUID = 1407374900330806L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330806L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Reference to the Resource to which the value update relates.
     */
    private ObjectRef<Resource> resource;

    /**
     * Time of Resource value update.
     */
    private Time timestamp;

    /**
     * Value of the resource.  MAL Attribute type must match the dataType of the
     * resource definition.
     */
    private Attribute value;

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
    public ResourceUpdate(ObjectRef<Resource> resource,
            Time timestamp,
            Attribute value) {
        this.resource = resource;
        this.timestamp = timestamp;
        this.value = value;
    }

    @Override
    public Element createElement() {
        return new ResourceUpdate();
    }

    /**
     * Returns the field resource.
     * 
     * @return The field resource
     */
    public ObjectRef<Resource> getResource() {
        return resource;
    }

    /**
     * Returns the field timestamp.
     * 
     * @return The field timestamp
     */
    public Time getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the field value.
     * 
     * @return The field value
     */
    public Attribute getValue() {
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
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (resource == null) {
            throw new MALException("The field 'resource' cannot be null!");
        }
        if (timestamp == null) {
            throw new MALException("The field 'timestamp' cannot be null!");
        }
        if (value == null) {
            throw new MALException("The field 'value' cannot be null!");
        }
        encoder.encodeElement(resource);
        encoder.encodeTime(timestamp);
        encoder.encodeAttribute(value);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        resource = (ObjectRef<Resource>) decoder.decodeElement(new ObjectRef<Resource>());
        timestamp = decoder.decodeTime();
        value = (Attribute) decoder.decodeAttribute();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
