package org.ccsds.moims.mo.com.activitytracking;

import org.ccsds.moims.mo.com.COMHelper;
import org.ccsds.moims.mo.com.COMObject;
import org.ccsds.moims.mo.com.COMService;
import org.ccsds.moims.mo.com.activitytracking.structures.ActivityAcceptance;
import org.ccsds.moims.mo.com.activitytracking.structures.ActivityExecution;
import org.ccsds.moims.mo.com.activitytracking.structures.ActivityTransfer;
import org.ccsds.moims.mo.com.activitytracking.structures.OperationActivity;
import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.ServiceKey;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * Helper class for ActivityTracking service.
 */
public class ActivityTrackingServiceInfo extends COMService {

    /**
     * Service number literal.
     */
    public static final int _ACTIVITYTRACKING_SERVICE_NUMBER = 3;

    /**
     * Service number instance.
     */
    public static final UShort ACTIVITYTRACKING_SERVICE_NUMBER = new UShort(_ACTIVITYTRACKING_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier ACTIVITYTRACKING_SERVICE_NAME = new Identifier("ActivityTracking");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            2, 1, ACTIVITYTRACKING_SERVICE_NUMBER);

    /**
     * Area elements.
     */
    public static final Element[] ACTIVITYTRACKING_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{};

    /**
     * Literal for object OPERATIONACTIVITY.
     */
    @Deprecated
    public static final int _OPERATIONACTIVITY_OBJECT_NUMBER = 6;

    /**
     * Instance for object OPERATIONACTIVITY.
     */
    @Deprecated
    public static final UShort OPERATIONACTIVITY_OBJECT_NUMBER = new UShort(_OPERATIONACTIVITY_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier OPERATIONACTIVITY_OBJECT_NAME = new Identifier("OperationActivity");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType OPERATIONACTIVITY_OBJECT_TYPE = new ObjectType(new UShort(2), ACTIVITYTRACKING_SERVICE_NUMBER, new UOctet(1), OPERATIONACTIVITY_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject OPERATIONACTIVITY_OBJECT = new COMObject(OPERATIONACTIVITY_OBJECT_TYPE, OPERATIONACTIVITY_OBJECT_NAME, OperationActivity.SHORT_FORM, false, null, false, null, false);

    /**
     * Literal for object RELEASE.
     */
    @Deprecated
    public static final int _RELEASE_OBJECT_NUMBER = 1;

    /**
     * Instance for object RELEASE.
     */
    @Deprecated
    public static final UShort RELEASE_OBJECT_NUMBER = new UShort(_RELEASE_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier RELEASE_OBJECT_NAME = new Identifier("Release");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType RELEASE_OBJECT_TYPE = new ObjectType(new UShort(2), ACTIVITYTRACKING_SERVICE_NUMBER, new UOctet(1), RELEASE_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject RELEASE_OBJECT = new COMObject(RELEASE_OBJECT_TYPE, RELEASE_OBJECT_NAME, ActivityTransfer.SHORT_FORM, false, null, true, null, true);

    /**
     * Literal for object RECEPTION.
     */
    @Deprecated
    public static final int _RECEPTION_OBJECT_NUMBER = 2;

    /**
     * Instance for object RECEPTION.
     */
    @Deprecated
    public static final UShort RECEPTION_OBJECT_NUMBER = new UShort(_RECEPTION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier RECEPTION_OBJECT_NAME = new Identifier("Reception");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType RECEPTION_OBJECT_TYPE = new ObjectType(new UShort(2), ACTIVITYTRACKING_SERVICE_NUMBER, new UOctet(1), RECEPTION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject RECEPTION_OBJECT = new COMObject(RECEPTION_OBJECT_TYPE, RECEPTION_OBJECT_NAME, ActivityTransfer.SHORT_FORM, false, null, true, null, true);

    /**
     * Literal for object FORWARD.
     */
    @Deprecated
    public static final int _FORWARD_OBJECT_NUMBER = 3;

    /**
     * Instance for object FORWARD.
     */
    @Deprecated
    public static final UShort FORWARD_OBJECT_NUMBER = new UShort(_FORWARD_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier FORWARD_OBJECT_NAME = new Identifier("Forward");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType FORWARD_OBJECT_TYPE = new ObjectType(new UShort(2), ACTIVITYTRACKING_SERVICE_NUMBER, new UOctet(1), FORWARD_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject FORWARD_OBJECT = new COMObject(FORWARD_OBJECT_TYPE, FORWARD_OBJECT_NAME, ActivityTransfer.SHORT_FORM, false, null, true, null, true);

    /**
     * Literal for object ACCEPTANCE.
     */
    @Deprecated
    public static final int _ACCEPTANCE_OBJECT_NUMBER = 4;

    /**
     * Instance for object ACCEPTANCE.
     */
    @Deprecated
    public static final UShort ACCEPTANCE_OBJECT_NUMBER = new UShort(_ACCEPTANCE_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier ACCEPTANCE_OBJECT_NAME = new Identifier("Acceptance");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType ACCEPTANCE_OBJECT_TYPE = new ObjectType(new UShort(2), ACTIVITYTRACKING_SERVICE_NUMBER, new UOctet(1), ACCEPTANCE_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject ACCEPTANCE_OBJECT = new COMObject(ACCEPTANCE_OBJECT_TYPE, ACCEPTANCE_OBJECT_NAME, ActivityAcceptance.SHORT_FORM, false, null, true, null, true);

    /**
     * Literal for object EXECUTION.
     */
    @Deprecated
    public static final int _EXECUTION_OBJECT_NUMBER = 5;

    /**
     * Instance for object EXECUTION.
     */
    @Deprecated
    public static final UShort EXECUTION_OBJECT_NUMBER = new UShort(_EXECUTION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier EXECUTION_OBJECT_NAME = new Identifier("Execution");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType EXECUTION_OBJECT_TYPE = new ObjectType(new UShort(2), ACTIVITYTRACKING_SERVICE_NUMBER, new UOctet(1), EXECUTION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject EXECUTION_OBJECT = new COMObject(EXECUTION_OBJECT_TYPE, EXECUTION_OBJECT_NAME, ActivityExecution.SHORT_FORM, false, null, true, null, true);

    /**
     * Object instance.
     */
    public static final COMObject[] COM_OBJECTS = {
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
    public MALArea getArea() {
        return COMHelper.COM_AREA;
    }

    @Override
    public MOErrorException generateMOError(int operationNumber,
            int errorNumber,
            Object extraInfo) {
        MOErrorException areaError = COMHelper.generateMOError(errorNumber, extraInfo);
        return (areaError != null) ? areaError : MALHelper.generateMOError(errorNumber, extraInfo);
    }

}
