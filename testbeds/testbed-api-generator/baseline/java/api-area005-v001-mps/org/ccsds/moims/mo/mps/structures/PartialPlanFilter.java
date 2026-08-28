package org.ccsds.moims.mo.mps.structures;

/**
 * E1: PartialPlanFilter is a data structure input to the getPartialPlan operation
 * of the Plan Distribution Service that contains a reference to the source
 * Plan, and specifies the criteria used to select the partial plan.
 */
public final class PartialPlanFilter implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1407374900331013L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900331013L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Reference to the Plan of which the partial plan is a selected subset.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> sourcePlan;

    /**
     * Selection criterion based on the domain of contained ActivityInstances.
     * An ordered list representing a domain hierarchy, ‘*’ can be used to represent
     * a wildcard at that level.
     */
    private org.ccsds.moims.mo.mal.structures.IdentifierList domain;

    /**
     * Selection criterion based on the subPlan of contained ActivityInstances.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier subPlan;

    /**
     * Selection criterion based on tags associated with contained ActivityInstances.
     */
    private org.ccsds.moims.mo.mal.structures.StringList tags;

    /**
     * Selection criterion indicating the start of a range of time, position,
     * or events associated with contained ActivityInstances.  If no actual time
     * is known for a Trigger, its predicted time may be used instead to derive
     * the relevant range. .
     */
    private org.ccsds.moims.mo.mps.structures.Trigger partialPlanStart;

    /**
     * Selection criterion indicating the end of a range of time, position, or
     * events associated with contained ActivityInstances.  If no actual time
     * is known for a Trigger, its predicted time may be used instead to derive
     * the relevant range.
     */
    private org.ccsds.moims.mo.mps.structures.Trigger partialPlanEnd;

    /**
     * Default constructor for PartialPlanFilter.
     * 
     */
    public PartialPlanFilter() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param sourcePlan Reference to the Plan of which the partial plan is a selected subset.
     * @param domain Selection criterion based on the domain of contained ActivityInstances. An ordered list representing a domain hierarchy, ‘*’ can be used to represent a wildcard at that level.
     * @param subPlan Selection criterion based on the subPlan of contained ActivityInstances.
     * @param tags Selection criterion based on tags associated with contained ActivityInstances
     * @param partialPlanStart Selection criterion indicating the start of a range of time, position, or events associated with contained ActivityInstances.  If no actual time is known for a Trigger, its predicted time may be used instead to derive the relevant range. 
     * @param partialPlanEnd Selection criterion indicating the end of a range of time, position, or events associated with contained ActivityInstances.  If no actual time is known for a Trigger, its predicted time may be used instead to derive the relevant range.
     */
    public PartialPlanFilter(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> sourcePlan,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.Identifier subPlan,
            org.ccsds.moims.mo.mal.structures.StringList tags,
            org.ccsds.moims.mo.mps.structures.Trigger partialPlanStart,
            org.ccsds.moims.mo.mps.structures.Trigger partialPlanEnd) {
        this.sourcePlan = sourcePlan;
        this.domain = domain;
        this.subPlan = subPlan;
        this.tags = tags;
        this.partialPlanStart = partialPlanStart;
        this.partialPlanEnd = partialPlanEnd;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param sourcePlan Reference to the Plan of which the partial plan is a selected subset.
     */
    public PartialPlanFilter(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> sourcePlan) {
        this.sourcePlan = sourcePlan;
        this.domain = null;
        this.subPlan = null;
        this.tags = null;
        this.partialPlanStart = null;
        this.partialPlanEnd = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.PartialPlanFilter();
    }

    /**
     * Returns the field sourcePlan.
     * 
     * @return The field sourcePlan
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> getSourcePlan() {
        return sourcePlan;
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
     * Returns the field subPlan.
     * 
     * @return The field subPlan
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getSubPlan() {
        return subPlan;
    }

    /**
     * Returns the field tags.
     * 
     * @return The field tags
     */
    public org.ccsds.moims.mo.mal.structures.StringList getTags() {
        return tags;
    }

    /**
     * Returns the field partialPlanStart.
     * 
     * @return The field partialPlanStart
     */
    public org.ccsds.moims.mo.mps.structures.Trigger getPartialPlanStart() {
        return partialPlanStart;
    }

    /**
     * Returns the field partialPlanEnd.
     * 
     * @return The field partialPlanEnd
     */
    public org.ccsds.moims.mo.mps.structures.Trigger getPartialPlanEnd() {
        return partialPlanEnd;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PartialPlanFilter) {
            PartialPlanFilter other = (PartialPlanFilter) obj;
            if (sourcePlan == null) {
                if (other.sourcePlan != null) {
                    return false;
                }
            } else {
                if (! sourcePlan.equals(other.sourcePlan)) {
                    return false;
                }
            }
            if (domain == null) {
                if (other.domain != null) {
                    return false;
                }
            } else {
                if (! domain.equals(other.domain)) {
                    return false;
                }
            }
            if (subPlan == null) {
                if (other.subPlan != null) {
                    return false;
                }
            } else {
                if (! subPlan.equals(other.subPlan)) {
                    return false;
                }
            }
            if (tags == null) {
                if (other.tags != null) {
                    return false;
                }
            } else {
                if (! tags.equals(other.tags)) {
                    return false;
                }
            }
            if (partialPlanStart == null) {
                if (other.partialPlanStart != null) {
                    return false;
                }
            } else {
                if (! partialPlanStart.equals(other.partialPlanStart)) {
                    return false;
                }
            }
            if (partialPlanEnd == null) {
                if (other.partialPlanEnd != null) {
                    return false;
                }
            } else {
                if (! partialPlanEnd.equals(other.partialPlanEnd)) {
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
        hash = 83 * hash + (sourcePlan != null ? sourcePlan.hashCode() : 0);
        hash = 83 * hash + (domain != null ? domain.hashCode() : 0);
        hash = 83 * hash + (subPlan != null ? subPlan.hashCode() : 0);
        hash = 83 * hash + (tags != null ? tags.hashCode() : 0);
        hash = 83 * hash + (partialPlanStart != null ? partialPlanStart.hashCode() : 0);
        hash = 83 * hash + (partialPlanEnd != null ? partialPlanEnd.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(PartialPlanFilter: ");
        buf.append("sourcePlan=").append(sourcePlan);
        buf.append(", domain=").append(domain);
        buf.append(", subPlan=").append(subPlan);
        buf.append(", tags=").append(tags);
        buf.append(", partialPlanStart=").append(partialPlanStart);
        buf.append(", partialPlanEnd=").append(partialPlanEnd);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (sourcePlan == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'sourcePlan' cannot be null!");
        }
        encoder.encodeElement(sourcePlan);
        encoder.encodeNullableElement(domain);
        encoder.encodeNullableIdentifier(subPlan);
        encoder.encodeNullableElement(tags);
        encoder.encodeNullableAbstractElement(partialPlanStart);
        encoder.encodeNullableAbstractElement(partialPlanEnd);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        sourcePlan = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>());
        domain = (org.ccsds.moims.mo.mal.structures.IdentifierList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.IdentifierList());
        subPlan = decoder.decodeNullableIdentifier();
        tags = (org.ccsds.moims.mo.mal.structures.StringList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.StringList());
        partialPlanStart = (org.ccsds.moims.mo.mps.structures.Trigger) decoder.decodeNullableAbstractElement();
        partialPlanEnd = (org.ccsds.moims.mo.mps.structures.Trigger) decoder.decodeNullableAbstractElement();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
