package org.ccsds.moims.mo.mc.parameter.provider;

/**
 * Provider Inheritance skeleton for ParameterInheritanceSkeleton service.
 */
public abstract class ParameterInheritanceSkeleton implements org.ccsds.moims.mo.mal.provider.MALInteractionHandler, org.ccsds.moims.mo.mc.parameter.provider.ParameterSkeleton, org.ccsds.moims.mo.mc.parameter.provider.ParameterHandler {

    /**
     * The providerSet field.
     */
    private org.ccsds.moims.mo.mal.provider.MALProviderSet providerSet = new org.ccsds.moims.mo.mal.provider.MALProviderSet(org.ccsds.moims.mo.mc.parameter.ParameterHelper.PARAMETER_SERVICE);

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
    public void setSkeleton(org.ccsds.moims.mo.mc.parameter.provider.ParameterSkeleton skeleton) {
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
    public org.ccsds.moims.mo.mc.parameter.provider.MonitorValuePublisher createMonitorValuePublisher(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.Identifier networkZone,
            org.ccsds.moims.mo.mal.structures.SessionType sessionType,
            org.ccsds.moims.mo.mal.structures.Identifier sessionName,
            org.ccsds.moims.mo.mal.structures.QoSLevel qos,
            java.util.Map qosProps,
            org.ccsds.moims.mo.mal.structures.UInteger priority) throws org.ccsds.moims.mo.mal.MALException {
        return new org.ccsds.moims.mo.mc.parameter.provider.MonitorValuePublisher(providerSet.createPublisherSet(org.ccsds.moims.mo.mc.parameter.ParameterServiceInfo.MONITORVALUE_OP, domain, sessionType, sessionName, qos, qosProps, null));
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
          case org.ccsds.moims.mo.mc.parameter.ParameterServiceInfo._SETVALUE_OP_NUMBER:
            setValue((org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                (org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                (org.ccsds.moims.mo.mal.structures.NullableAttributeList) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.NullableAttributeList()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.mc.parameter.ParameterServiceInfo._ENABLEREPORTING_OP_NUMBER:
            enableReporting((org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                (org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.mc.parameter.ParameterServiceInfo._DISABLEREPORTING_OP_NUMBER:
            disableReporting((org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                (org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.mc.parameter.ParameterServiceInfo._SETREPORTINGPERIOD_OP_NUMBER:
            setReportingPeriod((org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                (org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                (org.ccsds.moims.mo.mal.structures.Duration) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.Duration()),
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
          case org.ccsds.moims.mo.mc.parameter.ParameterServiceInfo._GETVALUE_OP_NUMBER:
            interaction.sendResponse(getValue((org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                (org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                interaction));
            break;
          case org.ccsds.moims.mo.mc.parameter.ParameterServiceInfo._GETREPORTINGCONFIGURATION_OP_NUMBER:
            interaction.sendResponse(getReportingConfiguration((org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                (org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
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
