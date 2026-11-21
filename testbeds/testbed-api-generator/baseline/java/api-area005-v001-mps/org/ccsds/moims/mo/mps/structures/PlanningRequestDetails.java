package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.ObjectRef;

/**
 * E1: PlanningRequestDetails is a data structure used in the context of the
 * MPS Planning Request service submitRequest and updateRequest operations,
 * where the RequestInstance MO object cannot be used, because the full identity
 * of the resulting RequestInstance is not yet known at the time of submitting
 * or updating the request.
 */
public final class PlanningRequestDetails implements Composite {

    private static final long serialVersionUID = 1407374900330901L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330901L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * User supplied reference for the planning request.  This is distinct from
     * the identity of the RequestInstance that is assigned by the planning function.
     * No guarantees are made by the planning system about the contents of this
     * identifier; that is entirely up to the user who supplies the reference.
     */
    private Identifier userReference;

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
     * windows.  The planning request must be satisfied within this period. When
     * multiple TimeWindows are provided, the planning request may be satisfied
     * within any individual TimeWindow. If this field is null, no restriction
     * is placed on the times between which this request must be planned.
     */
    private TimeWindowList validityTimes;

    /**
     * Validity period for the planning request, expressed as one or more event
     * windows.  The planning request must be satisfied within this period. When
     * multiple EventWindows are provided, the planning request may be satisfied
     * within any individual EventWindow. If this field is null, no restriction
     * is placed on any events between which this request must be planned.
     */
    private EventWindowList validityEvents;

    /**
     * Specifies the time system used for all time fields within the planning
     * request (see 4.4.1). If null, the default time system is used.
     */
    private Identifier timeSystem;

    /**
     * The User ID for the person or organization raising the planning request.
     */
    private ObjectRef<PlanningUser> user;

    /**
     * Description of the request.
     */
    private String description;

    /**
     * List of named argument values.  If created from a template planning request,
     * this will include the arguments defined in the RequestDefinition.
     */
    private ArgumentList arguments;

    /**
     * A flag that indicates whether the planning request is for a repetitive
     * standing order (unbounded other than by the validity period), or is a one-off
     * request.  If it is a standing order, then the supplied activity details
     * must be an ActivityNode with specification of the repetition criteria.
     * It should be noted that a one-off request can still include repetition.
     */
    private Boolean standingOrder;

    /**
     * Set of activity details specifying requested activities.
     */
    private ActivityDetailsList activities;

    /**
     * Reference to an existing Plan (output of one planning function) submitted
     * as a planning request to another planning function in the context of a
     * distributed or hierarchical planning system. Only one of inputPlanRef and
     * inputPlan should be present within the planning request.
     */
    private ObjectRef<Plan> inputPlanRef;

    /**
     * An existing Plan  (output of one planning function) submitted as a planning
     * request to another planning function in the context of a distributed or
     * hierarchical planning system.  The Plan is embedded within the planning
     * request. Only one of inputPlanRef and inputPlan should be present within
     * the planning request.
     */
    private Plan inputPlan;

    /**
     * Free text for any additional user comments about the request.
     */
    private String comments;

    /**
     * Default constructor for PlanningRequestDetails.
     * 
     */
    public PlanningRequestDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param userReference User supplied reference for the planning request.  This is distinct from the identity of the RequestInstance that is assigned by the planning function.  No guarantees are made by the planning system about the contents of this identifier; that is entirely up to the user who supplies the reference.
     * @param definition Reference to the RequestDefinition from which the RequestInstance was created, if a planning request template was used.
     * @param planningPeriod Specifies which planning period the planning request applies to.  Planning period IDs are mission specific, but can be used to indicate mission phase; planning cycle; or ‘semester’ in observatory missions.
     * @param validityTimes Validity period for the planning request, expressed as one or more time windows.  The planning request must be satisfied within this period. When multiple TimeWindows are provided, the planning request may be satisfied within any individual TimeWindow. If this field is null, no restriction is placed on the times between which this request must be planned.
     * @param validityEvents Validity period for the planning request, expressed as one or more event windows.  The planning request must be satisfied within this period. When multiple EventWindows are provided, the planning request may be satisfied within any individual EventWindow. If this field is null, no restriction is placed on any events between which this request must be planned.
     * @param timeSystem Specifies the time system used for all time fields within the planning request (see 4.4.1). If null, the default time system is used.
     * @param user The User ID for the person or organization raising the planning request.
     * @param description Description of the request.
     * @param arguments List of named argument values.  If created from a template planning request, this will include the arguments defined in the RequestDefinition.
     * @param standingOrder A flag that indicates whether the planning request is for a repetitive standing order (unbounded other than by the validity period), or is a one-off request.  If it is a standing order, then the supplied activity details must be an ActivityNode with specification of the repetition criteria.  It should be noted that a one-off request can still include repetition.
     * @param activities Set of activity details specifying requested activities.
     * @param inputPlanRef Reference to an existing Plan (output of one planning function) submitted as a planning request to another planning function in the context of a distributed or hierarchical planning system. Only one of inputPlanRef and inputPlan should be present within the planning request.
     * @param inputPlan An existing Plan  (output of one planning function) submitted as a planning request to another planning function in the context of a distributed or hierarchical planning system.  The Plan is embedded within the planning request. Only one of inputPlanRef and inputPlan should be present within the planning request.
     * @param comments Free text for any additional user comments about the request.
     */
    public PlanningRequestDetails(Identifier userReference,
            ObjectRef<RequestDefinition> definition,
            Identifier planningPeriod,
            TimeWindowList validityTimes,
            EventWindowList validityEvents,
            Identifier timeSystem,
            ObjectRef<PlanningUser> user,
            String description,
            ArgumentList arguments,
            Boolean standingOrder,
            ActivityDetailsList activities,
            ObjectRef<Plan> inputPlanRef,
            Plan inputPlan,
            String comments) {
        this.userReference = userReference;
        this.definition = definition;
        this.planningPeriod = planningPeriod;
        this.validityTimes = validityTimes;
        this.validityEvents = validityEvents;
        this.timeSystem = timeSystem;
        this.user = user;
        this.description = description;
        this.arguments = arguments;
        this.standingOrder = standingOrder;
        this.activities = activities;
        this.inputPlanRef = inputPlanRef;
        this.inputPlan = inputPlan;
        this.comments = comments;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param userReference User supplied reference for the planning request.  This is distinct from the identity of the RequestInstance that is assigned by the planning function.  No guarantees are made by the planning system about the contents of this identifier; that is entirely up to the user who supplies the reference.
     * @param planningPeriod Specifies which planning period the planning request applies to.  Planning period IDs are mission specific, but can be used to indicate mission phase; planning cycle; or ‘semester’ in observatory missions.
     * @param user The User ID for the person or organization raising the planning request.
     * @param description Description of the request.
     * @param standingOrder A flag that indicates whether the planning request is for a repetitive standing order (unbounded other than by the validity period), or is a one-off request.  If it is a standing order, then the supplied activity details must be an ActivityNode with specification of the repetition criteria.  It should be noted that a one-off request can still include repetition.
     */
    public PlanningRequestDetails(Identifier userReference,
            Identifier planningPeriod,
            ObjectRef<PlanningUser> user,
            String description,
            Boolean standingOrder) {
        this.userReference = userReference;
        this.definition = null;
        this.planningPeriod = planningPeriod;
        this.validityTimes = null;
        this.validityEvents = null;
        this.timeSystem = null;
        this.user = user;
        this.description = description;
        this.arguments = null;
        this.standingOrder = standingOrder;
        this.activities = null;
        this.inputPlanRef = null;
        this.inputPlan = null;
        this.comments = null;
    }

    @Override
    public Element createElement() {
        return new PlanningRequestDetails();
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
     * Returns the field timeSystem.
     * 
     * @return The field timeSystem
     */
    public Identifier getTimeSystem() {
        return timeSystem;
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
     * Returns the field arguments.
     * 
     * @return The field arguments
     */
    public ArgumentList getArguments() {
        return arguments;
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
     * Returns the field activities.
     * 
     * @return The field activities
     */
    public ActivityDetailsList getActivities() {
        return activities;
    }

    /**
     * Returns the field inputPlanRef.
     * 
     * @return The field inputPlanRef
     */
    public ObjectRef<Plan> getInputPlanRef() {
        return inputPlanRef;
    }

    /**
     * Returns the field inputPlan.
     * 
     * @return The field inputPlan
     */
    public Plan getInputPlan() {
        return inputPlan;
    }

    /**
     * Returns the field comments.
     * 
     * @return The field comments
     */
    public String getComments() {
        return comments;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlanningRequestDetails) {
            PlanningRequestDetails other = (PlanningRequestDetails) obj;
            if (userReference == null) {
                if (other.userReference != null) {
                    return false;
                }
            } else {
                if (! userReference.equals(other.userReference)) {
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
            if (timeSystem == null) {
                if (other.timeSystem != null) {
                    return false;
                }
            } else {
                if (! timeSystem.equals(other.timeSystem)) {
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
            if (arguments == null) {
                if (other.arguments != null) {
                    return false;
                }
            } else {
                if (! arguments.equals(other.arguments)) {
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
            if (activities == null) {
                if (other.activities != null) {
                    return false;
                }
            } else {
                if (! activities.equals(other.activities)) {
                    return false;
                }
            }
            if (inputPlanRef == null) {
                if (other.inputPlanRef != null) {
                    return false;
                }
            } else {
                if (! inputPlanRef.equals(other.inputPlanRef)) {
                    return false;
                }
            }
            if (inputPlan == null) {
                if (other.inputPlan != null) {
                    return false;
                }
            } else {
                if (! inputPlan.equals(other.inputPlan)) {
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
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (userReference != null ? userReference.hashCode() : 0);
        hash = 83 * hash + (definition != null ? definition.hashCode() : 0);
        hash = 83 * hash + (planningPeriod != null ? planningPeriod.hashCode() : 0);
        hash = 83 * hash + (validityTimes != null ? validityTimes.hashCode() : 0);
        hash = 83 * hash + (validityEvents != null ? validityEvents.hashCode() : 0);
        hash = 83 * hash + (timeSystem != null ? timeSystem.hashCode() : 0);
        hash = 83 * hash + (user != null ? user.hashCode() : 0);
        hash = 83 * hash + (description != null ? description.hashCode() : 0);
        hash = 83 * hash + (arguments != null ? arguments.hashCode() : 0);
        hash = 83 * hash + (standingOrder != null ? standingOrder.hashCode() : 0);
        hash = 83 * hash + (activities != null ? activities.hashCode() : 0);
        hash = 83 * hash + (inputPlanRef != null ? inputPlanRef.hashCode() : 0);
        hash = 83 * hash + (inputPlan != null ? inputPlan.hashCode() : 0);
        hash = 83 * hash + (comments != null ? comments.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(PlanningRequestDetails: ");
        buf.append("userReference=").append(userReference);
        buf.append(", definition=").append(definition);
        buf.append(", planningPeriod=").append(planningPeriod);
        buf.append(", validityTimes=").append(validityTimes);
        buf.append(", validityEvents=").append(validityEvents);
        buf.append(", timeSystem=").append(timeSystem);
        buf.append(", user=").append(user);
        buf.append(", description=").append(description);
        buf.append(", arguments=").append(arguments);
        buf.append(", standingOrder=").append(standingOrder);
        buf.append(", activities=").append(activities);
        buf.append(", inputPlanRef=").append(inputPlanRef);
        buf.append(", inputPlan=").append(inputPlan);
        buf.append(", comments=").append(comments);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (userReference == null) {
            throw new MALException("The field 'userReference' cannot be null!");
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
        encoder.encodeIdentifier(userReference);
        encoder.encodeNullableElement(definition);
        encoder.encodeIdentifier(planningPeriod);
        encoder.encodeNullableElement(validityTimes);
        encoder.encodeNullableElement(validityEvents);
        encoder.encodeNullableIdentifier(timeSystem);
        encoder.encodeElement(user);
        encoder.encodeString(description);
        encoder.encodeNullableElement(arguments);
        encoder.encodeBoolean(standingOrder);
        encoder.encodeNullableElement(activities);
        encoder.encodeNullableElement(inputPlanRef);
        encoder.encodeNullableElement(inputPlan);
        encoder.encodeNullableString(comments);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        userReference = decoder.decodeIdentifier();
        definition = (ObjectRef<RequestDefinition>) decoder.decodeNullableElement(new ObjectRef<RequestDefinition>());
        planningPeriod = decoder.decodeIdentifier();
        validityTimes = (TimeWindowList) decoder.decodeNullableElement(new TimeWindowList());
        validityEvents = (EventWindowList) decoder.decodeNullableElement(new EventWindowList());
        timeSystem = decoder.decodeNullableIdentifier();
        user = (ObjectRef<PlanningUser>) decoder.decodeElement(new ObjectRef<PlanningUser>());
        description = decoder.decodeString();
        arguments = (ArgumentList) decoder.decodeNullableElement(new ArgumentList());
        standingOrder = decoder.decodeBoolean();
        activities = (ActivityDetailsList) decoder.decodeNullableElement(new ActivityDetailsList());
        inputPlanRef = (ObjectRef<Plan>) decoder.decodeNullableElement(new ObjectRef<Plan>());
        inputPlan = (Plan) decoder.decodeNullableElement(new Plan());
        comments = decoder.decodeNullableString();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
