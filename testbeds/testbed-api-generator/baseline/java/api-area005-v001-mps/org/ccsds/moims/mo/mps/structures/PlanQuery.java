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

/**
 * E1: PlanQuery is a data structure used in the context of queryPlan operation
 * of the MPS Plan Distribution Service.  It is used to specify search criteria
 * for querying the available set of Plans. .
 */
public final class PlanQuery implements Composite {

    private static final long serialVersionUID = 1407374900331010L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900331010L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Query for Plans with the specified PlanID.
     */
    private ObjectRef<Plan> planID;

    /**
     * Query for Plans with or without a precursor.
     */
    private Boolean hasPrecursor;

    /**
     * Query for Plans that are or are not patch plans.
     */
    private Boolean isPatchPlan;

    /**
     * Query for Plans with the specified precursor Plan.
     */
    private ObjectRef<Plan> precursorPlan;

    /**
     * Applicable only for patch plans.  Query for patch plans that have the specified
     * target Plan.
     */
    private ObjectRef<Plan> targetPlan;

    /**
     * Query for Plans with the specified originator.
     */
    private Identifier originator;

    /**
     * Query for Plans with a production date in the specified range.
     */
    private TimeWindow productionTime;

    /**
     * Query for Plans with a validity period within (overlapping with) the specified
     * range.
     */
    private TimeWindow validityPeriod;

    /**
     * Query for Plans that are or are not Alternate plans.
     */
    private Boolean isAlternate;

    /**
     * Query for Plans that have a current status matching one of the specified
     * Plan statuses.
     */
    private PlanStatusEnumList status;

    /**
     * Query for Plans that contain EventInstances inside plannedItems whose definition
     * matches one of the specified EventDefinitions.
     */
    private ObjectRefList plannedEvents;

    /**
     * Query for Plans that contain ActivityInstances inside plannedItems whose
     * definition matches one of the specified ActivityDefinitions.
     */
    private ObjectRefList plannedActivities;

    /**
     * Query for patch plans that contain EventInstances inside their revisions
     * whose definition matches one of the specified EventDefinitions. If the
     * revisionStatus is either New or Modified, then the EventInstances in the
     * current plan shall be checked.  If the revisionStatus is either Deleted
     * or Undefined, then the EventInstances in the revised plan shall be checked.
     * If the revised plan is not available anymore, then the result will depend
     * on the implementation.
     */
    private ObjectRefList revisedEvents;

    /**
     * Query for patch plans that contain ActivityInstances inside their revisions
     * whose definition matches one of the specified ActivityDefinitions. If the
     * revisionStatus is either New or Modified, then the ActivityInstances in
     * the current plan shall be checked.  If the revisionStatus is either Deleted
     * or Undefined, then the ActivityInstances in the revised plan shall be checked.
     * If the revised plan is not available anymore, then the result will depend
     * on the implementation.
     */
    private ObjectRefList revisedActivities;

    /**
     * Default constructor for PlanQuery.
     * 
     */
    public PlanQuery() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param planID Query for Plans with the specified PlanID.
     * @param hasPrecursor Query for Plans with or without a precursor.
     * @param isPatchPlan Query for Plans that are or are not patch plans.
     * @param precursorPlan Query for Plans with the specified precursor Plan.
     * @param targetPlan Applicable only for patch plans.  Query for patch plans that have the specified target Plan.
     * @param originator Query for Plans with the specified originator.
     * @param productionTime Query for Plans with a production date in the specified range.
     * @param validityPeriod Query for Plans with a validity period within (overlapping with) the specified range.
     * @param isAlternate Query for Plans that are or are not Alternate plans.
     * @param status Query for Plans that have a current status matching one of the specified Plan statuses.
     * @param plannedEvents Query for Plans that contain EventInstances inside plannedItems whose definition matches one of the specified EventDefinitions.
     * @param plannedActivities Query for Plans that contain ActivityInstances inside plannedItems whose definition matches one of the specified ActivityDefinitions.
     * @param revisedEvents Query for patch plans that contain EventInstances inside their revisions whose definition matches one of the specified EventDefinitions. If the revisionStatus is either New or Modified, then the EventInstances in the current plan shall be checked.  If the revisionStatus is either Deleted or Undefined, then the EventInstances in the revised plan shall be checked.  If the revised plan is not available anymore, then the result will depend on the implementation.
     * @param revisedActivities Query for patch plans that contain ActivityInstances inside their revisions whose definition matches one of the specified ActivityDefinitions. If the revisionStatus is either New or Modified, then the ActivityInstances in the current plan shall be checked.  If the revisionStatus is either Deleted or Undefined, then the ActivityInstances in the revised plan shall be checked.  If the revised plan is not available anymore, then the result will depend on the implementation.
     */
    public PlanQuery(ObjectRef<Plan> planID,
            Boolean hasPrecursor,
            Boolean isPatchPlan,
            ObjectRef<Plan> precursorPlan,
            ObjectRef<Plan> targetPlan,
            Identifier originator,
            TimeWindow productionTime,
            TimeWindow validityPeriod,
            Boolean isAlternate,
            PlanStatusEnumList status,
            ObjectRefList plannedEvents,
            ObjectRefList plannedActivities,
            ObjectRefList revisedEvents,
            ObjectRefList revisedActivities) {
        this.planID = planID;
        this.hasPrecursor = hasPrecursor;
        this.isPatchPlan = isPatchPlan;
        this.precursorPlan = precursorPlan;
        this.targetPlan = targetPlan;
        this.originator = originator;
        this.productionTime = productionTime;
        this.validityPeriod = validityPeriod;
        this.isAlternate = isAlternate;
        this.status = status;
        this.plannedEvents = plannedEvents;
        this.plannedActivities = plannedActivities;
        this.revisedEvents = revisedEvents;
        this.revisedActivities = revisedActivities;
    }

    @Override
    public Element createElement() {
        return new PlanQuery();
    }

    /**
     * Returns the field planID.
     * 
     * @return The field planID
     */
    public ObjectRef<Plan> getPlanID() {
        return planID;
    }

    /**
     * Returns the field hasPrecursor.
     * 
     * @return The field hasPrecursor
     */
    public Boolean getHasPrecursor() {
        return hasPrecursor;
    }

    /**
     * Returns the field isPatchPlan.
     * 
     * @return The field isPatchPlan
     */
    public Boolean getIsPatchPlan() {
        return isPatchPlan;
    }

    /**
     * Returns the field precursorPlan.
     * 
     * @return The field precursorPlan
     */
    public ObjectRef<Plan> getPrecursorPlan() {
        return precursorPlan;
    }

    /**
     * Returns the field targetPlan.
     * 
     * @return The field targetPlan
     */
    public ObjectRef<Plan> getTargetPlan() {
        return targetPlan;
    }

    /**
     * Returns the field originator.
     * 
     * @return The field originator
     */
    public Identifier getOriginator() {
        return originator;
    }

    /**
     * Returns the field productionTime.
     * 
     * @return The field productionTime
     */
    public TimeWindow getProductionTime() {
        return productionTime;
    }

    /**
     * Returns the field validityPeriod.
     * 
     * @return The field validityPeriod
     */
    public TimeWindow getValidityPeriod() {
        return validityPeriod;
    }

    /**
     * Returns the field isAlternate.
     * 
     * @return The field isAlternate
     */
    public Boolean getIsAlternate() {
        return isAlternate;
    }

    /**
     * Returns the field status.
     * 
     * @return The field status
     */
    public PlanStatusEnumList getStatus() {
        return status;
    }

    /**
     * Returns the field plannedEvents.
     * 
     * @return The field plannedEvents
     */
    public ObjectRefList getPlannedEvents() {
        return plannedEvents;
    }

    /**
     * Returns the field plannedActivities.
     * 
     * @return The field plannedActivities
     */
    public ObjectRefList getPlannedActivities() {
        return plannedActivities;
    }

    /**
     * Returns the field revisedEvents.
     * 
     * @return The field revisedEvents
     */
    public ObjectRefList getRevisedEvents() {
        return revisedEvents;
    }

    /**
     * Returns the field revisedActivities.
     * 
     * @return The field revisedActivities
     */
    public ObjectRefList getRevisedActivities() {
        return revisedActivities;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlanQuery) {
            PlanQuery other = (PlanQuery) obj;
            if (planID == null) {
                if (other.planID != null) {
                    return false;
                }
            } else {
                if (! planID.equals(other.planID)) {
                    return false;
                }
            }
            if (hasPrecursor == null) {
                if (other.hasPrecursor != null) {
                    return false;
                }
            } else {
                if (! hasPrecursor.equals(other.hasPrecursor)) {
                    return false;
                }
            }
            if (isPatchPlan == null) {
                if (other.isPatchPlan != null) {
                    return false;
                }
            } else {
                if (! isPatchPlan.equals(other.isPatchPlan)) {
                    return false;
                }
            }
            if (precursorPlan == null) {
                if (other.precursorPlan != null) {
                    return false;
                }
            } else {
                if (! precursorPlan.equals(other.precursorPlan)) {
                    return false;
                }
            }
            if (targetPlan == null) {
                if (other.targetPlan != null) {
                    return false;
                }
            } else {
                if (! targetPlan.equals(other.targetPlan)) {
                    return false;
                }
            }
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
            if (validityPeriod == null) {
                if (other.validityPeriod != null) {
                    return false;
                }
            } else {
                if (! validityPeriod.equals(other.validityPeriod)) {
                    return false;
                }
            }
            if (isAlternate == null) {
                if (other.isAlternate != null) {
                    return false;
                }
            } else {
                if (! isAlternate.equals(other.isAlternate)) {
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
            if (plannedEvents == null) {
                if (other.plannedEvents != null) {
                    return false;
                }
            } else {
                if (! plannedEvents.equals(other.plannedEvents)) {
                    return false;
                }
            }
            if (plannedActivities == null) {
                if (other.plannedActivities != null) {
                    return false;
                }
            } else {
                if (! plannedActivities.equals(other.plannedActivities)) {
                    return false;
                }
            }
            if (revisedEvents == null) {
                if (other.revisedEvents != null) {
                    return false;
                }
            } else {
                if (! revisedEvents.equals(other.revisedEvents)) {
                    return false;
                }
            }
            if (revisedActivities == null) {
                if (other.revisedActivities != null) {
                    return false;
                }
            } else {
                if (! revisedActivities.equals(other.revisedActivities)) {
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
        hash = 83 * hash + (planID != null ? planID.hashCode() : 0);
        hash = 83 * hash + (hasPrecursor != null ? hasPrecursor.hashCode() : 0);
        hash = 83 * hash + (isPatchPlan != null ? isPatchPlan.hashCode() : 0);
        hash = 83 * hash + (precursorPlan != null ? precursorPlan.hashCode() : 0);
        hash = 83 * hash + (targetPlan != null ? targetPlan.hashCode() : 0);
        hash = 83 * hash + (originator != null ? originator.hashCode() : 0);
        hash = 83 * hash + (productionTime != null ? productionTime.hashCode() : 0);
        hash = 83 * hash + (validityPeriod != null ? validityPeriod.hashCode() : 0);
        hash = 83 * hash + (isAlternate != null ? isAlternate.hashCode() : 0);
        hash = 83 * hash + (status != null ? status.hashCode() : 0);
        hash = 83 * hash + (plannedEvents != null ? plannedEvents.hashCode() : 0);
        hash = 83 * hash + (plannedActivities != null ? plannedActivities.hashCode() : 0);
        hash = 83 * hash + (revisedEvents != null ? revisedEvents.hashCode() : 0);
        hash = 83 * hash + (revisedActivities != null ? revisedActivities.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(PlanQuery: ");
        buf.append("planID=").append(planID);
        buf.append(", hasPrecursor=").append(hasPrecursor);
        buf.append(", isPatchPlan=").append(isPatchPlan);
        buf.append(", precursorPlan=").append(precursorPlan);
        buf.append(", targetPlan=").append(targetPlan);
        buf.append(", originator=").append(originator);
        buf.append(", productionTime=").append(productionTime);
        buf.append(", validityPeriod=").append(validityPeriod);
        buf.append(", isAlternate=").append(isAlternate);
        buf.append(", status=").append(status);
        buf.append(", plannedEvents=").append(plannedEvents);
        buf.append(", plannedActivities=").append(plannedActivities);
        buf.append(", revisedEvents=").append(revisedEvents);
        buf.append(", revisedActivities=").append(revisedActivities);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        encoder.encodeNullableElement(planID);
        encoder.encodeNullableBoolean(hasPrecursor);
        encoder.encodeNullableBoolean(isPatchPlan);
        encoder.encodeNullableElement(precursorPlan);
        encoder.encodeNullableElement(targetPlan);
        encoder.encodeNullableIdentifier(originator);
        encoder.encodeNullableElement(productionTime);
        encoder.encodeNullableElement(validityPeriod);
        encoder.encodeNullableBoolean(isAlternate);
        encoder.encodeNullableElement(status);
        encoder.encodeNullableElement(plannedEvents);
        encoder.encodeNullableElement(plannedActivities);
        encoder.encodeNullableElement(revisedEvents);
        encoder.encodeNullableElement(revisedActivities);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        planID = (ObjectRef<Plan>) decoder.decodeNullableElement(new ObjectRef<Plan>());
        hasPrecursor = decoder.decodeNullableBoolean();
        isPatchPlan = decoder.decodeNullableBoolean();
        precursorPlan = (ObjectRef<Plan>) decoder.decodeNullableElement(new ObjectRef<Plan>());
        targetPlan = (ObjectRef<Plan>) decoder.decodeNullableElement(new ObjectRef<Plan>());
        originator = decoder.decodeNullableIdentifier();
        productionTime = (TimeWindow) decoder.decodeNullableElement(new TimeWindow());
        validityPeriod = (TimeWindow) decoder.decodeNullableElement(new TimeWindow());
        isAlternate = decoder.decodeNullableBoolean();
        status = (PlanStatusEnumList) decoder.decodeNullableElement(new PlanStatusEnumList());
        plannedEvents = (ObjectRefList) decoder.decodeNullableElement(new ObjectRefList());
        plannedActivities = (ObjectRefList) decoder.decodeNullableElement(new ObjectRefList());
        revisedEvents = (ObjectRefList) decoder.decodeNullableElement(new ObjectRefList());
        revisedActivities = (ObjectRefList) decoder.decodeNullableElement(new ObjectRefList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
