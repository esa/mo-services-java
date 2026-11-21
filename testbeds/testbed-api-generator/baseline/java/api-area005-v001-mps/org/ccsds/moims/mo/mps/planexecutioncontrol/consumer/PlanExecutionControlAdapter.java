package org.ccsds.moims.mo.mps.planexecutioncontrol.consumer;

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
import org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo;
import org.ccsds.moims.mo.mps.structures.ActivitySuspensionStatusList;
import org.ccsds.moims.mo.mps.structures.ActivityUpdateList;
import org.ccsds.moims.mo.mps.structures.PlanActivationStatusList;
import org.ccsds.moims.mo.mps.structures.PlanDetailUpdate;
import org.ccsds.moims.mo.mps.structures.PlanUpdate;
import org.ccsds.moims.mo.mps.structures.PlanUpdateList;
import org.ccsds.moims.mo.mps.structures.SubPlanActivationStatusList;
import org.ccsds.moims.mo.mps.structures.SubPlanUpdate;
import org.ccsds.moims.mo.mps.structures.SubPlanUpdateList;

/**
 * Consumer adapter for PlanExecutionControl service.
 */
public abstract class PlanExecutionControlAdapter extends MALInteractionAdapter {

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation submitPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void submitPlanAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation submitPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void submitPlanErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation revokePlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void revokePlanAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation revokePlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void revokePlanErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getPlanStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param planStatus The planStatus field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getPlanStatusResponseReceived(MALMessageHeader msgHeader,
            PlanUpdateList planStatus,
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
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation activatePlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param activationStatus The activationStatus field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void activatePlanResponseReceived(MALMessageHeader msgHeader,
            PlanActivationStatusList activationStatus,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation activatePlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void activatePlanErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation deactivatePlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param activationStatus The activationStatus field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deactivatePlanResponseReceived(MALMessageHeader msgHeader,
            PlanActivationStatusList activationStatus,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation deactivatePlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deactivatePlanErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation monitorPlanExecution.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanExecutionRegisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is received
     * from a broker for the operation monitorPlanExecution.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanExecutionRegisterErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation monitorPlanExecution.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanExecutionDeregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update is received from a broker for the
     * operation monitorPlanExecution.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param subscriptionId The subscriptionId of the subscription.
     * @param updateHeader The Update header.
     * @param keys The typed Subscription Key accessors for this update
     * @param planUpdate The planUpdate field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanExecutionNotifyReceived(MALMessageHeader msgHeader,
            Identifier subscriptionId,
            UpdateHeader updateHeader,
            MonitorPlanExecutionSubscriptionKeys keys,
            PlanUpdate planUpdate,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation monitorPlanExecution.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanExecutionNotifyErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation monitorPlanExecutionDetail.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanExecutionDetailRegisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is received
     * from a broker for the operation monitorPlanExecutionDetail.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanExecutionDetailRegisterErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation monitorPlanExecutionDetail.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanExecutionDetailDeregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update is received from a broker for the
     * operation monitorPlanExecutionDetail.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param subscriptionId The subscriptionId of the subscription.
     * @param updateHeader The Update header.
     * @param keys The typed Subscription Key accessors for this update
     * @param detailUpdate The detailUpdate field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanExecutionDetailNotifyReceived(MALMessageHeader msgHeader,
            Identifier subscriptionId,
            UpdateHeader updateHeader,
            MonitorPlanExecutionDetailSubscriptionKeys keys,
            PlanDetailUpdate detailUpdate,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation monitorPlanExecutionDetail.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanExecutionDetailNotifyErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation activateSubPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param activationStatus The activationStatus field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void activateSubPlanResponseReceived(MALMessageHeader msgHeader,
            SubPlanActivationStatusList activationStatus,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation activateSubPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void activateSubPlanErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation deactivateSubPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param activationStatus The activationStatus field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deactivateSubPlanResponseReceived(MALMessageHeader msgHeader,
            SubPlanActivationStatusList activationStatus,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation deactivateSubPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deactivateSubPlanErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getSubPlanStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param subPlanStatus The subPlanStatus field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getSubPlanStatusResponseReceived(MALMessageHeader msgHeader,
            SubPlanUpdateList subPlanStatus,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getSubPlanStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getSubPlanStatusErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation monitorSubPlanExecution.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorSubPlanExecutionRegisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is received
     * from a broker for the operation monitorSubPlanExecution.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorSubPlanExecutionRegisterErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation monitorSubPlanExecution.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorSubPlanExecutionDeregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update is received from a broker for the
     * operation monitorSubPlanExecution.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param subscriptionId The subscriptionId of the subscription.
     * @param updateHeader The Update header.
     * @param keys The typed Subscription Key accessors for this update
     * @param subPlanUpdate The subPlanUpdate field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorSubPlanExecutionNotifyReceived(MALMessageHeader msgHeader,
            Identifier subscriptionId,
            UpdateHeader updateHeader,
            MonitorSubPlanExecutionSubscriptionKeys keys,
            SubPlanUpdate subPlanUpdate,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation monitorSubPlanExecution.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorSubPlanExecutionNotifyErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation suspendActivity.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param suspensionStatus The suspensionStatus field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void suspendActivityResponseReceived(MALMessageHeader msgHeader,
            ActivitySuspensionStatusList suspensionStatus,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation suspendActivity.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void suspendActivityErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation resumeActivity.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param suspensionStatus The suspensionStatus field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void resumeActivityResponseReceived(MALMessageHeader msgHeader,
            ActivitySuspensionStatusList suspensionStatus,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation resumeActivity.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void resumeActivityErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getActivityStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param activityStatus The activityStatus field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getActivityStatusResponseReceived(MALMessageHeader msgHeader,
            ActivityUpdateList activityStatus,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getActivityStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getActivityStatusErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanExecutionControlServiceInfo._SUBMITPLAN_OP_NUMBER:
            submitPlanAckReceived(msgHeader, qosProperties);
            break;
          case PlanExecutionControlServiceInfo._REVOKEPLAN_OP_NUMBER:
            revokePlanAckReceived(msgHeader, qosProperties);
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
          case PlanExecutionControlServiceInfo._SUBMITPLAN_OP_NUMBER:
            submitPlanErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanExecutionControlServiceInfo._REVOKEPLAN_OP_NUMBER:
            revokePlanErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case PlanExecutionControlServiceInfo._GETPLANSTATUS_OP_NUMBER:
            getPlanStatusResponseReceived(msgHeader,
                (PlanUpdateList) body.getBodyElement(0, new PlanUpdateList()), qosProperties);
            break;
          case PlanExecutionControlServiceInfo._ACTIVATEPLAN_OP_NUMBER:
            activatePlanResponseReceived(msgHeader,
                (PlanActivationStatusList) body.getBodyElement(0, new PlanActivationStatusList()), qosProperties);
            break;
          case PlanExecutionControlServiceInfo._DEACTIVATEPLAN_OP_NUMBER:
            deactivatePlanResponseReceived(msgHeader,
                (PlanActivationStatusList) body.getBodyElement(0, new PlanActivationStatusList()), qosProperties);
            break;
          case PlanExecutionControlServiceInfo._ACTIVATESUBPLAN_OP_NUMBER:
            activateSubPlanResponseReceived(msgHeader,
                (SubPlanActivationStatusList) body.getBodyElement(0, new SubPlanActivationStatusList()), qosProperties);
            break;
          case PlanExecutionControlServiceInfo._DEACTIVATESUBPLAN_OP_NUMBER:
            deactivateSubPlanResponseReceived(msgHeader,
                (SubPlanActivationStatusList) body.getBodyElement(0, new SubPlanActivationStatusList()), qosProperties);
            break;
          case PlanExecutionControlServiceInfo._GETSUBPLANSTATUS_OP_NUMBER:
            getSubPlanStatusResponseReceived(msgHeader,
                (SubPlanUpdateList) body.getBodyElement(0, new SubPlanUpdateList()), qosProperties);
            break;
          case PlanExecutionControlServiceInfo._SUSPENDACTIVITY_OP_NUMBER:
            suspendActivityResponseReceived(msgHeader,
                (ActivitySuspensionStatusList) body.getBodyElement(0, new ActivitySuspensionStatusList()), qosProperties);
            break;
          case PlanExecutionControlServiceInfo._RESUMEACTIVITY_OP_NUMBER:
            resumeActivityResponseReceived(msgHeader,
                (ActivitySuspensionStatusList) body.getBodyElement(0, new ActivitySuspensionStatusList()), qosProperties);
            break;
          case PlanExecutionControlServiceInfo._GETACTIVITYSTATUS_OP_NUMBER:
            getActivityStatusResponseReceived(msgHeader,
                (ActivityUpdateList) body.getBodyElement(0, new ActivityUpdateList()), qosProperties);
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
          case PlanExecutionControlServiceInfo._GETPLANSTATUS_OP_NUMBER:
            getPlanStatusErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanExecutionControlServiceInfo._ACTIVATEPLAN_OP_NUMBER:
            activatePlanErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanExecutionControlServiceInfo._DEACTIVATEPLAN_OP_NUMBER:
            deactivatePlanErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanExecutionControlServiceInfo._ACTIVATESUBPLAN_OP_NUMBER:
            activateSubPlanErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanExecutionControlServiceInfo._DEACTIVATESUBPLAN_OP_NUMBER:
            deactivateSubPlanErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanExecutionControlServiceInfo._GETSUBPLANSTATUS_OP_NUMBER:
            getSubPlanStatusErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanExecutionControlServiceInfo._SUSPENDACTIVITY_OP_NUMBER:
            suspendActivityErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanExecutionControlServiceInfo._RESUMEACTIVITY_OP_NUMBER:
            resumeActivityErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanExecutionControlServiceInfo._GETACTIVITYSTATUS_OP_NUMBER:
            getActivityStatusErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void registerAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanExecutionControlServiceInfo._MONITORPLANEXECUTION_OP_NUMBER:
            monitorPlanExecutionRegisterAckReceived(msgHeader, qosProperties);
            break;
          case PlanExecutionControlServiceInfo._MONITORPLANEXECUTIONDETAIL_OP_NUMBER:
            monitorPlanExecutionDetailRegisterAckReceived(msgHeader, qosProperties);
            break;
          case PlanExecutionControlServiceInfo._MONITORSUBPLANEXECUTION_OP_NUMBER:
            monitorSubPlanExecutionRegisterAckReceived(msgHeader, qosProperties);
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
          case PlanExecutionControlServiceInfo._MONITORPLANEXECUTION_OP_NUMBER:
            monitorPlanExecutionRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanExecutionControlServiceInfo._MONITORPLANEXECUTIONDETAIL_OP_NUMBER:
            monitorPlanExecutionDetailRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanExecutionControlServiceInfo._MONITORSUBPLANEXECUTION_OP_NUMBER:
            monitorSubPlanExecutionRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
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
        if ((MPSHelper.MPS_AREA_NUMBER.equals(msgHeader.getServiceArea())) && (PlanExecutionControlServiceInfo.PLANEXECUTIONCONTROL_SERVICE_NUMBER.equals(msgHeader.getService()))) {
          switch (msgHeader.getOperation().getValue()) {
            case PlanExecutionControlServiceInfo._MONITORPLANEXECUTION_OP_NUMBER:
              monitorPlanExecutionNotifyReceived(msgHeader,
                (Identifier) body.getBodyElement(0, new Identifier()),
                (UpdateHeader) body.getBodyElement(1, new UpdateHeader()),
                new MonitorPlanExecutionSubscriptionKeys((UpdateHeader) body.getBodyElement(1, new UpdateHeader()), selectedKeys),
                (PlanUpdate) body.getBodyElement(2, new PlanUpdate()), qosProperties);
              break;
            case PlanExecutionControlServiceInfo._MONITORPLANEXECUTIONDETAIL_OP_NUMBER:
              monitorPlanExecutionDetailNotifyReceived(msgHeader,
                (Identifier) body.getBodyElement(0, new Identifier()),
                (UpdateHeader) body.getBodyElement(1, new UpdateHeader()),
                new MonitorPlanExecutionDetailSubscriptionKeys((UpdateHeader) body.getBodyElement(1, new UpdateHeader()), selectedKeys),
                (PlanDetailUpdate) body.getBodyElement(2, null), qosProperties);
              break;
            case PlanExecutionControlServiceInfo._MONITORSUBPLANEXECUTION_OP_NUMBER:
              monitorSubPlanExecutionNotifyReceived(msgHeader,
                (Identifier) body.getBodyElement(0, new Identifier()),
                (UpdateHeader) body.getBodyElement(1, new UpdateHeader()),
                new MonitorSubPlanExecutionSubscriptionKeys((UpdateHeader) body.getBodyElement(1, new UpdateHeader()), selectedKeys),
                (SubPlanUpdate) body.getBodyElement(2, new SubPlanUpdate()), qosProperties);
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
          case PlanExecutionControlServiceInfo._MONITORPLANEXECUTION_OP_NUMBER:
            monitorPlanExecutionNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanExecutionControlServiceInfo._MONITORPLANEXECUTIONDETAIL_OP_NUMBER:
            monitorPlanExecutionDetailNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanExecutionControlServiceInfo._MONITORSUBPLANEXECUTION_OP_NUMBER:
            monitorSubPlanExecutionNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void deregisterAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanExecutionControlServiceInfo._MONITORPLANEXECUTION_OP_NUMBER:
            monitorPlanExecutionDeregisterAckReceived(msgHeader, qosProperties);
            break;
          case PlanExecutionControlServiceInfo._MONITORPLANEXECUTIONDETAIL_OP_NUMBER:
            monitorPlanExecutionDetailDeregisterAckReceived(msgHeader, qosProperties);
            break;
          case PlanExecutionControlServiceInfo._MONITORSUBPLANEXECUTION_OP_NUMBER:
            monitorSubPlanExecutionDeregisterAckReceived(msgHeader, qosProperties);
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
