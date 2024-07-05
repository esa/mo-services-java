package esa.mo.com.test.event;

/**
 * Provider Delegation skeleton for EventTestDelegationSkeleton service. This
 * class was directly copied from the old auto-generated code. The COM services
 * no longer exist, therefore, this class is on life-support. The future testbeds
 * will not follow the "DelegationSkeleton" approach.
 */
@Deprecated
public class EventTestDelegationSkeleton implements org.ccsds.moims.mo.mal.provider.MALInteractionHandler, org.ccsds.moims.mo.comprototype.eventtest.provider.EventTestSkeleton {

    private org.ccsds.moims.mo.mal.provider.MALProviderSet providerSet = new org.ccsds.moims.mo.mal.provider.MALProviderSet(org.ccsds.moims.mo.comprototype.eventtest.EventTestHelper.EVENTTEST_SERVICE);

    private org.ccsds.moims.mo.comprototype.eventtest.provider.EventTestHandler delegate;

    /**
     * Creates a delegation skeleton using the supplied delegate.
     *
     * @param delegate The interaction handler used for delegation.
     */
    public EventTestDelegationSkeleton(org.ccsds.moims.mo.comprototype.eventtest.provider.EventTestHandler delegate) {
        this.delegate = delegate;
        delegate.setSkeleton(this);
    }

    /**
     * Adds the supplied MAL provider to the internal list of providers used for
     * PubSub.
     *
     * @param provider The provider to be added.
     * @throws org.ccsds.moims.mo.mal.MALException If an error is detected.
     */
    public void malInitialize(org.ccsds.moims.mo.mal.provider.MALProvider provider) throws org.ccsds.moims.mo.mal.MALException {
        providerSet.addProvider(provider);
    }

    /**
     * Removes the supplied MAL provider from the internal list of providers
     * used for PubSub.
     *
     * @param provider The provider to be added.
     * @throws org.ccsds.moims.mo.mal.MALException If an error is detected.
     */
    public void malFinalize(org.ccsds.moims.mo.mal.provider.MALProvider provider) throws org.ccsds.moims.mo.mal.MALException {
        providerSet.removeProvider(provider);
    }

    /**
     * Called by the provider MAL layer on reception of a message to handle the
     * interaction.
     *
     * @param interaction The interaction object.
     * @param body The message body.
     * @throws org.ccsds.moims.mo.mal.MALException if there is a internal error.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a
     * operation interaction error.
     */
    public void handleSend(org.ccsds.moims.mo.mal.provider.MALInteraction interaction,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        switch (opNumber) {
            default:
                throw new org.ccsds.moims.mo.mal.MALInteractionException(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                        org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
    }

    /**
     * Called by the provider MAL layer on reception of a message to handle the
     * interaction.
     *
     * @param interaction The interaction object.
     * @param body The message body.
     * @throws org.ccsds.moims.mo.mal.MALException if there is a internal error.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a
     * operation interaction error.
     */
    public void handleSubmit(org.ccsds.moims.mo.mal.provider.MALSubmit interaction,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        switch (opNumber) {
            case org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo._RESETTEST_OP_NUMBER:
                delegate.resetTest((body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union("")) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(""))).getStringValue(),
                        interaction);
                interaction.sendAcknowledgement();
                break;
            case org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo._DELETEINSTANCE_OP_NUMBER:
                delegate.deleteInstance((body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Short.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Short.MAX_VALUE))).getShortValue(),
                        (body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.Union("")) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.Union(""))).getStringValue(),
                        (body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE))).getLongValue(),
                        interaction);
                interaction.sendAcknowledgement();
                break;
            case org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo._UPDATEINSTANCE_OP_NUMBER:
                delegate.updateInstance((body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE))).getLongValue(),
                        (org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnum) body.getBodyElement(1, org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnum.FIRST),
                        (org.ccsds.moims.mo.mal.structures.Duration) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.Duration()),
                        (org.ccsds.moims.mo.mal.structures.ShortList) body.getBodyElement(3, new org.ccsds.moims.mo.mal.structures.ShortList()),
                        interaction);
                interaction.sendAcknowledgement();
                break;
            case org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo._UPDATEINSTANCECOMPOSITE_OP_NUMBER:
                delegate.updateInstanceComposite((body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE))).getLongValue(),
                        (org.ccsds.moims.mo.mal.structures.UOctet) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UOctet()),
                        (body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.Union(Byte.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.Union(Byte.MAX_VALUE))).getOctetValue(),
                        (body.getBodyElement(3, new org.ccsds.moims.mo.mal.structures.Union(Double.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(3, new org.ccsds.moims.mo.mal.structures.Union(Double.MAX_VALUE))).getDoubleValue(),
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

    /**
     * Called by the provider MAL layer on reception of a message to handle the
     * interaction.
     *
     * @param interaction The interaction object.
     * @param body The message body.
     * @throws org.ccsds.moims.mo.mal.MALException if there is a internal error.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a
     * operation interaction error.
     */
    public void handleRequest(org.ccsds.moims.mo.mal.provider.MALRequest interaction,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        switch (opNumber) {
            case org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo._CREATEINSTANCE_OP_NUMBER:
                Long createinstanceRt = delegate.createinstance((body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Short.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Short.MAX_VALUE))).getShortValue(),
                        (body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.Union("")) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.Union(""))).getStringValue(),
                        (body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.Union("")) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.Union(""))).getStringValue(),
                        (body.getBodyElement(3, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(3, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE))).getLongValue(),
                        interaction);
                interaction.sendResponse((createinstanceRt == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(createinstanceRt));
                break;
            default:
                interaction.sendError(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                        org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
                throw new org.ccsds.moims.mo.mal.MALInteractionException(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                        org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
    }

    /**
     * Called by the provider MAL layer on reception of a message to handle the
     * interaction.
     *
     * @param interaction The interaction object.
     * @param body The message body.
     * @throws org.ccsds.moims.mo.mal.MALException if there is a internal error.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a
     * operation interaction error.
     */
    public void handleInvoke(org.ccsds.moims.mo.mal.provider.MALInvoke interaction,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        switch (opNumber) {
            default:
                interaction.sendError(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                        org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
                throw new org.ccsds.moims.mo.mal.MALInteractionException(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                        org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
    }

    /**
     * Called by the provider MAL layer on reception of a message to handle the
     * interaction.
     *
     * @param interaction The interaction object.
     * @param body The message body.
     * @throws org.ccsds.moims.mo.mal.MALException if there is a internal error.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a
     * operation interaction error.
     */
    public void handleProgress(org.ccsds.moims.mo.mal.provider.MALProgress interaction,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
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
