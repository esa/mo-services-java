package org.ccsds.moims.mo.mc.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.AttributeType;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.MOObject;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;

/**
 * The ParameterDefinition structure holds a parameter definition.
 */
public final class ParameterDefinition extends MOObject {

    private static final long serialVersionUID = 1125899940397077L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397077L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The description field.
     */
    private String description;

    /**
     * The rawType field.
     */
    private AttributeType rawType;

    /**
     * The rawUnit field.
     */
    private String rawUnit;

    /**
     * The convertedType field.
     */
    private AttributeType convertedType;

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
    public ParameterDefinition(ObjectIdentity objectIdentity,
            String description,
            AttributeType rawType,
            String rawUnit,
            AttributeType convertedType,
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
    public ParameterDefinition(ObjectIdentity objectIdentity,
            String description,
            AttributeType rawType) {
        super(objectIdentity);
        this.description = description;
        this.rawType = rawType;
        this.rawUnit = null;
        this.convertedType = null;
        this.convertedUnit = null;
    }

    @Override
    public Element createElement() {
        return new ParameterDefinition();
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
    public AttributeType getRawType() {
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
    public AttributeType getConvertedType() {
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
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (description == null) {
            throw new MALException("The field 'description' cannot be null!");
        }
        if (rawType == null) {
            throw new MALException("The field 'rawType' cannot be null!");
        }
        encoder.encodeString(description);
        encoder.encodeElement(rawType);
        encoder.encodeNullableString(rawUnit);
        encoder.encodeNullableElement(convertedType);
        encoder.encodeNullableString(convertedUnit);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        description = decoder.decodeString();
        rawType = (AttributeType) decoder.decodeElement(AttributeType.BLOB);
        rawUnit = decoder.decodeNullableString();
        convertedType = (AttributeType) decoder.decodeNullableElement(AttributeType.BLOB);
        convertedUnit = decoder.decodeNullableString();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
