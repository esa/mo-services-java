package org.ccsds.moims.mo.mc.parameter.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * The ParameterRawValue structure holds a new raw value for a specific parameter.
 */
public final class ParameterRawValue implements Composite {

    private static final long serialVersionUID = 1125908513554438L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125908513554438L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The object instance identifier of the parameter identity.
     */
    private Long paramInstId;

    /**
     * The parameter raw value. The value of NULL is a valid value and carries
     * no special significance in the parameter service.
     */
    private Attribute rawValue;

    /**
     * Default constructor for ParameterRawValue.
     * 
     */
    public ParameterRawValue() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param paramInstId The object instance identifier of the parameter identity.
     * @param rawValue The parameter raw value. The value of NULL is a valid value and carries no special significance in the parameter service.
     */
    public ParameterRawValue(Long paramInstId,
            Attribute rawValue) {
        this.paramInstId = paramInstId;
        this.rawValue = rawValue;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param paramInstId The object instance identifier of the parameter identity.
     */
    public ParameterRawValue(Long paramInstId) {
        this.paramInstId = paramInstId;
        this.rawValue = null;
    }

    @Override
    public Element createElement() {
        return new ParameterRawValue();
    }

    /**
     * Returns the field paramInstId.
     * 
     * @return The field paramInstId
     */
    public Long getParamInstId() {
        return paramInstId;
    }

    /**
     * Returns the field rawValue.
     * 
     * @return The field rawValue
     */
    public Attribute getRawValue() {
        return rawValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ParameterRawValue) {
            ParameterRawValue other = (ParameterRawValue) obj;
            if (paramInstId == null) {
                if (other.paramInstId != null) {
                    return false;
                }
            } else {
                if (! paramInstId.equals(other.paramInstId)) {
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
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (paramInstId != null ? paramInstId.hashCode() : 0);
        hash = 83 * hash + (rawValue != null ? rawValue.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ParameterRawValue: ");
        buf.append("paramInstId=").append(paramInstId);
        buf.append(", rawValue=").append(rawValue);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (paramInstId == null) {
            throw new MALException("The field 'paramInstId' cannot be null!");
        }
        encoder.encodeLong(paramInstId);
        encoder.encodeNullableAttribute(rawValue);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        paramInstId = decoder.decodeLong();
        rawValue = (Attribute) decoder.decodeNullableAttribute();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
