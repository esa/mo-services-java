package org.ccsds.moims.mo.mps.planinformationmanagement;

/**
 * Helper class for PlanInformationManagement service.
 */
public class PlanInformationManagementServiceInfo extends org.ccsds.moims.mo.mal.ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _PLANINFORMATIONMANAGEMENT_SERVICE_NUMBER = 4;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort PLANINFORMATIONMANAGEMENT_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_PLANINFORMATIONMANAGEMENT_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier PLANINFORMATIONMANAGEMENT_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("PlanInformationManagement");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            5, 1, PLANINFORMATIONMANAGEMENT_SERVICE_NUMBER);

    /**
     * Operation number literal for operation LISTREQUESTDEFS.
     */
    public static final int _LISTREQUESTDEFS_OP_NUMBER = 1;

    /**
     * Operation number instance for operation LISTREQUESTDEFS.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort LISTREQUESTDEFS_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_LISTREQUESTDEFS_OP_NUMBER);

    /**
     * Operation instance for operation LISTREQUESTDEFS.
     */
    public static final org.ccsds.moims.mo.mal.MALProgressOperation LISTREQUESTDEFS_OP = new org.ccsds.moims.mo.mal.MALProgressOperation(SERVICE_KEY, 
            LISTREQUESTDEFS_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("listRequestDefs"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("requestDefs", true, org.ccsds.moims.mo.mal.structures.ObjectRefList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("requestDefs", false, org.ccsds.moims.mo.mps.structures.DefListEntryList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            "The listRequestDefs operation is used to obtain a list of available RequestDefinitions together with their descriptions.  The list can be filtered by domain or restricted to specified definition IDs.  All available versions are listed. The domain field is an ordered list of identifiers representing a domain hierarchy, any node of which can use ‘*’ as a wildcard (meaning any domain identifier at that level of the hierarchy).  If a set of domains is required that cannot be represented through the use of wildcards, then the operation will need to be repeated using different domain filters.");

    /**
     * Operation number literal for operation GETREQUESTDEFS.
     */
    public static final int _GETREQUESTDEFS_OP_NUMBER = 2;

    /**
     * Operation number instance for operation GETREQUESTDEFS.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort GETREQUESTDEFS_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GETREQUESTDEFS_OP_NUMBER);

    /**
     * Operation instance for operation GETREQUESTDEFS.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation GETREQUESTDEFS_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            GETREQUESTDEFS_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("getRequestDefs"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("requestDefs", false, org.ccsds.moims.mo.mal.structures.ObjectRefList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("definitions", false, org.ccsds.moims.mo.mps.structures.RequestDefinitionList.SHORT_FORM, "")}, 
            "The getRequestDefs operation is used to retrieve one or more available RequestDefinitions, whose identity is known to the consumer.");

    /**
     * Operation number literal for operation LISTEVENTDEFS.
     */
    public static final int _LISTEVENTDEFS_OP_NUMBER = 3;

    /**
     * Operation number instance for operation LISTEVENTDEFS.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort LISTEVENTDEFS_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_LISTEVENTDEFS_OP_NUMBER);

    /**
     * Operation instance for operation LISTEVENTDEFS.
     */
    public static final org.ccsds.moims.mo.mal.MALProgressOperation LISTEVENTDEFS_OP = new org.ccsds.moims.mo.mal.MALProgressOperation(SERVICE_KEY, 
            LISTEVENTDEFS_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("listEventDefs"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("eventDefs", true, org.ccsds.moims.mo.mal.structures.ObjectRefList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("eventDefs", false, org.ccsds.moims.mo.mps.structures.DefListEntryList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            "The listEventDefs operation is used to obtain a list of available EventDefinitions together with their descriptions.  The list can be filtered by domain or restricted to specified definition IDs.  All available versions are listed. The domain field is an ordered list of identifiers representing a domain hierarchy, any node of which can use ‘*’ as a wildcard (meaning any domain identifier at that level of the hierarchy).  If a set of domains is required that cannot be represented through the use of wildcards, then the operation will need to be repeated using different domain filters.");

    /**
     * Operation number literal for operation GETEVENTDEFS.
     */
    public static final int _GETEVENTDEFS_OP_NUMBER = 4;

    /**
     * Operation number instance for operation GETEVENTDEFS.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort GETEVENTDEFS_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GETEVENTDEFS_OP_NUMBER);

    /**
     * Operation instance for operation GETEVENTDEFS.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation GETEVENTDEFS_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            GETEVENTDEFS_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("getEventDefs"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("eventDefs", false, org.ccsds.moims.mo.mal.structures.ObjectRefList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("definitions", false, org.ccsds.moims.mo.mps.structures.EventDefinitionList.SHORT_FORM, "")}, 
            "The getEventDefs operation is used to retrieve one or more available EventDefinitions, whose identity is known to the consumer.");

    /**
     * Operation number literal for operation LISTACTIVITYDEFS.
     */
    public static final int _LISTACTIVITYDEFS_OP_NUMBER = 5;

    /**
     * Operation number instance for operation LISTACTIVITYDEFS.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort LISTACTIVITYDEFS_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_LISTACTIVITYDEFS_OP_NUMBER);

    /**
     * Operation instance for operation LISTACTIVITYDEFS.
     */
    public static final org.ccsds.moims.mo.mal.MALProgressOperation LISTACTIVITYDEFS_OP = new org.ccsds.moims.mo.mal.MALProgressOperation(SERVICE_KEY, 
            LISTACTIVITYDEFS_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("listActivityDefs"), 
            new org.ccsds.moims.mo.mal.structures.UShort(3), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("activityDefs", true, org.ccsds.moims.mo.mal.structures.ObjectRefList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("defaultTags", true, org.ccsds.moims.mo.mal.structures.StringList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("activitytDefs", false, org.ccsds.moims.mo.mps.structures.DefListEntryList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            "The listActivityDefs operation is used to obtain a list of available ActivityDefinitions together with their descriptions.  The list can be filtered by domain or restricted to specified definition IDs.  All available versions are listed. The domain field is an ordered list of identifiers representing a domain hierarchy, any node of which can use ‘*’ as a wildcard (meaning any domain identifier at that level of the hierarchy).  If a set of domains is required that cannot be represented through the use of wildcards, then the operation will need to be repeated using different domain filters.");

    /**
     * Operation number literal for operation GETACTIVITYDEFS.
     */
    public static final int _GETACTIVITYDEFS_OP_NUMBER = 6;

    /**
     * Operation number instance for operation GETACTIVITYDEFS.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort GETACTIVITYDEFS_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GETACTIVITYDEFS_OP_NUMBER);

    /**
     * Operation instance for operation GETACTIVITYDEFS.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation GETACTIVITYDEFS_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            GETACTIVITYDEFS_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("getActivityDefs"), 
            new org.ccsds.moims.mo.mal.structures.UShort(3), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("activityDefs", false, org.ccsds.moims.mo.mal.structures.ObjectRefList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("definitions", false, org.ccsds.moims.mo.mps.structures.ActivityDefinitionList.SHORT_FORM, "")}, 
            "The getActivityDefs operation is used to retrieve one or more available ActivityDefinitions, whose identity is known to the consumer.");

    /**
     * Operation number literal for operation LISTRESOURCEDEFS.
     */
    public static final int _LISTRESOURCEDEFS_OP_NUMBER = 7;

    /**
     * Operation number instance for operation LISTRESOURCEDEFS.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort LISTRESOURCEDEFS_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_LISTRESOURCEDEFS_OP_NUMBER);

    /**
     * Operation instance for operation LISTRESOURCEDEFS.
     */
    public static final org.ccsds.moims.mo.mal.MALProgressOperation LISTRESOURCEDEFS_OP = new org.ccsds.moims.mo.mal.MALProgressOperation(SERVICE_KEY, 
            LISTRESOURCEDEFS_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("listResourceDefs"), 
            new org.ccsds.moims.mo.mal.structures.UShort(4), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("dataType", true, org.ccsds.moims.mo.mal.structures.AttributeTypeList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("resourceDefs", false, org.ccsds.moims.mo.mps.structures.DefListEntryList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            "The listResourceDefs operation is used to obtain a list of available Resources together with their descriptions.  The list can be filtered by domain or restricted to data types.  All available versions are listed. The domain field is an ordered list of identifiers representing a domain hierarchy, any node of which can use ‘*’ as a wildcard (meaning any domain identifier at that level of the hierarchy).  If a set of domains is required that cannot be represented through the use of wildcards, then the operation will need to be repeated using different domain filters.");

    /**
     * Operation number literal for operation GETRESOURCEDEFS.
     */
    public static final int _GETRESOURCEDEFS_OP_NUMBER = 8;

    /**
     * Operation number instance for operation GETRESOURCEDEFS.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort GETRESOURCEDEFS_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GETRESOURCEDEFS_OP_NUMBER);

    /**
     * Operation instance for operation GETRESOURCEDEFS.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation GETRESOURCEDEFS_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            GETRESOURCEDEFS_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("getResourceDefs"), 
            new org.ccsds.moims.mo.mal.structures.UShort(4), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("resources", false, org.ccsds.moims.mo.mal.structures.ObjectRefList.SHORT_FORM, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("definitions", false, org.ccsds.moims.mo.mps.structures.ResourceList.SHORT_FORM, "")}, 
            "The getResourceDefs operation is used to retrieve the definition of one or more available Resources, whose identity is known to the consumer. It should be noted that this operation is designed to retrieve the resource definition and not the current value of the resource (the value field may contain a default value for the resource).");

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] PLANINFORMATIONMANAGEMENT_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{LISTREQUESTDEFS_OP,
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
    public org.ccsds.moims.mo.mal.MALArea getArea() {
        return org.ccsds.moims.mo.mps.MPSHelper.MPS_AREA;
    }

    @Override
    public org.ccsds.moims.mo.mal.MOErrorException generateMOError(int errorNumber,
            Object extraInfo) {
        switch (errorNumber) {
            case 1:
                return new org.ccsds.moims.mo.mps.InvalidException(extraInfo);
            case 2:
                return new org.ccsds.moims.mo.mps.CancelFailedException(extraInfo);
            case 3:
                return new org.ccsds.moims.mo.mps.UpdateFailedException(extraInfo);
            case 4:
                return new org.ccsds.moims.mo.mps.RevokeFailedException(extraInfo);
            case 5:
                return new org.ccsds.moims.mo.mps.InsertFailedException(extraInfo);
            case 6:
                return new org.ccsds.moims.mo.mps.DeleteFailedException(extraInfo);
            case 7:
                return new org.ccsds.moims.mo.mps.ActivateFailedException(extraInfo);
            case 8:
                return new org.ccsds.moims.mo.mps.DeactivateFailedException(extraInfo);
            case 9:
                return new org.ccsds.moims.mo.mps.SubmitFailedException(extraInfo);
            case 10:
                return new org.ccsds.moims.mo.mps.UnsupportedException(extraInfo);
            case 11:
                return new org.ccsds.moims.mo.mps.ActivateSubplanFailedException(extraInfo);
            case 12:
                return new org.ccsds.moims.mo.mps.DeactivateSubplanFailedException(extraInfo);
        }
        return null;
    }

}
