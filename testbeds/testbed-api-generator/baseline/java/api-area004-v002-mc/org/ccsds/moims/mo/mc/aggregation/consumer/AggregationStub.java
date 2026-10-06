package org.ccsds.moims.mo.mc.aggregation.consumer;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.UnknownException;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mc.AmbiguousException;
import org.ccsds.moims.mo.mc.DuplicateException;
import org.ccsds.moims.mo.mc.InvalidException;
import org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo;
import org.ccsds.moims.mo.mc.structures.AggregationDefinitionList;
import org.ccsds.moims.mo.mc.structures.AggregationValueList;
import org.ccsds.moims.mo.mc.structures.ReportConfigurationList;

/**
 * Consumer stub for Aggregation service.
 */
public class AggregationStub {

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
    public AggregationStub(MALConsumer consumer) {
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
            AggregationAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.register(AggregationServiceInfo.MONITORVALUE_OP, subscription, adapter);
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
            AggregationAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRegister(AggregationServiceInfo.MONITORVALUE_OP, subscription, adapter);
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
            consumer.deregister(AggregationServiceInfo.MONITORVALUE_OP, identifierList);
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
            AggregationAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncDeregister(AggregationServiceInfo.MONITORVALUE_OP, identifierList, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The getValue operation returns the latest received value for a requested
     * aggregation.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @return The return value of the interaction
     * @throws UnknownException Operation specific.
     * @throws AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public AggregationValueList getValue(IdentifierList domain,
            IdentifierList keys) throws UnknownException, AmbiguousException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(AggregationServiceInfo.GETVALUE_OP, domain, keys);
            Object body0 = (Object) body.getBodyElement(0, new AggregationValueList());
            return (AggregationValueList) body0;
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
            AggregationAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(AggregationServiceInfo.GETVALUE_OP, adapter, domain, keys);
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
            AggregationAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(AggregationServiceInfo.GETVALUE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The getReportingConfiguration operation allows a consumer to retrieve the
     * current configuration for the generation of reports for an aggregation.
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
            MALMessageBody body = consumer.request(AggregationServiceInfo.GETREPORTINGCONFIGURATION_OP, domain, keys);
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
            AggregationAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(AggregationServiceInfo.GETREPORTINGCONFIGURATION_OP, adapter, domain, keys);
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
            AggregationAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(AggregationServiceInfo.GETREPORTINGCONFIGURATION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The enableReporting operation allows a consumer to request the generation
     * of reports for specific aggregations.
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
            consumer.submit(AggregationServiceInfo.ENABLEREPORTING_OP, domain, keys);
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
            AggregationAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(AggregationServiceInfo.ENABLEREPORTING_OP, adapter, domain, keys);
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
            AggregationAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(AggregationServiceInfo.ENABLEREPORTING_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The disableReporting operation allows a consumer to stop the generation
     * of reports for specific aggregations.
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
            consumer.submit(AggregationServiceInfo.DISABLEREPORTING_OP, domain, keys);
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
            AggregationAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(AggregationServiceInfo.DISABLEREPORTING_OP, adapter, domain, keys);
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
            AggregationAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(AggregationServiceInfo.DISABLEREPORTING_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The setReportingPeriod operation allows a consumer to set the reporting
     * interval for specific aggregations.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param reportInterval The reportInterval field.
     * @throws UnknownException Operation specific.
     * @throws AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws InvalidException The input data or operation format is invalid and does not meet required criteria.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void setReportingPeriod(IdentifierList domain,
            IdentifierList keys,
            Duration reportInterval) throws UnknownException, AmbiguousException, InvalidException, MALStandardError, MALException {
        try {
            consumer.submit(AggregationServiceInfo.SETREPORTINGPERIOD_OP, domain, keys, reportInterval);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            if (error instanceof AmbiguousException) {
                throw (AmbiguousException) error;
            }
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
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
            AggregationAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(AggregationServiceInfo.SETREPORTINGPERIOD_OP, adapter, domain, keys, reportInterval);
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
            AggregationAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(AggregationServiceInfo.SETREPORTINGPERIOD_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The listDefinition operation allows a consumer to retrieve the AggregationDefinition
     * objects for the supported aggregations of the provider.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @return The return value of the interaction
     * @throws UnknownException Operation specific.
     * @throws AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public AggregationDefinitionList listDefinition(IdentifierList domain,
            IdentifierList keys) throws UnknownException, AmbiguousException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(AggregationServiceInfo.LISTDEFINITION_OP, domain, keys);
            Object body0 = (Object) body.getBodyElement(0, new AggregationDefinitionList());
            return (AggregationDefinitionList) body0;
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
     * Asynchronous version of method listDefinition.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncListDefinition(IdentifierList domain,
            IdentifierList keys,
            AggregationAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(AggregationServiceInfo.LISTDEFINITION_OP, adapter, domain, keys);
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
    public void continueListDefinition(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            AggregationAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(AggregationServiceInfo.LISTDEFINITION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The addAggregation operation allows a consumer to define one or more aggregations
     * that do not currently exist.
     * 
     * @param newObjects The newObjects field.
     * @throws DuplicateException The entry or operation is a duplicate of an existing record, violating uniqueness.
     * @throws InvalidException The input data or operation format is invalid and does not meet required criteria.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void addAggregation(AggregationDefinitionList newObjects) throws DuplicateException, InvalidException, MALStandardError, MALException {
        try {
            consumer.submit(AggregationServiceInfo.ADDAGGREGATION_OP, newObjects);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DuplicateException) {
                throw (DuplicateException) error;
            }
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method addAggregation.
     * 
     * @param newObjects The newObjects field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncAddAggregation(AggregationDefinitionList newObjects,
            AggregationAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(AggregationServiceInfo.ADDAGGREGATION_OP, adapter, newObjects);
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
    public void continueAddAggregation(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            AggregationAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(AggregationServiceInfo.ADDAGGREGATION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The removeAggregation operation allows a consumer to remove one or more
     * aggregations from the list of aggregations supported by the aggregation
     * provider.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @throws UnknownException Operation specific.
     * @throws AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void removeAggregation(IdentifierList domain,
            IdentifierList keys) throws UnknownException, AmbiguousException, MALStandardError, MALException {
        try {
            consumer.submit(AggregationServiceInfo.REMOVEAGGREGATION_OP, domain, keys);
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
     * Asynchronous version of method removeAggregation.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncRemoveAggregation(IdentifierList domain,
            IdentifierList keys,
            AggregationAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(AggregationServiceInfo.REMOVEAGGREGATION_OP, adapter, domain, keys);
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
    public void continueRemoveAggregation(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            AggregationAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(AggregationServiceInfo.REMOVEAGGREGATION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

}
