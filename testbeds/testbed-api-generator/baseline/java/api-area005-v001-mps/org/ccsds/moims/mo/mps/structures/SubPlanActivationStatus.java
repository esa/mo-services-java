package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 * E1: SubPlanActivationStatus is a data structure that returns the activation
 * status of a sub-plan in the context of the MPS Plan Execution Control service
 * activateSubPlan and deactivateSubPlan operations.
 */
public final class SubPlanActivationStatus implements Composite {

    private static final long serialVersionUID = 1407374900331009L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900331009L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Identifier of the sub-plan to which the status relates.
     */
    private Identifier plan;

    /**
     * Current status of the sub-plan, which may be Activated or Deactivated.
     */
    private SubPlanStatusEnum status;

    /**
     * ActivationInfo provides customizable detailed information on the result
     * of the activation/deactivation request for the referenced sub-plan. .
     */
    private String activationInfo;

    /**
     * Default constructor for SubPlanActivationStatus.
     * 
     */
    public SubPlanActivationStatus() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param plan Identifier of the sub-plan to which the status relates.
     * @param status Current status of the sub-plan, which may be Activated or Deactivated.
     * @param activationInfo ActivationInfo provides customizable detailed information on the result of the activation/deactivation request for the referenced sub-plan. 
     */
    public SubPlanActivationStatus(Identifier plan,
            SubPlanStatusEnum status,
            String activationInfo) {
        this.plan = plan;
        this.status = status;
        this.activationInfo = activationInfo;
    }

    @Override
    public Element createElement() {
        return new SubPlanActivationStatus();
    }

    /**
     * Returns the field plan.
     * 
     * @return The field plan
     */
    public Identifier getPlan() {
        return plan;
    }

    /**
     * Returns the field status.
     * 
     * @return The field status
     */
    public SubPlanStatusEnum getStatus() {
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
        if (obj instanceof SubPlanActivationStatus) {
            SubPlanActivationStatus other = (SubPlanActivationStatus) obj;
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
        buf.append("(SubPlanActivationStatus: ");
        buf.append("plan=").append(plan);
        buf.append(", status=").append(status);
        buf.append(", activationInfo=").append(activationInfo);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (plan == null) {
            throw new MALException("The field 'plan' cannot be null!");
        }
        if (status == null) {
            throw new MALException("The field 'status' cannot be null!");
        }
        if (activationInfo == null) {
            throw new MALException("The field 'activationInfo' cannot be null!");
        }
        encoder.encodeIdentifier(plan);
        encoder.encodeElement(status);
        encoder.encodeString(activationInfo);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        plan = decoder.decodeIdentifier();
        status = (SubPlanStatusEnum) decoder.decodeElement(SubPlanStatusEnum.ACTIVATED);
        activationInfo = decoder.decodeString();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
