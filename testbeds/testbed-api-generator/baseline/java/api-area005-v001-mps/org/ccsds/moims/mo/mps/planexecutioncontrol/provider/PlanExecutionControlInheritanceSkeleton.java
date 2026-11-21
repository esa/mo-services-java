package org.ccsds.moims.mo.mps.planexecutioncontrol.provider;

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
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlHelper;
import org.ccsds.moims.mo.mps.planexecutioncontrol.PlanExecutionControlServiceInfo;
import org.ccsds.moims.mo.mps.structures.Plan;

/**
 * Provider Inheritance skeleton for PlanExecutionControlInheritanceSkeleton
 * service.
 */
public abstract class PlanExecutionControlInheritanceSkeleton implements MALInteractionHandler, PlanExecutionControlSkeleton, PlanExecutionControlHandler {

    /**
     * The providerSet field.
     */
    private MALProviderSet providerSet = new MALProviderSet(PlanExecutionControlHelper.PLANEXECUTIONCONTROL_SERVICE);

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
    public void setSkeleton(PlanExecutionControlSkeleton skeleton) {
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
    public MonitorPlanExecutionPublisher createMonitorPlanExecutionPublisher(IdentifierList domain,
            Identifier networkZone,
            SessionType sessionType,
            Identifier sessionName,
            QoSLevel qos,
            Map qosProps,
            UInteger priority) throws MALException {
        return new MonitorPlanExecutionPublisher(providerSet.createPublisherSet(PlanExecutionControlServiceInfo.MONITORPLANEXECUTION_OP, domain, sessionType, sessionName, qos, qosProps, null));
    }

    @Override
    public MonitorPlanExecutionDetailPublisher createMonitorPlanExecutionDetailPublisher(IdentifierList domain,
            Identifier networkZone,
            SessionType sessionType,
            Identifier sessionName,
            QoSLevel qos,
            Map qosProps,
            UInteger priority) throws MALException {
        return new MonitorPlanExecutionDetailPublisher(providerSet.createPublisherSet(PlanExecutionControlServiceInfo.MONITORPLANEXECUTIONDETAIL_OP, domain, sessionType, sessionName, qos, qosProps, null));
    }

    @Override
    public MonitorSubPlanExecutionPublisher createMonitorSubPlanExecutionPublisher(IdentifierList domain,
            Identifier networkZone,
            SessionType sessionType,
            Identifier sessionName,
            QoSLevel qos,
            Map qosProps,
            UInteger priority) throws MALException {
        return new MonitorSubPlanExecutionPublisher(providerSet.createPublisherSet(PlanExecutionControlServiceInfo.MONITORSUBPLANEXECUTION_OP, domain, sessionType, sessionName, qos, qosProps, null));
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
          case PlanExecutionControlServiceInfo._SUBMITPLAN_OP_NUMBER:
            submitPlan((Plan) body.getBodyElement(0, new Plan()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case PlanExecutionControlServiceInfo._REVOKEPLAN_OP_NUMBER:
            revokePlan((ObjectRef<Plan>) body.getBodyElement(0, new ObjectRef<Plan>()),
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
          case PlanExecutionControlServiceInfo._GETPLANSTATUS_OP_NUMBER:
            interaction.sendResponse(getPlanStatus((ObjectRefList) body.getBodyElement(0, new ObjectRefList()),
                interaction));
            break;
          case PlanExecutionControlServiceInfo._ACTIVATEPLAN_OP_NUMBER:
            interaction.sendResponse(activatePlan((ObjectRefList) body.getBodyElement(0, new ObjectRefList()),
                interaction));
            break;
          case PlanExecutionControlServiceInfo._DEACTIVATEPLAN_OP_NUMBER:
            interaction.sendResponse(deactivatePlan((ObjectRefList) body.getBodyElement(0, new ObjectRefList()),
                (Identifier) body.getBodyElement(1, new Identifier()),
                interaction));
            break;
          case PlanExecutionControlServiceInfo._ACTIVATESUBPLAN_OP_NUMBER:
            interaction.sendResponse(activateSubPlan((IdentifierList) body.getBodyElement(0, new IdentifierList()),
                interaction));
            break;
          case PlanExecutionControlServiceInfo._DEACTIVATESUBPLAN_OP_NUMBER:
            interaction.sendResponse(deactivateSubPlan((IdentifierList) body.getBodyElement(0, new IdentifierList()),
                (body.getBodyElement(1, new Union("")) == null) ? null : ((Union) body.getBodyElement(1, new Union(""))).getStringValue(),
                interaction));
            break;
          case PlanExecutionControlServiceInfo._GETSUBPLANSTATUS_OP_NUMBER:
            interaction.sendResponse(getSubPlanStatus((IdentifierList) body.getBodyElement(0, new IdentifierList()),
                interaction));
            break;
          case PlanExecutionControlServiceInfo._SUSPENDACTIVITY_OP_NUMBER:
            interaction.sendResponse(suspendActivity((ObjectRefList) body.getBodyElement(0, new ObjectRefList()),
                (ObjectRefList) body.getBodyElement(1, new ObjectRefList()),
                (StringList) body.getBodyElement(2, new StringList()),
                (body.getBodyElement(3, new Union("")) == null) ? null : ((Union) body.getBodyElement(3, new Union(""))).getStringValue(),
                interaction));
            break;
          case PlanExecutionControlServiceInfo._RESUMEACTIVITY_OP_NUMBER:
            interaction.sendResponse(resumeActivity((ObjectRefList) body.getBodyElement(0, new ObjectRefList()),
                (ObjectRefList) body.getBodyElement(1, new ObjectRefList()),
                (StringList) body.getBodyElement(2, new StringList()),
                interaction));
            break;
          case PlanExecutionControlServiceInfo._GETACTIVITYSTATUS_OP_NUMBER:
            interaction.sendResponse(getActivityStatus((ObjectRefList) body.getBodyElement(0, new ObjectRefList()),
                (ObjectRefList) body.getBodyElement(1, new ObjectRefList()),
                (IdentifierList) body.getBodyElement(2, new IdentifierList()),
                (StringList) body.getBodyElement(3, new StringList()),
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
