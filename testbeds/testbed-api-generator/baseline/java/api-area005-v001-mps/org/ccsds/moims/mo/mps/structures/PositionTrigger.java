package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Time;

/**
 * E6: Sub-type of Trigger based on position.  Depending on the coordinate
 * type of position used, a margin may be specified in terms of distance from
 * the specified position.
 */
public final class PositionTrigger extends Trigger {

    private static final long serialVersionUID = 1407374900330548L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330548L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Planned position of Trigger.
     */
    private Position triggerPosition;

    /**
     * Defines a sphere around the trigger position within which a position is
     * considered to meet the trigger condition.
     */
    private Distance distanceMargin;

    /**
     * Default constructor for PositionTrigger.
     * 
     */
    public PositionTrigger() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param time Predicted or actual time of Trigger.  The predicted time may evolve during the planning process up to the time of execution.  The actual time is only available post execution, and hence can only be provided by a plan execution function.
     * @param triggerPosition Planned position of Trigger.
     * @param distanceMargin Defines a sphere around the trigger position within which a position is considered to meet the trigger condition.
     */
    public PositionTrigger(Time time,
            Position triggerPosition,
            Distance distanceMargin) {
        super(time);
        this.triggerPosition = triggerPosition;
        this.distanceMargin = distanceMargin;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param time Predicted or actual time of Trigger.  The predicted time may evolve during the planning process up to the time of execution.  The actual time is only available post execution, and hence can only be provided by a plan execution function.
     * @param triggerPosition Planned position of Trigger.
     */
    public PositionTrigger(Time time,
            Position triggerPosition) {
        super(time);
        this.triggerPosition = triggerPosition;
        this.distanceMargin = null;
    }

    @Override
    public Element createElement() {
        return new PositionTrigger();
    }

    /**
     * Returns the field triggerPosition.
     * 
     * @return The field triggerPosition
     */
    public Position getTriggerPosition() {
        return triggerPosition;
    }

    /**
     * Returns the field distanceMargin.
     * 
     * @return The field distanceMargin
     */
    public Distance getDistanceMargin() {
        return distanceMargin;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PositionTrigger) {
            if (! super.equals(obj)) {
                return false;
            }
            PositionTrigger other = (PositionTrigger) obj;
            if (triggerPosition == null) {
                if (other.triggerPosition != null) {
                    return false;
                }
            } else {
                if (! triggerPosition.equals(other.triggerPosition)) {
                    return false;
                }
            }
            if (distanceMargin == null) {
                if (other.distanceMargin != null) {
                    return false;
                }
            } else {
                if (! distanceMargin.equals(other.distanceMargin)) {
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
        hash = 83 * hash + (triggerPosition != null ? triggerPosition.hashCode() : 0);
        hash = 83 * hash + (distanceMargin != null ? distanceMargin.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(PositionTrigger: ");
        buf.append(super.toString());
        buf.append(", triggerPosition=").append(triggerPosition);
        buf.append(", distanceMargin=").append(distanceMargin);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (triggerPosition == null) {
            throw new MALException("The field 'triggerPosition' cannot be null!");
        }
        encoder.encodeAbstractElement(triggerPosition);
        encoder.encodeNullableElement(distanceMargin);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        triggerPosition = (Position) decoder.decodeAbstractElement();
        distanceMargin = (Distance) decoder.decodeNullableElement(new Distance());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
