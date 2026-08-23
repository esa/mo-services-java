package org.ccsds.moims.mo.com.activitytracking;

/**
 * Helper class for ActivityTracking service.
 */
public class ActivityTrackingServiceInfo extends org.ccsds.moims.mo.com.COMService {

    /**
     * Service number literal.
     */
    public static final int _ACTIVITYTRACKING_SERVICE_NUMBER = 3;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort ACTIVITYTRACKING_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ACTIVITYTRACKING_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier ACTIVITYTRACKING_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ActivityTracking");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            2, 1, ACTIVITYTRACKING_SERVICE_NUMBER);

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] ACTIVITYTRACKING_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{};

    /**
     * Literal for object OPERATIONACTIVITY.
     */
    @Deprecated
    public static final int _OPERATIONACTIVITY_OBJECT_NUMBER = 6;

    /**
     * Instance for object OPERATIONACTIVITY.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort OPERATIONACTIVITY_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_OPERATIONACTIVITY_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier OPERATIONACTIVITY_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("OperationActivity");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType OPERATIONACTIVITY_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(2), ACTIVITYTRACKING_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), OPERATIONACTIVITY_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject OPERATIONACTIVITY_OBJECT = new org.ccsds.moims.mo.com.COMObject(OPERATIONACTIVITY_OBJECT_TYPE, OPERATIONACTIVITY_OBJECT_NAME, org.ccsds.moims.mo.com.activitytracking.structures.OperationActivity.SHORT_FORM, false, null, false, null, false);

    /**
     * Literal for object RELEASE.
     */
    @Deprecated
    public static final int _RELEASE_OBJECT_NUMBER = 1;

    /**
     * Instance for object RELEASE.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort RELEASE_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_RELEASE_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier RELEASE_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("Release");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType RELEASE_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(2), ACTIVITYTRACKING_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), RELEASE_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject RELEASE_OBJECT = new org.ccsds.moims.mo.com.COMObject(RELEASE_OBJECT_TYPE, RELEASE_OBJECT_NAME, org.ccsds.moims.mo.com.activitytracking.structures.ActivityTransfer.SHORT_FORM, false, null, true, null, true);

    /**
     * Literal for object RECEPTION.
     */
    @Deprecated
    public static final int _RECEPTION_OBJECT_NUMBER = 2;

    /**
     * Instance for object RECEPTION.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort RECEPTION_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_RECEPTION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier RECEPTION_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("Reception");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType RECEPTION_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(2), ACTIVITYTRACKING_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), RECEPTION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject RECEPTION_OBJECT = new org.ccsds.moims.mo.com.COMObject(RECEPTION_OBJECT_TYPE, RECEPTION_OBJECT_NAME, org.ccsds.moims.mo.com.activitytracking.structures.ActivityTransfer.SHORT_FORM, false, null, true, null, true);

    /**
     * Literal for object FORWARD.
     */
    @Deprecated
    public static final int _FORWARD_OBJECT_NUMBER = 3;

    /**
     * Instance for object FORWARD.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort FORWARD_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_FORWARD_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier FORWARD_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("Forward");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType FORWARD_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(2), ACTIVITYTRACKING_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), FORWARD_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject FORWARD_OBJECT = new org.ccsds.moims.mo.com.COMObject(FORWARD_OBJECT_TYPE, FORWARD_OBJECT_NAME, org.ccsds.moims.mo.com.activitytracking.structures.ActivityTransfer.SHORT_FORM, false, null, true, null, true);

    /**
     * Literal for object ACCEPTANCE.
     */
    @Deprecated
    public static final int _ACCEPTANCE_OBJECT_NUMBER = 4;

    /**
     * Instance for object ACCEPTANCE.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort ACCEPTANCE_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ACCEPTANCE_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier ACCEPTANCE_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("Acceptance");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType ACCEPTANCE_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(2), ACTIVITYTRACKING_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), ACCEPTANCE_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject ACCEPTANCE_OBJECT = new org.ccsds.moims.mo.com.COMObject(ACCEPTANCE_OBJECT_TYPE, ACCEPTANCE_OBJECT_NAME, org.ccsds.moims.mo.com.activitytracking.structures.ActivityAcceptance.SHORT_FORM, false, null, true, null, true);

    /**
     * Literal for object EXECUTION.
     */
    @Deprecated
    public static final int _EXECUTION_OBJECT_NUMBER = 5;

    /**
     * Instance for object EXECUTION.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort EXECUTION_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_EXECUTION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier EXECUTION_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("Execution");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType EXECUTION_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(2), ACTIVITYTRACKING_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), EXECUTION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject EXECUTION_OBJECT = new org.ccsds.moims.mo.com.COMObject(EXECUTION_OBJECT_TYPE, EXECUTION_OBJECT_NAME, org.ccsds.moims.mo.com.activitytracking.structures.ActivityExecution.SHORT_FORM, false, null, true, null, true);

    /**
     * Object instance.
     */
    public static final org.ccsds.moims.mo.com.COMObject[] COM_OBJECTS = {
        OPERATIONACTIVITY_OBJECT,
        RELEASE_OBJECT,
        RECEPTION_OBJECT,
        FORWARD_OBJECT,
        ACCEPTANCE_OBJECT,
        EXECUTION_OBJECT,};

    /**
     * Creates an instance of the ActivityTracking ServiceInfo.
     * 
     */
    public ActivityTrackingServiceInfo() {
        super(SERVICE_KEY, ACTIVITYTRACKING_SERVICE_NAME, ACTIVITYTRACKING_SERVICE_ELEMENTS, OPERATIONS, COM_OBJECTS);
    }

    @Override
    public org.ccsds.moims.mo.mal.MALArea getArea() {
        return org.ccsds.moims.mo.com.COMHelper.COM_AREA;
    }

    @Override
    public org.ccsds.moims.mo.mal.MOErrorException generateMOError(int errorNumber,
            Object extraInfo) {
        switch (errorNumber) {
            case 70000:
                return new org.ccsds.moims.mo.com.InvalidException(extraInfo);
            case 70001:
                return new org.ccsds.moims.mo.com.DuplicateException(extraInfo);
        }
        return null;
    }

}
