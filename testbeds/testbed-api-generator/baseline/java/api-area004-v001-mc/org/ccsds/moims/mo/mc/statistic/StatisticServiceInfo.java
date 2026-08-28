package org.ccsds.moims.mo.mc.statistic;

/**
 * Helper class for Statistic service.
 */
public class StatisticServiceInfo extends org.ccsds.moims.mo.com.COMService {

    /**
     * Service number literal.
     */
    public static final int _STATISTIC_SERVICE_NUMBER = 5;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort STATISTIC_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_STATISTIC_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier STATISTIC_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("Statistic");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            4, 1, STATISTIC_SERVICE_NUMBER);

    /**
     * Operation number literal for operation GETSTATISTICS.
     */
    public static final int _GETSTATISTICS_OP_NUMBER = 1;

    /**
     * Operation number instance for operation GETSTATISTICS.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort GETSTATISTICS_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GETSTATISTICS_OP_NUMBER);

    /**
     * Operation instance for operation GETSTATISTICS.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation GETSTATISTICS_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            GETSTATISTICS_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("getStatistics"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("funcObjInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The funcObjInstIds field shall include a list of StatisticFunction object instance identifiers to match.\nThe funcObjInstIds field shall support the wildcard value of '0' and will match all functions of the provider.\nThe wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made."),
                new org.ccsds.moims.mo.mal.OperationField("isGroup", true, org.ccsds.moims.mo.mal.structures.Attribute.BOOLEAN_SHORT_FORM, null),
                new org.ccsds.moims.mo.mal.OperationField("paramObjInstIds", true, org.ccsds.moims.mo.com.structures.ObjectKeyList.SHORT_FORM, "If the isGroup field is TRUE then the paramObjInstIds field shall contain GroupIdentity object instance identifiers, otherwise the field shall contain ParameterIdentity object instance identifiers.\nIf the isGroup field is TRUE, the requested Group, or the Group objects referenced by that Group, must contain ParameterIdentity objects otherwise an INVALID error shall be returned.\nThe ParameterIdentity objects referenced, either directly or indirectly via groups, by the paramObjInstIds field shall be the parameters to match.\nThe paramObjInstIds field shall support the wildcard value of '0' and matches all parameters of the provider matched to the functions given in the funcObjInstIds field.\nThe wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.\nIf a requested function, group or parameters is unknown then an UNKNOWN error shall be returned.\nThe sets of matched StatisticFunction objects and ParameterIdentity objects shall be matched to the set of existing StatisticLink objects to determine which StatisticLink objects to report on.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("evaluations", true, org.ccsds.moims.mo.mc.statistic.structures.StatisticEvaluationReportList.SHORT_FORM, "The response shall contain a list of matching statistics evaluation values.\nThe operation shall trigger an evaluation of the statistical functions matched and return the new evaluation values.\nIf it is not possible to return an evaluation value for a matched evaluation (for example not enough samples available) then no entry for that evaluation shall be included.\nThe evaluation shall not trigger a report via the monitorStatistics operation.\nRequesting an evaluation shall ignore the samplingInterval, reportingInterval, and collectionInterval fields and requests an immediate evaluation of the statistic.\nRequesting an evaluation during a periodic evaluation shall not influence the periodic evaluation (e.g. it does not reset the samplingInterval, reportingInterval, and collectionInterval timers or the current periodic collection value).")}, 
            "The getStatistics operation returns the latest value for a set of existing statistic evaluations.");

    /**
     * Operation number literal for operation RESETEVALUATION.
     */
    public static final int _RESETEVALUATION_OP_NUMBER = 2;

    /**
     * Operation number instance for operation RESETEVALUATION.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort RESETEVALUATION_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_RESETEVALUATION_OP_NUMBER);

    /**
     * Operation instance for operation RESETEVALUATION.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation RESETEVALUATION_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            RESETEVALUATION_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("resetEvaluation"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("isStatLinkGroup", true, org.ccsds.moims.mo.mal.structures.Attribute.BOOLEAN_SHORT_FORM, "If the isStatLinkGroup field is TRUE then the objInstIds field shall contain GroupIdentity object instance identifiers, otherwise the field shall contain StatisticFunction object instance identifiers.\nIf the isStatLinkGroup field is TRUE, the requested Group, or the Group objects referenced by that Group, must contain StatisticLink objects otherwise an INVALID error shall be returned.\nThe StatisticLink objects referenced, either indirectly via statistic functions or indirectly via groups, by the objInstIds field shall be the StatisticLink objects to match."),
                new org.ccsds.moims.mo.mal.OperationField("objInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The objInstIds field shall support the wildcard value of '0' and matches all StatisticLink objects of the provider.\nThe wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.\nIf a requested function or group is unknown then an UNKNOWN error shall be returned."),
                new org.ccsds.moims.mo.mal.OperationField("returnLatestEval", true, org.ccsds.moims.mo.mal.structures.Attribute.BOOLEAN_SHORT_FORM, "If the returnLatestEval Boolean field is TRUE then the latest evaluation result for each of the matched links shall be returned before resetting, otherwise a NULL is returned.\nIf an error is raised then no resetting of evaluations shall be made as a result of this operation call.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("evaluations", true, org.ccsds.moims.mo.mc.statistic.structures.StatisticEvaluationReportList.SHORT_FORM, null)}, 
            "The operation allows a consumer to reset the statistical evaluations so the evaluations restart from the current time (without changing the collection interval), optionally returning the evaluation up to that point. Resetting the evaluation will affect all consumers.");

    /**
     * Operation number literal for operation MONITORSTATISTICS.
     */
    public static final int _MONITORSTATISTICS_OP_NUMBER = 3;

    /**
     * Operation number instance for operation MONITORSTATISTICS.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort MONITORSTATISTICS_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_MONITORSTATISTICS_OP_NUMBER);

    /**
     * Operation instance for operation MONITORSTATISTICS.
     */
    public static final org.ccsds.moims.mo.mal.MALPubSubOperation MONITORSTATISTICS_OP = new org.ccsds.moims.mo.mal.MALPubSubOperation(SERVICE_KEY, 
            MONITORSTATISTICS_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("monitorStatistics"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("relatedId", true, org.ccsds.moims.mo.mal.structures.Attribute.LONG_SHORT_FORM, "The MAL EntityKey.firstSubKey shall contain the statistic function name.\nThe MAL EntityKey.secondSubKey shall contain the StatisticLink object instance identifier.\nThe MAL EntityKey.thirdSubKey shall contain the ParameterIdentity object instance identifier.\nThe MAL EntityKey.fourthSubKey shall contain the new StatisticValueInstance object instance identifier.\nThe timestamp of the StatisticValueInstance report shall be taken from the publish message.\nThe related link of the update shall be held in the relatedId field."),
                new org.ccsds.moims.mo.mal.OperationField("sourceId", true, org.ccsds.moims.mo.com.structures.ObjectId.SHORT_FORM, "The source link of the StatisticValueInstance shall be held in the sourceId field.\nIf no source link is needed then the sourceId shall be set to NULL."),
                new org.ccsds.moims.mo.mal.OperationField("statisticValue", true, org.ccsds.moims.mo.mc.statistic.structures.StatisticValue.SHORT_FORM, "The second part of the publish message shall be the StatisticValueInstance object value.")}, 
            "The monitorStatistics operation allows a consumer to subscribe for statistical evaluation value reports.\nIt should be noted that no evaluation reports will be generated if the service provider has been disabled via the enableService operation.");

    /**
     * Key names instance for MONITORSTATISTICS operation of pubsub interaction
     * pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.Identifier [] _MONITORSTATISTICS_OP_KEY_NAMES = {};

    /**
     * Key names instance for MONITORSTATISTICS operation of pubsub interaction
     * pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.IdentifierList MONITORSTATISTICS_OP_KEY_NAMES = new org.ccsds.moims.mo.mal.structures.IdentifierList(new java.util.ArrayList<>(java.util.Arrays.asList(_MONITORSTATISTICS_OP_KEY_NAMES)));

    /**
     * Operation number literal for operation ENABLESERVICE.
     */
    public static final int _ENABLESERVICE_OP_NUMBER = 4;

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
            new org.ccsds.moims.mo.mal.structures.UShort(3), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("enableService", true, org.ccsds.moims.mo.mal.structures.Attribute.BOOLEAN_SHORT_FORM, "If enableService is set to TRUE the service shall be enabled and evaluation and reporting of statistics will be reset and commence.\nIf enableService is set to FALSE then all evaluation of statistics shall be suspended and no statistics will be reported.\nIf the enableService value matches the current enabled state of the service then no change shall be made and no error reported. Enabling an already enabled service has no effect.")}, 
            "The enableService operation allows a consumer to globally control whether evaluation of all statistics is performed or not.");

    /**
     * Operation number literal for operation GETSERVICESTATUS.
     */
    public static final int _GETSERVICESTATUS_OP_NUMBER = 5;

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
            new org.ccsds.moims.mo.mal.structures.UShort(3), 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("serviceEnabled", true, org.ccsds.moims.mo.mal.structures.Attribute.BOOLEAN_SHORT_FORM, "The operation shall return TRUE if the service is currently enabled or FALSE if the service is currently disabled.")}, 
            "The getServiceStatus operation allows a consumer to determine the global statistic service enabled status.");

    /**
     * Operation number literal for operation ENABLEREPORTING.
     */
    public static final int _ENABLEREPORTING_OP_NUMBER = 6;

    /**
     * Operation number instance for operation ENABLEREPORTING.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort ENABLEREPORTING_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ENABLEREPORTING_OP_NUMBER);

    /**
     * Operation instance for operation ENABLEREPORTING.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation ENABLEREPORTING_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            ENABLEREPORTING_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("enableReporting"), 
            new org.ccsds.moims.mo.mal.structures.UShort(4), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("isGroupIds", true, org.ccsds.moims.mo.mal.structures.Attribute.BOOLEAN_SHORT_FORM, "If the isGroupIds field is TRUE then the enableInstances field shall contain GroupIdentity object instance identifiers, otherwise the field contains StatisticFunction object instance identifiers.\nIf the isGroupIds field is TRUE, the requested Group, or the Group objects referenced by that Group, must contain StatisticLink objects otherwise an INVALID error shall be returned.\nThe StatisticLink objects referenced, either indirectly via StatisticFunction objects or indirectly via groups, by the enableInstances field shall be the StatisticLink objects to match.\nThe id of the enableInstances field shall support the wildcard value of '0' and matches all StatisticLink objects of the provider.\nThe service provider shall check for the wildcard value in the list of object instance identifiers in the enableInstances field first and if found no other checks of supplied object instance identifiers shall be made.\nIf the enableInstances field contains a value of TRUE then reports after the reporting and collection intervals for matching StatisticLink objects shall be generated, a value of FALSE requests that reports will not be generated.\nNo error shall be raised if the enableInstances Boolean value supplied is the same as the current reportingEnabled field for a StatisticLink object i.e. enabling an already enabled link will not result in an error.\nIf a requested StatisticFunction or GroupIdentity object is unknown then an UNKNOWN error shall be returned.\nIf an error is raised then no modifications shall be made as a result of this operation call.\nThe provider should create and store a new StatisticLinkDefinition object in the COM archive if the reportingEnabled field is changed."),
                new org.ccsds.moims.mo.mal.OperationField("enableInstances", true, org.ccsds.moims.mo.com.structures.InstanceBooleanPairList.SHORT_FORM, "If the generation of reports is being enabled, then the provider shall generate a report immediately and start the report interval from that report.")}, 
            "The enableReporting operation allows a consumer to control whether reports for specific statistical functions are generated or not. The operation allows the consumer to select the functions directly or indirectly using groups.");

    /**
     * Operation number literal for operation LISTPARAMETEREVALUATIONS.
     */
    public static final int _LISTPARAMETEREVALUATIONS_OP_NUMBER = 7;

    /**
     * Operation number instance for operation LISTPARAMETEREVALUATIONS.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort LISTPARAMETEREVALUATIONS_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_LISTPARAMETEREVALUATIONS_OP_NUMBER);

    /**
     * Operation instance for operation LISTPARAMETEREVALUATIONS.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation LISTPARAMETEREVALUATIONS_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            LISTPARAMETEREVALUATIONS_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("listParameterEvaluations"), 
            new org.ccsds.moims.mo.mal.structures.UShort(5), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("statObjInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The statObjInstIds field shall hold a list of StatisticFunction object instance identifiers to retrieve the StatisticLink object instance identifiers for.\nThe request may contain the wildcard value of '0' to return all supported statistic links.\nThe wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.\nIf a provided identifier does not include a wildcard and does not match an existing StatisticFunction object then this operation shall fail with an UNKNOWN error.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("statLinkObjInstIds", true, org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkSummaryList.SHORT_FORM, "The response shall contain a list of StatisticLinkSummary that contain the object instance identifiers of the StatisticLink, StatisticFunction, and ParameterIdentity for the matched StatisticFunction objects.")}, 
            "The listParameterEvaluations operation allows a consumer to request the object instance identifiers of the StatisticLink objects for the evaluations of the provider.");

    /**
     * Operation number literal for operation ADDPARAMETEREVALUATION.
     */
    public static final int _ADDPARAMETEREVALUATION_OP_NUMBER = 8;

    /**
     * Operation number instance for operation ADDPARAMETEREVALUATION.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort ADDPARAMETEREVALUATION_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ADDPARAMETEREVALUATION_OP_NUMBER);

    /**
     * Operation instance for operation ADDPARAMETEREVALUATION.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation ADDPARAMETEREVALUATION_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            ADDPARAMETEREVALUATION_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("addParameterEvaluation"), 
            new org.ccsds.moims.mo.mal.structures.UShort(6), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("newDetails", true, org.ccsds.moims.mo.mc.statistic.structures.StatisticCreationRequestList.SHORT_FORM, "The newDetails field shall hold a StatisticCreationRequest for each new parameter to be sampled.\nThe statFuncInstId field of the StatisticCreationRequest shall reference the object instance identifier of the StatisticFunction to be used.\nIf the statFuncInstId field does not match an existing StatisticFunction then an UNKNOWN error shall be raised.\nThe parameterId shall reference the ParameterIdentity that the function is being applied to.\nIf the parameterId field does not match an existing ParameterIdentity then an UNKNOWN error shall be raised.\nIf the type of the matched parameter is not supported by the matched statistical function, for example Mean average of a String parameter, then an INVALID error shall be returned.\nThe samplingInterval field shall contain the sampling duration interval for the parameter.\nIf the supplied samplingInterval is not supported for the requested parameter then an INVALID error shall be returned.\nIf an error is raised then no new StatisticLink object shall be created and stored as a result of this operation call.\nIf no error is to be raised then StatisticLink and StatisticLinkDefinition objects shall be created for each function/parameter link and stored in the COM archive.\nThe referenced parameter shall be sampled immediately and the sampling, reporting and collection intervals started.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("newObjInstIds", true, org.ccsds.moims.mo.mc.structures.ObjectInstancePairList.SHORT_FORM, "The response shall contain the list of object instance identifiers for the new StatisticLink and StatisticLinkDefinition objects.\nThe object instance identifiers of the StatisticLink and StatisticLinkDefinition objects shall be held in the first and second fields of the ObjectInstancePair structure respectively.\nThe returned list shall maintain the same order as the submitted links.")}, 
            "The addParameterEvaluation operation allows a consumer to request that one or more parameters/function combinations are added to the list of parameters that are being evaluated.\nThe new StatisticLink and StatisticLinkDefinition objects are expected to be stored in the COM archive by the provider of the statistic service.");

    /**
     * Operation number literal for operation UPDATEPARAMETEREVALUATION.
     */
    public static final int _UPDATEPARAMETEREVALUATION_OP_NUMBER = 9;

    /**
     * Operation number instance for operation UPDATEPARAMETEREVALUATION.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort UPDATEPARAMETEREVALUATION_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_UPDATEPARAMETEREVALUATION_OP_NUMBER);

    /**
     * Operation instance for operation UPDATEPARAMETEREVALUATION.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation UPDATEPARAMETEREVALUATION_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            UPDATEPARAMETEREVALUATION_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("updateParameterEvaluation"), 
            new org.ccsds.moims.mo.mal.structures.UShort(6), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("linkIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The linkIds field shall contain the object instance identifiers of the StatisticLink objects to be updated.\nIf the linkIds list contains either NULL or '0' an INVALID error shall be raised.\nThe supplied object instance identifiers shall match existing link objects, an UNKNOWN error shall be raised if this is not the case.\nIf the supplied samplingInterval is not supported for the requested parameter then an INVALID error shall be returned."),
                new org.ccsds.moims.mo.mal.OperationField("newDetails", true, org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkDetailsList.SHORT_FORM, "The newDetails field shall contain the replacement StatisticLinkDetails.\nThe two lists shall be ordered the same.\nThe number of entries in the two lists shall be the same size otherwise an INVALID error shall be returned.\nIf an error is raised then no links shall be updated as a result of this operation call.\nThe provider shall create a new StatisticLinkDefinition object and store it in the COM archive.\nIf any of the intervals are updated then the service shall reset the relevant timer and use the new intervals immediately.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("newLinkDefIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The response shall contain the list of object instance identifiers for the new StatisticLinkDefinition objects.\nThe returned list shall maintain the same order as the submitted links.")}, 
            "The updateParameterEvaluation operation allows a consumer to modify the intervals, reporting and reset Booleans for one or more statistical evaluation links.\nThe replacement StatisticLinkDefinition objects should be stored in the COM archive by the service provider. The operation does not remove the previous object from the COM archive, merely removes the object from the provider.");

    /**
     * Operation number literal for operation REMOVEPARAMETEREVALUATION.
     */
    public static final int _REMOVEPARAMETEREVALUATION_OP_NUMBER = 10;

    /**
     * Operation number instance for operation REMOVEPARAMETEREVALUATION.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort REMOVEPARAMETEREVALUATION_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_REMOVEPARAMETEREVALUATION_OP_NUMBER);

    /**
     * Operation instance for operation REMOVEPARAMETEREVALUATION.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation REMOVEPARAMETEREVALUATION_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            REMOVEPARAMETEREVALUATION_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("removeParameterEvaluation"), 
            new org.ccsds.moims.mo.mal.structures.UShort(6), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("objInstIds", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The objInstIds field holds the object instance identifiers of the StatisticLink objects to be removed from the provider.\nThe list may contain the wildcard value of '0'.\nThe wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.\nIf a provided StatisticLink object instance identifier does not include a wildcard and does not match an existing StatisticLink object then this operation shall fail with an UNKNOWN error.\nMatched StatisticLink objects shall not be removed from the COM archive only the list of evaluated StatisticLink objects in the provider.\nIf an error is raised then no StatisticLink objects shall be removed as a result of this operation call.\nIf the operation succeeds then the provider shall not evaluate those parameter/function definition combinations for the deleted StatisticLink objects anymore.")}, 
            "The removeParameterEvaluation operation allows a consumer to remove one or more parameters from the list of parameters being sampled by the statistic provider.\nThe operation does not remove the StatisticLink or StatisticLinkDefinition objects from the COM archive, merely removes them from the provider. This permits existing evaluation results to continue to reference the correct StatisticLink and StatisticLinkDefinition objects in the COM archive.");

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] STATISTIC_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{GETSTATISTICS_OP,
        RESETEVALUATION_OP,
        MONITORSTATISTICS_OP,
        ENABLESERVICE_OP,
        GETSERVICESTATUS_OP,
        ENABLEREPORTING_OP,
        LISTPARAMETEREVALUATIONS_OP,
        ADDPARAMETEREVALUATION_OP,
        UPDATEPARAMETEREVALUATION_OP,
        REMOVEPARAMETEREVALUATION_OP};

    /**
     * Literal for object STATISTICFUNCTION.
     */
    @Deprecated
    public static final int _STATISTICFUNCTION_OBJECT_NUMBER = 1;

    /**
     * Instance for object STATISTICFUNCTION.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort STATISTICFUNCTION_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_STATISTICFUNCTION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier STATISTICFUNCTION_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("StatisticFunction");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType STATISTICFUNCTION_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), STATISTIC_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), STATISTICFUNCTION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject STATISTICFUNCTION_OBJECT = new org.ccsds.moims.mo.com.COMObject(STATISTICFUNCTION_OBJECT_TYPE, STATISTICFUNCTION_OBJECT_NAME, org.ccsds.moims.mo.mc.statistic.structures.StatisticFunctionDetails.SHORT_FORM, false, null, false, null, false);

    /**
     * Literal for object STATISTICLINK.
     */
    @Deprecated
    public static final int _STATISTICLINK_OBJECT_NUMBER = 2;

    /**
     * Instance for object STATISTICLINK.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort STATISTICLINK_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_STATISTICLINK_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier STATISTICLINK_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("StatisticLink");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType STATISTICLINK_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), STATISTIC_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), STATISTICLINK_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject STATISTICLINK_OBJECT = new org.ccsds.moims.mo.com.COMObject(STATISTICLINK_OBJECT_TYPE, STATISTICLINK_OBJECT_NAME, null, true, org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo.STATISTICFUNCTION_OBJECT_TYPE, true, org.ccsds.moims.mo.mc.parameter.ParameterServiceInfo.PARAMETERIDENTITY_OBJECT_TYPE, false);

    /**
     * Literal for object STATISTICLINKDEFINITION.
     */
    @Deprecated
    public static final int _STATISTICLINKDEFINITION_OBJECT_NUMBER = 3;

    /**
     * Instance for object STATISTICLINKDEFINITION.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort STATISTICLINKDEFINITION_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_STATISTICLINKDEFINITION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier STATISTICLINKDEFINITION_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("StatisticLinkDefinition");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType STATISTICLINKDEFINITION_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), STATISTIC_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), STATISTICLINKDEFINITION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject STATISTICLINKDEFINITION_OBJECT = new org.ccsds.moims.mo.com.COMObject(STATISTICLINKDEFINITION_OBJECT_TYPE, STATISTICLINKDEFINITION_OBJECT_NAME, org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkDetails.SHORT_FORM, true, org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo.STATISTICLINK_OBJECT_TYPE, false, null, false);

    /**
     * Literal for object STATISTICVALUEINSTANCE.
     */
    @Deprecated
    public static final int _STATISTICVALUEINSTANCE_OBJECT_NUMBER = 4;

    /**
     * Instance for object STATISTICVALUEINSTANCE.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort STATISTICVALUEINSTANCE_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_STATISTICVALUEINSTANCE_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier STATISTICVALUEINSTANCE_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("StatisticValueInstance");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType STATISTICVALUEINSTANCE_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), STATISTIC_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), STATISTICVALUEINSTANCE_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject STATISTICVALUEINSTANCE_OBJECT = new org.ccsds.moims.mo.com.COMObject(STATISTICVALUEINSTANCE_OBJECT_TYPE, STATISTICVALUEINSTANCE_OBJECT_NAME, org.ccsds.moims.mo.mc.statistic.structures.StatisticValue.SHORT_FORM, true, org.ccsds.moims.mo.mc.statistic.StatisticServiceInfo.STATISTICLINKDEFINITION_OBJECT_TYPE, true, null, false);

    /**
     * Object instance.
     */
    public static final org.ccsds.moims.mo.com.COMObject[] COM_OBJECTS = {
        STATISTICFUNCTION_OBJECT,
        STATISTICLINK_OBJECT,
        STATISTICLINKDEFINITION_OBJECT,
        STATISTICVALUEINSTANCE_OBJECT,};

    /**
     * Creates an instance of the Statistic ServiceInfo.
     * 
     */
    public StatisticServiceInfo() {
        super(SERVICE_KEY, STATISTIC_SERVICE_NAME, STATISTIC_SERVICE_ELEMENTS, OPERATIONS, COM_OBJECTS);
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
