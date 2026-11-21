package org.ccsds.moims.mo.mc.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * The ParameterValueData structure shall be used to hold a specific value
 * of the parameter.
 */
public final class ParameterValueData implements Composite {

    private static final long serialVersionUID = 1125899940397078L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397078L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The validityState field.
     */
    private ValidityState validityState;

    /**
     * The rawValue field.
     */
    private Attribute rawValue;

    /**
     * The convertedValue field.
     */
    private Attribute convertedValue;

    /**
     * Default constructor for ParameterValueData.
     * 
     */
    public ParameterValueData() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param validityState The validityState field.
     * @param rawValue The rawValue field.
     * @param convertedValue The convertedValue field.
     */
    public ParameterValueData(ValidityState validityState,
            Attribute rawValue,
            Attribute convertedValue) {
        this.validityState = validityState;
        this.rawValue = rawValue;
        this.convertedValue = convertedValue;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param validityState The validityState field.
     */
    public ParameterValueData(ValidityState validityState) {
        this.validityState = validityState;
        this.rawValue = null;
        this.convertedValue = null;
    }

    @Override
    public Element createElement() {
        return new ParameterValueData();
    }

    /**
     * Returns the field validityState.
     * 
     * @return The field validityState
     */
    public ValidityState getValidityState() {
        return validityState;
    }

    /**
     * Returns the field rawValue.
     * 
     * @return The field rawValue
     */
    public Attribute getRawValue() {
        return rawValue;
    }

    /**
     * Returns the field convertedValue.
     * 
     * @return The field convertedValue
     */
    public Attribute getConvertedValue() {
        return convertedValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ParameterValueData) {
            ParameterValueData other = (ParameterValueData) obj;
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
        buf.append("(ParameterValueData: ");
        buf.append("validityState=").append(validityState);
        buf.append(", rawValue=").append(rawValue);
        buf.append(", convertedValue=").append(convertedValue);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (validityState == null) {
            throw new MALException("The field 'validityState' cannot be null!");
        }
        encoder.encodeElement(validityState);
        encoder.encodeNullableAttribute(rawValue);
        encoder.encodeNullableAttribute(convertedValue);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        validityState = (ValidityState) decoder.decodeElement(ValidityState.VALID);
        rawValue = (Attribute) decoder.decodeNullableAttribute();
        convertedValue = (Attribute) decoder.decodeNullableAttribute();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
