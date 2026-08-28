package org.ccsds.moims.mo.mc.statistic.provider;

/**
 * Provider Inheritance skeleton for StatisticInheritanceSkeleton service.
 */
public abstract class StatisticInheritanceSkeleton implements org.ccsds.moims.mo.mal.provider.MALInteractionHandler, org.ccsds.moims.mo.mc.statistic.provider.StatisticSkeleton, org.ccsds.moims.mo.mc.statistic.provider.StatisticHandler {

    /**
     * The providerSet field.
     */
    private org.ccsds.moims.mo.mal.provider.MALProviderSet providerSet = new org.ccsds.moims.mo.mal.provider.MALProviderSet(org.ccsds.moims.mo.mc.statistic.StatisticHelper.STATISTIC_SERVICE);

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
    public void setSkeleton(org.ccsds.moims.mo.mc.statistic.provider.StatisticSkeleton skeleton) {
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
    public org.ccsds.moims.mo.mc.statistic.provider.MonitorStatisticsPublisher createMonitorStatisticsPublisher(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.Identifier networkZone,
            org.ccsds.moims.mo.mal.structures.SessionType sessionType,
            org.ccsds.moims.mo.mal.structures.Identifier sessionName,
            org.ccsds.moims.mo.mal.structures.QoSLevel qos,
            java.util.Map qosProps,
            org.ccsds.moims.mo.mal.structures.UInteger priority) throws org.ccsds.moims.mo.mal.MALException {
        return new org.ccsds.moims.mo.mc.statistic.provider.MonitorStatisticsPublisher(providerSet.createPublisherSet(org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo.MONITORSTATISTICS_OP, domain, sessionType, sessionName, qos, qosProps, null));
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
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._ENABLESERVICE_OP_NUMBER:
            enableService((body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE))).getBooleanValue(),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._ENABLEREPORTING_OP_NUMBER:
            enableReporting((body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE))).getBooleanValue(),
                (org.ccsds.moims.mo.com.structures.InstanceBooleanPairList) body.getBodyElement(1, new org.ccsds.moims.mo.com.structures.InstanceBooleanPairList()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._REMOVEPARAMETEREVALUATION_OP_NUMBER:
            removeParameterEvaluation((org.ccsds.moims.mo.mal.structures.LongList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.LongList()),
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
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._GETSTATISTICS_OP_NUMBER:
            interaction.sendResponse(getStatistics((org.ccsds.moims.mo.mal.structures.LongList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.LongList()),
                (body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE))).getBooleanValue(),
                (org.ccsds.moims.mo.com.structures.ObjectKeyList) body.getBodyElement(2, new org.ccsds.moims.mo.com.structures.ObjectKeyList()),
                interaction));
            break;
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._RESETEVALUATION_OP_NUMBER:
            interaction.sendResponse(resetEvaluation((body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE))).getBooleanValue(),
                (org.ccsds.moims.mo.mal.structures.LongList) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.LongList()),
                (body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE))).getBooleanValue(),
                interaction));
            break;
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._GETSERVICESTATUS_OP_NUMBER:
            Boolean getServiceStatusRt = getServiceStatus(interaction);
            interaction.sendResponse((getServiceStatusRt == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(getServiceStatusRt));
            break;
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._LISTPARAMETEREVALUATIONS_OP_NUMBER:
            interaction.sendResponse(listParameterEvaluations((org.ccsds.moims.mo.mal.structures.LongList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.LongList()),
                interaction));
            break;
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._ADDPARAMETEREVALUATION_OP_NUMBER:
            interaction.sendResponse(addParameterEvaluation((org.ccsds.moims.mo.mc.statistic.structures.StatisticCreationRequestList) body.getBodyElement(0, new org.ccsds.moims.mo.mc.statistic.structures.StatisticCreationRequestList()),
                interaction));
            break;
          case org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo._UPDATEPARAMETEREVALUATION_OP_NUMBER:
            interaction.sendResponse(updateParameterEvaluation((org.ccsds.moims.mo.mal.structures.LongList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.LongList()),
                (org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkDetailsList) body.getBodyElement(1, new org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkDetailsList()),
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
        switch (opNumber) {
          default:
            interaction.sendError(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                    org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new org.ccsds.moims.mo.mal.MALInteractionException(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                    org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
    }

}
