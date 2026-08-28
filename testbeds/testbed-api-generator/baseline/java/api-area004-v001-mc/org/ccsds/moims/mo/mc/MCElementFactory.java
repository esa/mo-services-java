package org.ccsds.moims.mo.mc;

/**
 * Creates the Elements of the MC area, without holding an instance of each
 * of them, so that the class of a type is only loaded once a message carries
 * that type.
 */
public final class MCElementFactory implements org.ccsds.moims.mo.mal.AreaElementFactory {

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement(int serviceNumber,
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
    private static org.ccsds.moims.mo.mal.structures.Element createAreaElement(int typeNumber) {
        switch (typeNumber) {
            case -7: return new org.ccsds.moims.mo.mc.structures.ObjectInstancePairList();
            case -6: return new org.ccsds.moims.mo.mc.structures.SeverityList();
            case -4: return new org.ccsds.moims.mo.mc.structures.ParameterExpressionList();
            case -3: return new org.ccsds.moims.mo.mc.structures.ConditionalConversionList();
            case -2: return new org.ccsds.moims.mo.mc.structures.AttributeValueList();
            case -1: return new org.ccsds.moims.mo.mc.structures.ArgumentDefinitionDetailsList();
            case 1: return new org.ccsds.moims.mo.mc.structures.ArgumentDefinitionDetails();
            case 2: return new org.ccsds.moims.mo.mc.structures.AttributeValue();
            case 3: return new org.ccsds.moims.mo.mc.structures.ConditionalConversion();
            case 4: return new org.ccsds.moims.mo.mc.structures.ParameterExpression();
            case 6: return new org.ccsds.moims.mo.mc.structures.Severity();
            case 7: return new org.ccsds.moims.mo.mc.structures.ObjectInstancePair();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the Action service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static org.ccsds.moims.mo.mal.structures.Element createActionElement(int typeNumber) {
        switch (typeNumber) {
            case -4: return new org.ccsds.moims.mo.mc.action.structures.ActionCategoryList();
            case -3: return new org.ccsds.moims.mo.mc.action.structures.ActionCreationRequestList();
            case -2: return new org.ccsds.moims.mo.mc.action.structures.ActionInstanceDetailsList();
            case -1: return new org.ccsds.moims.mo.mc.action.structures.ActionDefinitionDetailsList();
            case 1: return new org.ccsds.moims.mo.mc.action.structures.ActionDefinitionDetails();
            case 2: return new org.ccsds.moims.mo.mc.action.structures.ActionInstanceDetails();
            case 3: return new org.ccsds.moims.mo.mc.action.structures.ActionCreationRequest();
            case 4: return new org.ccsds.moims.mo.mc.action.structures.ActionCategory();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the Parameter service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static org.ccsds.moims.mo.mal.structures.Element createParameterElement(int typeNumber) {
        switch (typeNumber) {
            case -7: return new org.ccsds.moims.mo.mc.parameter.structures.ParameterValueDetailsList();
            case -6: return new org.ccsds.moims.mo.mc.parameter.structures.ParameterRawValueList();
            case -5: return new org.ccsds.moims.mo.mc.parameter.structures.ParameterCreationRequestList();
            case -4: return new org.ccsds.moims.mo.mc.parameter.structures.ValidityStateList();
            case -3: return new org.ccsds.moims.mo.mc.parameter.structures.ParameterConversionList();
            case -2: return new org.ccsds.moims.mo.mc.parameter.structures.ParameterValueList();
            case -1: return new org.ccsds.moims.mo.mc.parameter.structures.ParameterDefinitionDetailsList();
            case 1: return new org.ccsds.moims.mo.mc.parameter.structures.ParameterDefinitionDetails();
            case 2: return new org.ccsds.moims.mo.mc.parameter.structures.ParameterValue();
            case 3: return new org.ccsds.moims.mo.mc.parameter.structures.ParameterConversion();
            case 4: return new org.ccsds.moims.mo.mc.parameter.structures.ValidityState();
            case 5: return new org.ccsds.moims.mo.mc.parameter.structures.ParameterCreationRequest();
            case 6: return new org.ccsds.moims.mo.mc.parameter.structures.ParameterRawValue();
            case 7: return new org.ccsds.moims.mo.mc.parameter.structures.ParameterValueDetails();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the Alert service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static org.ccsds.moims.mo.mal.structures.Element createAlertElement(int typeNumber) {
        switch (typeNumber) {
            case -3: return new org.ccsds.moims.mo.mc.alert.structures.AlertCreationRequestList();
            case -2: return new org.ccsds.moims.mo.mc.alert.structures.AlertEventDetailsList();
            case -1: return new org.ccsds.moims.mo.mc.alert.structures.AlertDefinitionDetailsList();
            case 1: return new org.ccsds.moims.mo.mc.alert.structures.AlertDefinitionDetails();
            case 2: return new org.ccsds.moims.mo.mc.alert.structures.AlertEventDetails();
            case 3: return new org.ccsds.moims.mo.mc.alert.structures.AlertCreationRequest();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the Check service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static org.ccsds.moims.mo.mal.structures.Element createCheckElement(int typeNumber) {
        switch (typeNumber) {
            case -13: return new org.ccsds.moims.mo.mc.check.structures.CheckTypedInstanceList();
            case -12: return new org.ccsds.moims.mo.mc.check.structures.CompoundCheckDefinitionList();
            case -11: return new org.ccsds.moims.mo.mc.check.structures.LimitCheckDefinitionList();
            case -10: return new org.ccsds.moims.mo.mc.check.structures.DeltaCheckDefinitionList();
            case -9: return new org.ccsds.moims.mo.mc.check.structures.ReferenceCheckDefinitionList();
            case -8: return new org.ccsds.moims.mo.mc.check.structures.ConstantCheckDefinitionList();
            case -7: return new org.ccsds.moims.mo.mc.check.structures.ReferenceValueList();
            case -6: return new org.ccsds.moims.mo.mc.check.structures.CheckStateList();
            case -5: return new org.ccsds.moims.mo.mc.check.structures.CheckResultFilterList();
            case -4: return new org.ccsds.moims.mo.mc.check.structures.CheckResultSummaryList();
            case -3: return new org.ccsds.moims.mo.mc.check.structures.CheckLinkSummaryList();
            case -2: return new org.ccsds.moims.mo.mc.check.structures.CheckResultList();
            case -1: return new org.ccsds.moims.mo.mc.check.structures.CheckLinkDetailsList();
            case 1: return new org.ccsds.moims.mo.mc.check.structures.CheckLinkDetails();
            case 2: return new org.ccsds.moims.mo.mc.check.structures.CheckResult();
            case 3: return new org.ccsds.moims.mo.mc.check.structures.CheckLinkSummary();
            case 4: return new org.ccsds.moims.mo.mc.check.structures.CheckResultSummary();
            case 5: return new org.ccsds.moims.mo.mc.check.structures.CheckResultFilter();
            case 6: return new org.ccsds.moims.mo.mc.check.structures.CheckState();
            case 7: return new org.ccsds.moims.mo.mc.check.structures.ReferenceValue();
            case 8: return new org.ccsds.moims.mo.mc.check.structures.ConstantCheckDefinition();
            case 9: return new org.ccsds.moims.mo.mc.check.structures.ReferenceCheckDefinition();
            case 10: return new org.ccsds.moims.mo.mc.check.structures.DeltaCheckDefinition();
            case 11: return new org.ccsds.moims.mo.mc.check.structures.LimitCheckDefinition();
            case 12: return new org.ccsds.moims.mo.mc.check.structures.CompoundCheckDefinition();
            case 13: return new org.ccsds.moims.mo.mc.check.structures.CheckTypedInstance();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the Statistic service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static org.ccsds.moims.mo.mal.structures.Element createStatisticElement(int typeNumber) {
        switch (typeNumber) {
            case -6: return new org.ccsds.moims.mo.mc.statistic.structures.StatisticEvaluationReportList();
            case -5: return new org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkSummaryList();
            case -4: return new org.ccsds.moims.mo.mc.statistic.structures.StatisticCreationRequestList();
            case -3: return new org.ccsds.moims.mo.mc.statistic.structures.StatisticValueList();
            case -2: return new org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkDetailsList();
            case -1: return new org.ccsds.moims.mo.mc.statistic.structures.StatisticFunctionDetailsList();
            case 1: return new org.ccsds.moims.mo.mc.statistic.structures.StatisticFunctionDetails();
            case 2: return new org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkDetails();
            case 3: return new org.ccsds.moims.mo.mc.statistic.structures.StatisticValue();
            case 4: return new org.ccsds.moims.mo.mc.statistic.structures.StatisticCreationRequest();
            case 5: return new org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkSummary();
            case 6: return new org.ccsds.moims.mo.mc.statistic.structures.StatisticEvaluationReport();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the Aggregation service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static org.ccsds.moims.mo.mal.structures.Element createAggregationElement(int typeNumber) {
        switch (typeNumber) {
            case -11: return new org.ccsds.moims.mo.mc.aggregation.structures.AggregationValueDetailsList();
            case -10: return new org.ccsds.moims.mo.mc.aggregation.structures.AggregationCreationRequestList();
            case -9: return new org.ccsds.moims.mo.mc.aggregation.structures.GenerationModeList();
            case -8: return new org.ccsds.moims.mo.mc.aggregation.structures.ThresholdTypeList();
            case -7: return new org.ccsds.moims.mo.mc.aggregation.structures.AggregationCategoryList();
            case -6: return new org.ccsds.moims.mo.mc.aggregation.structures.ThresholdFilterList();
            case -5: return new org.ccsds.moims.mo.mc.aggregation.structures.AggregationParameterValueList();
            case -4: return new org.ccsds.moims.mo.mc.aggregation.structures.AggregationSetValueList();
            case -3: return new org.ccsds.moims.mo.mc.aggregation.structures.AggregationValueList();
            case -2: return new org.ccsds.moims.mo.mc.aggregation.structures.AggregationParameterSetList();
            case -1: return new org.ccsds.moims.mo.mc.aggregation.structures.AggregationDefinitionDetailsList();
            case 1: return new org.ccsds.moims.mo.mc.aggregation.structures.AggregationDefinitionDetails();
            case 2: return new org.ccsds.moims.mo.mc.aggregation.structures.AggregationParameterSet();
            case 3: return new org.ccsds.moims.mo.mc.aggregation.structures.AggregationValue();
            case 4: return new org.ccsds.moims.mo.mc.aggregation.structures.AggregationSetValue();
            case 5: return new org.ccsds.moims.mo.mc.aggregation.structures.AggregationParameterValue();
            case 6: return new org.ccsds.moims.mo.mc.aggregation.structures.ThresholdFilter();
            case 7: return new org.ccsds.moims.mo.mc.aggregation.structures.AggregationCategory();
            case 8: return new org.ccsds.moims.mo.mc.aggregation.structures.ThresholdType();
            case 9: return new org.ccsds.moims.mo.mc.aggregation.structures.GenerationMode();
            case 10: return new org.ccsds.moims.mo.mc.aggregation.structures.AggregationCreationRequest();
            case 11: return new org.ccsds.moims.mo.mc.aggregation.structures.AggregationValueDetails();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the Conversion service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static org.ccsds.moims.mo.mal.structures.Element createConversionElement(int typeNumber) {
        switch (typeNumber) {
            case -4: return new org.ccsds.moims.mo.mc.conversion.structures.RangeConversionDetailsList();
            case -3: return new org.ccsds.moims.mo.mc.conversion.structures.PolyConversionDetailsList();
            case -2: return new org.ccsds.moims.mo.mc.conversion.structures.LineConversionDetailsList();
            case -1: return new org.ccsds.moims.mo.mc.conversion.structures.DiscreteConversionDetailsList();
            case 1: return new org.ccsds.moims.mo.mc.conversion.structures.DiscreteConversionDetails();
            case 2: return new org.ccsds.moims.mo.mc.conversion.structures.LineConversionDetails();
            case 3: return new org.ccsds.moims.mo.mc.conversion.structures.PolyConversionDetails();
            case 4: return new org.ccsds.moims.mo.mc.conversion.structures.RangeConversionDetails();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the Group service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static org.ccsds.moims.mo.mal.structures.Element createGroupElement(int typeNumber) {
        switch (typeNumber) {
            case -1: return new org.ccsds.moims.mo.mc.group.structures.GroupDetailsList();
            case 1: return new org.ccsds.moims.mo.mc.group.structures.GroupDetails();
            default: return null;
        }
    }

}
