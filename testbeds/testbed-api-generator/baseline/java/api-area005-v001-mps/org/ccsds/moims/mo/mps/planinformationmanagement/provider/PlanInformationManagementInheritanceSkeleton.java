package org.ccsds.moims.mo.mps.planinformationmanagement.provider;

import java.io.IOException;
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
import org.ccsds.moims.mo.mal.structures.AttributeTypeList;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementHelper;
import org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo;

/**
 * Provider Inheritance skeleton for PlanInformationManagementInheritanceSkeleton
 * service.
 */
public abstract class PlanInformationManagementInheritanceSkeleton implements MALInteractionHandler, PlanInformationManagementSkeleton, PlanInformationManagementHandler {

    /**
     * The providerSet field.
     */
    private MALProviderSet providerSet = new MALProviderSet(PlanInformationManagementHelper.PLANINFORMATIONMANAGEMENT_SERVICE);

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
    public void setSkeleton(PlanInformationManagementSkeleton skeleton) {
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
        switch (opNumber) {
          default:
            interaction.sendError(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new MALInteractionException(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
    }

    @Override
    public void handleRequest(MALRequest interaction,
            MALMessageBody body) throws MALException, MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        try {
        switch (opNumber) {
          case PlanInformationManagementServiceInfo._GETREQUESTDEFS_OP_NUMBER:
            interaction.sendResponse(getRequestDefs((ObjectRefList) body.getBodyElement(0, new ObjectRefList()),
                interaction));
            break;
          case PlanInformationManagementServiceInfo._GETEVENTDEFS_OP_NUMBER:
            interaction.sendResponse(getEventDefs((ObjectRefList) body.getBodyElement(0, new ObjectRefList()),
                interaction));
            break;
          case PlanInformationManagementServiceInfo._GETACTIVITYDEFS_OP_NUMBER:
            interaction.sendResponse(getActivityDefs((ObjectRefList) body.getBodyElement(0, new ObjectRefList()),
                interaction));
            break;
          case PlanInformationManagementServiceInfo._GETRESOURCEDEFS_OP_NUMBER:
            interaction.sendResponse(getResourceDefs((ObjectRefList) body.getBodyElement(0, new ObjectRefList()),
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
        switch (opNumber) {
          default:
            interaction.sendError(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new MALInteractionException(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
    }

    @Override
    public void handleProgress(MALProgress interaction,
            MALMessageBody body) throws MALException, MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        try {
        switch (opNumber) {
          case PlanInformationManagementServiceInfo._LISTREQUESTDEFS_OP_NUMBER:
            listRequestDefs((IdentifierList) body.getBodyElement(0, new IdentifierList()),
                (ObjectRefList) body.getBodyElement(1, new ObjectRefList()),
                new ListRequestDefsInteraction(interaction));
            break;
          case PlanInformationManagementServiceInfo._LISTEVENTDEFS_OP_NUMBER:
            listEventDefs((IdentifierList) body.getBodyElement(0, new IdentifierList()),
                (ObjectRefList) body.getBodyElement(1, new ObjectRefList()),
                new ListEventDefsInteraction(interaction));
            break;
          case PlanInformationManagementServiceInfo._LISTACTIVITYDEFS_OP_NUMBER:
            listActivityDefs((IdentifierList) body.getBodyElement(0, new IdentifierList()),
                (ObjectRefList) body.getBodyElement(1, new ObjectRefList()),
                (StringList) body.getBodyElement(2, new StringList()),
                new ListActivityDefsInteraction(interaction));
            break;
          case PlanInformationManagementServiceInfo._LISTRESOURCEDEFS_OP_NUMBER:
            listResourceDefs((IdentifierList) body.getBodyElement(0, new IdentifierList()),
                (AttributeTypeList) body.getBodyElement(1, new AttributeTypeList()),
                new ListResourceDefsInteraction(interaction));
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
