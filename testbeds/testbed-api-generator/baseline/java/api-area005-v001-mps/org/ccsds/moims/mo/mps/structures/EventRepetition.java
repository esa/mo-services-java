package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E1: A sub-type of Repetition based on planning events.
 */
public final class EventRepetition extends Repetition {

    private static final long serialVersionUID = 1407374900330559L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330559L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Reference to an EventDefinition (type of event).
     */
    private Element eventRef;

    /**
     * Number of occurrences of the planning event required between occurrences
     * of the planning activity.
     */
    private Element separation;

    /**
     * The allowed tolerance (+/-) in the number of occurrences of the planning
     * event between occurrences of the planning activity, the interpretation
     * of which is dependent on the separationType.
     */
    private Element tolerance;

    /**
     * Default constructor for EventRepetition.
     * 
     */
    public EventRepetition() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param count Maximum number of repeat cycles/instances. If not specified there is no limit to the number of repetitions.
     * @param timeWindow Time period over which the repetition is applicable. If not specified repetition continues indefinitely.
     * @param separationType Specifies whether the repetition interval is Relative to the previous occurrence, or Absolute for all occurrences.
     * @param eventRef Reference to an EventDefinition (type of event).
     * @param separation Number of occurrences of the planning event required between occurrences of the planning activity.
     * @param tolerance The allowed tolerance (+/-) in the number of occurrences of the planning event between occurrences of the planning activity, the interpretation of which is dependent on the separationType.
     */
    public EventRepetition(Integer count,
            TimeWindow timeWindow,
            SeparationTypeEnum separationType,
            Element eventRef,
            Element separation,
            Element tolerance) {
        super(count,
            timeWindow,
            separationType);
        this.eventRef = eventRef;
        this.separation = separation;
        this.tolerance = tolerance;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param separationType Specifies whether the repetition interval is Relative to the previous occurrence, or Absolute for all occurrences.
     * @param eventRef Reference to an EventDefinition (type of event).
     * @param separation Number of occurrences of the planning event required between occurrences of the planning activity.
     * @param tolerance The allowed tolerance (+/-) in the number of occurrences of the planning event between occurrences of the planning activity, the interpretation of which is dependent on the separationType.
     */
    public EventRepetition(SeparationTypeEnum separationType,
            Element eventRef,
            Element separation,
            Element tolerance) {
        super(separationType);
        this.eventRef = eventRef;
        this.separation = separation;
        this.tolerance = tolerance;
    }

    @Override
    public Element createElement() {
        return new EventRepetition();
    }

    /**
     * Returns the field eventRef.
     * 
     * @return The field eventRef
     */
    public Element getEventRef() {
        return eventRef;
    }

    /**
     * Returns the field separation.
     * 
     * @return The field separation
     */
    public Element getSeparation() {
        return separation;
    }

    /**
     * Returns the field tolerance.
     * 
     * @return The field tolerance
     */
    public Element getTolerance() {
        return tolerance;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EventRepetition) {
            if (! super.equals(obj)) {
                return false;
            }
            EventRepetition other = (EventRepetition) obj;
            if (eventRef == null) {
                if (other.eventRef != null) {
                    return false;
                }
            } else {
                if (! eventRef.equals(other.eventRef)) {
                    return false;
                }
            }
            if (separation == null) {
                if (other.separation != null) {
                    return false;
                }
            } else {
                if (! separation.equals(other.separation)) {
                    return false;
                }
            }
            if (tolerance == null) {
                if (other.tolerance != null) {
                    return false;
                }
            } else {
                if (! tolerance.equals(other.tolerance)) {
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
        hash = 83 * hash + (eventRef != null ? eventRef.hashCode() : 0);
        hash = 83 * hash + (separation != null ? separation.hashCode() : 0);
        hash = 83 * hash + (tolerance != null ? tolerance.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(EventRepetition: ");
        buf.append(super.toString());
        buf.append(", eventRef=").append(eventRef);
        buf.append(", separation=").append(separation);
        buf.append(", tolerance=").append(tolerance);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (eventRef == null) {
            throw new MALException("The field 'eventRef' cannot be null!");
        }
        if (separation == null) {
            throw new MALException("The field 'separation' cannot be null!");
        }
        if (tolerance == null) {
            throw new MALException("The field 'tolerance' cannot be null!");
        }
        encoder.encodeAbstractElement(eventRef);
        encoder.encodeAbstractElement(separation);
        encoder.encodeAbstractElement(tolerance);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        eventRef = (Element) decoder.decodeAbstractElement();
        separation = (Element) decoder.decodeAbstractElement();
        tolerance = (Element) decoder.decodeAbstractElement();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
