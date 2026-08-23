package org.ccsds.moims.mo.mps.structures;

/**
 * E1: PlanActivationStatus is a data structure that returns the activation
 * status of a Plan in the context of the MPS Plan Execution Control service
 * activatePlan and deactivatePlan operations.
 */
public final class PlanActivationStatus implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1407374900331006L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900331006L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Reference to the Plan to which the status relates.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> plan;

    /**
     * Current status of the Plan.
     */
    private org.ccsds.moims.mo.mps.structures.PlanStatusEnum status;

    /**
     * ActivationInfo provides customizable detailed information on the result
     * of the activation/deactivation request for the referenced Plan. .
     */
    private String activationInfo;

    /**
     * Default constructor for PlanActivationStatus.
     * 
     */
    public PlanActivationStatus() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param plan Reference to the Plan to which the status relates.
     * @param status Current status of the Plan.
     * @param activationInfo ActivationInfo provides customizable detailed information on the result of the activation/deactivation request for the referenced Plan. 
     */
    public PlanActivationStatus(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> plan,
            org.ccsds.moims.mo.mps.structures.PlanStatusEnum status,
            String activationInfo) {
        this.plan = plan;
        this.status = status;
        this.activationInfo = activationInfo;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.PlanActivationStatus();
    }

    /**
     * Returns the field plan.
     * 
     * @return The field plan
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> getPlan() {
        return plan;
    }

    /**
     * Returns the field status.
     * 
     * @return The field status
     */
    public org.ccsds.moims.mo.mps.structures.PlanStatusEnum getStatus() {
        return status;
    }

    /**
     * Returns the field activationInfo.
     * 
     * @return The field activationInfo
     */
    public String getActivationInfo() {
        return activationInfo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlanActivationStatus) {
            PlanActivationStatus other = (PlanActivationStatus) obj;
            if (plan == null) {
                if (other.plan != null) {
                    return false;
                }
            } else {
                if (! plan.equals(other.plan)) {
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
            if (activationInfo == null) {
                if (other.activationInfo != null) {
                    return false;
                }
            } else {
                if (! activationInfo.equals(other.activationInfo)) {
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
        hash = 83 * hash + (status != null ? status.hashCode() : 0);
        hash = 83 * hash + (activationInfo != null ? activationInfo.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(PlanActivationStatus: ");
        buf.append("plan=").append(plan);
        buf.append(", status=").append(status);
        buf.append(", activationInfo=").append(activationInfo);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (plan == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'plan' cannot be null!");
        }
        if (status == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'status' cannot be null!");
        }
        if (activationInfo == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'activationInfo' cannot be null!");
        }
        encoder.encodeElement(plan);
        encoder.encodeElement(status);
        encoder.encodeString(activationInfo);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        plan = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>());
        status = (org.ccsds.moims.mo.mps.structures.PlanStatusEnum) decoder.decodeElement(org.ccsds.moims.mo.mps.structures.PlanStatusEnum.DRAFT);
        activationInfo = decoder.decodeString();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
