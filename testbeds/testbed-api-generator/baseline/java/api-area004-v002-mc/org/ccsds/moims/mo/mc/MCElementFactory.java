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
        if (serviceNumber != 0) {
            return null; // This Area declares no types under a service
        }
        switch (typeNumber) {
            case -61: return new org.ccsds.moims.mo.mc.structures.AggregationValueList();
            case -60: return new org.ccsds.moims.mo.mc.structures.AggregationDefinitionList();
            case -32: return new org.ccsds.moims.mo.mc.structures.AlertConfigurationList();
            case -31: return new org.ccsds.moims.mo.mc.structures.AlertEventList();
            case -30: return new org.ccsds.moims.mo.mc.structures.AlertDefinitionList();
            case -24: return new org.ccsds.moims.mo.mc.structures.ReportConfigurationList();
            case -23: return new org.ccsds.moims.mo.mc.structures.ParameterValueList();
            case -22: return new org.ccsds.moims.mo.mc.structures.ParameterValueDataList();
            case -21: return new org.ccsds.moims.mo.mc.structures.ParameterDefinitionList();
            case -20: return new org.ccsds.moims.mo.mc.structures.ValidityStateList();
            case -15: return new org.ccsds.moims.mo.mc.structures.ActionCompleteEventList();
            case -14: return new org.ccsds.moims.mo.mc.structures.ActionInProgressEventList();
            case -13: return new org.ccsds.moims.mo.mc.structures.ActionStartEventList();
            case -12: return new org.ccsds.moims.mo.mc.structures.ActionExecutionRequestList();
            case -11: return new org.ccsds.moims.mo.mc.structures.ActionDefinitionList();
            case -10: return new org.ccsds.moims.mo.mc.structures.ActionCategoryList();
            case -6: return new org.ccsds.moims.mo.mc.structures.SeverityList();
            case -1: return new org.ccsds.moims.mo.mc.structures.ArgumentDefinitionList();
            case 1: return new org.ccsds.moims.mo.mc.structures.ArgumentDefinition();
            case 6: return new org.ccsds.moims.mo.mc.structures.Severity();
            case 10: return new org.ccsds.moims.mo.mc.structures.ActionCategory();
            case 11: return new org.ccsds.moims.mo.mc.structures.ActionDefinition();
            case 12: return new org.ccsds.moims.mo.mc.structures.ActionExecutionRequest();
            case 13: return new org.ccsds.moims.mo.mc.structures.ActionStartEvent();
            case 14: return new org.ccsds.moims.mo.mc.structures.ActionInProgressEvent();
            case 15: return new org.ccsds.moims.mo.mc.structures.ActionCompleteEvent();
            case 20: return new org.ccsds.moims.mo.mc.structures.ValidityState();
            case 21: return new org.ccsds.moims.mo.mc.structures.ParameterDefinition();
            case 22: return new org.ccsds.moims.mo.mc.structures.ParameterValueData();
            case 23: return new org.ccsds.moims.mo.mc.structures.ParameterValue();
            case 24: return new org.ccsds.moims.mo.mc.structures.ReportConfiguration();
            case 30: return new org.ccsds.moims.mo.mc.structures.AlertDefinition();
            case 31: return new org.ccsds.moims.mo.mc.structures.AlertEvent();
            case 32: return new org.ccsds.moims.mo.mc.structures.AlertConfiguration();
            case 60: return new org.ccsds.moims.mo.mc.structures.AggregationDefinition();
            case 61: return new org.ccsds.moims.mo.mc.structures.AggregationValue();
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
    private static org.ccsds.moims.mo.mal.structures.Element createAreaElementOutOfBand(int typeNumber) {
        switch (typeNumber) {
            case -90: return new org.ccsds.moims.mo.mc.structures.PacketValueList();
            case 90: return new org.ccsds.moims.mo.mc.structures.PacketValue();
            default: return null;
        }
    }

}
