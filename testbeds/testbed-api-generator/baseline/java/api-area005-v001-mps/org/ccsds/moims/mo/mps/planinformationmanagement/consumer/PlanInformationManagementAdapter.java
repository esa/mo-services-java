package org.ccsds.moims.mo.mps.planinformationmanagement.consumer;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo;
import org.ccsds.moims.mo.mps.structures.ActivityDefinitionList;
import org.ccsds.moims.mo.mps.structures.DefListEntryList;
import org.ccsds.moims.mo.mps.structures.EventDefinitionList;
import org.ccsds.moims.mo.mps.structures.RequestDefinitionList;
import org.ccsds.moims.mo.mps.structures.ResourceList;

/**
 * Consumer adapter for PlanInformationManagement service.
 */
public abstract class PlanInformationManagementAdapter extends MALInteractionAdapter {

    /**
     * Called by the MAL when a PROGRESS acknowledgement is received from a provider
     * for the operation listRequestDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listRequestDefsAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update is received from a provider for
     * the operation listRequestDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param requestDefs The requestDefs field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listRequestDefsUpdateReceived(MALMessageHeader msgHeader,
            DefListEntryList requestDefs,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response is received from a provider
     * for the operation listRequestDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listRequestDefsResponseReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement error is received from
     * a provider for the operation listRequestDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listRequestDefsAckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update error is received from a provider
     * for the operation listRequestDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listRequestDefsUpdateErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response error is received from a provider
     * for the operation listRequestDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listRequestDefsResponseErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getRequestDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param definitions The definitions field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestDefsResponseReceived(MALMessageHeader msgHeader,
            RequestDefinitionList definitions,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getRequestDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestDefsErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement is received from a provider
     * for the operation listEventDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listEventDefsAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update is received from a provider for
     * the operation listEventDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param eventDefs The eventDefs field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listEventDefsUpdateReceived(MALMessageHeader msgHeader,
            DefListEntryList eventDefs,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response is received from a provider
     * for the operation listEventDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listEventDefsResponseReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement error is received from
     * a provider for the operation listEventDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listEventDefsAckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update error is received from a provider
     * for the operation listEventDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listEventDefsUpdateErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response error is received from a provider
     * for the operation listEventDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listEventDefsResponseErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getEventDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param definitions The definitions field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getEventDefsResponseReceived(MALMessageHeader msgHeader,
            EventDefinitionList definitions,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getEventDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getEventDefsErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement is received from a provider
     * for the operation listActivityDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listActivityDefsAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update is received from a provider for
     * the operation listActivityDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param activitytDefs The activitytDefs field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listActivityDefsUpdateReceived(MALMessageHeader msgHeader,
            DefListEntryList activitytDefs,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response is received from a provider
     * for the operation listActivityDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listActivityDefsResponseReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement error is received from
     * a provider for the operation listActivityDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listActivityDefsAckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update error is received from a provider
     * for the operation listActivityDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listActivityDefsUpdateErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response error is received from a provider
     * for the operation listActivityDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listActivityDefsResponseErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getActivityDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param definitions The definitions field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getActivityDefsResponseReceived(MALMessageHeader msgHeader,
            ActivityDefinitionList definitions,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getActivityDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getActivityDefsErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement is received from a provider
     * for the operation listResourceDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listResourceDefsAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update is received from a provider for
     * the operation listResourceDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param resourceDefs The resourceDefs field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listResourceDefsUpdateReceived(MALMessageHeader msgHeader,
            DefListEntryList resourceDefs,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response is received from a provider
     * for the operation listResourceDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listResourceDefsResponseReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement error is received from
     * a provider for the operation listResourceDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listResourceDefsAckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update error is received from a provider
     * for the operation listResourceDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listResourceDefsUpdateErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response error is received from a provider
     * for the operation listResourceDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listResourceDefsResponseErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getResourceDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param definitions The definitions field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getResourceDefsResponseReceived(MALMessageHeader msgHeader,
            ResourceList definitions,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getResourceDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getResourceDefsErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void requestResponseReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanInformationManagementServiceInfo._GETREQUESTDEFS_OP_NUMBER:
            getRequestDefsResponseReceived(msgHeader,
                (RequestDefinitionList) body.getBodyElement(0, new RequestDefinitionList()), qosProperties);
            break;
          case PlanInformationManagementServiceInfo._GETEVENTDEFS_OP_NUMBER:
            getEventDefsResponseReceived(msgHeader,
                (EventDefinitionList) body.getBodyElement(0, new EventDefinitionList()), qosProperties);
            break;
          case PlanInformationManagementServiceInfo._GETACTIVITYDEFS_OP_NUMBER:
            getActivityDefsResponseReceived(msgHeader,
                (ActivityDefinitionList) body.getBodyElement(0, new ActivityDefinitionList()), qosProperties);
            break;
          case PlanInformationManagementServiceInfo._GETRESOURCEDEFS_OP_NUMBER:
            getResourceDefsResponseReceived(msgHeader,
                (ResourceList) body.getBodyElement(0, new ResourceList()), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanInformationManagementServiceInfo._GETREQUESTDEFS_OP_NUMBER:
            getRequestDefsErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanInformationManagementServiceInfo._GETEVENTDEFS_OP_NUMBER:
            getEventDefsErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanInformationManagementServiceInfo._GETACTIVITYDEFS_OP_NUMBER:
            getActivityDefsErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanInformationManagementServiceInfo._GETRESOURCEDEFS_OP_NUMBER:
            getResourceDefsErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressAckReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanInformationManagementServiceInfo._LISTREQUESTDEFS_OP_NUMBER:
            listRequestDefsAckReceived(msgHeader, qosProperties);
            break;
          case PlanInformationManagementServiceInfo._LISTEVENTDEFS_OP_NUMBER:
            listEventDefsAckReceived(msgHeader, qosProperties);
            break;
          case PlanInformationManagementServiceInfo._LISTACTIVITYDEFS_OP_NUMBER:
            listActivityDefsAckReceived(msgHeader, qosProperties);
            break;
          case PlanInformationManagementServiceInfo._LISTRESOURCEDEFS_OP_NUMBER:
            listResourceDefsAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressAckErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanInformationManagementServiceInfo._LISTREQUESTDEFS_OP_NUMBER:
            listRequestDefsAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanInformationManagementServiceInfo._LISTEVENTDEFS_OP_NUMBER:
            listEventDefsAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanInformationManagementServiceInfo._LISTACTIVITYDEFS_OP_NUMBER:
            listActivityDefsAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanInformationManagementServiceInfo._LISTRESOURCEDEFS_OP_NUMBER:
            listResourceDefsAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressUpdateReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanInformationManagementServiceInfo._LISTREQUESTDEFS_OP_NUMBER:
            listRequestDefsUpdateReceived(msgHeader,
                (DefListEntryList) body.getBodyElement(0, new DefListEntryList()), qosProperties);
            break;
          case PlanInformationManagementServiceInfo._LISTEVENTDEFS_OP_NUMBER:
            listEventDefsUpdateReceived(msgHeader,
                (DefListEntryList) body.getBodyElement(0, new DefListEntryList()), qosProperties);
            break;
          case PlanInformationManagementServiceInfo._LISTACTIVITYDEFS_OP_NUMBER:
            listActivityDefsUpdateReceived(msgHeader,
                (DefListEntryList) body.getBodyElement(0, new DefListEntryList()), qosProperties);
            break;
          case PlanInformationManagementServiceInfo._LISTRESOURCEDEFS_OP_NUMBER:
            listResourceDefsUpdateReceived(msgHeader,
                (DefListEntryList) body.getBodyElement(0, new DefListEntryList()), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressUpdateErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanInformationManagementServiceInfo._LISTREQUESTDEFS_OP_NUMBER:
            listRequestDefsUpdateErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanInformationManagementServiceInfo._LISTEVENTDEFS_OP_NUMBER:
            listEventDefsUpdateErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanInformationManagementServiceInfo._LISTACTIVITYDEFS_OP_NUMBER:
            listActivityDefsUpdateErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanInformationManagementServiceInfo._LISTRESOURCEDEFS_OP_NUMBER:
            listResourceDefsUpdateErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressResponseReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanInformationManagementServiceInfo._LISTREQUESTDEFS_OP_NUMBER:
            listRequestDefsResponseReceived(msgHeader, qosProperties);
            break;
          case PlanInformationManagementServiceInfo._LISTEVENTDEFS_OP_NUMBER:
            listEventDefsResponseReceived(msgHeader, qosProperties);
            break;
          case PlanInformationManagementServiceInfo._LISTACTIVITYDEFS_OP_NUMBER:
            listActivityDefsResponseReceived(msgHeader, qosProperties);
            break;
          case PlanInformationManagementServiceInfo._LISTRESOURCEDEFS_OP_NUMBER:
            listResourceDefsResponseReceived(msgHeader, qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressResponseErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case PlanInformationManagementServiceInfo._LISTREQUESTDEFS_OP_NUMBER:
            listRequestDefsResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanInformationManagementServiceInfo._LISTEVENTDEFS_OP_NUMBER:
            listEventDefsResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanInformationManagementServiceInfo._LISTACTIVITYDEFS_OP_NUMBER:
            listActivityDefsResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case PlanInformationManagementServiceInfo._LISTRESOURCEDEFS_OP_NUMBER:
            listResourceDefsResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

}
