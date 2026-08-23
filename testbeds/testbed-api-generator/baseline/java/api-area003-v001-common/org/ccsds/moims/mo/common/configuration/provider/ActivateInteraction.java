package org.ccsds.moims.mo.common.configuration.provider;

/**
 * Provider INVOKE interaction class for Configuration::activate operation.
 */
public class ActivateInteraction {

    /**
     * The interaction field.
     */
    private org.ccsds.moims.mo.mal.provider.MALInvoke interaction;

    /**
     * Wraps the provided MAL interaction object with methods for sending responses
     * to an INVOKE interaction from a provider.
     * 
     * @param interaction The MAL interaction action object to use.
     */
    public ActivateInteraction(org.ccsds.moims.mo.mal.provider.MALInvoke interaction) {
        this.interaction = interaction;
    }

    /**
     * Returns the MAL interaction object used for returning messages from the
     * provider.
     * 
     * @return The MAL interaction object provided in the constructor
     */
    public org.ccsds.moims.mo.mal.provider.MALInvoke getInteraction() {
        return interaction;
    }

    /**
     * Sends a INVOKE acknowledge to the consumer.
     * 
     * @return Returns the MAL message created by the acknowledge
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage sendAcknowledgement() throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return interaction.sendAcknowledgement((Object[]) null);
    }

    /**
     * Sends a INVOKE response to the consumer.
     * 
     * @param activationResult The service provider that implements the selected service shall, after the reception of the ConfigurationSwitch event, reconfigure itself and publish a ConfigurationSwitched COM event.
If the operation fails the previous configuration shall remain active.
In the case of a provider configuration, where multiple service configurations are being switched, the provider must switch all configurations successfully or roll back to the previous configuration. No partial reconfiguration is supported.
If a provider level configuration is successful, and a COM archive is being used, then the service provider that implements the selected service shall store in the COM archive a new ProviderConfigurationLink COM object that links its ServiceProvider object to the new activated ProviderConfiguration COM object.
The response message shall be sent when the configuration is either made active or fails.
If the activation was successful then the activationResult field shall be set to TRUE, otherwise FALSE for failure.
     * @param previousConfig The previousConfig field shall point to the previously active configuration or NULL if no configuration was previously active or the activationResult was FALSE for failure.
If a service configuration was requested, configObjId field shall referenced a ServiceConfiguration, the response shall contain a list of a single item of the previous ServiceConfiguration COM object.
If a provider configuration was requested, configObjId field shall referenced a ProviderConfiguration, the response shall contain a list of the previous ProviderConfiguration COM object followed by the list of previous ServiceConfiguration objects active for that provider.
     * @return Returns the MAL message created by the response
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage sendResponse(Boolean activationResult,
            org.ccsds.moims.mo.com.structures.ObjectIdList previousConfig) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return interaction.sendResponse((activationResult == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(activationResult), previousConfig);
    }

    /**
     * Sends an error to the consumer.
     * 
     * @param error The MAL error to send to the consumer.
     * @return Returns the MAL message created by the error
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage sendError(org.ccsds.moims.mo.mal.MOErrorException error) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return interaction.sendError(error);
    }

}
