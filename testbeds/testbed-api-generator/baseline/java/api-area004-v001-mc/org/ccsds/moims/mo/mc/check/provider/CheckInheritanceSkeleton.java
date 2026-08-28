package org.ccsds.moims.mo.mc.check.provider;

/**
 * Provider Inheritance skeleton for CheckInheritanceSkeleton service.
 */
public abstract class CheckInheritanceSkeleton implements org.ccsds.moims.mo.mal.provider.MALInteractionHandler, org.ccsds.moims.mo.mc.check.provider.CheckSkeleton, org.ccsds.moims.mo.mc.check.provider.CheckHandler {

    /**
     * The providerSet field.
     */
    private org.ccsds.moims.mo.mal.provider.MALProviderSet providerSet = new org.ccsds.moims.mo.mal.provider.MALProviderSet(org.ccsds.moims.mo.mc.check.CheckHelper.CHECK_SERVICE);

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
    public void setSkeleton(org.ccsds.moims.mo.mc.check.provider.CheckSkeleton skeleton) {
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
          case org.ccsds.moims.mo.mc.check.CheckServiceInfo._ENABLESERVICE_OP_NUMBER:
            enableService((body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE))).getBooleanValue(),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.mc.check.CheckServiceInfo._ENABLECHECK_OP_NUMBER:
            enableCheck((body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE))).getBooleanValue(),
                (org.ccsds.moims.mo.com.structures.InstanceBooleanPairList) body.getBodyElement(1, new org.ccsds.moims.mo.com.structures.InstanceBooleanPairList()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.mc.check.CheckServiceInfo._TRIGGERCHECK_OP_NUMBER:
            triggerCheck((org.ccsds.moims.mo.mal.structures.LongList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.LongList()),
                (org.ccsds.moims.mo.mal.structures.LongList) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.LongList()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.mc.check.CheckServiceInfo._REMOVECHECK_OP_NUMBER:
            removeCheck((org.ccsds.moims.mo.mal.structures.LongList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.LongList()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.mc.check.CheckServiceInfo._REMOVEPARAMETERCHECK_OP_NUMBER:
            removeParameterCheck((org.ccsds.moims.mo.mal.structures.LongList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.LongList()),
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
          case org.ccsds.moims.mo.mc.check.CheckServiceInfo._GETSERVICESTATUS_OP_NUMBER:
            Boolean getServiceStatusRt = getServiceStatus(interaction);
            interaction.sendResponse((getServiceStatusRt == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(getServiceStatusRt));
            break;
          case org.ccsds.moims.mo.mc.check.CheckServiceInfo._LISTDEFINITION_OP_NUMBER:
            interaction.sendResponse(listDefinition((org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                interaction));
            break;
          case org.ccsds.moims.mo.mc.check.CheckServiceInfo._LISTCHECKLINKS_OP_NUMBER:
            interaction.sendResponse(listCheckLinks((org.ccsds.moims.mo.mal.structures.LongList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.LongList()),
                interaction));
            break;
          case org.ccsds.moims.mo.mc.check.CheckServiceInfo._ADDCHECK_OP_NUMBER:
            interaction.sendResponse(addCheck((org.ccsds.moims.mo.mal.structures.StringList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.StringList()),
                (org.ccsds.moims.mo.mc.check.structures.CheckDefinitionDetailsList) body.getBodyElement(1, new org.ccsds.moims.mo.mc.check.structures.CheckDefinitionDetailsList()),
                interaction));
            break;
          case org.ccsds.moims.mo.mc.check.CheckServiceInfo._UPDATEDEFINITION_OP_NUMBER:
            interaction.sendResponse(updateDefinition((org.ccsds.moims.mo.mal.structures.LongList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.LongList()),
                (org.ccsds.moims.mo.mc.check.structures.CheckDefinitionDetailsList) body.getBodyElement(1, new org.ccsds.moims.mo.mc.check.structures.CheckDefinitionDetailsList()),
                interaction));
            break;
          case org.ccsds.moims.mo.mc.check.CheckServiceInfo._ADDPARAMETERCHECK_OP_NUMBER:
            interaction.sendResponse(addParameterCheck((org.ccsds.moims.mo.mc.check.structures.CheckLinkDetailsList) body.getBodyElement(0, new org.ccsds.moims.mo.mc.check.structures.CheckLinkDetailsList()),
                (org.ccsds.moims.mo.com.structures.ObjectDetailsList) body.getBodyElement(1, new org.ccsds.moims.mo.com.structures.ObjectDetailsList()),
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
        try {
        switch (opNumber) {
          case org.ccsds.moims.mo.mc.check.CheckServiceInfo._GETCURRENTTRANSITIONLIST_OP_NUMBER:
            getCurrentTransitionList((org.ccsds.moims.mo.mc.check.structures.CheckResultFilter) body.getBodyElement(0, new org.ccsds.moims.mo.mc.check.structures.CheckResultFilter()),
                new GetCurrentTransitionListInteraction(interaction));
            break;
          case org.ccsds.moims.mo.mc.check.CheckServiceInfo._GETSUMMARYREPORT_OP_NUMBER:
            getSummaryReport((org.ccsds.moims.mo.mal.structures.LongList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.LongList()),
                new GetSummaryReportInteraction(interaction));
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

}
