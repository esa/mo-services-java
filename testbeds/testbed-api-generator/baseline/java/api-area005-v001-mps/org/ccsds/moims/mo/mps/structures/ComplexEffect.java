package org.ccsds.moims.mo.mps.structures;

/**
 * E5: In the simple effect, the value of the impacted planning resource is
 * set to the specified value at a single point in time. With the complex
 * effect, the value of the impacted planning resource can be evolved over
 * a specified time period in accordance with a defined RelativeResourceProfile.
 */
public final class ComplexEffect extends org.ccsds.moims.mo.mps.structures.Effect {

    private static final long serialVersionUID = 1407374900330546L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330546L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Identifies the point in the duration of the applicable planning activity
     * to which the start of the effect period relates. Default is the start of
     * the planning activity.
     */
    private org.ccsds.moims.mo.mps.structures.Slider startRef;

    /**
     * Identifies the point in the duration of the applicable planning activity
     * to which the end of the effect period relates. Default is the end of the
     * planning activity.
     */
    private org.ccsds.moims.mo.mps.structures.Slider endRef;

    /**
     * Offset from startRef that specifies the start of the effect period.  A
     * positive offset implies a shift later in time. Default is no offset.
     */
    private org.ccsds.moims.mo.mal.structures.Element startOffset;

    /**
     * Offset from endRef that specifies the end of the effect period.  A positive
     * offset implies a shift later in time. Default is no offset.
     */
    private org.ccsds.moims.mo.mal.structures.Element endOffset;

    /**
     * Operation to be performed on the planning resource.  One of: SET, INCREASE,
     * DECREASE. Increase and decrease are only applicable to numeric data types.
     */
    private org.ccsds.moims.mo.mps.structures.EffectOperationEnum operator;

    /**
     * Resource profile specifying an evolving value to which the value of the
     * planning resource is to be set if the Effect operator is SET; or to be
     * increased/decreased by if it is INCREASE or DECREASE (see 4.5.4.4).
     */
    private org.ccsds.moims.mo.mps.structures.RelativeResourceProfile valueProfile;

    /**
     * Default constructor for ComplexEffect.
     * 
     */
    public ComplexEffect() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param resourceRef Identifies the planning resource that is constrained for the duration of the planning activity.
     * @param startRef Identifies the point in the duration of the applicable planning activity to which the start of the effect period relates. Default is the start of the planning activity.
     * @param endRef Identifies the point in the duration of the applicable planning activity to which the end of the effect period relates. Default is the end of the planning activity.
     * @param startOffset Offset from startRef that specifies the start of the effect period.  A positive offset implies a shift later in time. Default is no offset.
     * @param endOffset Offset from endRef that specifies the end of the effect period.  A positive offset implies a shift later in time. Default is no offset.
     * @param operator Operation to be performed on the planning resource.  One of: SET, INCREASE, DECREASE. Increase and decrease are only applicable to numeric data types.
     * @param valueProfile Resource profile specifying an evolving value to which the value of the planning resource is to be set if the Effect operator is SET; or to be increased/decreased by if it is INCREASE or DECREASE (see 4.5.4.4).
     */
    public ComplexEffect(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Resource> resourceRef,
            org.ccsds.moims.mo.mps.structures.Slider startRef,
            org.ccsds.moims.mo.mps.structures.Slider endRef,
            org.ccsds.moims.mo.mal.structures.Element startOffset,
            org.ccsds.moims.mo.mal.structures.Element endOffset,
            org.ccsds.moims.mo.mps.structures.EffectOperationEnum operator,
            org.ccsds.moims.mo.mps.structures.RelativeResourceProfile valueProfile) {
        super(resourceRef);
        this.startRef = startRef;
        this.endRef = endRef;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.operator = operator;
        this.valueProfile = valueProfile;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param resourceRef Identifies the planning resource that is constrained for the duration of the planning activity.
     * @param operator Operation to be performed on the planning resource.  One of: SET, INCREASE, DECREASE. Increase and decrease are only applicable to numeric data types.
     * @param valueProfile Resource profile specifying an evolving value to which the value of the planning resource is to be set if the Effect operator is SET; or to be increased/decreased by if it is INCREASE or DECREASE (see 4.5.4.4).
     */
    public ComplexEffect(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Resource> resourceRef,
            org.ccsds.moims.mo.mps.structures.EffectOperationEnum operator,
            org.ccsds.moims.mo.mps.structures.RelativeResourceProfile valueProfile) {
        super(resourceRef);
        this.startRef = null;
        this.endRef = null;
        this.startOffset = null;
        this.endOffset = null;
        this.operator = operator;
        this.valueProfile = valueProfile;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.ComplexEffect();
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
     * Returns the field operator.
     * 
     * @return The field operator
     */
    public org.ccsds.moims.mo.mps.structures.EffectOperationEnum getOperator() {
        return operator;
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
        if (obj instanceof ComplexEffect) {
            if (! super.equals(obj)) {
                return false;
            }
            ComplexEffect other = (ComplexEffect) obj;
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
            if (operator == null) {
                if (other.operator != null) {
                    return false;
                }
            } else {
                if (! operator.equals(other.operator)) {
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
        hash = 83 * hash + (operator != null ? operator.hashCode() : 0);
        hash = 83 * hash + (valueProfile != null ? valueProfile.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ComplexEffect: ");
        buf.append(super.toString());
        buf.append(", startRef=").append(startRef);
        buf.append(", endRef=").append(endRef);
        buf.append(", startOffset=").append(startOffset);
        buf.append(", endOffset=").append(endOffset);
        buf.append(", operator=").append(operator);
        buf.append(", valueProfile=").append(valueProfile);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (operator == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'operator' cannot be null!");
        }
        if (valueProfile == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'valueProfile' cannot be null!");
        }
        encoder.encodeNullableElement(startRef);
        encoder.encodeNullableElement(endRef);
        encoder.encodeNullableAbstractElement(startOffset);
        encoder.encodeNullableAbstractElement(endOffset);
        encoder.encodeElement(operator);
        encoder.encodeElement(valueProfile);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        startRef = (org.ccsds.moims.mo.mps.structures.Slider) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.Slider());
        endRef = (org.ccsds.moims.mo.mps.structures.Slider) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.Slider());
        startOffset = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeNullableAbstractElement();
        endOffset = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeNullableAbstractElement();
        operator = (org.ccsds.moims.mo.mps.structures.EffectOperationEnum) decoder.decodeElement(org.ccsds.moims.mo.mps.structures.EffectOperationEnum.SET);
        valueProfile = (org.ccsds.moims.mo.mps.structures.RelativeResourceProfile) decoder.decodeElement(new org.ccsds.moims.mo.mps.structures.RelativeResourceProfile());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
