package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.MOObject;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;

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
public final class EventInstance extends MOObject {

    private static final long serialVersionUID = 1407374900330698L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330698L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Reference to the EventDefinition.
     */
    private ObjectRef<EventDefinition> definition;

    /**
     * Reference to an external source event (e.g., NAV predicted event, or CSS
     * contact event).
     */
    private Identifier sourceEvent;

    /**
     * List of references to child EventInstances.  For a single event, this list
     * is empty; for a group event, the list will be populated.
     */
    private ObjectRefList events;

    /**
     * Predicted or actual time of the event.  EventTime is nullable: it can be
     * predicted without an eventTime (e.g., if position based).
     */
    private FineTime eventTime;

    /**
     * Argument values for each argument defined in the EventDefinition.
     */
    private ArgumentList arguments;

    /**
     * Current status of the event instance (see event state model in 4.5.3.2).
     */
    private EventStatusEnum eventStatus;

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
    public EventInstance(ObjectIdentity objectIdentity,
            ObjectRef<EventDefinition> definition,
            Identifier sourceEvent,
            ObjectRefList events,
            FineTime eventTime,
            ArgumentList arguments,
            EventStatusEnum eventStatus,
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
    public EventInstance(ObjectIdentity objectIdentity,
            ObjectRef<EventDefinition> definition,
            EventStatusEnum eventStatus) {
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
    public Element createElement() {
        return new EventInstance();
    }

    /**
     * Returns the field definition.
     * 
     * @return The field definition
     */
    public ObjectRef<EventDefinition> getDefinition() {
        return definition;
    }

    /**
     * Returns the field sourceEvent.
     * 
     * @return The field sourceEvent
     */
    public Identifier getSourceEvent() {
        return sourceEvent;
    }

    /**
     * Returns the field events.
     * 
     * @return The field events
     */
    public ObjectRefList getEvents() {
        return events;
    }

    /**
     * Returns the field eventTime.
     * 
     * @return The field eventTime
     */
    public FineTime getEventTime() {
        return eventTime;
    }

    /**
     * Returns the field arguments.
     * 
     * @return The field arguments
     */
    public ArgumentList getArguments() {
        return arguments;
    }

    /**
     * Returns the field eventStatus.
     * 
     * @return The field eventStatus
     */
    public EventStatusEnum getEventStatus() {
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
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (definition == null) {
            throw new MALException("The field 'definition' cannot be null!");
        }
        if (eventStatus == null) {
            throw new MALException("The field 'eventStatus' cannot be null!");
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
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        definition = (ObjectRef<EventDefinition>) decoder.decodeElement(new ObjectRef<EventDefinition>());
        sourceEvent = decoder.decodeNullableIdentifier();
        events = (ObjectRefList) decoder.decodeNullableElement(new ObjectRefList());
        eventTime = decoder.decodeNullableFineTime();
        arguments = (ArgumentList) decoder.decodeNullableElement(new ArgumentList());
        eventStatus = (EventStatusEnum) decoder.decodeElement(EventStatusEnum.GROUP);
        statusInfo = decoder.decodeNullableString();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
