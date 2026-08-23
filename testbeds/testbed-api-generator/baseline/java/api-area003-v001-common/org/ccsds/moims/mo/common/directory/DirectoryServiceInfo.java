package org.ccsds.moims.mo.common.directory;

/**
 * Helper class for Directory service.
 */
public class DirectoryServiceInfo extends org.ccsds.moims.mo.com.COMService {

    /**
     * Service number literal.
     */
    public static final int _DIRECTORY_SERVICE_NUMBER = 1;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort DIRECTORY_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_DIRECTORY_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier DIRECTORY_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("Directory");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            3, 1, DIRECTORY_SERVICE_NUMBER);

    /**
     * Operation number literal for operation LOOKUPPROVIDER.
     */
    public static final int _LOOKUPPROVIDER_OP_NUMBER = 1;

    /**
     * Operation number instance for operation LOOKUPPROVIDER.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort LOOKUPPROVIDER_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_LOOKUPPROVIDER_OP_NUMBER);

    /**
     * Operation instance for operation LOOKUPPROVIDER.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation LOOKUPPROVIDER_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            LOOKUPPROVIDER_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("lookupProvider"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("filter", true, org.ccsds.moims.mo.common.directory.structures.ServiceFilter.SHORT_FORM, "The filter field shall define the lookup query and be used to match details previously published using the publishProvider operation, the specifics of the ServiceFilter fields are defined in the following requirements.\nIf the serviceProviderId field is NULL then all service provider identifiers shall be matched.\nIf the final identifier of the domain field of the filter is the wildcard '*', then all sub-domains shall be searched for matches. See R[2] section 3.5.6.5.g.\nIf the wildcard is used in any other part of the domain other than the final one then an INVALID error shall be returned.\nIf the domain field is NULL then all domains shall be matched.\nIf the network field is NULL then all networks shall be matched.\nIf the sessionType field is NULL then all session types shall be matched.\nIf the sessionName field is NULL then all session names shall be matched.\nThe serviceKey field shall be used to match against ServiceKey fields held in the PublishDetails used to publish a specific provider.\nIf the serviceKey field is NULL then all areas, services and versions shall be matched.\nIf the area field is the wildcard '0' then all areas names shall be matched.\nIf the service field is the wildcard '0' then all services shall be matched.\nIf the version field is the wildcard '0' then all area versions shall be matched.\nIf the requiredCapabilitySets field is NULL or an empty list then all service capability sets shall be matched.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("matchingProviders", true, org.ccsds.moims.mo.common.directory.structures.ProviderSummaryList.SHORT_FORM, "The operation shall return a list of service providers that match the filter.\nIf no service providers match the supplied filter then an empty list shall be returned.")}, 
            "The lookup operation allows a service consumer to query the directory service to return a list of service providers that match the requested criteria. If no match is found, then an empty list is returned.\n\nNOTE: The various filters that may be specified as part of this operation are combined using AND logic.");

    /**
     * Operation number literal for operation PUBLISHPROVIDER.
     */
    public static final int _PUBLISHPROVIDER_OP_NUMBER = 2;

    /**
     * Operation number instance for operation PUBLISHPROVIDER.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort PUBLISHPROVIDER_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_PUBLISHPROVIDER_OP_NUMBER);

    /**
     * Operation instance for operation PUBLISHPROVIDER.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation PUBLISHPROVIDER_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            PUBLISHPROVIDER_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("publishProvider"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("newProviderDetails", true, org.ccsds.moims.mo.common.directory.structures.PublishDetails.SHORT_FORM, "The newProviderDetails field shall hold the provider details of the service to be added or updated in the directory service.\nIf any of the fields of the newProviderDetails domain/sessionName/network fields are either empty or contain the wildcard '*' an INVALID error shall be returned.\nIf the providerId field of the PublishDetails structure is empty or contains the wildcard '*' an INVALID error shall be returned.\nFor each contained ServiceKey structure if the area/service/version fields contain '0' then an INVALID error shall be returned.\nFor each contained supportedCapabilitySets list if the list is empty or contains '0' then an INVALID error shall be returned.\nIf the supportedLevels list is empty or the priorityLevels field is '0' for each contained AddressDetails structure found either within the ProviderDetails or the inner ServiceCapability structures then an INVALID error shall be returned.\nIf an error is being returned then no changes shall be made.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("providerObjId", true, org.ccsds.moims.mo.mal.structures.Attribute.LONG_SHORT_FORM, "If the providerId field of the PublishDetails structure matches an existing ServiceProvider COM object, the operation shall update the existing details of that provider.\nIf the providerId field of the PublishDetails structure does not match an existing ServiceProvider COM object, then the operation shall create a new ServiceProvider COM object to represent the new service provider.\nA new ProviderCapabilities COM object shall be created to store the capabilities of the provider.\nThe created objects should be stored in the COM archive by the directory service provider.\nThe operation shall return the COM object instance identifiers of the ServiceProvider and ProviderCapabilities COM objects representing the provider."),
                new org.ccsds.moims.mo.mal.OperationField("capabilitiesObjId", true, org.ccsds.moims.mo.mal.structures.Attribute.LONG_SHORT_FORM, null)}, 
            "The publishProvider operation adds a new or updates an existing entry in the list of service providers held in the directory service.");

    /**
     * Operation number literal for operation WITHDRAWPROVIDER.
     */
    public static final int _WITHDRAWPROVIDER_OP_NUMBER = 3;

    /**
     * Operation number instance for operation WITHDRAWPROVIDER.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort WITHDRAWPROVIDER_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_WITHDRAWPROVIDER_OP_NUMBER);

    /**
     * Operation instance for operation WITHDRAWPROVIDER.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation WITHDRAWPROVIDER_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            WITHDRAWPROVIDER_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("withdrawProvider"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("providerObjId", true, org.ccsds.moims.mo.mal.structures.Attribute.LONG_SHORT_FORM, "The providerObjId field shall hold the object instance identifier for the ServiceProvider COM object to remove from the directory service.\nIf the supplied identifier is '0' an INVALID error shall be returned.\nIf the supplied identifier does not match an existing ServiceProvider COM object then an UNKNOWN error shall be returned.\nIf an error is being returned then no changes shall be made.\nThe matched provider shall be removed from the directory service.")}, 
            "The withdrawProvider operation removes an existing entry from the list of service providers held in the directory service. If no match is found for the withdraw request, then nothing is changed.");

    /**
     * Operation number literal for operation GETSERVICEXML.
     */
    public static final int _GETSERVICEXML_OP_NUMBER = 4;

    /**
     * Operation number instance for operation GETSERVICEXML.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort GETSERVICEXML_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GETSERVICEXML_OP_NUMBER);

    /**
     * Operation instance for operation GETSERVICEXML.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation GETSERVICEXML_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            GETSERVICEXML_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("getServiceXML"), 
            new org.ccsds.moims.mo.mal.structures.UShort(3), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("providerObjId", true, org.ccsds.moims.mo.mal.structures.Attribute.LONG_SHORT_FORM, "The providerObjId field shall hold the COM object instance identifier for the ServiceProvider to obtain the service XML for.\nIf the supplied instance identifier is '0' an INVALID error shall be returned.\nIf the supplied identifier does not match an existing ServiceProvider COM object then an UNKNOWN error shall be returned.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("xmlFiles", true, org.ccsds.moims.mo.mal.structures.FileList.SHORT_FORM, "The list of XML files supplied during the publishProvider operation for the matched provider shall be returned.\nIf no XML files were supplied by the provider then an empty list shall be returned.")}, 
            "The getServiceXML operation returns the list of XML files that were submitted by the service provider by the publishProvider operation.\nIf no files were supplied then this operation returns an empty list.");

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] DIRECTORY_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{LOOKUPPROVIDER_OP,
        PUBLISHPROVIDER_OP,
        WITHDRAWPROVIDER_OP,
        GETSERVICEXML_OP};

    /**
     * Literal for object SERVICEPROVIDER.
     */
    @Deprecated
    public static final int _SERVICEPROVIDER_OBJECT_NUMBER = 1;

    /**
     * Instance for object SERVICEPROVIDER.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort SERVICEPROVIDER_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_SERVICEPROVIDER_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier SERVICEPROVIDER_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ServiceProvider");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType SERVICEPROVIDER_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(3), DIRECTORY_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), SERVICEPROVIDER_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject SERVICEPROVIDER_OBJECT = new org.ccsds.moims.mo.com.COMObject(SERVICEPROVIDER_OBJECT_TYPE, SERVICEPROVIDER_OBJECT_NAME, org.ccsds.moims.mo.mal.structures.Attribute.IDENTIFIER_SHORT_FORM, false, null, false, null, false);

    /**
     * Literal for object PROVIDERCAPABILITIES.
     */
    @Deprecated
    public static final int _PROVIDERCAPABILITIES_OBJECT_NUMBER = 2;

    /**
     * Instance for object PROVIDERCAPABILITIES.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort PROVIDERCAPABILITIES_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_PROVIDERCAPABILITIES_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier PROVIDERCAPABILITIES_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ProviderCapabilities");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType PROVIDERCAPABILITIES_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(3), DIRECTORY_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), PROVIDERCAPABILITIES_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject PROVIDERCAPABILITIES_OBJECT = new org.ccsds.moims.mo.com.COMObject(PROVIDERCAPABILITIES_OBJECT_TYPE, PROVIDERCAPABILITIES_OBJECT_NAME, org.ccsds.moims.mo.common.directory.structures.ProviderDetails.SHORT_FORM, true, org.ccsds.moims.mo.common.directory.DirectoryServiceInfo.SERVICEPROVIDER_OBJECT_TYPE, false, null, false);

    /**
     * Object instance.
     */
    public static final org.ccsds.moims.mo.com.COMObject[] COM_OBJECTS = {
        SERVICEPROVIDER_OBJECT,
        PROVIDERCAPABILITIES_OBJECT,};

    /**
     * Creates an instance of the Directory ServiceInfo.
     * 
     */
    public DirectoryServiceInfo() {
        super(SERVICE_KEY, DIRECTORY_SERVICE_NAME, DIRECTORY_SERVICE_ELEMENTS, OPERATIONS, COM_OBJECTS);
    }

    @Override
    public org.ccsds.moims.mo.mal.MALArea getArea() {
        return org.ccsds.moims.mo.common.CommonHelper.COMMON_AREA;
    }

    @Override
    public org.ccsds.moims.mo.mal.MOErrorException generateMOError(int errorNumber,
            Object extraInfo) {
        switch (errorNumber) {
        }
        return null;
    }

}
