package org.ccsds.moims.mo.mc.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * The ReportConfiguration structure is used to retrieve the configuration
 * of the report generation of a parameter.
 */
public final class ReportConfiguration implements Composite {

    private static final long serialVersionUID = 1125899940397080L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397080L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The generationEnabled field.
     */
    private Boolean generationEnabled;

    /**
     * The reportInterval field.
     */
    private Duration reportInterval;

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
            Duration reportInterval) {
        this.generationEnabled = generationEnabled;
        this.reportInterval = reportInterval;
    }

    @Override
    public Element createElement() {
        return new ReportConfiguration();
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
    public Duration getReportInterval() {
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
    public void encode(MALEncoder encoder) throws MALException {
        if (generationEnabled == null) {
            throw new MALException("The field 'generationEnabled' cannot be null!");
        }
        if (reportInterval == null) {
            throw new MALException("The field 'reportInterval' cannot be null!");
        }
        encoder.encodeBoolean(generationEnabled);
        encoder.encodeDuration(reportInterval);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        generationEnabled = decoder.decodeBoolean();
        reportInterval = decoder.decodeDuration();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
