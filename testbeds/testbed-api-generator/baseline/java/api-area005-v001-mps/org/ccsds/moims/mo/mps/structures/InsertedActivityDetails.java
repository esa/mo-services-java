package org.ccsds.moims.mo.mps.structures;

/**
 * E1: A concrete sub-type of ActivityDetails (4.5.2.3.1) that is a variation
 * of SimpleActivityDetails providing additional details for a single ActivityInstance
 * to be inserted into a Plan using the MPS Plan Edit service.
 */
public final class InsertedActivityDetails extends org.ccsds.moims.mo.mps.structures.ActivityDetails {

    private static final long serialVersionUID = 1407374900330603L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330603L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Reference to the Plan into which the ActivityInstance is to be inserted.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> plan;

    /**
     * Optionally specifies the trigger that initiates the ActivityInstance: may
     * be time, position, or event based.
     */
    private org.ccsds.moims.mo.mps.structures.Trigger start;

    /**
     * Optionally specifies the trigger that ends the ActivityInstance.
     */
    private org.ccsds.moims.mo.mps.structures.Trigger end;

    /**
     * Reference to the ActivityDefinition.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityDefinition> activityDefinition;

    /**
     * Set of argument specifications for each argument definition contained in
     * the referenced activity definition.  These supply a value for each argument,
     * or an expression to enable the value to be derived.
     */
    private org.ccsds.moims.mo.mps.structures.ArgSpecList argSpecs;

    /**
     * The User ID for the person or organization inserting the activity into
     * the Plan.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.PlanningUser> user;

    /**
     * A single constraint or a constraint node that may contain multiple constraints,
     * specific to the ActivityInstance to be created.
     */
    private org.ccsds.moims.mo.mps.structures.Constraint constraints;

    /**
     * Set of Effects specific to the ActivityInstance to be created.
     */
    private org.ccsds.moims.mo.mps.structures.EffectList effects;

    /**
     * Optional association of the ActivityInstance with a defined sub-plan.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier subPlan;

    /**
     * Set of tags that may be used to associate the Activity with a subset of
     * the Plan, grouping activities by operational responsibility (controller/group/system)
     * or other criteria.
     */
    private org.ccsds.moims.mo.mal.structures.StringList tags;

    /**
     * Default constructor for InsertedActivityDetails.
     * 
     */
    public InsertedActivityDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param activityRef Specifies how the ActivityInstance is placed with respect to any defined Repetition (0=Start; 1=End). Default is Start.
     * @param activityOffset Specifies an offset in time for the ActivityInstance from any defined Repetition. Default is no offset.
     * @param relatedEvent Specifies a related Event (or Event Group) for the ActivityInstance.  Argument specifications and constraints may reference arguments and fields of the RelatedEvent.
     * @param comments Any notes associated with the ActivityDetails.
     * @param plan Reference to the Plan into which the ActivityInstance is to be inserted.
     * @param start Optionally specifies the trigger that initiates the ActivityInstance: may be time, position, or event based.
     * @param end Optionally specifies the trigger that ends the ActivityInstance.
     * @param activityDefinition Reference to the ActivityDefinition.
     * @param argSpecs Set of argument specifications for each argument definition contained in the referenced activity definition.  These supply a value for each argument, or an expression to enable the value to be derived.
     * @param user The User ID for the person or organization inserting the activity into the Plan.
     * @param constraints A single constraint or a constraint node that may contain multiple constraints, specific to the ActivityInstance to be created.
     * @param effects Set of Effects specific to the ActivityInstance to be created.
     * @param subPlan Optional association of the ActivityInstance with a defined sub-plan.
     * @param tags Set of tags that may be used to associate the Activity with a subset of the Plan, grouping activities by operational responsibility (controller/group/system) or other criteria.
     */
    public InsertedActivityDetails(org.ccsds.moims.mo.mps.structures.Slider activityRef,
            org.ccsds.moims.mo.mal.structures.Element activityOffset,
            org.ccsds.moims.mo.mal.structures.Element relatedEvent,
            String comments,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> plan,
            org.ccsds.moims.mo.mps.structures.Trigger start,
            org.ccsds.moims.mo.mps.structures.Trigger end,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityDefinition> activityDefinition,
            org.ccsds.moims.mo.mps.structures.ArgSpecList argSpecs,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.PlanningUser> user,
            org.ccsds.moims.mo.mps.structures.Constraint constraints,
            org.ccsds.moims.mo.mps.structures.EffectList effects,
            org.ccsds.moims.mo.mal.structures.Identifier subPlan,
            org.ccsds.moims.mo.mal.structures.StringList tags) {
        super(activityRef,
            activityOffset,
            relatedEvent,
            comments);
        this.plan = plan;
        this.start = start;
        this.end = end;
        this.activityDefinition = activityDefinition;
        this.argSpecs = argSpecs;
        this.user = user;
        this.constraints = constraints;
        this.effects = effects;
        this.subPlan = subPlan;
        this.tags = tags;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param plan Reference to the Plan into which the ActivityInstance is to be inserted.
     * @param activityDefinition Reference to the ActivityDefinition.
     * @param user The User ID for the person or organization inserting the activity into the Plan.
     */
    public InsertedActivityDetails(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> plan,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityDefinition> activityDefinition,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.PlanningUser> user) {
        this.plan = plan;
        this.start = null;
        this.end = null;
        this.activityDefinition = activityDefinition;
        this.argSpecs = null;
        this.user = user;
        this.constraints = null;
        this.effects = null;
        this.subPlan = null;
        this.tags = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.InsertedActivityDetails();
    }

    /**
     * Returns the field plan.
     * 
     * @return The field plan
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> getPlan() {
        return plan;
    }

    /**
     * Returns the field start.
     * 
     * @return The field start
     */
    public org.ccsds.moims.mo.mps.structures.Trigger getStart() {
        return start;
    }

    /**
     * Returns the field end.
     * 
     * @return The field end
     */
    public org.ccsds.moims.mo.mps.structures.Trigger getEnd() {
        return end;
    }

    /**
     * Returns the field activityDefinition.
     * 
     * @return The field activityDefinition
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityDefinition> getActivityDefinition() {
        return activityDefinition;
    }

    /**
     * Returns the field argSpecs.
     * 
     * @return The field argSpecs
     */
    public org.ccsds.moims.mo.mps.structures.ArgSpecList getArgSpecs() {
        return argSpecs;
    }

    /**
     * Returns the field user.
     * 
     * @return The field user
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.PlanningUser> getUser() {
        return user;
    }

    /**
     * Returns the field constraints.
     * 
     * @return The field constraints
     */
    public org.ccsds.moims.mo.mps.structures.Constraint getConstraints() {
        return constraints;
    }

    /**
     * Returns the field effects.
     * 
     * @return The field effects
     */
    public org.ccsds.moims.mo.mps.structures.EffectList getEffects() {
        return effects;
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof InsertedActivityDetails) {
            if (! super.equals(obj)) {
                return false;
            }
            InsertedActivityDetails other = (InsertedActivityDetails) obj;
            if (plan == null) {
                if (other.plan != null) {
                    return false;
                }
            } else {
                if (! plan.equals(other.plan)) {
                    return false;
                }
            }
            if (start == null) {
                if (other.start != null) {
                    return false;
                }
            } else {
                if (! start.equals(other.start)) {
                    return false;
                }
            }
            if (end == null) {
                if (other.end != null) {
                    return false;
                }
            } else {
                if (! end.equals(other.end)) {
                    return false;
                }
            }
            if (activityDefinition == null) {
                if (other.activityDefinition != null) {
                    return false;
                }
            } else {
                if (! activityDefinition.equals(other.activityDefinition)) {
                    return false;
                }
            }
            if (argSpecs == null) {
                if (other.argSpecs != null) {
                    return false;
                }
            } else {
                if (! argSpecs.equals(other.argSpecs)) {
                    return false;
                }
            }
            if (user == null) {
                if (other.user != null) {
                    return false;
                }
            } else {
                if (! user.equals(other.user)) {
                    return false;
                }
            }
            if (constraints == null) {
                if (other.constraints != null) {
                    return false;
                }
            } else {
                if (! constraints.equals(other.constraints)) {
                    return false;
                }
            }
            if (effects == null) {
                if (other.effects != null) {
                    return false;
                }
            } else {
                if (! effects.equals(other.effects)) {
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
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 83 * hash + (plan != null ? plan.hashCode() : 0);
        hash = 83 * hash + (start != null ? start.hashCode() : 0);
        hash = 83 * hash + (end != null ? end.hashCode() : 0);
        hash = 83 * hash + (activityDefinition != null ? activityDefinition.hashCode() : 0);
        hash = 83 * hash + (argSpecs != null ? argSpecs.hashCode() : 0);
        hash = 83 * hash + (user != null ? user.hashCode() : 0);
        hash = 83 * hash + (constraints != null ? constraints.hashCode() : 0);
        hash = 83 * hash + (effects != null ? effects.hashCode() : 0);
        hash = 83 * hash + (subPlan != null ? subPlan.hashCode() : 0);
        hash = 83 * hash + (tags != null ? tags.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(InsertedActivityDetails: ");
        buf.append(super.toString());
        buf.append(", plan=").append(plan);
        buf.append(", start=").append(start);
        buf.append(", end=").append(end);
        buf.append(", activityDefinition=").append(activityDefinition);
        buf.append(", argSpecs=").append(argSpecs);
        buf.append(", user=").append(user);
        buf.append(", constraints=").append(constraints);
        buf.append(", effects=").append(effects);
        buf.append(", subPlan=").append(subPlan);
        buf.append(", tags=").append(tags);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (plan == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'plan' cannot be null!");
        }
        if (activityDefinition == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'activityDefinition' cannot be null!");
        }
        if (user == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'user' cannot be null!");
        }
        encoder.encodeElement(plan);
        encoder.encodeNullableAbstractElement(start);
        encoder.encodeNullableAbstractElement(end);
        encoder.encodeElement(activityDefinition);
        encoder.encodeNullableElement(argSpecs);
        encoder.encodeElement(user);
        encoder.encodeNullableAbstractElement(constraints);
        encoder.encodeNullableElement(effects);
        encoder.encodeNullableIdentifier(subPlan);
        encoder.encodeNullableElement(tags);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        plan = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>());
        start = (org.ccsds.moims.mo.mps.structures.Trigger) decoder.decodeNullableAbstractElement();
        end = (org.ccsds.moims.mo.mps.structures.Trigger) decoder.decodeNullableAbstractElement();
        activityDefinition = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityDefinition>) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityDefinition>());
        argSpecs = (org.ccsds.moims.mo.mps.structures.ArgSpecList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.ArgSpecList());
        user = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.PlanningUser>) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.PlanningUser>());
        constraints = (org.ccsds.moims.mo.mps.structures.Constraint) decoder.decodeNullableAbstractElement();
        effects = (org.ccsds.moims.mo.mps.structures.EffectList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.EffectList());
        subPlan = decoder.decodeNullableIdentifier();
        tags = (org.ccsds.moims.mo.mal.structures.StringList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.StringList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
