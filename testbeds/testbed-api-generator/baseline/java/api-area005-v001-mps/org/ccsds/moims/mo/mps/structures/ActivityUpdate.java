package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.mal.structures.Time;

/**
 * E1: ActivityUpdate is a data structure that is used to report the dynamic
 * status of an ActivityInstance in the context of the MPS Plan Execution
 * Control service monitorPlanExecutionDetail and getActivityStatus operations.
 * ActivityUpdates may be distributed to subscribing applications, including
 * status displays, to inform them of the latest status of the activity.
 * This may be particularly relevant in conjunction with a plan execution
 * function.  ActivityUpdates may be stored in activity history to provide
 * a complete record of evolving status over time.
 */
public final class ActivityUpdate extends PlanDetailUpdate {

    private static final long serialVersionUID = 1407374900330602L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330602L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Reference to the ActivityInstance to which the status update relates.
     */
    private ObjectRef<ActivityInstance> activityInstance;

    /**
     * Time of status update. Only nullable in the context of an updateActivity
     * operation: the timestamp must be provided when reporting ActivityInstance
     * status.
     */
    private Time timestamp;

    /**
     * Optional reference to the Plan containing the ActivityInstance to which
     * this update pertains.
     */
    private ObjectRef<Plan> plan;

    /**
     * Argument values.
     */
    private ArgumentList arguments;

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
     * Optional duration of the ActivityInstance (estimated until execution, actual
     * post execution).
     */
    private Duration duration;

    /**
     * Optional association of the ActivityInstance with a defined sub-plan.
     */
    private Identifier subPlan;

    /**
     * Set of tags that may be used to associate the ActivityInstance with an
     * identified subset of the Plan, grouping activities by operational responsibility
     * (controller/group/system) or other criteria.
     */
    private StringList tags;

    /**
     * Current status of the ActivityInstance.
     */
    private ActivityStatusEnum status;

    /**
     * Reference to the instance of an executable body for the ActivityInstance
     * (procedure, action sequence, etc.).  The manner in which this reference
     * is interpreted is implementation specific.
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
     * Supplementary error information.
     */
    private String errorInfo;

    /**
     * Default constructor for ActivityUpdate.
     * 
     */
    public ActivityUpdate() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param activityInstance Reference to the ActivityInstance to which the status update relates.
     * @param timestamp Time of status update. Only nullable in the context of an updateActivity operation: the timestamp must be provided when reporting ActivityInstance status.
     * @param plan Optional reference to the Plan containing the ActivityInstance to which this update pertains.
     * @param arguments Argument values.
     * @param start Optionally specifies the trigger that initiates the ActivityInstance: may be time, position, or event based.
     * @param end Optionally specifies the trigger that ends the ActivityInstance.
     * @param duration Optional duration of the ActivityInstance (estimated until execution, actual post execution).
     * @param subPlan Optional association of the ActivityInstance with a defined sub-plan.
     * @param tags Set of tags that may be used to associate the ActivityInstance with an identified subset of the Plan, grouping activities by operational responsibility (controller/group/system) or other criteria.
     * @param status Current status of the ActivityInstance.
     * @param executionInstance Reference to the instance of an executable body for the ActivityInstance (procedure, action sequence, etc.).  The manner in which this reference is interpreted is implementation specific.
     * @param returnData Optional return data from the planning process, provided as a list of ID-Value pairs.  This can be used to provide additional information required by the User to interpret the planned activity.
     * @param statusInfo StatusInfo provides the reason for entering the Terminated State and is customizable, but if the following conditions exist then the specified text shall be used: - Completed (nominal); - Expired (prior to Activation or during plan Suspension); - Deleted; - Failed (see ErrorCode/ErrorInfo).
     * @param errorCode Error Code optional in the case of a failure status for the planning activity (for example Terminated state with statusInfo Failed).  The codes are implementation specific.
     * @param errorInfo Supplementary error information.
     */
    public ActivityUpdate(ObjectRef<ActivityInstance> activityInstance,
            Time timestamp,
            ObjectRef<Plan> plan,
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
        this.activityInstance = activityInstance;
        this.timestamp = timestamp;
        this.plan = plan;
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
     * @param activityInstance Reference to the ActivityInstance to which the status update relates.
     * @param status Current status of the ActivityInstance.
     */
    public ActivityUpdate(ObjectRef<ActivityInstance> activityInstance,
            ActivityStatusEnum status) {
        this.activityInstance = activityInstance;
        this.timestamp = null;
        this.plan = null;
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
        return new ActivityUpdate();
    }

    /**
     * Returns the field activityInstance.
     * 
     * @return The field activityInstance
     */
    public ObjectRef<ActivityInstance> getActivityInstance() {
        return activityInstance;
    }

    /**
     * Returns the field timestamp.
     * 
     * @return The field timestamp
     */
    public Time getTimestamp() {
        return timestamp;
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
        if (obj instanceof ActivityUpdate) {
            if (! super.equals(obj)) {
                return false;
            }
            ActivityUpdate other = (ActivityUpdate) obj;
            if (activityInstance == null) {
                if (other.activityInstance != null) {
                    return false;
                }
            } else {
                if (! activityInstance.equals(other.activityInstance)) {
                    return false;
                }
            }
            if (timestamp == null) {
                if (other.timestamp != null) {
                    return false;
                }
            } else {
                if (! timestamp.equals(other.timestamp)) {
                    return false;
                }
            }
            if (plan == null) {
                if (other.plan != null) {
                    return false;
                }
            } else {
                if (! plan.equals(other.plan)) {
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
        hash = 83 * hash + (activityInstance != null ? activityInstance.hashCode() : 0);
        hash = 83 * hash + (timestamp != null ? timestamp.hashCode() : 0);
        hash = 83 * hash + (plan != null ? plan.hashCode() : 0);
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
        buf.append("(ActivityUpdate: ");
        buf.append(super.toString());
        buf.append(", activityInstance=").append(activityInstance);
        buf.append(", timestamp=").append(timestamp);
        buf.append(", plan=").append(plan);
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
        if (activityInstance == null) {
            throw new MALException("The field 'activityInstance' cannot be null!");
        }
        if (status == null) {
            throw new MALException("The field 'status' cannot be null!");
        }
        encoder.encodeElement(activityInstance);
        encoder.encodeNullableTime(timestamp);
        encoder.encodeNullableElement(plan);
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
        activityInstance = (ObjectRef<ActivityInstance>) decoder.decodeElement(new ObjectRef<ActivityInstance>());
        timestamp = decoder.decodeNullableTime();
        plan = (ObjectRef<Plan>) decoder.decodeNullableElement(new ObjectRef<Plan>());
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
