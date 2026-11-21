package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Time;

/**
 * E1: PlanUpdate is a data structure that is used to report changes in status
 * of the Plan as it proceeds through both planning and plan execution functions.
 * It is returned in the context of the MPS Plan Distribution service getPlanStatus
 * and monitorPlanStatus operations, and also the MPS Plan Execution Control
 * service monitorPlanExecution and getPlanStatus operations. PlanUpdates
 * may be distributed to subscribing applications, including status displays,
 * to inform them of the latest status of a Plan.  PlanUpdates may be stored
 * in plan history to provide a complete record of evolving status over time.
 */
public final class PlanUpdate implements Composite {

    private static final long serialVersionUID = 1407374900331004L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900331004L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Reference to the Plan to which the status update relates.
     */
    private ObjectRef<Plan> plan;

    /**
     * Time of status update.
     */
    private Time timestamp;

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
     * text shall be used: - Completed (nominal); - Superseded by a successor
     * Plan; - Revoked by a User; - Cancelled (deactivated after start of execution);
     * - Expired (reached the end of its validity period without being activated).
     */
    private String statusInfo;

    /**
     * Default constructor for PlanUpdate.
     * 
     */
    public PlanUpdate() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param plan Reference to the Plan to which the status update relates.
     * @param timestamp Time of status update.
     * @param isAlternate Flag indicating if the Plan has currently been released as an Operational or Alternate plan.
     * @param status Current status of the Plan.
     * @param statusInfo Supplementary information for a Plan in the Terminated state.  This is customizable, but if the following conditions exist then the specified text shall be used: - Completed (nominal); - Superseded by a successor Plan; - Revoked by a User; - Cancelled (deactivated after start of execution); - Expired (reached the end of its validity period without being activated).
     */
    public PlanUpdate(ObjectRef<Plan> plan,
            Time timestamp,
            Boolean isAlternate,
            PlanStatusEnum status,
            String statusInfo) {
        this.plan = plan;
        this.timestamp = timestamp;
        this.isAlternate = isAlternate;
        this.status = status;
        this.statusInfo = statusInfo;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param plan Reference to the Plan to which the status update relates.
     * @param timestamp Time of status update.
     * @param isAlternate Flag indicating if the Plan has currently been released as an Operational or Alternate plan.
     * @param status Current status of the Plan.
     */
    public PlanUpdate(ObjectRef<Plan> plan,
            Time timestamp,
            Boolean isAlternate,
            PlanStatusEnum status) {
        this.plan = plan;
        this.timestamp = timestamp;
        this.isAlternate = isAlternate;
        this.status = status;
        this.statusInfo = null;
    }

    @Override
    public Element createElement() {
        return new PlanUpdate();
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
     * Returns the field timestamp.
     * 
     * @return The field timestamp
     */
    public Time getTimestamp() {
        return timestamp;
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
        if (obj instanceof PlanUpdate) {
            PlanUpdate other = (PlanUpdate) obj;
            if (plan == null) {
                if (other.plan != null) {
                    return false;
                }
            } else {
                if (! plan.equals(other.plan)) {
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
        hash = 83 * hash + (timestamp != null ? timestamp.hashCode() : 0);
        hash = 83 * hash + (isAlternate != null ? isAlternate.hashCode() : 0);
        hash = 83 * hash + (status != null ? status.hashCode() : 0);
        hash = 83 * hash + (statusInfo != null ? statusInfo.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(PlanUpdate: ");
        buf.append("plan=").append(plan);
        buf.append(", timestamp=").append(timestamp);
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
        if (timestamp == null) {
            throw new MALException("The field 'timestamp' cannot be null!");
        }
        if (isAlternate == null) {
            throw new MALException("The field 'isAlternate' cannot be null!");
        }
        if (status == null) {
            throw new MALException("The field 'status' cannot be null!");
        }
        encoder.encodeElement(plan);
        encoder.encodeTime(timestamp);
        encoder.encodeBoolean(isAlternate);
        encoder.encodeElement(status);
        encoder.encodeNullableString(statusInfo);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        plan = (ObjectRef<Plan>) decoder.decodeElement(new ObjectRef<Plan>());
        timestamp = decoder.decodeTime();
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
