package org.ccsds.moims.mo.common.directory.structures;

/**
 * The ProviderSummary structure holds information about a provider of a service
 * and its capabilities.
 */
public final class ProviderSummary implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 844429241876485L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 844429241876485L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The COM object key of this service provider.
     */
    private org.ccsds.moims.mo.com.structures.ObjectKey providerKey;

    /**
     * The id of this service provider.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier providerId;

    /**
     * The service capabilities supported by this provider.
     */
    private org.ccsds.moims.mo.common.directory.structures.ProviderDetails providerDetails;

    /**
     * Default constructor for ProviderSummary.
     * 
     */
    public ProviderSummary() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param providerKey The COM object key of this service provider
     * @param providerId The id of this service provider.
     * @param providerDetails The service capabilities supported by this provider
     */
    public ProviderSummary(org.ccsds.moims.mo.com.structures.ObjectKey providerKey,
            org.ccsds.moims.mo.mal.structures.Identifier providerId,
            org.ccsds.moims.mo.common.directory.structures.ProviderDetails providerDetails) {
        this.providerKey = providerKey;
        this.providerId = providerId;
        this.providerDetails = providerDetails;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.common.directory.structures.ProviderSummary();
    }

    /**
     * Returns the field providerKey.
     * 
     * @return The field providerKey
     */
    public org.ccsds.moims.mo.com.structures.ObjectKey getProviderKey() {
        return providerKey;
    }

    /**
     * Returns the field providerId.
     * 
     * @return The field providerId
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getProviderId() {
        return providerId;
    }

    /**
     * Returns the field providerDetails.
     * 
     * @return The field providerDetails
     */
    public org.ccsds.moims.mo.common.directory.structures.ProviderDetails getProviderDetails() {
        return providerDetails;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ProviderSummary) {
            ProviderSummary other = (ProviderSummary) obj;
            if (providerKey == null) {
                if (other.providerKey != null) {
                    return false;
                }
            } else {
                if (! providerKey.equals(other.providerKey)) {
                    return false;
                }
            }
            if (providerId == null) {
                if (other.providerId != null) {
                    return false;
                }
            } else {
                if (! providerId.equals(other.providerId)) {
                    return false;
                }
            }
            if (providerDetails == null) {
                if (other.providerDetails != null) {
                    return false;
                }
            } else {
                if (! providerDetails.equals(other.providerDetails)) {
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
        hash = 83 * hash + (providerKey != null ? providerKey.hashCode() : 0);
        hash = 83 * hash + (providerId != null ? providerId.hashCode() : 0);
        hash = 83 * hash + (providerDetails != null ? providerDetails.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ProviderSummary: ");
        buf.append("providerKey=").append(providerKey);
        buf.append(", providerId=").append(providerId);
        buf.append(", providerDetails=").append(providerDetails);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (providerKey == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'providerKey' cannot be null!");
        }
        if (providerId == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'providerId' cannot be null!");
        }
        if (providerDetails == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'providerDetails' cannot be null!");
        }
        encoder.encodeElement(providerKey);
        encoder.encodeIdentifier(providerId);
        encoder.encodeElement(providerDetails);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        providerKey = (org.ccsds.moims.mo.com.structures.ObjectKey) decoder.decodeElement(new org.ccsds.moims.mo.com.structures.ObjectKey());
        providerId = decoder.decodeIdentifier();
        providerDetails = (org.ccsds.moims.mo.common.directory.structures.ProviderDetails) decoder.decodeElement(new org.ccsds.moims.mo.common.directory.structures.ProviderDetails());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
