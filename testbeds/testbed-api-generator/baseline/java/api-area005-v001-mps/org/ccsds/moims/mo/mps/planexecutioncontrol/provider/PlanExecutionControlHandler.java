package org.ccsds.moims.mo.mps.planexecutioncontrol.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.mps.ActivateFailedException;
import org.ccsds.moims.mo.mps.ActivateSubplanFailedException;
import org.ccsds.moims.mo.mps.DeactivateFailedException;
import org.ccsds.moims.mo.mps.DeactivateSubplanFailedException;
import org.ccsds.moims.mo.mps.InvalidException;
import org.ccsds.moims.mo.mps.RevokeFailedException;
import org.ccsds.moims.mo.mps.SubmitFailedException;
import org.ccsds.moims.mo.mps.UnsupportedException;
import org.ccsds.moims.mo.mps.structures.ActivitySuspensionStatusList;
import org.ccsds.moims.mo.mps.structures.ActivityUpdateList;
import org.ccsds.moims.mo.mps.structures.Plan;
import org.ccsds.moims.mo.mps.structures.PlanActivationStatusList;
import org.ccsds.moims.mo.mps.structures.PlanUpdateList;
import org.ccsds.moims.mo.mps.structures.SubPlanActivationStatusList;
import org.ccsds.moims.mo.mps.structures.SubPlanUpdateList;

/**
 * Interface that providers of the PlanExecutionControl service must implement
 * to handle the operations of that service.
 */
public interface PlanExecutionControlHandler {

    /**
     * Implements the operation submitPlan.
     * 
     * @param plan The plan field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws SubmitFailedException The submitPlan operation failed as the submitted plan was already terminated.
     * @throws UnsupportedException An optional data structure used in the message is not supported by the service provider.
     * @throws MALException if there is an implementation exception
     */
    void submitPlan(Plan plan,
            MALInteraction interaction) throws InvalidException, SubmitFailedException, UnsupportedException, MALException;
    /**
     * Implements the operation revokePlan.
     * 
     * @param planRef The planRef field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws RevokeFailedException The revokePlan operation failed to revoke the referenced Plan, for example because it has already started executing.
     * @throws MALException if there is an implementation exception
     */
    void revokePlan(ObjectRef<Plan> planRef,
            MALInteraction interaction) throws InvalidException, RevokeFailedException, MALException;
    /**
     * Implements the operation getPlanStatus.
     * 
     * @param planRefs The planRefs field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALException if there is an implementation exception
     */
    PlanUpdateList getPlanStatus(ObjectRefList planRefs,
            MALInteraction interaction) throws InvalidException, MALException;
    /**
     * Implements the operation activatePlan.
     * 
     * @param planRefs The planRefs field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws ActivateFailedException The activatePlan operation failed as the activation was outside the validity period of the Plan, or the start of the planPeriod had already passed.  
     * @throws MALException if there is an implementation exception
     */
    PlanActivationStatusList activatePlan(ObjectRefList planRefs,
            MALInteraction interaction) throws InvalidException, ActivateFailedException, MALException;
    /**
     * Implements the operation deactivatePlan.
     * 
     * @param planRefs The planRefs field.
     * @param deactivationMode The deactivationMode field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws DeactivateFailedException The deactivatePlan operation failed.
     * @throws MALException if there is an implementation exception
     */
    PlanActivationStatusList deactivatePlan(ObjectRefList planRefs,
            Identifier deactivationMode,
            MALInteraction interaction) throws InvalidException, DeactivateFailedException, MALException;
    /**
     * Implements the operation activateSubPlan.
     * 
     * @param subPlanIDs The subPlanIDs field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws ActivateSubplanFailedException The activateSubPlan operation failed.
     * @throws MALException if there is an implementation exception
     */
    SubPlanActivationStatusList activateSubPlan(IdentifierList subPlanIDs,
            MALInteraction interaction) throws InvalidException, ActivateSubplanFailedException, MALException;
    /**
     * Implements the operation deactivateSubPlan.
     * 
     * @param subPlanIDs The subPlanIDs field.
     * @param deactivationMode The deactivationMode field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws DeactivateSubplanFailedException The deactivateSubPlan operation failed.
     * @throws MALException if there is an implementation exception
     */
    SubPlanActivationStatusList deactivateSubPlan(IdentifierList subPlanIDs,
            String deactivationMode,
            MALInteraction interaction) throws InvalidException, DeactivateSubplanFailedException, MALException;
    /**
     * Implements the operation getSubPlanStatus.
     * 
     * @param subPlanIDs The subPlanIDs field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALException if there is an implementation exception
     */
    SubPlanUpdateList getSubPlanStatus(IdentifierList subPlanIDs,
            MALInteraction interaction) throws InvalidException, MALException;
    /**
     * Implements the operation suspendActivity.
     * 
     * @param planRefs The planRefs field.
     * @param activityRefs The activityRefs field.
     * @param tags The tags field.
     * @param suspensionMode The suspensionMode field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALException if there is an implementation exception
     */
    ActivitySuspensionStatusList suspendActivity(ObjectRefList planRefs,
            ObjectRefList activityRefs,
            StringList tags,
            String suspensionMode,
            MALInteraction interaction) throws InvalidException, MALException;
    /**
     * Implements the operation resumeActivity.
     * 
     * @param planRefs The planRefs field.
     * @param activityRefs The activityRefs field.
     * @param tags The tags field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALException if there is an implementation exception
     */
    ActivitySuspensionStatusList resumeActivity(ObjectRefList planRefs,
            ObjectRefList activityRefs,
            StringList tags,
            MALInteraction interaction) throws InvalidException, MALException;
    /**
     * Implements the operation getActivityStatus.
     * 
     * @param planRefs The planRefs field.
     * @param activityRefs The activityRefs field.
     * @param subPlans The subPlans field.
     * @param tags The tags field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALException if there is an implementation exception
     */
    ActivityUpdateList getActivityStatus(ObjectRefList planRefs,
            ObjectRefList activityRefs,
            IdentifierList subPlans,
            StringList tags,
            MALInteraction interaction) throws InvalidException, MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(PlanExecutionControlSkeleton skeleton);
}
