package org.ccsds.moims.mo.common.configuration.provider;

/**
 * Provider INVOKE interaction class for Configuration::storeCurrent operation.
 */
public class StoreCurrentInteraction {

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
    public StoreCurrentInteraction(org.ccsds.moims.mo.mal.provider.MALInvoke interaction) {
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
     * @param objInstId The response shall contain the object identifier of the new configuration object if successful or NULL if not.
     * @return Returns the MAL message created by the response
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage sendResponse(org.ccsds.moims.mo.com.structures.ObjectId objInstId) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return interaction.sendResponse(objInstId);
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
