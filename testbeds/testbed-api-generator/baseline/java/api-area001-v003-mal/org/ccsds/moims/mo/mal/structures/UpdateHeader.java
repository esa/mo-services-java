package org.ccsds.moims.mo.mal.structures;

/**
 * The UpdateHeader structure shall be used by updates using the PUBSUB Interaction
 * Pattern. It shall hold information that identifies a single update.
 */
public final class UpdateHeader implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 281475027043307L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475027043307L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The source of the update, usually a PUBSUB provider.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier source;

    /**
     * The domain of this update. The individual domain identifier parts shall
     * not be set as the wildcard character ‘*’.
     */
    private org.ccsds.moims.mo.mal.structures.IdentifierList domain;

    /**
     * The values for the PUBSUB keys. The values shall be ordered according to
     * the defined keys if the consumer subscription did not enable trimming.
     */
    private org.ccsds.moims.mo.mal.structures.NullableAttributeList keyValues;

    /**
     * Default constructor for UpdateHeader.
     * 
     */
    public UpdateHeader() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param source The source of the update, usually a PUBSUB provider.
     * @param domain The domain of this update. The individual domain identifier parts shall not be set as the wildcard character ‘*’.
     * @param keyValues The values for the PUBSUB keys. The values shall be ordered according to the defined keys if the consumer subscription did not enable trimming.
     */
    public UpdateHeader(org.ccsds.moims.mo.mal.structures.Identifier source,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.NullableAttributeList keyValues) {
        this.source = source;
        this.domain = domain;
        this.keyValues = keyValues;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mal.structures.UpdateHeader();
    }

    /**
     * Returns the field source.
     * 
     * @return The field source
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getSource() {
        return source;
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
     * Returns the field keyValues.
     * 
     * @return The field keyValues
     */
    public org.ccsds.moims.mo.mal.structures.NullableAttributeList getKeyValues() {
        return keyValues;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UpdateHeader) {
            UpdateHeader other = (UpdateHeader) obj;
            if (source == null) {
                if (other.source != null) {
                    return false;
                }
            } else {
                if (! source.equals(other.source)) {
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
            if (keyValues == null) {
                if (other.keyValues != null) {
                    return false;
                }
            } else {
                if (! keyValues.equals(other.keyValues)) {
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
        hash = 83 * hash + (source != null ? source.hashCode() : 0);
        hash = 83 * hash + (domain != null ? domain.hashCode() : 0);
        hash = 83 * hash + (keyValues != null ? keyValues.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(UpdateHeader: ");
        buf.append("source=").append(source);
        buf.append(", domain=").append(domain);
        buf.append(", keyValues=").append(keyValues);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        encoder.encodeNullableIdentifier(source);
        encoder.encodeNullableElement(domain);
        encoder.encodeNullableElement(keyValues);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        source = decoder.decodeNullableIdentifier();
        domain = (org.ccsds.moims.mo.mal.structures.IdentifierList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.IdentifierList());
        keyValues = (org.ccsds.moims.mo.mal.structures.NullableAttributeList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.NullableAttributeList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
