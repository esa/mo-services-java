package org.ccsds.moims.mo.mps.structures;

/**
 * E2: The time at which a planning activity must be planned.
 */
public final class TimeConstraint extends org.ccsds.moims.mo.mps.structures.Constraint {

    private static final long serialVersionUID = 1407374900330528L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330528L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The time at which the planning activity must be planned.
     */
    private org.ccsds.moims.mo.mal.structures.Element time;

    /**
     * The point in the duration of the planning activity that is time constrained.
     * 0:  the start of the planning activity. 1:  the end of the planning activity.
     */
    private org.ccsds.moims.mo.mps.structures.Slider timeRef;

    /**
     * Default constructor for TimeConstraint.
     * 
     */
    public TimeConstraint() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param negate Specifies whether the result of combining the Constraints is to be inverted (NOT function). Default = False.
     * @param time The time at which the planning activity must be planned.
     * @param timeRef The point in the duration of the planning activity that is time constrained. 0:  the start of the planning activity. 1:  the end of the planning activity.
     */
    public TimeConstraint(Boolean negate,
            org.ccsds.moims.mo.mal.structures.Element time,
            org.ccsds.moims.mo.mps.structures.Slider timeRef) {
        super(negate);
        this.time = time;
        this.timeRef = timeRef;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param time The time at which the planning activity must be planned.
     * @param timeRef The point in the duration of the planning activity that is time constrained. 0:  the start of the planning activity. 1:  the end of the planning activity.
     */
    public TimeConstraint(org.ccsds.moims.mo.mal.structures.Element time,
            org.ccsds.moims.mo.mps.structures.Slider timeRef) {
        this.time = time;
        this.timeRef = timeRef;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.TimeConstraint();
    }

    /**
     * Returns the field time.
     * 
     * @return The field time
     */
    public org.ccsds.moims.mo.mal.structures.Element getTime() {
        return time;
    }

    /**
     * Returns the field timeRef.
     * 
     * @return The field timeRef
     */
    public org.ccsds.moims.mo.mps.structures.Slider getTimeRef() {
        return timeRef;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TimeConstraint) {
            if (! super.equals(obj)) {
                return false;
            }
            TimeConstraint other = (TimeConstraint) obj;
            if (time == null) {
                if (other.time != null) {
                    return false;
                }
            } else {
                if (! time.equals(other.time)) {
                    return false;
                }
            }
            if (timeRef == null) {
                if (other.timeRef != null) {
                    return false;
                }
            } else {
                if (! timeRef.equals(other.timeRef)) {
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
        hash = 83 * hash + (time != null ? time.hashCode() : 0);
        hash = 83 * hash + (timeRef != null ? timeRef.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(TimeConstraint: ");
        buf.append(super.toString());
        buf.append(", time=").append(time);
        buf.append(", timeRef=").append(timeRef);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (time == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'time' cannot be null!");
        }
        if (timeRef == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'timeRef' cannot be null!");
        }
        encoder.encodeAbstractElement(time);
        encoder.encodeElement(timeRef);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        time = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        timeRef = (org.ccsds.moims.mo.mps.structures.Slider) decoder.decodeElement(new org.ccsds.moims.mo.mps.structures.Slider());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
