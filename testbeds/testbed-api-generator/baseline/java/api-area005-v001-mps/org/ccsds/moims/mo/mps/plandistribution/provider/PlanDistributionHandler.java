package org.ccsds.moims.mo.mps.plandistribution.provider;

/**
 * Interface that providers of the PlanDistribution service must implement
 * to handle the operations of that service.
 */
public interface PlanDistributionHandler {

    /**
     * Implements the operation getPlanSummaries.
     * 
     * @param planFilter The planFilter field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mps.structures.PlanSummaryStatusList getPlanSummaries(org.ccsds.moims.mo.mps.structures.PlanFilter planFilter,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation getPlan.
     * 
     * @param planRefs The planRefs field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void getPlan(org.ccsds.moims.mo.mal.structures.ObjectRefList planRefs,
            org.ccsds.moims.mo.mps.plandistribution.provider.GetPlanInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
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
     * Implements the operation queryPlan.
     * 
     * @param query The query field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void queryPlan(org.ccsds.moims.mo.mps.structures.PlanQuery query,
            org.ccsds.moims.mo.mps.plandistribution.provider.QueryPlanInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation getPartialPlan.
     * 
     * @param partialPlanFilter The partialPlanFilter field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mps.structures.PartialPlan getPartialPlan(org.ccsds.moims.mo.mps.structures.PartialPlanFilter partialPlanFilter,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.mps.plandistribution.provider.PlanDistributionSkeleton skeleton);
}
