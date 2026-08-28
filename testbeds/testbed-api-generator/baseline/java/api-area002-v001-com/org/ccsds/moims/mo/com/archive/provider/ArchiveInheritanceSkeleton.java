package org.ccsds.moims.mo.com.archive.provider;

/**
 * Provider Inheritance skeleton for ArchiveInheritanceSkeleton service.
 */
public abstract class ArchiveInheritanceSkeleton implements org.ccsds.moims.mo.mal.provider.MALInteractionHandler, org.ccsds.moims.mo.com.archive.provider.ArchiveSkeleton, org.ccsds.moims.mo.com.archive.provider.ArchiveHandler {

    /**
     * The providerSet field.
     */
    private org.ccsds.moims.mo.mal.provider.MALProviderSet providerSet = new org.ccsds.moims.mo.mal.provider.MALProviderSet(org.ccsds.moims.mo.com.archive.ArchiveHelper.ARCHIVE_SERVICE);

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
    public void setSkeleton(org.ccsds.moims.mo.com.archive.provider.ArchiveSkeleton skeleton) {
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
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._UPDATE_OP_NUMBER:
            update((org.ccsds.moims.mo.com.structures.ObjectType) body.getBodyElement(0, new org.ccsds.moims.mo.com.structures.ObjectType()),
                (org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                (org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList) body.getBodyElement(2, new org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList()),
                (org.ccsds.moims.mo.mal.structures.HeterogeneousList) body.getBodyElement(3, null),
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
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._STORE_OP_NUMBER:
            interaction.sendResponse(store((body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE))).getBooleanValue(),
                (org.ccsds.moims.mo.com.structures.ObjectType) body.getBodyElement(1, new org.ccsds.moims.mo.com.structures.ObjectType()),
                (org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                (org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList) body.getBodyElement(3, new org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList()),
                (org.ccsds.moims.mo.mal.structures.HeterogeneousList) body.getBodyElement(4, null),
                interaction));
            break;
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._DELETE_OP_NUMBER:
            interaction.sendResponse(delete((org.ccsds.moims.mo.com.structures.ObjectType) body.getBodyElement(0, new org.ccsds.moims.mo.com.structures.ObjectType()),
                (org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                (org.ccsds.moims.mo.mal.structures.LongList) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.LongList()),
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
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._RETRIEVE_OP_NUMBER:
            retrieve((org.ccsds.moims.mo.com.structures.ObjectType) body.getBodyElement(0, new org.ccsds.moims.mo.com.structures.ObjectType()),
                (org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                (org.ccsds.moims.mo.mal.structures.LongList) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.LongList()),
                new RetrieveInteraction(interaction));
            break;
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._COUNT_OP_NUMBER:
            count((org.ccsds.moims.mo.com.structures.ObjectType) body.getBodyElement(0, new org.ccsds.moims.mo.com.structures.ObjectType()),
                (org.ccsds.moims.mo.com.archive.structures.ArchiveQueryList) body.getBodyElement(1, new org.ccsds.moims.mo.com.archive.structures.ArchiveQueryList()),
                (org.ccsds.moims.mo.com.archive.structures.QueryFilterList) body.getBodyElement(2, new org.ccsds.moims.mo.com.archive.structures.QueryFilterList()),
                new CountInteraction(interaction));
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
        try {
        switch (opNumber) {
          case org.ccsds.moims.mo.com.archive.ArchiveServiceInfo._QUERY_OP_NUMBER:
            query((body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE))).getBooleanValue(),
                (org.ccsds.moims.mo.com.structures.ObjectType) body.getBodyElement(1, new org.ccsds.moims.mo.com.structures.ObjectType()),
                (org.ccsds.moims.mo.com.archive.structures.ArchiveQueryList) body.getBodyElement(2, new org.ccsds.moims.mo.com.archive.structures.ArchiveQueryList()),
                (org.ccsds.moims.mo.com.archive.structures.QueryFilterList) body.getBodyElement(3, new org.ccsds.moims.mo.com.archive.structures.QueryFilterList()),
                new QueryInteraction(interaction));
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
