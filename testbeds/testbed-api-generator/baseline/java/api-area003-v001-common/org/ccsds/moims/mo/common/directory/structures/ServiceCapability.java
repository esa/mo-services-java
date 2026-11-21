package org.ccsds.moims.mo.common.directory.structures;

import org.ccsds.moims.mo.common.structures.ServiceKey;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.UShortList;

/**
 * The ServiceCapability structure holds information about a service and the
 * capabilities offered by a provider.
 */
public final class ServiceCapability implements Composite {

    private static final long serialVersionUID = 844429241876482L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 844429241876482L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The area, service, and version fields.
     */
    private ServiceKey serviceKey;

    /**
     * The supported capability set numbers for this service provider. If NULL
     * then all capability sets supported.
     */
    private UShortList supportedCapabilitySets;

    /**
     * Allows the passing of deployment specific service properties.
     */
    private NamedValueList serviceProperties;

    /**
     * Optional set of address details for this specific service which shall be
     * used instead of the provider ones when accessing this service. If all address
     * information is supplied in the containing ProviderDetails structure field
     * this list should be replaced with a NULL.
     */
    private AddressDetailsList serviceAddresses;

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
    public ServiceCapability(ServiceKey serviceKey,
            UShortList supportedCapabilitySets,
            NamedValueList serviceProperties,
            AddressDetailsList serviceAddresses) {
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
    public ServiceCapability(ServiceKey serviceKey) {
        this.serviceKey = serviceKey;
        this.supportedCapabilitySets = null;
        this.serviceProperties = null;
        this.serviceAddresses = null;
    }

    @Override
    public Element createElement() {
        return new ServiceCapability();
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
     * Returns the field supportedCapabilitySets.
     * 
     * @return The field supportedCapabilitySets
     */
    public UShortList getSupportedCapabilitySets() {
        return supportedCapabilitySets;
    }

    /**
     * Returns the field serviceProperties.
     * 
     * @return The field serviceProperties
     */
    public NamedValueList getServiceProperties() {
        return serviceProperties;
    }

    /**
     * Returns the field serviceAddresses.
     * 
     * @return The field serviceAddresses
     */
    public AddressDetailsList getServiceAddresses() {
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
    public void encode(MALEncoder encoder) throws MALException {
        if (serviceKey == null) {
            throw new MALException("The field 'serviceKey' cannot be null!");
        }
        encoder.encodeElement(serviceKey);
        encoder.encodeNullableElement(supportedCapabilitySets);
        encoder.encodeNullableElement(serviceProperties);
        encoder.encodeNullableElement(serviceAddresses);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        serviceKey = (ServiceKey) decoder.decodeElement(new ServiceKey());
        supportedCapabilitySets = (UShortList) decoder.decodeNullableElement(new UShortList());
        serviceProperties = (NamedValueList) decoder.decodeNullableElement(new NamedValueList());
        serviceAddresses = (AddressDetailsList) decoder.decodeNullableElement(new AddressDetailsList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
