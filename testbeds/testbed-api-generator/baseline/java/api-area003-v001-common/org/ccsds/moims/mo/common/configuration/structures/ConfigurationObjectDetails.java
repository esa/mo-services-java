package org.ccsds.moims.mo.common.configuration.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * The ConfigurationObjectDetails composite holds a zero to many ConfigurationObjectSet
 * structures. It allows a configuration to reference COM objects from more
 * than one domain or of more than one COM object type.
 */
public final class ConfigurationObjectDetails implements Composite {

    private static final long serialVersionUID = 844446421745666L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 844446421745666L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The list of configuration objects.
     */
    private ConfigurationObjectSetList configObjects;

    /**
     * Default constructor for ConfigurationObjectDetails.
     * 
     */
    public ConfigurationObjectDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param configObjects The list of configuration objects.
     */
    public ConfigurationObjectDetails(ConfigurationObjectSetList configObjects) {
        this.configObjects = configObjects;
    }

    @Override
    public Element createElement() {
        return new ConfigurationObjectDetails();
    }

    /**
     * Returns the field configObjects.
     * 
     * @return The field configObjects
     */
    public ConfigurationObjectSetList getConfigObjects() {
        return configObjects;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ConfigurationObjectDetails) {
            ConfigurationObjectDetails other = (ConfigurationObjectDetails) obj;
            if (configObjects == null) {
                if (other.configObjects != null) {
                    return false;
                }
            } else {
                if (! configObjects.equals(other.configObjects)) {
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
        hash = 83 * hash + (configObjects != null ? configObjects.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ConfigurationObjectDetails: ");
        buf.append("configObjects=").append(configObjects);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (configObjects == null) {
            throw new MALException("The field 'configObjects' cannot be null!");
        }
        encoder.encodeElement(configObjects);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        configObjects = (ConfigurationObjectSetList) decoder.decodeElement(new ConfigurationObjectSetList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
