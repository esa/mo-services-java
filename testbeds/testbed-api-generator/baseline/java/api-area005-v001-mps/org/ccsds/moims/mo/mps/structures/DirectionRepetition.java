package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E6: A sub-type of Repetition based on direction, which supports the specification
 * of astronomical surveys.
 */
public final class DirectionRepetition extends Repetition {

    private static final long serialVersionUID = 1407374900330555L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330555L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Nominal direction of first occurrence.
     */
    private Element initialDirection;

    /**
     * Specifies the direction of repetition as line connecting the initial and
     * target directions.
     */
    private Element targetDirection;

    /**
     * The required angle between occurrences.
     */
    private Element separation;

    /**
     * The allowed tolerance (+/-) in the required angle between occurrences,
     * the interpretation of which is dependent on the separationType.
     */
    private Element tolerance;

    /**
     * Default constructor for DirectionRepetition.
     * 
     */
    public DirectionRepetition() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param count Maximum number of repeat cycles/instances. If not specified there is no limit to the number of repetitions.
     * @param timeWindow Time period over which the repetition is applicable. If not specified repetition continues indefinitely.
     * @param separationType Specifies whether the repetition interval is Relative to the previous occurrence, or Absolute for all occurrences.
     * @param initialDirection Nominal direction of first occurrence.
     * @param targetDirection Specifies the direction of repetition as line connecting the initial and target directions.
     * @param separation The required angle between occurrences.
     * @param tolerance The allowed tolerance (+/-) in the required angle between occurrences, the interpretation of which is dependent on the separationType.
     */
    public DirectionRepetition(Integer count,
            TimeWindow timeWindow,
            SeparationTypeEnum separationType,
            Element initialDirection,
            Element targetDirection,
            Element separation,
            Element tolerance) {
        super(count,
            timeWindow,
            separationType);
        this.initialDirection = initialDirection;
        this.targetDirection = targetDirection;
        this.separation = separation;
        this.tolerance = tolerance;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param separationType Specifies whether the repetition interval is Relative to the previous occurrence, or Absolute for all occurrences.
     * @param initialDirection Nominal direction of first occurrence.
     * @param targetDirection Specifies the direction of repetition as line connecting the initial and target directions.
     * @param separation The required angle between occurrences.
     * @param tolerance The allowed tolerance (+/-) in the required angle between occurrences, the interpretation of which is dependent on the separationType.
     */
    public DirectionRepetition(SeparationTypeEnum separationType,
            Element initialDirection,
            Element targetDirection,
            Element separation,
            Element tolerance) {
        super(separationType);
        this.initialDirection = initialDirection;
        this.targetDirection = targetDirection;
        this.separation = separation;
        this.tolerance = tolerance;
    }

    @Override
    public Element createElement() {
        return new DirectionRepetition();
    }

    /**
     * Returns the field initialDirection.
     * 
     * @return The field initialDirection
     */
    public Element getInitialDirection() {
        return initialDirection;
    }

    /**
     * Returns the field targetDirection.
     * 
     * @return The field targetDirection
     */
    public Element getTargetDirection() {
        return targetDirection;
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
        if (obj instanceof DirectionRepetition) {
            if (! super.equals(obj)) {
                return false;
            }
            DirectionRepetition other = (DirectionRepetition) obj;
            if (initialDirection == null) {
                if (other.initialDirection != null) {
                    return false;
                }
            } else {
                if (! initialDirection.equals(other.initialDirection)) {
                    return false;
                }
            }
            if (targetDirection == null) {
                if (other.targetDirection != null) {
                    return false;
                }
            } else {
                if (! targetDirection.equals(other.targetDirection)) {
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
        hash = 83 * hash + (initialDirection != null ? initialDirection.hashCode() : 0);
        hash = 83 * hash + (targetDirection != null ? targetDirection.hashCode() : 0);
        hash = 83 * hash + (separation != null ? separation.hashCode() : 0);
        hash = 83 * hash + (tolerance != null ? tolerance.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(DirectionRepetition: ");
        buf.append(super.toString());
        buf.append(", initialDirection=").append(initialDirection);
        buf.append(", targetDirection=").append(targetDirection);
        buf.append(", separation=").append(separation);
        buf.append(", tolerance=").append(tolerance);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (initialDirection == null) {
            throw new MALException("The field 'initialDirection' cannot be null!");
        }
        if (targetDirection == null) {
            throw new MALException("The field 'targetDirection' cannot be null!");
        }
        if (separation == null) {
            throw new MALException("The field 'separation' cannot be null!");
        }
        if (tolerance == null) {
            throw new MALException("The field 'tolerance' cannot be null!");
        }
        encoder.encodeAbstractElement(initialDirection);
        encoder.encodeAbstractElement(targetDirection);
        encoder.encodeAbstractElement(separation);
        encoder.encodeAbstractElement(tolerance);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        initialDirection = (Element) decoder.decodeAbstractElement();
        targetDirection = (Element) decoder.decodeAbstractElement();
        separation = (Element) decoder.decodeAbstractElement();
        tolerance = (Element) decoder.decodeAbstractElement();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
