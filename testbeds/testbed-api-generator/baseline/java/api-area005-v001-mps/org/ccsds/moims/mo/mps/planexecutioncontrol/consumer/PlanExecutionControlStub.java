package org.ccsds.moims.mo.mps.planexecutioncontrol.consumer;

/**
 * Consumer stub for PlanExecutionControl service.
 */
public class PlanExecutionControlStub {

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
    public PlanExecutionControlStub(org.ccsds.moims.mo.mal.consumer.MALConsumer consumer) {
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
     * The submitPlan operation is used to send a plan to a plan execution function
     * (the service provider), making it available for execution.  The service
     * provider acknowledges the reception of the plan or returns an error. NOTE
     * – The submitted plan may be a full plan or a patch plan.
     * 
     * @param plan The plan field.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void submitPlan(org.ccsds.moims.mo.mps.structures.Plan plan) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.SUBMITPLAN_OP, plan);
    }

    /**
     * Asynchronous version of method submitPlan.
     * 
     * @param plan The plan field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncSubmitPlan(org.ccsds.moims.mo.mps.structures.Plan plan,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.SUBMITPLAN_OP, adapter, plan);
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
    public void continueSubmitPlan(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.SUBMITPLAN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The revokePlan operation is used to request a plan execution function to
     * revoke a previously submitted Plan, making it unavailable for execution.
     * The service provider acknowledges the revocation of the Plan or returns
     * an error.
     * 
     * @param planRef The planRef field.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void revokePlan(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.REVOKEPLAN_OP, planRef);
    }

    /**
     * Asynchronous version of method revokePlan.
     * 
     * @param planRef The planRef field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncRevokePlan(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.REVOKEPLAN_OP, adapter, planRef);
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
    public void continueRevokePlan(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.REVOKEPLAN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The getPlanStatus operation is used to obtain the current status of one
     * or more known Plans that have been previously submitted to a plan execution
     * function.
     * 
     * @param planRefs The planRefs field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mps.structures.PlanUpdateList getPlanStatus(org.ccsds.moims.mo.mal.structures.ObjectRefList planRefs) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.GETPLANSTATUS_OP, planRefs);
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
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.GETPLANSTATUS_OP, adapter, planRefs);
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
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.GETPLANSTATUS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The activatePlan operation is used to request the execution of specified
     * Plans that have previously been submitted to a plan execution function.
     * The service provider enables the execution of the referenced Plans and
     * the ActivityInstances contained within them, subject to the triggering
     * constraints specified within the Plans.  It is not possible to activate
     * a Plan outside its validity period, or after the start of the Plan period.
     * In this case, the operation will return an ACTIVATE_FAILED error. NOTES
     * Multiple plans with a common precursor may have been submitted to a plan
     * execution function.  Usually only one of these is considered the nominal
     * plan, the other alternative or contingency plans having the isAlternate
     * flag set.  It is implementation dependent whether the service provider
     * will allow activation of Plans that have the isAlternate flag set, but
     * this may be blocked for operational safety.  Where this is the case, the
     * plan edit service can be used to change the state of the isAlternate flag
     * prior to activation (see 3.9.5). In order to activate a patch Plan, the
     * precursor Plan on which it is based must also be activated.  It is recommended
     * that the activatePlan operation references the target Plan (the result
     * of merging the patch Plan with its precursor), rather than the patch Plan
     * itself (although this is allowed).  It is implementation dependent how
     * it is achieved (merge patch with precursor prior to activation, or activate
     * precursor and then merge patch), but if the precursor Plan is not already
     * activated, then activating a target or patch Plan implies that the precursor
     * is also activated.  If the precursor plan has not previously been submitted
     * to the service provider (or has been revoked), then it is not possible
     * to activate the target or patch Plan.
     * 
     * @param planRefs The planRefs field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mps.structures.PlanActivationStatusList activatePlan(org.ccsds.moims.mo.mal.structures.ObjectRefList planRefs) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.ACTIVATEPLAN_OP, planRefs);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.PlanActivationStatusList());
        return (org.ccsds.moims.mo.mps.structures.PlanActivationStatusList) body0;
    }

    /**
     * Asynchronous version of method activatePlan.
     * 
     * @param planRefs The planRefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncActivatePlan(org.ccsds.moims.mo.mal.structures.ObjectRefList planRefs,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.ACTIVATEPLAN_OP, adapter, planRefs);
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
    public void continueActivatePlan(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.ACTIVATEPLAN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The deactivatePlan operation is used to request deactivation of specified
     * Plans that have previously been activated.  The service provider disables
     * the execution of the referenced Plans and the ActivityInstances contained
     * within them, where it is possible to do so. The deactivationMode argument
     * allows selection of the deactivation behavior.  For example: Orderly (ceases
     * execution of any new activities, but allows those already initiated to
     * complete); Rapid (ceases execution of the Plan, but allows activities already
     * initiated to continue until their next defined breakpoint); Immediate (ceases
     * execution of the Plan and all activities currently in progress). It should
     * be noted that it is dependent on the service provider implementation which
     * deactivationModes are supported, and that the above list is not exhaustive.
     * The service provider returns a list of PlanActivationStatus data structures
     * comprising Plan status and activationInfo as a String for each Plan in
     * the deactivation list.  The activationInfo allows the return of deployment
     * specific details on the deactivation, such as the deactivation mode applied
     * or reasons for a failure to deactivate. If a Plan is deactivated prior
     * to any of its constituent ActivityInstances being executed (or before the
     * specified planPeriodStart), then all new ActivityInstances and EventInstances
     * contained in the Plan are unloaded or removed, and the status of the Plan
     * reverts to SUBMITTED. If a Plan is deactivated after any of its constituent
     * ActivityInstances have been executed (or after the specified planPeriodStart),
     * then the status of the Plan and the status of all contained ActivityInstances
     * and EventInstances that will not be executed are set to TERMINATED with
     * the additional statusInfo ‘CANCELLED’.
     * 
     * @param planRefs The planRefs field.
     * @param deactivationMode The deactivationMode field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mps.structures.PlanActivationStatusList deactivatePlan(org.ccsds.moims.mo.mal.structures.ObjectRefList planRefs,
            org.ccsds.moims.mo.mal.structures.Identifier deactivationMode) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.DEACTIVATEPLAN_OP, planRefs, deactivationMode);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.PlanActivationStatusList());
        return (org.ccsds.moims.mo.mps.structures.PlanActivationStatusList) body0;
    }

    /**
     * Asynchronous version of method deactivatePlan.
     * 
     * @param planRefs The planRefs field.
     * @param deactivationMode The deactivationMode field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncDeactivatePlan(org.ccsds.moims.mo.mal.structures.ObjectRefList planRefs,
            org.ccsds.moims.mo.mal.structures.Identifier deactivationMode,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.DEACTIVATEPLAN_OP, adapter, planRefs, deactivationMode);
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
    public void continueDeactivatePlan(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.DEACTIVATEPLAN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * Register method for the monitorPlanExecution PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void monitorPlanExecutionRegister(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.register(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.MONITORPLANEXECUTION_OP, subscription, adapter);
    }

    /**
     * Asynchronous version of method monitorPlanExecutionRegister.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncMonitorPlanExecutionRegister(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRegister(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.MONITORPLANEXECUTION_OP, subscription, adapter);
    }

    /**
     * Deregister method for the monitorPlanExecution PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void monitorPlanExecutionDeregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.deregister(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.MONITORPLANEXECUTION_OP, identifierList);
    }

    /**
     * Asynchronous version of method monitorPlanExecutionDeregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncMonitorPlanExecutionDeregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncDeregister(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.MONITORPLANEXECUTION_OP, identifierList, adapter);
    }

    /**
     * Register method for the monitorPlanExecutionDetail PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void monitorPlanExecutionDetailRegister(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.register(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.MONITORPLANEXECUTIONDETAIL_OP, subscription, adapter);
    }

    /**
     * Asynchronous version of method monitorPlanExecutionDetailRegister.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncMonitorPlanExecutionDetailRegister(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRegister(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.MONITORPLANEXECUTIONDETAIL_OP, subscription, adapter);
    }

    /**
     * Deregister method for the monitorPlanExecutionDetail PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void monitorPlanExecutionDetailDeregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.deregister(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.MONITORPLANEXECUTIONDETAIL_OP, identifierList);
    }

    /**
     * Asynchronous version of method monitorPlanExecutionDetailDeregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncMonitorPlanExecutionDetailDeregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncDeregister(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.MONITORPLANEXECUTIONDETAIL_OP, identifierList, adapter);
    }

    /**
     * The activateSubPlan operation is used to request that the service provider
     * activates the referenced SubPlans and enables the execution of ActivityInstances
     * that are contained in activated Plans and allocated to activated SubPlans.
     * NOTES It is implementation dependent whether SubPlans are initially ACTIVATED
     * and therefore do not require activation unless previously deactivated.
     * Where the operation is directly supported by the service provider there
     * is little reason for the activation to fail, but if the operation is delegated,
     * for example to an on-board planning function, there is the potential for
     * the operation to fail.
     * 
     * @param subPlanIDs The subPlanIDs field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mps.structures.SubPlanActivationStatusList activateSubPlan(org.ccsds.moims.mo.mal.structures.IdentifierList subPlanIDs) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.ACTIVATESUBPLAN_OP, subPlanIDs);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.SubPlanActivationStatusList());
        return (org.ccsds.moims.mo.mps.structures.SubPlanActivationStatusList) body0;
    }

    /**
     * Asynchronous version of method activateSubPlan.
     * 
     * @param subPlanIDs The subPlanIDs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncActivateSubPlan(org.ccsds.moims.mo.mal.structures.IdentifierList subPlanIDs,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.ACTIVATESUBPLAN_OP, adapter, subPlanIDs);
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
    public void continueActivateSubPlan(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.ACTIVATESUBPLAN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The deactivateSubPlan operation is used to request that the service provider
     * deactivates the referenced SubPlans and disables the execution of ActivityInstances
     * that are contained in activated Plans and allocated to the deactivated
     * SubPlans, where it is possible to do so. The deactivationMode argument
     * allows selection of the deactivation behavior.  For example: Orderly (ceases
     * execution of any new activities, but allows those already initiated to
     * complete); Rapid (ceases execution of the Sub-plan, but allows activities
     * already initiated to continue until their next defined breakpoint); Immediate
     * (ceases execution of the Sub-plan and all activities currently in progress).
     * It should be noted that it is dependent on the service provider implementation
     * which deactivationModes are supported, and that the above list is not exhaustive.
     * The service provider returns a list of SubPlanActivationStatus data structures
     * comprising sub-plan status and activationInfo as a String for each sub-plan
     * in the deactivation list.  The activationInfo allows the return of deployment
     * specific details on the deactivation, such as the deactivation mode applied
     * or reasons for a failure to deactivate. NOTE – Where the operation is directly
     * supported by the service provider there is little reason for the deactivation
     * to fail, but if the operation is delegated, for example to an on-board
     * planning function, there is the potential for the operation to fail.
     * 
     * @param subPlanIDs The subPlanIDs field.
     * @param deactivationMode The deactivationMode field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mps.structures.SubPlanActivationStatusList deactivateSubPlan(org.ccsds.moims.mo.mal.structures.IdentifierList subPlanIDs,
            String deactivationMode) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.DEACTIVATESUBPLAN_OP, subPlanIDs, (deactivationMode == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(deactivationMode));
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.SubPlanActivationStatusList());
        return (org.ccsds.moims.mo.mps.structures.SubPlanActivationStatusList) body0;
    }

    /**
     * Asynchronous version of method deactivateSubPlan.
     * 
     * @param subPlanIDs The subPlanIDs field.
     * @param deactivationMode The deactivationMode field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncDeactivateSubPlan(org.ccsds.moims.mo.mal.structures.IdentifierList subPlanIDs,
            String deactivationMode,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.DEACTIVATESUBPLAN_OP, adapter, subPlanIDs, (deactivationMode == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(deactivationMode));
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
    public void continueDeactivateSubPlan(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.DEACTIVATESUBPLAN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The getSubPlanStatus operation is used to obtain the current status of
     * one or more SubPlans.
     * 
     * @param subPlanIDs The subPlanIDs field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mps.structures.SubPlanUpdateList getSubPlanStatus(org.ccsds.moims.mo.mal.structures.IdentifierList subPlanIDs) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.GETSUBPLANSTATUS_OP, subPlanIDs);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.SubPlanUpdateList());
        return (org.ccsds.moims.mo.mps.structures.SubPlanUpdateList) body0;
    }

    /**
     * Asynchronous version of method getSubPlanStatus.
     * 
     * @param subPlanIDs The subPlanIDs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncGetSubPlanStatus(org.ccsds.moims.mo.mal.structures.IdentifierList subPlanIDs,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.GETSUBPLANSTATUS_OP, adapter, subPlanIDs);
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
    public void continueGetSubPlanStatus(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.GETSUBPLANSTATUS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * Register method for the monitorSubPlanExecution PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void monitorSubPlanExecutionRegister(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.register(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.MONITORSUBPLANEXECUTION_OP, subscription, adapter);
    }

    /**
     * Asynchronous version of method monitorSubPlanExecutionRegister.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncMonitorSubPlanExecutionRegister(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRegister(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.MONITORSUBPLANEXECUTION_OP, subscription, adapter);
    }

    /**
     * Deregister method for the monitorSubPlanExecution PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void monitorSubPlanExecutionDeregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.deregister(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.MONITORSUBPLANEXECUTION_OP, identifierList);
    }

    /**
     * Asynchronous version of method monitorSubPlanExecutionDeregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncMonitorSubPlanExecutionDeregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncDeregister(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.MONITORSUBPLANEXECUTION_OP, identifierList, adapter);
    }

    /**
     * The suspendActivity operation is used to request suspension of the execution
     * of selected activities in one or more plans, without changing the state
     * of the plan(s). The suspensionMode argument allows selection of the suspension
     * behavior.  For example: Orderly (suspends execution of any new activities,
     * but allows those already initiated to complete); Rapid (suspends execution
     * of any new activities, but allows any activities and their sub-activities
     * already initiated to continue until their next defined breakpoint); Immediate
     * (suspends execution of all activities, including those currently in progress).
     * It should be noted that it is dependent on the service provider implementation
     * which deactivationModes are supported, and that the above list is not exhaustive.
     * The service provider responds with a list of ActivitySuspensionStatus data
     * structures comprising activity status and suspensionInfo (as a String)
     * for each activity subject to the suspension request. The suspensionInfo
     * allows the return of deployment specific details on the suspension, such
     * as the suspension mode applied or reasons for a failure to suspend.
     * 
     * @param planRefs The planRefs field.
     * @param activityRefs The activityRefs field.
     * @param tags The tags field.
     * @param suspensionMode The suspensionMode field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mps.structures.ActivitySuspensionStatusList suspendActivity(org.ccsds.moims.mo.mal.structures.ObjectRefList planRefs,
            org.ccsds.moims.mo.mal.structures.ObjectRefList activityRefs,
            org.ccsds.moims.mo.mal.structures.StringList tags,
            String suspensionMode) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.SUSPENDACTIVITY_OP, planRefs, activityRefs, tags, (suspensionMode == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(suspensionMode));
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.ActivitySuspensionStatusList());
        return (org.ccsds.moims.mo.mps.structures.ActivitySuspensionStatusList) body0;
    }

    /**
     * Asynchronous version of method suspendActivity.
     * 
     * @param planRefs The planRefs field.
     * @param activityRefs The activityRefs field.
     * @param tags The tags field.
     * @param suspensionMode The suspensionMode field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncSuspendActivity(org.ccsds.moims.mo.mal.structures.ObjectRefList planRefs,
            org.ccsds.moims.mo.mal.structures.ObjectRefList activityRefs,
            org.ccsds.moims.mo.mal.structures.StringList tags,
            String suspensionMode,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.SUSPENDACTIVITY_OP, adapter, planRefs, activityRefs, tags, (suspensionMode == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(suspensionMode));
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
    public void continueSuspendActivity(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.SUSPENDACTIVITY_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The resumeActivity operation is used to request resumption of the execution
     * of selected activities in one or more plans, without changing the state
     * of the plan(s). The service provider responds with a list of ActivitySuspensionStatus
     * data structures comprising activity status and suspensionInfo (as a String)
     * for each activity subject to the resumption request. The suspensionInfo
     * allows the return of deployment specific details on the resumption, such
     * as the reasons for a failure to resume.
     * 
     * @param planRefs The planRefs field.
     * @param activityRefs The activityRefs field.
     * @param tags The tags field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mps.structures.ActivitySuspensionStatusList resumeActivity(org.ccsds.moims.mo.mal.structures.ObjectRefList planRefs,
            org.ccsds.moims.mo.mal.structures.ObjectRefList activityRefs,
            org.ccsds.moims.mo.mal.structures.StringList tags) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.RESUMEACTIVITY_OP, planRefs, activityRefs, tags);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.ActivitySuspensionStatusList());
        return (org.ccsds.moims.mo.mps.structures.ActivitySuspensionStatusList) body0;
    }

    /**
     * Asynchronous version of method resumeActivity.
     * 
     * @param planRefs The planRefs field.
     * @param activityRefs The activityRefs field.
     * @param tags The tags field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncResumeActivity(org.ccsds.moims.mo.mal.structures.ObjectRefList planRefs,
            org.ccsds.moims.mo.mal.structures.ObjectRefList activityRefs,
            org.ccsds.moims.mo.mal.structures.StringList tags,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.RESUMEACTIVITY_OP, adapter, planRefs, activityRefs, tags);
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
    public void continueResumeActivity(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.RESUMEACTIVITY_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The getActivityStatus operation is used to request a detailed report from
     * the service provider on the current status of ActivityInstances, selected
     * at activity, sub-plan, or tag levels.
     * 
     * @param planRefs The planRefs field.
     * @param activityRefs The activityRefs field.
     * @param subPlans The subPlans field.
     * @param tags The tags field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mps.structures.ActivityUpdateList getActivityStatus(org.ccsds.moims.mo.mal.structures.ObjectRefList planRefs,
            org.ccsds.moims.mo.mal.structures.ObjectRefList activityRefs,
            org.ccsds.moims.mo.mal.structures.IdentifierList subPlans,
            org.ccsds.moims.mo.mal.structures.StringList tags) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.GETACTIVITYSTATUS_OP, planRefs, activityRefs, subPlans, tags);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.ActivityUpdateList());
        return (org.ccsds.moims.mo.mps.structures.ActivityUpdateList) body0;
    }

    /**
     * Asynchronous version of method getActivityStatus.
     * 
     * @param planRefs The planRefs field.
     * @param activityRefs The activityRefs field.
     * @param subPlans The subPlans field.
     * @param tags The tags field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncGetActivityStatus(org.ccsds.moims.mo.mal.structures.ObjectRefList planRefs,
            org.ccsds.moims.mo.mal.structures.ObjectRefList activityRefs,
            org.ccsds.moims.mo.mal.structures.IdentifierList subPlans,
            org.ccsds.moims.mo.mal.structures.StringList tags,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.GETACTIVITYSTATUS_OP, adapter, planRefs, activityRefs, subPlans, tags);
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
    public void continueGetActivityStatus(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planexecutioncontrol.consumer.PlanExecutionControlAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.GETACTIVITYSTATUS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

}
