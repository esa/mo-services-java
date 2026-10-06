package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E6: A sub-type of Repetition that starts at a given Position and repeats
 * based on separation from each subsequent occurrence.
 */
public final class PositionRepetition extends Repetition {

    private static final long serialVersionUID = 1407374900330553L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330553L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Nominal position of first occurrence.
     */
    private Element initialPosition;

    /**
     * Direction of repetition.
     */
    private Element repetitionDirection;

    /**
     * The required Distance between occurrences.
     */
    private Element separation;

    /**
     * The allowed tolerance (+/-) in the required distance between occurrences,
     * the interpretation of which is dependent on the separationType.
     */
    private Element tolerance;

    /**
     * Default constructor for PositionRepetition.
     * 
     */
    public PositionRepetition() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param count Maximum number of repeat cycles/instances. If not specified there is no limit to the number of repetitions.
     * @param timeWindow Time period over which the repetition is applicable. If not specified repetition continues indefinitely.
     * @param separationType Specifies whether the repetition interval is Relative to the previous occurrence, or Absolute for all occurrences.
     * @param initialPosition Nominal position of first occurrence.
     * @param repetitionDirection Direction of repetition.
     * @param separation The required Distance between occurrences.
     * @param tolerance The allowed tolerance (+/-) in the required distance between occurrences, the interpretation of which is dependent on the separationType.
     */
    public PositionRepetition(Integer count,
            TimeWindow timeWindow,
            SeparationTypeEnum separationType,
            Element initialPosition,
            Element repetitionDirection,
            Element separation,
            Element tolerance) {
        super(count,
            timeWindow,
            separationType);
        this.initialPosition = initialPosition;
        this.repetitionDirection = repetitionDirection;
        this.separation = separation;
        this.tolerance = tolerance;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param separationType Specifies whether the repetition interval is Relative to the previous occurrence, or Absolute for all occurrences.
     * @param initialPosition Nominal position of first occurrence.
     * @param repetitionDirection Direction of repetition.
     * @param separation The required Distance between occurrences.
     * @param tolerance The allowed tolerance (+/-) in the required distance between occurrences, the interpretation of which is dependent on the separationType.
     */
    public PositionRepetition(SeparationTypeEnum separationType,
            Element initialPosition,
            Element repetitionDirection,
            Element separation,
            Element tolerance) {
        super(separationType);
        this.initialPosition = initialPosition;
        this.repetitionDirection = repetitionDirection;
        this.separation = separation;
        this.tolerance = tolerance;
    }

    @Override
    public Element createElement() {
        return new PositionRepetition();
    }

    /**
     * Returns the field initialPosition.
     * 
     * @return The field initialPosition
     */
    public Element getInitialPosition() {
        return initialPosition;
    }

    /**
     * Returns the field repetitionDirection.
     * 
     * @return The field repetitionDirection
     */
    public Element getRepetitionDirection() {
        return repetitionDirection;
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
        if (obj instanceof PositionRepetition) {
            if (! super.equals(obj)) {
                return false;
            }
            PositionRepetition other = (PositionRepetition) obj;
            if (initialPosition == null) {
                if (other.initialPosition != null) {
                    return false;
                }
            } else {
                if (! initialPosition.equals(other.initialPosition)) {
                    return false;
                }
            }
            if (repetitionDirection == null) {
                if (other.repetitionDirection != null) {
                    return false;
                }
            } else {
                if (! repetitionDirection.equals(other.repetitionDirection)) {
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
        hash = 83 * hash + (initialPosition != null ? initialPosition.hashCode() : 0);
        hash = 83 * hash + (repetitionDirection != null ? repetitionDirection.hashCode() : 0);
        hash = 83 * hash + (separation != null ? separation.hashCode() : 0);
        hash = 83 * hash + (tolerance != null ? tolerance.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(PositionRepetition: ");
        buf.append(super.toString());
        buf.append(", initialPosition=").append(initialPosition);
        buf.append(", repetitionDirection=").append(repetitionDirection);
        buf.append(", separation=").append(separation);
        buf.append(", tolerance=").append(tolerance);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (initialPosition == null) {
            throw new MALException("The field 'initialPosition' cannot be null!");
        }
        if (repetitionDirection == null) {
            throw new MALException("The field 'repetitionDirection' cannot be null!");
        }
        if (separation == null) {
            throw new MALException("The field 'separation' cannot be null!");
        }
        if (tolerance == null) {
            throw new MALException("The field 'tolerance' cannot be null!");
        }
        encoder.encodeAbstractElement(initialPosition);
        encoder.encodeAbstractElement(repetitionDirection);
        encoder.encodeAbstractElement(separation);
        encoder.encodeAbstractElement(tolerance);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        initialPosition = (Element) decoder.decodeAbstractElement();
        repetitionDirection = (Element) decoder.decodeAbstractElement();
        separation = (Element) decoder.decodeAbstractElement();
        tolerance = (Element) decoder.decodeAbstractElement();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
