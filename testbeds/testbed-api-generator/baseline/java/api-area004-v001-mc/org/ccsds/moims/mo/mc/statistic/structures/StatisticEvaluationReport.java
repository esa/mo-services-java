package org.ccsds.moims.mo.mc.statistic.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * The StatisticEvaluationReport structure holds the set of statistical results.
 */
public final class StatisticEvaluationReport implements Composite {

    private static final long serialVersionUID = 1125921398456326L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125921398456326L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The statistic link object instance identifier.
     */
    private Long linkId;

    /**
     * The statistical evaluation value.
     */
    private StatisticValue value;

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
            StatisticValue value) {
        this.linkId = linkId;
        this.value = value;
    }

    @Override
    public Element createElement() {
        return new StatisticEvaluationReport();
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
    public StatisticValue getValue() {
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
    public void encode(MALEncoder encoder) throws MALException {
        if (linkId == null) {
            throw new MALException("The field 'linkId' cannot be null!");
        }
        if (value == null) {
            throw new MALException("The field 'value' cannot be null!");
        }
        encoder.encodeLong(linkId);
        encoder.encodeElement(value);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        linkId = decoder.decodeLong();
        value = (StatisticValue) decoder.decodeElement(new StatisticValue());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
