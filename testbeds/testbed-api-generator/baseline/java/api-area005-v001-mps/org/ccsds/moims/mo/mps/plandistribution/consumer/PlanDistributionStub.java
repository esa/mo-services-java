package org.ccsds.moims.mo.mps.plandistribution.consumer;

/**
 * Consumer stub for PlanDistribution service.
 */
public class PlanDistributionStub {

    /**
     * The consumer field.
     */
    private final org.ccsds.moims.mo.mal.consumer.MALConsumer consumer;

    /**
     * Wraps a MALconsumer connection with service specific methods that map from
     * the high level service API to the generic MAL API.
     * 
     * @param consumer consumer The MALConsumer to use in this stub.
     */
    public PlanDistributionStub(org.ccsds.moims.mo.mal.consumer.MALConsumer consumer) {
        this.consumer = consumer;
    }

    /**
     * Returns the internal MAL consumer object used for sending of messages from
     * this interface.
     * 
     * @return The MAL consumer object.
     */
    public org.ccsds.moims.mo.mal.consumer.MALConsumer getConsumer() {
        return consumer;
    }

    /**
     * The getPlanSummaries operation allows consumers to obtain a filtered list
     * of currently available Plans.  The request uses the PlanFilter structure
     * to select the set of plans of interest, using the following keys: Domain
     * of the Plan; Reference to the Plan; Reference to the precursor Plan of
     * the Plan; Current status of the Plan; Originator of the Plan; Validity
     * period of the Plan (as a time window). The response returns a list of PlanSummaryStatus
     * structures containing references to the identities, descriptive header
     * fields, and status of the Plans that match the filter.
     * 
     * @param planFilter The planFilter field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mps.structures.PlanSummaryStatusList getPlanSummaries(org.ccsds.moims.mo.mps.structures.PlanFilter planFilter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mps.plandistribution.PlanDistributionServiceInfo.GETPLANSUMMARIES_OP, planFilter);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.PlanSummaryStatusList());
        return (org.ccsds.moims.mo.mps.structures.PlanSummaryStatusList) body0;
    }

    /**
     * Asynchronous version of method getPlanSummaries.
     * 
     * @param planFilter The planFilter field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncGetPlanSummaries(org.ccsds.moims.mo.mps.structures.PlanFilter planFilter,
            org.ccsds.moims.mo.mps.plandistribution.consumer.PlanDistributionAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mps.plandistribution.PlanDistributionServiceInfo.GETPLANSUMMARIES_OP, adapter, planFilter);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueGetPlanSummaries(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.plandistribution.consumer.PlanDistributionAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.plandistribution.PlanDistributionServiceInfo.GETPLANSUMMARIES_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The getPlan operation is used to obtain the full content of one or more
     * known Plans.  The operation uses the Progress interaction pattern, to allow
     * the response to be spread across multiple messages.
     * 
     * @param planRefs The planRefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void getPlan(org.ccsds.moims.mo.mal.structures.ObjectRefList planRefs,
            org.ccsds.moims.mo.mps.plandistribution.consumer.PlanDistributionAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.progress(org.ccsds.moims.mo.mps.plandistribution.PlanDistributionServiceInfo.GETPLAN_OP, adapter, planRefs);
    }

    /**
     * Asynchronous version of method getPlan.
     * 
     * @param planRefs The planRefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncGetPlan(org.ccsds.moims.mo.mal.structures.ObjectRefList planRefs,
            org.ccsds.moims.mo.mps.plandistribution.consumer.PlanDistributionAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncProgress(org.ccsds.moims.mo.mps.plandistribution.PlanDistributionServiceInfo.GETPLAN_OP, adapter, planRefs);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueGetPlan(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.plandistribution.consumer.PlanDistributionAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.plandistribution.PlanDistributionServiceInfo.GETPLAN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The getPlanStatus operation is used to obtain the current status of one
     * or more known Plans.  The operation uses the Request interaction pattern.
     * 
     * @param planRefs The planRefs field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mps.structures.PlanUpdateList getPlanStatus(org.ccsds.moims.mo.mal.structures.ObjectRefList planRefs) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mps.plandistribution.PlanDistributionServiceInfo.GETPLANSTATUS_OP, planRefs);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.PlanUpdateList());
        return (org.ccsds.moims.mo.mps.structures.PlanUpdateList) body0;
    }

    /**
     * Asynchronous version of method getPlanStatus.
     * 
     * @param planRefs The planRefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncGetPlanStatus(org.ccsds.moims.mo.mal.structures.ObjectRefList planRefs,
            org.ccsds.moims.mo.mps.plandistribution.consumer.PlanDistributionAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mps.plandistribution.PlanDistributionServiceInfo.GETPLANSTATUS_OP, adapter, planRefs);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueGetPlanStatus(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.plandistribution.consumer.PlanDistributionAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.plandistribution.PlanDistributionServiceInfo.GETPLANSTATUS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * Register method for the monitorPlanStatus PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void monitorPlanStatusRegister(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.mps.plandistribution.consumer.PlanDistributionAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.register(org.ccsds.moims.mo.mps.plandistribution.PlanDistributionServiceInfo.MONITORPLANSTATUS_OP, subscription, adapter);
    }

    /**
     * Asynchronous version of method monitorPlanStatusRegister.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncMonitorPlanStatusRegister(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.mps.plandistribution.consumer.PlanDistributionAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRegister(org.ccsds.moims.mo.mps.plandistribution.PlanDistributionServiceInfo.MONITORPLANSTATUS_OP, subscription, adapter);
    }

    /**
     * Deregister method for the monitorPlanStatus PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void monitorPlanStatusDeregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.deregister(org.ccsds.moims.mo.mps.plandistribution.PlanDistributionServiceInfo.MONITORPLANSTATUS_OP, identifierList);
    }

    /**
     * Asynchronous version of method monitorPlanStatusDeregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncMonitorPlanStatusDeregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList,
            org.ccsds.moims.mo.mps.plandistribution.consumer.PlanDistributionAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncDeregister(org.ccsds.moims.mo.mps.plandistribution.PlanDistributionServiceInfo.MONITORPLANSTATUS_OP, identifierList, adapter);
    }

    /**
     * Register method for the monitorPlan PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void monitorPlanRegister(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.mps.plandistribution.consumer.PlanDistributionAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.register(org.ccsds.moims.mo.mps.plandistribution.PlanDistributionServiceInfo.MONITORPLAN_OP, subscription, adapter);
    }

    /**
     * Asynchronous version of method monitorPlanRegister.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncMonitorPlanRegister(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.mps.plandistribution.consumer.PlanDistributionAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRegister(org.ccsds.moims.mo.mps.plandistribution.PlanDistributionServiceInfo.MONITORPLAN_OP, subscription, adapter);
    }

    /**
     * Deregister method for the monitorPlan PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void monitorPlanDeregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.deregister(org.ccsds.moims.mo.mps.plandistribution.PlanDistributionServiceInfo.MONITORPLAN_OP, identifierList);
    }

    /**
     * Asynchronous version of method monitorPlanDeregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncMonitorPlanDeregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList,
            org.ccsds.moims.mo.mps.plandistribution.consumer.PlanDistributionAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncDeregister(org.ccsds.moims.mo.mps.plandistribution.PlanDistributionServiceInfo.MONITORPLAN_OP, identifierList, adapter);
    }

    /**
     * The queryPlan operation enables a consumer to retrieve a filtered set of
     * plans, based on an extended set of filter criteria, including relevant
     * fields of the plan information sections of the plan, as well as the type
     * of planning activities and planning events contained within the plan.
     * 
     * @param query The query field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void queryPlan(org.ccsds.moims.mo.mps.structures.PlanQuery query,
            org.ccsds.moims.mo.mps.plandistribution.consumer.PlanDistributionAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.progress(org.ccsds.moims.mo.mps.plandistribution.PlanDistributionServiceInfo.QUERYPLAN_OP, adapter, query);
    }

    /**
     * Asynchronous version of method queryPlan.
     * 
     * @param query The query field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncQueryPlan(org.ccsds.moims.mo.mps.structures.PlanQuery query,
            org.ccsds.moims.mo.mps.plandistribution.consumer.PlanDistributionAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncProgress(org.ccsds.moims.mo.mps.plandistribution.PlanDistributionServiceInfo.QUERYPLAN_OP, adapter, query);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueQueryPlan(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.plandistribution.consumer.PlanDistributionAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.plandistribution.PlanDistributionServiceInfo.QUERYPLAN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The getPartialPlan operation enables a consumer to extract a subset of
     * a Plan that meets the supplied partialPlanFilter.  The filter can select
     * the partial plan content based on: a shorter period than that covered by
     * the plan, specified by time, position, or events; a subset of contained
     * ActivityInstances, based on their domain, associated SubPlan or tags. The
     * PartialPlan returned includes the filter criteria and a version of the
     * plan containing only the ActivityInstances that match those criteria.
     * It is implementation dependent what is returned in terms of events and
     * resources, but it may be assumed that any related events and resources
     * would be included in the returned partial plan.
     * 
     * @param partialPlanFilter The partialPlanFilter field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mps.structures.PartialPlan getPartialPlan(org.ccsds.moims.mo.mps.structures.PartialPlanFilter partialPlanFilter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mps.plandistribution.PlanDistributionServiceInfo.GETPARTIALPLAN_OP, partialPlanFilter);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.PartialPlan());
        return (org.ccsds.moims.mo.mps.structures.PartialPlan) body0;
    }

    /**
     * Asynchronous version of method getPartialPlan.
     * 
     * @param partialPlanFilter The partialPlanFilter field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncGetPartialPlan(org.ccsds.moims.mo.mps.structures.PartialPlanFilter partialPlanFilter,
            org.ccsds.moims.mo.mps.plandistribution.consumer.PlanDistributionAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mps.plandistribution.PlanDistributionServiceInfo.GETPARTIALPLAN_OP, adapter, partialPlanFilter);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueGetPartialPlan(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.plandistribution.consumer.PlanDistributionAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.plandistribution.PlanDistributionServiceInfo.GETPARTIALPLAN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

}
