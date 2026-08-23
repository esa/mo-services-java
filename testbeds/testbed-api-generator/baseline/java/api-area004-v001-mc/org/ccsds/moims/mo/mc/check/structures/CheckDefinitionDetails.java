package org.ccsds.moims.mo.mc.check.structures;

/**
 * The CheckDefinitionDetails structure holds the definition of a check.
 */
public abstract class CheckDefinitionDetails implements org.ccsds.moims.mo.mal.structures.Composite {

    /**
     * The description of the check. May be empty.
     */
    private String description;

    /**
     * Indicates the seriousness of the violation based on its possible negative
     * consequences.
     */
    private org.ccsds.moims.mo.mc.structures.Severity checkSeverity;

    /**
     * Maximum interval that can elapse between generations of CheckResult reports.
     * If this value expires, then a CheckResult is generated with the same state
     * for the previous and current state. If set to &quot;0&quot;, then no maximum
     * reporting interval shall be applied.
     */
    private org.ccsds.moims.mo.mal.structures.Duration maxReportingInterval;

    /**
     * Number of consecutive valid samples passing the check for the check to
     * be OK.
     */
    private org.ccsds.moims.mo.mal.structures.UInteger nominalCount;

    /**
     * If nominalCount is zero then this is duration that a parameter is continuously
     * passing the check for the check to be OK. If nominalCount is not zero then
     * this is the period over which samples will be used in the nominalCount
     * calculation, i.e. samples further in the past than nominalTime are not
     * considered.
     */
    private org.ccsds.moims.mo.mal.structures.Duration nominalTime;

    /**
     * Number of consecutive valid samples violating the check for the check to
     * be in violation.
     */
    private org.ccsds.moims.mo.mal.structures.UInteger violationCount;

    /**
     * If violationCount is zero then this is duration that a parameter is continuously
     * violating the check for the check to be in violation. If violationCount
     * not zero then this is the period over which samples will be used in the
     * violationCount calculation, i.e. samples further in the past than violationTime
     * are not considered.
     */
    private org.ccsds.moims.mo.mal.structures.Duration violationTime;

    /**
     * Default constructor for CheckDefinitionDetails.
     * 
     */
    public CheckDefinitionDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param description The description of the check. May be empty.
     * @param checkSeverity Indicates the seriousness of the violation based on its possible negative consequences.
     * @param maxReportingInterval Maximum interval that can elapse between generations of CheckResult reports. If this value expires, then a CheckResult is generated with the same state for the previous and current state. If set to '0', then no maximum reporting interval shall be applied.
     * @param nominalCount Number of consecutive valid samples passing the check for the check to be OK.
     * @param nominalTime If nominalCount is zero then this is duration that a parameter is continuously passing the check for the check to be OK. If nominalCount is not zero then this is the period over which samples will be used in the nominalCount calculation, i.e. samples further in the past than nominalTime are not considered.
     * @param violationCount Number of consecutive valid samples violating the check for the check to be in violation.
     * @param violationTime If violationCount is zero then this is duration that a parameter is continuously violating the check for the check to be in violation. If violationCount not zero then this is the period over which samples will be used in the violationCount calculation, i.e. samples further in the past than violationTime are not considered.
     */
    public CheckDefinitionDetails(String description,
            org.ccsds.moims.mo.mc.structures.Severity checkSeverity,
            org.ccsds.moims.mo.mal.structures.Duration maxReportingInterval,
            org.ccsds.moims.mo.mal.structures.UInteger nominalCount,
            org.ccsds.moims.mo.mal.structures.Duration nominalTime,
            org.ccsds.moims.mo.mal.structures.UInteger violationCount,
            org.ccsds.moims.mo.mal.structures.Duration violationTime) {
        this.description = description;
        this.checkSeverity = checkSeverity;
        this.maxReportingInterval = maxReportingInterval;
        this.nominalCount = nominalCount;
        this.nominalTime = nominalTime;
        this.violationCount = violationCount;
        this.violationTime = violationTime;
    }

    /**
     * Returns the field description.
     * 
     * @return The field description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the field checkSeverity.
     * 
     * @return The field checkSeverity
     */
    public org.ccsds.moims.mo.mc.structures.Severity getCheckSeverity() {
        return checkSeverity;
    }

    /**
     * Returns the field maxReportingInterval.
     * 
     * @return The field maxReportingInterval
     */
    public org.ccsds.moims.mo.mal.structures.Duration getMaxReportingInterval() {
        return maxReportingInterval;
    }

    /**
     * Returns the field nominalCount.
     * 
     * @return The field nominalCount
     */
    public org.ccsds.moims.mo.mal.structures.UInteger getNominalCount() {
        return nominalCount;
    }

    /**
     * Returns the field nominalTime.
     * 
     * @return The field nominalTime
     */
    public org.ccsds.moims.mo.mal.structures.Duration getNominalTime() {
        return nominalTime;
    }

    /**
     * Returns the field violationCount.
     * 
     * @return The field violationCount
     */
    public org.ccsds.moims.mo.mal.structures.UInteger getViolationCount() {
        return violationCount;
    }

    /**
     * Returns the field violationTime.
     * 
     * @return The field violationTime
     */
    public org.ccsds.moims.mo.mal.structures.Duration getViolationTime() {
        return violationTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CheckDefinitionDetails) {
            CheckDefinitionDetails other = (CheckDefinitionDetails) obj;
            if (description == null) {
                if (other.description != null) {
                    return false;
                }
            } else {
                if (! description.equals(other.description)) {
                    return false;
                }
            }
            if (checkSeverity == null) {
                if (other.checkSeverity != null) {
                    return false;
                }
            } else {
                if (! checkSeverity.equals(other.checkSeverity)) {
                    return false;
                }
            }
            if (maxReportingInterval == null) {
                if (other.maxReportingInterval != null) {
                    return false;
                }
            } else {
                if (! maxReportingInterval.equals(other.maxReportingInterval)) {
                    return false;
                }
            }
            if (nominalCount == null) {
                if (other.nominalCount != null) {
                    return false;
                }
            } else {
                if (! nominalCount.equals(other.nominalCount)) {
                    return false;
                }
            }
            if (nominalTime == null) {
                if (other.nominalTime != null) {
                    return false;
                }
            } else {
                if (! nominalTime.equals(other.nominalTime)) {
                    return false;
                }
            }
            if (violationCount == null) {
                if (other.violationCount != null) {
                    return false;
                }
            } else {
                if (! violationCount.equals(other.violationCount)) {
                    return false;
                }
            }
            if (violationTime == null) {
                if (other.violationTime != null) {
                    return false;
                }
            } else {
                if (! violationTime.equals(other.violationTime)) {
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
        hash = 83 * hash + (description != null ? description.hashCode() : 0);
        hash = 83 * hash + (checkSeverity != null ? checkSeverity.hashCode() : 0);
        hash = 83 * hash + (maxReportingInterval != null ? maxReportingInterval.hashCode() : 0);
        hash = 83 * hash + (nominalCount != null ? nominalCount.hashCode() : 0);
        hash = 83 * hash + (nominalTime != null ? nominalTime.hashCode() : 0);
        hash = 83 * hash + (violationCount != null ? violationCount.hashCode() : 0);
        hash = 83 * hash + (violationTime != null ? violationTime.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(CheckDefinitionDetails: ");
        buf.append("description=").append(description);
        buf.append(", checkSeverity=").append(checkSeverity);
        buf.append(", maxReportingInterval=").append(maxReportingInterval);
        buf.append(", nominalCount=").append(nominalCount);
        buf.append(", nominalTime=").append(nominalTime);
        buf.append(", violationCount=").append(violationCount);
        buf.append(", violationTime=").append(violationTime);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (description == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'description' cannot be null!");
        }
        if (checkSeverity == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'checkSeverity' cannot be null!");
        }
        if (maxReportingInterval == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'maxReportingInterval' cannot be null!");
        }
        if (nominalCount == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'nominalCount' cannot be null!");
        }
        if (nominalTime == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'nominalTime' cannot be null!");
        }
        if (violationCount == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'violationCount' cannot be null!");
        }
        if (violationTime == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'violationTime' cannot be null!");
        }
        encoder.encodeString(description);
        encoder.encodeElement(checkSeverity);
        encoder.encodeDuration(maxReportingInterval);
        encoder.encodeUInteger(nominalCount);
        encoder.encodeDuration(nominalTime);
        encoder.encodeUInteger(violationCount);
        encoder.encodeDuration(violationTime);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        description = decoder.decodeString();
        checkSeverity = (org.ccsds.moims.mo.mc.structures.Severity) decoder.decodeElement(org.ccsds.moims.mo.mc.structures.Severity.INFORMATIONAL);
        maxReportingInterval = decoder.decodeDuration();
        nominalCount = decoder.decodeUInteger();
        nominalTime = decoder.decodeDuration();
        violationCount = decoder.decodeUInteger();
        violationTime = decoder.decodeDuration();
        return this;
    }

}
