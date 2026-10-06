package org.ccsds.moims.mo.malprototype2.iptest.provider;

import java.io.IOException;
import java.util.Map;
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
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.malprototype.structures.Auto;
import org.ccsds.moims.mo.malprototype.structures.TestPublishDeregister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishRegister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate;
import org.ccsds.moims.mo.malprototype2.iptest.IPTestHelper;
import org.ccsds.moims.mo.malprototype2.iptest.IPTestServiceInfo;

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
    public Monitor2Publisher createMonitor2Publisher(IdentifierList domain,
            Identifier networkZone,
            SessionType sessionType,
            Identifier sessionName,
            QoSLevel qos,
            Map qosProps,
            UInteger priority) throws MALException {
        return new Monitor2Publisher(providerSet.createPublisherSet(IPTestServiceInfo.MONITOR2_OP, domain, sessionType, sessionName, qos, qosProps, null));
    }

    @Override
    public void handleSend(MALInteraction interaction,
            MALMessageBody body) throws MALException, MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        switch (opNumber) {
          case IPTestServiceInfo._TESTOBJECTREFSEND_OP_NUMBER:
            testObjectRefSend((ObjectRef<Auto>) body.getBodyElement(0, new ObjectRef<Auto>()),
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
        switch (opNumber) {
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
          case IPTestServiceInfo._TESTOBJECTREFSUBMIT_OP_NUMBER:
            testObjectRefSubmit((ObjectRef<Auto>) body.getBodyElement(0, new ObjectRef<Auto>()),
                interaction);
            interaction.sendAcknowledgement();
            break;
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
