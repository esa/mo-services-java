package org.ccsds.moims.mo.mps.planexecutioncontrol.consumer;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mps.ActivateFailedException;
import org.ccsds.moims.mo.mps.ActivateSubplanFailedException;
import org.ccsds.moims.mo.mps.DeactivateFailedException;
import org.ccsds.moims.mo.mps.DeactivateSubplanFailedException;
import org.ccsds.moims.mo.mps.InvalidException;
import org.ccsds.moims.mo.mps.RevokeFailedException;
import org.ccsds.moims.mo.mps.SubmitFailedException;
import org.ccsds.moims.mo.mps.UnsupportedException;
import org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo;
import org.ccsds.moims.mo.mps.structures.ActivitySuspensionStatusList;
import org.ccsds.moims.mo.mps.structures.ActivityUpdateList;
import org.ccsds.moims.mo.mps.structures.Plan;
import org.ccsds.moims.mo.mps.structures.PlanActivationStatusList;
import org.ccsds.moims.mo.mps.structures.PlanUpdateList;
import org.ccsds.moims.mo.mps.structures.SubPlanActivationStatusList;
import org.ccsds.moims.mo.mps.structures.SubPlanUpdateList;

/**
 * Consumer stub for PlanExecutionControl service.
 */
public class PlanExecutionControlStub {

    /**
     * The consumer field.
     */
    private final MALConsumer consumer;

    /**
     * Wraps a MALconsumer connection with service specific methods that map from
     * the high level service API to the generic MAL API.
     * 
     * @param consumer consumer The MALConsumer to use in this stub.
     */
    public PlanExecutionControlStub(MALConsumer consumer) {
        this.consumer = consumer;
    }

    /**
     * Returns the internal MAL consumer object used for sending of messages from
     * this interface.
     * 
     * @return The MAL consumer object.
     */
    public MALConsumer getConsumer() {
        return consumer;
    }

    /**
     * The submitPlan operation is used to send a plan to a plan execution function
     * (the service provider), making it available for execution.  The service
     * provider acknowledges the reception of the plan or returns an error. NOTE
     * – The submitted plan may be a full plan or a patch plan.
     * 
     * @param plan The plan field.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws SubmitFailedException The submitPlan operation failed as the submitted plan was already terminated.
     * @throws UnsupportedException An optional data structure used in the message is not supported by the service provider.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void submitPlan(Plan plan) throws InvalidException, SubmitFailedException, UnsupportedException, MALStandardError, MALException {
        try {
            consumer.submit(PlanExecutionControlServiceInfo.SUBMITPLAN_OP, plan);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof SubmitFailedException) {
                throw (SubmitFailedException) error;
            }
            if (error instanceof UnsupportedException) {
                throw (UnsupportedException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method submitPlan.
     * 
     * @param plan The plan field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncSubmitPlan(Plan plan,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(PlanExecutionControlServiceInfo.SUBMITPLAN_OP, adapter, plan);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueSubmitPlan(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanExecutionControlServiceInfo.SUBMITPLAN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The revokePlan operation is used to request a plan execution function to
     * revoke a previously submitted Plan, making it unavailable for execution.
     * The service provider acknowledges the revocation of the Plan or returns
     * an error.
     * 
     * @param planRef The planRef field.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws RevokeFailedException The revokePlan operation failed to revoke the referenced Plan, for example because it has already started executing.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void revokePlan(ObjectRef<Plan> planRef) throws InvalidException, RevokeFailedException, MALStandardError, MALException {
        try {
            consumer.submit(PlanExecutionControlServiceInfo.REVOKEPLAN_OP, planRef);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof RevokeFailedException) {
                throw (RevokeFailedException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method revokePlan.
     * 
     * @param planRef The planRef field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncRevokePlan(ObjectRef<Plan> planRef,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(PlanExecutionControlServiceInfo.REVOKEPLAN_OP, adapter, planRef);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueRevokePlan(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanExecutionControlServiceInfo.REVOKEPLAN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The getPlanStatus operation is used to obtain the current status of one
     * or more known Plans that have been previously submitted to a plan execution
     * function.
     * 
     * @param planRefs The planRefs field.
     * @return The return value of the interaction
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public PlanUpdateList getPlanStatus(ObjectRefList planRefs) throws InvalidException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(PlanExecutionControlServiceInfo.GETPLANSTATUS_OP, planRefs);
            Object body0 = (Object) body.getBodyElement(0, new PlanUpdateList());
            return (PlanUpdateList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method getPlanStatus.
     * 
     * @param planRefs The planRefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncGetPlanStatus(ObjectRefList planRefs,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(PlanExecutionControlServiceInfo.GETPLANSTATUS_OP, adapter, planRefs);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueGetPlanStatus(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanExecutionControlServiceInfo.GETPLANSTATUS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
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
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws ActivateFailedException The activatePlan operation failed as the activation was outside the validity period of the Plan, or the start of the planPeriod had already passed.  
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public PlanActivationStatusList activatePlan(ObjectRefList planRefs) throws InvalidException, ActivateFailedException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(PlanExecutionControlServiceInfo.ACTIVATEPLAN_OP, planRefs);
            Object body0 = (Object) body.getBodyElement(0, new PlanActivationStatusList());
            return (PlanActivationStatusList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof ActivateFailedException) {
                throw (ActivateFailedException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method activatePlan.
     * 
     * @param planRefs The planRefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncActivatePlan(ObjectRefList planRefs,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(PlanExecutionControlServiceInfo.ACTIVATEPLAN_OP, adapter, planRefs);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueActivatePlan(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanExecutionControlServiceInfo.ACTIVATEPLAN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
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
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws DeactivateFailedException The deactivatePlan operation failed.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public PlanActivationStatusList deactivatePlan(ObjectRefList planRefs,
            Identifier deactivationMode) throws InvalidException, DeactivateFailedException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(PlanExecutionControlServiceInfo.DEACTIVATEPLAN_OP, planRefs, deactivationMode);
            Object body0 = (Object) body.getBodyElement(0, new PlanActivationStatusList());
            return (PlanActivationStatusList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof DeactivateFailedException) {
                throw (DeactivateFailedException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method deactivatePlan.
     * 
     * @param planRefs The planRefs field.
     * @param deactivationMode The deactivationMode field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncDeactivatePlan(ObjectRefList planRefs,
            Identifier deactivationMode,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(PlanExecutionControlServiceInfo.DEACTIVATEPLAN_OP, adapter, planRefs, deactivationMode);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueDeactivatePlan(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanExecutionControlServiceInfo.DEACTIVATEPLAN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Register method for the monitorPlanExecution PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorPlanExecutionRegister(Subscription subscription,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.register(PlanExecutionControlServiceInfo.MONITORPLANEXECUTION_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorPlanExecutionRegister.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncMonitorPlanExecutionRegister(Subscription subscription,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRegister(PlanExecutionControlServiceInfo.MONITORPLANEXECUTION_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Deregister method for the monitorPlanExecution PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorPlanExecutionDeregister(IdentifierList identifierList) throws MALStandardError, MALException {
        try {
            consumer.deregister(PlanExecutionControlServiceInfo.MONITORPLANEXECUTION_OP, identifierList);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorPlanExecutionDeregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncMonitorPlanExecutionDeregister(IdentifierList identifierList,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncDeregister(PlanExecutionControlServiceInfo.MONITORPLANEXECUTION_OP, identifierList, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Register method for the monitorPlanExecutionDetail PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorPlanExecutionDetailRegister(Subscription subscription,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.register(PlanExecutionControlServiceInfo.MONITORPLANEXECUTIONDETAIL_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorPlanExecutionDetailRegister.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncMonitorPlanExecutionDetailRegister(Subscription subscription,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRegister(PlanExecutionControlServiceInfo.MONITORPLANEXECUTIONDETAIL_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Deregister method for the monitorPlanExecutionDetail PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorPlanExecutionDetailDeregister(IdentifierList identifierList) throws MALStandardError, MALException {
        try {
            consumer.deregister(PlanExecutionControlServiceInfo.MONITORPLANEXECUTIONDETAIL_OP, identifierList);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorPlanExecutionDetailDeregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncMonitorPlanExecutionDetailDeregister(IdentifierList identifierList,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncDeregister(PlanExecutionControlServiceInfo.MONITORPLANEXECUTIONDETAIL_OP, identifierList, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
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
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws ActivateSubplanFailedException The activateSubPlan operation failed.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public SubPlanActivationStatusList activateSubPlan(IdentifierList subPlanIDs) throws InvalidException, ActivateSubplanFailedException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(PlanExecutionControlServiceInfo.ACTIVATESUBPLAN_OP, subPlanIDs);
            Object body0 = (Object) body.getBodyElement(0, new SubPlanActivationStatusList());
            return (SubPlanActivationStatusList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof ActivateSubplanFailedException) {
                throw (ActivateSubplanFailedException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method activateSubPlan.
     * 
     * @param subPlanIDs The subPlanIDs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncActivateSubPlan(IdentifierList subPlanIDs,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(PlanExecutionControlServiceInfo.ACTIVATESUBPLAN_OP, adapter, subPlanIDs);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueActivateSubPlan(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanExecutionControlServiceInfo.ACTIVATESUBPLAN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
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
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws DeactivateSubplanFailedException The deactivateSubPlan operation failed.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public SubPlanActivationStatusList deactivateSubPlan(IdentifierList subPlanIDs,
            String deactivationMode) throws InvalidException, DeactivateSubplanFailedException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(PlanExecutionControlServiceInfo.DEACTIVATESUBPLAN_OP, subPlanIDs, (deactivationMode == null) ? null : new Union(deactivationMode));
            Object body0 = (Object) body.getBodyElement(0, new SubPlanActivationStatusList());
            return (SubPlanActivationStatusList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof DeactivateSubplanFailedException) {
                throw (DeactivateSubplanFailedException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method deactivateSubPlan.
     * 
     * @param subPlanIDs The subPlanIDs field.
     * @param deactivationMode The deactivationMode field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncDeactivateSubPlan(IdentifierList subPlanIDs,
            String deactivationMode,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(PlanExecutionControlServiceInfo.DEACTIVATESUBPLAN_OP, adapter, subPlanIDs, (deactivationMode == null) ? null : new Union(deactivationMode));
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueDeactivateSubPlan(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanExecutionControlServiceInfo.DEACTIVATESUBPLAN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The getSubPlanStatus operation is used to obtain the current status of
     * one or more SubPlans.
     * 
     * @param subPlanIDs The subPlanIDs field.
     * @return The return value of the interaction
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public SubPlanUpdateList getSubPlanStatus(IdentifierList subPlanIDs) throws InvalidException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(PlanExecutionControlServiceInfo.GETSUBPLANSTATUS_OP, subPlanIDs);
            Object body0 = (Object) body.getBodyElement(0, new SubPlanUpdateList());
            return (SubPlanUpdateList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method getSubPlanStatus.
     * 
     * @param subPlanIDs The subPlanIDs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncGetSubPlanStatus(IdentifierList subPlanIDs,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(PlanExecutionControlServiceInfo.GETSUBPLANSTATUS_OP, adapter, subPlanIDs);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueGetSubPlanStatus(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanExecutionControlServiceInfo.GETSUBPLANSTATUS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Register method for the monitorSubPlanExecution PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorSubPlanExecutionRegister(Subscription subscription,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.register(PlanExecutionControlServiceInfo.MONITORSUBPLANEXECUTION_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorSubPlanExecutionRegister.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncMonitorSubPlanExecutionRegister(Subscription subscription,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRegister(PlanExecutionControlServiceInfo.MONITORSUBPLANEXECUTION_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Deregister method for the monitorSubPlanExecution PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorSubPlanExecutionDeregister(IdentifierList identifierList) throws MALStandardError, MALException {
        try {
            consumer.deregister(PlanExecutionControlServiceInfo.MONITORSUBPLANEXECUTION_OP, identifierList);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorSubPlanExecutionDeregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncMonitorSubPlanExecutionDeregister(IdentifierList identifierList,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncDeregister(PlanExecutionControlServiceInfo.MONITORSUBPLANEXECUTION_OP, identifierList, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
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
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public ActivitySuspensionStatusList suspendActivity(ObjectRefList planRefs,
            ObjectRefList activityRefs,
            StringList tags,
            String suspensionMode) throws InvalidException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(PlanExecutionControlServiceInfo.SUSPENDACTIVITY_OP, planRefs, activityRefs, tags, (suspensionMode == null) ? null : new Union(suspensionMode));
            Object body0 = (Object) body.getBodyElement(0, new ActivitySuspensionStatusList());
            return (ActivitySuspensionStatusList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
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
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncSuspendActivity(ObjectRefList planRefs,
            ObjectRefList activityRefs,
            StringList tags,
            String suspensionMode,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(PlanExecutionControlServiceInfo.SUSPENDACTIVITY_OP, adapter, planRefs, activityRefs, tags, (suspensionMode == null) ? null : new Union(suspensionMode));
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueSuspendActivity(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanExecutionControlServiceInfo.SUSPENDACTIVITY_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
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
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public ActivitySuspensionStatusList resumeActivity(ObjectRefList planRefs,
            ObjectRefList activityRefs,
            StringList tags) throws InvalidException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(PlanExecutionControlServiceInfo.RESUMEACTIVITY_OP, planRefs, activityRefs, tags);
            Object body0 = (Object) body.getBodyElement(0, new ActivitySuspensionStatusList());
            return (ActivitySuspensionStatusList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method resumeActivity.
     * 
     * @param planRefs The planRefs field.
     * @param activityRefs The activityRefs field.
     * @param tags The tags field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncResumeActivity(ObjectRefList planRefs,
            ObjectRefList activityRefs,
            StringList tags,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(PlanExecutionControlServiceInfo.RESUMEACTIVITY_OP, adapter, planRefs, activityRefs, tags);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueResumeActivity(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanExecutionControlServiceInfo.RESUMEACTIVITY_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
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
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public ActivityUpdateList getActivityStatus(ObjectRefList planRefs,
            ObjectRefList activityRefs,
            IdentifierList subPlans,
            StringList tags) throws InvalidException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(PlanExecutionControlServiceInfo.GETACTIVITYSTATUS_OP, planRefs, activityRefs, subPlans, tags);
            Object body0 = (Object) body.getBodyElement(0, new ActivityUpdateList());
            return (ActivityUpdateList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
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
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncGetActivityStatus(ObjectRefList planRefs,
            ObjectRefList activityRefs,
            IdentifierList subPlans,
            StringList tags,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(PlanExecutionControlServiceInfo.GETACTIVITYSTATUS_OP, adapter, planRefs, activityRefs, subPlans, tags);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueGetActivityStatus(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanExecutionControlAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanExecutionControlServiceInfo.GETACTIVITYSTATUS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

}
