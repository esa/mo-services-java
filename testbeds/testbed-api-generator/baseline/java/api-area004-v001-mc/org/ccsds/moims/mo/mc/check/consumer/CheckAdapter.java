package org.ccsds.moims.mo.mc.check.consumer;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mc.check.CheckServiceInfo;
import org.ccsds.moims.mo.mc.check.structures.CheckLinkSummaryList;
import org.ccsds.moims.mo.mc.check.structures.CheckResultSummaryList;
import org.ccsds.moims.mo.mc.check.structures.CheckTypedInstanceList;
import org.ccsds.moims.mo.mc.structures.ObjectInstancePairList;

/**
 * Consumer adapter for Check service.
 */
public abstract class CheckAdapter extends MALInteractionAdapter {

    /**
     * Called by the MAL when a PROGRESS acknowledgement is received from a provider
     * for the operation getCurrentTransitionList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getCurrentTransitionListAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update is received from a provider for
     * the operation getCurrentTransitionList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param updateSummaries The returned list shall contain an entry for each matched check returning the object instance identifier and the latest CheckResult for that CheckLink object.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getCurrentTransitionListUpdateReceived(MALMessageHeader msgHeader,
            CheckResultSummaryList updateSummaries,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response is received from a provider
     * for the operation getCurrentTransitionList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param responseSummaries The PROGRESS pattern is used to allow the possibly large list of filtered check results to be split into several updates.
The size of the lists returned in each update and final response is implementation specific.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getCurrentTransitionListResponseReceived(MALMessageHeader msgHeader,
            CheckResultSummaryList responseSummaries,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement error is received from
     * a provider for the operation getCurrentTransitionList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getCurrentTransitionListAckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update error is received from a provider
     * for the operation getCurrentTransitionList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getCurrentTransitionListUpdateErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response error is received from a provider
     * for the operation getCurrentTransitionList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getCurrentTransitionListResponseErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement is received from a provider
     * for the operation getSummaryReport.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getSummaryReportAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update is received from a provider for
     * the operation getSummaryReport.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param updateObjInstIds The returned updates and final response shall contain an entry for each requested CheckIdentity.
The first part of the update shall be the CheckIdentity object instance identifier.
The second part shall be the list of all CheckLink object instance identifiers and CheckResults associated with that CheckIdentity.
     * @param updateSummaries updateSummaries Argument number 1 as defined by the service operation
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getSummaryReportUpdateReceived(MALMessageHeader msgHeader,
            Long updateObjInstIds,
            CheckResultSummaryList updateSummaries,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response is received from a provider
     * for the operation getSummaryReport.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param responseObjInstIds responseObjInstIds Argument number 0 as defined by the service operation
     * @param responseSummaries responseSummaries Argument number 1 as defined by the service operation
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getSummaryReportResponseReceived(MALMessageHeader msgHeader,
            Long responseObjInstIds,
            CheckResultSummaryList responseSummaries,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement error is received from
     * a provider for the operation getSummaryReport.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getSummaryReportAckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update error is received from a provider
     * for the operation getSummaryReport.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getSummaryReportUpdateErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response error is received from a provider
     * for the operation getSummaryReport.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getSummaryReportResponseErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation enableService.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void enableServiceAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation enableService.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void enableServiceErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getServiceStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param serviceEnabled The operation shall return TRUE if the service is currently enabled or FALSE if the service is currently disabled.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getServiceStatusResponseReceived(MALMessageHeader msgHeader,
            Boolean serviceEnabled,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getServiceStatus.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getServiceStatusErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation enableCheck.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void enableCheckAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation enableCheck.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void enableCheckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation triggerCheck.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void triggerCheckAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation triggerCheck.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void triggerCheckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation listDefinition.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param objInstIds The response shall contain a list of matching CheckIdentity and actual check definition object instance identifiers and the actual check definition object type.
The returned list shall maintain the same order as the submitted list unless the wildcard value was included in the request.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listDefinitionResponseReceived(MALMessageHeader msgHeader,
            CheckTypedInstanceList objInstIds,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation listDefinition.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listDefinitionErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation listCheckLinks.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param chkLinkObjInstIds The response shall contain a list of CheckLinkSummary that contain the object instance identifiers of the CheckLink, CheckIdentity, and ParameterIdentity for the matched CheckIdentity objects.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listCheckLinksResponseReceived(MALMessageHeader msgHeader,
            CheckLinkSummaryList chkLinkObjInstIds,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation listCheckLinks.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listCheckLinksErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation addCheck.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param newObjInstIds The response shall contain the list of object instance identifiers for the CheckIdentity and new actual definition objects.
The returned list shall maintain the same order as the submitted definitions.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void addCheckResponseReceived(MALMessageHeader msgHeader,
            ObjectInstancePairList newObjInstIds,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation addCheck.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void addCheckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation updateDefinition.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param newObjInstIds The response shall contain the list of object instance identifiers for the new check definition objects.
The returned list shall maintain the same order as the submitted definitions.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateDefinitionResponseReceived(MALMessageHeader msgHeader,
            LongList newObjInstIds,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation updateDefinition.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void updateDefinitionErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation removeCheck.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void removeCheckAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation removeCheck.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void removeCheckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation addParameterCheck.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param newObjInstIds The response shall contain the list of object instance identifiers for the new CheckLink and CheckLinkDefinition objects.
The returned list shall maintain the same order as the submitted links.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void addParameterCheckResponseReceived(MALMessageHeader msgHeader,
            ObjectInstancePairList newObjInstIds,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation addParameterCheck.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void addParameterCheckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation removeParameterCheck.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void removeParameterCheckAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation removeParameterCheck.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void removeParameterCheckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case CheckServiceInfo._ENABLESERVICE_OP_NUMBER:
            enableServiceAckReceived(msgHeader, qosProperties);
            break;
          case CheckServiceInfo._ENABLECHECK_OP_NUMBER:
            enableCheckAckReceived(msgHeader, qosProperties);
            break;
          case CheckServiceInfo._TRIGGERCHECK_OP_NUMBER:
            triggerCheckAckReceived(msgHeader, qosProperties);
            break;
          case CheckServiceInfo._REMOVECHECK_OP_NUMBER:
            removeCheckAckReceived(msgHeader, qosProperties);
            break;
          case CheckServiceInfo._REMOVEPARAMETERCHECK_OP_NUMBER:
            removeParameterCheckAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void submitErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case CheckServiceInfo._ENABLESERVICE_OP_NUMBER:
            enableServiceErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case CheckServiceInfo._ENABLECHECK_OP_NUMBER:
            enableCheckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case CheckServiceInfo._TRIGGERCHECK_OP_NUMBER:
            triggerCheckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case CheckServiceInfo._REMOVECHECK_OP_NUMBER:
            removeCheckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case CheckServiceInfo._REMOVEPARAMETERCHECK_OP_NUMBER:
            removeParameterCheckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestResponseReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case CheckServiceInfo._GETSERVICESTATUS_OP_NUMBER:
            getServiceStatusResponseReceived(msgHeader,
                (body.getBodyElement(0, new Union(Boolean.FALSE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Boolean.FALSE))).getBooleanValue(), qosProperties);
            break;
          case CheckServiceInfo._LISTDEFINITION_OP_NUMBER:
            listDefinitionResponseReceived(msgHeader,
                (CheckTypedInstanceList) body.getBodyElement(0, new CheckTypedInstanceList()), qosProperties);
            break;
          case CheckServiceInfo._LISTCHECKLINKS_OP_NUMBER:
            listCheckLinksResponseReceived(msgHeader,
                (CheckLinkSummaryList) body.getBodyElement(0, new CheckLinkSummaryList()), qosProperties);
            break;
          case CheckServiceInfo._ADDCHECK_OP_NUMBER:
            addCheckResponseReceived(msgHeader,
                (ObjectInstancePairList) body.getBodyElement(0, new ObjectInstancePairList()), qosProperties);
            break;
          case CheckServiceInfo._UPDATEDEFINITION_OP_NUMBER:
            updateDefinitionResponseReceived(msgHeader,
                (LongList) body.getBodyElement(0, new LongList()), qosProperties);
            break;
          case CheckServiceInfo._ADDPARAMETERCHECK_OP_NUMBER:
            addParameterCheckResponseReceived(msgHeader,
                (ObjectInstancePairList) body.getBodyElement(0, new ObjectInstancePairList()), qosProperties);
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
          case CheckServiceInfo._GETSERVICESTATUS_OP_NUMBER:
            getServiceStatusErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case CheckServiceInfo._LISTDEFINITION_OP_NUMBER:
            listDefinitionErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case CheckServiceInfo._LISTCHECKLINKS_OP_NUMBER:
            listCheckLinksErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case CheckServiceInfo._ADDCHECK_OP_NUMBER:
            addCheckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case CheckServiceInfo._UPDATEDEFINITION_OP_NUMBER:
            updateDefinitionErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case CheckServiceInfo._ADDPARAMETERCHECK_OP_NUMBER:
            addParameterCheckErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case CheckServiceInfo._GETCURRENTTRANSITIONLIST_OP_NUMBER:
            getCurrentTransitionListAckReceived(msgHeader, qosProperties);
            break;
          case CheckServiceInfo._GETSUMMARYREPORT_OP_NUMBER:
            getSummaryReportAckReceived(msgHeader, qosProperties);
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
          case CheckServiceInfo._GETCURRENTTRANSITIONLIST_OP_NUMBER:
            getCurrentTransitionListAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case CheckServiceInfo._GETSUMMARYREPORT_OP_NUMBER:
            getSummaryReportAckErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case CheckServiceInfo._GETCURRENTTRANSITIONLIST_OP_NUMBER:
            getCurrentTransitionListUpdateReceived(msgHeader,
                (CheckResultSummaryList) body.getBodyElement(0, new CheckResultSummaryList()), qosProperties);
            break;
          case CheckServiceInfo._GETSUMMARYREPORT_OP_NUMBER:
            getSummaryReportUpdateReceived(msgHeader,
                (body.getBodyElement(0, new Union(Long.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Long.MAX_VALUE))).getLongValue(),
                (CheckResultSummaryList) body.getBodyElement(1, new CheckResultSummaryList()), qosProperties);
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
          case CheckServiceInfo._GETCURRENTTRANSITIONLIST_OP_NUMBER:
            getCurrentTransitionListUpdateErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case CheckServiceInfo._GETSUMMARYREPORT_OP_NUMBER:
            getSummaryReportUpdateErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case CheckServiceInfo._GETCURRENTTRANSITIONLIST_OP_NUMBER:
            getCurrentTransitionListResponseReceived(msgHeader,
                (CheckResultSummaryList) body.getBodyElement(0, new CheckResultSummaryList()), qosProperties);
            break;
          case CheckServiceInfo._GETSUMMARYREPORT_OP_NUMBER:
            getSummaryReportResponseReceived(msgHeader,
                (body.getBodyElement(0, new Union(Long.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Long.MAX_VALUE))).getLongValue(),
                (CheckResultSummaryList) body.getBodyElement(1, new CheckResultSummaryList()), qosProperties);
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
          case CheckServiceInfo._GETCURRENTTRANSITIONLIST_OP_NUMBER:
            getCurrentTransitionListResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case CheckServiceInfo._GETSUMMARYREPORT_OP_NUMBER:
            getSummaryReportResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

}
