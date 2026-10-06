package org.ccsds.moims.mo.mc.statistic.structures;

import org.ccsds.moims.mo.com.structures.ObjectKey;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * The StatisticCreationRequest structure holds the link details for a specific
 * parameter and function association.
 */
public final class StatisticCreationRequest implements Composite {

    private static final long serialVersionUID = 1125921398456324L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125921398456324L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The object instance identifier of the statistical function to be used.
     */
    private Long statFuncInstId;

    /**
     * The object key of the ParameterIdentity object being referenced.
     */
    private ObjectKey parameterId;

    /**
     * The collection, reporting, and sampling intervals.
     */
    private StatisticLinkDetails linkDetails;

    /**
     * Default constructor for StatisticCreationRequest.
     * 
     */
    public StatisticCreationRequest() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param statFuncInstId The object instance identifier of the statistical function to be used.
     * @param parameterId The object key of the ParameterIdentity object being referenced.
     * @param linkDetails The collection, reporting, and sampling intervals.
     */
    public StatisticCreationRequest(Long statFuncInstId,
            ObjectKey parameterId,
            StatisticLinkDetails linkDetails) {
        this.statFuncInstId = statFuncInstId;
        this.parameterId = parameterId;
        this.linkDetails = linkDetails;
    }

    @Override
    public Element createElement() {
        return new StatisticCreationRequest();
    }

    /**
     * Returns the field statFuncInstId.
     * 
     * @return The field statFuncInstId
     */
    public Long getStatFuncInstId() {
        return statFuncInstId;
    }

    /**
     * Returns the field parameterId.
     * 
     * @return The field parameterId
     */
    public ObjectKey getParameterId() {
        return parameterId;
    }

    /**
     * Returns the field linkDetails.
     * 
     * @return The field linkDetails
     */
    public StatisticLinkDetails getLinkDetails() {
        return linkDetails;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StatisticCreationRequest) {
            StatisticCreationRequest other = (StatisticCreationRequest) obj;
            if (statFuncInstId == null) {
                if (other.statFuncInstId != null) {
                    return false;
                }
            } else {
                if (! statFuncInstId.equals(other.statFuncInstId)) {
                    return false;
                }
            }
            if (parameterId == null) {
                if (other.parameterId != null) {
                    return false;
                }
            } else {
                if (! parameterId.equals(other.parameterId)) {
                    return false;
                }
            }
            if (linkDetails == null) {
                if (other.linkDetails != null) {
                    return false;
                }
            } else {
                if (! linkDetails.equals(other.linkDetails)) {
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
        hash = 83 * hash + (statFuncInstId != null ? statFuncInstId.hashCode() : 0);
        hash = 83 * hash + (parameterId != null ? parameterId.hashCode() : 0);
        hash = 83 * hash + (linkDetails != null ? linkDetails.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(StatisticCreationRequest: ");
        buf.append("statFuncInstId=").append(statFuncInstId);
        buf.append(", parameterId=").append(parameterId);
        buf.append(", linkDetails=").append(linkDetails);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (statFuncInstId == null) {
            throw new MALException("The field 'statFuncInstId' cannot be null!");
        }
        if (parameterId == null) {
            throw new MALException("The field 'parameterId' cannot be null!");
        }
        if (linkDetails == null) {
            throw new MALException("The field 'linkDetails' cannot be null!");
        }
        encoder.encodeLong(statFuncInstId);
        encoder.encodeElement(parameterId);
        encoder.encodeElement(linkDetails);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        statFuncInstId = decoder.decodeLong();
        parameterId = (ObjectKey) decoder.decodeElement(new ObjectKey());
        linkDetails = (StatisticLinkDetails) decoder.decodeElement(new StatisticLinkDetails());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
