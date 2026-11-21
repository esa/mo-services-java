package org.ccsds.moims.mo.mc.statistic.consumer;

import org.ccsds.moims.mo.com.InvalidException;
import org.ccsds.moims.mo.com.structures.InstanceBooleanPairList;
import org.ccsds.moims.mo.com.structures.ObjectKeyList;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.UnknownException;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo;
import org.ccsds.moims.mo.mc.statistic.structures.StatisticCreationRequestList;
import org.ccsds.moims.mo.mc.statistic.structures.StatisticEvaluationReportList;
import org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkDetailsList;
import org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkSummaryList;
import org.ccsds.moims.mo.mc.structures.ObjectInstancePairList;

/**
 * Consumer stub for Statistic service.
 */
public class StatisticStub {

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
    public StatisticStub(MALConsumer consumer) {
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
     * The getStatistics operation returns the latest value for a set of existing
     * statistic evaluations.
     * 
     * @param funcObjInstIds The funcObjInstIds field shall include a list of StatisticFunction object instance identifiers to match.
The funcObjInstIds field shall support the wildcard value of '0' and will match all functions of the provider.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
     * @param isGroup isGroup Argument number 1 as defined by the service operation
     * @param paramObjInstIds If the isGroup field is TRUE then the paramObjInstIds field shall contain GroupIdentity object instance identifiers, otherwise the field shall contain ParameterIdentity object instance identifiers.
If the isGroup field is TRUE, the requested Group, or the Group objects referenced by that Group, must contain ParameterIdentity objects otherwise an INVALID error shall be returned.
The ParameterIdentity objects referenced, either directly or indirectly via groups, by the paramObjInstIds field shall be the parameters to match.
The paramObjInstIds field shall support the wildcard value of '0' and matches all parameters of the provider matched to the functions given in the funcObjInstIds field.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a requested function, group or parameters is unknown then an UNKNOWN error shall be returned.
The sets of matched StatisticFunction objects and ParameterIdentity objects shall be matched to the set of existing StatisticLink objects to determine which StatisticLink objects to report on.
     * @return The return value of the interaction
     * @throws UnknownException One or more of the requested groups or parameters do not exist in the provider or statistic functions is not supported by the provider.
     * @throws InvalidException One of the supplied groups is not a group of groups or ParameterIdentity objects.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public StatisticEvaluationReportList getStatistics(LongList funcObjInstIds,
            Boolean isGroup,
            ObjectKeyList paramObjInstIds) throws UnknownException, InvalidException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(StatisticServiceInfo.GETSTATISTICS_OP, funcObjInstIds, (isGroup == null) ? null : new Union(isGroup), paramObjInstIds);
            Object body0 = (Object) body.getBodyElement(0, new StatisticEvaluationReportList());
            return (StatisticEvaluationReportList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method getStatistics.
     * 
     * @param funcObjInstIds The funcObjInstIds field shall include a list of StatisticFunction object instance identifiers to match.
The funcObjInstIds field shall support the wildcard value of '0' and will match all functions of the provider.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
     * @param isGroup isGroup Argument number 1 as defined by the service operation
     * @param paramObjInstIds If the isGroup field is TRUE then the paramObjInstIds field shall contain GroupIdentity object instance identifiers, otherwise the field shall contain ParameterIdentity object instance identifiers.
If the isGroup field is TRUE, the requested Group, or the Group objects referenced by that Group, must contain ParameterIdentity objects otherwise an INVALID error shall be returned.
The ParameterIdentity objects referenced, either directly or indirectly via groups, by the paramObjInstIds field shall be the parameters to match.
The paramObjInstIds field shall support the wildcard value of '0' and matches all parameters of the provider matched to the functions given in the funcObjInstIds field.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a requested function, group or parameters is unknown then an UNKNOWN error shall be returned.
The sets of matched StatisticFunction objects and ParameterIdentity objects shall be matched to the set of existing StatisticLink objects to determine which StatisticLink objects to report on.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncGetStatistics(LongList funcObjInstIds,
            Boolean isGroup,
            ObjectKeyList paramObjInstIds,
            StatisticAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(StatisticServiceInfo.GETSTATISTICS_OP, adapter, funcObjInstIds, (isGroup == null) ? null : new Union(isGroup), paramObjInstIds);
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
    public void continueGetStatistics(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            StatisticAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(StatisticServiceInfo.GETSTATISTICS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The operation allows a consumer to reset the statistical evaluations so
     * the evaluations restart from the current time (without changing the collection
     * interval), optionally returning the evaluation up to that point. Resetting
     * the evaluation will affect all consumers.
     * 
     * @param isStatLinkGroup If the isStatLinkGroup field is TRUE then the objInstIds field shall contain GroupIdentity object instance identifiers, otherwise the field shall contain StatisticFunction object instance identifiers.
If the isStatLinkGroup field is TRUE, the requested Group, or the Group objects referenced by that Group, must contain StatisticLink objects otherwise an INVALID error shall be returned.
The StatisticLink objects referenced, either indirectly via statistic functions or indirectly via groups, by the objInstIds field shall be the StatisticLink objects to match.
     * @param objInstIds The objInstIds field shall support the wildcard value of '0' and matches all StatisticLink objects of the provider.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a requested function or group is unknown then an UNKNOWN error shall be returned.
     * @param returnLatestEval If the returnLatestEval Boolean field is TRUE then the latest evaluation result for each of the matched links shall be returned before resetting, otherwise a NULL is returned.
If an error is raised then no resetting of evaluations shall be made as a result of this operation call.
     * @return The return value of the interaction
     * @throws InvalidException One of the supplied groups is not a group of groups or StatisticLink objects.
     * @throws UnknownException One or more of the requested groups or functions is unknown.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public StatisticEvaluationReportList resetEvaluation(Boolean isStatLinkGroup,
            LongList objInstIds,
            Boolean returnLatestEval) throws InvalidException, UnknownException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(StatisticServiceInfo.RESETEVALUATION_OP, (isStatLinkGroup == null) ? null : new Union(isStatLinkGroup), objInstIds, (returnLatestEval == null) ? null : new Union(returnLatestEval));
            Object body0 = (Object) body.getBodyElement(0, new StatisticEvaluationReportList());
            return (StatisticEvaluationReportList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method resetEvaluation.
     * 
     * @param isStatLinkGroup If the isStatLinkGroup field is TRUE then the objInstIds field shall contain GroupIdentity object instance identifiers, otherwise the field shall contain StatisticFunction object instance identifiers.
If the isStatLinkGroup field is TRUE, the requested Group, or the Group objects referenced by that Group, must contain StatisticLink objects otherwise an INVALID error shall be returned.
The StatisticLink objects referenced, either indirectly via statistic functions or indirectly via groups, by the objInstIds field shall be the StatisticLink objects to match.
     * @param objInstIds The objInstIds field shall support the wildcard value of '0' and matches all StatisticLink objects of the provider.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a requested function or group is unknown then an UNKNOWN error shall be returned.
     * @param returnLatestEval If the returnLatestEval Boolean field is TRUE then the latest evaluation result for each of the matched links shall be returned before resetting, otherwise a NULL is returned.
If an error is raised then no resetting of evaluations shall be made as a result of this operation call.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncResetEvaluation(Boolean isStatLinkGroup,
            LongList objInstIds,
            Boolean returnLatestEval,
            StatisticAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(StatisticServiceInfo.RESETEVALUATION_OP, adapter, (isStatLinkGroup == null) ? null : new Union(isStatLinkGroup), objInstIds, (returnLatestEval == null) ? null : new Union(returnLatestEval));
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
    public void continueResetEvaluation(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            StatisticAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(StatisticServiceInfo.RESETEVALUATION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Register method for the monitorStatistics PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorStatisticsRegister(Subscription subscription,
            StatisticAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.register(StatisticServiceInfo.MONITORSTATISTICS_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorStatisticsRegister.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncMonitorStatisticsRegister(Subscription subscription,
            StatisticAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRegister(StatisticServiceInfo.MONITORSTATISTICS_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Deregister method for the monitorStatistics PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorStatisticsDeregister(IdentifierList identifierList) throws MALStandardError, MALException {
        try {
            consumer.deregister(StatisticServiceInfo.MONITORSTATISTICS_OP, identifierList);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorStatisticsDeregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncMonitorStatisticsDeregister(IdentifierList identifierList,
            StatisticAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncDeregister(StatisticServiceInfo.MONITORSTATISTICS_OP, identifierList, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The enableService operation allows a consumer to globally control whether
     * evaluation of all statistics is performed or not.
     * 
     * @param enableService If enableService is set to TRUE the service shall be enabled and evaluation and reporting of statistics will be reset and commence.
If enableService is set to FALSE then all evaluation of statistics shall be suspended and no statistics will be reported.
If the enableService value matches the current enabled state of the service then no change shall be made and no error reported. Enabling an already enabled service has no effect.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void enableService(Boolean enableService) throws MALStandardError, MALException {
        try {
            consumer.submit(StatisticServiceInfo.ENABLESERVICE_OP, (enableService == null) ? null : new Union(enableService));
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method enableService.
     * 
     * @param enableService If enableService is set to TRUE the service shall be enabled and evaluation and reporting of statistics will be reset and commence.
If enableService is set to FALSE then all evaluation of statistics shall be suspended and no statistics will be reported.
If the enableService value matches the current enabled state of the service then no change shall be made and no error reported. Enabling an already enabled service has no effect.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncEnableService(Boolean enableService,
            StatisticAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(StatisticServiceInfo.ENABLESERVICE_OP, adapter, (enableService == null) ? null : new Union(enableService));
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
    public void continueEnableService(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            StatisticAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(StatisticServiceInfo.ENABLESERVICE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The getServiceStatus operation allows a consumer to determine the global
     * statistic service enabled status.
     * 
     * @return The return value of the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Boolean getServiceStatus() throws MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(StatisticServiceInfo.GETSERVICESTATUS_OP, (Object[]) null);
            Object body0 = (Object) body.getBodyElement(0, new Union(Boolean.FALSE));
            return (body0 == null) ? null : ((Union) body0).getBooleanValue();
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method getServiceStatus.
     * 
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncGetServiceStatus(StatisticAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(StatisticServiceInfo.GETSERVICESTATUS_OP, adapter, (Object[]) null);
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
    public void continueGetServiceStatus(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            StatisticAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(StatisticServiceInfo.GETSERVICESTATUS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The enableReporting operation allows a consumer to control whether reports
     * for specific statistical functions are generated or not. The operation
     * allows the consumer to select the functions directly or indirectly using
     * groups.
     * 
     * @param isGroupIds If the isGroupIds field is TRUE then the enableInstances field shall contain GroupIdentity object instance identifiers, otherwise the field contains StatisticFunction object instance identifiers.
If the isGroupIds field is TRUE, the requested Group, or the Group objects referenced by that Group, must contain StatisticLink objects otherwise an INVALID error shall be returned.
The StatisticLink objects referenced, either indirectly via StatisticFunction objects or indirectly via groups, by the enableInstances field shall be the StatisticLink objects to match.
The id of the enableInstances field shall support the wildcard value of '0' and matches all StatisticLink objects of the provider.
The service provider shall check for the wildcard value in the list of object instance identifiers in the enableInstances field first and if found no other checks of supplied object instance identifiers shall be made.
If the enableInstances field contains a value of TRUE then reports after the reporting and collection intervals for matching StatisticLink objects shall be generated, a value of FALSE requests that reports will not be generated.
No error shall be raised if the enableInstances Boolean value supplied is the same as the current reportingEnabled field for a StatisticLink object i.e. enabling an already enabled link will not result in an error.
If a requested StatisticFunction or GroupIdentity object is unknown then an UNKNOWN error shall be returned.
If an error is raised then no modifications shall be made as a result of this operation call.
The provider should create and store a new StatisticLinkDefinition object in the COM archive if the reportingEnabled field is changed.
     * @param enableInstances If the generation of reports is being enabled, then the provider shall generate a report immediately and start the report interval from that report.
     * @throws UnknownException One or more of the requested StatisticFunction or Group objects is unknown.
     * @throws InvalidException One of the supplied groups is not a group of groups or StatisticLink objects.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void enableReporting(Boolean isGroupIds,
            InstanceBooleanPairList enableInstances) throws UnknownException, InvalidException, MALStandardError, MALException {
        try {
            consumer.submit(StatisticServiceInfo.ENABLEREPORTING_OP, (isGroupIds == null) ? null : new Union(isGroupIds), enableInstances);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method enableReporting.
     * 
     * @param isGroupIds If the isGroupIds field is TRUE then the enableInstances field shall contain GroupIdentity object instance identifiers, otherwise the field contains StatisticFunction object instance identifiers.
If the isGroupIds field is TRUE, the requested Group, or the Group objects referenced by that Group, must contain StatisticLink objects otherwise an INVALID error shall be returned.
The StatisticLink objects referenced, either indirectly via StatisticFunction objects or indirectly via groups, by the enableInstances field shall be the StatisticLink objects to match.
The id of the enableInstances field shall support the wildcard value of '0' and matches all StatisticLink objects of the provider.
The service provider shall check for the wildcard value in the list of object instance identifiers in the enableInstances field first and if found no other checks of supplied object instance identifiers shall be made.
If the enableInstances field contains a value of TRUE then reports after the reporting and collection intervals for matching StatisticLink objects shall be generated, a value of FALSE requests that reports will not be generated.
No error shall be raised if the enableInstances Boolean value supplied is the same as the current reportingEnabled field for a StatisticLink object i.e. enabling an already enabled link will not result in an error.
If a requested StatisticFunction or GroupIdentity object is unknown then an UNKNOWN error shall be returned.
If an error is raised then no modifications shall be made as a result of this operation call.
The provider should create and store a new StatisticLinkDefinition object in the COM archive if the reportingEnabled field is changed.
     * @param enableInstances If the generation of reports is being enabled, then the provider shall generate a report immediately and start the report interval from that report.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncEnableReporting(Boolean isGroupIds,
            InstanceBooleanPairList enableInstances,
            StatisticAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(StatisticServiceInfo.ENABLEREPORTING_OP, adapter, (isGroupIds == null) ? null : new Union(isGroupIds), enableInstances);
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
            StatisticAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(StatisticServiceInfo.ENABLEREPORTING_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The listParameterEvaluations operation allows a consumer to request the
     * object instance identifiers of the StatisticLink objects for the evaluations
     * of the provider.
     * 
     * @param statObjInstIds The statObjInstIds field shall hold a list of StatisticFunction object instance identifiers to retrieve the StatisticLink object instance identifiers for.
The request may contain the wildcard value of '0' to return all supported statistic links.
The wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.
If a provided identifier does not include a wildcard and does not match an existing StatisticFunction object then this operation shall fail with an UNKNOWN error.
     * @return The return value of the interaction
     * @throws UnknownException One of the supplied identifiers is unknown.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public StatisticLinkSummaryList listParameterEvaluations(LongList statObjInstIds) throws UnknownException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(StatisticServiceInfo.LISTPARAMETEREVALUATIONS_OP, statObjInstIds);
            Object body0 = (Object) body.getBodyElement(0, new StatisticLinkSummaryList());
            return (StatisticLinkSummaryList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method listParameterEvaluations.
     * 
     * @param statObjInstIds The statObjInstIds field shall hold a list of StatisticFunction object instance identifiers to retrieve the StatisticLink object instance identifiers for.
The request may contain the wildcard value of '0' to return all supported statistic links.
The wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.
If a provided identifier does not include a wildcard and does not match an existing StatisticFunction object then this operation shall fail with an UNKNOWN error.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncListParameterEvaluations(LongList statObjInstIds,
            StatisticAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(StatisticServiceInfo.LISTPARAMETEREVALUATIONS_OP, adapter, statObjInstIds);
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
    public void continueListParameterEvaluations(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            StatisticAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(StatisticServiceInfo.LISTPARAMETEREVALUATIONS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The addParameterEvaluation operation allows a consumer to request that
     * one or more parameters/function combinations are added to the list of parameters
     * that are being evaluated.
     * The new StatisticLink and StatisticLinkDefinition objects are expected
     * to be stored in the COM archive by the provider of the statistic service.
     * 
     * @param newDetails The newDetails field shall hold a StatisticCreationRequest for each new parameter to be sampled.
The statFuncInstId field of the StatisticCreationRequest shall reference the object instance identifier of the StatisticFunction to be used.
If the statFuncInstId field does not match an existing StatisticFunction then an UNKNOWN error shall be raised.
The parameterId shall reference the ParameterIdentity that the function is being applied to.
If the parameterId field does not match an existing ParameterIdentity then an UNKNOWN error shall be raised.
If the type of the matched parameter is not supported by the matched statistical function, for example Mean average of a String parameter, then an INVALID error shall be returned.
The samplingInterval field shall contain the sampling duration interval for the parameter.
If the supplied samplingInterval is not supported for the requested parameter then an INVALID error shall be returned.
If an error is raised then no new StatisticLink object shall be created and stored as a result of this operation call.
If no error is to be raised then StatisticLink and StatisticLinkDefinition objects shall be created for each function/parameter link and stored in the COM archive.
The referenced parameter shall be sampled immediately and the sampling, reporting and collection intervals started.
     * @return The return value of the interaction
     * @throws InvalidException One or more of the supplied StatisticLink is either requesting an invalid sampling interval or invalid function for the request parameter.
     * @throws UnknownException One of the requested StatisticLink objects references either an unknown StatisticFunction object or an unknown ParameterIdentity object.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public ObjectInstancePairList addParameterEvaluation(StatisticCreationRequestList newDetails) throws InvalidException, UnknownException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(StatisticServiceInfo.ADDPARAMETEREVALUATION_OP, newDetails);
            Object body0 = (Object) body.getBodyElement(0, new ObjectInstancePairList());
            return (ObjectInstancePairList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method addParameterEvaluation.
     * 
     * @param newDetails The newDetails field shall hold a StatisticCreationRequest for each new parameter to be sampled.
The statFuncInstId field of the StatisticCreationRequest shall reference the object instance identifier of the StatisticFunction to be used.
If the statFuncInstId field does not match an existing StatisticFunction then an UNKNOWN error shall be raised.
The parameterId shall reference the ParameterIdentity that the function is being applied to.
If the parameterId field does not match an existing ParameterIdentity then an UNKNOWN error shall be raised.
If the type of the matched parameter is not supported by the matched statistical function, for example Mean average of a String parameter, then an INVALID error shall be returned.
The samplingInterval field shall contain the sampling duration interval for the parameter.
If the supplied samplingInterval is not supported for the requested parameter then an INVALID error shall be returned.
If an error is raised then no new StatisticLink object shall be created and stored as a result of this operation call.
If no error is to be raised then StatisticLink and StatisticLinkDefinition objects shall be created for each function/parameter link and stored in the COM archive.
The referenced parameter shall be sampled immediately and the sampling, reporting and collection intervals started.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncAddParameterEvaluation(StatisticCreationRequestList newDetails,
            StatisticAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(StatisticServiceInfo.ADDPARAMETEREVALUATION_OP, adapter, newDetails);
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
    public void continueAddParameterEvaluation(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            StatisticAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(StatisticServiceInfo.ADDPARAMETEREVALUATION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The updateParameterEvaluation operation allows a consumer to modify the
     * intervals, reporting and reset Booleans for one or more statistical evaluation
     * links.
     * The replacement StatisticLinkDefinition objects should be stored in the
     * COM archive by the service provider. The operation does not remove the
     * previous object from the COM archive, merely removes the object from the
     * provider.
     * 
     * @param linkIds The linkIds field shall contain the object instance identifiers of the StatisticLink objects to be updated.
If the linkIds list contains either NULL or '0' an INVALID error shall be raised.
The supplied object instance identifiers shall match existing link objects, an UNKNOWN error shall be raised if this is not the case.
If the supplied samplingInterval is not supported for the requested parameter then an INVALID error shall be returned.
     * @param newDetails The newDetails field shall contain the replacement StatisticLinkDetails.
The two lists shall be ordered the same.
The number of entries in the two lists shall be the same size otherwise an INVALID error shall be returned.
If an error is raised then no links shall be updated as a result of this operation call.
The provider shall create a new StatisticLinkDefinition object and store it in the COM archive.
If any of the intervals are updated then the service shall reset the relevant timer and use the new intervals immediately.
     * @return The return value of the interaction
     * @throws UnknownException One or more of the supplied StatisticLink object instance identifiers is unknown.
     * @throws InvalidException One or more of the supplied object instance identifiers list contains either a NULL or '0' or is requesting an invalid sampling interval for the request parameter or the two supplied lists are not the same length.
If the two lists are not the same length then the extra information field shall contain the first index of the element in the largest list which does not have corresponding element in the other list.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public LongList updateParameterEvaluation(LongList linkIds,
            StatisticLinkDetailsList newDetails) throws UnknownException, InvalidException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(StatisticServiceInfo.UPDATEPARAMETEREVALUATION_OP, linkIds, newDetails);
            Object body0 = (Object) body.getBodyElement(0, new LongList());
            return (LongList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method updateParameterEvaluation.
     * 
     * @param linkIds The linkIds field shall contain the object instance identifiers of the StatisticLink objects to be updated.
If the linkIds list contains either NULL or '0' an INVALID error shall be raised.
The supplied object instance identifiers shall match existing link objects, an UNKNOWN error shall be raised if this is not the case.
If the supplied samplingInterval is not supported for the requested parameter then an INVALID error shall be returned.
     * @param newDetails The newDetails field shall contain the replacement StatisticLinkDetails.
The two lists shall be ordered the same.
The number of entries in the two lists shall be the same size otherwise an INVALID error shall be returned.
If an error is raised then no links shall be updated as a result of this operation call.
The provider shall create a new StatisticLinkDefinition object and store it in the COM archive.
If any of the intervals are updated then the service shall reset the relevant timer and use the new intervals immediately.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncUpdateParameterEvaluation(LongList linkIds,
            StatisticLinkDetailsList newDetails,
            StatisticAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(StatisticServiceInfo.UPDATEPARAMETEREVALUATION_OP, adapter, linkIds, newDetails);
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
    public void continueUpdateParameterEvaluation(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            StatisticAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(StatisticServiceInfo.UPDATEPARAMETEREVALUATION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The removeParameterEvaluation operation allows a consumer to remove one
     * or more parameters from the list of parameters being sampled by the statistic
     * provider.
     * The operation does not remove the StatisticLink or StatisticLinkDefinition
     * objects from the COM archive, merely removes them from the provider. This
     * permits existing evaluation results to continue to reference the correct
     * StatisticLink and StatisticLinkDefinition objects in the COM archive.
     * 
     * @param objInstIds The objInstIds field holds the object instance identifiers of the StatisticLink objects to be removed from the provider.
The list may contain the wildcard value of '0'.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a provided StatisticLink object instance identifier does not include a wildcard and does not match an existing StatisticLink object then this operation shall fail with an UNKNOWN error.
Matched StatisticLink objects shall not be removed from the COM archive only the list of evaluated StatisticLink objects in the provider.
If an error is raised then no StatisticLink objects shall be removed as a result of this operation call.
If the operation succeeds then the provider shall not evaluate those parameter/function definition combinations for the deleted StatisticLink objects anymore.
     * @throws UnknownException One or more of the supplied StatisticLink object instance identifiers is unknown.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void removeParameterEvaluation(LongList objInstIds) throws UnknownException, MALStandardError, MALException {
        try {
            consumer.submit(StatisticServiceInfo.REMOVEPARAMETEREVALUATION_OP, objInstIds);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method removeParameterEvaluation.
     * 
     * @param objInstIds The objInstIds field holds the object instance identifiers of the StatisticLink objects to be removed from the provider.
The list may contain the wildcard value of '0'.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a provided StatisticLink object instance identifier does not include a wildcard and does not match an existing StatisticLink object then this operation shall fail with an UNKNOWN error.
Matched StatisticLink objects shall not be removed from the COM archive only the list of evaluated StatisticLink objects in the provider.
If an error is raised then no StatisticLink objects shall be removed as a result of this operation call.
If the operation succeeds then the provider shall not evaluate those parameter/function definition combinations for the deleted StatisticLink objects anymore.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncRemoveParameterEvaluation(LongList objInstIds,
            StatisticAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(StatisticServiceInfo.REMOVEPARAMETEREVALUATION_OP, adapter, objInstIds);
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
    public void continueRemoveParameterEvaluation(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            StatisticAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(StatisticServiceInfo.REMOVEPARAMETEREVALUATION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

}
