package org.ccsds.moims.mo.mps.plandistribution.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mps.InvalidException;
import org.ccsds.moims.mo.mps.structures.PartialPlan;
import org.ccsds.moims.mo.mps.structures.PartialPlanFilter;
import org.ccsds.moims.mo.mps.structures.PlanFilter;
import org.ccsds.moims.mo.mps.structures.PlanQuery;
import org.ccsds.moims.mo.mps.structures.PlanSummaryStatusList;
import org.ccsds.moims.mo.mps.structures.PlanUpdateList;

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
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALException if there is an implementation exception
     */
    PlanSummaryStatusList getPlanSummaries(PlanFilter planFilter,
            MALInteraction interaction) throws InvalidException, MALException;
    /**
     * Implements the operation getPlan.
     * 
     * @param planRefs The planRefs field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALException if there is an implementation exception
     */
    void getPlan(ObjectRefList planRefs,
            GetPlanInteraction interaction) throws InvalidException, MALException;
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
     * Implements the operation queryPlan.
     * 
     * @param query The query field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALException if there is an implementation exception
     */
    void queryPlan(PlanQuery query,
            QueryPlanInteraction interaction) throws InvalidException, MALException;
    /**
     * Implements the operation getPartialPlan.
     * 
     * @param partialPlanFilter The partialPlanFilter field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALException if there is an implementation exception
     */
    PartialPlan getPartialPlan(PartialPlanFilter partialPlanFilter,
            MALInteraction interaction) throws InvalidException, MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(PlanDistributionSkeleton skeleton);
}
