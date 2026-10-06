package org.ccsds.moims.mo.mps.planedit.provider;

import java.io.IOException;
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
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mps.planedit.PlanEditHelper;
import org.ccsds.moims.mo.mps.planedit.PlanEditServiceInfo;
import org.ccsds.moims.mo.mps.structures.ActivityInstance;
import org.ccsds.moims.mo.mps.structures.ActivityUpdate;
import org.ccsds.moims.mo.mps.structures.EventInstance;
import org.ccsds.moims.mo.mps.structures.EventUpdate;
import org.ccsds.moims.mo.mps.structures.InsertedActivityDetails;
import org.ccsds.moims.mo.mps.structures.InsertedEventDetails;
import org.ccsds.moims.mo.mps.structures.Plan;
import org.ccsds.moims.mo.mps.structures.PlanStatusEnum;
import org.ccsds.moims.mo.mps.structures.ResourceProfile;
import org.ccsds.moims.mo.mps.structures.ResourceUpdate;
import org.ccsds.moims.mo.mps.structures.TimeWindow;

/**
 * Provider Inheritance skeleton for PlanEditInheritanceSkeleton service.
 */
public abstract class PlanEditInheritanceSkeleton implements MALInteractionHandler, PlanEditSkeleton, PlanEditHandler {

    /**
     * The providerSet field.
     */
    private MALProviderSet providerSet = new MALProviderSet(PlanEditHelper.PLANEDIT_SERVICE);

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
    public void setSkeleton(PlanEditSkeleton skeleton) {
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
        try {
        switch (opNumber) {
          case PlanEditServiceInfo._UPDATEPLANSTATUS_OP_NUMBER:
            updatePlanStatus((ObjectRef<Plan>) body.getBodyElement(0, new ObjectRef<Plan>()),
                (PlanStatusEnum) body.getBodyElement(1, PlanStatusEnum.DRAFT),
                (body.getBodyElement(2, new Union(Boolean.FALSE)) == null) ? null : ((Union) body.getBodyElement(2, new Union(Boolean.FALSE))).getBooleanValue(),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case PlanEditServiceInfo._DELETEACTIVITY_OP_NUMBER:
            deleteActivity((ObjectRef<Plan>) body.getBodyElement(0, new ObjectRef<Plan>()),
                (ObjectRef<ActivityInstance>) body.getBodyElement(1, new ObjectRef<ActivityInstance>()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case PlanEditServiceInfo._DELETEEVENT_OP_NUMBER:
            deleteEvent((ObjectRef<Plan>) body.getBodyElement(0, new ObjectRef<Plan>()),
                (ObjectRef<EventInstance>) body.getBodyElement(1, new ObjectRef<EventInstance>()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case PlanEditServiceInfo._UPDATEACTIVITY_OP_NUMBER:
            updateActivity((ObjectRef<Plan>) body.getBodyElement(0, new ObjectRef<Plan>()),
                (ActivityUpdate) body.getBodyElement(1, new ActivityUpdate()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case PlanEditServiceInfo._UPDATEEVENT_OP_NUMBER:
            updateEvent((ObjectRef<Plan>) body.getBodyElement(0, new ObjectRef<Plan>()),
                (EventUpdate) body.getBodyElement(1, new EventUpdate()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case PlanEditServiceInfo._UPDATERESOURCEVALUE_OP_NUMBER:
            updateResourceValue((ObjectRef<Plan>) body.getBodyElement(0, new ObjectRef<Plan>()),
                (ResourceUpdate) body.getBodyElement(1, new ResourceUpdate()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case PlanEditServiceInfo._UPDATERESOURCEPROFILE_OP_NUMBER:
            updateResourceProfile((ObjectRef<Plan>) body.getBodyElement(0, new ObjectRef<Plan>()),
                (ResourceProfile) body.getBodyElement(1, new ResourceProfile()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case PlanEditServiceInfo._APPLYTIMESHIFT_OP_NUMBER:
            applyTimeShift((ObjectRef<Plan>) body.getBodyElement(0, new ObjectRef<Plan>()),
                (IdentifierList) body.getBodyElement(1, new IdentifierList()),
                (TimeWindow) body.getBodyElement(2, new TimeWindow()),
                (Duration) body.getBodyElement(3, new Duration()),
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
          case PlanEditServiceInfo._INSERTACTIVITY_OP_NUMBER:
            interaction.sendResponse(insertActivity((InsertedActivityDetails) body.getBodyElement(0, new InsertedActivityDetails()),
                interaction));
            break;
          case PlanEditServiceInfo._INSERTEVENT_OP_NUMBER:
            interaction.sendResponse(insertEvent((InsertedEventDetails) body.getBodyElement(0, new InsertedEventDetails()),
                interaction));
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
