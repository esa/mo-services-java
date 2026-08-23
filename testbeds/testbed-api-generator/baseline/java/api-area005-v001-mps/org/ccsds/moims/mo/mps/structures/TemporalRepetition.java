package org.ccsds.moims.mo.mps.structures;

/**
 * E1: A sub-type of Repetition based on time.
 */
public final class TemporalRepetition extends org.ccsds.moims.mo.mps.structures.Repetition {

    private static final long serialVersionUID = 1407374900330557L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330557L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Nominal time of first occurrence.
     */
    private org.ccsds.moims.mo.mal.structures.Element initialTime;

    /**
     * The required time interval between occurrences.
     */
    private org.ccsds.moims.mo.mal.structures.Element separation;

    /**
     * The allowed tolerance (+/-) in the required time between occurrences, the
     * interpretation of which is dependent on the separationType.
     */
    private org.ccsds.moims.mo.mal.structures.Element tolerance;

    /**
     * Default constructor for TemporalRepetition.
     * 
     */
    public TemporalRepetition() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param count Maximum number of repeat cycles/instances. If not specified there is no limit to the number of repetitions.
     * @param timeWindow Time period over which the repetition is applicable. If not specified repetition continues indefinitely.
     * @param separationType Specifies whether the repetition interval is Relative to the previous occurrence, or Absolute for all occurrences.
     * @param initialTime Nominal time of first occurrence.
     * @param separation The required time interval between occurrences.
     * @param tolerance The allowed tolerance (+/-) in the required time between occurrences, the interpretation of which is dependent on the separationType.
     */
    public TemporalRepetition(Integer count,
            org.ccsds.moims.mo.mps.structures.TimeWindow timeWindow,
            org.ccsds.moims.mo.mps.structures.SeparationTypeEnum separationType,
            org.ccsds.moims.mo.mal.structures.Element initialTime,
            org.ccsds.moims.mo.mal.structures.Element separation,
            org.ccsds.moims.mo.mal.structures.Element tolerance) {
        super(count,
            timeWindow,
            separationType);
        this.initialTime = initialTime;
        this.separation = separation;
        this.tolerance = tolerance;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param separationType Specifies whether the repetition interval is Relative to the previous occurrence, or Absolute for all occurrences.
     * @param initialTime Nominal time of first occurrence.
     * @param separation The required time interval between occurrences.
     * @param tolerance The allowed tolerance (+/-) in the required time between occurrences, the interpretation of which is dependent on the separationType.
     */
    public TemporalRepetition(org.ccsds.moims.mo.mps.structures.SeparationTypeEnum separationType,
            org.ccsds.moims.mo.mal.structures.Element initialTime,
            org.ccsds.moims.mo.mal.structures.Element separation,
            org.ccsds.moims.mo.mal.structures.Element tolerance) {
        super(separationType);
        this.initialTime = initialTime;
        this.separation = separation;
        this.tolerance = tolerance;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.TemporalRepetition();
    }

    /**
     * Returns the field initialTime.
     * 
     * @return The field initialTime
     */
    public org.ccsds.moims.mo.mal.structures.Element getInitialTime() {
        return initialTime;
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
        if (obj instanceof TemporalRepetition) {
            if (! super.equals(obj)) {
                return false;
            }
            TemporalRepetition other = (TemporalRepetition) obj;
            if (initialTime == null) {
                if (other.initialTime != null) {
                    return false;
                }
            } else {
                if (! initialTime.equals(other.initialTime)) {
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
        hash = 83 * hash + (initialTime != null ? initialTime.hashCode() : 0);
        hash = 83 * hash + (separation != null ? separation.hashCode() : 0);
        hash = 83 * hash + (tolerance != null ? tolerance.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(TemporalRepetition: ");
        buf.append(super.toString());
        buf.append(", initialTime=").append(initialTime);
        buf.append(", separation=").append(separation);
        buf.append(", tolerance=").append(tolerance);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (initialTime == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'initialTime' cannot be null!");
        }
        if (separation == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'separation' cannot be null!");
        }
        if (tolerance == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'tolerance' cannot be null!");
        }
        encoder.encodeAbstractElement(initialTime);
        encoder.encodeAbstractElement(separation);
        encoder.encodeAbstractElement(tolerance);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        initialTime = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        separation = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        tolerance = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
