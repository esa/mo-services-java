package org.ccsds.moims.mo.mps.structures;

/**
 * E1: A data structure that returns the status and supplementary suspension
 * information for an ActivityInstance affected by an MPS Plan Execution Control
 * service suspendActivity or resumeActivity operation.
 */
public final class ActivitySuspensionStatus implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1407374900330604L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330604L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Reference to an ActivityInstance.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityInstance> activityInstance;

    /**
     * Optional reference to the Plan containing the ActivityInstance.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> plan;

    /**
     * Current Status of the ActivityInstance.
     */
    private org.ccsds.moims.mo.mps.structures.ActivityStatusEnum status;

    /**
     * Supplementary information on the suspension/resumption status of the ActivityInstance.
     * This may detail the point of suspension, which may be specific to the suspension
     * mode; or a reason why resumption was not possible.
     */
    private String suspensionInfo;

    /**
     * Default constructor for ActivitySuspensionStatus.
     * 
     */
    public ActivitySuspensionStatus() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param activityInstance Reference to an ActivityInstance.
     * @param plan Optional reference to the Plan containing the ActivityInstance.
     * @param status Current Status of the ActivityInstance.
     * @param suspensionInfo Supplementary information on the suspension/resumption status of the ActivityInstance. This may detail the point of suspension, which may be specific to the suspension mode; or a reason why resumption was not possible.
     */
    public ActivitySuspensionStatus(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityInstance> activityInstance,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> plan,
            org.ccsds.moims.mo.mps.structures.ActivityStatusEnum status,
            String suspensionInfo) {
        this.activityInstance = activityInstance;
        this.plan = plan;
        this.status = status;
        this.suspensionInfo = suspensionInfo;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param activityInstance Reference to an ActivityInstance.
     * @param status Current Status of the ActivityInstance.
     */
    public ActivitySuspensionStatus(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityInstance> activityInstance,
            org.ccsds.moims.mo.mps.structures.ActivityStatusEnum status) {
        this.activityInstance = activityInstance;
        this.plan = null;
        this.status = status;
        this.suspensionInfo = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.ActivitySuspensionStatus();
    }

    /**
     * Returns the field activityInstance.
     * 
     * @return The field activityInstance
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityInstance> getActivityInstance() {
        return activityInstance;
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
    public org.ccsds.moims.mo.mps.structures.ActivityStatusEnum getStatus() {
        return status;
    }

    /**
     * Returns the field suspensionInfo.
     * 
     * @return The field suspensionInfo
     */
    public String getSuspensionInfo() {
        return suspensionInfo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ActivitySuspensionStatus) {
            ActivitySuspensionStatus other = (ActivitySuspensionStatus) obj;
            if (activityInstance == null) {
                if (other.activityInstance != null) {
                    return false;
                }
            } else {
                if (! activityInstance.equals(other.activityInstance)) {
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
            if (status == null) {
                if (other.status != null) {
                    return false;
                }
            } else {
                if (! status.equals(other.status)) {
                    return false;
                }
            }
            if (suspensionInfo == null) {
                if (other.suspensionInfo != null) {
                    return false;
                }
            } else {
                if (! suspensionInfo.equals(other.suspensionInfo)) {
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
        hash = 83 * hash + (activityInstance != null ? activityInstance.hashCode() : 0);
        hash = 83 * hash + (plan != null ? plan.hashCode() : 0);
        hash = 83 * hash + (status != null ? status.hashCode() : 0);
        hash = 83 * hash + (suspensionInfo != null ? suspensionInfo.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ActivitySuspensionStatus: ");
        buf.append("activityInstance=").append(activityInstance);
        buf.append(", plan=").append(plan);
        buf.append(", status=").append(status);
        buf.append(", suspensionInfo=").append(suspensionInfo);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (activityInstance == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'activityInstance' cannot be null!");
        }
        if (status == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'status' cannot be null!");
        }
        encoder.encodeElement(activityInstance);
        encoder.encodeNullableElement(plan);
        encoder.encodeElement(status);
        encoder.encodeNullableString(suspensionInfo);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        activityInstance = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityInstance>) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityInstance>());
        plan = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>());
        status = (org.ccsds.moims.mo.mps.structures.ActivityStatusEnum) decoder.decodeElement(org.ccsds.moims.mo.mps.structures.ActivityStatusEnum.PLANNED);
        suspensionInfo = decoder.decodeNullableString();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
