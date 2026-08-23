package org.ccsds.moims.mo.mps.planedit.provider;

/**
 * Provider Inheritance skeleton for PlanEditInheritanceSkeleton service.
 */
public abstract class PlanEditInheritanceSkeleton implements org.ccsds.moims.mo.mal.provider.MALInteractionHandler, org.ccsds.moims.mo.mps.planedit.provider.PlanEditSkeleton, org.ccsds.moims.mo.mps.planedit.provider.PlanEditHandler {

    /**
     * The providerSet field.
     */
    private org.ccsds.moims.mo.mal.provider.MALProviderSet providerSet = new org.ccsds.moims.mo.mal.provider.MALProviderSet(org.ccsds.moims.mo.mps.planedit.PlanEditHelper.PLANEDIT_SERVICE);

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
    public void setSkeleton(org.ccsds.moims.mo.mps.planedit.provider.PlanEditSkeleton skeleton) {
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
        try {
        switch (opNumber) {
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._UPDATEPLANSTATUS_OP_NUMBER:
            updatePlanStatus((org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>()),
                (org.ccsds.moims.mo.mps.structures.PlanStatusEnum) body.getBodyElement(1, org.ccsds.moims.mo.mps.structures.PlanStatusEnum.DRAFT),
                (body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE))).getBooleanValue(),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._DELETEACTIVITY_OP_NUMBER:
            deleteActivity((org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>()),
                (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityInstance>) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityInstance>()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._DELETEEVENT_OP_NUMBER:
            deleteEvent((org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>()),
                (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventInstance>) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventInstance>()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._UPDATEACTIVITY_OP_NUMBER:
            updateActivity((org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>()),
                (org.ccsds.moims.mo.mps.structures.ActivityUpdate) body.getBodyElement(1, new org.ccsds.moims.mo.mps.structures.ActivityUpdate()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._UPDATEEVENT_OP_NUMBER:
            updateEvent((org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>()),
                (org.ccsds.moims.mo.mps.structures.EventUpdate) body.getBodyElement(1, new org.ccsds.moims.mo.mps.structures.EventUpdate()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._UPDATERESOURCEVALUE_OP_NUMBER:
            updateResourceValue((org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>()),
                (org.ccsds.moims.mo.mps.structures.ResourceUpdate) body.getBodyElement(1, new org.ccsds.moims.mo.mps.structures.ResourceUpdate()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._UPDATERESOURCEPROFILE_OP_NUMBER:
            updateResourceProfile((org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>()),
                (org.ccsds.moims.mo.mps.structures.ResourceProfile) body.getBodyElement(1, new org.ccsds.moims.mo.mps.structures.ResourceProfile()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._APPLYTIMESHIFT_OP_NUMBER:
            applyTimeShift((org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>()),
                (org.ccsds.moims.mo.mal.structures.IdentifierList) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.IdentifierList()),
                (org.ccsds.moims.mo.mps.structures.TimeWindow) body.getBodyElement(2, new org.ccsds.moims.mo.mps.structures.TimeWindow()),
                (org.ccsds.moims.mo.mal.structures.Duration) body.getBodyElement(3, new org.ccsds.moims.mo.mal.structures.Duration()),
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
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._INSERTACTIVITY_OP_NUMBER:
            interaction.sendResponse(insertActivity((org.ccsds.moims.mo.mps.structures.InsertedActivityDetails) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.InsertedActivityDetails()),
                interaction));
            break;
          case org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo._INSERTEVENT_OP_NUMBER:
            interaction.sendResponse(insertEvent((org.ccsds.moims.mo.mps.structures.InsertedEventDetails) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.InsertedEventDetails()),
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
