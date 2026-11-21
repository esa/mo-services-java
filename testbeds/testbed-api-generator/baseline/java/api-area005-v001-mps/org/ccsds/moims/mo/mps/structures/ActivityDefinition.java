package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.MOObject;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mal.structures.StringList;

/**
 * E1: An ActivityDefinition is an MO object that contains static configuration
 * data relating to multiple occurrences of a planning activity.  Its identity
 * is defined by a definitionID, which includes a constant key and an evolving
 * version that is updated each time the definition is revised.  ActivityDefinitions
 * form part of the planning configuration data.
 */
public final class ActivityDefinition extends MOObject {

    private static final long serialVersionUID = 1407374900330597L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330597L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Description of the Activity.
     */
    private String description;

    /**
     * List of Argument Definitions.
     */
    private ArgDefList argDefs;

    /**
     * A single constraint or a constraint node that may contain multiple constraints,
     * applicable to all instances of the Activity.
     */
    private Constraint constraints;

    /**
     * Set of Effects applicable to all instances of the Activity.
     */
    private EffectList effects;

    /**
     * Reference to the definition of an executable body for the Activity (procedure,
     * action sequence, etc.).  The manner in which this reference is interpreted
     * is implementation specific.
     */
    private Identifier executionDefinition;

    /**
     * Supports calculation of an estimated duration of an Activity Instance.
     */
    private Element durationSpec;

    /**
     * Set of activity details specifying child activities.
     */
    private ActivityDetailsList children;

    /**
     * Free-text field that can be used to categorize an activity into one of
     * several arbitrary categories.  Enables a planning system to customize behavior
     * for activities, such as their presentation in displays, based on the specified
     * value.
     */
    private String activityType;

    /**
     * Default set of Tags that may be used to associate the Activity with others,
     * grouping activities by operational responsibility (controller/group/system)
     * or other criteria.
     */
    private StringList defaultTags;

    /**
     * Default constructor for ActivityDefinition.
     * 
     */
    public ActivityDefinition() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param description Description of the Activity.
     * @param argDefs List of Argument Definitions.
     * @param constraints A single constraint or a constraint node that may contain multiple constraints, applicable to all instances of the Activity.
     * @param effects Set of Effects applicable to all instances of the Activity.
     * @param executionDefinition Reference to the definition of an executable body for the Activity (procedure, action sequence, etc.).  The manner in which this reference is interpreted is implementation specific.
     * @param durationSpec Supports calculation of an estimated duration of an Activity Instance.
     * @param children Set of activity details specifying child activities.
     * @param activityType Free-text field that can be used to categorize an activity into one of several arbitrary categories.  Enables a planning system to customize behavior for activities, such as their presentation in displays, based on the specified value.
     * @param defaultTags Default set of Tags that may be used to associate the Activity with others, grouping activities by operational responsibility (controller/group/system) or other criteria.
     */
    public ActivityDefinition(ObjectIdentity objectIdentity,
            String description,
            ArgDefList argDefs,
            Constraint constraints,
            EffectList effects,
            Identifier executionDefinition,
            Element durationSpec,
            ActivityDetailsList children,
            String activityType,
            StringList defaultTags) {
        super(objectIdentity);
        this.description = description;
        this.argDefs = argDefs;
        this.constraints = constraints;
        this.effects = effects;
        this.executionDefinition = executionDefinition;
        this.durationSpec = durationSpec;
        this.children = children;
        this.activityType = activityType;
        this.defaultTags = defaultTags;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param description Description of the Activity.
     */
    public ActivityDefinition(ObjectIdentity objectIdentity,
            String description) {
        super(objectIdentity);
        this.description = description;
        this.argDefs = null;
        this.constraints = null;
        this.effects = null;
        this.executionDefinition = null;
        this.durationSpec = null;
        this.children = null;
        this.activityType = null;
        this.defaultTags = null;
    }

    @Override
    public Element createElement() {
        return new ActivityDefinition();
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
     * Returns the field argDefs.
     * 
     * @return The field argDefs
     */
    public ArgDefList getArgDefs() {
        return argDefs;
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
     * Returns the field executionDefinition.
     * 
     * @return The field executionDefinition
     */
    public Identifier getExecutionDefinition() {
        return executionDefinition;
    }

    /**
     * Returns the field durationSpec.
     * 
     * @return The field durationSpec
     */
    public Element getDurationSpec() {
        return durationSpec;
    }

    /**
     * Returns the field children.
     * 
     * @return The field children
     */
    public ActivityDetailsList getChildren() {
        return children;
    }

    /**
     * Returns the field activityType.
     * 
     * @return The field activityType
     */
    public String getActivityType() {
        return activityType;
    }

    /**
     * Returns the field defaultTags.
     * 
     * @return The field defaultTags
     */
    public StringList getDefaultTags() {
        return defaultTags;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ActivityDefinition) {
            if (! super.equals(obj)) {
                return false;
            }
            ActivityDefinition other = (ActivityDefinition) obj;
            if (description == null) {
                if (other.description != null) {
                    return false;
                }
            } else {
                if (! description.equals(other.description)) {
                    return false;
                }
            }
            if (argDefs == null) {
                if (other.argDefs != null) {
                    return false;
                }
            } else {
                if (! argDefs.equals(other.argDefs)) {
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
            if (executionDefinition == null) {
                if (other.executionDefinition != null) {
                    return false;
                }
            } else {
                if (! executionDefinition.equals(other.executionDefinition)) {
                    return false;
                }
            }
            if (durationSpec == null) {
                if (other.durationSpec != null) {
                    return false;
                }
            } else {
                if (! durationSpec.equals(other.durationSpec)) {
                    return false;
                }
            }
            if (children == null) {
                if (other.children != null) {
                    return false;
                }
            } else {
                if (! children.equals(other.children)) {
                    return false;
                }
            }
            if (activityType == null) {
                if (other.activityType != null) {
                    return false;
                }
            } else {
                if (! activityType.equals(other.activityType)) {
                    return false;
                }
            }
            if (defaultTags == null) {
                if (other.defaultTags != null) {
                    return false;
                }
            } else {
                if (! defaultTags.equals(other.defaultTags)) {
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
        hash = 83 * hash + (description != null ? description.hashCode() : 0);
        hash = 83 * hash + (argDefs != null ? argDefs.hashCode() : 0);
        hash = 83 * hash + (constraints != null ? constraints.hashCode() : 0);
        hash = 83 * hash + (effects != null ? effects.hashCode() : 0);
        hash = 83 * hash + (executionDefinition != null ? executionDefinition.hashCode() : 0);
        hash = 83 * hash + (durationSpec != null ? durationSpec.hashCode() : 0);
        hash = 83 * hash + (children != null ? children.hashCode() : 0);
        hash = 83 * hash + (activityType != null ? activityType.hashCode() : 0);
        hash = 83 * hash + (defaultTags != null ? defaultTags.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ActivityDefinition: ");
        buf.append(super.toString());
        buf.append(", description=").append(description);
        buf.append(", argDefs=").append(argDefs);
        buf.append(", constraints=").append(constraints);
        buf.append(", effects=").append(effects);
        buf.append(", executionDefinition=").append(executionDefinition);
        buf.append(", durationSpec=").append(durationSpec);
        buf.append(", children=").append(children);
        buf.append(", activityType=").append(activityType);
        buf.append(", defaultTags=").append(defaultTags);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (description == null) {
            throw new MALException("The field 'description' cannot be null!");
        }
        encoder.encodeString(description);
        encoder.encodeNullableElement(argDefs);
        encoder.encodeNullableAbstractElement(constraints);
        encoder.encodeNullableElement(effects);
        encoder.encodeNullableIdentifier(executionDefinition);
        encoder.encodeNullableAbstractElement(durationSpec);
        encoder.encodeNullableElement(children);
        encoder.encodeNullableString(activityType);
        encoder.encodeNullableElement(defaultTags);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        description = decoder.decodeString();
        argDefs = (ArgDefList) decoder.decodeNullableElement(new ArgDefList());
        constraints = (Constraint) decoder.decodeNullableAbstractElement();
        effects = (EffectList) decoder.decodeNullableElement(new EffectList());
        executionDefinition = decoder.decodeNullableIdentifier();
        durationSpec = (Element) decoder.decodeNullableAbstractElement();
        children = (ActivityDetailsList) decoder.decodeNullableElement(new ActivityDetailsList());
        activityType = decoder.decodeNullableString();
        defaultTags = (StringList) decoder.decodeNullableElement(new StringList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
