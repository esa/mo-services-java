package org.ccsds.moims.mo.mps.structures;

/**
 * E1: Sub-type of Trigger based on planning event.
 */
public final class EventTrigger extends org.ccsds.moims.mo.mps.structures.Trigger {

    private static final long serialVersionUID = 1407374900330551L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330551L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Reference to an EventInstance.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventInstance> triggerEvent;

    /**
     * Time offset from the EventInstance.
     */
    private org.ccsds.moims.mo.mal.structures.Duration timeOffset;

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
    public EventTrigger(org.ccsds.moims.mo.mal.structures.Time time,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventInstance> triggerEvent,
            org.ccsds.moims.mo.mal.structures.Duration timeOffset) {
        super(time);
        this.triggerEvent = triggerEvent;
        this.timeOffset = timeOffset;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.EventTrigger();
    }

    /**
     * Returns the field triggerEvent.
     * 
     * @return The field triggerEvent
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventInstance> getTriggerEvent() {
        return triggerEvent;
    }

    /**
     * Returns the field timeOffset.
     * 
     * @return The field timeOffset
     */
    public org.ccsds.moims.mo.mal.structures.Duration getTimeOffset() {
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
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (triggerEvent == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'triggerEvent' cannot be null!");
        }
        if (timeOffset == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'timeOffset' cannot be null!");
        }
        encoder.encodeElement(triggerEvent);
        encoder.encodeDuration(timeOffset);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        triggerEvent = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventInstance>) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventInstance>());
        timeOffset = decoder.decodeDuration();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
