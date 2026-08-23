package org.ccsds.moims.mo.mc.statistic.structures;

/**
 * The StatisticCreationRequest structure holds the link details for a specific
 * parameter and function association.
 */
public final class StatisticCreationRequest implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125921398456324L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125921398456324L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The object instance identifier of the statistical function to be used.
     */
    private Long statFuncInstId;

    /**
     * The object key of the ParameterIdentity object being referenced.
     */
    private org.ccsds.moims.mo.com.structures.ObjectKey parameterId;

    /**
     * The collection, reporting, and sampling intervals.
     */
    private org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkDetails linkDetails;

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
            org.ccsds.moims.mo.com.structures.ObjectKey parameterId,
            org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkDetails linkDetails) {
        this.statFuncInstId = statFuncInstId;
        this.parameterId = parameterId;
        this.linkDetails = linkDetails;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.statistic.structures.StatisticCreationRequest();
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
    public org.ccsds.moims.mo.com.structures.ObjectKey getParameterId() {
        return parameterId;
    }

    /**
     * Returns the field linkDetails.
     * 
     * @return The field linkDetails
     */
    public org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkDetails getLinkDetails() {
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
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (statFuncInstId == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'statFuncInstId' cannot be null!");
        }
        if (parameterId == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'parameterId' cannot be null!");
        }
        if (linkDetails == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'linkDetails' cannot be null!");
        }
        encoder.encodeLong(statFuncInstId);
        encoder.encodeElement(parameterId);
        encoder.encodeElement(linkDetails);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        statFuncInstId = decoder.decodeLong();
        parameterId = (org.ccsds.moims.mo.com.structures.ObjectKey) decoder.decodeElement(new org.ccsds.moims.mo.com.structures.ObjectKey());
        linkDetails = (org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkDetails) decoder.decodeElement(new org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkDetails());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
