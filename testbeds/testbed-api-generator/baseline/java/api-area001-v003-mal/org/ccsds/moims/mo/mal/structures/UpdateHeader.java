package org.ccsds.moims.mo.mal.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;

/**
 * The UpdateHeader structure shall be used by updates using the PUBSUB Interaction
 * Pattern. It shall hold information that identifies a single update.
 */
public final class UpdateHeader implements Composite {

    private static final long serialVersionUID = 281475027043307L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475027043307L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The source of the update, usually a PUBSUB provider.
     */
    private Identifier source;

    /**
     * The domain of this update. The individual domain identifier parts shall
     * not be set as the wildcard character ‘*’.
     */
    private IdentifierList domain;

    /**
     * The values for the PUBSUB keys. The values shall be ordered according to
     * the defined keys if the consumer subscription did not enable trimming.
     */
    private NullableAttributeList keyValues;

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
    public UpdateHeader(Identifier source,
            IdentifierList domain,
            NullableAttributeList keyValues) {
        this.source = source;
        this.domain = domain;
        this.keyValues = keyValues;
    }

    @Override
    public Element createElement() {
        return new UpdateHeader();
    }

    /**
     * Returns the field source.
     * 
     * @return The field source
     */
    public Identifier getSource() {
        return source;
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
     * Returns the field keyValues.
     * 
     * @return The field keyValues
     */
    public NullableAttributeList getKeyValues() {
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
    public void encode(MALEncoder encoder) throws MALException {
        encoder.encodeNullableIdentifier(source);
        encoder.encodeNullableElement(domain);
        encoder.encodeNullableElement(keyValues);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        source = decoder.decodeNullableIdentifier();
        domain = (IdentifierList) decoder.decodeNullableElement(new IdentifierList());
        keyValues = (NullableAttributeList) decoder.decodeNullableElement(new NullableAttributeList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
