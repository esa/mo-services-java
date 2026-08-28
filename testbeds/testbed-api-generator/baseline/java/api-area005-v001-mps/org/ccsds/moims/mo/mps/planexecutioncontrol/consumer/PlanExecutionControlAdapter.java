package org.ccsds.moims.mo.mps.planexecutioncontrol.consumer;

/**
 * Consumer adapter for PlanExecutionControl service.
 */
public abstract class PlanExecutionControlAdapter extends org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter {

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation submitPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void submitPlanAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation submitPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void submitPlanErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation revokePlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void revokePlanAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation revokePlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void revokePlanErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getPlanStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param planStatus The planStatus field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getPlanStatusResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mps.structures.PlanUpdateList planStatus,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getPlanStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getPlanStatusErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation activatePlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param activationStatus The activationStatus field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void activatePlanResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mps.structures.PlanActivationStatusList activationStatus,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation activatePlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void activatePlanErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation deactivatePlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param activationStatus The activationStatus field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deactivatePlanResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mps.structures.PlanActivationStatusList activationStatus,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation deactivatePlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deactivatePlanErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation monitorPlanExecution.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanExecutionRegisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is received
     * from a broker for the operation monitorPlanExecution.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanExecutionRegisterErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation monitorPlanExecution.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanExecutionDeregisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
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
    public void monitorPlanExecutionNotifyReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Identifier subscriptionId,
            org.ccsds.moims.mo.mal.structures.UpdateHeader updateHeader,
            MonitorPlanExecutionSubscriptionKeys keys,
            org.ccsds.moims.mo.mps.structures.PlanUpdate planUpdate,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation monitorPlanExecution.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanExecutionNotifyErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation monitorPlanExecutionDetail.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanExecutionDetailRegisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is received
     * from a broker for the operation monitorPlanExecutionDetail.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanExecutionDetailRegisterErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation monitorPlanExecutionDetail.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanExecutionDetailDeregisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
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
    public void monitorPlanExecutionDetailNotifyReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Identifier subscriptionId,
            org.ccsds.moims.mo.mal.structures.UpdateHeader updateHeader,
            MonitorPlanExecutionDetailSubscriptionKeys keys,
            org.ccsds.moims.mo.mps.structures.PlanDetailUpdate detailUpdate,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation monitorPlanExecutionDetail.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorPlanExecutionDetailNotifyErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation activateSubPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param activationStatus The activationStatus field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void activateSubPlanResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mps.structures.SubPlanActivationStatusList activationStatus,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation activateSubPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void activateSubPlanErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation deactivateSubPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param activationStatus The activationStatus field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deactivateSubPlanResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mps.structures.SubPlanActivationStatusList activationStatus,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation deactivateSubPlan.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deactivateSubPlanErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getSubPlanStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param subPlanStatus The subPlanStatus field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getSubPlanStatusResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mps.structures.SubPlanUpdateList subPlanStatus,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getSubPlanStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getSubPlanStatusErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation monitorSubPlanExecution.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorSubPlanExecutionRegisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is received
     * from a broker for the operation monitorSubPlanExecution.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorSubPlanExecutionRegisterErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation monitorSubPlanExecution.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorSubPlanExecutionDeregisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
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
    public void monitorSubPlanExecutionNotifyReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Identifier subscriptionId,
            org.ccsds.moims.mo.mal.structures.UpdateHeader updateHeader,
            MonitorSubPlanExecutionSubscriptionKeys keys,
            org.ccsds.moims.mo.mps.structures.SubPlanUpdate subPlanUpdate,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation monitorSubPlanExecution.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void monitorSubPlanExecutionNotifyErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation suspendActivity.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param suspensionStatus The suspensionStatus field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void suspendActivityResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mps.structures.ActivitySuspensionStatusList suspensionStatus,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation suspendActivity.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void suspendActivityErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation resumeActivity.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param suspensionStatus The suspensionStatus field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void resumeActivityResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mps.structures.ActivitySuspensionStatusList suspensionStatus,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation resumeActivity.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void resumeActivityErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getActivityStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param activityStatus The activityStatus field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getActivityStatusResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mps.structures.ActivityUpdateList activityStatus,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getActivityStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getActivityStatusErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._SUBMITPLAN_OP_NUMBER:
            submitPlanAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._REVOKEPLAN_OP_NUMBER:
            revokePlanAckReceived(msgHeader, qosProperties);
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
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._SUBMITPLAN_OP_NUMBER:
            submitPlanErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._REVOKEPLAN_OP_NUMBER:
            revokePlanErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._GETPLANSTATUS_OP_NUMBER:
            getPlanStatusResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mps.structures.PlanUpdateList) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.PlanUpdateList()), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._ACTIVATEPLAN_OP_NUMBER:
            activatePlanResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mps.structures.PlanActivationStatusList) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.PlanActivationStatusList()), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._DEACTIVATEPLAN_OP_NUMBER:
            deactivatePlanResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mps.structures.PlanActivationStatusList) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.PlanActivationStatusList()), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._ACTIVATESUBPLAN_OP_NUMBER:
            activateSubPlanResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mps.structures.SubPlanActivationStatusList) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.SubPlanActivationStatusList()), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._DEACTIVATESUBPLAN_OP_NUMBER:
            deactivateSubPlanResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mps.structures.SubPlanActivationStatusList) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.SubPlanActivationStatusList()), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._GETSUBPLANSTATUS_OP_NUMBER:
            getSubPlanStatusResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mps.structures.SubPlanUpdateList) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.SubPlanUpdateList()), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._SUSPENDACTIVITY_OP_NUMBER:
            suspendActivityResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mps.structures.ActivitySuspensionStatusList) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.ActivitySuspensionStatusList()), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._RESUMEACTIVITY_OP_NUMBER:
            resumeActivityResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mps.structures.ActivitySuspensionStatusList) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.ActivitySuspensionStatusList()), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._GETACTIVITYSTATUS_OP_NUMBER:
            getActivityStatusResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mps.structures.ActivityUpdateList) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.ActivityUpdateList()), qosProperties);
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
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._GETPLANSTATUS_OP_NUMBER:
            getPlanStatusErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._ACTIVATEPLAN_OP_NUMBER:
            activatePlanErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._DEACTIVATEPLAN_OP_NUMBER:
            deactivatePlanErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._ACTIVATESUBPLAN_OP_NUMBER:
            activateSubPlanErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._DEACTIVATESUBPLAN_OP_NUMBER:
            deactivateSubPlanErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._GETSUBPLANSTATUS_OP_NUMBER:
            getSubPlanStatusErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._SUSPENDACTIVITY_OP_NUMBER:
            suspendActivityErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._RESUMEACTIVITY_OP_NUMBER:
            resumeActivityErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._GETACTIVITYSTATUS_OP_NUMBER:
            getActivityStatusErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void registerAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._MONITORPLANEXECUTION_OP_NUMBER:
            monitorPlanExecutionRegisterAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._MONITORPLANEXECUTIONDETAIL_OP_NUMBER:
            monitorPlanExecutionDetailRegisterAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._MONITORSUBPLANEXECUTION_OP_NUMBER:
            monitorSubPlanExecutionRegisterAckReceived(msgHeader, qosProperties);
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
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._MONITORPLANEXECUTION_OP_NUMBER:
            monitorPlanExecutionRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._MONITORPLANEXECUTIONDETAIL_OP_NUMBER:
            monitorPlanExecutionDetailRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._MONITORSUBPLANEXECUTION_OP_NUMBER:
            monitorSubPlanExecutionRegisterErrorReceived(msgHeader, body.getError(), qosProperties);
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
        if ((org.ccsds.moims.mo.mps.MPSHelper.MPS_AREA_NUMBER.equals(msgHeader.getServiceArea())) && (org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.PLANEXECUTIONCONTROL_SERVICE_NUMBER.equals(msgHeader.getService()))) {
          switch (msgHeader.getOperation().getValue()) {
            case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._MONITORPLANEXECUTION_OP_NUMBER:
              monitorPlanExecutionNotifyReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Identifier) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Identifier()),
                (org.ccsds.moims.mo.mal.structures.UpdateHeader) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UpdateHeader()),
                new MonitorPlanExecutionSubscriptionKeys((org.ccsds.moims.mo.mal.structures.UpdateHeader) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UpdateHeader()), selectedKeys),
                (org.ccsds.moims.mo.mps.structures.PlanUpdate) body.getBodyElement(2, new org.ccsds.moims.mo.mps.structures.PlanUpdate()), qosProperties);
              break;
            case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._MONITORPLANEXECUTIONDETAIL_OP_NUMBER:
              monitorPlanExecutionDetailNotifyReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Identifier) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Identifier()),
                (org.ccsds.moims.mo.mal.structures.UpdateHeader) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UpdateHeader()),
                new MonitorPlanExecutionDetailSubscriptionKeys((org.ccsds.moims.mo.mal.structures.UpdateHeader) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UpdateHeader()), selectedKeys),
                (org.ccsds.moims.mo.mps.structures.PlanDetailUpdate) body.getBodyElement(2, null), qosProperties);
              break;
            case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._MONITORSUBPLANEXECUTION_OP_NUMBER:
              monitorSubPlanExecutionNotifyReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Identifier) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Identifier()),
                (org.ccsds.moims.mo.mal.structures.UpdateHeader) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UpdateHeader()),
                new MonitorSubPlanExecutionSubscriptionKeys((org.ccsds.moims.mo.mal.structures.UpdateHeader) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UpdateHeader()), selectedKeys),
                (org.ccsds.moims.mo.mps.structures.SubPlanUpdate) body.getBodyElement(2, new org.ccsds.moims.mo.mps.structures.SubPlanUpdate()), qosProperties);
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
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._MONITORPLANEXECUTION_OP_NUMBER:
            monitorPlanExecutionNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._MONITORPLANEXECUTIONDETAIL_OP_NUMBER:
            monitorPlanExecutionDetailNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._MONITORSUBPLANEXECUTION_OP_NUMBER:
            monitorSubPlanExecutionNotifyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void deregisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._MONITORPLANEXECUTION_OP_NUMBER:
            monitorPlanExecutionDeregisterAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._MONITORPLANEXECUTIONDETAIL_OP_NUMBER:
            monitorPlanExecutionDetailDeregisterAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._MONITORSUBPLANEXECUTION_OP_NUMBER:
            monitorSubPlanExecutionDeregisterAckReceived(msgHeader, qosProperties);
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
