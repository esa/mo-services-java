package org.ccsds.moims.mo.mps.structures;

/**
 * E6: A sub-type of Repetition based on direction, which supports the specification
 * of astronomical surveys.
 */
public final class DirectionRepetition extends org.ccsds.moims.mo.mps.structures.Repetition {

    private static final long serialVersionUID = 1407374900330555L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330555L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Nominal direction of first occurrence.
     */
    private org.ccsds.moims.mo.mal.structures.Element initialDirection;

    /**
     * Specifies the direction of repetition as line connecting the initial and
     * target directions.
     */
    private org.ccsds.moims.mo.mal.structures.Element targetDirection;

    /**
     * The required angle between occurrences.
     */
    private org.ccsds.moims.mo.mal.structures.Element separation;

    /**
     * The allowed tolerance (+/-) in the required angle between occurrences,
     * the interpretation of which is dependent on the separationType.
     */
    private org.ccsds.moims.mo.mal.structures.Element tolerance;

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
            org.ccsds.moims.mo.mps.structures.TimeWindow timeWindow,
            org.ccsds.moims.mo.mps.structures.SeparationTypeEnum separationType,
            org.ccsds.moims.mo.mal.structures.Element initialDirection,
            org.ccsds.moims.mo.mal.structures.Element targetDirection,
            org.ccsds.moims.mo.mal.structures.Element separation,
            org.ccsds.moims.mo.mal.structures.Element tolerance) {
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
    public DirectionRepetition(org.ccsds.moims.mo.mps.structures.SeparationTypeEnum separationType,
            org.ccsds.moims.mo.mal.structures.Element initialDirection,
            org.ccsds.moims.mo.mal.structures.Element targetDirection,
            org.ccsds.moims.mo.mal.structures.Element separation,
            org.ccsds.moims.mo.mal.structures.Element tolerance) {
        super(separationType);
        this.initialDirection = initialDirection;
        this.targetDirection = targetDirection;
        this.separation = separation;
        this.tolerance = tolerance;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.DirectionRepetition();
    }

    /**
     * Returns the field initialDirection.
     * 
     * @return The field initialDirection
     */
    public org.ccsds.moims.mo.mal.structures.Element getInitialDirection() {
        return initialDirection;
    }

    /**
     * Returns the field targetDirection.
     * 
     * @return The field targetDirection
     */
    public org.ccsds.moims.mo.mal.structures.Element getTargetDirection() {
        return targetDirection;
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
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (initialDirection == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'initialDirection' cannot be null!");
        }
        if (targetDirection == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'targetDirection' cannot be null!");
        }
        if (separation == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'separation' cannot be null!");
        }
        if (tolerance == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'tolerance' cannot be null!");
        }
        encoder.encodeAbstractElement(initialDirection);
        encoder.encodeAbstractElement(targetDirection);
        encoder.encodeAbstractElement(separation);
        encoder.encodeAbstractElement(tolerance);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        initialDirection = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        targetDirection = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        separation = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        tolerance = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
