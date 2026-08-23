package org.ccsds.moims.mo.mps.structures;

/**
 * E1: An EventInstance is an MO object that contains the identity of a specific
 * occurrence of a planning event, together with both static and dynamic information
 * associated with that occurrence.  It supports relationships to its definition
 * and source. The source of an EventInstance may be an external event, corresponding
 * to a NAV Predicted Event or a CSS Contact Event. EventInstances may be
 * contained within a Plan. EventInstances may be referenced as a related
 * event by an ActivityInstance, so that the ActivityInstance can reference
 * the timing and arguments of the related EventInstance.
 */
public final class EventInstance extends org.ccsds.moims.mo.mal.structures.MOObject {

    private static final long serialVersionUID = 1407374900330698L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330698L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Reference to the EventDefinition.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventDefinition> definition;

    /**
     * Reference to an external source event (e.g., NAV predicted event, or CSS
     * contact event).
     */
    private org.ccsds.moims.mo.mal.structures.Identifier sourceEvent;

    /**
     * List of references to child EventInstances.  For a single event, this list
     * is empty; for a group event, the list will be populated.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRefList events;

    /**
     * Predicted or actual time of the event.  EventTime is nullable: it can be
     * predicted without an eventTime (e.g., if position based).
     */
    private org.ccsds.moims.mo.mal.structures.FineTime eventTime;

    /**
     * Argument values for each argument defined in the EventDefinition.
     */
    private org.ccsds.moims.mo.mps.structures.ArgumentList arguments;

    /**
     * Current status of the event instance (see event state model in 4.5.3.2).
     */
    private org.ccsds.moims.mo.mps.structures.EventStatusEnum eventStatus;

    /**
     * StatusInfo provides the reason for entering the terminated state and is
     * customizable, but if the following conditions exist then the specified
     * text shall be used: - Occurred (Event has been triggered); - Did Not Occur
     * (Event expired or did not occur within validity period); - Deleted (Event
     * was deleted).
     */
    private String statusInfo;

    /**
     * Default constructor for EventInstance.
     * 
     */
    public EventInstance() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param definition Reference to the EventDefinition.
     * @param sourceEvent Reference to an external source event (e.g., NAV predicted event, or CSS contact event).
     * @param events List of references to child EventInstances.  For a single event, this list is empty; for a group event, the list will be populated.
     * @param eventTime Predicted or actual time of the event.  EventTime is nullable: it can be predicted without an eventTime (e.g., if position based).
     * @param arguments Argument values for each argument defined in the EventDefinition.
     * @param eventStatus Current status of the event instance (see event state model in 4.5.3.2).
     * @param statusInfo StatusInfo provides the reason for entering the terminated state and is customizable, but if the following conditions exist then the specified text shall be used: - Occurred (Event has been triggered); - Did Not Occur (Event expired or did not occur within validity period); - Deleted (Event was deleted).
     */
    public EventInstance(org.ccsds.moims.mo.mal.structures.ObjectIdentity objectIdentity,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventDefinition> definition,
            org.ccsds.moims.mo.mal.structures.Identifier sourceEvent,
            org.ccsds.moims.mo.mal.structures.ObjectRefList events,
            org.ccsds.moims.mo.mal.structures.FineTime eventTime,
            org.ccsds.moims.mo.mps.structures.ArgumentList arguments,
            org.ccsds.moims.mo.mps.structures.EventStatusEnum eventStatus,
            String statusInfo) {
        super(objectIdentity);
        this.definition = definition;
        this.sourceEvent = sourceEvent;
        this.events = events;
        this.eventTime = eventTime;
        this.arguments = arguments;
        this.eventStatus = eventStatus;
        this.statusInfo = statusInfo;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param definition Reference to the EventDefinition.
     * @param eventStatus Current status of the event instance (see event state model in 4.5.3.2).
     */
    public EventInstance(org.ccsds.moims.mo.mal.structures.ObjectIdentity objectIdentity,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventDefinition> definition,
            org.ccsds.moims.mo.mps.structures.EventStatusEnum eventStatus) {
        super(objectIdentity);
        this.definition = definition;
        this.sourceEvent = null;
        this.events = null;
        this.eventTime = null;
        this.arguments = null;
        this.eventStatus = eventStatus;
        this.statusInfo = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.EventInstance();
    }

    /**
     * Returns the field definition.
     * 
     * @return The field definition
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventDefinition> getDefinition() {
        return definition;
    }

    /**
     * Returns the field sourceEvent.
     * 
     * @return The field sourceEvent
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getSourceEvent() {
        return sourceEvent;
    }

    /**
     * Returns the field events.
     * 
     * @return The field events
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRefList getEvents() {
        return events;
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
        if (obj instanceof EventInstance) {
            if (! super.equals(obj)) {
                return false;
            }
            EventInstance other = (EventInstance) obj;
            if (definition == null) {
                if (other.definition != null) {
                    return false;
                }
            } else {
                if (! definition.equals(other.definition)) {
                    return false;
                }
            }
            if (sourceEvent == null) {
                if (other.sourceEvent != null) {
                    return false;
                }
            } else {
                if (! sourceEvent.equals(other.sourceEvent)) {
                    return false;
                }
            }
            if (events == null) {
                if (other.events != null) {
                    return false;
                }
            } else {
                if (! events.equals(other.events)) {
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
        hash = 83 * hash + (definition != null ? definition.hashCode() : 0);
        hash = 83 * hash + (sourceEvent != null ? sourceEvent.hashCode() : 0);
        hash = 83 * hash + (events != null ? events.hashCode() : 0);
        hash = 83 * hash + (eventTime != null ? eventTime.hashCode() : 0);
        hash = 83 * hash + (arguments != null ? arguments.hashCode() : 0);
        hash = 83 * hash + (eventStatus != null ? eventStatus.hashCode() : 0);
        hash = 83 * hash + (statusInfo != null ? statusInfo.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(EventInstance: ");
        buf.append(super.toString());
        buf.append(", definition=").append(definition);
        buf.append(", sourceEvent=").append(sourceEvent);
        buf.append(", events=").append(events);
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
        if (definition == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'definition' cannot be null!");
        }
        if (eventStatus == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'eventStatus' cannot be null!");
        }
        encoder.encodeElement(definition);
        encoder.encodeNullableIdentifier(sourceEvent);
        encoder.encodeNullableElement(events);
        encoder.encodeNullableFineTime(eventTime);
        encoder.encodeNullableElement(arguments);
        encoder.encodeElement(eventStatus);
        encoder.encodeNullableString(statusInfo);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        definition = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventDefinition>) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventDefinition>());
        sourceEvent = decoder.decodeNullableIdentifier();
        events = (org.ccsds.moims.mo.mal.structures.ObjectRefList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.ObjectRefList());
        eventTime = decoder.decodeNullableFineTime();
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
