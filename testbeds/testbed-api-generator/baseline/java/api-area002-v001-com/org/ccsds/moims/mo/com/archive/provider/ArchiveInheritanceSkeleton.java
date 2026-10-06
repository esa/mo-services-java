package org.ccsds.moims.mo.com.archive.provider;

import java.io.IOException;
import org.ccsds.moims.mo.com.archive.ArchiveHelper;
import org.ccsds.moims.mo.com.archive.ArchiveServiceInfo;
import org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList;
import org.ccsds.moims.mo.com.archive.structures.ArchiveQueryList;
import org.ccsds.moims.mo.com.archive.structures.QueryFilterList;
import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.UnsupportedOperationException;
import org.ccsds.moims.mo.mal.helpertools.connections.ConnectionProvider;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.provider.MALInvoke;
import org.ccsds.moims.mo.mal.provider.MALProgress;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALProviderSet;
import org.ccsds.moims.mo.mal.provider.MALRequest;
import org.ccsds.moims.mo.mal.provider.MALSubmit;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;

/**
 * Provider Inheritance skeleton for ArchiveInheritanceSkeleton service.
 */
public abstract class ArchiveInheritanceSkeleton implements MALInteractionHandler, ArchiveSkeleton, ArchiveHandler {

    /**
     * The providerSet field.
     */
    private MALProviderSet providerSet = new MALProviderSet(ArchiveHelper.ARCHIVE_SERVICE);

    /**
     * Returns the connection object for this provider.
     * 
     * @return the connection object for this provider
     * @throws IOException if the method was not implemented yet.
     */
    public ConnectionProvider getConnection() throws IOException {
        throw new IOException("This method needs to be overridden!");
    }

    @Override
    public void setSkeleton(ArchiveSkeleton skeleton) {
        // Not used in the inheritance pattern (the skeleton is 'this');
    }

    @Override
    public void malInitialize(MALProvider provider) throws MALException {
        providerSet.addProvider(provider);
    }

    @Override
    public void malFinalize(MALProvider provider) throws MALException {
        providerSet.removeProvider(provider);
    }

    @Override
    public void handleSend(MALInteraction interaction,
            MALMessageBody body) throws MALException, MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        switch (opNumber) {
          default:
            throw new MALInteractionException(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
    }

    @Override
    public void handleSubmit(MALSubmit interaction,
            MALMessageBody body) throws MALException, MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        try {
        switch (opNumber) {
          case ArchiveServiceInfo._UPDATE_OP_NUMBER:
            update((ObjectType) body.getBodyElement(0, new ObjectType()),
                (IdentifierList) body.getBodyElement(1, new IdentifierList()),
                (ArchiveDetailsList) body.getBodyElement(2, new ArchiveDetailsList()),
                (HeterogeneousList) body.getBodyElement(3, null),
                interaction);
            interaction.sendAcknowledgement();
            break;
          default:
            interaction.sendError(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new MALInteractionException(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
        } catch (MOErrorException error) {
          throw new MALInteractionException(error);
        }
    }

    @Override
    public void handleRequest(MALRequest interaction,
            MALMessageBody body) throws MALException, MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        try {
        switch (opNumber) {
          case ArchiveServiceInfo._STORE_OP_NUMBER:
            interaction.sendResponse(store((body.getBodyElement(0, new Union(Boolean.FALSE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Boolean.FALSE))).getBooleanValue(),
                (ObjectType) body.getBodyElement(1, new ObjectType()),
                (IdentifierList) body.getBodyElement(2, new IdentifierList()),
                (ArchiveDetailsList) body.getBodyElement(3, new ArchiveDetailsList()),
                (HeterogeneousList) body.getBodyElement(4, null),
                interaction));
            break;
          case ArchiveServiceInfo._DELETE_OP_NUMBER:
            interaction.sendResponse(delete((ObjectType) body.getBodyElement(0, new ObjectType()),
                (IdentifierList) body.getBodyElement(1, new IdentifierList()),
                (LongList) body.getBodyElement(2, new LongList()),
                interaction));
            break;
          default:
            interaction.sendError(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new MALInteractionException(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
        } catch (MOErrorException error) {
          throw new MALInteractionException(error);
        }
    }

    @Override
    public void handleInvoke(MALInvoke interaction,
            MALMessageBody body) throws MALException, MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        try {
        switch (opNumber) {
          case ArchiveServiceInfo._RETRIEVE_OP_NUMBER:
            retrieve((ObjectType) body.getBodyElement(0, new ObjectType()),
                (IdentifierList) body.getBodyElement(1, new IdentifierList()),
                (LongList) body.getBodyElement(2, new LongList()),
                new RetrieveInteraction(interaction));
            break;
          case ArchiveServiceInfo._COUNT_OP_NUMBER:
            count((ObjectType) body.getBodyElement(0, new ObjectType()),
                (ArchiveQueryList) body.getBodyElement(1, new ArchiveQueryList()),
                (QueryFilterList) body.getBodyElement(2, new QueryFilterList()),
                new CountInteraction(interaction));
            break;
          default:
            interaction.sendError(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new MALInteractionException(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
        } catch (MOErrorException error) {
          throw new MALInteractionException(error);
        }
    }

    @Override
    public void handleProgress(MALProgress interaction,
            MALMessageBody body) throws MALException, MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        try {
        switch (opNumber) {
          case ArchiveServiceInfo._QUERY_OP_NUMBER:
            query((body.getBodyElement(0, new Union(Boolean.FALSE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Boolean.FALSE))).getBooleanValue(),
                (ObjectType) body.getBodyElement(1, new ObjectType()),
                (ArchiveQueryList) body.getBodyElement(2, new ArchiveQueryList()),
                (QueryFilterList) body.getBodyElement(3, new QueryFilterList()),
                new QueryInteraction(interaction));
            break;
          default:
            interaction.sendError(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new MALInteractionException(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
        } catch (MOErrorException error) {
          throw new MALInteractionException(error);
        }
    }

}
