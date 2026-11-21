package org.ccsds.moims.mo.mpd.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 * A ProductType contains the product type definition of a mission data product.
 * The ProductType defines the metadata attributes associated with the product
 * and implies (but does not specify) the structure of the product body. The
 * ProductType is part of the Product Metadata.
 */
public final class ProductType implements Composite {

    private static final long serialVersionUID = 2533274807173122L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 2533274807173122L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The name of the Product Type.
     */
    private Identifier name;

    /**
     * The description of the Product Type.
     */
    private String description;

    /**
     * The list of metadata Attribute Definitions.
     */
    private AttributeDefList attributeDefs;

    /**
     * Default constructor for ProductType.
     * 
     */
    public ProductType() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param name The name of the Product Type.
     * @param description The description of the Product Type.
     * @param attributeDefs The list of metadata Attribute Definitions.
     */
    public ProductType(Identifier name,
            String description,
            AttributeDefList attributeDefs) {
        this.name = name;
        this.description = description;
        this.attributeDefs = attributeDefs;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param name The name of the Product Type.
     */
    public ProductType(Identifier name) {
        this.name = name;
        this.description = null;
        this.attributeDefs = null;
    }

    @Override
    public Element createElement() {
        return new ProductType();
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
     * Returns the field description.
     * 
     * @return The field description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the field attributeDefs.
     * 
     * @return The field attributeDefs
     */
    public AttributeDefList getAttributeDefs() {
        return attributeDefs;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ProductType) {
            ProductType other = (ProductType) obj;
            if (name == null) {
                if (other.name != null) {
                    return false;
                }
            } else {
                if (! name.equals(other.name)) {
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
            if (attributeDefs == null) {
                if (other.attributeDefs != null) {
                    return false;
                }
            } else {
                if (! attributeDefs.equals(other.attributeDefs)) {
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
        hash = 83 * hash + (description != null ? description.hashCode() : 0);
        hash = 83 * hash + (attributeDefs != null ? attributeDefs.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ProductType: ");
        buf.append("name=").append(name);
        buf.append(", description=").append(description);
        buf.append(", attributeDefs=").append(attributeDefs);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (name == null) {
            throw new MALException("The field 'name' cannot be null!");
        }
        encoder.encodeIdentifier(name);
        encoder.encodeNullableString(description);
        encoder.encodeNullableElement(attributeDefs);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        name = decoder.decodeIdentifier();
        description = decoder.decodeNullableString();
        attributeDefs = (AttributeDefList) decoder.decodeNullableElement(new AttributeDefList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
