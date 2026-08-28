package org.ccsds.moims.mo.mpd.structures;

/**
 * The ProductFilter is used in the context of standing orders and service
 * operations requesting a filtered list of available products. It specifies
 * a filter in terms of productType, domain, sources and metadata attribute
 * values. To pass the filter, the product must satisfy all criteria specified:
 * the productType, the domain, one of any listed source objects and all specified
 * metadata attribute filters. If no filter is specified for source or attribute
 * filters, then all products of the specified type pass the filter. A ProductFilter
 * relates to a specific ProductType. Note that the domain filter relates
 * to the domain of the product. The domain of a generic product type (for
 * example an orbit file) may be defined at agency level to avoid the need
 * to define new types for each mission, but the generated product will be
 * associated with a specific mission domain.
 */
public final class ProductFilter implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 2533274807173126L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 2533274807173126L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The name of the product type definition.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier productType;

    /**
     * The domain to filter on. A wildcard may be used in order to select all
     * possibilities within a sub-domain.
     */
    private org.ccsds.moims.mo.mal.structures.IdentifierList domain;

    /**
     * The set of sources associated with the products to filter on. If the product
     * source matches at least one of the listed sources, then it passes the filter.
     * If the sources field is set to NULL, then no filtering on the product source
     * will be performed.
     */
    private org.ccsds.moims.mo.mal.structures.IdentifierList sources;

    /**
     * Set of attribute filters defining the desired values of product metadata
     * attributes. If multiple attribute filters are defined, then the product
     * metadata must match all (ANDed) specified criteria to pass the filter.
     */
    private org.ccsds.moims.mo.mpd.structures.AttributeFilterList attributeFilter;

    /**
     * Default constructor for ProductFilter.
     * 
     */
    public ProductFilter() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param productType The name of the product type definition.
     * @param domain The domain to filter on. A wildcard may be used in order to select all possibilities within a sub-domain.
     * @param sources The set of sources associated with the products to filter on. If the product source matches at least one of the listed sources, then it passes the filter. If the sources field is set to NULL, then no filtering on the product source will be performed.
     * @param attributeFilter Set of attribute filters defining the desired values of product metadata attributes. If multiple attribute filters are defined, then the product metadata must match all (ANDed) specified criteria to pass the filter.
     */
    public ProductFilter(org.ccsds.moims.mo.mal.structures.Identifier productType,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.IdentifierList sources,
            org.ccsds.moims.mo.mpd.structures.AttributeFilterList attributeFilter) {
        this.productType = productType;
        this.domain = domain;
        this.sources = sources;
        this.attributeFilter = attributeFilter;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mpd.structures.ProductFilter();
    }

    /**
     * Returns the field productType.
     * 
     * @return The field productType
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getProductType() {
        return productType;
    }

    /**
     * Returns the field domain.
     * 
     * @return The field domain
     */
    public org.ccsds.moims.mo.mal.structures.IdentifierList getDomain() {
        return domain;
    }

    /**
     * Returns the field sources.
     * 
     * @return The field sources
     */
    public org.ccsds.moims.mo.mal.structures.IdentifierList getSources() {
        return sources;
    }

    /**
     * Returns the field attributeFilter.
     * 
     * @return The field attributeFilter
     */
    public org.ccsds.moims.mo.mpd.structures.AttributeFilterList getAttributeFilter() {
        return attributeFilter;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ProductFilter) {
            ProductFilter other = (ProductFilter) obj;
            if (productType == null) {
                if (other.productType != null) {
                    return false;
                }
            } else {
                if (! productType.equals(other.productType)) {
                    return false;
                }
            }
            if (domain == null) {
                if (other.domain != null) {
                    return false;
                }
            } else {
                if (! domain.equals(other.domain)) {
                    return false;
                }
            }
            if (sources == null) {
                if (other.sources != null) {
                    return false;
                }
            } else {
                if (! sources.equals(other.sources)) {
                    return false;
                }
            }
            if (attributeFilter == null) {
                if (other.attributeFilter != null) {
                    return false;
                }
            } else {
                if (! attributeFilter.equals(other.attributeFilter)) {
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
        hash = 83 * hash + (domain != null ? domain.hashCode() : 0);
        hash = 83 * hash + (sources != null ? sources.hashCode() : 0);
        hash = 83 * hash + (attributeFilter != null ? attributeFilter.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ProductFilter: ");
        buf.append("productType=").append(productType);
        buf.append(", domain=").append(domain);
        buf.append(", sources=").append(sources);
        buf.append(", attributeFilter=").append(attributeFilter);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        encoder.encodeNullableIdentifier(productType);
        encoder.encodeNullableElement(domain);
        encoder.encodeNullableElement(sources);
        encoder.encodeNullableElement(attributeFilter);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        productType = decoder.decodeNullableIdentifier();
        domain = (org.ccsds.moims.mo.mal.structures.IdentifierList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.IdentifierList());
        sources = (org.ccsds.moims.mo.mal.structures.IdentifierList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.IdentifierList());
        attributeFilter = (org.ccsds.moims.mo.mpd.structures.AttributeFilterList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mpd.structures.AttributeFilterList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
