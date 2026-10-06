package org.ccsds.moims.mo.com.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * The ObjectId structure combines an object type and an object key such that
 * it identifies the instance and type of an object for a specific domain.
 */
public final class ObjectId implements Composite {

    private static final long serialVersionUID = 562949970198531L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 562949970198531L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The fully qualified unique identifier of the type.
     */
    private ObjectType type;

    /**
     * The combination of the object domain and object instance identifier.
     */
    private ObjectKey key;

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
    public ObjectId(ObjectType type,
            ObjectKey key) {
        this.type = type;
        this.key = key;
    }

    @Override
    public Element createElement() {
        return new ObjectId();
    }

    /**
     * Returns the field type.
     * 
     * @return The field type
     */
    public ObjectType getType() {
        return type;
    }

    /**
     * Returns the field key.
     * 
     * @return The field key
     */
    public ObjectKey getKey() {
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
    public void encode(MALEncoder encoder) throws MALException {
        if (type == null) {
            throw new MALException("The field 'type' cannot be null!");
        }
        if (key == null) {
            throw new MALException("The field 'key' cannot be null!");
        }
        encoder.encodeElement(type);
        encoder.encodeElement(key);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        type = (ObjectType) decoder.decodeElement(new ObjectType());
        key = (ObjectKey) decoder.decodeElement(new ObjectKey());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
