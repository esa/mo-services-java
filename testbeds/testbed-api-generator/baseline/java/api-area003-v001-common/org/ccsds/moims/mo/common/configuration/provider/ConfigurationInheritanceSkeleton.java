package org.ccsds.moims.mo.common.configuration.provider;

import java.io.IOException;
import org.ccsds.moims.mo.com.structures.ObjectId;
import org.ccsds.moims.mo.com.structures.ObjectIdList;
import org.ccsds.moims.mo.com.structures.ObjectKey;
import org.ccsds.moims.mo.common.configuration.ConfigurationHelper;
import org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo;
import org.ccsds.moims.mo.common.configuration.structures.ConfigurationType;
import org.ccsds.moims.mo.common.structures.ServiceKey;
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
import org.ccsds.moims.mo.mal.structures.File;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;

/**
 * Provider Inheritance skeleton for ConfigurationInheritanceSkeleton service.
 */
public abstract class ConfigurationInheritanceSkeleton implements MALInteractionHandler, ConfigurationSkeleton, ConfigurationHandler {

    /**
     * The providerSet field.
     */
    private MALProviderSet providerSet = new MALProviderSet(ConfigurationHelper.CONFIGURATION_SERVICE);

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
    public void setSkeleton(ConfigurationSkeleton skeleton) {
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
          case ConfigurationServiceInfo._ADD_OP_NUMBER:
            add((ObjectKey) body.getBodyElement(0, new ObjectKey()),
                (ObjectIdList) body.getBodyElement(1, new ObjectIdList()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case ConfigurationServiceInfo._REMOVE_OP_NUMBER:
            remove((ObjectKey) body.getBodyElement(0, new ObjectKey()),
                (ObjectIdList) body.getBodyElement(1, new ObjectIdList()),
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
          case ConfigurationServiceInfo._LIST_OP_NUMBER:
            interaction.sendResponse(list((ConfigurationType) body.getBodyElement(0, ConfigurationType.PROVIDER),
                (IdentifierList) body.getBodyElement(1, new IdentifierList()),
                (ServiceKey) body.getBodyElement(2, new ServiceKey()),
                interaction));
            break;
          case ConfigurationServiceInfo._GETCURRENT_OP_NUMBER:
            interaction.sendResponse(getCurrent((ObjectKey) body.getBodyElement(0, new ObjectKey()),
                (ServiceKey) body.getBodyElement(1, new ServiceKey()),
                interaction));
            break;
          case ConfigurationServiceInfo._EXPORTXML_OP_NUMBER:
            interaction.sendResponse(exportXML((ObjectId) body.getBodyElement(0, new ObjectId()),
                (body.getBodyElement(1, new Union(Boolean.FALSE)) == null) ? null : ((Union) body.getBodyElement(1, new Union(Boolean.FALSE))).getBooleanValue(),
                interaction));
            break;
          case ConfigurationServiceInfo._IMPORTXML_OP_NUMBER:
            interaction.sendResponse(importXML((File) body.getBodyElement(0, new File()),
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
          case ConfigurationServiceInfo._ACTIVATE_OP_NUMBER:
            activate((ObjectKey) body.getBodyElement(0, new ObjectKey()),
                (ObjectId) body.getBodyElement(1, new ObjectId()),
                new ActivateInteraction(interaction));
            break;
          case ConfigurationServiceInfo._STORECURRENT_OP_NUMBER:
            storeCurrent((ObjectKey) body.getBodyElement(0, new ObjectKey()),
                (ServiceKey) body.getBodyElement(1, new ServiceKey()),
                (body.getBodyElement(2, new Union(Boolean.FALSE)) == null) ? null : ((Union) body.getBodyElement(2, new Union(Boolean.FALSE))).getBooleanValue(),
                new StoreCurrentInteraction(interaction));
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
        switch (opNumber) {
          default:
            interaction.sendError(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new MALInteractionException(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
    }

}
