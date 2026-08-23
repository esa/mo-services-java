package org.ccsds.moims.mo.mps.planexecutioncontrol.provider;

/**
 * Provider Inheritance skeleton for PlanExecutionControlInheritanceSkeleton
 * service.
 */
public abstract class PlanExecutionControlInheritanceSkeleton implements org.ccsds.moims.mo.mal.provider.MALInteractionHandler, org.ccsds.moims.mo.mps.planexecutioncontrol.provider.PlanExecutionControlSkeleton, org.ccsds.moims.mo.mps.planexecutioncontrol.provider.PlanExecutionControlHandler {

    /**
     * The providerSet field.
     */
    private org.ccsds.moims.mo.mal.provider.MALProviderSet providerSet = new org.ccsds.moims.mo.mal.provider.MALProviderSet(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlHelper.PLANEXECUTIONCONTROL_SERVICE);

    /**
     * Returns the connection object for this provider.
     * 
     * @return the connection object for this provider
     * @throws java.io.IOException if the method was not implemented yet.
     */
    public org.ccsds.moims.mo.mal.helpertools.connections.ConnectionProvider getConnection() throws java.io.IOException {
        throw new java.io.IOException("This method needs to be overridden!");
    }

    @Override
    public void setSkeleton(org.ccsds.moims.mo.mps.planexecutioncontrol.provider.PlanExecutionControlSkeleton skeleton) {
        // Not used in the inheritance pattern (the skeleton is 'this');
    }

    @Override
    public void malInitialize(org.ccsds.moims.mo.mal.provider.MALProvider provider) throws org.ccsds.moims.mo.mal.MALException {
        providerSet.addProvider(provider);
    }

    @Override
    public void malFinalize(org.ccsds.moims.mo.mal.provider.MALProvider provider) throws org.ccsds.moims.mo.mal.MALException {
        providerSet.removeProvider(provider);
    }

    @Override
    public org.ccsds.moims.mo.mps.planexecutioncontrol.provider.MonitorPlanExecutionPublisher createMonitorPlanExecutionPublisher(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.Identifier networkZone,
            org.ccsds.moims.mo.mal.structures.SessionType sessionType,
            org.ccsds.moims.mo.mal.structures.Identifier sessionName,
            org.ccsds.moims.mo.mal.structures.QoSLevel qos,
            java.util.Map qosProps,
            org.ccsds.moims.mo.mal.structures.UInteger priority) throws org.ccsds.moims.mo.mal.MALException {
        return new org.ccsds.moims.mo.mps.planexecutioncontrol.provider.MonitorPlanExecutionPublisher(providerSet.createPublisherSet(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.MONITORPLANEXECUTION_OP, domain, sessionType, sessionName, qos, qosProps, null));
    }

    @Override
    public org.ccsds.moims.mo.mps.planexecutioncontrol.provider.MonitorPlanExecutionDetailPublisher createMonitorPlanExecutionDetailPublisher(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.Identifier networkZone,
            org.ccsds.moims.mo.mal.structures.SessionType sessionType,
            org.ccsds.moims.mo.mal.structures.Identifier sessionName,
            org.ccsds.moims.mo.mal.structures.QoSLevel qos,
            java.util.Map qosProps,
            org.ccsds.moims.mo.mal.structures.UInteger priority) throws org.ccsds.moims.mo.mal.MALException {
        return new org.ccsds.moims.mo.mps.planexecutioncontrol.provider.MonitorPlanExecutionDetailPublisher(providerSet.createPublisherSet(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.MONITORPLANEXECUTIONDETAIL_OP, domain, sessionType, sessionName, qos, qosProps, null));
    }

    @Override
    public org.ccsds.moims.mo.mps.planexecutioncontrol.provider.MonitorSubPlanExecutionPublisher createMonitorSubPlanExecutionPublisher(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.Identifier networkZone,
            org.ccsds.moims.mo.mal.structures.SessionType sessionType,
            org.ccsds.moims.mo.mal.structures.Identifier sessionName,
            org.ccsds.moims.mo.mal.structures.QoSLevel qos,
            java.util.Map qosProps,
            org.ccsds.moims.mo.mal.structures.UInteger priority) throws org.ccsds.moims.mo.mal.MALException {
        return new org.ccsds.moims.mo.mps.planexecutioncontrol.provider.MonitorSubPlanExecutionPublisher(providerSet.createPublisherSet(org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo.MONITORSUBPLANEXECUTION_OP, domain, sessionType, sessionName, qos, qosProps, null));
    }

    @Override
    public void handleSend(org.ccsds.moims.mo.mal.provider.MALInteraction interaction,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body) throws org.ccsds.moims.mo.mal.MALException, org.ccsds.moims.mo.mal.MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        switch (opNumber) {
          default:
            throw new org.ccsds.moims.mo.mal.MALInteractionException(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                    org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
    }

    @Override
    public void handleSubmit(org.ccsds.moims.mo.mal.provider.MALSubmit interaction,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body) throws org.ccsds.moims.mo.mal.MALException, org.ccsds.moims.mo.mal.MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        try {
        switch (opNumber) {
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._SUBMITPLAN_OP_NUMBER:
            submitPlan((org.ccsds.moims.mo.mps.structures.Plan) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.Plan()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._REVOKEPLAN_OP_NUMBER:
            revokePlan((org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          default:
            interaction.sendError(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                    org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new org.ccsds.moims.mo.mal.MALInteractionException(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                    org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
        } catch (org.ccsds.moims.mo.mal.MOErrorException error) {
          throw new org.ccsds.moims.mo.mal.MALInteractionException(error);
        }
    }

    @Override
    public void handleRequest(org.ccsds.moims.mo.mal.provider.MALRequest interaction,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body) throws org.ccsds.moims.mo.mal.MALException, org.ccsds.moims.mo.mal.MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        try {
        switch (opNumber) {
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._GETPLANSTATUS_OP_NUMBER:
            interaction.sendResponse(getPlanStatus((org.ccsds.moims.mo.mal.structures.ObjectRefList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRefList()),
                interaction));
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._ACTIVATEPLAN_OP_NUMBER:
            interaction.sendResponse(activatePlan((org.ccsds.moims.mo.mal.structures.ObjectRefList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRefList()),
                interaction));
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._DEACTIVATEPLAN_OP_NUMBER:
            interaction.sendResponse(deactivatePlan((org.ccsds.moims.mo.mal.structures.ObjectRefList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRefList()),
                (org.ccsds.moims.mo.mal.structures.Identifier) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.Identifier()),
                interaction));
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._ACTIVATESUBPLAN_OP_NUMBER:
            interaction.sendResponse(activateSubPlan((org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                interaction));
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._DEACTIVATESUBPLAN_OP_NUMBER:
            interaction.sendResponse(deactivateSubPlan((org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                (body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.Union("")) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.Union(""))).getStringValue(),
                interaction));
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._GETSUBPLANSTATUS_OP_NUMBER:
            interaction.sendResponse(getSubPlanStatus((org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                interaction));
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._SUSPENDACTIVITY_OP_NUMBER:
            interaction.sendResponse(suspendActivity((org.ccsds.moims.mo.mal.structures.ObjectRefList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRefList()),
                (org.ccsds.moims.mo.mal.structures.ObjectRefList) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.ObjectRefList()),
                (org.ccsds.moims.mo.mal.structures.StringList) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.StringList()),
                (body.getBodyElement(3, new org.ccsds.moims.mo.mal.structures.Union("")) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(3, new org.ccsds.moims.mo.mal.structures.Union(""))).getStringValue(),
                interaction));
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._RESUMEACTIVITY_OP_NUMBER:
            interaction.sendResponse(resumeActivity((org.ccsds.moims.mo.mal.structures.ObjectRefList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRefList()),
                (org.ccsds.moims.mo.mal.structures.ObjectRefList) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.ObjectRefList()),
                (org.ccsds.moims.mo.mal.structures.StringList) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.StringList()),
                interaction));
            break;
          case org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo._GETACTIVITYSTATUS_OP_NUMBER:
            interaction.sendResponse(getActivityStatus((org.ccsds.moims.mo.mal.structures.ObjectRefList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRefList()),
                (org.ccsds.moims.mo.mal.structures.ObjectRefList) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.ObjectRefList()),
                (org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                (org.ccsds.moims.mo.mal.structures.StringList) body.getBodyElement(3, new org.ccsds.moims.mo.mal.structures.StringList()),
                interaction));
            break;
          default:
            interaction.sendError(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                    org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new org.ccsds.moims.mo.mal.MALInteractionException(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                    org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
        } catch (org.ccsds.moims.mo.mal.MOErrorException error) {
          throw new org.ccsds.moims.mo.mal.MALInteractionException(error);
        }
    }

    @Override
    public void handleInvoke(org.ccsds.moims.mo.mal.provider.MALInvoke interaction,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body) throws org.ccsds.moims.mo.mal.MALException, org.ccsds.moims.mo.mal.MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        switch (opNumber) {
          default:
            interaction.sendError(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                    org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new org.ccsds.moims.mo.mal.MALInteractionException(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                    org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
    }

    @Override
    public void handleProgress(org.ccsds.moims.mo.mal.provider.MALProgress interaction,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body) throws org.ccsds.moims.mo.mal.MALException, org.ccsds.moims.mo.mal.MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        switch (opNumber) {
          default:
            interaction.sendError(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                    org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new org.ccsds.moims.mo.mal.MALInteractionException(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                    org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
    }

}
