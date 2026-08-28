package org.ccsds.moims.mo.com.structures;

/**
 * The ObjectKey structure combines a domain and an object instance identifier
 * such that it identifies the instance of an object for a specific domain.
 */
public final class ObjectKey implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 562949970198530L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 562949970198530L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The domain of the object instance.
     */
    private org.ccsds.moims.mo.mal.structures.IdentifierList domain;

    /**
     * The unique identifier of the object instance. Must not be &quot;0&quot;
     * for values as this is the wildcard.
     */
    private Long instId;

    /**
     * Default constructor for ObjectKey.
     * 
     */
    public ObjectKey() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param domain The domain of the object instance.
     * @param instId The unique identifier of the object instance. Must not be '0' for values as this is the wildcard.
     */
    public ObjectKey(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            Long instId) {
        this.domain = domain;
        this.instId = instId;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.com.structures.ObjectKey();
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
     * Returns the field instId.
     * 
     * @return The field instId
     */
    public Long getInstId() {
        return instId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ObjectKey) {
            ObjectKey other = (ObjectKey) obj;
            if (domain == null) {
                if (other.domain != null) {
                    return false;
                }
            } else {
                if (! domain.equals(other.domain)) {
                    return false;
                }
            }
            if (instId == null) {
                if (other.instId != null) {
                    return false;
                }
            } else {
                if (! instId.equals(other.instId)) {
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
        hash = 83 * hash + (instId != null ? instId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ObjectKey: ");
        buf.append("domain=").append(domain);
        buf.append(", instId=").append(instId);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (domain == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'domain' cannot be null!");
        }
        if (instId == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'instId' cannot be null!");
        }
        encoder.encodeElement(domain);
        encoder.encodeLong(instId);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        domain = (org.ccsds.moims.mo.mal.structures.IdentifierList) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.IdentifierList());
        instId = decoder.decodeLong();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
