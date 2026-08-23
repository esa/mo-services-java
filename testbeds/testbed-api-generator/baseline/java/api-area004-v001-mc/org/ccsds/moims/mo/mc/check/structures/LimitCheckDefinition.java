package org.ccsds.moims.mo.mc.check.structures;

/**
 * The LimitCheckDefinition defines a high and low limit check. It is valid
 * to supply only one limit; the other limit is assumed to be the relevant
 * maximum supported by the type being checked in this case.
 */
public final class LimitCheckDefinition extends org.ccsds.moims.mo.mc.check.structures.CheckDefinitionDetails {

    private static final long serialVersionUID = 1125917103489035L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125917103489035L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * If TRUE, then the safe (non violating) values lie outside the specified
     * limits range.
     */
    private Boolean violateInRange;

    /**
     * The lower limit of the value. Must be of the correct type for the entity
     * being checked.
     */
    private org.ccsds.moims.mo.mal.structures.Attribute lowerLimit;

    /**
     * The upper limit of the value. Must be of the correct type for the entity
     * being checked.
     */
    private org.ccsds.moims.mo.mal.structures.Attribute upperLimit;

    /**
     * Default constructor for LimitCheckDefinition.
     * 
     */
    public LimitCheckDefinition() {
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
     * @param violateInRange If TRUE, then the safe (non violating) values lie outside the specified limits range.
     * @param lowerLimit The lower limit of the value. Must be of the correct type for the entity being checked.
     * @param upperLimit The upper limit of the value. Must be of the correct type for the entity being checked.
     */
    public LimitCheckDefinition(String description,
            org.ccsds.moims.mo.mc.structures.Severity checkSeverity,
            org.ccsds.moims.mo.mal.structures.Duration maxReportingInterval,
            org.ccsds.moims.mo.mal.structures.UInteger nominalCount,
            org.ccsds.moims.mo.mal.structures.Duration nominalTime,
            org.ccsds.moims.mo.mal.structures.UInteger violationCount,
            org.ccsds.moims.mo.mal.structures.Duration violationTime,
            Boolean violateInRange,
            org.ccsds.moims.mo.mal.structures.Attribute lowerLimit,
            org.ccsds.moims.mo.mal.structures.Attribute upperLimit) {
        super(description,
            checkSeverity,
            maxReportingInterval,
            nominalCount,
            nominalTime,
            violationCount,
            violationTime);
        this.violateInRange = violateInRange;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
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
     * @param violateInRange If TRUE, then the safe (non violating) values lie outside the specified limits range.
     */
    public LimitCheckDefinition(String description,
            org.ccsds.moims.mo.mc.structures.Severity checkSeverity,
            org.ccsds.moims.mo.mal.structures.Duration maxReportingInterval,
            org.ccsds.moims.mo.mal.structures.UInteger nominalCount,
            org.ccsds.moims.mo.mal.structures.Duration nominalTime,
            org.ccsds.moims.mo.mal.structures.UInteger violationCount,
            org.ccsds.moims.mo.mal.structures.Duration violationTime,
            Boolean violateInRange) {
        super(description,
            checkSeverity,
            maxReportingInterval,
            nominalCount,
            nominalTime,
            violationCount,
            violationTime);
        this.violateInRange = violateInRange;
        this.lowerLimit = null;
        this.upperLimit = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.check.structures.LimitCheckDefinition();
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
     * Returns the field lowerLimit.
     * 
     * @return The field lowerLimit
     */
    public org.ccsds.moims.mo.mal.structures.Attribute getLowerLimit() {
        return lowerLimit;
    }

    /**
     * Returns the field upperLimit.
     * 
     * @return The field upperLimit
     */
    public org.ccsds.moims.mo.mal.structures.Attribute getUpperLimit() {
        return upperLimit;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LimitCheckDefinition) {
            if (! super.equals(obj)) {
                return false;
            }
            LimitCheckDefinition other = (LimitCheckDefinition) obj;
            if (violateInRange == null) {
                if (other.violateInRange != null) {
                    return false;
                }
            } else {
                if (! violateInRange.equals(other.violateInRange)) {
                    return false;
                }
            }
            if (lowerLimit == null) {
                if (other.lowerLimit != null) {
                    return false;
                }
            } else {
                if (! lowerLimit.equals(other.lowerLimit)) {
                    return false;
                }
            }
            if (upperLimit == null) {
                if (other.upperLimit != null) {
                    return false;
                }
            } else {
                if (! upperLimit.equals(other.upperLimit)) {
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
        hash = 83 * hash + (violateInRange != null ? violateInRange.hashCode() : 0);
        hash = 83 * hash + (lowerLimit != null ? lowerLimit.hashCode() : 0);
        hash = 83 * hash + (upperLimit != null ? upperLimit.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(LimitCheckDefinition: ");
        buf.append(super.toString());
        buf.append(", violateInRange=").append(violateInRange);
        buf.append(", lowerLimit=").append(lowerLimit);
        buf.append(", upperLimit=").append(upperLimit);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (violateInRange == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'violateInRange' cannot be null!");
        }
        encoder.encodeBoolean(violateInRange);
        encoder.encodeNullableAttribute(lowerLimit);
        encoder.encodeNullableAttribute(upperLimit);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        violateInRange = decoder.decodeBoolean();
        lowerLimit = (org.ccsds.moims.mo.mal.structures.Attribute) decoder.decodeNullableAttribute();
        upperLimit = (org.ccsds.moims.mo.mal.structures.Attribute) decoder.decodeNullableAttribute();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
