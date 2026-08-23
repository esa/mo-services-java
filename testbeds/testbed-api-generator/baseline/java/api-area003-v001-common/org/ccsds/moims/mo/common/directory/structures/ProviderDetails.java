package org.ccsds.moims.mo.common.directory.structures;

/**
 * The ProviderDetails structure holds information about a provider of a service
 * and its capabilities. The structure contains a list of AddressDetails structures
 * which should be used when the individual services listed by the provider
 * do not supply address information. A provider may support more than one
 * transport technology and therefore can be reached using more than one address.
 */
public final class ProviderDetails implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 844429241876481L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 844429241876481L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The service capabilities supported by this service provider.
     */
    private org.ccsds.moims.mo.common.directory.structures.ServiceCapabilityList serviceCapabilities;

    /**
     * List of addresses for all services of this service provider unless service
     * specific addresses are supplied in the serviceCapabilities field. If all
     * address information is supplied in the serviceCapabilities field this list
     * should be zero length.
     */
    private org.ccsds.moims.mo.common.directory.structures.AddressDetailsList providerAddresses;

    /**
     * Default constructor for ProviderDetails.
     * 
     */
    public ProviderDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param serviceCapabilities The service capabilities supported by this service provider
     * @param providerAddresses List of addresses for all services of this service provider unless service specific addresses are supplied in the serviceCapabilities field. If all address information is supplied in the serviceCapabilities field this list should be zero length.
     */
    public ProviderDetails(org.ccsds.moims.mo.common.directory.structures.ServiceCapabilityList serviceCapabilities,
            org.ccsds.moims.mo.common.directory.structures.AddressDetailsList providerAddresses) {
        this.serviceCapabilities = serviceCapabilities;
        this.providerAddresses = providerAddresses;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.common.directory.structures.ProviderDetails();
    }

    /**
     * Returns the field serviceCapabilities.
     * 
     * @return The field serviceCapabilities
     */
    public org.ccsds.moims.mo.common.directory.structures.ServiceCapabilityList getServiceCapabilities() {
        return serviceCapabilities;
    }

    /**
     * Returns the field providerAddresses.
     * 
     * @return The field providerAddresses
     */
    public org.ccsds.moims.mo.common.directory.structures.AddressDetailsList getProviderAddresses() {
        return providerAddresses;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ProviderDetails) {
            ProviderDetails other = (ProviderDetails) obj;
            if (serviceCapabilities == null) {
                if (other.serviceCapabilities != null) {
                    return false;
                }
            } else {
                if (! serviceCapabilities.equals(other.serviceCapabilities)) {
                    return false;
                }
            }
            if (providerAddresses == null) {
                if (other.providerAddresses != null) {
                    return false;
                }
            } else {
                if (! providerAddresses.equals(other.providerAddresses)) {
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
        hash = 83 * hash + (serviceCapabilities != null ? serviceCapabilities.hashCode() : 0);
        hash = 83 * hash + (providerAddresses != null ? providerAddresses.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ProviderDetails: ");
        buf.append("serviceCapabilities=").append(serviceCapabilities);
        buf.append(", providerAddresses=").append(providerAddresses);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (serviceCapabilities == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'serviceCapabilities' cannot be null!");
        }
        if (providerAddresses == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'providerAddresses' cannot be null!");
        }
        encoder.encodeElement(serviceCapabilities);
        encoder.encodeElement(providerAddresses);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        serviceCapabilities = (org.ccsds.moims.mo.common.directory.structures.ServiceCapabilityList) decoder.decodeElement(new org.ccsds.moims.mo.common.directory.structures.ServiceCapabilityList());
        providerAddresses = (org.ccsds.moims.mo.common.directory.structures.AddressDetailsList) decoder.decodeElement(new org.ccsds.moims.mo.common.directory.structures.AddressDetailsList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
