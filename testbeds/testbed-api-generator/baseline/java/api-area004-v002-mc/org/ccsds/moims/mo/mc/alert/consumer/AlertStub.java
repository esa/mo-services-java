package org.ccsds.moims.mo.mc.alert.consumer;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.UnknownException;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mc.AmbiguousException;
import org.ccsds.moims.mo.mc.alert.AlertServiceInfo;
import org.ccsds.moims.mo.mc.structures.AlertConfigurationList;

/**
 * Consumer stub for Alert service.
 */
public class AlertStub {

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
    public AlertStub(MALConsumer consumer) {
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
     * Register method for the monitorAlert PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorAlertRegister(Subscription subscription,
            AlertAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.register(AlertServiceInfo.MONITORALERT_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorAlertRegister.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncMonitorAlertRegister(Subscription subscription,
            AlertAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRegister(AlertServiceInfo.MONITORALERT_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Deregister method for the monitorAlert PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorAlertDeregister(IdentifierList identifierList) throws MALStandardError, MALException {
        try {
            consumer.deregister(AlertServiceInfo.MONITORALERT_OP, identifierList);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorAlertDeregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncMonitorAlertDeregister(IdentifierList identifierList,
            AlertAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncDeregister(AlertServiceInfo.MONITORALERT_OP, identifierList, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The getAlertConfiguration operation allows a consumer to retrieve the current
     * configuration for the generation of an alert.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @return The return value of the interaction
     * @throws UnknownException Operation specific.
     * @throws AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public AlertConfigurationList getAlertConfiguration(IdentifierList domain,
            IdentifierList keys) throws UnknownException, AmbiguousException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(AlertServiceInfo.GETALERTCONFIGURATION_OP, domain, keys);
            Object body0 = (Object) body.getBodyElement(0, new AlertConfigurationList());
            return (AlertConfigurationList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            if (error instanceof AmbiguousException) {
                throw (AmbiguousException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method getAlertConfiguration.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncGetAlertConfiguration(IdentifierList domain,
            IdentifierList keys,
            AlertAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(AlertServiceInfo.GETALERTCONFIGURATION_OP, adapter, domain, keys);
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
    public void continueGetAlertConfiguration(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            AlertAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(AlertServiceInfo.GETALERTCONFIGURATION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The enableGeneration operation allows a consumer to control whether instances
     * of specific alerts are generated or not.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @throws UnknownException Operation specific.
     * @throws AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void enableGeneration(IdentifierList domain,
            IdentifierList keys) throws UnknownException, AmbiguousException, MALStandardError, MALException {
        try {
            consumer.submit(AlertServiceInfo.ENABLEGENERATION_OP, domain, keys);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            if (error instanceof AmbiguousException) {
                throw (AmbiguousException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method enableGeneration.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncEnableGeneration(IdentifierList domain,
            IdentifierList keys,
            AlertAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(AlertServiceInfo.ENABLEGENERATION_OP, adapter, domain, keys);
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
    public void continueEnableGeneration(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            AlertAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(AlertServiceInfo.ENABLEGENERATION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The disableGeneration operation allows a consumer to stop the generation
     * of instances of specific alerts.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @throws UnknownException Operation specific.
     * @throws AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void disableGeneration(IdentifierList domain,
            IdentifierList keys) throws UnknownException, AmbiguousException, MALStandardError, MALException {
        try {
            consumer.submit(AlertServiceInfo.DISABLEGENERATION_OP, domain, keys);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            if (error instanceof AmbiguousException) {
                throw (AmbiguousException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method disableGeneration.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncDisableGeneration(IdentifierList domain,
            IdentifierList keys,
            AlertAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(AlertServiceInfo.DISABLEGENERATION_OP, adapter, domain, keys);
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
    public void continueDisableGeneration(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            AlertAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(AlertServiceInfo.DISABLEGENERATION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

}
