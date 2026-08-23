package org.ccsds.moims.mo.mps.structures;

/**
 * E1: The definition of an argument is an ArgDef, a set of which may be contained
 * within the definition MO object of a planning event, planning activity,
 * or planning request.  This defines the name and data type of the argument.
 * Depending on the data type, the ArgDef may require additional type specific
 * fields to support data validation.  Subtypes are identified for Numeric,
 * String, and Status arguments.
 */
public final class ArgDef implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1407374900330519L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330519L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Name of the argument.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier argName;

    /**
     * Extended description of the argument.
     */
    private String description;

    /**
     * Enumeration specifying the data type of the argument.
     */
    private org.ccsds.moims.mo.mps.structures.ArgTypeEnum argType;

    /**
     * The units of a single quantity, in which the argument value is expressed
     * in.
     */
    private String argUnits;

    /**
     * If True, indicates that the argument is an array of values of type ArgTypeEnum.
     */
    private Boolean isArray;

    /**
     * Optional.  Specifies the allowed range of values for the Argument, with
     * concrete subtypes specific to the data type of the Argument.
     */
    private org.ccsds.moims.mo.mps.structures.ValidationDetails validationData;

    /**
     * Default constructor for ArgDef.
     * 
     */
    public ArgDef() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param argName Name of the argument.
     * @param description Extended description of the argument.
     * @param argType Enumeration specifying the data type of the argument.
     * @param argUnits The units of a single quantity, in which the argument value is expressed in.
     * @param isArray If True, indicates that the argument is an array of values of type ArgTypeEnum.
     * @param validationData Optional.  Specifies the allowed range of values for the Argument, with concrete subtypes specific to the data type of the Argument.
     */
    public ArgDef(org.ccsds.moims.mo.mal.structures.Identifier argName,
            String description,
            org.ccsds.moims.mo.mps.structures.ArgTypeEnum argType,
            String argUnits,
            Boolean isArray,
            org.ccsds.moims.mo.mps.structures.ValidationDetails validationData) {
        this.argName = argName;
        this.description = description;
        this.argType = argType;
        this.argUnits = argUnits;
        this.isArray = isArray;
        this.validationData = validationData;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param argName Name of the argument.
     * @param description Extended description of the argument.
     * @param argType Enumeration specifying the data type of the argument.
     * @param isArray If True, indicates that the argument is an array of values of type ArgTypeEnum.
     */
    public ArgDef(org.ccsds.moims.mo.mal.structures.Identifier argName,
            String description,
            org.ccsds.moims.mo.mps.structures.ArgTypeEnum argType,
            Boolean isArray) {
        this.argName = argName;
        this.description = description;
        this.argType = argType;
        this.argUnits = null;
        this.isArray = isArray;
        this.validationData = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.ArgDef();
    }

    /**
     * Returns the field argName.
     * 
     * @return The field argName
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getArgName() {
        return argName;
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
     * Returns the field argType.
     * 
     * @return The field argType
     */
    public org.ccsds.moims.mo.mps.structures.ArgTypeEnum getArgType() {
        return argType;
    }

    /**
     * Returns the field argUnits.
     * 
     * @return The field argUnits
     */
    public String getArgUnits() {
        return argUnits;
    }

    /**
     * Returns the field isArray.
     * 
     * @return The field isArray
     */
    public Boolean getIsArray() {
        return isArray;
    }

    /**
     * Returns the field validationData.
     * 
     * @return The field validationData
     */
    public org.ccsds.moims.mo.mps.structures.ValidationDetails getValidationData() {
        return validationData;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ArgDef) {
            ArgDef other = (ArgDef) obj;
            if (argName == null) {
                if (other.argName != null) {
                    return false;
                }
            } else {
                if (! argName.equals(other.argName)) {
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
            if (argType == null) {
                if (other.argType != null) {
                    return false;
                }
            } else {
                if (! argType.equals(other.argType)) {
                    return false;
                }
            }
            if (argUnits == null) {
                if (other.argUnits != null) {
                    return false;
                }
            } else {
                if (! argUnits.equals(other.argUnits)) {
                    return false;
                }
            }
            if (isArray == null) {
                if (other.isArray != null) {
                    return false;
                }
            } else {
                if (! isArray.equals(other.isArray)) {
                    return false;
                }
            }
            if (validationData == null) {
                if (other.validationData != null) {
                    return false;
                }
            } else {
                if (! validationData.equals(other.validationData)) {
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
        hash = 83 * hash + (argName != null ? argName.hashCode() : 0);
        hash = 83 * hash + (description != null ? description.hashCode() : 0);
        hash = 83 * hash + (argType != null ? argType.hashCode() : 0);
        hash = 83 * hash + (argUnits != null ? argUnits.hashCode() : 0);
        hash = 83 * hash + (isArray != null ? isArray.hashCode() : 0);
        hash = 83 * hash + (validationData != null ? validationData.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ArgDef: ");
        buf.append("argName=").append(argName);
        buf.append(", description=").append(description);
        buf.append(", argType=").append(argType);
        buf.append(", argUnits=").append(argUnits);
        buf.append(", isArray=").append(isArray);
        buf.append(", validationData=").append(validationData);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (argName == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'argName' cannot be null!");
        }
        if (description == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'description' cannot be null!");
        }
        if (argType == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'argType' cannot be null!");
        }
        if (isArray == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'isArray' cannot be null!");
        }
        encoder.encodeIdentifier(argName);
        encoder.encodeString(description);
        encoder.encodeElement(argType);
        encoder.encodeNullableString(argUnits);
        encoder.encodeBoolean(isArray);
        encoder.encodeNullableAbstractElement(validationData);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        argName = decoder.decodeIdentifier();
        description = decoder.decodeString();
        argType = (org.ccsds.moims.mo.mps.structures.ArgTypeEnum) decoder.decodeElement(org.ccsds.moims.mo.mps.structures.ArgTypeEnum.BLOB);
        argUnits = decoder.decodeNullableString();
        isArray = decoder.decodeBoolean();
        validationData = (org.ccsds.moims.mo.mps.structures.ValidationDetails) decoder.decodeNullableAbstractElement();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
