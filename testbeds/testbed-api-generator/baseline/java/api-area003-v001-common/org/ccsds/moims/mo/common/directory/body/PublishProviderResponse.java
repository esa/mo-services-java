package org.ccsds.moims.mo.common.directory.body;

/**
 * Multi body return class for PublishProviderResponse.
 */
public final class PublishProviderResponse {

    /**
     * providerObjId: If the providerId field of the PublishDetails structure
     * matches an existing ServiceProvider COM object, the operation shall update
     * the existing details of that provider.
     * If the providerId field of the PublishDetails structure does not match
     * an existing ServiceProvider COM object, then the operation shall create
     * a new ServiceProvider COM object to represent the new service provider.
     * A new ProviderCapabilities COM object shall be created to store the capabilities
     * of the provider.
     * The created objects should be stored in the COM archive by the directory
     * service provider.
     * The operation shall return the COM object instance identifiers of the ServiceProvider
     * and ProviderCapabilities COM objects representing the provider.
     */
    private Long providerObjId;

    /**
     * capabilitiesObjId: capabilitiesObjId Argument number 1 as defined by the
     * service operation.
     */
    private Long capabilitiesObjId;

    /**
     * Default constructor for PublishProviderResponse.
     * 
     */
    public PublishProviderResponse() {
    }

    /**
     * Constructs an instance of this type using provided values.
     * 
     * @param providerObjId If the providerId field of the PublishDetails structure matches an existing ServiceProvider COM object, the operation shall update the existing details of that provider.
If the providerId field of the PublishDetails structure does not match an existing ServiceProvider COM object, then the operation shall create a new ServiceProvider COM object to represent the new service provider.
A new ProviderCapabilities COM object shall be created to store the capabilities of the provider.
The created objects should be stored in the COM archive by the directory service provider.
The operation shall return the COM object instance identifiers of the ServiceProvider and ProviderCapabilities COM objects representing the provider.
     * @param capabilitiesObjId capabilitiesObjId Argument number 1 as defined by the service operation
     */
    public PublishProviderResponse(Long providerObjId,
            Long capabilitiesObjId) {
        this.providerObjId = providerObjId;
        this.capabilitiesObjId = capabilitiesObjId;
    }

    /**
     * Returns the field providerObjId.
     * 
     * @return The field providerObjId
     */
    public Long getProviderObjId() {
        return providerObjId;
    }

    /**
     * Returns the field capabilitiesObjId.
     * 
     * @return The field capabilitiesObjId
     */
    public Long getCapabilitiesObjId() {
        return capabilitiesObjId;
    }

}
