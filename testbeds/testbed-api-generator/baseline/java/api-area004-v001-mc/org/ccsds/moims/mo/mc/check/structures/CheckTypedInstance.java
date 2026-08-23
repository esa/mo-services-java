package org.ccsds.moims.mo.mc.check.structures;

/**
 * The CheckTypedInstance structure is used to hold the two COM object instance
 * identifiers that form the identity and the body of the check definition
 * in combination with the COM object type of the check body definition.
 */
public final class CheckTypedInstance implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125917103489037L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125917103489037L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The COM object type of the check body.
     */
    private org.ccsds.moims.mo.com.structures.ObjectType objDefCheckType;

    /**
     * The object instance identifiers.
     */
    private org.ccsds.moims.mo.mc.structures.ObjectInstancePair objInstIds;

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
    public CheckTypedInstance(org.ccsds.moims.mo.com.structures.ObjectType objDefCheckType,
            org.ccsds.moims.mo.mc.structures.ObjectInstancePair objInstIds) {
        this.objDefCheckType = objDefCheckType;
        this.objInstIds = objInstIds;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param objDefCheckType The COM object type of the check body.
     */
    public CheckTypedInstance(org.ccsds.moims.mo.com.structures.ObjectType objDefCheckType) {
        this.objDefCheckType = objDefCheckType;
        this.objInstIds = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.check.structures.CheckTypedInstance();
    }

    /**
     * Returns the field objDefCheckType.
     * 
     * @return The field objDefCheckType
     */
    public org.ccsds.moims.mo.com.structures.ObjectType getObjDefCheckType() {
        return objDefCheckType;
    }

    /**
     * Returns the field objInstIds.
     * 
     * @return The field objInstIds
     */
    public org.ccsds.moims.mo.mc.structures.ObjectInstancePair getObjInstIds() {
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
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (objDefCheckType == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'objDefCheckType' cannot be null!");
        }
        encoder.encodeElement(objDefCheckType);
        encoder.encodeNullableElement(objInstIds);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        objDefCheckType = (org.ccsds.moims.mo.com.structures.ObjectType) decoder.decodeElement(new org.ccsds.moims.mo.com.structures.ObjectType());
        objInstIds = (org.ccsds.moims.mo.mc.structures.ObjectInstancePair) decoder.decodeNullableElement(new org.ccsds.moims.mo.mc.structures.ObjectInstancePair());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
