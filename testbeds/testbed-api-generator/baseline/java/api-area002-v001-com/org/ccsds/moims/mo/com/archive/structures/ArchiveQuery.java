package org.ccsds.moims.mo.com.archive.structures;

/**
 * The ArchiveQuery structure is used to specify filters on the common parts
 * of an object in an archive.
 */
public final class ArchiveQuery implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 562958560133122L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 562958560133122L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Only the objects whose domain matches the provided domain will be returned.
     * The domain field supports the wildcard value of &quot;*&quot; only in the
     * last part of the domain. If NULL then all domains shall be matched.
     */
    private org.ccsds.moims.mo.mal.structures.IdentifierList domain;

    /**
     * Optional network zone. Only the objects whose network zone matches the
     * provided value will be returned. If NULL then all values will be matched.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier network;

    /**
     * Optional provider. Only the objects whose provider URI matches the provided
     * provider URI will be returned. If NULL then all values will be matched.
     */
    private org.ccsds.moims.mo.mal.structures.URI provider;

    /**
     * Object instance identifier of the related Object. Only the objects whose
     * related Object matches the provided related Object will be returned unless
     * the &quot;0&quot; wildcard value is supplied.
     */
    private Long related;

    /**
     * Optional source. Only the objects whose source matches the provided source
     * will be returned. The fields of the ObjectId may contain the wildcard values
     * (&quot;0&quot; for numeric fields, &quot;*&quot; for Identifier fields).
     * If NULL then all values will be matched. .
     */
    private org.ccsds.moims.mo.com.structures.ObjectId source;

    /**
     * Optional start time. Only the objects whose timestamp is equal or greater
     * than the provided start time will be returned. If NULL then no start time
     * shall be applied.
     */
    private org.ccsds.moims.mo.mal.structures.FineTime startTime;

    /**
     * Optional end time. Only the objects whose timestamp is equal or less than
     * the provided end time will be returned. If NULL then no end time will be
     * applied.
     */
    private org.ccsds.moims.mo.mal.structures.FineTime endTime;

    /**
     * If set to TRUE then returned values shall be sorted in ascending order,
     * if FALSE then in descending order. If NULL then no sorting shall be applied.
     */
    private Boolean sortOrder;

    /**
     * If the returned values are to be sorted because the sortOrder field is
     * not NULL then this field may contain the name of the field in the object
     * body (MAL::Composite) to sort against. If the object body is not a composite
     * but an Attribute or Enumeration then to sort on the values an empty string
     * of "" should be used. Enumerations are sorted on their ordinal. If this
     * field is NULL then the objects shall be sorted on the COM object timestamp.
     * The field follows the naming convention of CompositeFilter::fieldName.
     * If the field points to a composite, list, abstract type (including Attribute)
     * or Blob then no sorting shall be applied.
     */
    private String sortFieldName;

    /**
     * Default constructor for ArchiveQuery.
     * 
     */
    public ArchiveQuery() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param domain Only the objects whose domain matches the provided domain will be returned. The domain field supports the wildcard value of '*' only in the last part of the domain. If NULL then all domains shall be matched.
     * @param network Optional network zone. Only the objects whose network zone matches the provided value will be returned. If NULL then all values will be matched.
     * @param provider Optional provider. Only the objects whose provider URI matches the provided provider URI will be returned. If NULL then all values will be matched.
     * @param related Object instance identifier of the related Object. Only the objects whose related Object matches the provided related Object will be returned unless the '0' wildcard value is supplied.
     * @param source Optional source. Only the objects whose source matches the provided source will be returned. The fields of the ObjectId may contain the wildcard values ('0' for numeric fields, '*' for Identifier fields). If NULL then all values will be matched. 
     * @param startTime Optional start time. Only the objects whose timestamp is equal or greater than the provided start time will be returned. If NULL then no start time shall be applied.
     * @param endTime Optional end time. Only the objects whose timestamp is equal or less than the provided end time will be returned. If NULL then no end time will be applied.
     * @param sortOrder If set to TRUE then returned values shall be sorted in ascending order, if FALSE then in descending order. If NULL then no sorting shall be applied.
     * @param sortFieldName If the returned values are to be sorted because the sortOrder field is not NULL then this field may contain the name of the field in the object body (MAL::Composite) to sort against. If the object body is not a composite but an Attribute or Enumeration then to sort on the values an empty string of "" should be used. Enumerations are sorted on their ordinal. If this field is NULL then the objects shall be sorted on the COM object timestamp. The field follows the naming convention of CompositeFilter::fieldName. If the field points to a composite, list, abstract type (including Attribute) or Blob then no sorting shall be applied.
     */
    public ArchiveQuery(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.Identifier network,
            org.ccsds.moims.mo.mal.structures.URI provider,
            Long related,
            org.ccsds.moims.mo.com.structures.ObjectId source,
            org.ccsds.moims.mo.mal.structures.FineTime startTime,
            org.ccsds.moims.mo.mal.structures.FineTime endTime,
            Boolean sortOrder,
            String sortFieldName) {
        this.domain = domain;
        this.network = network;
        this.provider = provider;
        this.related = related;
        this.source = source;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sortOrder = sortOrder;
        this.sortFieldName = sortFieldName;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param related Object instance identifier of the related Object. Only the objects whose related Object matches the provided related Object will be returned unless the '0' wildcard value is supplied.
     */
    public ArchiveQuery(Long related) {
        this.domain = null;
        this.network = null;
        this.provider = null;
        this.related = related;
        this.source = null;
        this.startTime = null;
        this.endTime = null;
        this.sortOrder = null;
        this.sortFieldName = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.com.archive.structures.ArchiveQuery();
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
     * Returns the field network.
     * 
     * @return The field network
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getNetwork() {
        return network;
    }

    /**
     * Returns the field provider.
     * 
     * @return The field provider
     */
    public org.ccsds.moims.mo.mal.structures.URI getProvider() {
        return provider;
    }

    /**
     * Returns the field related.
     * 
     * @return The field related
     */
    public Long getRelated() {
        return related;
    }

    /**
     * Returns the field source.
     * 
     * @return The field source
     */
    public org.ccsds.moims.mo.com.structures.ObjectId getSource() {
        return source;
    }

    /**
     * Returns the field startTime.
     * 
     * @return The field startTime
     */
    public org.ccsds.moims.mo.mal.structures.FineTime getStartTime() {
        return startTime;
    }

    /**
     * Returns the field endTime.
     * 
     * @return The field endTime
     */
    public org.ccsds.moims.mo.mal.structures.FineTime getEndTime() {
        return endTime;
    }

    /**
     * Returns the field sortOrder.
     * 
     * @return The field sortOrder
     */
    public Boolean getSortOrder() {
        return sortOrder;
    }

    /**
     * Returns the field sortFieldName.
     * 
     * @return The field sortFieldName
     */
    public String getSortFieldName() {
        return sortFieldName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ArchiveQuery) {
            ArchiveQuery other = (ArchiveQuery) obj;
            if (domain == null) {
                if (other.domain != null) {
                    return false;
                }
            } else {
                if (! domain.equals(other.domain)) {
                    return false;
                }
            }
            if (network == null) {
                if (other.network != null) {
                    return false;
                }
            } else {
                if (! network.equals(other.network)) {
                    return false;
                }
            }
            if (provider == null) {
                if (other.provider != null) {
                    return false;
                }
            } else {
                if (! provider.equals(other.provider)) {
                    return false;
                }
            }
            if (related == null) {
                if (other.related != null) {
                    return false;
                }
            } else {
                if (! related.equals(other.related)) {
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
            if (startTime == null) {
                if (other.startTime != null) {
                    return false;
                }
            } else {
                if (! startTime.equals(other.startTime)) {
                    return false;
                }
            }
            if (endTime == null) {
                if (other.endTime != null) {
                    return false;
                }
            } else {
                if (! endTime.equals(other.endTime)) {
                    return false;
                }
            }
            if (sortOrder == null) {
                if (other.sortOrder != null) {
                    return false;
                }
            } else {
                if (! sortOrder.equals(other.sortOrder)) {
                    return false;
                }
            }
            if (sortFieldName == null) {
                if (other.sortFieldName != null) {
                    return false;
                }
            } else {
                if (! sortFieldName.equals(other.sortFieldName)) {
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
        hash = 83 * hash + (domain != null ? domain.hashCode() : 0);
        hash = 83 * hash + (network != null ? network.hashCode() : 0);
        hash = 83 * hash + (provider != null ? provider.hashCode() : 0);
        hash = 83 * hash + (related != null ? related.hashCode() : 0);
        hash = 83 * hash + (source != null ? source.hashCode() : 0);
        hash = 83 * hash + (startTime != null ? startTime.hashCode() : 0);
        hash = 83 * hash + (endTime != null ? endTime.hashCode() : 0);
        hash = 83 * hash + (sortOrder != null ? sortOrder.hashCode() : 0);
        hash = 83 * hash + (sortFieldName != null ? sortFieldName.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ArchiveQuery: ");
        buf.append("domain=").append(domain);
        buf.append(", network=").append(network);
        buf.append(", provider=").append(provider);
        buf.append(", related=").append(related);
        buf.append(", source=").append(source);
        buf.append(", startTime=").append(startTime);
        buf.append(", endTime=").append(endTime);
        buf.append(", sortOrder=").append(sortOrder);
        buf.append(", sortFieldName=").append(sortFieldName);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (related == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'related' cannot be null!");
        }
        encoder.encodeNullableElement(domain);
        encoder.encodeNullableIdentifier(network);
        encoder.encodeNullableURI(provider);
        encoder.encodeLong(related);
        encoder.encodeNullableElement(source);
        encoder.encodeNullableFineTime(startTime);
        encoder.encodeNullableFineTime(endTime);
        encoder.encodeNullableBoolean(sortOrder);
        encoder.encodeNullableString(sortFieldName);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        domain = (org.ccsds.moims.mo.mal.structures.IdentifierList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.IdentifierList());
        network = decoder.decodeNullableIdentifier();
        provider = decoder.decodeNullableURI();
        related = decoder.decodeLong();
        source = (org.ccsds.moims.mo.com.structures.ObjectId) decoder.decodeNullableElement(new org.ccsds.moims.mo.com.structures.ObjectId());
        startTime = decoder.decodeNullableFineTime();
        endTime = decoder.decodeNullableFineTime();
        sortOrder = decoder.decodeNullableBoolean();
        sortFieldName = decoder.decodeNullableString();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
