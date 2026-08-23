package org.ccsds.moims.mo.mc.check.consumer;

/**
 * Consumer stub for Check service.
 */
public class CheckStub {

    /**
     * The consumer field.
     */
    private final org.ccsds.moims.mo.mal.consumer.MALConsumer consumer;

    /**
     * Wraps a MALconsumer connection with service specific methods that map from
     * the high level service API to the generic MAL API.
     * 
     * @param consumer consumer The MALConsumer to use in this stub.
     */
    public CheckStub(org.ccsds.moims.mo.mal.consumer.MALConsumer consumer) {
        this.consumer = consumer;
    }

    /**
     * Returns the internal MAL consumer object used for sending of messages from
     * this interface.
     * 
     * @return The MAL consumer object.
     */
    public org.ccsds.moims.mo.mal.consumer.MALConsumer getConsumer() {
        return consumer;
    }

    /**
     * The getCurrentTransitionList operation allows a consumer to obtain the
     * latest result of a number of checks filtering on check state.
     * 
     * @param filter The filter field shall contain a set of object instance identifiers for which the check result is required.
If the checkFilterViaGroups field is TRUE then the checkFilter field shall contain GroupIdentity object instance identifiers, otherwise the field contains CheckIdentity object instance identifiers.
The CheckIdentity objects referenced, either directly or indirectly via groups, by the checkFilter field shall be the CheckIdentity objects to match.
The checkFilter field shall support the wildcard value of '0' and shall match all CheckIdentity objects of the provider.
The service provider shall check for the wildcard value in the list of object instance identifiers in the checkFilter field first and if found no other checks of supplied CheckIdentity object instance identifiers shall be made.
If the parameterFilterViaGroups field is TRUE then the parameterFilter field shall contain GroupIdentity object instance identifiers, otherwise the field contains ParameterIdentity object instance identifiers.
The ParameterIdentity objects referenced, either directly or indirectly via groups, by the parameterFilter field shall be the ParameterIdentity objects to match.
The parameterFilter field shall support the wildcard value of '0' and shall match all ParameterIdentity objects of the provider.
The service provider shall check for the wildcard value in the list of object instance identifiers in the parameterFilter field first and if found no other checks of supplied ParameterIdentity object instance identifiers shall be made.
If a referenced GroupIdentity object is unknown then an UNKNOWN error shall be returned.
If a requested Group, or the Group objects referenced by that Group, does not contain CheckIdentity objects for the checkFilter or ParameterIdentity for the parameterFilter then an INVALID error shall be returned.
If a referenced CheckIdentity object, either directly or indirectly via groups, is unknown then an UNKNOWN error shall be returned.
If a referenced ParameterIdentity object, either directly or indirectly via groups, is unknown then an UNKNOWN error shall be returned.
The filter field shall also contain a list of CheckState enumerations of which states to filter on.
The supplied lists shall be AND'd together to form the complete filter.
If a CheckLink object matches the CheckIdentity filter, and the ParameterIdentity filter, and its state matches any of the supplied CheckState enumerations, then its latest CheckResult value shall be returned.
To report all checks, the wildcard values may be used in the CheckResultFilter.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void getCurrentTransitionList(org.ccsds.moims.mo.mc.check.structures.CheckResultFilter filter,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.progress(org.ccsds.moims.mo.mc.check.CheckServiceInfo.GETCURRENTTRANSITIONLIST_OP, adapter, filter);
    }

    /**
     * Asynchronous version of method getCurrentTransitionList.
     * 
     * @param filter The filter field shall contain a set of object instance identifiers for which the check result is required.
If the checkFilterViaGroups field is TRUE then the checkFilter field shall contain GroupIdentity object instance identifiers, otherwise the field contains CheckIdentity object instance identifiers.
The CheckIdentity objects referenced, either directly or indirectly via groups, by the checkFilter field shall be the CheckIdentity objects to match.
The checkFilter field shall support the wildcard value of '0' and shall match all CheckIdentity objects of the provider.
The service provider shall check for the wildcard value in the list of object instance identifiers in the checkFilter field first and if found no other checks of supplied CheckIdentity object instance identifiers shall be made.
If the parameterFilterViaGroups field is TRUE then the parameterFilter field shall contain GroupIdentity object instance identifiers, otherwise the field contains ParameterIdentity object instance identifiers.
The ParameterIdentity objects referenced, either directly or indirectly via groups, by the parameterFilter field shall be the ParameterIdentity objects to match.
The parameterFilter field shall support the wildcard value of '0' and shall match all ParameterIdentity objects of the provider.
The service provider shall check for the wildcard value in the list of object instance identifiers in the parameterFilter field first and if found no other checks of supplied ParameterIdentity object instance identifiers shall be made.
If a referenced GroupIdentity object is unknown then an UNKNOWN error shall be returned.
If a requested Group, or the Group objects referenced by that Group, does not contain CheckIdentity objects for the checkFilter or ParameterIdentity for the parameterFilter then an INVALID error shall be returned.
If a referenced CheckIdentity object, either directly or indirectly via groups, is unknown then an UNKNOWN error shall be returned.
If a referenced ParameterIdentity object, either directly or indirectly via groups, is unknown then an UNKNOWN error shall be returned.
The filter field shall also contain a list of CheckState enumerations of which states to filter on.
The supplied lists shall be AND'd together to form the complete filter.
If a CheckLink object matches the CheckIdentity filter, and the ParameterIdentity filter, and its state matches any of the supplied CheckState enumerations, then its latest CheckResult value shall be returned.
To report all checks, the wildcard values may be used in the CheckResultFilter.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncGetCurrentTransitionList(org.ccsds.moims.mo.mc.check.structures.CheckResultFilter filter,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncProgress(org.ccsds.moims.mo.mc.check.CheckServiceInfo.GETCURRENTTRANSITIONLIST_OP, adapter, filter);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueGetCurrentTransitionList(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mc.check.CheckServiceInfo.GETCURRENTTRANSITIONLIST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The getSummaryReport operation allows a consumer to obtain the status of
     * a number of checks and the result of any check evaluations linked to them.
     * 
     * @param objInstIds The objInstIds field shall hold one or more CheckIdentity object instance identifiers of which a check report is required.
A wildcard value of '0' shall report on all checks.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a requested check is unknown then an UNKNOWN error shall be returned in the ACKNOWLEDGE message and the operation shall end.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void getSummaryReport(org.ccsds.moims.mo.mal.structures.LongList objInstIds,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.progress(org.ccsds.moims.mo.mc.check.CheckServiceInfo.GETSUMMARYREPORT_OP, adapter, objInstIds);
    }

    /**
     * Asynchronous version of method getSummaryReport.
     * 
     * @param objInstIds The objInstIds field shall hold one or more CheckIdentity object instance identifiers of which a check report is required.
A wildcard value of '0' shall report on all checks.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a requested check is unknown then an UNKNOWN error shall be returned in the ACKNOWLEDGE message and the operation shall end.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncGetSummaryReport(org.ccsds.moims.mo.mal.structures.LongList objInstIds,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncProgress(org.ccsds.moims.mo.mc.check.CheckServiceInfo.GETSUMMARYREPORT_OP, adapter, objInstIds);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueGetSummaryReport(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mc.check.CheckServiceInfo.GETSUMMARYREPORT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The enableService operation allows a consumer to globally control whether
     * evaluation of all checks is performed or not.
     * It should be noted that no check reports will be generated if the service
     * provider has been disabled via the enableService operation.
     * 
     * @param enableService If enableService is set to TRUE the service shall be enabled and evaluation and reporting of check will commence.
If enableService is set to FALSE then all evaluation of checks shall be suspended and no check transitions will be reported.
If the enableService value matches the current enabled state of the service then no change shall be made and no error reported. Enabling an already enabled service has no effect.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void enableService(Boolean enableService) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.mc.check.CheckServiceInfo.ENABLESERVICE_OP, (enableService == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(enableService));
    }

    /**
     * Asynchronous version of method enableService.
     * 
     * @param enableService If enableService is set to TRUE the service shall be enabled and evaluation and reporting of check will commence.
If enableService is set to FALSE then all evaluation of checks shall be suspended and no check transitions will be reported.
If the enableService value matches the current enabled state of the service then no change shall be made and no error reported. Enabling an already enabled service has no effect.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncEnableService(Boolean enableService,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.mc.check.CheckServiceInfo.ENABLESERVICE_OP, adapter, (enableService == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(enableService));
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueEnableService(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mc.check.CheckServiceInfo.ENABLESERVICE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The getServiceStatus operation allows a consumer to determine the global
     * check service enabled status.
     * 
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public Boolean getServiceStatus() throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mc.check.CheckServiceInfo.GETSERVICESTATUS_OP, (Object[]) null);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE));
        return (body0 == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body0).getBooleanValue();
    }

    /**
     * Asynchronous version of method getServiceStatus.
     * 
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncGetServiceStatus(org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mc.check.CheckServiceInfo.GETSERVICESTATUS_OP, adapter, (Object[]) null);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueGetServiceStatus(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mc.check.CheckServiceInfo.GETSERVICESTATUS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The enableCheck operation allows a consumer to control whether evaluation
     * of a set of checks is performed or not. The operation allows the consumer
     * to select the checks directly or indirectly using groups.
     * 
     * @param isGroupIds If the isGroupIds field is TRUE then the enableInstances field shall contain GroupIdentity object instance identifiers, otherwise the field contains CheckLink object instance identifiers.
The CheckLink objects referenced, either directly or indirectly via groups, by the enableInstances field shall be the CheckLink objects to match.
The id of the enableInstances field shall support the wildcard value of '0' and matches all CheckLink objects of the provider.
The service provider shall check for the wildcard value in the list of object instance identifiers in the enableInstances field first and if found no other checks of supplied object instance identifiers shall be made.
If the enableInstances field contains a value of TRUE then evaluations of matching CheckLink objects shall be performed, a value of FALSE requests that evaluations will not be performed.
No error shall be raised if the enableInstances Boolean value supplied is the same as the current checkEnabled field for a CheckLink object i.e. enabling an already enabled check will not result in an error.
If a requested CheckLink or GroupIdentity object is unknown then an UNKNOWN error shall be returned.
If a requested Group, or the Group objects referenced by that Group, does not contain CheckLink objects then an INVALID error shall be returned.
If an error is raised then no modifications shall be made as a result of this operation call.
The provider shall create and store a new CheckLinkDefinition object in the COM archive if the checkEnabled field is changed.
     * @param enableInstances If the check is being enabled, and the check is defined as being periodic in the check link definition, then the provider shall generate a check result immediately and start the checking interval from that check.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void enableCheck(Boolean isGroupIds,
            org.ccsds.moims.mo.com.structures.InstanceBooleanPairList enableInstances) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.mc.check.CheckServiceInfo.ENABLECHECK_OP, (isGroupIds == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(isGroupIds), enableInstances);
    }

    /**
     * Asynchronous version of method enableCheck.
     * 
     * @param isGroupIds If the isGroupIds field is TRUE then the enableInstances field shall contain GroupIdentity object instance identifiers, otherwise the field contains CheckLink object instance identifiers.
The CheckLink objects referenced, either directly or indirectly via groups, by the enableInstances field shall be the CheckLink objects to match.
The id of the enableInstances field shall support the wildcard value of '0' and matches all CheckLink objects of the provider.
The service provider shall check for the wildcard value in the list of object instance identifiers in the enableInstances field first and if found no other checks of supplied object instance identifiers shall be made.
If the enableInstances field contains a value of TRUE then evaluations of matching CheckLink objects shall be performed, a value of FALSE requests that evaluations will not be performed.
No error shall be raised if the enableInstances Boolean value supplied is the same as the current checkEnabled field for a CheckLink object i.e. enabling an already enabled check will not result in an error.
If a requested CheckLink or GroupIdentity object is unknown then an UNKNOWN error shall be returned.
If a requested Group, or the Group objects referenced by that Group, does not contain CheckLink objects then an INVALID error shall be returned.
If an error is raised then no modifications shall be made as a result of this operation call.
The provider shall create and store a new CheckLinkDefinition object in the COM archive if the checkEnabled field is changed.
     * @param enableInstances If the check is being enabled, and the check is defined as being periodic in the check link definition, then the provider shall generate a check result immediately and start the checking interval from that check.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncEnableCheck(Boolean isGroupIds,
            org.ccsds.moims.mo.com.structures.InstanceBooleanPairList enableInstances,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.mc.check.CheckServiceInfo.ENABLECHECK_OP, adapter, (isGroupIds == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(isGroupIds), enableInstances);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueEnableCheck(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mc.check.CheckServiceInfo.ENABLECHECK_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The triggerCheck operation allows a consumer to request the immediate evaluation
     * of a number of checks. Any violations will cause appropriate events to
     * be generated.
     * It should be noted that no check reports will be generated if the service
     * provider has been disabled via the enableService operation.
     * 
     * @param checkObjInstIds The checkObjInstIds field shall hold a list of CheckIdentity object instance identifiers to trigger the evaluation of all linked checks.
     * @param linkObjInstIds The linkObjInstIds field shall hold a list of CheckLink object instance identifiers to trigger the evaluation of.
The wildcard value of '0' shall be permitted in either list.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a requested CheckIdentity or CheckLink object is unknown then an UNKNOWN error shall be returned.
If an error is raised then no evaluations shall be made as a result of this operation call.
Either list may be empty in which case filtering on that aspect, check identity or specific check link, shall be ignored.
The two lists shall be combined using 'OR' logic, where a CheckLink is evaluated if the identity of a check is in the first list or if the link is directly listed in the second list.
Triggering a check shall ignore the nominalTime, nominalCount, violationTime and violationCount fields and requests an immediate evaluation of the checks.
Triggering a check during a periodic check shall not influence the periodic check (e.g. it does not reset the checkInterval timer, the successive valid samples that passed/violated the check or the maxReportingInterval timer).
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void triggerCheck(org.ccsds.moims.mo.mal.structures.LongList checkObjInstIds,
            org.ccsds.moims.mo.mal.structures.LongList linkObjInstIds) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.mc.check.CheckServiceInfo.TRIGGERCHECK_OP, checkObjInstIds, linkObjInstIds);
    }

    /**
     * Asynchronous version of method triggerCheck.
     * 
     * @param checkObjInstIds The checkObjInstIds field shall hold a list of CheckIdentity object instance identifiers to trigger the evaluation of all linked checks.
     * @param linkObjInstIds The linkObjInstIds field shall hold a list of CheckLink object instance identifiers to trigger the evaluation of.
The wildcard value of '0' shall be permitted in either list.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a requested CheckIdentity or CheckLink object is unknown then an UNKNOWN error shall be returned.
If an error is raised then no evaluations shall be made as a result of this operation call.
Either list may be empty in which case filtering on that aspect, check identity or specific check link, shall be ignored.
The two lists shall be combined using 'OR' logic, where a CheckLink is evaluated if the identity of a check is in the first list or if the link is directly listed in the second list.
Triggering a check shall ignore the nominalTime, nominalCount, violationTime and violationCount fields and requests an immediate evaluation of the checks.
Triggering a check during a periodic check shall not influence the periodic check (e.g. it does not reset the checkInterval timer, the successive valid samples that passed/violated the check or the maxReportingInterval timer).
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTriggerCheck(org.ccsds.moims.mo.mal.structures.LongList checkObjInstIds,
            org.ccsds.moims.mo.mal.structures.LongList linkObjInstIds,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.mc.check.CheckServiceInfo.TRIGGERCHECK_OP, adapter, checkObjInstIds, linkObjInstIds);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueTriggerCheck(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mc.check.CheckServiceInfo.TRIGGERCHECK_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The listDefinition operation allows a consumer to request the latest object
     * instance identifiers of the CheckIdentity and actual check definition objects
     * for the supported checks of the provider.
     * 
     * @param names The names field shall hold a list of CheckIdentity names to retrieve the CheckIdentity and actual check definition object instance identifiers for.
The request may contain the wildcard value of '*' to return all supported check definitions.
The wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.
If a provided identifier does not include a wildcard and does not match an existing CheckIdentity object then this operation shall fail with an UNKNOWN error.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mc.check.structures.CheckTypedInstanceList listDefinition(org.ccsds.moims.mo.mal.structures.IdentifierList names) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mc.check.CheckServiceInfo.LISTDEFINITION_OP, names);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mc.check.structures.CheckTypedInstanceList());
        return (org.ccsds.moims.mo.mc.check.structures.CheckTypedInstanceList) body0;
    }

    /**
     * Asynchronous version of method listDefinition.
     * 
     * @param names The names field shall hold a list of CheckIdentity names to retrieve the CheckIdentity and actual check definition object instance identifiers for.
The request may contain the wildcard value of '*' to return all supported check definitions.
The wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.
If a provided identifier does not include a wildcard and does not match an existing CheckIdentity object then this operation shall fail with an UNKNOWN error.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncListDefinition(org.ccsds.moims.mo.mal.structures.IdentifierList names,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mc.check.CheckServiceInfo.LISTDEFINITION_OP, adapter, names);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueListDefinition(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mc.check.CheckServiceInfo.LISTDEFINITION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The listCheckLinks operation allows a consumer to request the object instance
     * identifiers of the CheckLink objects for the checks of the provider.
     * 
     * @param checkObjInstIds The checkObjInstIds field shall hold a list of CheckIdentity object instance identifiers to retrieve the CheckLink object instance identifiers for.
The request may contain the wildcard value of '0' to return all supported check links.
The wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.
If a provided identifier does not include a wildcard and does not match an existing CheckIdentity object then this operation shall fail with an UNKNOWN error.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mc.check.structures.CheckLinkSummaryList listCheckLinks(org.ccsds.moims.mo.mal.structures.LongList checkObjInstIds) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mc.check.CheckServiceInfo.LISTCHECKLINKS_OP, checkObjInstIds);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mc.check.structures.CheckLinkSummaryList());
        return (org.ccsds.moims.mo.mc.check.structures.CheckLinkSummaryList) body0;
    }

    /**
     * Asynchronous version of method listCheckLinks.
     * 
     * @param checkObjInstIds The checkObjInstIds field shall hold a list of CheckIdentity object instance identifiers to retrieve the CheckLink object instance identifiers for.
The request may contain the wildcard value of '0' to return all supported check links.
The wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.
If a provided identifier does not include a wildcard and does not match an existing CheckIdentity object then this operation shall fail with an UNKNOWN error.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncListCheckLinks(org.ccsds.moims.mo.mal.structures.LongList checkObjInstIds,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mc.check.CheckServiceInfo.LISTCHECKLINKS_OP, adapter, checkObjInstIds);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueListCheckLinks(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mc.check.CheckServiceInfo.LISTCHECKLINKS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The addCheck operation allows a consumer to define one or more checks that
     * do not currently exist.
     * The new CheckIdentity and actual check definition objects are expected
     * to be stored in the COM archive by the provider of the check service.
     * 
     * @param checkNames The checkNames field shall hold the names of the checks to be added.
The checkNames field must not contain NULL, the wildcard '*', or empty value (an INVALID error shall be returned in this case).
The supplied names must be unique among all CheckIdentity objects for the domain of the provider otherwise a DUPLICATE error shall be raised.
     * @param checkDefDetails The checkDefDetails field shall hold the CheckDefinitionDetails to be added.
The two lists shall be ordered the same.
The number of entries in the two lists shall be the same size otherwise an INVALID error shall be raised.
Only one of nominalTime and nominalCount is permitted to be zero, an INVALID error shall be returned if this is not the case.
Only one of violationTime and violationCount is permitted to be zero, an INVALID error shall be returned if this is not the case.
If an error is raised then no new identities and definitions shall be added as a result of this operation call.
If the supplied name matches an existing, but removed, CheckIdentity then that CheckIdentity shall be reused otherwise a new CheckIdentity shall be created.
The provider shall create a new actual check definition object and store it, and any new CheckIdentity objects, in the COM archive.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mc.structures.ObjectInstancePairList addCheck(org.ccsds.moims.mo.mal.structures.StringList checkNames,
            org.ccsds.moims.mo.mc.check.structures.CheckDefinitionDetailsList checkDefDetails) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mc.check.CheckServiceInfo.ADDCHECK_OP, checkNames, checkDefDetails);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mc.structures.ObjectInstancePairList());
        return (org.ccsds.moims.mo.mc.structures.ObjectInstancePairList) body0;
    }

    /**
     * Asynchronous version of method addCheck.
     * 
     * @param checkNames The checkNames field shall hold the names of the checks to be added.
The checkNames field must not contain NULL, the wildcard '*', or empty value (an INVALID error shall be returned in this case).
The supplied names must be unique among all CheckIdentity objects for the domain of the provider otherwise a DUPLICATE error shall be raised.
     * @param checkDefDetails The checkDefDetails field shall hold the CheckDefinitionDetails to be added.
The two lists shall be ordered the same.
The number of entries in the two lists shall be the same size otherwise an INVALID error shall be raised.
Only one of nominalTime and nominalCount is permitted to be zero, an INVALID error shall be returned if this is not the case.
Only one of violationTime and violationCount is permitted to be zero, an INVALID error shall be returned if this is not the case.
If an error is raised then no new identities and definitions shall be added as a result of this operation call.
If the supplied name matches an existing, but removed, CheckIdentity then that CheckIdentity shall be reused otherwise a new CheckIdentity shall be created.
The provider shall create a new actual check definition object and store it, and any new CheckIdentity objects, in the COM archive.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncAddCheck(org.ccsds.moims.mo.mal.structures.StringList checkNames,
            org.ccsds.moims.mo.mc.check.structures.CheckDefinitionDetailsList checkDefDetails,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mc.check.CheckServiceInfo.ADDCHECK_OP, adapter, checkNames, checkDefDetails);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueAddCheck(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mc.check.CheckServiceInfo.ADDCHECK_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The updateDefinition operation allows a consumer to update a definition
     * for one or more checks.
     * This differs from deleting an existing check and adding a new definition
     * with the same check name in the fact that the CheckIdentity object is not
     * changed between the two definitions.
     * The replacement definition should be stored in the COM archive by the service
     * provider. The operation does not remove the previous object from the COM
     * archive, merely removes the object from the provider.
     * The operation also cannot be used to update a check definition for a check
     * that is currently being used i.e. has CheckLink objects linked to it. The
     * CheckLink objects should first be removed using removeParameterCheck before
     * calling this operation.
     * 
     * @param checkInstIds The checkInstIds field shall hold the object instance identifiers of the CheckIdentity objects to be updated.
If the checkInstIds list contains either NULL or '0' an INVALID error shall be raised.
The supplied object instance identifiers shall match existing identity objects, an UNKNOWN error shall be raised if this is not the case.
If the check to be updated is currently being used by a CheckLink object, a REFERENCED error shall be raised.
     * @param checkDefDetails The checkDefDetails field shall contain the replacement CheckDefinitionDetails.
The two lists shall be ordered the same.
The number of entries in the two lists shall be the same size otherwise an INVALID error shall be raised.
Only one of nominalTime and nominalCount is permitted to be zero, an INVALID error shall be returned if this is not the case.
Only one of violationTime and violationCount is permitted to be zero, an INVALID error shall be returned if this is not the case.
If an error is raised then no definitions shall be updated as a result of this operation call.
The provider shall create new actual check definition objects and store them in the COM archive.
The new definition object shall be the current definition used for the specific CheckIdentity.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.structures.LongList updateDefinition(org.ccsds.moims.mo.mal.structures.LongList checkInstIds,
            org.ccsds.moims.mo.mc.check.structures.CheckDefinitionDetailsList checkDefDetails) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mc.check.CheckServiceInfo.UPDATEDEFINITION_OP, checkInstIds, checkDefDetails);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.LongList());
        return (org.ccsds.moims.mo.mal.structures.LongList) body0;
    }

    /**
     * Asynchronous version of method updateDefinition.
     * 
     * @param checkInstIds The checkInstIds field shall hold the object instance identifiers of the CheckIdentity objects to be updated.
If the checkInstIds list contains either NULL or '0' an INVALID error shall be raised.
The supplied object instance identifiers shall match existing identity objects, an UNKNOWN error shall be raised if this is not the case.
If the check to be updated is currently being used by a CheckLink object, a REFERENCED error shall be raised.
     * @param checkDefDetails The checkDefDetails field shall contain the replacement CheckDefinitionDetails.
The two lists shall be ordered the same.
The number of entries in the two lists shall be the same size otherwise an INVALID error shall be raised.
Only one of nominalTime and nominalCount is permitted to be zero, an INVALID error shall be returned if this is not the case.
Only one of violationTime and violationCount is permitted to be zero, an INVALID error shall be returned if this is not the case.
If an error is raised then no definitions shall be updated as a result of this operation call.
The provider shall create new actual check definition objects and store them in the COM archive.
The new definition object shall be the current definition used for the specific CheckIdentity.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncUpdateDefinition(org.ccsds.moims.mo.mal.structures.LongList checkInstIds,
            org.ccsds.moims.mo.mc.check.structures.CheckDefinitionDetailsList checkDefDetails,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mc.check.CheckServiceInfo.UPDATEDEFINITION_OP, adapter, checkInstIds, checkDefDetails);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueUpdateDefinition(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mc.check.CheckServiceInfo.UPDATEDEFINITION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The removeCheck operation allows a consumer to remove one or more definitions
     * from the list of checks supported by the check provider.
     * The operation does not remove the CheckIdentity and actual check definition
     * objects from the COM archive, merely removes the objects from the provider.
     * This permits existing CheckLink objects to continue to reference the correct
     * check object in the COM archive.
     * 
     * @param objInstIds The objInstIds field holds the object instance identifiers of the CheckIdentity objects to be removed from the provider.
The list may contain the wildcard value of '0'.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a provided CheckIdentity instance identifier does not include a wildcard and does not match an existing check then this operation shall fail with an UNKNOWN error.
If any of the matched CheckIdentity objects are being referenced by a CheckLink object then a REFERENCED error shall be returned.
Matched CheckIdentity objects shall not be removed from the COM archive only the list of available CheckIdentity objects in the provider.
If an error is raised then no CheckIdentity objects shall be removed as a result of this operation call.
If the operation succeeds then the provider shall not allow new CheckLink objects to be created for the matched CheckIdentity anymore, existing CheckLink objects are not affected.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void removeCheck(org.ccsds.moims.mo.mal.structures.LongList objInstIds) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.mc.check.CheckServiceInfo.REMOVECHECK_OP, objInstIds);
    }

    /**
     * Asynchronous version of method removeCheck.
     * 
     * @param objInstIds The objInstIds field holds the object instance identifiers of the CheckIdentity objects to be removed from the provider.
The list may contain the wildcard value of '0'.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a provided CheckIdentity instance identifier does not include a wildcard and does not match an existing check then this operation shall fail with an UNKNOWN error.
If any of the matched CheckIdentity objects are being referenced by a CheckLink object then a REFERENCED error shall be returned.
Matched CheckIdentity objects shall not be removed from the COM archive only the list of available CheckIdentity objects in the provider.
If an error is raised then no CheckIdentity objects shall be removed as a result of this operation call.
If the operation succeeds then the provider shall not allow new CheckLink objects to be created for the matched CheckIdentity anymore, existing CheckLink objects are not affected.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncRemoveCheck(org.ccsds.moims.mo.mal.structures.LongList objInstIds,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.mc.check.CheckServiceInfo.REMOVECHECK_OP, adapter, objInstIds);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueRemoveCheck(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mc.check.CheckServiceInfo.REMOVECHECK_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The addParameterCheck operation allows a consumer to request that one or
     * more parameters/check combinations are added to the list of checks that
     * are being evaluated.
     * The new CheckLink and CheckLinkDefinition objects are expected to be stored
     * in the COM archive by the provider of the check service.
     * 
     * @param linkDetails The linkDetails field shall contain the new CheckLink details.
The linkRefs field shall contain the related and source links of the new CheckLink.
The related field of the ObjectDetails shall reference the object instance identifier of the CheckIdentity being used by the new CheckLink.
The source field of the ObjectDetails shall reference the ParameterIdentity that the check is being applied to.
The two lists must be ordered the same so that the correct ObjectDetails for a specific CheckLink can be determined.
     * @param linkRefs If the requested CheckIdentity and ParameterIdentity do not exist then an UNKNOWN error shall be returned.
The number of entries in the two lists shall be the same size otherwise an INVALID error shall be raised.
If an interval that is not supported by the provider is requested then an INVALID error shall be returned.
If the checkInterval is not '0' and the checkOnChange Value is TRUE, then an INVALID error shall be returned.
The provider shall create new CheckLink and CheckLinkDefinition objects for each pair and store them in the COM archive.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mc.structures.ObjectInstancePairList addParameterCheck(org.ccsds.moims.mo.mc.check.structures.CheckLinkDetailsList linkDetails,
            org.ccsds.moims.mo.com.structures.ObjectDetailsList linkRefs) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mc.check.CheckServiceInfo.ADDPARAMETERCHECK_OP, linkDetails, linkRefs);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mc.structures.ObjectInstancePairList());
        return (org.ccsds.moims.mo.mc.structures.ObjectInstancePairList) body0;
    }

    /**
     * Asynchronous version of method addParameterCheck.
     * 
     * @param linkDetails The linkDetails field shall contain the new CheckLink details.
The linkRefs field shall contain the related and source links of the new CheckLink.
The related field of the ObjectDetails shall reference the object instance identifier of the CheckIdentity being used by the new CheckLink.
The source field of the ObjectDetails shall reference the ParameterIdentity that the check is being applied to.
The two lists must be ordered the same so that the correct ObjectDetails for a specific CheckLink can be determined.
     * @param linkRefs If the requested CheckIdentity and ParameterIdentity do not exist then an UNKNOWN error shall be returned.
The number of entries in the two lists shall be the same size otherwise an INVALID error shall be raised.
If an interval that is not supported by the provider is requested then an INVALID error shall be returned.
If the checkInterval is not '0' and the checkOnChange Value is TRUE, then an INVALID error shall be returned.
The provider shall create new CheckLink and CheckLinkDefinition objects for each pair and store them in the COM archive.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncAddParameterCheck(org.ccsds.moims.mo.mc.check.structures.CheckLinkDetailsList linkDetails,
            org.ccsds.moims.mo.com.structures.ObjectDetailsList linkRefs,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mc.check.CheckServiceInfo.ADDPARAMETERCHECK_OP, adapter, linkDetails, linkRefs);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueAddParameterCheck(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mc.check.CheckServiceInfo.ADDPARAMETERCHECK_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The removeParameterCheck operation allows a consumer to remove one or more
     * parameters from the list of parameters being checked by the check provider.
     * The operation does not remove the CheckLink or CheckLinkDefinition objects
     * from the COM archive, merely removes them from the provider. This permits
     * existing CheckTransition events to continue to reference the correct check
     * link/definition objects in the COM archive.
     * 
     * @param objInstIds The objInstIds field holds the object instance identifiers of the CheckLink objects to be removed from the provider.
The list may contain the wildcard value of '0'.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a provided CheckLink instance identifier does not include a wildcard and does not match an existing link then this operation shall fail with an UNKNOWN error.
Matched CheckLink objects shall not be removed from the COM archive only the list of available CheckLink objects in the provider.
If an error is raised then no CheckLink objects shall be removed as a result of this operation call.
If the operation succeeds then the provider shall not evaluate those parameter/check definition combinations for the deleted CheckLink objects anymore.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void removeParameterCheck(org.ccsds.moims.mo.mal.structures.LongList objInstIds) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.mc.check.CheckServiceInfo.REMOVEPARAMETERCHECK_OP, objInstIds);
    }

    /**
     * Asynchronous version of method removeParameterCheck.
     * 
     * @param objInstIds The objInstIds field holds the object instance identifiers of the CheckLink objects to be removed from the provider.
The list may contain the wildcard value of '0'.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a provided CheckLink instance identifier does not include a wildcard and does not match an existing link then this operation shall fail with an UNKNOWN error.
Matched CheckLink objects shall not be removed from the COM archive only the list of available CheckLink objects in the provider.
If an error is raised then no CheckLink objects shall be removed as a result of this operation call.
If the operation succeeds then the provider shall not evaluate those parameter/check definition combinations for the deleted CheckLink objects anymore.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncRemoveParameterCheck(org.ccsds.moims.mo.mal.structures.LongList objInstIds,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.mc.check.CheckServiceInfo.REMOVEPARAMETERCHECK_OP, adapter, objInstIds);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueRemoveParameterCheck(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mc.check.consumer.CheckAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mc.check.CheckServiceInfo.REMOVEPARAMETERCHECK_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

}
