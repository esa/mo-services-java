package org.ccsds.moims.mo.mps.structures;

/**
 * E1: A data structure that provides the information required to create the
 * EventInstance to be inserted into a Plan using the MPS Plan Edit service.
 */
public final class InsertedEventDetails implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1407374900330702L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330702L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Reference to the Plan into which the Event is to be inserted.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> plan;

    /**
     * Reference to the EventDefinition.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventDefinition> eventDefinition;

    /**
     * Specifies the predicted or actual time of the event.  For an inserted event
     * this must be present.
     */
    private org.ccsds.moims.mo.mal.structures.FineTime eventTime;

    /**
     * Argument values.
     */
    private org.ccsds.moims.mo.mps.structures.ArgumentList arguments;

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
    public InsertedEventDetails(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> plan,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventDefinition> eventDefinition,
            org.ccsds.moims.mo.mal.structures.FineTime eventTime,
            org.ccsds.moims.mo.mps.structures.ArgumentList arguments) {
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
    public InsertedEventDetails(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> plan,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventDefinition> eventDefinition,
            org.ccsds.moims.mo.mal.structures.FineTime eventTime) {
        this.plan = plan;
        this.eventDefinition = eventDefinition;
        this.eventTime = eventTime;
        this.arguments = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.InsertedEventDetails();
    }

    /**
     * Returns the field plan.
     * 
     * @return The field plan
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> getPlan() {
        return plan;
    }

    /**
     * Returns the field eventDefinition.
     * 
     * @return The field eventDefinition
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventDefinition> getEventDefinition() {
        return eventDefinition;
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
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (plan == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'plan' cannot be null!");
        }
        if (eventDefinition == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'eventDefinition' cannot be null!");
        }
        if (eventTime == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'eventTime' cannot be null!");
        }
        encoder.encodeElement(plan);
        encoder.encodeElement(eventDefinition);
        encoder.encodeFineTime(eventTime);
        encoder.encodeNullableElement(arguments);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        plan = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>());
        eventDefinition = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventDefinition>) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventDefinition>());
        eventTime = decoder.decodeFineTime();
        arguments = (org.ccsds.moims.mo.mps.structures.ArgumentList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.ArgumentList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
