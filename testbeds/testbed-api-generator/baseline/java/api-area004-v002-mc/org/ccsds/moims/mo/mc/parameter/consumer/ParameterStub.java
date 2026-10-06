package org.ccsds.moims.mo.mc.parameter.consumer;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.UnknownException;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mc.AmbiguousException;
import org.ccsds.moims.mo.mc.InvalidException;
import org.ccsds.moims.mo.mc.ReadOnlyException;
import org.ccsds.moims.mo.mc.parameter.ParameterServiceInfo;
import org.ccsds.moims.mo.mc.structures.ParameterValueList;
import org.ccsds.moims.mo.mc.structures.ReportConfigurationList;

/**
 * Consumer stub for Parameter service.
 */
public class ParameterStub {

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
    public ParameterStub(MALConsumer consumer) {
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
     * Register method for the monitorValue PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorValueRegister(Subscription subscription,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.register(ParameterServiceInfo.MONITORVALUE_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorValueRegister.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncMonitorValueRegister(Subscription subscription,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRegister(ParameterServiceInfo.MONITORVALUE_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Deregister method for the monitorValue PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorValueDeregister(IdentifierList identifierList) throws MALStandardError, MALException {
        try {
            consumer.deregister(ParameterServiceInfo.MONITORVALUE_OP, identifierList);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorValueDeregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncMonitorValueDeregister(IdentifierList identifierList,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncDeregister(ParameterServiceInfo.MONITORVALUE_OP, identifierList, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The getValue operation returns the latest received value for a requested
     * parameter.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @return The return value of the interaction
     * @throws UnknownException Operation specific.
     * @throws AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public ParameterValueList getValue(IdentifierList domain,
            IdentifierList keys) throws UnknownException, AmbiguousException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ParameterServiceInfo.GETVALUE_OP, domain, keys);
            Object body0 = (Object) body.getBodyElement(0, new ParameterValueList());
            return (ParameterValueList) body0;
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
     * Asynchronous version of method getValue.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncGetValue(IdentifierList domain,
            IdentifierList keys,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ParameterServiceInfo.GETVALUE_OP, adapter, domain, keys);
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
    public void continueGetValue(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ParameterServiceInfo.GETVALUE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The setValue operation allows a consumer to set the raw value for one or
     * more parameters.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param newRawValues The newRawValues field.
     * @throws UnknownException Operation specific.
     * @throws InvalidException The input data or operation format is invalid and does not meet required criteria.
     * @throws ReadOnlyException The operation attempted to modify read-only data, which cannot be changed.
     * @throws AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void setValue(IdentifierList domain,
            IdentifierList keys,
            NullableAttributeList newRawValues) throws UnknownException, InvalidException, ReadOnlyException, AmbiguousException, MALStandardError, MALException {
        try {
            consumer.submit(ParameterServiceInfo.SETVALUE_OP, domain, keys, newRawValues);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof ReadOnlyException) {
                throw (ReadOnlyException) error;
            }
            if (error instanceof AmbiguousException) {
                throw (AmbiguousException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method setValue.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param newRawValues The newRawValues field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncSetValue(IdentifierList domain,
            IdentifierList keys,
            NullableAttributeList newRawValues,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(ParameterServiceInfo.SETVALUE_OP, adapter, domain, keys, newRawValues);
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
    public void continueSetValue(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ParameterServiceInfo.SETVALUE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The getReportingConfiguration operation allows a consumer to retrieve the
     * current configuration for the generation of reports for a parameter.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @return The return value of the interaction
     * @throws UnknownException Operation specific.
     * @throws AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public ReportConfigurationList getReportingConfiguration(IdentifierList domain,
            IdentifierList keys) throws UnknownException, AmbiguousException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ParameterServiceInfo.GETREPORTINGCONFIGURATION_OP, domain, keys);
            Object body0 = (Object) body.getBodyElement(0, new ReportConfigurationList());
            return (ReportConfigurationList) body0;
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
     * Asynchronous version of method getReportingConfiguration.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncGetReportingConfiguration(IdentifierList domain,
            IdentifierList keys,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ParameterServiceInfo.GETREPORTINGCONFIGURATION_OP, adapter, domain, keys);
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
    public void continueGetReportingConfiguration(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ParameterServiceInfo.GETREPORTINGCONFIGURATION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The enableReporting operation allows a consumer to request the generation
     * of reports for specific parameters.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @throws UnknownException Operation specific.
     * @throws AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void enableReporting(IdentifierList domain,
            IdentifierList keys) throws UnknownException, AmbiguousException, MALStandardError, MALException {
        try {
            consumer.submit(ParameterServiceInfo.ENABLEREPORTING_OP, domain, keys);
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
     * Asynchronous version of method enableReporting.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncEnableReporting(IdentifierList domain,
            IdentifierList keys,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(ParameterServiceInfo.ENABLEREPORTING_OP, adapter, domain, keys);
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
    public void continueEnableReporting(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ParameterServiceInfo.ENABLEREPORTING_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The disableReporting operation allows a consumer to stop the generation
     * of reports for specific parameters.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @throws UnknownException Operation specific.
     * @throws AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void disableReporting(IdentifierList domain,
            IdentifierList keys) throws UnknownException, AmbiguousException, MALStandardError, MALException {
        try {
            consumer.submit(ParameterServiceInfo.DISABLEREPORTING_OP, domain, keys);
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
     * Asynchronous version of method disableReporting.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncDisableReporting(IdentifierList domain,
            IdentifierList keys,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(ParameterServiceInfo.DISABLEREPORTING_OP, adapter, domain, keys);
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
    public void continueDisableReporting(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ParameterServiceInfo.DISABLEREPORTING_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The setReportingPeriod operation allows a consumer to set the reporting
     * interval for specific parameters.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param reportInterval The reportInterval field.
     * @throws UnknownException Operation specific.
     * @throws InvalidException The input data or operation format is invalid and does not meet required criteria.
     * @throws AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void setReportingPeriod(IdentifierList domain,
            IdentifierList keys,
            Duration reportInterval) throws UnknownException, InvalidException, AmbiguousException, MALStandardError, MALException {
        try {
            consumer.submit(ParameterServiceInfo.SETREPORTINGPERIOD_OP, domain, keys, reportInterval);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof AmbiguousException) {
                throw (AmbiguousException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method setReportingPeriod.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param reportInterval The reportInterval field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncSetReportingPeriod(IdentifierList domain,
            IdentifierList keys,
            Duration reportInterval,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(ParameterServiceInfo.SETREPORTINGPERIOD_OP, adapter, domain, keys, reportInterval);
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
    public void continueSetReportingPeriod(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ParameterServiceInfo.SETREPORTINGPERIOD_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

}
