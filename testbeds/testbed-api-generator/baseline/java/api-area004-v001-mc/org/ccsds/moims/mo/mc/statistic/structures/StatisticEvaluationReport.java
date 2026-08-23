package org.ccsds.moims.mo.mc.statistic.structures;

/**
 * The StatisticEvaluationReport structure holds the set of statistical results.
 */
public final class StatisticEvaluationReport implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125921398456326L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125921398456326L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The statistic link object instance identifier.
     */
    private Long linkId;

    /**
     * The statistical evaluation value.
     */
    private org.ccsds.moims.mo.mc.statistic.structures.StatisticValue value;

    /**
     * Default constructor for StatisticEvaluationReport.
     * 
     */
    public StatisticEvaluationReport() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param linkId The statistic link object instance identifier.
     * @param value The statistical evaluation value.
     */
    public StatisticEvaluationReport(Long linkId,
            org.ccsds.moims.mo.mc.statistic.structures.StatisticValue value) {
        this.linkId = linkId;
        this.value = value;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.statistic.structures.StatisticEvaluationReport();
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
     * Returns the field value.
     * 
     * @return The field value
     */
    public org.ccsds.moims.mo.mc.statistic.structures.StatisticValue getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StatisticEvaluationReport) {
            StatisticEvaluationReport other = (StatisticEvaluationReport) obj;
            if (linkId == null) {
                if (other.linkId != null) {
                    return false;
                }
            } else {
                if (! linkId.equals(other.linkId)) {
                    return false;
                }
            }
            if (value == null) {
                if (other.value != null) {
                    return false;
                }
            } else {
                if (! value.equals(other.value)) {
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
        hash = 83 * hash + (linkId != null ? linkId.hashCode() : 0);
        hash = 83 * hash + (value != null ? value.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(StatisticEvaluationReport: ");
        buf.append("linkId=").append(linkId);
        buf.append(", value=").append(value);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (linkId == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'linkId' cannot be null!");
        }
        if (value == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'value' cannot be null!");
        }
        encoder.encodeLong(linkId);
        encoder.encodeElement(value);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        linkId = decoder.decodeLong();
        value = (org.ccsds.moims.mo.mc.statistic.structures.StatisticValue) decoder.decodeElement(new org.ccsds.moims.mo.mc.statistic.structures.StatisticValue());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
