package org.ccsds.moims.mo.mc.alert;

/**
 * Helper class for Alert service.
 */
public class AlertServiceInfo extends org.ccsds.moims.mo.mal.ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _ALERT_SERVICE_NUMBER = 3;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort ALERT_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ALERT_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier ALERT_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("Alert");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            4, 2, ALERT_SERVICE_NUMBER);

    /**
     * Operation number literal for operation MONITORALERT.
     */
    public static final int _MONITORALERT_OP_NUMBER = 1;

    /**
     * Operation number instance for operation MONITORALERT.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort MONITORALERT_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_MONITORALERT_OP_NUMBER);

    /**
     * Operation instance for operation MONITORALERT.
     */
    public static final org.ccsds.moims.mo.mal.MALPubSubOperation MONITORALERT_OP = new org.ccsds.moims.mo.mal.MALPubSubOperation(SERVICE_KEY, 
            MONITORALERT_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("monitorAlert"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("timestamp", false, org.ccsds.moims.mo.mal.structures.Attribute.TIME_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("arguments", true, org.ccsds.moims.mo.mal.structures.NullableAttributeList.SHORT_FORM, "")}, 
            "The monitorAlert operation allows a consumer to subscribe for alerts.");

    /**
     * Key names instance for MONITORALERT operation of pubsub interaction pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.Identifier [] _MONITORALERT_OP_KEY_NAMES = {new org.ccsds.moims.mo.mal.structures.Identifier("alertKey"),
            new org.ccsds.moims.mo.mal.structures.Identifier("alertVersion"),
            new org.ccsds.moims.mo.mal.structures.Identifier("alertSeverity")};

    /**
     * Key names instance for MONITORALERT operation of pubsub interaction pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.IdentifierList MONITORALERT_OP_KEY_NAMES = new org.ccsds.moims.mo.mal.structures.IdentifierList(new java.util.ArrayList<>(java.util.Arrays.asList(_MONITORALERT_OP_KEY_NAMES)));

    /**
     * Operation number literal for operation GETALERTCONFIGURATION.
     */
    public static final int _GETALERTCONFIGURATION_OP_NUMBER = 2;

    /**
     * Operation number instance for operation GETALERTCONFIGURATION.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort GETALERTCONFIGURATION_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GETALERTCONFIGURATION_OP_NUMBER);

    /**
     * Operation instance for operation GETALERTCONFIGURATION.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation GETALERTCONFIGURATION_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            GETALERTCONFIGURATION_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("getAlertConfiguration"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("keys", false, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("alertConfigs", false, org.ccsds.moims.mo.mc.structures.AlertConfigurationList.SHORT_FORM, "")}, 
            "The getAlertConfiguration operation allows a consumer to retrieve the current configuration for the generation of an alert.");

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
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation ENABLEGENERATION_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            ENABLEGENERATION_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("enableGeneration"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("keys", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, "")}, 
            "The enableGeneration operation allows a consumer to control whether instances of specific alerts are generated or not.");

    /**
     * Operation number literal for operation DISABLEGENERATION.
     */
    public static final int _DISABLEGENERATION_OP_NUMBER = 4;

    /**
     * Operation number instance for operation DISABLEGENERATION.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort DISABLEGENERATION_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_DISABLEGENERATION_OP_NUMBER);

    /**
     * Operation instance for operation DISABLEGENERATION.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation DISABLEGENERATION_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            DISABLEGENERATION_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("disableGeneration"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("keys", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, "")}, 
            "The disableGeneration operation allows a consumer to stop the generation of instances of specific alerts.");

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] ALERT_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{MONITORALERT_OP,
        GETALERTCONFIGURATION_OP,
        ENABLEGENERATION_OP,
        DISABLEGENERATION_OP};

    /**
     * Creates an instance of the Alert ServiceInfo.
     * 
     */
    public AlertServiceInfo() {
        super(SERVICE_KEY, ALERT_SERVICE_NAME, ALERT_SERVICE_ELEMENTS, OPERATIONS);
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
