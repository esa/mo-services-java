package org.ccsds.moims.mo.mal.structures;

/**
 * The ObjectIdentity structure shall represent the Object Identity of an
 * MO Object.
 */
public final class ObjectIdentity implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 281475027043312L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475027043312L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The domain of the MO Object being referenced.
     */
    private org.ccsds.moims.mo.mal.structures.IdentifierList domain;

    /**
     * The key of the MO Object being referenced.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier key;

    /**
     * The version of the MO Object being referenced.
     */
    private org.ccsds.moims.mo.mal.structures.UInteger version;

    /**
     * Default constructor for ObjectIdentity.
     * 
     */
    public ObjectIdentity() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param domain The domain of the MO Object being referenced.
     * @param key The key of the MO Object being referenced.
     * @param version The version of the MO Object being referenced.
     */
    public ObjectIdentity(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.Identifier key,
            org.ccsds.moims.mo.mal.structures.UInteger version) {
        this.domain = domain;
        this.key = key;
        this.version = version;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mal.structures.ObjectIdentity();
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
     * Returns the field key.
     * 
     * @return The field key
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getKey() {
        return key;
    }

    /**
     * Returns the field version.
     * 
     * @return The field version
     */
    public org.ccsds.moims.mo.mal.structures.UInteger getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ObjectIdentity) {
            ObjectIdentity other = (ObjectIdentity) obj;
            if (domain == null) {
                if (other.domain != null) {
                    return false;
                }
            } else {
                if (! domain.equals(other.domain)) {
                    return false;
                }
            }
            if (key == null) {
                if (other.key != null) {
                    return false;
                }
            } else {
                if (! key.equals(other.key)) {
                    return false;
                }
            }
            if (version == null) {
                if (other.version != null) {
                    return false;
                }
            } else {
                if (! version.equals(other.version)) {
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
        hash = 83 * hash + (domain != null ? domain.hashCode() : 0);
        hash = 83 * hash + (key != null ? key.hashCode() : 0);
        hash = 83 * hash + (version != null ? version.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ObjectIdentity: ");
        buf.append("domain=").append(domain);
        buf.append(", key=").append(key);
        buf.append(", version=").append(version);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (domain == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'domain' cannot be null!");
        }
        if (key == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'key' cannot be null!");
        }
        if (version == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'version' cannot be null!");
        }
        encoder.encodeElement(domain);
        encoder.encodeIdentifier(key);
        encoder.encodeUInteger(version);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        domain = (org.ccsds.moims.mo.mal.structures.IdentifierList) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.IdentifierList());
        key = decoder.decodeIdentifier();
        version = decoder.decodeUInteger();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
