package org.ccsds.moims.mo.mps.structures;

/**
 * E7: Sub-type of geometric constraint expressed in terms of a specified
 * Position and a tolerance.  The tolerance is defined as a sphere around
 * the specified position, expressed as a distance or angle.  It should be
 * noted that the position itself can be expressed using any of the concrete
 * position subtypes, including orbital and surface positions.  The use of
 * a constraint expressed by OrbitalPosition is particularly relevant for
 * Earth observation satellites with a repetitive ground track and on-board
 * position-based planning function.  The position can also be specified as
 * an expression.
 */
public final class PositionConstraint extends org.ccsds.moims.mo.mps.structures.GeometricConstraint {

    private static final long serialVersionUID = 1407374900330535L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330535L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Specifies the required position expressed using any concrete position type.
     */
    private org.ccsds.moims.mo.mal.structures.Element position;

    /**
     * Specifies the maximum distance or angle from the required position that
     * satisfies the constraint, effectively defining a sphere around the required
     * position.  The PhysicalValue shall be of type Distance or Angle (in case
     * of an OrbitalPosition).
     */
    private org.ccsds.moims.mo.mal.structures.Element tolerance;

    /**
     * Default constructor for PositionConstraint.
     * 
     */
    public PositionConstraint() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param negate Specifies whether the result of combining the Constraints is to be inverted (NOT function). Default = False.
     * @param startRef Identifies the point in the duration of the applicable planning activity to which the start of the constraint period relates. Default is the start of the planning activity.
     * @param endRef Identifies the point in the duration of the applicable planning activity to which the end of the constraint period relates. Default is the end of the planning activity.
     * @param startOffset Offset from startRef that specifies the start of the constraint period.  A positive offset implies a shift later in time. Default is no offset.
     * @param endOffset Offset from endRef that specifies the end of the constraint period.  A positive offset implies a shift later in time. Default is no offset.
     * @param position Specifies the required position expressed using any concrete position type.
     * @param tolerance Specifies the maximum distance or angle from the required position that satisfies the constraint, effectively defining a sphere around the required position.  The PhysicalValue shall be of type Distance or Angle (in case of an OrbitalPosition).
     */
    public PositionConstraint(Boolean negate,
            org.ccsds.moims.mo.mps.structures.Slider startRef,
            org.ccsds.moims.mo.mps.structures.Slider endRef,
            org.ccsds.moims.mo.mal.structures.Element startOffset,
            org.ccsds.moims.mo.mal.structures.Element endOffset,
            org.ccsds.moims.mo.mal.structures.Element position,
            org.ccsds.moims.mo.mal.structures.Element tolerance) {
        super(negate,
            startRef,
            endRef,
            startOffset,
            endOffset);
        this.position = position;
        this.tolerance = tolerance;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param position Specifies the required position expressed using any concrete position type.
     * @param tolerance Specifies the maximum distance or angle from the required position that satisfies the constraint, effectively defining a sphere around the required position.  The PhysicalValue shall be of type Distance or Angle (in case of an OrbitalPosition).
     */
    public PositionConstraint(org.ccsds.moims.mo.mal.structures.Element position,
            org.ccsds.moims.mo.mal.structures.Element tolerance) {
        this.position = position;
        this.tolerance = tolerance;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.PositionConstraint();
    }

    /**
     * Returns the field position.
     * 
     * @return The field position
     */
    public org.ccsds.moims.mo.mal.structures.Element getPosition() {
        return position;
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
        if (obj instanceof PositionConstraint) {
            if (! super.equals(obj)) {
                return false;
            }
            PositionConstraint other = (PositionConstraint) obj;
            if (position == null) {
                if (other.position != null) {
                    return false;
                }
            } else {
                if (! position.equals(other.position)) {
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
        hash = 83 * hash + (position != null ? position.hashCode() : 0);
        hash = 83 * hash + (tolerance != null ? tolerance.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(PositionConstraint: ");
        buf.append(super.toString());
        buf.append(", position=").append(position);
        buf.append(", tolerance=").append(tolerance);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (position == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'position' cannot be null!");
        }
        if (tolerance == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'tolerance' cannot be null!");
        }
        encoder.encodeAbstractElement(position);
        encoder.encodeAbstractElement(tolerance);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        position = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        tolerance = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
