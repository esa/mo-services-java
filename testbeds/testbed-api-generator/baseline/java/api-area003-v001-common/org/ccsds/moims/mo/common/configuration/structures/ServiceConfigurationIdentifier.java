package org.ccsds.moims.mo.common.configuration.structures;

import org.ccsds.moims.mo.common.structures.ServiceKey;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 * The ServiceConfigurationIdentifier structure holds the name and service
 * key of a service configuration object.
 */
public final class ServiceConfigurationIdentifier implements Composite {

    private static final long serialVersionUID = 844446421745667L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 844446421745667L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The configName field.
     */
    private Identifier configName;

    /**
     * The serviceKey field.
     */
    private ServiceKey serviceKey;

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
    public ServiceConfigurationIdentifier(Identifier configName,
            ServiceKey serviceKey) {
        this.configName = configName;
        this.serviceKey = serviceKey;
    }

    @Override
    public Element createElement() {
        return new ServiceConfigurationIdentifier();
    }

    /**
     * Returns the field configName.
     * 
     * @return The field configName
     */
    public Identifier getConfigName() {
        return configName;
    }

    /**
     * Returns the field serviceKey.
     * 
     * @return The field serviceKey
     */
    public ServiceKey getServiceKey() {
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
    public void encode(MALEncoder encoder) throws MALException {
        if (configName == null) {
            throw new MALException("The field 'configName' cannot be null!");
        }
        if (serviceKey == null) {
            throw new MALException("The field 'serviceKey' cannot be null!");
        }
        encoder.encodeIdentifier(configName);
        encoder.encodeElement(serviceKey);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        configName = decoder.decodeIdentifier();
        serviceKey = (ServiceKey) decoder.decodeElement(new ServiceKey());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
