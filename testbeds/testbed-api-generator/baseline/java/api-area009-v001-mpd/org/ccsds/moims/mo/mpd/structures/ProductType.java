package org.ccsds.moims.mo.mpd.structures;

/**
 * A ProductType contains the product type definition of a mission data product.
 * The ProductType defines the metadata attributes associated with the product
 * and implies (but does not specify) the structure of the product body. The
 * ProductType is part of the Product Metadata.
 */
public final class ProductType implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 2533274807173122L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 2533274807173122L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The name of the Product Type.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier name;

    /**
     * The description of the Product Type.
     */
    private String description;

    /**
     * The list of metadata Attribute Definitions.
     */
    private org.ccsds.moims.mo.mpd.structures.AttributeDefList attributeDefs;

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
    public ProductType(org.ccsds.moims.mo.mal.structures.Identifier name,
            String description,
            org.ccsds.moims.mo.mpd.structures.AttributeDefList attributeDefs) {
        this.name = name;
        this.description = description;
        this.attributeDefs = attributeDefs;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param name The name of the Product Type.
     */
    public ProductType(org.ccsds.moims.mo.mal.structures.Identifier name) {
        this.name = name;
        this.description = null;
        this.attributeDefs = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mpd.structures.ProductType();
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
    public org.ccsds.moims.mo.mpd.structures.AttributeDefList getAttributeDefs() {
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
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (name == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'name' cannot be null!");
        }
        encoder.encodeIdentifier(name);
        encoder.encodeNullableString(description);
        encoder.encodeNullableElement(attributeDefs);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        name = decoder.decodeIdentifier();
        description = decoder.decodeNullableString();
        attributeDefs = (org.ccsds.moims.mo.mpd.structures.AttributeDefList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mpd.structures.AttributeDefList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
