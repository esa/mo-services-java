package org.ccsds.moims.mo.mc.structures;

/**
 * The ReportConfiguration structure is used to retrieve the configuration
 * of the report generation of a parameter.
 */
public final class ReportConfiguration implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125899940397080L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397080L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The generationEnabled field.
     */
    private Boolean generationEnabled;

    /**
     * The reportInterval field.
     */
    private org.ccsds.moims.mo.mal.structures.Duration reportInterval;

    /**
     * Default constructor for ReportConfiguration.
     * 
     */
    public ReportConfiguration() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param generationEnabled The generationEnabled field.
     * @param reportInterval The reportInterval field.
     */
    public ReportConfiguration(Boolean generationEnabled,
            org.ccsds.moims.mo.mal.structures.Duration reportInterval) {
        this.generationEnabled = generationEnabled;
        this.reportInterval = reportInterval;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.structures.ReportConfiguration();
    }

    /**
     * Returns the field generationEnabled.
     * 
     * @return The field generationEnabled
     */
    public Boolean getGenerationEnabled() {
        return generationEnabled;
    }

    /**
     * Returns the field reportInterval.
     * 
     * @return The field reportInterval
     */
    public org.ccsds.moims.mo.mal.structures.Duration getReportInterval() {
        return reportInterval;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ReportConfiguration) {
            ReportConfiguration other = (ReportConfiguration) obj;
            if (generationEnabled == null) {
                if (other.generationEnabled != null) {
                    return false;
                }
            } else {
                if (! generationEnabled.equals(other.generationEnabled)) {
                    return false;
                }
            }
            if (reportInterval == null) {
                if (other.reportInterval != null) {
                    return false;
                }
            } else {
                if (! reportInterval.equals(other.reportInterval)) {
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
        hash = 83 * hash + (generationEnabled != null ? generationEnabled.hashCode() : 0);
        hash = 83 * hash + (reportInterval != null ? reportInterval.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ReportConfiguration: ");
        buf.append("generationEnabled=").append(generationEnabled);
        buf.append(", reportInterval=").append(reportInterval);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (generationEnabled == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'generationEnabled' cannot be null!");
        }
        if (reportInterval == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'reportInterval' cannot be null!");
        }
        encoder.encodeBoolean(generationEnabled);
        encoder.encodeDuration(reportInterval);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        generationEnabled = decoder.decodeBoolean();
        reportInterval = decoder.decodeDuration();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
