package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Time;

/**
 * E1: Sub-type of Trigger based on planning event.
 */
public final class EventTrigger extends Trigger {

    private static final long serialVersionUID = 1407374900330551L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330551L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Reference to an EventInstance.
     */
    private ObjectRef<EventInstance> triggerEvent;

    /**
     * Time offset from the EventInstance.
     */
    private Duration timeOffset;

    /**
     * Default constructor for EventTrigger.
     * 
     */
    public EventTrigger() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param time Predicted or actual time of Trigger.  The predicted time may evolve during the planning process up to the time of execution.  The actual time is only available post execution, and hence can only be provided by a plan execution function.
     * @param triggerEvent Reference to an EventInstance
     * @param timeOffset Time offset from the EventInstance
     */
    public EventTrigger(Time time,
            ObjectRef<EventInstance> triggerEvent,
            Duration timeOffset) {
        super(time);
        this.triggerEvent = triggerEvent;
        this.timeOffset = timeOffset;
    }

    @Override
    public Element createElement() {
        return new EventTrigger();
    }

    /**
     * Returns the field triggerEvent.
     * 
     * @return The field triggerEvent
     */
    public ObjectRef<EventInstance> getTriggerEvent() {
        return triggerEvent;
    }

    /**
     * Returns the field timeOffset.
     * 
     * @return The field timeOffset
     */
    public Duration getTimeOffset() {
        return timeOffset;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EventTrigger) {
            if (! super.equals(obj)) {
                return false;
            }
            EventTrigger other = (EventTrigger) obj;
            if (triggerEvent == null) {
                if (other.triggerEvent != null) {
                    return false;
                }
            } else {
                if (! triggerEvent.equals(other.triggerEvent)) {
                    return false;
                }
            }
            if (timeOffset == null) {
                if (other.timeOffset != null) {
                    return false;
                }
            } else {
                if (! timeOffset.equals(other.timeOffset)) {
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
        hash = 83 * hash + (triggerEvent != null ? triggerEvent.hashCode() : 0);
        hash = 83 * hash + (timeOffset != null ? timeOffset.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(EventTrigger: ");
        buf.append(super.toString());
        buf.append(", triggerEvent=").append(triggerEvent);
        buf.append(", timeOffset=").append(timeOffset);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (triggerEvent == null) {
            throw new MALException("The field 'triggerEvent' cannot be null!");
        }
        if (timeOffset == null) {
            throw new MALException("The field 'timeOffset' cannot be null!");
        }
        encoder.encodeElement(triggerEvent);
        encoder.encodeDuration(timeOffset);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        triggerEvent = (ObjectRef<EventInstance>) decoder.decodeElement(new ObjectRef<EventInstance>());
        timeOffset = decoder.decodeDuration();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
