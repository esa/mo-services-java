package org.ccsds.moims.mo.mps.structures;

/**
 * E5: A simple effect applies the defined operation on the specified planning
 * resource at the time relative to the planning activity defined by (timeRef
 * + timeOffset).
 */
public final class SimpleEffect extends org.ccsds.moims.mo.mps.structures.Effect {

    private static final long serialVersionUID = 1407374900330544L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330544L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The point in the duration of the planning activity to which the time of
     * the Effect is relative. 0:  the start of the planning activity 1:  the
     * end of the planning activity.
     */
    private org.ccsds.moims.mo.mps.structures.Slider timeRef;

    /**
     * Offset from timeRef that specifies the time at which the Effect is to be
     * applied. Default is no offset.
     */
    private org.ccsds.moims.mo.mal.structures.Element timeOffset;

    /**
     * Operation to be performed on the planning resource.  One of: SET, INCREASE,
     * DECREASE. Increase and decrease are only applicable to numeric data types.
     */
    private org.ccsds.moims.mo.mps.structures.EffectOperationEnum operator;

    /**
     * The value that the planning resource is to be set to if the Effect operator
     * is SET; or to be increased or decreased by if it is INCREASE or DECREASE.
     * MAL Attribute type must match the dataType of the Resource definition.
     */
    private org.ccsds.moims.mo.mal.structures.Attribute value;

    /**
     * Default constructor for SimpleEffect.
     * 
     */
    public SimpleEffect() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param resourceRef Identifies the planning resource that is constrained for the duration of the planning activity.
     * @param timeRef The point in the duration of the planning activity to which the time of the Effect is relative. 0:  the start of the planning activity 1:  the end of the planning activity
     * @param timeOffset Offset from timeRef that specifies the time at which the Effect is to be applied. Default is no offset.
     * @param operator Operation to be performed on the planning resource.  One of: SET, INCREASE, DECREASE. Increase and decrease are only applicable to numeric data types.
     * @param value The value that the planning resource is to be set to if the Effect operator is SET; or to be increased or decreased by if it is INCREASE or DECREASE. MAL Attribute type must match the dataType of the Resource definition.
     */
    public SimpleEffect(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Resource> resourceRef,
            org.ccsds.moims.mo.mps.structures.Slider timeRef,
            org.ccsds.moims.mo.mal.structures.Element timeOffset,
            org.ccsds.moims.mo.mps.structures.EffectOperationEnum operator,
            org.ccsds.moims.mo.mal.structures.Attribute value) {
        super(resourceRef);
        this.timeRef = timeRef;
        this.timeOffset = timeOffset;
        this.operator = operator;
        this.value = value;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param resourceRef Identifies the planning resource that is constrained for the duration of the planning activity.
     * @param timeRef The point in the duration of the planning activity to which the time of the Effect is relative. 0:  the start of the planning activity 1:  the end of the planning activity
     * @param operator Operation to be performed on the planning resource.  One of: SET, INCREASE, DECREASE. Increase and decrease are only applicable to numeric data types.
     * @param value The value that the planning resource is to be set to if the Effect operator is SET; or to be increased or decreased by if it is INCREASE or DECREASE. MAL Attribute type must match the dataType of the Resource definition.
     */
    public SimpleEffect(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Resource> resourceRef,
            org.ccsds.moims.mo.mps.structures.Slider timeRef,
            org.ccsds.moims.mo.mps.structures.EffectOperationEnum operator,
            org.ccsds.moims.mo.mal.structures.Attribute value) {
        super(resourceRef);
        this.timeRef = timeRef;
        this.timeOffset = null;
        this.operator = operator;
        this.value = value;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.SimpleEffect();
    }

    /**
     * Returns the field timeRef.
     * 
     * @return The field timeRef
     */
    public org.ccsds.moims.mo.mps.structures.Slider getTimeRef() {
        return timeRef;
    }

    /**
     * Returns the field timeOffset.
     * 
     * @return The field timeOffset
     */
    public org.ccsds.moims.mo.mal.structures.Element getTimeOffset() {
        return timeOffset;
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
     * Returns the field value.
     * 
     * @return The field value
     */
    public org.ccsds.moims.mo.mal.structures.Attribute getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SimpleEffect) {
            if (! super.equals(obj)) {
                return false;
            }
            SimpleEffect other = (SimpleEffect) obj;
            if (timeRef == null) {
                if (other.timeRef != null) {
                    return false;
                }
            } else {
                if (! timeRef.equals(other.timeRef)) {
                    return false;
                }
            }
            if (timeOffset == null) {
                if (other.timeOffset != null) {
                    return false;
                }
            } else {
                if (! timeOffset.equals(other.timeOffset)) {
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
            if (value == null) {
                if (other.value != null) {
                    return false;
                }
            } else {
                if (! value.equals(other.value)) {
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
        hash = 83 * hash + (timeRef != null ? timeRef.hashCode() : 0);
        hash = 83 * hash + (timeOffset != null ? timeOffset.hashCode() : 0);
        hash = 83 * hash + (operator != null ? operator.hashCode() : 0);
        hash = 83 * hash + (value != null ? value.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(SimpleEffect: ");
        buf.append(super.toString());
        buf.append(", timeRef=").append(timeRef);
        buf.append(", timeOffset=").append(timeOffset);
        buf.append(", operator=").append(operator);
        buf.append(", value=").append(value);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (timeRef == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'timeRef' cannot be null!");
        }
        if (operator == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'operator' cannot be null!");
        }
        if (value == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'value' cannot be null!");
        }
        encoder.encodeElement(timeRef);
        encoder.encodeNullableAbstractElement(timeOffset);
        encoder.encodeElement(operator);
        encoder.encodeAttribute(value);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        timeRef = (org.ccsds.moims.mo.mps.structures.Slider) decoder.decodeElement(new org.ccsds.moims.mo.mps.structures.Slider());
        timeOffset = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeNullableAbstractElement();
        operator = (org.ccsds.moims.mo.mps.structures.EffectOperationEnum) decoder.decodeElement(org.ccsds.moims.mo.mps.structures.EffectOperationEnum.SET);
        value = (org.ccsds.moims.mo.mal.structures.Attribute) decoder.decodeAttribute();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
