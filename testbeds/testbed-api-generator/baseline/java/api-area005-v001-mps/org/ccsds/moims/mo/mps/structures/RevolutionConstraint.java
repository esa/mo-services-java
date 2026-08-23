package org.ccsds.moims.mo.mps.structures;

/**
 * E7: Specifies a range of revolution angles for a rotating spacecraft.
 */
public final class RevolutionConstraint extends org.ccsds.moims.mo.mps.structures.GeometricConstraint {

    private static final long serialVersionUID = 1407374900330538L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330538L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Angle of revolution.
     */
    private org.ccsds.moims.mo.mal.structures.Element revolutionAngle;

    /**
     * Tolerance in the angle of revolution.
     */
    private org.ccsds.moims.mo.mal.structures.Element tolerance;

    /**
     * Default constructor for RevolutionConstraint.
     * 
     */
    public RevolutionConstraint() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param negate Specifies whether the result of combining the Constraints is to be inverted (NOT function). Default = False.
     * @param startRef Identifies the point in the duration of the applicable planning activity to which the start of the constraint period relates. Default is the start of the planning activity.
     * @param endRef Identifies the point in the duration of the applicable planning activity to which the end of the constraint period relates. Default is the end of the planning activity.
     * @param startOffset Offset from startRef that specifies the start of the constraint period.  A positive offset implies a shift later in time. Default is no offset.
     * @param endOffset Offset from endRef that specifies the end of the constraint period.  A positive offset implies a shift later in time. Default is no offset.
     * @param revolutionAngle Angle of revolution.
     * @param tolerance Tolerance in the angle of revolution.
     */
    public RevolutionConstraint(Boolean negate,
            org.ccsds.moims.mo.mps.structures.Slider startRef,
            org.ccsds.moims.mo.mps.structures.Slider endRef,
            org.ccsds.moims.mo.mal.structures.Element startOffset,
            org.ccsds.moims.mo.mal.structures.Element endOffset,
            org.ccsds.moims.mo.mal.structures.Element revolutionAngle,
            org.ccsds.moims.mo.mal.structures.Element tolerance) {
        super(negate,
            startRef,
            endRef,
            startOffset,
            endOffset);
        this.revolutionAngle = revolutionAngle;
        this.tolerance = tolerance;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param revolutionAngle Angle of revolution.
     * @param tolerance Tolerance in the angle of revolution.
     */
    public RevolutionConstraint(org.ccsds.moims.mo.mal.structures.Element revolutionAngle,
            org.ccsds.moims.mo.mal.structures.Element tolerance) {
        this.revolutionAngle = revolutionAngle;
        this.tolerance = tolerance;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.RevolutionConstraint();
    }

    /**
     * Returns the field revolutionAngle.
     * 
     * @return The field revolutionAngle
     */
    public org.ccsds.moims.mo.mal.structures.Element getRevolutionAngle() {
        return revolutionAngle;
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
        if (obj instanceof RevolutionConstraint) {
            if (! super.equals(obj)) {
                return false;
            }
            RevolutionConstraint other = (RevolutionConstraint) obj;
            if (revolutionAngle == null) {
                if (other.revolutionAngle != null) {
                    return false;
                }
            } else {
                if (! revolutionAngle.equals(other.revolutionAngle)) {
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
        hash = 83 * hash + (revolutionAngle != null ? revolutionAngle.hashCode() : 0);
        hash = 83 * hash + (tolerance != null ? tolerance.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(RevolutionConstraint: ");
        buf.append(super.toString());
        buf.append(", revolutionAngle=").append(revolutionAngle);
        buf.append(", tolerance=").append(tolerance);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (revolutionAngle == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'revolutionAngle' cannot be null!");
        }
        if (tolerance == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'tolerance' cannot be null!");
        }
        encoder.encodeAbstractElement(revolutionAngle);
        encoder.encodeAbstractElement(tolerance);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        revolutionAngle = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        tolerance = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
