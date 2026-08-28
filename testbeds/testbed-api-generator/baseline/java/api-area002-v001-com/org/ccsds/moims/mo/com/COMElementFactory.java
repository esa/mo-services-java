package org.ccsds.moims.mo.com;

/**
 * Creates the Elements of the COM area, without holding an instance of each
 * of them, so that the class of a type is only loaded once a message carries
 * that type.
 */
public final class COMElementFactory implements org.ccsds.moims.mo.mal.AreaElementFactory {

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement(int serviceNumber,
            int typeNumber) {
        switch (serviceNumber) {
            case 0: return createAreaElement(typeNumber);
            case 1: return createEventElement(typeNumber);
            case 2: return createArchiveElement(typeNumber);
            case 3: return createActivityTrackingElement(typeNumber);
            default: return null;
        }
    }

    @Override
    public int getAreaNumber() {
        return 2;
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
            case -5: return new org.ccsds.moims.mo.com.structures.InstanceBooleanPairList();
            case -4: return new org.ccsds.moims.mo.com.structures.ObjectDetailsList();
            case -3: return new org.ccsds.moims.mo.com.structures.ObjectIdList();
            case -2: return new org.ccsds.moims.mo.com.structures.ObjectKeyList();
            case -1: return new org.ccsds.moims.mo.com.structures.ObjectTypeList();
            case 1: return new org.ccsds.moims.mo.com.structures.ObjectType();
            case 2: return new org.ccsds.moims.mo.com.structures.ObjectKey();
            case 3: return new org.ccsds.moims.mo.com.structures.ObjectId();
            case 4: return new org.ccsds.moims.mo.com.structures.ObjectDetails();
            case 5: return new org.ccsds.moims.mo.com.structures.InstanceBooleanPair();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the Event service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static org.ccsds.moims.mo.mal.structures.Element createEventElement(int typeNumber) {
        return null;
    }

    /**
     * Creates an Element declared by the Archive service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static org.ccsds.moims.mo.mal.structures.Element createArchiveElement(int typeNumber) {
        switch (typeNumber) {
            case -5: return new org.ccsds.moims.mo.com.archive.structures.ExpressionOperatorList();
            case -4: return new org.ccsds.moims.mo.com.archive.structures.CompositeFilterSetList();
            case -3: return new org.ccsds.moims.mo.com.archive.structures.CompositeFilterList();
            case -2: return new org.ccsds.moims.mo.com.archive.structures.ArchiveQueryList();
            case -1: return new org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList();
            case 1: return new org.ccsds.moims.mo.com.archive.structures.ArchiveDetails();
            case 2: return new org.ccsds.moims.mo.com.archive.structures.ArchiveQuery();
            case 3: return new org.ccsds.moims.mo.com.archive.structures.CompositeFilter();
            case 4: return new org.ccsds.moims.mo.com.archive.structures.CompositeFilterSet();
            case 5: return new org.ccsds.moims.mo.com.archive.structures.ExpressionOperator();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the ActivityTracking service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static org.ccsds.moims.mo.mal.structures.Element createActivityTrackingElement(int typeNumber) {
        switch (typeNumber) {
            case -4: return new org.ccsds.moims.mo.com.activitytracking.structures.OperationActivityList();
            case -3: return new org.ccsds.moims.mo.com.activitytracking.structures.ActivityExecutionList();
            case -2: return new org.ccsds.moims.mo.com.activitytracking.structures.ActivityAcceptanceList();
            case -1: return new org.ccsds.moims.mo.com.activitytracking.structures.ActivityTransferList();
            case 1: return new org.ccsds.moims.mo.com.activitytracking.structures.ActivityTransfer();
            case 2: return new org.ccsds.moims.mo.com.activitytracking.structures.ActivityAcceptance();
            case 3: return new org.ccsds.moims.mo.com.activitytracking.structures.ActivityExecution();
            case 4: return new org.ccsds.moims.mo.com.activitytracking.structures.OperationActivity();
            default: return null;
        }
    }

}
