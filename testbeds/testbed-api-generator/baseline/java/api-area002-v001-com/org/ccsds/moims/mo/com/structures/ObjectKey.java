package org.ccsds.moims.mo.com.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.IdentifierList;

/**
 * The ObjectKey structure combines a domain and an object instance identifier
 * such that it identifies the instance of an object for a specific domain.
 */
public final class ObjectKey implements Composite {

    private static final long serialVersionUID = 562949970198530L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 562949970198530L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The domain of the object instance.
     */
    private IdentifierList domain;

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
    public ObjectKey(IdentifierList domain,
            Long instId) {
        this.domain = domain;
        this.instId = instId;
    }

    @Override
    public Element createElement() {
        return new ObjectKey();
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
    public void encode(MALEncoder encoder) throws MALException {
        if (domain == null) {
            throw new MALException("The field 'domain' cannot be null!");
        }
        if (instId == null) {
            throw new MALException("The field 'instId' cannot be null!");
        }
        encoder.encodeElement(domain);
        encoder.encodeLong(instId);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        domain = (IdentifierList) decoder.decodeElement(new IdentifierList());
        instId = decoder.decodeLong();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
