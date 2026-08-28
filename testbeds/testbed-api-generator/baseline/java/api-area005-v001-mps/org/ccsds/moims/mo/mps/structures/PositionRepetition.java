package org.ccsds.moims.mo.mps.structures;

/**
 * E6: A sub-type of Repetition that starts at a given Position and repeats
 * based on separation from each subsequent occurrence.
 */
public final class PositionRepetition extends org.ccsds.moims.mo.mps.structures.Repetition {

    private static final long serialVersionUID = 1407374900330553L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330553L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Nominal position of first occurrence.
     */
    private org.ccsds.moims.mo.mal.structures.Element initialPosition;

    /**
     * Direction of repetition.
     */
    private org.ccsds.moims.mo.mal.structures.Element repetitionDirection;

    /**
     * The required Distance between occurrences.
     */
    private org.ccsds.moims.mo.mal.structures.Element separation;

    /**
     * The allowed tolerance (+/-) in the required distance between occurrences,
     * the interpretation of which is dependent on the separationType.
     */
    private org.ccsds.moims.mo.mal.structures.Element tolerance;

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
            org.ccsds.moims.mo.mps.structures.TimeWindow timeWindow,
            org.ccsds.moims.mo.mps.structures.SeparationTypeEnum separationType,
            org.ccsds.moims.mo.mal.structures.Element initialPosition,
            org.ccsds.moims.mo.mal.structures.Element repetitionDirection,
            org.ccsds.moims.mo.mal.structures.Element separation,
            org.ccsds.moims.mo.mal.structures.Element tolerance) {
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
    public PositionRepetition(org.ccsds.moims.mo.mps.structures.SeparationTypeEnum separationType,
            org.ccsds.moims.mo.mal.structures.Element initialPosition,
            org.ccsds.moims.mo.mal.structures.Element repetitionDirection,
            org.ccsds.moims.mo.mal.structures.Element separation,
            org.ccsds.moims.mo.mal.structures.Element tolerance) {
        super(separationType);
        this.initialPosition = initialPosition;
        this.repetitionDirection = repetitionDirection;
        this.separation = separation;
        this.tolerance = tolerance;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.PositionRepetition();
    }

    /**
     * Returns the field initialPosition.
     * 
     * @return The field initialPosition
     */
    public org.ccsds.moims.mo.mal.structures.Element getInitialPosition() {
        return initialPosition;
    }

    /**
     * Returns the field repetitionDirection.
     * 
     * @return The field repetitionDirection
     */
    public org.ccsds.moims.mo.mal.structures.Element getRepetitionDirection() {
        return repetitionDirection;
    }

    /**
     * Returns the field separation.
     * 
     * @return The field separation
     */
    public org.ccsds.moims.mo.mal.structures.Element getSeparation() {
        return separation;
    }

    /**
     * Returns the field tolerance.
     * 
     * @return The field tolerance
     */
    public org.ccsds.moims.mo.mal.structures.Element getTolerance() {
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
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (initialPosition == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'initialPosition' cannot be null!");
        }
        if (repetitionDirection == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'repetitionDirection' cannot be null!");
        }
        if (separation == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'separation' cannot be null!");
        }
        if (tolerance == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'tolerance' cannot be null!");
        }
        encoder.encodeAbstractElement(initialPosition);
        encoder.encodeAbstractElement(repetitionDirection);
        encoder.encodeAbstractElement(separation);
        encoder.encodeAbstractElement(tolerance);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        initialPosition = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        repetitionDirection = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        separation = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        tolerance = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
