package org.ccsds.moims.mo.common.configuration.consumer;

/**
 * Consumer stub for Configuration service.
 */
public class ConfigurationStub {

    /**
     * The consumer field.
     */
    private final org.ccsds.moims.mo.mal.consumer.MALConsumer consumer;

    /**
     * Wraps a MALconsumer connection with service specific methods that map from
     * the high level service API to the generic MAL API.
     * 
     * @param consumer consumer The MALConsumer to use in this stub.
     */
    public ConfigurationStub(org.ccsds.moims.mo.mal.consumer.MALConsumer consumer) {
        this.consumer = consumer;
    }

    /**
     * Returns the internal MAL consumer object used for sending of messages from
     * this interface.
     * 
     * @return The MAL consumer object.
     */
    public org.ccsds.moims.mo.mal.consumer.MALConsumer getConsumer() {
        return consumer;
    }

    /**
     * The activate operation instructs a service provider to make a specific
     * configuration active. The operation returns once the new configuration
     * is active or when the activation attempt has failed for some reason.
     * The requested configuration must have either already been added to the
     * configuration service provider using the add operation or alternatively
     * known to the configuration service by another implementation specific mechanism.
     * NOTE - The service is reconfigured for all service consumers not just the
     * calling consumer.
     * 
     * @param serviceProvider The serviceProvider field shall contain the domain and object instance identifier of the ServiceProvider COM object being (re)configured.
If the service provider referenced by the serviceProvider field is not known an UNKNOWN error shall be returned.
     * @param configObjId The configObjId field shall hold the COM object identifier that identifies the configuration to activate.
The configObjId field shall reference either a ProviderConfiguration or ServiceConfiguration object.
An UNKNOWN error shall be returned if the object instance identifier held in the configObjId field does not match an existing configuration.
An INVALID error shall be returned if the object instance identifier held in the configObjId field does not reference either a ProviderConfiguration or ServiceConfiguration object.
If the object instance identifier held in the configObjId field does not reference a valid configuration for the service provider an INVALID error shall be returned. A valid configuration is one that is returned from the list operation for the matched service provider.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void activate(org.ccsds.moims.mo.com.structures.ObjectKey serviceProvider,
            org.ccsds.moims.mo.com.structures.ObjectId configObjId,
            org.ccsds.moims.mo.common.configuration.consumer.ConfigurationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.invoke(org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.ACTIVATE_OP, adapter, serviceProvider, configObjId);
    }

    /**
     * Asynchronous version of method activate.
     * 
     * @param serviceProvider The serviceProvider field shall contain the domain and object instance identifier of the ServiceProvider COM object being (re)configured.
If the service provider referenced by the serviceProvider field is not known an UNKNOWN error shall be returned.
     * @param configObjId The configObjId field shall hold the COM object identifier that identifies the configuration to activate.
The configObjId field shall reference either a ProviderConfiguration or ServiceConfiguration object.
An UNKNOWN error shall be returned if the object instance identifier held in the configObjId field does not match an existing configuration.
An INVALID error shall be returned if the object instance identifier held in the configObjId field does not reference either a ProviderConfiguration or ServiceConfiguration object.
If the object instance identifier held in the configObjId field does not reference a valid configuration for the service provider an INVALID error shall be returned. A valid configuration is one that is returned from the list operation for the matched service provider.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncActivate(org.ccsds.moims.mo.com.structures.ObjectKey serviceProvider,
            org.ccsds.moims.mo.com.structures.ObjectId configObjId,
            org.ccsds.moims.mo.common.configuration.consumer.ConfigurationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncInvoke(org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.ACTIVATE_OP, adapter, serviceProvider, configObjId);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueActivate(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.common.configuration.consumer.ConfigurationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.ACTIVATE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The list operation returns list of configurations known to the configuration
     * service provider for a certain configuration type in a specific domain.
     * To appear in the response from the list operation a configuration must
     * have either been added to the configuration service provider using the
     * add operation or alternatively known to the configuration service by another
     * implementation specific mechanism.
     * 
     * @param configurationType The configurationType argument shall hold the type of configuration to be listed.
     * @param domain The domain request argument shall contain the domain of the configuration objects to return.
The domain field supports the wildcard value of '*' only in the last part of the domain, otherwise an INVALID error shall be returned. See section 3.5.6.5.g in R[2].
     * @param serviceKey If the request configuration type is SERVICE, then an optional filter may be supplied in the serviceKey field where the ServiceKey composite holds the service area, service, and version values to match on.
The filter shall be applied with a logical AND for each field of the service key.
Wildcard values of '0' are not accepted in the serviceKey fields, an INVALID error shall be returned in this case.
For other types of configuration the serviceKey field shall be ignored and may be set to NULL in the request.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.com.structures.ObjectIdList list(org.ccsds.moims.mo.common.configuration.structures.ConfigurationType configurationType,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.common.structures.ServiceKey serviceKey) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.LIST_OP, configurationType, domain, serviceKey);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.com.structures.ObjectIdList());
        return (org.ccsds.moims.mo.com.structures.ObjectIdList) body0;
    }

    /**
     * Asynchronous version of method list.
     * 
     * @param configurationType The configurationType argument shall hold the type of configuration to be listed.
     * @param domain The domain request argument shall contain the domain of the configuration objects to return.
The domain field supports the wildcard value of '*' only in the last part of the domain, otherwise an INVALID error shall be returned. See section 3.5.6.5.g in R[2].
     * @param serviceKey If the request configuration type is SERVICE, then an optional filter may be supplied in the serviceKey field where the ServiceKey composite holds the service area, service, and version values to match on.
The filter shall be applied with a logical AND for each field of the service key.
Wildcard values of '0' are not accepted in the serviceKey fields, an INVALID error shall be returned in this case.
For other types of configuration the serviceKey field shall be ignored and may be set to NULL in the request.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncList(org.ccsds.moims.mo.common.configuration.structures.ConfigurationType configurationType,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.common.structures.ServiceKey serviceKey,
            org.ccsds.moims.mo.common.configuration.consumer.ConfigurationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.LIST_OP, adapter, configurationType, domain, serviceKey);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueList(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.common.configuration.consumer.ConfigurationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.LIST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The getCurrent operation returns the currently selected configuration of
     * a service provider, either of the complete provider (ProviderConfiguration)
     * or a specific service (ServiceConfiguration) of that provider, as far as
     * the Configuration Service provider is concerned.
     * This means that if the provider of a specific service has modified their
     * configuration by some other means, and this has not been stored using the
     * storeCurrent operation, then this operation will return the unmodified
     * configuration.
     * If a provider level configuration is required by this operation, and service
     * configurations different to the ones in the provider configuration have
     * been activated by the activate operation, then this operation will return
     * the ProviderConfiguration followed by the list of modified ServiceConfigurations.
     * The current configuration of a service provider is most likely the configuration
     * that was last activated using the activate operation, however implementations
     * of the configuration service may have other means (outside the scope of
     * this service) of selecting the current configuration of a service provider.
     * The operation can also be used to determine the initial configuration of
     * a service provider before it has been activated, it is therefore a useful
     * operation to call by a service provider during start-up to determine its
     * initial configuration. If this is the case a ConfigurationSwitched event
     * should be published.
     * 
     * @param serviceProvider The serviceProvider field shall contain the domain and object instance identifier of the ServiceProvider COM object being queried.
     * @param serviceKey If the serviceKey field is not NULL, then the operation shall return the configuration of the selected service, as specified in the field.
For retrieval of the provider level configuration the serviceKey field shall be set to NULL in the request.
An UNKNOWN error shall be returned if the combination of service provider and service filter fields don't match an existing service provider, service key or configuration.
No wildcards are supported, an INVALID error must be returned in this case.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.com.structures.ObjectIdList getCurrent(org.ccsds.moims.mo.com.structures.ObjectKey serviceProvider,
            org.ccsds.moims.mo.common.structures.ServiceKey serviceKey) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.GETCURRENT_OP, serviceProvider, serviceKey);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.com.structures.ObjectIdList());
        return (org.ccsds.moims.mo.com.structures.ObjectIdList) body0;
    }

    /**
     * Asynchronous version of method getCurrent.
     * 
     * @param serviceProvider The serviceProvider field shall contain the domain and object instance identifier of the ServiceProvider COM object being queried.
     * @param serviceKey If the serviceKey field is not NULL, then the operation shall return the configuration of the selected service, as specified in the field.
For retrieval of the provider level configuration the serviceKey field shall be set to NULL in the request.
An UNKNOWN error shall be returned if the combination of service provider and service filter fields don't match an existing service provider, service key or configuration.
No wildcards are supported, an INVALID error must be returned in this case.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncGetCurrent(org.ccsds.moims.mo.com.structures.ObjectKey serviceProvider,
            org.ccsds.moims.mo.common.structures.ServiceKey serviceKey,
            org.ccsds.moims.mo.common.configuration.consumer.ConfigurationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.GETCURRENT_OP, adapter, serviceProvider, serviceKey);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueGetCurrent(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.common.configuration.consumer.ConfigurationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.GETCURRENT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The exportXML operation returns the actual Configuration information in
     * the XML format from the configuration object stored in the Archive.
     * The returned XML is in the standardised format and one of two levels of
     * detail, compact or complete, can be selected. Compact contains just the
     * COM object instance identifiers with the respective domains and object
     * types whereas complete augments the compact with the additional set of
     * values inside the respective service objects.
     * The XML standardised format is the XML representation of the referenced
     * COM objects as defined by the XML encoding given in R[4]. Example XML documents
     * can be found in an annex at the end of this specification.
     * If the implementation of the configuration service is not using a COM archive
     * then an error is returned.
     * It should be noted that this operation only supports COM configurations.
     * 
     * @param confObjId If the implementation of the configuration service is not using a COM archive then an UNSUPPORTED_OPERATION error shall be returned.
The confObjId argument shall contain the type, domain and COM object instance identifier of the configuration object to return the XML representation of.
An UNKNOWN error shall be returned if the confObjId field does not match an existing COM object.
An INVALID error shall be returned if the confObjId does not refer to either a ProviderConfiguration or a ServiceConfiguration object.
An INVALID error shall be returned if the confObjId refers to either a hard-coded or a non-COM configuration.
     * @param returnComplete The returnComplete Boolean shall be set to True if the returned XML is to be in the complete standardised format, otherwise it will be in the compact standardised format.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.structures.File exportXML(org.ccsds.moims.mo.com.structures.ObjectId confObjId,
            Boolean returnComplete) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.EXPORTXML_OP, confObjId, (returnComplete == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(returnComplete));
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.File());
        return (org.ccsds.moims.mo.mal.structures.File) body0;
    }

    /**
     * Asynchronous version of method exportXML.
     * 
     * @param confObjId If the implementation of the configuration service is not using a COM archive then an UNSUPPORTED_OPERATION error shall be returned.
The confObjId argument shall contain the type, domain and COM object instance identifier of the configuration object to return the XML representation of.
An UNKNOWN error shall be returned if the confObjId field does not match an existing COM object.
An INVALID error shall be returned if the confObjId does not refer to either a ProviderConfiguration or a ServiceConfiguration object.
An INVALID error shall be returned if the confObjId refers to either a hard-coded or a non-COM configuration.
     * @param returnComplete The returnComplete Boolean shall be set to True if the returned XML is to be in the complete standardised format, otherwise it will be in the compact standardised format.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncExportXML(org.ccsds.moims.mo.com.structures.ObjectId confObjId,
            Boolean returnComplete,
            org.ccsds.moims.mo.common.configuration.consumer.ConfigurationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.EXPORTXML_OP, adapter, confObjId, (returnComplete == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(returnComplete));
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueExportXML(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.common.configuration.consumer.ConfigurationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.EXPORTXML_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The add operation makes a new Configuration available on the Configuration
     * Service. The Configuration must already exist in the COM archive to be
     * added to the Configuration Service.
     * If the implementation of the configuration service is not using a COM archive
     * then an error is returned.
     * This operation can be used to add COM, non-COM and hard-coded configurations.
     * 
     * @param serviceProvider If the implementation of the configuration service is not using a COM archive then an UNSUPPORTED_OPERATION error shall be returned.
The first argument shall contain the domain and object identifier of the ServiceProvider COM object which the configurations are being added to.
     * @param configObjIds The second argument shall contain a list of service and/or provider configurations to add to the list of configurations available for the specific service provider.
If either the service provider or the configuration objects are unknown then an UNKNOWN error shall be returned.
If any of the supplied configuration objects are not provider or service configuration objects then an INVALID error shall be returned.
If an error is raised then no new configurations shall be added as a result of this operation call.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void add(org.ccsds.moims.mo.com.structures.ObjectKey serviceProvider,
            org.ccsds.moims.mo.com.structures.ObjectIdList configObjIds) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.ADD_OP, serviceProvider, configObjIds);
    }

    /**
     * Asynchronous version of method add.
     * 
     * @param serviceProvider If the implementation of the configuration service is not using a COM archive then an UNSUPPORTED_OPERATION error shall be returned.
The first argument shall contain the domain and object identifier of the ServiceProvider COM object which the configurations are being added to.
     * @param configObjIds The second argument shall contain a list of service and/or provider configurations to add to the list of configurations available for the specific service provider.
If either the service provider or the configuration objects are unknown then an UNKNOWN error shall be returned.
If any of the supplied configuration objects are not provider or service configuration objects then an INVALID error shall be returned.
If an error is raised then no new configurations shall be added as a result of this operation call.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncAdd(org.ccsds.moims.mo.com.structures.ObjectKey serviceProvider,
            org.ccsds.moims.mo.com.structures.ObjectIdList configObjIds,
            org.ccsds.moims.mo.common.configuration.consumer.ConfigurationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.ADD_OP, adapter, serviceProvider, configObjIds);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueAdd(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.common.configuration.consumer.ConfigurationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.ADD_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The remove operation, removes a provider configuration from the list of
     * configurations available for that provider in the configuration service.
     * The operation does not remove the configuration objects from the COM archive,
     * merely removes the objects from the configuration service provider.
     * If the implementation of the configuration service is not using a COM archive
     * then an error is returned.
     * This operation can be used to remove COM, non-COM, and hard-coded configurations
     * from a provider.
     * 
     * @param serviceProvider If the implementation of the configuration service is not using a COM archive then an UNSUPPORTED_OPERATION error shall be returned.
The first argument shall contain the domain and object identifier of the ServiceProvider COM object which the configurations are being removed from.
     * @param configObjIds The second argument shall contain a list of service and/or provider configurations to remove from the list of configurations available for the specific service provider.
If either the service provider or a provided object identifier does not match an existing configuration object then this operation shall fail with an UNKNOWN error.
If any of the supplied configuration objects are not provider or service configuration objects then an INVALID error shall be returned.
If an error is raised then no configurations shall be removed as a result of this operation call.
Matched configuration objects shall not be removed from the COM archive only the list of configuration objects in the provider.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void remove(org.ccsds.moims.mo.com.structures.ObjectKey serviceProvider,
            org.ccsds.moims.mo.com.structures.ObjectIdList configObjIds) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.REMOVE_OP, serviceProvider, configObjIds);
    }

    /**
     * Asynchronous version of method remove.
     * 
     * @param serviceProvider If the implementation of the configuration service is not using a COM archive then an UNSUPPORTED_OPERATION error shall be returned.
The first argument shall contain the domain and object identifier of the ServiceProvider COM object which the configurations are being removed from.
     * @param configObjIds The second argument shall contain a list of service and/or provider configurations to remove from the list of configurations available for the specific service provider.
If either the service provider or a provided object identifier does not match an existing configuration object then this operation shall fail with an UNKNOWN error.
If any of the supplied configuration objects are not provider or service configuration objects then an INVALID error shall be returned.
If an error is raised then no configurations shall be removed as a result of this operation call.
Matched configuration objects shall not be removed from the COM archive only the list of configuration objects in the provider.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncRemove(org.ccsds.moims.mo.com.structures.ObjectKey serviceProvider,
            org.ccsds.moims.mo.com.structures.ObjectIdList configObjIds,
            org.ccsds.moims.mo.common.configuration.consumer.ConfigurationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.REMOVE_OP, adapter, serviceProvider, configObjIds);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueRemove(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.common.configuration.consumer.ConfigurationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.REMOVE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The storeCurrent operation requests the creation of a new Configuration
     * object containing the current Configuration of a specific Service or Provider
     * configuration and stores the new Configuration object in the COM archive.
     * Optionally the configuration can be added to the list of available configurations.
     * The actual service provider is responsible for the creation of the new
     * Configuration objects in the COM archive as it is the provider that contains
     * the configuration being stored.
     * This operation&quot;s expected use is for storing a configuration that
     * has been modified "on line" in the service provider. For instance, a need
     * to modify the thresholds of the parameter checks on ground can arise during
     * operations. Before being "officialised", the new thresholds are usually
     * implemented locally and tested. If the modifications are deemed correct
     * then the configuration may be stored in the archive and becomes a new official
     * configuration of the relevant service. This configuration can then be used
     * by other providers of the same service via the add and activate operations.
     * If the implementation of the configuration service is not using a COM archive
     * then an error is returned.
     * This operation can be used to request the store of both COM and non-COM
     * configurations. It does not make sense to support hard-coded configurations
     * as by definition they are fixed in nature.
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
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void storeCurrent(org.ccsds.moims.mo.com.structures.ObjectKey serviceProvider,
            org.ccsds.moims.mo.common.structures.ServiceKey serviceKey,
            Boolean autoAdd,
            org.ccsds.moims.mo.common.configuration.consumer.ConfigurationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.invoke(org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.STORECURRENT_OP, adapter, serviceProvider, serviceKey, (autoAdd == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(autoAdd));
    }

    /**
     * Asynchronous version of method storeCurrent.
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
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncStoreCurrent(org.ccsds.moims.mo.com.structures.ObjectKey serviceProvider,
            org.ccsds.moims.mo.common.structures.ServiceKey serviceKey,
            Boolean autoAdd,
            org.ccsds.moims.mo.common.configuration.consumer.ConfigurationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncInvoke(org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.STORECURRENT_OP, adapter, serviceProvider, serviceKey, (autoAdd == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(autoAdd));
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueStoreCurrent(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.common.configuration.consumer.ConfigurationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.STORECURRENT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The importXML generates a new Configuration object from a XML File and
     * stores the Configuration in the COM archive. Afterwards, the configuration
     * can be added to the list of available configurations using the add operation.
     * The operation is only for importing XML configurations that use the standardised
     * format defined by the Configuration service.
     * If the implementation of the configuration service is not using a COM archive
     * then an error is returned.
     * The importXML can only be used to import COM based configurations.
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
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.com.structures.ObjectId importXML(org.ccsds.moims.mo.mal.structures.File xmlFile) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.IMPORTXML_OP, xmlFile);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.com.structures.ObjectId());
        return (org.ccsds.moims.mo.com.structures.ObjectId) body0;
    }

    /**
     * Asynchronous version of method importXML.
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
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncImportXML(org.ccsds.moims.mo.mal.structures.File xmlFile,
            org.ccsds.moims.mo.common.configuration.consumer.ConfigurationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.IMPORTXML_OP, adapter, xmlFile);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueImportXML(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.common.configuration.consumer.ConfigurationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo.IMPORTXML_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

}
