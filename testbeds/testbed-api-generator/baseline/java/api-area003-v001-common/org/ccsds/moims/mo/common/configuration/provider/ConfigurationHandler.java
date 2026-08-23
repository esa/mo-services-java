package org.ccsds.moims.mo.common.configuration.provider;

/**
 * Interface that providers of the Configuration service must implement to
 * handle the operations of that service.
 */
public interface ConfigurationHandler {

    /**
     * Implements the operation activate.
     * 
     * @param serviceProvider The serviceProvider field shall contain the domain and object instance identifier of the ServiceProvider COM object being (re)configured.
If the service provider referenced by the serviceProvider field is not known an UNKNOWN error shall be returned.
     * @param configObjId The configObjId field shall hold the COM object identifier that identifies the configuration to activate.
The configObjId field shall reference either a ProviderConfiguration or ServiceConfiguration object.
An UNKNOWN error shall be returned if the object instance identifier held in the configObjId field does not match an existing configuration.
An INVALID error shall be returned if the object instance identifier held in the configObjId field does not reference either a ProviderConfiguration or ServiceConfiguration object.
If the object instance identifier held in the configObjId field does not reference a valid configuration for the service provider an INVALID error shall be returned. A valid configuration is one that is returned from the list operation for the matched service provider.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.UnknownException Requested configuration is unknown.
     * @throws org.ccsds.moims.mo.com.InvalidException Requested configuration is invalid.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void activate(org.ccsds.moims.mo.com.structures.ObjectKey serviceProvider,
            org.ccsds.moims.mo.com.structures.ObjectId configObjId,
            org.ccsds.moims.mo.common.configuration.provider.ActivateInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation list.
     * 
     * @param configurationType The configurationType argument shall hold the type of configuration to be listed.
     * @param domain The domain request argument shall contain the domain of the configuration objects to return.
The domain field supports the wildcard value of '*' only in the last part of the domain, otherwise an INVALID error shall be returned. See section 3.5.6.5.g in R[2].
     * @param serviceKey If the request configuration type is SERVICE, then an optional filter may be supplied in the serviceKey field where the ServiceKey composite holds the service area, service, and version values to match on.
The filter shall be applied with a logical AND for each field of the service key.
Wildcard values of '0' are not accepted in the serviceKey fields, an INVALID error shall be returned in this case.
For other types of configuration the serviceKey field shall be ignored and may be set to NULL in the request.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.com.InvalidException Requested configuration is not a valid configuration object type or wildcard values were specified in the service key filter.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.com.structures.ObjectIdList list(org.ccsds.moims.mo.common.configuration.structures.ConfigurationType configurationType,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.common.structures.ServiceKey serviceKey,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation getCurrent.
     * 
     * @param serviceProvider The serviceProvider field shall contain the domain and object instance identifier of the ServiceProvider COM object being queried.
     * @param serviceKey If the serviceKey field is not NULL, then the operation shall return the configuration of the selected service, as specified in the field.
For retrieval of the provider level configuration the serviceKey field shall be set to NULL in the request.
An UNKNOWN error shall be returned if the combination of service provider and service filter fields don't match an existing service provider, service key or configuration.
No wildcards are supported, an INVALID error must be returned in this case.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.com.InvalidException The request contained one or more wildcards.
     * @throws org.ccsds.moims.mo.mal.UnknownException Requested service provider and service key combination is unknown.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.com.structures.ObjectIdList getCurrent(org.ccsds.moims.mo.com.structures.ObjectKey serviceProvider,
            org.ccsds.moims.mo.common.structures.ServiceKey serviceKey,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation exportXML.
     * 
     * @param confObjId If the implementation of the configuration service is not using a COM archive then an UNSUPPORTED_OPERATION error shall be returned.
The confObjId argument shall contain the type, domain and COM object instance identifier of the configuration object to return the XML representation of.
An UNKNOWN error shall be returned if the confObjId field does not match an existing COM object.
An INVALID error shall be returned if the confObjId does not refer to either a ProviderConfiguration or a ServiceConfiguration object.
An INVALID error shall be returned if the confObjId refers to either a hard-coded or a non-COM configuration.
     * @param returnComplete The returnComplete Boolean shall be set to True if the returned XML is to be in the complete standardised format, otherwise it will be in the compact standardised format.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.com.InvalidException Requested configuration is not a valid configuration object type.
     * @throws org.ccsds.moims.mo.mal.UnknownException Requested object is unknown.
     * @throws org.ccsds.moims.mo.mal.UnsupportedOperationException The operation requires the use of a COM archive
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.File exportXML(org.ccsds.moims.mo.com.structures.ObjectId confObjId,
            Boolean returnComplete,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.UnsupportedOperationException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation add.
     * 
     * @param serviceProvider If the implementation of the configuration service is not using a COM archive then an UNSUPPORTED_OPERATION error shall be returned.
The first argument shall contain the domain and object identifier of the ServiceProvider COM object which the configurations are being added to.
     * @param configObjIds The second argument shall contain a list of service and/or provider configurations to add to the list of configurations available for the specific service provider.
If either the service provider or the configuration objects are unknown then an UNKNOWN error shall be returned.
If any of the supplied configuration objects are not provider or service configuration objects then an INVALID error shall be returned.
If an error is raised then no new configurations shall be added as a result of this operation call.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.com.InvalidException One of the supplied configuration object instance identifiers is either not a Service or a Provider configuration object.
     * @throws org.ccsds.moims.mo.mal.UnsupportedOperationException The operation requires the use of a COM archive
     * @throws org.ccsds.moims.mo.mal.UnknownException One of the supplied service or configuration object instance identifiers is unknown.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void add(org.ccsds.moims.mo.com.structures.ObjectKey serviceProvider,
            org.ccsds.moims.mo.com.structures.ObjectIdList configObjIds,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.UnsupportedOperationException, org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation remove.
     * 
     * @param serviceProvider If the implementation of the configuration service is not using a COM archive then an UNSUPPORTED_OPERATION error shall be returned.
The first argument shall contain the domain and object identifier of the ServiceProvider COM object which the configurations are being removed from.
     * @param configObjIds The second argument shall contain a list of service and/or provider configurations to remove from the list of configurations available for the specific service provider.
If either the service provider or a provided object identifier does not match an existing configuration object then this operation shall fail with an UNKNOWN error.
If any of the supplied configuration objects are not provider or service configuration objects then an INVALID error shall be returned.
If an error is raised then no configurations shall be removed as a result of this operation call.
Matched configuration objects shall not be removed from the COM archive only the list of configuration objects in the provider.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.UnknownException Either the service provider or one of the supplied configuration object instance identifiers is unknown.
     * @throws org.ccsds.moims.mo.com.InvalidException One of the supplied configuration object instance identifiers is either not a Service or a Provider configuration object.
     * @throws org.ccsds.moims.mo.mal.UnsupportedOperationException The operation requires the use of a COM archive
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void remove(org.ccsds.moims.mo.com.structures.ObjectKey serviceProvider,
            org.ccsds.moims.mo.com.structures.ObjectIdList configObjIds,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.UnsupportedOperationException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation storeCurrent.
     * 
     * @param serviceProvider If the implementation of the configuration service is not using a COM archive then an UNSUPPORTED_OPERATION error shall be returned.
The serviceProvider field shall contain the domain and object instance identifier of the ServiceProvider COM object that must store its current configuration.
If the service provider is not known an UNKNOWN error shall be returned.
     * @param serviceKey If the serviceKey field is not NULL then only the specified service of the provider shall be stored.
Wildcard values of '0' are not accepted in the serviceKey fields, an INVALID error shall be returned in this case.
If the serviceKey field is not NULL and the referenced service is not supported by the service provider an UNKNOWN error shall be returned.
The operation shall publish a ConfigurationStore event containing the selected configuration to be stored.
The service provider that implements the selected service shall, after the reception of the event, store its current Configuration in the COM Archive.
Once the relevant service provider has finished storing its configuration it shall publish a ConfigurationStored event with the stored configuration's ObjectId as its body.
If the request is for a hard-coded configuration then the relevant service provider must fail the store request by returning a NULL as a response.
     * @param autoAdd If the autoAdd field is set to TRUE then, once the stored event has been published, and if it indicates success, the configuration service provider shall add the new configuration to the list of available configurations for the selected service provider. In effect call the add operation.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.com.InvalidException Not a valid Service or Provider object
     * @throws org.ccsds.moims.mo.mal.UnsupportedOperationException The operation requires the use of a COM archive
     * @throws org.ccsds.moims.mo.mal.UnknownException The service provider referenced is not known or the referenced service is not known by the provider.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void storeCurrent(org.ccsds.moims.mo.com.structures.ObjectKey serviceProvider,
            org.ccsds.moims.mo.common.structures.ServiceKey serviceKey,
            Boolean autoAdd,
            org.ccsds.moims.mo.common.configuration.provider.StoreCurrentInteraction interaction) throws org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.UnsupportedOperationException, org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation importXML.
     * 
     * @param xmlFile If the implementation of the configuration service is not using a COM archive then an UNSUPPORTED_OPERATION error shall be returned.
The supplied file contained in the xmlFile argument shall be read and converted to COM objects.
If there is a problem converting the XML then an INVALID error shall be returned.
For every object present within the XML file that does not exist in the COM Archive, the Configuration service shall create a new object with the same content and store the object in the COM Archive.
If the object already exists in the COM Archive, nothing shall be created.
If the object already exists in the COM Archive but contains a different content a DUPLICATE error shall be raised.
The newly generated Configuration object shall always reference existing objects in the Archive.
The newly generated Configuration object should be checked for consistency. An INVALID error shall be raised if the configuration is not valid.
If an error is raised then no objects shall be stored in the COM archive and operation shall end.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.UnsupportedOperationException The operation requires the use of a COM archive
     * @throws org.ccsds.moims.mo.com.DuplicateException The supplied XML contains a duplicate object definition that is different to the one held in the COM Archive.
     * @throws org.ccsds.moims.mo.com.InvalidException The supplied XML was not valid.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.com.structures.ObjectId importXML(org.ccsds.moims.mo.mal.structures.File xmlFile,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnsupportedOperationException, org.ccsds.moims.mo.com.DuplicateException, org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.common.configuration.provider.ConfigurationSkeleton skeleton);
}
