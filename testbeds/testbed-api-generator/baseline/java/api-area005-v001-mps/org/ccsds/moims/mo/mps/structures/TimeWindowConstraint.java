package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E2: A time window within which the planning activity is to be planned.
 */
public final class TimeWindowConstraint extends Constraint {

    private static final long serialVersionUID = 1407374900330529L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330529L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The point in the duration of the activity that is constrained to be after
     * the start time of the time window.  Although typically the start of the
     * activity (0), this can be any point up to the end of the activity (1).
     * Default is the start of the planning activity.
     */
    private Slider startRef;

    /**
     * The point in the duration of the activity that is constrained to be before
     * the end time of the time window.  Although typically the end of the activity
     * (1), this can be any point up to the start of the activity (0). Default
     * is the end of the planning activity.
     */
    private Slider endRef;

    /**
     * The [set of] TimeWindows within which the activity must be placed on the
     * Plan.
     */
    private TimeWindowList timeWindows;

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
            Slider startRef,
            Slider endRef,
            TimeWindowList timeWindows) {
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
    public TimeWindowConstraint(TimeWindowList timeWindows) {
        this.startRef = null;
        this.endRef = null;
        this.timeWindows = timeWindows;
    }

    @Override
    public Element createElement() {
        return new TimeWindowConstraint();
    }

    /**
     * Returns the field startRef.
     * 
     * @return The field startRef
     */
    public Slider getStartRef() {
        return startRef;
    }

    /**
     * Returns the field endRef.
     * 
     * @return The field endRef
     */
    public Slider getEndRef() {
        return endRef;
    }

    /**
     * Returns the field timeWindows.
     * 
     * @return The field timeWindows
     */
    public TimeWindowList getTimeWindows() {
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
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (timeWindows == null) {
            throw new MALException("The field 'timeWindows' cannot be null!");
        }
        encoder.encodeNullableElement(startRef);
        encoder.encodeNullableElement(endRef);
        encoder.encodeElement(timeWindows);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        startRef = (Slider) decoder.decodeNullableElement(new Slider());
        endRef = (Slider) decoder.decodeNullableElement(new Slider());
        timeWindows = (TimeWindowList) decoder.decodeElement(new TimeWindowList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
