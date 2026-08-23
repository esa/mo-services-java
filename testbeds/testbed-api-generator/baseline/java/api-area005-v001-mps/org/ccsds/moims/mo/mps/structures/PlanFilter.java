package org.ccsds.moims.mo.mps.structures;

/**
 * E1: PlanFilter is a data structure used in the context of MPS Plan Distribution
 * Service operations to specify a filtered set of Plans.  .
 */
public final class PlanFilter implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1407374900331012L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900331012L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Domain of the Plan. An ordered list representing a domain hierarchy, ‘*’
     * can be used to represent a wildcard at that level.
     */
    private org.ccsds.moims.mo.mal.structures.IdentifierList domain;

    /**
     * Reference to the Plan.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planID;

    /**
     * Reference to the precursor Plan of the Plan.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> precursorPlan;

    /**
     * Current status (enum) of the Plan.
     */
    private org.ccsds.moims.mo.mps.structures.PlanStatusEnum status;

    /**
     * Originator of the Plan.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier originator;

    /**
     * Period of time with which the validity period of the Plan overlaps.
     */
    private org.ccsds.moims.mo.mps.structures.TimeWindow validityPeriod;

    /**
     * Default constructor for PlanFilter.
     * 
     */
    public PlanFilter() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param domain Domain of the Plan. An ordered list representing a domain hierarchy, ‘*’ can be used to represent a wildcard at that level.
     * @param planID Reference to the Plan.
     * @param precursorPlan Reference to the precursor Plan of the Plan.
     * @param status Current status (enum) of the Plan.
     * @param originator Originator of the Plan.
     * @param validityPeriod Period of time with which the validity period of the Plan overlaps.
     */
    public PlanFilter(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planID,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> precursorPlan,
            org.ccsds.moims.mo.mps.structures.PlanStatusEnum status,
            org.ccsds.moims.mo.mal.structures.Identifier originator,
            org.ccsds.moims.mo.mps.structures.TimeWindow validityPeriod) {
        this.domain = domain;
        this.planID = planID;
        this.precursorPlan = precursorPlan;
        this.status = status;
        this.originator = originator;
        this.validityPeriod = validityPeriod;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.PlanFilter();
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
     * Returns the field planID.
     * 
     * @return The field planID
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> getPlanID() {
        return planID;
    }

    /**
     * Returns the field precursorPlan.
     * 
     * @return The field precursorPlan
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> getPrecursorPlan() {
        return precursorPlan;
    }

    /**
     * Returns the field status.
     * 
     * @return The field status
     */
    public org.ccsds.moims.mo.mps.structures.PlanStatusEnum getStatus() {
        return status;
    }

    /**
     * Returns the field originator.
     * 
     * @return The field originator
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getOriginator() {
        return originator;
    }

    /**
     * Returns the field validityPeriod.
     * 
     * @return The field validityPeriod
     */
    public org.ccsds.moims.mo.mps.structures.TimeWindow getValidityPeriod() {
        return validityPeriod;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlanFilter) {
            PlanFilter other = (PlanFilter) obj;
            if (domain == null) {
                if (other.domain != null) {
                    return false;
                }
            } else {
                if (! domain.equals(other.domain)) {
                    return false;
                }
            }
            if (planID == null) {
                if (other.planID != null) {
                    return false;
                }
            } else {
                if (! planID.equals(other.planID)) {
                    return false;
                }
            }
            if (precursorPlan == null) {
                if (other.precursorPlan != null) {
                    return false;
                }
            } else {
                if (! precursorPlan.equals(other.precursorPlan)) {
                    return false;
                }
            }
            if (status == null) {
                if (other.status != null) {
                    return false;
                }
            } else {
                if (! status.equals(other.status)) {
                    return false;
                }
            }
            if (originator == null) {
                if (other.originator != null) {
                    return false;
                }
            } else {
                if (! originator.equals(other.originator)) {
                    return false;
                }
            }
            if (validityPeriod == null) {
                if (other.validityPeriod != null) {
                    return false;
                }
            } else {
                if (! validityPeriod.equals(other.validityPeriod)) {
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
        hash = 83 * hash + (planID != null ? planID.hashCode() : 0);
        hash = 83 * hash + (precursorPlan != null ? precursorPlan.hashCode() : 0);
        hash = 83 * hash + (status != null ? status.hashCode() : 0);
        hash = 83 * hash + (originator != null ? originator.hashCode() : 0);
        hash = 83 * hash + (validityPeriod != null ? validityPeriod.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(PlanFilter: ");
        buf.append("domain=").append(domain);
        buf.append(", planID=").append(planID);
        buf.append(", precursorPlan=").append(precursorPlan);
        buf.append(", status=").append(status);
        buf.append(", originator=").append(originator);
        buf.append(", validityPeriod=").append(validityPeriod);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        encoder.encodeNullableElement(domain);
        encoder.encodeNullableElement(planID);
        encoder.encodeNullableElement(precursorPlan);
        encoder.encodeNullableElement(status);
        encoder.encodeNullableIdentifier(originator);
        encoder.encodeNullableElement(validityPeriod);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        domain = (org.ccsds.moims.mo.mal.structures.IdentifierList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.IdentifierList());
        planID = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>());
        precursorPlan = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>());
        status = (org.ccsds.moims.mo.mps.structures.PlanStatusEnum) decoder.decodeNullableElement(org.ccsds.moims.mo.mps.structures.PlanStatusEnum.DRAFT);
        originator = decoder.decodeNullableIdentifier();
        validityPeriod = (org.ccsds.moims.mo.mps.structures.TimeWindow) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.TimeWindow());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
