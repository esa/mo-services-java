package org.ccsds.moims.mo.mc.action;

/**
 * Helper class for Action service.
 */
public class ActionServiceInfo extends org.ccsds.moims.mo.com.COMService {

    /**
     * Service number literal.
     */
    public static final int _ACTION_SERVICE_NUMBER = 1;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort ACTION_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ACTION_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier ACTION_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("Action");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            4, 1, ACTION_SERVICE_NUMBER);

    /**
     * Operation number literal for operation SUBMITACTION.
     */
    public static final int _SUBMITACTION_OP_NUMBER = 1;

    /**
     * Operation number instance for operation SUBMITACTION.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort SUBMITACTION_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_SUBMITACTION_OP_NUMBER);

    /**
     * Operation instance for operation SUBMITACTION.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation SUBMITACTION_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            SUBMITACTION_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("submitAction"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("actionInstId", true, org.ccsds.moims.mo.mal.structures.Attribute.LONG_SHORT_FORM, "The actionInstId field of the submission shall contain the object instance identifier of the ActionInstance to be used for activity tracking events."),
                new org.ccsds.moims.mo.mal.OperationField("actionDetails", true, org.ccsds.moims.mo.mc.action.structures.ActionInstanceDetails.SHORT_FORM, "The actionDetails part of the submission shall contain the argument values and related information of the action instance to be executed.\nIf the defInstId of the supplied actionDetails field does not match a known ActionDefinition object then an UNKNOWN error shall be returned.\nThe size of the argumentValues list of the ActionInstanceDetails structure shall be compared to the size of the argument list in the matched ActionDefinition object and an INVALID error shall be returned if they are not the same.\nIf the ActionInstanceDetails structure contains an argumentIds field value then this shall be compared to the same field in the matched ActionDefinition object and must be the same size and contain the same values, an INVALID error shall be returned if this is not the case.\nIf the ActionInstanceDetails structure contains an isRawValue field value then the size of this list shall be compared to the size of the argument list in the matched ActionDefinition object and an INVALID error shall be returned if they are not the same.\nIf the supplied argument values do not match the attribute type specified in the action definition then an INVALID error shall be returned.\nA service provider may apply some deployment specific checks to the action instance and can return an INVALID error if they fail.\nIf an error is raised then no action shall be executed.\nThe SUBMIT acknowledgement shall be returned once the action has been accepted for execution but before execution starts.")}, 
            "The submitAction operation allows a consumer to submit an action to a provider for remote execution.");

    /**
     * Operation number literal for operation PRECHECKACTION.
     */
    public static final int _PRECHECKACTION_OP_NUMBER = 2;

    /**
     * Operation number instance for operation PRECHECKACTION.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort PRECHECKACTION_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_PRECHECKACTION_OP_NUMBER);

    /**
     * Operation instance for operation PRECHECKACTION.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation PRECHECKACTION_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            PRECHECKACTION_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("preCheckAction"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("actionDetails", true, org.ccsds.moims.mo.mc.action.structures.ActionInstanceDetails.SHORT_FORM, "The actionDetails part of the submission shall contain the argument values and related information of the action instance to be executed.\nIf the ActionInstanceDetails structure contains an argumentIds field value then this shall be compared to the same field in the matched ActionDefinition object and must be the same size and contain the same values, an INVALID error shall be returned if this is not the case.\nIf the supplied argument values do not match the attribute type specified in the action definition then an INVALID error shall be returned.\nA service provider may apply some deployment specific checks to the action instance and can return an INVALID error if they fail.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("accepted", true, org.ccsds.moims.mo.mal.structures.Attribute.BOOLEAN_SHORT_FORM, "The returned Boolean shall be set to TRUE if the action would be accepted successfully; otherwise the operation shall return FALSE.")}, 
            "The preCheckAction operation allows a consumer to check that an action would be successfully accepted for execution without actually submitting the action. The operation is expected to be provided by local action proxies rather than the remote system to allow for quick local checks before sending the action over long and slow space links.");

    /**
     * Operation number literal for operation LISTDEFINITION.
     */
    public static final int _LISTDEFINITION_OP_NUMBER = 3;

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
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("actionNames", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, "The actionNames field shall contain a list of action names to retrieve the ActionIdentity and ActionDefinition object instance identifiers for.\nThe request may contain the wildcard value of '*' to return all supported ActionIdentity and ActionDefinition objects.\nThe wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.\nIf a provided identifier does not include a wildcard and does not match an existing ActionIdentity object then this operation shall fail with an UNKNOWN error.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("actionInstIds", true, org.ccsds.moims.mo.mc.structures.ObjectInstancePairList.SHORT_FORM, "The response shall contain a list of matching ActionIdentity and ActionDefinition object instance identifiers.\nThe returned list shall maintain the same order as the submitted list unless the wildcard value was included in the request.")}, 
            "The listDefinition operation allows a consumer to request the object instance identifiers of the latest ActionIdentity and ActionDefinition objects for the supported actions of the provider.");

    /**
     * Operation number literal for operation ADDACTION.
     */
    public static final int _ADDACTION_OP_NUMBER = 4;

    /**
     * Operation number instance for operation ADDACTION.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort ADDACTION_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ADDACTION_OP_NUMBER);

    /**
     * Operation instance for operation ADDACTION.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation ADDACTION_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            ADDACTION_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("addAction"), 
            new org.ccsds.moims.mo.mal.structures.UShort(3), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("actionDefDetails", true, org.ccsds.moims.mo.mc.action.structures.ActionCreationRequestList.SHORT_FORM, "The actionDefDetails field shall hold the name and definitions to be added.\nThe name field must not be the wildcard '*', or empty (an INVALID error shall be returned in this case).\nThe supplied name must be unique among all ActionIdentity objects for the domain of the provider otherwise a DUPLICATE error shall be raised.\nIf an error is raised then no new identities and definitions shall be added as a result of this operation call.\nIf the supplied name matches an existing, but removed, ActionIdentity then that ActionIdentity shall be reused otherwise a new ActionIdentity shall be created.\nThe provider shall create a new ActionDefinition object and store it, and any new ActionIdentity objects, in the COM archive.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("newObjInstIds", true, org.ccsds.moims.mo.mc.structures.ObjectInstancePairList.SHORT_FORM, "The response shall contain the list of object instance identifiers for the ActionIdentity and new ActionDefinition objects.\nThe returned list shall maintain the same order as the submitted definitions.")}, 
            "The addAction operation allows a consumer to define one or more actions that do not currently exist. The new ActionIdentity and ActionDefinition objects are expected to be stored in the COM archive by the provider of the action service.");

    /**
     * Operation number literal for operation UPDATEDEFINITION.
     */
    public static final int _UPDATEDEFINITION_OP_NUMBER = 5;

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
            new org.ccsds.moims.mo.mal.structures.UShort(3), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("actionObjInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The actionObjInstIds field shall contain the list of object instance identifiers of the ActionIdentity objects to be updated.\nThe supplied object instance identifiers shall match existing identity objects, an UNKNOWN error shall be raised if this is not the case.\nIf the actionObjInstIds list contains either NULL or '0' an INVALID error shall be raised."),
                new org.ccsds.moims.mo.mal.OperationField("actionDefDetails", true, org.ccsds.moims.mo.mc.action.structures.ActionDefinitionDetailsList.SHORT_FORM, "The actionDefDetails field shall contain the replacement ActionDefinitionDetails.\nThe two lists shall be ordered the same.\nThe number of entries in the two lists shall be the same size otherwise an INVALID error shall be returned.\nIf an error is raised then no definitions shall be modified as a result of this operation call.\nThe provider shall create a new ActionDefinition object and store it in the COM archive.\nThe new ActionDefinition object shall be the current ActionDefinition used for the specific ActionIdentity.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("newDefInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The response shall contain the list of object instance identifiers for the new ActionDefinition objects.\nThe returned list shall maintain the same order as the submitted definitions.")}, 
            "The updateDefinition operation allows a consumer to update a definition for one or more actions.\nThis differs from deleting an existing action and adding a new definition with the same name in the fact that the ActionIdentity object is not changed between the two definitions.\nThe replacement definition should be stored in the COM archive by the service provider. The operation does not remove the previous ActionDefinition object from the COM archive, merely removes the object from the provider. This permits existing, and completed, ActionInstance objects to continue to reference the correct ActionIdentity and ActionDefinition objects in the COM archive.");

    /**
     * Operation number literal for operation REMOVEACTION.
     */
    public static final int _REMOVEACTION_OP_NUMBER = 6;

    /**
     * Operation number instance for operation REMOVEACTION.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort REMOVEACTION_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_REMOVEACTION_OP_NUMBER);

    /**
     * Operation instance for operation REMOVEACTION.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation REMOVEACTION_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            REMOVEACTION_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("removeAction"), 
            new org.ccsds.moims.mo.mal.structures.UShort(3), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("actionInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The actionInstIds field shall hold the object instance identifiers of the ActionIdentity objects to be removed from the provider.\nThe wildcard value of '0' in the list of object instance identifiers shall be supported and matches all actions of the provider.\nThe wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.\nIf a provided ActionIdentity object instance identifier does not include a wildcard and does not match an existing ActionIdentity object then this operation shall fail with an UNKNOWN error.\nIf a matched definition is still being used by an executing action instance then this operation shall not fail because of this reason.\nMatched ActionIdentity objects shall not be removed from the COM archive only the list of ActionIdentity objects in the provider.\nRemoved ActionIdentity object shall not be allowed to be referenced by new action instances.\nIf an error is raised then no actions shall be removed as a result of this operation call.")}, 
            "The removeAction operation allows a consumer to remove one or more actions from the list of actions supported by the action provider.\nThe operation does not remove the ActionIdentity or ActionDefinition object from the COM archive, merely removes the objects from the provider. This permits existing, and completed, ActionInstance objects to continue to reference the correct ActionIdentity and ActionDefinition objects in the COM archive.");

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] ACTION_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{SUBMITACTION_OP,
        PRECHECKACTION_OP,
        LISTDEFINITION_OP,
        ADDACTION_OP,
        UPDATEDEFINITION_OP,
        REMOVEACTION_OP};

    /**
     * Literal for object ACTIONIDENTITY.
     */
    @Deprecated
    public static final int _ACTIONIDENTITY_OBJECT_NUMBER = 1;

    /**
     * Instance for object ACTIONIDENTITY.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort ACTIONIDENTITY_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ACTIONIDENTITY_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier ACTIONIDENTITY_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ActionIdentity");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType ACTIONIDENTITY_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), ACTION_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), ACTIONIDENTITY_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject ACTIONIDENTITY_OBJECT = new org.ccsds.moims.mo.com.COMObject(ACTIONIDENTITY_OBJECT_TYPE, ACTIONIDENTITY_OBJECT_NAME, org.ccsds.moims.mo.mal.structures.Attribute.IDENTIFIER_SHORT_FORM, false, null, true, null, false);

    /**
     * Literal for object ACTIONDEFINITION.
     */
    @Deprecated
    public static final int _ACTIONDEFINITION_OBJECT_NUMBER = 2;

    /**
     * Instance for object ACTIONDEFINITION.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort ACTIONDEFINITION_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ACTIONDEFINITION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier ACTIONDEFINITION_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ActionDefinition");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType ACTIONDEFINITION_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), ACTION_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), ACTIONDEFINITION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject ACTIONDEFINITION_OBJECT = new org.ccsds.moims.mo.com.COMObject(ACTIONDEFINITION_OBJECT_TYPE, ACTIONDEFINITION_OBJECT_NAME, org.ccsds.moims.mo.mc.action.structures.ActionDefinitionDetails.SHORT_FORM, true, org.ccsds.moims.mo.mc.action.ActionServiceInfo.ACTIONIDENTITY_OBJECT_TYPE, true, null, false);

    /**
     * Literal for object ACTIONINSTANCE.
     */
    @Deprecated
    public static final int _ACTIONINSTANCE_OBJECT_NUMBER = 3;

    /**
     * Instance for object ACTIONINSTANCE.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort ACTIONINSTANCE_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ACTIONINSTANCE_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier ACTIONINSTANCE_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ActionInstance");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType ACTIONINSTANCE_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), ACTION_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), ACTIONINSTANCE_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject ACTIONINSTANCE_OBJECT = new org.ccsds.moims.mo.com.COMObject(ACTIONINSTANCE_OBJECT_TYPE, ACTIONINSTANCE_OBJECT_NAME, org.ccsds.moims.mo.mc.action.structures.ActionInstanceDetails.SHORT_FORM, true, org.ccsds.moims.mo.mc.action.ActionServiceInfo.ACTIONDEFINITION_OBJECT_TYPE, true, null, false);

    /**
     * Literal for object ACTIONFAILURE.
     */
    @Deprecated
    public static final int _ACTIONFAILURE_OBJECT_NUMBER = 6;

    /**
     * Instance for object ACTIONFAILURE.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort ACTIONFAILURE_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ACTIONFAILURE_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier ACTIONFAILURE_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ActionFailure");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType ACTIONFAILURE_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), ACTION_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), ACTIONFAILURE_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject ACTIONFAILURE_OBJECT = new org.ccsds.moims.mo.com.COMObject(ACTIONFAILURE_OBJECT_TYPE, ACTIONFAILURE_OBJECT_NAME, org.ccsds.moims.mo.mal.structures.Attribute.UINTEGER_SHORT_FORM, true, org.ccsds.moims.mo.mc.action.ActionServiceInfo.ACTIONINSTANCE_OBJECT_TYPE, true, org.ccsds.moims.mo.com.activitytracking.ActivityTrackingServiceInfo.EXECUTION_OBJECT_TYPE, true);

    /**
     * Object instance.
     */
    public static final org.ccsds.moims.mo.com.COMObject[] COM_OBJECTS = {
        ACTIONIDENTITY_OBJECT,
        ACTIONDEFINITION_OBJECT,
        ACTIONINSTANCE_OBJECT,
        ACTIONFAILURE_OBJECT,};

    /**
     * Creates an instance of the Action ServiceInfo.
     * 
     */
    public ActionServiceInfo() {
        super(SERVICE_KEY, ACTION_SERVICE_NAME, ACTION_SERVICE_ELEMENTS, OPERATIONS, COM_OBJECTS);
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
