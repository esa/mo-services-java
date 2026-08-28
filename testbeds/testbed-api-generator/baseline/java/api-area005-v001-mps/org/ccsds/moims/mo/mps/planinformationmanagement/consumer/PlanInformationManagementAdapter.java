package org.ccsds.moims.mo.mps.planinformationmanagement.consumer;

/**
 * Consumer adapter for PlanInformationManagement service.
 */
public abstract class PlanInformationManagementAdapter extends org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter {

    /**
     * Called by the MAL when a PROGRESS acknowledgement is received from a provider
     * for the operation listRequestDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listRequestDefsAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update is received from a provider for
     * the operation listRequestDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param requestDefs The requestDefs field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listRequestDefsUpdateReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mps.structures.DefListEntryList requestDefs,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response is received from a provider
     * for the operation listRequestDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listRequestDefsResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement error is received from
     * a provider for the operation listRequestDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listRequestDefsAckErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update error is received from a provider
     * for the operation listRequestDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listRequestDefsUpdateErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response error is received from a provider
     * for the operation listRequestDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listRequestDefsResponseErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getRequestDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param definitions The definitions field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestDefsResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mps.structures.RequestDefinitionList definitions,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getRequestDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getRequestDefsErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement is received from a provider
     * for the operation listEventDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listEventDefsAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update is received from a provider for
     * the operation listEventDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param eventDefs The eventDefs field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listEventDefsUpdateReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mps.structures.DefListEntryList eventDefs,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response is received from a provider
     * for the operation listEventDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listEventDefsResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement error is received from
     * a provider for the operation listEventDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listEventDefsAckErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update error is received from a provider
     * for the operation listEventDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listEventDefsUpdateErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response error is received from a provider
     * for the operation listEventDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listEventDefsResponseErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getEventDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param definitions The definitions field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getEventDefsResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mps.structures.EventDefinitionList definitions,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getEventDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getEventDefsErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement is received from a provider
     * for the operation listActivityDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listActivityDefsAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update is received from a provider for
     * the operation listActivityDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param activitytDefs The activitytDefs field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listActivityDefsUpdateReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mps.structures.DefListEntryList activitytDefs,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response is received from a provider
     * for the operation listActivityDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listActivityDefsResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement error is received from
     * a provider for the operation listActivityDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listActivityDefsAckErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update error is received from a provider
     * for the operation listActivityDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listActivityDefsUpdateErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response error is received from a provider
     * for the operation listActivityDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listActivityDefsResponseErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getActivityDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param definitions The definitions field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getActivityDefsResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mps.structures.ActivityDefinitionList definitions,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getActivityDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getActivityDefsErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement is received from a provider
     * for the operation listResourceDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listResourceDefsAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update is received from a provider for
     * the operation listResourceDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param resourceDefs The resourceDefs field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listResourceDefsUpdateReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mps.structures.DefListEntryList resourceDefs,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response is received from a provider
     * for the operation listResourceDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listResourceDefsResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement error is received from
     * a provider for the operation listResourceDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listResourceDefsAckErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update error is received from a provider
     * for the operation listResourceDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listResourceDefsUpdateErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response error is received from a provider
     * for the operation listResourceDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listResourceDefsResponseErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getResourceDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param definitions The definitions field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getResourceDefsResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mps.structures.ResourceList definitions,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getResourceDefs.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getResourceDefsErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    @Override
    public final void requestResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._GETREQUESTDEFS_OP_NUMBER:
            getRequestDefsResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mps.structures.RequestDefinitionList) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.RequestDefinitionList()), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._GETEVENTDEFS_OP_NUMBER:
            getEventDefsResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mps.structures.EventDefinitionList) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.EventDefinitionList()), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._GETACTIVITYDEFS_OP_NUMBER:
            getActivityDefsResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mps.structures.ActivityDefinitionList) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.ActivityDefinitionList()), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._GETRESOURCEDEFS_OP_NUMBER:
            getResourceDefsResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mps.structures.ResourceList) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.ResourceList()), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._GETREQUESTDEFS_OP_NUMBER:
            getRequestDefsErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._GETEVENTDEFS_OP_NUMBER:
            getEventDefsErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._GETACTIVITYDEFS_OP_NUMBER:
            getActivityDefsErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._GETRESOURCEDEFS_OP_NUMBER:
            getResourceDefsErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTREQUESTDEFS_OP_NUMBER:
            listRequestDefsAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTEVENTDEFS_OP_NUMBER:
            listEventDefsAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTACTIVITYDEFS_OP_NUMBER:
            listActivityDefsAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTRESOURCEDEFS_OP_NUMBER:
            listResourceDefsAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressAckErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTREQUESTDEFS_OP_NUMBER:
            listRequestDefsAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTEVENTDEFS_OP_NUMBER:
            listEventDefsAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTACTIVITYDEFS_OP_NUMBER:
            listActivityDefsAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTRESOURCEDEFS_OP_NUMBER:
            listResourceDefsAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressUpdateReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTREQUESTDEFS_OP_NUMBER:
            listRequestDefsUpdateReceived(msgHeader,
                (org.ccsds.moims.mo.mps.structures.DefListEntryList) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.DefListEntryList()), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTEVENTDEFS_OP_NUMBER:
            listEventDefsUpdateReceived(msgHeader,
                (org.ccsds.moims.mo.mps.structures.DefListEntryList) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.DefListEntryList()), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTACTIVITYDEFS_OP_NUMBER:
            listActivityDefsUpdateReceived(msgHeader,
                (org.ccsds.moims.mo.mps.structures.DefListEntryList) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.DefListEntryList()), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTRESOURCEDEFS_OP_NUMBER:
            listResourceDefsUpdateReceived(msgHeader,
                (org.ccsds.moims.mo.mps.structures.DefListEntryList) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.DefListEntryList()), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressUpdateErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTREQUESTDEFS_OP_NUMBER:
            listRequestDefsUpdateErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTEVENTDEFS_OP_NUMBER:
            listEventDefsUpdateErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTACTIVITYDEFS_OP_NUMBER:
            listActivityDefsUpdateErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTRESOURCEDEFS_OP_NUMBER:
            listResourceDefsUpdateErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTREQUESTDEFS_OP_NUMBER:
            listRequestDefsResponseReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTEVENTDEFS_OP_NUMBER:
            listEventDefsResponseReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTACTIVITYDEFS_OP_NUMBER:
            listActivityDefsResponseReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTRESOURCEDEFS_OP_NUMBER:
            listResourceDefsResponseReceived(msgHeader, qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressResponseErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTREQUESTDEFS_OP_NUMBER:
            listRequestDefsResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTEVENTDEFS_OP_NUMBER:
            listEventDefsResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTACTIVITYDEFS_OP_NUMBER:
            listActivityDefsResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo._LISTRESOURCEDEFS_OP_NUMBER:
            listResourceDefsResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

}
