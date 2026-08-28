package org.ccsds.moims.mo.common.configuration;

/**
 * Helper class for Configuration service.
 */
public class ConfigurationServiceInfo extends org.ccsds.moims.mo.com.COMService {

    /**
     * Service number literal.
     */
    public static final int _CONFIGURATION_SERVICE_NUMBER = 5;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort CONFIGURATION_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_CONFIGURATION_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier CONFIGURATION_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("Configuration");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            3, 1, CONFIGURATION_SERVICE_NUMBER);

    /**
     * Operation number literal for operation ACTIVATE.
     */
    public static final int _ACTIVATE_OP_NUMBER = 1;

    /**
     * Operation number instance for operation ACTIVATE.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort ACTIVATE_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ACTIVATE_OP_NUMBER);

    /**
     * Operation instance for operation ACTIVATE.
     */
    public static final org.ccsds.moims.mo.mal.MALInvokeOperation ACTIVATE_OP = new org.ccsds.moims.mo.mal.MALInvokeOperation(SERVICE_KEY, 
            ACTIVATE_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("activate"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("serviceProvider", true, org.ccsds.moims.mo.com.structures.ObjectKey.SHORT_FORM, "The serviceProvider field shall contain the domain and object instance identifier of the ServiceProvider COM object being (re)configured.\nIf the service provider referenced by the serviceProvider field is not known an UNKNOWN error shall be returned."),
                new org.ccsds.moims.mo.mal.OperationField("configObjId", true, org.ccsds.moims.mo.com.structures.ObjectId.SHORT_FORM, "The configObjId field shall hold the COM object identifier that identifies the configuration to activate.\nThe configObjId field shall reference either a ProviderConfiguration or ServiceConfiguration object.\nAn UNKNOWN error shall be returned if the object instance identifier held in the configObjId field does not match an existing configuration.\nAn INVALID error shall be returned if the object instance identifier held in the configObjId field does not reference either a ProviderConfiguration or ServiceConfiguration object.\nIf the object instance identifier held in the configObjId field does not reference a valid configuration for the service provider an INVALID error shall be returned. A valid configuration is one that is returned from the list operation for the matched service provider.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("activationResult", true, org.ccsds.moims.mo.mal.structures.Attribute.BOOLEAN_SHORT_FORM, "The service provider that implements the selected service shall, after the reception of the ConfigurationSwitch event, reconfigure itself and publish a ConfigurationSwitched COM event.\nIf the operation fails the previous configuration shall remain active.\nIn the case of a provider configuration, where multiple service configurations are being switched, the provider must switch all configurations successfully or roll back to the previous configuration. No partial reconfiguration is supported.\nIf a provider level configuration is successful, and a COM archive is being used, then the service provider that implements the selected service shall store in the COM archive a new ProviderConfigurationLink COM object that links its ServiceProvider object to the new activated ProviderConfiguration COM object.\nThe response message shall be sent when the configuration is either made active or fails.\nIf the activation was successful then the activationResult field shall be set to TRUE, otherwise FALSE for failure."),
                new org.ccsds.moims.mo.mal.OperationField("previousConfig", true, org.ccsds.moims.mo.com.structures.ObjectIdList.SHORT_FORM, "The previousConfig field shall point to the previously active configuration or NULL if no configuration was previously active or the activationResult was FALSE for failure.\nIf a service configuration was requested, configObjId field shall referenced a ServiceConfiguration, the response shall contain a list of a single item of the previous ServiceConfiguration COM object.\nIf a provider configuration was requested, configObjId field shall referenced a ProviderConfiguration, the response shall contain a list of the previous ProviderConfiguration COM object followed by the list of previous ServiceConfiguration objects active for that provider.")}, 
            "The activate operation instructs a service provider to make a specific configuration active. The operation returns once the new configuration is active or when the activation attempt has failed for some reason.\nThe requested configuration must have either already been added to the configuration service provider using the add operation or alternatively known to the configuration service by another implementation specific mechanism.\nNOTE - The service is reconfigured for all service consumers not just the calling consumer.");

    /**
     * Operation number literal for operation LIST.
     */
    public static final int _LIST_OP_NUMBER = 2;

    /**
     * Operation number instance for operation LIST.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort LIST_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_LIST_OP_NUMBER);

    /**
     * Operation instance for operation LIST.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation LIST_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            LIST_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("list"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("configurationType", true, org.ccsds.moims.mo.common.configuration.structures.ConfigurationType.SHORT_FORM, "The configurationType argument shall hold the type of configuration to be listed."),
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, "The domain request argument shall contain the domain of the configuration objects to return.\nThe domain field supports the wildcard value of '*' only in the last part of the domain, otherwise an INVALID error shall be returned. See section 3.5.6.5.g in R[2]."),
                new org.ccsds.moims.mo.mal.OperationField("serviceKey", true, org.ccsds.moims.mo.common.structures.ServiceKey.SHORT_FORM, "If the request configuration type is SERVICE, then an optional filter may be supplied in the serviceKey field where the ServiceKey composite holds the service area, service, and version values to match on.\nThe filter shall be applied with a logical AND for each field of the service key.\nWildcard values of '0' are not accepted in the serviceKey fields, an INVALID error shall be returned in this case.\nFor other types of configuration the serviceKey field shall be ignored and may be set to NULL in the request.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("objInstIds", true, org.ccsds.moims.mo.com.structures.ObjectIdList.SHORT_FORM, "The operation shall return the list of matched configuration objects known to the configuration service provider.\nIf no configurations matched then an empty list shall be returned.")}, 
            "The list operation returns list of configurations known to the configuration service provider for a certain configuration type in a specific domain.\nTo appear in the response from the list operation a configuration must have either been added to the configuration service provider using the add operation or alternatively known to the configuration service by another implementation specific mechanism.");

    /**
     * Operation number literal for operation GETCURRENT.
     */
    public static final int _GETCURRENT_OP_NUMBER = 3;

    /**
     * Operation number instance for operation GETCURRENT.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort GETCURRENT_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GETCURRENT_OP_NUMBER);

    /**
     * Operation instance for operation GETCURRENT.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation GETCURRENT_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            GETCURRENT_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("getCurrent"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("serviceProvider", true, org.ccsds.moims.mo.com.structures.ObjectKey.SHORT_FORM, "The serviceProvider field shall contain the domain and object instance identifier of the ServiceProvider COM object being queried."),
                new org.ccsds.moims.mo.mal.OperationField("serviceKey", true, org.ccsds.moims.mo.common.structures.ServiceKey.SHORT_FORM, "If the serviceKey field is not NULL, then the operation shall return the configuration of the selected service, as specified in the field.\nFor retrieval of the provider level configuration the serviceKey field shall be set to NULL in the request.\nAn UNKNOWN error shall be returned if the combination of service provider and service filter fields don't match an existing service provider, service key or configuration.\nNo wildcards are supported, an INVALID error must be returned in this case.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("objInstId", true, org.ccsds.moims.mo.com.structures.ObjectIdList.SHORT_FORM, "If a service configuration was requested, serviceKey was not NULL, the response shall contain a list of a single item of the matched ServiceConfiguration COM object.\nIf a provider configuration was requested, serviceKey was NULL, the response shall contain a list of the matched ProviderConfiguration COM object followed by the list of active ServiceConfiguration objects active for that provider.")}, 
            "The getCurrent operation returns the currently selected configuration of a service provider, either of the complete provider (ProviderConfiguration) or a specific service (ServiceConfiguration) of that provider, as far as the Configuration Service provider is concerned.\nThis means that if the provider of a specific service has modified their configuration by some other means, and this has not been stored using the storeCurrent operation, then this operation will return the unmodified configuration.\nIf a provider level configuration is required by this operation, and service configurations different to the ones in the provider configuration have been activated by the activate operation, then this operation will return the ProviderConfiguration followed by the list of modified ServiceConfigurations.\nThe current configuration of a service provider is most likely the configuration that was last activated using the activate operation, however implementations of the configuration service may have other means (outside the scope of this service) of selecting the current configuration of a service provider.\nThe operation can also be used to determine the initial configuration of a service provider before it has been activated, it is therefore a useful operation to call by a service provider during start-up to determine its initial configuration. If this is the case a ConfigurationSwitched event should be published.");

    /**
     * Operation number literal for operation EXPORTXML.
     */
    public static final int _EXPORTXML_OP_NUMBER = 4;

    /**
     * Operation number instance for operation EXPORTXML.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort EXPORTXML_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_EXPORTXML_OP_NUMBER);

    /**
     * Operation instance for operation EXPORTXML.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation EXPORTXML_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            EXPORTXML_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("exportXML"), 
            new org.ccsds.moims.mo.mal.structures.UShort(3), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("confObjId", true, org.ccsds.moims.mo.com.structures.ObjectId.SHORT_FORM, "If the implementation of the configuration service is not using a COM archive then an UNSUPPORTED_OPERATION error shall be returned.\nThe confObjId argument shall contain the type, domain and COM object instance identifier of the configuration object to return the XML representation of.\nAn UNKNOWN error shall be returned if the confObjId field does not match an existing COM object.\nAn INVALID error shall be returned if the confObjId does not refer to either a ProviderConfiguration or a ServiceConfiguration object.\nAn INVALID error shall be returned if the confObjId refers to either a hard-coded or a non-COM configuration."),
                new org.ccsds.moims.mo.mal.OperationField("returnComplete", true, org.ccsds.moims.mo.mal.structures.Attribute.BOOLEAN_SHORT_FORM, "The returnComplete Boolean shall be set to True if the returned XML is to be in the complete standardised format, otherwise it will be in the compact standardised format.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("xmlConfiguration", true, org.ccsds.moims.mo.mal.structures.File.SHORT_FORM, "The returned File object shall contain the configuration XML.\nThe Configuration object shall not be deleted from the COM Archive.")}, 
            "The exportXML operation returns the actual Configuration information in the XML format from the configuration object stored in the Archive.\nThe returned XML is in the standardised format and one of two levels of detail, compact or complete, can be selected. Compact contains just the COM object instance identifiers with the respective domains and object types whereas complete augments the compact with the additional set of values inside the respective service objects. \nThe XML standardised format is the XML representation of the referenced COM objects as defined by the XML encoding given in R[4]. Example XML documents can be found in an annex at the end of this specification.\nIf the implementation of the configuration service is not using a COM archive then an error is returned.\nIt should be noted that this operation only supports COM configurations.");

    /**
     * Operation number literal for operation ADD.
     */
    public static final int _ADD_OP_NUMBER = 5;

    /**
     * Operation number instance for operation ADD.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort ADD_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ADD_OP_NUMBER);

    /**
     * Operation instance for operation ADD.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation ADD_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            ADD_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("add"), 
            new org.ccsds.moims.mo.mal.structures.UShort(4), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("serviceProvider", true, org.ccsds.moims.mo.com.structures.ObjectKey.SHORT_FORM, "If the implementation of the configuration service is not using a COM archive then an UNSUPPORTED_OPERATION error shall be returned.\nThe first argument shall contain the domain and object identifier of the ServiceProvider COM object which the configurations are being added to."),
                new org.ccsds.moims.mo.mal.OperationField("configObjIds", true, org.ccsds.moims.mo.com.structures.ObjectIdList.SHORT_FORM, "The second argument shall contain a list of service and/or provider configurations to add to the list of configurations available for the specific service provider.\nIf either the service provider or the configuration objects are unknown then an UNKNOWN error shall be returned.\nIf any of the supplied configuration objects are not provider or service configuration objects then an INVALID error shall be returned.\nIf an error is raised then no new configurations shall be added as a result of this operation call.")}, 
            "The add operation makes a new Configuration available on the Configuration Service. The Configuration must already exist in the COM archive to be added to the Configuration Service.\nIf the implementation of the configuration service is not using a COM archive then an error is returned.\nThis operation can be used to add COM, non-COM and hard-coded configurations.");

    /**
     * Operation number literal for operation REMOVE.
     */
    public static final int _REMOVE_OP_NUMBER = 6;

    /**
     * Operation number instance for operation REMOVE.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort REMOVE_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_REMOVE_OP_NUMBER);

    /**
     * Operation instance for operation REMOVE.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation REMOVE_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            REMOVE_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("remove"), 
            new org.ccsds.moims.mo.mal.structures.UShort(4), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("serviceProvider", true, org.ccsds.moims.mo.com.structures.ObjectKey.SHORT_FORM, "If the implementation of the configuration service is not using a COM archive then an UNSUPPORTED_OPERATION error shall be returned.\nThe first argument shall contain the domain and object identifier of the ServiceProvider COM object which the configurations are being removed from."),
                new org.ccsds.moims.mo.mal.OperationField("configObjIds", true, org.ccsds.moims.mo.com.structures.ObjectIdList.SHORT_FORM, "The second argument shall contain a list of service and/or provider configurations to remove from the list of configurations available for the specific service provider.\nIf either the service provider or a provided object identifier does not match an existing configuration object then this operation shall fail with an UNKNOWN error.\nIf any of the supplied configuration objects are not provider or service configuration objects then an INVALID error shall be returned.\nIf an error is raised then no configurations shall be removed as a result of this operation call.\nMatched configuration objects shall not be removed from the COM archive only the list of configuration objects in the provider.")}, 
            "The remove operation, removes a provider configuration from the list of configurations available for that provider in the configuration service. The operation does not remove the configuration objects from the COM archive, merely removes the objects from the configuration service provider.\nIf the implementation of the configuration service is not using a COM archive then an error is returned.\nThis operation can be used to remove COM, non-COM, and hard-coded configurations from a provider.");

    /**
     * Operation number literal for operation STORECURRENT.
     */
    public static final int _STORECURRENT_OP_NUMBER = 7;

    /**
     * Operation number instance for operation STORECURRENT.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort STORECURRENT_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_STORECURRENT_OP_NUMBER);

    /**
     * Operation instance for operation STORECURRENT.
     */
    public static final org.ccsds.moims.mo.mal.MALInvokeOperation STORECURRENT_OP = new org.ccsds.moims.mo.mal.MALInvokeOperation(SERVICE_KEY, 
            STORECURRENT_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("storeCurrent"), 
            new org.ccsds.moims.mo.mal.structures.UShort(5), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("serviceProvider", true, org.ccsds.moims.mo.com.structures.ObjectKey.SHORT_FORM, "If the implementation of the configuration service is not using a COM archive then an UNSUPPORTED_OPERATION error shall be returned.\nThe serviceProvider field shall contain the domain and object instance identifier of the ServiceProvider COM object that must store its current configuration.\nIf the service provider is not known an UNKNOWN error shall be returned."),
                new org.ccsds.moims.mo.mal.OperationField("serviceKey", true, org.ccsds.moims.mo.common.structures.ServiceKey.SHORT_FORM, "If the serviceKey field is not NULL then only the specified service of the provider shall be stored.\nWildcard values of '0' are not accepted in the serviceKey fields, an INVALID error shall be returned in this case.\nIf the serviceKey field is not NULL and the referenced service is not supported by the service provider an UNKNOWN error shall be returned.\nThe operation shall publish a ConfigurationStore event containing the selected configuration to be stored.\nThe service provider that implements the selected service shall, after the reception of the event, store its current Configuration in the COM Archive.\nOnce the relevant service provider has finished storing its configuration it shall publish a ConfigurationStored event with the stored configuration's ObjectId as its body.\nIf the request is for a hard-coded configuration then the relevant service provider must fail the store request by returning a NULL as a response."),
                new org.ccsds.moims.mo.mal.OperationField("autoAdd", true, org.ccsds.moims.mo.mal.structures.Attribute.BOOLEAN_SHORT_FORM, "If the autoAdd field is set to TRUE then, once the stored event has been published, and if it indicates success, the configuration service provider shall add the new configuration to the list of available configurations for the selected service provider. In effect call the add operation.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("objInstId", true, org.ccsds.moims.mo.com.structures.ObjectId.SHORT_FORM, "The response shall contain the object identifier of the new configuration object if successful or NULL if not.")}, 
            "The storeCurrent operation requests the creation of a new Configuration object containing the current Configuration of a specific Service or Provider configuration and stores the new Configuration object in the COM archive. Optionally the configuration can be added to the list of available configurations.\nThe actual service provider is responsible for the creation of the new Configuration objects in the COM archive as it is the provider that contains the configuration being stored.\nThis operation's expected use is for storing a configuration that has been modified \"on line\" in the service provider. For instance, a need to modify the thresholds of the parameter checks on ground can arise during operations. Before being \"officialised\", the new thresholds are usually implemented locally and tested. If the modifications are deemed correct then the configuration may be stored in the archive and becomes a new official configuration of the relevant service. This configuration can then be used by other providers of the same service via the add and activate operations.\nIf the implementation of the configuration service is not using a COM archive then an error is returned.\nThis operation can be used to request the store of both COM and non-COM configurations. It does not make sense to support hard-coded configurations as by definition they are fixed in nature.");

    /**
     * Operation number literal for operation IMPORTXML.
     */
    public static final int _IMPORTXML_OP_NUMBER = 8;

    /**
     * Operation number instance for operation IMPORTXML.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort IMPORTXML_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_IMPORTXML_OP_NUMBER);

    /**
     * Operation instance for operation IMPORTXML.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation IMPORTXML_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            IMPORTXML_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("importXML"), 
            new org.ccsds.moims.mo.mal.structures.UShort(6), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("xmlFile", true, org.ccsds.moims.mo.mal.structures.File.SHORT_FORM, "If the implementation of the configuration service is not using a COM archive then an UNSUPPORTED_OPERATION error shall be returned.\nThe supplied file contained in the xmlFile argument shall be read and converted to COM objects.\nIf there is a problem converting the XML then an INVALID error shall be returned.\nFor every object present within the XML file that does not exist in the COM Archive, the Configuration service shall create a new object with the same content and store the object in the COM Archive.\nIf the object already exists in the COM Archive, nothing shall be created.\nIf the object already exists in the COM Archive but contains a different content a DUPLICATE error shall be raised.\nThe newly generated Configuration object shall always reference existing objects in the Archive.\nThe newly generated Configuration object should be checked for consistency. An INVALID error shall be raised if the configuration is not valid.\nIf an error is raised then no objects shall be stored in the COM archive and operation shall end.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("objInstId", true, org.ccsds.moims.mo.com.structures.ObjectId.SHORT_FORM, "The return response shall contain in the objInstId field the object identifier of the new configuration object.")}, 
            "The importXML generates a new Configuration object from a XML File and stores the Configuration in the COM archive. Afterwards, the configuration can be added to the list of available configurations using the add operation.\nThe operation is only for importing XML configurations that use the standardised format defined by the Configuration service.\nIf the implementation of the configuration service is not using a COM archive then an error is returned.\nThe importXML can only be used to import COM based configurations.");

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] CONFIGURATION_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{ACTIVATE_OP,
        LIST_OP,
        GETCURRENT_OP,
        EXPORTXML_OP,
        ADD_OP,
        REMOVE_OP,
        STORECURRENT_OP,
        IMPORTXML_OP};

    /**
     * Literal for object SERVICECONFIGURATION.
     */
    @Deprecated
    public static final int _SERVICECONFIGURATION_OBJECT_NUMBER = 1;

    /**
     * Instance for object SERVICECONFIGURATION.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort SERVICECONFIGURATION_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_SERVICECONFIGURATION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier SERVICECONFIGURATION_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ServiceConfiguration");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType SERVICECONFIGURATION_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(3), CONFIGURATION_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), SERVICECONFIGURATION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject SERVICECONFIGURATION_OBJECT = new org.ccsds.moims.mo.com.COMObject(SERVICECONFIGURATION_OBJECT_TYPE, SERVICECONFIGURATION_OBJECT_NAME, org.ccsds.moims.mo.common.configuration.structures.ServiceConfigurationIdentifier.SHORT_FORM, true, org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.CONFIGURATIONOBJECTS_OBJECT_TYPE, true, null, false);

    /**
     * Literal for object PROVIDERCONFIGURATION.
     */
    @Deprecated
    public static final int _PROVIDERCONFIGURATION_OBJECT_NUMBER = 2;

    /**
     * Instance for object PROVIDERCONFIGURATION.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort PROVIDERCONFIGURATION_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_PROVIDERCONFIGURATION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier PROVIDERCONFIGURATION_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ProviderConfiguration");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType PROVIDERCONFIGURATION_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(3), CONFIGURATION_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), PROVIDERCONFIGURATION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject PROVIDERCONFIGURATION_OBJECT = new org.ccsds.moims.mo.com.COMObject(PROVIDERCONFIGURATION_OBJECT_TYPE, PROVIDERCONFIGURATION_OBJECT_NAME, org.ccsds.moims.mo.mal.structures.Attribute.IDENTIFIER_SHORT_FORM, true, org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.CONFIGURATIONOBJECTS_OBJECT_TYPE, true, null, false);

    /**
     * Literal for object CONFIGURATIONOBJECTS.
     */
    @Deprecated
    public static final int _CONFIGURATIONOBJECTS_OBJECT_NUMBER = 3;

    /**
     * Instance for object CONFIGURATIONOBJECTS.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort CONFIGURATIONOBJECTS_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_CONFIGURATIONOBJECTS_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier CONFIGURATIONOBJECTS_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ConfigurationObjects");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType CONFIGURATIONOBJECTS_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(3), CONFIGURATION_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), CONFIGURATIONOBJECTS_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject CONFIGURATIONOBJECTS_OBJECT = new org.ccsds.moims.mo.com.COMObject(CONFIGURATIONOBJECTS_OBJECT_TYPE, CONFIGURATIONOBJECTS_OBJECT_NAME, org.ccsds.moims.mo.common.configuration.structures.ConfigurationObjectDetails.SHORT_FORM, false, null, true, null, false);

    /**
     * Literal for object CONFIGURATIONFILE.
     */
    @Deprecated
    public static final int _CONFIGURATIONFILE_OBJECT_NUMBER = 4;

    /**
     * Instance for object CONFIGURATIONFILE.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort CONFIGURATIONFILE_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_CONFIGURATIONFILE_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier CONFIGURATIONFILE_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ConfigurationFile");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType CONFIGURATIONFILE_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(3), CONFIGURATION_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), CONFIGURATIONFILE_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject CONFIGURATIONFILE_OBJECT = new org.ccsds.moims.mo.com.COMObject(CONFIGURATIONFILE_OBJECT_TYPE, CONFIGURATIONFILE_OBJECT_NAME, org.ccsds.moims.mo.mal.structures.File.SHORT_FORM, false, null, true, null, false);

    /**
     * Literal for object PROVIDERCONFIGURATIONLINK.
     */
    @Deprecated
    public static final int _PROVIDERCONFIGURATIONLINK_OBJECT_NUMBER = 5;

    /**
     * Instance for object PROVIDERCONFIGURATIONLINK.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort PROVIDERCONFIGURATIONLINK_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_PROVIDERCONFIGURATIONLINK_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier PROVIDERCONFIGURATIONLINK_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ProviderConfigurationLink");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType PROVIDERCONFIGURATIONLINK_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(3), CONFIGURATION_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), PROVIDERCONFIGURATIONLINK_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject PROVIDERCONFIGURATIONLINK_OBJECT = new org.ccsds.moims.mo.com.COMObject(PROVIDERCONFIGURATIONLINK_OBJECT_TYPE, PROVIDERCONFIGURATIONLINK_OBJECT_NAME, null, true, org.ccsds.moims.mo.common.directory.DirectoryServiceInfo.SERVICEPROVIDER_OBJECT_TYPE, true, org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.PROVIDERCONFIGURATION_OBJECT_TYPE, false);

    /**
     * Literal for object CONFIGURATIONSWITCH.
     */
    @Deprecated
    public static final int _CONFIGURATIONSWITCH_OBJECT_NUMBER = 6;

    /**
     * Instance for object CONFIGURATIONSWITCH.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort CONFIGURATIONSWITCH_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_CONFIGURATIONSWITCH_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier CONFIGURATIONSWITCH_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ConfigurationSwitch");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType CONFIGURATIONSWITCH_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(3), CONFIGURATION_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), CONFIGURATIONSWITCH_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject CONFIGURATIONSWITCH_OBJECT = new org.ccsds.moims.mo.com.COMObject(CONFIGURATIONSWITCH_OBJECT_TYPE, CONFIGURATIONSWITCH_OBJECT_NAME, org.ccsds.moims.mo.com.structures.ObjectId.SHORT_FORM, true, org.ccsds.moims.mo.common.directory.DirectoryServiceInfo.SERVICEPROVIDER_OBJECT_TYPE, true, null, true);

    /**
     * Literal for object CONFIGURATIONSWITCHED.
     */
    @Deprecated
    public static final int _CONFIGURATIONSWITCHED_OBJECT_NUMBER = 7;

    /**
     * Instance for object CONFIGURATIONSWITCHED.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort CONFIGURATIONSWITCHED_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_CONFIGURATIONSWITCHED_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier CONFIGURATIONSWITCHED_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ConfigurationSwitched");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType CONFIGURATIONSWITCHED_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(3), CONFIGURATION_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), CONFIGURATIONSWITCHED_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject CONFIGURATIONSWITCHED_OBJECT = new org.ccsds.moims.mo.com.COMObject(CONFIGURATIONSWITCHED_OBJECT_TYPE, CONFIGURATIONSWITCHED_OBJECT_NAME, org.ccsds.moims.mo.mal.structures.Attribute.BOOLEAN_SHORT_FORM, true, org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.CONFIGURATIONSWITCH_OBJECT_TYPE, true, null, true);

    /**
     * Literal for object CONFIGURATIONSTORE.
     */
    @Deprecated
    public static final int _CONFIGURATIONSTORE_OBJECT_NUMBER = 8;

    /**
     * Instance for object CONFIGURATIONSTORE.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort CONFIGURATIONSTORE_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_CONFIGURATIONSTORE_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier CONFIGURATIONSTORE_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ConfigurationStore");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType CONFIGURATIONSTORE_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(3), CONFIGURATION_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), CONFIGURATIONSTORE_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject CONFIGURATIONSTORE_OBJECT = new org.ccsds.moims.mo.com.COMObject(CONFIGURATIONSTORE_OBJECT_TYPE, CONFIGURATIONSTORE_OBJECT_NAME, org.ccsds.moims.mo.common.structures.ServiceKey.SHORT_FORM, true, org.ccsds.moims.mo.common.directory.DirectoryServiceInfo.SERVICEPROVIDER_OBJECT_TYPE, true, null, true);

    /**
     * Literal for object CONFIGURATIONSTORED.
     */
    @Deprecated
    public static final int _CONFIGURATIONSTORED_OBJECT_NUMBER = 9;

    /**
     * Instance for object CONFIGURATIONSTORED.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort CONFIGURATIONSTORED_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_CONFIGURATIONSTORED_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier CONFIGURATIONSTORED_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ConfigurationStored");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType CONFIGURATIONSTORED_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(3), CONFIGURATION_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), CONFIGURATIONSTORED_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject CONFIGURATIONSTORED_OBJECT = new org.ccsds.moims.mo.com.COMObject(CONFIGURATIONSTORED_OBJECT_TYPE, CONFIGURATIONSTORED_OBJECT_NAME, org.ccsds.moims.mo.com.structures.ObjectId.SHORT_FORM, true, org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.CONFIGURATIONSTORE_OBJECT_TYPE, false, null, true);

    /**
     * Object instance.
     */
    public static final org.ccsds.moims.mo.com.COMObject[] COM_OBJECTS = {
        SERVICECONFIGURATION_OBJECT,
        PROVIDERCONFIGURATION_OBJECT,
        CONFIGURATIONOBJECTS_OBJECT,
        CONFIGURATIONFILE_OBJECT,
        PROVIDERCONFIGURATIONLINK_OBJECT,
        CONFIGURATIONSWITCH_OBJECT,
        CONFIGURATIONSWITCHED_OBJECT,
        CONFIGURATIONSTORE_OBJECT,
        CONFIGURATIONSTORED_OBJECT,};

    /**
     * Creates an instance of the Configuration ServiceInfo.
     * 
     */
    public ConfigurationServiceInfo() {
        super(SERVICE_KEY, CONFIGURATION_SERVICE_NAME, CONFIGURATION_SERVICE_ELEMENTS, OPERATIONS, COM_OBJECTS);
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
