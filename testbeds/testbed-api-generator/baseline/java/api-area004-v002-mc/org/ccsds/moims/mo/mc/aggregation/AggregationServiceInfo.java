package org.ccsds.moims.mo.mc.aggregation;

/**
 * Helper class for Aggregation service.
 */
public class AggregationServiceInfo extends org.ccsds.moims.mo.mal.ServiceInfo {

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
            4, 2, AGGREGATION_SERVICE_NUMBER);

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
                new org.ccsds.moims.mo.mal.OperationField("timestamp", false, org.ccsds.moims.mo.mal.structures.Attribute.TIME_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("values", false, org.ccsds.moims.mo.mc.structures.ParameterValueDataList.SHORT_FORM, "")}, 
            "The monitorValue operation allows a consumer to subscribe for aggregation value reports.");

    /**
     * Key names instance for MONITORVALUE operation of pubsub interaction pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.Identifier [] _MONITORVALUE_OP_KEY_NAMES = {new org.ccsds.moims.mo.mal.structures.Identifier("aggregationKey"),
            new org.ccsds.moims.mo.mal.structures.Identifier("aggregationVersion")};

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
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("keys", false, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("aggregationValues", false, org.ccsds.moims.mo.mc.structures.AggregationValueList.SHORT_FORM, "")}, 
            "The getValue operation returns the latest received value for a requested aggregation.");

    /**
     * Operation number literal for operation GETREPORTINGCONFIGURATION.
     */
    public static final int _GETREPORTINGCONFIGURATION_OP_NUMBER = 3;

    /**
     * Operation number instance for operation GETREPORTINGCONFIGURATION.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort GETREPORTINGCONFIGURATION_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GETREPORTINGCONFIGURATION_OP_NUMBER);

    /**
     * Operation instance for operation GETREPORTINGCONFIGURATION.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation GETREPORTINGCONFIGURATION_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            GETREPORTINGCONFIGURATION_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("getReportingConfiguration"), 
            new org.ccsds.moims.mo.mal.structures.UShort(3), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("keys", false, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("reportConfigs", false, org.ccsds.moims.mo.mc.structures.ReportConfigurationList.SHORT_FORM, "")}, 
            "The getReportingConfiguration operation allows a consumer to retrieve the current configuration for the generation of reports for an aggregation.");

    /**
     * Operation number literal for operation ENABLEREPORTING.
     */
    public static final int _ENABLEREPORTING_OP_NUMBER = 4;

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
            new org.ccsds.moims.mo.mal.structures.UShort(3), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("keys", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, "")}, 
            "The enableReporting operation allows a consumer to request the generation of reports for specific aggregations.");

    /**
     * Operation number literal for operation DISABLEREPORTING.
     */
    public static final int _DISABLEREPORTING_OP_NUMBER = 5;

    /**
     * Operation number instance for operation DISABLEREPORTING.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort DISABLEREPORTING_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_DISABLEREPORTING_OP_NUMBER);

    /**
     * Operation instance for operation DISABLEREPORTING.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation DISABLEREPORTING_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            DISABLEREPORTING_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("disableReporting"), 
            new org.ccsds.moims.mo.mal.structures.UShort(3), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("keys", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, "")}, 
            "The disableReporting operation allows a consumer to stop the generation of reports for specific aggregations.");

    /**
     * Operation number literal for operation SETREPORTINGPERIOD.
     */
    public static final int _SETREPORTINGPERIOD_OP_NUMBER = 6;

    /**
     * Operation number instance for operation SETREPORTINGPERIOD.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort SETREPORTINGPERIOD_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_SETREPORTINGPERIOD_OP_NUMBER);

    /**
     * Operation instance for operation SETREPORTINGPERIOD.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation SETREPORTINGPERIOD_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            SETREPORTINGPERIOD_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("setReportingPeriod"), 
            new org.ccsds.moims.mo.mal.structures.UShort(3), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("keys", false, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("reportInterval", false, org.ccsds.moims.mo.mal.structures.Attribute.DURATION_SHORT_FORM, "")}, 
            "The setReportingPeriod operation allows a consumer to set the reporting interval for specific aggregations.");

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
            new org.ccsds.moims.mo.mal.structures.UShort(4), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("keys", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("definitions", false, org.ccsds.moims.mo.mc.structures.AggregationDefinitionList.SHORT_FORM, "")}, 
            "The listDefinition operation allows a consumer to retrieve the AggregationDefinition objects for the supported aggregations of the provider.");

    /**
     * Operation number literal for operation ADDAGGREGATION.
     */
    public static final int _ADDAGGREGATION_OP_NUMBER = 8;

    /**
     * Operation number instance for operation ADDAGGREGATION.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort ADDAGGREGATION_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ADDAGGREGATION_OP_NUMBER);

    /**
     * Operation instance for operation ADDAGGREGATION.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation ADDAGGREGATION_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            ADDAGGREGATION_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("addAggregation"), 
            new org.ccsds.moims.mo.mal.structures.UShort(5), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("newObjects", false, org.ccsds.moims.mo.mc.structures.AggregationDefinitionList.SHORT_FORM, "")}, 
            "The addAggregation operation allows a consumer to define one or more aggregations that do not currently exist.");

    /**
     * Operation number literal for operation REMOVEAGGREGATION.
     */
    public static final int _REMOVEAGGREGATION_OP_NUMBER = 9;

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
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("keys", false, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, "")}, 
            "The removeAggregation operation allows a consumer to remove one or more aggregations from the list of aggregations supported by the aggregation provider.");

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] AGGREGATION_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{MONITORVALUE_OP,
        GETVALUE_OP,
        GETREPORTINGCONFIGURATION_OP,
        ENABLEREPORTING_OP,
        DISABLEREPORTING_OP,
        SETREPORTINGPERIOD_OP,
        LISTDEFINITION_OP,
        ADDAGGREGATION_OP,
        REMOVEAGGREGATION_OP};

    /**
     * Creates an instance of the Aggregation ServiceInfo.
     * 
     */
    public AggregationServiceInfo() {
        super(SERVICE_KEY, AGGREGATION_SERVICE_NAME, AGGREGATION_SERVICE_ELEMENTS, OPERATIONS);
    }

    @Override
    public org.ccsds.moims.mo.mal.MALArea getArea() {
        return org.ccsds.moims.mo.mc.MCHelper.MC_AREA;
    }

    @Override
    public org.ccsds.moims.mo.mal.MOErrorException generateMOError(int errorNumber,
            Object extraInfo) {
        switch (errorNumber) {
            case 1:
                return new org.ccsds.moims.mo.mc.ReadOnlyException(extraInfo);
            case 2:
                return new org.ccsds.moims.mo.mc.DuplicateException(extraInfo);
            case 3:
                return new org.ccsds.moims.mo.mc.InvalidException(extraInfo);
            case 4:
                return new org.ccsds.moims.mo.mc.RejectedException(extraInfo);
            case 5:
                return new org.ccsds.moims.mo.mc.AmbiguousException(extraInfo);
        }
        return null;
    }

}
