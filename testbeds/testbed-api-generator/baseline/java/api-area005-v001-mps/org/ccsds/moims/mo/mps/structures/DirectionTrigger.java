package org.ccsds.moims.mo.mps.structures;

/**
 * E6: Sub-type of Trigger based on pointing.  Depending on the coordinate
 * type of direction used, a margin may be specified in terms of angle from
 * the specified direction.
 */
public final class DirectionTrigger extends org.ccsds.moims.mo.mps.structures.Trigger {

    private static final long serialVersionUID = 1407374900330549L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330549L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Planned direction of Trigger.
     */
    private org.ccsds.moims.mo.mps.structures.Direction triggerDirection;

    /**
     * Defines a cone around the trigger direction within which a direction is
     * considered to meet the trigger condition.
     */
    private org.ccsds.moims.mo.mps.structures.Angle angleMargin;

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
    public DirectionTrigger(org.ccsds.moims.mo.mal.structures.Time time,
            org.ccsds.moims.mo.mps.structures.Direction triggerDirection,
            org.ccsds.moims.mo.mps.structures.Angle angleMargin) {
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
    public DirectionTrigger(org.ccsds.moims.mo.mal.structures.Time time,
            org.ccsds.moims.mo.mps.structures.Direction triggerDirection) {
        super(time);
        this.triggerDirection = triggerDirection;
        this.angleMargin = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.DirectionTrigger();
    }

    /**
     * Returns the field triggerDirection.
     * 
     * @return The field triggerDirection
     */
    public org.ccsds.moims.mo.mps.structures.Direction getTriggerDirection() {
        return triggerDirection;
    }

    /**
     * Returns the field angleMargin.
     * 
     * @return The field angleMargin
     */
    public org.ccsds.moims.mo.mps.structures.Angle getAngleMargin() {
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
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (triggerDirection == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'triggerDirection' cannot be null!");
        }
        encoder.encodeAbstractElement(triggerDirection);
        encoder.encodeNullableElement(angleMargin);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        triggerDirection = (org.ccsds.moims.mo.mps.structures.Direction) decoder.decodeAbstractElement();
        angleMargin = (org.ccsds.moims.mo.mps.structures.Angle) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.Angle());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
