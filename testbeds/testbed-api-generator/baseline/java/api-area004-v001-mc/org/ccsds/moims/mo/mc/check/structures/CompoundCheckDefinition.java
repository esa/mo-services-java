package org.ccsds.moims.mo.mc.check.structures;

/**
 * The CompoundCheckDefinition structure holds the object instance identifiers
 * of one or more check link objects to monitor for a compound check.
 */
public final class CompoundCheckDefinition extends org.ccsds.moims.mo.mc.check.structures.CheckDefinitionDetails {

    private static final long serialVersionUID = 1125917103489036L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125917103489036L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The number of referenced checks that must be in violation for this check
     * to be considered in violation. If set to &quot;0&quot; then all referenced
     * checks must be in violation.
     */
    private org.ccsds.moims.mo.mal.structures.UInteger minimumChecksInViolation;

    /**
     * The set of CheckLink objects that form the compound check.
     */
    private org.ccsds.moims.mo.mal.structures.LongList checkLinkIds;

    /**
     * Default constructor for CompoundCheckDefinition.
     * 
     */
    public CompoundCheckDefinition() {
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
     * @param minimumChecksInViolation The number of referenced checks that must be in violation for this check to be considered in violation. If set to '0' then all referenced checks must be in violation.
     * @param checkLinkIds The set of CheckLink objects that form the compound check.
     */
    public CompoundCheckDefinition(String description,
            org.ccsds.moims.mo.mc.structures.Severity checkSeverity,
            org.ccsds.moims.mo.mal.structures.Duration maxReportingInterval,
            org.ccsds.moims.mo.mal.structures.UInteger nominalCount,
            org.ccsds.moims.mo.mal.structures.Duration nominalTime,
            org.ccsds.moims.mo.mal.structures.UInteger violationCount,
            org.ccsds.moims.mo.mal.structures.Duration violationTime,
            org.ccsds.moims.mo.mal.structures.UInteger minimumChecksInViolation,
            org.ccsds.moims.mo.mal.structures.LongList checkLinkIds) {
        super(description,
            checkSeverity,
            maxReportingInterval,
            nominalCount,
            nominalTime,
            violationCount,
            violationTime);
        this.minimumChecksInViolation = minimumChecksInViolation;
        this.checkLinkIds = checkLinkIds;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.check.structures.CompoundCheckDefinition();
    }

    /**
     * Returns the field minimumChecksInViolation.
     * 
     * @return The field minimumChecksInViolation
     */
    public org.ccsds.moims.mo.mal.structures.UInteger getMinimumChecksInViolation() {
        return minimumChecksInViolation;
    }

    /**
     * Returns the field checkLinkIds.
     * 
     * @return The field checkLinkIds
     */
    public org.ccsds.moims.mo.mal.structures.LongList getCheckLinkIds() {
        return checkLinkIds;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CompoundCheckDefinition) {
            if (! super.equals(obj)) {
                return false;
            }
            CompoundCheckDefinition other = (CompoundCheckDefinition) obj;
            if (minimumChecksInViolation == null) {
                if (other.minimumChecksInViolation != null) {
                    return false;
                }
            } else {
                if (! minimumChecksInViolation.equals(other.minimumChecksInViolation)) {
                    return false;
                }
            }
            if (checkLinkIds == null) {
                if (other.checkLinkIds != null) {
                    return false;
                }
            } else {
                if (! checkLinkIds.equals(other.checkLinkIds)) {
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
        hash = 83 * hash + (minimumChecksInViolation != null ? minimumChecksInViolation.hashCode() : 0);
        hash = 83 * hash + (checkLinkIds != null ? checkLinkIds.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(CompoundCheckDefinition: ");
        buf.append(super.toString());
        buf.append(", minimumChecksInViolation=").append(minimumChecksInViolation);
        buf.append(", checkLinkIds=").append(checkLinkIds);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (minimumChecksInViolation == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'minimumChecksInViolation' cannot be null!");
        }
        if (checkLinkIds == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'checkLinkIds' cannot be null!");
        }
        encoder.encodeUInteger(minimumChecksInViolation);
        encoder.encodeElement(checkLinkIds);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        minimumChecksInViolation = decoder.decodeUInteger();
        checkLinkIds = (org.ccsds.moims.mo.mal.structures.LongList) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.LongList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
