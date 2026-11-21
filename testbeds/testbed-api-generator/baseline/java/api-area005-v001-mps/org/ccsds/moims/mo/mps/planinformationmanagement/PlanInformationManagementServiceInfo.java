package org.ccsds.moims.mo.mps.planinformationmanagement;

import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.ServiceKey;
import org.ccsds.moims.mo.mal.structures.AttributeTypeList;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mps.InvalidException;
import org.ccsds.moims.mo.mps.MPSHelper;
import org.ccsds.moims.mo.mps.structures.ActivityDefinitionList;
import org.ccsds.moims.mo.mps.structures.DefListEntryList;
import org.ccsds.moims.mo.mps.structures.EventDefinitionList;
import org.ccsds.moims.mo.mps.structures.RequestDefinitionList;
import org.ccsds.moims.mo.mps.structures.ResourceList;

/**
 * Helper class for PlanInformationManagement service.
 */
public class PlanInformationManagementServiceInfo extends ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _PLANINFORMATIONMANAGEMENT_SERVICE_NUMBER = 4;

    /**
     * Service number instance.
     */
    public static final UShort PLANINFORMATIONMANAGEMENT_SERVICE_NUMBER = new UShort(_PLANINFORMATIONMANAGEMENT_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier PLANINFORMATIONMANAGEMENT_SERVICE_NAME = new Identifier("PlanInformationManagement");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            5, 1, PLANINFORMATIONMANAGEMENT_SERVICE_NUMBER);

    /**
     * Operation number literal for operation LISTREQUESTDEFS.
     */
    public static final int _LISTREQUESTDEFS_OP_NUMBER = 1;

    /**
     * Operation number instance for operation LISTREQUESTDEFS.
     */
    private static final UShort LISTREQUESTDEFS_OP_NUMBER = new UShort(_LISTREQUESTDEFS_OP_NUMBER);

    /**
     * Operation instance for operation LISTREQUESTDEFS.
     */
    public static final MALProgressOperation LISTREQUESTDEFS_OP = new MALProgressOperation(SERVICE_KEY, 
            LISTREQUESTDEFS_OP_NUMBER, 
            new Identifier("listRequestDefs"), 
            new UShort(1), 
            new OperationField[] {
                new OperationField("domain", true, IdentifierList.SHORT_FORM, ""),
                new OperationField("requestDefs", true, ObjectRefList.SHORT_FORM, "")}, 
            new OperationField[] {}, 
            new OperationField[] {
                new OperationField("requestDefs", false, DefListEntryList.SHORT_FORM, "")}, 
            new OperationField[] {}, 
            "The listRequestDefs operation is used to obtain a list of available RequestDefinitions together with their descriptions.  The list can be filtered by domain or restricted to specified definition IDs.  All available versions are listed. The domain field is an ordered list of identifiers representing a domain hierarchy, any node of which can use ‘*’ as a wildcard (meaning any domain identifier at that level of the hierarchy).  If a set of domains is required that cannot be represented through the use of wildcards, then the operation will need to be repeated using different domain filters.");

    /**
     * Operation number literal for operation GETREQUESTDEFS.
     */
    public static final int _GETREQUESTDEFS_OP_NUMBER = 2;

    /**
     * Operation number instance for operation GETREQUESTDEFS.
     */
    private static final UShort GETREQUESTDEFS_OP_NUMBER = new UShort(_GETREQUESTDEFS_OP_NUMBER);

    /**
     * Operation instance for operation GETREQUESTDEFS.
     */
    public static final MALRequestOperation GETREQUESTDEFS_OP = new MALRequestOperation(SERVICE_KEY, 
            GETREQUESTDEFS_OP_NUMBER, 
            new Identifier("getRequestDefs"), 
            new UShort(1), 
            new OperationField[] {
                new OperationField("requestDefs", false, ObjectRefList.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("definitions", false, RequestDefinitionList.SHORT_FORM, "")}, 
            "The getRequestDefs operation is used to retrieve one or more available RequestDefinitions, whose identity is known to the consumer.");

    /**
     * Operation number literal for operation LISTEVENTDEFS.
     */
    public static final int _LISTEVENTDEFS_OP_NUMBER = 3;

    /**
     * Operation number instance for operation LISTEVENTDEFS.
     */
    private static final UShort LISTEVENTDEFS_OP_NUMBER = new UShort(_LISTEVENTDEFS_OP_NUMBER);

    /**
     * Operation instance for operation LISTEVENTDEFS.
     */
    public static final MALProgressOperation LISTEVENTDEFS_OP = new MALProgressOperation(SERVICE_KEY, 
            LISTEVENTDEFS_OP_NUMBER, 
            new Identifier("listEventDefs"), 
            new UShort(2), 
            new OperationField[] {
                new OperationField("domain", true, IdentifierList.SHORT_FORM, ""),
                new OperationField("eventDefs", true, ObjectRefList.SHORT_FORM, "")}, 
            new OperationField[] {}, 
            new OperationField[] {
                new OperationField("eventDefs", false, DefListEntryList.SHORT_FORM, "")}, 
            new OperationField[] {}, 
            "The listEventDefs operation is used to obtain a list of available EventDefinitions together with their descriptions.  The list can be filtered by domain or restricted to specified definition IDs.  All available versions are listed. The domain field is an ordered list of identifiers representing a domain hierarchy, any node of which can use ‘*’ as a wildcard (meaning any domain identifier at that level of the hierarchy).  If a set of domains is required that cannot be represented through the use of wildcards, then the operation will need to be repeated using different domain filters.");

    /**
     * Operation number literal for operation GETEVENTDEFS.
     */
    public static final int _GETEVENTDEFS_OP_NUMBER = 4;

    /**
     * Operation number instance for operation GETEVENTDEFS.
     */
    private static final UShort GETEVENTDEFS_OP_NUMBER = new UShort(_GETEVENTDEFS_OP_NUMBER);

    /**
     * Operation instance for operation GETEVENTDEFS.
     */
    public static final MALRequestOperation GETEVENTDEFS_OP = new MALRequestOperation(SERVICE_KEY, 
            GETEVENTDEFS_OP_NUMBER, 
            new Identifier("getEventDefs"), 
            new UShort(2), 
            new OperationField[] {
                new OperationField("eventDefs", false, ObjectRefList.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("definitions", false, EventDefinitionList.SHORT_FORM, "")}, 
            "The getEventDefs operation is used to retrieve one or more available EventDefinitions, whose identity is known to the consumer.");

    /**
     * Operation number literal for operation LISTACTIVITYDEFS.
     */
    public static final int _LISTACTIVITYDEFS_OP_NUMBER = 5;

    /**
     * Operation number instance for operation LISTACTIVITYDEFS.
     */
    private static final UShort LISTACTIVITYDEFS_OP_NUMBER = new UShort(_LISTACTIVITYDEFS_OP_NUMBER);

    /**
     * Operation instance for operation LISTACTIVITYDEFS.
     */
    public static final MALProgressOperation LISTACTIVITYDEFS_OP = new MALProgressOperation(SERVICE_KEY, 
            LISTACTIVITYDEFS_OP_NUMBER, 
            new Identifier("listActivityDefs"), 
            new UShort(3), 
            new OperationField[] {
                new OperationField("domain", true, IdentifierList.SHORT_FORM, ""),
                new OperationField("activityDefs", true, ObjectRefList.SHORT_FORM, ""),
                new OperationField("defaultTags", true, StringList.SHORT_FORM, "")}, 
            new OperationField[] {}, 
            new OperationField[] {
                new OperationField("activitytDefs", false, DefListEntryList.SHORT_FORM, "")}, 
            new OperationField[] {}, 
            "The listActivityDefs operation is used to obtain a list of available ActivityDefinitions together with their descriptions.  The list can be filtered by domain or restricted to specified definition IDs.  All available versions are listed. The domain field is an ordered list of identifiers representing a domain hierarchy, any node of which can use ‘*’ as a wildcard (meaning any domain identifier at that level of the hierarchy).  If a set of domains is required that cannot be represented through the use of wildcards, then the operation will need to be repeated using different domain filters.");

    /**
     * Operation number literal for operation GETACTIVITYDEFS.
     */
    public static final int _GETACTIVITYDEFS_OP_NUMBER = 6;

    /**
     * Operation number instance for operation GETACTIVITYDEFS.
     */
    private static final UShort GETACTIVITYDEFS_OP_NUMBER = new UShort(_GETACTIVITYDEFS_OP_NUMBER);

    /**
     * Operation instance for operation GETACTIVITYDEFS.
     */
    public static final MALRequestOperation GETACTIVITYDEFS_OP = new MALRequestOperation(SERVICE_KEY, 
            GETACTIVITYDEFS_OP_NUMBER, 
            new Identifier("getActivityDefs"), 
            new UShort(3), 
            new OperationField[] {
                new OperationField("activityDefs", false, ObjectRefList.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("definitions", false, ActivityDefinitionList.SHORT_FORM, "")}, 
            "The getActivityDefs operation is used to retrieve one or more available ActivityDefinitions, whose identity is known to the consumer.");

    /**
     * Operation number literal for operation LISTRESOURCEDEFS.
     */
    public static final int _LISTRESOURCEDEFS_OP_NUMBER = 7;

    /**
     * Operation number instance for operation LISTRESOURCEDEFS.
     */
    private static final UShort LISTRESOURCEDEFS_OP_NUMBER = new UShort(_LISTRESOURCEDEFS_OP_NUMBER);

    /**
     * Operation instance for operation LISTRESOURCEDEFS.
     */
    public static final MALProgressOperation LISTRESOURCEDEFS_OP = new MALProgressOperation(SERVICE_KEY, 
            LISTRESOURCEDEFS_OP_NUMBER, 
            new Identifier("listResourceDefs"), 
            new UShort(4), 
            new OperationField[] {
                new OperationField("domain", true, IdentifierList.SHORT_FORM, ""),
                new OperationField("dataType", true, AttributeTypeList.SHORT_FORM, "")}, 
            new OperationField[] {}, 
            new OperationField[] {
                new OperationField("resourceDefs", false, DefListEntryList.SHORT_FORM, "")}, 
            new OperationField[] {}, 
            "The listResourceDefs operation is used to obtain a list of available Resources together with their descriptions.  The list can be filtered by domain or restricted to data types.  All available versions are listed. The domain field is an ordered list of identifiers representing a domain hierarchy, any node of which can use ‘*’ as a wildcard (meaning any domain identifier at that level of the hierarchy).  If a set of domains is required that cannot be represented through the use of wildcards, then the operation will need to be repeated using different domain filters.");

    /**
     * Operation number literal for operation GETRESOURCEDEFS.
     */
    public static final int _GETRESOURCEDEFS_OP_NUMBER = 8;

    /**
     * Operation number instance for operation GETRESOURCEDEFS.
     */
    private static final UShort GETRESOURCEDEFS_OP_NUMBER = new UShort(_GETRESOURCEDEFS_OP_NUMBER);

    /**
     * Operation instance for operation GETRESOURCEDEFS.
     */
    public static final MALRequestOperation GETRESOURCEDEFS_OP = new MALRequestOperation(SERVICE_KEY, 
            GETRESOURCEDEFS_OP_NUMBER, 
            new Identifier("getResourceDefs"), 
            new UShort(4), 
            new OperationField[] {
                new OperationField("resources", false, ObjectRefList.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("definitions", false, ResourceList.SHORT_FORM, "")}, 
            "The getResourceDefs operation is used to retrieve the definition of one or more available Resources, whose identity is known to the consumer. It should be noted that this operation is designed to retrieve the resource definition and not the current value of the resource (the value field may contain a default value for the resource).");

    /**
     * Area elements.
     */
    public static final Element[] PLANINFORMATIONMANAGEMENT_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{LISTREQUESTDEFS_OP,
        GETREQUESTDEFS_OP,
        LISTEVENTDEFS_OP,
        GETEVENTDEFS_OP,
        LISTACTIVITYDEFS_OP,
        GETACTIVITYDEFS_OP,
        LISTRESOURCEDEFS_OP,
        GETRESOURCEDEFS_OP};

    /**
     * Creates an instance of the PlanInformationManagement ServiceInfo.
     * 
     */
    public PlanInformationManagementServiceInfo() {
        super(SERVICE_KEY, PLANINFORMATIONMANAGEMENT_SERVICE_NAME, PLANINFORMATIONMANAGEMENT_SERVICE_ELEMENTS, OPERATIONS);
    }

    @Override
    public MALArea getArea() {
        return MPSHelper.MPS_AREA;
    }

    @Override
    public MOErrorException generateMOError(int operationNumber,
            int errorNumber,
            Object extraInfo) {
        switch (operationNumber) {
            case 1:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
                }
                break;
            case 2:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
                }
                break;
            case 3:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
                }
                break;
            case 4:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
                }
                break;
            case 5:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
                }
                break;
            case 6:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
                }
                break;
            case 7:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
                }
                break;
            case 8:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
                }
                break;
        }
        MOErrorException areaError = MPSHelper.generateMOError(errorNumber, extraInfo);
        return (areaError != null) ? areaError : MALHelper.generateMOError(errorNumber, extraInfo);
    }

}
