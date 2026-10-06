package org.ccsds.moims.mo.mpd.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.AttributeType;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 * An AttributeDef specifies a metadata attribute in terms of its name, attribute
 * type, optional units, and a free text description. Note that as AttributeDef
 * is only used in the context of ProductType.
 */
public final class AttributeDef implements Composite {

    private static final long serialVersionUID = 2533274807173128L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 2533274807173128L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The name to the metadata attribute.
     */
    private Identifier name;

    /**
     * Specifies the MAL attribute type of the metadata attribute.
     */
    private AttributeType attributeType;

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
    public AttributeDef(Identifier name,
            AttributeType attributeType,
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
    public AttributeDef(Identifier name,
            AttributeType attributeType) {
        this.name = name;
        this.attributeType = attributeType;
        this.units = null;
        this.description = null;
    }

    @Override
    public Element createElement() {
        return new AttributeDef();
    }

    /**
     * Returns the field name.
     * 
     * @return The field name
     */
    public Identifier getName() {
        return name;
    }

    /**
     * Returns the field attributeType.
     * 
     * @return The field attributeType
     */
    public AttributeType getAttributeType() {
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
    public void encode(MALEncoder encoder) throws MALException {
        if (name == null) {
            throw new MALException("The field 'name' cannot be null!");
        }
        if (attributeType == null) {
            throw new MALException("The field 'attributeType' cannot be null!");
        }
        encoder.encodeIdentifier(name);
        encoder.encodeElement(attributeType);
        encoder.encodeNullableString(units);
        encoder.encodeNullableString(description);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        name = decoder.decodeIdentifier();
        attributeType = (AttributeType) decoder.decodeElement(AttributeType.BLOB);
        units = decoder.decodeNullableString();
        description = decoder.decodeNullableString();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
