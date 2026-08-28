package org.ccsds.moims.mo.mps.structures;

/**
 * E1: The PlanInformation section of a plan contains administrative and validity
 * details associated with the plan as a whole.
 */
public final class PlanInformation implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1407374900330999L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330999L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Identity of the entity or system responsible for the production of the
     * plan.  The implementing planning system is responsible for defining the
     * value to be provided for this field.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier originator;

    /**
     * Date and time of production of the plan.
     */
    private org.ccsds.moims.mo.mal.structures.Time productionTime;

    /**
     * Description of the plan.
     */
    private String description;

    /**
     * Field for additional comments or notes to the operations team regarding
     * the plan.
     */
    private String comments;

    /**
     * Start of validity period for the plan. The validity period defines when
     * the plan is available for operational use.  It cannot be used outside its
     * validity period.
     */
    private org.ccsds.moims.mo.mal.structures.Time validityStart;

    /**
     * End of validity period for the plan.
     */
    private org.ccsds.moims.mo.mal.structures.Time validityEnd;

    /**
     * Start of the plan period. The plan period defines the start and end points
     * of the plan.  Planned items (planning activities and events) contained
     * within the plan must at least partially overlap the plan period.  The use
     * of the trigger structure allows this to be specified in terms of time,
     * position, pointing, or planning events.  Examples are: - a specified period
     * of time; - an orbital repeat cycle; - a period between two events.
     */
    private org.ccsds.moims.mo.mps.structures.Trigger planPeriodStart;

    /**
     * End of the plan period.
     */
    private org.ccsds.moims.mo.mps.structures.Trigger planPeriodEnd;

    /**
     * Specifies the time system used for all time fields within the Plan (see
     * 4.4.1). If Null, the default time system is used.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier timeSystem;

    /**
     * Default constructor for PlanInformation.
     * 
     */
    public PlanInformation() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param originator Identity of the entity or system responsible for the production of the plan.  The implementing planning system is responsible for defining the value to be provided for this field.
     * @param productionTime Date and time of production of the plan.
     * @param description Description of the plan.
     * @param comments Field for additional comments or notes to the operations team regarding the plan.
     * @param validityStart Start of validity period for the plan. The validity period defines when the plan is available for operational use.  It cannot be used outside its validity period.
     * @param validityEnd End of validity period for the plan.
     * @param planPeriodStart Start of the plan period. The plan period defines the start and end points of the plan.  Planned items (planning activities and events) contained within the plan must at least partially overlap the plan period.  The use of the trigger structure allows this to be specified in terms of time, position, pointing, or planning events.  Examples are: - a specified period of time; - an orbital repeat cycle; - a period between two events.
     * @param planPeriodEnd End of the plan period.
     * @param timeSystem Specifies the time system used for all time fields within the Plan (see 4.4.1). If Null, the default time system is used.
     */
    public PlanInformation(org.ccsds.moims.mo.mal.structures.Identifier originator,
            org.ccsds.moims.mo.mal.structures.Time productionTime,
            String description,
            String comments,
            org.ccsds.moims.mo.mal.structures.Time validityStart,
            org.ccsds.moims.mo.mal.structures.Time validityEnd,
            org.ccsds.moims.mo.mps.structures.Trigger planPeriodStart,
            org.ccsds.moims.mo.mps.structures.Trigger planPeriodEnd,
            org.ccsds.moims.mo.mal.structures.Identifier timeSystem) {
        this.originator = originator;
        this.productionTime = productionTime;
        this.description = description;
        this.comments = comments;
        this.validityStart = validityStart;
        this.validityEnd = validityEnd;
        this.planPeriodStart = planPeriodStart;
        this.planPeriodEnd = planPeriodEnd;
        this.timeSystem = timeSystem;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param originator Identity of the entity or system responsible for the production of the plan.  The implementing planning system is responsible for defining the value to be provided for this field.
     * @param productionTime Date and time of production of the plan.
     * @param description Description of the plan.
     * @param validityStart Start of validity period for the plan. The validity period defines when the plan is available for operational use.  It cannot be used outside its validity period.
     * @param validityEnd End of validity period for the plan.
     * @param planPeriodStart Start of the plan period. The plan period defines the start and end points of the plan.  Planned items (planning activities and events) contained within the plan must at least partially overlap the plan period.  The use of the trigger structure allows this to be specified in terms of time, position, pointing, or planning events.  Examples are: - a specified period of time; - an orbital repeat cycle; - a period between two events.
     * @param planPeriodEnd End of the plan period.
     */
    public PlanInformation(org.ccsds.moims.mo.mal.structures.Identifier originator,
            org.ccsds.moims.mo.mal.structures.Time productionTime,
            String description,
            org.ccsds.moims.mo.mal.structures.Time validityStart,
            org.ccsds.moims.mo.mal.structures.Time validityEnd,
            org.ccsds.moims.mo.mps.structures.Trigger planPeriodStart,
            org.ccsds.moims.mo.mps.structures.Trigger planPeriodEnd) {
        this.originator = originator;
        this.productionTime = productionTime;
        this.description = description;
        this.comments = null;
        this.validityStart = validityStart;
        this.validityEnd = validityEnd;
        this.planPeriodStart = planPeriodStart;
        this.planPeriodEnd = planPeriodEnd;
        this.timeSystem = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.PlanInformation();
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
     * Returns the field productionTime.
     * 
     * @return The field productionTime
     */
    public org.ccsds.moims.mo.mal.structures.Time getProductionTime() {
        return productionTime;
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
     * Returns the field comments.
     * 
     * @return The field comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * Returns the field validityStart.
     * 
     * @return The field validityStart
     */
    public org.ccsds.moims.mo.mal.structures.Time getValidityStart() {
        return validityStart;
    }

    /**
     * Returns the field validityEnd.
     * 
     * @return The field validityEnd
     */
    public org.ccsds.moims.mo.mal.structures.Time getValidityEnd() {
        return validityEnd;
    }

    /**
     * Returns the field planPeriodStart.
     * 
     * @return The field planPeriodStart
     */
    public org.ccsds.moims.mo.mps.structures.Trigger getPlanPeriodStart() {
        return planPeriodStart;
    }

    /**
     * Returns the field planPeriodEnd.
     * 
     * @return The field planPeriodEnd
     */
    public org.ccsds.moims.mo.mps.structures.Trigger getPlanPeriodEnd() {
        return planPeriodEnd;
    }

    /**
     * Returns the field timeSystem.
     * 
     * @return The field timeSystem
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getTimeSystem() {
        return timeSystem;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlanInformation) {
            PlanInformation other = (PlanInformation) obj;
            if (originator == null) {
                if (other.originator != null) {
                    return false;
                }
            } else {
                if (! originator.equals(other.originator)) {
                    return false;
                }
            }
            if (productionTime == null) {
                if (other.productionTime != null) {
                    return false;
                }
            } else {
                if (! productionTime.equals(other.productionTime)) {
                    return false;
                }
            }
            if (description == null) {
                if (other.description != null) {
                    return false;
                }
            } else {
                if (! description.equals(other.description)) {
                    return false;
                }
            }
            if (comments == null) {
                if (other.comments != null) {
                    return false;
                }
            } else {
                if (! comments.equals(other.comments)) {
                    return false;
                }
            }
            if (validityStart == null) {
                if (other.validityStart != null) {
                    return false;
                }
            } else {
                if (! validityStart.equals(other.validityStart)) {
                    return false;
                }
            }
            if (validityEnd == null) {
                if (other.validityEnd != null) {
                    return false;
                }
            } else {
                if (! validityEnd.equals(other.validityEnd)) {
                    return false;
                }
            }
            if (planPeriodStart == null) {
                if (other.planPeriodStart != null) {
                    return false;
                }
            } else {
                if (! planPeriodStart.equals(other.planPeriodStart)) {
                    return false;
                }
            }
            if (planPeriodEnd == null) {
                if (other.planPeriodEnd != null) {
                    return false;
                }
            } else {
                if (! planPeriodEnd.equals(other.planPeriodEnd)) {
                    return false;
                }
            }
            if (timeSystem == null) {
                if (other.timeSystem != null) {
                    return false;
                }
            } else {
                if (! timeSystem.equals(other.timeSystem)) {
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
        hash = 83 * hash + (originator != null ? originator.hashCode() : 0);
        hash = 83 * hash + (productionTime != null ? productionTime.hashCode() : 0);
        hash = 83 * hash + (description != null ? description.hashCode() : 0);
        hash = 83 * hash + (comments != null ? comments.hashCode() : 0);
        hash = 83 * hash + (validityStart != null ? validityStart.hashCode() : 0);
        hash = 83 * hash + (validityEnd != null ? validityEnd.hashCode() : 0);
        hash = 83 * hash + (planPeriodStart != null ? planPeriodStart.hashCode() : 0);
        hash = 83 * hash + (planPeriodEnd != null ? planPeriodEnd.hashCode() : 0);
        hash = 83 * hash + (timeSystem != null ? timeSystem.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(PlanInformation: ");
        buf.append("originator=").append(originator);
        buf.append(", productionTime=").append(productionTime);
        buf.append(", description=").append(description);
        buf.append(", comments=").append(comments);
        buf.append(", validityStart=").append(validityStart);
        buf.append(", validityEnd=").append(validityEnd);
        buf.append(", planPeriodStart=").append(planPeriodStart);
        buf.append(", planPeriodEnd=").append(planPeriodEnd);
        buf.append(", timeSystem=").append(timeSystem);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (originator == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'originator' cannot be null!");
        }
        if (productionTime == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'productionTime' cannot be null!");
        }
        if (description == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'description' cannot be null!");
        }
        if (validityStart == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'validityStart' cannot be null!");
        }
        if (validityEnd == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'validityEnd' cannot be null!");
        }
        if (planPeriodStart == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'planPeriodStart' cannot be null!");
        }
        if (planPeriodEnd == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'planPeriodEnd' cannot be null!");
        }
        encoder.encodeIdentifier(originator);
        encoder.encodeTime(productionTime);
        encoder.encodeString(description);
        encoder.encodeNullableString(comments);
        encoder.encodeTime(validityStart);
        encoder.encodeTime(validityEnd);
        encoder.encodeAbstractElement(planPeriodStart);
        encoder.encodeAbstractElement(planPeriodEnd);
        encoder.encodeNullableIdentifier(timeSystem);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        originator = decoder.decodeIdentifier();
        productionTime = decoder.decodeTime();
        description = decoder.decodeString();
        comments = decoder.decodeNullableString();
        validityStart = decoder.decodeTime();
        validityEnd = decoder.decodeTime();
        planPeriodStart = (org.ccsds.moims.mo.mps.structures.Trigger) decoder.decodeAbstractElement();
        planPeriodEnd = (org.ccsds.moims.mo.mps.structures.Trigger) decoder.decodeAbstractElement();
        timeSystem = decoder.decodeNullableIdentifier();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
