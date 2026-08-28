package org.ccsds.moims.mo.mc.statistic.consumer;

/**
 * Consumer adapter for Statistic service.
 */
public abstract class StatisticAdapter extends org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter {

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
    public void getStatisticsResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mc.statistic.structures.StatisticEvaluationReportList evaluations,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getStatistics.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getStatisticsErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation resetEvaluation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param evaluations evaluations Argument number 0 as defined by the service operation
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void resetEvaluationResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mc.statistic.structures.StatisticEvaluationReportList evaluations,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation resetEvaluation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void resetEvaluationErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation monitorStatistics.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorStatisticsRegisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is received
     * from a broker for the operation monitorStatistics.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorStatisticsRegisterErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation monitorStatistics.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorStatisticsDeregisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
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
    public void monitorStatisticsNotifyReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Identifier subscriptionId,
            org.ccsds.moims.mo.mal.structures.UpdateHeader updateHeader,
            MonitorStatisticsSubscriptionKeys keys,
            Long relatedId,
            org.ccsds.moims.mo.com.structures.ObjectId sourceId,
            org.ccsds.moims.mo.mc.statistic.structures.StatisticValue statisticValue,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation monitorStatistics.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorStatisticsNotifyErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation enableService.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void enableServiceAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation enableService.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void enableServiceErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getServiceStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param serviceEnabled The operation shall return TRUE if the service is currently enabled or FALSE if the service is currently disabled.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getServiceStatusResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            Boolean serviceEnabled,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getServiceStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getServiceStatusErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation enableReporting.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void enableReportingAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation enableReporting.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void enableReportingErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation listParameterEvaluations.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param statLinkObjInstIds The response shall contain a list of StatisticLinkSummary that contain the object instance identifiers of the StatisticLink, StatisticFunction, and ParameterIdentity for the matched StatisticFunction objects.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listParameterEvaluationsResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkSummaryList statLinkObjInstIds,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation listParameterEvaluations.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listParameterEvaluationsErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
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
    public void addParameterEvaluationResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mc.structures.ObjectInstancePairList newObjInstIds,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation addParameterEvaluation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void addParameterEvaluationErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
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
    public void updateParameterEvaluationResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.LongList newLinkDefIds,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation updateParameterEvaluation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateParameterEvaluationErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation removeParameterEvaluation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void removeParameterEvaluationAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation removeParameterEvaluation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void removeParameterEvaluationErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._ENABLESERVICE_OP_NUMBER:
            enableServiceAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._ENABLEREPORTING_OP_NUMBER:
            enableReportingAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._REMOVEPARAMETEREVALUATION_OP_NUMBER:
            removeParameterEvaluationAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void submitErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._ENABLESERVICE_OP_NUMBER:
            enableServiceErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._ENABLEREPORTING_OP_NUMBER:
            enableReportingErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._REMOVEPARAMETEREVALUATION_OP_NUMBER:
            removeParameterEvaluationErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._GETSTATISTICS_OP_NUMBER:
            getStatisticsResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mc.statistic.structures.StatisticEvaluationReportList) body.getBodyElement(0, new org.ccsds.moims.mo.mc.statistic.structures.StatisticEvaluationReportList()), qosProperties);
            break;
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._RESETEVALUATION_OP_NUMBER:
            resetEvaluationResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mc.statistic.structures.StatisticEvaluationReportList) body.getBodyElement(0, new org.ccsds.moims.mo.mc.statistic.structures.StatisticEvaluationReportList()), qosProperties);
            break;
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._GETSERVICESTATUS_OP_NUMBER:
            getServiceStatusResponseReceived(msgHeader,
                (body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE))).getBooleanValue(), qosProperties);
            break;
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._LISTPARAMETEREVALUATIONS_OP_NUMBER:
            listParameterEvaluationsResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkSummaryList) body.getBodyElement(0, new org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkSummaryList()), qosProperties);
            break;
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._ADDPARAMETEREVALUATION_OP_NUMBER:
            addParameterEvaluationResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mc.structures.ObjectInstancePairList) body.getBodyElement(0, new org.ccsds.moims.mo.mc.structures.ObjectInstancePairList()), qosProperties);
            break;
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._UPDATEPARAMETEREVALUATION_OP_NUMBER:
            updateParameterEvaluationResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.LongList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.LongList()), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._GETSTATISTICS_OP_NUMBER:
            getStatisticsErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._RESETEVALUATION_OP_NUMBER:
            resetEvaluationErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._GETSERVICESTATUS_OP_NUMBER:
            getServiceStatusErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._LISTPARAMETEREVALUATIONS_OP_NUMBER:
            listParameterEvaluationsErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._ADDPARAMETEREVALUATION_OP_NUMBER:
            addParameterEvaluationErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._UPDATEPARAMETEREVALUATION_OP_NUMBER:
            updateParameterEvaluationErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void registerAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._MONITORSTATISTICS_OP_NUMBER:
            monitorStatisticsRegisterAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void registerErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._MONITORSTATISTICS_OP_NUMBER:
            monitorStatisticsRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void notifyReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALNotifyBody body,
            org.ccsds.moims.mo.mal.structures.IdentifierList selectedKeys,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        if ((org.ccsds.moims.mo.mc.MCHelper.MC_AREA_NUMBER.equals(msgHeader.getServiceArea())) && (org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo.STATISTIC_SERVICE_NUMBER.equals(msgHeader.getService()))) {
          switch (msgHeader.getOperation().getValue()) {
            case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._MONITORSTATISTICS_OP_NUMBER:
              monitorStatisticsNotifyReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Identifier) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Identifier()),
                (org.ccsds.moims.mo.mal.structures.UpdateHeader) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UpdateHeader()),
                new MonitorStatisticsSubscriptionKeys((org.ccsds.moims.mo.mal.structures.UpdateHeader) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UpdateHeader()), selectedKeys),
                (body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE))).getLongValue(),
                (org.ccsds.moims.mo.com.structures.ObjectId) body.getBodyElement(3, new org.ccsds.moims.mo.com.structures.ObjectId()),
                (org.ccsds.moims.mo.mc.statistic.structures.StatisticValue) body.getBodyElement(4, new org.ccsds.moims.mo.mc.statistic.structures.StatisticValue()), qosProperties);
              break;
            default:
              throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
          }
        }
        else {
          notifyReceivedFromOtherService(msgHeader, body, qosProperties);
        }
    }

    @Override
    public final void notifyErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._MONITORSTATISTICS_OP_NUMBER:
            monitorStatisticsNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void deregisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._MONITORSTATISTICS_OP_NUMBER:
            monitorStatisticsDeregisterAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    /**
     * Called by the MAL when a PubSub update from another service is received
     * from a broker.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param body body The body of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     * @throws org.ccsds.moims.mo.mal.MALException if an error is detected processing the message.
     */
    public void notifyReceivedFromOtherService(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALNotifyBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
    }

}
