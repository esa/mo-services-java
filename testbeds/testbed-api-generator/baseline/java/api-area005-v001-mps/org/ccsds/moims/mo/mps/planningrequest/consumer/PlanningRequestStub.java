package org.ccsds.moims.mo.mps.planningrequest.consumer;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mps.CancelFailedException;
import org.ccsds.moims.mo.mps.InvalidException;
import org.ccsds.moims.mo.mps.UnsupportedException;
import org.ccsds.moims.mo.mps.UpdateFailedException;
import org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo;
import org.ccsds.moims.mo.mps.structures.PlanningRequestDetails;
import org.ccsds.moims.mo.mps.structures.PlanningRequestResponse;
import org.ccsds.moims.mo.mps.structures.RequestFilter;
import org.ccsds.moims.mo.mps.structures.RequestInstance;
import org.ccsds.moims.mo.mps.structures.RequestSummaryStatusList;

/**
 * Consumer stub for PlanningRequest service.
 */
public class PlanningRequestStub {

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
    public PlanningRequestStub(MALConsumer consumer) {
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
     * The submitRequest operation sends a planning request to the provider, which
     * then creates a corresponding RequestInstance object and returns its identity
     * to the consumer.
     * 
     * @param requestDetails The requestDetails field.
     * @return The return value of the interaction
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws UnsupportedException An optional data structure used in the message is not supported by the service provider.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public PlanningRequestResponse submitRequest(PlanningRequestDetails requestDetails) throws InvalidException, UnsupportedException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(PlanningRequestServiceInfo.SUBMITREQUEST_OP, requestDetails);
            Object body0 = (Object) body.getBodyElement(0, new PlanningRequestResponse());
            return (PlanningRequestResponse) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof UnsupportedException) {
                throw (UnsupportedException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method submitRequest.
     * 
     * @param requestDetails The requestDetails field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncSubmitRequest(PlanningRequestDetails requestDetails,
            PlanningRequestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(PlanningRequestServiceInfo.SUBMITREQUEST_OP, adapter, requestDetails);
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
    public void continueSubmitRequest(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanningRequestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanningRequestServiceInfo.SUBMITREQUEST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The getRequestSummaries operation allows consumers to obtain a filtered
     * list of currently available RequestInstances.  The request uses the RequestFilter
     * structure to select the set of planning requests of interest, using the
     * following keys: Domain of the RequestInstance; Reference to the RequestInstance;
     * Creation date and time of the RequestInstance (as a time range); Reference
     * to the RequestDefinition from which the RequestInstance was created; User
     * ID of the PlanningUser who initiated the RequestInstance; User Reference
     * supplied by the User when submitting the RequestInstance; Current status
     * of the RequestInstance; Reference to the output Plan(s) generated in response
     * to the RequestInstance. The response returns a list of RequestSummaryStatus
     * structures containing references to the identities, descriptive header
     * fields, and status of the RequestInstances that match the filter.
     * 
     * @param requestFilter The requestFilter field.
     * @return The return value of the interaction
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public RequestSummaryStatusList getRequestSummaries(RequestFilter requestFilter) throws InvalidException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(PlanningRequestServiceInfo.GETREQUESTSUMMARIES_OP, requestFilter);
            Object body0 = (Object) body.getBodyElement(0, new RequestSummaryStatusList());
            return (RequestSummaryStatusList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method getRequestSummaries.
     * 
     * @param requestFilter The requestFilter field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncGetRequestSummaries(RequestFilter requestFilter,
            PlanningRequestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(PlanningRequestServiceInfo.GETREQUESTSUMMARIES_OP, adapter, requestFilter);
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
    public void continueGetRequestSummaries(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanningRequestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanningRequestServiceInfo.GETREQUESTSUMMARIES_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The getRequestStatus operation is used to obtain the current status of
     * one or more known RequestInstances.  The operation uses the Progress interaction
     * pattern, to allow the response to be spread across multiple messages.
     * 
     * @param requestRefs The requestRefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void getRequestStatus(ObjectRefList requestRefs,
            PlanningRequestAdapter adapter) throws InvalidException, MALStandardError, MALException {
        try {
            consumer.progress(PlanningRequestServiceInfo.GETREQUESTSTATUS_OP, adapter, requestRefs);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method getRequestStatus.
     * 
     * @param requestRefs The requestRefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncGetRequestStatus(ObjectRefList requestRefs,
            PlanningRequestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncProgress(PlanningRequestServiceInfo.GETREQUESTSTATUS_OP, adapter, requestRefs);
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
    public void continueGetRequestStatus(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanningRequestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanningRequestServiceInfo.GETREQUESTSTATUS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The cancelRequest operation is used by a consumer to cancel a previously
     * submitted planning request.  The service provider acknowledges the cancellation
     * of the RequestInstance or returns an error.
     * 
     * @param requestRef The requestRef field.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws CancelFailedException The cancelRequest operation failed to cancel the referenced RequestInstance.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void cancelRequest(ObjectRef<RequestInstance> requestRef) throws InvalidException, CancelFailedException, MALStandardError, MALException {
        try {
            consumer.submit(PlanningRequestServiceInfo.CANCELREQUEST_OP, requestRef);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof CancelFailedException) {
                throw (CancelFailedException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method cancelRequest.
     * 
     * @param requestRef The requestRef field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncCancelRequest(ObjectRef<RequestInstance> requestRef,
            PlanningRequestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(PlanningRequestServiceInfo.CANCELREQUEST_OP, adapter, requestRef);
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
    public void continueCancelRequest(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanningRequestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanningRequestServiceInfo.CANCELREQUEST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The updateRequest operation may be used to modify the PlanningRequestDetails
     * associated with a previously submitted planning request.  This results
     * in the creation of a new version of the RequestInstance (with the same
     * key) by the service provider, which returns a reference to the new version
     * to the consumer.
     * 
     * @param requestRef The requestRef field.
     * @param requestDetails The requestDetails field.
     * @return The return value of the interaction
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws UnsupportedException An optional data structure used in the message is not supported by the service provider.
     * @throws UpdateFailedException The update operation (to Request, PlanStatus, Activity, Event or Resource) failed to update the referenced object.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public PlanningRequestResponse updateRequest(ObjectRef<RequestInstance> requestRef,
            PlanningRequestDetails requestDetails) throws InvalidException, UnsupportedException, UpdateFailedException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(PlanningRequestServiceInfo.UPDATEREQUEST_OP, requestRef, requestDetails);
            Object body0 = (Object) body.getBodyElement(0, new PlanningRequestResponse());
            return (PlanningRequestResponse) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof UnsupportedException) {
                throw (UnsupportedException) error;
            }
            if (error instanceof UpdateFailedException) {
                throw (UpdateFailedException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method updateRequest.
     * 
     * @param requestRef The requestRef field.
     * @param requestDetails The requestDetails field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncUpdateRequest(ObjectRef<RequestInstance> requestRef,
            PlanningRequestDetails requestDetails,
            PlanningRequestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(PlanningRequestServiceInfo.UPDATEREQUEST_OP, adapter, requestRef, requestDetails);
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
    public void continueUpdateRequest(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanningRequestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanningRequestServiceInfo.UPDATEREQUEST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Register method for the monitorRequestStatus PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorRequestStatusRegister(Subscription subscription,
            PlanningRequestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.register(PlanningRequestServiceInfo.MONITORREQUESTSTATUS_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorRequestStatusRegister.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncMonitorRequestStatusRegister(Subscription subscription,
            PlanningRequestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRegister(PlanningRequestServiceInfo.MONITORREQUESTSTATUS_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Deregister method for the monitorRequestStatus PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorRequestStatusDeregister(IdentifierList identifierList) throws MALStandardError, MALException {
        try {
            consumer.deregister(PlanningRequestServiceInfo.MONITORREQUESTSTATUS_OP, identifierList);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorRequestStatusDeregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncMonitorRequestStatusDeregister(IdentifierList identifierList,
            PlanningRequestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncDeregister(PlanningRequestServiceInfo.MONITORREQUESTSTATUS_OP, identifierList, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The getRequest operation is used to obtain the full content of one or more
     * known RequestInstances.  The operation uses the Progress interaction pattern,
     * to allow the response to be spread across multiple messages.
     * 
     * @param requestRefs The requestRefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void getRequest(ObjectRefList requestRefs,
            PlanningRequestAdapter adapter) throws InvalidException, MALStandardError, MALException {
        try {
            consumer.progress(PlanningRequestServiceInfo.GETREQUEST_OP, adapter, requestRefs);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method getRequest.
     * 
     * @param requestRefs The requestRefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncGetRequest(ObjectRefList requestRefs,
            PlanningRequestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncProgress(PlanningRequestServiceInfo.GETREQUEST_OP, adapter, requestRefs);
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
    public void continueGetRequest(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanningRequestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanningRequestServiceInfo.GETREQUEST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

}
