package org.ccsds.moims.mo.mc.alert.consumer;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALNotifyBody;
import org.ccsds.moims.mo.mc.MCHelper;
import org.ccsds.moims.mo.mc.alert.AlertServiceInfo;
import org.ccsds.moims.mo.mc.structures.AlertConfigurationList;

/**
 * Consumer adapter for Alert service.
 */
public abstract class AlertAdapter extends MALInteractionAdapter {

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation monitorAlert.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorAlertRegisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is received
     * from a broker for the operation monitorAlert.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorAlertRegisterErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation monitorAlert.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorAlertDeregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update is received from a broker for the
     * operation monitorAlert.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param subscriptionId The subscriptionId of the subscription.
     * @param updateHeader The Update header.
     * @param keys The typed Subscription Key accessors for this update
     * @param timestamp The timestamp field.
     * @param arguments The arguments field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorAlertNotifyReceived(MALMessageHeader msgHeader,
            Identifier subscriptionId,
            UpdateHeader updateHeader,
            MonitorAlertSubscriptionKeys keys,
            Time timestamp,
            NullableAttributeList arguments,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation monitorAlert.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorAlertNotifyErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getAlertConfiguration.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param alertConfigs The alertConfigs field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getAlertConfigurationResponseReceived(MALMessageHeader msgHeader,
            AlertConfigurationList alertConfigs,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getAlertConfiguration.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getAlertConfigurationErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation enableGeneration.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void enableGenerationAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation enableGeneration.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void enableGenerationErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation disableGeneration.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void disableGenerationAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation disableGeneration.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void disableGenerationErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case AlertServiceInfo._ENABLEGENERATION_OP_NUMBER:
            enableGenerationAckReceived(msgHeader, qosProperties);
            break;
          case AlertServiceInfo._DISABLEGENERATION_OP_NUMBER:
            disableGenerationAckReceived(msgHeader, qosProperties);
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
          case AlertServiceInfo._ENABLEGENERATION_OP_NUMBER:
            enableGenerationErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case AlertServiceInfo._DISABLEGENERATION_OP_NUMBER:
            disableGenerationErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case AlertServiceInfo._GETALERTCONFIGURATION_OP_NUMBER:
            getAlertConfigurationResponseReceived(msgHeader,
                (AlertConfigurationList) body.getBodyElement(0, new AlertConfigurationList()), qosProperties);
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
          case AlertServiceInfo._GETALERTCONFIGURATION_OP_NUMBER:
            getAlertConfigurationErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void registerAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case AlertServiceInfo._MONITORALERT_OP_NUMBER:
            monitorAlertRegisterAckReceived(msgHeader, qosProperties);
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
          case AlertServiceInfo._MONITORALERT_OP_NUMBER:
            monitorAlertRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
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
        if ((MCHelper.MC_AREA_NUMBER.equals(msgHeader.getServiceArea())) && (AlertServiceInfo.ALERT_SERVICE_NUMBER.equals(msgHeader.getService()))) {
          switch (msgHeader.getOperation().getValue()) {
            case AlertServiceInfo._MONITORALERT_OP_NUMBER:
              monitorAlertNotifyReceived(msgHeader,
                (Identifier) body.getBodyElement(0, new Identifier()),
                (UpdateHeader) body.getBodyElement(1, new UpdateHeader()),
                new MonitorAlertSubscriptionKeys((UpdateHeader) body.getBodyElement(1, new UpdateHeader()), selectedKeys),
                (Time) body.getBodyElement(2, new Time()),
                (NullableAttributeList) body.getBodyElement(3, new NullableAttributeList()), qosProperties);
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
          case AlertServiceInfo._MONITORALERT_OP_NUMBER:
            monitorAlertNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void deregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case AlertServiceInfo._MONITORALERT_OP_NUMBER:
            monitorAlertDeregisterAckReceived(msgHeader, qosProperties);
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
