package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Time;

/**
 * E6: Sub-type of Trigger based on pointing.  Depending on the coordinate
 * type of direction used, a margin may be specified in terms of angle from
 * the specified direction.
 */
public final class DirectionTrigger extends Trigger {

    private static final long serialVersionUID = 1407374900330549L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330549L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Planned direction of Trigger.
     */
    private Direction triggerDirection;

    /**
     * Defines a cone around the trigger direction within which a direction is
     * considered to meet the trigger condition.
     */
    private Angle angleMargin;

    /**
     * Default constructor for DirectionTrigger.
     * 
     */
    public DirectionTrigger() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param time Predicted or actual time of Trigger.  The predicted time may evolve during the planning process up to the time of execution.  The actual time is only available post execution, and hence can only be provided by a plan execution function.
     * @param triggerDirection Planned direction of Trigger.
     * @param angleMargin Defines a cone around the trigger direction within which a direction is considered to meet the trigger condition.
     */
    public DirectionTrigger(Time time,
            Direction triggerDirection,
            Angle angleMargin) {
        super(time);
        this.triggerDirection = triggerDirection;
        this.angleMargin = angleMargin;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param time Predicted or actual time of Trigger.  The predicted time may evolve during the planning process up to the time of execution.  The actual time is only available post execution, and hence can only be provided by a plan execution function.
     * @param triggerDirection Planned direction of Trigger.
     */
    public DirectionTrigger(Time time,
            Direction triggerDirection) {
        super(time);
        this.triggerDirection = triggerDirection;
        this.angleMargin = null;
    }

    @Override
    public Element createElement() {
        return new DirectionTrigger();
    }

    /**
     * Returns the field triggerDirection.
     * 
     * @return The field triggerDirection
     */
    public Direction getTriggerDirection() {
        return triggerDirection;
    }

    /**
     * Returns the field angleMargin.
     * 
     * @return The field angleMargin
     */
    public Angle getAngleMargin() {
        return angleMargin;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DirectionTrigger) {
            if (! super.equals(obj)) {
                return false;
            }
            DirectionTrigger other = (DirectionTrigger) obj;
            if (triggerDirection == null) {
                if (other.triggerDirection != null) {
                    return false;
                }
            } else {
                if (! triggerDirection.equals(other.triggerDirection)) {
                    return false;
                }
            }
            if (angleMargin == null) {
                if (other.angleMargin != null) {
                    return false;
                }
            } else {
                if (! angleMargin.equals(other.angleMargin)) {
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
        hash = 83 * hash + (triggerDirection != null ? triggerDirection.hashCode() : 0);
        hash = 83 * hash + (angleMargin != null ? angleMargin.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(DirectionTrigger: ");
        buf.append(super.toString());
        buf.append(", triggerDirection=").append(triggerDirection);
        buf.append(", angleMargin=").append(angleMargin);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (triggerDirection == null) {
            throw new MALException("The field 'triggerDirection' cannot be null!");
        }
        encoder.encodeAbstractElement(triggerDirection);
        encoder.encodeNullableElement(angleMargin);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        triggerDirection = (Direction) decoder.decodeAbstractElement();
        angleMargin = (Angle) decoder.decodeNullableElement(new Angle());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
