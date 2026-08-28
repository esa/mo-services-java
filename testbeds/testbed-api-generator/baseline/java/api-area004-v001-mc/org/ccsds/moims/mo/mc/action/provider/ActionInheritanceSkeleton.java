package org.ccsds.moims.mo.mc.action.provider;

/**
 * Provider Inheritance skeleton for ActionInheritanceSkeleton service.
 */
public abstract class ActionInheritanceSkeleton implements org.ccsds.moims.mo.mal.provider.MALInteractionHandler, org.ccsds.moims.mo.mc.action.provider.ActionSkeleton, org.ccsds.moims.mo.mc.action.provider.ActionHandler {

    /**
     * The providerSet field.
     */
    private org.ccsds.moims.mo.mal.provider.MALProviderSet providerSet = new org.ccsds.moims.mo.mal.provider.MALProviderSet(org.ccsds.moims.mo.mc.action.ActionHelper.ACTION_SERVICE);

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
    public void setSkeleton(org.ccsds.moims.mo.mc.action.provider.ActionSkeleton skeleton) {
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
          case org.ccsds.moims.mo.mc.action.ActionServiceInfo._SUBMITACTION_OP_NUMBER:
            submitAction((body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE))).getLongValue(),
                (org.ccsds.moims.mo.mc.action.structures.ActionInstanceDetails) body.getBodyElement(1, new org.ccsds.moims.mo.mc.action.structures.ActionInstanceDetails()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.mc.action.ActionServiceInfo._REMOVEACTION_OP_NUMBER:
            removeAction((org.ccsds.moims.mo.mal.structures.LongList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.LongList()),
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
          case org.ccsds.moims.mo.mc.action.ActionServiceInfo._PRECHECKACTION_OP_NUMBER:
            Boolean preCheckActionRt = preCheckAction((org.ccsds.moims.mo.mc.action.structures.ActionInstanceDetails) body.getBodyElement(0, new org.ccsds.moims.mo.mc.action.structures.ActionInstanceDetails()),
                interaction);
            interaction.sendResponse((preCheckActionRt == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(preCheckActionRt));
            break;
          case org.ccsds.moims.mo.mc.action.ActionServiceInfo._LISTDEFINITION_OP_NUMBER:
            interaction.sendResponse(listDefinition((org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                interaction));
            break;
          case org.ccsds.moims.mo.mc.action.ActionServiceInfo._ADDACTION_OP_NUMBER:
            interaction.sendResponse(addAction((org.ccsds.moims.mo.mc.action.structures.ActionCreationRequestList) body.getBodyElement(0, new org.ccsds.moims.mo.mc.action.structures.ActionCreationRequestList()),
                interaction));
            break;
          case org.ccsds.moims.mo.mc.action.ActionServiceInfo._UPDATEDEFINITION_OP_NUMBER:
            interaction.sendResponse(updateDefinition((org.ccsds.moims.mo.mal.structures.LongList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.LongList()),
                (org.ccsds.moims.mo.mc.action.structures.ActionDefinitionDetailsList) body.getBodyElement(1, new org.ccsds.moims.mo.mc.action.structures.ActionDefinitionDetailsList()),
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
