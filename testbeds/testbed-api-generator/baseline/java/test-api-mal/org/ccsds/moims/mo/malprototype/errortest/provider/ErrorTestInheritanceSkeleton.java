package org.ccsds.moims.mo.malprototype.errortest.provider;

import java.io.IOException;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
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
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.malprototype.errortest.ErrorTestHelper;
import org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo;

/**
 * Provider Inheritance skeleton for ErrorTestInheritanceSkeleton service.
 */
public abstract class ErrorTestInheritanceSkeleton implements MALInteractionHandler, ErrorTestSkeleton, ErrorTestHandler {

    /**
     * The providerSet field.
     */
    private MALProviderSet providerSet = new MALProviderSet(ErrorTestHelper.ERRORTEST_SERVICE);

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
    public void setSkeleton(ErrorTestSkeleton skeleton) {
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
        switch (opNumber) {
          default:
            interaction.sendError(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new MALInteractionException(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
    }

    @Override
    public void handleRequest(MALRequest interaction,
            MALMessageBody body) throws MALException, MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        switch (opNumber) {
          case ErrorTestServiceInfo._TESTDELIVERYFAILED_OP_NUMBER:
            interaction.sendResponse(testDeliveryFailed((Element) body.getBodyElement(0, null),
                interaction));
            break;
          case ErrorTestServiceInfo._TESTDELIVERYTIMEDOUT_OP_NUMBER:
            interaction.sendResponse(testDeliveryTimedout((Element) body.getBodyElement(0, null),
                interaction));
            break;
          case ErrorTestServiceInfo._TESTDELIVERYDELAYED_OP_NUMBER:
            interaction.sendResponse(testDeliveryDelayed((Element) body.getBodyElement(0, null),
                interaction));
            break;
          case ErrorTestServiceInfo._TESTDESTINATIONUNKNOWN_OP_NUMBER:
            interaction.sendResponse(testDestinationUnknown((Element) body.getBodyElement(0, null),
                interaction));
            break;
          case ErrorTestServiceInfo._TESTDESTINATIONTRANSIENT_OP_NUMBER:
            interaction.sendResponse(testDestinationTransient((Element) body.getBodyElement(0, null),
                interaction));
            break;
          case ErrorTestServiceInfo._TESTDESTINATIONLOST_OP_NUMBER:
            interaction.sendResponse(testDestinationLost((Element) body.getBodyElement(0, null),
                interaction));
            break;
          case ErrorTestServiceInfo._TESTENCRYPTIONFAIL_OP_NUMBER:
            interaction.sendResponse(testEncryptionFail((Element) body.getBodyElement(0, null),
                interaction));
            break;
          case ErrorTestServiceInfo._TESTUNSUPPORTEDAREA_OP_NUMBER:
            interaction.sendResponse(testUnsupportedArea((Element) body.getBodyElement(0, null),
                interaction));
            break;
          case ErrorTestServiceInfo._TESTUNSUPPORTEDOPERATION_OP_NUMBER:
            interaction.sendResponse(testUnsupportedOperation((Element) body.getBodyElement(0, null),
                interaction));
            break;
          case ErrorTestServiceInfo._TESTUNSUPPORTEDAREAVERSION_OP_NUMBER:
            interaction.sendResponse(testUnsupportedAreaVersion((Element) body.getBodyElement(0, null),
                interaction));
            break;
          case ErrorTestServiceInfo._TESTBADENCODING_OP_NUMBER:
            interaction.sendResponse(testBadEncoding((Element) body.getBodyElement(0, null),
                interaction));
            break;
          case ErrorTestServiceInfo._TESTUNKNOWN_OP_NUMBER:
            interaction.sendResponse(testUnknown((Element) body.getBodyElement(0, null),
                interaction));
            break;
          case ErrorTestServiceInfo._TESTAUTHENTICATIONFAILURE_OP_NUMBER:
            interaction.sendResponse(testAuthenticationFailure((Element) body.getBodyElement(0, null),
                interaction));
            break;
          case ErrorTestServiceInfo._TESTAUTHORIZATIONFAILURE_OP_NUMBER:
            interaction.sendResponse(testAuthorizationFailure((Element) body.getBodyElement(0, null),
                interaction));
            break;
          case ErrorTestServiceInfo._TESTUNSUPPORTEDSERVICE_OP_NUMBER:
            interaction.sendResponse(testUnsupportedService((Element) body.getBodyElement(0, null),
                interaction));
            break;
          default:
            interaction.sendError(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new MALInteractionException(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
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
