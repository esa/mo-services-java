package org.ccsds.moims.mo.common.directory.structures;

import org.ccsds.moims.mo.com.structures.ObjectKey;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 * The ProviderSummary structure holds information about a provider of a service
 * and its capabilities.
 */
public final class ProviderSummary implements Composite {

    private static final long serialVersionUID = 844429241876485L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 844429241876485L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The COM object key of this service provider.
     */
    private ObjectKey providerKey;

    /**
     * The id of this service provider.
     */
    private Identifier providerId;

    /**
     * The service capabilities supported by this provider.
     */
    private ProviderDetails providerDetails;

    /**
     * Default constructor for ProviderSummary.
     * 
     */
    public ProviderSummary() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param providerKey The COM object key of this service provider
     * @param providerId The id of this service provider.
     * @param providerDetails The service capabilities supported by this provider
     */
    public ProviderSummary(ObjectKey providerKey,
            Identifier providerId,
            ProviderDetails providerDetails) {
        this.providerKey = providerKey;
        this.providerId = providerId;
        this.providerDetails = providerDetails;
    }

    @Override
    public Element createElement() {
        return new ProviderSummary();
    }

    /**
     * Returns the field providerKey.
     * 
     * @return The field providerKey
     */
    public ObjectKey getProviderKey() {
        return providerKey;
    }

    /**
     * Returns the field providerId.
     * 
     * @return The field providerId
     */
    public Identifier getProviderId() {
        return providerId;
    }

    /**
     * Returns the field providerDetails.
     * 
     * @return The field providerDetails
     */
    public ProviderDetails getProviderDetails() {
        return providerDetails;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ProviderSummary) {
            ProviderSummary other = (ProviderSummary) obj;
            if (providerKey == null) {
                if (other.providerKey != null) {
                    return false;
                }
            } else {
                if (! providerKey.equals(other.providerKey)) {
                    return false;
                }
            }
            if (providerId == null) {
                if (other.providerId != null) {
                    return false;
                }
            } else {
                if (! providerId.equals(other.providerId)) {
                    return false;
                }
            }
            if (providerDetails == null) {
                if (other.providerDetails != null) {
                    return false;
                }
            } else {
                if (! providerDetails.equals(other.providerDetails)) {
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
        hash = 83 * hash + (providerKey != null ? providerKey.hashCode() : 0);
        hash = 83 * hash + (providerId != null ? providerId.hashCode() : 0);
        hash = 83 * hash + (providerDetails != null ? providerDetails.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ProviderSummary: ");
        buf.append("providerKey=").append(providerKey);
        buf.append(", providerId=").append(providerId);
        buf.append(", providerDetails=").append(providerDetails);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (providerKey == null) {
            throw new MALException("The field 'providerKey' cannot be null!");
        }
        if (providerId == null) {
            throw new MALException("The field 'providerId' cannot be null!");
        }
        if (providerDetails == null) {
            throw new MALException("The field 'providerDetails' cannot be null!");
        }
        encoder.encodeElement(providerKey);
        encoder.encodeIdentifier(providerId);
        encoder.encodeElement(providerDetails);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        providerKey = (ObjectKey) decoder.decodeElement(new ObjectKey());
        providerId = decoder.decodeIdentifier();
        providerDetails = (ProviderDetails) decoder.decodeElement(new ProviderDetails());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
