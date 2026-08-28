package org.ccsds.moims.mo.mps.structures;

/**
 * E5: In the [simple] resource constraint, the value of the referenced planning
 * resource is constrained against a single value for the entire duration
 * of the planning activity to which the constraint applies. With the complex
 * resource constraint, the period over which the constraint applies can be
 * customized relative to the planning activity to which the constraint applies;
 * and the value against which the referenced planning resource is constrained
 * can be specified as a relative resource profile which evolves over time.
 * The fields of the complex resource constraint extend or modify those of
 * the [simple] resource constraint as given below.
 */
public final class ComplexResourceConstraint extends org.ccsds.moims.mo.mps.structures.ResourceConstraint {

    private static final long serialVersionUID = 1407374900330534L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330534L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Identifies the point in the duration of the applicable planning activity
     * to which the start of the constraint period relates. Default is the start
     * of the planning activity.
     */
    private org.ccsds.moims.mo.mps.structures.Slider startRef;

    /**
     * Identifies the point in the duration of the applicable planning activity
     * to which the end of the constraint period relates. Default is the end of
     * the planning activity.
     */
    private org.ccsds.moims.mo.mps.structures.Slider endRef;

    /**
     * Offset from startRef that specifies the start of the constraint period.
     * A positive offset implies a shift later in time. Default is no offset.
     */
    private org.ccsds.moims.mo.mal.structures.Element startOffset;

    /**
     * Offset from endRef that specifies the end of the constraint period.  A
     * positive offset implies a shift later in time. Default is no offset.
     */
    private org.ccsds.moims.mo.mal.structures.Element endOffset;

    /**
     * ResourceProfile specifying an evolving value over time against which the
     * value of the planning resource is to be compared (see 4.5.4.4).
     */
    private org.ccsds.moims.mo.mps.structures.RelativeResourceProfile valueProfile;

    /**
     * Default constructor for ComplexResourceConstraint.
     * 
     */
    public ComplexResourceConstraint() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param negate Specifies whether the result of combining the Constraints is to be inverted (NOT function). Default = False.
     * @param resourceRef Identifies the planning resource that is constrained for the duration of the planning activity.
     * @param comparator Comparison operator, which may be one of: =, !=, _, _=, _, _=, contains, icontains. The contains operator only applies to strings and may be case sensitive or insensitive.
     * @param startRef Identifies the point in the duration of the applicable planning activity to which the start of the constraint period relates. Default is the start of the planning activity.
     * @param endRef Identifies the point in the duration of the applicable planning activity to which the end of the constraint period relates. Default is the end of the planning activity.
     * @param startOffset Offset from startRef that specifies the start of the constraint period.  A positive offset implies a shift later in time. Default is no offset.
     * @param endOffset Offset from endRef that specifies the end of the constraint period.  A positive offset implies a shift later in time. Default is no offset.
     * @param valueProfile ResourceProfile specifying an evolving value over time against which the value of the planning resource is to be compared (see 4.5.4.4).
     */
    public ComplexResourceConstraint(Boolean negate,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Resource> resourceRef,
            org.ccsds.moims.mo.mps.structures.ExpressionOperatorEnum comparator,
            org.ccsds.moims.mo.mps.structures.Slider startRef,
            org.ccsds.moims.mo.mps.structures.Slider endRef,
            org.ccsds.moims.mo.mal.structures.Element startOffset,
            org.ccsds.moims.mo.mal.structures.Element endOffset,
            org.ccsds.moims.mo.mps.structures.RelativeResourceProfile valueProfile) {
        super(negate,
            resourceRef,
            comparator);
        this.startRef = startRef;
        this.endRef = endRef;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.valueProfile = valueProfile;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param resourceRef Identifies the planning resource that is constrained for the duration of the planning activity.
     * @param comparator Comparison operator, which may be one of: =, !=, _, _=, _, _=, contains, icontains. The contains operator only applies to strings and may be case sensitive or insensitive.
     * @param valueProfile ResourceProfile specifying an evolving value over time against which the value of the planning resource is to be compared (see 4.5.4.4).
     */
    public ComplexResourceConstraint(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Resource> resourceRef,
            org.ccsds.moims.mo.mps.structures.ExpressionOperatorEnum comparator,
            org.ccsds.moims.mo.mps.structures.RelativeResourceProfile valueProfile) {
        super(resourceRef,
            comparator);
        this.startRef = null;
        this.endRef = null;
        this.startOffset = null;
        this.endOffset = null;
        this.valueProfile = valueProfile;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.ComplexResourceConstraint();
    }

    /**
     * Returns the field startRef.
     * 
     * @return The field startRef
     */
    public org.ccsds.moims.mo.mps.structures.Slider getStartRef() {
        return startRef;
    }

    /**
     * Returns the field endRef.
     * 
     * @return The field endRef
     */
    public org.ccsds.moims.mo.mps.structures.Slider getEndRef() {
        return endRef;
    }

    /**
     * Returns the field startOffset.
     * 
     * @return The field startOffset
     */
    public org.ccsds.moims.mo.mal.structures.Element getStartOffset() {
        return startOffset;
    }

    /**
     * Returns the field endOffset.
     * 
     * @return The field endOffset
     */
    public org.ccsds.moims.mo.mal.structures.Element getEndOffset() {
        return endOffset;
    }

    /**
     * Returns the field valueProfile.
     * 
     * @return The field valueProfile
     */
    public org.ccsds.moims.mo.mps.structures.RelativeResourceProfile getValueProfile() {
        return valueProfile;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ComplexResourceConstraint) {
            if (! super.equals(obj)) {
                return false;
            }
            ComplexResourceConstraint other = (ComplexResourceConstraint) obj;
            if (startRef == null) {
                if (other.startRef != null) {
                    return false;
                }
            } else {
                if (! startRef.equals(other.startRef)) {
                    return false;
                }
            }
            if (endRef == null) {
                if (other.endRef != null) {
                    return false;
                }
            } else {
                if (! endRef.equals(other.endRef)) {
                    return false;
                }
            }
            if (startOffset == null) {
                if (other.startOffset != null) {
                    return false;
                }
            } else {
                if (! startOffset.equals(other.startOffset)) {
                    return false;
                }
            }
            if (endOffset == null) {
                if (other.endOffset != null) {
                    return false;
                }
            } else {
                if (! endOffset.equals(other.endOffset)) {
                    return false;
                }
            }
            if (valueProfile == null) {
                if (other.valueProfile != null) {
                    return false;
                }
            } else {
                if (! valueProfile.equals(other.valueProfile)) {
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
        hash = 83 * hash + (startRef != null ? startRef.hashCode() : 0);
        hash = 83 * hash + (endRef != null ? endRef.hashCode() : 0);
        hash = 83 * hash + (startOffset != null ? startOffset.hashCode() : 0);
        hash = 83 * hash + (endOffset != null ? endOffset.hashCode() : 0);
        hash = 83 * hash + (valueProfile != null ? valueProfile.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ComplexResourceConstraint: ");
        buf.append(super.toString());
        buf.append(", startRef=").append(startRef);
        buf.append(", endRef=").append(endRef);
        buf.append(", startOffset=").append(startOffset);
        buf.append(", endOffset=").append(endOffset);
        buf.append(", valueProfile=").append(valueProfile);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (valueProfile == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'valueProfile' cannot be null!");
        }
        encoder.encodeNullableElement(startRef);
        encoder.encodeNullableElement(endRef);
        encoder.encodeNullableAbstractElement(startOffset);
        encoder.encodeNullableAbstractElement(endOffset);
        encoder.encodeElement(valueProfile);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        startRef = (org.ccsds.moims.mo.mps.structures.Slider) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.Slider());
        endRef = (org.ccsds.moims.mo.mps.structures.Slider) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.Slider());
        startOffset = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeNullableAbstractElement();
        endOffset = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeNullableAbstractElement();
        valueProfile = (org.ccsds.moims.mo.mps.structures.RelativeResourceProfile) decoder.decodeElement(new org.ccsds.moims.mo.mps.structures.RelativeResourceProfile());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
