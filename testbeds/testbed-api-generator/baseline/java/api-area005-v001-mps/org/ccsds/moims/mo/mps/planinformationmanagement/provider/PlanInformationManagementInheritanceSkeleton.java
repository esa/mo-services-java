package org.ccsds.moims.mo.mps.planinformationmanagement.provider;

/**
 * Provider Inheritance skeleton for PlanInformationManagementInheritanceSkeleton
 * service.
 */
public abstract class PlanInformationManagementInheritanceSkeleton implements org.ccsds.moims.mo.mal.provider.MALInteractionHandler, org.ccsds.moims.mo.mps.planinformationmanagement.provider.PlanInformationManagementSkeleton, org.ccsds.moims.mo.mps.planinformationmanagement.provider.PlanInformationManagementHandler {

    /**
     * The providerSet field.
     */
    private org.ccsds.moims.mo.mal.provider.MALProviderSet providerSet = new org.ccsds.moims.mo.mal.provider.MALProviderSet(org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementHelper.PLANINFORMATIONMANAGEMENT_SERVICE);

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
    public void setSkeleton(org.ccsds.moims.mo.mps.planinformationmanagement.provider.PlanInformationManagementSkeleton skeleton) {
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
        switch (opNumber) {
          default:
            interaction.sendError(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                    org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new org.ccsds.moims.mo.mal.MALInteractionException(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                    org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
    }

    @Override
    public void handleRequest(org.ccsds.moims.mo.mal.provider.MALRequest interaction,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body) throws org.ccsds.moims.mo.mal.MALException, org.ccsds.moims.mo.mal.MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        try {
        switch (opNumber) {
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._GETREQUESTDEFS_OP_NUMBER:
            interaction.sendResponse(getRequestDefs((org.ccsds.moims.mo.mal.structures.ObjectRefList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRefList()),
                interaction));
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._GETEVENTDEFS_OP_NUMBER:
            interaction.sendResponse(getEventDefs((org.ccsds.moims.mo.mal.structures.ObjectRefList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRefList()),
                interaction));
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._GETACTIVITYDEFS_OP_NUMBER:
            interaction.sendResponse(getActivityDefs((org.ccsds.moims.mo.mal.structures.ObjectRefList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRefList()),
                interaction));
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._GETRESOURCEDEFS_OP_NUMBER:
            interaction.sendResponse(getResourceDefs((org.ccsds.moims.mo.mal.structures.ObjectRefList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRefList()),
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
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTREQUESTDEFS_OP_NUMBER:
            listRequestDefs((org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                (org.ccsds.moims.mo.mal.structures.ObjectRefList) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.ObjectRefList()),
                new ListRequestDefsInteraction(interaction));
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTEVENTDEFS_OP_NUMBER:
            listEventDefs((org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                (org.ccsds.moims.mo.mal.structures.ObjectRefList) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.ObjectRefList()),
                new ListEventDefsInteraction(interaction));
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTACTIVITYDEFS_OP_NUMBER:
            listActivityDefs((org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                (org.ccsds.moims.mo.mal.structures.ObjectRefList) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.ObjectRefList()),
                (org.ccsds.moims.mo.mal.structures.StringList) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.StringList()),
                new ListActivityDefsInteraction(interaction));
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTRESOURCEDEFS_OP_NUMBER:
            listResourceDefs((org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                (org.ccsds.moims.mo.mal.structures.AttributeTypeList) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.AttributeTypeList()),
                new ListResourceDefsInteraction(interaction));
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
