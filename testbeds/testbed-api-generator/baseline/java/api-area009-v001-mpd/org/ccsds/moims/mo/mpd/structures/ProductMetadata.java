package org.ccsds.moims.mo.mpd.structures;

/**
 * The ProductMetadata comprises the metadata of the product (without the
 * product body) and is used when returning a list of available products for
 * retrieval. A ProductMetadata is associated to a specific Product.
 */
public final class ProductMetadata implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 2533274807173124L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 2533274807173124L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The product type definition.
     */
    private org.ccsds.moims.mo.mpd.structures.ProductType productType;

    /**
     * The reference to the Product.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mpd.structures.Product> productRef;

    /**
     * The time at which the product was generated.
     */
    private org.ccsds.moims.mo.mal.structures.Time creationDate;

    /**
     * The source that triggered the generation of the product.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier source;

    /**
     * An external URI for the products to be retrieved. For example, this can
     * be used for pulling a mission data product file via a HTTP URL link.
     */
    private org.ccsds.moims.mo.mal.structures.URI externalURI;

    /**
     * Period of time to which the source data used to generate the product relates.
     */
    private org.ccsds.moims.mo.mpd.structures.TimeWindow contentDate;

    /**
     * Named values for metadata attributes whose name and type correspond to
     * those defined in the referenced ProductType.
     */
    private org.ccsds.moims.mo.mal.structures.NamedValueList attributes;

    /**
     * The textual description of this specific occurrence of the product.
     */
    private String description;

    /**
     * Additional optional metadata for files.
     */
    private org.ccsds.moims.mo.mpd.structures.FileMetadata fileMetadata;

    /**
     * An optional checksum of the product body. If this functionality is enabled,
     * then the checksum algorithm for the calculation shall be agreed as an out-of-band
     * agreement.
     */
    private org.ccsds.moims.mo.mal.structures.Blob checksum;

    /**
     * Default constructor for ProductMetadata.
     * 
     */
    public ProductMetadata() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param productType The product type definition.
     * @param productRef The reference to the Product.
     * @param creationDate The time at which the product was generated.
     * @param source The source that triggered the generation of the product.
     * @param externalURI An external URI for the products to be retrieved. For example, this can be used for pulling a mission data product file via a HTTP URL link.
     * @param contentDate Period of time to which the source data used to generate the product relates.
     * @param attributes Named values for metadata attributes whose name and type correspond to those defined in the referenced ProductType.
     * @param description The textual description of this specific occurrence of the product.
     * @param fileMetadata Additional optional metadata for files.
     * @param checksum An optional checksum of the product body. If this functionality is enabled, then the checksum algorithm for the calculation shall be agreed as an out-of-band agreement.
     */
    public ProductMetadata(org.ccsds.moims.mo.mpd.structures.ProductType productType,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mpd.structures.Product> productRef,
            org.ccsds.moims.mo.mal.structures.Time creationDate,
            org.ccsds.moims.mo.mal.structures.Identifier source,
            org.ccsds.moims.mo.mal.structures.URI externalURI,
            org.ccsds.moims.mo.mpd.structures.TimeWindow contentDate,
            org.ccsds.moims.mo.mal.structures.NamedValueList attributes,
            String description,
            org.ccsds.moims.mo.mpd.structures.FileMetadata fileMetadata,
            org.ccsds.moims.mo.mal.structures.Blob checksum) {
        this.productType = productType;
        this.productRef = productRef;
        this.creationDate = creationDate;
        this.source = source;
        this.externalURI = externalURI;
        this.contentDate = contentDate;
        this.attributes = attributes;
        this.description = description;
        this.fileMetadata = fileMetadata;
        this.checksum = checksum;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param productType The product type definition.
     * @param productRef The reference to the Product.
     * @param creationDate The time at which the product was generated.
     * @param contentDate Period of time to which the source data used to generate the product relates.
     */
    public ProductMetadata(org.ccsds.moims.mo.mpd.structures.ProductType productType,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mpd.structures.Product> productRef,
            org.ccsds.moims.mo.mal.structures.Time creationDate,
            org.ccsds.moims.mo.mpd.structures.TimeWindow contentDate) {
        this.productType = productType;
        this.productRef = productRef;
        this.creationDate = creationDate;
        this.source = null;
        this.externalURI = null;
        this.contentDate = contentDate;
        this.attributes = null;
        this.description = null;
        this.fileMetadata = null;
        this.checksum = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mpd.structures.ProductMetadata();
    }

    /**
     * Returns the field productType.
     * 
     * @return The field productType
     */
    public org.ccsds.moims.mo.mpd.structures.ProductType getProductType() {
        return productType;
    }

    /**
     * Returns the field productRef.
     * 
     * @return The field productRef
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mpd.structures.Product> getProductRef() {
        return productRef;
    }

    /**
     * Returns the field creationDate.
     * 
     * @return The field creationDate
     */
    public org.ccsds.moims.mo.mal.structures.Time getCreationDate() {
        return creationDate;
    }

    /**
     * Returns the field source.
     * 
     * @return The field source
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getSource() {
        return source;
    }

    /**
     * Returns the field externalURI.
     * 
     * @return The field externalURI
     */
    public org.ccsds.moims.mo.mal.structures.URI getExternalURI() {
        return externalURI;
    }

    /**
     * Returns the field contentDate.
     * 
     * @return The field contentDate
     */
    public org.ccsds.moims.mo.mpd.structures.TimeWindow getContentDate() {
        return contentDate;
    }

    /**
     * Returns the field attributes.
     * 
     * @return The field attributes
     */
    public org.ccsds.moims.mo.mal.structures.NamedValueList getAttributes() {
        return attributes;
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
     * Returns the field fileMetadata.
     * 
     * @return The field fileMetadata
     */
    public org.ccsds.moims.mo.mpd.structures.FileMetadata getFileMetadata() {
        return fileMetadata;
    }

    /**
     * Returns the field checksum.
     * 
     * @return The field checksum
     */
    public org.ccsds.moims.mo.mal.structures.Blob getChecksum() {
        return checksum;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ProductMetadata) {
            ProductMetadata other = (ProductMetadata) obj;
            if (productType == null) {
                if (other.productType != null) {
                    return false;
                }
            } else {
                if (! productType.equals(other.productType)) {
                    return false;
                }
            }
            if (productRef == null) {
                if (other.productRef != null) {
                    return false;
                }
            } else {
                if (! productRef.equals(other.productRef)) {
                    return false;
                }
            }
            if (creationDate == null) {
                if (other.creationDate != null) {
                    return false;
                }
            } else {
                if (! creationDate.equals(other.creationDate)) {
                    return false;
                }
            }
            if (source == null) {
                if (other.source != null) {
                    return false;
                }
            } else {
                if (! source.equals(other.source)) {
                    return false;
                }
            }
            if (externalURI == null) {
                if (other.externalURI != null) {
                    return false;
                }
            } else {
                if (! externalURI.equals(other.externalURI)) {
                    return false;
                }
            }
            if (contentDate == null) {
                if (other.contentDate != null) {
                    return false;
                }
            } else {
                if (! contentDate.equals(other.contentDate)) {
                    return false;
                }
            }
            if (attributes == null) {
                if (other.attributes != null) {
                    return false;
                }
            } else {
                if (! attributes.equals(other.attributes)) {
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
            if (fileMetadata == null) {
                if (other.fileMetadata != null) {
                    return false;
                }
            } else {
                if (! fileMetadata.equals(other.fileMetadata)) {
                    return false;
                }
            }
            if (checksum == null) {
                if (other.checksum != null) {
                    return false;
                }
            } else {
                if (! checksum.equals(other.checksum)) {
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
        hash = 83 * hash + (productType != null ? productType.hashCode() : 0);
        hash = 83 * hash + (productRef != null ? productRef.hashCode() : 0);
        hash = 83 * hash + (creationDate != null ? creationDate.hashCode() : 0);
        hash = 83 * hash + (source != null ? source.hashCode() : 0);
        hash = 83 * hash + (externalURI != null ? externalURI.hashCode() : 0);
        hash = 83 * hash + (contentDate != null ? contentDate.hashCode() : 0);
        hash = 83 * hash + (attributes != null ? attributes.hashCode() : 0);
        hash = 83 * hash + (description != null ? description.hashCode() : 0);
        hash = 83 * hash + (fileMetadata != null ? fileMetadata.hashCode() : 0);
        hash = 83 * hash + (checksum != null ? checksum.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ProductMetadata: ");
        buf.append("productType=").append(productType);
        buf.append(", productRef=").append(productRef);
        buf.append(", creationDate=").append(creationDate);
        buf.append(", source=").append(source);
        buf.append(", externalURI=").append(externalURI);
        buf.append(", contentDate=").append(contentDate);
        buf.append(", attributes=").append(attributes);
        buf.append(", description=").append(description);
        buf.append(", fileMetadata=").append(fileMetadata);
        buf.append(", checksum=").append(checksum);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (productType == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'productType' cannot be null!");
        }
        if (productRef == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'productRef' cannot be null!");
        }
        if (creationDate == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'creationDate' cannot be null!");
        }
        if (contentDate == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'contentDate' cannot be null!");
        }
        encoder.encodeElement(productType);
        encoder.encodeElement(productRef);
        encoder.encodeTime(creationDate);
        encoder.encodeNullableIdentifier(source);
        encoder.encodeNullableURI(externalURI);
        encoder.encodeElement(contentDate);
        encoder.encodeNullableElement(attributes);
        encoder.encodeNullableString(description);
        encoder.encodeNullableElement(fileMetadata);
        encoder.encodeNullableBlob(checksum);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        productType = (org.ccsds.moims.mo.mpd.structures.ProductType) decoder.decodeElement(new org.ccsds.moims.mo.mpd.structures.ProductType());
        productRef = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mpd.structures.Product>) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mpd.structures.Product>());
        creationDate = decoder.decodeTime();
        source = decoder.decodeNullableIdentifier();
        externalURI = decoder.decodeNullableURI();
        contentDate = (org.ccsds.moims.mo.mpd.structures.TimeWindow) decoder.decodeElement(new org.ccsds.moims.mo.mpd.structures.TimeWindow());
        attributes = (org.ccsds.moims.mo.mal.structures.NamedValueList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.NamedValueList());
        description = decoder.decodeNullableString();
        fileMetadata = (org.ccsds.moims.mo.mpd.structures.FileMetadata) decoder.decodeNullableElement(new org.ccsds.moims.mo.mpd.structures.FileMetadata());
        checksum = decoder.decodeNullableBlob();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
