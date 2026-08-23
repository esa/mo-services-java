package org.ccsds.moims.mo.mc.check.provider;

/**
 * Provider PROGRESS interaction class for Check::getSummaryReport operation.
 */
public class GetSummaryReportInteraction {

    /**
     * The interaction field.
     */
    private org.ccsds.moims.mo.mal.provider.MALProgress interaction;

    /**
     * Wraps the provided MAL interaction object with methods for sending responses
     * to an PROGRESS interaction from a provider.
     * 
     * @param interaction The MAL interaction action object to use.
     */
    public GetSummaryReportInteraction(org.ccsds.moims.mo.mal.provider.MALProgress interaction) {
        this.interaction = interaction;
    }

    /**
     * Returns the MAL interaction object used for returning messages from the
     * provider.
     * 
     * @return The MAL interaction object provided in the constructor
     */
    public org.ccsds.moims.mo.mal.provider.MALProgress getInteraction() {
        return interaction;
    }

    /**
     * Sends a PROGRESS acknowledge to the consumer.
     * 
     * @return Returns the MAL message created by the acknowledge
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage sendAcknowledgement() throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return interaction.sendAcknowledgement((Object[]) null);
    }

    /**
     * Sends a PROGRESS update to the consumer.
     * 
     * @param updateObjInstIds The returned updates and final response shall contain an entry for each requested CheckIdentity.
The first part of the update shall be the CheckIdentity object instance identifier.
The second part shall be the list of all CheckLink object instance identifiers and CheckResults associated with that CheckIdentity.
     * @param updateSummaries updateSummaries Argument number 1 as defined by the service operation
     * @return Returns the MAL message created by the update
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage sendUpdate(Long updateObjInstIds,
            org.ccsds.moims.mo.mc.check.structures.CheckResultSummaryList updateSummaries) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return interaction.sendUpdate((updateObjInstIds == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(updateObjInstIds), updateSummaries);
    }

    /**
     * Sends a PROGRESS response to the consumer.
     * 
     * @param responseObjInstIds responseObjInstIds Argument number 0 as defined by the service operation
     * @param responseSummaries responseSummaries Argument number 1 as defined by the service operation
     * @return Returns the MAL message created by the response
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage sendResponse(Long responseObjInstIds,
            org.ccsds.moims.mo.mc.check.structures.CheckResultSummaryList responseSummaries) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return interaction.sendResponse((responseObjInstIds == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(responseObjInstIds), responseSummaries);
    }

    /**
     * Sends an error to the consumer.
     * 
     * @param error error The MAL error to send to the consumer.
     * @return Returns the MAL message created by the error
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage sendError(org.ccsds.moims.mo.mal.MOErrorException error) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return interaction.sendError(error);
    }

    /**
     * Sends an update error to the consumer.
     * 
     * @param error error The MAL error to send to the consumer.
     * @return Returns the MAL message created by the error
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage sendUpdateError(org.ccsds.moims.mo.mal.MOErrorException error) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return interaction.sendUpdateError(error);
    }

}
