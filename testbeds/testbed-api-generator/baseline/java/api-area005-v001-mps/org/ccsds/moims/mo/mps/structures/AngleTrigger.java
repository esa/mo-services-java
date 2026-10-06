package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Time;

/**
 * E6: Sub-type of Trigger based on the angle subtended between three physical
 * objects.
 */
public final class AngleTrigger extends Trigger {

    private static final long serialVersionUID = 1407374900330550L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330550L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Position of the center object.  The trigger angle is that subtended at
     * the center object by target objects 1 and 2.
     */
    private Position centerObject;

    /**
     * Position of target object 1.
     */
    private Position targetObject1;

    /**
     * Position of target object 2.
     */
    private Position targetObject2;

    /**
     * Minimum angle subtended at the center object by target objects 1 and 2.
     */
    private Angle minAngle;

    /**
     * Maximum angle subtended at the center object by target objects 1 and 2.
     */
    private Angle maxAngle;

    /**
     * Default constructor for AngleTrigger.
     * 
     */
    public AngleTrigger() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param time Predicted or actual time of Trigger.  The predicted time may evolve during the planning process up to the time of execution.  The actual time is only available post execution, and hence can only be provided by a plan execution function.
     * @param centerObject Position of the center object.  The trigger angle is that subtended at the center object by target objects 1 and 2.
     * @param targetObject1 Position of target object 1.
     * @param targetObject2 Position of target object 2.
     * @param minAngle Minimum angle subtended at the center object by target objects 1 and 2.
     * @param maxAngle Maximum angle subtended at the center object by target objects 1 and 2.
     */
    public AngleTrigger(Time time,
            Position centerObject,
            Position targetObject1,
            Position targetObject2,
            Angle minAngle,
            Angle maxAngle) {
        super(time);
        this.centerObject = centerObject;
        this.targetObject1 = targetObject1;
        this.targetObject2 = targetObject2;
        this.minAngle = minAngle;
        this.maxAngle = maxAngle;
    }

    @Override
    public Element createElement() {
        return new AngleTrigger();
    }

    /**
     * Returns the field centerObject.
     * 
     * @return The field centerObject
     */
    public Position getCenterObject() {
        return centerObject;
    }

    /**
     * Returns the field targetObject1.
     * 
     * @return The field targetObject1
     */
    public Position getTargetObject1() {
        return targetObject1;
    }

    /**
     * Returns the field targetObject2.
     * 
     * @return The field targetObject2
     */
    public Position getTargetObject2() {
        return targetObject2;
    }

    /**
     * Returns the field minAngle.
     * 
     * @return The field minAngle
     */
    public Angle getMinAngle() {
        return minAngle;
    }

    /**
     * Returns the field maxAngle.
     * 
     * @return The field maxAngle
     */
    public Angle getMaxAngle() {
        return maxAngle;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AngleTrigger) {
            if (! super.equals(obj)) {
                return false;
            }
            AngleTrigger other = (AngleTrigger) obj;
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
            if (minAngle == null) {
                if (other.minAngle != null) {
                    return false;
                }
            } else {
                if (! minAngle.equals(other.minAngle)) {
                    return false;
                }
            }
            if (maxAngle == null) {
                if (other.maxAngle != null) {
                    return false;
                }
            } else {
                if (! maxAngle.equals(other.maxAngle)) {
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
        hash = 83 * hash + (minAngle != null ? minAngle.hashCode() : 0);
        hash = 83 * hash + (maxAngle != null ? maxAngle.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(AngleTrigger: ");
        buf.append(super.toString());
        buf.append(", centerObject=").append(centerObject);
        buf.append(", targetObject1=").append(targetObject1);
        buf.append(", targetObject2=").append(targetObject2);
        buf.append(", minAngle=").append(minAngle);
        buf.append(", maxAngle=").append(maxAngle);
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
        if (minAngle == null) {
            throw new MALException("The field 'minAngle' cannot be null!");
        }
        if (maxAngle == null) {
            throw new MALException("The field 'maxAngle' cannot be null!");
        }
        encoder.encodeAbstractElement(centerObject);
        encoder.encodeAbstractElement(targetObject1);
        encoder.encodeAbstractElement(targetObject2);
        encoder.encodeElement(minAngle);
        encoder.encodeElement(maxAngle);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        centerObject = (Position) decoder.decodeAbstractElement();
        targetObject1 = (Position) decoder.decodeAbstractElement();
        targetObject2 = (Position) decoder.decodeAbstractElement();
        minAngle = (Angle) decoder.decodeElement(new Angle());
        maxAngle = (Angle) decoder.decodeElement(new Angle());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
