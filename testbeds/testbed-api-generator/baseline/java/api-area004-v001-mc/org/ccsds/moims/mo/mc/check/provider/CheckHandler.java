package org.ccsds.moims.mo.mc.check.provider;

/**
 * Interface that providers of the Check service must implement to handle
 * the operations of that service.
 */
public interface CheckHandler {

    /**
     * Implements the operation getCurrentTransitionList.
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
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.com.InvalidException One of the referenced groups does not contain the correct type of object.
     * @throws org.ccsds.moims.mo.mal.UnknownException One or more of the checks, parameters, or groups specified in the list do not exist.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void getCurrentTransitionList(org.ccsds.moims.mo.mc.check.structures.CheckResultFilter filter,
            org.ccsds.moims.mo.mc.check.provider.GetCurrentTransitionListInteraction interaction) throws org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation getSummaryReport.
     * 
     * @param objInstIds The objInstIds field shall hold one or more CheckIdentity object instance identifiers of which a check report is required.
A wildcard value of '0' shall report on all checks.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a requested check is unknown then an UNKNOWN error shall be returned in the ACKNOWLEDGE message and the operation shall end.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.UnknownException One or more of the checks specified in the list do not exist.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void getSummaryReport(org.ccsds.moims.mo.mal.structures.LongList objInstIds,
            org.ccsds.moims.mo.mc.check.provider.GetSummaryReportInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation enableService.
     * 
     * @param enableService If enableService is set to TRUE the service shall be enabled and evaluation and reporting of check will commence.
If enableService is set to FALSE then all evaluation of checks shall be suspended and no check transitions will be reported.
If the enableService value matches the current enabled state of the service then no change shall be made and no error reported. Enabling an already enabled service has no effect.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void enableService(Boolean enableService,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation getServiceStatus.
     * 
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    Boolean getServiceStatus(org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation enableCheck.
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
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.UnknownException One or more of the requested groups or CheckLink objects is unknown.
     * @throws org.ccsds.moims.mo.com.InvalidException One of the supplied groups is not a group of either other group objects or CheckLink objects.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void enableCheck(Boolean isGroupIds,
            org.ccsds.moims.mo.com.structures.InstanceBooleanPairList enableInstances,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation triggerCheck.
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
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.UnknownException One or more of the requested CheckIdentity or CheckLink objects is unknown.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void triggerCheck(org.ccsds.moims.mo.mal.structures.LongList checkObjInstIds,
            org.ccsds.moims.mo.mal.structures.LongList linkObjInstIds,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation listDefinition.
     * 
     * @param names The names field shall hold a list of CheckIdentity names to retrieve the CheckIdentity and actual check definition object instance identifiers for.
The request may contain the wildcard value of '*' to return all supported check definitions.
The wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.
If a provided identifier does not include a wildcard and does not match an existing CheckIdentity object then this operation shall fail with an UNKNOWN error.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.UnknownException One of the supplied identifiers is unknown.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mc.check.structures.CheckTypedInstanceList listDefinition(org.ccsds.moims.mo.mal.structures.IdentifierList names,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation listCheckLinks.
     * 
     * @param checkObjInstIds The checkObjInstIds field shall hold a list of CheckIdentity object instance identifiers to retrieve the CheckLink object instance identifiers for.
The request may contain the wildcard value of '0' to return all supported check links.
The wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.
If a provided identifier does not include a wildcard and does not match an existing CheckIdentity object then this operation shall fail with an UNKNOWN error.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.UnknownException One of the supplied identifiers is unknown.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mc.check.structures.CheckLinkSummaryList listCheckLinks(org.ccsds.moims.mo.mal.structures.LongList checkObjInstIds,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation addCheck.
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
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.com.InvalidException One of the supplied CheckIdentity objects contains an invalid name or the two lists are not the same size or there is an inconsistency in the time and count fields.
If the two lists are not the same length then the extra information field shall contain the first index of the element in the largest list which does not have corresponding element in the other list.
     * @throws org.ccsds.moims.mo.com.DuplicateException One or more of the CheckIdentity objects being added has supplied a check name that is already in use in the domain.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mc.structures.ObjectInstancePairList addCheck(org.ccsds.moims.mo.mal.structures.StringList checkNames,
            org.ccsds.moims.mo.mc.check.structures.CheckDefinitionDetailsList checkDefDetails,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.com.DuplicateException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation updateDefinition.
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
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.UnknownException One of the supplied CheckIdentity object instance identifiers is unknown.
     * @throws org.ccsds.moims.mo.com.InvalidException The supplied object instance identifiers list contains either a NULL or '0', or the two lists do not contain the same number of entries or there is an inconsistency in the time and count fields.
If the two lists are not the same length then the extra information field shall contain the first index of the element in the largest list which does not have corresponding element in the other list.
     * @throws org.ccsds.moims.mo.mc.ReferencedException One of the check objects is currently being used by a CheckLink object.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.LongList updateDefinition(org.ccsds.moims.mo.mal.structures.LongList checkInstIds,
            org.ccsds.moims.mo.mc.check.structures.CheckDefinitionDetailsList checkDefDetails,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mc.ReferencedException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation removeCheck.
     * 
     * @param objInstIds The objInstIds field holds the object instance identifiers of the CheckIdentity objects to be removed from the provider.
The list may contain the wildcard value of '0'.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a provided CheckIdentity instance identifier does not include a wildcard and does not match an existing check then this operation shall fail with an UNKNOWN error.
If any of the matched CheckIdentity objects are being referenced by a CheckLink object then a REFERENCED error shall be returned.
Matched CheckIdentity objects shall not be removed from the COM archive only the list of available CheckIdentity objects in the provider.
If an error is raised then no CheckIdentity objects shall be removed as a result of this operation call.
If the operation succeeds then the provider shall not allow new CheckLink objects to be created for the matched CheckIdentity anymore, existing CheckLink objects are not affected.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mc.ReferencedException One of the check objects is currently being used by a CheckLink object.
     * @throws org.ccsds.moims.mo.mal.UnknownException One of the supplied CheckIdentity object instance identifiers is unknown.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void removeCheck(org.ccsds.moims.mo.mal.structures.LongList objInstIds,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mc.ReferencedException, org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation addParameterCheck.
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
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.com.InvalidException The supplied lists do not contain the same number of entries, the supplied interval is not supported by the provider, or a period check with changed based checking has been requested.
If the two lists are not the same length then the extra information field shall contain the first index of the element in the largest list which does not have corresponding element in the other list.
     * @throws org.ccsds.moims.mo.mal.UnknownException One of the supplied object instance identifiers is unknown.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mc.structures.ObjectInstancePairList addParameterCheck(org.ccsds.moims.mo.mc.check.structures.CheckLinkDetailsList linkDetails,
            org.ccsds.moims.mo.com.structures.ObjectDetailsList linkRefs,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation removeParameterCheck.
     * 
     * @param objInstIds The objInstIds field holds the object instance identifiers of the CheckLink objects to be removed from the provider.
The list may contain the wildcard value of '0'.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a provided CheckLink instance identifier does not include a wildcard and does not match an existing link then this operation shall fail with an UNKNOWN error.
Matched CheckLink objects shall not be removed from the COM archive only the list of available CheckLink objects in the provider.
If an error is raised then no CheckLink objects shall be removed as a result of this operation call.
If the operation succeeds then the provider shall not evaluate those parameter/check definition combinations for the deleted CheckLink objects anymore.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.UnknownException One of the supplied CheckLink object instance identifiers is unknown.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void removeParameterCheck(org.ccsds.moims.mo.mal.structures.LongList objInstIds,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.mc.check.provider.CheckSkeleton skeleton);
}
