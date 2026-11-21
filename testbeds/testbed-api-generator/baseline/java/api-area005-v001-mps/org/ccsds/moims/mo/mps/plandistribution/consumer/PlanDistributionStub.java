package org.ccsds.moims.mo.mps.plandistribution.consumer;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mps.InvalidException;
import org.ccsds.moims.mo.mps.plandistribution.PlanDistributionServiceInfo;
import org.ccsds.moims.mo.mps.structures.PartialPlan;
import org.ccsds.moims.mo.mps.structures.PartialPlanFilter;
import org.ccsds.moims.mo.mps.structures.PlanFilter;
import org.ccsds.moims.mo.mps.structures.PlanQuery;
import org.ccsds.moims.mo.mps.structures.PlanSummaryStatusList;
import org.ccsds.moims.mo.mps.structures.PlanUpdateList;

/**
 * Consumer stub for PlanDistribution service.
 */
public class PlanDistributionStub {

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
    public PlanDistributionStub(MALConsumer consumer) {
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
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public PlanSummaryStatusList getPlanSummaries(PlanFilter planFilter) throws InvalidException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(PlanDistributionServiceInfo.GETPLANSUMMARIES_OP, planFilter);
            Object body0 = (Object) body.getBodyElement(0, new PlanSummaryStatusList());
            return (PlanSummaryStatusList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method getPlanSummaries.
     * 
     * @param planFilter The planFilter field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncGetPlanSummaries(PlanFilter planFilter,
            PlanDistributionAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(PlanDistributionServiceInfo.GETPLANSUMMARIES_OP, adapter, planFilter);
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
    public void continueGetPlanSummaries(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanDistributionAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanDistributionServiceInfo.GETPLANSUMMARIES_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The getPlan operation is used to obtain the full content of one or more
     * known Plans.  The operation uses the Progress interaction pattern, to allow
     * the response to be spread across multiple messages.
     * 
     * @param planRefs The planRefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void getPlan(ObjectRefList planRefs,
            PlanDistributionAdapter adapter) throws InvalidException, MALStandardError, MALException {
        try {
            consumer.progress(PlanDistributionServiceInfo.GETPLAN_OP, adapter, planRefs);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method getPlan.
     * 
     * @param planRefs The planRefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncGetPlan(ObjectRefList planRefs,
            PlanDistributionAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncProgress(PlanDistributionServiceInfo.GETPLAN_OP, adapter, planRefs);
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
    public void continueGetPlan(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanDistributionAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanDistributionServiceInfo.GETPLAN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The getPlanStatus operation is used to obtain the current status of one
     * or more known Plans.  The operation uses the Request interaction pattern.
     * 
     * @param planRefs The planRefs field.
     * @return The return value of the interaction
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public PlanUpdateList getPlanStatus(ObjectRefList planRefs) throws InvalidException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(PlanDistributionServiceInfo.GETPLANSTATUS_OP, planRefs);
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
            PlanDistributionAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(PlanDistributionServiceInfo.GETPLANSTATUS_OP, adapter, planRefs);
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
            PlanDistributionAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanDistributionServiceInfo.GETPLANSTATUS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Register method for the monitorPlanStatus PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorPlanStatusRegister(Subscription subscription,
            PlanDistributionAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.register(PlanDistributionServiceInfo.MONITORPLANSTATUS_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorPlanStatusRegister.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncMonitorPlanStatusRegister(Subscription subscription,
            PlanDistributionAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRegister(PlanDistributionServiceInfo.MONITORPLANSTATUS_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Deregister method for the monitorPlanStatus PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorPlanStatusDeregister(IdentifierList identifierList) throws MALStandardError, MALException {
        try {
            consumer.deregister(PlanDistributionServiceInfo.MONITORPLANSTATUS_OP, identifierList);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorPlanStatusDeregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncMonitorPlanStatusDeregister(IdentifierList identifierList,
            PlanDistributionAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncDeregister(PlanDistributionServiceInfo.MONITORPLANSTATUS_OP, identifierList, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Register method for the monitorPlan PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorPlanRegister(Subscription subscription,
            PlanDistributionAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.register(PlanDistributionServiceInfo.MONITORPLAN_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorPlanRegister.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncMonitorPlanRegister(Subscription subscription,
            PlanDistributionAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRegister(PlanDistributionServiceInfo.MONITORPLAN_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Deregister method for the monitorPlan PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorPlanDeregister(IdentifierList identifierList) throws MALStandardError, MALException {
        try {
            consumer.deregister(PlanDistributionServiceInfo.MONITORPLAN_OP, identifierList);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorPlanDeregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncMonitorPlanDeregister(IdentifierList identifierList,
            PlanDistributionAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncDeregister(PlanDistributionServiceInfo.MONITORPLAN_OP, identifierList, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The queryPlan operation enables a consumer to retrieve a filtered set of
     * plans, based on an extended set of filter criteria, including relevant
     * fields of the plan information sections of the plan, as well as the type
     * of planning activities and planning events contained within the plan.
     * 
     * @param query The query field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void queryPlan(PlanQuery query,
            PlanDistributionAdapter adapter) throws InvalidException, MALStandardError, MALException {
        try {
            consumer.progress(PlanDistributionServiceInfo.QUERYPLAN_OP, adapter, query);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method queryPlan.
     * 
     * @param query The query field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncQueryPlan(PlanQuery query,
            PlanDistributionAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncProgress(PlanDistributionServiceInfo.QUERYPLAN_OP, adapter, query);
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
    public void continueQueryPlan(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanDistributionAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanDistributionServiceInfo.QUERYPLAN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
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
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public PartialPlan getPartialPlan(PartialPlanFilter partialPlanFilter) throws InvalidException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(PlanDistributionServiceInfo.GETPARTIALPLAN_OP, partialPlanFilter);
            Object body0 = (Object) body.getBodyElement(0, new PartialPlan());
            return (PartialPlan) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method getPartialPlan.
     * 
     * @param partialPlanFilter The partialPlanFilter field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncGetPartialPlan(PartialPlanFilter partialPlanFilter,
            PlanDistributionAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(PlanDistributionServiceInfo.GETPARTIALPLAN_OP, adapter, partialPlanFilter);
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
    public void continueGetPartialPlan(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanDistributionAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanDistributionServiceInfo.GETPARTIALPLAN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

}
