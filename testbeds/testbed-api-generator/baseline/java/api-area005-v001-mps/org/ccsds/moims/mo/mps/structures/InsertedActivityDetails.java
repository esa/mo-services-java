package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.StringList;

/**
 * E1: A concrete sub-type of ActivityDetails (4.5.2.3.1) that is a variation
 * of SimpleActivityDetails providing additional details for a single ActivityInstance
 * to be inserted into a Plan using the MPS Plan Edit service.
 */
public final class InsertedActivityDetails extends ActivityDetails {

    private static final long serialVersionUID = 1407374900330603L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330603L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Reference to the Plan into which the ActivityInstance is to be inserted.
     */
    private ObjectRef<Plan> plan;

    /**
     * Optionally specifies the trigger that initiates the ActivityInstance: may
     * be time, position, or event based.
     */
    private Trigger start;

    /**
     * Optionally specifies the trigger that ends the ActivityInstance.
     */
    private Trigger end;

    /**
     * Reference to the ActivityDefinition.
     */
    private ObjectRef<ActivityDefinition> activityDefinition;

    /**
     * Set of argument specifications for each argument definition contained in
     * the referenced activity definition.  These supply a value for each argument,
     * or an expression to enable the value to be derived.
     */
    private ArgSpecList argSpecs;

    /**
     * The User ID for the person or organization inserting the activity into
     * the Plan.
     */
    private ObjectRef<PlanningUser> user;

    /**
     * A single constraint or a constraint node that may contain multiple constraints,
     * specific to the ActivityInstance to be created.
     */
    private Constraint constraints;

    /**
     * Set of Effects specific to the ActivityInstance to be created.
     */
    private EffectList effects;

    /**
     * Optional association of the ActivityInstance with a defined sub-plan.
     */
    private Identifier subPlan;

    /**
     * Set of tags that may be used to associate the Activity with a subset of
     * the Plan, grouping activities by operational responsibility (controller/group/system)
     * or other criteria.
     */
    private StringList tags;

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
    public InsertedActivityDetails(Slider activityRef,
            Element activityOffset,
            Element relatedEvent,
            String comments,
            ObjectRef<Plan> plan,
            Trigger start,
            Trigger end,
            ObjectRef<ActivityDefinition> activityDefinition,
            ArgSpecList argSpecs,
            ObjectRef<PlanningUser> user,
            Constraint constraints,
            EffectList effects,
            Identifier subPlan,
            StringList tags) {
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
    public InsertedActivityDetails(ObjectRef<Plan> plan,
            ObjectRef<ActivityDefinition> activityDefinition,
            ObjectRef<PlanningUser> user) {
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
    public Element createElement() {
        return new InsertedActivityDetails();
    }

    /**
     * Returns the field plan.
     * 
     * @return The field plan
     */
    public ObjectRef<Plan> getPlan() {
        return plan;
    }

    /**
     * Returns the field start.
     * 
     * @return The field start
     */
    public Trigger getStart() {
        return start;
    }

    /**
     * Returns the field end.
     * 
     * @return The field end
     */
    public Trigger getEnd() {
        return end;
    }

    /**
     * Returns the field activityDefinition.
     * 
     * @return The field activityDefinition
     */
    public ObjectRef<ActivityDefinition> getActivityDefinition() {
        return activityDefinition;
    }

    /**
     * Returns the field argSpecs.
     * 
     * @return The field argSpecs
     */
    public ArgSpecList getArgSpecs() {
        return argSpecs;
    }

    /**
     * Returns the field user.
     * 
     * @return The field user
     */
    public ObjectRef<PlanningUser> getUser() {
        return user;
    }

    /**
     * Returns the field constraints.
     * 
     * @return The field constraints
     */
    public Constraint getConstraints() {
        return constraints;
    }

    /**
     * Returns the field effects.
     * 
     * @return The field effects
     */
    public EffectList getEffects() {
        return effects;
    }

    /**
     * Returns the field subPlan.
     * 
     * @return The field subPlan
     */
    public Identifier getSubPlan() {
        return subPlan;
    }

    /**
     * Returns the field tags.
     * 
     * @return The field tags
     */
    public StringList getTags() {
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
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (plan == null) {
            throw new MALException("The field 'plan' cannot be null!");
        }
        if (activityDefinition == null) {
            throw new MALException("The field 'activityDefinition' cannot be null!");
        }
        if (user == null) {
            throw new MALException("The field 'user' cannot be null!");
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
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        plan = (ObjectRef<Plan>) decoder.decodeElement(new ObjectRef<Plan>());
        start = (Trigger) decoder.decodeNullableAbstractElement();
        end = (Trigger) decoder.decodeNullableAbstractElement();
        activityDefinition = (ObjectRef<ActivityDefinition>) decoder.decodeElement(new ObjectRef<ActivityDefinition>());
        argSpecs = (ArgSpecList) decoder.decodeNullableElement(new ArgSpecList());
        user = (ObjectRef<PlanningUser>) decoder.decodeElement(new ObjectRef<PlanningUser>());
        constraints = (Constraint) decoder.decodeNullableAbstractElement();
        effects = (EffectList) decoder.decodeNullableElement(new EffectList());
        subPlan = decoder.decodeNullableIdentifier();
        tags = (StringList) decoder.decodeNullableElement(new StringList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
