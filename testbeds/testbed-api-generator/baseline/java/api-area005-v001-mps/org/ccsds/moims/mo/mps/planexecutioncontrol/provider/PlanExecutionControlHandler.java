package org.ccsds.moims.mo.mps.planexecutioncontrol.provider;

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
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mps.SubmitFailedException The submitPlan operation failed as the submitted plan was already terminated.
     * @throws org.ccsds.moims.mo.mps.UnsupportedException An optional data structure used in the message is not supported by the service provider.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void submitPlan(org.ccsds.moims.mo.mps.structures.Plan plan,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mps.SubmitFailedException, org.ccsds.moims.mo.mps.UnsupportedException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation revokePlan.
     * 
     * @param planRef The planRef field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mps.RevokeFailedException The revokePlan operation failed to revoke the referenced Plan, for example because it has already started executing.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void revokePlan(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mps.RevokeFailedException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation getPlanStatus.
     * 
     * @param planRefs The planRefs field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mps.structures.PlanUpdateList getPlanStatus(org.ccsds.moims.mo.mal.structures.ObjectRefList planRefs,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation activatePlan.
     * 
     * @param planRefs The planRefs field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mps.ActivateFailedException The activatePlan operation failed as the activation was outside the validity period of the Plan, or the start of the planPeriod had already passed.  
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mps.structures.PlanActivationStatusList activatePlan(org.ccsds.moims.mo.mal.structures.ObjectRefList planRefs,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mps.ActivateFailedException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation deactivatePlan.
     * 
     * @param planRefs The planRefs field.
     * @param deactivationMode The deactivationMode field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mps.DeactivateFailedException The deactivatePlan operation failed.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mps.structures.PlanActivationStatusList deactivatePlan(org.ccsds.moims.mo.mal.structures.ObjectRefList planRefs,
            org.ccsds.moims.mo.mal.structures.Identifier deactivationMode,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mps.DeactivateFailedException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation activateSubPlan.
     * 
     * @param subPlanIDs The subPlanIDs field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mps.ActivateSubplanFailedException The activateSubPlan operation failed.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mps.structures.SubPlanActivationStatusList activateSubPlan(org.ccsds.moims.mo.mal.structures.IdentifierList subPlanIDs,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mps.ActivateSubplanFailedException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation deactivateSubPlan.
     * 
     * @param subPlanIDs The subPlanIDs field.
     * @param deactivationMode The deactivationMode field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mps.DeactivateSubplanFailedException The deactivateSubPlan operation failed.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mps.structures.SubPlanActivationStatusList deactivateSubPlan(org.ccsds.moims.mo.mal.structures.IdentifierList subPlanIDs,
            String deactivationMode,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mps.DeactivateSubplanFailedException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation getSubPlanStatus.
     * 
     * @param subPlanIDs The subPlanIDs field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mps.structures.SubPlanUpdateList getSubPlanStatus(org.ccsds.moims.mo.mal.structures.IdentifierList subPlanIDs,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation suspendActivity.
     * 
     * @param planRefs The planRefs field.
     * @param activityRefs The activityRefs field.
     * @param tags The tags field.
     * @param suspensionMode The suspensionMode field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mps.structures.ActivitySuspensionStatusList suspendActivity(org.ccsds.moims.mo.mal.structures.ObjectRefList planRefs,
            org.ccsds.moims.mo.mal.structures.ObjectRefList activityRefs,
            org.ccsds.moims.mo.mal.structures.StringList tags,
            String suspensionMode,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation resumeActivity.
     * 
     * @param planRefs The planRefs field.
     * @param activityRefs The activityRefs field.
     * @param tags The tags field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mps.structures.ActivitySuspensionStatusList resumeActivity(org.ccsds.moims.mo.mal.structures.ObjectRefList planRefs,
            org.ccsds.moims.mo.mal.structures.ObjectRefList activityRefs,
            org.ccsds.moims.mo.mal.structures.StringList tags,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation getActivityStatus.
     * 
     * @param planRefs The planRefs field.
     * @param activityRefs The activityRefs field.
     * @param subPlans The subPlans field.
     * @param tags The tags field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mps.structures.ActivityUpdateList getActivityStatus(org.ccsds.moims.mo.mal.structures.ObjectRefList planRefs,
            org.ccsds.moims.mo.mal.structures.ObjectRefList activityRefs,
            org.ccsds.moims.mo.mal.structures.IdentifierList subPlans,
            org.ccsds.moims.mo.mal.structures.StringList tags,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.mps.planexecutioncontrol.provider.PlanExecutionControlSkeleton skeleton);
}
