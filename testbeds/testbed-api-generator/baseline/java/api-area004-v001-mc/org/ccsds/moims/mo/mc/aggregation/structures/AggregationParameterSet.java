package org.ccsds.moims.mo.mc.aggregation.structures;

/**
 * The AggregationParameterSet structure holds the identifier and optional
 * filter for a parameter, or set of parameters, in an aggregation.
 */
public final class AggregationParameterSet implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125925693423618L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125925693423618L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The domain of the parameters being referenced in this set of parameters,
     * NULL if the same domain as the aggregation.
     */
    private org.ccsds.moims.mo.mal.structures.IdentifierList domain;

    /**
     * The list of object instance identifiers of the ParameterIdentity objects
     * being included in the aggregation.
     */
    private org.ccsds.moims.mo.mal.structures.LongList parameters;

    /**
     * The interval between samples of the parameters in the set. If &quot;0&quot;
     * then just a single sample of the parameters is required per aggregation
     * report.
     */
    private org.ccsds.moims.mo.mal.structures.Duration sampleInterval;

    /**
     * If the AggregationParameterSet contains a single parameter then this field
     * contains the filter to apply for filtered reports when filters are applied.
     * NULL if no filter required or this set contains more than one parameter.
     */
    private org.ccsds.moims.mo.mc.aggregation.structures.ThresholdFilter reportFilter;

    /**
     * Default constructor for AggregationParameterSet.
     * 
     */
    public AggregationParameterSet() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param domain The domain of the parameters being referenced in this set of parameters, NULL if the same domain as the aggregation.
     * @param parameters The list of object instance identifiers of the ParameterIdentity objects being included in the aggregation.
     * @param sampleInterval The interval between samples of the parameters in the set. If '0' then just a single sample of the parameters is required per aggregation report.
     * @param reportFilter If the AggregationParameterSet contains a single parameter then this field contains the filter to apply for filtered reports when filters are applied. NULL if no filter required or this set contains more than one parameter.
     */
    public AggregationParameterSet(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.LongList parameters,
            org.ccsds.moims.mo.mal.structures.Duration sampleInterval,
            org.ccsds.moims.mo.mc.aggregation.structures.ThresholdFilter reportFilter) {
        this.domain = domain;
        this.parameters = parameters;
        this.sampleInterval = sampleInterval;
        this.reportFilter = reportFilter;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param parameters The list of object instance identifiers of the ParameterIdentity objects being included in the aggregation.
     * @param sampleInterval The interval between samples of the parameters in the set. If '0' then just a single sample of the parameters is required per aggregation report.
     */
    public AggregationParameterSet(org.ccsds.moims.mo.mal.structures.LongList parameters,
            org.ccsds.moims.mo.mal.structures.Duration sampleInterval) {
        this.domain = null;
        this.parameters = parameters;
        this.sampleInterval = sampleInterval;
        this.reportFilter = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.aggregation.structures.AggregationParameterSet();
    }

    /**
     * Returns the field domain.
     * 
     * @return The field domain
     */
    public org.ccsds.moims.mo.mal.structures.IdentifierList getDomain() {
        return domain;
    }

    /**
     * Returns the field parameters.
     * 
     * @return The field parameters
     */
    public org.ccsds.moims.mo.mal.structures.LongList getParameters() {
        return parameters;
    }

    /**
     * Returns the field sampleInterval.
     * 
     * @return The field sampleInterval
     */
    public org.ccsds.moims.mo.mal.structures.Duration getSampleInterval() {
        return sampleInterval;
    }

    /**
     * Returns the field reportFilter.
     * 
     * @return The field reportFilter
     */
    public org.ccsds.moims.mo.mc.aggregation.structures.ThresholdFilter getReportFilter() {
        return reportFilter;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AggregationParameterSet) {
            AggregationParameterSet other = (AggregationParameterSet) obj;
            if (domain == null) {
                if (other.domain != null) {
                    return false;
                }
            } else {
                if (! domain.equals(other.domain)) {
                    return false;
                }
            }
            if (parameters == null) {
                if (other.parameters != null) {
                    return false;
                }
            } else {
                if (! parameters.equals(other.parameters)) {
                    return false;
                }
            }
            if (sampleInterval == null) {
                if (other.sampleInterval != null) {
                    return false;
                }
            } else {
                if (! sampleInterval.equals(other.sampleInterval)) {
                    return false;
                }
            }
            if (reportFilter == null) {
                if (other.reportFilter != null) {
                    return false;
                }
            } else {
                if (! reportFilter.equals(other.reportFilter)) {
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
        hash = 83 * hash + (domain != null ? domain.hashCode() : 0);
        hash = 83 * hash + (parameters != null ? parameters.hashCode() : 0);
        hash = 83 * hash + (sampleInterval != null ? sampleInterval.hashCode() : 0);
        hash = 83 * hash + (reportFilter != null ? reportFilter.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(AggregationParameterSet: ");
        buf.append("domain=").append(domain);
        buf.append(", parameters=").append(parameters);
        buf.append(", sampleInterval=").append(sampleInterval);
        buf.append(", reportFilter=").append(reportFilter);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (parameters == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'parameters' cannot be null!");
        }
        if (sampleInterval == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'sampleInterval' cannot be null!");
        }
        encoder.encodeNullableElement(domain);
        encoder.encodeElement(parameters);
        encoder.encodeDuration(sampleInterval);
        encoder.encodeNullableElement(reportFilter);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        domain = (org.ccsds.moims.mo.mal.structures.IdentifierList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.IdentifierList());
        parameters = (org.ccsds.moims.mo.mal.structures.LongList) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.LongList());
        sampleInterval = decoder.decodeDuration();
        reportFilter = (org.ccsds.moims.mo.mc.aggregation.structures.ThresholdFilter) decoder.decodeNullableElement(new org.ccsds.moims.mo.mc.aggregation.structures.ThresholdFilter());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
