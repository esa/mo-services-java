package org.ccsds.moims.mo.mps.structures;

/**
 * E6: A sub-type of Repetition based on the revolutions of a rotating spacecraft
 * or instrument.
 */
public final class RevolutionRepetition extends org.ccsds.moims.mo.mps.structures.Repetition {

    private static final long serialVersionUID = 1407374900330556L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330556L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The required number of revolutions between occurrences.
     */
    private org.ccsds.moims.mo.mal.structures.Element revsSeparation;

    /**
     * The allowed tolerance (+/-) in the required number of revolutions between
     * occurrences, the interpretation of which is dependent on the separationType.
     */
    private org.ccsds.moims.mo.mal.structures.Element revsTolerance;

    /**
     * Specifies the angle within a revolution.
     */
    private org.ccsds.moims.mo.mal.structures.Element revAngle;

    /**
     * Default constructor for RevolutionRepetition.
     * 
     */
    public RevolutionRepetition() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param count Maximum number of repeat cycles/instances. If not specified there is no limit to the number of repetitions.
     * @param timeWindow Time period over which the repetition is applicable. If not specified repetition continues indefinitely.
     * @param separationType Specifies whether the repetition interval is Relative to the previous occurrence, or Absolute for all occurrences.
     * @param revsSeparation The required number of revolutions between occurrences.
     * @param revsTolerance The allowed tolerance (+/-) in the required number of revolutions between occurrences, the interpretation of which is dependent on the separationType.
     * @param revAngle Specifies the angle within a revolution.
     */
    public RevolutionRepetition(Integer count,
            org.ccsds.moims.mo.mps.structures.TimeWindow timeWindow,
            org.ccsds.moims.mo.mps.structures.SeparationTypeEnum separationType,
            org.ccsds.moims.mo.mal.structures.Element revsSeparation,
            org.ccsds.moims.mo.mal.structures.Element revsTolerance,
            org.ccsds.moims.mo.mal.structures.Element revAngle) {
        super(count,
            timeWindow,
            separationType);
        this.revsSeparation = revsSeparation;
        this.revsTolerance = revsTolerance;
        this.revAngle = revAngle;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param separationType Specifies whether the repetition interval is Relative to the previous occurrence, or Absolute for all occurrences.
     * @param revsSeparation The required number of revolutions between occurrences.
     * @param revsTolerance The allowed tolerance (+/-) in the required number of revolutions between occurrences, the interpretation of which is dependent on the separationType.
     * @param revAngle Specifies the angle within a revolution.
     */
    public RevolutionRepetition(org.ccsds.moims.mo.mps.structures.SeparationTypeEnum separationType,
            org.ccsds.moims.mo.mal.structures.Element revsSeparation,
            org.ccsds.moims.mo.mal.structures.Element revsTolerance,
            org.ccsds.moims.mo.mal.structures.Element revAngle) {
        super(separationType);
        this.revsSeparation = revsSeparation;
        this.revsTolerance = revsTolerance;
        this.revAngle = revAngle;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.RevolutionRepetition();
    }

    /**
     * Returns the field revsSeparation.
     * 
     * @return The field revsSeparation
     */
    public org.ccsds.moims.mo.mal.structures.Element getRevsSeparation() {
        return revsSeparation;
    }

    /**
     * Returns the field revsTolerance.
     * 
     * @return The field revsTolerance
     */
    public org.ccsds.moims.mo.mal.structures.Element getRevsTolerance() {
        return revsTolerance;
    }

    /**
     * Returns the field revAngle.
     * 
     * @return The field revAngle
     */
    public org.ccsds.moims.mo.mal.structures.Element getRevAngle() {
        return revAngle;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RevolutionRepetition) {
            if (! super.equals(obj)) {
                return false;
            }
            RevolutionRepetition other = (RevolutionRepetition) obj;
            if (revsSeparation == null) {
                if (other.revsSeparation != null) {
                    return false;
                }
            } else {
                if (! revsSeparation.equals(other.revsSeparation)) {
                    return false;
                }
            }
            if (revsTolerance == null) {
                if (other.revsTolerance != null) {
                    return false;
                }
            } else {
                if (! revsTolerance.equals(other.revsTolerance)) {
                    return false;
                }
            }
            if (revAngle == null) {
                if (other.revAngle != null) {
                    return false;
                }
            } else {
                if (! revAngle.equals(other.revAngle)) {
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
        hash = 83 * hash + (revsSeparation != null ? revsSeparation.hashCode() : 0);
        hash = 83 * hash + (revsTolerance != null ? revsTolerance.hashCode() : 0);
        hash = 83 * hash + (revAngle != null ? revAngle.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(RevolutionRepetition: ");
        buf.append(super.toString());
        buf.append(", revsSeparation=").append(revsSeparation);
        buf.append(", revsTolerance=").append(revsTolerance);
        buf.append(", revAngle=").append(revAngle);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (revsSeparation == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'revsSeparation' cannot be null!");
        }
        if (revsTolerance == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'revsTolerance' cannot be null!");
        }
        if (revAngle == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'revAngle' cannot be null!");
        }
        encoder.encodeAbstractElement(revsSeparation);
        encoder.encodeAbstractElement(revsTolerance);
        encoder.encodeAbstractElement(revAngle);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        revsSeparation = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        revsTolerance = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        revAngle = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
