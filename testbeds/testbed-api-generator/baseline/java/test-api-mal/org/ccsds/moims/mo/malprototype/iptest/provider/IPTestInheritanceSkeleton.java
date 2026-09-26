package org.ccsds.moims.mo.malprototype.iptest.provider;

/**
 * Provider Inheritance skeleton for IPTestInheritanceSkeleton service.
 */
public abstract class IPTestInheritanceSkeleton implements org.ccsds.moims.mo.mal.provider.MALInteractionHandler, org.ccsds.moims.mo.malprototype.iptest.provider.IPTestSkeleton, org.ccsds.moims.mo.malprototype.iptest.provider.IPTestHandler {

    /**
     * The providerSet field.
     */
    private org.ccsds.moims.mo.mal.provider.MALProviderSet providerSet = new org.ccsds.moims.mo.mal.provider.MALProviderSet(org.ccsds.moims.mo.malprototype.iptest.IPTestHelper.IPTEST_SERVICE);

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
    public void setSkeleton(org.ccsds.moims.mo.malprototype.iptest.provider.IPTestSkeleton skeleton) {
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
    public org.ccsds.moims.mo.malprototype.iptest.provider.MonitorPublisher createMonitorPublisher(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.Identifier networkZone,
            org.ccsds.moims.mo.mal.structures.SessionType sessionType,
            org.ccsds.moims.mo.mal.structures.Identifier sessionName,
            org.ccsds.moims.mo.mal.structures.QoSLevel qos,
            java.util.Map qosProps,
            org.ccsds.moims.mo.mal.structures.UInteger priority) throws org.ccsds.moims.mo.mal.MALException {
        return new org.ccsds.moims.mo.malprototype.iptest.provider.MonitorPublisher(providerSet.createPublisherSet(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.MONITOR_OP, domain, sessionType, sessionName, qos, qosProps, null));
    }

    @Override
    public org.ccsds.moims.mo.malprototype.iptest.provider.MonitorMultiPublisher createMonitorMultiPublisher(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.Identifier networkZone,
            org.ccsds.moims.mo.mal.structures.SessionType sessionType,
            org.ccsds.moims.mo.mal.structures.Identifier sessionName,
            org.ccsds.moims.mo.mal.structures.QoSLevel qos,
            java.util.Map qosProps,
            org.ccsds.moims.mo.mal.structures.UInteger priority) throws org.ccsds.moims.mo.mal.MALException {
        return new org.ccsds.moims.mo.malprototype.iptest.provider.MonitorMultiPublisher(providerSet.createPublisherSet(org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo.MONITORMULTI_OP, domain, sessionType, sessionName, qos, qosProps, null));
    }

    @Override
    public void handleSend(org.ccsds.moims.mo.mal.provider.MALInteraction interaction,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body) throws org.ccsds.moims.mo.mal.MALException, org.ccsds.moims.mo.mal.MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        switch (opNumber) {
          case org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo._SEND_OP_NUMBER:
            send((org.ccsds.moims.mo.malprototype.structures.IPTestDefinition) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.IPTestDefinition()),
                interaction);
            break;
          case org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo._SENDMULTI_OP_NUMBER:
            sendMulti((org.ccsds.moims.mo.malprototype.structures.IPTestDefinition) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.IPTestDefinition()),
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(1, null),
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
        try {
        switch (opNumber) {
          case org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo._TESTSUBMIT_OP_NUMBER:
            testSubmit((org.ccsds.moims.mo.malprototype.structures.IPTestDefinition) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.IPTestDefinition()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo._PUBLISHUPDATES_OP_NUMBER:
            publishUpdates((org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo._PUBLISHREGISTER_OP_NUMBER:
            publishRegister((org.ccsds.moims.mo.malprototype.structures.TestPublishRegister) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.TestPublishRegister()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo._PUBLISHDEREGISTER_OP_NUMBER:
            publishDeregister((org.ccsds.moims.mo.malprototype.structures.TestPublishDeregister) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.TestPublishDeregister()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo._TESTMULTIPLENOTIFY_OP_NUMBER:
            testMultipleNotify((org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo._SUBMITMULTI_OP_NUMBER:
            submitMulti((org.ccsds.moims.mo.malprototype.structures.IPTestDefinition) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.IPTestDefinition()),
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(1, null),
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
          case org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo._REQUEST_OP_NUMBER:
            String requestRt = request((org.ccsds.moims.mo.malprototype.structures.IPTestDefinition) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.IPTestDefinition()),
                interaction);
            interaction.sendResponse((requestRt == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(requestRt));
            break;
          case org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo._GETRESULT_OP_NUMBER:
            interaction.sendResponse(getResult((org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo._REQUESTMULTI_OP_NUMBER:
            org.ccsds.moims.mo.malprototype.iptest.body.RequestMultiResponse requestMultiRt = requestMulti((org.ccsds.moims.mo.malprototype.structures.IPTestDefinition) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.IPTestDefinition()),
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(1, null),
                interaction);
            interaction.sendResponse(
                    (requestMultiRt.getOutput1() == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(requestMultiRt.getOutput1()),
                    requestMultiRt.getOutput2()
            );
            break;
          case org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo._TESTREQUESTEMPTYBODY_OP_NUMBER:
            testRequestEmptyBody((org.ccsds.moims.mo.malprototype.structures.IPTestDefinition) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.IPTestDefinition()),
                interaction);
            interaction.sendResponse();
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
        try {
        switch (opNumber) {
          case org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo._INVOKE_OP_NUMBER:
            invoke((org.ccsds.moims.mo.malprototype.structures.IPTestDefinition) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.IPTestDefinition()),
                new InvokeInteraction(interaction));
            break;
          case org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo._INVOKEMULTI_OP_NUMBER:
            invokeMulti((org.ccsds.moims.mo.malprototype.structures.IPTestDefinition) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.IPTestDefinition()),
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(1, null),
                new InvokeMultiInteraction(interaction));
            break;
          case org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo._TESTINVOKEEMPTYBODY_OP_NUMBER:
            testInvokeEmptyBody((org.ccsds.moims.mo.malprototype.structures.IPTestDefinition) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.IPTestDefinition()),
                new TestInvokeEmptyBodyInteraction(interaction));
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
    public void handleProgress(org.ccsds.moims.mo.mal.provider.MALProgress interaction,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body) throws org.ccsds.moims.mo.mal.MALException, org.ccsds.moims.mo.mal.MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        try {
        switch (opNumber) {
          case org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo._PROGRESS_OP_NUMBER:
            progress((org.ccsds.moims.mo.malprototype.structures.IPTestDefinition) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.IPTestDefinition()),
                new ProgressInteraction(interaction));
            break;
          case org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo._PROGRESSMULTI_OP_NUMBER:
            progressMulti((org.ccsds.moims.mo.malprototype.structures.IPTestDefinition) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.IPTestDefinition()),
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(1, null),
                new ProgressMultiInteraction(interaction));
            break;
          case org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo._TESTPROGRESSEMPTYBODY_OP_NUMBER:
            testProgressEmptyBody((org.ccsds.moims.mo.malprototype.structures.IPTestDefinition) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.IPTestDefinition()),
                new TestProgressEmptyBodyInteraction(interaction));
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

}
