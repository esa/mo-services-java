package org.ccsds.moims.mo.mpd.structures;

/**
 * An AttributeDef specifies a metadata attribute in terms of its name, attribute
 * type, optional units, and a free text description. Note that as AttributeDef
 * is only used in the context of ProductType.
 */
public final class AttributeDef implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 2533274807173128L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 2533274807173128L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The name to the metadata attribute.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier name;

    /**
     * Specifies the MAL attribute type of the metadata attribute.
     */
    private org.ccsds.moims.mo.mal.structures.AttributeType attributeType;

    /**
     * The units associated with the metadata attribute (optional).
     */
    private String units;

    /**
     * The description of the metadata attribute (optional).
     */
    private String description;

    /**
     * Default constructor for AttributeDef.
     * 
     */
    public AttributeDef() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param name The name to the metadata attribute.
     * @param attributeType Specifies the MAL attribute type of the metadata attribute.
     * @param units The units associated with the metadata attribute (optional).
     * @param description The description of the metadata attribute (optional).
     */
    public AttributeDef(org.ccsds.moims.mo.mal.structures.Identifier name,
            org.ccsds.moims.mo.mal.structures.AttributeType attributeType,
            String units,
            String description) {
        this.name = name;
        this.attributeType = attributeType;
        this.units = units;
        this.description = description;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param name The name to the metadata attribute.
     * @param attributeType Specifies the MAL attribute type of the metadata attribute.
     */
    public AttributeDef(org.ccsds.moims.mo.mal.structures.Identifier name,
            org.ccsds.moims.mo.mal.structures.AttributeType attributeType) {
        this.name = name;
        this.attributeType = attributeType;
        this.units = null;
        this.description = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mpd.structures.AttributeDef();
    }

    /**
     * Returns the field name.
     * 
     * @return The field name
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getName() {
        return name;
    }

    /**
     * Returns the field attributeType.
     * 
     * @return The field attributeType
     */
    public org.ccsds.moims.mo.mal.structures.AttributeType getAttributeType() {
        return attributeType;
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
     * Returns the field description.
     * 
     * @return The field description
     */
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AttributeDef) {
            AttributeDef other = (AttributeDef) obj;
            if (name == null) {
                if (other.name != null) {
                    return false;
                }
            } else {
                if (! name.equals(other.name)) {
                    return false;
                }
            }
            if (attributeType == null) {
                if (other.attributeType != null) {
                    return false;
                }
            } else {
                if (! attributeType.equals(other.attributeType)) {
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
            if (description == null) {
                if (other.description != null) {
                    return false;
                }
            } else {
                if (! description.equals(other.description)) {
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
        hash = 83 * hash + (name != null ? name.hashCode() : 0);
        hash = 83 * hash + (attributeType != null ? attributeType.hashCode() : 0);
        hash = 83 * hash + (units != null ? units.hashCode() : 0);
        hash = 83 * hash + (description != null ? description.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(AttributeDef: ");
        buf.append("name=").append(name);
        buf.append(", attributeType=").append(attributeType);
        buf.append(", units=").append(units);
        buf.append(", description=").append(description);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (name == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'name' cannot be null!");
        }
        if (attributeType == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'attributeType' cannot be null!");
        }
        encoder.encodeIdentifier(name);
        encoder.encodeElement(attributeType);
        encoder.encodeNullableString(units);
        encoder.encodeNullableString(description);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        name = decoder.decodeIdentifier();
        attributeType = (org.ccsds.moims.mo.mal.structures.AttributeType) decoder.decodeElement(org.ccsds.moims.mo.mal.structures.AttributeType.BLOB);
        units = decoder.decodeNullableString();
        description = decoder.decodeNullableString();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
