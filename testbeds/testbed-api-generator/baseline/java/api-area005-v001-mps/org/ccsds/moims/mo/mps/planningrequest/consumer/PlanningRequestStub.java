package org.ccsds.moims.mo.mps.planningrequest.consumer;

/**
 * Consumer stub for PlanningRequest service.
 */
public class PlanningRequestStub {

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
    public PlanningRequestStub(org.ccsds.moims.mo.mal.consumer.MALConsumer consumer) {
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
     * The submitRequest operation sends a planning request to the provider, which
     * then creates a corresponding RequestInstance object and returns its identity
     * to the consumer.
     * 
     * @param requestDetails The requestDetails field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mps.UnsupportedException An optional data structure used in the message is not supported by the service provider.
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mps.structures.PlanningRequestResponse submitRequest(org.ccsds.moims.mo.mps.structures.PlanningRequestDetails requestDetails) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mps.UnsupportedException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo.SUBMITREQUEST_OP, requestDetails);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.PlanningRequestResponse());
            return (org.ccsds.moims.mo.mps.structures.PlanningRequestResponse) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.mps.InvalidException) {
                throw (org.ccsds.moims.mo.mps.InvalidException) error;
            }
            if (error instanceof org.ccsds.moims.mo.mps.UnsupportedException) {
                throw (org.ccsds.moims.mo.mps.UnsupportedException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method submitRequest.
     * 
     * @param requestDetails The requestDetails field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncSubmitRequest(org.ccsds.moims.mo.mps.structures.PlanningRequestDetails requestDetails,
            org.ccsds.moims.mo.mps.planningrequest.consumer.PlanningRequestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo.SUBMITREQUEST_OP, adapter, requestDetails);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueSubmitRequest(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planningrequest.consumer.PlanningRequestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo.SUBMITREQUEST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
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
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mps.structures.RequestSummaryStatusList getRequestSummaries(org.ccsds.moims.mo.mps.structures.RequestFilter requestFilter) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo.GETREQUESTSUMMARIES_OP, requestFilter);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.RequestSummaryStatusList());
            return (org.ccsds.moims.mo.mps.structures.RequestSummaryStatusList) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.mps.InvalidException) {
                throw (org.ccsds.moims.mo.mps.InvalidException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method getRequestSummaries.
     * 
     * @param requestFilter The requestFilter field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncGetRequestSummaries(org.ccsds.moims.mo.mps.structures.RequestFilter requestFilter,
            org.ccsds.moims.mo.mps.planningrequest.consumer.PlanningRequestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo.GETREQUESTSUMMARIES_OP, adapter, requestFilter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueGetRequestSummaries(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planningrequest.consumer.PlanningRequestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo.GETREQUESTSUMMARIES_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The getRequestStatus operation is used to obtain the current status of
     * one or more known RequestInstances.  The operation uses the Progress interaction
     * pattern, to allow the response to be spread across multiple messages.
     * 
     * @param requestRefs The requestRefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void getRequestStatus(org.ccsds.moims.mo.mal.structures.ObjectRefList requestRefs,
            org.ccsds.moims.mo.mps.planningrequest.consumer.PlanningRequestAdapter adapter) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.progress(org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo.GETREQUESTSTATUS_OP, adapter, requestRefs);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.mps.InvalidException) {
                throw (org.ccsds.moims.mo.mps.InvalidException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method getRequestStatus.
     * 
     * @param requestRefs The requestRefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncGetRequestStatus(org.ccsds.moims.mo.mal.structures.ObjectRefList requestRefs,
            org.ccsds.moims.mo.mps.planningrequest.consumer.PlanningRequestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncProgress(org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo.GETREQUESTSTATUS_OP, adapter, requestRefs);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueGetRequestStatus(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planningrequest.consumer.PlanningRequestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo.GETREQUESTSTATUS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The cancelRequest operation is used by a consumer to cancel a previously
     * submitted planning request.  The service provider acknowledges the cancellation
     * of the RequestInstance or returns an error.
     * 
     * @param requestRef The requestRef field.
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mps.CancelFailedException The cancelRequest operation failed to cancel the referenced RequestInstance.
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void cancelRequest(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.RequestInstance> requestRef) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mps.CancelFailedException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.submit(org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo.CANCELREQUEST_OP, requestRef);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.mps.InvalidException) {
                throw (org.ccsds.moims.mo.mps.InvalidException) error;
            }
            if (error instanceof org.ccsds.moims.mo.mps.CancelFailedException) {
                throw (org.ccsds.moims.mo.mps.CancelFailedException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method cancelRequest.
     * 
     * @param requestRef The requestRef field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncCancelRequest(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.RequestInstance> requestRef,
            org.ccsds.moims.mo.mps.planningrequest.consumer.PlanningRequestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncSubmit(org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo.CANCELREQUEST_OP, adapter, requestRef);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueCancelRequest(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planningrequest.consumer.PlanningRequestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo.CANCELREQUEST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
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
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mps.UnsupportedException An optional data structure used in the message is not supported by the service provider.
     * @throws org.ccsds.moims.mo.mps.UpdateFailedException The update operation (to Request, PlanStatus, Activity, Event or Resource) failed to update the referenced object.
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mps.structures.PlanningRequestResponse updateRequest(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.RequestInstance> requestRef,
            org.ccsds.moims.mo.mps.structures.PlanningRequestDetails requestDetails) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mps.UnsupportedException, org.ccsds.moims.mo.mps.UpdateFailedException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo.UPDATEREQUEST_OP, requestRef, requestDetails);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.PlanningRequestResponse());
            return (org.ccsds.moims.mo.mps.structures.PlanningRequestResponse) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.mps.InvalidException) {
                throw (org.ccsds.moims.mo.mps.InvalidException) error;
            }
            if (error instanceof org.ccsds.moims.mo.mps.UnsupportedException) {
                throw (org.ccsds.moims.mo.mps.UnsupportedException) error;
            }
            if (error instanceof org.ccsds.moims.mo.mps.UpdateFailedException) {
                throw (org.ccsds.moims.mo.mps.UpdateFailedException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method updateRequest.
     * 
     * @param requestRef The requestRef field.
     * @param requestDetails The requestDetails field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncUpdateRequest(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.RequestInstance> requestRef,
            org.ccsds.moims.mo.mps.structures.PlanningRequestDetails requestDetails,
            org.ccsds.moims.mo.mps.planningrequest.consumer.PlanningRequestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo.UPDATEREQUEST_OP, adapter, requestRef, requestDetails);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueUpdateRequest(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planningrequest.consumer.PlanningRequestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo.UPDATEREQUEST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Register method for the monitorRequestStatus PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorRequestStatusRegister(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.mps.planningrequest.consumer.PlanningRequestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.register(org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo.MONITORREQUESTSTATUS_OP, subscription, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorRequestStatusRegister.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncMonitorRequestStatusRegister(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.mps.planningrequest.consumer.PlanningRequestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRegister(org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo.MONITORREQUESTSTATUS_OP, subscription, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Deregister method for the monitorRequestStatus PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorRequestStatusDeregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.deregister(org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo.MONITORREQUESTSTATUS_OP, identifierList);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorRequestStatusDeregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncMonitorRequestStatusDeregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList,
            org.ccsds.moims.mo.mps.planningrequest.consumer.PlanningRequestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncDeregister(org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo.MONITORREQUESTSTATUS_OP, identifierList, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The getRequest operation is used to obtain the full content of one or more
     * known RequestInstances.  The operation uses the Progress interaction pattern,
     * to allow the response to be spread across multiple messages.
     * 
     * @param requestRefs The requestRefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void getRequest(org.ccsds.moims.mo.mal.structures.ObjectRefList requestRefs,
            org.ccsds.moims.mo.mps.planningrequest.consumer.PlanningRequestAdapter adapter) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.progress(org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo.GETREQUEST_OP, adapter, requestRefs);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.mps.InvalidException) {
                throw (org.ccsds.moims.mo.mps.InvalidException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method getRequest.
     * 
     * @param requestRefs The requestRefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncGetRequest(org.ccsds.moims.mo.mal.structures.ObjectRefList requestRefs,
            org.ccsds.moims.mo.mps.planningrequest.consumer.PlanningRequestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncProgress(org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo.GETREQUEST_OP, adapter, requestRefs);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueGetRequest(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planningrequest.consumer.PlanningRequestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.mps.planningrequest.PlanningRequestServiceInfo.GETREQUEST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

}
