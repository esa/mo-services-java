package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.AttributeType;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.MOObject;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;

/**
 * E4: A resource is an MO object that contains both the static fields that
 * define a planning resource and a dynamic field that holds its current value.
 * Its identity is defined by a constant key and evolving version, which
 * is updated each time the definition is revised.  Resource definitions form
 * part of the planning configuration data and in practice the value field
 * may be omitted in this context, although it may also be used to provide
 * an initial or default value. Depending on the resource data type, the resource
 * definition may require additional type-specific fields to support data
 * validation.  Subtypes are defined for Numeric, String, and enumerated Status
 * type resources.  The base Resource MO object type can be used where no
 * data validation is applicable.  The following fields are applicable to
 * the base type and all subtypes.
 */
public final class Resource extends MOObject {

    private static final long serialVersionUID = 1407374900330797L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330797L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Description of the Resource.
     */
    private String description;

    /**
     * Specifies the data type of the Resource, which must be a supported MAL
     * Attribute type.
     */
    private AttributeType dataType;

    /**
     * Optional.  Specifies the units of a single quantity, in which the value
     * of the Resource is expressed in.
     */
    private String units;

    /**
     * Optional.  Specifies the allowed range of values for the Resource, with
     * concrete subtypes specific to the data type of the Resource.
     */
    private ValidationDetails validationData;

    /**
     * Value of the resource.  MAL Attribute type must match the dataType of the
     * Resource definition. The value is only nullable in the context of a Resource
     * definition (planning configuration data).
     */
    private Attribute value;

    /**
     * Default constructor for Resource.
     * 
     */
    public Resource() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param description Description of the Resource.
     * @param dataType Specifies the data type of the Resource, which must be a supported MAL Attribute type.
     * @param units Optional.  Specifies the units of a single quantity, in which the value of the Resource is expressed in.
     * @param validationData Optional.  Specifies the allowed range of values for the Resource, with concrete subtypes specific to the data type of the Resource.
     * @param value Value of the resource.  MAL Attribute type must match the dataType of the Resource definition. The value is only nullable in the context of a Resource definition (planning configuration data).
     */
    public Resource(ObjectIdentity objectIdentity,
            String description,
            AttributeType dataType,
            String units,
            ValidationDetails validationData,
            Attribute value) {
        super(objectIdentity);
        this.description = description;
        this.dataType = dataType;
        this.units = units;
        this.validationData = validationData;
        this.value = value;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param description Description of the Resource.
     * @param dataType Specifies the data type of the Resource, which must be a supported MAL Attribute type.
     */
    public Resource(ObjectIdentity objectIdentity,
            String description,
            AttributeType dataType) {
        super(objectIdentity);
        this.description = description;
        this.dataType = dataType;
        this.units = null;
        this.validationData = null;
        this.value = null;
    }

    @Override
    public Element createElement() {
        return new Resource();
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
     * Returns the field dataType.
     * 
     * @return The field dataType
     */
    public AttributeType getDataType() {
        return dataType;
    }

    /**
     * Returns the field units.
     * 
     * @return The field units
     */
    public String getUnits() {
        return units;
    }

    /**
     * Returns the field validationData.
     * 
     * @return The field validationData
     */
    public ValidationDetails getValidationData() {
        return validationData;
    }

    /**
     * Returns the field value.
     * 
     * @return The field value
     */
    public Attribute getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Resource) {
            if (! super.equals(obj)) {
                return false;
            }
            Resource other = (Resource) obj;
            if (description == null) {
                if (other.description != null) {
                    return false;
                }
            } else {
                if (! description.equals(other.description)) {
                    return false;
                }
            }
            if (dataType == null) {
                if (other.dataType != null) {
                    return false;
                }
            } else {
                if (! dataType.equals(other.dataType)) {
                    return false;
                }
            }
            if (units == null) {
                if (other.units != null) {
                    return false;
                }
            } else {
                if (! units.equals(other.units)) {
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
        hash = 83 * hash + (description != null ? description.hashCode() : 0);
        hash = 83 * hash + (dataType != null ? dataType.hashCode() : 0);
        hash = 83 * hash + (units != null ? units.hashCode() : 0);
        hash = 83 * hash + (validationData != null ? validationData.hashCode() : 0);
        hash = 83 * hash + (value != null ? value.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(Resource: ");
        buf.append(super.toString());
        buf.append(", description=").append(description);
        buf.append(", dataType=").append(dataType);
        buf.append(", units=").append(units);
        buf.append(", validationData=").append(validationData);
        buf.append(", value=").append(value);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (description == null) {
            throw new MALException("The field 'description' cannot be null!");
        }
        if (dataType == null) {
            throw new MALException("The field 'dataType' cannot be null!");
        }
        encoder.encodeString(description);
        encoder.encodeElement(dataType);
        encoder.encodeNullableString(units);
        encoder.encodeNullableAbstractElement(validationData);
        encoder.encodeNullableAttribute(value);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        description = decoder.decodeString();
        dataType = (AttributeType) decoder.decodeElement(AttributeType.BLOB);
        units = decoder.decodeNullableString();
        validationData = (ValidationDetails) decoder.decodeNullableAbstractElement();
        value = (Attribute) decoder.decodeNullableAttribute();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
