package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E6: A sub-type of Repetition based on the angle subtended between three
 * physical objects.
 */
public final class AngleRepetition extends Repetition {

    private static final long serialVersionUID = 1407374900330558L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330558L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Position of the center object.
     */
    private Element centerObject;

    /**
     * Position of target object 1.
     */
    private Element targetObject1;

    /**
     * Position of target object 2.
     */
    private Element targetObject2;

    /**
     * Initial angle subtended at the center object by target objects 1 and 2.
     */
    private Element initialAngle;

    /**
     * The required angle between occurrences. If this is zero, this implies that
     * repetition is between multiple occurrences of the initialAngle.
     */
    private Element separation;

    /**
     * The allowed tolerance (+/-) in the required angle between occurrences,
     * the interpretation of which is dependent on the separationType.
     */
    private Element tolerance;

    /**
     * Default constructor for AngleRepetition.
     * 
     */
    public AngleRepetition() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param count Maximum number of repeat cycles/instances. If not specified there is no limit to the number of repetitions.
     * @param timeWindow Time period over which the repetition is applicable. If not specified repetition continues indefinitely.
     * @param separationType Specifies whether the repetition interval is Relative to the previous occurrence, or Absolute for all occurrences.
     * @param centerObject Position of the center object.
     * @param targetObject1 Position of target object 1.
     * @param targetObject2 Position of target object 2.
     * @param initialAngle Initial angle subtended at the center object by target objects 1 and 2.
     * @param separation The required angle between occurrences. If this is zero, this implies that repetition is between multiple occurrences of the initialAngle.
     * @param tolerance The allowed tolerance (+/-) in the required angle between occurrences, the interpretation of which is dependent on the separationType.
     */
    public AngleRepetition(Integer count,
            TimeWindow timeWindow,
            SeparationTypeEnum separationType,
            Element centerObject,
            Element targetObject1,
            Element targetObject2,
            Element initialAngle,
            Element separation,
            Element tolerance) {
        super(count,
            timeWindow,
            separationType);
        this.centerObject = centerObject;
        this.targetObject1 = targetObject1;
        this.targetObject2 = targetObject2;
        this.initialAngle = initialAngle;
        this.separation = separation;
        this.tolerance = tolerance;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param separationType Specifies whether the repetition interval is Relative to the previous occurrence, or Absolute for all occurrences.
     * @param centerObject Position of the center object.
     * @param targetObject1 Position of target object 1.
     * @param targetObject2 Position of target object 2.
     * @param initialAngle Initial angle subtended at the center object by target objects 1 and 2.
     * @param separation The required angle between occurrences. If this is zero, this implies that repetition is between multiple occurrences of the initialAngle.
     * @param tolerance The allowed tolerance (+/-) in the required angle between occurrences, the interpretation of which is dependent on the separationType.
     */
    public AngleRepetition(SeparationTypeEnum separationType,
            Element centerObject,
            Element targetObject1,
            Element targetObject2,
            Element initialAngle,
            Element separation,
            Element tolerance) {
        super(separationType);
        this.centerObject = centerObject;
        this.targetObject1 = targetObject1;
        this.targetObject2 = targetObject2;
        this.initialAngle = initialAngle;
        this.separation = separation;
        this.tolerance = tolerance;
    }

    @Override
    public Element createElement() {
        return new AngleRepetition();
    }

    /**
     * Returns the field centerObject.
     * 
     * @return The field centerObject
     */
    public Element getCenterObject() {
        return centerObject;
    }

    /**
     * Returns the field targetObject1.
     * 
     * @return The field targetObject1
     */
    public Element getTargetObject1() {
        return targetObject1;
    }

    /**
     * Returns the field targetObject2.
     * 
     * @return The field targetObject2
     */
    public Element getTargetObject2() {
        return targetObject2;
    }

    /**
     * Returns the field initialAngle.
     * 
     * @return The field initialAngle
     */
    public Element getInitialAngle() {
        return initialAngle;
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
        if (obj instanceof AngleRepetition) {
            if (! super.equals(obj)) {
                return false;
            }
            AngleRepetition other = (AngleRepetition) obj;
            if (centerObject == null) {
                if (other.centerObject != null) {
                    return false;
                }
            } else {
                if (! centerObject.equals(other.centerObject)) {
                    return false;
                }
            }
            if (targetObject1 == null) {
                if (other.targetObject1 != null) {
                    return false;
                }
            } else {
                if (! targetObject1.equals(other.targetObject1)) {
                    return false;
                }
            }
            if (targetObject2 == null) {
                if (other.targetObject2 != null) {
                    return false;
                }
            } else {
                if (! targetObject2.equals(other.targetObject2)) {
                    return false;
                }
            }
            if (initialAngle == null) {
                if (other.initialAngle != null) {
                    return false;
                }
            } else {
                if (! initialAngle.equals(other.initialAngle)) {
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
        hash = 83 * hash + (centerObject != null ? centerObject.hashCode() : 0);
        hash = 83 * hash + (targetObject1 != null ? targetObject1.hashCode() : 0);
        hash = 83 * hash + (targetObject2 != null ? targetObject2.hashCode() : 0);
        hash = 83 * hash + (initialAngle != null ? initialAngle.hashCode() : 0);
        hash = 83 * hash + (separation != null ? separation.hashCode() : 0);
        hash = 83 * hash + (tolerance != null ? tolerance.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(AngleRepetition: ");
        buf.append(super.toString());
        buf.append(", centerObject=").append(centerObject);
        buf.append(", targetObject1=").append(targetObject1);
        buf.append(", targetObject2=").append(targetObject2);
        buf.append(", initialAngle=").append(initialAngle);
        buf.append(", separation=").append(separation);
        buf.append(", tolerance=").append(tolerance);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (centerObject == null) {
            throw new MALException("The field 'centerObject' cannot be null!");
        }
        if (targetObject1 == null) {
            throw new MALException("The field 'targetObject1' cannot be null!");
        }
        if (targetObject2 == null) {
            throw new MALException("The field 'targetObject2' cannot be null!");
        }
        if (initialAngle == null) {
            throw new MALException("The field 'initialAngle' cannot be null!");
        }
        if (separation == null) {
            throw new MALException("The field 'separation' cannot be null!");
        }
        if (tolerance == null) {
            throw new MALException("The field 'tolerance' cannot be null!");
        }
        encoder.encodeAbstractElement(centerObject);
        encoder.encodeAbstractElement(targetObject1);
        encoder.encodeAbstractElement(targetObject2);
        encoder.encodeAbstractElement(initialAngle);
        encoder.encodeAbstractElement(separation);
        encoder.encodeAbstractElement(tolerance);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        centerObject = (Element) decoder.decodeAbstractElement();
        targetObject1 = (Element) decoder.decodeAbstractElement();
        targetObject2 = (Element) decoder.decodeAbstractElement();
        initialAngle = (Element) decoder.decodeAbstractElement();
        separation = (Element) decoder.decodeAbstractElement();
        tolerance = (Element) decoder.decodeAbstractElement();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
