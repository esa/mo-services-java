package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E6: A sub-type of Repetition based on the revolutions of a rotating spacecraft
 * or instrument.
 */
public final class RevolutionRepetition extends Repetition {

    private static final long serialVersionUID = 1407374900330556L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330556L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The required number of revolutions between occurrences.
     */
    private Element revsSeparation;

    /**
     * The allowed tolerance (+/-) in the required number of revolutions between
     * occurrences, the interpretation of which is dependent on the separationType.
     */
    private Element revsTolerance;

    /**
     * Specifies the angle within a revolution.
     */
    private Element revAngle;

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
            TimeWindow timeWindow,
            SeparationTypeEnum separationType,
            Element revsSeparation,
            Element revsTolerance,
            Element revAngle) {
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
    public RevolutionRepetition(SeparationTypeEnum separationType,
            Element revsSeparation,
            Element revsTolerance,
            Element revAngle) {
        super(separationType);
        this.revsSeparation = revsSeparation;
        this.revsTolerance = revsTolerance;
        this.revAngle = revAngle;
    }

    @Override
    public Element createElement() {
        return new RevolutionRepetition();
    }

    /**
     * Returns the field revsSeparation.
     * 
     * @return The field revsSeparation
     */
    public Element getRevsSeparation() {
        return revsSeparation;
    }

    /**
     * Returns the field revsTolerance.
     * 
     * @return The field revsTolerance
     */
    public Element getRevsTolerance() {
        return revsTolerance;
    }

    /**
     * Returns the field revAngle.
     * 
     * @return The field revAngle
     */
    public Element getRevAngle() {
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
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (revsSeparation == null) {
            throw new MALException("The field 'revsSeparation' cannot be null!");
        }
        if (revsTolerance == null) {
            throw new MALException("The field 'revsTolerance' cannot be null!");
        }
        if (revAngle == null) {
            throw new MALException("The field 'revAngle' cannot be null!");
        }
        encoder.encodeAbstractElement(revsSeparation);
        encoder.encodeAbstractElement(revsTolerance);
        encoder.encodeAbstractElement(revAngle);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        revsSeparation = (Element) decoder.decodeAbstractElement();
        revsTolerance = (Element) decoder.decodeAbstractElement();
        revAngle = (Element) decoder.decodeAbstractElement();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
