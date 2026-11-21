package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ObjectRef;

/**
 * E1: PlanSummaryStatus is a data structure that provides a summary view
 * of a Plan that includes the PlanInformation section and current status,
 * but not the full details of the Plan.  It is returned in the context of
 * the MPS Plan Distribution service getPlanSummaries operation.
 */
public final class PlanSummaryStatus implements Composite {

    private static final long serialVersionUID = 1407374900331005L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900331005L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Reference to the Plan to which the summary status relates.
     */
    private ObjectRef<Plan> plan;

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
     * Flag indicating if the Plan has currently been released as an Operational
     * or Alternate plan.
     */
    private Boolean isAlternate;

    /**
     * Current status of the Plan.
     */
    private PlanStatusEnum status;

    /**
     * Supplementary information for a Plan in the Terminated state.
     */
    private String statusInfo;

    /**
     * Default constructor for PlanSummaryStatus.
     * 
     */
    public PlanSummaryStatus() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param plan Reference to the Plan to which the summary status relates.
     * @param isPatchPlan Flag indicating if the Plan is a patch plan that only contains details of the changes from the precursor Plan.  A patch plan must have a precursor.  It must also include a single PlanRevision relative to the precursor Plan.
     * @param precursorPlan Reference to a precursor (or predecessor) Plan from which the changes are detailed in the Plan.  This may be used if there is an iterative re-planning cycle in which successive plans overlap, or where a previous Plan has been updated through re-planning.  If there is no precursor, then the Plan must be a self-standing full plan. If the Plan is a Patch Plan, then a precursor plan must be specified.
     * @param targetPlan Applicable only for patch plans, this is a reference to the target Plan.  This target Plan is the result of applying the patch plan to the precursor Plan and is distinct from the identity of the patch plan itself.  Patch plans are not permitted in the context of a planning request.
     * @param information Contains header information relating to the Plan, including its originator and validity period.
     * @param isAlternate Flag indicating if the Plan has currently been released as an Operational or Alternate plan.
     * @param status Current status of the Plan.
     * @param statusInfo Supplementary information for a Plan in the Terminated state.
     */
    public PlanSummaryStatus(ObjectRef<Plan> plan,
            Boolean isPatchPlan,
            ObjectRef<Plan> precursorPlan,
            ObjectRef<Plan> targetPlan,
            PlanInformation information,
            Boolean isAlternate,
            PlanStatusEnum status,
            String statusInfo) {
        this.plan = plan;
        this.isPatchPlan = isPatchPlan;
        this.precursorPlan = precursorPlan;
        this.targetPlan = targetPlan;
        this.information = information;
        this.isAlternate = isAlternate;
        this.status = status;
        this.statusInfo = statusInfo;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param plan Reference to the Plan to which the summary status relates.
     * @param isPatchPlan Flag indicating if the Plan is a patch plan that only contains details of the changes from the precursor Plan.  A patch plan must have a precursor.  It must also include a single PlanRevision relative to the precursor Plan.
     * @param information Contains header information relating to the Plan, including its originator and validity period.
     * @param isAlternate Flag indicating if the Plan has currently been released as an Operational or Alternate plan.
     * @param status Current status of the Plan.
     */
    public PlanSummaryStatus(ObjectRef<Plan> plan,
            Boolean isPatchPlan,
            PlanInformation information,
            Boolean isAlternate,
            PlanStatusEnum status) {
        this.plan = plan;
        this.isPatchPlan = isPatchPlan;
        this.precursorPlan = null;
        this.targetPlan = null;
        this.information = information;
        this.isAlternate = isAlternate;
        this.status = status;
        this.statusInfo = null;
    }

    @Override
    public Element createElement() {
        return new PlanSummaryStatus();
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
        if (obj instanceof PlanSummaryStatus) {
            PlanSummaryStatus other = (PlanSummaryStatus) obj;
            if (plan == null) {
                if (other.plan != null) {
                    return false;
                }
            } else {
                if (! plan.equals(other.plan)) {
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
            if (information == null) {
                if (other.information != null) {
                    return false;
                }
            } else {
                if (! information.equals(other.information)) {
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
        int hash = 7;
        hash = 83 * hash + (plan != null ? plan.hashCode() : 0);
        hash = 83 * hash + (isPatchPlan != null ? isPatchPlan.hashCode() : 0);
        hash = 83 * hash + (precursorPlan != null ? precursorPlan.hashCode() : 0);
        hash = 83 * hash + (targetPlan != null ? targetPlan.hashCode() : 0);
        hash = 83 * hash + (information != null ? information.hashCode() : 0);
        hash = 83 * hash + (isAlternate != null ? isAlternate.hashCode() : 0);
        hash = 83 * hash + (status != null ? status.hashCode() : 0);
        hash = 83 * hash + (statusInfo != null ? statusInfo.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(PlanSummaryStatus: ");
        buf.append("plan=").append(plan);
        buf.append(", isPatchPlan=").append(isPatchPlan);
        buf.append(", precursorPlan=").append(precursorPlan);
        buf.append(", targetPlan=").append(targetPlan);
        buf.append(", information=").append(information);
        buf.append(", isAlternate=").append(isAlternate);
        buf.append(", status=").append(status);
        buf.append(", statusInfo=").append(statusInfo);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (plan == null) {
            throw new MALException("The field 'plan' cannot be null!");
        }
        if (isPatchPlan == null) {
            throw new MALException("The field 'isPatchPlan' cannot be null!");
        }
        if (information == null) {
            throw new MALException("The field 'information' cannot be null!");
        }
        if (isAlternate == null) {
            throw new MALException("The field 'isAlternate' cannot be null!");
        }
        if (status == null) {
            throw new MALException("The field 'status' cannot be null!");
        }
        encoder.encodeElement(plan);
        encoder.encodeBoolean(isPatchPlan);
        encoder.encodeNullableElement(precursorPlan);
        encoder.encodeNullableElement(targetPlan);
        encoder.encodeElement(information);
        encoder.encodeBoolean(isAlternate);
        encoder.encodeElement(status);
        encoder.encodeNullableString(statusInfo);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        plan = (ObjectRef<Plan>) decoder.decodeElement(new ObjectRef<Plan>());
        isPatchPlan = decoder.decodeBoolean();
        precursorPlan = (ObjectRef<Plan>) decoder.decodeNullableElement(new ObjectRef<Plan>());
        targetPlan = (ObjectRef<Plan>) decoder.decodeNullableElement(new ObjectRef<Plan>());
        information = (PlanInformation) decoder.decodeElement(new PlanInformation());
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
