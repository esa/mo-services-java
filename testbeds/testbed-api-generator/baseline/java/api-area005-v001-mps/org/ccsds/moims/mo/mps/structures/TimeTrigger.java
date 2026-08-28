package org.ccsds.moims.mo.mps.structures;

/**
 * E1: Sub-type of Trigger based on time.  The trigger time is the specified
 * constraint, and will usually match the predicted time on the base class
 * during the planning process, but the actual time could still be slightly
 * different post-execution.
 */
public final class TimeTrigger extends org.ccsds.moims.mo.mps.structures.Trigger {

    private static final long serialVersionUID = 1407374900330547L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330547L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Planned time of Trigger.
     */
    private org.ccsds.moims.mo.mal.structures.Time triggerTime;

    /**
     * Default constructor for TimeTrigger.
     * 
     */
    public TimeTrigger() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param time Predicted or actual time of Trigger.  The predicted time may evolve during the planning process up to the time of execution.  The actual time is only available post execution, and hence can only be provided by a plan execution function.
     * @param triggerTime Planned time of Trigger.
     */
    public TimeTrigger(org.ccsds.moims.mo.mal.structures.Time time,
            org.ccsds.moims.mo.mal.structures.Time triggerTime) {
        super(time);
        this.triggerTime = triggerTime;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.TimeTrigger();
    }

    /**
     * Returns the field triggerTime.
     * 
     * @return The field triggerTime
     */
    public org.ccsds.moims.mo.mal.structures.Time getTriggerTime() {
        return triggerTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TimeTrigger) {
            if (! super.equals(obj)) {
                return false;
            }
            TimeTrigger other = (TimeTrigger) obj;
            if (triggerTime == null) {
                if (other.triggerTime != null) {
                    return false;
                }
            } else {
                if (! triggerTime.equals(other.triggerTime)) {
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
        hash = 83 * hash + (triggerTime != null ? triggerTime.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(TimeTrigger: ");
        buf.append(super.toString());
        buf.append(", triggerTime=").append(triggerTime);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (triggerTime == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'triggerTime' cannot be null!");
        }
        encoder.encodeTime(triggerTime);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        triggerTime = decoder.decodeTime();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
