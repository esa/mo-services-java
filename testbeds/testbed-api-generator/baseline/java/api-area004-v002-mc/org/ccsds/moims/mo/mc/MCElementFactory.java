package org.ccsds.moims.mo.mc;

import org.ccsds.moims.mo.mal.AreaElementFactory;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mc.structures.ActionCategory;
import org.ccsds.moims.mo.mc.structures.ActionCategoryList;
import org.ccsds.moims.mo.mc.structures.ActionCompleteEvent;
import org.ccsds.moims.mo.mc.structures.ActionCompleteEventList;
import org.ccsds.moims.mo.mc.structures.ActionDefinition;
import org.ccsds.moims.mo.mc.structures.ActionDefinitionList;
import org.ccsds.moims.mo.mc.structures.ActionExecutionRequest;
import org.ccsds.moims.mo.mc.structures.ActionExecutionRequestList;
import org.ccsds.moims.mo.mc.structures.ActionInProgressEvent;
import org.ccsds.moims.mo.mc.structures.ActionInProgressEventList;
import org.ccsds.moims.mo.mc.structures.ActionStartEvent;
import org.ccsds.moims.mo.mc.structures.ActionStartEventList;
import org.ccsds.moims.mo.mc.structures.AggregationDefinition;
import org.ccsds.moims.mo.mc.structures.AggregationDefinitionList;
import org.ccsds.moims.mo.mc.structures.AggregationValue;
import org.ccsds.moims.mo.mc.structures.AggregationValueList;
import org.ccsds.moims.mo.mc.structures.AlertConfiguration;
import org.ccsds.moims.mo.mc.structures.AlertConfigurationList;
import org.ccsds.moims.mo.mc.structures.AlertDefinition;
import org.ccsds.moims.mo.mc.structures.AlertDefinitionList;
import org.ccsds.moims.mo.mc.structures.AlertEvent;
import org.ccsds.moims.mo.mc.structures.AlertEventList;
import org.ccsds.moims.mo.mc.structures.ArgumentDefinition;
import org.ccsds.moims.mo.mc.structures.ArgumentDefinitionList;
import org.ccsds.moims.mo.mc.structures.PacketValue;
import org.ccsds.moims.mo.mc.structures.PacketValueList;
import org.ccsds.moims.mo.mc.structures.ParameterDefinition;
import org.ccsds.moims.mo.mc.structures.ParameterDefinitionList;
import org.ccsds.moims.mo.mc.structures.ParameterValue;
import org.ccsds.moims.mo.mc.structures.ParameterValueData;
import org.ccsds.moims.mo.mc.structures.ParameterValueDataList;
import org.ccsds.moims.mo.mc.structures.ParameterValueList;
import org.ccsds.moims.mo.mc.structures.ReportConfiguration;
import org.ccsds.moims.mo.mc.structures.ReportConfigurationList;
import org.ccsds.moims.mo.mc.structures.Severity;
import org.ccsds.moims.mo.mc.structures.SeverityList;
import org.ccsds.moims.mo.mc.structures.ValidityState;
import org.ccsds.moims.mo.mc.structures.ValidityStateList;

/**
 * Creates the Elements of the MC area, without holding an instance of each
 * of them, so that the class of a type is only loaded once a message carries
 * that type.
 */
public final class MCElementFactory implements AreaElementFactory {

    @Override
    public Element createElement(int serviceNumber,
            int typeNumber) {
        if (serviceNumber != 0) {
            return null; // This Area declares no types under a service
        }
        switch (typeNumber) {
            case -61: return new AggregationValueList();
            case -60: return new AggregationDefinitionList();
            case -32: return new AlertConfigurationList();
            case -31: return new AlertEventList();
            case -30: return new AlertDefinitionList();
            case -24: return new ReportConfigurationList();
            case -23: return new ParameterValueList();
            case -22: return new ParameterValueDataList();
            case -21: return new ParameterDefinitionList();
            case -20: return new ValidityStateList();
            case -15: return new ActionCompleteEventList();
            case -14: return new ActionInProgressEventList();
            case -13: return new ActionStartEventList();
            case -12: return new ActionExecutionRequestList();
            case -11: return new ActionDefinitionList();
            case -10: return new ActionCategoryList();
            case -6: return new SeverityList();
            case -1: return new ArgumentDefinitionList();
            case 1: return new ArgumentDefinition();
            case 6: return new Severity();
            case 10: return new ActionCategory();
            case 11: return new ActionDefinition();
            case 12: return new ActionExecutionRequest();
            case 13: return new ActionStartEvent();
            case 14: return new ActionInProgressEvent();
            case 15: return new ActionCompleteEvent();
            case 20: return new ValidityState();
            case 21: return new ParameterDefinition();
            case 22: return new ParameterValueData();
            case 23: return new ParameterValue();
            case 24: return new ReportConfiguration();
            case 30: return new AlertDefinition();
            case 31: return new AlertEvent();
            case 32: return new AlertConfiguration();
            case 60: return new AggregationDefinition();
            case 61: return new AggregationValue();
            default: return createAreaElementOutOfBand(typeNumber);
        }
    }

    @Override
    public int getAreaNumber() {
        return 4;
    }

    @Override
    public int getAreaVersion() {
        return 2;
    }

    /**
     * Creates an Element whose type number lies too far out to be held in the
     * jump table that is asked first. This says nothing about how often the type
     * is asked for: the numbers of an Area are not handed out in the order of
     * use.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static Element createAreaElementOutOfBand(int typeNumber) {
        switch (typeNumber) {
            case -90: return new PacketValueList();
            case 90: return new PacketValue();
            default: return null;
        }
    }

}
