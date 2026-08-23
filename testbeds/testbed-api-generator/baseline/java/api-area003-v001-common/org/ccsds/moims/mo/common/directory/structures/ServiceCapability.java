package org.ccsds.moims.mo.common.directory.structures;

/**
 * The ServiceCapability structure holds information about a service and the
 * capabilities offered by a provider.
 */
public final class ServiceCapability implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 844429241876482L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 844429241876482L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The area, service, and version fields.
     */
    private org.ccsds.moims.mo.common.structures.ServiceKey serviceKey;

    /**
     * The supported capability set numbers for this service provider. If NULL
     * then all capability sets supported.
     */
    private org.ccsds.moims.mo.mal.structures.UShortList supportedCapabilitySets;

    /**
     * Allows the passing of deployment specific service properties.
     */
    private org.ccsds.moims.mo.mal.structures.NamedValueList serviceProperties;

    /**
     * Optional set of address details for this specific service which shall be
     * used instead of the provider ones when accessing this service. If all address
     * information is supplied in the containing ProviderDetails structure field
     * this list should be replaced with a NULL.
     */
    private org.ccsds.moims.mo.common.directory.structures.AddressDetailsList serviceAddresses;

    /**
     * Default constructor for ServiceCapability.
     * 
     */
    public ServiceCapability() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param serviceKey The area, service, and version fields.
     * @param supportedCapabilitySets The supported capability set numbers for this service provider. If NULL then all capability sets supported.
     * @param serviceProperties Allows the passing of deployment specific service properties.
     * @param serviceAddresses Optional set of address details for this specific service which shall be used instead of the provider ones when accessing this service. If all address information is supplied in the containing ProviderDetails structure field this list should be replaced with a NULL.
     */
    public ServiceCapability(org.ccsds.moims.mo.common.structures.ServiceKey serviceKey,
            org.ccsds.moims.mo.mal.structures.UShortList supportedCapabilitySets,
            org.ccsds.moims.mo.mal.structures.NamedValueList serviceProperties,
            org.ccsds.moims.mo.common.directory.structures.AddressDetailsList serviceAddresses) {
        this.serviceKey = serviceKey;
        this.supportedCapabilitySets = supportedCapabilitySets;
        this.serviceProperties = serviceProperties;
        this.serviceAddresses = serviceAddresses;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param serviceKey The area, service, and version fields.
     */
    public ServiceCapability(org.ccsds.moims.mo.common.structures.ServiceKey serviceKey) {
        this.serviceKey = serviceKey;
        this.supportedCapabilitySets = null;
        this.serviceProperties = null;
        this.serviceAddresses = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.common.directory.structures.ServiceCapability();
    }

    /**
     * Returns the field serviceKey.
     * 
     * @return The field serviceKey
     */
    public org.ccsds.moims.mo.common.structures.ServiceKey getServiceKey() {
        return serviceKey;
    }

    /**
     * Returns the field supportedCapabilitySets.
     * 
     * @return The field supportedCapabilitySets
     */
    public org.ccsds.moims.mo.mal.structures.UShortList getSupportedCapabilitySets() {
        return supportedCapabilitySets;
    }

    /**
     * Returns the field serviceProperties.
     * 
     * @return The field serviceProperties
     */
    public org.ccsds.moims.mo.mal.structures.NamedValueList getServiceProperties() {
        return serviceProperties;
    }

    /**
     * Returns the field serviceAddresses.
     * 
     * @return The field serviceAddresses
     */
    public org.ccsds.moims.mo.common.directory.structures.AddressDetailsList getServiceAddresses() {
        return serviceAddresses;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ServiceCapability) {
            ServiceCapability other = (ServiceCapability) obj;
            if (serviceKey == null) {
                if (other.serviceKey != null) {
                    return false;
                }
            } else {
                if (! serviceKey.equals(other.serviceKey)) {
                    return false;
                }
            }
            if (supportedCapabilitySets == null) {
                if (other.supportedCapabilitySets != null) {
                    return false;
                }
            } else {
                if (! supportedCapabilitySets.equals(other.supportedCapabilitySets)) {
                    return false;
                }
            }
            if (serviceProperties == null) {
                if (other.serviceProperties != null) {
                    return false;
                }
            } else {
                if (! serviceProperties.equals(other.serviceProperties)) {
                    return false;
                }
            }
            if (serviceAddresses == null) {
                if (other.serviceAddresses != null) {
                    return false;
                }
            } else {
                if (! serviceAddresses.equals(other.serviceAddresses)) {
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
        hash = 83 * hash + (serviceKey != null ? serviceKey.hashCode() : 0);
        hash = 83 * hash + (supportedCapabilitySets != null ? supportedCapabilitySets.hashCode() : 0);
        hash = 83 * hash + (serviceProperties != null ? serviceProperties.hashCode() : 0);
        hash = 83 * hash + (serviceAddresses != null ? serviceAddresses.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ServiceCapability: ");
        buf.append("serviceKey=").append(serviceKey);
        buf.append(", supportedCapabilitySets=").append(supportedCapabilitySets);
        buf.append(", serviceProperties=").append(serviceProperties);
        buf.append(", serviceAddresses=").append(serviceAddresses);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (serviceKey == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'serviceKey' cannot be null!");
        }
        encoder.encodeElement(serviceKey);
        encoder.encodeNullableElement(supportedCapabilitySets);
        encoder.encodeNullableElement(serviceProperties);
        encoder.encodeNullableElement(serviceAddresses);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        serviceKey = (org.ccsds.moims.mo.common.structures.ServiceKey) decoder.decodeElement(new org.ccsds.moims.mo.common.structures.ServiceKey());
        supportedCapabilitySets = (org.ccsds.moims.mo.mal.structures.UShortList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.UShortList());
        serviceProperties = (org.ccsds.moims.mo.mal.structures.NamedValueList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.NamedValueList());
        serviceAddresses = (org.ccsds.moims.mo.common.directory.structures.AddressDetailsList) decoder.decodeNullableElement(new org.ccsds.moims.mo.common.directory.structures.AddressDetailsList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
