package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.MOObject;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;

/**
 * E1: A RequestDefinition is an MO object that contains the specification
 * of a re-usable planning request template.
 */
public final class RequestDefinition extends MOObject {

    private static final long serialVersionUID = 1407374900330897L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330897L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Description of the re-usable RequestDefinition.
     */
    private String description;

    /**
     * List of argument definitions.  Arguments may be referenced in ActivityDetails
     * and constraints.
     */
    private ArgDefList argDefs;

    /**
     * A flag that indicates whether the planning request is for a repetitive
     * standing order (unbounded other than by the validity period), or is a one-off
     * request.  If it is a standing order, then the supplied activity details
     * must be an ActivityNode with specification of the repetition criteria.
     * It should be noted that a one-off request can still include repetition.
     */
    private Boolean standingOrder;

    /**
     * Set of activity details specifying requested activities.
     */
    private ActivityDetailsList activities;

    /**
     * Default constructor for RequestDefinition.
     * 
     */
    public RequestDefinition() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param description Description of the re-usable RequestDefinition.
     * @param argDefs List of argument definitions.  Arguments may be referenced in ActivityDetails and constraints.
     * @param standingOrder A flag that indicates whether the planning request is for a repetitive standing order (unbounded other than by the validity period), or is a one-off request.  If it is a standing order, then the supplied activity details must be an ActivityNode with specification of the repetition criteria.  It should be noted that a one-off request can still include repetition.
     * @param activities Set of activity details specifying requested activities.
     */
    public RequestDefinition(ObjectIdentity objectIdentity,
            String description,
            ArgDefList argDefs,
            Boolean standingOrder,
            ActivityDetailsList activities) {
        super(objectIdentity);
        this.description = description;
        this.argDefs = argDefs;
        this.standingOrder = standingOrder;
        this.activities = activities;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param description Description of the re-usable RequestDefinition.
     * @param standingOrder A flag that indicates whether the planning request is for a repetitive standing order (unbounded other than by the validity period), or is a one-off request.  If it is a standing order, then the supplied activity details must be an ActivityNode with specification of the repetition criteria.  It should be noted that a one-off request can still include repetition.
     * @param activities Set of activity details specifying requested activities.
     */
    public RequestDefinition(ObjectIdentity objectIdentity,
            String description,
            Boolean standingOrder,
            ActivityDetailsList activities) {
        super(objectIdentity);
        this.description = description;
        this.argDefs = null;
        this.standingOrder = standingOrder;
        this.activities = activities;
    }

    @Override
    public Element createElement() {
        return new RequestDefinition();
    }

    /**
     * Returns the field description.
     * 
     * @return The field description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the field argDefs.
     * 
     * @return The field argDefs
     */
    public ArgDefList getArgDefs() {
        return argDefs;
    }

    /**
     * Returns the field standingOrder.
     * 
     * @return The field standingOrder
     */
    public Boolean getStandingOrder() {
        return standingOrder;
    }

    /**
     * Returns the field activities.
     * 
     * @return The field activities
     */
    public ActivityDetailsList getActivities() {
        return activities;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RequestDefinition) {
            if (! super.equals(obj)) {
                return false;
            }
            RequestDefinition other = (RequestDefinition) obj;
            if (description == null) {
                if (other.description != null) {
                    return false;
                }
            } else {
                if (! description.equals(other.description)) {
                    return false;
                }
            }
            if (argDefs == null) {
                if (other.argDefs != null) {
                    return false;
                }
            } else {
                if (! argDefs.equals(other.argDefs)) {
                    return false;
                }
            }
            if (standingOrder == null) {
                if (other.standingOrder != null) {
                    return false;
                }
            } else {
                if (! standingOrder.equals(other.standingOrder)) {
                    return false;
                }
            }
            if (activities == null) {
                if (other.activities != null) {
                    return false;
                }
            } else {
                if (! activities.equals(other.activities)) {
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
        hash = 83 * hash + (description != null ? description.hashCode() : 0);
        hash = 83 * hash + (argDefs != null ? argDefs.hashCode() : 0);
        hash = 83 * hash + (standingOrder != null ? standingOrder.hashCode() : 0);
        hash = 83 * hash + (activities != null ? activities.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(RequestDefinition: ");
        buf.append(super.toString());
        buf.append(", description=").append(description);
        buf.append(", argDefs=").append(argDefs);
        buf.append(", standingOrder=").append(standingOrder);
        buf.append(", activities=").append(activities);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (description == null) {
            throw new MALException("The field 'description' cannot be null!");
        }
        if (standingOrder == null) {
            throw new MALException("The field 'standingOrder' cannot be null!");
        }
        if (activities == null) {
            throw new MALException("The field 'activities' cannot be null!");
        }
        encoder.encodeString(description);
        encoder.encodeNullableElement(argDefs);
        encoder.encodeBoolean(standingOrder);
        encoder.encodeElement(activities);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        description = decoder.decodeString();
        argDefs = (ArgDefList) decoder.decodeNullableElement(new ArgDefList());
        standingOrder = decoder.decodeBoolean();
        activities = (ActivityDetailsList) decoder.decodeElement(new ActivityDetailsList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
