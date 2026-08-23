package org.ccsds.moims.mo.mps.structures;

/**
 * E1: A concrete sub-type of ActivityDetails, a SimpleActivityDetails provides
 * the information required to instantiate a single ActivityInstance.
 */
public final class SimpleActivityDetails extends org.ccsds.moims.mo.mps.structures.ActivityDetails {

    private static final long serialVersionUID = 1407374900330601L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330601L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

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
     * A set of tags that may be used to associate the Activity with an identified
     * subset of the Plan, grouping activities by operational responsibility (controller/group/system)
     * or other criteria.
     */
    private org.ccsds.moims.mo.mal.structures.StringList tags;

    /**
     * Default constructor for SimpleActivityDetails.
     * 
     */
    public SimpleActivityDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param activityRef Specifies how the ActivityInstance is placed with respect to any defined Repetition (0=Start; 1=End). Default is Start.
     * @param activityOffset Specifies an offset in time for the ActivityInstance from any defined Repetition. Default is no offset.
     * @param relatedEvent Specifies a related Event (or Event Group) for the ActivityInstance.  Argument specifications and constraints may reference arguments and fields of the RelatedEvent.
     * @param comments Any notes associated with the ActivityDetails.
     * @param activityDefinition Reference to the ActivityDefinition.
     * @param argSpecs Set of argument specifications for each argument definition contained in the referenced activity definition.  These supply a value for each argument, or an expression to enable the value to be derived.
     * @param constraints A single constraint or a constraint node that may contain multiple constraints, specific to the ActivityInstance to be created.
     * @param effects Set of Effects specific to the ActivityInstance to be created.
     * @param subPlan Optional association of the ActivityInstance with a defined sub-plan.
     * @param tags A set of tags that may be used to associate the Activity with an identified subset of the Plan, grouping activities by operational responsibility (controller/group/system) or other criteria.
     */
    public SimpleActivityDetails(org.ccsds.moims.mo.mps.structures.Slider activityRef,
            org.ccsds.moims.mo.mal.structures.Element activityOffset,
            org.ccsds.moims.mo.mal.structures.Element relatedEvent,
            String comments,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityDefinition> activityDefinition,
            org.ccsds.moims.mo.mps.structures.ArgSpecList argSpecs,
            org.ccsds.moims.mo.mps.structures.Constraint constraints,
            org.ccsds.moims.mo.mps.structures.EffectList effects,
            org.ccsds.moims.mo.mal.structures.Identifier subPlan,
            org.ccsds.moims.mo.mal.structures.StringList tags) {
        super(activityRef,
            activityOffset,
            relatedEvent,
            comments);
        this.activityDefinition = activityDefinition;
        this.argSpecs = argSpecs;
        this.constraints = constraints;
        this.effects = effects;
        this.subPlan = subPlan;
        this.tags = tags;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param activityDefinition Reference to the ActivityDefinition.
     */
    public SimpleActivityDetails(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityDefinition> activityDefinition) {
        this.activityDefinition = activityDefinition;
        this.argSpecs = null;
        this.constraints = null;
        this.effects = null;
        this.subPlan = null;
        this.tags = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.SimpleActivityDetails();
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
        if (obj instanceof SimpleActivityDetails) {
            if (! super.equals(obj)) {
                return false;
            }
            SimpleActivityDetails other = (SimpleActivityDetails) obj;
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
        hash = 83 * hash + (activityDefinition != null ? activityDefinition.hashCode() : 0);
        hash = 83 * hash + (argSpecs != null ? argSpecs.hashCode() : 0);
        hash = 83 * hash + (constraints != null ? constraints.hashCode() : 0);
        hash = 83 * hash + (effects != null ? effects.hashCode() : 0);
        hash = 83 * hash + (subPlan != null ? subPlan.hashCode() : 0);
        hash = 83 * hash + (tags != null ? tags.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(SimpleActivityDetails: ");
        buf.append(super.toString());
        buf.append(", activityDefinition=").append(activityDefinition);
        buf.append(", argSpecs=").append(argSpecs);
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
        if (activityDefinition == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'activityDefinition' cannot be null!");
        }
        encoder.encodeElement(activityDefinition);
        encoder.encodeNullableElement(argSpecs);
        encoder.encodeNullableAbstractElement(constraints);
        encoder.encodeNullableElement(effects);
        encoder.encodeNullableIdentifier(subPlan);
        encoder.encodeNullableElement(tags);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        activityDefinition = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityDefinition>) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityDefinition>());
        argSpecs = (org.ccsds.moims.mo.mps.structures.ArgSpecList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.ArgSpecList());
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
