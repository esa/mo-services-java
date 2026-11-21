package org.ccsds.moims.mo.mc.aggregation;

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
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mc.AmbiguousException;
import org.ccsds.moims.mo.mc.DuplicateException;
import org.ccsds.moims.mo.mc.InvalidException;
import org.ccsds.moims.mo.mc.MCHelper;
import org.ccsds.moims.mo.mc.structures.AggregationDefinitionList;
import org.ccsds.moims.mo.mc.structures.AggregationValueList;
import org.ccsds.moims.mo.mc.structures.ParameterValueDataList;
import org.ccsds.moims.mo.mc.structures.ReportConfigurationList;

/**
 * Helper class for Aggregation service.
 */
public class AggregationServiceInfo extends ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _AGGREGATION_SERVICE_NUMBER = 6;

    /**
     * Service number instance.
     */
    public static final UShort AGGREGATION_SERVICE_NUMBER = new UShort(_AGGREGATION_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier AGGREGATION_SERVICE_NAME = new Identifier("Aggregation");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            4, 2, AGGREGATION_SERVICE_NUMBER);

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
                new OperationField("values", false, ParameterValueDataList.SHORT_FORM, "")}, 
            "The monitorValue operation allows a consumer to subscribe for aggregation value reports.");

    /**
     * Key names instance for MONITORVALUE operation of pubsub interaction pattern.
     */
    private static final Identifier [] _MONITORVALUE_OP_KEY_NAMES = {new Identifier("aggregationKey"),
            new Identifier("aggregationVersion")};

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
                new OperationField("aggregationValues", false, AggregationValueList.SHORT_FORM, "")}, 
            "The getValue operation returns the latest received value for a requested aggregation.");

    /**
     * Operation number literal for operation GETREPORTINGCONFIGURATION.
     */
    public static final int _GETREPORTINGCONFIGURATION_OP_NUMBER = 3;

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
            new UShort(3), 
            new OperationField[] {
                new OperationField("domain", true, IdentifierList.SHORT_FORM, ""),
                new OperationField("keys", false, IdentifierList.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("reportConfigs", false, ReportConfigurationList.SHORT_FORM, "")}, 
            "The getReportingConfiguration operation allows a consumer to retrieve the current configuration for the generation of reports for an aggregation.");

    /**
     * Operation number literal for operation ENABLEREPORTING.
     */
    public static final int _ENABLEREPORTING_OP_NUMBER = 4;

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
            new UShort(3), 
            new OperationField[] {
                new OperationField("domain", true, IdentifierList.SHORT_FORM, ""),
                new OperationField("keys", true, IdentifierList.SHORT_FORM, "")}, 
            "The enableReporting operation allows a consumer to request the generation of reports for specific aggregations.");

    /**
     * Operation number literal for operation DISABLEREPORTING.
     */
    public static final int _DISABLEREPORTING_OP_NUMBER = 5;

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
            new UShort(3), 
            new OperationField[] {
                new OperationField("domain", true, IdentifierList.SHORT_FORM, ""),
                new OperationField("keys", true, IdentifierList.SHORT_FORM, "")}, 
            "The disableReporting operation allows a consumer to stop the generation of reports for specific aggregations.");

    /**
     * Operation number literal for operation SETREPORTINGPERIOD.
     */
    public static final int _SETREPORTINGPERIOD_OP_NUMBER = 6;

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
            new UShort(3), 
            new OperationField[] {
                new OperationField("domain", true, IdentifierList.SHORT_FORM, ""),
                new OperationField("keys", false, IdentifierList.SHORT_FORM, ""),
                new OperationField("reportInterval", false, Attribute.DURATION_SHORT_FORM, "")}, 
            "The setReportingPeriod operation allows a consumer to set the reporting interval for specific aggregations.");

    /**
     * Operation number literal for operation LISTDEFINITION.
     */
    public static final int _LISTDEFINITION_OP_NUMBER = 7;

    /**
     * Operation number instance for operation LISTDEFINITION.
     */
    private static final UShort LISTDEFINITION_OP_NUMBER = new UShort(_LISTDEFINITION_OP_NUMBER);

    /**
     * Operation instance for operation LISTDEFINITION.
     */
    public static final MALRequestOperation LISTDEFINITION_OP = new MALRequestOperation(SERVICE_KEY, 
            LISTDEFINITION_OP_NUMBER, 
            new Identifier("listDefinition"), 
            new UShort(4), 
            new OperationField[] {
                new OperationField("domain", true, IdentifierList.SHORT_FORM, ""),
                new OperationField("keys", true, IdentifierList.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("definitions", false, AggregationDefinitionList.SHORT_FORM, "")}, 
            "The listDefinition operation allows a consumer to retrieve the AggregationDefinition objects for the supported aggregations of the provider.");

    /**
     * Operation number literal for operation ADDAGGREGATION.
     */
    public static final int _ADDAGGREGATION_OP_NUMBER = 8;

    /**
     * Operation number instance for operation ADDAGGREGATION.
     */
    private static final UShort ADDAGGREGATION_OP_NUMBER = new UShort(_ADDAGGREGATION_OP_NUMBER);

    /**
     * Operation instance for operation ADDAGGREGATION.
     */
    public static final MALSubmitOperation ADDAGGREGATION_OP = new MALSubmitOperation(SERVICE_KEY, 
            ADDAGGREGATION_OP_NUMBER, 
            new Identifier("addAggregation"), 
            new UShort(5), 
            new OperationField[] {
                new OperationField("newObjects", false, AggregationDefinitionList.SHORT_FORM, "")}, 
            "The addAggregation operation allows a consumer to define one or more aggregations that do not currently exist.");

    /**
     * Operation number literal for operation REMOVEAGGREGATION.
     */
    public static final int _REMOVEAGGREGATION_OP_NUMBER = 9;

    /**
     * Operation number instance for operation REMOVEAGGREGATION.
     */
    private static final UShort REMOVEAGGREGATION_OP_NUMBER = new UShort(_REMOVEAGGREGATION_OP_NUMBER);

    /**
     * Operation instance for operation REMOVEAGGREGATION.
     */
    public static final MALSubmitOperation REMOVEAGGREGATION_OP = new MALSubmitOperation(SERVICE_KEY, 
            REMOVEAGGREGATION_OP_NUMBER, 
            new Identifier("removeAggregation"), 
            new UShort(5), 
            new OperationField[] {
                new OperationField("domain", true, IdentifierList.SHORT_FORM, ""),
                new OperationField("keys", false, IdentifierList.SHORT_FORM, "")}, 
            "The removeAggregation operation allows a consumer to remove one or more aggregations from the list of aggregations supported by the aggregation provider.");

    /**
     * Area elements.
     */
    public static final Element[] AGGREGATION_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{MONITORVALUE_OP,
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
                    case 3:
                        return new InvalidException(extraInfo);
                }
                break;
            case 7:
                switch (errorNumber) {
                    case 65551:
                        return new UnknownException(extraInfo);
                    case 5:
                        return new AmbiguousException(extraInfo);
                }
                break;
            case 8:
                switch (errorNumber) {
                    case 2:
                        return new DuplicateException(extraInfo);
                    case 3:
                        return new InvalidException(extraInfo);
                }
                break;
            case 9:
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
