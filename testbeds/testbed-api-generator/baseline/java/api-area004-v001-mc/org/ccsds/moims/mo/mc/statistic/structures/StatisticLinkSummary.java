package org.ccsds.moims.mo.mc.statistic.structures;

/**
 * The StatisticLinkSummary structure holds the ids of a specific statistic
 * link and the function and parameter it links to.
 */
public final class StatisticLinkSummary implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125921398456325L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125921398456325L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The object instance identifier of the StatisticFunction object.
     */
    private Long funcId;

    /**
     * The object instance identifier of the StatisticLink object.
     */
    private Long linkId;

    /**
     * The object instance identifier of the StatisticLinkDefinition object.
     */
    private Long linkDefId;

    /**
     * TRUE if reporting of the evaluation instance is enabled.
     */
    private Boolean reportingEnabled;

    /**
     * The object instance identifier of the ParameterIdentity object for the
     * statistic link.
     */
    private org.ccsds.moims.mo.com.structures.ObjectKey parameterId;

    /**
     * Default constructor for StatisticLinkSummary.
     * 
     */
    public StatisticLinkSummary() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param funcId The object instance identifier of the StatisticFunction object.
     * @param linkId The object instance identifier of the StatisticLink object.
     * @param linkDefId The object instance identifier of the StatisticLinkDefinition object.
     * @param reportingEnabled TRUE if reporting of the evaluation instance is enabled.
     * @param parameterId The object instance identifier of the ParameterIdentity object for the statistic link.
     */
    public StatisticLinkSummary(Long funcId,
            Long linkId,
            Long linkDefId,
            Boolean reportingEnabled,
            org.ccsds.moims.mo.com.structures.ObjectKey parameterId) {
        this.funcId = funcId;
        this.linkId = linkId;
        this.linkDefId = linkDefId;
        this.reportingEnabled = reportingEnabled;
        this.parameterId = parameterId;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkSummary();
    }

    /**
     * Returns the field funcId.
     * 
     * @return The field funcId
     */
    public Long getFuncId() {
        return funcId;
    }

    /**
     * Returns the field linkId.
     * 
     * @return The field linkId
     */
    public Long getLinkId() {
        return linkId;
    }

    /**
     * Returns the field linkDefId.
     * 
     * @return The field linkDefId
     */
    public Long getLinkDefId() {
        return linkDefId;
    }

    /**
     * Returns the field reportingEnabled.
     * 
     * @return The field reportingEnabled
     */
    public Boolean getReportingEnabled() {
        return reportingEnabled;
    }

    /**
     * Returns the field parameterId.
     * 
     * @return The field parameterId
     */
    public org.ccsds.moims.mo.com.structures.ObjectKey getParameterId() {
        return parameterId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StatisticLinkSummary) {
            StatisticLinkSummary other = (StatisticLinkSummary) obj;
            if (funcId == null) {
                if (other.funcId != null) {
                    return false;
                }
            } else {
                if (! funcId.equals(other.funcId)) {
                    return false;
                }
            }
            if (linkId == null) {
                if (other.linkId != null) {
                    return false;
                }
            } else {
                if (! linkId.equals(other.linkId)) {
                    return false;
                }
            }
            if (linkDefId == null) {
                if (other.linkDefId != null) {
                    return false;
                }
            } else {
                if (! linkDefId.equals(other.linkDefId)) {
                    return false;
                }
            }
            if (reportingEnabled == null) {
                if (other.reportingEnabled != null) {
                    return false;
                }
            } else {
                if (! reportingEnabled.equals(other.reportingEnabled)) {
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
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (funcId != null ? funcId.hashCode() : 0);
        hash = 83 * hash + (linkId != null ? linkId.hashCode() : 0);
        hash = 83 * hash + (linkDefId != null ? linkDefId.hashCode() : 0);
        hash = 83 * hash + (reportingEnabled != null ? reportingEnabled.hashCode() : 0);
        hash = 83 * hash + (parameterId != null ? parameterId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(StatisticLinkSummary: ");
        buf.append("funcId=").append(funcId);
        buf.append(", linkId=").append(linkId);
        buf.append(", linkDefId=").append(linkDefId);
        buf.append(", reportingEnabled=").append(reportingEnabled);
        buf.append(", parameterId=").append(parameterId);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (funcId == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'funcId' cannot be null!");
        }
        if (linkId == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'linkId' cannot be null!");
        }
        if (linkDefId == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'linkDefId' cannot be null!");
        }
        if (reportingEnabled == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'reportingEnabled' cannot be null!");
        }
        if (parameterId == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'parameterId' cannot be null!");
        }
        encoder.encodeLong(funcId);
        encoder.encodeLong(linkId);
        encoder.encodeLong(linkDefId);
        encoder.encodeBoolean(reportingEnabled);
        encoder.encodeElement(parameterId);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        funcId = decoder.decodeLong();
        linkId = decoder.decodeLong();
        linkDefId = decoder.decodeLong();
        reportingEnabled = decoder.decodeBoolean();
        parameterId = (org.ccsds.moims.mo.com.structures.ObjectKey) decoder.decodeElement(new org.ccsds.moims.mo.com.structures.ObjectKey());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
