package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.MOObject;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mal.structures.ObjectRef;

/**
 * E1: A Plan is an MO object that contains both the static fields that define
 * a version of a plan and dynamic fields that hold its current state.  Its
 * identity is defined by a constant key and an evolving version, which is
 * updated each time the Plan is revised.
 */
public final class Plan extends MOObject {

    private static final long serialVersionUID = 1407374900330997L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330997L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Flag indicating if the Plan is a patch plan that only contains details
     * of the changes from the precursor Plan.  A patch plan must have a precursor.
     * It must also include a single PlanRevision relative to the precursor Plan.
     */
    private Boolean isPatchPlan;

    /**
     * Reference to a precursor (or predecessor) Plan from which the changes are
     * detailed in the Plan.  This may be used if there is an iterative re-planning
     * cycle in which successive plans overlap, or where a previous Plan has been
     * updated through re-planning.  If there is no precursor, then the Plan must
     * be a self-standing full plan. If the Plan is a Patch Plan, then a precursor
     * plan must be specified.
     */
    private ObjectRef<Plan> precursorPlan;

    /**
     * Applicable only for patch plans, this is a reference to the target Plan.
     * This target Plan is the result of applying the patch plan to the precursor
     * Plan and is distinct from the identity of the patch plan itself.  Patch
     * plans are not permitted in the context of a planning request.
     */
    private ObjectRef<Plan> targetPlan;

    /**
     * Contains header information relating to the Plan, including its originator
     * and validity period.
     */
    private PlanInformation information;

    /**
     * Contains the planned activities and events that constitute the Plan.
     */
    private PlannedItems items;

    /**
     * Details the changes between this Plan and other Plans (or other versions
     * of the same Plan), usually the precursor Plan.  Optional, but must contain
     * at least one element in a patch plan. Multiple revisions may be included,
     * documenting the differences with any other version of a Plan.  This can
     * be used to provide a change history for successive versions of the same
     * Plan, or to document the differences between alternate Plans.
     */
    private PlanRevisionList revisions;

    /**
     * If present, must contain one ResourceProfile per planning resource.  These
     * profiles shall provide the projected evolution of the value of a planning
     * resource, or its initial value at the start of the Plan.  Which approach
     * is used is a deployment choice.
     */
    private ResourceProfileList resources;

    /**
     * Flag indicating if the Plan has currently been released as an Operational
     * or Alternate plan.
     */
    private Boolean isAlternate;

    /**
     * Current status of the Plan.
     */
    private PlanStatusEnum status;

    /**
     * Supplementary information for a Plan in the Terminated state.  This is
     * customizable, but if the following conditions exist then the specified
     * text shall be used: - Completed (nominal); - Superseded (by a successor
     * Plan); - Revoked; - Cancelled (deactivated after start of execution); -
     * Expired.
     */
    private String statusInfo;

    /**
     * Default constructor for Plan.
     * 
     */
    public Plan() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param isPatchPlan Flag indicating if the Plan is a patch plan that only contains details of the changes from the precursor Plan.  A patch plan must have a precursor.  It must also include a single PlanRevision relative to the precursor Plan.
     * @param precursorPlan Reference to a precursor (or predecessor) Plan from which the changes are detailed in the Plan.  This may be used if there is an iterative re-planning cycle in which successive plans overlap, or where a previous Plan has been updated through re-planning.  If there is no precursor, then the Plan must be a self-standing full plan. If the Plan is a Patch Plan, then a precursor plan must be specified.
     * @param targetPlan Applicable only for patch plans, this is a reference to the target Plan.  This target Plan is the result of applying the patch plan to the precursor Plan and is distinct from the identity of the patch plan itself.  Patch plans are not permitted in the context of a planning request.
     * @param information Contains header information relating to the Plan, including its originator and validity period.
     * @param items Contains the planned activities and events that constitute the Plan.
     * @param revisions Details the changes between this Plan and other Plans (or other versions of the same Plan), usually the precursor Plan.  Optional, but must contain at least one element in a patch plan. Multiple revisions may be included, documenting the differences with any other version of a Plan.  This can be used to provide a change history for successive versions of the same Plan, or to document the differences between alternate Plans.
     * @param resources If present, must contain one ResourceProfile per planning resource.  These profiles shall provide the projected evolution of the value of a planning resource, or its initial value at the start of the Plan.  Which approach is used is a deployment choice.
     * @param isAlternate Flag indicating if the Plan has currently been released as an Operational or Alternate plan.
     * @param status Current status of the Plan.
     * @param statusInfo Supplementary information for a Plan in the Terminated state.  This is customizable, but if the following conditions exist then the specified text shall be used: - Completed (nominal); - Superseded (by a successor Plan); - Revoked; - Cancelled (deactivated after start of execution); - Expired.
     */
    public Plan(ObjectIdentity objectIdentity,
            Boolean isPatchPlan,
            ObjectRef<Plan> precursorPlan,
            ObjectRef<Plan> targetPlan,
            PlanInformation information,
            PlannedItems items,
            PlanRevisionList revisions,
            ResourceProfileList resources,
            Boolean isAlternate,
            PlanStatusEnum status,
            String statusInfo) {
        super(objectIdentity);
        this.isPatchPlan = isPatchPlan;
        this.precursorPlan = precursorPlan;
        this.targetPlan = targetPlan;
        this.information = information;
        this.items = items;
        this.revisions = revisions;
        this.resources = resources;
        this.isAlternate = isAlternate;
        this.status = status;
        this.statusInfo = statusInfo;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param isPatchPlan Flag indicating if the Plan is a patch plan that only contains details of the changes from the precursor Plan.  A patch plan must have a precursor.  It must also include a single PlanRevision relative to the precursor Plan.
     * @param information Contains header information relating to the Plan, including its originator and validity period.
     * @param items Contains the planned activities and events that constitute the Plan.
     * @param isAlternate Flag indicating if the Plan has currently been released as an Operational or Alternate plan.
     * @param status Current status of the Plan.
     */
    public Plan(ObjectIdentity objectIdentity,
            Boolean isPatchPlan,
            PlanInformation information,
            PlannedItems items,
            Boolean isAlternate,
            PlanStatusEnum status) {
        super(objectIdentity);
        this.isPatchPlan = isPatchPlan;
        this.precursorPlan = null;
        this.targetPlan = null;
        this.information = information;
        this.items = items;
        this.revisions = null;
        this.resources = null;
        this.isAlternate = isAlternate;
        this.status = status;
        this.statusInfo = null;
    }

    @Override
    public Element createElement() {
        return new Plan();
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
     * Returns the field information.
     * 
     * @return The field information
     */
    public PlanInformation getInformation() {
        return information;
    }

    /**
     * Returns the field items.
     * 
     * @return The field items
     */
    public PlannedItems getItems() {
        return items;
    }

    /**
     * Returns the field revisions.
     * 
     * @return The field revisions
     */
    public PlanRevisionList getRevisions() {
        return revisions;
    }

    /**
     * Returns the field resources.
     * 
     * @return The field resources
     */
    public ResourceProfileList getResources() {
        return resources;
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
    public PlanStatusEnum getStatus() {
        return status;
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
        if (obj instanceof Plan) {
            if (! super.equals(obj)) {
                return false;
            }
            Plan other = (Plan) obj;
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
            if (information == null) {
                if (other.information != null) {
                    return false;
                }
            } else {
                if (! information.equals(other.information)) {
                    return false;
                }
            }
            if (items == null) {
                if (other.items != null) {
                    return false;
                }
            } else {
                if (! items.equals(other.items)) {
                    return false;
                }
            }
            if (revisions == null) {
                if (other.revisions != null) {
                    return false;
                }
            } else {
                if (! revisions.equals(other.revisions)) {
                    return false;
                }
            }
            if (resources == null) {
                if (other.resources != null) {
                    return false;
                }
            } else {
                if (! resources.equals(other.resources)) {
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
        int hash = super.hashCode();
        hash = 83 * hash + (isPatchPlan != null ? isPatchPlan.hashCode() : 0);
        hash = 83 * hash + (precursorPlan != null ? precursorPlan.hashCode() : 0);
        hash = 83 * hash + (targetPlan != null ? targetPlan.hashCode() : 0);
        hash = 83 * hash + (information != null ? information.hashCode() : 0);
        hash = 83 * hash + (items != null ? items.hashCode() : 0);
        hash = 83 * hash + (revisions != null ? revisions.hashCode() : 0);
        hash = 83 * hash + (resources != null ? resources.hashCode() : 0);
        hash = 83 * hash + (isAlternate != null ? isAlternate.hashCode() : 0);
        hash = 83 * hash + (status != null ? status.hashCode() : 0);
        hash = 83 * hash + (statusInfo != null ? statusInfo.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(Plan: ");
        buf.append(super.toString());
        buf.append(", isPatchPlan=").append(isPatchPlan);
        buf.append(", precursorPlan=").append(precursorPlan);
        buf.append(", targetPlan=").append(targetPlan);
        buf.append(", information=").append(information);
        buf.append(", items=").append(items);
        buf.append(", revisions=").append(revisions);
        buf.append(", resources=").append(resources);
        buf.append(", isAlternate=").append(isAlternate);
        buf.append(", status=").append(status);
        buf.append(", statusInfo=").append(statusInfo);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (isPatchPlan == null) {
            throw new MALException("The field 'isPatchPlan' cannot be null!");
        }
        if (information == null) {
            throw new MALException("The field 'information' cannot be null!");
        }
        if (items == null) {
            throw new MALException("The field 'items' cannot be null!");
        }
        if (isAlternate == null) {
            throw new MALException("The field 'isAlternate' cannot be null!");
        }
        if (status == null) {
            throw new MALException("The field 'status' cannot be null!");
        }
        encoder.encodeBoolean(isPatchPlan);
        encoder.encodeNullableElement(precursorPlan);
        encoder.encodeNullableElement(targetPlan);
        encoder.encodeElement(information);
        encoder.encodeElement(items);
        encoder.encodeNullableElement(revisions);
        encoder.encodeNullableElement(resources);
        encoder.encodeBoolean(isAlternate);
        encoder.encodeElement(status);
        encoder.encodeNullableString(statusInfo);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        isPatchPlan = decoder.decodeBoolean();
        precursorPlan = (ObjectRef<Plan>) decoder.decodeNullableElement(new ObjectRef<Plan>());
        targetPlan = (ObjectRef<Plan>) decoder.decodeNullableElement(new ObjectRef<Plan>());
        information = (PlanInformation) decoder.decodeElement(new PlanInformation());
        items = (PlannedItems) decoder.decodeElement(new PlannedItems());
        revisions = (PlanRevisionList) decoder.decodeNullableElement(new PlanRevisionList());
        resources = (ResourceProfileList) decoder.decodeNullableElement(new ResourceProfileList());
        isAlternate = decoder.decodeBoolean();
        status = (PlanStatusEnum) decoder.decodeElement(PlanStatusEnum.DRAFT);
        statusInfo = decoder.decodeNullableString();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
