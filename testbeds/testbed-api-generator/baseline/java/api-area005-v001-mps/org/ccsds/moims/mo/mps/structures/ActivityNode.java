package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E1: A concrete sub-type of ActivityDetails, an ActivityNode is a container
 * node for a set of ActivityDetails together with an optional Repetition
 * specification.
 */
public final class ActivityNode extends ActivityDetails {

    private static final long serialVersionUID = 1407374900330600L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330600L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Optional Repetition specification.
     */
    private Repetition repetition;

    /**
     * Set of ActivityDetails.
     */
    private ActivityDetailsList activities;

    /**
     * Default constructor for ActivityNode.
     * 
     */
    public ActivityNode() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param activityRef Specifies how the ActivityInstance is placed with respect to any defined Repetition (0=Start; 1=End). Default is Start.
     * @param activityOffset Specifies an offset in time for the ActivityInstance from any defined Repetition. Default is no offset.
     * @param relatedEvent Specifies a related Event (or Event Group) for the ActivityInstance.  Argument specifications and constraints may reference arguments and fields of the RelatedEvent.
     * @param comments Any notes associated with the ActivityDetails.
     * @param repetition Optional Repetition specification.
     * @param activities Set of ActivityDetails.
     */
    public ActivityNode(Slider activityRef,
            Element activityOffset,
            Element relatedEvent,
            String comments,
            Repetition repetition,
            ActivityDetailsList activities) {
        super(activityRef,
            activityOffset,
            relatedEvent,
            comments);
        this.repetition = repetition;
        this.activities = activities;
    }

    @Override
    public Element createElement() {
        return new ActivityNode();
    }

    /**
     * Returns the field repetition.
     * 
     * @return The field repetition
     */
    public Repetition getRepetition() {
        return repetition;
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
        if (obj instanceof ActivityNode) {
            if (! super.equals(obj)) {
                return false;
            }
            ActivityNode other = (ActivityNode) obj;
            if (repetition == null) {
                if (other.repetition != null) {
                    return false;
                }
            } else {
                if (! repetition.equals(other.repetition)) {
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
        hash = 83 * hash + (repetition != null ? repetition.hashCode() : 0);
        hash = 83 * hash + (activities != null ? activities.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ActivityNode: ");
        buf.append(super.toString());
        buf.append(", repetition=").append(repetition);
        buf.append(", activities=").append(activities);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        encoder.encodeNullableAbstractElement(repetition);
        encoder.encodeNullableElement(activities);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        repetition = (Repetition) decoder.decodeNullableAbstractElement();
        activities = (ActivityDetailsList) decoder.decodeNullableElement(new ActivityDetailsList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
