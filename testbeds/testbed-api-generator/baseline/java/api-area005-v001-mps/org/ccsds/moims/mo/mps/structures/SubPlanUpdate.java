package org.ccsds.moims.mo.mps.structures;

/**
 * E1: SubPlanUpdate is a data structure that is used to report changes in
 * status of a sub-plan during plan execution.  It is returned in the context
 * of the MPS Plan Execution Control service monitorSubPlanExecution and getSubPlanStatus
 * operations. Sub-plans are not defined as objects within the MPS model.
 * Individual activities within a Plan may be associated with a single sub-plan
 * via its Identifier.  The plan execution function is responsible for managing
 * and reporting sub-plan status associated with relevant Plan Execution Control
 * service operations, if supported.
 */
public final class SubPlanUpdate implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1407374900331007L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900331007L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Identifier of the sub-plan to which the update relates.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier subPlan;

    /**
     * Time of status update.
     */
    private org.ccsds.moims.mo.mal.structures.Time timestamp;

    /**
     * Current status of the sub-plan, which may be Activated or Deactivated.
     */
    private org.ccsds.moims.mo.mps.structures.SubPlanStatusEnum status;

    /**
     * Default constructor for SubPlanUpdate.
     * 
     */
    public SubPlanUpdate() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param subPlan Identifier of the sub-plan to which the update relates.
     * @param timestamp Time of status update.
     * @param status Current status of the sub-plan, which may be Activated or Deactivated.
     */
    public SubPlanUpdate(org.ccsds.moims.mo.mal.structures.Identifier subPlan,
            org.ccsds.moims.mo.mal.structures.Time timestamp,
            org.ccsds.moims.mo.mps.structures.SubPlanStatusEnum status) {
        this.subPlan = subPlan;
        this.timestamp = timestamp;
        this.status = status;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.SubPlanUpdate();
    }

    /**
     * Returns the field subPlan.
     * 
     * @return The field subPlan
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getSubPlan() {
        return subPlan;
    }

    /**
     * Returns the field timestamp.
     * 
     * @return The field timestamp
     */
    public org.ccsds.moims.mo.mal.structures.Time getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the field status.
     * 
     * @return The field status
     */
    public org.ccsds.moims.mo.mps.structures.SubPlanStatusEnum getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SubPlanUpdate) {
            SubPlanUpdate other = (SubPlanUpdate) obj;
            if (subPlan == null) {
                if (other.subPlan != null) {
                    return false;
                }
            } else {
                if (! subPlan.equals(other.subPlan)) {
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
            if (status == null) {
                if (other.status != null) {
                    return false;
                }
            } else {
                if (! status.equals(other.status)) {
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
        hash = 83 * hash + (subPlan != null ? subPlan.hashCode() : 0);
        hash = 83 * hash + (timestamp != null ? timestamp.hashCode() : 0);
        hash = 83 * hash + (status != null ? status.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(SubPlanUpdate: ");
        buf.append("subPlan=").append(subPlan);
        buf.append(", timestamp=").append(timestamp);
        buf.append(", status=").append(status);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (subPlan == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'subPlan' cannot be null!");
        }
        if (timestamp == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'timestamp' cannot be null!");
        }
        if (status == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'status' cannot be null!");
        }
        encoder.encodeIdentifier(subPlan);
        encoder.encodeTime(timestamp);
        encoder.encodeElement(status);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        subPlan = decoder.decodeIdentifier();
        timestamp = decoder.decodeTime();
        status = (org.ccsds.moims.mo.mps.structures.SubPlanStatusEnum) decoder.decodeElement(org.ccsds.moims.mo.mps.structures.SubPlanStatusEnum.ACTIVATED);
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
