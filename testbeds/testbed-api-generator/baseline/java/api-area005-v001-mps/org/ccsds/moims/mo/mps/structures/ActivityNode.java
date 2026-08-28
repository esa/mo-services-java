package org.ccsds.moims.mo.mps.structures;

/**
 * E1: A concrete sub-type of ActivityDetails, an ActivityNode is a container
 * node for a set of ActivityDetails together with an optional Repetition
 * specification.
 */
public final class ActivityNode extends org.ccsds.moims.mo.mps.structures.ActivityDetails {

    private static final long serialVersionUID = 1407374900330600L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330600L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Optional Repetition specification.
     */
    private org.ccsds.moims.mo.mps.structures.Repetition repetition;

    /**
     * Set of ActivityDetails.
     */
    private org.ccsds.moims.mo.mps.structures.ActivityDetailsList activities;

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
    public ActivityNode(org.ccsds.moims.mo.mps.structures.Slider activityRef,
            org.ccsds.moims.mo.mal.structures.Element activityOffset,
            org.ccsds.moims.mo.mal.structures.Element relatedEvent,
            String comments,
            org.ccsds.moims.mo.mps.structures.Repetition repetition,
            org.ccsds.moims.mo.mps.structures.ActivityDetailsList activities) {
        super(activityRef,
            activityOffset,
            relatedEvent,
            comments);
        this.repetition = repetition;
        this.activities = activities;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.ActivityNode();
    }

    /**
     * Returns the field repetition.
     * 
     * @return The field repetition
     */
    public org.ccsds.moims.mo.mps.structures.Repetition getRepetition() {
        return repetition;
    }

    /**
     * Returns the field activities.
     * 
     * @return The field activities
     */
    public org.ccsds.moims.mo.mps.structures.ActivityDetailsList getActivities() {
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
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        encoder.encodeNullableAbstractElement(repetition);
        encoder.encodeNullableElement(activities);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        repetition = (org.ccsds.moims.mo.mps.structures.Repetition) decoder.decodeNullableAbstractElement();
        activities = (org.ccsds.moims.mo.mps.structures.ActivityDetailsList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.ActivityDetailsList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
