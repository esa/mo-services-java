package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.ObjectRef;

/**
 * E1: A data structure that provides the information required to create the
 * EventInstance to be inserted into a Plan using the MPS Plan Edit service.
 */
public final class InsertedEventDetails implements Composite {

    private static final long serialVersionUID = 1407374900330702L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330702L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Reference to the Plan into which the Event is to be inserted.
     */
    private ObjectRef<Plan> plan;

    /**
     * Reference to the EventDefinition.
     */
    private ObjectRef<EventDefinition> eventDefinition;

    /**
     * Specifies the predicted or actual time of the event.  For an inserted event
     * this must be present.
     */
    private FineTime eventTime;

    /**
     * Argument values.
     */
    private ArgumentList arguments;

    /**
     * Default constructor for InsertedEventDetails.
     * 
     */
    public InsertedEventDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param plan Reference to the Plan into which the Event is to be inserted.
     * @param eventDefinition Reference to the EventDefinition.
     * @param eventTime Specifies the predicted or actual time of the event.  For an inserted event this must be present.
     * @param arguments Argument values.
     */
    public InsertedEventDetails(ObjectRef<Plan> plan,
            ObjectRef<EventDefinition> eventDefinition,
            FineTime eventTime,
            ArgumentList arguments) {
        this.plan = plan;
        this.eventDefinition = eventDefinition;
        this.eventTime = eventTime;
        this.arguments = arguments;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param plan Reference to the Plan into which the Event is to be inserted.
     * @param eventDefinition Reference to the EventDefinition.
     * @param eventTime Specifies the predicted or actual time of the event.  For an inserted event this must be present.
     */
    public InsertedEventDetails(ObjectRef<Plan> plan,
            ObjectRef<EventDefinition> eventDefinition,
            FineTime eventTime) {
        this.plan = plan;
        this.eventDefinition = eventDefinition;
        this.eventTime = eventTime;
        this.arguments = null;
    }

    @Override
    public Element createElement() {
        return new InsertedEventDetails();
    }

    /**
     * Returns the field plan.
     * 
     * @return The field plan
     */
    public ObjectRef<Plan> getPlan() {
        return plan;
    }

    /**
     * Returns the field eventDefinition.
     * 
     * @return The field eventDefinition
     */
    public ObjectRef<EventDefinition> getEventDefinition() {
        return eventDefinition;
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof InsertedEventDetails) {
            InsertedEventDetails other = (InsertedEventDetails) obj;
            if (plan == null) {
                if (other.plan != null) {
                    return false;
                }
            } else {
                if (! plan.equals(other.plan)) {
                    return false;
                }
            }
            if (eventDefinition == null) {
                if (other.eventDefinition != null) {
                    return false;
                }
            } else {
                if (! eventDefinition.equals(other.eventDefinition)) {
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
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (plan != null ? plan.hashCode() : 0);
        hash = 83 * hash + (eventDefinition != null ? eventDefinition.hashCode() : 0);
        hash = 83 * hash + (eventTime != null ? eventTime.hashCode() : 0);
        hash = 83 * hash + (arguments != null ? arguments.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(InsertedEventDetails: ");
        buf.append("plan=").append(plan);
        buf.append(", eventDefinition=").append(eventDefinition);
        buf.append(", eventTime=").append(eventTime);
        buf.append(", arguments=").append(arguments);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (plan == null) {
            throw new MALException("The field 'plan' cannot be null!");
        }
        if (eventDefinition == null) {
            throw new MALException("The field 'eventDefinition' cannot be null!");
        }
        if (eventTime == null) {
            throw new MALException("The field 'eventTime' cannot be null!");
        }
        encoder.encodeElement(plan);
        encoder.encodeElement(eventDefinition);
        encoder.encodeFineTime(eventTime);
        encoder.encodeNullableElement(arguments);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        plan = (ObjectRef<Plan>) decoder.decodeElement(new ObjectRef<Plan>());
        eventDefinition = (ObjectRef<EventDefinition>) decoder.decodeElement(new ObjectRef<EventDefinition>());
        eventTime = decoder.decodeFineTime();
        arguments = (ArgumentList) decoder.decodeNullableElement(new ArgumentList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
