package org.ccsds.moims.mo.mc.check.structures;

import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mc.structures.ObjectInstancePair;

/**
 * The CheckTypedInstance structure is used to hold the two COM object instance
 * identifiers that form the identity and the body of the check definition
 * in combination with the COM object type of the check body definition.
 */
public final class CheckTypedInstance implements Composite {

    private static final long serialVersionUID = 1125917103489037L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125917103489037L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The COM object type of the check body.
     */
    private ObjectType objDefCheckType;

    /**
     * The object instance identifiers.
     */
    private ObjectInstancePair objInstIds;

    /**
     * Default constructor for CheckTypedInstance.
     * 
     */
    public CheckTypedInstance() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param objDefCheckType The COM object type of the check body.
     * @param objInstIds The object instance identifiers.
     */
    public CheckTypedInstance(ObjectType objDefCheckType,
            ObjectInstancePair objInstIds) {
        this.objDefCheckType = objDefCheckType;
        this.objInstIds = objInstIds;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param objDefCheckType The COM object type of the check body.
     */
    public CheckTypedInstance(ObjectType objDefCheckType) {
        this.objDefCheckType = objDefCheckType;
        this.objInstIds = null;
    }

    @Override
    public Element createElement() {
        return new CheckTypedInstance();
    }

    /**
     * Returns the field objDefCheckType.
     * 
     * @return The field objDefCheckType
     */
    public ObjectType getObjDefCheckType() {
        return objDefCheckType;
    }

    /**
     * Returns the field objInstIds.
     * 
     * @return The field objInstIds
     */
    public ObjectInstancePair getObjInstIds() {
        return objInstIds;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CheckTypedInstance) {
            CheckTypedInstance other = (CheckTypedInstance) obj;
            if (objDefCheckType == null) {
                if (other.objDefCheckType != null) {
                    return false;
                }
            } else {
                if (! objDefCheckType.equals(other.objDefCheckType)) {
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
        hash = 83 * hash + (objDefCheckType != null ? objDefCheckType.hashCode() : 0);
        hash = 83 * hash + (objInstIds != null ? objInstIds.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(CheckTypedInstance: ");
        buf.append("objDefCheckType=").append(objDefCheckType);
        buf.append(", objInstIds=").append(objInstIds);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (objDefCheckType == null) {
            throw new MALException("The field 'objDefCheckType' cannot be null!");
        }
        encoder.encodeElement(objDefCheckType);
        encoder.encodeNullableElement(objInstIds);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        objDefCheckType = (ObjectType) decoder.decodeElement(new ObjectType());
        objInstIds = (ObjectInstancePair) decoder.decodeNullableElement(new ObjectInstancePair());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
