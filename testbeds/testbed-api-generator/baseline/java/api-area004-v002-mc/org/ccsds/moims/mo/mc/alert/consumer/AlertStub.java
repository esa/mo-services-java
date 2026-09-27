package org.ccsds.moims.mo.mc.alert.consumer;

/**
 * Consumer stub for Alert service.
 */
public class AlertStub {

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
    public AlertStub(org.ccsds.moims.mo.mal.consumer.MALConsumer consumer) {
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
     * Register method for the monitorAlert PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorAlertRegister(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.mc.alert.consumer.AlertAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.register(org.ccsds.moims.mo.mc.alert.AlertServiceInfo.MONITORALERT_OP, subscription, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorAlertRegister.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncMonitorAlertRegister(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.mc.alert.consumer.AlertAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRegister(org.ccsds.moims.mo.mc.alert.AlertServiceInfo.MONITORALERT_OP, subscription, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Deregister method for the monitorAlert PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorAlertDeregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.deregister(org.ccsds.moims.mo.mc.alert.AlertServiceInfo.MONITORALERT_OP, identifierList);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorAlertDeregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncMonitorAlertDeregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList,
            org.ccsds.moims.mo.mc.alert.consumer.AlertAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncDeregister(org.ccsds.moims.mo.mc.alert.AlertServiceInfo.MONITORALERT_OP, identifierList, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The getAlertConfiguration operation allows a consumer to retrieve the current
     * configuration for the generation of an alert.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.UnknownException Operation specific.
     * @throws org.ccsds.moims.mo.mc.AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mc.structures.AlertConfigurationList getAlertConfiguration(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.IdentifierList keys) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mc.AmbiguousException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mc.alert.AlertServiceInfo.GETALERTCONFIGURATION_OP, domain, keys);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mc.structures.AlertConfigurationList());
            return (org.ccsds.moims.mo.mc.structures.AlertConfigurationList) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.mal.UnknownException) {
                throw (org.ccsds.moims.mo.mal.UnknownException) error;
            }
            if (error instanceof org.ccsds.moims.mo.mc.AmbiguousException) {
                throw (org.ccsds.moims.mo.mc.AmbiguousException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method getAlertConfiguration.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncGetAlertConfiguration(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.IdentifierList keys,
            org.ccsds.moims.mo.mc.alert.consumer.AlertAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.mc.alert.AlertServiceInfo.GETALERTCONFIGURATION_OP, adapter, domain, keys);
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
    public void continueGetAlertConfiguration(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mc.alert.consumer.AlertAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.mc.alert.AlertServiceInfo.GETALERTCONFIGURATION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The enableGeneration operation allows a consumer to control whether instances
     * of specific alerts are generated or not.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @throws org.ccsds.moims.mo.mal.UnknownException Operation specific.
     * @throws org.ccsds.moims.mo.mc.AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void enableGeneration(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.IdentifierList keys) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mc.AmbiguousException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.submit(org.ccsds.moims.mo.mc.alert.AlertServiceInfo.ENABLEGENERATION_OP, domain, keys);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.mal.UnknownException) {
                throw (org.ccsds.moims.mo.mal.UnknownException) error;
            }
            if (error instanceof org.ccsds.moims.mo.mc.AmbiguousException) {
                throw (org.ccsds.moims.mo.mc.AmbiguousException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method enableGeneration.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncEnableGeneration(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.IdentifierList keys,
            org.ccsds.moims.mo.mc.alert.consumer.AlertAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncSubmit(org.ccsds.moims.mo.mc.alert.AlertServiceInfo.ENABLEGENERATION_OP, adapter, domain, keys);
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
    public void continueEnableGeneration(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mc.alert.consumer.AlertAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.mc.alert.AlertServiceInfo.ENABLEGENERATION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The disableGeneration operation allows a consumer to stop the generation
     * of instances of specific alerts.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @throws org.ccsds.moims.mo.mal.UnknownException Operation specific.
     * @throws org.ccsds.moims.mo.mc.AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void disableGeneration(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.IdentifierList keys) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mc.AmbiguousException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.submit(org.ccsds.moims.mo.mc.alert.AlertServiceInfo.DISABLEGENERATION_OP, domain, keys);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.mal.UnknownException) {
                throw (org.ccsds.moims.mo.mal.UnknownException) error;
            }
            if (error instanceof org.ccsds.moims.mo.mc.AmbiguousException) {
                throw (org.ccsds.moims.mo.mc.AmbiguousException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method disableGeneration.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncDisableGeneration(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.IdentifierList keys,
            org.ccsds.moims.mo.mc.alert.consumer.AlertAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncSubmit(org.ccsds.moims.mo.mc.alert.AlertServiceInfo.DISABLEGENERATION_OP, adapter, domain, keys);
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
    public void continueDisableGeneration(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mc.alert.consumer.AlertAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.mc.alert.AlertServiceInfo.DISABLEGENERATION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

}
