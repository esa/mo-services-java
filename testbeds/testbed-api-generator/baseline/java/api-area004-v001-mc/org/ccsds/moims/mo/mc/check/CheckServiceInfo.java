package org.ccsds.moims.mo.mc.check;

/**
 * Helper class for Check service.
 */
public class CheckServiceInfo extends org.ccsds.moims.mo.com.COMService {

    /**
     * Service number literal.
     */
    public static final int _CHECK_SERVICE_NUMBER = 4;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort CHECK_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_CHECK_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier CHECK_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("Check");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            4, 1, CHECK_SERVICE_NUMBER);

    /**
     * Operation number literal for operation GETCURRENTTRANSITIONLIST.
     */
    public static final int _GETCURRENTTRANSITIONLIST_OP_NUMBER = 1;

    /**
     * Operation number instance for operation GETCURRENTTRANSITIONLIST.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort GETCURRENTTRANSITIONLIST_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GETCURRENTTRANSITIONLIST_OP_NUMBER);

    /**
     * Operation instance for operation GETCURRENTTRANSITIONLIST.
     */
    public static final org.ccsds.moims.mo.mal.MALProgressOperation GETCURRENTTRANSITIONLIST_OP = new org.ccsds.moims.mo.mal.MALProgressOperation(SERVICE_KEY, 
            GETCURRENTTRANSITIONLIST_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("getCurrentTransitionList"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("filter", true, org.ccsds.moims.mo.mc.check.structures.CheckResultFilter.SHORT_FORM, "The filter field shall contain a set of object instance identifiers for which the check result is required.\nIf the checkFilterViaGroups field is TRUE then the checkFilter field shall contain GroupIdentity object instance identifiers, otherwise the field contains CheckIdentity object instance identifiers.\nThe CheckIdentity objects referenced, either directly or indirectly via groups, by the checkFilter field shall be the CheckIdentity objects to match.\nThe checkFilter field shall support the wildcard value of '0' and shall match all CheckIdentity objects of the provider.\nThe service provider shall check for the wildcard value in the list of object instance identifiers in the checkFilter field first and if found no other checks of supplied CheckIdentity object instance identifiers shall be made.\nIf the parameterFilterViaGroups field is TRUE then the parameterFilter field shall contain GroupIdentity object instance identifiers, otherwise the field contains ParameterIdentity object instance identifiers.\nThe ParameterIdentity objects referenced, either directly or indirectly via groups, by the parameterFilter field shall be the ParameterIdentity objects to match.\nThe parameterFilter field shall support the wildcard value of '0' and shall match all ParameterIdentity objects of the provider.\nThe service provider shall check for the wildcard value in the list of object instance identifiers in the parameterFilter field first and if found no other checks of supplied ParameterIdentity object instance identifiers shall be made.\nIf a referenced GroupIdentity object is unknown then an UNKNOWN error shall be returned.\nIf a requested Group, or the Group objects referenced by that Group, does not contain CheckIdentity objects for the checkFilter or ParameterIdentity for the parameterFilter then an INVALID error shall be returned.\nIf a referenced CheckIdentity object, either directly or indirectly via groups, is unknown then an UNKNOWN error shall be returned.\nIf a referenced ParameterIdentity object, either directly or indirectly via groups, is unknown then an UNKNOWN error shall be returned.\nThe filter field shall also contain a list of CheckState enumerations of which states to filter on.\nThe supplied lists shall be AND'd together to form the complete filter.\nIf a CheckLink object matches the CheckIdentity filter, and the ParameterIdentity filter, and its state matches any of the supplied CheckState enumerations, then its latest CheckResult value shall be returned.\nTo report all checks, the wildcard values may be used in the CheckResultFilter.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("updateSummaries", true, org.ccsds.moims.mo.mc.check.structures.CheckResultSummaryList.SHORT_FORM, "The returned list shall contain an entry for each matched check returning the object instance identifier and the latest CheckResult for that CheckLink object.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("responseSummaries", true, org.ccsds.moims.mo.mc.check.structures.CheckResultSummaryList.SHORT_FORM, "The PROGRESS pattern is used to allow the possibly large list of filtered check results to be split into several updates.\nThe size of the lists returned in each update and final response is implementation specific.")}, 
            "The getCurrentTransitionList operation allows a consumer to obtain the latest result of a number of checks filtering on check state.");

    /**
     * Operation number literal for operation GETSUMMARYREPORT.
     */
    public static final int _GETSUMMARYREPORT_OP_NUMBER = 2;

    /**
     * Operation number instance for operation GETSUMMARYREPORT.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort GETSUMMARYREPORT_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GETSUMMARYREPORT_OP_NUMBER);

    /**
     * Operation instance for operation GETSUMMARYREPORT.
     */
    public static final org.ccsds.moims.mo.mal.MALProgressOperation GETSUMMARYREPORT_OP = new org.ccsds.moims.mo.mal.MALProgressOperation(SERVICE_KEY, 
            GETSUMMARYREPORT_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("getSummaryReport"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("objInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The objInstIds field shall hold one or more CheckIdentity object instance identifiers of which a check report is required.\nA wildcard value of '0' shall report on all checks.\nThe wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.\nIf a requested check is unknown then an UNKNOWN error shall be returned in the ACKNOWLEDGE message and the operation shall end.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("updateObjInstIds", true, org.ccsds.moims.mo.mal.structures.Attribute.LONG_SHORT_FORM, "The returned updates and final response shall contain an entry for each requested CheckIdentity.\nThe first part of the update shall be the CheckIdentity object instance identifier.\nThe second part shall be the list of all CheckLink object instance identifiers and CheckResults associated with that CheckIdentity."),
                new org.ccsds.moims.mo.mal.OperationField("updateSummaries", true, org.ccsds.moims.mo.mc.check.structures.CheckResultSummaryList.SHORT_FORM, null)}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("responseObjInstIds", true, org.ccsds.moims.mo.mal.structures.Attribute.LONG_SHORT_FORM, null),
                new org.ccsds.moims.mo.mal.OperationField("responseSummaries", true, org.ccsds.moims.mo.mc.check.structures.CheckResultSummaryList.SHORT_FORM, null)}, 
            "The getSummaryReport operation allows a consumer to obtain the status of a number of checks and the result of any check evaluations linked to them.");

    /**
     * Operation number literal for operation ENABLESERVICE.
     */
    public static final int _ENABLESERVICE_OP_NUMBER = 3;

    /**
     * Operation number instance for operation ENABLESERVICE.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort ENABLESERVICE_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ENABLESERVICE_OP_NUMBER);

    /**
     * Operation instance for operation ENABLESERVICE.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation ENABLESERVICE_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            ENABLESERVICE_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("enableService"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("enableService", true, org.ccsds.moims.mo.mal.structures.Attribute.BOOLEAN_SHORT_FORM, "If enableService is set to TRUE the service shall be enabled and evaluation and reporting of check will commence.\nIf enableService is set to FALSE then all evaluation of checks shall be suspended and no check transitions will be reported.\nIf the enableService value matches the current enabled state of the service then no change shall be made and no error reported. Enabling an already enabled service has no effect.")}, 
            "The enableService operation allows a consumer to globally control whether evaluation of all checks is performed or not.\nIt should be noted that no check reports will be generated if the service provider has been disabled via the enableService operation.");

    /**
     * Operation number literal for operation GETSERVICESTATUS.
     */
    public static final int _GETSERVICESTATUS_OP_NUMBER = 4;

    /**
     * Operation number instance for operation GETSERVICESTATUS.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort GETSERVICESTATUS_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GETSERVICESTATUS_OP_NUMBER);

    /**
     * Operation instance for operation GETSERVICESTATUS.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation GETSERVICESTATUS_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            GETSERVICESTATUS_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("getServiceStatus"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("serviceEnabled", true, org.ccsds.moims.mo.mal.structures.Attribute.BOOLEAN_SHORT_FORM, "The operation shall return TRUE if the service is currently enabled or FALSE if the service is currently disabled.")}, 
            "The getServiceStatus operation allows a consumer to determine the global check service enabled status.");

    /**
     * Operation number literal for operation ENABLECHECK.
     */
    public static final int _ENABLECHECK_OP_NUMBER = 5;

    /**
     * Operation number instance for operation ENABLECHECK.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort ENABLECHECK_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ENABLECHECK_OP_NUMBER);

    /**
     * Operation instance for operation ENABLECHECK.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation ENABLECHECK_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            ENABLECHECK_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("enableCheck"), 
            new org.ccsds.moims.mo.mal.structures.UShort(3), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("isGroupIds", true, org.ccsds.moims.mo.mal.structures.Attribute.BOOLEAN_SHORT_FORM, "If the isGroupIds field is TRUE then the enableInstances field shall contain GroupIdentity object instance identifiers, otherwise the field contains CheckLink object instance identifiers.\nThe CheckLink objects referenced, either directly or indirectly via groups, by the enableInstances field shall be the CheckLink objects to match.\nThe id of the enableInstances field shall support the wildcard value of '0' and matches all CheckLink objects of the provider.\nThe service provider shall check for the wildcard value in the list of object instance identifiers in the enableInstances field first and if found no other checks of supplied object instance identifiers shall be made.\nIf the enableInstances field contains a value of TRUE then evaluations of matching CheckLink objects shall be performed, a value of FALSE requests that evaluations will not be performed.\nNo error shall be raised if the enableInstances Boolean value supplied is the same as the current checkEnabled field for a CheckLink object i.e. enabling an already enabled check will not result in an error.\nIf a requested CheckLink or GroupIdentity object is unknown then an UNKNOWN error shall be returned.\nIf a requested Group, or the Group objects referenced by that Group, does not contain CheckLink objects then an INVALID error shall be returned.\nIf an error is raised then no modifications shall be made as a result of this operation call.\nThe provider shall create and store a new CheckLinkDefinition object in the COM archive if the checkEnabled field is changed."),
                new org.ccsds.moims.mo.mal.OperationField("enableInstances", true, org.ccsds.moims.mo.com.structures.InstanceBooleanPairList.SHORT_FORM, "If the check is being enabled, and the check is defined as being periodic in the check link definition, then the provider shall generate a check result immediately and start the checking interval from that check.")}, 
            "The enableCheck operation allows a consumer to control whether evaluation of a set of checks is performed or not. The operation allows the consumer to select the checks directly or indirectly using groups.");

    /**
     * Operation number literal for operation TRIGGERCHECK.
     */
    public static final int _TRIGGERCHECK_OP_NUMBER = 6;

    /**
     * Operation number instance for operation TRIGGERCHECK.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort TRIGGERCHECK_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TRIGGERCHECK_OP_NUMBER);

    /**
     * Operation instance for operation TRIGGERCHECK.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation TRIGGERCHECK_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            TRIGGERCHECK_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("triggerCheck"), 
            new org.ccsds.moims.mo.mal.structures.UShort(4), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("checkObjInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The checkObjInstIds field shall hold a list of CheckIdentity object instance identifiers to trigger the evaluation of all linked checks."),
                new org.ccsds.moims.mo.mal.OperationField("linkObjInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The linkObjInstIds field shall hold a list of CheckLink object instance identifiers to trigger the evaluation of.\nThe wildcard value of '0' shall be permitted in either list.\nThe wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.\nIf a requested CheckIdentity or CheckLink object is unknown then an UNKNOWN error shall be returned.\nIf an error is raised then no evaluations shall be made as a result of this operation call.\nEither list may be empty in which case filtering on that aspect, check identity or specific check link, shall be ignored.\nThe two lists shall be combined using 'OR' logic, where a CheckLink is evaluated if the identity of a check is in the first list or if the link is directly listed in the second list.\nTriggering a check shall ignore the nominalTime, nominalCount, violationTime and violationCount fields and requests an immediate evaluation of the checks.\nTriggering a check during a periodic check shall not influence the periodic check (e.g. it does not reset the checkInterval timer, the successive valid samples that passed/violated the check or the maxReportingInterval timer).")}, 
            "The triggerCheck operation allows a consumer to request the immediate evaluation of a number of checks. Any violations will cause appropriate events to be generated.\nIt should be noted that no check reports will be generated if the service provider has been disabled via the enableService operation.");

    /**
     * Operation number literal for operation LISTDEFINITION.
     */
    public static final int _LISTDEFINITION_OP_NUMBER = 7;

    /**
     * Operation number instance for operation LISTDEFINITION.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort LISTDEFINITION_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_LISTDEFINITION_OP_NUMBER);

    /**
     * Operation instance for operation LISTDEFINITION.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation LISTDEFINITION_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            LISTDEFINITION_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("listDefinition"), 
            new org.ccsds.moims.mo.mal.structures.UShort(5), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("names", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, "The names field shall hold a list of CheckIdentity names to retrieve the CheckIdentity and actual check definition object instance identifiers for.\nThe request may contain the wildcard value of '*' to return all supported check definitions.\nThe wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.\nIf a provided identifier does not include a wildcard and does not match an existing CheckIdentity object then this operation shall fail with an UNKNOWN error.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("objInstIds", true, org.ccsds.moims.mo.mc.check.structures.CheckTypedInstanceList.SHORT_FORM, "The response shall contain a list of matching CheckIdentity and actual check definition object instance identifiers and the actual check definition object type.\nThe returned list shall maintain the same order as the submitted list unless the wildcard value was included in the request.")}, 
            "The listDefinition operation allows a consumer to request the latest object instance identifiers of the CheckIdentity and actual check definition objects for the supported checks of the provider.");

    /**
     * Operation number literal for operation LISTCHECKLINKS.
     */
    public static final int _LISTCHECKLINKS_OP_NUMBER = 8;

    /**
     * Operation number instance for operation LISTCHECKLINKS.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort LISTCHECKLINKS_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_LISTCHECKLINKS_OP_NUMBER);

    /**
     * Operation instance for operation LISTCHECKLINKS.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation LISTCHECKLINKS_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            LISTCHECKLINKS_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("listCheckLinks"), 
            new org.ccsds.moims.mo.mal.structures.UShort(5), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("checkObjInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The checkObjInstIds field shall hold a list of CheckIdentity object instance identifiers to retrieve the CheckLink object instance identifiers for.\nThe request may contain the wildcard value of '0' to return all supported check links.\nThe wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.\nIf a provided identifier does not include a wildcard and does not match an existing CheckIdentity object then this operation shall fail with an UNKNOWN error.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("chkLinkObjInstIds", true, org.ccsds.moims.mo.mc.check.structures.CheckLinkSummaryList.SHORT_FORM, "The response shall contain a list of CheckLinkSummary that contain the object instance identifiers of the CheckLink, CheckIdentity, and ParameterIdentity for the matched CheckIdentity objects.")}, 
            "The listCheckLinks operation allows a consumer to request the object instance identifiers of the CheckLink objects for the checks of the provider.");

    /**
     * Operation number literal for operation ADDCHECK.
     */
    public static final int _ADDCHECK_OP_NUMBER = 9;

    /**
     * Operation number instance for operation ADDCHECK.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort ADDCHECK_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ADDCHECK_OP_NUMBER);

    /**
     * Operation instance for operation ADDCHECK.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation ADDCHECK_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            ADDCHECK_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("addCheck"), 
            new org.ccsds.moims.mo.mal.structures.UShort(6), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("checkNames", true, org.ccsds.moims.mo.mal.structures.StringList.SHORT_FORM, "The checkNames field shall hold the names of the checks to be added.\nThe checkNames field must not contain NULL, the wildcard '*', or empty value (an INVALID error shall be returned in this case).\nThe supplied names must be unique among all CheckIdentity objects for the domain of the provider otherwise a DUPLICATE error shall be raised."),
                new org.ccsds.moims.mo.mal.OperationField("checkDefDetails", true, null, "The checkDefDetails field shall hold the CheckDefinitionDetails to be added.\nThe two lists shall be ordered the same.\nThe number of entries in the two lists shall be the same size otherwise an INVALID error shall be raised.\nOnly one of nominalTime and nominalCount is permitted to be zero, an INVALID error shall be returned if this is not the case.\nOnly one of violationTime and violationCount is permitted to be zero, an INVALID error shall be returned if this is not the case.\nIf an error is raised then no new identities and definitions shall be added as a result of this operation call.\nIf the supplied name matches an existing, but removed, CheckIdentity then that CheckIdentity shall be reused otherwise a new CheckIdentity shall be created.\nThe provider shall create a new actual check definition object and store it, and any new CheckIdentity objects, in the COM archive.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("newObjInstIds", true, org.ccsds.moims.mo.mc.structures.ObjectInstancePairList.SHORT_FORM, "The response shall contain the list of object instance identifiers for the CheckIdentity and new actual definition objects.\nThe returned list shall maintain the same order as the submitted definitions.")}, 
            "The addCheck operation allows a consumer to define one or more checks that do not currently exist.\nThe new CheckIdentity and actual check definition objects are expected to be stored in the COM archive by the provider of the check service.");

    /**
     * Operation number literal for operation UPDATEDEFINITION.
     */
    public static final int _UPDATEDEFINITION_OP_NUMBER = 10;

    /**
     * Operation number instance for operation UPDATEDEFINITION.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort UPDATEDEFINITION_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_UPDATEDEFINITION_OP_NUMBER);

    /**
     * Operation instance for operation UPDATEDEFINITION.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation UPDATEDEFINITION_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            UPDATEDEFINITION_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("updateDefinition"), 
            new org.ccsds.moims.mo.mal.structures.UShort(6), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("checkInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The checkInstIds field shall hold the object instance identifiers of the CheckIdentity objects to be updated.\nIf the checkInstIds list contains either NULL or '0' an INVALID error shall be raised.\nThe supplied object instance identifiers shall match existing identity objects, an UNKNOWN error shall be raised if this is not the case.\nIf the check to be updated is currently being used by a CheckLink object, a REFERENCED error shall be raised."),
                new org.ccsds.moims.mo.mal.OperationField("checkDefDetails", true, null, "The checkDefDetails field shall contain the replacement CheckDefinitionDetails.\nThe two lists shall be ordered the same.\nThe number of entries in the two lists shall be the same size otherwise an INVALID error shall be raised.\nOnly one of nominalTime and nominalCount is permitted to be zero, an INVALID error shall be returned if this is not the case.\nOnly one of violationTime and violationCount is permitted to be zero, an INVALID error shall be returned if this is not the case.\nIf an error is raised then no definitions shall be updated as a result of this operation call.\nThe provider shall create new actual check definition objects and store them in the COM archive.\nThe new definition object shall be the current definition used for the specific CheckIdentity.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("newObjInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The response shall contain the list of object instance identifiers for the new check definition objects.\nThe returned list shall maintain the same order as the submitted definitions.")}, 
            "The updateDefinition operation allows a consumer to update a definition for one or more checks.\nThis differs from deleting an existing check and adding a new definition with the same check name in the fact that the CheckIdentity object is not changed between the two definitions.\nThe replacement definition should be stored in the COM archive by the service provider. The operation does not remove the previous object from the COM archive, merely removes the object from the provider.\nThe operation also cannot be used to update a check definition for a check that is currently being used i.e. has CheckLink objects linked to it. The CheckLink objects should first be removed using removeParameterCheck before calling this operation.");

    /**
     * Operation number literal for operation REMOVECHECK.
     */
    public static final int _REMOVECHECK_OP_NUMBER = 11;

    /**
     * Operation number instance for operation REMOVECHECK.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort REMOVECHECK_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_REMOVECHECK_OP_NUMBER);

    /**
     * Operation instance for operation REMOVECHECK.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation REMOVECHECK_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            REMOVECHECK_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("removeCheck"), 
            new org.ccsds.moims.mo.mal.structures.UShort(6), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("objInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The objInstIds field holds the object instance identifiers of the CheckIdentity objects to be removed from the provider.\nThe list may contain the wildcard value of '0'.\nThe wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.\nIf a provided CheckIdentity instance identifier does not include a wildcard and does not match an existing check then this operation shall fail with an UNKNOWN error.\nIf any of the matched CheckIdentity objects are being referenced by a CheckLink object then a REFERENCED error shall be returned.\nMatched CheckIdentity objects shall not be removed from the COM archive only the list of available CheckIdentity objects in the provider.\nIf an error is raised then no CheckIdentity objects shall be removed as a result of this operation call.\nIf the operation succeeds then the provider shall not allow new CheckLink objects to be created for the matched CheckIdentity anymore, existing CheckLink objects are not affected.")}, 
            "The removeCheck operation allows a consumer to remove one or more definitions from the list of checks supported by the check provider.\nThe operation does not remove the CheckIdentity and actual check definition objects from the COM archive, merely removes the objects from the provider. This permits existing CheckLink objects to continue to reference the correct check object in the COM archive.");

    /**
     * Operation number literal for operation ADDPARAMETERCHECK.
     */
    public static final int _ADDPARAMETERCHECK_OP_NUMBER = 12;

    /**
     * Operation number instance for operation ADDPARAMETERCHECK.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort ADDPARAMETERCHECK_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ADDPARAMETERCHECK_OP_NUMBER);

    /**
     * Operation instance for operation ADDPARAMETERCHECK.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation ADDPARAMETERCHECK_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            ADDPARAMETERCHECK_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("addParameterCheck"), 
            new org.ccsds.moims.mo.mal.structures.UShort(7), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("linkDetails", true, org.ccsds.moims.mo.mc.check.structures.CheckLinkDetailsList.SHORT_FORM, "The linkDetails field shall contain the new CheckLink details.\nThe linkRefs field shall contain the related and source links of the new CheckLink.\nThe related field of the ObjectDetails shall reference the object instance identifier of the CheckIdentity being used by the new CheckLink.\nThe source field of the ObjectDetails shall reference the ParameterIdentity that the check is being applied to.\nThe two lists must be ordered the same so that the correct ObjectDetails for a specific CheckLink can be determined."),
                new org.ccsds.moims.mo.mal.OperationField("linkRefs", true, org.ccsds.moims.mo.com.structures.ObjectDetailsList.SHORT_FORM, "If the requested CheckIdentity and ParameterIdentity do not exist then an UNKNOWN error shall be returned.\nThe number of entries in the two lists shall be the same size otherwise an INVALID error shall be raised.\nIf an interval that is not supported by the provider is requested then an INVALID error shall be returned.\nIf the checkInterval is not '0' and the checkOnChange Value is TRUE, then an INVALID error shall be returned.\nThe provider shall create new CheckLink and CheckLinkDefinition objects for each pair and store them in the COM archive.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("newObjInstIds", true, org.ccsds.moims.mo.mc.structures.ObjectInstancePairList.SHORT_FORM, "The response shall contain the list of object instance identifiers for the new CheckLink and CheckLinkDefinition objects.\nThe returned list shall maintain the same order as the submitted links.")}, 
            "The addParameterCheck operation allows a consumer to request that one or more parameters/check combinations are added to the list of checks that are being evaluated.\nThe new CheckLink and CheckLinkDefinition objects are expected to be stored in the COM archive by the provider of the check service.");

    /**
     * Operation number literal for operation REMOVEPARAMETERCHECK.
     */
    public static final int _REMOVEPARAMETERCHECK_OP_NUMBER = 13;

    /**
     * Operation number instance for operation REMOVEPARAMETERCHECK.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort REMOVEPARAMETERCHECK_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_REMOVEPARAMETERCHECK_OP_NUMBER);

    /**
     * Operation instance for operation REMOVEPARAMETERCHECK.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation REMOVEPARAMETERCHECK_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            REMOVEPARAMETERCHECK_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("removeParameterCheck"), 
            new org.ccsds.moims.mo.mal.structures.UShort(7), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("objInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The objInstIds field holds the object instance identifiers of the CheckLink objects to be removed from the provider.\nThe list may contain the wildcard value of '0'.\nThe wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.\nIf a provided CheckLink instance identifier does not include a wildcard and does not match an existing link then this operation shall fail with an UNKNOWN error.\nMatched CheckLink objects shall not be removed from the COM archive only the list of available CheckLink objects in the provider.\nIf an error is raised then no CheckLink objects shall be removed as a result of this operation call.\nIf the operation succeeds then the provider shall not evaluate those parameter/check definition combinations for the deleted CheckLink objects anymore.")}, 
            "The removeParameterCheck operation allows a consumer to remove one or more parameters from the list of parameters being checked by the check provider.\nThe operation does not remove the CheckLink or CheckLinkDefinition objects from the COM archive, merely removes them from the provider. This permits existing CheckTransition events to continue to reference the correct check link/definition objects in the COM archive.");

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] CHECK_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{GETCURRENTTRANSITIONLIST_OP,
        GETSUMMARYREPORT_OP,
        ENABLESERVICE_OP,
        GETSERVICESTATUS_OP,
        ENABLECHECK_OP,
        TRIGGERCHECK_OP,
        LISTDEFINITION_OP,
        LISTCHECKLINKS_OP,
        ADDCHECK_OP,
        UPDATEDEFINITION_OP,
        REMOVECHECK_OP,
        ADDPARAMETERCHECK_OP,
        REMOVEPARAMETERCHECK_OP};

    /**
     * Literal for object CHECKIDENTITY.
     */
    @Deprecated
    public static final int _CHECKIDENTITY_OBJECT_NUMBER = 1;

    /**
     * Instance for object CHECKIDENTITY.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort CHECKIDENTITY_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_CHECKIDENTITY_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier CHECKIDENTITY_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("CheckIdentity");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType CHECKIDENTITY_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), CHECK_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), CHECKIDENTITY_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject CHECKIDENTITY_OBJECT = new org.ccsds.moims.mo.com.COMObject(CHECKIDENTITY_OBJECT_TYPE, CHECKIDENTITY_OBJECT_NAME, org.ccsds.moims.mo.mal.structures.Attribute.IDENTIFIER_SHORT_FORM, false, null, false, null, false);

    /**
     * Literal for object CHECKLINK.
     */
    @Deprecated
    public static final int _CHECKLINK_OBJECT_NUMBER = 2;

    /**
     * Instance for object CHECKLINK.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort CHECKLINK_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_CHECKLINK_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier CHECKLINK_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("CheckLink");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType CHECKLINK_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), CHECK_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), CHECKLINK_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject CHECKLINK_OBJECT = new org.ccsds.moims.mo.com.COMObject(CHECKLINK_OBJECT_TYPE, CHECKLINK_OBJECT_NAME, null, true, org.ccsds.moims.mo.mc.check.CheckServiceInfo.CHECKIDENTITY_OBJECT_TYPE, true, org.ccsds.moims.mo.mc.parameter.ParameterServiceInfo.PARAMETERIDENTITY_OBJECT_TYPE, false);

    /**
     * Literal for object CHECKLINKDEFINITION.
     */
    @Deprecated
    public static final int _CHECKLINKDEFINITION_OBJECT_NUMBER = 3;

    /**
     * Instance for object CHECKLINKDEFINITION.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort CHECKLINKDEFINITION_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_CHECKLINKDEFINITION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier CHECKLINKDEFINITION_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("CheckLinkDefinition");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType CHECKLINKDEFINITION_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), CHECK_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), CHECKLINKDEFINITION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject CHECKLINKDEFINITION_OBJECT = new org.ccsds.moims.mo.com.COMObject(CHECKLINKDEFINITION_OBJECT_TYPE, CHECKLINKDEFINITION_OBJECT_NAME, org.ccsds.moims.mo.mc.check.structures.CheckLinkDetails.SHORT_FORM, true, org.ccsds.moims.mo.mc.check.CheckServiceInfo.CHECKLINK_OBJECT_TYPE, false, null, false);

    /**
     * Literal for object CONSTANTCHECK.
     */
    @Deprecated
    public static final int _CONSTANTCHECK_OBJECT_NUMBER = 5;

    /**
     * Instance for object CONSTANTCHECK.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort CONSTANTCHECK_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_CONSTANTCHECK_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier CONSTANTCHECK_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ConstantCheck");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType CONSTANTCHECK_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), CHECK_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), CONSTANTCHECK_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject CONSTANTCHECK_OBJECT = new org.ccsds.moims.mo.com.COMObject(CONSTANTCHECK_OBJECT_TYPE, CONSTANTCHECK_OBJECT_NAME, org.ccsds.moims.mo.mc.check.structures.ConstantCheckDefinition.SHORT_FORM, true, org.ccsds.moims.mo.mc.check.CheckServiceInfo.CHECKIDENTITY_OBJECT_TYPE, false, null, false);

    /**
     * Literal for object REFERENCECHECK.
     */
    @Deprecated
    public static final int _REFERENCECHECK_OBJECT_NUMBER = 6;

    /**
     * Instance for object REFERENCECHECK.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort REFERENCECHECK_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_REFERENCECHECK_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier REFERENCECHECK_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ReferenceCheck");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType REFERENCECHECK_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), CHECK_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), REFERENCECHECK_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject REFERENCECHECK_OBJECT = new org.ccsds.moims.mo.com.COMObject(REFERENCECHECK_OBJECT_TYPE, REFERENCECHECK_OBJECT_NAME, org.ccsds.moims.mo.mc.check.structures.ReferenceCheckDefinition.SHORT_FORM, true, org.ccsds.moims.mo.mc.check.CheckServiceInfo.CHECKIDENTITY_OBJECT_TYPE, false, null, false);

    /**
     * Literal for object DELTACHECK.
     */
    @Deprecated
    public static final int _DELTACHECK_OBJECT_NUMBER = 7;

    /**
     * Instance for object DELTACHECK.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort DELTACHECK_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_DELTACHECK_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier DELTACHECK_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("DeltaCheck");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType DELTACHECK_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), CHECK_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), DELTACHECK_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject DELTACHECK_OBJECT = new org.ccsds.moims.mo.com.COMObject(DELTACHECK_OBJECT_TYPE, DELTACHECK_OBJECT_NAME, org.ccsds.moims.mo.mc.check.structures.DeltaCheckDefinition.SHORT_FORM, true, org.ccsds.moims.mo.mc.check.CheckServiceInfo.CHECKIDENTITY_OBJECT_TYPE, false, null, false);

    /**
     * Literal for object LIMITCHECK.
     */
    @Deprecated
    public static final int _LIMITCHECK_OBJECT_NUMBER = 8;

    /**
     * Instance for object LIMITCHECK.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort LIMITCHECK_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_LIMITCHECK_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier LIMITCHECK_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("LimitCheck");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType LIMITCHECK_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), CHECK_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), LIMITCHECK_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject LIMITCHECK_OBJECT = new org.ccsds.moims.mo.com.COMObject(LIMITCHECK_OBJECT_TYPE, LIMITCHECK_OBJECT_NAME, org.ccsds.moims.mo.mc.check.structures.LimitCheckDefinition.SHORT_FORM, true, org.ccsds.moims.mo.mc.check.CheckServiceInfo.CHECKIDENTITY_OBJECT_TYPE, false, null, false);

    /**
     * Literal for object COMPOUNDCHECK.
     */
    @Deprecated
    public static final int _COMPOUNDCHECK_OBJECT_NUMBER = 9;

    /**
     * Instance for object COMPOUNDCHECK.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort COMPOUNDCHECK_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_COMPOUNDCHECK_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier COMPOUNDCHECK_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("CompoundCheck");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType COMPOUNDCHECK_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), CHECK_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), COMPOUNDCHECK_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject COMPOUNDCHECK_OBJECT = new org.ccsds.moims.mo.com.COMObject(COMPOUNDCHECK_OBJECT_TYPE, COMPOUNDCHECK_OBJECT_NAME, org.ccsds.moims.mo.mc.check.structures.CompoundCheckDefinition.SHORT_FORM, true, org.ccsds.moims.mo.mc.check.CheckServiceInfo.CHECKIDENTITY_OBJECT_TYPE, false, null, false);

    /**
     * Literal for object CHECKTRANSITION.
     */
    @Deprecated
    public static final int _CHECKTRANSITION_OBJECT_NUMBER = 4;

    /**
     * Instance for object CHECKTRANSITION.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort CHECKTRANSITION_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_CHECKTRANSITION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier CHECKTRANSITION_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("CheckTransition");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType CHECKTRANSITION_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), CHECK_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), CHECKTRANSITION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject CHECKTRANSITION_OBJECT = new org.ccsds.moims.mo.com.COMObject(CHECKTRANSITION_OBJECT_TYPE, CHECKTRANSITION_OBJECT_NAME, org.ccsds.moims.mo.mc.check.structures.CheckResult.SHORT_FORM, true, org.ccsds.moims.mo.mc.check.CheckServiceInfo.CHECKLINKDEFINITION_OBJECT_TYPE, true, null, true);

    /**
     * Object instance.
     */
    public static final org.ccsds.moims.mo.com.COMObject[] COM_OBJECTS = {
        CHECKIDENTITY_OBJECT,
        CHECKLINK_OBJECT,
        CHECKLINKDEFINITION_OBJECT,
        CONSTANTCHECK_OBJECT,
        REFERENCECHECK_OBJECT,
        DELTACHECK_OBJECT,
        LIMITCHECK_OBJECT,
        COMPOUNDCHECK_OBJECT,
        CHECKTRANSITION_OBJECT,};

    /**
     * Creates an instance of the Check ServiceInfo.
     * 
     */
    public CheckServiceInfo() {
        super(SERVICE_KEY, CHECK_SERVICE_NAME, CHECK_SERVICE_ELEMENTS, OPERATIONS, COM_OBJECTS);
    }

    @Override
    public org.ccsds.moims.mo.mal.MALArea getArea() {
        return org.ccsds.moims.mo.mc.MCHelper.MC_AREA;
    }

    @Override
    public org.ccsds.moims.mo.mal.MOErrorException generateMOError(int errorNumber,
            Object extraInfo) {
        switch (errorNumber) {
            case 70020:
                return new org.ccsds.moims.mo.mc.ReadonlyException(extraInfo);
            case 70021:
                return new org.ccsds.moims.mo.mc.ReferencedException(extraInfo);
        }
        return null;
    }

}
