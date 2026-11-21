package org.ccsds.moims.mo.mc.parameter;

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
import org.ccsds.moims.mo.mc.InvalidException;
import org.ccsds.moims.mo.mc.MCHelper;
import org.ccsds.moims.mo.mc.ReadOnlyException;
import org.ccsds.moims.mo.mc.structures.ParameterValueData;
import org.ccsds.moims.mo.mc.structures.ParameterValueList;
import org.ccsds.moims.mo.mc.structures.ReportConfigurationList;

/**
 * Helper class for Parameter service.
 */
public class ParameterServiceInfo extends ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _PARAMETER_SERVICE_NUMBER = 2;

    /**
     * Service number instance.
     */
    public static final UShort PARAMETER_SERVICE_NUMBER = new UShort(_PARAMETER_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier PARAMETER_SERVICE_NAME = new Identifier("Parameter");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            4, 2, PARAMETER_SERVICE_NUMBER);

    /**
     * Operation number literal for operation MONITORVALUE.
     */
    public static final int _MONITORVALUE_OP_NUMBER = 1;

    /**
     * Operation number instance for operation MONITORVALUE.
     */
    private static final UShort MONITORVALUE_OP_NUMBER = new UShort(_MONITORVALUE_OP_NUMBER);

    /**
     * Operation instance for operation MONITORVALUE.
     */
    public static final MALPubSubOperation MONITORVALUE_OP = new MALPubSubOperation(SERVICE_KEY, 
            MONITORVALUE_OP_NUMBER, 
            new Identifier("monitorValue"), 
            new UShort(1), 
            new OperationField[] {
                new OperationField("timestamp", false, Attribute.TIME_SHORT_FORM, ""),
                new OperationField("samplingTime", true, Attribute.TIME_SHORT_FORM, ""),
                new OperationField("newValue", false, ParameterValueData.SHORT_FORM, "")}, 
            "The monitorValue operation allows a consumer to subscribe for parameter value reports.");

    /**
     * Key names instance for MONITORVALUE operation of pubsub interaction pattern.
     */
    private static final Identifier [] _MONITORVALUE_OP_KEY_NAMES = {new Identifier("parameterKey"),
            new Identifier("parameterVersion")};

    /**
     * Key names instance for MONITORVALUE operation of pubsub interaction pattern.
     */
    private static final IdentifierList MONITORVALUE_OP_KEY_NAMES = new IdentifierList(new ArrayList<>(Arrays.asList(_MONITORVALUE_OP_KEY_NAMES)));

    /**
     * Operation number literal for operation GETVALUE.
     */
    public static final int _GETVALUE_OP_NUMBER = 2;

    /**
     * Operation number instance for operation GETVALUE.
     */
    private static final UShort GETVALUE_OP_NUMBER = new UShort(_GETVALUE_OP_NUMBER);

    /**
     * Operation instance for operation GETVALUE.
     */
    public static final MALRequestOperation GETVALUE_OP = new MALRequestOperation(SERVICE_KEY, 
            GETVALUE_OP_NUMBER, 
            new Identifier("getValue"), 
            new UShort(2), 
            new OperationField[] {
                new OperationField("domain", true, IdentifierList.SHORT_FORM, ""),
                new OperationField("keys", false, IdentifierList.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("parameterValues", false, ParameterValueList.SHORT_FORM, "")}, 
            "The getValue operation returns the latest received value for a requested parameter.");

    /**
     * Operation number literal for operation SETVALUE.
     */
    public static final int _SETVALUE_OP_NUMBER = 3;

    /**
     * Operation number instance for operation SETVALUE.
     */
    private static final UShort SETVALUE_OP_NUMBER = new UShort(_SETVALUE_OP_NUMBER);

    /**
     * Operation instance for operation SETVALUE.
     */
    public static final MALSubmitOperation SETVALUE_OP = new MALSubmitOperation(SERVICE_KEY, 
            SETVALUE_OP_NUMBER, 
            new Identifier("setValue"), 
            new UShort(3), 
            new OperationField[] {
                new OperationField("domain", true, IdentifierList.SHORT_FORM, ""),
                new OperationField("keys", false, IdentifierList.SHORT_FORM, ""),
                new OperationField("newRawValues", false, NullableAttributeList.SHORT_FORM, "")}, 
            "The setValue operation allows a consumer to set the raw value for one or more parameters.");

    /**
     * Operation number literal for operation GETREPORTINGCONFIGURATION.
     */
    public static final int _GETREPORTINGCONFIGURATION_OP_NUMBER = 4;

    /**
     * Operation number instance for operation GETREPORTINGCONFIGURATION.
     */
    private static final UShort GETREPORTINGCONFIGURATION_OP_NUMBER = new UShort(_GETREPORTINGCONFIGURATION_OP_NUMBER);

    /**
     * Operation instance for operation GETREPORTINGCONFIGURATION.
     */
    public static final MALRequestOperation GETREPORTINGCONFIGURATION_OP = new MALRequestOperation(SERVICE_KEY, 
            GETREPORTINGCONFIGURATION_OP_NUMBER, 
            new Identifier("getReportingConfiguration"), 
            new UShort(4), 
            new OperationField[] {
                new OperationField("domain", true, IdentifierList.SHORT_FORM, ""),
                new OperationField("keys", false, IdentifierList.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("reportConfigs", false, ReportConfigurationList.SHORT_FORM, "")}, 
            "The getReportingConfiguration operation allows a consumer to retrieve the current configuration for the generation of reports for a parameter.");

    /**
     * Operation number literal for operation ENABLEREPORTING.
     */
    public static final int _ENABLEREPORTING_OP_NUMBER = 5;

    /**
     * Operation number instance for operation ENABLEREPORTING.
     */
    private static final UShort ENABLEREPORTING_OP_NUMBER = new UShort(_ENABLEREPORTING_OP_NUMBER);

    /**
     * Operation instance for operation ENABLEREPORTING.
     */
    public static final MALSubmitOperation ENABLEREPORTING_OP = new MALSubmitOperation(SERVICE_KEY, 
            ENABLEREPORTING_OP_NUMBER, 
            new Identifier("enableReporting"), 
            new UShort(4), 
            new OperationField[] {
                new OperationField("domain", true, IdentifierList.SHORT_FORM, ""),
                new OperationField("keys", true, IdentifierList.SHORT_FORM, "")}, 
            "The enableReporting operation allows a consumer to request the generation of reports for specific parameters.");

    /**
     * Operation number literal for operation DISABLEREPORTING.
     */
    public static final int _DISABLEREPORTING_OP_NUMBER = 6;

    /**
     * Operation number instance for operation DISABLEREPORTING.
     */
    private static final UShort DISABLEREPORTING_OP_NUMBER = new UShort(_DISABLEREPORTING_OP_NUMBER);

    /**
     * Operation instance for operation DISABLEREPORTING.
     */
    public static final MALSubmitOperation DISABLEREPORTING_OP = new MALSubmitOperation(SERVICE_KEY, 
            DISABLEREPORTING_OP_NUMBER, 
            new Identifier("disableReporting"), 
            new UShort(4), 
            new OperationField[] {
                new OperationField("domain", true, IdentifierList.SHORT_FORM, ""),
                new OperationField("keys", true, IdentifierList.SHORT_FORM, "")}, 
            "The disableReporting operation allows a consumer to stop the generation of reports for specific parameters.");

    /**
     * Operation number literal for operation SETREPORTINGPERIOD.
     */
    public static final int _SETREPORTINGPERIOD_OP_NUMBER = 7;

    /**
     * Operation number instance for operation SETREPORTINGPERIOD.
     */
    private static final UShort SETREPORTINGPERIOD_OP_NUMBER = new UShort(_SETREPORTINGPERIOD_OP_NUMBER);

    /**
     * Operation instance for operation SETREPORTINGPERIOD.
     */
    public static final MALSubmitOperation SETREPORTINGPERIOD_OP = new MALSubmitOperation(SERVICE_KEY, 
            SETREPORTINGPERIOD_OP_NUMBER, 
            new Identifier("setReportingPeriod"), 
            new UShort(4), 
            new OperationField[] {
                new OperationField("domain", true, IdentifierList.SHORT_FORM, ""),
                new OperationField("keys", false, IdentifierList.SHORT_FORM, ""),
                new OperationField("reportInterval", false, Attribute.DURATION_SHORT_FORM, "")}, 
            "The setReportingPeriod operation allows a consumer to set the reporting interval for specific parameters.");

    /**
     * Area elements.
     */
    public static final Element[] PARAMETER_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{MONITORVALUE_OP,
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
                    case 3:
                        return new InvalidException(extraInfo);
                    case 1:
                        return new ReadOnlyException(extraInfo);
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
            case 5:
                switch (errorNumber) {
                    case 65551:
                        return new UnknownException(extraInfo);
                    case 5:
                        return new AmbiguousException(extraInfo);
                }
                break;
            case 6:
                switch (errorNumber) {
                    case 65551:
                        return new UnknownException(extraInfo);
                    case 5:
                        return new AmbiguousException(extraInfo);
                }
                break;
            case 7:
                switch (errorNumber) {
                    case 65551:
                        return new UnknownException(extraInfo);
                    case 3:
                        return new InvalidException(extraInfo);
                    case 5:
                        return new AmbiguousException(extraInfo);
                }
                break;
        }
        MOErrorException areaError = MCHelper.generateMOError(errorNumber, extraInfo);
        return (areaError != null) ? areaError : MALHelper.generateMOError(errorNumber, extraInfo);
    }

}
