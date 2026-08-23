package org.ccsds.moims.mo.common.configuration.structures;

/**
 * The ConfigurationObjectDetails composite holds a zero to many ConfigurationObjectSet
 * structures. It allows a configuration to reference COM objects from more
 * than one domain or of more than one COM object type.
 */
public final class ConfigurationObjectDetails implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 844446421745666L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 844446421745666L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The list of configuration objects.
     */
    private org.ccsds.moims.mo.common.configuration.structures.ConfigurationObjectSetList configObjects;

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
    public ConfigurationObjectDetails(org.ccsds.moims.mo.common.configuration.structures.ConfigurationObjectSetList configObjects) {
        this.configObjects = configObjects;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.common.configuration.structures.ConfigurationObjectDetails();
    }

    /**
     * Returns the field configObjects.
     * 
     * @return The field configObjects
     */
    public org.ccsds.moims.mo.common.configuration.structures.ConfigurationObjectSetList getConfigObjects() {
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
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (configObjects == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'configObjects' cannot be null!");
        }
        encoder.encodeElement(configObjects);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        configObjects = (org.ccsds.moims.mo.common.configuration.structures.ConfigurationObjectSetList) decoder.decodeElement(new org.ccsds.moims.mo.common.configuration.structures.ConfigurationObjectSetList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
