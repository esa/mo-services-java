package org.ccsds.moims.mo.mc.aggregation;

/**
 * Helper class for Aggregation service.
 */
public class AggregationServiceInfo extends org.ccsds.moims.mo.com.COMService {

    /**
     * Service number literal.
     */
    public static final int _AGGREGATION_SERVICE_NUMBER = 6;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort AGGREGATION_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_AGGREGATION_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier AGGREGATION_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("Aggregation");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            4, 1, AGGREGATION_SERVICE_NUMBER);

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
                new org.ccsds.moims.mo.mal.OperationField("objId", true, org.ccsds.moims.mo.com.structures.ObjectId.SHORT_FORM, "The MAL EntityKey.firstSubKey shall contain the aggregation name.\nThe MAL EntityKey.secondSubKey shall contain the AggregationIdentity object instance identifier.\nThe MAL EntityKey.thirdSubKey shall contain the AggregationDefinition object instance identifier.\nThe MAL EntityKey.fourthSubKey shall contain the new AggregationValueInstance object instance identifier.\nThe timestamp of the AggregationValueInstance report shall be taken from the publish message.\nThe publish message shall include the ObjectId of the source link of the report.\nIf no source link is needed then the ObjectId shall be replaced with a NULL."),
                new org.ccsds.moims.mo.mal.OperationField("newValue", true, org.ccsds.moims.mo.mc.aggregation.structures.AggregationValue.SHORT_FORM, "The second part of the publish message shall be the AggregationValue.")}, 
            "The monitorValue operation allows a consumer to subscribe for aggregation value reports.");

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
                new org.ccsds.moims.mo.mal.OperationField("aggInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The aggInstIds field shall provide the list of AggregationIdentity object instance identifiers.\nThe wildcard value of '0' shall be supported and matches all aggregations of the provider.\nThe wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.\nIf a requested aggregation is unknown then an UNKNOWN error shall be returned.\nThe filter shall not be applied for the getValue operation.\nIf an aggregation is being reported periodically, using the operation shall not reset the reportInterval or filteredTimeout timer.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("aggValDetails", true, org.ccsds.moims.mo.mc.aggregation.structures.AggregationValueDetailsList.SHORT_FORM, "The response shall contain a list of returned AggregationIdentity and AggregationDefinition object instance identifier pairs and a matching list of AggregationValues.\nThe new value shall not be published via the monitorValue operation.")}, 
            "The getValue operation returns the latest received value for a requested aggregation.");

    /**
     * Operation number literal for operation ENABLEGENERATION.
     */
    public static final int _ENABLEGENERATION_OP_NUMBER = 3;

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
            new org.ccsds.moims.mo.mal.structures.UShort(3), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("isGroupIds", true, org.ccsds.moims.mo.mal.structures.Attribute.BOOLEAN_SHORT_FORM, "If the isGroupIds field is TRUE then the enableInstances field shall contain GroupIdentity object instance identifiers, otherwise the field contains AggregationIdentity object instance identifiers.\nThe AggregationIdentity objects referenced, either directly or indirectly via groups, by the enableInstances field shall be the AggregationIdentity objects to match.\nThe id of the enableInstances field shall support the wildcard value of '0' and matches all AggregationIdentity objects of the provider.\nThe service provider shall check for the wildcard value in the list of object instance identifiers in the enableInstances field first and if found no other checks of supplied object instance identifiers shall be made.\nIf the enableInstances field contains a value of TRUE then reports of matching AggregationIdentity objects shall be generated, a value of FALSE requests that reports will not be generated.\nNo error shall be raised if the enableInstances Boolean value supplied is the same as the current generationEnabled field of the definition for a matched AggregationIdentity object i.e. enabling an already enabled aggregation will not result in an error.\nIf a requested AggregationIdentity or GroupIdentity object is unknown then an UNKNOWN error shall be returned.\nIf a requested Group, or the Group objects referenced by that Group, does not contain AggregationIdentity objects then an INVALID error shall be returned.\nIf an error is raised then no modifications shall be made as a result of this operation call.\nThe provider shall create and store a new AggregationDefinition object in the COM archive if the generationEnabled field is changed.\nIf a new AggregationDefinition object is created then that new object shall be the current AggregationDefinition used for the specific AggregationIdentity."),
                new org.ccsds.moims.mo.mal.OperationField("enableInstances", true, org.ccsds.moims.mo.com.structures.InstanceBooleanPairList.SHORT_FORM, "If the generation of reports is being enabled, and the aggregation is defined as being periodic, then the provider shall generate a report immediately and start the report interval from that report.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("newObjInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The response shall contain the list of object instance identifiers for the new AggregationDefinition objects.")}, 
            "The enableGeneration operation allows a consumer to control whether reports for specific aggregations are generated or not. The operation allows the consumer to select the aggregations directly or indirectly using groups. This affects all types of aggregations, periodic, filtered and ad-hoc.");

    /**
     * Operation number literal for operation ENABLEFILTER.
     */
    public static final int _ENABLEFILTER_OP_NUMBER = 4;

    /**
     * Operation number instance for operation ENABLEFILTER.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort ENABLEFILTER_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ENABLEFILTER_OP_NUMBER);

    /**
     * Operation instance for operation ENABLEFILTER.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation ENABLEFILTER_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            ENABLEFILTER_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("enableFilter"), 
            new org.ccsds.moims.mo.mal.structures.UShort(3), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("isGroupIds", true, org.ccsds.moims.mo.mal.structures.Attribute.BOOLEAN_SHORT_FORM, "If the isGroupIds field is TRUE then the enableInstances field shall contain GroupIdentity object instance identifiers, otherwise the field contains AggregationIdentity object instance identifiers.\nThe AggregationIdentity objects referenced, either directly or indirectly via groups, by the enableInstances field shall be the AggregationIdentity objects to match.\nThe id of the enableInstances field shall support the wildcard value of '0' and matches all AggregationIdentity objects of the provider.\nThe service provider shall check for the wildcard value in the list of object instance identifiers in the enableInstances field first and if found no other checks of supplied object instance identifiers shall be made.\nIf the enableInstances field contains a value of TRUE then reports of matching AggregationIdentity objects shall be filtered, a value of FALSE requests that reports will not be filtered.\nNo error shall be raised if the enableInstances Boolean value supplied is the same as the current filterEnabled field of the definition for a matched AggregationIdentity object i.e. filtering an already filtered aggregation will not result in an error.\nIf a requested AggregationIdentity or GroupIdentity object is unknown then an UNKNOWN error shall be returned.\nIf a requested Group, or the Group objects referenced by that Group, does not contain AggregationIdentity objects then an INVALID error shall be returned.\nIf an error is raised then no modifications shall be made as a result of this operation call.\nThe provider shall create and store a new AggregationDefinition object in the COM archive if the filterEnabled field is changed.\nIf a new AggregationDefinition object is created then that new object shall be the current AggregationDefinition used for the specific AggregationIdentity."),
                new org.ccsds.moims.mo.mal.OperationField("enableInstances", true, org.ccsds.moims.mo.com.structures.InstanceBooleanPairList.SHORT_FORM, null)}, 
            "The enableFilter operation allows a consumer to control whether reports for specific aggregations are filtered or not. The operation allows the consumer to select the aggregations directly or indirectly using groups. This affects both periodic and ad-hoc aggregations.");

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
            new org.ccsds.moims.mo.mal.structures.UShort(4), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("aggNames", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, "The aggNames field shall contain a list of aggregation names to retrieve the AggregationIdentity and AggregationDefinition object instance identifiers for.\nThe aggNames field may contain the wildcard value of '*' to return all supported AggregationIdentity and AggregationDefinition objects.\nThe wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.\nIf a provided identifier does not include a wildcard and does not match an existing AggregationIdentity object then this operation shall fail with an UNKNOWN error.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("objInstIds", true, org.ccsds.moims.mo.mc.structures.ObjectInstancePairList.SHORT_FORM, "The response shall contain a list of matching AggregationIdentity and AggregationDefinition object instance identifiers.\nThe returned list shall maintain the same order as the submitted list unless the wildcard value was included in the request.")}, 
            "The listDefinition operation allows a consumer to request the latest object instance identifiers of the AggregationIdentity and AggregationDefinition objects for the supported aggregations of the provider.");

    /**
     * Operation number literal for operation ADDAGGREGATION.
     */
    public static final int _ADDAGGREGATION_OP_NUMBER = 6;

    /**
     * Operation number instance for operation ADDAGGREGATION.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort ADDAGGREGATION_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ADDAGGREGATION_OP_NUMBER);

    /**
     * Operation instance for operation ADDAGGREGATION.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation ADDAGGREGATION_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            ADDAGGREGATION_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("addAggregation"), 
            new org.ccsds.moims.mo.mal.structures.UShort(5), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("aggDefDetails", true, org.ccsds.moims.mo.mc.aggregation.structures.AggregationCreationRequestList.SHORT_FORM, "The aggDefDetails field shall hold the name and the AggregationDefinitionDetails to be added.\nThe name field must not be the wildcard '*', or empty (an INVALID error shall be returned in this case).\nIf the supplied reportInterval or sampleInterval values are not supported by the provider then an INVALID error shall be returned.\nThe supplied name must be unique among all AggregationIdentity objects for the domain of the provider otherwise a DUPLICATE error shall be raised.\nIf an error is raised then no new identities and definitions shall be added as a result of this operation call.\nIf the supplied name matches an existing, but removed, AggregationIdentity then that AggregationIdentity shall be reused otherwise a new AggregationIdentity shall be created.\nThe provider shall create a new AggregationDefinition object and store it, and any new AggregationIdentity objects, in the COM archive.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("newObjInstIds", true, org.ccsds.moims.mo.mc.structures.ObjectInstancePairList.SHORT_FORM, "The response shall contain the list of object instance identifiers for the AggregationIdentity and new AggregationDefinition objects.\nThe returned list shall maintain the same order as the submitted definitions.")}, 
            "The addAggregation operation allows a consumer to define one or more aggregations that do not currently exist.\nThe new AggregationIdentity and AggregationDefinition objects are expected to be stored in the COM archive by the provider of the aggregation service.");

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
            new org.ccsds.moims.mo.mal.structures.UShort(5), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("aggInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The aggInstIds field shall contain the object instance identifiers of the AggregationIdentity objects to be updated.\nThe supplied object instance identifiers shall match existing identity objects, an UNKNOWN error shall be raised if this is not the case.\nIf the aggInstIds list contains either NULL or '0' an INVALID error shall be raised."),
                new org.ccsds.moims.mo.mal.OperationField("aggDefDetails", true, org.ccsds.moims.mo.mc.aggregation.structures.AggregationDefinitionDetailsList.SHORT_FORM, "The aggDefDetails field shall contain the replacement AggregationDefinitionDetails.\nThe two lists shall be ordered the same.\nThe number of entries in the two lists shall be the same size otherwise an INVALID error shall be returned.\nIf the supplied reportInterval or sampleInterval values are not supported by the provider then an INVALID error shall be returned.\nIf an error is raised then no definitions shall be updated as a result of this operation call.\nThe provider shall create a new AggregationDefinition object and store it in the COM archive.\nThe new AggregationDefinition object shall be the current AggregationDefinition used for the specific AggregationIdentity.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("newObjInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The response shall contain the list of object instance identifiers for the new AggregationDefinition objects.\nThe returned list shall maintain the same order as the submitted definitions.")}, 
            "The updateDefinition operation allows a consumer to update a definition for one or more aggregations.\nThis differs from deleting an existing aggregation and adding a new definition with the same aggregation name in the fact that the AggregationIdentity object is not changed between the two definitions.\nThe replacement definition should be stored in the COM archive by the service provider. The operation does not remove the previous object from the COM archive, merely removes the object from the provider.");

    /**
     * Operation number literal for operation REMOVEAGGREGATION.
     */
    public static final int _REMOVEAGGREGATION_OP_NUMBER = 8;

    /**
     * Operation number instance for operation REMOVEAGGREGATION.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort REMOVEAGGREGATION_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_REMOVEAGGREGATION_OP_NUMBER);

    /**
     * Operation instance for operation REMOVEAGGREGATION.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation REMOVEAGGREGATION_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            REMOVEAGGREGATION_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("removeAggregation"), 
            new org.ccsds.moims.mo.mal.structures.UShort(5), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("aggInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The aggInstIds field shall hold the object instance identifiers of the AggregationIdentity objects to be removed from the provider.\nThe list may contain the wildcard value of '0'.\nThe wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.\nIf a provided AggregationIdentity object instance identifier does not include a wildcard and does not match an existing aggregation then this operation shall fail with an UNKNOWN error.\nMatched AggregationIdentity and AggregationDefinition objects shall not be removed from the COM archive only the list of AggregationIdentity and AggregationDefinition objects in the provider.\nIf an error is raised then no aggregations shall be removed as a result of this operation call.\nIf the operation succeeds then the provider shall not publish aggregation values for the deleted AggregationIdentity objects anymore.")}, 
            "The removeAggregation operation allows a consumer to remove one or more aggregations from the list of aggregations supported by the aggregation provider.\nThe operation does not remove the AggregationIdentity or AggregationDefinition objects from the COM archive, merely removes the objects from the provider. This permits existing AggregationValueInstance objects to continue to reference the correct AggregationDefinition object in the COM archive.");

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] AGGREGATION_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{MONITORVALUE_OP,
        GETVALUE_OP,
        ENABLEGENERATION_OP,
        ENABLEFILTER_OP,
        LISTDEFINITION_OP,
        ADDAGGREGATION_OP,
        UPDATEDEFINITION_OP,
        REMOVEAGGREGATION_OP};

    /**
     * Literal for object AGGREGATIONIDENTITY.
     */
    @Deprecated
    public static final int _AGGREGATIONIDENTITY_OBJECT_NUMBER = 1;

    /**
     * Instance for object AGGREGATIONIDENTITY.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort AGGREGATIONIDENTITY_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_AGGREGATIONIDENTITY_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier AGGREGATIONIDENTITY_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("AggregationIdentity");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType AGGREGATIONIDENTITY_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), AGGREGATION_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), AGGREGATIONIDENTITY_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject AGGREGATIONIDENTITY_OBJECT = new org.ccsds.moims.mo.com.COMObject(AGGREGATIONIDENTITY_OBJECT_TYPE, AGGREGATIONIDENTITY_OBJECT_NAME, org.ccsds.moims.mo.mal.structures.Attribute.IDENTIFIER_SHORT_FORM, false, null, true, null, false);

    /**
     * Literal for object AGGREGATIONDEFINITION.
     */
    @Deprecated
    public static final int _AGGREGATIONDEFINITION_OBJECT_NUMBER = 2;

    /**
     * Instance for object AGGREGATIONDEFINITION.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort AGGREGATIONDEFINITION_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_AGGREGATIONDEFINITION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier AGGREGATIONDEFINITION_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("AggregationDefinition");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType AGGREGATIONDEFINITION_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), AGGREGATION_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), AGGREGATIONDEFINITION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject AGGREGATIONDEFINITION_OBJECT = new org.ccsds.moims.mo.com.COMObject(AGGREGATIONDEFINITION_OBJECT_TYPE, AGGREGATIONDEFINITION_OBJECT_NAME, org.ccsds.moims.mo.mc.aggregation.structures.AggregationDefinitionDetails.SHORT_FORM, true, org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.AGGREGATIONIDENTITY_OBJECT_TYPE, true, null, false);

    /**
     * Literal for object AGGREGATIONVALUEINSTANCE.
     */
    @Deprecated
    public static final int _AGGREGATIONVALUEINSTANCE_OBJECT_NUMBER = 3;

    /**
     * Instance for object AGGREGATIONVALUEINSTANCE.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort AGGREGATIONVALUEINSTANCE_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_AGGREGATIONVALUEINSTANCE_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier AGGREGATIONVALUEINSTANCE_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("AggregationValueInstance");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType AGGREGATIONVALUEINSTANCE_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), AGGREGATION_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), AGGREGATIONVALUEINSTANCE_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject AGGREGATIONVALUEINSTANCE_OBJECT = new org.ccsds.moims.mo.com.COMObject(AGGREGATIONVALUEINSTANCE_OBJECT_TYPE, AGGREGATIONVALUEINSTANCE_OBJECT_NAME, org.ccsds.moims.mo.mc.aggregation.structures.AggregationValue.SHORT_FORM, true, org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.AGGREGATIONDEFINITION_OBJECT_TYPE, true, null, false);

    /**
     * Object instance.
     */
    public static final org.ccsds.moims.mo.com.COMObject[] COM_OBJECTS = {
        AGGREGATIONIDENTITY_OBJECT,
        AGGREGATIONDEFINITION_OBJECT,
        AGGREGATIONVALUEINSTANCE_OBJECT,};

    /**
     * Creates an instance of the Aggregation ServiceInfo.
     * 
     */
    public AggregationServiceInfo() {
        super(SERVICE_KEY, AGGREGATION_SERVICE_NAME, AGGREGATION_SERVICE_ELEMENTS, OPERATIONS, COM_OBJECTS);
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
