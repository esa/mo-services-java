package org.ccsds.moims.mo.malprototype.structures;

/**
 * This data structure specifies how the IPTest provider shall register.
 */
public final class TestPublishRegister extends org.ccsds.moims.mo.malprototype.structures.TestPublish {

    private static final long serialVersionUID = 28147497687842823L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687842823L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The names of the keys to be used by the broker.
     */
    private org.ccsds.moims.mo.mal.structures.IdentifierList keyNames;

    /**
     * The types of the keys to be used by the broker.
     */
    private org.ccsds.moims.mo.mal.structures.AttributeTypeList keyTypes;

    /**
     * The code of the Publish Register error expected to be received.-1 if no
     * Publish Register error is expected.
     */
    private org.ccsds.moims.mo.mal.structures.UInteger errorCode;

    /**
     * Default constructor for TestPublishRegister.
     * 
     */
    public TestPublishRegister() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param Qos The QoS level to be used by the provider.
     * @param Priority The priority to be used by the provider.
     * @param domain The domain to be used by the provider.
     * @param networkZone The network zone to be used by the provider.
     * @param Session The session type to be used by the provider.
     * @param sessionName The session name to be used by the provider.
     * @param testMultiType Whether to use the multi type version of the PubSub operation.
     * @param keyNames The names of the keys to be used by the broker.
     * @param keyTypes The types of the keys to be used by the broker.
     * @param errorCode The code of the Publish Register error expected to be received.-1 if no Publish Register error is expected.
     */
    public TestPublishRegister(org.ccsds.moims.mo.mal.structures.QoSLevel Qos,
            org.ccsds.moims.mo.mal.structures.UInteger Priority,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.Identifier networkZone,
            org.ccsds.moims.mo.mal.structures.SessionType Session,
            org.ccsds.moims.mo.mal.structures.Identifier sessionName,
            Boolean testMultiType,
            org.ccsds.moims.mo.mal.structures.IdentifierList keyNames,
            org.ccsds.moims.mo.mal.structures.AttributeTypeList keyTypes,
            org.ccsds.moims.mo.mal.structures.UInteger errorCode) {
        super(Qos,
            Priority,
            domain,
            networkZone,
            Session,
            sessionName,
            testMultiType);
        this.keyNames = keyNames;
        this.keyTypes = keyTypes;
        this.errorCode = errorCode;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.malprototype.structures.TestPublishRegister();
    }

    /**
     * Returns the field keyNames.
     * 
     * @return The field keyNames
     */
    public org.ccsds.moims.mo.mal.structures.IdentifierList getKeyNames() {
        return keyNames;
    }

    /**
     * Returns the field keyTypes.
     * 
     * @return The field keyTypes
     */
    public org.ccsds.moims.mo.mal.structures.AttributeTypeList getKeyTypes() {
        return keyTypes;
    }

    /**
     * Returns the field errorCode.
     * 
     * @return The field errorCode
     */
    public org.ccsds.moims.mo.mal.structures.UInteger getErrorCode() {
        return errorCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TestPublishRegister) {
            if (! super.equals(obj)) {
                return false;
            }
            TestPublishRegister other = (TestPublishRegister) obj;
            if (keyNames == null) {
                if (other.keyNames != null) {
                    return false;
                }
            } else {
                if (! keyNames.equals(other.keyNames)) {
                    return false;
                }
            }
            if (keyTypes == null) {
                if (other.keyTypes != null) {
                    return false;
                }
            } else {
                if (! keyTypes.equals(other.keyTypes)) {
                    return false;
                }
            }
            if (errorCode == null) {
                if (other.errorCode != null) {
                    return false;
                }
            } else {
                if (! errorCode.equals(other.errorCode)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 83 * hash + (keyNames != null ? keyNames.hashCode() : 0);
        hash = 83 * hash + (keyTypes != null ? keyTypes.hashCode() : 0);
        hash = 83 * hash + (errorCode != null ? errorCode.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(TestPublishRegister: ");
        buf.append(super.toString());
        buf.append(", keyNames=").append(keyNames);
        buf.append(", keyTypes=").append(keyTypes);
        buf.append(", errorCode=").append(errorCode);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        encoder.encodeNullableElement(keyNames);
        encoder.encodeNullableElement(keyTypes);
        encoder.encodeNullableUInteger(errorCode);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        keyNames = (org.ccsds.moims.mo.mal.structures.IdentifierList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.IdentifierList());
        keyTypes = (org.ccsds.moims.mo.mal.structures.AttributeTypeList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.AttributeTypeList());
        errorCode = decoder.decodeNullableUInteger();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
