package org.ccsds.moims.mo.mc;

import org.ccsds.moims.mo.mal.AreaElementFactory;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mc.action.structures.ActionCategory;
import org.ccsds.moims.mo.mc.action.structures.ActionCategoryList;
import org.ccsds.moims.mo.mc.action.structures.ActionCreationRequest;
import org.ccsds.moims.mo.mc.action.structures.ActionCreationRequestList;
import org.ccsds.moims.mo.mc.action.structures.ActionDefinitionDetails;
import org.ccsds.moims.mo.mc.action.structures.ActionDefinitionDetailsList;
import org.ccsds.moims.mo.mc.action.structures.ActionInstanceDetails;
import org.ccsds.moims.mo.mc.action.structures.ActionInstanceDetailsList;
import org.ccsds.moims.mo.mc.aggregation.structures.AggregationCategory;
import org.ccsds.moims.mo.mc.aggregation.structures.AggregationCategoryList;
import org.ccsds.moims.mo.mc.aggregation.structures.AggregationCreationRequest;
import org.ccsds.moims.mo.mc.aggregation.structures.AggregationCreationRequestList;
import org.ccsds.moims.mo.mc.aggregation.structures.AggregationDefinitionDetails;
import org.ccsds.moims.mo.mc.aggregation.structures.AggregationDefinitionDetailsList;
import org.ccsds.moims.mo.mc.aggregation.structures.AggregationParameterSet;
import org.ccsds.moims.mo.mc.aggregation.structures.AggregationParameterSetList;
import org.ccsds.moims.mo.mc.aggregation.structures.AggregationParameterValue;
import org.ccsds.moims.mo.mc.aggregation.structures.AggregationParameterValueList;
import org.ccsds.moims.mo.mc.aggregation.structures.AggregationSetValue;
import org.ccsds.moims.mo.mc.aggregation.structures.AggregationSetValueList;
import org.ccsds.moims.mo.mc.aggregation.structures.AggregationValue;
import org.ccsds.moims.mo.mc.aggregation.structures.AggregationValueDetails;
import org.ccsds.moims.mo.mc.aggregation.structures.AggregationValueDetailsList;
import org.ccsds.moims.mo.mc.aggregation.structures.AggregationValueList;
import org.ccsds.moims.mo.mc.aggregation.structures.GenerationMode;
import org.ccsds.moims.mo.mc.aggregation.structures.GenerationModeList;
import org.ccsds.moims.mo.mc.aggregation.structures.ThresholdFilter;
import org.ccsds.moims.mo.mc.aggregation.structures.ThresholdFilterList;
import org.ccsds.moims.mo.mc.aggregation.structures.ThresholdType;
import org.ccsds.moims.mo.mc.aggregation.structures.ThresholdTypeList;
import org.ccsds.moims.mo.mc.alert.structures.AlertCreationRequest;
import org.ccsds.moims.mo.mc.alert.structures.AlertCreationRequestList;
import org.ccsds.moims.mo.mc.alert.structures.AlertDefinitionDetails;
import org.ccsds.moims.mo.mc.alert.structures.AlertDefinitionDetailsList;
import org.ccsds.moims.mo.mc.alert.structures.AlertEventDetails;
import org.ccsds.moims.mo.mc.alert.structures.AlertEventDetailsList;
import org.ccsds.moims.mo.mc.check.structures.CheckLinkDetails;
import org.ccsds.moims.mo.mc.check.structures.CheckLinkDetailsList;
import org.ccsds.moims.mo.mc.check.structures.CheckLinkSummary;
import org.ccsds.moims.mo.mc.check.structures.CheckLinkSummaryList;
import org.ccsds.moims.mo.mc.check.structures.CheckResult;
import org.ccsds.moims.mo.mc.check.structures.CheckResultFilter;
import org.ccsds.moims.mo.mc.check.structures.CheckResultFilterList;
import org.ccsds.moims.mo.mc.check.structures.CheckResultList;
import org.ccsds.moims.mo.mc.check.structures.CheckResultSummary;
import org.ccsds.moims.mo.mc.check.structures.CheckResultSummaryList;
import org.ccsds.moims.mo.mc.check.structures.CheckState;
import org.ccsds.moims.mo.mc.check.structures.CheckStateList;
import org.ccsds.moims.mo.mc.check.structures.CheckTypedInstance;
import org.ccsds.moims.mo.mc.check.structures.CheckTypedInstanceList;
import org.ccsds.moims.mo.mc.check.structures.CompoundCheckDefinition;
import org.ccsds.moims.mo.mc.check.structures.CompoundCheckDefinitionList;
import org.ccsds.moims.mo.mc.check.structures.ConstantCheckDefinition;
import org.ccsds.moims.mo.mc.check.structures.ConstantCheckDefinitionList;
import org.ccsds.moims.mo.mc.check.structures.DeltaCheckDefinition;
import org.ccsds.moims.mo.mc.check.structures.DeltaCheckDefinitionList;
import org.ccsds.moims.mo.mc.check.structures.LimitCheckDefinition;
import org.ccsds.moims.mo.mc.check.structures.LimitCheckDefinitionList;
import org.ccsds.moims.mo.mc.check.structures.ReferenceCheckDefinition;
import org.ccsds.moims.mo.mc.check.structures.ReferenceCheckDefinitionList;
import org.ccsds.moims.mo.mc.check.structures.ReferenceValue;
import org.ccsds.moims.mo.mc.check.structures.ReferenceValueList;
import org.ccsds.moims.mo.mc.conversion.structures.DiscreteConversionDetails;
import org.ccsds.moims.mo.mc.conversion.structures.DiscreteConversionDetailsList;
import org.ccsds.moims.mo.mc.conversion.structures.LineConversionDetails;
import org.ccsds.moims.mo.mc.conversion.structures.LineConversionDetailsList;
import org.ccsds.moims.mo.mc.conversion.structures.PolyConversionDetails;
import org.ccsds.moims.mo.mc.conversion.structures.PolyConversionDetailsList;
import org.ccsds.moims.mo.mc.conversion.structures.RangeConversionDetails;
import org.ccsds.moims.mo.mc.conversion.structures.RangeConversionDetailsList;
import org.ccsds.moims.mo.mc.group.structures.GroupDetails;
import org.ccsds.moims.mo.mc.group.structures.GroupDetailsList;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterConversion;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterConversionList;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterCreationRequest;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterCreationRequestList;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterDefinitionDetails;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterDefinitionDetailsList;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterRawValue;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterRawValueList;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterValue;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterValueDetails;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterValueDetailsList;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterValueList;
import org.ccsds.moims.mo.mc.parameter.structures.ValidityState;
import org.ccsds.moims.mo.mc.parameter.structures.ValidityStateList;
import org.ccsds.moims.mo.mc.statistic.structures.StatisticCreationRequest;
import org.ccsds.moims.mo.mc.statistic.structures.StatisticCreationRequestList;
import org.ccsds.moims.mo.mc.statistic.structures.StatisticEvaluationReport;
import org.ccsds.moims.mo.mc.statistic.structures.StatisticEvaluationReportList;
import org.ccsds.moims.mo.mc.statistic.structures.StatisticFunctionDetails;
import org.ccsds.moims.mo.mc.statistic.structures.StatisticFunctionDetailsList;
import org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkDetails;
import org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkDetailsList;
import org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkSummary;
import org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkSummaryList;
import org.ccsds.moims.mo.mc.statistic.structures.StatisticValue;
import org.ccsds.moims.mo.mc.statistic.structures.StatisticValueList;
import org.ccsds.moims.mo.mc.structures.ArgumentDefinitionDetails;
import org.ccsds.moims.mo.mc.structures.ArgumentDefinitionDetailsList;
import org.ccsds.moims.mo.mc.structures.AttributeValue;
import org.ccsds.moims.mo.mc.structures.AttributeValueList;
import org.ccsds.moims.mo.mc.structures.ConditionalConversion;
import org.ccsds.moims.mo.mc.structures.ConditionalConversionList;
import org.ccsds.moims.mo.mc.structures.ObjectInstancePair;
import org.ccsds.moims.mo.mc.structures.ObjectInstancePairList;
import org.ccsds.moims.mo.mc.structures.ParameterExpression;
import org.ccsds.moims.mo.mc.structures.ParameterExpressionList;
import org.ccsds.moims.mo.mc.structures.Severity;
import org.ccsds.moims.mo.mc.structures.SeverityList;

/**
 * Creates the Elements of the MC area, without holding an instance of each
 * of them, so that the class of a type is only loaded once a message carries
 * that type.
 */
public final class MCElementFactory implements AreaElementFactory {

    @Override
    public Element createElement(int serviceNumber,
            int typeNumber) {
        switch (serviceNumber) {
            case 0: return createAreaElement(typeNumber);
            case 1: return createActionElement(typeNumber);
            case 2: return createParameterElement(typeNumber);
            case 3: return createAlertElement(typeNumber);
            case 4: return createCheckElement(typeNumber);
            case 5: return createStatisticElement(typeNumber);
            case 6: return createAggregationElement(typeNumber);
            case 7: return createConversionElement(typeNumber);
            case 8: return createGroupElement(typeNumber);
            default: return null;
        }
    }

    @Override
    public int getAreaNumber() {
        return 4;
    }

    @Override
    public int getAreaVersion() {
        return 1;
    }

    /**
     * Creates an Element declared by the area itself.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static Element createAreaElement(int typeNumber) {
        switch (typeNumber) {
            case -7: return new ObjectInstancePairList();
            case -6: return new SeverityList();
            case -4: return new ParameterExpressionList();
            case -3: return new ConditionalConversionList();
            case -2: return new AttributeValueList();
            case -1: return new ArgumentDefinitionDetailsList();
            case 1: return new ArgumentDefinitionDetails();
            case 2: return new AttributeValue();
            case 3: return new ConditionalConversion();
            case 4: return new ParameterExpression();
            case 6: return new Severity();
            case 7: return new ObjectInstancePair();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the Action service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static Element createActionElement(int typeNumber) {
        switch (typeNumber) {
            case -4: return new ActionCategoryList();
            case -3: return new ActionCreationRequestList();
            case -2: return new ActionInstanceDetailsList();
            case -1: return new ActionDefinitionDetailsList();
            case 1: return new ActionDefinitionDetails();
            case 2: return new ActionInstanceDetails();
            case 3: return new ActionCreationRequest();
            case 4: return new ActionCategory();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the Parameter service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static Element createParameterElement(int typeNumber) {
        switch (typeNumber) {
            case -7: return new ParameterValueDetailsList();
            case -6: return new ParameterRawValueList();
            case -5: return new ParameterCreationRequestList();
            case -4: return new ValidityStateList();
            case -3: return new ParameterConversionList();
            case -2: return new ParameterValueList();
            case -1: return new ParameterDefinitionDetailsList();
            case 1: return new ParameterDefinitionDetails();
            case 2: return new ParameterValue();
            case 3: return new ParameterConversion();
            case 4: return new ValidityState();
            case 5: return new ParameterCreationRequest();
            case 6: return new ParameterRawValue();
            case 7: return new ParameterValueDetails();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the Alert service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static Element createAlertElement(int typeNumber) {
        switch (typeNumber) {
            case -3: return new AlertCreationRequestList();
            case -2: return new AlertEventDetailsList();
            case -1: return new AlertDefinitionDetailsList();
            case 1: return new AlertDefinitionDetails();
            case 2: return new AlertEventDetails();
            case 3: return new AlertCreationRequest();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the Check service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static Element createCheckElement(int typeNumber) {
        switch (typeNumber) {
            case -13: return new CheckTypedInstanceList();
            case -12: return new CompoundCheckDefinitionList();
            case -11: return new LimitCheckDefinitionList();
            case -10: return new DeltaCheckDefinitionList();
            case -9: return new ReferenceCheckDefinitionList();
            case -8: return new ConstantCheckDefinitionList();
            case -7: return new ReferenceValueList();
            case -6: return new CheckStateList();
            case -5: return new CheckResultFilterList();
            case -4: return new CheckResultSummaryList();
            case -3: return new CheckLinkSummaryList();
            case -2: return new CheckResultList();
            case -1: return new CheckLinkDetailsList();
            case 1: return new CheckLinkDetails();
            case 2: return new CheckResult();
            case 3: return new CheckLinkSummary();
            case 4: return new CheckResultSummary();
            case 5: return new CheckResultFilter();
            case 6: return new CheckState();
            case 7: return new ReferenceValue();
            case 8: return new ConstantCheckDefinition();
            case 9: return new ReferenceCheckDefinition();
            case 10: return new DeltaCheckDefinition();
            case 11: return new LimitCheckDefinition();
            case 12: return new CompoundCheckDefinition();
            case 13: return new CheckTypedInstance();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the Statistic service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static Element createStatisticElement(int typeNumber) {
        switch (typeNumber) {
            case -6: return new StatisticEvaluationReportList();
            case -5: return new StatisticLinkSummaryList();
            case -4: return new StatisticCreationRequestList();
            case -3: return new StatisticValueList();
            case -2: return new StatisticLinkDetailsList();
            case -1: return new StatisticFunctionDetailsList();
            case 1: return new StatisticFunctionDetails();
            case 2: return new StatisticLinkDetails();
            case 3: return new StatisticValue();
            case 4: return new StatisticCreationRequest();
            case 5: return new StatisticLinkSummary();
            case 6: return new StatisticEvaluationReport();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the Aggregation service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static Element createAggregationElement(int typeNumber) {
        switch (typeNumber) {
            case -11: return new AggregationValueDetailsList();
            case -10: return new AggregationCreationRequestList();
            case -9: return new GenerationModeList();
            case -8: return new ThresholdTypeList();
            case -7: return new AggregationCategoryList();
            case -6: return new ThresholdFilterList();
            case -5: return new AggregationParameterValueList();
            case -4: return new AggregationSetValueList();
            case -3: return new AggregationValueList();
            case -2: return new AggregationParameterSetList();
            case -1: return new AggregationDefinitionDetailsList();
            case 1: return new AggregationDefinitionDetails();
            case 2: return new AggregationParameterSet();
            case 3: return new AggregationValue();
            case 4: return new AggregationSetValue();
            case 5: return new AggregationParameterValue();
            case 6: return new ThresholdFilter();
            case 7: return new AggregationCategory();
            case 8: return new ThresholdType();
            case 9: return new GenerationMode();
            case 10: return new AggregationCreationRequest();
            case 11: return new AggregationValueDetails();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the Conversion service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static Element createConversionElement(int typeNumber) {
        switch (typeNumber) {
            case -4: return new RangeConversionDetailsList();
            case -3: return new PolyConversionDetailsList();
            case -2: return new LineConversionDetailsList();
            case -1: return new DiscreteConversionDetailsList();
            case 1: return new DiscreteConversionDetails();
            case 2: return new LineConversionDetails();
            case 3: return new PolyConversionDetails();
            case 4: return new RangeConversionDetails();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the Group service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static Element createGroupElement(int typeNumber) {
        switch (typeNumber) {
            case -1: return new GroupDetailsList();
            case 1: return new GroupDetails();
            default: return null;
        }
    }

}
