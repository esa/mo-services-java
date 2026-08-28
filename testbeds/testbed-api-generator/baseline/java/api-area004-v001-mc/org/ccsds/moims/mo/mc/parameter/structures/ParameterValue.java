package org.ccsds.moims.mo.mc.parameter.structures;

/**
 * This structure holds a specific value of the parameter. The type of the
 * value shall match that specified in the parameter definition.
 */
public final class ParameterValue implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125908513554434L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125908513554434L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Holds the validity state for a parameter value. If the parameter is valid
     * then this should be set to &quot;0&quot;.
     */
    private org.ccsds.moims.mo.mal.structures.UOctet validityState;

    /**
     * The parameter raw value. The value of NULL is a valid value and carries
     * no special significance in the parameter service.
     */
    private org.ccsds.moims.mo.mal.structures.Attribute rawValue;

    /**
     * The parameter converted value.
     */
    private org.ccsds.moims.mo.mal.structures.Attribute convertedValue;

    /**
     * Default constructor for ParameterValue.
     * 
     */
    public ParameterValue() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param validityState Holds the validity state for a parameter value. If the parameter is valid then this should be set to '0'.
     * @param rawValue The parameter raw value. The value of NULL is a valid value and carries no special significance in the parameter service.
     * @param convertedValue The parameter converted value.
     */
    public ParameterValue(org.ccsds.moims.mo.mal.structures.UOctet validityState,
            org.ccsds.moims.mo.mal.structures.Attribute rawValue,
            org.ccsds.moims.mo.mal.structures.Attribute convertedValue) {
        this.validityState = validityState;
        this.rawValue = rawValue;
        this.convertedValue = convertedValue;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param validityState Holds the validity state for a parameter value. If the parameter is valid then this should be set to '0'.
     */
    public ParameterValue(org.ccsds.moims.mo.mal.structures.UOctet validityState) {
        this.validityState = validityState;
        this.rawValue = null;
        this.convertedValue = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.parameter.structures.ParameterValue();
    }

    /**
     * Returns the field validityState.
     * 
     * @return The field validityState
     */
    public org.ccsds.moims.mo.mal.structures.UOctet getValidityState() {
        return validityState;
    }

    /**
     * Returns the field rawValue.
     * 
     * @return The field rawValue
     */
    public org.ccsds.moims.mo.mal.structures.Attribute getRawValue() {
        return rawValue;
    }

    /**
     * Returns the field convertedValue.
     * 
     * @return The field convertedValue
     */
    public org.ccsds.moims.mo.mal.structures.Attribute getConvertedValue() {
        return convertedValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ParameterValue) {
            ParameterValue other = (ParameterValue) obj;
            if (validityState == null) {
                if (other.validityState != null) {
                    return false;
                }
            } else {
                if (! validityState.equals(other.validityState)) {
                    return false;
                }
            }
            if (rawValue == null) {
                if (other.rawValue != null) {
                    return false;
                }
            } else {
                if (! rawValue.equals(other.rawValue)) {
                    return false;
                }
            }
            if (convertedValue == null) {
                if (other.convertedValue != null) {
                    return false;
                }
            } else {
                if (! convertedValue.equals(other.convertedValue)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (validityState != null ? validityState.hashCode() : 0);
        hash = 83 * hash + (rawValue != null ? rawValue.hashCode() : 0);
        hash = 83 * hash + (convertedValue != null ? convertedValue.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ParameterValue: ");
        buf.append("validityState=").append(validityState);
        buf.append(", rawValue=").append(rawValue);
        buf.append(", convertedValue=").append(convertedValue);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (validityState == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'validityState' cannot be null!");
        }
        encoder.encodeUOctet(validityState);
        encoder.encodeNullableAttribute(rawValue);
        encoder.encodeNullableAttribute(convertedValue);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        validityState = decoder.decodeUOctet();
        rawValue = (org.ccsds.moims.mo.mal.structures.Attribute) decoder.decodeNullableAttribute();
        convertedValue = (org.ccsds.moims.mo.mal.structures.Attribute) decoder.decodeNullableAttribute();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
