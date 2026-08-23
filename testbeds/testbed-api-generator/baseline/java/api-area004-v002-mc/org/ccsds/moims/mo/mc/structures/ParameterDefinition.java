package org.ccsds.moims.mo.mc.structures;

/**
 * The ParameterDefinition structure holds a parameter definition.
 */
public final class ParameterDefinition extends org.ccsds.moims.mo.mal.structures.MOObject {

    private static final long serialVersionUID = 1125899940397077L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397077L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The description field.
     */
    private String description;

    /**
     * The rawType field.
     */
    private org.ccsds.moims.mo.mal.structures.AttributeType rawType;

    /**
     * The rawUnit field.
     */
    private String rawUnit;

    /**
     * The convertedType field.
     */
    private org.ccsds.moims.mo.mal.structures.AttributeType convertedType;

    /**
     * The convertedUnit field.
     */
    private String convertedUnit;

    /**
     * Default constructor for ParameterDefinition.
     * 
     */
    public ParameterDefinition() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param description The description field.
     * @param rawType The rawType field.
     * @param rawUnit The rawUnit field.
     * @param convertedType The convertedType field.
     * @param convertedUnit The convertedUnit field.
     */
    public ParameterDefinition(org.ccsds.moims.mo.mal.structures.ObjectIdentity objectIdentity,
            String description,
            org.ccsds.moims.mo.mal.structures.AttributeType rawType,
            String rawUnit,
            org.ccsds.moims.mo.mal.structures.AttributeType convertedType,
            String convertedUnit) {
        super(objectIdentity);
        this.description = description;
        this.rawType = rawType;
        this.rawUnit = rawUnit;
        this.convertedType = convertedType;
        this.convertedUnit = convertedUnit;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param description The description field.
     * @param rawType The rawType field.
     */
    public ParameterDefinition(org.ccsds.moims.mo.mal.structures.ObjectIdentity objectIdentity,
            String description,
            org.ccsds.moims.mo.mal.structures.AttributeType rawType) {
        super(objectIdentity);
        this.description = description;
        this.rawType = rawType;
        this.rawUnit = null;
        this.convertedType = null;
        this.convertedUnit = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.structures.ParameterDefinition();
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
    public org.ccsds.moims.mo.mal.structures.AttributeType getRawType() {
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
     * Returns the field convertedType.
     * 
     * @return The field convertedType
     */
    public org.ccsds.moims.mo.mal.structures.AttributeType getConvertedType() {
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
        if (obj instanceof ParameterDefinition) {
            if (! super.equals(obj)) {
                return false;
            }
            ParameterDefinition other = (ParameterDefinition) obj;
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
        int hash = super.hashCode();
        hash = 83 * hash + (description != null ? description.hashCode() : 0);
        hash = 83 * hash + (rawType != null ? rawType.hashCode() : 0);
        hash = 83 * hash + (rawUnit != null ? rawUnit.hashCode() : 0);
        hash = 83 * hash + (convertedType != null ? convertedType.hashCode() : 0);
        hash = 83 * hash + (convertedUnit != null ? convertedUnit.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ParameterDefinition: ");
        buf.append(super.toString());
        buf.append(", description=").append(description);
        buf.append(", rawType=").append(rawType);
        buf.append(", rawUnit=").append(rawUnit);
        buf.append(", convertedType=").append(convertedType);
        buf.append(", convertedUnit=").append(convertedUnit);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (description == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'description' cannot be null!");
        }
        if (rawType == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'rawType' cannot be null!");
        }
        encoder.encodeString(description);
        encoder.encodeElement(rawType);
        encoder.encodeNullableString(rawUnit);
        encoder.encodeNullableElement(convertedType);
        encoder.encodeNullableString(convertedUnit);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        description = decoder.decodeString();
        rawType = (org.ccsds.moims.mo.mal.structures.AttributeType) decoder.decodeElement(org.ccsds.moims.mo.mal.structures.AttributeType.BLOB);
        rawUnit = decoder.decodeNullableString();
        convertedType = (org.ccsds.moims.mo.mal.structures.AttributeType) decoder.decodeNullableElement(org.ccsds.moims.mo.mal.structures.AttributeType.BLOB);
        convertedUnit = decoder.decodeNullableString();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
