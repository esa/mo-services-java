package org.ccsds.moims.mo.mc.aggregation.provider;

import java.io.IOException;
import java.util.Map;
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
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mc.aggregation.AggregationHelper;
import org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo;
import org.ccsds.moims.mo.mc.structures.AggregationDefinitionList;

/**
 * Provider Inheritance skeleton for AggregationInheritanceSkeleton service.
 */
public abstract class AggregationInheritanceSkeleton implements MALInteractionHandler, AggregationSkeleton, AggregationHandler {

    /**
     * The providerSet field.
     */
    private MALProviderSet providerSet = new MALProviderSet(AggregationHelper.AGGREGATION_SERVICE);

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
    public void setSkeleton(AggregationSkeleton skeleton) {
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
        return new MonitorValuePublisher(providerSet.createPublisherSet(AggregationServiceInfo.MONITORVALUE_OP, domain, sessionType, sessionName, qos, qosProps, null));
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
          case AggregationServiceInfo._ENABLEREPORTING_OP_NUMBER:
            enableReporting((IdentifierList) body.getBodyElement(0, new IdentifierList()),
                (IdentifierList) body.getBodyElement(1, new IdentifierList()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case AggregationServiceInfo._DISABLEREPORTING_OP_NUMBER:
            disableReporting((IdentifierList) body.getBodyElement(0, new IdentifierList()),
                (IdentifierList) body.getBodyElement(1, new IdentifierList()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case AggregationServiceInfo._SETREPORTINGPERIOD_OP_NUMBER:
            setReportingPeriod((IdentifierList) body.getBodyElement(0, new IdentifierList()),
                (IdentifierList) body.getBodyElement(1, new IdentifierList()),
                (Duration) body.getBodyElement(2, new Duration()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case AggregationServiceInfo._ADDAGGREGATION_OP_NUMBER:
            addAggregation((AggregationDefinitionList) body.getBodyElement(0, new AggregationDefinitionList()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case AggregationServiceInfo._REMOVEAGGREGATION_OP_NUMBER:
            removeAggregation((IdentifierList) body.getBodyElement(0, new IdentifierList()),
                (IdentifierList) body.getBodyElement(1, new IdentifierList()),
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
          case AggregationServiceInfo._GETVALUE_OP_NUMBER:
            interaction.sendResponse(getValue((IdentifierList) body.getBodyElement(0, new IdentifierList()),
                (IdentifierList) body.getBodyElement(1, new IdentifierList()),
                interaction));
            break;
          case AggregationServiceInfo._GETREPORTINGCONFIGURATION_OP_NUMBER:
            interaction.sendResponse(getReportingConfiguration((IdentifierList) body.getBodyElement(0, new IdentifierList()),
                (IdentifierList) body.getBodyElement(1, new IdentifierList()),
                interaction));
            break;
          case AggregationServiceInfo._LISTDEFINITION_OP_NUMBER:
            interaction.sendResponse(listDefinition((IdentifierList) body.getBodyElement(0, new IdentifierList()),
                (IdentifierList) body.getBodyElement(1, new IdentifierList()),
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
