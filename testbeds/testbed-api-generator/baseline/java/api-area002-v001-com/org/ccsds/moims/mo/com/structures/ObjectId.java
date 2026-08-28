package org.ccsds.moims.mo.com.structures;

/**
 * The ObjectId structure combines an object type and an object key such that
 * it identifies the instance and type of an object for a specific domain.
 */
public final class ObjectId implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 562949970198531L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 562949970198531L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The fully qualified unique identifier of the type.
     */
    private org.ccsds.moims.mo.com.structures.ObjectType type;

    /**
     * The combination of the object domain and object instance identifier.
     */
    private org.ccsds.moims.mo.com.structures.ObjectKey key;

    /**
     * Default constructor for ObjectId.
     * 
     */
    public ObjectId() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param type The fully qualified unique identifier of the type.
     * @param key The combination of the object domain and object instance identifier.
     */
    public ObjectId(org.ccsds.moims.mo.com.structures.ObjectType type,
            org.ccsds.moims.mo.com.structures.ObjectKey key) {
        this.type = type;
        this.key = key;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.com.structures.ObjectId();
    }

    /**
     * Returns the field type.
     * 
     * @return The field type
     */
    public org.ccsds.moims.mo.com.structures.ObjectType getType() {
        return type;
    }

    /**
     * Returns the field key.
     * 
     * @return The field key
     */
    public org.ccsds.moims.mo.com.structures.ObjectKey getKey() {
        return key;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ObjectId) {
            ObjectId other = (ObjectId) obj;
            if (type == null) {
                if (other.type != null) {
                    return false;
                }
            } else {
                if (! type.equals(other.type)) {
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
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (type != null ? type.hashCode() : 0);
        hash = 83 * hash + (key != null ? key.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ObjectId: ");
        buf.append("type=").append(type);
        buf.append(", key=").append(key);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (type == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'type' cannot be null!");
        }
        if (key == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'key' cannot be null!");
        }
        encoder.encodeElement(type);
        encoder.encodeElement(key);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        type = (org.ccsds.moims.mo.com.structures.ObjectType) decoder.decodeElement(new org.ccsds.moims.mo.com.structures.ObjectType());
        key = (org.ccsds.moims.mo.com.structures.ObjectKey) decoder.decodeElement(new org.ccsds.moims.mo.com.structures.ObjectKey());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
