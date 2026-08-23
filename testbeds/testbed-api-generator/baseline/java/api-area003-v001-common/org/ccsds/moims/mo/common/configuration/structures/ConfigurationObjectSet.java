package org.ccsds.moims.mo.common.configuration.structures;

/**
 * The configuration object set holds a set of object identifiers for a single
 * COM object type in a single domain.
 */
public final class ConfigurationObjectSet implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 844446421745665L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 844446421745665L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The COM object type of the configuration objects.
     */
    private org.ccsds.moims.mo.com.structures.ObjectType objType;

    /**
     * The domain of the configuration objects.
     */
    private org.ccsds.moims.mo.mal.structures.IdentifierList domain;

    /**
     * The set of COM object identifiers that form this configuration set.
     */
    private org.ccsds.moims.mo.mal.structures.LongList objInstIds;

    /**
     * Default constructor for ConfigurationObjectSet.
     * 
     */
    public ConfigurationObjectSet() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param objType The COM object type of the configuration objects.
     * @param domain The domain of the configuration objects.
     * @param objInstIds The set of COM object identifiers that form this configuration set.
     */
    public ConfigurationObjectSet(org.ccsds.moims.mo.com.structures.ObjectType objType,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.LongList objInstIds) {
        this.objType = objType;
        this.domain = domain;
        this.objInstIds = objInstIds;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.common.configuration.structures.ConfigurationObjectSet();
    }

    /**
     * Returns the field objType.
     * 
     * @return The field objType
     */
    public org.ccsds.moims.mo.com.structures.ObjectType getObjType() {
        return objType;
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
     * Returns the field objInstIds.
     * 
     * @return The field objInstIds
     */
    public org.ccsds.moims.mo.mal.structures.LongList getObjInstIds() {
        return objInstIds;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ConfigurationObjectSet) {
            ConfigurationObjectSet other = (ConfigurationObjectSet) obj;
            if (objType == null) {
                if (other.objType != null) {
                    return false;
                }
            } else {
                if (! objType.equals(other.objType)) {
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
            if (objInstIds == null) {
                if (other.objInstIds != null) {
                    return false;
                }
            } else {
                if (! objInstIds.equals(other.objInstIds)) {
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
        hash = 83 * hash + (objType != null ? objType.hashCode() : 0);
        hash = 83 * hash + (domain != null ? domain.hashCode() : 0);
        hash = 83 * hash + (objInstIds != null ? objInstIds.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ConfigurationObjectSet: ");
        buf.append("objType=").append(objType);
        buf.append(", domain=").append(domain);
        buf.append(", objInstIds=").append(objInstIds);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (objType == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'objType' cannot be null!");
        }
        if (domain == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'domain' cannot be null!");
        }
        if (objInstIds == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'objInstIds' cannot be null!");
        }
        encoder.encodeElement(objType);
        encoder.encodeElement(domain);
        encoder.encodeElement(objInstIds);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        objType = (org.ccsds.moims.mo.com.structures.ObjectType) decoder.decodeElement(new org.ccsds.moims.mo.com.structures.ObjectType());
        domain = (org.ccsds.moims.mo.mal.structures.IdentifierList) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.IdentifierList());
        objInstIds = (org.ccsds.moims.mo.mal.structures.LongList) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.LongList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
