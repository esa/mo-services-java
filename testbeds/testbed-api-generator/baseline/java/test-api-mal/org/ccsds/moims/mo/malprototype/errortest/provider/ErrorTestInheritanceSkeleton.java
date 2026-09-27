package org.ccsds.moims.mo.malprototype.errortest.provider;

/**
 * Provider Inheritance skeleton for ErrorTestInheritanceSkeleton service.
 */
public abstract class ErrorTestInheritanceSkeleton implements org.ccsds.moims.mo.mal.provider.MALInteractionHandler, org.ccsds.moims.mo.malprototype.errortest.provider.ErrorTestSkeleton, org.ccsds.moims.mo.malprototype.errortest.provider.ErrorTestHandler {

    /**
     * The providerSet field.
     */
    private org.ccsds.moims.mo.mal.provider.MALProviderSet providerSet = new org.ccsds.moims.mo.mal.provider.MALProviderSet(org.ccsds.moims.mo.malprototype.errortest.ErrorTestHelper.ERRORTEST_SERVICE);

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
    public void setSkeleton(org.ccsds.moims.mo.malprototype.errortest.provider.ErrorTestSkeleton skeleton) {
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
        switch (opNumber) {
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
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTDELIVERYFAILED_OP_NUMBER:
            interaction.sendResponse(testDeliveryFailed((org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTDELIVERYTIMEDOUT_OP_NUMBER:
            interaction.sendResponse(testDeliveryTimedout((org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTDELIVERYDELAYED_OP_NUMBER:
            interaction.sendResponse(testDeliveryDelayed((org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTDESTINATIONUNKNOWN_OP_NUMBER:
            interaction.sendResponse(testDestinationUnknown((org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTDESTINATIONTRANSIENT_OP_NUMBER:
            interaction.sendResponse(testDestinationTransient((org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTDESTINATIONLOST_OP_NUMBER:
            interaction.sendResponse(testDestinationLost((org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTENCRYPTIONFAIL_OP_NUMBER:
            interaction.sendResponse(testEncryptionFail((org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTUNSUPPORTEDAREA_OP_NUMBER:
            interaction.sendResponse(testUnsupportedArea((org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTUNSUPPORTEDOPERATION_OP_NUMBER:
            interaction.sendResponse(testUnsupportedOperation((org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTUNSUPPORTEDAREAVERSION_OP_NUMBER:
            interaction.sendResponse(testUnsupportedAreaVersion((org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTBADENCODING_OP_NUMBER:
            interaction.sendResponse(testBadEncoding((org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTUNKNOWN_OP_NUMBER:
            interaction.sendResponse(testUnknown((org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTAUTHENTICATIONFAILURE_OP_NUMBER:
            interaction.sendResponse(testAuthenticationFailure((org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTAUTHORIZATIONFAILURE_OP_NUMBER:
            interaction.sendResponse(testAuthorizationFailure((org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo._TESTUNSUPPORTEDSERVICE_OP_NUMBER:
            interaction.sendResponse(testUnsupportedService((org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null),
                interaction));
            break;
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
