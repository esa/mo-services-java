package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Time;

/**
 * E1: All sub-classes of Trigger include the time at which they are predicted
 * to occur (in advance of execution); and, where applicable, the time at
 * which they actually occurred (post execution).
 */
public abstract class Trigger implements Composite {

    /**
     * Predicted or actual time of Trigger.  The predicted time may evolve during
     * the planning process up to the time of execution.  The actual time is only
     * available post execution, and hence can only be provided by a plan execution
     * function.
     */
    private Time time;

    /**
     * Default constructor for Trigger.
     * 
     */
    public Trigger() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param time Predicted or actual time of Trigger.  The predicted time may evolve during the planning process up to the time of execution.  The actual time is only available post execution, and hence can only be provided by a plan execution function.
     */
    public Trigger(Time time) {
        this.time = time;
    }

    /**
     * Returns the field time.
     * 
     * @return The field time
     */
    public Time getTime() {
        return time;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Trigger) {
            Trigger other = (Trigger) obj;
            if (time == null) {
                if (other.time != null) {
                    return false;
                }
            } else {
                if (! time.equals(other.time)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (time != null ? time.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(Trigger: ");
        buf.append("time=").append(time);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (time == null) {
            throw new MALException("The field 'time' cannot be null!");
        }
        encoder.encodeTime(time);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        time = decoder.decodeTime();
        return this;
    }

}
