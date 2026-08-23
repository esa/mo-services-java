package org.ccsds.moims.mo.mc.statistic.structures;

/**
 * The StatisticLinkDetails structure holds the sampling, reporting, and collection
 * intervals for one parameter statistic function link.
 */
public final class StatisticLinkDetails implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125921398456322L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125921398456322L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The interval between samples of the parameter.
     */
    private org.ccsds.moims.mo.mal.structures.Duration samplingInterval;

    /**
     * The interval between periodic reports being generated. If set to &quot;0&quot;,
     * then no periodic reports shall be sent.
     */
    private org.ccsds.moims.mo.mal.structures.Duration reportingInterval;

    /**
     * The collection and reset interval of the statistical evaluation for the
     * linked parameter. If set to &quot;0&quot;, then no periodic reset of the
     * evaluation shall be performed.
     */
    private org.ccsds.moims.mo.mal.structures.Duration collectionInterval;

    /**
     * If TRUE the evaluation will reset its value every collection interval.
     * If FALSE it will maintain a moving evaluation of the function for the collection
     * interval.
     */
    private Boolean resetEveryCollection;

    /**
     * TRUE if reporting of the evaluation instance is enabled.
     */
    private Boolean reportingEnabled;

    /**
     * If TRUE then use the converted value of the Parameter, else use the raw
     * value.
     */
    private Boolean useConverted;

    /**
     * Default constructor for StatisticLinkDetails.
     * 
     */
    public StatisticLinkDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param samplingInterval The interval between samples of the parameter.
     * @param reportingInterval The interval between periodic reports being generated. If set to '0', then no periodic reports shall be sent.
     * @param collectionInterval The collection and reset interval of the statistical evaluation for the linked parameter. If set to '0', then no periodic reset of the evaluation shall be performed.
     * @param resetEveryCollection If TRUE the evaluation will reset its value every collection interval. If FALSE it will maintain a moving evaluation of the function for the collection interval.
     * @param reportingEnabled TRUE if reporting of the evaluation instance is enabled.
     * @param useConverted If TRUE then use the converted value of the Parameter, else use the raw value
     */
    public StatisticLinkDetails(org.ccsds.moims.mo.mal.structures.Duration samplingInterval,
            org.ccsds.moims.mo.mal.structures.Duration reportingInterval,
            org.ccsds.moims.mo.mal.structures.Duration collectionInterval,
            Boolean resetEveryCollection,
            Boolean reportingEnabled,
            Boolean useConverted) {
        this.samplingInterval = samplingInterval;
        this.reportingInterval = reportingInterval;
        this.collectionInterval = collectionInterval;
        this.resetEveryCollection = resetEveryCollection;
        this.reportingEnabled = reportingEnabled;
        this.useConverted = useConverted;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkDetails();
    }

    /**
     * Returns the field samplingInterval.
     * 
     * @return The field samplingInterval
     */
    public org.ccsds.moims.mo.mal.structures.Duration getSamplingInterval() {
        return samplingInterval;
    }

    /**
     * Returns the field reportingInterval.
     * 
     * @return The field reportingInterval
     */
    public org.ccsds.moims.mo.mal.structures.Duration getReportingInterval() {
        return reportingInterval;
    }

    /**
     * Returns the field collectionInterval.
     * 
     * @return The field collectionInterval
     */
    public org.ccsds.moims.mo.mal.structures.Duration getCollectionInterval() {
        return collectionInterval;
    }

    /**
     * Returns the field resetEveryCollection.
     * 
     * @return The field resetEveryCollection
     */
    public Boolean getResetEveryCollection() {
        return resetEveryCollection;
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
     * Returns the field useConverted.
     * 
     * @return The field useConverted
     */
    public Boolean getUseConverted() {
        return useConverted;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StatisticLinkDetails) {
            StatisticLinkDetails other = (StatisticLinkDetails) obj;
            if (samplingInterval == null) {
                if (other.samplingInterval != null) {
                    return false;
                }
            } else {
                if (! samplingInterval.equals(other.samplingInterval)) {
                    return false;
                }
            }
            if (reportingInterval == null) {
                if (other.reportingInterval != null) {
                    return false;
                }
            } else {
                if (! reportingInterval.equals(other.reportingInterval)) {
                    return false;
                }
            }
            if (collectionInterval == null) {
                if (other.collectionInterval != null) {
                    return false;
                }
            } else {
                if (! collectionInterval.equals(other.collectionInterval)) {
                    return false;
                }
            }
            if (resetEveryCollection == null) {
                if (other.resetEveryCollection != null) {
                    return false;
                }
            } else {
                if (! resetEveryCollection.equals(other.resetEveryCollection)) {
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
            if (useConverted == null) {
                if (other.useConverted != null) {
                    return false;
                }
            } else {
                if (! useConverted.equals(other.useConverted)) {
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
        hash = 83 * hash + (samplingInterval != null ? samplingInterval.hashCode() : 0);
        hash = 83 * hash + (reportingInterval != null ? reportingInterval.hashCode() : 0);
        hash = 83 * hash + (collectionInterval != null ? collectionInterval.hashCode() : 0);
        hash = 83 * hash + (resetEveryCollection != null ? resetEveryCollection.hashCode() : 0);
        hash = 83 * hash + (reportingEnabled != null ? reportingEnabled.hashCode() : 0);
        hash = 83 * hash + (useConverted != null ? useConverted.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(StatisticLinkDetails: ");
        buf.append("samplingInterval=").append(samplingInterval);
        buf.append(", reportingInterval=").append(reportingInterval);
        buf.append(", collectionInterval=").append(collectionInterval);
        buf.append(", resetEveryCollection=").append(resetEveryCollection);
        buf.append(", reportingEnabled=").append(reportingEnabled);
        buf.append(", useConverted=").append(useConverted);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (samplingInterval == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'samplingInterval' cannot be null!");
        }
        if (reportingInterval == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'reportingInterval' cannot be null!");
        }
        if (collectionInterval == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'collectionInterval' cannot be null!");
        }
        if (resetEveryCollection == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'resetEveryCollection' cannot be null!");
        }
        if (reportingEnabled == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'reportingEnabled' cannot be null!");
        }
        if (useConverted == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'useConverted' cannot be null!");
        }
        encoder.encodeDuration(samplingInterval);
        encoder.encodeDuration(reportingInterval);
        encoder.encodeDuration(collectionInterval);
        encoder.encodeBoolean(resetEveryCollection);
        encoder.encodeBoolean(reportingEnabled);
        encoder.encodeBoolean(useConverted);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        samplingInterval = decoder.decodeDuration();
        reportingInterval = decoder.decodeDuration();
        collectionInterval = decoder.decodeDuration();
        resetEveryCollection = decoder.decodeBoolean();
        reportingEnabled = decoder.decodeBoolean();
        useConverted = decoder.decodeBoolean();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
