package org.ccsds.moims.mo.common.directory.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.FileList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.SessionType;

/**
 * The PublishDetails structure holds all the required information to publish
 * new service provider details.
 */
public final class PublishDetails implements Composite {

    private static final long serialVersionUID = 844429241876486L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 844429241876486L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The unique service provider id; allows multiple service providers of the
     * same service type to coexist in the directory service.
     */
    private Identifier providerId;

    /**
     * The domain of the provider.
     */
    private IdentifierList domain;

    /**
     * The type of session of the provider.
     */
    private SessionType sessionType;

    /**
     * If this is part of a replay session, this field holds the session name
     * of the source session. NULL otherwise.
     */
    private Identifier sourceSessionName;

    /**
     * The network of the provider.
     */
    private Identifier network;

    /**
     * The new service provider details.
     */
    private ProviderDetails providerDetails;

    /**
     * The optional XML files to associate with this provider.
     */
    private FileList serviceXML;

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
    public PublishDetails(Identifier providerId,
            IdentifierList domain,
            SessionType sessionType,
            Identifier sourceSessionName,
            Identifier network,
            ProviderDetails providerDetails,
            FileList serviceXML) {
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
    public PublishDetails(Identifier providerId,
            IdentifierList domain,
            SessionType sessionType,
            Identifier network,
            ProviderDetails providerDetails) {
        this.providerId = providerId;
        this.domain = domain;
        this.sessionType = sessionType;
        this.sourceSessionName = null;
        this.network = network;
        this.providerDetails = providerDetails;
        this.serviceXML = null;
    }

    @Override
    public Element createElement() {
        return new PublishDetails();
    }

    /**
     * Returns the field providerId.
     * 
     * @return The field providerId
     */
    public Identifier getProviderId() {
        return providerId;
    }

    /**
     * Returns the field domain.
     * 
     * @return The field domain
     */
    public IdentifierList getDomain() {
        return domain;
    }

    /**
     * Returns the field sessionType.
     * 
     * @return The field sessionType
     */
    public SessionType getSessionType() {
        return sessionType;
    }

    /**
     * Returns the field sourceSessionName.
     * 
     * @return The field sourceSessionName
     */
    public Identifier getSourceSessionName() {
        return sourceSessionName;
    }

    /**
     * Returns the field network.
     * 
     * @return The field network
     */
    public Identifier getNetwork() {
        return network;
    }

    /**
     * Returns the field providerDetails.
     * 
     * @return The field providerDetails
     */
    public ProviderDetails getProviderDetails() {
        return providerDetails;
    }

    /**
     * Returns the field serviceXML.
     * 
     * @return The field serviceXML
     */
    public FileList getServiceXML() {
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
    public void encode(MALEncoder encoder) throws MALException {
        if (providerId == null) {
            throw new MALException("The field 'providerId' cannot be null!");
        }
        if (domain == null) {
            throw new MALException("The field 'domain' cannot be null!");
        }
        if (sessionType == null) {
            throw new MALException("The field 'sessionType' cannot be null!");
        }
        if (network == null) {
            throw new MALException("The field 'network' cannot be null!");
        }
        if (providerDetails == null) {
            throw new MALException("The field 'providerDetails' cannot be null!");
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
    public Element decode(MALDecoder decoder) throws MALException {
        providerId = decoder.decodeIdentifier();
        domain = (IdentifierList) decoder.decodeElement(new IdentifierList());
        sessionType = (SessionType) decoder.decodeElement(SessionType.LIVE);
        sourceSessionName = decoder.decodeNullableIdentifier();
        network = decoder.decodeIdentifier();
        providerDetails = (ProviderDetails) decoder.decodeElement(new ProviderDetails());
        serviceXML = (FileList) decoder.decodeNullableElement(new FileList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
