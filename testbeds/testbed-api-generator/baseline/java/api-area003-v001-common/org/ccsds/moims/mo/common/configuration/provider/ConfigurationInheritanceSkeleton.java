package org.ccsds.moims.mo.common.configuration.provider;

/**
 * Provider Inheritance skeleton for ConfigurationInheritanceSkeleton service.
 */
public abstract class ConfigurationInheritanceSkeleton implements org.ccsds.moims.mo.mal.provider.MALInteractionHandler, org.ccsds.moims.mo.common.configuration.provider.ConfigurationSkeleton, org.ccsds.moims.mo.common.configuration.provider.ConfigurationHandler {

    /**
     * The providerSet field.
     */
    private org.ccsds.moims.mo.mal.provider.MALProviderSet providerSet = new org.ccsds.moims.mo.mal.provider.MALProviderSet(org.ccsds.moims.mo.common.configuration.ConfigurationHelper.CONFIGURATION_SERVICE);

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
    public void setSkeleton(org.ccsds.moims.mo.common.configuration.provider.ConfigurationSkeleton skeleton) {
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
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._ADD_OP_NUMBER:
            add((org.ccsds.moims.mo.com.structures.ObjectKey) body.getBodyElement(0, new org.ccsds.moims.mo.com.structures.ObjectKey()),
                (org.ccsds.moims.mo.com.structures.ObjectIdList) body.getBodyElement(1, new org.ccsds.moims.mo.com.structures.ObjectIdList()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._REMOVE_OP_NUMBER:
            remove((org.ccsds.moims.mo.com.structures.ObjectKey) body.getBodyElement(0, new org.ccsds.moims.mo.com.structures.ObjectKey()),
                (org.ccsds.moims.mo.com.structures.ObjectIdList) body.getBodyElement(1, new org.ccsds.moims.mo.com.structures.ObjectIdList()),
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
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._LIST_OP_NUMBER:
            interaction.sendResponse(list((org.ccsds.moims.mo.common.configuration.structures.ConfigurationType) body.getBodyElement(0, org.ccsds.moims.mo.common.configuration.structures.ConfigurationType.PROVIDER),
                (org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                (org.ccsds.moims.mo.common.structures.ServiceKey) body.getBodyElement(2, new org.ccsds.moims.mo.common.structures.ServiceKey()),
                interaction));
            break;
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._GETCURRENT_OP_NUMBER:
            interaction.sendResponse(getCurrent((org.ccsds.moims.mo.com.structures.ObjectKey) body.getBodyElement(0, new org.ccsds.moims.mo.com.structures.ObjectKey()),
                (org.ccsds.moims.mo.common.structures.ServiceKey) body.getBodyElement(1, new org.ccsds.moims.mo.common.structures.ServiceKey()),
                interaction));
            break;
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._EXPORTXML_OP_NUMBER:
            interaction.sendResponse(exportXML((org.ccsds.moims.mo.com.structures.ObjectId) body.getBodyElement(0, new org.ccsds.moims.mo.com.structures.ObjectId()),
                (body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE))).getBooleanValue(),
                interaction));
            break;
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._IMPORTXML_OP_NUMBER:
            interaction.sendResponse(importXML((org.ccsds.moims.mo.mal.structures.File) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.File()),
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
        try {
        switch (opNumber) {
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._ACTIVATE_OP_NUMBER:
            activate((org.ccsds.moims.mo.com.structures.ObjectKey) body.getBodyElement(0, new org.ccsds.moims.mo.com.structures.ObjectKey()),
                (org.ccsds.moims.mo.com.structures.ObjectId) body.getBodyElement(1, new org.ccsds.moims.mo.com.structures.ObjectId()),
                new ActivateInteraction(interaction));
            break;
          case org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo._STORECURRENT_OP_NUMBER:
            storeCurrent((org.ccsds.moims.mo.com.structures.ObjectKey) body.getBodyElement(0, new org.ccsds.moims.mo.com.structures.ObjectKey()),
                (org.ccsds.moims.mo.common.structures.ServiceKey) body.getBodyElement(1, new org.ccsds.moims.mo.common.structures.ServiceKey()),
                (body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE))).getBooleanValue(),
                new StoreCurrentInteraction(interaction));
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
