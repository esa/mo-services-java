package org.ccsds.moims.mo.common.directory;

import org.ccsds.moims.mo.com.COMObject;
import org.ccsds.moims.mo.com.COMService;
import org.ccsds.moims.mo.com.InvalidException;
import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.common.CommonHelper;
import org.ccsds.moims.mo.common.directory.structures.ProviderDetails;
import org.ccsds.moims.mo.common.directory.structures.ProviderSummaryList;
import org.ccsds.moims.mo.common.directory.structures.PublishDetails;
import org.ccsds.moims.mo.common.directory.structures.ServiceFilter;
import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.ServiceKey;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.FileList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * Helper class for Directory service.
 */
public class DirectoryServiceInfo extends COMService {

    /**
     * Service number literal.
     */
    public static final int _DIRECTORY_SERVICE_NUMBER = 1;

    /**
     * Service number instance.
     */
    public static final UShort DIRECTORY_SERVICE_NUMBER = new UShort(_DIRECTORY_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier DIRECTORY_SERVICE_NAME = new Identifier("Directory");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            3, 1, DIRECTORY_SERVICE_NUMBER);

    /**
     * Operation number literal for operation LOOKUPPROVIDER.
     */
    public static final int _LOOKUPPROVIDER_OP_NUMBER = 1;

    /**
     * Operation number instance for operation LOOKUPPROVIDER.
     */
    private static final UShort LOOKUPPROVIDER_OP_NUMBER = new UShort(_LOOKUPPROVIDER_OP_NUMBER);

    /**
     * Operation instance for operation LOOKUPPROVIDER.
     */
    public static final MALRequestOperation LOOKUPPROVIDER_OP = new MALRequestOperation(SERVICE_KEY, 
            LOOKUPPROVIDER_OP_NUMBER, 
            new Identifier("lookupProvider"), 
            new UShort(1), 
            new OperationField[] {
                new OperationField("filter", true, ServiceFilter.SHORT_FORM, "The filter field shall define the lookup query and be used to match details previously published using the publishProvider operation, the specifics of the ServiceFilter fields are defined in the following requirements.\nIf the serviceProviderId field is NULL then all service provider identifiers shall be matched.\nIf the final identifier of the domain field of the filter is the wildcard '*', then all sub-domains shall be searched for matches. See R[2] section 3.5.6.5.g.\nIf the wildcard is used in any other part of the domain other than the final one then an INVALID error shall be returned.\nIf the domain field is NULL then all domains shall be matched.\nIf the network field is NULL then all networks shall be matched.\nIf the sessionType field is NULL then all session types shall be matched.\nIf the sessionName field is NULL then all session names shall be matched.\nThe serviceKey field shall be used to match against ServiceKey fields held in the PublishDetails used to publish a specific provider.\nIf the serviceKey field is NULL then all areas, services and versions shall be matched.\nIf the area field is the wildcard '0' then all areas names shall be matched.\nIf the service field is the wildcard '0' then all services shall be matched.\nIf the version field is the wildcard '0' then all area versions shall be matched.\nIf the requiredCapabilitySets field is NULL or an empty list then all service capability sets shall be matched.")}, 
            new OperationField[] {
                new OperationField("matchingProviders", true, ProviderSummaryList.SHORT_FORM, "The operation shall return a list of service providers that match the filter.\nIf no service providers match the supplied filter then an empty list shall be returned.")}, 
            "The lookup operation allows a service consumer to query the directory service to return a list of service providers that match the requested criteria. If no match is found, then an empty list is returned.\n\nNOTE: The various filters that may be specified as part of this operation are combined using AND logic.");

    /**
     * Operation number literal for operation PUBLISHPROVIDER.
     */
    public static final int _PUBLISHPROVIDER_OP_NUMBER = 2;

    /**
     * Operation number instance for operation PUBLISHPROVIDER.
     */
    private static final UShort PUBLISHPROVIDER_OP_NUMBER = new UShort(_PUBLISHPROVIDER_OP_NUMBER);

    /**
     * Operation instance for operation PUBLISHPROVIDER.
     */
    public static final MALRequestOperation PUBLISHPROVIDER_OP = new MALRequestOperation(SERVICE_KEY, 
            PUBLISHPROVIDER_OP_NUMBER, 
            new Identifier("publishProvider"), 
            new UShort(2), 
            new OperationField[] {
                new OperationField("newProviderDetails", true, PublishDetails.SHORT_FORM, "The newProviderDetails field shall hold the provider details of the service to be added or updated in the directory service.\nIf any of the fields of the newProviderDetails domain/sessionName/network fields are either empty or contain the wildcard '*' an INVALID error shall be returned.\nIf the providerId field of the PublishDetails structure is empty or contains the wildcard '*' an INVALID error shall be returned.\nFor each contained ServiceKey structure if the area/service/version fields contain '0' then an INVALID error shall be returned.\nFor each contained supportedCapabilitySets list if the list is empty or contains '0' then an INVALID error shall be returned.\nIf the supportedLevels list is empty or the priorityLevels field is '0' for each contained AddressDetails structure found either within the ProviderDetails or the inner ServiceCapability structures then an INVALID error shall be returned.\nIf an error is being returned then no changes shall be made.")}, 
            new OperationField[] {
                new OperationField("providerObjId", true, Attribute.LONG_SHORT_FORM, "If the providerId field of the PublishDetails structure matches an existing ServiceProvider COM object, the operation shall update the existing details of that provider.\nIf the providerId field of the PublishDetails structure does not match an existing ServiceProvider COM object, then the operation shall create a new ServiceProvider COM object to represent the new service provider.\nA new ProviderCapabilities COM object shall be created to store the capabilities of the provider.\nThe created objects should be stored in the COM archive by the directory service provider.\nThe operation shall return the COM object instance identifiers of the ServiceProvider and ProviderCapabilities COM objects representing the provider."),
                new OperationField("capabilitiesObjId", true, Attribute.LONG_SHORT_FORM, null)}, 
            "The publishProvider operation adds a new or updates an existing entry in the list of service providers held in the directory service.");

    /**
     * Operation number literal for operation WITHDRAWPROVIDER.
     */
    public static final int _WITHDRAWPROVIDER_OP_NUMBER = 3;

    /**
     * Operation number instance for operation WITHDRAWPROVIDER.
     */
    private static final UShort WITHDRAWPROVIDER_OP_NUMBER = new UShort(_WITHDRAWPROVIDER_OP_NUMBER);

    /**
     * Operation instance for operation WITHDRAWPROVIDER.
     */
    public static final MALSubmitOperation WITHDRAWPROVIDER_OP = new MALSubmitOperation(SERVICE_KEY, 
            WITHDRAWPROVIDER_OP_NUMBER, 
            new Identifier("withdrawProvider"), 
            new UShort(2), 
            new OperationField[] {
                new OperationField("providerObjId", true, Attribute.LONG_SHORT_FORM, "The providerObjId field shall hold the object instance identifier for the ServiceProvider COM object to remove from the directory service.\nIf the supplied identifier is '0' an INVALID error shall be returned.\nIf the supplied identifier does not match an existing ServiceProvider COM object then an UNKNOWN error shall be returned.\nIf an error is being returned then no changes shall be made.\nThe matched provider shall be removed from the directory service.")}, 
            "The withdrawProvider operation removes an existing entry from the list of service providers held in the directory service. If no match is found for the withdraw request, then nothing is changed.");

    /**
     * Operation number literal for operation GETSERVICEXML.
     */
    public static final int _GETSERVICEXML_OP_NUMBER = 4;

    /**
     * Operation number instance for operation GETSERVICEXML.
     */
    private static final UShort GETSERVICEXML_OP_NUMBER = new UShort(_GETSERVICEXML_OP_NUMBER);

    /**
     * Operation instance for operation GETSERVICEXML.
     */
    public static final MALRequestOperation GETSERVICEXML_OP = new MALRequestOperation(SERVICE_KEY, 
            GETSERVICEXML_OP_NUMBER, 
            new Identifier("getServiceXML"), 
            new UShort(3), 
            new OperationField[] {
                new OperationField("providerObjId", true, Attribute.LONG_SHORT_FORM, "The providerObjId field shall hold the COM object instance identifier for the ServiceProvider to obtain the service XML for.\nIf the supplied instance identifier is '0' an INVALID error shall be returned.\nIf the supplied identifier does not match an existing ServiceProvider COM object then an UNKNOWN error shall be returned.")}, 
            new OperationField[] {
                new OperationField("xmlFiles", true, FileList.SHORT_FORM, "The list of XML files supplied during the publishProvider operation for the matched provider shall be returned.\nIf no XML files were supplied by the provider then an empty list shall be returned.")}, 
            "The getServiceXML operation returns the list of XML files that were submitted by the service provider by the publishProvider operation.\nIf no files were supplied then this operation returns an empty list.");

    /**
     * Area elements.
     */
    public static final Element[] DIRECTORY_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{LOOKUPPROVIDER_OP,
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
    public static final UShort SERVICEPROVIDER_OBJECT_NUMBER = new UShort(_SERVICEPROVIDER_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier SERVICEPROVIDER_OBJECT_NAME = new Identifier("ServiceProvider");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType SERVICEPROVIDER_OBJECT_TYPE = new ObjectType(new UShort(3), DIRECTORY_SERVICE_NUMBER, new UOctet(1), SERVICEPROVIDER_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject SERVICEPROVIDER_OBJECT = new COMObject(SERVICEPROVIDER_OBJECT_TYPE, SERVICEPROVIDER_OBJECT_NAME, Attribute.IDENTIFIER_SHORT_FORM, false, null, false, null, false);

    /**
     * Literal for object PROVIDERCAPABILITIES.
     */
    @Deprecated
    public static final int _PROVIDERCAPABILITIES_OBJECT_NUMBER = 2;

    /**
     * Instance for object PROVIDERCAPABILITIES.
     */
    @Deprecated
    public static final UShort PROVIDERCAPABILITIES_OBJECT_NUMBER = new UShort(_PROVIDERCAPABILITIES_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier PROVIDERCAPABILITIES_OBJECT_NAME = new Identifier("ProviderCapabilities");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType PROVIDERCAPABILITIES_OBJECT_TYPE = new ObjectType(new UShort(3), DIRECTORY_SERVICE_NUMBER, new UOctet(1), PROVIDERCAPABILITIES_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject PROVIDERCAPABILITIES_OBJECT = new COMObject(PROVIDERCAPABILITIES_OBJECT_TYPE, PROVIDERCAPABILITIES_OBJECT_NAME, ProviderDetails.SHORT_FORM, true, DirectoryServiceInfo.SERVICEPROVIDER_OBJECT_TYPE, false, null, false);

    /**
     * Object instance.
     */
    public static final COMObject[] COM_OBJECTS = {
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
    public MALArea getArea() {
        return CommonHelper.COMMON_AREA;
    }

    @Override
    public MOErrorException generateMOError(int operationNumber,
            int errorNumber,
            Object extraInfo) {
        switch (operationNumber) {
            case 1:
                switch (errorNumber) {
                    case 70000:
                        return new InvalidException(extraInfo);
                }
                break;
            case 2:
                switch (errorNumber) {
                    case 70000:
                        return new InvalidException(extraInfo);
                }
                break;
            case 3:
                switch (errorNumber) {
                    case 70000:
                        return new InvalidException(extraInfo);
                }
                break;
            case 4:
                switch (errorNumber) {
                    case 70000:
                        return new InvalidException(extraInfo);
                }
                break;
        }
        MOErrorException areaError = CommonHelper.generateMOError(errorNumber, extraInfo);
        return (areaError != null) ? areaError : MALHelper.generateMOError(errorNumber, extraInfo);
    }

}
