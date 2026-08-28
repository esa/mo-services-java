package org.ccsds.moims.mo.mc.parameter;

/**
 * Helper class for Parameter service.
 */
public class ParameterServiceInfo extends org.ccsds.moims.mo.mal.ServiceInfo {

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
            4, 2, PARAMETER_SERVICE_NUMBER);

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
                new org.ccsds.moims.mo.mal.OperationField("samplingTime", true, org.ccsds.moims.mo.mal.structures.Attribute.TIME_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("newValue", false, org.ccsds.moims.mo.mc.structures.ParameterValueData.SHORT_FORM, "")}, 
            "The monitorValue operation allows a consumer to subscribe for parameter value reports.");

    /**
     * Key names instance for MONITORVALUE operation of pubsub interaction pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.Identifier [] _MONITORVALUE_OP_KEY_NAMES = {new org.ccsds.moims.mo.mal.structures.Identifier("parameterKey"),
            new org.ccsds.moims.mo.mal.structures.Identifier("parameterVersion")};

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
                new org.ccsds.moims.mo.mal.OperationField("parameterValues", false, org.ccsds.moims.mo.mc.structures.ParameterValueList.SHORT_FORM, "")}, 
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
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("keys", false, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("newRawValues", false, org.ccsds.moims.mo.mal.structures.NullableAttributeList.SHORT_FORM, "")}, 
            "The setValue operation allows a consumer to set the raw value for one or more parameters.");

    /**
     * Operation number literal for operation GETREPORTINGCONFIGURATION.
     */
    public static final int _GETREPORTINGCONFIGURATION_OP_NUMBER = 4;

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
            new org.ccsds.moims.mo.mal.structures.UShort(4), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("keys", false, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("reportConfigs", false, org.ccsds.moims.mo.mc.structures.ReportConfigurationList.SHORT_FORM, "")}, 
            "The getReportingConfiguration operation allows a consumer to retrieve the current configuration for the generation of reports for a parameter.");

    /**
     * Operation number literal for operation ENABLEREPORTING.
     */
    public static final int _ENABLEREPORTING_OP_NUMBER = 5;

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
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("keys", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, "")}, 
            "The enableReporting operation allows a consumer to request the generation of reports for specific parameters.");

    /**
     * Operation number literal for operation DISABLEREPORTING.
     */
    public static final int _DISABLEREPORTING_OP_NUMBER = 6;

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
            new org.ccsds.moims.mo.mal.structures.UShort(4), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("keys", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, "")}, 
            "The disableReporting operation allows a consumer to stop the generation of reports for specific parameters.");

    /**
     * Operation number literal for operation SETREPORTINGPERIOD.
     */
    public static final int _SETREPORTINGPERIOD_OP_NUMBER = 7;

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
            new org.ccsds.moims.mo.mal.structures.UShort(4), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("keys", false, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("reportInterval", false, org.ccsds.moims.mo.mal.structures.Attribute.DURATION_SHORT_FORM, "")}, 
            "The setReportingPeriod operation allows a consumer to set the reporting interval for specific parameters.");

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
        GETREPORTINGCONFIGURATION_OP,
        ENABLEREPORTING_OP,
        DISABLEREPORTING_OP,
        SETREPORTINGPERIOD_OP};

    /**
     * Creates an instance of the Parameter ServiceInfo.
     * 
     */
    public ParameterServiceInfo() {
        super(SERVICE_KEY, PARAMETER_SERVICE_NAME, PARAMETER_SERVICE_ELEMENTS, OPERATIONS);
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
