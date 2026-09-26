package org.ccsds.moims.mo.malprototype.structures;

/**
 * The InteractionKey structure.
 */
public final class InteractionKey implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 28147497687842827L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687842827L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The consumer&quot;s URI.
     */
    private org.ccsds.moims.mo.mal.structures.URI URIfrom;

    /**
     * The transaction identifier of the interaction.
     */
    private Integer transactionId;

    /**
     * The type of the interaction.
     */
    private org.ccsds.moims.mo.mal.structures.InteractionType interactionType;

    /**
     * The name of the called service.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier Service;

    /**
     * The name of the called operation.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier operation;

    /**
     * Default constructor for InteractionKey.
     * 
     */
    public InteractionKey() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param URIfrom The consumer's URI
     * @param transactionId The transaction identifier of the interaction
     * @param interactionType The type of the interaction
     * @param Service The name of the called service
     * @param operation The name of the called operation
     */
    public InteractionKey(org.ccsds.moims.mo.mal.structures.URI URIfrom,
            Integer transactionId,
            org.ccsds.moims.mo.mal.structures.InteractionType interactionType,
            org.ccsds.moims.mo.mal.structures.Identifier Service,
            org.ccsds.moims.mo.mal.structures.Identifier operation) {
        this.URIfrom = URIfrom;
        this.transactionId = transactionId;
        this.interactionType = interactionType;
        this.Service = Service;
        this.operation = operation;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.malprototype.structures.InteractionKey();
    }

    /**
     * Returns the field URIfrom.
     * 
     * @return The field URIfrom
     */
    public org.ccsds.moims.mo.mal.structures.URI getURIfrom() {
        return URIfrom;
    }

    /**
     * Returns the field transactionId.
     * 
     * @return The field transactionId
     */
    public Integer getTransactionId() {
        return transactionId;
    }

    /**
     * Returns the field interactionType.
     * 
     * @return The field interactionType
     */
    public org.ccsds.moims.mo.mal.structures.InteractionType getInteractionType() {
        return interactionType;
    }

    /**
     * Returns the field Service.
     * 
     * @return The field Service
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getService() {
        return Service;
    }

    /**
     * Returns the field operation.
     * 
     * @return The field operation
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getOperation() {
        return operation;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof InteractionKey) {
            InteractionKey other = (InteractionKey) obj;
            if (URIfrom == null) {
                if (other.URIfrom != null) {
                    return false;
                }
            } else {
                if (! URIfrom.equals(other.URIfrom)) {
                    return false;
                }
            }
            if (transactionId == null) {
                if (other.transactionId != null) {
                    return false;
                }
            } else {
                if (! transactionId.equals(other.transactionId)) {
                    return false;
                }
            }
            if (interactionType == null) {
                if (other.interactionType != null) {
                    return false;
                }
            } else {
                if (! interactionType.equals(other.interactionType)) {
                    return false;
                }
            }
            if (Service == null) {
                if (other.Service != null) {
                    return false;
                }
            } else {
                if (! Service.equals(other.Service)) {
                    return false;
                }
            }
            if (operation == null) {
                if (other.operation != null) {
                    return false;
                }
            } else {
                if (! operation.equals(other.operation)) {
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
        hash = 83 * hash + (URIfrom != null ? URIfrom.hashCode() : 0);
        hash = 83 * hash + (transactionId != null ? transactionId.hashCode() : 0);
        hash = 83 * hash + (interactionType != null ? interactionType.hashCode() : 0);
        hash = 83 * hash + (Service != null ? Service.hashCode() : 0);
        hash = 83 * hash + (operation != null ? operation.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(InteractionKey: ");
        buf.append("URIfrom=").append(URIfrom);
        buf.append(", transactionId=").append(transactionId);
        buf.append(", interactionType=").append(interactionType);
        buf.append(", Service=").append(Service);
        buf.append(", operation=").append(operation);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        encoder.encodeNullableURI(URIfrom);
        encoder.encodeNullableInteger(transactionId);
        encoder.encodeNullableElement(interactionType);
        encoder.encodeNullableIdentifier(Service);
        encoder.encodeNullableIdentifier(operation);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        URIfrom = decoder.decodeNullableURI();
        transactionId = decoder.decodeNullableInteger();
        interactionType = (org.ccsds.moims.mo.mal.structures.InteractionType) decoder.decodeNullableElement(org.ccsds.moims.mo.mal.structures.InteractionType.SEND);
        Service = decoder.decodeNullableIdentifier();
        operation = decoder.decodeNullableIdentifier();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
