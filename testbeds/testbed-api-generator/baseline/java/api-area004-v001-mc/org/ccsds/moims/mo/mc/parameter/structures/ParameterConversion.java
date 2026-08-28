package org.ccsds.moims.mo.mc.parameter.structures;

/**
 * The ParameterConversion structure holds information about the conversions
 * to be applied to a parameter.
 */
public final class ParameterConversion implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125908513554435L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125908513554435L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Holds the attribute short form part of the converted type of the parameter,
     * e.g., for a MAL::String parameter it shall hold 15.
     */
    private Byte convertedType;

    /**
     * The converted parameter unit. If NULL then converted type has no unit.
     */
    private String convertedUnit;

    /**
     * The conversions to be applied. Only the first TRUE conversion should be
     * applied.
     */
    private org.ccsds.moims.mo.mc.structures.ConditionalConversionList conditionalConversions;

    /**
     * Default constructor for ParameterConversion.
     * 
     */
    public ParameterConversion() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param convertedType Holds the attribute short form part of the converted type of the parameter, e.g., for a MAL::String parameter it shall hold 15.
     * @param convertedUnit The converted parameter unit. If NULL then converted type has no unit.
     * @param conditionalConversions The conversions to be applied. Only the first TRUE conversion should be applied.
     */
    public ParameterConversion(Byte convertedType,
            String convertedUnit,
            org.ccsds.moims.mo.mc.structures.ConditionalConversionList conditionalConversions) {
        this.convertedType = convertedType;
        this.convertedUnit = convertedUnit;
        this.conditionalConversions = conditionalConversions;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param convertedType Holds the attribute short form part of the converted type of the parameter, e.g., for a MAL::String parameter it shall hold 15.
     * @param conditionalConversions The conversions to be applied. Only the first TRUE conversion should be applied.
     */
    public ParameterConversion(Byte convertedType,
            org.ccsds.moims.mo.mc.structures.ConditionalConversionList conditionalConversions) {
        this.convertedType = convertedType;
        this.convertedUnit = null;
        this.conditionalConversions = conditionalConversions;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.parameter.structures.ParameterConversion();
    }

    /**
     * Returns the field convertedType.
     * 
     * @return The field convertedType
     */
    public Byte getConvertedType() {
        return convertedType;
    }

    /**
     * Returns the field convertedUnit.
     * 
     * @return The field convertedUnit
     */
    public String getConvertedUnit() {
        return convertedUnit;
    }

    /**
     * Returns the field conditionalConversions.
     * 
     * @return The field conditionalConversions
     */
    public org.ccsds.moims.mo.mc.structures.ConditionalConversionList getConditionalConversions() {
        return conditionalConversions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ParameterConversion) {
            ParameterConversion other = (ParameterConversion) obj;
            if (convertedType == null) {
                if (other.convertedType != null) {
                    return false;
                }
            } else {
                if (! convertedType.equals(other.convertedType)) {
                    return false;
                }
            }
            if (convertedUnit == null) {
                if (other.convertedUnit != null) {
                    return false;
                }
            } else {
                if (! convertedUnit.equals(other.convertedUnit)) {
                    return false;
                }
            }
            if (conditionalConversions == null) {
                if (other.conditionalConversions != null) {
                    return false;
                }
            } else {
                if (! conditionalConversions.equals(other.conditionalConversions)) {
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
        hash = 83 * hash + (convertedType != null ? convertedType.hashCode() : 0);
        hash = 83 * hash + (convertedUnit != null ? convertedUnit.hashCode() : 0);
        hash = 83 * hash + (conditionalConversions != null ? conditionalConversions.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ParameterConversion: ");
        buf.append("convertedType=").append(convertedType);
        buf.append(", convertedUnit=").append(convertedUnit);
        buf.append(", conditionalConversions=").append(conditionalConversions);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (convertedType == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'convertedType' cannot be null!");
        }
        if (conditionalConversions == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'conditionalConversions' cannot be null!");
        }
        encoder.encodeOctet(convertedType);
        encoder.encodeNullableString(convertedUnit);
        encoder.encodeElement(conditionalConversions);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        convertedType = decoder.decodeOctet();
        convertedUnit = decoder.decodeNullableString();
        conditionalConversions = (org.ccsds.moims.mo.mc.structures.ConditionalConversionList) decoder.decodeElement(new org.ccsds.moims.mo.mc.structures.ConditionalConversionList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
