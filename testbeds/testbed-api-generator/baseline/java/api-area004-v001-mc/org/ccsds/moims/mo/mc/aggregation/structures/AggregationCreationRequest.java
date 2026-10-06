package org.ccsds.moims.mo.mc.aggregation.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 * The AggregationCreationRequest contains all the fields required when creating
 * a new aggregation in a provider.
 */
public final class AggregationCreationRequest implements Composite {

    private static final long serialVersionUID = 1125925693423626L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125925693423626L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The name of the aggregation. Must not be empty or the wildcard value.
     */
    private Identifier name;

    /**
     * The aggregation definition details.
     */
    private AggregationDefinitionDetails aggDefDetails;

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
    public AggregationCreationRequest(Identifier name,
            AggregationDefinitionDetails aggDefDetails) {
        this.name = name;
        this.aggDefDetails = aggDefDetails;
    }

    @Override
    public Element createElement() {
        return new AggregationCreationRequest();
    }

    /**
     * Returns the field name.
     * 
     * @return The field name
     */
    public Identifier getName() {
        return name;
    }

    /**
     * Returns the field aggDefDetails.
     * 
     * @return The field aggDefDetails
     */
    public AggregationDefinitionDetails getAggDefDetails() {
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
    public void encode(MALEncoder encoder) throws MALException {
        if (name == null) {
            throw new MALException("The field 'name' cannot be null!");
        }
        if (aggDefDetails == null) {
            throw new MALException("The field 'aggDefDetails' cannot be null!");
        }
        encoder.encodeIdentifier(name);
        encoder.encodeElement(aggDefDetails);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        name = decoder.decodeIdentifier();
        aggDefDetails = (AggregationDefinitionDetails) decoder.decodeElement(new AggregationDefinitionDetails());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
