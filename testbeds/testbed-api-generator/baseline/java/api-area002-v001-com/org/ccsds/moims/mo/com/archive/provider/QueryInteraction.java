package org.ccsds.moims.mo.com.archive.provider;

/**
 * Provider PROGRESS interaction class for Archive::query operation.
 */
public class QueryInteraction {

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
    public QueryInteraction(org.ccsds.moims.mo.mal.provider.MALProgress interaction) {
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
     * @param objType objType Argument number 0 as defined by the service operation
     * @param domain domain Argument number 1 as defined by the service operation
     * @param objDetails objDetails Argument number 2 as defined by the service operation
     * @param objBodies objBodies Argument number 3 as defined by the service operation
     * @return Returns the MAL message created by the update
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage sendUpdate(org.ccsds.moims.mo.com.structures.ObjectType objType,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList objDetails,
            org.ccsds.moims.mo.mal.structures.HeterogeneousList objBodies) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return interaction.sendUpdate(objType, domain, objDetails, objBodies);
    }

    /**
     * Sends a PROGRESS response to the consumer.
     * 
     * @param objType The updates and the responses shall contain the set of matched objects.
If a wildcard was used in the ObjectType part of the request then the updates and response shall contain the ObjectType of each matched object.
If there was not any wildcards in the ObjectType part of the request the ObjectType in the updates and response shall be replaced by a NULL.
The first returned list shall contain the domain of the objects being returned.
If multiple ObjectTypes or domains have been matched then multiple Update message may be returned.
There shall be an entry in the second and third lists for each matched object.
The second returned list shall contain the archive details stored for the matched objects.
If the initial Boolean of the request was True the third returned list shall contain the bodies of the objects.
If the initial Boolean of the request was NULL or False the third returned list shall be replaced by a NULL.
The returned lists shall be sorted based on the sorting options specified in ArchiveQuery.
Each domain/object type pair shall be sorted separately from other domain/object type pairs, there is no requirement for sorting to be applied across domain/object type pairs.
When the field being sorted on contains a NULL value, or does not exist in the matched object (due to a containing composite being NULL), these entries shall be added to the end of the returned list in the order that they are matched.
When no objects have been matched only a response with NULL for each part of the response shall be returned.
     * @param domain domain Argument number 1 as defined by the service operation
     * @param objDetails objDetails Argument number 2 as defined by the service operation
     * @param objBodies objBodies Argument number 3 as defined by the service operation
     * @return Returns the MAL message created by the response
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage sendResponse(org.ccsds.moims.mo.com.structures.ObjectType objType,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList objDetails,
            org.ccsds.moims.mo.mal.structures.HeterogeneousList objBodies) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return interaction.sendResponse(objType, domain, objDetails, objBodies);
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
