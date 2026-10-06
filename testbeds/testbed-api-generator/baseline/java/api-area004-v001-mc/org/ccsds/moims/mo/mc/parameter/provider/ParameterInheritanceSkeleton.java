package org.ccsds.moims.mo.mc.parameter.provider;

import java.io.IOException;
import java.util.Map;
import org.ccsds.moims.mo.com.structures.InstanceBooleanPairList;
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
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mc.parameter.ParameterHelper;
import org.ccsds.moims.mo.mc.parameter.ParameterServiceInfo;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterCreationRequestList;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterDefinitionDetailsList;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterRawValueList;

/**
 * Provider Inheritance skeleton for ParameterInheritanceSkeleton service.
 */
public abstract class ParameterInheritanceSkeleton implements MALInteractionHandler, ParameterSkeleton, ParameterHandler {

    /**
     * The providerSet field.
     */
    private MALProviderSet providerSet = new MALProviderSet(ParameterHelper.PARAMETER_SERVICE);

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
    public void setSkeleton(ParameterSkeleton skeleton) {
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
    public MonitorValuePublisher createMonitorValuePublisher(IdentifierList domain,
            Identifier networkZone,
            SessionType sessionType,
            Identifier sessionName,
            QoSLevel qos,
            Map qosProps,
            UInteger priority) throws MALException {
        return new MonitorValuePublisher(providerSet.createPublisherSet(ParameterServiceInfo.MONITORVALUE_OP, domain, sessionType, sessionName, qos, qosProps, null));
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
          case ParameterServiceInfo._SETVALUE_OP_NUMBER:
            setValue((ParameterRawValueList) body.getBodyElement(0, new ParameterRawValueList()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case ParameterServiceInfo._REMOVEPARAMETER_OP_NUMBER:
            removeParameter((LongList) body.getBodyElement(0, new LongList()),
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
          case ParameterServiceInfo._GETVALUE_OP_NUMBER:
            interaction.sendResponse(getValue((LongList) body.getBodyElement(0, new LongList()),
                interaction));
            break;
          case ParameterServiceInfo._ENABLEGENERATION_OP_NUMBER:
            interaction.sendResponse(enableGeneration((body.getBodyElement(0, new Union(Boolean.FALSE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Boolean.FALSE))).getBooleanValue(),
                (InstanceBooleanPairList) body.getBodyElement(1, new InstanceBooleanPairList()),
                interaction));
            break;
          case ParameterServiceInfo._LISTDEFINITION_OP_NUMBER:
            interaction.sendResponse(listDefinition((IdentifierList) body.getBodyElement(0, new IdentifierList()),
                interaction));
            break;
          case ParameterServiceInfo._ADDPARAMETER_OP_NUMBER:
            interaction.sendResponse(addParameter((ParameterCreationRequestList) body.getBodyElement(0, new ParameterCreationRequestList()),
                interaction));
            break;
          case ParameterServiceInfo._UPDATEDEFINITION_OP_NUMBER:
            interaction.sendResponse(updateDefinition((LongList) body.getBodyElement(0, new LongList()),
                (ParameterDefinitionDetailsList) body.getBodyElement(1, new ParameterDefinitionDetailsList()),
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
        switch (opNumber) {
          default:
            interaction.sendError(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new MALInteractionException(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
    }

}
