package org.ccsds.moims.mo.com.archive.structures;

/**
 * The ArchiveDetails structure is used to hold information about a single
 * entry in an Archive.
 */
public final class ArchiveDetails implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 562958560133121L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 562958560133121L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The object instance identifier of the archived object.
     */
    private Long instId;

    /**
     * The details of the Object.
     */
    private org.ccsds.moims.mo.com.structures.ObjectDetails details;

    /**
     * The network zone of the object.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier network;

    /**
     * The time the object was created.
     */
    private org.ccsds.moims.mo.mal.structures.FineTime timestamp;

    /**
     * The component that created the object (a component may be anything from
     * an onboard equipment to a software process on the ground).
     */
    private org.ccsds.moims.mo.mal.structures.URI provider;

    /**
     * Default constructor for ArchiveDetails.
     * 
     */
    public ArchiveDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param instId The object instance identifier of the archived object.
     * @param details The details of the Object.
     * @param network The network zone of the object.
     * @param timestamp The time the object was created.
     * @param provider The component that created the object (a component may be anything from an onboard equipment to a software process on the ground).
     */
    public ArchiveDetails(Long instId,
            org.ccsds.moims.mo.com.structures.ObjectDetails details,
            org.ccsds.moims.mo.mal.structures.Identifier network,
            org.ccsds.moims.mo.mal.structures.FineTime timestamp,
            org.ccsds.moims.mo.mal.structures.URI provider) {
        this.instId = instId;
        this.details = details;
        this.network = network;
        this.timestamp = timestamp;
        this.provider = provider;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param instId The object instance identifier of the archived object.
     * @param details The details of the Object.
     */
    public ArchiveDetails(Long instId,
            org.ccsds.moims.mo.com.structures.ObjectDetails details) {
        this.instId = instId;
        this.details = details;
        this.network = null;
        this.timestamp = null;
        this.provider = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.com.archive.structures.ArchiveDetails();
    }

    /**
     * Returns the field instId.
     * 
     * @return The field instId
     */
    public Long getInstId() {
        return instId;
    }

    /**
     * Returns the field details.
     * 
     * @return The field details
     */
    public org.ccsds.moims.mo.com.structures.ObjectDetails getDetails() {
        return details;
    }

    /**
     * Returns the field network.
     * 
     * @return The field network
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getNetwork() {
        return network;
    }

    /**
     * Returns the field timestamp.
     * 
     * @return The field timestamp
     */
    public org.ccsds.moims.mo.mal.structures.FineTime getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the field provider.
     * 
     * @return The field provider
     */
    public org.ccsds.moims.mo.mal.structures.URI getProvider() {
        return provider;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ArchiveDetails) {
            ArchiveDetails other = (ArchiveDetails) obj;
            if (instId == null) {
                if (other.instId != null) {
                    return false;
                }
            } else {
                if (! instId.equals(other.instId)) {
                    return false;
                }
            }
            if (details == null) {
                if (other.details != null) {
                    return false;
                }
            } else {
                if (! details.equals(other.details)) {
                    return false;
                }
            }
            if (network == null) {
                if (other.network != null) {
                    return false;
                }
            } else {
                if (! network.equals(other.network)) {
                    return false;
                }
            }
            if (timestamp == null) {
                if (other.timestamp != null) {
                    return false;
                }
            } else {
                if (! timestamp.equals(other.timestamp)) {
                    return false;
                }
            }
            if (provider == null) {
                if (other.provider != null) {
                    return false;
                }
            } else {
                if (! provider.equals(other.provider)) {
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
        hash = 83 * hash + (instId != null ? instId.hashCode() : 0);
        hash = 83 * hash + (details != null ? details.hashCode() : 0);
        hash = 83 * hash + (network != null ? network.hashCode() : 0);
        hash = 83 * hash + (timestamp != null ? timestamp.hashCode() : 0);
        hash = 83 * hash + (provider != null ? provider.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ArchiveDetails: ");
        buf.append("instId=").append(instId);
        buf.append(", details=").append(details);
        buf.append(", network=").append(network);
        buf.append(", timestamp=").append(timestamp);
        buf.append(", provider=").append(provider);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (instId == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'instId' cannot be null!");
        }
        if (details == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'details' cannot be null!");
        }
        encoder.encodeLong(instId);
        encoder.encodeElement(details);
        encoder.encodeNullableIdentifier(network);
        encoder.encodeNullableFineTime(timestamp);
        encoder.encodeNullableURI(provider);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        instId = decoder.decodeLong();
        details = (org.ccsds.moims.mo.com.structures.ObjectDetails) decoder.decodeElement(new org.ccsds.moims.mo.com.structures.ObjectDetails());
        network = decoder.decodeNullableIdentifier();
        timestamp = decoder.decodeNullableFineTime();
        provider = decoder.decodeNullableURI();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
