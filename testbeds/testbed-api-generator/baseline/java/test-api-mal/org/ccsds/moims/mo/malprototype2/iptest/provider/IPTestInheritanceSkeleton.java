package org.ccsds.moims.mo.malprototype2.iptest.provider;

/**
 * Provider Inheritance skeleton for IPTestInheritanceSkeleton service.
 */
public abstract class IPTestInheritanceSkeleton implements org.ccsds.moims.mo.mal.provider.MALInteractionHandler, org.ccsds.moims.mo.malprototype2.iptest.provider.IPTestSkeleton, org.ccsds.moims.mo.malprototype2.iptest.provider.IPTestHandler {

    /**
     * The providerSet field.
     */
    private org.ccsds.moims.mo.mal.provider.MALProviderSet providerSet = new org.ccsds.moims.mo.mal.provider.MALProviderSet(org.ccsds.moims.mo.malprototype2.iptest.IPTestHelper.IPTEST_SERVICE);

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
    public void setSkeleton(org.ccsds.moims.mo.malprototype2.iptest.provider.IPTestSkeleton skeleton) {
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
    public org.ccsds.moims.mo.malprototype2.iptest.provider.MonitorPublisher createMonitorPublisher(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.Identifier networkZone,
            org.ccsds.moims.mo.mal.structures.SessionType sessionType,
            org.ccsds.moims.mo.mal.structures.Identifier sessionName,
            org.ccsds.moims.mo.mal.structures.QoSLevel qos,
            java.util.Map qosProps,
            org.ccsds.moims.mo.mal.structures.UInteger priority) throws org.ccsds.moims.mo.mal.MALException {
        return new org.ccsds.moims.mo.malprototype2.iptest.provider.MonitorPublisher(providerSet.createPublisherSet(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.MONITOR_OP, domain, sessionType, sessionName, qos, qosProps, null));
    }

    @Override
    public org.ccsds.moims.mo.malprototype2.iptest.provider.Monitor2Publisher createMonitor2Publisher(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.Identifier networkZone,
            org.ccsds.moims.mo.mal.structures.SessionType sessionType,
            org.ccsds.moims.mo.mal.structures.Identifier sessionName,
            org.ccsds.moims.mo.mal.structures.QoSLevel qos,
            java.util.Map qosProps,
            org.ccsds.moims.mo.mal.structures.UInteger priority) throws org.ccsds.moims.mo.mal.MALException {
        return new org.ccsds.moims.mo.malprototype2.iptest.provider.Monitor2Publisher(providerSet.createPublisherSet(org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo.MONITOR2_OP, domain, sessionType, sessionName, qos, qosProps, null));
    }

    @Override
    public void handleSend(org.ccsds.moims.mo.mal.provider.MALInteraction interaction,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body) throws org.ccsds.moims.mo.mal.MALException, org.ccsds.moims.mo.mal.MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        switch (opNumber) {
          case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._TESTOBJECTREFSEND_OP_NUMBER:
            testObjectRefSend((org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto>) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto>()),
                interaction);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALInteractionException(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                    org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
    }

    @Override
    public void handleSubmit(org.ccsds.moims.mo.mal.provider.MALSubmit interaction,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body) throws org.ccsds.moims.mo.mal.MALException, org.ccsds.moims.mo.mal.MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        switch (opNumber) {
          case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._PUBLISHUPDATES_OP_NUMBER:
            publishUpdates((org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._PUBLISHREGISTER_OP_NUMBER:
            publishRegister((org.ccsds.moims.mo.malprototype.structures.TestPublishRegister) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.TestPublishRegister()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._PUBLISHDEREGISTER_OP_NUMBER:
            publishDeregister((org.ccsds.moims.mo.malprototype.structures.TestPublishDeregister) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.TestPublishDeregister()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._TESTMULTIPLENOTIFY_OP_NUMBER:
            testMultipleNotify((org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo._TESTOBJECTREFSUBMIT_OP_NUMBER:
            testObjectRefSubmit((org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto>) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto>()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          default:
            interaction.sendError(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                    org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new org.ccsds.moims.mo.mal.MALInteractionException(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                    org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
    }

    @Override
    public void handleRequest(org.ccsds.moims.mo.mal.provider.MALRequest interaction,
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
