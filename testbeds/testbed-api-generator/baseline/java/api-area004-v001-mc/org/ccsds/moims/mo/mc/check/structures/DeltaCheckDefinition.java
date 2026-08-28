package org.ccsds.moims.mo.mc.check.structures;

/**
 * The DeltaCheckDefinition defines a delta transition check.
 */
public final class DeltaCheckDefinition extends org.ccsds.moims.mo.mc.check.structures.CheckDefinitionDetails {

    private static final long serialVersionUID = 1125917103489034L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125917103489034L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The value to compare the current value against.
     */
    private org.ccsds.moims.mo.mc.check.structures.ReferenceValue checkReference;

    /**
     * If TRUE, then the safe (non violating) values lie outside the specified
     * threshold range.
     */
    private Boolean violateInRange;

    /**
     * If TRUE, then the thresholds contain value deltas. If FALSE, they contain
     * percentage deltas.
     */
    private Boolean valueDelta;

    /**
     * The lower threshold of the delta value. Must be of the correct type for
     * the entity being checked. Must be a Float if percentage threshold in the
     * range (-1.0 to 1.0 representing +-100%).
     */
    private org.ccsds.moims.mo.mal.structures.Attribute lowerThreshold;

    /**
     * The upper threshold of the delta value. Must be of the correct type for
     * the entity being checked. Must be a Float if percentage threshold in the
     * range (-1.0 to 1.0 representing +-100%).
     */
    private org.ccsds.moims.mo.mal.structures.Attribute upperThreshold;

    /**
     * Default constructor for DeltaCheckDefinition.
     * 
     */
    public DeltaCheckDefinition() {
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
     * @param checkReference The value to compare the current value against.
     * @param violateInRange If TRUE, then the safe (non violating) values lie outside the specified threshold range.
     * @param valueDelta If TRUE, then the thresholds contain value deltas. If FALSE, they contain percentage deltas.
     * @param lowerThreshold The lower threshold of the delta value. Must be of the correct type for the entity being checked. Must be a Float if percentage threshold in the range (-1.0 to 1.0 representing +-100%).
     * @param upperThreshold The upper threshold of the delta value. Must be of the correct type for the entity being checked. Must be a Float if percentage threshold in the range (-1.0 to 1.0 representing +-100%).
     */
    public DeltaCheckDefinition(String description,
            org.ccsds.moims.mo.mc.structures.Severity checkSeverity,
            org.ccsds.moims.mo.mal.structures.Duration maxReportingInterval,
            org.ccsds.moims.mo.mal.structures.UInteger nominalCount,
            org.ccsds.moims.mo.mal.structures.Duration nominalTime,
            org.ccsds.moims.mo.mal.structures.UInteger violationCount,
            org.ccsds.moims.mo.mal.structures.Duration violationTime,
            org.ccsds.moims.mo.mc.check.structures.ReferenceValue checkReference,
            Boolean violateInRange,
            Boolean valueDelta,
            org.ccsds.moims.mo.mal.structures.Attribute lowerThreshold,
            org.ccsds.moims.mo.mal.structures.Attribute upperThreshold) {
        super(description,
            checkSeverity,
            maxReportingInterval,
            nominalCount,
            nominalTime,
            violationCount,
            violationTime);
        this.checkReference = checkReference;
        this.violateInRange = violateInRange;
        this.valueDelta = valueDelta;
        this.lowerThreshold = lowerThreshold;
        this.upperThreshold = upperThreshold;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param description The description of the check. May be empty.
     * @param checkSeverity Indicates the seriousness of the violation based on its possible negative consequences.
     * @param maxReportingInterval Maximum interval that can elapse between generations of CheckResult reports. If this value expires, then a CheckResult is generated with the same state for the previous and current state. If set to '0', then no maximum reporting interval shall be applied.
     * @param nominalCount Number of consecutive valid samples passing the check for the check to be OK.
     * @param nominalTime If nominalCount is zero then this is duration that a parameter is continuously passing the check for the check to be OK. If nominalCount is not zero then this is the period over which samples will be used in the nominalCount calculation, i.e. samples further in the past than nominalTime are not considered.
     * @param violationCount Number of consecutive valid samples violating the check for the check to be in violation.
     * @param violationTime If violationCount is zero then this is duration that a parameter is continuously violating the check for the check to be in violation. If violationCount not zero then this is the period over which samples will be used in the violationCount calculation, i.e. samples further in the past than violationTime are not considered.
     * @param checkReference The value to compare the current value against.
     * @param violateInRange If TRUE, then the safe (non violating) values lie outside the specified threshold range.
     * @param valueDelta If TRUE, then the thresholds contain value deltas. If FALSE, they contain percentage deltas.
     */
    public DeltaCheckDefinition(String description,
            org.ccsds.moims.mo.mc.structures.Severity checkSeverity,
            org.ccsds.moims.mo.mal.structures.Duration maxReportingInterval,
            org.ccsds.moims.mo.mal.structures.UInteger nominalCount,
            org.ccsds.moims.mo.mal.structures.Duration nominalTime,
            org.ccsds.moims.mo.mal.structures.UInteger violationCount,
            org.ccsds.moims.mo.mal.structures.Duration violationTime,
            org.ccsds.moims.mo.mc.check.structures.ReferenceValue checkReference,
            Boolean violateInRange,
            Boolean valueDelta) {
        super(description,
            checkSeverity,
            maxReportingInterval,
            nominalCount,
            nominalTime,
            violationCount,
            violationTime);
        this.checkReference = checkReference;
        this.violateInRange = violateInRange;
        this.valueDelta = valueDelta;
        this.lowerThreshold = null;
        this.upperThreshold = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.check.structures.DeltaCheckDefinition();
    }

    /**
     * Returns the field checkReference.
     * 
     * @return The field checkReference
     */
    public org.ccsds.moims.mo.mc.check.structures.ReferenceValue getCheckReference() {
        return checkReference;
    }

    /**
     * Returns the field violateInRange.
     * 
     * @return The field violateInRange
     */
    public Boolean getViolateInRange() {
        return violateInRange;
    }

    /**
     * Returns the field valueDelta.
     * 
     * @return The field valueDelta
     */
    public Boolean getValueDelta() {
        return valueDelta;
    }

    /**
     * Returns the field lowerThreshold.
     * 
     * @return The field lowerThreshold
     */
    public org.ccsds.moims.mo.mal.structures.Attribute getLowerThreshold() {
        return lowerThreshold;
    }

    /**
     * Returns the field upperThreshold.
     * 
     * @return The field upperThreshold
     */
    public org.ccsds.moims.mo.mal.structures.Attribute getUpperThreshold() {
        return upperThreshold;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DeltaCheckDefinition) {
            if (! super.equals(obj)) {
                return false;
            }
            DeltaCheckDefinition other = (DeltaCheckDefinition) obj;
            if (checkReference == null) {
                if (other.checkReference != null) {
                    return false;
                }
            } else {
                if (! checkReference.equals(other.checkReference)) {
                    return false;
                }
            }
            if (violateInRange == null) {
                if (other.violateInRange != null) {
                    return false;
                }
            } else {
                if (! violateInRange.equals(other.violateInRange)) {
                    return false;
                }
            }
            if (valueDelta == null) {
                if (other.valueDelta != null) {
                    return false;
                }
            } else {
                if (! valueDelta.equals(other.valueDelta)) {
                    return false;
                }
            }
            if (lowerThreshold == null) {
                if (other.lowerThreshold != null) {
                    return false;
                }
            } else {
                if (! lowerThreshold.equals(other.lowerThreshold)) {
                    return false;
                }
            }
            if (upperThreshold == null) {
                if (other.upperThreshold != null) {
                    return false;
                }
            } else {
                if (! upperThreshold.equals(other.upperThreshold)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 83 * hash + (checkReference != null ? checkReference.hashCode() : 0);
        hash = 83 * hash + (violateInRange != null ? violateInRange.hashCode() : 0);
        hash = 83 * hash + (valueDelta != null ? valueDelta.hashCode() : 0);
        hash = 83 * hash + (lowerThreshold != null ? lowerThreshold.hashCode() : 0);
        hash = 83 * hash + (upperThreshold != null ? upperThreshold.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(DeltaCheckDefinition: ");
        buf.append(super.toString());
        buf.append(", checkReference=").append(checkReference);
        buf.append(", violateInRange=").append(violateInRange);
        buf.append(", valueDelta=").append(valueDelta);
        buf.append(", lowerThreshold=").append(lowerThreshold);
        buf.append(", upperThreshold=").append(upperThreshold);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (checkReference == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'checkReference' cannot be null!");
        }
        if (violateInRange == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'violateInRange' cannot be null!");
        }
        if (valueDelta == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'valueDelta' cannot be null!");
        }
        encoder.encodeElement(checkReference);
        encoder.encodeBoolean(violateInRange);
        encoder.encodeBoolean(valueDelta);
        encoder.encodeNullableAttribute(lowerThreshold);
        encoder.encodeNullableAttribute(upperThreshold);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        checkReference = (org.ccsds.moims.mo.mc.check.structures.ReferenceValue) decoder.decodeElement(new org.ccsds.moims.mo.mc.check.structures.ReferenceValue());
        violateInRange = decoder.decodeBoolean();
        valueDelta = decoder.decodeBoolean();
        lowerThreshold = (org.ccsds.moims.mo.mal.structures.Attribute) decoder.decodeNullableAttribute();
        upperThreshold = (org.ccsds.moims.mo.mal.structures.Attribute) decoder.decodeNullableAttribute();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
