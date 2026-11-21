package org.ccsds.moims.mo.mc.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * The ObjectInstancePair structure is used to hold the object instance identifier
 * of an Identity object with its associated Definition object.
 */
public final class ObjectInstancePair implements Composite {

    private static final long serialVersionUID = 1125899923619847L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899923619847L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The object instance identifier of the Identity object.
     */
    private Long objIdentityInstanceId;

    /**
     * The object instance identifier of the Definition object.
     */
    private Long objDefInstanceId;

    /**
     * Default constructor for ObjectInstancePair.
     * 
     */
    public ObjectInstancePair() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param objIdentityInstanceId The object instance identifier of the Identity object.
     * @param objDefInstanceId The object instance identifier of the Definition object.
     */
    public ObjectInstancePair(Long objIdentityInstanceId,
            Long objDefInstanceId) {
        this.objIdentityInstanceId = objIdentityInstanceId;
        this.objDefInstanceId = objDefInstanceId;
    }

    @Override
    public Element createElement() {
        return new ObjectInstancePair();
    }

    /**
     * Returns the field objIdentityInstanceId.
     * 
     * @return The field objIdentityInstanceId
     */
    public Long getObjIdentityInstanceId() {
        return objIdentityInstanceId;
    }

    /**
     * Returns the field objDefInstanceId.
     * 
     * @return The field objDefInstanceId
     */
    public Long getObjDefInstanceId() {
        return objDefInstanceId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ObjectInstancePair) {
            ObjectInstancePair other = (ObjectInstancePair) obj;
            if (objIdentityInstanceId == null) {
                if (other.objIdentityInstanceId != null) {
                    return false;
                }
            } else {
                if (! objIdentityInstanceId.equals(other.objIdentityInstanceId)) {
                    return false;
                }
            }
            if (objDefInstanceId == null) {
                if (other.objDefInstanceId != null) {
                    return false;
                }
            } else {
                if (! objDefInstanceId.equals(other.objDefInstanceId)) {
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
        hash = 83 * hash + (objIdentityInstanceId != null ? objIdentityInstanceId.hashCode() : 0);
        hash = 83 * hash + (objDefInstanceId != null ? objDefInstanceId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ObjectInstancePair: ");
        buf.append("objIdentityInstanceId=").append(objIdentityInstanceId);
        buf.append(", objDefInstanceId=").append(objDefInstanceId);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (objIdentityInstanceId == null) {
            throw new MALException("The field 'objIdentityInstanceId' cannot be null!");
        }
        if (objDefInstanceId == null) {
            throw new MALException("The field 'objDefInstanceId' cannot be null!");
        }
        encoder.encodeLong(objIdentityInstanceId);
        encoder.encodeLong(objDefInstanceId);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        objIdentityInstanceId = decoder.decodeLong();
        objDefInstanceId = decoder.decodeLong();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
