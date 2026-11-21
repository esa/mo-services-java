package org.ccsds.moims.mo.mc.alert;

import java.util.ArrayList;
import java.util.Arrays;
import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.ServiceKey;
import org.ccsds.moims.mo.mal.UnknownException;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mc.AmbiguousException;
import org.ccsds.moims.mo.mc.MCHelper;
import org.ccsds.moims.mo.mc.structures.AlertConfigurationList;

/**
 * Helper class for Alert service.
 */
public class AlertServiceInfo extends ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _ALERT_SERVICE_NUMBER = 3;

    /**
     * Service number instance.
     */
    public static final UShort ALERT_SERVICE_NUMBER = new UShort(_ALERT_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier ALERT_SERVICE_NAME = new Identifier("Alert");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            4, 2, ALERT_SERVICE_NUMBER);

    /**
     * Operation number literal for operation MONITORALERT.
     */
    public static final int _MONITORALERT_OP_NUMBER = 1;

    /**
     * Operation number instance for operation MONITORALERT.
     */
    private static final UShort MONITORALERT_OP_NUMBER = new UShort(_MONITORALERT_OP_NUMBER);

    /**
     * Operation instance for operation MONITORALERT.
     */
    public static final MALPubSubOperation MONITORALERT_OP = new MALPubSubOperation(SERVICE_KEY, 
            MONITORALERT_OP_NUMBER, 
            new Identifier("monitorAlert"), 
            new UShort(1), 
            new OperationField[] {
                new OperationField("timestamp", false, Attribute.TIME_SHORT_FORM, ""),
                new OperationField("arguments", true, NullableAttributeList.SHORT_FORM, "")}, 
            "The monitorAlert operation allows a consumer to subscribe for alerts.");

    /**
     * Key names instance for MONITORALERT operation of pubsub interaction pattern.
     */
    private static final Identifier [] _MONITORALERT_OP_KEY_NAMES = {new Identifier("alertKey"),
            new Identifier("alertVersion"),
            new Identifier("alertSeverity")};

    /**
     * Key names instance for MONITORALERT operation of pubsub interaction pattern.
     */
    private static final IdentifierList MONITORALERT_OP_KEY_NAMES = new IdentifierList(new ArrayList<>(Arrays.asList(_MONITORALERT_OP_KEY_NAMES)));

    /**
     * Operation number literal for operation GETALERTCONFIGURATION.
     */
    public static final int _GETALERTCONFIGURATION_OP_NUMBER = 2;

    /**
     * Operation number instance for operation GETALERTCONFIGURATION.
     */
    private static final UShort GETALERTCONFIGURATION_OP_NUMBER = new UShort(_GETALERTCONFIGURATION_OP_NUMBER);

    /**
     * Operation instance for operation GETALERTCONFIGURATION.
     */
    public static final MALRequestOperation GETALERTCONFIGURATION_OP = new MALRequestOperation(SERVICE_KEY, 
            GETALERTCONFIGURATION_OP_NUMBER, 
            new Identifier("getAlertConfiguration"), 
            new UShort(2), 
            new OperationField[] {
                new OperationField("domain", true, IdentifierList.SHORT_FORM, ""),
                new OperationField("keys", false, IdentifierList.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("alertConfigs", false, AlertConfigurationList.SHORT_FORM, "")}, 
            "The getAlertConfiguration operation allows a consumer to retrieve the current configuration for the generation of an alert.");

    /**
     * Operation number literal for operation ENABLEGENERATION.
     */
    public static final int _ENABLEGENERATION_OP_NUMBER = 3;

    /**
     * Operation number instance for operation ENABLEGENERATION.
     */
    private static final UShort ENABLEGENERATION_OP_NUMBER = new UShort(_ENABLEGENERATION_OP_NUMBER);

    /**
     * Operation instance for operation ENABLEGENERATION.
     */
    public static final MALSubmitOperation ENABLEGENERATION_OP = new MALSubmitOperation(SERVICE_KEY, 
            ENABLEGENERATION_OP_NUMBER, 
            new Identifier("enableGeneration"), 
            new UShort(2), 
            new OperationField[] {
                new OperationField("domain", true, IdentifierList.SHORT_FORM, ""),
                new OperationField("keys", true, IdentifierList.SHORT_FORM, "")}, 
            "The enableGeneration operation allows a consumer to control whether instances of specific alerts are generated or not.");

    /**
     * Operation number literal for operation DISABLEGENERATION.
     */
    public static final int _DISABLEGENERATION_OP_NUMBER = 4;

    /**
     * Operation number instance for operation DISABLEGENERATION.
     */
    private static final UShort DISABLEGENERATION_OP_NUMBER = new UShort(_DISABLEGENERATION_OP_NUMBER);

    /**
     * Operation instance for operation DISABLEGENERATION.
     */
    public static final MALSubmitOperation DISABLEGENERATION_OP = new MALSubmitOperation(SERVICE_KEY, 
            DISABLEGENERATION_OP_NUMBER, 
            new Identifier("disableGeneration"), 
            new UShort(2), 
            new OperationField[] {
                new OperationField("domain", true, IdentifierList.SHORT_FORM, ""),
                new OperationField("keys", true, IdentifierList.SHORT_FORM, "")}, 
            "The disableGeneration operation allows a consumer to stop the generation of instances of specific alerts.");

    /**
     * Area elements.
     */
    public static final Element[] ALERT_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{MONITORALERT_OP,
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
    public MALArea getArea() {
        return MCHelper.MC_AREA;
    }

    @Override
    public MOErrorException generateMOError(int operationNumber,
            int errorNumber,
            Object extraInfo) {
        switch (operationNumber) {
            case 2:
                switch (errorNumber) {
                    case 65551:
                        return new UnknownException(extraInfo);
                    case 5:
                        return new AmbiguousException(extraInfo);
                }
                break;
            case 3:
                switch (errorNumber) {
                    case 65551:
                        return new UnknownException(extraInfo);
                    case 5:
                        return new AmbiguousException(extraInfo);
                }
                break;
            case 4:
                switch (errorNumber) {
                    case 65551:
                        return new UnknownException(extraInfo);
                    case 5:
                        return new AmbiguousException(extraInfo);
                }
                break;
        }
        MOErrorException areaError = MCHelper.generateMOError(errorNumber, extraInfo);
        return (areaError != null) ? areaError : MALHelper.generateMOError(errorNumber, extraInfo);
    }

}
