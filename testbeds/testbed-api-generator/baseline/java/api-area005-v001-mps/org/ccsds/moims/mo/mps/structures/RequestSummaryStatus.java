package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.Time;

/**
 * E1: RequestSummaryStatus is a data structure used in the context of the
 * MPS Planning Request service getRequestSummaries operation, where a list
 * of these structures is returned.  It contains header fields of the planning
 * request and its status, but not the request content (arguments, activities
 * and constraints).
 */
public final class RequestSummaryStatus implements Composite {

    private static final long serialVersionUID = 1407374900330903L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330903L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Reference to the RequestInstance.
     */
    private ObjectRef<RequestInstance> requestInstance;

    /**
     * Optional user supplied reference for the planning request.  This is distinct
     * from the identity of the RequestInstance that is assigned by the planning
     * function.
     */
    private Identifier userReference;

    /**
     * Creation date and time of the RequestInstance version.
     */
    private Time creationTime;

    /**
     * Reference to the RequestDefinition from which the RequestInstance was created,
     * if a planning request template was used.
     */
    private ObjectRef<RequestDefinition> definition;

    /**
     * Specifies which planning period the planning request applies to.  Planning
     * period IDs are mission specific, but can be used to indicate mission phase;
     * planning cycle; or ‘semester’ in observatory missions.
     */
    private Identifier planningPeriod;

    /**
     * Validity period for the planning request, expressed as one or more time
     * windows.  The planning request must be satisfied within this period. Only
     * one of validityTime or validityEvent should be present in a planning request.
     */
    private TimeWindowList validityTimes;

    /**
     * Validity period for the planning request, expressed as one or more event
     * windows.  The planning request must be satisfied within this period. Only
     * one of validityTime or validityEvent should be present in a planning request.
     */
    private EventWindowList validityEvents;

    /**
     * The User ID for the person or organization raising the planning request.
     */
    private ObjectRef<PlanningUser> user;

    /**
     * Description of the request.
     */
    private String description;

    /**
     * A flag that indicates whether the planning request is for a repetitive
     * standing order (unbounded other than by the validity period), or is a one-off
     * request.
     */
    private Boolean standingOrder;

    /**
     * Free text for any additional user comments about the request.
     */
    private String comments;

    /**
     * Current status of the RequestInstance (see planning request state model
     * in 4.5.5.2).
     */
    private RequestStatusEnum status;

    /**
     * References to output Plan(s) that contains the activities resulting from
     * the planning request.
     */
    private ObjectRefList outputPlanRefs;

    /**
     * StatusInfo provides the reason for termination and is customizable, but
     * includes: - Completed (all constituent activities completed successfully);
     * - Expired (constituent activities expired prior to execution); - Failed
     * (constituent activities failed during execution); - Deleted (constituent
     * activities were deleted); - PartiallyCompleted. It may also be used to
     * provide the reason for rejection.
     */
    private String statusInfo;

    /**
     * Default constructor for RequestSummaryStatus.
     * 
     */
    public RequestSummaryStatus() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param requestInstance Reference to the RequestInstance.
     * @param userReference Optional user supplied reference for the planning request.  This is distinct from the identity of the RequestInstance that is assigned by the planning function.
     * @param creationTime Creation date and time of the RequestInstance version.
     * @param definition Reference to the RequestDefinition from which the RequestInstance was created, if a planning request template was used.
     * @param planningPeriod Specifies which planning period the planning request applies to.  Planning period IDs are mission specific, but can be used to indicate mission phase; planning cycle; or ‘semester’ in observatory missions.
     * @param validityTimes Validity period for the planning request, expressed as one or more time windows.  The planning request must be satisfied within this period. Only one of validityTime or validityEvent should be present in a planning request.
     * @param validityEvents Validity period for the planning request, expressed as one or more event windows.  The planning request must be satisfied within this period. Only one of validityTime or validityEvent should be present in a planning request.
     * @param user The User ID for the person or organization raising the planning request.
     * @param description Description of the request.
     * @param standingOrder A flag that indicates whether the planning request is for a repetitive standing order (unbounded other than by the validity period), or is a one-off request.
     * @param comments Free text for any additional user comments about the request.
     * @param status Current status of the RequestInstance (see planning request state model in 4.5.5.2).
     * @param outputPlanRefs References to output Plan(s) that contains the activities resulting from the planning request.
     * @param statusInfo StatusInfo provides the reason for termination and is customizable, but includes: - Completed (all constituent activities completed successfully); - Expired (constituent activities expired prior to execution); - Failed (constituent activities failed during execution); - Deleted (constituent activities were deleted); - PartiallyCompleted. It may also be used to provide the reason for rejection.
     */
    public RequestSummaryStatus(ObjectRef<RequestInstance> requestInstance,
            Identifier userReference,
            Time creationTime,
            ObjectRef<RequestDefinition> definition,
            Identifier planningPeriod,
            TimeWindowList validityTimes,
            EventWindowList validityEvents,
            ObjectRef<PlanningUser> user,
            String description,
            Boolean standingOrder,
            String comments,
            RequestStatusEnum status,
            ObjectRefList outputPlanRefs,
            String statusInfo) {
        this.requestInstance = requestInstance;
        this.userReference = userReference;
        this.creationTime = creationTime;
        this.definition = definition;
        this.planningPeriod = planningPeriod;
        this.validityTimes = validityTimes;
        this.validityEvents = validityEvents;
        this.user = user;
        this.description = description;
        this.standingOrder = standingOrder;
        this.comments = comments;
        this.status = status;
        this.outputPlanRefs = outputPlanRefs;
        this.statusInfo = statusInfo;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param requestInstance Reference to the RequestInstance.
     * @param creationTime Creation date and time of the RequestInstance version.
     * @param planningPeriod Specifies which planning period the planning request applies to.  Planning period IDs are mission specific, but can be used to indicate mission phase; planning cycle; or ‘semester’ in observatory missions.
     * @param user The User ID for the person or organization raising the planning request.
     * @param description Description of the request.
     * @param standingOrder A flag that indicates whether the planning request is for a repetitive standing order (unbounded other than by the validity period), or is a one-off request.
     * @param status Current status of the RequestInstance (see planning request state model in 4.5.5.2).
     */
    public RequestSummaryStatus(ObjectRef<RequestInstance> requestInstance,
            Time creationTime,
            Identifier planningPeriod,
            ObjectRef<PlanningUser> user,
            String description,
            Boolean standingOrder,
            RequestStatusEnum status) {
        this.requestInstance = requestInstance;
        this.userReference = null;
        this.creationTime = creationTime;
        this.definition = null;
        this.planningPeriod = planningPeriod;
        this.validityTimes = null;
        this.validityEvents = null;
        this.user = user;
        this.description = description;
        this.standingOrder = standingOrder;
        this.comments = null;
        this.status = status;
        this.outputPlanRefs = null;
        this.statusInfo = null;
    }

    @Override
    public Element createElement() {
        return new RequestSummaryStatus();
    }

    /**
     * Returns the field requestInstance.
     * 
     * @return The field requestInstance
     */
    public ObjectRef<RequestInstance> getRequestInstance() {
        return requestInstance;
    }

    /**
     * Returns the field userReference.
     * 
     * @return The field userReference
     */
    public Identifier getUserReference() {
        return userReference;
    }

    /**
     * Returns the field creationTime.
     * 
     * @return The field creationTime
     */
    public Time getCreationTime() {
        return creationTime;
    }

    /**
     * Returns the field definition.
     * 
     * @return The field definition
     */
    public ObjectRef<RequestDefinition> getDefinition() {
        return definition;
    }

    /**
     * Returns the field planningPeriod.
     * 
     * @return The field planningPeriod
     */
    public Identifier getPlanningPeriod() {
        return planningPeriod;
    }

    /**
     * Returns the field validityTimes.
     * 
     * @return The field validityTimes
     */
    public TimeWindowList getValidityTimes() {
        return validityTimes;
    }

    /**
     * Returns the field validityEvents.
     * 
     * @return The field validityEvents
     */
    public EventWindowList getValidityEvents() {
        return validityEvents;
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
     * Returns the field description.
     * 
     * @return The field description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the field standingOrder.
     * 
     * @return The field standingOrder
     */
    public Boolean getStandingOrder() {
        return standingOrder;
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
     * Returns the field status.
     * 
     * @return The field status
     */
    public RequestStatusEnum getStatus() {
        return status;
    }

    /**
     * Returns the field outputPlanRefs.
     * 
     * @return The field outputPlanRefs
     */
    public ObjectRefList getOutputPlanRefs() {
        return outputPlanRefs;
    }

    /**
     * Returns the field statusInfo.
     * 
     * @return The field statusInfo
     */
    public String getStatusInfo() {
        return statusInfo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RequestSummaryStatus) {
            RequestSummaryStatus other = (RequestSummaryStatus) obj;
            if (requestInstance == null) {
                if (other.requestInstance != null) {
                    return false;
                }
            } else {
                if (! requestInstance.equals(other.requestInstance)) {
                    return false;
                }
            }
            if (userReference == null) {
                if (other.userReference != null) {
                    return false;
                }
            } else {
                if (! userReference.equals(other.userReference)) {
                    return false;
                }
            }
            if (creationTime == null) {
                if (other.creationTime != null) {
                    return false;
                }
            } else {
                if (! creationTime.equals(other.creationTime)) {
                    return false;
                }
            }
            if (definition == null) {
                if (other.definition != null) {
                    return false;
                }
            } else {
                if (! definition.equals(other.definition)) {
                    return false;
                }
            }
            if (planningPeriod == null) {
                if (other.planningPeriod != null) {
                    return false;
                }
            } else {
                if (! planningPeriod.equals(other.planningPeriod)) {
                    return false;
                }
            }
            if (validityTimes == null) {
                if (other.validityTimes != null) {
                    return false;
                }
            } else {
                if (! validityTimes.equals(other.validityTimes)) {
                    return false;
                }
            }
            if (validityEvents == null) {
                if (other.validityEvents != null) {
                    return false;
                }
            } else {
                if (! validityEvents.equals(other.validityEvents)) {
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
            if (description == null) {
                if (other.description != null) {
                    return false;
                }
            } else {
                if (! description.equals(other.description)) {
                    return false;
                }
            }
            if (standingOrder == null) {
                if (other.standingOrder != null) {
                    return false;
                }
            } else {
                if (! standingOrder.equals(other.standingOrder)) {
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
            if (status == null) {
                if (other.status != null) {
                    return false;
                }
            } else {
                if (! status.equals(other.status)) {
                    return false;
                }
            }
            if (outputPlanRefs == null) {
                if (other.outputPlanRefs != null) {
                    return false;
                }
            } else {
                if (! outputPlanRefs.equals(other.outputPlanRefs)) {
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
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (requestInstance != null ? requestInstance.hashCode() : 0);
        hash = 83 * hash + (userReference != null ? userReference.hashCode() : 0);
        hash = 83 * hash + (creationTime != null ? creationTime.hashCode() : 0);
        hash = 83 * hash + (definition != null ? definition.hashCode() : 0);
        hash = 83 * hash + (planningPeriod != null ? planningPeriod.hashCode() : 0);
        hash = 83 * hash + (validityTimes != null ? validityTimes.hashCode() : 0);
        hash = 83 * hash + (validityEvents != null ? validityEvents.hashCode() : 0);
        hash = 83 * hash + (user != null ? user.hashCode() : 0);
        hash = 83 * hash + (description != null ? description.hashCode() : 0);
        hash = 83 * hash + (standingOrder != null ? standingOrder.hashCode() : 0);
        hash = 83 * hash + (comments != null ? comments.hashCode() : 0);
        hash = 83 * hash + (status != null ? status.hashCode() : 0);
        hash = 83 * hash + (outputPlanRefs != null ? outputPlanRefs.hashCode() : 0);
        hash = 83 * hash + (statusInfo != null ? statusInfo.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(RequestSummaryStatus: ");
        buf.append("requestInstance=").append(requestInstance);
        buf.append(", userReference=").append(userReference);
        buf.append(", creationTime=").append(creationTime);
        buf.append(", definition=").append(definition);
        buf.append(", planningPeriod=").append(planningPeriod);
        buf.append(", validityTimes=").append(validityTimes);
        buf.append(", validityEvents=").append(validityEvents);
        buf.append(", user=").append(user);
        buf.append(", description=").append(description);
        buf.append(", standingOrder=").append(standingOrder);
        buf.append(", comments=").append(comments);
        buf.append(", status=").append(status);
        buf.append(", outputPlanRefs=").append(outputPlanRefs);
        buf.append(", statusInfo=").append(statusInfo);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (requestInstance == null) {
            throw new MALException("The field 'requestInstance' cannot be null!");
        }
        if (creationTime == null) {
            throw new MALException("The field 'creationTime' cannot be null!");
        }
        if (planningPeriod == null) {
            throw new MALException("The field 'planningPeriod' cannot be null!");
        }
        if (user == null) {
            throw new MALException("The field 'user' cannot be null!");
        }
        if (description == null) {
            throw new MALException("The field 'description' cannot be null!");
        }
        if (standingOrder == null) {
            throw new MALException("The field 'standingOrder' cannot be null!");
        }
        if (status == null) {
            throw new MALException("The field 'status' cannot be null!");
        }
        encoder.encodeElement(requestInstance);
        encoder.encodeNullableIdentifier(userReference);
        encoder.encodeTime(creationTime);
        encoder.encodeNullableElement(definition);
        encoder.encodeIdentifier(planningPeriod);
        encoder.encodeNullableElement(validityTimes);
        encoder.encodeNullableElement(validityEvents);
        encoder.encodeElement(user);
        encoder.encodeString(description);
        encoder.encodeBoolean(standingOrder);
        encoder.encodeNullableString(comments);
        encoder.encodeElement(status);
        encoder.encodeNullableElement(outputPlanRefs);
        encoder.encodeNullableString(statusInfo);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        requestInstance = (ObjectRef<RequestInstance>) decoder.decodeElement(new ObjectRef<RequestInstance>());
        userReference = decoder.decodeNullableIdentifier();
        creationTime = decoder.decodeTime();
        definition = (ObjectRef<RequestDefinition>) decoder.decodeNullableElement(new ObjectRef<RequestDefinition>());
        planningPeriod = decoder.decodeIdentifier();
        validityTimes = (TimeWindowList) decoder.decodeNullableElement(new TimeWindowList());
        validityEvents = (EventWindowList) decoder.decodeNullableElement(new EventWindowList());
        user = (ObjectRef<PlanningUser>) decoder.decodeElement(new ObjectRef<PlanningUser>());
        description = decoder.decodeString();
        standingOrder = decoder.decodeBoolean();
        comments = decoder.decodeNullableString();
        status = (RequestStatusEnum) decoder.decodeElement(RequestStatusEnum.REQUESTED);
        outputPlanRefs = (ObjectRefList) decoder.decodeNullableElement(new ObjectRefList());
        statusInfo = decoder.decodeNullableString();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
