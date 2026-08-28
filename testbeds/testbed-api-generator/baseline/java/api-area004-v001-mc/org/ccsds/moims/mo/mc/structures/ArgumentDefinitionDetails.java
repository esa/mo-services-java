package org.ccsds.moims.mo.mc.structures;

/**
 * The ArgumentDefinitionDetails structure holds the details of an argument
 * definition with a set of associated attributes, such as conversion used.
 * The conditionalConversions define the conditions where a referenced conversion
 * is applied. Only the first TRUE conversion should be applied.
 */
public final class ArgumentDefinitionDetails implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125899923619841L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899923619841L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Holds the argument definition identifier.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier argId;

    /**
     * Optional argument description.
     */
    private String description;

    /**
     * Holds the attribute short form part of the raw type of the argument, e.g.,
     * for a MAL::String argument it shall hold 15.
     */
    private Byte rawType;

    /**
     * The unit for the raw value.
     */
    private String rawUnit;

    /**
     * The conditional conversions to apply to the argument. Only the first TRUE
     * conversion should be applied.
     */
    private org.ccsds.moims.mo.mc.structures.ConditionalConversionList conditionalConversions;

    /**
     * Holds the attribute short form part of the converted type of the argument,
     * e.g., for a MAL::String argument it shall hold 15. Must not be NULL if
     * a conversion condition is supplied.
     */
    private Byte convertedType;

    /**
     * The converted argument units.
     */
    private String convertedUnit;

    /**
     * Default constructor for ArgumentDefinitionDetails.
     * 
     */
    public ArgumentDefinitionDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param argId Holds the argument definition identifier.
     * @param description Optional argument description.
     * @param rawType Holds the attribute short form part of the raw type of the argument, e.g., for a MAL::String argument it shall hold 15.
     * @param rawUnit The unit for the raw value.
     * @param conditionalConversions The conditional conversions to apply to the argument. Only the first TRUE conversion should be applied.
     * @param convertedType Holds the attribute short form part of the converted type of the argument, e.g., for a MAL::String argument it shall hold 15. Must not be NULL if a conversion condition is supplied.
     * @param convertedUnit The converted argument units.
     */
    public ArgumentDefinitionDetails(org.ccsds.moims.mo.mal.structures.Identifier argId,
            String description,
            Byte rawType,
            String rawUnit,
            org.ccsds.moims.mo.mc.structures.ConditionalConversionList conditionalConversions,
            Byte convertedType,
            String convertedUnit) {
        this.argId = argId;
        this.description = description;
        this.rawType = rawType;
        this.rawUnit = rawUnit;
        this.conditionalConversions = conditionalConversions;
        this.convertedType = convertedType;
        this.convertedUnit = convertedUnit;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param argId Holds the argument definition identifier.
     * @param rawType Holds the attribute short form part of the raw type of the argument, e.g., for a MAL::String argument it shall hold 15.
     */
    public ArgumentDefinitionDetails(org.ccsds.moims.mo.mal.structures.Identifier argId,
            Byte rawType) {
        this.argId = argId;
        this.description = null;
        this.rawType = rawType;
        this.rawUnit = null;
        this.conditionalConversions = null;
        this.convertedType = null;
        this.convertedUnit = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.structures.ArgumentDefinitionDetails();
    }

    /**
     * Returns the field argId.
     * 
     * @return The field argId
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getArgId() {
        return argId;
    }

    /**
     * Returns the field description.
     * 
     * @return The field description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the field rawType.
     * 
     * @return The field rawType
     */
    public Byte getRawType() {
        return rawType;
    }

    /**
     * Returns the field rawUnit.
     * 
     * @return The field rawUnit
     */
    public String getRawUnit() {
        return rawUnit;
    }

    /**
     * Returns the field conditionalConversions.
     * 
     * @return The field conditionalConversions
     */
    public org.ccsds.moims.mo.mc.structures.ConditionalConversionList getConditionalConversions() {
        return conditionalConversions;
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ArgumentDefinitionDetails) {
            ArgumentDefinitionDetails other = (ArgumentDefinitionDetails) obj;
            if (argId == null) {
                if (other.argId != null) {
                    return false;
                }
            } else {
                if (! argId.equals(other.argId)) {
                    return false;
                }
            }
            if (description == null) {
                if (other.description != null) {
                    return false;
                }
            } else {
                if (! description.equals(other.description)) {
                    return false;
                }
            }
            if (rawType == null) {
                if (other.rawType != null) {
                    return false;
                }
            } else {
                if (! rawType.equals(other.rawType)) {
                    return false;
                }
            }
            if (rawUnit == null) {
                if (other.rawUnit != null) {
                    return false;
                }
            } else {
                if (! rawUnit.equals(other.rawUnit)) {
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
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (argId != null ? argId.hashCode() : 0);
        hash = 83 * hash + (description != null ? description.hashCode() : 0);
        hash = 83 * hash + (rawType != null ? rawType.hashCode() : 0);
        hash = 83 * hash + (rawUnit != null ? rawUnit.hashCode() : 0);
        hash = 83 * hash + (conditionalConversions != null ? conditionalConversions.hashCode() : 0);
        hash = 83 * hash + (convertedType != null ? convertedType.hashCode() : 0);
        hash = 83 * hash + (convertedUnit != null ? convertedUnit.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ArgumentDefinitionDetails: ");
        buf.append("argId=").append(argId);
        buf.append(", description=").append(description);
        buf.append(", rawType=").append(rawType);
        buf.append(", rawUnit=").append(rawUnit);
        buf.append(", conditionalConversions=").append(conditionalConversions);
        buf.append(", convertedType=").append(convertedType);
        buf.append(", convertedUnit=").append(convertedUnit);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (argId == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'argId' cannot be null!");
        }
        if (rawType == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'rawType' cannot be null!");
        }
        encoder.encodeIdentifier(argId);
        encoder.encodeNullableString(description);
        encoder.encodeOctet(rawType);
        encoder.encodeNullableString(rawUnit);
        encoder.encodeNullableElement(conditionalConversions);
        encoder.encodeNullableOctet(convertedType);
        encoder.encodeNullableString(convertedUnit);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        argId = decoder.decodeIdentifier();
        description = decoder.decodeNullableString();
        rawType = decoder.decodeOctet();
        rawUnit = decoder.decodeNullableString();
        conditionalConversions = (org.ccsds.moims.mo.mc.structures.ConditionalConversionList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mc.structures.ConditionalConversionList());
        convertedType = decoder.decodeNullableOctet();
        convertedUnit = decoder.decodeNullableString();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
