package org.ccsds.moims.mo.mc.aggregation.consumer;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALNotifyBody;
import org.ccsds.moims.mo.mc.MCHelper;
import org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo;
import org.ccsds.moims.mo.mc.structures.AggregationDefinitionList;
import org.ccsds.moims.mo.mc.structures.AggregationValueList;
import org.ccsds.moims.mo.mc.structures.ParameterValueDataList;
import org.ccsds.moims.mo.mc.structures.ReportConfigurationList;

/**
 * Consumer adapter for Aggregation service.
 */
public abstract class AggregationAdapter extends MALInteractionAdapter {

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation monitorValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorValueRegisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is received
     * from a broker for the operation monitorValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorValueRegisterErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation monitorValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorValueDeregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update is received from a broker for the
     * operation monitorValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param subscriptionId The subscriptionId of the subscription.
     * @param updateHeader The Update header.
     * @param keys The typed Subscription Key accessors for this update
     * @param timestamp The timestamp field.
     * @param values The values field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorValueNotifyReceived(MALMessageHeader msgHeader,
            Identifier subscriptionId,
            UpdateHeader updateHeader,
            MonitorValueSubscriptionKeys keys,
            Time timestamp,
            ParameterValueDataList values,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation monitorValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorValueNotifyErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param aggregationValues The aggregationValues field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getValueResponseReceived(MALMessageHeader msgHeader,
            AggregationValueList aggregationValues,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getValue.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getValueErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getReportingConfiguration.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param reportConfigs The reportConfigs field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getReportingConfigurationResponseReceived(MALMessageHeader msgHeader,
            ReportConfigurationList reportConfigs,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getReportingConfiguration.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getReportingConfigurationErrorReceived(MALMessageHeader msgHeader,
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
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation disableReporting.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void disableReportingAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation disableReporting.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void disableReportingErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation setReportingPeriod.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void setReportingPeriodAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation setReportingPeriod.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void setReportingPeriodErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation listDefinition.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param definitions The definitions field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listDefinitionResponseReceived(MALMessageHeader msgHeader,
            AggregationDefinitionList definitions,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation listDefinition.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listDefinitionErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation addAggregation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void addAggregationAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation addAggregation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void addAggregationErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation removeAggregation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void removeAggregationAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation removeAggregation.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void removeAggregationErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case AggregationServiceInfo._ENABLEREPORTING_OP_NUMBER:
            enableReportingAckReceived(msgHeader, qosProperties);
            break;
          case AggregationServiceInfo._DISABLEREPORTING_OP_NUMBER:
            disableReportingAckReceived(msgHeader, qosProperties);
            break;
          case AggregationServiceInfo._SETREPORTINGPERIOD_OP_NUMBER:
            setReportingPeriodAckReceived(msgHeader, qosProperties);
            break;
          case AggregationServiceInfo._ADDAGGREGATION_OP_NUMBER:
            addAggregationAckReceived(msgHeader, qosProperties);
            break;
          case AggregationServiceInfo._REMOVEAGGREGATION_OP_NUMBER:
            removeAggregationAckReceived(msgHeader, qosProperties);
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
          case AggregationServiceInfo._ENABLEREPORTING_OP_NUMBER:
            enableReportingErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case AggregationServiceInfo._DISABLEREPORTING_OP_NUMBER:
            disableReportingErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case AggregationServiceInfo._SETREPORTINGPERIOD_OP_NUMBER:
            setReportingPeriodErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case AggregationServiceInfo._ADDAGGREGATION_OP_NUMBER:
            addAggregationErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case AggregationServiceInfo._REMOVEAGGREGATION_OP_NUMBER:
            removeAggregationErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case AggregationServiceInfo._GETVALUE_OP_NUMBER:
            getValueResponseReceived(msgHeader,
                (AggregationValueList) body.getBodyElement(0, new AggregationValueList()), qosProperties);
            break;
          case AggregationServiceInfo._GETREPORTINGCONFIGURATION_OP_NUMBER:
            getReportingConfigurationResponseReceived(msgHeader,
                (ReportConfigurationList) body.getBodyElement(0, new ReportConfigurationList()), qosProperties);
            break;
          case AggregationServiceInfo._LISTDEFINITION_OP_NUMBER:
            listDefinitionResponseReceived(msgHeader,
                (AggregationDefinitionList) body.getBodyElement(0, new AggregationDefinitionList()), qosProperties);
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
          case AggregationServiceInfo._GETVALUE_OP_NUMBER:
            getValueErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case AggregationServiceInfo._GETREPORTINGCONFIGURATION_OP_NUMBER:
            getReportingConfigurationErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case AggregationServiceInfo._LISTDEFINITION_OP_NUMBER:
            listDefinitionErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void registerAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case AggregationServiceInfo._MONITORVALUE_OP_NUMBER:
            monitorValueRegisterAckReceived(msgHeader, qosProperties);
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
          case AggregationServiceInfo._MONITORVALUE_OP_NUMBER:
            monitorValueRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
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
        if ((MCHelper.MC_AREA_NUMBER.equals(msgHeader.getServiceArea())) && (AggregationServiceInfo.AGGREGATION_SERVICE_NUMBER.equals(msgHeader.getService()))) {
          switch (msgHeader.getOperation().getValue()) {
            case AggregationServiceInfo._MONITORVALUE_OP_NUMBER:
              monitorValueNotifyReceived(msgHeader,
                (Identifier) body.getBodyElement(0, new Identifier()),
                (UpdateHeader) body.getBodyElement(1, new UpdateHeader()),
                new MonitorValueSubscriptionKeys((UpdateHeader) body.getBodyElement(1, new UpdateHeader()), selectedKeys),
                (Time) body.getBodyElement(2, new Time()),
                (ParameterValueDataList) body.getBodyElement(3, new ParameterValueDataList()), qosProperties);
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
          case AggregationServiceInfo._MONITORVALUE_OP_NUMBER:
            monitorValueNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void deregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case AggregationServiceInfo._MONITORVALUE_OP_NUMBER:
            monitorValueDeregisterAckReceived(msgHeader, qosProperties);
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
