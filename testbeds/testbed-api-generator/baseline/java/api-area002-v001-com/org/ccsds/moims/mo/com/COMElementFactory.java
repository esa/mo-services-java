package org.ccsds.moims.mo.com;

import org.ccsds.moims.mo.com.activitytracking.structures.ActivityAcceptance;
import org.ccsds.moims.mo.com.activitytracking.structures.ActivityAcceptanceList;
import org.ccsds.moims.mo.com.activitytracking.structures.ActivityExecution;
import org.ccsds.moims.mo.com.activitytracking.structures.ActivityExecutionList;
import org.ccsds.moims.mo.com.activitytracking.structures.ActivityTransfer;
import org.ccsds.moims.mo.com.activitytracking.structures.ActivityTransferList;
import org.ccsds.moims.mo.com.activitytracking.structures.OperationActivity;
import org.ccsds.moims.mo.com.activitytracking.structures.OperationActivityList;
import org.ccsds.moims.mo.com.archive.structures.ArchiveDetails;
import org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList;
import org.ccsds.moims.mo.com.archive.structures.ArchiveQuery;
import org.ccsds.moims.mo.com.archive.structures.ArchiveQueryList;
import org.ccsds.moims.mo.com.archive.structures.CompositeFilter;
import org.ccsds.moims.mo.com.archive.structures.CompositeFilterList;
import org.ccsds.moims.mo.com.archive.structures.CompositeFilterSet;
import org.ccsds.moims.mo.com.archive.structures.CompositeFilterSetList;
import org.ccsds.moims.mo.com.archive.structures.ExpressionOperator;
import org.ccsds.moims.mo.com.archive.structures.ExpressionOperatorList;
import org.ccsds.moims.mo.com.structures.InstanceBooleanPair;
import org.ccsds.moims.mo.com.structures.InstanceBooleanPairList;
import org.ccsds.moims.mo.com.structures.ObjectDetails;
import org.ccsds.moims.mo.com.structures.ObjectDetailsList;
import org.ccsds.moims.mo.com.structures.ObjectId;
import org.ccsds.moims.mo.com.structures.ObjectIdList;
import org.ccsds.moims.mo.com.structures.ObjectKey;
import org.ccsds.moims.mo.com.structures.ObjectKeyList;
import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.com.structures.ObjectTypeList;
import org.ccsds.moims.mo.mal.AreaElementFactory;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * Creates the Elements of the COM area, without holding an instance of each
 * of them, so that the class of a type is only loaded once a message carries
 * that type.
 */
public final class COMElementFactory implements AreaElementFactory {

    @Override
    public Element createElement(int serviceNumber,
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
    private static Element createAreaElement(int typeNumber) {
        switch (typeNumber) {
            case -5: return new InstanceBooleanPairList();
            case -4: return new ObjectDetailsList();
            case -3: return new ObjectIdList();
            case -2: return new ObjectKeyList();
            case -1: return new ObjectTypeList();
            case 1: return new ObjectType();
            case 2: return new ObjectKey();
            case 3: return new ObjectId();
            case 4: return new ObjectDetails();
            case 5: return new InstanceBooleanPair();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the Event service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static Element createEventElement(int typeNumber) {
        return null;
    }

    /**
     * Creates an Element declared by the Archive service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static Element createArchiveElement(int typeNumber) {
        switch (typeNumber) {
            case -5: return new ExpressionOperatorList();
            case -4: return new CompositeFilterSetList();
            case -3: return new CompositeFilterList();
            case -2: return new ArchiveQueryList();
            case -1: return new ArchiveDetailsList();
            case 1: return new ArchiveDetails();
            case 2: return new ArchiveQuery();
            case 3: return new CompositeFilter();
            case 4: return new CompositeFilterSet();
            case 5: return new ExpressionOperator();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the ActivityTracking service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static Element createActivityTrackingElement(int typeNumber) {
        switch (typeNumber) {
            case -4: return new OperationActivityList();
            case -3: return new ActivityExecutionList();
            case -2: return new ActivityAcceptanceList();
            case -1: return new ActivityTransferList();
            case 1: return new ActivityTransfer();
            case 2: return new ActivityAcceptance();
            case 3: return new ActivityExecution();
            case 4: return new OperationActivity();
            default: return null;
        }
    }

}
