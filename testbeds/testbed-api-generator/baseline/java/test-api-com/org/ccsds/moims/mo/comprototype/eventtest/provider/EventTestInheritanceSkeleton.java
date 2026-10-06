package org.ccsds.moims.mo.comprototype.eventtest.provider;

import java.io.IOException;
import org.ccsds.moims.mo.comprototype.eventtest.EventTestHelper;
import org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo;
import org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnum;
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
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.ShortList;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;

/**
 * Provider Inheritance skeleton for EventTestInheritanceSkeleton service.
 */
public abstract class EventTestInheritanceSkeleton implements MALInteractionHandler, EventTestSkeleton, EventTestHandler {

    /**
     * The providerSet field.
     */
    private MALProviderSet providerSet = new MALProviderSet(EventTestHelper.EVENTTEST_SERVICE);

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
    public void setSkeleton(EventTestSkeleton skeleton) {
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
          case EventTestServiceInfo._RESETTEST_OP_NUMBER:
            resetTest((body.getBodyElement(0, new Union("")) == null) ? null : ((Union) body.getBodyElement(0, new Union(""))).getStringValue(),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case EventTestServiceInfo._DELETEINSTANCE_OP_NUMBER:
            deleteInstance((body.getBodyElement(0, new Union(Short.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Short.MAX_VALUE))).getShortValue(),
                (body.getBodyElement(1, new Union("")) == null) ? null : ((Union) body.getBodyElement(1, new Union(""))).getStringValue(),
                (body.getBodyElement(2, new Union(Long.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(2, new Union(Long.MAX_VALUE))).getLongValue(),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case EventTestServiceInfo._UPDATEINSTANCE_OP_NUMBER:
            updateInstance((body.getBodyElement(0, new Union(Long.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Long.MAX_VALUE))).getLongValue(),
                (BasicEnum) body.getBodyElement(1, BasicEnum.FIRST),
                (Duration) body.getBodyElement(2, new Duration()),
                (ShortList) body.getBodyElement(3, new ShortList()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case EventTestServiceInfo._UPDATEINSTANCECOMPOSITE_OP_NUMBER:
            updateInstanceComposite((body.getBodyElement(0, new Union(Long.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Long.MAX_VALUE))).getLongValue(),
                (UOctet) body.getBodyElement(1, new UOctet()),
                (body.getBodyElement(2, new Union(Byte.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(2, new Union(Byte.MAX_VALUE))).getOctetValue(),
                (body.getBodyElement(3, new Union(Double.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(3, new Union(Double.MAX_VALUE))).getDoubleValue(),
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
          case EventTestServiceInfo._CREATEINSTANCE_OP_NUMBER:
            Long createinstanceRt = createinstance((body.getBodyElement(0, new Union(Short.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Short.MAX_VALUE))).getShortValue(),
                (body.getBodyElement(1, new Union("")) == null) ? null : ((Union) body.getBodyElement(1, new Union(""))).getStringValue(),
                (body.getBodyElement(2, new Union("")) == null) ? null : ((Union) body.getBodyElement(2, new Union(""))).getStringValue(),
                (body.getBodyElement(3, new Union(Long.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(3, new Union(Long.MAX_VALUE))).getLongValue(),
                interaction);
            interaction.sendResponse((createinstanceRt == null) ? null : new Union(createinstanceRt));
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
