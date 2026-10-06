package org.ccsds.moims.mo.mc.check.provider;

import java.io.IOException;
import org.ccsds.moims.mo.com.structures.InstanceBooleanPairList;
import org.ccsds.moims.mo.com.structures.ObjectDetailsList;
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
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mc.check.CheckHelper;
import org.ccsds.moims.mo.mc.check.CheckServiceInfo;
import org.ccsds.moims.mo.mc.check.structures.CheckDefinitionDetailsList;
import org.ccsds.moims.mo.mc.check.structures.CheckLinkDetailsList;
import org.ccsds.moims.mo.mc.check.structures.CheckResultFilter;

/**
 * Provider Inheritance skeleton for CheckInheritanceSkeleton service.
 */
public abstract class CheckInheritanceSkeleton implements MALInteractionHandler, CheckSkeleton, CheckHandler {

    /**
     * The providerSet field.
     */
    private MALProviderSet providerSet = new MALProviderSet(CheckHelper.CHECK_SERVICE);

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
    public void setSkeleton(CheckSkeleton skeleton) {
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
          case CheckServiceInfo._ENABLESERVICE_OP_NUMBER:
            enableService((body.getBodyElement(0, new Union(Boolean.FALSE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Boolean.FALSE))).getBooleanValue(),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case CheckServiceInfo._ENABLECHECK_OP_NUMBER:
            enableCheck((body.getBodyElement(0, new Union(Boolean.FALSE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Boolean.FALSE))).getBooleanValue(),
                (InstanceBooleanPairList) body.getBodyElement(1, new InstanceBooleanPairList()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case CheckServiceInfo._TRIGGERCHECK_OP_NUMBER:
            triggerCheck((LongList) body.getBodyElement(0, new LongList()),
                (LongList) body.getBodyElement(1, new LongList()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case CheckServiceInfo._REMOVECHECK_OP_NUMBER:
            removeCheck((LongList) body.getBodyElement(0, new LongList()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case CheckServiceInfo._REMOVEPARAMETERCHECK_OP_NUMBER:
            removeParameterCheck((LongList) body.getBodyElement(0, new LongList()),
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
          case CheckServiceInfo._GETSERVICESTATUS_OP_NUMBER:
            Boolean getServiceStatusRt = getServiceStatus(interaction);
            interaction.sendResponse((getServiceStatusRt == null) ? null : new Union(getServiceStatusRt));
            break;
          case CheckServiceInfo._LISTDEFINITION_OP_NUMBER:
            interaction.sendResponse(listDefinition((IdentifierList) body.getBodyElement(0, new IdentifierList()),
                interaction));
            break;
          case CheckServiceInfo._LISTCHECKLINKS_OP_NUMBER:
            interaction.sendResponse(listCheckLinks((LongList) body.getBodyElement(0, new LongList()),
                interaction));
            break;
          case CheckServiceInfo._ADDCHECK_OP_NUMBER:
            interaction.sendResponse(addCheck((StringList) body.getBodyElement(0, new StringList()),
                (CheckDefinitionDetailsList) body.getBodyElement(1, new CheckDefinitionDetailsList()),
                interaction));
            break;
          case CheckServiceInfo._UPDATEDEFINITION_OP_NUMBER:
            interaction.sendResponse(updateDefinition((LongList) body.getBodyElement(0, new LongList()),
                (CheckDefinitionDetailsList) body.getBodyElement(1, new CheckDefinitionDetailsList()),
                interaction));
            break;
          case CheckServiceInfo._ADDPARAMETERCHECK_OP_NUMBER:
            interaction.sendResponse(addParameterCheck((CheckLinkDetailsList) body.getBodyElement(0, new CheckLinkDetailsList()),
                (ObjectDetailsList) body.getBodyElement(1, new ObjectDetailsList()),
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
          case CheckServiceInfo._GETCURRENTTRANSITIONLIST_OP_NUMBER:
            getCurrentTransitionList((CheckResultFilter) body.getBodyElement(0, new CheckResultFilter()),
                new GetCurrentTransitionListInteraction(interaction));
            break;
          case CheckServiceInfo._GETSUMMARYREPORT_OP_NUMBER:
            getSummaryReport((LongList) body.getBodyElement(0, new LongList()),
                new GetSummaryReportInteraction(interaction));
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
