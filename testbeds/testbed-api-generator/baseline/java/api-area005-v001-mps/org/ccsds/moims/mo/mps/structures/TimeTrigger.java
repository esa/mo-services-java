package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Time;

/**
 * E1: Sub-type of Trigger based on time.  The trigger time is the specified
 * constraint, and will usually match the predicted time on the base class
 * during the planning process, but the actual time could still be slightly
 * different post-execution.
 */
public final class TimeTrigger extends Trigger {

    private static final long serialVersionUID = 1407374900330547L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330547L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Planned time of Trigger.
     */
    private Time triggerTime;

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
    public TimeTrigger(Time time,
            Time triggerTime) {
        super(time);
        this.triggerTime = triggerTime;
    }

    @Override
    public Element createElement() {
        return new TimeTrigger();
    }

    /**
     * Returns the field triggerTime.
     * 
     * @return The field triggerTime
     */
    public Time getTriggerTime() {
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
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (triggerTime == null) {
            throw new MALException("The field 'triggerTime' cannot be null!");
        }
        encoder.encodeTime(triggerTime);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        triggerTime = decoder.decodeTime();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
