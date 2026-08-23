package org.ccsds.moims.mo.mpd.structures;

/**
 * A Product is an MO object that corresponds to a specific occurrence of
 * a generated mission data product.
 */
public final class Product extends org.ccsds.moims.mo.mal.structures.MOObject {

    private static final long serialVersionUID = 2533274807173121L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 2533274807173121L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The product metadata of the mission data product. The productRef field
     * inside the productMetadata may be null when used in this context.
     */
    private org.ccsds.moims.mo.mpd.structures.ProductMetadata productMetadata;

    /**
     * The product body of the mission data product.
     */
    private org.ccsds.moims.mo.mal.structures.Blob productBody;

    /**
     * Default constructor for Product.
     * 
     */
    public Product() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param productMetadata The product metadata of the mission data product. The productRef field inside the productMetadata may be null when used in this context.
     * @param productBody The product body of the mission data product.
     */
    public Product(org.ccsds.moims.mo.mal.structures.ObjectIdentity objectIdentity,
            org.ccsds.moims.mo.mpd.structures.ProductMetadata productMetadata,
            org.ccsds.moims.mo.mal.structures.Blob productBody) {
        super(objectIdentity);
        this.productMetadata = productMetadata;
        this.productBody = productBody;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mpd.structures.Product();
    }

    /**
     * Returns the field productMetadata.
     * 
     * @return The field productMetadata
     */
    public org.ccsds.moims.mo.mpd.structures.ProductMetadata getProductMetadata() {
        return productMetadata;
    }

    /**
     * Returns the field productBody.
     * 
     * @return The field productBody
     */
    public org.ccsds.moims.mo.mal.structures.Blob getProductBody() {
        return productBody;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product) {
            if (! super.equals(obj)) {
                return false;
            }
            Product other = (Product) obj;
            if (productMetadata == null) {
                if (other.productMetadata != null) {
                    return false;
                }
            } else {
                if (! productMetadata.equals(other.productMetadata)) {
                    return false;
                }
            }
            if (productBody == null) {
                if (other.productBody != null) {
                    return false;
                }
            } else {
                if (! productBody.equals(other.productBody)) {
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
        hash = 83 * hash + (productMetadata != null ? productMetadata.hashCode() : 0);
        hash = 83 * hash + (productBody != null ? productBody.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(Product: ");
        buf.append(super.toString());
        buf.append(", productMetadata=").append(productMetadata);
        buf.append(", productBody=").append(productBody);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (productMetadata == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'productMetadata' cannot be null!");
        }
        if (productBody == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'productBody' cannot be null!");
        }
        encoder.encodeElement(productMetadata);
        encoder.encodeBlob(productBody);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        productMetadata = (org.ccsds.moims.mo.mpd.structures.ProductMetadata) decoder.decodeElement(new org.ccsds.moims.mo.mpd.structures.ProductMetadata());
        productBody = decoder.decodeBlob();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
