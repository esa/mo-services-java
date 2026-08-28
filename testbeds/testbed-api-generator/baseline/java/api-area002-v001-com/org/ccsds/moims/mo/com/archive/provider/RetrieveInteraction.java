package org.ccsds.moims.mo.com.archive.provider;

/**
 * Provider INVOKE interaction class for Archive::retrieve operation.
 */
public class RetrieveInteraction {

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
    public RetrieveInteraction(org.ccsds.moims.mo.mal.provider.MALInvoke interaction) {
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
     * @param objDetails The response shall contain the set of matched objects.
The first returned list shall contain the matched object instance identifiers and object details of the matched objects.
The second returned list shall contain the object bodies ordered identically to the first list unless no body for the object is declared in the service specification, in which case a NULL replaces the complete list.
There shall be an entry in each returned list for each matched object.
When no objects have been matched only a response with NULL for each part of the response shall be returned.
The ordering of the returned objects is not specified and implementation specific.
If ordering of the returned objects is required then the query operation should be used instead.
     * @param objBodies objBodies Argument number 1 as defined by the service operation
     * @return Returns the MAL message created by the response
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage sendResponse(org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList objDetails,
            org.ccsds.moims.mo.mal.structures.HeterogeneousList objBodies) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return interaction.sendResponse(objDetails, objBodies);
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
