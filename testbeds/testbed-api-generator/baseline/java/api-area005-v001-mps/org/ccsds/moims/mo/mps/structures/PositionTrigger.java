package org.ccsds.moims.mo.mps.structures;

/**
 * E6: Sub-type of Trigger based on position.  Depending on the coordinate
 * type of position used, a margin may be specified in terms of distance from
 * the specified position.
 */
public final class PositionTrigger extends org.ccsds.moims.mo.mps.structures.Trigger {

    private static final long serialVersionUID = 1407374900330548L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330548L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Planned position of Trigger.
     */
    private org.ccsds.moims.mo.mps.structures.Position triggerPosition;

    /**
     * Defines a sphere around the trigger position within which a position is
     * considered to meet the trigger condition.
     */
    private org.ccsds.moims.mo.mps.structures.Distance distanceMargin;

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
    public PositionTrigger(org.ccsds.moims.mo.mal.structures.Time time,
            org.ccsds.moims.mo.mps.structures.Position triggerPosition,
            org.ccsds.moims.mo.mps.structures.Distance distanceMargin) {
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
    public PositionTrigger(org.ccsds.moims.mo.mal.structures.Time time,
            org.ccsds.moims.mo.mps.structures.Position triggerPosition) {
        super(time);
        this.triggerPosition = triggerPosition;
        this.distanceMargin = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.PositionTrigger();
    }

    /**
     * Returns the field triggerPosition.
     * 
     * @return The field triggerPosition
     */
    public org.ccsds.moims.mo.mps.structures.Position getTriggerPosition() {
        return triggerPosition;
    }

    /**
     * Returns the field distanceMargin.
     * 
     * @return The field distanceMargin
     */
    public org.ccsds.moims.mo.mps.structures.Distance getDistanceMargin() {
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
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (triggerPosition == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'triggerPosition' cannot be null!");
        }
        encoder.encodeAbstractElement(triggerPosition);
        encoder.encodeNullableElement(distanceMargin);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        triggerPosition = (org.ccsds.moims.mo.mps.structures.Position) decoder.decodeAbstractElement();
        distanceMargin = (org.ccsds.moims.mo.mps.structures.Distance) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.Distance());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
