package org.ccsds.moims.mo.common.directory.structures;

import org.ccsds.moims.mo.common.structures.ServiceKey;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UShortList;

/**
 * The ServiceFilter structure holds all information required by the Directory
 * service for service lookup operation. The field filters are AND&quot;d
 * together.
 */
public final class ServiceFilter implements Composite {

    private static final long serialVersionUID = 844429241876487L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 844429241876487L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The required service provider. Can be NULL in which case matches all values.
     */
    private Identifier serviceProviderId;

    /**
     * The domain to query. Can be NULL in which case matches all values.
     */
    private IdentifierList domain;

    /**
     * The network to match. Can be NULL in which case matches all values.
     */
    private Identifier network;

    /**
     * The session type to match. Can be NULL in which case matches all values.
     */
    private SessionType sessionType;

    /**
     * The session name to match. Can be NULL in which case matches all values.
     */
    private Identifier sessionName;

    /**
     * The service to filter on, values can be NULL which matches all values.
     */
    private ServiceKey serviceKey;

    /**
     * List of required capability sets. If NULL then matches any.
     */
    private UShortList requiredCapabilitySets;

    /**
     * Default constructor for ServiceFilter.
     * 
     */
    public ServiceFilter() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param serviceProviderId The required service provider. Can be NULL in which case matches all values.
     * @param domain The domain to query. Can be NULL in which case matches all values.
     * @param network The network to match. Can be NULL in which case matches all values.
     * @param sessionType The session type to match. Can be NULL in which case matches all values.
     * @param sessionName The session name to match. Can be NULL in which case matches all values.
     * @param serviceKey The service to filter on, values can be NULL which matches all values.
     * @param requiredCapabilitySets List of required capability sets. If NULL then matches any.
     */
    public ServiceFilter(Identifier serviceProviderId,
            IdentifierList domain,
            Identifier network,
            SessionType sessionType,
            Identifier sessionName,
            ServiceKey serviceKey,
            UShortList requiredCapabilitySets) {
        this.serviceProviderId = serviceProviderId;
        this.domain = domain;
        this.network = network;
        this.sessionType = sessionType;
        this.sessionName = sessionName;
        this.serviceKey = serviceKey;
        this.requiredCapabilitySets = requiredCapabilitySets;
    }

    @Override
    public Element createElement() {
        return new ServiceFilter();
    }

    /**
     * Returns the field serviceProviderId.
     * 
     * @return The field serviceProviderId
     */
    public Identifier getServiceProviderId() {
        return serviceProviderId;
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
     * Returns the field network.
     * 
     * @return The field network
     */
    public Identifier getNetwork() {
        return network;
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
     * Returns the field sessionName.
     * 
     * @return The field sessionName
     */
    public Identifier getSessionName() {
        return sessionName;
    }

    /**
     * Returns the field serviceKey.
     * 
     * @return The field serviceKey
     */
    public ServiceKey getServiceKey() {
        return serviceKey;
    }

    /**
     * Returns the field requiredCapabilitySets.
     * 
     * @return The field requiredCapabilitySets
     */
    public UShortList getRequiredCapabilitySets() {
        return requiredCapabilitySets;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ServiceFilter) {
            ServiceFilter other = (ServiceFilter) obj;
            if (serviceProviderId == null) {
                if (other.serviceProviderId != null) {
                    return false;
                }
            } else {
                if (! serviceProviderId.equals(other.serviceProviderId)) {
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
            if (network == null) {
                if (other.network != null) {
                    return false;
                }
            } else {
                if (! network.equals(other.network)) {
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
            if (sessionName == null) {
                if (other.sessionName != null) {
                    return false;
                }
            } else {
                if (! sessionName.equals(other.sessionName)) {
                    return false;
                }
            }
            if (serviceKey == null) {
                if (other.serviceKey != null) {
                    return false;
                }
            } else {
                if (! serviceKey.equals(other.serviceKey)) {
                    return false;
                }
            }
            if (requiredCapabilitySets == null) {
                if (other.requiredCapabilitySets != null) {
                    return false;
                }
            } else {
                if (! requiredCapabilitySets.equals(other.requiredCapabilitySets)) {
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
        hash = 83 * hash + (serviceProviderId != null ? serviceProviderId.hashCode() : 0);
        hash = 83 * hash + (domain != null ? domain.hashCode() : 0);
        hash = 83 * hash + (network != null ? network.hashCode() : 0);
        hash = 83 * hash + (sessionType != null ? sessionType.hashCode() : 0);
        hash = 83 * hash + (sessionName != null ? sessionName.hashCode() : 0);
        hash = 83 * hash + (serviceKey != null ? serviceKey.hashCode() : 0);
        hash = 83 * hash + (requiredCapabilitySets != null ? requiredCapabilitySets.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ServiceFilter: ");
        buf.append("serviceProviderId=").append(serviceProviderId);
        buf.append(", domain=").append(domain);
        buf.append(", network=").append(network);
        buf.append(", sessionType=").append(sessionType);
        buf.append(", sessionName=").append(sessionName);
        buf.append(", serviceKey=").append(serviceKey);
        buf.append(", requiredCapabilitySets=").append(requiredCapabilitySets);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        encoder.encodeNullableIdentifier(serviceProviderId);
        encoder.encodeNullableElement(domain);
        encoder.encodeNullableIdentifier(network);
        encoder.encodeNullableElement(sessionType);
        encoder.encodeNullableIdentifier(sessionName);
        encoder.encodeNullableElement(serviceKey);
        encoder.encodeNullableElement(requiredCapabilitySets);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        serviceProviderId = decoder.decodeNullableIdentifier();
        domain = (IdentifierList) decoder.decodeNullableElement(new IdentifierList());
        network = decoder.decodeNullableIdentifier();
        sessionType = (SessionType) decoder.decodeNullableElement(SessionType.LIVE);
        sessionName = decoder.decodeNullableIdentifier();
        serviceKey = (ServiceKey) decoder.decodeNullableElement(new ServiceKey());
        requiredCapabilitySets = (UShortList) decoder.decodeNullableElement(new UShortList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
