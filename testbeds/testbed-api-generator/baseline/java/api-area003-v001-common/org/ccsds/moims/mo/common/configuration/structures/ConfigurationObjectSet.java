package org.ccsds.moims.mo.common.configuration.structures;

import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.LongList;

/**
 * The configuration object set holds a set of object identifiers for a single
 * COM object type in a single domain.
 */
public final class ConfigurationObjectSet implements Composite {

    private static final long serialVersionUID = 844446421745665L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 844446421745665L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The COM object type of the configuration objects.
     */
    private ObjectType objType;

    /**
     * The domain of the configuration objects.
     */
    private IdentifierList domain;

    /**
     * The set of COM object identifiers that form this configuration set.
     */
    private LongList objInstIds;

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
    public ConfigurationObjectSet(ObjectType objType,
            IdentifierList domain,
            LongList objInstIds) {
        this.objType = objType;
        this.domain = domain;
        this.objInstIds = objInstIds;
    }

    @Override
    public Element createElement() {
        return new ConfigurationObjectSet();
    }

    /**
     * Returns the field objType.
     * 
     * @return The field objType
     */
    public ObjectType getObjType() {
        return objType;
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
     * Returns the field objInstIds.
     * 
     * @return The field objInstIds
     */
    public LongList getObjInstIds() {
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
    public void encode(MALEncoder encoder) throws MALException {
        if (objType == null) {
            throw new MALException("The field 'objType' cannot be null!");
        }
        if (domain == null) {
            throw new MALException("The field 'domain' cannot be null!");
        }
        if (objInstIds == null) {
            throw new MALException("The field 'objInstIds' cannot be null!");
        }
        encoder.encodeElement(objType);
        encoder.encodeElement(domain);
        encoder.encodeElement(objInstIds);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        objType = (ObjectType) decoder.decodeElement(new ObjectType());
        domain = (IdentifierList) decoder.decodeElement(new IdentifierList());
        objInstIds = (LongList) decoder.decodeElement(new LongList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
