package org.ccsds.moims.mo.mc.statistic.provider;

import java.io.IOException;
import java.util.Map;
import org.ccsds.moims.mo.com.structures.InstanceBooleanPairList;
import org.ccsds.moims.mo.com.structures.ObjectKeyList;
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
import org.ccsds.moims.mo.mc.statistic.StatisticHelper;
import org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo;
import org.ccsds.moims.mo.mc.statistic.structures.StatisticCreationRequestList;
import org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkDetailsList;

/**
 * Provider Inheritance skeleton for StatisticInheritanceSkeleton service.
 */
public abstract class StatisticInheritanceSkeleton implements MALInteractionHandler, StatisticSkeleton, StatisticHandler {

    /**
     * The providerSet field.
     */
    private MALProviderSet providerSet = new MALProviderSet(StatisticHelper.STATISTIC_SERVICE);

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
    public void setSkeleton(StatisticSkeleton skeleton) {
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
    public MonitorStatisticsPublisher createMonitorStatisticsPublisher(IdentifierList domain,
            Identifier networkZone,
            SessionType sessionType,
            Identifier sessionName,
            QoSLevel qos,
            Map qosProps,
            UInteger priority) throws MALException {
        return new MonitorStatisticsPublisher(providerSet.createPublisherSet(StatisticServiceInfo.MONITORSTATISTICS_OP, domain, sessionType, sessionName, qos, qosProps, null));
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
          case StatisticServiceInfo._ENABLESERVICE_OP_NUMBER:
            enableService((body.getBodyElement(0, new Union(Boolean.FALSE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Boolean.FALSE))).getBooleanValue(),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case StatisticServiceInfo._ENABLEREPORTING_OP_NUMBER:
            enableReporting((body.getBodyElement(0, new Union(Boolean.FALSE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Boolean.FALSE))).getBooleanValue(),
                (InstanceBooleanPairList) body.getBodyElement(1, new InstanceBooleanPairList()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case StatisticServiceInfo._REMOVEPARAMETEREVALUATION_OP_NUMBER:
            removeParameterEvaluation((LongList) body.getBodyElement(0, new LongList()),
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
          case StatisticServiceInfo._GETSTATISTICS_OP_NUMBER:
            interaction.sendResponse(getStatistics((LongList) body.getBodyElement(0, new LongList()),
                (body.getBodyElement(1, new Union(Boolean.FALSE)) == null) ? null : ((Union) body.getBodyElement(1, new Union(Boolean.FALSE))).getBooleanValue(),
                (ObjectKeyList) body.getBodyElement(2, new ObjectKeyList()),
                interaction));
            break;
          case StatisticServiceInfo._RESETEVALUATION_OP_NUMBER:
            interaction.sendResponse(resetEvaluation((body.getBodyElement(0, new Union(Boolean.FALSE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Boolean.FALSE))).getBooleanValue(),
                (LongList) body.getBodyElement(1, new LongList()),
                (body.getBodyElement(2, new Union(Boolean.FALSE)) == null) ? null : ((Union) body.getBodyElement(2, new Union(Boolean.FALSE))).getBooleanValue(),
                interaction));
            break;
          case StatisticServiceInfo._GETSERVICESTATUS_OP_NUMBER:
            Boolean getServiceStatusRt = getServiceStatus(interaction);
            interaction.sendResponse((getServiceStatusRt == null) ? null : new Union(getServiceStatusRt));
            break;
          case StatisticServiceInfo._LISTPARAMETEREVALUATIONS_OP_NUMBER:
            interaction.sendResponse(listParameterEvaluations((LongList) body.getBodyElement(0, new LongList()),
                interaction));
            break;
          case StatisticServiceInfo._ADDPARAMETEREVALUATION_OP_NUMBER:
            interaction.sendResponse(addParameterEvaluation((StatisticCreationRequestList) body.getBodyElement(0, new StatisticCreationRequestList()),
                interaction));
            break;
          case StatisticServiceInfo._UPDATEPARAMETEREVALUATION_OP_NUMBER:
            interaction.sendResponse(updateParameterEvaluation((LongList) body.getBodyElement(0, new LongList()),
                (StatisticLinkDetailsList) body.getBodyElement(1, new StatisticLinkDetailsList()),
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
