package org.ccsds.moims.mo.mps.structures;

/**
 * E2: A time window within which the planning activity is to be planned.
 */
public final class TimeWindowConstraint extends org.ccsds.moims.mo.mps.structures.Constraint {

    private static final long serialVersionUID = 1407374900330529L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330529L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The point in the duration of the activity that is constrained to be after
     * the start time of the time window.  Although typically the start of the
     * activity (0), this can be any point up to the end of the activity (1).
     * Default is the start of the planning activity.
     */
    private org.ccsds.moims.mo.mps.structures.Slider startRef;

    /**
     * The point in the duration of the activity that is constrained to be before
     * the end time of the time window.  Although typically the end of the activity
     * (1), this can be any point up to the start of the activity (0). Default
     * is the end of the planning activity.
     */
    private org.ccsds.moims.mo.mps.structures.Slider endRef;

    /**
     * The [set of] TimeWindows within which the activity must be placed on the
     * Plan.
     */
    private org.ccsds.moims.mo.mps.structures.TimeWindowList timeWindows;

    /**
     * Default constructor for TimeWindowConstraint.
     * 
     */
    public TimeWindowConstraint() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param negate Specifies whether the result of combining the Constraints is to be inverted (NOT function). Default = False.
     * @param startRef The point in the duration of the activity that is constrained to be after the start time of the time window.  Although typically the start of the activity (0), this can be any point up to the end of the activity (1). Default is the start of the planning activity.
     * @param endRef The point in the duration of the activity that is constrained to be before the end time of the time window.  Although typically the end of the activity (1), this can be any point up to the start of the activity (0). Default is the end of the planning activity.
     * @param timeWindows The [set of] TimeWindows within which the activity must be placed on the Plan.
     */
    public TimeWindowConstraint(Boolean negate,
            org.ccsds.moims.mo.mps.structures.Slider startRef,
            org.ccsds.moims.mo.mps.structures.Slider endRef,
            org.ccsds.moims.mo.mps.structures.TimeWindowList timeWindows) {
        super(negate);
        this.startRef = startRef;
        this.endRef = endRef;
        this.timeWindows = timeWindows;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param timeWindows The [set of] TimeWindows within which the activity must be placed on the Plan.
     */
    public TimeWindowConstraint(org.ccsds.moims.mo.mps.structures.TimeWindowList timeWindows) {
        this.startRef = null;
        this.endRef = null;
        this.timeWindows = timeWindows;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.TimeWindowConstraint();
    }

    /**
     * Returns the field startRef.
     * 
     * @return The field startRef
     */
    public org.ccsds.moims.mo.mps.structures.Slider getStartRef() {
        return startRef;
    }

    /**
     * Returns the field endRef.
     * 
     * @return The field endRef
     */
    public org.ccsds.moims.mo.mps.structures.Slider getEndRef() {
        return endRef;
    }

    /**
     * Returns the field timeWindows.
     * 
     * @return The field timeWindows
     */
    public org.ccsds.moims.mo.mps.structures.TimeWindowList getTimeWindows() {
        return timeWindows;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TimeWindowConstraint) {
            if (! super.equals(obj)) {
                return false;
            }
            TimeWindowConstraint other = (TimeWindowConstraint) obj;
            if (startRef == null) {
                if (other.startRef != null) {
                    return false;
                }
            } else {
                if (! startRef.equals(other.startRef)) {
                    return false;
                }
            }
            if (endRef == null) {
                if (other.endRef != null) {
                    return false;
                }
            } else {
                if (! endRef.equals(other.endRef)) {
                    return false;
                }
            }
            if (timeWindows == null) {
                if (other.timeWindows != null) {
                    return false;
                }
            } else {
                if (! timeWindows.equals(other.timeWindows)) {
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
        hash = 83 * hash + (startRef != null ? startRef.hashCode() : 0);
        hash = 83 * hash + (endRef != null ? endRef.hashCode() : 0);
        hash = 83 * hash + (timeWindows != null ? timeWindows.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(TimeWindowConstraint: ");
        buf.append(super.toString());
        buf.append(", startRef=").append(startRef);
        buf.append(", endRef=").append(endRef);
        buf.append(", timeWindows=").append(timeWindows);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (timeWindows == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'timeWindows' cannot be null!");
        }
        encoder.encodeNullableElement(startRef);
        encoder.encodeNullableElement(endRef);
        encoder.encodeElement(timeWindows);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        startRef = (org.ccsds.moims.mo.mps.structures.Slider) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.Slider());
        endRef = (org.ccsds.moims.mo.mps.structures.Slider) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.Slider());
        timeWindows = (org.ccsds.moims.mo.mps.structures.TimeWindowList) decoder.decodeElement(new org.ccsds.moims.mo.mps.structures.TimeWindowList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
