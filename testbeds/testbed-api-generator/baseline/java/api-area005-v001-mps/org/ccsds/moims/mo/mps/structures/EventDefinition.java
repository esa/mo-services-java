package org.ccsds.moims.mo.mps.structures;

/**
 * E1: An EventDefinition is an MO object that contains static configuration
 * data relating to multiple occurrences of a planning event.  Its identity
 * is defined by a definitionID, which includes a constant key and an evolving
 * version, which is updated each time the definition is revised.  Event definitions
 * form part of the planning configuration data. Events may be either Predicted
 * or Potential.  Events that are predictable either by time or position can
 * have specific instances included in a Plan.  Potential events are those
 * that may occur during the execution of a Plan, but the specific time or
 * position is not predicted.
 */
public final class EventDefinition extends org.ccsds.moims.mo.mal.structures.MOObject {

    private static final long serialVersionUID = 1407374900330697L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330697L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Description of the event.
     */
    private String description;

    /**
     * Enumeration: one of {Predicted, Potential} indicating whether the event
     * occurrence is known in advance or can occur at any time.  .
     */
    private org.ccsds.moims.mo.mps.structures.PredictabilityEnum predictability;

    /**
     * Free-text field that can be used to categorize an event into one of several
     * arbitrary categories.  Enables a planning system to customize behavior
     * for events, such as their presentation in displays, based on the specified
     * value.
     */
    private String eventType;

    /**
     * List of argument definitions.
     */
    private org.ccsds.moims.mo.mps.structures.ArgDefList argDefs;

    /**
     * List of child event definitions.  For a single event, this list shall be
     * empty; for a group event, the list shall be populated.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRefList eventDefinitions;

    /**
     * Default constructor for EventDefinition.
     * 
     */
    public EventDefinition() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param description Description of the event.
     * @param predictability Enumeration: one of {Predicted, Potential} indicating whether the event occurrence is known in advance or can occur at any time.  
     * @param eventType Free-text field that can be used to categorize an event into one of several arbitrary categories.  Enables a planning system to customize behavior for events, such as their presentation in displays, based on the specified value.
     * @param argDefs List of argument definitions.
     * @param eventDefinitions List of child event definitions.  For a single event, this list shall be empty; for a group event, the list shall be populated.
     */
    public EventDefinition(org.ccsds.moims.mo.mal.structures.ObjectIdentity objectIdentity,
            String description,
            org.ccsds.moims.mo.mps.structures.PredictabilityEnum predictability,
            String eventType,
            org.ccsds.moims.mo.mps.structures.ArgDefList argDefs,
            org.ccsds.moims.mo.mal.structures.ObjectRefList eventDefinitions) {
        super(objectIdentity);
        this.description = description;
        this.predictability = predictability;
        this.eventType = eventType;
        this.argDefs = argDefs;
        this.eventDefinitions = eventDefinitions;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param description Description of the event.
     * @param predictability Enumeration: one of {Predicted, Potential} indicating whether the event occurrence is known in advance or can occur at any time.  
     */
    public EventDefinition(org.ccsds.moims.mo.mal.structures.ObjectIdentity objectIdentity,
            String description,
            org.ccsds.moims.mo.mps.structures.PredictabilityEnum predictability) {
        super(objectIdentity);
        this.description = description;
        this.predictability = predictability;
        this.eventType = null;
        this.argDefs = null;
        this.eventDefinitions = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.EventDefinition();
    }

    /**
     * Returns the field description.
     * 
     * @return The field description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the field predictability.
     * 
     * @return The field predictability
     */
    public org.ccsds.moims.mo.mps.structures.PredictabilityEnum getPredictability() {
        return predictability;
    }

    /**
     * Returns the field eventType.
     * 
     * @return The field eventType
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Returns the field argDefs.
     * 
     * @return The field argDefs
     */
    public org.ccsds.moims.mo.mps.structures.ArgDefList getArgDefs() {
        return argDefs;
    }

    /**
     * Returns the field eventDefinitions.
     * 
     * @return The field eventDefinitions
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRefList getEventDefinitions() {
        return eventDefinitions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EventDefinition) {
            if (! super.equals(obj)) {
                return false;
            }
            EventDefinition other = (EventDefinition) obj;
            if (description == null) {
                if (other.description != null) {
                    return false;
                }
            } else {
                if (! description.equals(other.description)) {
                    return false;
                }
            }
            if (predictability == null) {
                if (other.predictability != null) {
                    return false;
                }
            } else {
                if (! predictability.equals(other.predictability)) {
                    return false;
                }
            }
            if (eventType == null) {
                if (other.eventType != null) {
                    return false;
                }
            } else {
                if (! eventType.equals(other.eventType)) {
                    return false;
                }
            }
            if (argDefs == null) {
                if (other.argDefs != null) {
                    return false;
                }
            } else {
                if (! argDefs.equals(other.argDefs)) {
                    return false;
                }
            }
            if (eventDefinitions == null) {
                if (other.eventDefinitions != null) {
                    return false;
                }
            } else {
                if (! eventDefinitions.equals(other.eventDefinitions)) {
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
        hash = 83 * hash + (description != null ? description.hashCode() : 0);
        hash = 83 * hash + (predictability != null ? predictability.hashCode() : 0);
        hash = 83 * hash + (eventType != null ? eventType.hashCode() : 0);
        hash = 83 * hash + (argDefs != null ? argDefs.hashCode() : 0);
        hash = 83 * hash + (eventDefinitions != null ? eventDefinitions.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(EventDefinition: ");
        buf.append(super.toString());
        buf.append(", description=").append(description);
        buf.append(", predictability=").append(predictability);
        buf.append(", eventType=").append(eventType);
        buf.append(", argDefs=").append(argDefs);
        buf.append(", eventDefinitions=").append(eventDefinitions);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (description == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'description' cannot be null!");
        }
        if (predictability == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'predictability' cannot be null!");
        }
        encoder.encodeString(description);
        encoder.encodeElement(predictability);
        encoder.encodeNullableString(eventType);
        encoder.encodeNullableElement(argDefs);
        encoder.encodeNullableElement(eventDefinitions);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        description = decoder.decodeString();
        predictability = (org.ccsds.moims.mo.mps.structures.PredictabilityEnum) decoder.decodeElement(org.ccsds.moims.mo.mps.structures.PredictabilityEnum.PREDICTED);
        eventType = decoder.decodeNullableString();
        argDefs = (org.ccsds.moims.mo.mps.structures.ArgDefList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.ArgDefList());
        eventDefinitions = (org.ccsds.moims.mo.mal.structures.ObjectRefList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.ObjectRefList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
