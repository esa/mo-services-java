package org.ccsds.moims.mo.mc.parameter;

/**
 * Helper class for Parameter service.
 */
public class ParameterServiceInfo extends org.ccsds.moims.mo.com.COMService {

    /**
     * Service number literal.
     */
    public static final int _PARAMETER_SERVICE_NUMBER = 2;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort PARAMETER_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_PARAMETER_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier PARAMETER_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("Parameter");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            4, 1, PARAMETER_SERVICE_NUMBER);

    /**
     * Operation number literal for operation MONITORVALUE.
     */
    public static final int _MONITORVALUE_OP_NUMBER = 1;

    /**
     * Operation number instance for operation MONITORVALUE.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort MONITORVALUE_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_MONITORVALUE_OP_NUMBER);

    /**
     * Operation instance for operation MONITORVALUE.
     */
    public static final org.ccsds.moims.mo.mal.MALPubSubOperation MONITORVALUE_OP = new org.ccsds.moims.mo.mal.MALPubSubOperation(SERVICE_KEY, 
            MONITORVALUE_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("monitorValue"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("objId", true, org.ccsds.moims.mo.com.structures.ObjectId.SHORT_FORM, "The MAL EntityKey.firstSubKey shall contain the parameter name.\nThe MAL EntityKey.secondSubKey shall contain the ParameterIdentity object instance identifier.\nThe MAL EntityKey.thirdSubKey shall contain the ParameterDefinition object instance identifier.\nThe MAL EntityKey.fourthSubKey shall contain the new ParameterValueInstance object instance identifier.\nThe timestamp of the ParameterValueInstance report shall be taken from the publish message and shall be the time of the parameter value update.\nThe publish message shall include the ObjectId of the source link of the report.\nIf no source link is needed then the ObjectId shall be replaced with a NULL."),
                new org.ccsds.moims.mo.mal.OperationField("newValue", true, org.ccsds.moims.mo.mc.parameter.structures.ParameterValue.SHORT_FORM, "The second part of the publish message shall be the ParameterValueInstance object value.")}, 
            "The monitorValue operation allows a consumer to subscribe for parameter value reports.");

    /**
     * Key names instance for MONITORVALUE operation of pubsub interaction pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.Identifier [] _MONITORVALUE_OP_KEY_NAMES = {};

    /**
     * Key names instance for MONITORVALUE operation of pubsub interaction pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.IdentifierList MONITORVALUE_OP_KEY_NAMES = new org.ccsds.moims.mo.mal.structures.IdentifierList(new java.util.ArrayList<>(java.util.Arrays.asList(_MONITORVALUE_OP_KEY_NAMES)));

    /**
     * Operation number literal for operation GETVALUE.
     */
    public static final int _GETVALUE_OP_NUMBER = 2;

    /**
     * Operation number instance for operation GETVALUE.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort GETVALUE_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GETVALUE_OP_NUMBER);

    /**
     * Operation instance for operation GETVALUE.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation GETVALUE_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            GETVALUE_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("getValue"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("paramInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The paramInstIds field shall provide the list of ParameterIdentity object instance identifiers.\nThe wildcard value of '0' shall be supported and matches all parameters of the provider.\nThe wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.\nIf a requested parameter is unknown then an UNKNOWN error shall be returned.\nIf a parameter is being reported periodically, using the operation shall not reset the reportInterval timer.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("paramValDetails", true, org.ccsds.moims.mo.mc.parameter.structures.ParameterValueDetailsList.SHORT_FORM, "The response shall contain a list of returned ParameterIdentity and ParameterDefinition object instance identifier pairs and a matching list of parameter values.\nThe new value shall not be published via the monitorValue operation.")}, 
            "The getValue operation returns the latest received value for a requested parameter.");

    /**
     * Operation number literal for operation SETVALUE.
     */
    public static final int _SETVALUE_OP_NUMBER = 3;

    /**
     * Operation number instance for operation SETVALUE.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort SETVALUE_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_SETVALUE_OP_NUMBER);

    /**
     * Operation instance for operation SETVALUE.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation SETVALUE_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            SETVALUE_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("setValue"), 
            new org.ccsds.moims.mo.mal.structures.UShort(3), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("newRawValues", true, org.ccsds.moims.mo.mc.parameter.structures.ParameterRawValueList.SHORT_FORM, "The submitted newRawValues shall hold a list of ParameterRawValues that contain the ParameterIdentity object instance identifier and the respective raw value to be set.\nIf the paramInstId field contains the wildcard value of '0' then an INVALID error shall be returned.\nIf a requested ParameterIdentity is unknown then an UNKNOWN error shall be returned.\nIf a request ParameterIdentity is not settable due to it being read only then a READONLY error shall be returned.\nThe rawValue shall contain the new parameter raw value to be set.\nIf the supplied new parameter raw value does not match the defined type for the ParameterIdentity then an INVALID error shall be returned.\nIf an error is raised then no modifications shall be made as a result of this operation call.\nThe parameter values shall be set concurrently, by this it is meant that all values are set at the same time without interleaving of other values being (ATOMIC behaviour). How this is implemented is an implementation detail.\nThe service provider shall create new ParameterValueInstance objects for the updated parameter values, store these in the COM Archive, and publish these new values.")}, 
            "The setValue operation allows a consumer to set the raw value for one or more parameters.");

    /**
     * Operation number literal for operation ENABLEGENERATION.
     */
    public static final int _ENABLEGENERATION_OP_NUMBER = 4;

    /**
     * Operation number instance for operation ENABLEGENERATION.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort ENABLEGENERATION_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ENABLEGENERATION_OP_NUMBER);

    /**
     * Operation instance for operation ENABLEGENERATION.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation ENABLEGENERATION_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            ENABLEGENERATION_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("enableGeneration"), 
            new org.ccsds.moims.mo.mal.structures.UShort(4), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("isGroupIds", true, org.ccsds.moims.mo.mal.structures.Attribute.BOOLEAN_SHORT_FORM, "If the isGroupIds field is TRUE then the enableInstances field shall contain GroupIdentity object instance identifiers, otherwise the field contains ParameterIdentity object instance identifiers.\nThe ParameterIdentity objects referenced, either directly or indirectly via groups, by the enableInstances field shall be the ParameterIdentity objects to match.\nThe id of the enableInstances field shall support the wildcard value of '0' and matches all ParameterIdentity objects of the provider.\nThe service provider shall check for the wildcard value in the list of object instance identifiers in the enableInstances field first and if found no other checks of supplied object instance identifiers shall be made.\nIf the enableInstances field contains a value of TRUE then reports for matching ParameterIdentity objects shall be generated, a value of FALSE requests that reports will not be generated.\nNo error shall be raised if the enableInstances Boolean value supplied is the same as the current generationEnabled field of the definition for a matched ParameterIdentity object i.e. enabling an already enabled parameter will not result in an error.\nIf a requested ParameterIdentity or GroupIdentity object is unknown then an UNKNOWN error shall be returned.\nIf a requested Group, or the Group objects referenced by that Group, does not contain ParameterIdentity objects then an INVALID error shall be returned.\nIf an error is raised then no modifications shall be made as a result of this operation call.\nThe provider shall create and store a new ParameterDefinition object in the COM archive if the generationEnabled field is changed.\nIf a new ParameterDefinition object is created then that new object shall be the current ParameterDefinition used for the specific ParameterIdentity."),
                new org.ccsds.moims.mo.mal.OperationField("enableInstances", true, org.ccsds.moims.mo.com.structures.InstanceBooleanPairList.SHORT_FORM, "If the generation of reports is being enabled, and the parameter is defined as being periodic, then the provider shall generate a report immediately and start the report interval from that report.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("newObjInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The response shall contain the list of object instance identifiers for the new ParameterDefinition objects.")}, 
            "The enableGeneration operation allows a consumer to control whether reports for specific parameters are generated or not. The operation allows the consumer to select the parameters directly or indirectly using groups.");

    /**
     * Operation number literal for operation LISTDEFINITION.
     */
    public static final int _LISTDEFINITION_OP_NUMBER = 5;

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
                new org.ccsds.moims.mo.mal.OperationField("paramNames", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, "The paramNames field shall contain a list of parameter names to retrieve the ParameterIdentity and ParameterDefinition object instance identifiers for.\nThe paramNames field may contain the wildcard value of '*' to return all supported ParameterIdentity and ParameterDefinition objects.\nThe wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.\nIf a provided identifier does not include a wildcard and does not match an existing ParameterIdentity object then this operation shall fail with an UNKNOWN error.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("objInstIds", true, org.ccsds.moims.mo.mc.structures.ObjectInstancePairList.SHORT_FORM, "The response shall contain a list of matching ParameterIdentity and ParameterDefinition object instance identifier pairs.\nThe returned list shall maintain the same order as the submitted list unless the wildcard value was included in the request.")}, 
            "The listDefinition operation allows a consumer to request the latest object instance identifiers of the ParameterIdentity and ParameterDefinition objects for the supported parameters of the provider.");

    /**
     * Operation number literal for operation ADDPARAMETER.
     */
    public static final int _ADDPARAMETER_OP_NUMBER = 6;

    /**
     * Operation number instance for operation ADDPARAMETER.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort ADDPARAMETER_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ADDPARAMETER_OP_NUMBER);

    /**
     * Operation instance for operation ADDPARAMETER.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation ADDPARAMETER_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            ADDPARAMETER_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("addParameter"), 
            new org.ccsds.moims.mo.mal.structures.UShort(6), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("paramDefDetails", true, org.ccsds.moims.mo.mc.parameter.structures.ParameterCreationRequestList.SHORT_FORM, "The paramDefDetails field shall hold the name and the ParameterDefinitionDetails to be added.\nThe name field must not be the wildcard '*', or empty (an INVALID error shall be returned in this case).\nIf the supplied reportInterval value is not supported by the provider then an INVALID error shall be returned.\nThe supplied name must be unique among all ParameterIdentity objects for the domain of the provider otherwise a DUPLICATE error shall be raised.\nIf an error is raised then no new identities and definitions shall be added as a result of this operation call.\nIf the supplied name matches an existing, but removed, ParameterIdentity then that ParameterIdentity shall be reused otherwise a new ParameterIdentity shall be created.\nThe provider shall create a new ParameterDefinition object and store it, and any new ParameterIdentity objects, in the COM archive.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("newObjInstIds", true, org.ccsds.moims.mo.mc.structures.ObjectInstancePairList.SHORT_FORM, "The response shall contain the list of object instance identifiers for the ParameterIdentity and new ParameterDefinition objects.\nThe returned list shall maintain the same order as the submitted definitions.")}, 
            "The addParameter operation allows a consumer to define one or more parameters that do not currently exist.\nThe new ParameterIdentity and ParameterDefinition objects are expected to be stored in the COM archive by the provider of the parameter service.");

    /**
     * Operation number literal for operation UPDATEDEFINITION.
     */
    public static final int _UPDATEDEFINITION_OP_NUMBER = 7;

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
                new org.ccsds.moims.mo.mal.OperationField("paramInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The paramInstIds field shall contain the object instance identifiers of the ParameterIdentity objects to be updated.\nThe supplied object instance identifiers shall match existing identity objects, an UNKNOWN error shall be raised if this is not the case.\nIf the paramInstIds list contains either NULL or '0' an INVALID error shall be raised."),
                new org.ccsds.moims.mo.mal.OperationField("paramDefDetails", true, org.ccsds.moims.mo.mc.parameter.structures.ParameterDefinitionDetailsList.SHORT_FORM, "The paramDefDetails field shall contain the replacement ParameterDefinitionDetails.\nThe two lists shall be ordered the same.\nThe number of entries in the two lists shall be the same size otherwise an INVALID error shall be returned.\nIf the supplied reportInterval value is not supported by the provider then an INVALID error shall be returned.\nIf an error is raised then no definitions shall be updated as a result of this operation call.\nThe provider shall create a new ParameterDefinition object and store it in the COM archive.\nThe new ParameterDefinition object shall be the current ParameterDefinition used for the specific ParameterIdentity.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("newObjInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The response shall contain the list of object instance identifiers for the new ParameterDefinition objects.\nThe returned list shall maintain the same order as the submitted definitions.")}, 
            "The updateDefinition operation allows a consumer to update a definition for one or more parameters.\nThis differs from deleting an existing parameter and adding a new definition with the same parameter name in the fact that the ParameterIdentity object is not changed between the two definitions.\nThe replacement definition should be stored in the COM archive by the service provider. The operation does not remove the previous object from the COM archive, merely removes the object from the provider.");

    /**
     * Operation number literal for operation REMOVEPARAMETER.
     */
    public static final int _REMOVEPARAMETER_OP_NUMBER = 8;

    /**
     * Operation number instance for operation REMOVEPARAMETER.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort REMOVEPARAMETER_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_REMOVEPARAMETER_OP_NUMBER);

    /**
     * Operation instance for operation REMOVEPARAMETER.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation REMOVEPARAMETER_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            REMOVEPARAMETER_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("removeParameter"), 
            new org.ccsds.moims.mo.mal.structures.UShort(6), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("paramInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The paramInstIds field shall hold the object instance identifiers of the ParameterIdentity objects to be removed from the provider.\nThe list may contain the wildcard value of '0'.\nThe wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.\nIf a provided ParameterIdentity object instance identifier does not include a wildcard and does not match an existing parameter identity object then this operation shall fail with an UNKNOWN error.\nMatched ParameterIdentity and ParameterDefinition objects shall not be removed from the COM archive only the list of ParameterIdentity and ParameterDefinition objects from the provider.\nIf an error is raised then no parameters shall be removed as a result of this operation call.\nIf the operation succeeds then the provider shall not publish parameter values for the deleted ParameterIdentity objects anymore.")}, 
            "The removeParameter operation allows a consumer to remove one or more parameters from the list of parameters supported by the parameter provider.\nThe operation does not remove the ParameterIdentity or ParameterDefinition objects from the COM archive, merely removes the objects from the provider. This permits existing parameter values to continue to reference the correct ParameterIdentity and ParameterDefinition objects in the COM archive.");

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] PARAMETER_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{MONITORVALUE_OP,
        GETVALUE_OP,
        SETVALUE_OP,
        ENABLEGENERATION_OP,
        LISTDEFINITION_OP,
        ADDPARAMETER_OP,
        UPDATEDEFINITION_OP,
        REMOVEPARAMETER_OP};

    /**
     * Literal for object PARAMETERIDENTITY.
     */
    @Deprecated
    public static final int _PARAMETERIDENTITY_OBJECT_NUMBER = 1;

    /**
     * Instance for object PARAMETERIDENTITY.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort PARAMETERIDENTITY_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_PARAMETERIDENTITY_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier PARAMETERIDENTITY_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ParameterIdentity");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType PARAMETERIDENTITY_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), PARAMETER_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), PARAMETERIDENTITY_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject PARAMETERIDENTITY_OBJECT = new org.ccsds.moims.mo.com.COMObject(PARAMETERIDENTITY_OBJECT_TYPE, PARAMETERIDENTITY_OBJECT_NAME, org.ccsds.moims.mo.mal.structures.Attribute.IDENTIFIER_SHORT_FORM, false, null, true, null, false);

    /**
     * Literal for object PARAMETERDEFINITION.
     */
    @Deprecated
    public static final int _PARAMETERDEFINITION_OBJECT_NUMBER = 2;

    /**
     * Instance for object PARAMETERDEFINITION.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort PARAMETERDEFINITION_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_PARAMETERDEFINITION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier PARAMETERDEFINITION_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ParameterDefinition");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType PARAMETERDEFINITION_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), PARAMETER_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), PARAMETERDEFINITION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject PARAMETERDEFINITION_OBJECT = new org.ccsds.moims.mo.com.COMObject(PARAMETERDEFINITION_OBJECT_TYPE, PARAMETERDEFINITION_OBJECT_NAME, org.ccsds.moims.mo.mc.parameter.structures.ParameterDefinitionDetails.SHORT_FORM, true, org.ccsds.moims.mo.mc.parameter.ParameterServiceInfo.PARAMETERIDENTITY_OBJECT_TYPE, true, null, false);

    /**
     * Literal for object PARAMETERVALUEINSTANCE.
     */
    @Deprecated
    public static final int _PARAMETERVALUEINSTANCE_OBJECT_NUMBER = 3;

    /**
     * Instance for object PARAMETERVALUEINSTANCE.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort PARAMETERVALUEINSTANCE_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_PARAMETERVALUEINSTANCE_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier PARAMETERVALUEINSTANCE_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ParameterValueInstance");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType PARAMETERVALUEINSTANCE_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), PARAMETER_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), PARAMETERVALUEINSTANCE_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject PARAMETERVALUEINSTANCE_OBJECT = new org.ccsds.moims.mo.com.COMObject(PARAMETERVALUEINSTANCE_OBJECT_TYPE, PARAMETERVALUEINSTANCE_OBJECT_NAME, org.ccsds.moims.mo.mc.parameter.structures.ParameterValue.SHORT_FORM, true, org.ccsds.moims.mo.mc.parameter.ParameterServiceInfo.PARAMETERDEFINITION_OBJECT_TYPE, true, null, false);

    /**
     * Object instance.
     */
    public static final org.ccsds.moims.mo.com.COMObject[] COM_OBJECTS = {
        PARAMETERIDENTITY_OBJECT,
        PARAMETERDEFINITION_OBJECT,
        PARAMETERVALUEINSTANCE_OBJECT,};

    /**
     * Creates an instance of the Parameter ServiceInfo.
     * 
     */
    public ParameterServiceInfo() {
        super(SERVICE_KEY, PARAMETER_SERVICE_NAME, PARAMETER_SERVICE_ELEMENTS, OPERATIONS, COM_OBJECTS);
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
