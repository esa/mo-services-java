package org.ccsds.moims.mo.mps;

/**
 * Creates the Elements of the MPS area, without holding an instance of each
 * of them, so that the class of a type is only loaded once a message carries
 * that type.
 */
public final class MPSElementFactory implements org.ccsds.moims.mo.mal.AreaElementFactory {

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement(int serviceNumber,
            int typeNumber) {
        if (serviceNumber != 0) {
            return null; // This Area declares no types under a service
        }
        switch (typeNumber) {
            case -517: return new org.ccsds.moims.mo.mps.structures.PartialPlanFilterList();
            case -516: return new org.ccsds.moims.mo.mps.structures.PlanFilterList();
            case -515: return new org.ccsds.moims.mo.mps.structures.PartialPlanList();
            case -514: return new org.ccsds.moims.mo.mps.structures.PlanQueryList();
            case -513: return new org.ccsds.moims.mo.mps.structures.SubPlanActivationStatusList();
            case -512: return new org.ccsds.moims.mo.mps.structures.SubPlanStatusEnumList();
            case -511: return new org.ccsds.moims.mo.mps.structures.SubPlanUpdateList();
            case -510: return new org.ccsds.moims.mo.mps.structures.PlanActivationStatusList();
            case -509: return new org.ccsds.moims.mo.mps.structures.PlanSummaryStatusList();
            case -508: return new org.ccsds.moims.mo.mps.structures.PlanUpdateList();
            case -507: return new org.ccsds.moims.mo.mps.structures.RevisionStatusEnumList();
            case -506: return new org.ccsds.moims.mo.mps.structures.ItemRevisionList();
            case -505: return new org.ccsds.moims.mo.mps.structures.PlanRevisionList();
            case -504: return new org.ccsds.moims.mo.mps.structures.PlannedItemsList();
            case -503: return new org.ccsds.moims.mo.mps.structures.PlanInformationList();
            case -502: return new org.ccsds.moims.mo.mps.structures.PlanStatusEnumList();
            case -501: return new org.ccsds.moims.mo.mps.structures.PlanList();
            case -408: return new org.ccsds.moims.mo.mps.structures.RequestFilterList();
            case -407: return new org.ccsds.moims.mo.mps.structures.RequestSummaryStatusList();
            case -406: return new org.ccsds.moims.mo.mps.structures.PlanningRequestResponseList();
            case -405: return new org.ccsds.moims.mo.mps.structures.PlanningRequestDetailsList();
            case -404: return new org.ccsds.moims.mo.mps.structures.RequestStatusUpdateList();
            case -403: return new org.ccsds.moims.mo.mps.structures.RequestStatusEnumList();
            case -402: return new org.ccsds.moims.mo.mps.structures.RequestInstanceList();
            case -401: return new org.ccsds.moims.mo.mps.structures.RequestDefinitionList();
            case -310: return new org.ccsds.moims.mo.mps.structures.ResourceUpdateList();
            case -309: return new org.ccsds.moims.mo.mps.structures.RelativeProfileEntryList();
            case -308: return new org.ccsds.moims.mo.mps.structures.RelativeProfileSegmentList();
            case -307: return new org.ccsds.moims.mo.mps.structures.RelativeResourceProfileList();
            case -306: return new org.ccsds.moims.mo.mps.structures.ProfileEntryList();
            case -305: return new org.ccsds.moims.mo.mps.structures.InterpolationTypeEnumList();
            case -304: return new org.ccsds.moims.mo.mps.structures.ProfileSegmentList();
            case -303: return new org.ccsds.moims.mo.mps.structures.ResourceProfileList();
            case -302: return new org.ccsds.moims.mo.mps.structures.NumericResourceList();
            case -301: return new org.ccsds.moims.mo.mps.structures.ResourceList();
            case -206: return new org.ccsds.moims.mo.mps.structures.InsertedEventDetailsList();
            case -205: return new org.ccsds.moims.mo.mps.structures.EventUpdateList();
            case -204: return new org.ccsds.moims.mo.mps.structures.PredictabilityEnumList();
            case -203: return new org.ccsds.moims.mo.mps.structures.EventStatusEnumList();
            case -202: return new org.ccsds.moims.mo.mps.structures.EventInstanceList();
            case -201: return new org.ccsds.moims.mo.mps.structures.EventDefinitionList();
            case -108: return new org.ccsds.moims.mo.mps.structures.ActivitySuspensionStatusList();
            case -107: return new org.ccsds.moims.mo.mps.structures.InsertedActivityDetailsList();
            case -106: return new org.ccsds.moims.mo.mps.structures.ActivityUpdateList();
            case -105: return new org.ccsds.moims.mo.mps.structures.SimpleActivityDetailsList();
            case -104: return new org.ccsds.moims.mo.mps.structures.ActivityNodeList();
            case -103: return new org.ccsds.moims.mo.mps.structures.ActivityStatusEnumList();
            case -102: return new org.ccsds.moims.mo.mps.structures.ActivityInstanceList();
            case -101: return new org.ccsds.moims.mo.mps.structures.ActivityDefinitionList();
            case -64: return new org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnumList();
            case -63: return new org.ccsds.moims.mo.mps.structures.EventRepetitionList();
            case -62: return new org.ccsds.moims.mo.mps.structures.AngleRepetitionList();
            case -61: return new org.ccsds.moims.mo.mps.structures.TemporalRepetitionList();
            case -60: return new org.ccsds.moims.mo.mps.structures.RevolutionRepetitionList();
            case -59: return new org.ccsds.moims.mo.mps.structures.DirectionRepetitionList();
            case -58: return new org.ccsds.moims.mo.mps.structures.OrbitRepetitionList();
            case -57: return new org.ccsds.moims.mo.mps.structures.PositionRepetitionList();
            case -56: return new org.ccsds.moims.mo.mps.structures.SeparationTypeEnumList();
            case -55: return new org.ccsds.moims.mo.mps.structures.EventTriggerList();
            case -54: return new org.ccsds.moims.mo.mps.structures.AngleTriggerList();
            case -53: return new org.ccsds.moims.mo.mps.structures.DirectionTriggerList();
            case -52: return new org.ccsds.moims.mo.mps.structures.PositionTriggerList();
            case -51: return new org.ccsds.moims.mo.mps.structures.TimeTriggerList();
            case -50: return new org.ccsds.moims.mo.mps.structures.ComplexEffectList();
            case -49: return new org.ccsds.moims.mo.mps.structures.EffectOperationEnumList();
            case -48: return new org.ccsds.moims.mo.mps.structures.SimpleEffectList();
            case -47: return new org.ccsds.moims.mo.mps.structures.FunctionConstraintList();
            case -46: return new org.ccsds.moims.mo.mps.structures.SeparationConstraintList();
            case -45: return new org.ccsds.moims.mo.mps.structures.SequentialConstraintList();
            case -44: return new org.ccsds.moims.mo.mps.structures.AngleConstraintList();
            case -43: return new org.ccsds.moims.mo.mps.structures.DistanceConstraintList();
            case -42: return new org.ccsds.moims.mo.mps.structures.RevolutionConstraintList();
            case -41: return new org.ccsds.moims.mo.mps.structures.PointingConstraintList();
            case -40: return new org.ccsds.moims.mo.mps.structures.EllipsoidalPositionConstraintList();
            case -39: return new org.ccsds.moims.mo.mps.structures.PositionConstraintList();
            case -38: return new org.ccsds.moims.mo.mps.structures.ComplexResourceConstraintList();
            case -37: return new org.ccsds.moims.mo.mps.structures.SimpleResourceConstraintList();
            case -36: return new org.ccsds.moims.mo.mps.structures.ExpressionOperatorEnumList();
            case -35: return new org.ccsds.moims.mo.mps.structures.ArgumentConstraintList();
            case -34: return new org.ccsds.moims.mo.mps.structures.DurationConstraintList();
            case -33: return new org.ccsds.moims.mo.mps.structures.TimeWindowConstraintList();
            case -32: return new org.ccsds.moims.mo.mps.structures.TimeConstraintList();
            case -31: return new org.ccsds.moims.mo.mps.structures.ConstraintExpressionList();
            case -30: return new org.ccsds.moims.mo.mps.structures.LogicOpEnumList();
            case -29: return new org.ccsds.moims.mo.mps.structures.ConstraintNodeList();
            case -28: return new org.ccsds.moims.mo.mps.structures.StatusValuesList();
            case -27: return new org.ccsds.moims.mo.mps.structures.StringPatternList();
            case -26: return new org.ccsds.moims.mo.mps.structures.NumericRangeList();
            case -25: return new org.ccsds.moims.mo.mps.structures.ArgSpecList();
            case -24: return new org.ccsds.moims.mo.mps.structures.ArgumentList();
            case -23: return new org.ccsds.moims.mo.mps.structures.ArgDefList();
            case -22: return new org.ccsds.moims.mo.mps.structures.ExternalExpressionList();
            case -21: return new org.ccsds.moims.mo.mps.structures.DistanceList();
            case -20: return new org.ccsds.moims.mo.mps.structures.AngularVelocityList();
            case -19: return new org.ccsds.moims.mo.mps.structures.AngleList();
            case -18: return new org.ccsds.moims.mo.mps.structures.DirectionReferenceList();
            case -17: return new org.ccsds.moims.mo.mps.structures.NamedTargetDirectionList();
            case -16: return new org.ccsds.moims.mo.mps.structures.RADecDirectionList();
            case -15: return new org.ccsds.moims.mo.mps.structures.SphericalDirectionList();
            case -14: return new org.ccsds.moims.mo.mps.structures.CartesianDirectionList();
            case -13: return new org.ccsds.moims.mo.mps.structures.PositionReferenceList();
            case -12: return new org.ccsds.moims.mo.mps.structures.ObjectPositionList();
            case -11: return new org.ccsds.moims.mo.mps.structures.OrbitalPositionList();
            case -10: return new org.ccsds.moims.mo.mps.structures.OrbitFilePositionList();
            case -9: return new org.ccsds.moims.mo.mps.structures.SurfacePositionList();
            case -8: return new org.ccsds.moims.mo.mps.structures.CartesianPositionList();
            case -7: return new org.ccsds.moims.mo.mps.structures.DefListEntryList();
            case -6: return new org.ccsds.moims.mo.mps.structures.EventWindowList();
            case -5: return new org.ccsds.moims.mo.mps.structures.TimeWindowList();
            case -4: return new org.ccsds.moims.mo.mps.structures.StateDefList();
            case -3: return new org.ccsds.moims.mo.mps.structures.SliderList();
            case -2: return new org.ccsds.moims.mo.mps.structures.NamedElementList();
            case -1: return new org.ccsds.moims.mo.mps.structures.ArgTypeEnumList();
            case 1: return new org.ccsds.moims.mo.mps.structures.ArgTypeEnum();
            case 2: return new org.ccsds.moims.mo.mps.structures.NamedElement();
            case 3: return new org.ccsds.moims.mo.mps.structures.Slider();
            case 4: return new org.ccsds.moims.mo.mps.structures.StateDef();
            case 5: return new org.ccsds.moims.mo.mps.structures.TimeWindow();
            case 6: return new org.ccsds.moims.mo.mps.structures.EventWindow();
            case 7: return new org.ccsds.moims.mo.mps.structures.DefListEntry();
            case 8: return new org.ccsds.moims.mo.mps.structures.CartesianPosition();
            case 9: return new org.ccsds.moims.mo.mps.structures.SurfacePosition();
            case 10: return new org.ccsds.moims.mo.mps.structures.OrbitFilePosition();
            case 11: return new org.ccsds.moims.mo.mps.structures.OrbitalPosition();
            case 12: return new org.ccsds.moims.mo.mps.structures.ObjectPosition();
            case 13: return new org.ccsds.moims.mo.mps.structures.PositionReference();
            case 14: return new org.ccsds.moims.mo.mps.structures.CartesianDirection();
            case 15: return new org.ccsds.moims.mo.mps.structures.SphericalDirection();
            case 16: return new org.ccsds.moims.mo.mps.structures.RADecDirection();
            case 17: return new org.ccsds.moims.mo.mps.structures.NamedTargetDirection();
            case 18: return new org.ccsds.moims.mo.mps.structures.DirectionReference();
            case 19: return new org.ccsds.moims.mo.mps.structures.Angle();
            case 20: return new org.ccsds.moims.mo.mps.structures.AngularVelocity();
            case 21: return new org.ccsds.moims.mo.mps.structures.Distance();
            case 22: return new org.ccsds.moims.mo.mps.structures.ExternalExpression();
            case 23: return new org.ccsds.moims.mo.mps.structures.ArgDef();
            case 24: return new org.ccsds.moims.mo.mps.structures.Argument();
            case 25: return new org.ccsds.moims.mo.mps.structures.ArgSpec();
            case 26: return new org.ccsds.moims.mo.mps.structures.NumericRange();
            case 27: return new org.ccsds.moims.mo.mps.structures.StringPattern();
            case 28: return new org.ccsds.moims.mo.mps.structures.StatusValues();
            case 29: return new org.ccsds.moims.mo.mps.structures.ConstraintNode();
            case 30: return new org.ccsds.moims.mo.mps.structures.LogicOpEnum();
            case 31: return new org.ccsds.moims.mo.mps.structures.ConstraintExpression();
            case 32: return new org.ccsds.moims.mo.mps.structures.TimeConstraint();
            case 33: return new org.ccsds.moims.mo.mps.structures.TimeWindowConstraint();
            case 34: return new org.ccsds.moims.mo.mps.structures.DurationConstraint();
            case 35: return new org.ccsds.moims.mo.mps.structures.ArgumentConstraint();
            case 36: return new org.ccsds.moims.mo.mps.structures.ExpressionOperatorEnum();
            case 37: return new org.ccsds.moims.mo.mps.structures.SimpleResourceConstraint();
            case 38: return new org.ccsds.moims.mo.mps.structures.ComplexResourceConstraint();
            case 39: return new org.ccsds.moims.mo.mps.structures.PositionConstraint();
            case 40: return new org.ccsds.moims.mo.mps.structures.EllipsoidalPositionConstraint();
            case 41: return new org.ccsds.moims.mo.mps.structures.PointingConstraint();
            case 42: return new org.ccsds.moims.mo.mps.structures.RevolutionConstraint();
            case 43: return new org.ccsds.moims.mo.mps.structures.DistanceConstraint();
            case 44: return new org.ccsds.moims.mo.mps.structures.AngleConstraint();
            case 45: return new org.ccsds.moims.mo.mps.structures.SequentialConstraint();
            case 46: return new org.ccsds.moims.mo.mps.structures.SeparationConstraint();
            case 47: return new org.ccsds.moims.mo.mps.structures.FunctionConstraint();
            case 48: return new org.ccsds.moims.mo.mps.structures.SimpleEffect();
            case 49: return new org.ccsds.moims.mo.mps.structures.EffectOperationEnum();
            case 50: return new org.ccsds.moims.mo.mps.structures.ComplexEffect();
            case 51: return new org.ccsds.moims.mo.mps.structures.TimeTrigger();
            case 52: return new org.ccsds.moims.mo.mps.structures.PositionTrigger();
            case 53: return new org.ccsds.moims.mo.mps.structures.DirectionTrigger();
            case 54: return new org.ccsds.moims.mo.mps.structures.AngleTrigger();
            case 55: return new org.ccsds.moims.mo.mps.structures.EventTrigger();
            case 56: return new org.ccsds.moims.mo.mps.structures.SeparationTypeEnum();
            case 57: return new org.ccsds.moims.mo.mps.structures.PositionRepetition();
            case 58: return new org.ccsds.moims.mo.mps.structures.OrbitRepetition();
            case 59: return new org.ccsds.moims.mo.mps.structures.DirectionRepetition();
            case 60: return new org.ccsds.moims.mo.mps.structures.RevolutionRepetition();
            case 61: return new org.ccsds.moims.mo.mps.structures.TemporalRepetition();
            case 62: return new org.ccsds.moims.mo.mps.structures.AngleRepetition();
            case 63: return new org.ccsds.moims.mo.mps.structures.EventRepetition();
            case 64: return new org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum();
            case 101: return new org.ccsds.moims.mo.mps.structures.ActivityDefinition();
            case 102: return new org.ccsds.moims.mo.mps.structures.ActivityInstance();
            case 103: return new org.ccsds.moims.mo.mps.structures.ActivityStatusEnum();
            case 104: return new org.ccsds.moims.mo.mps.structures.ActivityNode();
            case 105: return new org.ccsds.moims.mo.mps.structures.SimpleActivityDetails();
            case 106: return new org.ccsds.moims.mo.mps.structures.ActivityUpdate();
            case 107: return new org.ccsds.moims.mo.mps.structures.InsertedActivityDetails();
            case 108: return new org.ccsds.moims.mo.mps.structures.ActivitySuspensionStatus();
            case 201: return new org.ccsds.moims.mo.mps.structures.EventDefinition();
            case 202: return new org.ccsds.moims.mo.mps.structures.EventInstance();
            case 203: return new org.ccsds.moims.mo.mps.structures.EventStatusEnum();
            case 204: return new org.ccsds.moims.mo.mps.structures.PredictabilityEnum();
            case 205: return new org.ccsds.moims.mo.mps.structures.EventUpdate();
            case 206: return new org.ccsds.moims.mo.mps.structures.InsertedEventDetails();
            case 301: return new org.ccsds.moims.mo.mps.structures.Resource();
            case 302: return new org.ccsds.moims.mo.mps.structures.NumericResource();
            case 303: return new org.ccsds.moims.mo.mps.structures.ResourceProfile();
            case 304: return new org.ccsds.moims.mo.mps.structures.ProfileSegment();
            case 305: return new org.ccsds.moims.mo.mps.structures.InterpolationTypeEnum();
            case 306: return new org.ccsds.moims.mo.mps.structures.ProfileEntry();
            case 307: return new org.ccsds.moims.mo.mps.structures.RelativeResourceProfile();
            case 308: return new org.ccsds.moims.mo.mps.structures.RelativeProfileSegment();
            case 309: return new org.ccsds.moims.mo.mps.structures.RelativeProfileEntry();
            case 310: return new org.ccsds.moims.mo.mps.structures.ResourceUpdate();
            case 401: return new org.ccsds.moims.mo.mps.structures.RequestDefinition();
            case 402: return new org.ccsds.moims.mo.mps.structures.RequestInstance();
            case 403: return new org.ccsds.moims.mo.mps.structures.RequestStatusEnum();
            case 404: return new org.ccsds.moims.mo.mps.structures.RequestStatusUpdate();
            case 405: return new org.ccsds.moims.mo.mps.structures.PlanningRequestDetails();
            case 406: return new org.ccsds.moims.mo.mps.structures.PlanningRequestResponse();
            case 407: return new org.ccsds.moims.mo.mps.structures.RequestSummaryStatus();
            case 408: return new org.ccsds.moims.mo.mps.structures.RequestFilter();
            case 501: return new org.ccsds.moims.mo.mps.structures.Plan();
            case 502: return new org.ccsds.moims.mo.mps.structures.PlanStatusEnum();
            case 503: return new org.ccsds.moims.mo.mps.structures.PlanInformation();
            case 504: return new org.ccsds.moims.mo.mps.structures.PlannedItems();
            case 505: return new org.ccsds.moims.mo.mps.structures.PlanRevision();
            case 506: return new org.ccsds.moims.mo.mps.structures.ItemRevision();
            case 507: return new org.ccsds.moims.mo.mps.structures.RevisionStatusEnum();
            case 508: return new org.ccsds.moims.mo.mps.structures.PlanUpdate();
            case 509: return new org.ccsds.moims.mo.mps.structures.PlanSummaryStatus();
            case 510: return new org.ccsds.moims.mo.mps.structures.PlanActivationStatus();
            case 511: return new org.ccsds.moims.mo.mps.structures.SubPlanUpdate();
            case 512: return new org.ccsds.moims.mo.mps.structures.SubPlanStatusEnum();
            case 513: return new org.ccsds.moims.mo.mps.structures.SubPlanActivationStatus();
            case 514: return new org.ccsds.moims.mo.mps.structures.PlanQuery();
            case 515: return new org.ccsds.moims.mo.mps.structures.PartialPlan();
            case 516: return new org.ccsds.moims.mo.mps.structures.PlanFilter();
            case 517: return new org.ccsds.moims.mo.mps.structures.PartialPlanFilter();
            default: return createAreaElementOutOfBand(typeNumber);
        }
    }

    @Override
    public int getAreaNumber() {
        return 5;
    }

    @Override
    public int getAreaVersion() {
        return 1;
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
            case -702: return new org.ccsds.moims.mo.mps.structures.FunctionDetailsList();
            case -701: return new org.ccsds.moims.mo.mps.structures.FunctionDefinitionDetailsList();
            case -601: return new org.ccsds.moims.mo.mps.structures.PlanningUserList();
            case 601: return new org.ccsds.moims.mo.mps.structures.PlanningUser();
            case 701: return new org.ccsds.moims.mo.mps.structures.FunctionDefinitionDetails();
            case 702: return new org.ccsds.moims.mo.mps.structures.FunctionDetails();
            default: return null;
        }
    }

}
