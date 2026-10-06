package org.ccsds.moims.mo.mc.statistic.consumer;

import java.util.Map;
import org.ccsds.moims.mo.com.structures.ObjectId;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALNotifyBody;
import org.ccsds.moims.mo.mc.MCHelper;
import org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo;
import org.ccsds.moims.mo.mc.statistic.structures.StatisticEvaluationReportList;
import org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkSummaryList;
import org.ccsds.moims.mo.mc.statistic.structures.StatisticValue;
import org.ccsds.moims.mo.mc.structures.ObjectInstancePairList;

/**
 * Consumer adapter for Statistic service.
 */
public abstract class StatisticAdapter extends MALInteractionAdapter {

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getStatistics.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param evaluations The response shall contain a list of matching statistics evaluation values.
The operation shall trigger an evaluation of the statistical functions matched and return the new evaluation values.
If it is not possible to return an evaluation value for a matched evaluation (for example not enough samples available) then no entry for that evaluation shall be included.
The evaluation shall not trigger a report via the monitorStatistics operation.
Requesting an evaluation shall ignore the samplingInterval, reportingInterval, and collectionInterval fields and requests an immediate evaluation of the statistic.
Requesting an evaluation during a periodic evaluation shall not influence the periodic evaluation (e.g. it does not reset the samplingInterval, reportingInterval, and collectionInterval timers or the current periodic collection value).
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getStatisticsResponseReceived(MALMessageHeader msgHeader,
            StatisticEvaluationReportList evaluations,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getStatistics.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getStatisticsErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation resetEvaluation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param evaluations evaluations Argument number 0 as defined by the service operation
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void resetEvaluationResponseReceived(MALMessageHeader msgHeader,
            StatisticEvaluationReportList evaluations,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation resetEvaluation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void resetEvaluationErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation monitorStatistics.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorStatisticsRegisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is received
     * from a broker for the operation monitorStatistics.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorStatisticsRegisterErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation monitorStatistics.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorStatisticsDeregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update is received from a broker for the
     * operation monitorStatistics.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param subscriptionId The subscriptionId of the subscription.
     * @param updateHeader The Update header.
     * @param keys The typed Subscription Key accessors for this update
     * @param relatedId The MAL EntityKey.firstSubKey shall contain the statistic function name.
The MAL EntityKey.secondSubKey shall contain the StatisticLink object instance identifier.
The MAL EntityKey.thirdSubKey shall contain the ParameterIdentity object instance identifier.
The MAL EntityKey.fourthSubKey shall contain the new StatisticValueInstance object instance identifier.
The timestamp of the StatisticValueInstance report shall be taken from the publish message.
The related link of the update shall be held in the relatedId field.
     * @param sourceId The source link of the StatisticValueInstance shall be held in the sourceId field.
If no source link is needed then the sourceId shall be set to NULL.
     * @param statisticValue The second part of the publish message shall be the StatisticValueInstance object value.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorStatisticsNotifyReceived(MALMessageHeader msgHeader,
            Identifier subscriptionId,
            UpdateHeader updateHeader,
            MonitorStatisticsSubscriptionKeys keys,
            Long relatedId,
            ObjectId sourceId,
            StatisticValue statisticValue,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation monitorStatistics.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorStatisticsNotifyErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation enableService.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void enableServiceAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation enableService.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void enableServiceErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getServiceStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param serviceEnabled The operation shall return TRUE if the service is currently enabled or FALSE if the service is currently disabled.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getServiceStatusResponseReceived(MALMessageHeader msgHeader,
            Boolean serviceEnabled,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getServiceStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getServiceStatusErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation enableReporting.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void enableReportingAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation enableReporting.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void enableReportingErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation listParameterEvaluations.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param statLinkObjInstIds The response shall contain a list of StatisticLinkSummary that contain the object instance identifiers of the StatisticLink, StatisticFunction, and ParameterIdentity for the matched StatisticFunction objects.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listParameterEvaluationsResponseReceived(MALMessageHeader msgHeader,
            StatisticLinkSummaryList statLinkObjInstIds,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation listParameterEvaluations.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listParameterEvaluationsErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation addParameterEvaluation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param newObjInstIds The response shall contain the list of object instance identifiers for the new StatisticLink and StatisticLinkDefinition objects.
The object instance identifiers of the StatisticLink and StatisticLinkDefinition objects shall be held in the first and second fields of the ObjectInstancePair structure respectively.
The returned list shall maintain the same order as the submitted links.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void addParameterEvaluationResponseReceived(MALMessageHeader msgHeader,
            ObjectInstancePairList newObjInstIds,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation addParameterEvaluation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void addParameterEvaluationErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation updateParameterEvaluation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param newLinkDefIds The response shall contain the list of object instance identifiers for the new StatisticLinkDefinition objects.
The returned list shall maintain the same order as the submitted links.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateParameterEvaluationResponseReceived(MALMessageHeader msgHeader,
            LongList newLinkDefIds,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation updateParameterEvaluation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateParameterEvaluationErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation removeParameterEvaluation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void removeParameterEvaluationAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation removeParameterEvaluation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void removeParameterEvaluationErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case StatisticServiceInfo._ENABLESERVICE_OP_NUMBER:
            enableServiceAckReceived(msgHeader, qosProperties);
            break;
          case StatisticServiceInfo._ENABLEREPORTING_OP_NUMBER:
            enableReportingAckReceived(msgHeader, qosProperties);
            break;
          case StatisticServiceInfo._REMOVEPARAMETEREVALUATION_OP_NUMBER:
            removeParameterEvaluationAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void submitErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case StatisticServiceInfo._ENABLESERVICE_OP_NUMBER:
            enableServiceErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case StatisticServiceInfo._ENABLEREPORTING_OP_NUMBER:
            enableReportingErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case StatisticServiceInfo._REMOVEPARAMETEREVALUATION_OP_NUMBER:
            removeParameterEvaluationErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestResponseReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case StatisticServiceInfo._GETSTATISTICS_OP_NUMBER:
            getStatisticsResponseReceived(msgHeader,
                (StatisticEvaluationReportList) body.getBodyElement(0, new StatisticEvaluationReportList()), qosProperties);
            break;
          case StatisticServiceInfo._RESETEVALUATION_OP_NUMBER:
            resetEvaluationResponseReceived(msgHeader,
                (StatisticEvaluationReportList) body.getBodyElement(0, new StatisticEvaluationReportList()), qosProperties);
            break;
          case StatisticServiceInfo._GETSERVICESTATUS_OP_NUMBER:
            getServiceStatusResponseReceived(msgHeader,
                (body.getBodyElement(0, new Union(Boolean.FALSE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Boolean.FALSE))).getBooleanValue(), qosProperties);
            break;
          case StatisticServiceInfo._LISTPARAMETEREVALUATIONS_OP_NUMBER:
            listParameterEvaluationsResponseReceived(msgHeader,
                (StatisticLinkSummaryList) body.getBodyElement(0, new StatisticLinkSummaryList()), qosProperties);
            break;
          case StatisticServiceInfo._ADDPARAMETEREVALUATION_OP_NUMBER:
            addParameterEvaluationResponseReceived(msgHeader,
                (ObjectInstancePairList) body.getBodyElement(0, new ObjectInstancePairList()), qosProperties);
            break;
          case StatisticServiceInfo._UPDATEPARAMETEREVALUATION_OP_NUMBER:
            updateParameterEvaluationResponseReceived(msgHeader,
                (LongList) body.getBodyElement(0, new LongList()), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case StatisticServiceInfo._GETSTATISTICS_OP_NUMBER:
            getStatisticsErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case StatisticServiceInfo._RESETEVALUATION_OP_NUMBER:
            resetEvaluationErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case StatisticServiceInfo._GETSERVICESTATUS_OP_NUMBER:
            getServiceStatusErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case StatisticServiceInfo._LISTPARAMETEREVALUATIONS_OP_NUMBER:
            listParameterEvaluationsErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case StatisticServiceInfo._ADDPARAMETEREVALUATION_OP_NUMBER:
            addParameterEvaluationErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case StatisticServiceInfo._UPDATEPARAMETEREVALUATION_OP_NUMBER:
            updateParameterEvaluationErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void registerAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case StatisticServiceInfo._MONITORSTATISTICS_OP_NUMBER:
            monitorStatisticsRegisterAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void registerErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case StatisticServiceInfo._MONITORSTATISTICS_OP_NUMBER:
            monitorStatisticsRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void notifyReceived(MALMessageHeader msgHeader,
            MALNotifyBody body,
            IdentifierList selectedKeys,
            Map qosProperties) throws MALException {
        if ((MCHelper.MC_AREA_NUMBER.equals(msgHeader.getServiceArea())) && (StatisticServiceInfo.STATISTIC_SERVICE_NUMBER.equals(msgHeader.getService()))) {
          switch (msgHeader.getOperation().getValue()) {
            case StatisticServiceInfo._MONITORSTATISTICS_OP_NUMBER:
              monitorStatisticsNotifyReceived(msgHeader,
                (Identifier) body.getBodyElement(0, new Identifier()),
                (UpdateHeader) body.getBodyElement(1, new UpdateHeader()),
                new MonitorStatisticsSubscriptionKeys((UpdateHeader) body.getBodyElement(1, new UpdateHeader()), selectedKeys),
                (body.getBodyElement(2, new Union(Long.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(2, new Union(Long.MAX_VALUE))).getLongValue(),
                (ObjectId) body.getBodyElement(3, new ObjectId()),
                (StatisticValue) body.getBodyElement(4, new StatisticValue()), qosProperties);
              break;
            default:
              throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
          }
        }
        else {
          notifyReceivedFromOtherService(msgHeader, body, qosProperties);
        }
    }

    @Override
    public final void notifyErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case StatisticServiceInfo._MONITORSTATISTICS_OP_NUMBER:
            monitorStatisticsNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void deregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case StatisticServiceInfo._MONITORSTATISTICS_OP_NUMBER:
            monitorStatisticsDeregisterAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    /**
     * Called by the MAL when a PubSub update from another service is received
     * from a broker.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param body body The body of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     * @throws MALException if an error is detected processing the message.
     */
    public void notifyReceivedFromOtherService(MALMessageHeader msgHeader,
            MALNotifyBody body,
            Map qosProperties) throws MALException {
    }

}
