package org.ccsds.moims.mo.mps.plandistribution.consumer;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALNotifyBody;
import org.ccsds.moims.mo.mps.MPSHelper;
import org.ccsds.moims.mo.mps.plandistribution.PlanDistributionServiceInfo;
import org.ccsds.moims.mo.mps.structures.PartialPlan;
import org.ccsds.moims.mo.mps.structures.Plan;
import org.ccsds.moims.mo.mps.structures.PlanSummaryStatusList;
import org.ccsds.moims.mo.mps.structures.PlanUpdate;
import org.ccsds.moims.mo.mps.structures.PlanUpdateList;

/**
 * Consumer adapter for PlanDistribution service.
 */
public abstract class PlanDistributionAdapter extends MALInteractionAdapter {

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getPlanSummaries.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param planSummaries The planSummaries field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getPlanSummariesResponseReceived(MALMessageHeader msgHeader,
            PlanSummaryStatusList planSummaries,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getPlanSummaries.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getPlanSummariesErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement is received from a provider
     * for the operation getPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getPlanAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update is received from a provider for
     * the operation getPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param retrievedPlan The retrievedPlan field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getPlanUpdateReceived(MALMessageHeader msgHeader,
            Plan retrievedPlan,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response is received from a provider
     * for the operation getPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getPlanResponseReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement error is received from
     * a provider for the operation getPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getPlanAckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update error is received from a provider
     * for the operation getPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getPlanUpdateErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response error is received from a provider
     * for the operation getPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getPlanResponseErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getPlanStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param responsePlans The responsePlans field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getPlanStatusResponseReceived(MALMessageHeader msgHeader,
            PlanUpdateList responsePlans,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getPlanStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getPlanStatusErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation monitorPlanStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanStatusRegisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is received
     * from a broker for the operation monitorPlanStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanStatusRegisterErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation monitorPlanStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanStatusDeregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update is received from a broker for the
     * operation monitorPlanStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param subscriptionId The subscriptionId of the subscription.
     * @param updateHeader The Update header.
     * @param keys The typed Subscription Key accessors for this update
     * @param planUpdate The planUpdate field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanStatusNotifyReceived(MALMessageHeader msgHeader,
            Identifier subscriptionId,
            UpdateHeader updateHeader,
            MonitorPlanStatusSubscriptionKeys keys,
            PlanUpdate planUpdate,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation monitorPlanStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanStatusNotifyErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation monitorPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanRegisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is received
     * from a broker for the operation monitorPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanRegisterErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation monitorPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanDeregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update is received from a broker for the
     * operation monitorPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param subscriptionId The subscriptionId of the subscription.
     * @param updateHeader The Update header.
     * @param keys The typed Subscription Key accessors for this update
     * @param plan The plan field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanNotifyReceived(MALMessageHeader msgHeader,
            Identifier subscriptionId,
            UpdateHeader updateHeader,
            MonitorPlanSubscriptionKeys keys,
            Plan plan,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation monitorPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanNotifyErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement is received from a provider
     * for the operation queryPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void queryPlanAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update is received from a provider for
     * the operation queryPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param queriedPlan The queriedPlan field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void queryPlanUpdateReceived(MALMessageHeader msgHeader,
            Plan queriedPlan,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response is received from a provider
     * for the operation queryPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void queryPlanResponseReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement error is received from
     * a provider for the operation queryPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void queryPlanAckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update error is received from a provider
     * for the operation queryPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void queryPlanUpdateErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response error is received from a provider
     * for the operation queryPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void queryPlanResponseErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getPartialPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param partialPlan The partialPlan field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getPartialPlanResponseReceived(MALMessageHeader msgHeader,
            PartialPlan partialPlan,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getPartialPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getPartialPlanErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void requestResponseReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanDistributionServiceInfo._GETPLANSUMMARIES_OP_NUMBER:
            getPlanSummariesResponseReceived(msgHeader,
                (PlanSummaryStatusList) body.getBodyElement(0, new PlanSummaryStatusList()), qosProperties);
            break;
          case PlanDistributionServiceInfo._GETPLANSTATUS_OP_NUMBER:
            getPlanStatusResponseReceived(msgHeader,
                (PlanUpdateList) body.getBodyElement(0, new PlanUpdateList()), qosProperties);
            break;
          case PlanDistributionServiceInfo._GETPARTIALPLAN_OP_NUMBER:
            getPartialPlanResponseReceived(msgHeader,
                (PartialPlan) body.getBodyElement(0, new PartialPlan()), qosProperties);
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
          case PlanDistributionServiceInfo._GETPLANSUMMARIES_OP_NUMBER:
            getPlanSummariesErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanDistributionServiceInfo._GETPLANSTATUS_OP_NUMBER:
            getPlanStatusErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanDistributionServiceInfo._GETPARTIALPLAN_OP_NUMBER:
            getPartialPlanErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressAckReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanDistributionServiceInfo._GETPLAN_OP_NUMBER:
            getPlanAckReceived(msgHeader, qosProperties);
            break;
          case PlanDistributionServiceInfo._QUERYPLAN_OP_NUMBER:
            queryPlanAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressAckErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanDistributionServiceInfo._GETPLAN_OP_NUMBER:
            getPlanAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanDistributionServiceInfo._QUERYPLAN_OP_NUMBER:
            queryPlanAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressUpdateReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanDistributionServiceInfo._GETPLAN_OP_NUMBER:
            getPlanUpdateReceived(msgHeader,
                (Plan) body.getBodyElement(0, new Plan()), qosProperties);
            break;
          case PlanDistributionServiceInfo._QUERYPLAN_OP_NUMBER:
            queryPlanUpdateReceived(msgHeader,
                (Plan) body.getBodyElement(0, new Plan()), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressUpdateErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanDistributionServiceInfo._GETPLAN_OP_NUMBER:
            getPlanUpdateErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanDistributionServiceInfo._QUERYPLAN_OP_NUMBER:
            queryPlanUpdateErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressResponseReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanDistributionServiceInfo._GETPLAN_OP_NUMBER:
            getPlanResponseReceived(msgHeader, qosProperties);
            break;
          case PlanDistributionServiceInfo._QUERYPLAN_OP_NUMBER:
            queryPlanResponseReceived(msgHeader, qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressResponseErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanDistributionServiceInfo._GETPLAN_OP_NUMBER:
            getPlanResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanDistributionServiceInfo._QUERYPLAN_OP_NUMBER:
            queryPlanResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void registerAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanDistributionServiceInfo._MONITORPLANSTATUS_OP_NUMBER:
            monitorPlanStatusRegisterAckReceived(msgHeader, qosProperties);
            break;
          case PlanDistributionServiceInfo._MONITORPLAN_OP_NUMBER:
            monitorPlanRegisterAckReceived(msgHeader, qosProperties);
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
          case PlanDistributionServiceInfo._MONITORPLANSTATUS_OP_NUMBER:
            monitorPlanStatusRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanDistributionServiceInfo._MONITORPLAN_OP_NUMBER:
            monitorPlanRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
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
        if ((MPSHelper.MPS_AREA_NUMBER.equals(msgHeader.getServiceArea())) && (PlanDistributionServiceInfo.PLANDISTRIBUTION_SERVICE_NUMBER.equals(msgHeader.getService()))) {
          switch (msgHeader.getOperation().getValue()) {
            case PlanDistributionServiceInfo._MONITORPLANSTATUS_OP_NUMBER:
              monitorPlanStatusNotifyReceived(msgHeader,
                (Identifier) body.getBodyElement(0, new Identifier()),
                (UpdateHeader) body.getBodyElement(1, new UpdateHeader()),
                new MonitorPlanStatusSubscriptionKeys((UpdateHeader) body.getBodyElement(1, new UpdateHeader()), selectedKeys),
                (PlanUpdate) body.getBodyElement(2, new PlanUpdate()), qosProperties);
              break;
            case PlanDistributionServiceInfo._MONITORPLAN_OP_NUMBER:
              monitorPlanNotifyReceived(msgHeader,
                (Identifier) body.getBodyElement(0, new Identifier()),
                (UpdateHeader) body.getBodyElement(1, new UpdateHeader()),
                new MonitorPlanSubscriptionKeys((UpdateHeader) body.getBodyElement(1, new UpdateHeader()), selectedKeys),
                (Plan) body.getBodyElement(2, new Plan()), qosProperties);
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
          case PlanDistributionServiceInfo._MONITORPLANSTATUS_OP_NUMBER:
            monitorPlanStatusNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanDistributionServiceInfo._MONITORPLAN_OP_NUMBER:
            monitorPlanNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void deregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanDistributionServiceInfo._MONITORPLANSTATUS_OP_NUMBER:
            monitorPlanStatusDeregisterAckReceived(msgHeader, qosProperties);
            break;
          case PlanDistributionServiceInfo._MONITORPLAN_OP_NUMBER:
            monitorPlanDeregisterAckReceived(msgHeader, qosProperties);
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
