package org.ccsds.moims.mo.mps.structures;

/**
 * E2: A DurationConstraint restricts the duration of a planning activity
 * within the plan.
 */
public final class DurationConstraint extends org.ccsds.moims.mo.mps.structures.Constraint {

    private static final long serialVersionUID = 1407374900330530L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330530L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Specifies the minimum duration of the planning activity. If omitted, a
     * value of 0 is used.
     */
    private org.ccsds.moims.mo.mal.structures.Element minDuration;

    /**
     * Specifies the maximum duration of the planning activity. If omitted, the
     * maximum representable MAL::Duration value is assumed.
     */
    private org.ccsds.moims.mo.mal.structures.Element maxDuration;

    /**
     * Default constructor for DurationConstraint.
     * 
     */
    public DurationConstraint() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param negate Specifies whether the result of combining the Constraints is to be inverted (NOT function). Default = False.
     * @param minDuration Specifies the minimum duration of the planning activity. If omitted, a value of 0 is used.
     * @param maxDuration Specifies the maximum duration of the planning activity. If omitted, the maximum representable MAL::Duration value is assumed.
     */
    public DurationConstraint(Boolean negate,
            org.ccsds.moims.mo.mal.structures.Element minDuration,
            org.ccsds.moims.mo.mal.structures.Element maxDuration) {
        super(negate);
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.DurationConstraint();
    }

    /**
     * Returns the field minDuration.
     * 
     * @return The field minDuration
     */
    public org.ccsds.moims.mo.mal.structures.Element getMinDuration() {
        return minDuration;
    }

    /**
     * Returns the field maxDuration.
     * 
     * @return The field maxDuration
     */
    public org.ccsds.moims.mo.mal.structures.Element getMaxDuration() {
        return maxDuration;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DurationConstraint) {
            if (! super.equals(obj)) {
                return false;
            }
            DurationConstraint other = (DurationConstraint) obj;
            if (minDuration == null) {
                if (other.minDuration != null) {
                    return false;
                }
            } else {
                if (! minDuration.equals(other.minDuration)) {
                    return false;
                }
            }
            if (maxDuration == null) {
                if (other.maxDuration != null) {
                    return false;
                }
            } else {
                if (! maxDuration.equals(other.maxDuration)) {
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
        hash = 83 * hash + (minDuration != null ? minDuration.hashCode() : 0);
        hash = 83 * hash + (maxDuration != null ? maxDuration.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(DurationConstraint: ");
        buf.append(super.toString());
        buf.append(", minDuration=").append(minDuration);
        buf.append(", maxDuration=").append(maxDuration);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        encoder.encodeNullableAbstractElement(minDuration);
        encoder.encodeNullableAbstractElement(maxDuration);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        minDuration = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeNullableAbstractElement();
        maxDuration = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeNullableAbstractElement();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
