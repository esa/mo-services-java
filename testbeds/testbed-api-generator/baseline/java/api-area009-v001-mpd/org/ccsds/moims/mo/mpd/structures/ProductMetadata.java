package org.ccsds.moims.mo.mpd.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 * The ProductMetadata comprises the metadata of the product (without the
 * product body) and is used when returning a list of available products for
 * retrieval. A ProductMetadata is associated to a specific Product.
 */
public final class ProductMetadata implements Composite {

    private static final long serialVersionUID = 2533274807173124L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 2533274807173124L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The product type definition.
     */
    private ProductType productType;

    /**
     * The reference to the Product.
     */
    private ObjectRef<Product> productRef;

    /**
     * The time at which the product was generated.
     */
    private Time creationDate;

    /**
     * The source that triggered the generation of the product.
     */
    private Identifier source;

    /**
     * An external URI for the products to be retrieved. For example, this can
     * be used for pulling a mission data product file via a HTTP URL link.
     */
    private URI externalURI;

    /**
     * Period of time to which the source data used to generate the product relates.
     */
    private TimeWindow contentDate;

    /**
     * Named values for metadata attributes whose name and type correspond to
     * those defined in the referenced ProductType.
     */
    private NamedValueList attributes;

    /**
     * The textual description of this specific occurrence of the product.
     */
    private String description;

    /**
     * Additional optional metadata for files.
     */
    private FileMetadata fileMetadata;

    /**
     * An optional checksum of the product body. If this functionality is enabled,
     * then the checksum algorithm for the calculation shall be agreed as an out-of-band
     * agreement.
     */
    private Blob checksum;

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
    public ProductMetadata(ProductType productType,
            ObjectRef<Product> productRef,
            Time creationDate,
            Identifier source,
            URI externalURI,
            TimeWindow contentDate,
            NamedValueList attributes,
            String description,
            FileMetadata fileMetadata,
            Blob checksum) {
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
    public ProductMetadata(ProductType productType,
            ObjectRef<Product> productRef,
            Time creationDate,
            TimeWindow contentDate) {
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
    public Element createElement() {
        return new ProductMetadata();
    }

    /**
     * Returns the field productType.
     * 
     * @return The field productType
     */
    public ProductType getProductType() {
        return productType;
    }

    /**
     * Returns the field productRef.
     * 
     * @return The field productRef
     */
    public ObjectRef<Product> getProductRef() {
        return productRef;
    }

    /**
     * Returns the field creationDate.
     * 
     * @return The field creationDate
     */
    public Time getCreationDate() {
        return creationDate;
    }

    /**
     * Returns the field source.
     * 
     * @return The field source
     */
    public Identifier getSource() {
        return source;
    }

    /**
     * Returns the field externalURI.
     * 
     * @return The field externalURI
     */
    public URI getExternalURI() {
        return externalURI;
    }

    /**
     * Returns the field contentDate.
     * 
     * @return The field contentDate
     */
    public TimeWindow getContentDate() {
        return contentDate;
    }

    /**
     * Returns the field attributes.
     * 
     * @return The field attributes
     */
    public NamedValueList getAttributes() {
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
    public FileMetadata getFileMetadata() {
        return fileMetadata;
    }

    /**
     * Returns the field checksum.
     * 
     * @return The field checksum
     */
    public Blob getChecksum() {
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
    public void encode(MALEncoder encoder) throws MALException {
        if (productType == null) {
            throw new MALException("The field 'productType' cannot be null!");
        }
        if (productRef == null) {
            throw new MALException("The field 'productRef' cannot be null!");
        }
        if (creationDate == null) {
            throw new MALException("The field 'creationDate' cannot be null!");
        }
        if (contentDate == null) {
            throw new MALException("The field 'contentDate' cannot be null!");
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
    public Element decode(MALDecoder decoder) throws MALException {
        productType = (ProductType) decoder.decodeElement(new ProductType());
        productRef = (ObjectRef<Product>) decoder.decodeElement(new ObjectRef<Product>());
        creationDate = decoder.decodeTime();
        source = decoder.decodeNullableIdentifier();
        externalURI = decoder.decodeNullableURI();
        contentDate = (TimeWindow) decoder.decodeElement(new TimeWindow());
        attributes = (NamedValueList) decoder.decodeNullableElement(new NamedValueList());
        description = decoder.decodeNullableString();
        fileMetadata = (FileMetadata) decoder.decodeNullableElement(new FileMetadata());
        checksum = decoder.decodeNullableBlob();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
