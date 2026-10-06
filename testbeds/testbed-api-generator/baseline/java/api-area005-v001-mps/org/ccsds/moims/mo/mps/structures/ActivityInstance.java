package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.MOObject;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.StringList;

/**
 * E1: An ActivityInstance is an MO object that contains the identity of a
 * specific occurrence of a planning activity, together with both static and
 * dynamic information associated with that occurrence.  It supports relationships
 * to its definition, source, a related planning event and any child activities.
 * ActivityInstances may be contained within a Plan. NOTE – The start and
 * end fields specify the trigger conditions (including time) that specify
 * when the ActivityInstance starts and/or ends in the context of a Plan.
 * The duration is an estimate of the time taken to execute the ActivityInstance
 * rather than an offset, which may for example be used in the visualization
 * of a Plan.  Duration may be used in conjunction with a specified end trigger
 * to determine the planned start time of an ActivityInstance.
 */
public final class ActivityInstance extends MOObject {

    private static final long serialVersionUID = 1407374900330598L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330598L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Reference to the ActivityDefinition.
     */
    private ObjectRef<ActivityDefinition> definition;

    /**
     * Object Type: RequestInstance | ActivityInstance | PlanningUser Reference
     * to the source of the ActivityInstance, which is either its parent ActivityInstance,
     * a RequestInstance if it is a root Activity, or a PlanningUser if directly
     * inserted.
     */
    private ObjectRef<Element> source;

    /**
     * Optional reference to an EventInstance that is specifically associated
     * with this instance of the Activity.  Typically, the Activity is placed
     * in response to the Event.
     */
    private ObjectRef<EventInstance> relatedEvent;

    /**
     * References to any child ActivityInstances.
     */
    private ObjectRefList children;

    /**
     * Any notes associated with this instance of the Activity.
     */
    private String comments;

    /**
     * A single constraint or a constraint node that may contain multiple constraints,
     * applicable to this instance of the Activity.
     */
    private Constraint constraints;

    /**
     * Set of Effects applicable to this instance of the Activity.
     */
    private EffectList effects;

    /**
     * Argument values for each Argument defined in the Activity Definition.
     */
    private ArgumentList arguments;

    /**
     * Optionally specifies the trigger that initiates the Activity: may be time,
     * position or event based.
     */
    private Trigger start;

    /**
     * Optionally specifies the trigger that ends the Activity.
     */
    private Trigger end;

    /**
     * Optional duration of the Activity (estimated until execution, actual post
     * execution).
     */
    private Duration duration;

    /**
     * Optional association of the Activity with a defined sub-plan.
     */
    private Identifier subPlan;

    /**
     * Set of Tags that may be used to associate the Activity with others, grouping
     * activities by operational responsibility (controller/group/system) or other
     * criteria.
     */
    private StringList tags;

    /**
     * Current Status of the Activity Instance (see Activity State Model in 4.5.2.2).
     */
    private ActivityStatusEnum status;

    /**
     * Reference to the instance of an executable body for the Activity (procedure,
     * action sequence, etc.).  The manner in which this reference is interpreted
     * is implementation specific.
     */
    private Identifier executionInstance;

    /**
     * Optional return data from the planning process, provided as a list of ID-Value
     * pairs.  This can be used to provide additional information required by
     * the User to interpret the planned activity.
     */
    private NamedValueList returnData;

    /**
     * StatusInfo provides the reason for entering the Terminated State and is
     * customizable, but if the following conditions exist then the specified
     * text shall be used: - Completed (nominal); - Expired (prior to Activation
     * or during plan Suspension); - Deleted; - Failed (see ErrorCode/ErrorInfo).
     */
    private String statusInfo;

    /**
     * Error Code optional in the case of a failure status for the planning activity
     * (for example Terminated state with statusInfo Failed).  The codes are implementation
     * specific.
     */
    private Integer errorCode;

    /**
     * Supplementary Error Information.
     */
    private String errorInfo;

    /**
     * Default constructor for ActivityInstance.
     * 
     */
    public ActivityInstance() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param definition Reference to the ActivityDefinition.
     * @param source Object Type: RequestInstance | ActivityInstance | PlanningUser Reference to the source of the ActivityInstance, which is either its parent ActivityInstance, a RequestInstance if it is a root Activity, or a PlanningUser if directly inserted.
     * @param relatedEvent Optional reference to an EventInstance that is specifically associated with this instance of the Activity.  Typically, the Activity is placed in response to the Event.
     * @param children References to any child ActivityInstances.
     * @param comments Any notes associated with this instance of the Activity.
     * @param constraints A single constraint or a constraint node that may contain multiple constraints, applicable to this instance of the Activity.
     * @param effects Set of Effects applicable to this instance of the Activity.
     * @param arguments Argument values for each Argument defined in the Activity Definition.
     * @param start Optionally specifies the trigger that initiates the Activity: may be time, position or event based.
     * @param end Optionally specifies the trigger that ends the Activity.
     * @param duration Optional duration of the Activity (estimated until execution, actual post execution).
     * @param subPlan Optional association of the Activity with a defined sub-plan.
     * @param tags Set of Tags that may be used to associate the Activity with others, grouping activities by operational responsibility (controller/group/system) or other criteria.
     * @param status Current Status of the Activity Instance (see Activity State Model in 4.5.2.2).
     * @param executionInstance Reference to the instance of an executable body for the Activity (procedure, action sequence, etc.).  The manner in which this reference is interpreted is implementation specific.
     * @param returnData Optional return data from the planning process, provided as a list of ID-Value pairs.  This can be used to provide additional information required by the User to interpret the planned activity.
     * @param statusInfo StatusInfo provides the reason for entering the Terminated State and is customizable, but if the following conditions exist then the specified text shall be used: - Completed (nominal); - Expired (prior to Activation or during plan Suspension); - Deleted; - Failed (see ErrorCode/ErrorInfo).
     * @param errorCode Error Code optional in the case of a failure status for the planning activity (for example Terminated state with statusInfo Failed).  The codes are implementation specific.
     * @param errorInfo Supplementary Error Information.
     */
    public ActivityInstance(ObjectIdentity objectIdentity,
            ObjectRef<ActivityDefinition> definition,
            ObjectRef<Element> source,
            ObjectRef<EventInstance> relatedEvent,
            ObjectRefList children,
            String comments,
            Constraint constraints,
            EffectList effects,
            ArgumentList arguments,
            Trigger start,
            Trigger end,
            Duration duration,
            Identifier subPlan,
            StringList tags,
            ActivityStatusEnum status,
            Identifier executionInstance,
            NamedValueList returnData,
            String statusInfo,
            Integer errorCode,
            String errorInfo) {
        super(objectIdentity);
        this.definition = definition;
        this.source = source;
        this.relatedEvent = relatedEvent;
        this.children = children;
        this.comments = comments;
        this.constraints = constraints;
        this.effects = effects;
        this.arguments = arguments;
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.subPlan = subPlan;
        this.tags = tags;
        this.status = status;
        this.executionInstance = executionInstance;
        this.returnData = returnData;
        this.statusInfo = statusInfo;
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param definition Reference to the ActivityDefinition.
     * @param source Object Type: RequestInstance | ActivityInstance | PlanningUser Reference to the source of the ActivityInstance, which is either its parent ActivityInstance, a RequestInstance if it is a root Activity, or a PlanningUser if directly inserted.
     * @param status Current Status of the Activity Instance (see Activity State Model in 4.5.2.2).
     */
    public ActivityInstance(ObjectIdentity objectIdentity,
            ObjectRef<ActivityDefinition> definition,
            ObjectRef<Element> source,
            ActivityStatusEnum status) {
        super(objectIdentity);
        this.definition = definition;
        this.source = source;
        this.relatedEvent = null;
        this.children = null;
        this.comments = null;
        this.constraints = null;
        this.effects = null;
        this.arguments = null;
        this.start = null;
        this.end = null;
        this.duration = null;
        this.subPlan = null;
        this.tags = null;
        this.status = status;
        this.executionInstance = null;
        this.returnData = null;
        this.statusInfo = null;
        this.errorCode = null;
        this.errorInfo = null;
    }

    @Override
    public Element createElement() {
        return new ActivityInstance();
    }

    /**
     * Returns the field definition.
     * 
     * @return The field definition
     */
    public ObjectRef<ActivityDefinition> getDefinition() {
        return definition;
    }

    /**
     * Returns the field source.
     * 
     * @return The field source
     */
    public ObjectRef<Element> getSource() {
        return source;
    }

    /**
     * Returns the field relatedEvent.
     * 
     * @return The field relatedEvent
     */
    public ObjectRef<EventInstance> getRelatedEvent() {
        return relatedEvent;
    }

    /**
     * Returns the field children.
     * 
     * @return The field children
     */
    public ObjectRefList getChildren() {
        return children;
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
     * Returns the field arguments.
     * 
     * @return The field arguments
     */
    public ArgumentList getArguments() {
        return arguments;
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
     * Returns the field duration.
     * 
     * @return The field duration
     */
    public Duration getDuration() {
        return duration;
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

    /**
     * Returns the field status.
     * 
     * @return The field status
     */
    public ActivityStatusEnum getStatus() {
        return status;
    }

    /**
     * Returns the field executionInstance.
     * 
     * @return The field executionInstance
     */
    public Identifier getExecutionInstance() {
        return executionInstance;
    }

    /**
     * Returns the field returnData.
     * 
     * @return The field returnData
     */
    public NamedValueList getReturnData() {
        return returnData;
    }

    /**
     * Returns the field statusInfo.
     * 
     * @return The field statusInfo
     */
    public String getStatusInfo() {
        return statusInfo;
    }

    /**
     * Returns the field errorCode.
     * 
     * @return The field errorCode
     */
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * Returns the field errorInfo.
     * 
     * @return The field errorInfo
     */
    public String getErrorInfo() {
        return errorInfo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ActivityInstance) {
            if (! super.equals(obj)) {
                return false;
            }
            ActivityInstance other = (ActivityInstance) obj;
            if (definition == null) {
                if (other.definition != null) {
                    return false;
                }
            } else {
                if (! definition.equals(other.definition)) {
                    return false;
                }
            }
            if (source == null) {
                if (other.source != null) {
                    return false;
                }
            } else {
                if (! source.equals(other.source)) {
                    return false;
                }
            }
            if (relatedEvent == null) {
                if (other.relatedEvent != null) {
                    return false;
                }
            } else {
                if (! relatedEvent.equals(other.relatedEvent)) {
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
            if (comments == null) {
                if (other.comments != null) {
                    return false;
                }
            } else {
                if (! comments.equals(other.comments)) {
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
            if (arguments == null) {
                if (other.arguments != null) {
                    return false;
                }
            } else {
                if (! arguments.equals(other.arguments)) {
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
            if (duration == null) {
                if (other.duration != null) {
                    return false;
                }
            } else {
                if (! duration.equals(other.duration)) {
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
            if (status == null) {
                if (other.status != null) {
                    return false;
                }
            } else {
                if (! status.equals(other.status)) {
                    return false;
                }
            }
            if (executionInstance == null) {
                if (other.executionInstance != null) {
                    return false;
                }
            } else {
                if (! executionInstance.equals(other.executionInstance)) {
                    return false;
                }
            }
            if (returnData == null) {
                if (other.returnData != null) {
                    return false;
                }
            } else {
                if (! returnData.equals(other.returnData)) {
                    return false;
                }
            }
            if (statusInfo == null) {
                if (other.statusInfo != null) {
                    return false;
                }
            } else {
                if (! statusInfo.equals(other.statusInfo)) {
                    return false;
                }
            }
            if (errorCode == null) {
                if (other.errorCode != null) {
                    return false;
                }
            } else {
                if (! errorCode.equals(other.errorCode)) {
                    return false;
                }
            }
            if (errorInfo == null) {
                if (other.errorInfo != null) {
                    return false;
                }
            } else {
                if (! errorInfo.equals(other.errorInfo)) {
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
        hash = 83 * hash + (definition != null ? definition.hashCode() : 0);
        hash = 83 * hash + (source != null ? source.hashCode() : 0);
        hash = 83 * hash + (relatedEvent != null ? relatedEvent.hashCode() : 0);
        hash = 83 * hash + (children != null ? children.hashCode() : 0);
        hash = 83 * hash + (comments != null ? comments.hashCode() : 0);
        hash = 83 * hash + (constraints != null ? constraints.hashCode() : 0);
        hash = 83 * hash + (effects != null ? effects.hashCode() : 0);
        hash = 83 * hash + (arguments != null ? arguments.hashCode() : 0);
        hash = 83 * hash + (start != null ? start.hashCode() : 0);
        hash = 83 * hash + (end != null ? end.hashCode() : 0);
        hash = 83 * hash + (duration != null ? duration.hashCode() : 0);
        hash = 83 * hash + (subPlan != null ? subPlan.hashCode() : 0);
        hash = 83 * hash + (tags != null ? tags.hashCode() : 0);
        hash = 83 * hash + (status != null ? status.hashCode() : 0);
        hash = 83 * hash + (executionInstance != null ? executionInstance.hashCode() : 0);
        hash = 83 * hash + (returnData != null ? returnData.hashCode() : 0);
        hash = 83 * hash + (statusInfo != null ? statusInfo.hashCode() : 0);
        hash = 83 * hash + (errorCode != null ? errorCode.hashCode() : 0);
        hash = 83 * hash + (errorInfo != null ? errorInfo.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ActivityInstance: ");
        buf.append(super.toString());
        buf.append(", definition=").append(definition);
        buf.append(", source=").append(source);
        buf.append(", relatedEvent=").append(relatedEvent);
        buf.append(", children=").append(children);
        buf.append(", comments=").append(comments);
        buf.append(", constraints=").append(constraints);
        buf.append(", effects=").append(effects);
        buf.append(", arguments=").append(arguments);
        buf.append(", start=").append(start);
        buf.append(", end=").append(end);
        buf.append(", duration=").append(duration);
        buf.append(", subPlan=").append(subPlan);
        buf.append(", tags=").append(tags);
        buf.append(", status=").append(status);
        buf.append(", executionInstance=").append(executionInstance);
        buf.append(", returnData=").append(returnData);
        buf.append(", statusInfo=").append(statusInfo);
        buf.append(", errorCode=").append(errorCode);
        buf.append(", errorInfo=").append(errorInfo);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (definition == null) {
            throw new MALException("The field 'definition' cannot be null!");
        }
        if (source == null) {
            throw new MALException("The field 'source' cannot be null!");
        }
        if (status == null) {
            throw new MALException("The field 'status' cannot be null!");
        }
        encoder.encodeElement(definition);
        encoder.encodeAbstractElement(source);
        encoder.encodeNullableElement(relatedEvent);
        encoder.encodeNullableElement(children);
        encoder.encodeNullableString(comments);
        encoder.encodeNullableAbstractElement(constraints);
        encoder.encodeNullableElement(effects);
        encoder.encodeNullableElement(arguments);
        encoder.encodeNullableAbstractElement(start);
        encoder.encodeNullableAbstractElement(end);
        encoder.encodeNullableDuration(duration);
        encoder.encodeNullableIdentifier(subPlan);
        encoder.encodeNullableElement(tags);
        encoder.encodeElement(status);
        encoder.encodeNullableIdentifier(executionInstance);
        encoder.encodeNullableElement(returnData);
        encoder.encodeNullableString(statusInfo);
        encoder.encodeNullableInteger(errorCode);
        encoder.encodeNullableString(errorInfo);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        definition = (ObjectRef<ActivityDefinition>) decoder.decodeElement(new ObjectRef<ActivityDefinition>());
        source = (ObjectRef<Element>) decoder.decodeAbstractElement();
        relatedEvent = (ObjectRef<EventInstance>) decoder.decodeNullableElement(new ObjectRef<EventInstance>());
        children = (ObjectRefList) decoder.decodeNullableElement(new ObjectRefList());
        comments = decoder.decodeNullableString();
        constraints = (Constraint) decoder.decodeNullableAbstractElement();
        effects = (EffectList) decoder.decodeNullableElement(new EffectList());
        arguments = (ArgumentList) decoder.decodeNullableElement(new ArgumentList());
        start = (Trigger) decoder.decodeNullableAbstractElement();
        end = (Trigger) decoder.decodeNullableAbstractElement();
        duration = decoder.decodeNullableDuration();
        subPlan = decoder.decodeNullableIdentifier();
        tags = (StringList) decoder.decodeNullableElement(new StringList());
        status = (ActivityStatusEnum) decoder.decodeElement(ActivityStatusEnum.PLANNED);
        executionInstance = decoder.decodeNullableIdentifier();
        returnData = (NamedValueList) decoder.decodeNullableElement(new NamedValueList());
        statusInfo = decoder.decodeNullableString();
        errorCode = decoder.decodeNullableInteger();
        errorInfo = decoder.decodeNullableString();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
