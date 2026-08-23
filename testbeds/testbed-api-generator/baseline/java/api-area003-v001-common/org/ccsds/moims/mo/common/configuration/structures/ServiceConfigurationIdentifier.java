package org.ccsds.moims.mo.common.configuration.structures;

/**
 * The ServiceConfigurationIdentifier structure holds the name and service
 * key of a service configuration object.
 */
public final class ServiceConfigurationIdentifier implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 844446421745667L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 844446421745667L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The configName field.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier configName;

    /**
     * The serviceKey field.
     */
    private org.ccsds.moims.mo.common.structures.ServiceKey serviceKey;

    /**
     * Default constructor for ServiceConfigurationIdentifier.
     * 
     */
    public ServiceConfigurationIdentifier() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param configName The configName field.
     * @param serviceKey The serviceKey field.
     */
    public ServiceConfigurationIdentifier(org.ccsds.moims.mo.mal.structures.Identifier configName,
            org.ccsds.moims.mo.common.structures.ServiceKey serviceKey) {
        this.configName = configName;
        this.serviceKey = serviceKey;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.common.configuration.structures.ServiceConfigurationIdentifier();
    }

    /**
     * Returns the field configName.
     * 
     * @return The field configName
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getConfigName() {
        return configName;
    }

    /**
     * Returns the field serviceKey.
     * 
     * @return The field serviceKey
     */
    public org.ccsds.moims.mo.common.structures.ServiceKey getServiceKey() {
        return serviceKey;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ServiceConfigurationIdentifier) {
            ServiceConfigurationIdentifier other = (ServiceConfigurationIdentifier) obj;
            if (configName == null) {
                if (other.configName != null) {
                    return false;
                }
            } else {
                if (! configName.equals(other.configName)) {
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
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (configName != null ? configName.hashCode() : 0);
        hash = 83 * hash + (serviceKey != null ? serviceKey.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ServiceConfigurationIdentifier: ");
        buf.append("configName=").append(configName);
        buf.append(", serviceKey=").append(serviceKey);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (configName == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'configName' cannot be null!");
        }
        if (serviceKey == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'serviceKey' cannot be null!");
        }
        encoder.encodeIdentifier(configName);
        encoder.encodeElement(serviceKey);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        configName = decoder.decodeIdentifier();
        serviceKey = (org.ccsds.moims.mo.common.structures.ServiceKey) decoder.decodeElement(new org.ccsds.moims.mo.common.structures.ServiceKey());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
