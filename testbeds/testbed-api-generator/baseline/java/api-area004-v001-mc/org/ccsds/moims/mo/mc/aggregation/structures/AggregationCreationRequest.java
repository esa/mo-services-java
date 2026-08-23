package org.ccsds.moims.mo.mc.aggregation.structures;

/**
 * The AggregationCreationRequest contains all the fields required when creating
 * a new aggregation in a provider.
 */
public final class AggregationCreationRequest implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125925693423626L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125925693423626L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The name of the aggregation. Must not be empty or the wildcard value.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier name;

    /**
     * The aggregation definition details.
     */
    private org.ccsds.moims.mo.mc.aggregation.structures.AggregationDefinitionDetails aggDefDetails;

    /**
     * Default constructor for AggregationCreationRequest.
     * 
     */
    public AggregationCreationRequest() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param name The name of the aggregation. Must not be empty or the wildcard value.
     * @param aggDefDetails The aggregation definition details.
     */
    public AggregationCreationRequest(org.ccsds.moims.mo.mal.structures.Identifier name,
            org.ccsds.moims.mo.mc.aggregation.structures.AggregationDefinitionDetails aggDefDetails) {
        this.name = name;
        this.aggDefDetails = aggDefDetails;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.aggregation.structures.AggregationCreationRequest();
    }

    /**
     * Returns the field name.
     * 
     * @return The field name
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getName() {
        return name;
    }

    /**
     * Returns the field aggDefDetails.
     * 
     * @return The field aggDefDetails
     */
    public org.ccsds.moims.mo.mc.aggregation.structures.AggregationDefinitionDetails getAggDefDetails() {
        return aggDefDetails;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AggregationCreationRequest) {
            AggregationCreationRequest other = (AggregationCreationRequest) obj;
            if (name == null) {
                if (other.name != null) {
                    return false;
                }
            } else {
                if (! name.equals(other.name)) {
                    return false;
                }
            }
            if (aggDefDetails == null) {
                if (other.aggDefDetails != null) {
                    return false;
                }
            } else {
                if (! aggDefDetails.equals(other.aggDefDetails)) {
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
        hash = 83 * hash + (name != null ? name.hashCode() : 0);
        hash = 83 * hash + (aggDefDetails != null ? aggDefDetails.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(AggregationCreationRequest: ");
        buf.append("name=").append(name);
        buf.append(", aggDefDetails=").append(aggDefDetails);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (name == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'name' cannot be null!");
        }
        if (aggDefDetails == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'aggDefDetails' cannot be null!");
        }
        encoder.encodeIdentifier(name);
        encoder.encodeElement(aggDefDetails);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        name = decoder.decodeIdentifier();
        aggDefDetails = (org.ccsds.moims.mo.mc.aggregation.structures.AggregationDefinitionDetails) decoder.decodeElement(new org.ccsds.moims.mo.mc.aggregation.structures.AggregationDefinitionDetails());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
