package org.ccsds.moims.mo.malprototype.iptest.provider;

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
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.malprototype.iptest.IPTestHelper;
import org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo;
import org.ccsds.moims.mo.malprototype.iptest.body.RequestMultiResponse;
import org.ccsds.moims.mo.malprototype.structures.IPTestDefinition;
import org.ccsds.moims.mo.malprototype.structures.TestPublishDeregister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishRegister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate;

/**
 * Provider Inheritance skeleton for IPTestInheritanceSkeleton service.
 */
public abstract class IPTestInheritanceSkeleton implements MALInteractionHandler, IPTestSkeleton, IPTestHandler {

    /**
     * The providerSet field.
     */
    private MALProviderSet providerSet = new MALProviderSet(IPTestHelper.IPTEST_SERVICE);

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
    public void setSkeleton(IPTestSkeleton skeleton) {
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
    public MonitorPublisher createMonitorPublisher(IdentifierList domain,
            Identifier networkZone,
            SessionType sessionType,
            Identifier sessionName,
            QoSLevel qos,
            Map qosProps,
            UInteger priority) throws MALException {
        return new MonitorPublisher(providerSet.createPublisherSet(IPTestServiceInfo.MONITOR_OP, domain, sessionType, sessionName, qos, qosProps, null));
    }

    @Override
    public MonitorMultiPublisher createMonitorMultiPublisher(IdentifierList domain,
            Identifier networkZone,
            SessionType sessionType,
            Identifier sessionName,
            QoSLevel qos,
            Map qosProps,
            UInteger priority) throws MALException {
        return new MonitorMultiPublisher(providerSet.createPublisherSet(IPTestServiceInfo.MONITORMULTI_OP, domain, sessionType, sessionName, qos, qosProps, null));
    }

    @Override
    public void handleSend(MALInteraction interaction,
            MALMessageBody body) throws MALException, MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        switch (opNumber) {
          case IPTestServiceInfo._SEND_OP_NUMBER:
            send((IPTestDefinition) body.getBodyElement(0, new IPTestDefinition()),
                interaction);
            break;
          case IPTestServiceInfo._SENDMULTI_OP_NUMBER:
            sendMulti((IPTestDefinition) body.getBodyElement(0, new IPTestDefinition()),
                (Element) body.getBodyElement(1, null),
                interaction);
            break;
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
          case IPTestServiceInfo._TESTSUBMIT_OP_NUMBER:
            testSubmit((IPTestDefinition) body.getBodyElement(0, new IPTestDefinition()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case IPTestServiceInfo._PUBLISHUPDATES_OP_NUMBER:
            publishUpdates((TestPublishUpdate) body.getBodyElement(0, new TestPublishUpdate()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case IPTestServiceInfo._PUBLISHREGISTER_OP_NUMBER:
            publishRegister((TestPublishRegister) body.getBodyElement(0, new TestPublishRegister()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case IPTestServiceInfo._PUBLISHDEREGISTER_OP_NUMBER:
            publishDeregister((TestPublishDeregister) body.getBodyElement(0, new TestPublishDeregister()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case IPTestServiceInfo._TESTMULTIPLENOTIFY_OP_NUMBER:
            testMultipleNotify((TestPublishUpdate) body.getBodyElement(0, new TestPublishUpdate()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case IPTestServiceInfo._SUBMITMULTI_OP_NUMBER:
            submitMulti((IPTestDefinition) body.getBodyElement(0, new IPTestDefinition()),
                (Element) body.getBodyElement(1, null),
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
          case IPTestServiceInfo._REQUEST_OP_NUMBER:
            String requestRt = request((IPTestDefinition) body.getBodyElement(0, new IPTestDefinition()),
                interaction);
            interaction.sendResponse((requestRt == null) ? null : new Union(requestRt));
            break;
          case IPTestServiceInfo._GETRESULT_OP_NUMBER:
            interaction.sendResponse(getResult((Element) body.getBodyElement(0, null),
                interaction));
            break;
          case IPTestServiceInfo._REQUESTMULTI_OP_NUMBER:
            RequestMultiResponse requestMultiRt = requestMulti((IPTestDefinition) body.getBodyElement(0, new IPTestDefinition()),
                (Element) body.getBodyElement(1, null),
                interaction);
            interaction.sendResponse(
                    (requestMultiRt.getOutput1() == null) ? null : new Union(requestMultiRt.getOutput1()),
                    requestMultiRt.getOutput2()
            );
            break;
          case IPTestServiceInfo._TESTREQUESTEMPTYBODY_OP_NUMBER:
            testRequestEmptyBody((IPTestDefinition) body.getBodyElement(0, new IPTestDefinition()),
                interaction);
            interaction.sendResponse();
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
          case IPTestServiceInfo._INVOKE_OP_NUMBER:
            invoke((IPTestDefinition) body.getBodyElement(0, new IPTestDefinition()),
                new InvokeInteraction(interaction));
            break;
          case IPTestServiceInfo._INVOKEMULTI_OP_NUMBER:
            invokeMulti((IPTestDefinition) body.getBodyElement(0, new IPTestDefinition()),
                (Element) body.getBodyElement(1, null),
                new InvokeMultiInteraction(interaction));
            break;
          case IPTestServiceInfo._TESTINVOKEEMPTYBODY_OP_NUMBER:
            testInvokeEmptyBody((IPTestDefinition) body.getBodyElement(0, new IPTestDefinition()),
                new TestInvokeEmptyBodyInteraction(interaction));
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
        try {
        switch (opNumber) {
          case IPTestServiceInfo._PROGRESS_OP_NUMBER:
            progress((IPTestDefinition) body.getBodyElement(0, new IPTestDefinition()),
                new ProgressInteraction(interaction));
            break;
          case IPTestServiceInfo._PROGRESSMULTI_OP_NUMBER:
            progressMulti((IPTestDefinition) body.getBodyElement(0, new IPTestDefinition()),
                (Element) body.getBodyElement(1, null),
                new ProgressMultiInteraction(interaction));
            break;
          case IPTestServiceInfo._TESTPROGRESSEMPTYBODY_OP_NUMBER:
            testProgressEmptyBody((IPTestDefinition) body.getBodyElement(0, new IPTestDefinition()),
                new TestProgressEmptyBodyInteraction(interaction));
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

}
