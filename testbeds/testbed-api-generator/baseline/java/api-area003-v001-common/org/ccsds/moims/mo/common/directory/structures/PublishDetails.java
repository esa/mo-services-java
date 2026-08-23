package org.ccsds.moims.mo.common.directory.structures;

/**
 * The PublishDetails structure holds all the required information to publish
 * new service provider details.
 */
public final class PublishDetails implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 844429241876486L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 844429241876486L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The unique service provider id; allows multiple service providers of the
     * same service type to coexist in the directory service.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier providerId;

    /**
     * The domain of the provider.
     */
    private org.ccsds.moims.mo.mal.structures.IdentifierList domain;

    /**
     * The type of session of the provider.
     */
    private org.ccsds.moims.mo.mal.structures.SessionType sessionType;

    /**
     * If this is part of a replay session, this field holds the session name
     * of the source session. NULL otherwise.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier sourceSessionName;

    /**
     * The network of the provider.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier network;

    /**
     * The new service provider details.
     */
    private org.ccsds.moims.mo.common.directory.structures.ProviderDetails providerDetails;

    /**
     * The optional XML files to associate with this provider.
     */
    private org.ccsds.moims.mo.mal.structures.FileList serviceXML;

    /**
     * Default constructor for PublishDetails.
     * 
     */
    public PublishDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param providerId The unique service provider id; allows multiple service providers of the same service type to coexist in the directory service.
     * @param domain The domain of the provider.
     * @param sessionType The type of session of the provider.
     * @param sourceSessionName If this is part of a replay session, this field holds the session name of the source session. NULL otherwise
     * @param network The network of the provider.
     * @param providerDetails The new service provider details.
     * @param serviceXML The optional XML files to associate with this provider.
     */
    public PublishDetails(org.ccsds.moims.mo.mal.structures.Identifier providerId,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.SessionType sessionType,
            org.ccsds.moims.mo.mal.structures.Identifier sourceSessionName,
            org.ccsds.moims.mo.mal.structures.Identifier network,
            org.ccsds.moims.mo.common.directory.structures.ProviderDetails providerDetails,
            org.ccsds.moims.mo.mal.structures.FileList serviceXML) {
        this.providerId = providerId;
        this.domain = domain;
        this.sessionType = sessionType;
        this.sourceSessionName = sourceSessionName;
        this.network = network;
        this.providerDetails = providerDetails;
        this.serviceXML = serviceXML;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param providerId The unique service provider id; allows multiple service providers of the same service type to coexist in the directory service.
     * @param domain The domain of the provider.
     * @param sessionType The type of session of the provider.
     * @param network The network of the provider.
     * @param providerDetails The new service provider details.
     */
    public PublishDetails(org.ccsds.moims.mo.mal.structures.Identifier providerId,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.SessionType sessionType,
            org.ccsds.moims.mo.mal.structures.Identifier network,
            org.ccsds.moims.mo.common.directory.structures.ProviderDetails providerDetails) {
        this.providerId = providerId;
        this.domain = domain;
        this.sessionType = sessionType;
        this.sourceSessionName = null;
        this.network = network;
        this.providerDetails = providerDetails;
        this.serviceXML = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.common.directory.structures.PublishDetails();
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
     * Returns the field domain.
     * 
     * @return The field domain
     */
    public org.ccsds.moims.mo.mal.structures.IdentifierList getDomain() {
        return domain;
    }

    /**
     * Returns the field sessionType.
     * 
     * @return The field sessionType
     */
    public org.ccsds.moims.mo.mal.structures.SessionType getSessionType() {
        return sessionType;
    }

    /**
     * Returns the field sourceSessionName.
     * 
     * @return The field sourceSessionName
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getSourceSessionName() {
        return sourceSessionName;
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
     * Returns the field providerDetails.
     * 
     * @return The field providerDetails
     */
    public org.ccsds.moims.mo.common.directory.structures.ProviderDetails getProviderDetails() {
        return providerDetails;
    }

    /**
     * Returns the field serviceXML.
     * 
     * @return The field serviceXML
     */
    public org.ccsds.moims.mo.mal.structures.FileList getServiceXML() {
        return serviceXML;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PublishDetails) {
            PublishDetails other = (PublishDetails) obj;
            if (providerId == null) {
                if (other.providerId != null) {
                    return false;
                }
            } else {
                if (! providerId.equals(other.providerId)) {
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
            if (sessionType == null) {
                if (other.sessionType != null) {
                    return false;
                }
            } else {
                if (! sessionType.equals(other.sessionType)) {
                    return false;
                }
            }
            if (sourceSessionName == null) {
                if (other.sourceSessionName != null) {
                    return false;
                }
            } else {
                if (! sourceSessionName.equals(other.sourceSessionName)) {
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
            if (providerDetails == null) {
                if (other.providerDetails != null) {
                    return false;
                }
            } else {
                if (! providerDetails.equals(other.providerDetails)) {
                    return false;
                }
            }
            if (serviceXML == null) {
                if (other.serviceXML != null) {
                    return false;
                }
            } else {
                if (! serviceXML.equals(other.serviceXML)) {
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
        hash = 83 * hash + (providerId != null ? providerId.hashCode() : 0);
        hash = 83 * hash + (domain != null ? domain.hashCode() : 0);
        hash = 83 * hash + (sessionType != null ? sessionType.hashCode() : 0);
        hash = 83 * hash + (sourceSessionName != null ? sourceSessionName.hashCode() : 0);
        hash = 83 * hash + (network != null ? network.hashCode() : 0);
        hash = 83 * hash + (providerDetails != null ? providerDetails.hashCode() : 0);
        hash = 83 * hash + (serviceXML != null ? serviceXML.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(PublishDetails: ");
        buf.append("providerId=").append(providerId);
        buf.append(", domain=").append(domain);
        buf.append(", sessionType=").append(sessionType);
        buf.append(", sourceSessionName=").append(sourceSessionName);
        buf.append(", network=").append(network);
        buf.append(", providerDetails=").append(providerDetails);
        buf.append(", serviceXML=").append(serviceXML);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (providerId == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'providerId' cannot be null!");
        }
        if (domain == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'domain' cannot be null!");
        }
        if (sessionType == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'sessionType' cannot be null!");
        }
        if (network == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'network' cannot be null!");
        }
        if (providerDetails == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'providerDetails' cannot be null!");
        }
        encoder.encodeIdentifier(providerId);
        encoder.encodeElement(domain);
        encoder.encodeElement(sessionType);
        encoder.encodeNullableIdentifier(sourceSessionName);
        encoder.encodeIdentifier(network);
        encoder.encodeElement(providerDetails);
        encoder.encodeNullableElement(serviceXML);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        providerId = decoder.decodeIdentifier();
        domain = (org.ccsds.moims.mo.mal.structures.IdentifierList) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.IdentifierList());
        sessionType = (org.ccsds.moims.mo.mal.structures.SessionType) decoder.decodeElement(org.ccsds.moims.mo.mal.structures.SessionType.LIVE);
        sourceSessionName = decoder.decodeNullableIdentifier();
        network = decoder.decodeIdentifier();
        providerDetails = (org.ccsds.moims.mo.common.directory.structures.ProviderDetails) decoder.decodeElement(new org.ccsds.moims.mo.common.directory.structures.ProviderDetails());
        serviceXML = (org.ccsds.moims.mo.mal.structures.FileList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.FileList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
