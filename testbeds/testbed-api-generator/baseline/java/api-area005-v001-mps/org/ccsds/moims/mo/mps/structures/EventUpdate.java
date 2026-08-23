package org.ccsds.moims.mo.mps.structures;

/**
 * E1: EventUpdate is a data structure that is used to report the dynamic
 * status of an EventInstance in the context of the MPS Plan Execution Control
 * service monitorPlanExecutionDetail operation. EventUpdates may be distributed
 * to subscribing applications, including status displays, to inform them
 * of the latest status of the event.  This may be particularly relevant in
 * conjunction with a plan execution function.  EventUpdates may be stored
 * in event history to provide a complete record of evolving status over time.
 */
public final class EventUpdate extends org.ccsds.moims.mo.mps.structures.PlanDetailUpdate {

    private static final long serialVersionUID = 1407374900330701L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330701L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Reference to the EventInstance to which the status update relates.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventInstance> eventInstance;

    /**
     * Time of status update.
     */
    private org.ccsds.moims.mo.mal.structures.Time timestamp;

    /**
     * Predicted or actual time of the event.  EventTime is nullable: it can be
     * predicted without an EventTime (e.g., if position based).
     */
    private org.ccsds.moims.mo.mal.structures.FineTime eventTime;

    /**
     * Argument values.
     */
    private org.ccsds.moims.mo.mps.structures.ArgumentList arguments;

    /**
     * Current status of the EventInstance.
     */
    private org.ccsds.moims.mo.mps.structures.EventStatusEnum eventStatus;

    /**
     * StatusInfo provides the reason for entering the Terminated state and is
     * customizable, but if the following conditions exist then the specified
     * text shall be used: - Occurred (Event has been triggered); - Did Not Occur
     * (Event expired or did not occur within validity period); - Deleted (Event
     * was deleted).
     */
    private String statusInfo;

    /**
     * Default constructor for EventUpdate.
     * 
     */
    public EventUpdate() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param eventInstance Reference to the EventInstance to which the status update relates.
     * @param timestamp Time of status update.
     * @param eventTime Predicted or actual time of the event.  EventTime is nullable: it can be predicted without an EventTime (e.g., if position based).
     * @param arguments Argument values.
     * @param eventStatus Current status of the EventInstance.
     * @param statusInfo StatusInfo provides the reason for entering the Terminated state and is customizable, but if the following conditions exist then the specified text shall be used: - Occurred (Event has been triggered); - Did Not Occur (Event expired or did not occur within validity period); - Deleted (Event was deleted).
     */
    public EventUpdate(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventInstance> eventInstance,
            org.ccsds.moims.mo.mal.structures.Time timestamp,
            org.ccsds.moims.mo.mal.structures.FineTime eventTime,
            org.ccsds.moims.mo.mps.structures.ArgumentList arguments,
            org.ccsds.moims.mo.mps.structures.EventStatusEnum eventStatus,
            String statusInfo) {
        this.eventInstance = eventInstance;
        this.timestamp = timestamp;
        this.eventTime = eventTime;
        this.arguments = arguments;
        this.eventStatus = eventStatus;
        this.statusInfo = statusInfo;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param eventInstance Reference to the EventInstance to which the status update relates.
     * @param timestamp Time of status update.
     * @param eventTime Predicted or actual time of the event.  EventTime is nullable: it can be predicted without an EventTime (e.g., if position based).
     * @param eventStatus Current status of the EventInstance.
     */
    public EventUpdate(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventInstance> eventInstance,
            org.ccsds.moims.mo.mal.structures.Time timestamp,
            org.ccsds.moims.mo.mal.structures.FineTime eventTime,
            org.ccsds.moims.mo.mps.structures.EventStatusEnum eventStatus) {
        this.eventInstance = eventInstance;
        this.timestamp = timestamp;
        this.eventTime = eventTime;
        this.arguments = null;
        this.eventStatus = eventStatus;
        this.statusInfo = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.EventUpdate();
    }

    /**
     * Returns the field eventInstance.
     * 
     * @return The field eventInstance
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventInstance> getEventInstance() {
        return eventInstance;
    }

    /**
     * Returns the field timestamp.
     * 
     * @return The field timestamp
     */
    public org.ccsds.moims.mo.mal.structures.Time getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the field eventTime.
     * 
     * @return The field eventTime
     */
    public org.ccsds.moims.mo.mal.structures.FineTime getEventTime() {
        return eventTime;
    }

    /**
     * Returns the field arguments.
     * 
     * @return The field arguments
     */
    public org.ccsds.moims.mo.mps.structures.ArgumentList getArguments() {
        return arguments;
    }

    /**
     * Returns the field eventStatus.
     * 
     * @return The field eventStatus
     */
    public org.ccsds.moims.mo.mps.structures.EventStatusEnum getEventStatus() {
        return eventStatus;
    }

    /**
     * Returns the field statusInfo.
     * 
     * @return The field statusInfo
     */
    public String getStatusInfo() {
        return statusInfo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EventUpdate) {
            if (! super.equals(obj)) {
                return false;
            }
            EventUpdate other = (EventUpdate) obj;
            if (eventInstance == null) {
                if (other.eventInstance != null) {
                    return false;
                }
            } else {
                if (! eventInstance.equals(other.eventInstance)) {
                    return false;
                }
            }
            if (timestamp == null) {
                if (other.timestamp != null) {
                    return false;
                }
            } else {
                if (! timestamp.equals(other.timestamp)) {
                    return false;
                }
            }
            if (eventTime == null) {
                if (other.eventTime != null) {
                    return false;
                }
            } else {
                if (! eventTime.equals(other.eventTime)) {
                    return false;
                }
            }
            if (arguments == null) {
                if (other.arguments != null) {
                    return false;
                }
            } else {
                if (! arguments.equals(other.arguments)) {
                    return false;
                }
            }
            if (eventStatus == null) {
                if (other.eventStatus != null) {
                    return false;
                }
            } else {
                if (! eventStatus.equals(other.eventStatus)) {
                    return false;
                }
            }
            if (statusInfo == null) {
                if (other.statusInfo != null) {
                    return false;
                }
            } else {
                if (! statusInfo.equals(other.statusInfo)) {
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
        hash = 83 * hash + (eventInstance != null ? eventInstance.hashCode() : 0);
        hash = 83 * hash + (timestamp != null ? timestamp.hashCode() : 0);
        hash = 83 * hash + (eventTime != null ? eventTime.hashCode() : 0);
        hash = 83 * hash + (arguments != null ? arguments.hashCode() : 0);
        hash = 83 * hash + (eventStatus != null ? eventStatus.hashCode() : 0);
        hash = 83 * hash + (statusInfo != null ? statusInfo.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(EventUpdate: ");
        buf.append(super.toString());
        buf.append(", eventInstance=").append(eventInstance);
        buf.append(", timestamp=").append(timestamp);
        buf.append(", eventTime=").append(eventTime);
        buf.append(", arguments=").append(arguments);
        buf.append(", eventStatus=").append(eventStatus);
        buf.append(", statusInfo=").append(statusInfo);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (eventInstance == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'eventInstance' cannot be null!");
        }
        if (timestamp == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'timestamp' cannot be null!");
        }
        if (eventTime == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'eventTime' cannot be null!");
        }
        if (eventStatus == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'eventStatus' cannot be null!");
        }
        encoder.encodeElement(eventInstance);
        encoder.encodeTime(timestamp);
        encoder.encodeFineTime(eventTime);
        encoder.encodeNullableElement(arguments);
        encoder.encodeElement(eventStatus);
        encoder.encodeNullableString(statusInfo);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        eventInstance = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventInstance>) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventInstance>());
        timestamp = decoder.decodeTime();
        eventTime = decoder.decodeFineTime();
        arguments = (org.ccsds.moims.mo.mps.structures.ArgumentList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.ArgumentList());
        eventStatus = (org.ccsds.moims.mo.mps.structures.EventStatusEnum) decoder.decodeElement(org.ccsds.moims.mo.mps.structures.EventStatusEnum.GROUP);
        statusInfo = decoder.decodeNullableString();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
